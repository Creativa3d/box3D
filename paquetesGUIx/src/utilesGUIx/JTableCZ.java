/*
 * JTableCZ.java
 *
 * Created on 9 de junio de 2005, 14:24
 */
package utilesGUIx;

import utiles.tipos.Moneda3Decimales;
import utiles.tipos.Moneda;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import utiles.*;
import utiles.tipos.Porcentual;
import utiles.tipos.Porcentual3Decimales;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

public class JTableCZ extends JTable implements FocusListener, KeyListener {

    private static final long serialVersionUID = 1L;
    public static final String mcsENTER = "ENTER";
    public static final String mcsESC = "ESC";
    private JTextFieldCZ moTextMoneda3D;
    private JTextFieldCZ moTextMoneda;
    private JTextFieldCZ moTextPorciento3D;
    private JTextFieldCZ moTextPorciento;
    private JTextFieldCZ moTextNDoble;
    private JTextFieldCZ moTextN;
    private JTextFieldCZ moTextC;
    private JTextFieldCZ moTextF;
    private JCheckBox moChe;
    private final JTableRenderConColor moRenderObject;
    private final JTableRenderConColor moRenderNumber;
    private final JTableRenderConColor moRenderDouble;
    private final JTableRenderConColor moRenderMoneda3D;
    private final JTableRenderConColor moRenderMoneda;
    private final JTableRenderConColor moRenderPorciento3D;
    private final JTableRenderConColor moRenderPorciento;
    private final JTableRenderConColor moRenderDate;
    private final JTableRendererBooleanColor moRenderBoolean;
    private Color moColorBackgroundDesac = null;
    private ITableCZColores moColores = null;
    private JTableCZEditor moTextCEditor;
    private JTableCZEditor moTextMoneda3DEditor;
    private JTableCZEditor moTextMonedaEditor;
    private JTableCZEditor moTextPorciento3DEditor;
    private JTableCZEditor moTextPorcientoEditor;
    private JTableCZEditor moTextNDobleEditor;
    private JTableCZEditor moTextNEditor;
    private JTableCZEditor moTextFEditor;
    private JTableCZEditor moCheEditor;

    /** Creates a new instance of JTableCZ */
    public JTableCZ() {
        super();
        KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        InputMap im = this.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        im.put(enter, im.get(tab));

        //creamos los objetos
        crearObjetos();

        setDefaultEditor(Float.class, moTextNDobleEditor);
        setDefaultEditor(Double.class, moTextNDobleEditor);
//        setDefaultEditor(Integer.class, moTextNDobleEditor);
        moTextNDobleEditor.addCellEditorListener(this);

        setDefaultEditor(Moneda3Decimales.class, moTextMoneda3DEditor);
        moTextMoneda3DEditor.addCellEditorListener(this);

        setDefaultEditor(Moneda.class, moTextMonedaEditor);
        moTextMonedaEditor.addCellEditorListener(this);

        setDefaultEditor(Porcentual3Decimales.class, moTextPorciento3DEditor);
        moTextPorciento3DEditor.addCellEditorListener(this);

        setDefaultEditor(Porcentual.class, moTextPorcientoEditor);
        moTextPorcientoEditor.addCellEditorListener(this);

        setDefaultEditor(Integer.class, moTextNEditor);
        moTextNEditor.addCellEditorListener(this);

        setDefaultEditor(String.class, moTextCEditor);
        setDefaultEditor(Object.class, moTextCEditor);
        moTextCEditor.addCellEditorListener(this);

        setDefaultEditor(JDateEdu.class, moTextFEditor);
        setDefaultEditor(Date.class, moTextFEditor);
        moTextFEditor.addCellEditorListener(this);

        setDefaultEditor(Boolean.class, moCheEditor);
        moCheEditor.addCellEditorListener(this);


        //establedmos los render en funcion del tipo de clase
        moRenderObject = new JTableRenderConColor(Object.class, this);
        moRenderNumber = new JTableRenderConColor(Number.class, this);
        moRenderDouble = new JTableRenderConColor(Double.class, this);
        moRenderMoneda3D = new JTableRenderConColor(Moneda3Decimales.class, this);
        moRenderMoneda = new JTableRenderConColor(Moneda.class, this);
        moRenderPorciento3D = new JTableRenderConColor(Porcentual3Decimales.class, this);
        moRenderPorciento = new JTableRenderConColor(Porcentual.class, this);
        moRenderDate = new JTableRenderConColor(Date.class, this);
        moRenderBoolean = new JTableRendererBooleanColor(this);

        setDefaultRenderer(Object.class, moRenderObject);
        setDefaultRenderer(Number.class, moRenderNumber);
        setDefaultRenderer(Float.class, moRenderDouble);
        setDefaultRenderer(Double.class, moRenderDouble);
        setDefaultRenderer(Moneda3Decimales.class, moRenderMoneda3D);
        setDefaultRenderer(Moneda.class, moRenderMoneda);
        setDefaultRenderer(Porcentual3Decimales.class, moRenderPorciento3D);
        setDefaultRenderer(Porcentual.class, moRenderPorciento);
        setDefaultRenderer(Date.class, moRenderDate);
        setDefaultRenderer(Boolean.class, moRenderBoolean);


        //añadimos el focus y key listener para "suavizar" los eventos del JTable
        addFocusListener(this);
        addKeyListener(this);

        setRowHeight(JGUIxConfigGlobal.getInstancia().getTableAltoFilas());
    }

