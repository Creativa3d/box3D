/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;

/**
 *
 * @author eduardo
 */
public class ActionListenerModeloWrapper implements ActionListener {
    private final ActionListenerCZ moModelo;
    
    public ActionListenerModeloWrapper(ActionListenerCZ poModelo){
        moModelo=poModelo;
    }

    public void actionPerformed(ActionEvent e) {
        moModelo.actionPerformed(new ActionEventCZ(e.getSource(), e.getID(), e.getActionCommand()));
    }
}
