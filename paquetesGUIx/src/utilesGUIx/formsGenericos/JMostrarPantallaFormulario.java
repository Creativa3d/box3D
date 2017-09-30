/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JInternalFrame;
import javax.swing.JWindow;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import utiles.JDepuracion;

public class JMostrarPantallaFormulario implements WindowListener, InternalFrameListener {

    private Component moForm;
    private JMostrarPantallaParam moParam;
    private JMostrarPantallaSwing moMostrar;

    public JMostrarPantallaFormulario(JMostrarPantallaSwing poMostrar, Component poForm, JMostrarPantallaParam poComponente) {
        moMostrar = poMostrar;
        moForm = poForm;
        moParam = poComponente;

        //para detectar cuando se cierran
        if (moForm instanceof JWindow) {
            ((JWindow) moForm).addWindowListener(this);
        } else if (moForm instanceof JInternalFrame) {
            ((JInternalFrame) moForm).addInternalFrameListener(this);
        } else if (moForm instanceof Window) {
            ((Window) moForm).addWindowListener(this);
        }

    }
    public JMostrarPantallaParam getParam(){
        return moParam;
    }
    public Component getForm(){
        return moForm;
    }

    public void mostrarFrente() {
        JMostrarPantalla.mostrarFrente(moForm, moParam);
    }

    /**Se debe usar siempre desde JMostrarPantalla o internamente*/
    public void dispose() {
        //cerramos en funcion del tipo de formulario
        if (moForm instanceof JWindow) {
            ((JWindow) moForm).dispose();
        } else if (moForm instanceof JInternalFrame) {
            ((JInternalFrame) moForm).dispose();
        } else if (moForm instanceof Window) {
            ((Window) moForm).dispose();
        }
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
        if (moParam != null && moParam.getCallBack()!=null) {
            moParam.getCallBack().callBack(moParam);
        }

        if (moMostrar != null) {
            moMostrar.remove(this);
        }
        moForm = null;
        moParam = null;
        moMostrar = null;
        System.gc();
        JDepuracion.anadirTexto(
                JDepuracion.mclINFORMACION,
                getClass().getName(),
                "Memoria libre " + String.valueOf(Runtime.getRuntime().freeMemory()));
    }

    public void windowClosing(WindowEvent e) {
        if(moParam.isXCierra()){

            if (moParam != null && moParam.getComponente() instanceof ISalir) {
                ((ISalir) moParam.getComponente()).salir();
            } else {
                dispose();
            }
        }
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
    }

    public void internalFrameClosed(InternalFrameEvent e) {
        windowClosed(null);
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        windowClosing(null);
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameOpened(InternalFrameEvent e) {
    }

    boolean isComponente(Object poComponente) {
        return (poComponente == moParam.getComponente()
                || poComponente == moParam.getControlador()
                || poComponente == moParam.getPanel()
                || poComponente == moParam.getCrear()
                || poComponente == moParam.getPanelNave()
                || poComponente == moParam
                || poComponente == moForm);
    }
    public boolean isMismoIdentificador(JMostrarPantallaParam poParam) {
        return moParam.isMismoIdentificador(poParam);
    }
}
