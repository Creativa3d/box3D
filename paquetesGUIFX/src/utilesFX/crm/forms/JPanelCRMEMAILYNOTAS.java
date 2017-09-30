/*
* JPanelCRMEMAILYNOTAS.java
*
* Creado el 27/4/2017
*/

package utilesFX.crm.forms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import utilesFX.*;
import utilesFX.formsGenericos.edicion.*;
import utilesGUIx.*;
import utilesGUIx.formsGenericos.IPanelControlador;
import ListDatos.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import org.controlsfx.control.textfield.CustomTextField;
import utiles.JComunicacion;


import utilesCRM.tablasExtend.JTEECRMEMAILYNOTAS;
import utilesFX.msgbox.JMsgBox;
import utilesFXAvisos.forms.JPanelGUIXEVENTOS;
import utilesFXAvisos.forms.JPanelMensajeFX;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

public class JPanelCRMEMAILYNOTAS extends JPanelGENERALBASE {

    private JTEECRMEMAILYNOTAS moCRMEMAILYNOTAS;

    @FXML
    private TabPane jTabbedPane1;

    @FXML
    private Label lblASUNTO;

    @FXML
    private CustomTextField txtASUNTO;

    @FXML
    private Label lblDESCRIPCION;

    private HTMLEditor txtDESCRIPCION;

    @FXML
    private Label lblCODIGOUSUARIO;

    @FXML
    private CustomTextField txtCODIGOUSUARIO;

    @FXML
    private Label lblTIPO;

    @FXML
    private CustomTextField txtTIPO;

    @FXML
    private Button btnVerRelacion;
    @FXML
    private BorderPane borderPane;
    /** Creates new form JPanelCRMEMAILYNOTAS*/
    public JPanelCRMEMAILYNOTAS() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelCRMEMAILYNOTAS.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }             
        txtDESCRIPCION=new HTMLEditor();
        borderPane.setCenter(txtDESCRIPCION);
        jTabbedPane1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                jTabbedPane1StateChanged();
            }
        });
        
        btnVerRelacion.setOnAction((ActionEvent event) -> {
            try {
                if(!moCRMEMAILYNOTAS.getCODIGOEVENTO().isVacio()){
                    JTEEGUIXEVENTOS loEvent = JTEEGUIXEVENTOS.getTabla(
                            moCRMEMAILYNOTAS.getCODIGOCALENDARIO().getString()
                            , moCRMEMAILYNOTAS.getCODIGOEVENTO().getString()
                            , moCRMEMAILYNOTAS.moList.moServidor);
                    JPanelGUIXEVENTOS loPanel = new JPanelGUIXEVENTOS();
                    loPanel.setDatos(
                            JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCalendario()
                            , loEvent, null, null);
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
                } else  if(!moCRMEMAILYNOTAS.getGUIXMENSAJESSENDCOD().isVacio()){
                    JTEEGUIXMENSAJESBD loEvent = JTEEGUIXMENSAJESBD.getTabla(
                            moCRMEMAILYNOTAS.getGUIXMENSAJESSENDCOD().getString()
                            , moCRMEMAILYNOTAS.moList.moServidor);
                    JMensaje loMensaje= loEvent.getMensaje();
                    loMensaje.getAtributos().put(moCRMEMAILYNOTAS.getCODIGOCONTACTONombre(), moCRMEMAILYNOTAS.getCODIGOCONTACTO().getString());
                    loMensaje.setUsuario(moCRMEMAILYNOTAS.getCODIGOUSUARIO().getString());
                    loMensaje.setGrupo(moCRMEMAILYNOTAS.getCODIGONEGOCIACION().getString());

                    JPanelMensajeFX loPanel = new JPanelMensajeFX();
                    loPanel.setDatos(loMensaje, null, JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().getPathPlantilla(), null, null, true);
                    
                            
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
                } else {
                    JMsgBox.mensajeInformacion(null, "No hay datos que mostrar");
                }
            } catch (Exception ex) {
                JMsgBox.mensajeErrorYLog(this, ex);
            }
        });
    }

    public void setDatos(final JTEECRMEMAILYNOTAS poCRMEMAILYNOTAS, final IPanelControlador poPadre) throws Exception {
        moCRMEMAILYNOTAS = poCRMEMAILYNOTAS;
        setDatos(poPadre);
    }

    @Override
    public String getTitulo() {
        String lsResult;
        if(moCRMEMAILYNOTAS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JFXConfigGlobal.getInstancia().getTextosForms().getTexto(JTEECRMEMAILYNOTAS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=JFXConfigGlobal.getInstancia().getTextosForms().getTexto(JTEECRMEMAILYNOTAS.msCTabla)  + 
                moCRMEMAILYNOTAS.getCODIGONOTA().getString();
        }
        return lsResult;
    }

    @Override
    public JSTabla getTabla(){
        return moCRMEMAILYNOTAS;
    }

    @Override
    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label

        lblCODIGOUSUARIO.setText(moCRMEMAILYNOTAS.getCODIGOUSUARIO().getCaption());
        addFieldControl(new JFieldControl(txtCODIGOUSUARIO,moCRMEMAILYNOTAS.getCODIGOUSUARIO()));

        lblTIPO.setText(moCRMEMAILYNOTAS.getTIPO().getCaption());
        addFieldControl(new JFieldControl(txtTIPO,moCRMEMAILYNOTAS.getTIPO()));
        lblASUNTO.setText(moCRMEMAILYNOTAS.getASUNTO().getCaption());
        addFieldControl(new JFieldControl(txtASUNTO,moCRMEMAILYNOTAS.getASUNTO()));
        lblDESCRIPCION.setText(moCRMEMAILYNOTAS.getDESCRIPCION().getCaption());
        addFieldControl(new JFieldConHTMLEditor(txtDESCRIPCION,moCRMEMAILYNOTAS.getDESCRIPCION()));

    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
        txtTIPO.setDisable(true);
        txtCODIGOUSUARIO.setDisable(true);
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
        super.mostrarDatos();

        if(moCRMEMAILYNOTAS.getTIPO().getString().equalsIgnoreCase(moCRMEMAILYNOTAS.mcsCorreo)){
            btnVerRelacion.setText("Ver correo");
        } else if(moCRMEMAILYNOTAS.getTIPO().getString().equalsIgnoreCase(moCRMEMAILYNOTAS.mcsTarea)){
            btnVerRelacion.setText("Ver tarea");
        } else {
            btnVerRelacion.setVisible(false);
        }

        jTabbedPane1StateChanged();

    }

    @Override
    public void establecerDatos() throws Exception {
        super.establecerDatos();
        moCRMEMAILYNOTAS.validarCampos();
    }

    @Override
    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moCRMEMAILYNOTAS.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 800, 600);
    }

    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        super.setBloqueoControles(pbBloqueo);
   }
    private void jTabbedPane1StateChanged() {
        try{
//            if(jTabbedPane1.getSelectionModel().getSelectedIndex()>=0){
//                switch(jTabbedPane1.getSelectionModel().getSelectedIndex()){
//                    case 0://General
//                        break;
//                    case 1://CRMNEGOCIACIONESCASOSCODIGONOTA
//                        compruebaPK();
//                        jPanelGenericoCRMNEGOCIACIONESCASOSCODIGONOTA.setControlador(moCRMEMAILYNOTAS.getControlador(JTCRMNEGOCIACIONESCASOS.msCTabla, moPadre.getParametros().getMostrarPantalla()));
//                        break;
//                }
//            }
        }catch(Exception e){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }
}
