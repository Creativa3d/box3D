/*
 * JRelacionesDefs.java
 *
 * Created on 15 de abril de 2005, 12:06
 */

package ListDatos.estructuraBD;

import utiles.*;

public class JRelacionesDefs  implements java.io.Serializable , Cloneable {
    private static final long serialVersionUID = 33333338L;
    //Importacion: es cuando la tabla actual toma las claves de otras tablas
    private IListaElementos moRelaciones;
    private String msTabla;
    /** Constructor */
    public JRelacionesDefs(final String psTabla){
        super();
        moRelaciones = new JListaElementos();
        msTabla = psTabla;
    }

    public String getTabla(){
        return msTabla;
    }
    
    //** dado un campo devuelve su relacion */
    public JRelacionesDef getRelacionDeCampo(final String psCampo) {
        JRelacionesDef loRelacionDeCampo = null;
        String lsCampo=psCampo.toUpperCase();
        for(int i=0;i<count();i++) { //recorro todas las relaciones
            JRelacionesDef loRelacion = getRelacion(i);
            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) {
                for(int j=0;j<loRelacion.getCamposRelacionCount();j++){
                    if(loRelacion.getCampoPropio(j).toUpperCase().compareTo(lsCampo)==0){
                        loRelacionDeCampo = loRelacion;
                    }
                }
            }
        }
        return loRelacionDeCampo;
    }
    
    //** anadir una nueva clave Import */
    public void addRelacion(final JRelacionesDef poItem) {
        moRelaciones.add(poItem);
        poItem.setTabla(msTabla);
    }

    //** recuperar una clave Import por codigo */
    public JRelacionesDef getRelacion(final int plcod) {
        return (JRelacionesDef) moRelaciones.get(plcod);
    }
    
    //** recuperar una clave Import por nombre */
    public JRelacionesDef getRelacion(final String psNombre) {
        
        int i;
        JRelacionesDef loRelacion = null;

        String lsNombre = psNombre.toUpperCase();
        for (i = 0; (i < moRelaciones.size()) && (loRelacion == null); i++) {
            if (((JRelacionesDef) moRelaciones.get(i)).getNombreRelacion().equalsIgnoreCase(lsNombre)) {
                loRelacion = ((JRelacionesDef) moRelaciones.get(i));
            }
        }

        return loRelacion;
    }
    
    //** devuelve la lista de relaciones */
    public IListaElementos getListaRelaciones(){
        return moRelaciones;
    }
    
    //** devuelve el numero de relaciones */
    public int count() {
        return moRelaciones.size();
    }

    public Object clone() throws CloneNotSupportedException {
        JRelacionesDefs loRelacs = (JRelacionesDefs) super.clone();
        loRelacs.moRelaciones = new JListaElementos();
        for(int i = 0 ; i < moRelaciones.size(); i++){
            loRelacs.moRelaciones.add(getRelacion(i).clone());
        }
        return loRelacs;
    }
    
}
