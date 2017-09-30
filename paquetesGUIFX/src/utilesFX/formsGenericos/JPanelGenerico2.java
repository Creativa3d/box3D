/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesFX.JCMBLinea;
import utilesFX.JTableViewCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;

/**
 *
 * @author eduardo
 */
public class JPanelGenerico2  extends JPanelGenericoAbstract {
    private static final long serialVersionUID = 1L;

    

    @FXML
    private Button jBtnBorrar;

    @FXML
    private BorderPane borderPanePrincipal;
    
    @FXML
    private ComboBox<JCMBLinea>cmbConfig;

    @FXML
    private JTableViewCZ jTableFiltroRapido;

    @FXML
    private JTableViewCZ jTableDatos;

    @FXML
    private Button jBtnNuevo;

    @FXML
    private Button btnMasFiltros;

    @FXML
    private Button btnMostrarCabezera;

    @FXML
    private Button jButtonCancelar;

    @FXML
    private ComboBox<JCMBLinea> cmbTipoFiltroRapido;
    @FXML private ComboBox<JCMBLinea> cmbFiltros;

    @FXML
    private Label lblPosicion;

    @FXML
    private TextField txtBusqueda;

    @FXML
    private Label lblTotal;

    @FXML
    private SplitPane splitPaneCentral;

    @FXML
    private GridPane jPanelConfigyFiltroRap;

    @FXML
    private Button jButtonAceptar;

    @FXML
    private Button btnOcultarCabezera;


    @FXML
    private Button btnConfig;

    @FXML
    private BorderPane jPanelInformacion;

    @FXML
    private StackPane jPanelCabezera;

    @FXML
    private Button jBtnEditar;

    @FXML
    private TabPane tabPaneBotones;

    @FXML
    private Button jBtnRefrescar;

    @FXML
    private FlowPane jPanelEditar;

    private  JPanelGeneralFiltroLinea jPanelGeneralFiltroLinea1;
    private  JPanelGeneralFiltroTodosCamp jPanelGeneralFiltroTodosCamp1;
    
    public static final int mclTipo = 1;
    public static Rectangulo moDimensionDefecto = new Rectangulo(99, 55);
    private boolean mbPrimeroRelac=true;

    
    /** Creates new form JPanelBusqueda */
    public JPanelGenerico2() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/formsGenericos/JPanelGenerico2.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            final Node root = (Node)loader.load();
            