    public JTextFieldCZ getEditorMoneda3D() {
        return moTextMoneda3D;
    }

    public JTextFieldCZ getEditorMoneda() {
        return moTextMoneda;
    }

    public JTextFieldCZ getEditorPorciento3D() {
        return moTextPorciento3D;
    }

    public JTextFieldCZ getEditorPorciento() {
        return moTextPorciento;
    }

    public JTextFieldCZ getEditorNDoble() {
        return moTextNDoble;
    }

    public JTextFieldCZ getEditorNumero() {
        return moTextN;
    }

    public JTextFieldCZ getEditorCadena() {
        return moTextC;
    }

    public JTextFieldCZ getEditorFecha() {
        return moTextF;
    }

    public JCheckBox getEditorBoolean() {
        return moChe;
    }

    public JTableRenderConColor getRenderObject() {
        return moRenderObject;
    }

    public JTableRenderConColor getRenderNumber() {
        return moRenderNumber;
    }

    public JTableRenderConColor getRenderDouble() {
        return moRenderDouble;
    }

    public JTableRenderConColor getRenderMoneda3D() {
        return moRenderMoneda3D;
    }

    public JTableRenderConColor getRenderMoneda() {
        return moRenderMoneda;
    }

    public JTableRenderConColor getRenderPorciento3D() {
        return moRenderPorciento3D;
    }

    public JTableRenderConColor getRenderPorciento() {
        return moRenderPorciento;
    }

    public JTableRenderConColor getRenderDate() {
        return moRenderDate;
    }

    public JTableRendererBooleanColor getRenderBoolean() {
        return moRenderBoolean;

    }

    public void setTableCZColores(final ITableCZColores poColores) {
        moColores = poColores;
    }

    public ITableCZColores getTableCZColores() {
        return moColores;
    }

    public Color getColorBackgroundDesac() {
        return moColorBackgroundDesac;
    }

    public void setColorBackgroundDesac(final Color poColorBackgroundDesac) {
        moColorBackgroundDesac = poColorBackgroundDesac;
    }

