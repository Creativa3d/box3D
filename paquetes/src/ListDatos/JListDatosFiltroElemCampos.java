package ListDatos;

import ListDatos.estructuraBD.JFieldDefs;
import utiles.JDepuracion;

/**
 *Objeto que contiene una condicion para filtrar un JListDatos 
 */
public final class JListDatosFiltroElemCampos implements IListDatosFiltro {

    private static final long serialVersionUID = 1L;
    private int mlCompara;
    private int mlCampo = -1;
    private int mlCampo2 = -1;
    private String msCampo = null;
    private String msCampo2 = null;
    private int mlTipo = JListDatos.mclTipoCadena;
    private String msTabla = null;
    private JOrdenacion moOrden = null;
    private boolean mbIgnoreCASE = true;
    private String msTabla2 = null;

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param plCampo campo
     * @param plCampo2 campo2
     */
    public JListDatosFiltroElemCampos(final int plCompara, final int plCampo,
            final int plCampo2) {
        setFiltroElem(plCompara, null, plCampo, null, null, plCampo2, null, JListDatos.mclTipoCadena);
    }

    /**
     * Constructor para condiciones de SQL
     * @param plCompara tipo de comparacion
     * @param psCampo
     * @param psCampo2
     * @param plTipo
     */
    public JListDatosFiltroElemCampos(final int plCompara
            , final String psCampo, final String psCampo2, int plTipo) {
        setFiltroElem(plCompara
                , null, -1, psCampo
                , null, -1, psCampo2, plTipo);
    }    
    public JListDatosFiltroElemCampos(final int plCompara
            , final String psTabla, final String psCampo
            , final String psTabla2, final String psCampo2
    ) {
        setFiltroElem(plCompara
                , psTabla, -1, psCampo
                , psTabla2, -1, psCampo2, JListDatos.mclTipoCadena);
    }

    /**
     * Constructor para condiciones de SQL
     * @param plCompara tipo de comparacion
     * @param psTabla tabla
     * @param psCampo campo
     * @param psCampo2 campo2
     * @param plTipo tipos de los campos
     */
    public JListDatosFiltroElemCampos(final int plCompara, final String psTabla,
            final String psCampo, final String psCampo2, final int plTipo) {
        setFiltroElem(plCompara, psTabla, -1, psCampo,psTabla, -1, psCampo2, plTipo);
    }

    private JListDatosFiltroElemCampos(final int plCompara, final String psTabla, final int plCampo,
            final String psCampo, final int plCampo2, final String psCampo2, final int plTipo) {
        setFiltroElem(plCompara, psTabla, plCampo, psCampo,psTabla, plCampo2, psCampo2, plTipo);
    }

    @Override
    public void inicializar(final String psTabla, final int[] palTodosTipos,
            final String[] pasTodosCampos) {
        //inicializamos los tipos de campos
        if (pasTodosCampos != null && palTodosTipos != null) {
            if (mlCampo >= 0) {
                mlTipo = palTodosTipos[mlCampo];
                msCampo = pasTodosCampos[mlCampo];
            } else if (msCampo != null) {
                for (int ii = 0; ii < pasTodosCampos.length; ii++) {
                    if (pasTodosCampos[ii].equalsIgnoreCase(msCampo)) {
                        mlTipo = palTodosTipos[ii];
                    }
                }
            }
            if (mlCampo2 >= 0) {
                mlTipo = palTodosTipos[mlCampo2];
                msCampo2 = pasTodosCampos[mlCampo2];
            } else if (msCampo2 != null) {
                for (int ii = 0; ii < pasTodosCampos.length; ii++) {
                    if (pasTodosCampos[ii].equalsIgnoreCase(msCampo2)) {
                        mlTipo = palTodosTipos[ii];
                    }
                }
            }
        }
        //tabla
        msTabla = psTabla;
        msTabla2 = psTabla;
        moOrden = null;
    }
public void inicializar(JListDatos poList){
      inicializar(poList.getTablaOAlias(), poList.getFields().malTipos(), poList.getFields().msNombres());
  }
  public void inicializar(String psTabla,JFieldDefs poCampos){
      inicializar(psTabla, poCampos.malTipos(), poCampos.msNombres());
  }
    public void setFiltroElem(final int plCompara
            , final String psTabla, final int plCampo, final String psCampo
            , final int plCampo2, final String psCampo2,
            final int plTipo) {
        setFiltroElem(plCompara, psTabla, plCampo, psCampo, psTabla, plCampo2, psCampo2, plTipo);
    }
       
