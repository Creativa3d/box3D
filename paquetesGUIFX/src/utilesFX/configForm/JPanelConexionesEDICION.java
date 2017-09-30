/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.configForm;

import ListDatos.JSTabla;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilesFX.JFieldConTextField;
import utilesFX.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.Rectangulo;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JT2CONEXIONESModelo;

/**
 *
 * @author eduardo
 */
public class JPanelConexionesEDICION extends JPanelGENERALBASE {


    @FXML
    private TextField txtNombre;

    @FXML
    private Label lblNombre;

    @FXML
    private Button btnPrueba;
    JPanelConexiones jPanelConexiones1;
    private JFieldConTextField txtNombreModelo;
    public JPanelConexionesEDICION (){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("JPanelConexionesEDICION.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        jPanelConexiones1=new JPanelConexiones();
        
        add(jPanelConexiones1, 0, 1, 2,1);
        
        btnPrueba.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    jPanelConexiones1.probar();
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeFlotante(JPanelConexionesEDICION.this, "Pruebas correctas");
                } catch (Exception ex) {
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(JPanelConexionesEDICION.this, ex, null);
                }
            }
        });
        
        
    }

    public void setDatos(JConexion poConexion, JT2CONEXIONESModelo poConexiones) throws Exception{
        jPanelConexiones1.setDatos(poConexion, poConexiones);
    }
    public JSTabla getTabla() {
        return jPanelConexiones1.getTabla();
    }

    public void aceptar() throws Exception {
        jPanelConexiones1.aceptar();
    }
    public void cancelar() throws Exception {
        jPanelConexiones1.cancelar();
    }

    public void rellenarPantalla() throws Exception {
        txtNombreModelo = new JFieldConTextField(txtNombre);
        jPanelConexiones1.rellenarPantalla();
    }

    public void mostrarDatos() throws Exception {
        jPanelConexiones1.mostrarDatos();
        txtNombreModelo.setValueTabla(jPanelConexiones1.getConexion().getNombre());
    }

    public void establecerDatos() throws Exception {
        jPanelConexiones1.establecerDatos();
        jPanelConexiones1.getConexion().setNombre(txtNombreModelo.getText());
    }

    public void habilitarSegunEdicion() throws Exception {
        jPanelConexiones1.habilitarSegunEdicion();
    }

    public void ponerTipoTextos() throws Exception {
        jPanelConexiones1.ponerTipoTextos();
    }

    public Rectangulo getTanano() {
        return jPanelConexiones1.getTanano();
    }

    public String getTitulo() {
        return jPanelConexiones1.getTitulo();
    }
    
}
