package ListDatos;

import utiles.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objeto base para crear una select independiente de la base de datos
 */
public final class JSelect implements java.io.Serializable, ISelectEjecutarSelect, Cloneable {

    private static final long serialVersionUID = 33333315L;
    /**
     *Constante indica todos los campos de la tabla
     */
    public static final String mcsTodosCampos = "*";
    /**
     *Constante Indica el tipo de Campos de la select sin modicicador (sin disticnt)
     */
    public static final int mclCamposNada = 0;
    /**
     *Constante Indica el tipo de Campos de la select sin repetidos
     */
    public static final int mclCamposDistinct = 1;

    //Lista de campos
    private final JListaElementos moCampos = new JListaElementos();
    //tipo de los campos
    private int mlCamposTipo = mclCamposNada;
    //where
    private JListDatosFiltroConj moFiltro = null;
    //form
    private JSelectFrom moTablas = null;
    //Lista de campos de group
    private JListaElementos moGroup = new JListaElementos();
    //Lista de campos del order
    private JListaElementos moOrder = new JListaElementos();
    //indica si es comprimido o no
    private boolean mbComprimido = false;
    /**select a pelo, si esta establecida se ejecuta esta, procurar no usar*/
    public String msSelectAPelo = null;
    /**
     *Permisos
     */
    private String msUsuario = null;
     /**
     *PassWord
     */
    private String msPassWord = null;
    /**
     *Permisos
     */
    private String msPermisos = null;

    private IListaElementos<Serializable> moMETAINFORMACION=null;

    /**
     * Constructor
     */
    public JSelect() {
        super();
        setTabla(null);
    }

    /**
     * Constructor
     * @param psTabla tabla inicial de la select
     */
    public JSelect(final String psTabla) {
        super();
        setTabla(psTabla);
    }

    /**
     * Constructor
     * @param psTabla tabla inicial de la select
     * @param psTablaAlias Alias de la tabla inicial de la select
     */
    public JSelect(final String psTabla, final String psTablaAlias) {
        super();
        setTabla(psTabla, psTablaAlias);
    }

    /**
     * anade un campo a la select
     * @param psNombre nombre del campo
     */
    public void addCampo(final String psNombre) {
        moCampos.add(new JSelectCampo(psNombre));
    }

    /**
     * anade un campo a la select
     * @param psTabla nombre tabla
     * @param psNombre nombre del campo
     */
    public void addCampo(final String psTabla, final String psNombre) {
        moCampos.add(new JSelectCampo(psTabla, psNombre));
    }

    /**
     * anade un campo a la select
     * @param plFuncion funcion
     * @param psTabla nombre tabla
     * @param psNombre nombre campo
     */
    public void addCampo(final int plFuncion, final String psTabla, final String psNombre) {
        moCampos.add(new JSelectCampo(plFuncion, psTabla, psNombre));
    }

    /**
     * anade un campo a la select
     * @param poCampo campo a anadir
     */
    public void addCampo(final JSelectCampo poCampo) {
        moCampos.add(poCampo);
    }

    /**
     * devuelve el campo de la select
     * @return objeto campo
     * @param i indice del campo
     */
    public JSelectCampo getCampo(final int i) {
        return (JSelectCampo) moCampos.get(i);
    }

    public IListaElementos getCampos() {
        return moCampos;
    }

    /**
     * numero de campos
     * @return numero de campos
     */
    public int getCountCampos() {
        return moCampos.size();
    }

    /**borramos todos los campo*/
    public void removeAllCampos() {
        moCampos.clear();
    }

    /**
     * tipo(Distinct,...) inicial de los campos de la select
     * @param plTipo establece una propiedad de los campos de la select(por ejemplo mclCamposDistinct)
     */
    public void setCamposTipo(final int plTipo) {
        mlCamposTipo = plTipo;
    }


    /**Devuelve si un campo esta en la lista de ordenacion*/
    public boolean isCampoOrdenado(String psTabla, String psCampo){
        boolean lbResult = false;
        for(int i = 0 ; i < moOrder.size() && !lbResult; i++){
            JSelectCampo loCampo = (JSelectCampo) moOrder.get(i);
            if(loCampo.getTabla().equalsIgnoreCase(psTabla) &&
               loCampo.getNombre().equalsIgnoreCase(psCampo)){
                lbResult = true;
            }
        }
        return lbResult;    
    }

