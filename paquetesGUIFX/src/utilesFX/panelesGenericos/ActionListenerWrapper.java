/*
 * ActionListenerWrapper.java
 *
 * Created on 16 de septiembre de 2004, 14:04
 */

package utilesFX.panelesGenericos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;

/**Envoltorio para hacer la acción */
public class ActionListenerWrapper implements EventHandler<ActionEvent> {
    private final ActionListenerAnadir moAnadir;
    private final ActionListenerBuscar moBuscar;
    
    /**
     * Creates a new instance of ActionListenerWrapper
     * @param poAnadir añadir
     * @param poBuscar buscar
     */
    public ActionListenerWrapper(ActionListenerAnadir poAnadir, ActionListenerBuscar poBuscar) {
        moAnadir = poAnadir;
        moBuscar = poBuscar;
    }
    
    @Override
    public void handle(ActionEvent e) {
        if(moAnadir !=null){
            moAnadir.actionPerformedAnadir(e);
        }
        if(moBuscar !=null){
            moBuscar.actionPerformedBuscar(e);
        }
    }
    
}
