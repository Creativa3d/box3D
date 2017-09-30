/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.msgbox;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesFX.Copiar;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JFormMensaje extends BorderPane{

    private static final long serialVersionUID = 1L;
    
    @FXML private Button btnAceptar;
    @FXML private Button btnCopiar;
    @FXML private Button btnDetalles;
    @FXML private WebView jLabel1;
    @FXML private ImageView btnDetallesImg;
    @FXML private ImageView btnCopiarImg;
    @FXML private ImageView btnAceptarImg;
    @FXML private GridPane  jPanel1;
    
    private String msMensaje = "";
    private String msTitulo = "";
    private Thread moThread;
    private boolean mbFin = false;
    private Thread moThread2;
    private String msDetalles;
    private Copiar moCopiar;
    private Stage modialog;
    private String msMensajeHTML;
    private Runnable moOk;
    
    public JFormMensaje() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFX/msgbox/JFormMensaje.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }     

        Image imageAccess = new Image(this.getClass().getResourceAsStream("/utilesFX/images/accept.gif"));
        Image imageCopiar = new Image(this.getClass().getResourceAsStream("/utilesFX/images/Copy16.gif"));
        Image imageDetalles = new Image(this.getClass().getResourceAsStream("/utilesFX/images/ColumnInsertBefore16.gif"));

        btnAceptarImg.setImage(imageAccess);
        btnCopiarImg.setImage(imageCopiar);
        btnDetallesImg.setImage(imageDetalles);   
        
        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnAceptarActionPerformed();
            }
            
        }); 
        btnCopiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnCopiarActionPerformed();
            }
            
        }); 
        btnDetalles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnDetallesActionPerformed();
            }
            
        }); 
    }    
    
    public void setOk(Runnable poOk){
        moOk=poOk;
    }

    public void setDatos(String psTexto, String psDetalles, Stage dialog) {
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                formWindowClosing();
            }
        });
        setMensaje(psTexto);
        msDetalles = psDetalles;
        btnDetalles.setVisible(psDetalles != null && !psDetalles.equals(""));
        modialog = dialog;
    }

    public void cerrarA(final int plSegundos) {
        moThread2 = new Thread(new Runnable() {
            public void run() {
                int lSegundosT = plSegundos;
                while (!mbFin && lSegundosT > 0) {
                    lSegundosT--;
                    synchronized(this){
                        try {
                            wait(1000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
                if (!mbFin) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            modialog.close();
                        }
                    });
                    
                }
                mbFin = true;
            }
        });
        moThread2.start();
    }

    public void setMostrarCada(final int plSegundos, final int plHasta) {
        moThread = new Thread(new Runnable() {
            public void run() {
                int lSegundosT = 0;
                while (!mbFin && lSegundosT < plHasta) {
                    lSegundosT += plSegundos;
                    modialog.toFront();
                    synchronized(this){
                        try {
                            wait(plSegundos * 1000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        });
        moThread.start();
    }

    public String getTitulo() {
        return msTitulo;
    }

    public String getMensaje() {
        return msMensaje;
    }

    public String getMensajeHTML() {
        return msMensajeHTML;
    }

    public void setMensajeHTML(String psTexto) {
        msMensaje = psTexto;
        msMensajeHTML = psTexto;
        mostrarMensaje();
    }

    public void setMensaje(String psTexto) {
        msMensaje = psTexto;
        mostrarMensaje();
    }

    private void mostrarMensaje() {
        if (msMensaje.indexOf("<html>") >= 0) {
            msMensajeHTML=msMensaje;
            jLabel1.getEngine().loadContent(msMensaje);
        } else {
            msMensajeHTML="<html>" + msMensaje.replaceAll("\n", "<br>") + "</html>";
            jLabel1.getEngine().loadContent(msMensajeHTML);
        }
    }
    
    public synchronized void setTituloInfo(String psTituloInfo) {
        msTitulo = psTituloInfo;
        
    }
    
    public synchronized void mostrarErrores(IListaElementos poErrores) {
        
        for (int i = 0; i < poErrores.size(); i++) {
            addErrorSinMostrar((String) poErrores.get(i));
        }
        mostrarMensaje();
    }
    
    public synchronized void addErrorSinMostrar(String psError) {
        msMensaje = msMensaje + psError + "<br>";
    }

    public synchronized void addError(String psError) {
        addErrorSinMostrar(psError);
        mostrarMensaje();
    }

    public GridPane getPanelBotonera() {
        return jPanel1;
    }
    
    

    private void btnAceptarActionPerformed() {                                           
        modialog.close();
        mbFin = true;
    }                                          
    
    private void formWindowClosing() {                                   
        mbFin = true;
        if(moOk!=null){
            moOk.run();
        }
    }                                  
    
    private void formWindowClosed() {                                  
        mbFin = true;
    }                                 
    
    private void btnDetallesActionPerformed() {                                            
        try{
            JMsgBox.mensajeError(this, msDetalles);
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }                                           

    private void btnCopiarActionPerformed() {                                          
        moCopiar = new Copiar();
        moCopiar.setClip(msMensaje);
    }                                         
    
}
