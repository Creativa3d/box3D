/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFXAvisos.forms;

import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utiles.IListaElementos;
import utiles.JArchivo;
import utiles.JCadenas;
import utiles.JComunicacion;
import utiles.JDepuracion;
import utilesFX.JCMBLinea;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.JTableViewCZ;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.edicion.JPanelGENERALBASE;
import utilesFX.msgbox.JMsgBox;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.Rectangulo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;
import utilesx.JEjecutar;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author eduardo
 */
public class JPanelMensajeFX  extends JPanelGENERALBASE {
    
    @FXML
    private Button btnAdjuntar;

    @FXML
    private Button btnAdjuntarPlantilla;

    @FXML
    private Button btnGuardarPlantilla;

    @FXML
    private Button btnCargarCampos;


    @FXML
    private Button btnResponder;
    @FXML
    private Button btnReenviar;

    @FXML
    private JTableViewCZ jTableAdjuntar;

    @FXML
    private ComboBox<JCMBLinea> cmbDe;

    @FXML
    private TextField txtEmailTo;

    @FXML
    private TextField txtCC;

    @FXML
    private TextField txtBCC;

    @FXML
    private TextField txtAsunto;
    
    @FXML
    private HTMLEditor htmlEditor1;
    
    
    @FXML
    private SplitPane jSplitPane1;
    
    
    protected JMensaje moMensaje;
    protected JComunicacion moComu;
    protected JListDatos moListAdjuntos;
    protected String msDirActual;
    protected String msPathPlantilla;
    private JGUIxAvisosDatosGenerales moDatosGenerales;
    private CallBack moCallback;
    private JFieldConComboBox mocmbDEField;
    private final WebView mWebView;
    private final ToolBar mTopToolBar;
    private final ToolBar mBottomToolBar;
    private boolean mbEnviarMensaje;
    private boolean mbPrimeraVez=true;
            
