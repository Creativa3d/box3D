/*
 * JTablaConfigTabla.java
 *
 * Created on 12 de julio de 2007, 9:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;

public class JTablaConfigTablas {
    
    private IListaElementos moTablas = new JListaElementos();
    
    /** Creates a new instance of JTablaConfigTabla */
    public JTablaConfigTablas() {
    }
    
    
    public int size(){
        return moTablas.size();
    }
    public JTablaConfigTabla getTabla(final int i){
        JTablaConfigTabla loTabla = null;
        if(i>=0){
            loTabla = (JTablaConfigTabla)moTablas.get(i);
        }
        return loTabla;
    }
    private int getIndiceTabla(final String psNombre){
        int lIndex=-1;
        if(psNombre==null){
            JDepuracion.anadirTexto(getClass().getName(), "Nombre nulo en recuperar Config tabla");
        }else{
            for(int i = 0 ; i < moTablas.size() && lIndex == -1;i++){
                if(getTabla(i).getNombre() != null && 
                   getTabla(i).getNombre().equalsIgnoreCase(psNombre)){
                    lIndex = i;
                }
            }
        }
        return lIndex;
    }
    public JTablaConfigTabla getTabla(final String psNombre){
        int lIndex = getIndiceTabla(psNombre);
        if(lIndex>=0){
            return getTabla(lIndex);
        }else{
            JTablaConfigTabla loTablaConfig = new JTablaConfigTabla();
            loTablaConfig.setNombre(psNombre);
            addTabla(loTablaConfig);
            return loTablaConfig;
        }
    }
    public void addTabla(final JTablaConfigTabla poColumn){
        moTablas.add(poColumn);
    }
    public void removeTabla(final int i){
        moTablas.remove(i);
    }
    public void removeTabla(final String psNombre){
        moTablas.remove(getIndiceTabla(psNombre));
    }
    
}
