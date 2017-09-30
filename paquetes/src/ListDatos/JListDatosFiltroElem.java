package ListDatos;

import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;

/**
 *Objeto que contiene una condicion para filtrar un JListDatos 
 */
public final class JListDatosFiltroElem implements IListDatosFiltro {

    private static final long serialVersionUID = 3333339L;
    private int mlCompara;
    private int[] malCampos = null;
    private String[] masCampos = null;
    private String[] masValores = null;
    private int[] malTipos = null;
    private String msTabla = null;
    private JOrdenacion moOrden = null;
    private IFilaDatos moFilaDatosCom = null;
    private boolean mbIgnoreCASE = true;
    private int mlUnionCondiciones = JListDatosFiltroConj.mclAND;

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param plCampo campo
     * @param psValor valor
     */
    public JListDatosFiltroElem(final int plCompara, final int plCampo,
            final String psValor) {
        setFiltroElem(plCompara, plCampo, psValor);
    }

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param palCampos campos
     * @param pasValores valores
     */
    public JListDatosFiltroElem(final int plCompara, final int[] palCampos,
            final String[] pasValores) {
        setFiltroElem(plCompara, palCampos, pasValores);
    }

    /**
     * Constructor para condiciones de SQL
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public JListDatosFiltroElem(final int plCompara, final String[] pasCampos,
            final String[] pasValores, final int[] palTipos) {
        setFiltroElem(plCompara, pasCampos, pasValores, palTipos);
    }

    /**
     * Constructor para condiciones de SQL
     * @param psTabla tabla
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public JListDatosFiltroElem(final int plCompara, final String psTabla, 
            final String[] pasCampos, final String[] pasValores, final int[] palTipos) {
        setFiltroElem(plCompara, psTabla, pasCampos, pasValores, palTipos);
    }

    /**
     * Constructor para condiciones de JListDatos/SQL
     * @param palCampos lista de posiciones de campos
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public JListDatosFiltroElem(final int plCompara, final int[] palCampos, 
            final String[] pasCampos, final String[] pasValores, final int[] palTipos) {
        setFiltroElem(plCompara, palCampos, pasCampos, pasValores, palTipos);
    }
    /**
     * Constructor para condiciones de JListDatos/SQL
     * @param plUnion Union de las condiciones
     * @param palCampos lista de posiciones de campos
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public JListDatosFiltroElem(final int plUnion, final int plCompara,
            final String psTabla, final int[] palCampos, final String[] pasCampos,
            final String[] pasValores, final int[] palTipos) {
        setFiltroElem(plUnion, plCompara, psTabla, palCampos, pasCampos,
            pasValores, palTipos);
    }

    public void inicializar(final String psTabla, final int[] palTodosTipos,
            final String[] pasTodosCampos) {
        //inicializamos los tipos de campos
        if(pasTodosCampos!=null && palTodosTipos!=null){
            if (malCampos != null) {
                malTipos = new int[malCampos.length];
                for (int i = 0; i < malCampos.length; i++) {
                    malTipos[i] = palTodosTipos[malCampos[i]];
                }
                //inicializamos los nombres de campos
                masCampos = new String[malCampos.length];
                for (int i = 0; i < malCampos.length; i++) {
                    masCampos[i] = pasTodosCampos[malCampos[i]];
                }
            }else if (masCampos != null) {
                malTipos = new int[masCampos.length];
                for(int i = 0 ; i < masCampos.length; i++){
                    for(int ii = 0 ; ii < pasTodosCampos.length; ii++){
                        if(pasTodosCampos[ii].equalsIgnoreCase(masCampos[i])){
                            malTipos[i] = palTodosTipos[ii];
                        }
                    }
                }
            }
        }
        //tabla
        msTabla = psTabla;
        moOrden = null;
        moFilaDatosCom = null;
    }
  public void inicializar(JListDatos poList){
      inicializar(poList.getTablaOAlias(), poList.getFields().malTipos(), poList.getFields().msNombres());
  }
  public void inicializar(String psTabla,JFieldDefs poCampos){
      inicializar(psTabla, poCampos.malTipos(), poCampos.msNombres());
  }

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param plCampo campo
     * @param psValor valor
     */
    public void setFiltroElem(final int plCompara, final int plCampo,
            final String psValor) {
        setFiltroElem(JListDatosFiltroConj.mclAND, plCompara, null,
                new int[]{plCampo}, null, new String[]{psValor}, null);
    }

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param palCampos campos
     * @param pasValores valores
     */
    public void setFiltroElem(final int plCompara, final int[] palCampos,
            final String[] pasValores) {
        setFiltroElem(JListDatosFiltroConj.mclAND, plCompara, null, palCampos,
                null, pasValores, null);
    }

