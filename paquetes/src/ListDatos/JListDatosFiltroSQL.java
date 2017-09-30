/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;


public class JListDatosFiltroSQL implements IListDatosFiltro {
    private static final long serialVersionUID = 1L;
    private String msSQL;
    
    public JListDatosFiltroSQL (String psSQL){
        msSQL = psSQL;
    }

    public void setDatos(String psSQL){
        msSQL = psSQL;
    }

    public void inicializar(String psTabla, int[] palTodosTipos, String[] pasTodosCampos) {
    }

    public boolean mbCumpleFiltro(IFilaDatos poFila) {
        return true;
    }

    public String msSQL(ISelectMotor poSelect) {
        return (msSQL==null || msSQL.equals("") ? null : msSQL );
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    
}
