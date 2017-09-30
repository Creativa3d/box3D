/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.navegador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.navegador.INavegador;

/**
 *
 * @author eduardo
 */
public class JNavegador extends GridPane implements EventHandler<ActionEvent>{

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnPrimero;

    @FXML
    private Button btnUltimo;

    @FXML
    public Button btnNuevo;

    @FXML
    private Button btnRefrescar;

    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnBuscar;    
    @FXML
    private Separator jSeparator2;    
    private INavegador moNavegador;    
    public JNavegador() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/navegador/JNavegador.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        btnAceptar.setOnAction(this);
        btnAnterior.setOnAction(this);
        btnBorrar.setOnAction(this);
        btnBuscar.setOnAction(this);
        btnCancelar.setOnAction(this);
        btnEditar.setOnAction(this);
        btnNuevo.setOnAction(this);
        btnPrimero.setOnAction(this);
        btnRefrescar.setOnAction(this);
        btnSiguiente.setOnAction(this);
        btnUltimo.setOnAction(this);

    }
    

    /**
     * Establece la fuente de datos
     * @param poDatos fuente datos
     */
    public void setDatos(INavegador poDatos){
        moNavegador = poDatos;
        btnBorrar.setVisible(moNavegador.getBorrarSN());
        btnEditar.setVisible(moNavegador.getEditarSN());
        btnNuevo.setVisible(moNavegador.getNuevoSN());
        btnRefrescar.setVisible(moNavegador.getRefrescarSN());
        btnBuscar.setVisible(moNavegador.getBuscarSN());
        if(!moNavegador.getDentroFormEdicionSN()){
            btnAceptar.setVisible(false);
            btnCancelar.setVisible(false);
            jSeparator2.setVisible(false);
        }
        
    }
    /**Deshabilita aceptar y cancelar y habilita todo lo demas*/
    public void setModoNormal(){
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);

        btnBorrar.setDisable(false);
        btnEditar.setDisable(false);
        btnNuevo.setDisable(false);
        btnRefrescar.setDisable(false);
        btnBuscar.setDisable(false);
        

        btnAnterior.setDisable(false);
        btnPrimero.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
    }
    /**Habilita aceptar y cancelar y desabilita todo lo demas*/
    public void setModoEdicion(){
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);

        btnBorrar.setDisable(true);
        btnEditar.setDisable(true);
        btnNuevo.setDisable(true);
        btnRefrescar.setDisable(true);
        btnBuscar.setDisable(true);

        btnAnterior.setDisable(true);
        btnPrimero.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
    }
    /**Ejecuta las acciones de los botone
     * @param es*/
    @Override
    public void handle(ActionEvent e) {
        try{
            if(e.getSource() == btnAnterior){
                btnSiguiente.setDisable(false);
                btnUltimo.setDisable(false);
                if(!moNavegador.anterior()){
                    btnPrimero.setDisable(true);
                    btnAnterior.setDisable(true);
                }
            }
            if(e.getSource() == btnPrimero){
                btnSiguiente.setDisable(false);
                btnUltimo.setDisable(false);
                if(!moNavegador.primero()){
                    btnPrimero.setDisable(true);
                    btnAnterior.setDisable(true);
                }
            }
            if(e.getSource() == btnSiguiente){
                btnPrimero.setDisable(false);
                btnAnterior.setDisable(false);
                if(!moNavegador.siguiente()){
                    btnSiguiente.setDisable(true);
                    btnUltimo.setDisable(true);
                }
            }
            if(e.getSource() == btnUltimo){
                btnPrimero.setDisable(false);
                btnAnterior.setDisable(false);
                if(!moNavegador.ultimo()){
                    btnSiguiente.setDisable(true);
                    btnUltimo.setDisable(true);
                }
            }
            if(e.getSource() == btnBorrar){

                    
                JOptionPaneFX.showConfirmDialog(
                        this
                        , "¿Estas seguro de borrar el registro actual?"
                        , ()->{
                            try {
                                moNavegador.borrar();
                            } catch (Throwable ex) {
                                JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
                            }
                        }
                        , ()->{});
                
            }
            if(e.getSource() == btnEditar){
                moNavegador.editar();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoEdicion();
                }
                
            }
            if(e.getSource() == btnNuevo){
                moNavegador.nuevo();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoEdicion();
                }
            }
            if(e.getSource() == btnAceptar){
                moNavegador.aceptar();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoNormal();
                }

            }
            if(e.getSource() == btnCancelar){
                moNavegador.cancelar();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoNormal();
                }
            }
            if(e.getSource() == btnRefrescar){
                moNavegador.refrescar();
                btnPrimero.setDisable(false);
                btnAnterior.setDisable(false);
                btnSiguiente.setDisable(false);
                btnUltimo.setDisable(false);
            }
            if(e.getSource() == btnBuscar){
                moNavegador.buscar();
                btnPrimero.setDisable(false);
                btnAnterior.setDisable(false);
                btnSiguiente.setDisable(false);
                btnUltimo.setDisable(false);
            }
            
        }catch(Throwable error){
            JMsgBox.mensajeErrorYLog(this, error, getClass().getName());
        }
    }        

}
