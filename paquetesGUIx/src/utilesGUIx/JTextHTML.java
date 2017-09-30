/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import ListDatos.ECampoError;
import ListDatos.estructuraBD.JFieldDef;
import com.hexidec.ekit.EkitCore;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.edicion.ITextBD;

/**
 *
 * @author eduardo
 */
public class JTextHTML extends JPanel  implements utilesGUI.tabla.IComponentParaTabla, ITextBD {
    private static final long serialVersionUID = 1L;
        
    private final EkitCore ekitCore;
    private javax.swing.JPopupMenu jPopupMenu1 = new javax.swing.JPopupMenu();    
    private final JButton jButtonMenu;
    private JFieldDef moCampo;
    private String command="html";
    
    
    
    public JTextHTML(){
        super();
        
        
        //editor HTML
        boolean multiBar = true;
        String sDocument = null;
        String sStyleSheet = null;
        String sRawDocument = null;
        URL urlStyleSheet = null;
        boolean includeToolBar = true;
        boolean showViewSource = false;
        boolean showMenuIcons = true;
        boolean editModeExclusive = false;
        String sLanguage = "es";
        String sCountry = "es";
        boolean base64 = true;
        boolean debugMode = true;

        ekitCore = new EkitCore(
                false, sDocument, sStyleSheet, sRawDocument, null, urlStyleSheet, includeToolBar, showViewSource, showMenuIcons, editModeExclusive, sLanguage, sCountry, base64, debugMode, true, multiBar, (multiBar ? EkitCore.TOOLBAR_DEFAULT_MULTI : EkitCore.TOOLBAR_DEFAULT_SINGLE), false);
        ekitCore.setEnterKeyIsBreak(true);
        
        if (includeToolBar) {
            if (multiBar) {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 0.0;
                gbc.gridx = 1;

//                  gbc.gridy      = 1;
//                  jPanelTexto.add(ekitCore.getToolBarMain(includeToolBar), gbc);

                gbc.gridy = 2;
                add(ekitCore.getToolBarFormat(includeToolBar), gbc);

//                gbc.gridy = 3;
//                jPanelTexto.add(ekitCore.getToolBarStyles(includeToolBar), gbc);

                gbc.anchor = GridBagConstraints.SOUTH;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1.0;
                gbc.gridy = 4;
                add(ekitCore, gbc);
            } else {
                setLayout(new BorderLayout());
                add(ekitCore, BorderLayout.CENTER);
                add(ekitCore.getToolBar(includeToolBar), BorderLayout.NORTH);
            }
        } else {
            setLayout(new BorderLayout());
            add(ekitCore, BorderLayout.CENTER);
        }
        Vector loVect = new Vector();
        loVect.add(ekitCore.KEY_MENU_FONT);
        loVect.add(ekitCore.KEY_MENU_EDIT);
        loVect.add(ekitCore.KEY_MENU_FORMAT);
        loVect.add(ekitCore.KEY_MENU_INSERT);
        loVect.add(ekitCore.KEY_MENU_SEARCH);
        loVect.add(ekitCore.KEY_MENU_TABLE);
        JMenuBar jMenuBar1 = ekitCore.getCustomMenuBar(loVect);
//        jPopupMenu1 = new JPopupMenu("Menu");
//        JMenu lom = new JMenu("csdf");
//        lom.add(new JMenuItem("per"));

//        jPopupMenu1.add(lom);
//        jPopupMenu1.add(new JMenuItem("sdasdf"));
        for (int i = 0; i < jMenuBar1.getMenuCount(); i++) {
            JMenu loMenuAux = jMenuBar1.getMenu(i);
            if(loMenuAux!=null){
                JMenu loNuevo = new JMenu(loMenuAux.getText());
                addMenus(loMenuAux, loNuevo);
                jPopupMenu1.add(loNuevo);
            }
        }
        JToolBar loToolbar = ekitCore.getToolBarFormat(includeToolBar);
        jButtonMenu = new javax.swing.JButton();
        jButtonMenu.setText("Menú");
        jButtonMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMenuActionPerformed(evt);
            }
        });        
        loToolbar.add(jButtonMenu);        
        
    }

    private void jButtonMenuActionPerformed(java.awt.event.ActionEvent evt) {                                            
        mostrarMenu();
    }                                           

    private void mostrarMenu(){
        if (jPopupMenu1.isVisible()) {
            jPopupMenu1.setVisible(false);
        } else {
            jPopupMenu1.show(this,
                    jButtonMenu.getLocation().x, jButtonMenu.getLocation().y - 1 + jButtonMenu.getHeight()+getY());
        }
    }
    /**
     * completa los menus de jMenuDestino con los menus de jMenuOrigen
     */
    public static void addMenus(JMenu jMenuOrigen, JMenu jMenuDestino) {
        for (int i = jMenuOrigen.getMenuComponentCount() - 1; i >= 0; i--) {
            Component loAux = jMenuOrigen.getMenuComponent(i);
            if (loAux.getClass() == JMenu.class) {
                JMenu loMenu = new JMenu(((JMenu) loAux).getText());
                jMenuDestino.add(loMenu);
                addMenus(((JMenu) loAux), loMenu);
            } else {
                jMenuDestino.add(loAux);
            }
            jMenuOrigen.remove(loAux);
        }
    }    
    
    public void setText(String ps){
        ekitCore.setDocumentText(ps);
    }
    public String getText(){
        return ekitCore.getDocumentText();
    }
    
    public EkitCore getEkitCore(){
        return ekitCore;
    }

    public JFieldDef getField(){
        return moCampo;
    }
    /**Establecemos el campo de la BD*/
    public void setField(final JFieldDef poCampo){
        moCampo = poCampo;
    }
    /**Devolvemos el campo de la BD*/
    public JFieldDef getCampo(){
        return moCampo;
    }
    
    /**Mostramos los datos del campo de BD guardado*/
    public void mostrarDatosBD(){
        if(moCampo!=null){
            try {
                setValueTabla(moCampo.getString());
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
    }
    /**Establecemos los datos de campo de BD guardado*/
    public void establecerDatosBD() throws ECampoError{
        if(moCampo!=null){
            moCampo.setValue(getText());
        }
    }

    public void setValueTabla(Object poValor) throws Exception {
        if(poValor==null){
            setText("");
        }else{
            setText(poValor.toString());
        }
    }

    public Object getValueTabla() {
        return getText();
    }

    public boolean getTextoCambiado() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); 
        ekitCore.setEnabled(enabled);
        ekitCore.getSourcePane().setEnabled(enabled);
    }
    public synchronized ActionListener[] getActionListeners() {
        return listenerList.getListeners(ActionListener.class);
    }
    /**
     * Adds the specified action listener to receive
     * action events from this textfield.
     *
     * @param l the action listener to be added
     */
    public synchronized void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    /**
     * Removes the specified action listener so that it no longer
     * receives action events from this textfield.
     *
     * @param l the action listener to be removed
     */
    public synchronized void removeActionListener(ActionListener l) {
            listenerList.remove(ActionListener.class, l);
    }    
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created.
     * The listener list is processed in last to
     * first order.
     * @see EventListenerList
     */
    protected void fireActionPerformed() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        int modifiers = 0;
        AWTEvent currentEvent = EventQueue.getCurrentEvent();
        if (currentEvent instanceof InputEvent) {
            modifiers = ((InputEvent)currentEvent).getModifiers();
        } else if (currentEvent instanceof ActionEvent) {
            modifiers = ((ActionEvent)currentEvent).getModifiers();
        }
        ActionEvent e =
            new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                            (command != null) ? command : getText(),
                            EventQueue.getMostRecentEventTime(), modifiers);

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    /**
     * Sets the command string used for action events.
     *
     * @param command the command string
     */
    public void setActionCommand(String command) {
        this.command = command;
    }
               /**
     * Processes action events occurring on this textfield by
     * dispatching them to any registered <code>ActionListener</code> objects.
     * This is normally called by the controller registered with
     * textfield.
     */
    public void postActionEvent() {
        fireActionPerformed();
    } 
}
