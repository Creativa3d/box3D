/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.controlProcesos;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;
import utilesGUI.procesar.IProcesoAccion;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.controlProcesos.IProcesoThreadGroupListener;
import utilesGUIx.controlProcesos.JProcesoManejador;

/**
 *
 * @author eduardo
 */
public class JProcesoThreadGroup implements IProcesoThreadGroup {

    private static final long serialVersionUID = 1L;

    private ProgressBar jProgressBar1;

    private transient IProcesoThreadGroup moManejador;
    private int mlSegundosRefresco = 3;
//    private JFormProcesoControl moForm=null;

    private boolean mbVerFormPorADD = true;
    private boolean mbPulsadoLupa = false;
    private boolean mbPuedePulsarseLupa = true;

    private long mlADD = -1;
    private boolean mbContinuar = true;
    private final Timer timer;
    private final Label moMensaje;
    private JFormProcesoControl moForm;
    private Stage dialog;

    /**
     * Creates new form JProcesoThreadGroup
     */
    public JProcesoThreadGroup(ProgressBar poProgressBar1, Label poMensaje) {
        jProgressBar1 = poProgressBar1;
        moMensaje=poMensaje;
        jProgressBar1.setVisible(false);
        jProgressBar1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                jProgressBar1MouseClicked();
            }
        });
        mbPuedePulsarseLupa = false;
        moManejador = new JProcesoManejador(new JProcesoElementoFactoryMethod());
        moForm = new JFormProcesoControl(this);

        

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                controlGrafico();
            }
        }, mlSegundosRefresco * 1000, mlSegundosRefresco * 1000);

        mbContinuar = true;
    }

    public void controlGrafico() {
        try {
            if (mbContinuar) {
                if (!moManejador.isProcesosActivos()) {
                    mbPulsadoLupa = false;
                    setMostrarBarra(false);
                    ocultarForm();
                } else {
                    long lAux = (new Date()).getTime();

                    if (isVerFormPorADD() && mlADD != -1 && (lAux - mlADD) > 2 * 1000
                            && JProcesoManejador.isProcesosMostrarForm(moManejador.getListaElementos())) {
                        verForm();
                    }
                    if (!JProcesoManejador.isProcesosMostrarForm(moManejador.getListaElementos())
                            && !mbPulsadoLupa) {
                        ocultarForm();
                    }
                }
            }
            if (mbContinuar) {
                if (moManejador.isProcesosActivos()) {
                    setMostrarBarra(true);
                }
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }

    public IProcesoThreadGroup getManejador() {
        return moManejador;
    }

    public void setManejador(IProcesoThreadGroup poManejador) {
        moManejador = poManejador;
    }

    public void addProcesoYEjecutar(IProcesoAccion poProceso) {
        addProcesoYEjecutar(poProceso, true);
    }

    public void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm) {
        addProcesoYEjecutar(poProceso, pbConMostrarForm, false);
    }

    private synchronized void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm, boolean pbIsSwing) {
        mbContinuar = true;

        setMostrarBarra(true);

        moManejador.addProcesoYEjecutar(poProceso, pbConMostrarForm);

        if (pbConMostrarForm) {
            mlADD = (new Date()).getTime();
        } else {
            mlADD = -1;
        }

    }

    public boolean isProcesosActivos() {
        return moManejador.isProcesosActivos();
    }

    public int getProcesoTotal() {
        return moManejador.getProcesoTotal();
    }

    public String getProcesoTexto() {
        return moManejador.getProcesoTexto();
    }

    public int getIndice(Object elemento) {
        return moManejador.getIndice(elemento);
    }

    public IListaElementos getListaElementos() {
        return moManejador.getListaElementos();
    }

    public void addListener(IProcesoThreadGroupListener poListener) {
        moManejador.addListener(poListener);
    }

    public void removeListener(IProcesoThreadGroupListener poListener) {
        moManejador.removeListener(poListener);
    }

    public IListaElementos getListaProcesos() {
        return moManejador.getListaProcesos();
    }

    private void setMostrarBarra(final boolean pbVer) {
        if (Platform.isFxApplicationThread()) {
            jProgressBar1.setVisible(pbVer);
            mbPuedePulsarseLupa = pbVer;
            jProgressBar1.layout();
            moMensaje.setText("");
            if (pbVer) {
                texto();
            }

        } else {
            Platform.runLater(new Runnable() {
                public void run() {
                    jProgressBar1.setVisible(pbVer);
                    mbPuedePulsarseLupa = pbVer;
                    jProgressBar1.layout();
                    moMensaje.setText("");
                    if (pbVer) {
                        texto();
                    }
                }
            });
        }

    }

    private void texto() {
        double ldTotalFinal = (double)moManejador.getProcesoTotal()/100.0;
        if(ldTotalFinal==0){
            ldTotalFinal=-1;
        }
        jProgressBar1.setProgress(ldTotalFinal);
        
        moMensaje.setText("" + String.valueOf(moManejador.getProcesoTotal()) + "% " + moManejador.getProcesoTexto());
        if (getForm().isVisible()) {
            getForm().actualizar();
        }
    }

    private JFormProcesoControl getForm() {
        return moForm;
    }


    void formSalir() {
        mbPulsadoLupa = false;
        mlADD = -1;
    }

    private synchronized Stage getDialog(){
        if(dialog==null){
            dialog = new Stage();
            dialog.setTitle("Procesos");
            dialog.setResizable(false);
//no ponemos el propietario pq cuando desaparece se pone el form principal como primer plano, dejando atras
//el nuevo form abierto
//            if(jProgressBar1.getScene()!=null){
//                dialog.initOwner(jProgressBar1.getScene().getWindow());
//            }
            if(JFXConfigGlobal.getInstancia().getMostrarPantalla().getImagenIcono()!=null){
                dialog.getIcons().add(
                        (Image) JFXConfigGlobal.getInstancia().getMostrarPantalla().getImagenIcono()
                );
            }          
            
            dialog.initModality(Modality.NONE);
            Scene s = new Scene(moForm);
            s.getStylesheets().add(
                    JFXConfigGlobal.getInstancia().getEstilo()
                        );
            dialog.setScene(s);
        }
        return dialog;
    }
    void ocultarForm() {
        if (Platform.isFxApplicationThread()) {
            getDialog().hide();
        } else {
            Platform.runLater(() -> {
                getDialog().hide();
            });
        }
    }

    void verForm() {
        if (Platform.isFxApplicationThread()) {
            getForm().actualizar();
            getDialog().show();                
            getDialog().toFront();
        } else {
            Platform.runLater(new Runnable() {
                public void run() {
                    if (moManejador.isProcesosActivos() || mbPulsadoLupa) {
                        getForm().actualizar();
                        getDialog().show();                
                        getDialog().toFront();
                    }
                }
            });
        }

    }

    protected void finalize() throws Throwable {
        mbContinuar = false;
        timer.cancel();
        timer.purge();
        super.finalize();
    }

    private void jProgressBar1MouseClicked() {

        if (mbPuedePulsarseLupa) {
            mbPulsadoLupa = true;
            verForm();
        }

    }

    /**
     * @return the mbVerFormPorADD
     */
    public boolean isVerFormPorADD() {
        return mbVerFormPorADD;
    }

    /**
     * @param mbVerFormPorADD the mbVerFormPorADD to set
     */
    public void setVerFormPorADD(boolean mbVerFormPorADD) {
        this.mbVerFormPorADD = mbVerFormPorADD;
    }

}
