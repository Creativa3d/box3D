/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;

public class JMostrarPantallaFormulario 
//implements WindowListener, InternalFrameListener
{

    private Object moForm;
    private JMostrarPantallaParam moParam;
    private JMostrarPantalla moMostrar;

    public JMostrarPantallaFormulario(JMostrarPantalla poMostrar, Object poForm, JMostrarPantallaParam poComponente) {
        moMostrar = poMostrar;
        moForm = poForm;
        moParam = poComponente;

        //para detectar cuando se cierran
        if (moForm instanceof Stage) {
            ((Stage) moForm).setOnCloseRequest((WindowEvent event) -> {
                if(event.getEventType() == WindowEvent.WINDOW_HIDDEN){
                    windowClosing();
                    event.consume();
                    accionesFinalesAlCerrar();
                } else {
                    windowClosed();
                }
            });
        } else if (moForm instanceof JInternalFrameFX) {
            ((JInternalFrameFX) moForm).setOnCloseRequest((JInternalFrameFXEvent event) -> {
                if(event.getEventType() == WindowEvent.WINDOW_HIDDEN){
                    windowClosing();
                    event.consume();
                    accionesFinalesAlCerrar();
                    
                } else {
                    windowClosed();
                }
            });
        }

    }
    public JMostrarPantallaParam getParam(){
        return moParam;
    }
    public Object getForm(){
        return moForm;
    }

    public void mostrarFrente() {
        JMostrarPantalla.mostrarFrente(moForm, moParam);
    }

    /**Se debe usar siempre desde JMostrarPantalla o internamente*/
    public void dispose() {
        //cerramos en funcion del tipo de formulario
        if (moForm instanceof Stage) {
            ((Stage) moForm).close();
        } else if (moForm instanceof JInternalFrameFX) {
            ((JInternalFrameFX) moForm).salir();
        }
    }
    public void windowClosed() {
        if (moParam != null && moParam.getControlador()!=null && moParam.getControlador() instanceof ISalir) {
            ((ISalir) moParam.getControlador()).salir();
        }
        accionesFinalesAlCerrar();
    }
    
    private void accionesFinalesAlCerrar(){
        if (moParam != null && moParam.getCallBack()!=null) {
            moParam.getCallBack().callBack(moParam);
        }

        if (moMostrar != null) {
            moMostrar.remove(this);
        }
        if (moForm instanceof Stage) {
//            ((Stage)moForm).getScene().setRoot(new Label());
//            ((Stage)moForm).setScene(new Scene(new Label()));
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

    public void windowClosing() {
        if(moParam!=null && moParam.isXCierra()){
            if (moParam != null && moParam.getComponente() instanceof ISalir) {
                ((ISalir) moParam.getComponente()).salir();
            } else {
                dispose();
            }
        }
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