            btnMostrarCabezera.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    btnMostrarCabezeraActionPerformed();
                }
            });
            btnOcultarCabezera.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    btnOcultarCabezeraActionPerformed();
                }
            });


            jPanelGeneralFiltroTodosCamp1 = new JPanelGeneralFiltroTodosCamp(txtBusqueda);

            jPanelGeneralFiltroLinea1 = new JPanelGeneralFiltroLinea(jTableFiltroRapido);

            tabPaneBotones.setTabMaxHeight(0);
            splitPaneCentral.setDividerPosition(0, 1.0);
            widthProperty().addListener(
                    (ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) ->{
                        if(!jPanelInformacion.isVisible())
                            splitPaneCentral.setDividerPosition(0, 1.0);
                    }
            );
            inicializar();
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            throw new IllegalStateException(ex);
            
        }
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
//        jBtnBorrar.requestLayout();
//        jBtnBorrar.requestLayout();
//        jBtnEditar.requestLayout();
//        jBtnNuevo.requestLayout();
//        jBtnRefrescar.requestLayout();
//        jButtonAceptar.requestLayout();
//        jButtonCancelar.requestLayout();
    }
    
    public void setBotoneraARRIBA(){
        borderPanePrincipal.setBottom(null);
        borderPanePrincipal.setTop(tabPaneBotones);
    
    }
    public void setBotoneraABAJO(){
        borderPanePrincipal.setTop(null);
        borderPanePrincipal.setBottom(tabPaneBotones);
    
    }

    
    @Override
    public JPanelGeneralFiltroLinea getPanelGeneralFiltroLinea1() {
        return jPanelGeneralFiltroLinea1;
    }

    @Override
    public JPanelGeneralFiltroTodosCamp getPanelGeneralFiltroTodosCamp1() {
        return jPanelGeneralFiltroTodosCamp1;
    }

    @Override
    public Button getBtnAceptar() {
        return jButtonAceptar;
    }

    @Override
    public Button getBtnBorrar() {
        return jBtnBorrar;
    }

    @Override
    public Button getBtnCancelar() {
        return jButtonCancelar;
    }

    @Override
    public Button getBtnEditar() {
        return jBtnEditar;
    }

    @Override
    public Button getBtnNuevo() {
        return jBtnNuevo;
    }

    @Override
    public Button getBtnRefrescar() {
        return jBtnRefrescar;
    }

    @Override
    public Button getbtnConfig() {
        return btnConfig;
    }
    @Override
    public Button getbtnMasFiltros() {
        return btnMasFiltros;
    }
    @Override
    public ComboBox<JCMBLinea> getcmbConfig() {
        return cmbConfig;
    }

    @Override
    public ComboBox<JCMBLinea> getcmbTipoFiltroRapido() {
        return cmbTipoFiltroRapido;
    }

    @Override
    public ComboBox<JCMBLinea> getcmbFiltros(){
        return cmbFiltros;
    }
    @Override
    public JTableViewCZ getTabla() {
        return jTableDatos;
    }

    @Override
    public void setTotal(String psValor) {
        lblTotal.setText(psValor);
    }

    @Override
    public void setPosicion(String psValor) {
        lblPosicion.setText(psValor);
    }

    @Override
    public Pane crearContenedorBotonesYADD(String psGrupo) {
        tabPaneBotones.setTabMaxHeight(1.7976931348623157E308);
        
        FlowPane loBox = new FlowPane();
        loBox.setHgap(4);
        loBox.setPadding(new Insets(5, 0, 5, 0));
        loBox.setPrefWidth(10000);
        loBox.setPrefHeight(60);
        loBox.setStyle("-fx-background-color: #EEEEEE");
        Tab tab1 = new Tab(psGrupo.compareTo("")==0 ? "General": psGrupo);
        tab1.setContent(loBox);
        
        tabPaneBotones.getTabs().add(tab1);
        
        return loBox;
    }


    @Override
    public void panelRelacionadoGenClear(){
        //eliminamos todos menos el primero
        for(int i = tabPaneBotones.getTabs().size()-1 ; i>=1; i--){
            tabPaneBotones.getTabs().remove(i);
        }
        tabPaneBotones.setTabMaxHeight(0);
    }
    @Override
    public void propiedadesBotonRecienCreado(Button poBoton) {
        poBoton.setContentDisplay(ContentDisplay.TOP);
        if(JCadenas.isVacio(poBoton.getStyle())){
            poBoton.setStyle("-fx-content-display: top;");
        }else{
            poBoton.setStyle(poBoton.getStyle()  + "-fx-content-display: top;");
        }
    }

    @Override
    public void setVisiblePanelConfigyFiltroRap(boolean pbVisible) {
        jPanelConfigyFiltroRap.setVisible(pbVisible);
    }

    @Override
    public void setVisiblePanelTareasFiltro(boolean pbVisible) {
        btnMasFiltros.setVisible(pbVisible);
    }

    @Override
    public Pane getPanelEditar() {
        return jPanelEditar;
    }

    @Override
    public Rectangulo getDimensionDefecto(final IBotonRelacionado poBoton) {
        if(poBoton!=null ){
            return moDimensionDefecto;
        }else{
            return null;
        }
    }

    @Override
    public Object getPanelInformacion() {
        return jPanelInformacion;
    }

    private void mostrarCabezera() {
        jPanelConfigyFiltroRap.setVisible(true);
        btnMostrarCabezera.setVisible(false);
        jPanelCabezera.layout();
    }
    private void ocultarCabezera() {
        jPanelConfigyFiltroRap.setVisible(false);
        btnMostrarCabezera.setVisible(true);
        jPanelCabezera.layout();
    }
    private void btnMostrarCabezeraActionPerformed() {                                                   
        mostrarCabezera();
    }                                                  

    private void btnOcultarCabezeraActionPerformed() {                                                   
        ocultarCabezera();
    }                                             



    
}