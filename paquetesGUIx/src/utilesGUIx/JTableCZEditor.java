/*
 * JTableCSEditor.java
 *
 * Created on 28 de octubre de 2008, 10:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.JFilaDatosDefecto;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class JTableCZEditor extends DefaultCellEditor  {
    private static final long serialVersionUID = 1L;
    private JTable moTable;
    
    /** Creates a new instance of JTableCSEditor */
    public JTableCZEditor(final JTextFieldCZ poCampo) {
        super(poCampo);
        poCampo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER || (e.getKeyCode()==KeyEvent.VK_TAB )){
                    if(moTable!=null && moTable.isEditing()){
                        stopCellEditing();
                        moTable.editingStopped(null);
                        moTable.requestFocus();
                        moTable.dispatchEvent(new KeyEvent(moTable, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_ENTER));
                    }
                }
                super.keyPressed(e);
            }
            
        });
        poCampo.removeActionListener(delegate);
        delegate = new DefaultCellEditor.EditorDelegate() {
            @Override
            public void setValue(Object value) {
		poCampo.setValueTabla(value);
                if(value!=null){
                    poCampo.setSelectionStart(0);
                    poCampo.setSelectionEnd(value.toString().length()+1000);
                }
            }

            @Override
	    public Object getCellEditorValue() {
                return poCampo.getCellEditorValue();
	    }
        };
	poCampo.addActionListener(delegate);
    }
    public JTableCZEditor(final JComboBoxCZ comboBox) {
        super(comboBox);
//        comboBox.addKeyListener(this);
        editorComponent = comboBox;
        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        delegate = new DefaultCellEditor.EditorDelegate() {
            EventObject moEvent;
            public void setValue(Object value) {
                comboBox.setValueTabla(value+JFilaDatosDefecto.mcsSeparacion1);
                if(moEvent!=null && moEvent instanceof KeyEvent){
                    KeyEvent loEventAntig = (KeyEvent) moEvent;
                    KeyEvent loEvent = new KeyEvent(comboBox, loEventAntig.getID(), loEventAntig.getWhen(), loEventAntig.getModifiers(), loEventAntig.getKeyCode(), loEventAntig.getKeyChar(), loEventAntig.getKeyLocation());
                    comboBox.processKeyEvent(loEvent);
                }
                moEvent=null;
            }
            public boolean isCellEditable(EventObject anEvent) {
                moEvent=anEvent;
                return true;
            }
            public Object getCellEditorValue() {
                return comboBox.getFilaActual().msCampo(0);
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    MouseEvent e = (MouseEvent)anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }
            public boolean stopCellEditing() {
                if(moTable!=null && moTable.isEditing()){
                    moTable.editingStopped(null);
                }
                return true;
                
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
//                //este es el unico sitio que funciona, ni en keypress, ni en keyrelease, no setCellValue ni getCellValue ni stopcellediting
//                if(moTable!=null && moTable.isEditing()){
//                    moTable.editingStopped(null);
//                }
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
//                super.itemStateChanged(e);
            }
            
        };
        comboBox.addActionListener(delegate);
        comboBox.addItemListener(delegate);
        comboBox.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == e.VK_TAB
                        || e.getKeyCode() == e.VK_RIGHT
                        || e.getKeyCode() == e.VK_LEFT){
                    if(moTable!=null && moTable.isEditing()){
                        moTable.editingStopped(null);
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
//non funciona        
//        comboBox.addFocusListener(new FocusAdapter() {
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                super.focusLost(e);
//                delegate.stopCellEditing();
//            }
//            
//        });
    }    
    public JTableCZEditor(final JCheckBox checkBox) {
        super(checkBox);
    }
    
    public JComponent getComponente(){
        return editorComponent;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        moTable=table;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                editorComponent.requestFocus();
            }
        });
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
//    //ultimo y primer sitio q se llama despues de empezar la edicion
//    public void addCellEditorListener(CellEditorListener l) {
//        super.addCellEditorListener(l);
//        if(moTable!=null){
//            row=moTable.getEditingRow();
//            col=moTable.getEditingColumn();
//        }
//    }    
}
