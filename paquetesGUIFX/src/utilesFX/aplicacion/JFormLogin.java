/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.aplicacion;

import ListDatos.JListDatos;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.JFieldConTextField;
import utilesFX.configForm.JT2CONEXIONES;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.msgbox.JMsgBox;
import utilesFX.seguridad.visual.JPanelCambioPassWord;
import utilesGUIx.aplicacion.JDatosGeneralesAplicacionModelo;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JConexionIO;
import utilesGUIx.configForm.JConexionLista;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**
 *
 * @author eduardo
 */
public class JFormLogin  extends BorderPane implements IPlugInFrame {
    // Variables declaration - do not modify                     
    @FXML private ComboBox cmbConexion;
    @FXML private Button jBtnConexionesBD;
    @FXML private Button jButtonAceptar;
    @FXML private Button btnCambioContrasena;
    @FXML private Button jButtonCancelar;
    @FXML private ImageView jButtonAceptarImg;
    @FXML private ImageView jButtonCancelarImg;
    @FXML private HBox jPanelBotones;
    @FXML private TextField jTextLogin;
    @FXML private PasswordField jTextPassWord;
    @FXML private ImageView imageview1;
    @FXML private Label lblConexion;
        @FXML
    private Label lblTitulo;
    private JDatosGeneralesAplicacion moDatosGenerales;
    private JFieldConComboBox cmbConexionModelo;
    private JFieldConTextField txtFieldUser;
    private JFieldConTextField txtFieldPass;
    private Runnable moCancel;
    private Runnable moOk;
    private JFormEdicionParametros moParametros = new JFormEdicionParametros();

