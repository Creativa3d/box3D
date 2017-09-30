/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos.edicion;


import ListDatos.ECampoError;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesFX.JFXConfigGlobal;
import utilesFX.aplicacion.avisosGUI.JLabelAvisos;
import utilesFX.formsGenericos.IPadreInterno;
import utilesFX.formsGenericos.JMostrarPantallaCargarForm;
import utilesFX.msgbox.JMsgBox;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionLista;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**
 *
 * @author eduardo
 */
public class JPanelEdicionNuevoRapido  extends BorderPane implements IPadreInterno, IPlugInFrame , IFormEdicionLista, IContainer {

    private static final long serialVersionUID = 1L;
                    
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalir;
    @FXML private Button lblInformacion;
    @FXML private HBox jPanelBotones;                


    
    private ISalir moPadre;
    private IListaElementos moListaEdiciones = new JListaElementos();
//    private ButtonDesact moDesativado = new ButtonDesact();
    private boolean mbValidarDespuesEstablecer=true;
    private JFormEdicionParametros moParam=new JFormEdicionParametros();
    private JLabelAvisos jLabelAvisos1;
    
    /** Creates new form JPanelEdicion */
    public JPanelEdicionNuevoRapido() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFX/formsGenericos/edicion/JPanelEdicionNuevoRapido.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }               
        
        
        btnAceptar.setOnAction((e)->btnAceptarActionPerformed(e));
        btnCancelar.setOnAction((e)->btnCancelarActionPerformed(e));
        btnSalir.setOnAction((e)->btnSalirActionPerformed( e));
                
        setBottom(jPanelBotones);
        
        btnCancelar.setVisible(false);
        lblInformacion.setVisible(false);
        
        jLabelAvisos1 = new JLabelAvisos(lblInformacion, null);
        
        JFXConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelEdicion");
        
    }
    public String getIdentificador() {
        return this.getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }

    public JFormEdicionParametros getParametros() {
        return moParam;
    }
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }
    
    public void setValidarDespuesEstablecer(boolean pb){
        mbValidarDespuesEstablecer=pb;
    }

    public Button getAceptar() {
        return btnAceptar;
    }

    public Button getCancelat() {
        return btnCancelar;
    }
    public Button getCancelar() {
        return btnCancelar;
    }

    public Button getSalir() {
        return btnSalir;
    }

    public void addBoton(Button poComponent){
        jPanelBotones.getChildren().add(poComponent);
    }
    public void addBoton(Button poComponent, int plPosicion){
        jPanelBotones.getChildren().add(plPosicion, poComponent);
    }

    public void aceptar(){
        btnAceptarActionPerformed(null);
    }
    public void cancelar(){
        btnCancelarActionPerformed(null);
    }
    /**
     * Establecemos el panel de los datos
     * @param poPanel Interfaz que debe cumplir el controlador de este panel
     * @param poPanelMismo componente a insertar en este panel, suele ser el mismo objeto que poPanel
     * @throws Exception error
     */
    public void setPanel(final IFormEdicionNavegador poPanel, final Node poPanelMismo) throws Exception {
        moListaEdiciones.add(poPanel);

        setCenter(poPanelMismo);
//        setPrefSize(poPanel.getTanano().width, poPanel.getTanano().height);
//        setLayoutX(poPanel.getTanano().x);
//        setLayoutY(poPanel.getTanano().y);
        
        jLabelAvisos1.setAvisos(poPanel.getParametros().getAvisos());
        lblInformacion.setVisible(poPanel.getParametros().getAvisos().size()>0);
                
        initEdicion(poPanel);
        if(poPanel.getParametros() !=null){
            poPanel.getParametros().getBotones().add(btnSalir);
            poPanel.getParametros().getBotones().add(btnAceptar);
            poPanel.getParametros().getBotones().add(btnCancelar);
            poPanel.getParametros().getBotones().add(jLabelAvisos1);
        }               

    }
    //inicializamos un panel
    private void initEdicion(final IFormEdicionNavegador poPanel) throws Exception{
        poPanel.rellenarPantalla();
        poPanel.ponerTipoTextos();
        
        if(poPanel.getParametros() != null){
            moParam=poPanel.getParametros();
            if(poPanel.getParametros().isSoloLectura()){
                setBloqueoControles(poPanel,true);

                mostrarDatos(poPanel);
            }else{
                setBloqueoControles(poPanel,false);

                mostrarDatos(poPanel);
                poPanel.habilitarSegunEdicion();
            }
        } else {
            setBloqueoControles(poPanel,false);

            mostrarDatos(poPanel);
            poPanel.habilitarSegunEdicion();
        }
    }
    
    /**
     * Añadimos una edición y la inicializamos
     */
    @Override
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion((IFormEdicionNavegador)poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
    @Override
    public IListaElementos getListaEdiciones() {
        return moListaEdiciones;
    }
    /**
     * Indica si todos los IFormEdicionNavegador son editables
     * @return 
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbSoloLectura &= loPanel.getParametros() != null && loPanel.getParametros().isSoloLectura();
        }
        return !lbSoloLectura;
    }
    
    private void btnSalirActionPerformed(ActionEvent evt) {                                         
        try {
            //para cada uno cancelamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                loPanel.cancelar();
            }

            if(moPadre==null){
                JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(this);
            }else{
                moPadre.salir();
            }
            moPadre = null;
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            JMsgBox.mensajeError(this, e);
        }
    }                                        
    private void comprobarEdicion() throws Exception{
        if(!isEditable()){
            throw new Exception(JFXConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura());
        }
    }
    private void btnAceptarActionPerformed(ActionEvent evt) {                                           
        if(!btnAceptar.isDisabled()){
            try {
                btnAceptar.setDisable(true);
                comprobarEdicion();
                boolean lbContinuar=true;
                if(!mbValidarDespuesEstablecer){
                    //para cada uno validamos
                    for(int i = 0 ; i < moListaEdiciones.size(); i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        lbContinuar &= loPanel.validarDatos();
                    }                      
                }
                if (lbContinuar) {
                    //para cada uno establecemos datos y  validamos
                    for(int i = 0 ; i < moListaEdiciones.size(); i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        establecerDatos(loPanel);
                        if(mbValidarDespuesEstablecer){
                            lbContinuar &= loPanel.validarDatos();
                        }
                    }                      
                }
                if (lbContinuar) {
                    //para cada uno aceptamos 
                    for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        loPanel.aceptar();
                    }                
                    //para cada uno nuevo
                    for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        loPanel.nuevo();
                        mostrarDatos(loPanel);
                        loPanel.habilitarSegunEdicion();
                    }                

                    //el titulo solo el principal
                    moPadre.setTitle(((IFormEdicionNavegador) moListaEdiciones.get(0)).getTitulo());                
                }
            } catch (Throwable e) {
                JDepuracion.anadirTexto(getClass().getName(), e);
                JMsgBox.mensajeError(this, e);
            } finally{
                Platform.runLater(()->btnAceptar.setDisable(false));
            }
        }
    }                                          

    private void btnCancelarActionPerformed(ActionEvent evt) {                                            

        
        try {
            //para cada uno nuevo, que ya cancela y mostramos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                loPanel.nuevo();
                mostrarDatos(loPanel);
                loPanel.habilitarSegunEdicion();
            }
            //el titulo solo el principal
            moPadre.setTitle(((IFormEdicionNavegador) moListaEdiciones.get(0)).getTitulo());                

        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            JMsgBox.mensajeError(this, e);
        }
        
        

    }                                           

    @Override
    public void setPadre(ISalir poPadre) {
        moPadre=poPadre;
    }
    private void mostrarDatos(IFormEdicionNavegador loPanel) throws Exception {
        JMostrarPantallaCargarForm.mostrarSiSwing(loPanel);
        loPanel.mostrarDatos();
    }

    private void establecerDatos(IFormEdicionNavegador loPanel) throws ECampoError, Exception {
        JMostrarPantallaCargarForm.establecerSiSwing(loPanel);
        loPanel.mostrarDatos();
    }

    private void setBloqueoControles(IFormEdicionNavegador poPanel, boolean pbBloqueo) throws Exception {
        poPanel.setBloqueoControles(pbBloqueo);
        JMostrarPantallaCargarForm.setBloqueoControlesSiSwing(poPanel, pbBloqueo);
    }


}