    private void crearObjetos() {
        if (moTextMoneda3D == null) {
            moTextMoneda3D = new JTextFieldCZ();
            moTextMoneda3D.mbAsocidoATabla = true;
            moTextMoneda3D.setTipo(JTipoTextoEstandar.mclTextMoneda3Decimales);
            moTextMoneda3DEditor = new JTableCZEditor(moTextMoneda3D);
        }
        if (moTextMoneda == null) {
            moTextMoneda = new JTextFieldCZ();
            moTextMoneda.mbAsocidoATabla = true;
            moTextMoneda.setTipo(JTipoTextoEstandar.mclTextMoneda);
            moTextMonedaEditor = new JTableCZEditor(moTextMoneda);
        }
        if (moTextPorciento == null) {
            moTextPorciento = new JTextFieldCZ();
            moTextPorciento.mbAsocidoATabla = true;
            moTextPorciento.setTipo(JTipoTextoEstandar.mclTextPorcentual);
            moTextPorcientoEditor = new JTableCZEditor(moTextPorciento);
        }
        if (moTextPorciento3D == null) {
            moTextPorciento3D = new JTextFieldCZ();
            moTextPorciento3D.mbAsocidoATabla = true;
            moTextPorciento3D.setTipo(JTipoTextoEstandar.mclTextPorcentual3Decimales);
            moTextPorciento3DEditor = new JTableCZEditor(moTextPorciento3D);
        }
        if (moTextNDoble == null) {
            moTextNDoble = new JTextFieldCZ();
            moTextNDoble.mbAsocidoATabla = true;
            moTextNDoble.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
            moTextNDobleEditor = new JTableCZEditor(moTextNDoble);
        }
        if (moTextN == null) {
            moTextN = new JTextFieldCZ();
            moTextN.mbAsocidoATabla = true;
            moTextN.setTipo(JTipoTextoEstandar.mclTextNumeroEntero);
            moTextNEditor = new JTableCZEditor(moTextN);
        }
        if (moTextC == null) {
            moTextC = new JTextFieldCZ();
            moTextC.mbAsocidoATabla = true;
            moTextC.setTipo(JTipoTextoEstandar.mclTextCadena);
            moTextCEditor = new JTableCZEditor(moTextC);
        }
        if (moTextF == null) {
            moTextF = new JTextFieldCZ();
            moTextF.mbAsocidoATabla = true;
            moTextF.setTipo(JTipoTextoEstandar.mclTextFecha);
            moTextFEditor = new JTableCZEditor(moTextF);
        }
        if (moChe == null) {
            moChe = new JCheckBox();
            moCheEditor = new JTableCZEditor(moChe);
        }

    }

