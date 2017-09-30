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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesFX.JFXConfigGlobal;
import utilesFX.formsGenericos.IPadreInterno;
import utilesFX.formsGenericos.JMostrarPantallaCargarForm;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionLista;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelEdicion extends BorderPane implements IPadreInterno, IPlugInFrame, IFormEdicionLista, IContainer, ISalir {
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
    @FXML private ImageView btnAceptarImg;    
    @FXML private ImageView btnCancelarImg;    
    @FXML private Pane jPanelBotones;
    @FXML private Label jLabel1;    
    @FXML private BorderPane jPanelCompleto;
    
    private ISalir moPadre;
    private IListaElementos moListaEdiciones = new JListaElementos();
    private JFormEdicionParametros moParam=new JFormEdicionParametros();

    public JPanelEdicion() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFX/formsGenericos/edicion/JPanelEdicion.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }        
        btnAceptarImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/accept.gif")));
        btnCancelarImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/cancel.gif")));
        btnAceptar.setOnAction((ActionEvent t) -> {
            btnAceptarActionPerformed();
        });          
        btnCancelar.setOnAction((ActionEvent t) -> {
            btnCancelarActionPerformed();
        });        
    }

    


    @Override
    public String getIdentificador() {
        return this.getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }

    @Override
    public JFormEdicionParametros getParametros() {
        return moParam;
    }
    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    @Override
    public void aplicarListaComponentesAplicacion() {
    }
    

    /**
     * Establecemos el panel de los datos
     * @param poPanel Interfaz que debe cumplir el controlador de este panel
     * @param poPanelMismo componente a insertar en este panel, suele ser el mismo objeto que poPanel
     * @throws Exception error
     */
    public void setPanel(final IFormEdicion poPanel, final Node poPanelMismo) throws Exception {
        jPanelCompleto.setCenter(poPanelMismo);
        moListaEdiciones.add(poPanel);
        
        initEdicion(poPanel);
        if(poPanel.getParametros() !=null && poPanel.getParametros().isSoloLectura()){
            btnAceptar.setVisible(false);
            btnCancelar.setText("Salir");
        }
    }
    //inicializamos un panel
    private void initEdicion(final IFormEdicion poPanel) throws Exception{
        poPanel.rellenarPantalla();
        poPanel.ponerTipoTextos();  
        JMostrarPantallaCargarForm.mostrarSiSwing(poPanel);
        poPanel.mostrarDatos();
        poPanel.habilitarSegunEdicion();
    }
    /**
     * Añadimos una edición y la inicializamos
     */
    @Override
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion(poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
    @Override
    public IListaElementos getListaEdiciones() {
        return moListaEdiciones;
    }    
    
    public void setEstado( String psEstado ) {
        jLabel1.setText(psEstado);
    }
    

    public Label getEstadoLabel() {
        return jLabel1;
    }

    public Pane getPanelBotones() {
        return jPanelBotones;
    }
    /**
     * Indica si todos los IFormEdicion son editables
     * @return 
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
            lbSoloLectura &= loPanel.getParametros() != null && loPanel.getParametros().isSoloLectura();
        }
        return !lbSoloLectura;
    }
    

    private void btnCancelarActionPerformed() {                                            
        try{
            //para cada uno cancelamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                loPanel.cancelar();
            }
            
            if(moPadre!=null){
                moPadre.salir();
            }
            moPadre = null;
        }catch(Throwable ex){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, ex, null);
        }        
        
    }                                           

    private void btnAceptarActionPerformed() {                                           
       boolean lbContinuar = true;
        try{
            btnAceptar.setDisable(true);            

            if(!isEditable()){
                throw new Exception(JFXConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura() );
            }
            //para cada uno establecemos datos y  validamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);        
                establecerDatos(loPanel);
                lbContinuar &= loPanel.validarDatos();
            }
            //para cada uno aceptamos
            for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                loPanel.aceptar();
            }
            if(moPadre!=null && lbContinuar){
                moPadre.salir();
            }else{
                Platform.runLater(() -> {
                    btnAceptar.setDisable(false);
                });
            }
        }catch(Throwable ex){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, ex, null);
            Platform.runLater(() -> {
                btnAceptar.setDisable(false);
            });
        } finally{
        }         
    }              
    @Override
    public void salir() {
        btnCancelarActionPerformed();
    }

    @Override
    public void setTitle(String psTitulo) {
    }
    @Override
    public void setPadre(ISalir poPadre) {
        moPadre=poPadre;
    }
    private void establecerDatos(IFormEdicion loPanel) throws ECampoError, Exception {
        JMostrarPantallaCargarForm.establecerSiSwing(loPanel);
        loPanel.establecerDatos();
    }
}