    /**
     * Creates new form JPanelMensaje
     */
    public JPanelMensajeFX() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFXAvisos/forms/JPanelMensajeFX.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        mWebView = (WebView)htmlEditor1.lookup(".web-view");
        mTopToolBar = (ToolBar) this.lookup(".top-toolbar");
        mBottomToolBar = (ToolBar) this.lookup(".bottom-toolbar");
        createCustomButtons();
        btnAdjuntar.setOnAction((ActionEvent event) -> {
            btnAdjuntarActionPerformed();
        });
        btnAdjuntarPlantilla.setOnAction((ActionEvent event) -> {
            btnAdjuntarPlantillaActionPerformed();
        });
        btnCargarCampos.setOnAction((ActionEvent event) -> {
            btnCargarCamposActionPerformed();
        });
        btnGuardarPlantilla.setOnAction((ActionEvent event) -> {
            btnGuardarPlantillaActionPerformed();
        });
        btnResponder.setOnAction((ActionEvent event) -> {
            btnResponderActionPerformed();
        });
        btnReenviar.setOnAction((ActionEvent event) -> {
            btnReenviarActionPerformed();
        });
        jTableAdjuntar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getClickCount() > 1) {
                jTableAdjuntarActionPerformed();
            }
        });
    }


    private void createCustomButtons() {

        ImageView graphic = new ImageView(new Image(
                getClass().getResourceAsStream("/utilesFX/images/inserimage.png")));
        Button mImportFileButton = new Button("", graphic);
        mImportFileButton.setTooltip(new Tooltip("Insertar fichero"));
        mImportFileButton.setOnAction((event) -> onImportFileButtonAction());
 
                
        mTopToolBar.getItems().add(mImportFileButton);
        
        
        graphic = new ImageView(new Image(
                getClass().getResourceAsStream("/utilesFX/images/Paste16.gif")));
        Button mPasteImage = new Button("Insertar imagen memoria", graphic);
        mPasteImage.setTooltip(new Tooltip("Imagen memoria"));
        mPasteImage.setOnAction((event) -> onImportImagenButtonAction());
 
        mTopToolBar.getItems().add(mPasteImage);
        
        mTopToolBar.getItems().add(new Separator(Orientation.VERTICAL));
    }
    private void onImportImagenButtonAction() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if(clipboard.hasImage()){
            try {
                Image image = clipboard.getImage();
                File  loFile = File.createTempFile("image", ".png");
                JFXConfigGlobal.saveToFile(image, "png", loFile);
                importDataFile(loFile);
            } catch (IOException ex) {
                Logger.getLogger(JPanelMensajeFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    private void onImportFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar un fichero a importar");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Todos", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            importDataFile(selectedFile);
        }
    }
        
    private void importDataFile(File file) {
        try {

            //get mime type of the file
            String type = java.nio.file.Files.probeContentType(file.toPath());
            //get html content
            byte[] data = JArchivo.getArchivoEnBytes(file.getAbsolutePath());
            String base64data = java.util.Base64.getEncoder().encodeToString(data);
            String htmlData =
                "<img alt=\"Embedded Image\" src=\"data:"+type+";base64,"+base64data+"\" />";

//            String htmlData = String.format(
//                    "<a href=\"\"></a>",
//                    type, base64data, type);
            //insert html
            insertHtmlAfterCursor(htmlData);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(this.getClass().getName(), ex);
        }
    }

    public void insertHtmlAfterCursor(String html) {
        
        html = html.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
        
        String script = String.format(
                "(function(html) {"
                + "  var sel, range;"
                + "  if (window.getSelection) {"
                + "    sel = window.getSelection();"
                + "    if (sel.getRangeAt && sel.rangeCount) {"
                + "      range = sel.getRangeAt(0);"
                + "      range.deleteContents();"
                + "      var el = document.createElement(\"div\");"
                + "      el.innerHTML = html;"
                + "      var frag = document.createDocumentFragment(),"
                + "        node, lastNode;"
                + "      while ((node = el.firstChild)) {"
                + "        lastNode = frag.appendChild(node);"
                + "      }"
                + "      range.insertNode(frag);"
                + "      if (lastNode) {"
                + "        range = range.cloneRange();"
                + "        range.setStartAfter(lastNode);"
                + "        range.collapse(true);"
                + "        sel.removeAllRanges();"
                + "        sel.addRange(range);"
                + "      }"
                + "    }"
                + "  }"
                + "  else if (document.selection && "
                + "           document.selection.type != \"Control\") {"
                + "    document.selection.createRange().pasteHTML(html);"
                + "  }"
                + "})(\"%s\");", html);
        
        mWebView.getEngine().executeScript(script);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu) throws Exception {
        setDatos(poMensaje, poComu, "", null, null, true);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu, String psPathPlantilla) throws Exception {
        setDatos(poMensaje, poComu, psPathPlantilla, null, null, true);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu, String psPathPlantilla, JGUIxAvisosDatosGenerales poDatosGenerales) throws Exception {
        setDatos(poMensaje, poComu, psPathPlantilla, poDatosGenerales, null, true);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu, String psPathPlantilla, JGUIxAvisosDatosGenerales poDatosGenerales, CallBack poCallback, boolean pbEnviarMensaje) throws Exception {
        moMensaje = poMensaje;
        moComu = poComu;
        msPathPlantilla = psPathPlantilla;
        moDatosGenerales=poDatosGenerales;
        moCallback=poCallback;
        mbEnviarMensaje=pbEnviarMensaje;

        if(moDatosGenerales==null){
            moDatosGenerales=JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos();
        }

    }
    

    @Override
    public String getTitulo() {
        return "Mensaje";
    }

    @Override
    public JSTabla getTabla() {
        return null;
    }

    @Override
    public void rellenarPantalla() throws Exception {
        mocmbDEField = new JFieldConComboBox(cmbDe);
        
        mocmbDEField.borrarTodo();
        if(moDatosGenerales==null){
            cmbDe.setVisible(false);
        } else {
            for(JGUIxAvisosCorreo loCorreo: moDatosGenerales.getListaCorreos()){
                mocmbDEField.addLinea(
                        loCorreo.getEnviar().getCorreoNombre()+ " <"+loCorreo.getEnviar().getCorreo() + ">"
                        , loCorreo.getIdentificador()+JFilaDatosDefecto.mcsSeparacion1);
            }   
        }
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
        setBloqueoControles( (getParametros() != null && getParametros().isSoloLectura()));
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    public String getDatosEmail(IListaElementos poEmail) throws Exception {
        
        StringBuffer lasEMailTO = new StringBuffer();

        for (int i = 0; i < poEmail.size(); i++) {
            if(JCadenas.isVacio(poEmail.get(i).toString())){
                poEmail.remove(i);
            }else{ 
                lasEMailTO.append(poEmail.get(i).toString());
                lasEMailTO.append(", ");
            }
        }
        return (lasEMailTO.toString());
    }
    @Override
    public void mostrarDatos() throws Exception {
        if(moMensaje.getCampos()==null || moMensaje.getCampos().isEmpty()){
            btnCargarCampos.setVisible(false);
        }        
        txtEmailTo.setText(getDatosEmail(moMensaje.getEmailTO()));
        txtCC.setText(getDatosEmail(moMensaje.getEmailCC()));
        txtBCC.setText(getDatosEmail(moMensaje.getEmailBCC()));
        
        moListAdjuntos = new JListDatos(null, "", new String[]{"Adjunto", "Fichero"}, new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena}, new int[]{1});

        for (int i = 0; i < moMensaje.getFicheroAdjunto().size(); i++) {
            moListAdjuntos.addNew();
            moListAdjuntos.getFields(0).setValue(moMensaje.getFicheroAdjunto().get(i));
            moListAdjuntos.getFields(1).setValue(moMensaje.getFicheroAdjunto().get(i));
            moListAdjuntos.update(false);
        }

        jTableAdjuntar.setModel((moListAdjuntos));
        jTableAdjuntar.setVisible(moListAdjuntos.size() > 0);
        if(jTableAdjuntar.isVisible()){
            jSplitPane1.setDividerPosition(0, 0.7);
        } else {
            jSplitPane1.setDividerPosition(0, 1);
        }
        ponerColumnaA0((TableColumn)jTableAdjuntar.getColumns().get(1));
        ((TableColumn)jTableAdjuntar.getColumns().get(0)).setPrefWidth(400);
        
        // Si existe una plantilla por defecto la adjuntamos
        if (!JCadenas.isVacio(msPathPlantilla)) {
            adjuntarPlantilla(msPathPlantilla);
        }
        if(!JCadenas.isVacio(moMensaje.getTexto())){
            htmlEditor1.setHtmlText(moMensaje.getTexto());
        }
        
        mWebView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mbPrimeraVez=false;
            }
        });
        mWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(!mbPrimeraVez){
                    Platform.runLater(() -> {
                      mWebView.getEngine().getLoadWorker().cancel();
                    });
                  }
                }
            }
        );

        mWebView.getEngine().locationProperty().addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            JEjecutar.abrirDocumento(newValue);
          }
        });
        
        txtAsunto.setText(moMensaje.getAsunto());

        if(JCadenas.isVacio(moMensaje.getIdentificadorEnvio())){
            mocmbDEField.mbSeleccionarClave(moDatosGenerales.getCorreoDefecto().getIdentificador()+JFilaDatosDefecto.mcsSeparacion1);
            if(!JCadenas.isVacio(moMensaje.getEmailFrom())){
                boolean lb=false;
                for(JGUIxAvisosCorreo loCorreo: moDatosGenerales.getListaCorreos()){
                    if(moMensaje.getEmailFrom().equalsIgnoreCase(loCorreo.getEnviar().getCorreo())){
                        mocmbDEField.mbSeleccionarClave(loCorreo.getIdentificador()+JFilaDatosDefecto.mcsSeparacion1);
                        lb=true;
                    }
                }
                if(!lb){
                    mocmbDEField.addLinea(moMensaje.getEmailFrom(), JFilaDatosDefecto.mcsSeparacion1);
                    mocmbDEField.mbSeleccionarClave(JFilaDatosDefecto.mcsSeparacion1);
                }
            }
        } else {
            mocmbDEField.mbSeleccionarClave(moMensaje.getIdentificadorEnvio()+JFilaDatosDefecto.mcsSeparacion1);
        }
    }

    private void adjuntarPlantilla(String pathPlantilla) throws Exception {
        File loPlantilla = new File(pathPlantilla);
        if (loPlantilla.exists()) {
            byte[] lbHtml = JArchivo.getArchivoEnBytes(pathPlantilla);
            htmlEditor1.setHtmlText(new String(lbHtml, "ISO-8859-1"));
        }
    }

    private void ponerColumnaA0(TableColumn loColumn) {
        loColumn.setMinWidth(0);
        loColumn.setVisible(false);
        loColumn.setMaxWidth(0);
        loColumn.setPrefWidth(0);

    }
 
    @Override
    public void establecerDatos() throws Exception {
        moMensaje.getEmailTO().clear();
        moMensaje.getEmailCC().clear();
        moMensaje.getEmailBCC().clear();
        
        String[] lasCorreo = JFilaDatosDefecto.moArrayDatos(txtEmailTo.getText()+",", ',');
        for (String lasCorreo1 : lasCorreo) {
            if (!lasCorreo1.trim().equals("")) {
                moMensaje.addEmailTO(lasCorreo1.trim());
            }
        }
        
        lasCorreo = JFilaDatosDefecto.moArrayDatos(txtCC.getText()+",", ',');
        for (String lasCorreo1 : lasCorreo) {
            if (!lasCorreo1.trim().equals("")) {
                moMensaje.addEmailCC(lasCorreo1.trim());
            }
        }
        
        lasCorreo = JFilaDatosDefecto.moArrayDatos(txtBCC.getText()+",", ',');
        for (String lasCorreo1 : lasCorreo) {
            if (!lasCorreo1.trim().equals("")) {
                moMensaje.addEmailBCC(lasCorreo1.trim());
            }
        }        
        moMensaje.getFicheroAdjunto().clear();
        if (moListAdjuntos.moveFirst()) {
            do {
                moMensaje.addFicheroAdjunto(moListAdjuntos.getFields(1).getString());
            } while (moListAdjuntos.moveNext());
        }
        
        
        String lsAsunto = txtAsunto.getText();
        String lsTexto = htmlEditor1.getHtmlText();
        if(moMensaje.getCampos()!=null){
            for (Map.Entry<String, Object> entry : moMensaje.getCampos().entrySet()) {

                String lsCampo = entry.getKey();
                if (lsAsunto.contains("[" + lsCampo + "]")) {
                    if (entry.getValue() != null) {
                        lsAsunto = lsAsunto.replace("[" + lsCampo + "]", entry.getValue().toString());
                    } else {
                        lsAsunto = lsAsunto.replace("[" + lsCampo + "]", "");
                    }
                }
                if (lsTexto.contains("[" + lsCampo + "]")) {
                    if (entry.getValue() != null) {
                        lsTexto = lsTexto.replace("[" + lsCampo + "]", entry.getValue().toString());
                    } else {
                        lsTexto = lsTexto.replace("[" + lsCampo + "]", "");
                    }
                }

            }
        }

        moMensaje.setAsunto(lsAsunto);
        moMensaje.setTexto(lsTexto);
        moMensaje.setIdentificadorEnvio(mocmbDEField.getFilaActual().msCampo(0));
        
        
    }

    @Override
    public void aceptar() throws Exception {
        if(moComu!=null){
            moComu.moObjecto = "1";
        }
        if(mbEnviarMensaje){
            if(JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup()==null){
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().inicializarEmail();
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().enviarEmail(moMensaje);
            } else {
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstracX() {
                    public String getTitulo() {
                        return "Enviando eMail";
                    }
                    public int getNumeroRegistros() {return -1;}
                    public void procesar() throws Throwable {
                        JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().inicializarEmail();
                        JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().enviarEmail(moMensaje);
                        if(moCallback!=null){
                            moCallback.callBack(this);
                        }                        
                    }
                    public String getTituloRegistroActual() {return "";}
                    public void mostrarMensaje(String psMensaje) {}

                    @Override
                    public void mostrarError(Throwable e) {
                        try {
                            //mostramos el error
                            super.mostrarError(e);
                            //recuperamos el mensaje para que lo vuelva a intentar
                            JPanelMensajeFX loPanel = new JPanelMensajeFX();
                            loPanel.setDatos(moMensaje, null, msPathPlantilla, moDatosGenerales, moCallback, true);
                            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
                        } catch (Exception ex) {
                            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(JPanelMensajeFX.this, e, null);
                        }
                        
                    }
                    
                });     
            }            
            
        }
        if(moCallback!=null){
            moCallback.callBack(this);
        }
    }

    @Override
    public void cancelar() throws Exception {
        if(moComu!=null){
            moComu.moObjecto = "0";
        }
        if(moCallback!=null){
            moCallback.callBack(this);
        }
    }

    @Override
    public Rectangulo getTanano() {
        return new Rectangulo(0, 0, 800, 600);
    }

    @Override
    public void setBloqueoControles(boolean pbBloqueo) throws Exception {
        
        boolean lb = pbBloqueo || (getParametros() != null && getParametros().isSoloLectura());
        txtAsunto.setDisable(lb);
        txtBCC.setDisable(lb);
        txtCC.setDisable(lb);
        txtEmailTo.setDisable(lb);
        btnAdjuntar.setDisable(lb);
        btnAdjuntarPlantilla.setDisable(lb);
        btnCargarCampos.setDisable(lb);
        cmbDe.setDisable(lb);
        mTopToolBar.setDisable(lb);
        mBottomToolBar.setDisable(lb);
        btnReenviar.setDisable(!lb);
        btnResponder.setDisable(!lb);

        
    }

    private void btnAdjuntarActionPerformed() {                                            
        try {
            FileChooser loFileM = new FileChooser();

            if (msDirActual != null && !msDirActual.equals("")) {
                loFileM.setInitialDirectory(new File(msDirActual));
            }
            List<File> loFiles = loFileM.showOpenMultipleDialog(new Stage());                
            
            if(loFiles != null && loFiles.size()>0){
                File loFile = loFiles.get(0);
                msDirActual = loFile.getParent();
                if (!loFile.exists()) {
                    throw new Exception("Fichero no existe " + loFile.getName());

                } else {
                    moListAdjuntos.addNew();
                    moListAdjuntos.getFields(0).setValue(loFile.getName());
                    moListAdjuntos.getFields(1).setValue(loFile.getAbsolutePath());
                    moListAdjuntos.update(false);
                    jTableAdjuntar.setVisible(true);
                    jSplitPane1.setDividerPosition(0, 0.8);
                }
            }
        } catch (Throwable e) {
            JMsgBox.mensajeErrorYLog(this, e);
        }

    }                                          
    

    private void jTableAdjuntarActionPerformed() {                                               
        if(jTableAdjuntar.getSelectionModel().getSelectedIndex()>=0){
            try {
                JListDatos loList = jTableAdjuntar.getListDatos();
                loList.setIndex(jTableAdjuntar.getSelectionModel().getSelectedIndex());
                JEjecutar.abrirDocumento(JTEEGUIXMENSAJESBD.getFileDeAdjunto(loList.getFields(1).toString()).getAbsolutePath());
            } catch (Exception ex) {
                JMsgBox.mensajeErrorYLog(this, ex);
            }
        }
    }                                              

    private void btnAdjuntarPlantillaActionPerformed() {                                                     
        try {
            FileChooser loFileM = new FileChooser();
            loFileM.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Plantilla GTC", new String[]{"gtc"}));

            List<File> loFiles = loFileM.showOpenMultipleDialog(new Stage());                
            
            if(loFiles != null && loFiles.size()>0){

                if (!loFiles.get(0).getPath().isEmpty()) {
                    if (!loFiles.get(0).getPath().endsWith(".gtc")) {
                        throw new Exception("La plantilla debe tener la extensión .gtc");
                    }
                    adjuntarPlantilla(loFiles.get(0).getPath());
                }
            }
        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            JMsgBox.mensajeErrorYLog(this, e);
        }
    }                                                    

    private void btnGuardarPlantillaActionPerformed() {                                                    
        try {
            FileChooser loFileM = new FileChooser();
            loFileM.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Plantilla GTC", new String[]{"gtc"}));
            File loFile = loFileM.showSaveDialog(new Stage());                
            
            if(loFile != null){
                exportarPlantilla(loFile.getAbsolutePath() + ".gtc");
            }
        } catch (Exception e) {
            JMsgBox.mensajeErrorYLog(this, e);
        }
    }                                                   

    private void btnCargarCamposActionPerformed() {                                                

        try {
            JPanelCargarCampos loPanel = new JPanelCargarCampos();
            
            Node loComponentFocused = this.getScene().getFocusOwner();
            int llCaretPosition = 0;
            if (loComponentFocused.equals(getTxtAsunto())) {
                llCaretPosition = getTxtAsunto().getCaretPosition();
            } else if (loComponentFocused.equals(getEkitCore())
                    || loComponentFocused.equals(mWebView)) {
                llCaretPosition = -1;
                loComponentFocused = htmlEditor1;
            }

            loPanel.setDatos(moMensaje.getCampos(), loComponentFocused, llCaretPosition);
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion(loPanel, null, loPanel, JMostrarPantalla.mclEdicionDialog);
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }                                               

    private void btnResponderActionPerformed() {
        try {
            JMensaje loMen = moMensaje.getResponder();
            JPanelMensajeFX loPanel = new JPanelMensajeFX();
            loPanel.setDatos(loMen, moComu, msPathPlantilla, moDatosGenerales, moCallback, true);
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }
    private void btnReenviarActionPerformed() {
        try {
            JMensaje loMen = moMensaje.getReenviar();
            JPanelMensajeFX loPanel = new JPanelMensajeFX();
            loPanel.setDatos(loMen, moComu, msPathPlantilla, moDatosGenerales, moCallback, true);
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }

    private void exportarPlantilla(String psPathPlantilla) {

        FileWriter loFile = null;
        try {
            loFile = new FileWriter(psPathPlantilla);
            PrintWriter loPrintWriter = new PrintWriter(loFile);

            loPrintWriter.println(htmlEditor1.getHtmlText());

        } catch (Exception e) {
            JMsgBox.mensajeError(this, e.getMessage());
        } finally {
            try {
                if (loFile != null) {
                    loFile.close();
                }
            } catch (Exception e2) {
                JMsgBox.mensajeError(this, e2.getMessage());
            }
        }
    }

    public HTMLEditor getEkitCore() {
        return htmlEditor1;
    }

    public TextField getTxtAsunto() {
        return txtAsunto;
    }



}
// String img =
//                    "<img alt=\"Embedded Image\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEeSURBVDhPbZFba8JAEIXz//9BoeCD4GNQAqVQBUVKm97UeMFaUmvF1mqUEJW0NXV6mtlL3PRwILuz52NnJxax5iGdt8l2TaP4shCZVBI4ezCjypVbkUklASN07NftlzMNh2GMYA6Iv0UFksWiH5w8Lk5HS9QkgHv5GAtuGq+SAKIAYJQl4E3UMTyYBLFzp7at1Q7p5scGQQlAszVdeJwYV7sqTa2xCKTKAET+dK1zysfSAEZReFqaadv1GsNi/53fAFv8KT2vkMYiKd8YADty7ptXvgaU3cuREc26Xx+YQPUtQnud3kznapkB2K4JJIcDgOBzr3uDkh+6Fjdb9XmUBf6OU3Fv6EHsIcy91hVTygP5CksA6uejGa78DxD9ApzMoGHun6uuAAAAAElFTkSuQmCC\" />";
////http://stackoverflow.com/questions/2213376/how-to-find-cursor-position-in-a-contenteditable-div