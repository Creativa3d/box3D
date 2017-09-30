/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.util;

import ListDatos.ECampoError;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import utilesGUI.tabla.IComponentParaTabla;
import utilesGUIx.JCheckBoxCZ;
import utilesGUIx.JTextFieldCZ;

public class NodeEditorFieldDef extends AbstractCellEditor implements TreeCellEditor {

    NodeRendererFieldDef renderer = new NodeRendererFieldDef();
//    ChangeEvent changeEvent = null;
    JTree tree;
    JFieldDef moCampoEditable;

    public NodeEditorFieldDef(JTree tree) {
        this.tree = tree;
    }

    public Object getCellEditorValue() {
        IComponentParaTabla checkbox = renderer.getLeafRenderer(moCampoEditable);
        try {
            moCampoEditable.setValue(checkbox.getValueTabla());
        } catch (ECampoError ex) {
            ex.printStackTrace();
        }
        return moCampoEditable;
    }

    public boolean isCellEditable(EventObject event) {
        boolean returnValue = false;
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;
            TreePath path = tree.getPathForLocation(mouseEvent.getX(),
                    mouseEvent.getY());
            if (path != null) {
                Object node = path.getLastPathComponent();
                if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
                    Object userObject = treeNode.getUserObject();
                    returnValue = (userObject instanceof JFieldDef);
                }
            }
        }
        return returnValue;
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row) {

        Component editor = renderer.getTreeCellRendererComponent(tree, value,
                true, expanded, leaf, row, true);

        // editor always selected / focused
        ItemListener itemListener = new ItemListener() {

            public void itemStateChanged(ItemEvent itemEvent) {
                if (stopCellEditing()) {
                    fireEditingStopped();
                }
            }
        };
        if (editor instanceof JCheckBoxCZ) {
            ((JCheckBoxCZ) editor).addItemListener(itemListener);
        }
        //OJO: problema de que debes dar intro antes de cambiar de campo, si no no guarda los cambios
        //no funciona ni textListener ni FocusLiostener, puede ser algo de getValueObject
        //q hay q usar getText o algo asi
//        if (editor instanceof JTextFieldCZ) {
//            ((JTextFieldCZ) editor).addFocusListener(new FocusListener() {
//                    public void textValueChanged(TextEvent e) {
//                        getCellEditorValue();
//                    }
//
//                public void focusGained(FocusEvent e) {
//                        getCellEditorValue();
//                }
//
//                public void focusLost(FocusEvent e) {
//                        getCellEditorValue();
//                }
//                });
//        }

        moCampoEditable=null;
        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
            Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
            if (userObject instanceof JFieldDef) {
                moCampoEditable = (JFieldDef) userObject;
            }
        }

        return editor;
    }
}


