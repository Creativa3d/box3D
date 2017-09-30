/*
* JPanelMUNICIPIOS.java
*
* Creado el 31/10/2016
*/

package paquetesguifx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import utilesFX.*;
import utilesFX.formsGenericos.edicion.*;
import utilesFX.panelesGenericos.*;
import utilesFX.formsGenericos.JPanelGenerico;
import utilesGUIx.*;
import utilesGUIx.formsGenericos.IPanelControlador;
import utiles.JDepuracion;
import ListDatos.*;
import org.controlsfx.control.textfield.CustomTextField;


public class JPanelMUNICIPIOS extends JPanelGENERALBASE {

    private JTEEMUNICIPIOS moMUNICIPIOS;

    @FXML private Label lblCODPROV;
    @FXML private CustomTextField txtCODPROV;
    @FXML private Label lblCODHACIENDA;
    @FXML private CustomTextField txtCODHACIENDA;
    @FXML private Label lblMUNICIPIO;
    @FXML private CustomTextField txtMUNICIPIO;
    @FXML private Label lblCPM;
    @FXML private CustomTextField txtCPM;
    @FXML private Label lblESCAPITALSN;
    @FXML private CustomTextField txtESCAPITALSN;
    @FXML private Label lblFECHAACT;
    @FXML private CustomTextField txtFECHAACT;
    @FXML private Label lblCODIGOINE;
    @FXML private CustomTextField txtCODIGOINE;
    /** Creates new form JPanelMUNICIPIOS*/
    public JPanelMUNICIPIOS() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelMUNICIPIOS.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }             
        getParametros().setComponenteDefecto(txtCODPROV);
    }

    public void setDatos(final JTEEMUNICIPIOS poMUNICIPIOS, final IPanelControlador poPadre) throws Exception {
        moMUNICIPIOS = poMUNICIPIOS;
        setDatos(poPadre);
    }

    public String getTitulo() {
        String lsResult;
        if(moMUNICIPIOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JTEEMUNICIPIOS.msCTabla + " [Nuevo]" ;
        }else{
            lsResult=JTEEMUNICIPIOS.msCTabla;
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moMUNICIPIOS;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODPROV.setText(moMUNICIPIOS.getCODPROV().getCaption());
        addFieldControl(new JFieldConTextField(txtCODPROV,moMUNICIPIOS.getCODPROV()));
        lblCODHACIENDA.setText(moMUNICIPIOS.getCODHACIENDA().getCaption());
        addFieldControl(new JFieldConTextField(txtCODHACIENDA,moMUNICIPIOS.getCODHACIENDA()));
        lblMUNICIPIO.setText(moMUNICIPIOS.getMUNICIPIO().getCaption());
        addFieldControl(new JFieldConTextField(txtMUNICIPIO,moMUNICIPIOS.getMUNICIPIO()));
        lblCPM.setText(moMUNICIPIOS.getCPM().getCaption());
        addFieldControl(new JFieldConTextField(txtCPM,moMUNICIPIOS.getCPM()));
        lblESCAPITALSN.setText(moMUNICIPIOS.getESCAPITALSN().getCaption());
        addFieldControl(new JFieldConTextField(txtESCAPITALSN,moMUNICIPIOS.getESCAPITALSN()));
        lblFECHAACT.setText(moMUNICIPIOS.getFECHAACT().getCaption());
        addFieldControl(new JFieldConTextField(txtFECHAACT,moMUNICIPIOS.getFECHAACT()));
        lblCODIGOINE.setText(moMUNICIPIOS.getCODIGOINE().getCaption());
        addFieldControl(new JFieldConTextField(txtCODIGOINE,moMUNICIPIOS.getCODIGOINE()));
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moMUNICIPIOS.moList.getModoTabla() == JListDatos.mclNuevo) {
        }else{
        }
    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        super.mostrarDatos();

    }

    public void establecerDatos() throws Exception {
        super.establecerDatos();
        moMUNICIPIOS.validarCampos();
    }
    public void recuperarDatos() throws Exception {
        JTEEMUNICIPIOS loTabla = JTEEMUNICIPIOS.getTabla(moListDatos.getFields().masCamposPrincipales()[0]
                , moListDatos.getFields().masCamposPrincipales()[1]
                , moMUNICIPIOS.moList.moServidor);
        moMUNICIPIOS.moList.clear();
        moMUNICIPIOS.moList.add(loTabla.moList.moFila());
        moMUNICIPIOS.moList.moveFirst();
                
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moMUNICIPIOS.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 400);
    }

}
