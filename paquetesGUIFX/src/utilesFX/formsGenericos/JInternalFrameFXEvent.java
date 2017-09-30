/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author eduardo
 */
public class JInternalFrameFXEvent extends Event {
    public static final EventType<JInternalFrameFXEvent> WINDOW_CLOSE_REQUEST = new EventType<JInternalFrameFXEvent>(ANY);

    public JInternalFrameFXEvent(Object source, EventType<? extends Event> et) {
        super(source, null, et);
    }
    
}