    /**
     *
     * @return Devuelve si hay agrupacion
     */
    public boolean isAgrupado(){
        return getCamposGroup().size()>0;
    }
    /**
     *
     * @param Establece si es agrupado
     */
    public void setAgrupado(boolean pbAgrupado){
        getCamposGroup().clear();
        if(pbAgrupado){
            for(int i = 0 ; i < moCampos.size(); i++){
                JSelectCampo loCampo = (JSelectCampo) moCampos.get(i);
                if(loCampo.getFuncion() == loCampo.mclFuncionNada){
                    addCampoGroup(loCampo.getTabla(), loCampo.getNombre());
                }
            }
        }
    }

    /**
     * devuevle el objeto form de la select
     * @return el objeto from
     */
    public JSelectFrom getFrom() {
        return moTablas;
    }

    /**
     * establece la tabla inicial
     * @param psTabla tabla inicial
     */
    public void setTabla(final String psTabla) {
        moTablas = new JSelectFrom(psTabla);
    }

    /**
     * establece la tabla inicial
     * @param psTabla tabla inicial
     */
    public void setTabla(final String psTabla,final String psTablaAlias) {
        moTablas = new JSelectFrom(psTabla, psTablaAlias);
    }

    /**
     * anade un campo del group by a la select
     * @param psNombre nombre del campo
     */
    public void addCampoGroup(final String psNombre) {
        addCampoGroup(new JSelectCampo(psNombre));
    }

    /**
     * anade un campo del group by a la select
     * @param psTabla  nombre del tabla
     * @param psNombre  nombre del campo
     */
    public void addCampoGroup(final String psTabla, final String psNombre) {
        addCampoGroup(new JSelectCampo(psTabla, psNombre));
    }

    /**
     * anade un campo del group by a la select y el campo del select simultaneamente
     * @param psTabla  nombre del tabla
     * @param psNombre  nombre del campo
     */
    public void addCampoGroupYCampo(final String psTabla, final String psNombre) {
        addCampoGroup(new JSelectCampo(psTabla, psNombre));
        addCampo(new JSelectCampo(psTabla, psNombre));
    }
    /**
     * anade un campo del group by a la select y el campo del select simultaneamente
     * @param poCampo  campo
     */
    public void addCampoGroupYCampo(JSelectCampo poCampo) {
        try {
            addCampoGroup((JSelectCampo) poCampo.clone());
            addCampo((JSelectCampo) poCampo.clone());
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto(JSelect.class.getName(), ex);
        }
    }
    
    /**
     * anade un campo del group by a la select
     * @param poCampo objeto campo
     */
    public void addCampoGroup(final JSelectCampo poCampo) {
        if (moGroup == null) {
            moGroup = new JListaElementos();
        }
        moGroup.add(poCampo);
    }
    public IListaElementos getCamposGroup(){
        if (moGroup == null) {
            moGroup = new JListaElementos();
        }
        return moGroup;
    }

    /**
     * anade un campo del order by a la select
     * @param psNombre  nombre del campo
     */
    public void addCampoOrder(final String psNombre) {
        addCampoOrder(new JSelectCampo(psNombre));
    }

    /**
     * anade un campo del order by a la select
     * @param psTabla nombre del tabla
     * @param psNombre  nombre del campo
     */
    public void addCampoOrder(final String psTabla, final String psNombre) {
        addCampoOrder(new JSelectCampo(psTabla, psNombre));
    }
    /**
     * anade un campo del order by a la select
     * @param psTabla nombre del tabla
     * @param psNombre  nombre del campo
     */
    public void addCampoOrder(final String psTabla, final String psNombre, final boolean pbAscendente) {
        if(pbAscendente){
            addCampoOrder(new JSelectCampo(psTabla, psNombre));
        }else{
            addCampoOrder(new JSelectCampo(JSelectCampo.mclOrdenDesc,psTabla, psNombre));
        }
    }