    public JFormLogin() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/aplicacion/JFormLogin.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        moParametros.setComponenteDefecto(jTextLogin);
        moParametros.setPlugInPasados(true);
        this.getStylesheets().add(
                JFXConfigGlobal.getInstancia().getEstilo()
                    );
        
//        jButtonAceptarImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/accept.gif")));
//        jButtonCancelarImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/cancel.gif")));
        jButtonAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                jButtonAceptarActionPerformed();
            }
        });          
        jButtonCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                jButtonCancelarActionPerformed();
            }
        });              
        jBtnConexionesBD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                jBtnConexionesBDActionPerformed();
            }
        });      
        btnCambioContrasena.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnCambioContrasenaActionPerformed();
            }
        });      
   
    }
    
    public void setDatos(JDatosGeneralesAplicacion poDatosGenerales, Runnable poOk, Runnable poCancel) throws Exception {
        moDatosGenerales = poDatosGenerales;
        moOk=poOk;
        moCancel=poCancel;

        cmbConexionModelo = new JFieldConComboBox(cmbConexion);
        txtFieldUser = new JFieldConTextField(jTextLogin);
        txtFieldPass = new JFieldConTextField(jTextPassWord);
        
        
        
        lblTitulo.setText(poDatosGenerales.getParametrosAplicacion().getCaptionProyecto());
        try {
            recargarCombo(cmbConexionModelo, moDatosGenerales.getDatosGeneralesXML());
        } catch (ClassNotFoundException e1) {
            JDepuracion.anadirTexto(getClass().getName(), e1);
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        lblConexion.setVisible(
                moDatosGenerales.getParametrosAplicacion().isConexionLogin()
                || cmbConexion.getItems().size()== 0);
        cmbConexion.setVisible(
                moDatosGenerales.getParametrosAplicacion().isConexionLogin()
                || cmbConexion.getItems().size() == 0);
        jBtnConexionesBD.setVisible(
                moDatosGenerales.getParametrosAplicacion().isConexionLogin()
                || cmbConexion.getItems().size() == 0);
        txtFieldUser.setText(moDatosGenerales.getUltUsuario());
        
        try {
            cmbConexionModelo.mbSeleccionarClave(moDatosGenerales.getUltTipoConexion());
        } catch (Throwable e) {
        }
        try {
            imageview1.setImage((Image) moDatosGenerales.getParametrosAplicacion().getImagenLogin());
        } catch (Throwable e) {
        }
    }    

    public static void recargarCombo(JFieldConComboBox poComboBoxServidor, JDatosGeneralesXML poXML) throws Exception {
        JConexionIO loIO = new JConexionIO();
        loIO.setLector(poXML);
        recargarCombo(poComboBoxServidor, loIO.leerListaConexiones());
    }

    public static void recargarCombo(JFieldConComboBox poComboBoxServidor, JConexionLista poLista) throws Exception {
        
        String lsConexion = null;
        //guardamos la conexion seleccionada
        if (poComboBoxServidor.getComponente().getSelectionModel().getSelectedIndex() >= 0) {
            lsConexion = poComboBoxServidor.getFilaActual().msCampo(0);
        }
        poComboBoxServidor.borrarTodo();
        for (int i = 0; i < poLista.size(); i++) {
            JConexion loAux = (JConexion) poLista.get(i);
            poComboBoxServidor.addLinea(
                    loAux.getNombre(),
                    loAux.getNombre());

        }
        //ponemos una por defecto
        try {
            if (poComboBoxServidor.getComponente().getSelectionModel().getSelectedIndex() < 0) {
                poComboBoxServidor.getComponente().getSelectionModel().clearAndSelect(0);
            }
        } catch (Throwable e) {
        }
        //ponemos la conexion guardada
        try {
            if (lsConexion != null) {
                poComboBoxServidor.mbSeleccionarClave(lsConexion);
            }
        } catch (Throwable e) {
        }
    }

    public static String getUltUsuario(JDatosGeneralesXML poXML) {
        return poXML.getPropiedad(JDatosGeneralesAplicacionModelo.mcsUltUsuario);
    }

    public static String getUltTipoConexion(JDatosGeneralesXML poXML) {
        try {
            return poXML.getPropiedad(JDatosGeneralesAplicacionModelo.mcsUltTipoConexion);
        } catch (Exception e) {
            return "";
        }
    }

    public static void autentificar(JFieldConComboBox poComboBoxServidor, String psLogin, String psPassWord, JDatosGeneralesAplicacion poDatosGenerales) throws Throwable {
        if (poComboBoxServidor.getComponente().getSelectionModel().getSelectedIndex() < 0) {
            throw new Exception("No se ha seleccionado ningun servidor");
        }
        autentificar(poComboBoxServidor.getFilaActual().msCampo(0), psLogin, psPassWord, poDatosGenerales);
    }
    
    //Autentificar directamente con el nombre del servidor
    public static void autentificar(String psServidor, String psLogin, String psPassWord, JDatosGeneralesAplicacion poDatosGenerales) throws Throwable {
        poDatosGenerales.autentificar(psServidor, psLogin, psPassWord);
    }
    
    public JDatosGeneralesAplicacion getDatosGenerales() {
        return moDatosGenerales;
    }

    private static boolean mbComparar(String psCadena1, String psCadena2) {
        boolean lbResult = true;
        if (psCadena1 == null || psCadena1.equals("")) {
            if (psCadena2 == null || psCadena2.equals("")) {
                lbResult = true;
            } else {
                lbResult = false;
            }
        } else {
            if (psCadena2 == null || psCadena2.equals("")) {
                lbResult = false;
            } else {
                lbResult = (psCadena1.equals(psCadena2));
            }
        }

        return lbResult;
    }

    public static void mostrarConexiones(JFieldConComboBox poComboBoxServidor, JDatosGeneralesAplicacion poDatosGenerales) throws Exception {
        final JT2CONEXIONES loConexiones = new JT2CONEXIONES(
                poDatosGenerales.getDatosGeneralesXML()
                , (poDatosGenerales.getMostrarPantalla() == null 
                        ? JFXConfigGlobal.getInstancia().getMostrarPantalla() 
                        : poDatosGenerales.getMostrarPantalla()));
        loConexiones.mostrarFormPrinci((IPanelControlador poControlador) -> {
            try {
                recargarCombo(poComboBoxServidor, poDatosGenerales.getDatosGeneralesXML());
                try {
                    poComboBoxServidor.mbSeleccionarClave(poDatosGenerales.getUltTipoConexion());
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(JFormLogin.class.getName(), ex);
                }
            } catch (Exception ex) {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                        null, ex, null);
            }
        });
        
        
    }
    private void jButtonAceptarActionPerformed() {                                               
        try {
            autentificar(cmbConexionModelo, jTextLogin.getText(), jTextPassWord.getText(), moDatosGenerales);
            JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(this);
            if(moOk!=null){
                Platform.runLater(moOk);
            }
        } catch (Throwable e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }

    }                                              

    private void jButtonCancelarActionPerformed() {                                                
        moDatosGenerales.setServer(null);
        JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(this);
        if(moCancel!=null){
            Platform.runLater(moCancel);
        }        
    }                                               

    private void formWindowActivated() {                                     
        try {
            recargarCombo(cmbConexionModelo, moDatosGenerales.getDatosGeneralesXML());
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }

    }                                    

    private void jBtnConexionesBDActionPerformed() {
        try {
            mostrarConexiones(cmbConexionModelo, moDatosGenerales);
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }
    private void btnCambioContrasenaActionPerformed() {       
        try {
            autentificar(cmbConexionModelo, jTextLogin.getText(), jTextPassWord.getText(), moDatosGenerales);
            JTPlugInUsuarios loUsu = moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW().getUsuarios();
            loUsu.moList.buscar(JListDatos.mclTIgual, loUsu.lPosiNOMBRE, jTextLogin.getText());
            loUsu.moList.moFila().setTipoModif(JListDatos.mclNada);
            JPanelCambioPassWord loPanel = new JPanelCambioPassWord();
            loPanel.setDatos(
                    moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW()
                    , loUsu);
            JMostrarPantallaParam loParam = new JMostrarPantallaParam((IFormEdicion)loPanel, JMostrarPantalla.mclEdicionFrame);
            loParam.setCallBack((JMostrarPantallaParam poControlador) -> {
                jTextPassWord.setText(loUsu.getPASSWORD().getString());
            });
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(loParam);
            
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }        
    }

    @Override
    public String getIdentificador() {
        return "login";
    }

    @Override
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    @Override
    public void aplicarListaComponentesAplicacion() {
    }
}
