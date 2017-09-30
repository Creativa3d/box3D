/*
 * JTableDefs.java
 *
 * Created on 15 de abril de 2005, 12:06
 */

package ListDatos.estructuraBD;

import utiles.*;

public class JTableDefs  implements java.io.Serializable, Cloneable {
    private static final long serialVersionUID = 33333340L;

    private IListaElementos moTablas;

    /** Constructor */
    public JTableDefs(){
        moTablas = new JListaElementos();
    }

    /** A�adir nueva tabla */
    public void add(final JTableDef poItem) {
        moTablas.add(poItem);
    }

    /** Recuperar una tabla por indice */
    public JTableDef get(final int plcod) {
        return (JTableDef) moTablas.get(plcod);
    }
    
    /** Borra una tabla por el nombre */
    public void deleteTable(final String psNombre) {
        int lnCont = 0;
        String lsNombre = psNombre.toUpperCase();
        JTableDef loTabla = null;
        while (lnCont < moTablas.size()) {
            loTabla = (JTableDef) moTablas.get(lnCont);
            if(loTabla.getNombre().toUpperCase().compareTo(lsNombre)==0) {
                moTablas.remove(lnCont);
            }
            lnCont++;
        }
    }
    
    /** Recuperar una tabla por nombre */
    public JTableDef get(final String psNombre) {
        int lnCont = 0;
        String lsNombre = psNombre.toUpperCase();
        JTableDef loTablaAux = null;
        JTableDef loTabla = null;
        while ((loTablaAux == null) && (lnCont < moTablas.size())) {
            loTabla = (JTableDef) moTablas.get(lnCont);
            if(loTabla.getNombre().toUpperCase().compareTo(lsNombre)==0) {
                loTablaAux = loTabla;
            }
            lnCont++;
        }
        return loTablaAux;
    }

    
    /** Devolver la lista de tablas */
    public IListaElementos getListaTablas() {
        return moTablas;
    }

    public int size() {
        return moTablas.size();
    }

    public Object clone() throws CloneNotSupportedException {
        JTableDefs loTablas = (JTableDefs) super.clone();
        loTablas.moTablas=new JListaElementos();
        for(int i = 0 ; i < moTablas.size(); i++){
            loTablas.moTablas.add((JTableDef)get(i).clone());
        }
        return loTablas;
    }
    
}
