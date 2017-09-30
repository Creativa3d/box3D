/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.configForm;

import ListDatos.JFilaDatosDefecto;
import ListDatos.JSTabla;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utiles.JConversiones;
import utilesFX.JCMBLinea;
import utilesFX.JFieldConComboBox;
import utilesFX.JFieldConTextField;
import utilesFX.formsGenericos.edicion.JPanelGENERALBASE;
import utilesFX.msgbox.JMsgBox;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.Rectangulo;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JT2CONEXIONESModelo;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelConexiones extends JPanelGENERALBASE {

    @FXML
    private Label lblUsuario;

    @FXML
    private TextField txtRuta;

    @FXML
    private Label lblDominio;

    @FXML
    private TextField txtIP;

    @FXML
    private PasswordField txtPassWord;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtInstancia;

    @FXML
    private Label lblRuta;

    @FXML
    private Label lblNomBD;

    @FXML
    private Label lblBD;

    @FXML
    private Label lblInstancia;

    @FXML
    private Label lblCODIFICACION;

    @FXML
    private TextField txtNomBD;

    @FXML
    private TextField txtDominio;

    @FXML
    private Label lblURL;

    @FXML
    private ComboBox<JCMBLinea> cmbBD;

    @FXML
    private Label lblPassWord;

    @FXML
    private TextField txtURL;

    @FXML
    private Label lblIP;

    @FXML
    private TextField txtCODIFICACION;    
    
    
    private JConexion moConexion;
    private JT2CONEXIONESModelo moConexionesControlador;
    private JFieldConTextField txtCODIFICACIONModelo;
    private JFieldConTextField txtDominioModelo;
    private JFieldConTextField txtIPModelo;
    private JFieldConTextField txtInstanciaModelo;
    private JFieldConTextField txtNomBDModelo;
    private JFieldConTextField txtPassWordModelo;
    private JFieldConTextField txtRutaModelo;
    private JFieldConTextField txtURLModelo;
    private JFieldConTextField txtUsuarioModelo;
    private JFieldConComboBox cmbBDModelo;
    
    public JPanelConexiones(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("JPanelConexiones.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        try{
            ponerTipoTextos();
        }catch(Exception e){
        }
        cmbBD.valueProperty().addListener(new ChangeListener<JCMBLinea>() {
            @Override
            public void changed(ObservableValue<? extends JCMBLinea> ov, JCMBLinea t, JCMBLinea t1) {
                cmbBDItemStateChanged();
            }
        });

        
        
        
    }
    
    

    public JConexion getConexion(){
        return moConexion;
    }
    public void setDatos(JConexion poConexion, JT2CONEXIONESModelo poConexiones) {
        moConexion = poConexion;
        moConexionesControlador = poConexiones;
    }
    public void setDatosAPelo(JConexion poConexion) throws Exception{
        moConexion = poConexion;
        rellenarPantalla();
        mostrarDatos();
    }
    
    public void setDatosYLeerConfig(JConexion poConexion) throws Exception{
        moConexion = poConexion;
        rellenarPantalla();
        try {
            moConexion.leerConfig();
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
        }
        mostrarDatos();
        
    }

    public void habilitarSegunEdicion() throws Exception {
    }

    public void ponerTipoTextos() throws Exception {
        txtDominioModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtIPModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtInstanciaModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtNomBDModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
//        txtPassWord.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtRutaModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtUsuarioModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtURLModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtCODIFICACIONModelo.setTipo(JTipoTextoEstandar.mclTextCadena);
    }

    public void rellenarPantalla(){
        txtCODIFICACIONModelo =  new JFieldConTextField(txtCODIFICACION,false);
        txtDominioModelo =  new JFieldConTextField(txtDominio,false);
        txtIPModelo =  new JFieldConTextField(txtIP,false);
        txtInstanciaModelo =  new JFieldConTextField(txtInstancia,false);
        txtNomBDModelo =  new JFieldConTextField(txtNomBD,false);
        txtPassWordModelo =  new JFieldConTextField(txtPassWord,false);
        txtRutaModelo =  new JFieldConTextField(txtRuta,false);
        txtURLModelo =  new JFieldConTextField(txtURL,false);
        txtUsuarioModelo =  new JFieldConTextField(txtUsuario,false);
        cmbBDModelo =  new JFieldConComboBox(cmbBD,false);
        
        cmbBDModelo.borrarTodo();
        cmbBDModelo.addLinea("Access ODBC", String.valueOf(JConexion.mclAccessODBC) + JFilaDatosDefecto.mcsSeparacion1 );
        cmbBDModelo.addLinea("Access Con Ruta", String.valueOf(JConexion.mclAccessConRuta) + JFilaDatosDefecto.mcsSeparacion1 );
        cmbBDModelo.addLinea("PostGreSql", String.valueOf(JConexion.mclPostGreSQL) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("Oracle ODBC", String.valueOf(JConexion.mclOracleODBC) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("SqlServer", String.valueOf(JConexion.mclSQLSERVER) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("SqlServer Microsoft", String.valueOf(JConexion.mclSQLSERVERMicrosoft) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("mySQL", String.valueOf(JConexion.mclmySQL) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("FireBird", String.valueOf(JConexion.mclFireBird) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("Internet", String.valueOf(JConexion.mclInternet) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("Internet Comprimido", String.valueOf(JConexion.mclInternetComprimido) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("Internet Comprimido IO", String.valueOf(JConexion.mclInternetComprimidoIO) + JFilaDatosDefecto.mcsSeparacion1);
        cmbBDModelo.addLinea("DBASE", String.valueOf(JConexion.mclDBASE) + JFilaDatosDefecto.mcsSeparacion1);
    }
    public JSTabla getTabla() {
        return null;
    }

    public void mostrarDatos() throws Exception {
        txtDominioModelo.setValueTabla(moConexion.msPantDominio );
        txtIPModelo.setValueTabla(moConexion.msPantIP );
        txtInstanciaModelo.setValueTabla(moConexion.msPantInstancia );
        txtNomBDModelo.setValueTabla(moConexion.msPantNombreBD );
        txtPassWordModelo.setText(moConexion.msPantPASSWORD );
        txtRutaModelo.setValueTabla(moConexion.msPantRuta );
        txtUsuarioModelo.setValueTabla(moConexion.msPantUSUARIO );
        txtURLModelo.setValueTabla(moConexion.msPantURL);
        txtCODIFICACIONModelo.setValueTabla(moConexion.msPANTCODIFICACION);
        cmbBDModelo.setValueTabla(String.valueOf(moConexion.mlPantTipoConexion) + JFilaDatosDefecto.mcsSeparacion1);
    }

    public void establecerDatos() throws Exception {
        moConexion.msPantDominio = txtDominioModelo.getText();
        moConexion.msPantIP = txtIPModelo.getText();
        moConexion.msPantInstancia = txtInstanciaModelo.getText();
        moConexion.msPantNombreBD = txtNomBDModelo.getText();
        moConexion.msPantPASSWORD = txtPassWordModelo.getText();
        moConexion.msPantRuta = txtRutaModelo.getText();
        moConexion.msPantUSUARIO = txtUsuarioModelo.getText();
        moConexion.msPantURL = txtURLModelo.getText();
        moConexion.msPANTCODIFICACION=txtCODIFICACIONModelo.getText();

        moConexion.mlPantTipoConexion = Integer.valueOf(cmbBDModelo.getFilaActual().msCampo(0)).intValue();
    }


    public Rectangulo getTanano() {
        return new Rectangulo(300, 400);
    }

    public String getTitulo() {
        return "Conexion";
    }
    
    public void cancelar() throws Exception {
        moConexion.mbCancelado=true;
    }

    public void aceptar()  throws Exception {
        moConexion.mbCancelado=false;
        moConexion.guardarConfig();
        if(moConexionesControlador!=null){
            moConexionesControlador.addConexion(moConexion);
        }
    }

    public Connection probar() throws Exception {
        establecerDatos();
        moConexion.guardarConfig();
        return moConexion.probar();
    }
    private void cmbBDItemStateChanged() {                                       
        try{
            String lsLinea = cmbBDModelo.getFilaActual().msCampo(0);
            boolean[] labResult = moConexion.getVisibles((int)JConversiones.cdbl(lsLinea));
            
            txtDominio.setVisible(labResult[JConexion.mclCampoDominio]);
            lblDominio.setVisible(labResult[JConexion.mclCampoDominio]);
            
            txtIP.setVisible(labResult[JConexion.mclCampoIP]);
            lblIP.setVisible(labResult[JConexion.mclCampoIP]);
            
            txtInstancia.setVisible(labResult[JConexion.mclCampoInstancia]);
            lblInstancia.setVisible(labResult[JConexion.mclCampoInstancia]);
            
            txtNomBD.setVisible(labResult[JConexion.mclCampoNombre]);
            lblNomBD.setVisible(labResult[JConexion.mclCampoNombre]);
            
            txtPassWord.setVisible(labResult[JConexion.mclCampoPassWord]);
            lblPassWord.setVisible(labResult[JConexion.mclCampoPassWord]);
            
            txtUsuario.setVisible(labResult[JConexion.mclCampoUSUARIO]);
            lblUsuario.setVisible(labResult[JConexion.mclCampoUSUARIO]);

            txtURL.setVisible(labResult[JConexion.mclCampoURL]);
            lblURL.setVisible(labResult[JConexion.mclCampoURL]);

            txtCODIFICACION.setVisible(labResult[JConexion.mclCampoCODIFICACION]);
            lblCODIFICACION.setVisible(labResult[JConexion.mclCampoCODIFICACION]);
            
            lblRuta.setVisible(labResult[JConexion.mclCampoRuta]);
            txtRuta.setVisible(labResult[JConexion.mclCampoRuta]);
            
            
        }catch(Exception e){
            JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }         
}
