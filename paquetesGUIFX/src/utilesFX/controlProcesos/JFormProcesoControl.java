/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.controlProcesos;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author eduardo
 */
public class JFormProcesoControl extends BorderPane {

    @FXML
    private Button btnSalir;

    @FXML
    private VBox jPanelProcesos;

    private final JProcesoThreadGroup moPadre;
    public JFormProcesoControl(JProcesoThreadGroup poPadre) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/controlProcesos/JFormProcesoControl.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();

        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }        
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                JFormProcesoControl.this.getScene().getWindow().hide();
                moPadre.formSalir();                
            }

        });
//         if(JFXConfigGlobal.getInstancia().getMostrarPantalla().getImagenIcono()!=null){
//            this.setIconImage(((ImageIcon) utilesGUIx.JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getImagenIcono()).getImage());
//        }          

        moPadre = poPadre;
        actualizar();
    }
    

    private JProcesoControl getControl(JProcesoElemento poElem){
        JProcesoControl loControl = null;
        for(int i = 0 ; i < jPanelProcesos.getChildren().size() && loControl==null; i++){
            Node loAux=jPanelProcesos.getChildren().get(i);
            if(loAux.getClass().isAssignableFrom(JProcesoControl.class)){
                JProcesoControl loAuxC = (JProcesoControl) loAux;
                if(loAuxC.getElemento() == poElem){
                    loControl=loAuxC;
                }
            }
        }
        if(loControl==null){
            loControl=new JProcesoControl(poElem);
            jPanelProcesos.getChildren().add(loControl);
        }
        return loControl;
    }
    private void procesar(){
        //borramos los componenetes visuales q nohagan falta
        for(int i = 0 ; i < jPanelProcesos.getChildren().size(); i++){
            Node loAux=jPanelProcesos.getChildren().get(i);
            if(loAux.getClass().isAssignableFrom(JProcesoControl.class)){
                JProcesoControl loAuxC = (JProcesoControl) loAux;
                if(moPadre.getIndice(loAuxC.getElemento())<0 ){
                    jPanelProcesos.getChildren().remove(loAux);
                }
            }
        }

        //actualizamos/añadimos los componenetes visuales
        for(int i = 0 ; i < moPadre.getListaElementos().size(); i++){
            JProcesoElemento loElem = (JProcesoElemento)moPadre.getListaElementos().get(i);
            JProcesoControl loControl = (JProcesoControl)getControl(loElem);

            if(loElem.getError() != null){
                loControl.establecer(
                        loElem.getProceso().getNumeroRegistro(),
                        loElem.getProceso().getNumeroRegistros(),
                        loElem.getProceso().getTitulo(),
                        loElem.getError().toString(),
                        loElem.getProceso().getParametros());
            }else{
                loControl.establecer(
                        loElem.getProceso().getNumeroRegistro(),
                        loElem.getProceso().getNumeroRegistros(),
                        loElem.getProceso().getTitulo(),
                        loElem.getProceso().getTituloRegistroActual(),
                        loElem.getProceso().getParametros());
            }
        }
    }
    void actualizar() {
         if (Platform.isFxApplicationThread()) {
            procesar();
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    procesar();
                }
            });
        }


    }    
}