    /**
     * anade un campo del order by a la select
     * @param poCampo  objeto campo
     */
    public void addCampoOrder(final JSelectCampo poCampo) {
        if (moOrder == null) {
            moOrder = new JListaElementos();
        }
        moOrder.add(poCampo);
    }

    public IListaElementos getCamposOrder(){
        if (moOrder == null) {
            moOrder = new JListaElementos();
        }
        return moOrder;
    }

    /**
     * devuelve el objeto where de la select
     * @return objeto where
     */
    public JListDatosFiltroConj getWhere() {
        if (moFiltro == null) {
            moFiltro = new JListDatosFiltroConj();
        }
        return moFiltro;
    }

    public String msSQL(final ISelectMotor poSelect) {
        String lsSelect = msSelectAPelo;
        if (lsSelect == null) {
            String lsWhere = null;
            String lsWhere2 = null;
            String lsFrom = null;
            String lsGroup = null;
            String lsOrder = null;

            //from
            JSelectFromParte loSelectFrom = moTablas.msSQL(poSelect);
            lsFrom = loSelectFrom.getFrom();
            lsWhere = loSelectFrom.getWhere();
            loSelectFrom = null;

            //where
            if (moFiltro != null) {
                lsWhere2 = moFiltro.msSQL(poSelect);
            }
            if (lsWhere2 != null) {
                if (lsWhere == null) {
                    lsWhere = lsWhere2;
                } else {
                    lsWhere = poSelect.msAnd(lsWhere, lsWhere2);
                }
            }

            //group
            lsGroup = poSelect.msListaCamposGroup(moGroup);
            //order
            lsOrder = poSelect.msListaCamposOrder(moOrder);
            lsSelect = poSelect.msSelect(
                    poSelect.msListaCampos(mlCamposTipo, moCampos),
                    lsFrom, lsWhere, lsGroup, null, lsOrder);

            lsWhere = null;
            lsWhere2 = null;
            lsFrom = null;
            lsGroup = null;
            lsOrder = null;
        }
        return lsSelect;
    }


