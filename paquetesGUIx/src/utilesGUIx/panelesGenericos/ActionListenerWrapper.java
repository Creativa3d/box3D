/*
 * ActionListenerWrapper.java
 *
 * Created on 16 de septiembre de 2004, 14:04
 */

package utilesGUIx.panelesGenericos;

import java.awt.event.ActionListener;

/**Envoltorio para hacer la acci�n */
public class ActionListenerWrapper implements ActionListener{
    private final ActionListenerAnadir moAnadir;
    private final ActionListenerBuscar moBuscar;
    
    /**
     * Creates a new instance of ActionListenerWrapper
     * @param poAnadir a�adir
     * @param poBuscar buscar
     */
    public ActionListenerWrapper(ActionListenerAnadir poAnadir, ActionListenerBuscar poBuscar) {
        moAnadir = poAnadir;
        moBuscar = poBuscar;
    }
    /**Ejecuta la acci�n*/
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(moAnadir !=null){
            moAnadir.actionPerformedAnadir(e);
        }
        if(moBuscar !=null){
            moBuscar.actionPerformedBuscar(e);
        }
    }
    
}
