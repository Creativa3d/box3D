package ListDatos;

import ListDatos.estructuraBD.JFieldDefs;
/**
 *Objeto que contiene una condicion para filtrar un JListDatos 
 */
public final class JListDatosFiltroBETWEEN implements IListDatosFiltro {

    private static final long serialVersionUID = 3333339L;
    private JListDatosFiltroConj moFiltro;
    
    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCampo campo
     * @param psValor1 valor
     * @param psValor2 valor
     */
    public JListDatosFiltroBETWEEN(final int plCampo
            , final String psValor1, final String psValor2) {
        moFiltro = new JListDatosFiltroConj();
        moFiltro.addCondicionAND(JListDatos.mclTMayorIgual, plCampo, psValor1);
        moFiltro.addCondicionAND(JListDatos.mclTMenorIgual, plCampo, psValor2);
        
    }

    public void inicializar(final String psTabla, final int[] palTodosTipos,
            final String[] pasTodosCampos) {
        moFiltro.inicializar(psTabla, palTodosTipos, pasTodosCampos);
    }
  public void inicializar(JListDatos poList){
      inicializar(poList.getTablaOAlias(), poList.getFields().malTipos(), poList.getFields().msNombres());
  }
  public void inicializar(String psTabla,JFieldDefs poCampos){
      inicializar(psTabla, poCampos.malTipos(), poCampos.msNombres());
  }

  public boolean mbCumpleFiltro(final IFilaDatos poFila) {
      return getFiltroConj().mbCumpleFiltro(poFila);
    }

    public String msSQL(final ISelectMotor poSelect) {
        return getFiltroConj().msSQL(poSelect);
    }
    
    public JListDatosFiltroConj getFiltroConj(){
        return moFiltro;
    }

    @Override
    public Object clone() {
        
        JListDatosFiltroBETWEEN loCon=null;
        try {
            loCon = (JListDatosFiltroBETWEEN) super.clone();
            loCon.moFiltro=(JListDatosFiltroConj)moFiltro.Clone();
        } catch (Exception ex) {
        }
        return loCon;
    }

    /**
     * clonacion del objeto con un tipo dado
     * @return objeto clonado
     */
    public JListDatosFiltroBETWEEN Clone() {
        return (JListDatosFiltroBETWEEN) clone();
    }

    /**
     * devuelve el objeto en string
     * @return cadena
     */
    @Override
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
}