    /**
     * devulve la select en texto
     * @return cadena
     */
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }

    /**
     * devulve el objeto select serializado
     * @return cadena del objeto serializado
     */
    public String toStringObjectCompleto() {
        String lsCadena = "";
        try {
            //mandamos los parametros
            ByteArrayOutputStream bs = new ByteArrayOutputStream(512);
            ObjectOutputStream salida = new ObjectOutputStream(bs);
            salida.writeObject(this);
            salida.flush();
            lsCadena = bs.toString();
            bs.close();
            salida.close();
        } catch (Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e);
        }
        return lsCadena;
    }

    public String getPassWord() {
        return msPassWord;
    }

    public String getPermisos() {
        return msPermisos;
    }

    public String getUsuario() {
        return msUsuario;
    }

    /**
     * Establece si se devuelve comprimido
     */
    public void setComprimido(final boolean pbValor) {
        mbComprimido = pbValor;
    }

    /**
     * Indica si se devuelve comprimido
     */
    public boolean getComprimido() {
        return mbComprimido;
    }

    public Object clone() throws CloneNotSupportedException {
        JSelect loSelect = new JSelect();

        loSelect.setCamposTipo(mlCamposTipo);
        loSelect.setComprimido(mbComprimido);
        loSelect.msSelectAPelo = msSelectAPelo;

        loSelect.setWhere((JListDatosFiltroConj) getWhere().clone());

        loSelect.setFrom((JSelectFrom) getFrom().clone());

        for (int i = 0; i < moCampos.size(); i++) {
            loSelect.addCampo((JSelectCampo) ((JSelectCampo) moCampos.get(i)).clone());
        }
        if (moGroup != null) {
            for (int i = 0; i < moGroup.size(); i++) {
                loSelect.addCampoGroup((JSelectCampo) ((JSelectCampo) moGroup.get(i)).clone());
            }
        }
        if (moOrder != null) {
            for (int i = 0; i < moOrder.size(); i++) {
                loSelect.addCampoOrder((JSelectCampo) ((JSelectCampo) moOrder.get(i)).clone());
            }
        }
        if (moMETAINFORMACION != null) {
            for (int i = 0; i < moMETAINFORMACION.size(); i++) {
                Serializable uno = moMETAINFORMACION.get(i);
                if (uno instanceof JSelectMeta){
                    loSelect.addMETAINFORMACION((JSelectMeta)((JSelectMeta)uno).clone());
                } else {
                    loSelect.addMETAINFORMACION(uno);
                }
            }
        }

        return loSelect;
    }

    void setFrom(final JSelectFrom poFrom) {
        moTablas = poFrom;
    }

    void setWhere(final JListDatosFiltroConj jListDatosFiltroConj) {
        moFiltro = jListDatosFiltroConj;
    }

    /**
     * @param msUsuario the msUsuario to set
     */
    public void setUsuario(final String msUsuario) {
        this.msUsuario = msUsuario;
    }

    /**
     * @param msPassWord the msPassWord to set
     */
    public void setPassWord(final String msPassWord) {
        this.msPassWord = msPassWord;
    }

    /**
     * @param msPermisos the msPermisos to set
     */
    public void setPermisos(final String msPermisos) {
        this.msPermisos = msPermisos;
    }
    /**Devuelve la lista de elementos de metainformacion*/
    public synchronized IListaElementos<Serializable> getMETAINFORMACION(){
        if(moMETAINFORMACION==null){
            moMETAINFORMACION = new JListaElementos<Serializable>();
        }
        return moMETAINFORMACION;
    }
    /**add elemento de metainformacion, nos aseguramos q sea serializable
     * @param poObject
     */
    public void addMETAINFORMACION(final Serializable poObject){
        getMETAINFORMACION().add(poObject);
    }

    public JSelectMeta getMETAFor_name(String name) {
        if (moMETAINFORMACION == null) {
            return null;
        }

        JSelectMeta meta = null;
        for (int i = 0; i < moMETAINFORMACION.size(); i++) {
            Object uno = moMETAINFORMACION.get(i);
            if (uno instanceof JSelectMeta
                    && (name.equals(((JSelectMeta) uno).getName()))) {
                meta = (JSelectMeta) uno;
                break;
            }
        }

        return meta;
    }

    public JSelectMeta getMETAPagina_actual() {
        return getMETAFor_name(JSelectMeta.pagina_actual);
    }

    public void setMETAPagina_actual(int pagina_actual) {
        JSelectMeta obj = getMETAPagina_actual();
        if (obj == null) {
            obj = new JSelectMeta(JSelectMeta.pagina_actual, null);
            addMETAINFORMACION(obj);
        }

        obj.setValue(String.valueOf(pagina_actual));
    }

    public JSelectMeta getMETAPagina_size() {
        return getMETAFor_name(JSelectMeta.pagina_size);
    }

    public void setMETAPagina_size(int pagina_size) {
        JSelectMeta obj = getMETAPagina_size();
        if (obj == null) {
            obj = new JSelectMeta(JSelectMeta.pagina_size, null);
            addMETAINFORMACION(obj);
        }

        obj.setValue(String.valueOf(pagina_size));
    }

    public JSelectMeta getMETAPagina_more_pages() {
        return getMETAFor_name(JSelectMeta.pagina_more_pages);
    }

    public void setMETAPagina_more_pages(boolean has_more_pages) {
        JSelectMeta obj = getMETAPagina_more_pages();
        if (obj == null) {
            obj = new JSelectMeta(JSelectMeta.pagina_more_pages, null);

            addMETAINFORMACION(obj);
        }

        obj.setValueAsBoolean(has_more_pages);
    }
    
    public JSelectMeta getMETAVersion_motor() {
        return getMETAFor_name(JSelectMeta.version_motor);
    }

    public void setMETAVersion_motor(int version_motor) {
        JSelectMeta obj = getMETAVersion_motor();
        if (obj == null) {
            obj = new JSelectMeta(JSelectMeta.version_motor, null);

            addMETAINFORMACION(obj);
        }

        obj.setValueAsInteger(version_motor);
    }
}