    public synchronized void addActionListener(final java.awt.event.ActionListener l) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.add(java.awt.event.ActionListener.class, l);
    }

    public synchronized void removeActionListener(final ActionListenerCZ l) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.remove(ActionListenerCZ.class, l);
    }

    public void addMouseListener1(final MouseListener l) {
        super.addMouseListener(l);
//        crearObjetos();
//        moTextNDoble.addMouseListener(l);
//        moTextN.addMouseListener(l);
//        moTextC.addMouseListener(l);
//        moTextF.addMouseListener(l);
//        moChe.addMouseListener(l);
    }

    public void removeMouseListener1(final MouseListener l) {
        super.removeMouseListener(l);
//        crearObjetos();
//        moTextNDoble.removeMouseListener(l);
//        moTextN.removeMouseListener(l);
//        moTextC.removeMouseListener(l);
//        moTextF.removeMouseListener(l);
//        moChe.removeMouseListener(l);
    }

    public synchronized void addFocusListener(final FocusListener l) {
        super.addFocusListener(l);
//        crearObjetos();
//        moTextNDoble.addFocusListener(l);
//        moTextN.addFocusListener(l);
//        moTextC.addFocusListener(l);
//        moTextF.addFocusListener(l);
//        moChe.addFocusListener(l);
    }

    public synchronized void removeFocusListener(final FocusListener l) {
        super.removeFocusListener(l);
//        crearObjetos();
//        moTextNDoble.removeFocusListener(l);
//        moTextN.removeFocusListener(l);
//        moTextC.removeFocusListener(l);
//        moTextF.removeFocusListener(l);
//        moChe.removeFocusListener(l);
    }

    public synchronized void addKeyListener(final KeyListener l) {
        super.addKeyListener(l);
//        crearObjetos();
//        moTextNDoble.addKeyListener(l);
//        moTextN.addKeyListener(l);
//        moTextC.addKeyListener(l);
//        moTextF.addKeyListener(l);
//        moChe.addKeyListener(l);
    }

    public synchronized void removeKeyListener(final KeyListener l) {
        super.removeKeyListener(l);
//        crearObjetos();
//        moTextNDoble.removeKeyListener(l);
//        moTextN.removeKeyListener(l);
//        moTextC.removeKeyListener(l);
//        moTextF.removeKeyListener(l);
//        moChe.removeKeyListener(l);
    }

    public void focusLost(final FocusEvent e) {
//        System.out.println("JTableCZ->focusLost");
//        if(
//                (e.getSource() != this )
//            ){
        if (getCellEditor() != null
                && isEditing()
                && e.getID() == FocusEvent.FOCUS_LOST
                && JTableCZEditor.class.isAssignableFrom(getCellEditor().getClass())
                && ((JTableCZEditor) getCellEditor()).getComponente().getClass() != javax.swing.JCheckBox.class
                && ((JTableCZEditor) getCellEditor()).getComponente() != e.getOppositeComponent()) {
            //esto se hace pq al editar una celda si pulsas el boton aceptar del form.
            //no se guarda la ultima celda
//            System.out.println("JTableCZ->focusLost->cancel edicion " + e.getSource().getClass().getName());
            int lRow = getEditingRow();
            int lCol = getEditingColumn();
            getCellEditor().stopCellEditing();
            getSelectionModel().setSelectionInterval(lRow, lRow);
            setColumnSelectionInterval(lCol, lCol);
        }
//        }
    }

    public void focusGained(final FocusEvent e) {
        //vacio
    }

    public void keyTyped(final KeyEvent e) {
        //vacio
    }

    public void keyReleased(final KeyEvent e) {
        //vacio
    }
    

    public void keyPressed(final KeyEvent e) {
        //si pulsamos el tabulador nos pasa al siguiente componente
        if ((e.getKeyCode() == e.VK_TAB)) {
            transferFocus();
        }
        //si pulsamos el enter nos pasa a la siguiente celda y ejeutamos la accion asociada
        if ((e.getKeyCode() == e.VK_ENTER)) {
            if (isEditing()) {
                getCellEditor().stopCellEditing();
            }
            llamarActionListener(false);
//            e.setKeyCode(e.VK_TAB);
        }
//        //si pulsamos el ESC ejecutamos la accion asociada ESC
//        if ((e.getKeyCode() == e.VK_ENTER)) {
//            llamarActionListener(true);
//            e.setKeyCode(e.VK_TAB);
//        }
    }

    private void llamarActionListener(final boolean pbCancelado) {
        if (isEnabled()) {
            Object[] listeners = listenerList.getListenerList();
            ActionEventCZ loAccionEvent = null;
            for (int i = 0; i < listeners.length; i++) {
                if (listeners[i] == ActionListenerCZ.class) {
                    if (loAccionEvent == null) {
                        loAccionEvent = new ActionEventCZ(this, 0, (pbCancelado ? mcsESC : mcsENTER));
                    }
                    ActionListenerCZ listener = (ActionListenerCZ) listeners[i + 1];
                    listener.actionPerformed(loAccionEvent);
                }
            }
        }

    }

    public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        try {
            tip = getValueAt(rowIndex, colIndex).toString();
            Component loComp = getCellRenderer(rowIndex, colIndex).getTableCellRendererComponent(
                    this, tip, false, false,
                    rowIndex, colIndex);
            if (loComp != null) {
                int lWith = loComp.getFontMetrics(loComp.getFont()).stringWidth(tip);
                if (lWith < getColumnModel().getColumn(colIndex).getWidth()) {
                    tip = super.getToolTipText(e);
                }
            }
        } catch (Throwable er) {
            tip = super.getToolTipText(e);
        }
        return tip;
    }
}
