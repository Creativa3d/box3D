/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.seguridad.visual;

import ListDatos.IResultado;
import ListDatos.JSTabla;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import utilesFX.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.Rectangulo;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;

/**
 *
 * @author eduardo
 */
public class JPanelCambioPassWord extends JPanelGENERALBASE {

    @FXML
    private PasswordField txtACTUAL;

    @FXML
    private PasswordField txtNUEVA;

    @FXML
    private PasswordField txtCONFIRMARNUEVA;
    private JTPlugInUsuarios moUsuarios;
    private IPlugInSeguridadRW moRW;
    
    public JPanelCambioPassWord() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/seguridad/visual/JPanelCambioPassWord.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }        
        
    }
    

    public void setDatos(IPlugInSeguridadRW poRW, JTPlugInUsuarios poUsuario){
        moUsuarios=poUsuario;
        moRW=poRW;
    }

    @Override
    public JSTabla getTabla() {
        return moUsuarios;
    }


    @Override
    public void rellenarPantalla() throws Exception {
    }
    
    @Override
    public void habilitarSegunEdicion() throws Exception {
    }


    @Override
    public void mostrarDatos() throws Exception {
    }

    @Override
    public void establecerDatos() throws Exception {
        if(!txtCONFIRMARNUEVA.getText().equalsIgnoreCase(txtNUEVA.getText())){
            throw new Exception("La nueva contraseña y su confirmación no coincide");
        }
        if(!txtACTUAL.getText().equalsIgnoreCase(moUsuarios.getPASSWORD().getString())){
            throw new Exception("La contraseña actual es incorrecta");
        }
        moUsuarios.getPASSWORD().setValue(txtNUEVA.getText());
    }

    @Override
    public void aceptar() throws Exception {
        IResultado loResult=moUsuarios.moList.update(false);
        moRW.guardarUsuario(moUsuarios.moList.moFila());
    }

    @Override
    public String getTitulo() {
        return "Cambio contraseña";
    }    

    @Override
    public Rectangulo getTanano() {
        return new Rectangulo(450, 300);
    }
}
