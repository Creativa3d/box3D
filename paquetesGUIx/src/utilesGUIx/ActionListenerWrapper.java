/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

/**
 *
 * @author eduardo
 */
public class ActionListenerWrapper implements ActionListenerCZ {
    private final java.awt.event.ActionListener moAction;
    
    public ActionListenerWrapper(java.awt.event.ActionListener po){
        moAction=po;
    }
    
    public void actionPerformed(ActionEventCZ e) {
        moAction.actionPerformed(new java.awt.event.ActionEvent(e.getSource(), 0, e.getActionCommand()));
    }
    
}