    /**
     * Constructor para condiciones de SQL
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public void setFiltroElem(final int plCompara, final String[] pasCampos,
            final String[] pasValores, final int[] palTipos) {
        setFiltroElem(JListDatosFiltroConj.mclAND, plCompara, (String)null, null,
                pasCampos, pasValores, palTipos);
    }

    /**
     * Constructor para condiciones de SQL
     * @param psTabla tabla
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public void setFiltroElem(final int plCompara, final String psTabla, 
            final String[] pasCampos, final String[] pasValores, final int[] palTipos) {
        setFiltroElem(JListDatosFiltroConj.mclAND, plCompara, psTabla, null,
                pasCampos, pasValores, palTipos);
    }
    /**
     * Constructor para condiciones de JListDatos/SQL
     * @param palCampos lista de posiciones de campos
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public void setFiltroElem(final int plCompara, final int[] palCampos, 
            final String[] pasCampos, final String[] pasValores, final int[] palTipos) {
        setFiltroElem(JListDatosFiltroConj.mclAND, plCompara, (String)null,
                palCampos, pasCampos, pasValores, palTipos);
    }

    /**
     * Constructor para condiciones de JListDatos/SQL
     * @param plUnion Union de las condiciones
     * @param palCampos lista de posiciones de campos
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public void setFiltroElem(final int plUnion, final int plCompara,
            final String psTabla, final int[] palCampos, final String[] pasCampos,
            final String[] pasValores, final int[] palTipos) {
        mlCompara = plCompara;
        msTabla = psTabla;
        mlUnionCondiciones = plUnion;
        if (palCampos != null) {
            malCampos = new int[palCampos.length];
            System.arraycopy(palCampos, 0, malCampos, 0, malCampos.length);
        } else{
            malCampos = null;
        }
        if (pasCampos != null) {
            masCampos = new String[pasCampos.length];
            System.arraycopy(pasCampos, 0, masCampos, 0, masCampos.length);
        }else{
            masCampos = null;
        }
        if (pasValores != null) {
            masValores = new String[pasValores.length];
            System.arraycopy(pasValores, 0, masValores, 0, masValores.length);
        }else{
            masValores = null;
        }
        if (palTipos != null) {
            malTipos = new int[palTipos.length];
            System.arraycopy(palTipos, 0, malTipos, 0, malTipos.length);
        }else{
            malTipos = null;
        }
        moOrden = null;
        moFilaDatosCom = null;
    }

    private void crearFilaCompararYOrden() {
        if (moOrden == null) {
            //creamos el objeto que nos comparara filas
            moOrden = new JOrdenacion(malCampos, malTipos, true, mbIgnoreCASE);
            //
            //creamos la fila a comparar
            //
            int i;
            int lMax = 0;
            for (i = 0; i < malCampos.length; i++) {
                if (malCampos[i] > lMax) {
                    lMax = malCampos[i];
                }
            }

            String[] lsCadenas = new String[lMax + 1];

            for (i = 0; i < malCampos.length; i++) {
                if(masValores[i]==null){
                    masValores[i]="";
                }
                lsCadenas[malCampos[i]] = masValores[i];
                if (mbIgnoreCASE) {
                    lsCadenas[malCampos[i]] = masValores[i].toLowerCase();
                }
            }
            moFilaDatosCom = new JFilaDatosDefecto(lsCadenas);
        }
    }

    public boolean mbCumpleFiltro(final IFilaDatos poFila) {
        boolean lbEncontrado = false;
        crearFilaCompararYOrden();

        if (malCampos.length == masValores.length) {
            //
            //realizamos el filtrado
            //
            int lComparacion = 0;
            if (mlCompara != JListDatos.mclTLike) {
                lComparacion = moOrden.compare(poFila, moFilaDatosCom);
            }
            switch (mlCompara) {
                case JListDatos.mclTIgual:
                    lbEncontrado = (lComparacion == JOrdenacion.mclIgual);
                    break;
                case JListDatos.mclTMayor:
                    lbEncontrado = (lComparacion == JOrdenacion.mclMayor);
                    break;
                case JListDatos.mclTMenor:
                    lbEncontrado = (lComparacion == JOrdenacion.mclMenor);
                    break;
                case JListDatos.mclTMayorIgual:
                    lbEncontrado =
                            (lComparacion == JOrdenacion.mclIgual) ||
                            (lComparacion == JOrdenacion.mclMayor);
                    break;
                case JListDatos.mclTMenorIgual:
                    lbEncontrado =
                            (lComparacion == JOrdenacion.mclIgual) ||
                            (lComparacion == JOrdenacion.mclMenor);
                    break;
                case JListDatos.mclTDistinto:
                    lbEncontrado = (lComparacion != JOrdenacion.mclIgual);
                    break;
                case JListDatos.mclTLike:
                    if (mbIgnoreCASE) {
                        //no hace falta hacer lowercase para la fila a comparar pq ya se ha hecho antes
                        lbEncontrado = (poFila.msCampo(malCampos[0]).toLowerCase().indexOf(moFilaDatosCom.msCampo(malCampos[0])) >= 0);
                    } else {
                        lbEncontrado = (poFila.msCampo(malCampos[0]).indexOf(moFilaDatosCom.msCampo(malCampos[0])) >= 0);
                    }
                    break;
                default:

            }
        }
        return lbEncontrado;
    }

    public String msSQL(final ISelectMotor poSelect) {
        String lsSQL = "";
        for (int i = 0; i < masCampos.length; i++) {
            String lsIzq = poSelect.msCondicion(poSelect.msCampo(JSelectCampo.mclFuncionNada, msTabla, masCampos[i]), mlCompara, masValores[i], null, malTipos[i]);
            if (i > 0) {
                if (mlUnionCondiciones == JListDatosFiltroConj.mclAND) {
                    lsSQL = poSelect.msAnd(lsSQL, lsIzq);
                } else {
                    lsSQL = poSelect.msOr(lsSQL, lsIzq);
                }
            } else {
                lsSQL = lsIzq;
            }
        }
        return lsSQL;
    }
    public boolean isMismoCampo(){
        boolean lbResult = true;
        String lsCampo = masCampos[0];
        for(int i = 1; i < masCampos.length && lbResult; i++){
            if(!lsCampo.equals(masCampos[i])){
                lbResult = false;
            }
        }

        return lbResult;
    }
    public String getCampo(int plIndex){
        return masCampos[plIndex];
    }
    public String getValor(int plIndex){
        return masValores[plIndex];
    }
    public String getTabla(){
        return msTabla;
    }
    public int getComparacion(){
        return mlCompara;
    }
    public int getUnionCondiciones(){
        return mlUnionCondiciones;
    }
    public JListDatosFiltroConj getFiltroConj(){
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        if(malCampos==null){
            malCampos = new int[masCampos.length];
        }
        if(masCampos==null){
            masCampos = new String[malCampos.length];
        }
        for(int i = 0 ; i < masCampos.length; i++){
            loFiltro.addCondicion(
                    mlUnionCondiciones,
                    new JListDatosFiltroElem(
                        mlUnionCondiciones,
                        mlCompara,
                        msTabla,
                        new int[]{malCampos[i]},
                        new String[]{masCampos[i]},
                        new String[]{masValores[i]},
                        new int[]{malTipos[i]}
                        )
                    );
        }
        return loFiltro;
    }

    public Object clone() {
        JListDatosFiltroElem loCon = new JListDatosFiltroElem(mlCompara, malCampos, masCampos, masValores, malTipos);
        if (msTabla != null) {
            loCon.msTabla = msTabla;
        }
        return loCon;
    }

    /**
     * clonacion del objeto con un tipo dado
     * @return objeto clonado
     */
    public JListDatosFiltroElem Clone() {
        return (JListDatosFiltroElem) clone();
    }

    /**
     * devuelve el objeto en string
     * @return cadena
     */
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
}
