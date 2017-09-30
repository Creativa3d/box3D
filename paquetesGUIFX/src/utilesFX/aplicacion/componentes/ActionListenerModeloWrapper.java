/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.aplicacion.componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;

/**
 *
 * @author eduardo
 */
public class ActionListenerModeloWrapper implements EventHandler<javafx.event.ActionEvent> {
    private final ActionListenerCZ moModelo;
    
    public ActionListenerModeloWrapper(ActionListenerCZ poModelo){
        moModelo=poModelo;
    }

    @Override
    public void handle(ActionEvent e) {
        String lsActionComand = e.getTarget().toString();
        if(e.getSource() instanceof Node){
            lsActionComand=((Node)e.getSource()).getId();
        }
        if(e.getSource() instanceof Menu){
            lsActionComand=((Menu)e.getSource()).getId();
        }
        if(e.getSource() instanceof MenuItem){
            lsActionComand=((MenuItem)e.getSource()).getId();
        }
        moModelo.actionPerformed(new ActionEventCZ(e.getSource(), 0, e.getTarget().toString()));
    }
}
