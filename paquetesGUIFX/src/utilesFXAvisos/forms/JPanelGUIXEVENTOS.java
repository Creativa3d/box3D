/*
* JPanelGUIXEVENTOS.java
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import org.controlsfx.control.textfield.CustomTextField;
import utiles.JComunicacion;
import utiles.JDateEdu;
import utilesFX.msgbox.JMsgBox;
import utilesFXAvisos.forms.util.JPanelOpcionesRepe;
import utilesFXAvisos.tablasControladoras.JT2GUIXAVISOS;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOSPRIORIDAD;


public class JPanelGUIXEVENTOS extends JPanelGENERALBASE {

    private JTEEGUIXEVENTOS moGUIXEVENTOS;
    private String msFechas;
    private String msTipoRepeOriginal;
    private JDatosGenerales moDatosGenerales;

    @FXML
    private TabPane jTabbedPane1;

    @FXML
    private CheckBox chkEVENTOSN;

    @FXML
    private JPanelBusquedaCombo jPanelCALENDARIO;

    @FXML
    private Label lblFECHADESDE;

    @FXML
    private CustomTextField txtFECHADESDE;

    @FXML
    private Label lblFECHAHASTA;

    @FXML
    private CustomTextField txtFECHAHASTA;

    @FXML
    private Label lblNOMBRE;

    @FXML
    private CustomTextField txtNOMBRE;

    @FXML
    private JPanelBusquedaCombo jPanelPRIORIDAD;

    @FXML
    private Label lblREPETICION;

    @FXML
    private ComboBox<JCMBLinea> cmbREPE_TIPO;

    @FXML
    private CustomTextField txtNUMERO;

    @FXML
    private JPanelBusquedaCombo jPanelBusquedaUsuario;

    @FXML
    private JPanelBusquedaCombo jPanelBusquedaUsuarioAsignado;

    @FXML
    private Button btnDatosRelacionados;

    @FXML
    private Label lblGRUPO;

    @FXML
    private CustomTextField txtGRUPO;

    @FXML
    private Label lblTEXTO;

    @FXML
    private TextArea txtTEXTO;

    @FXML
    private JPanelGenerico jPanelGenericoGUIXAVISOSCALENDARIOCODIGO;
        @FXML
    private Label lblColor;
    
    private JFieldConComboBox cmbREPE_TIPOModelo;
    private JFieldConTextField txtNUMEROModelo;
    /** Creates new form JPanelGUIXEVENTOS*/
    public JPanelGUIXEVENTOS() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelGUIXEVENTOS.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }             
        jTabbedPane1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                jTabbedPane1StateChanged();
            }
        });
        txtFECHADESDE.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtFECHADESDEFocusLost();
                }
            }
        });
        btnDatosRelacionados.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnDatosRelacionadosActionPerformed();
            }
        });
        
        cmbREPE_TIPO.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<JCMBLinea>() {
            @Override
            public void changed(ObservableValue<? extends JCMBLinea> observable, JCMBLinea oldValue, JCMBLinea newValue) {
                cmbREPE_TIPOItemStateChanged();
            }
        }
        );
        
        jPanelPRIORIDAD.addFocusListenerText(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                    jPanelBusquedaPrioridadFocusGained();
                    
            }
        });
        jPanelPRIORIDAD.addItemListener(new ChangeListener<JCMBLinea>() {
            @Override
            public void changed(ObservableValue<? extends JCMBLinea> observable, JCMBLinea oldValue, JCMBLinea newValue) {
                jPanelBusquedaPrioridadItemStateChanged();
            }
        });
        
    }

    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXEVENTOS poGUIXEVENTOS, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXEVENTOS = poGUIXEVENTOS;
        setDatos(poPadre);
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;
        
        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }

    @Override
    public String getTitulo() {
        String lsResult;
        if(moGUIXEVENTOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOS.msCTabla) + " " + 
                moGUIXEVENTOS.getCODIGO().getString() + " " + moGUIXEVENTOS.getNOMBRE().getString();
        }
        return lsResult;
    }


    @Override
    public JSTabla getTabla(){
        return moGUIXEVENTOS;
    }

    @Override
    public void rellenarPantalla() throws Exception {
        jPanelPRIORIDAD.setDatos(JTEEGUIXEVENTOSPRIORIDAD.getParamPanelBusq(moDatosGenerales));
        jPanelCALENDARIO.setDatos(JTEEGUIXCALENDARIO.getParamPanelBusq(moDatosGenerales));
        
        
        if(JGUIxConfigGlobalModelo.getInstancia().getPlugInSeguridad()==null){
            jPanelBusquedaUsuarioAsignado.setVisible(false);
        } else {
            JTPlugInUsuarios loUsu = JGUIxConfigGlobalModelo.getInstancia().getPlugInSeguridad().getUsuarios();
            utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
            loParam.mlCamposPrincipales = loUsu.moList.getFields().malCamposPrincipales();
            loParam.masTextosDescripciones = null;
            loParam.mbConDatos=true;
            loParam.mbMensajeSiNoExiste=true;
            loParam.moTabla=loUsu;
            loParam.malDescripciones = new int[]{
                loUsu.lPosiNOMBRE, loUsu.lPosiNOMBRECOMPLETO
            };
            jPanelBusquedaUsuarioAsignado.setDatos(loParam);
            jPanelBusquedaUsuario.setDatos(loParam);
            jPanelBusquedaUsuarioAsignado.setLabel(moGUIXEVENTOS.getUSUARIOASIGNADO().getCaption());
            jPanelBusquedaUsuarioAsignado.setField(moGUIXEVENTOS.getUSUARIOASIGNADO());
            addFieldControl(jPanelBusquedaUsuarioAsignado);
            jPanelBusquedaUsuario.setLabel(moGUIXEVENTOS.getUSUARIO().getCaption());
            jPanelBusquedaUsuario.setField(moGUIXEVENTOS.getUSUARIO());
            addFieldControl(jPanelBusquedaUsuario);
        }
        
        cmbREPE_TIPOModelo = new JFieldConComboBox(cmbREPE_TIPO);
        cmbREPE_TIPOModelo.borrarTodo();
        cmbREPE_TIPOModelo.addLinea("Evento único", JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPOModelo.addLinea("Diario", JTEEGUIXEVENTOS.mcsRepeticionesTipoDIA + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPOModelo.addLinea("Semanal", JTEEGUIXEVENTOS.mcsRepeticionesTipoSEMANAS + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPOModelo.addLinea("Mesual", JTEEGUIXEVENTOS.mcsRepeticionesTipoMESES + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPOModelo.addLinea("Anual", JTEEGUIXEVENTOS.mcsRepeticionesTipoANYO + JFilaDatosDefecto.mcsSeparacion1);
        
        txtNUMEROModelo = new JFieldConTextField(txtNUMERO);

        //ponemos los textos a los label
        jPanelCALENDARIO.setLabel(moGUIXEVENTOS.getCALENDARIO().getCaption());
        jPanelCALENDARIO.setField(moGUIXEVENTOS.getCALENDARIO());
        addFieldControl(jPanelCALENDARIO);
        lblFECHADESDE.setText(moGUIXEVENTOS.getFECHADESDE().getCaption());
        addFieldControl(new JFieldControl(txtFECHADESDE,moGUIXEVENTOS.getFECHADESDE()));
        lblFECHAHASTA.setText(moGUIXEVENTOS.getFECHAHASTA().getCaption());
        addFieldControl(new JFieldControl(txtFECHAHASTA,moGUIXEVENTOS.getFECHAHASTA()));
        lblNOMBRE.setText(moGUIXEVENTOS.getNOMBRE().getCaption());
        addFieldControl(new JFieldControl(txtNOMBRE,moGUIXEVENTOS.getNOMBRE()));
        lblTEXTO.setText(moGUIXEVENTOS.getTEXTO().getCaption());
        addFieldControl(new JFieldControl(txtTEXTO,moGUIXEVENTOS.getTEXTO()));
        lblREPETICION.setText(moGUIXEVENTOS.getREPETICION().getCaption());
        lblGRUPO.setText(moGUIXEVENTOS.getGRUPO().getCaption());
        addFieldControl(new JFieldControl(txtGRUPO,moGUIXEVENTOS.getGRUPO()));

        chkEVENTOSN.setText(moGUIXEVENTOS.getEVENTOSN().getCaption());
        addFieldControl(new JFieldControl(chkEVENTOSN,moGUIXEVENTOS.getEVENTOSN()));


        jPanelPRIORIDAD.setLabel(moGUIXEVENTOS.getPRIORIDAD().getCaption());
        jPanelPRIORIDAD.setField(moGUIXEVENTOS.getPRIORIDAD());
        addFieldControl(jPanelPRIORIDAD);
        jPanelCALENDARIO.setLabel(moGUIXEVENTOS.getCALENDARIO().getCaption());
        jPanelCALENDARIO.setField(moGUIXEVENTOS.getCALENDARIO());
        addFieldControl(jPanelCALENDARIO);

//        txtNUMERO.setLongitudTextoMaxima(2);
        
        

    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
        if(moGUIXEVENTOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            jPanelCALENDARIO.setDisable(false);
        }else{
            jPanelCALENDARIO.setDisable(true);
        }
        
            txtGRUPO.setVisible(false);
            lblGRUPO.setVisible(false);
//        }
            btnDatosRelacionados.setVisible(
                    moDatosGenerales.isDatosRelacionados()
                    && !moGUIXEVENTOS.getGRUPO().isVacio());
            chkEVENTOSN.setVisible(moGUIXEVENTOS.getList().getModoTabla()!=JListDatos.mclNuevo);
            jPanelBusquedaUsuario.setDisable(true);        
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
        super.mostrarDatos();
        msFechas = moGUIXEVENTOS.getFECHADESDE().getString()+moGUIXEVENTOS.getFECHAHASTA().getString();
        msTipoRepeOriginal = moGUIXEVENTOS.getRepeticionesTipo();
       
        cmbREPE_TIPOModelo.setValueTabla(msTipoRepeOriginal+JFilaDatosDefecto.mcsSeparacion1);
        txtNUMEROModelo.setValueTabla(String.valueOf(moGUIXEVENTOS.getRepeticionesNumero()));
        
        jPanelBusquedaPrioridadItemStateChanged();
        jTabbedPane1StateChanged();

    }

    @Override
    public void establecerDatos() throws Exception {
        super.establecerDatos();
        String lsFechas = moGUIXEVENTOS.getFECHADESDE().getString()+moGUIXEVENTOS.getFECHAHASTA().getString();
        if(!lsFechas.equals(msFechas)) {
            moGUIXEVENTOS.getEVENTOSN().setValue(false);
        }
        moGUIXEVENTOS.setRepeticiones(cmbREPE_TIPOModelo.getFilaActual().msCampo(0), txtNUMERO.getText());
        moGUIXEVENTOS.validarCampos();
    }

    @Override
    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        if(!moGUIXEVENTOS.getRepeticionesTipo().equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO)
             || (!msTipoRepeOriginal.equals("") && !msTipoRepeOriginal.equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO) )   ){
            int lTipo = JTEEGUIXEVENTOS.mclTodos;
            if(lModo!=JListDatos.mclNuevo){
                JPanelOpcionesRepe loPanel = new JPanelOpcionesRepe();
                JComunicacion loComu = new JComunicacion();
                loComu.moObjecto = String.valueOf(lTipo);
                loPanel.setDatos(moDatosGenerales, loComu, new CallBack<JComunicacion>() {
                    @Override
                    public void callBack(JComunicacion poComu) {
                        try{
                            int lTipo = Integer.valueOf(poComu.moObjecto.toString());
                            IResultado loResult=JTEEGUIXEVENTOS.procesarRepeticiones(moDatosGenerales, moGUIXEVENTOS, lTipo, moGUIXEVENTOS.moList.moServidor);
                            if(loResult.getBien()){
                                if(moPadre!=null){
                                    moPadre.refrescar();
                                }
                            }else{
                                throw new Exception(loResult.getMensaje());
                            }   
                        }catch(Exception e){
                            JMsgBox.mensajeErrorYLog(JPanelGUIXEVENTOS.this, e);
                        }
                    }
                });
                moDatosGenerales.getMostrarPantalla().mostrarForm(new JMostrarPantallaParam(loPanel, 400, 400, JMostrarPantallaParam.mclEdicionFrame, "Opciones"));
                
            } else {
                IResultado loResult=JTEEGUIXEVENTOS.procesarRepeticiones(moDatosGenerales, moGUIXEVENTOS, lTipo, moGUIXEVENTOS.moList.moServidor);
                if(loResult.getBien()){
                    moPadre.refrescar();
                }else{
                    throw new Exception(loResult.getMensaje());
                }
                
            }
        }else{
            IResultado loResult=moGUIXEVENTOS.guardar(moDatosGenerales);
            if(loResult.getBien()){
                 actualizarPadre(lModo);
            }else{
                throw new Exception(loResult.getMensaje());
            }
        }
    }

    @Override
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 600);
    }

    private void compruebaPK() throws Exception {
        if(
            jPanelCALENDARIO.getText().compareTo("") == 0 
          ) {
            jTabbedPane1.getSelectionModel().select(0);
            throw new Exception("Es necesario guardar datos antes de continuar");
        }
    }
    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        super.setBloqueoControles(pbBloqueo);
        setBloqueoControlesContainer(jPanelGenericoGUIXAVISOSCALENDARIOCODIGO,false);
   }

    private void jPanelBusquedaPrioridadItemStateChanged() {                                                         
        try{
       
            JTEEGUIXEVENTOSPRIORIDAD loPri = JTEEGUIXEVENTOSPRIORIDAD.getTabla(jPanelPRIORIDAD.getText(), moGUIXEVENTOS.moList.moServidor);
            if(loPri.moveFirst()){
                
                lblColor.setBackground(new Background(new BackgroundFill(
                        JFXConfigGlobal.toColor(new ColorCZ(loPri.getCOLOR().getInteger()))
                        , CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }catch(Exception e){
            
        }
    }                                                        
    private void jPanelBusquedaPrioridadFocusGained() {                                                    
        jPanelBusquedaPrioridadItemStateChanged();
    }                                                   

    private void txtFECHADESDEFocusLost() {                                        
        try{
            if(!JDateEdu.isDate(txtFECHAHASTA.getText() )
                    || new JDateEdu(txtFECHADESDE.getText()).compareTo(new JDateEdu(txtFECHAHASTA.getText()))>0
                    ){
               JDateEdu loDate = new JDateEdu(txtFECHADESDE.getText());
               loDate.add(loDate.mclHoras, 1);
               txtFECHAHASTA.setText(loDate.msFormatear("dd/MM/yyyy HH:mm"));                
            }
        }catch(Exception e){
            
        }
    }                                       


    private void btnDatosRelacionadosActionPerformed() {                                                     
        try{
            moDatosGenerales.mostrarDatosRelacionados(moGUIXEVENTOS);
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }
    }                                                    

    private void cmbREPE_TIPOItemStateChanged() {                                              
        try{
            txtNUMERO.setDisable(
                    cmbREPE_TIPOModelo.getFilaActual().msCampo(0).equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO)
                    );
            if(!txtNUMERO.isDisable()&& (txtNUMERO.getText().equals("") || txtNUMERO.getText().equals("1"))){
                txtNUMERO.setText(JTEEGUIXEVENTOS.mcsREPE_NUMERO_DEFECTO);
            }
            if(cmbREPE_TIPOModelo.getFilaActual().msCampo(0).equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO)){
                txtNUMERO.setText("1");
            }
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }

    }                                             
    
    private void jTabbedPane1StateChanged() {
        try{
            if(jTabbedPane1.getSelectionModel().getSelectedIndex()>=0){
                switch(jTabbedPane1.getSelectionModel().getSelectedIndex()){
                    case 0://General
                        break;
                    case 1://GUIXAVISOSCALENDARIOCODIGO
                        compruebaPK();
                        jPanelGenericoGUIXAVISOSCALENDARIOCODIGO.setControlador(new JT2GUIXAVISOS(moDatosGenerales, moGUIXEVENTOS.msCTabla, moGUIXEVENTOS.moList.moFila())
                                );
                        break;
                }
            }
        }catch(Exception e){
            utilesFX.JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }
}
