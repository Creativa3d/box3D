/*
 * JButtonCZ.java
 *
 * Created on 16 de enero de 2006, 12:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

import java.awt.AWTEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.KeyStroke;
        
public class JButtonCZ extends JButton {
    private boolean mbActivoKey=true;
    /** Creates a new instance of JButtonCZ */
    public JButtonCZ() {
        super();
        enableEvents(AWTEvent.KEY_EVENT_MASK);        
        KeyStroke[] laKeys = getInputMap().allKeys();
        for(int i = 0 ; laKeys!=null && i < laKeys.length; i++){
            if(laKeys[i].getKeyCode()==10){
                mbActivoKey = false;
            }
        }        
    }
    /** Creates a new instance of JButtonCZ */
    public JButtonCZ(final String psCaption) {
        super(psCaption);
    }
    
    public void setFocusable(final boolean focusable){
        try{
            super.setFocusable(focusable);
        }catch(Throwable e){
            
        }
    }
    
    public boolean isFocusable(){
        try{
            return super.isFocusable();
        }catch(Throwable e){
        }
        return true;
    }
    
    public ActionListener[] getActionListeners() {
        return (ActionListener[])(
                JGUIxUtil.getListeners(listenerList, ActionListener.class));
    }
/**
     *Procesamos el evento del teclado
     */
   protected void processKeyEvent(KeyEvent e){
        int id = e.getID();
        switch(id) {
          case KeyEvent.KEY_PRESSED:
            if(e.getKeyCode()==KeyEvent.VK_ENTER && mbActivoKey){
                doClick(0);
            }
            break;
          default:
        }
        super.processKeyEvent(e);
   }        
    
}
