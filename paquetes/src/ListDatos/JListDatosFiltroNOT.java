package ListDatos;

import ListDatos.estructuraBD.JFieldDefs;

/**
 * Objeto que contiene una condicion para filtrar un JListDatos
 */
public final class JListDatosFiltroNOT implements IListDatosFiltro {

    private static final long serialVersionUID = 3333339L;
    private IListDatosFiltro moFiltro;

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param poFiltro
     */
    public JListDatosFiltroNOT(final IListDatosFiltro poFiltro) {
        moFiltro = poFiltro;
    }

    @Override
    public void inicializar(final String psTabla, final int[] palTodosTipos,
            final String[] pasTodosCampos) {
        moFiltro.inicializar(psTabla, palTodosTipos, pasTodosCampos);
    }

    public void inicializar(JListDatos poList) {
        inicializar(poList.getTablaOAlias(), poList.getFields().malTipos(), poList.getFields().msNombres());
    }

    public void inicializar(String psTabla, JFieldDefs poCampos) {
        inicializar(psTabla, poCampos.malTipos(), poCampos.msNombres());
    }

    @Override
    public boolean mbCumpleFiltro(final IFilaDatos poFila) {
        return !moFiltro.mbCumpleFiltro(poFila);
    }

    @Override
    public String msSQL(final ISelectMotor poSelect) {
        return poSelect.msNot(moFiltro.msSQL(poSelect));
    }

    @Override
    public Object clone() {

        JListDatosFiltroNOT loCon = null;
        try {
            loCon = (JListDatosFiltroNOT) super.clone();
            loCon.moFiltro = (IListDatosFiltro) moFiltro.clone();
        } catch (Exception ex) {
        }
        return loCon;
    }

    /**
     * clonacion del objeto con un tipo dado
     *
     * @return objeto clonado
     */
    public JListDatosFiltroNOT Clone() {
        return (JListDatosFiltroNOT) clone();
    }

    /**
     * devuelve el objeto en string
     *
     * @return cadena
     */
    @Override
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
}
