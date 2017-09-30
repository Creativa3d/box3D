/*
 * ActionListenerWrapper.java
 *
 * Created on 16 de septiembre de 2004, 14:04
 */

package utilesGUIx.panelesGenericos;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;


/**Envoltorio para hacer la accion */
public class ActionListenerWrapper implements ActionListenerCZ{
    private final ActionListenerAnadir moAnadir;
    private final ActionListenerBuscar moBuscar;
    
    /**
     * Creates a new instance of ActionListenerWrapper
     * @param poAnadir anadir
     * @param poBuscar buscar
     */
    public ActionListenerWrapper(ActionListenerAnadir poAnadir, ActionListenerBuscar poBuscar) {
        moAnadir = poAnadir;
        moBuscar = poBuscar;
    }
    /**Ejecuta la accion*/
    public void actionPerformed(ActionEventCZ e) {
        if(moAnadir !=null){
            moAnadir.actionPerformedAnadir(e);
        }
        if(moBuscar !=null){
            moBuscar.actionPerformedBuscar(e);
        }
    }
    
}