    /**
     * Constructor para condiciones de JListDatos/SQL
     * @param plCompara tipo de comparacion
     */
    public void setFiltroElem(final int plCompara
            , final String psTabla, final int plCampo, final String psCampo
            , final String psTabla2, final int plCampo2, final String psCampo2,
            final int plTipo) {
        mlCompara = plCompara;
        msTabla = psTabla;
        msTabla2 = psTabla2;
        mlCampo = plCampo;
        mlCampo2 = plCampo2;
        msCampo = psCampo;
        msCampo2 = psCampo2;
        mlTipo = plTipo;
        moOrden = null;
    }

    private void crearFilaCompararYOrden() {
        if (moOrden == null) {
            //creamos el objeto que nos comparara filas
            moOrden = new JOrdenacion(new int[]{0}, new int[]{mlTipo}, true, mbIgnoreCASE);
        }
    }

    public boolean mbCumpleFiltro(final IFilaDatos poFila) {
        boolean lbEncontrado = false;
        crearFilaCompararYOrden();
        JFilaDatosDefecto loFila1;
        JFilaDatosDefecto loFila2;

        if (mbIgnoreCASE) {
            loFila1 = new JFilaDatosDefecto(new String[]{poFila.msCampo(mlCampo).toLowerCase()});
            loFila2 = new JFilaDatosDefecto(new String[]{poFila.msCampo(mlCampo2).toLowerCase()});
        } else {
            loFila1 = new JFilaDatosDefecto(new String[]{poFila.msCampo(mlCampo)});
            loFila2 = new JFilaDatosDefecto(new String[]{poFila.msCampo(mlCampo2)});
        }

        //
        //realizamos el filtrado
        //
        int lComparacion = 0;
        if (mlCompara != JListDatos.mclTLike) {
            lComparacion = moOrden.compare(loFila1, loFila2);
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
                        (lComparacion == JOrdenacion.mclIgual)
                        || (lComparacion == JOrdenacion.mclMayor);
                break;
            case JListDatos.mclTMenorIgual:
                lbEncontrado =
                        (lComparacion == JOrdenacion.mclIgual)
                        || (lComparacion == JOrdenacion.mclMenor);
                break;
            case JListDatos.mclTDistinto:
                lbEncontrado = (lComparacion != JOrdenacion.mclIgual);
                break;
            case JListDatos.mclTLike:
                lbEncontrado = (loFila1.msCampo(0).indexOf(loFila2.msCampo(0)) >= 0);
                break;
            default:

        }
        return lbEncontrado;
    }

    public String msSQL(final ISelectMotor poSelect) {
        String lsSQL = poSelect.msCondicion(
                poSelect.msCampo(JSelectCampo.mclFuncionNada, msTabla, msCampo),
                mlCompara,
                null,
                poSelect.msCampo(JSelectCampo.mclFuncionNada, msTabla2, msCampo2),
                mlTipo);
        return lsSQL;
    }

    public String getCampo() {
        return msCampo;
    }

    public String getCampo2() {
        return msCampo2;
    }

    public String getTabla() {
        return msTabla;
    }

    public String getTabla2() {
        return msTabla2;
    }

    public int getComparacion() {
        return mlCompara;
    }

    public JListDatosFiltroConj getFiltroConj() {
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(
                loFiltro.mclAND,
                new JListDatosFiltroElemCampos(
                mlCompara,
                msTabla,
                mlCampo,
                msCampo,
                mlCampo,
                msCampo2,
                mlTipo));
        return loFiltro;
    }

    public Object clone() {
        JListDatosFiltroElemCampos loCon = null;
        try {
            loCon = (JListDatosFiltroElemCampos) super.clone();
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        return loCon;
    }

    /**
     * clonacion del objeto con un tipo dado
     * @return objeto clonado
     */
    public JListDatosFiltroElemCampos Clone() {
        return (JListDatosFiltroElemCampos) clone();
    }

    /**
     * devuelve el objeto en string
     * @return cadena
     */
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
}
