/*
* JPanelGUIXAVISOS.java
*
* Creado el 13/9/2017
*/

package utilesFXAvisos.forms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import utilesFX.*;
import utilesFX.formsGenericos.edicion.*;
import utilesGUIx.*;
import utilesGUIx.formsGenericos.IPanelControlador;
import ListDatos.*;
import org.controlsfx.control.textfield.CustomTextField;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;


public class JPanelGUIXAVISOS extends JPanelGENERALBASE {


    private JTEEGUIXAVISOS moGUIXAVISOS;
    private JDatosGenerales moDatosGenerales;

    @FXML
    private Label lblFECHACONCRETA;

    @FXML
    private CustomTextField txtFECHACONCRETA;

    @FXML
    private CheckBox jCheckPANTALLASN;

    @FXML
    private Label lblTELF;

    @FXML
    private CustomTextField txtTELF;

    @FXML
    private Label lblSENDER;

    @FXML
    private CustomTextField txtSENDER;

    @FXML
    private Label lblEMAIL;

    @FXML
    private CustomTextField txtEMAIL;

    @FXML
    private CheckBox jCheckAVISADOSN;

    /** Creates new form JPanelGUIXAVISOS*/
    public JPanelGUIXAVISOS() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelGUIXAVISOS.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }             
    }


    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXAVISOS poGUIXAVISOS, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXAVISOS = poGUIXAVISOS;
        setDatos(poPadre) ;
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;
        
        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }
    
    @Override
    public String getTitulo() {
        String lsResult;
        if(moGUIXAVISOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXAVISOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXAVISOS.msCTabla)  + 
                moGUIXAVISOS.getCODIGO().getString();
        }
        return lsResult;
    }


    @Override
    public JSTabla getTabla(){
        return moGUIXAVISOS;
    }

    @Override
    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblFECHACONCRETA.setText(moGUIXAVISOS.getFECHACONCRETA().getCaption());
        addFieldControl(new JFieldControl(txtFECHACONCRETA,moGUIXAVISOS.getFECHACONCRETA()));
        jCheckPANTALLASN.setText(moGUIXAVISOS.getPANTALLASN().getCaption());
        addFieldControl(new JFieldControl(jCheckPANTALLASN,moGUIXAVISOS.getPANTALLASN()));
        lblTELF.setText(moGUIXAVISOS.getTELF().getCaption());
        addFieldControl(new JFieldControl(txtTELF,moGUIXAVISOS.getTELF()));
        lblSENDER.setText(moGUIXAVISOS.getSENDER().getCaption());
        addFieldControl(new JFieldControl(txtSENDER,moGUIXAVISOS.getSENDER()));
        lblEMAIL.setText(moGUIXAVISOS.getEMAIL().getCaption());
        addFieldControl(new JFieldControl(txtEMAIL,moGUIXAVISOS.getEMAIL()));
        jCheckAVISADOSN.setText(moGUIXAVISOS.getAVISADOSN().getCaption());
        addFieldControl(new JFieldControl(jCheckAVISADOSN,moGUIXAVISOS.getAVISADOSN()));
        
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
//        if(moGUIXAVISOS.moList.getModoTabla() == JListDatos.mclNuevo) {
//            jPanelCALENDARIO.setDisable(false);
//            txtCODIGOEVENTO.setDisable(false);
//            txtCODIGO.setDisable(false);
//        }else{
//            jPanelCALENDARIO.setDisable(true);
//            txtCODIGOEVENTO.setDisable(true);
//            txtCODIGO.setDisable(true);
//        }


        lblTELF.setVisible(moDatosGenerales.isSMS());
        txtTELF.setVisible(moDatosGenerales.isSMS());
        lblSENDER.setVisible(moDatosGenerales.isSMS());
        txtSENDER.setVisible(moDatosGenerales.isSMS());
        lblEMAIL.setVisible(moDatosGenerales.iseMail());
        txtEMAIL.setVisible(moDatosGenerales.iseMail());
        
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
        super.mostrarDatos();

    }

    @Override
    public void establecerDatos() throws Exception {
        super.establecerDatos();
        moGUIXAVISOS.validarCampos();
    }

    @Override
    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moGUIXAVISOS.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 470);
    }

}
