/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;

/**
 *
 * @author eduardo
 */
public class EventHandlerWrapper implements ActionListenerCZ {
    private final EventHandler<ActionEvent> moAction;
    
    public EventHandlerWrapper(EventHandler<ActionEvent> po){
        moAction=po;
    }
    
    @Override
    public void actionPerformed(ActionEventCZ e) {
        moAction.handle(new ActionEvent(e.getSource(), null));
    }
    
}
