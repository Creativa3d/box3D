/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utiles.JDepuracion;
import utilesFX.JCMBLinea;
import utilesFX.JTableViewCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;

/**
 *
 * @author eduardo
 */
public class JPanelGenerico  extends JPanelGenericoAbstract {
    private static final long serialVersionUID = 1L;

    
//    @FXML private Pane JPanelTipoFiltroRapido;
    @FXML private Button btnConfig;
    @FXML private Button btnMasFiltros;
    @FXML private Button btnMostrarCabezera;
    @FXML private Button btnOcultarCabezera;
    @FXML private ComboBox<JCMBLinea> cmbConfig;
    @FXML private ComboBox<JCMBLinea> cmbTipoFiltroRapido;
    @FXML private ComboBox<JCMBLinea> cmbFiltros;
    @FXML private Button jBtnBorrar;
    @FXML private Button jBtnEditar;
    @FXML private Button jBtnNuevo;
    @FXML private Button jBtnRefrescar;
    @FXML private Button jButtonAceptar;
    @FXML private Button jButtonCancelar;
    @FXML private Pane jPanelCabezera;
    @FXML private Pane jPanelConfigyFiltroRap;
    @FXML private Pane jPanelEditar;
    @FXML private TextField txtBusqueda;
    @FXML private BorderPane jPanelInformacion;
    @FXML private Accordion jPanelRelacionadoGen;
    @FXML private JTableViewCZ jTableDatos;
    @FXML private Label lblPosicion;
    @FXML private Label lblSplashLabel;
    @FXML private Label lblTotal;
    @FXML private JTableViewCZ jTableFiltroRapido;
    
    private  JPanelGeneralFiltroLinea jPanelGeneralFiltroLinea1;
    private  JPanelGeneralFiltroTodosCamp jPanelGeneralFiltroTodosCamp1;
    
    public static final int mclTipo = 0;
    public static Rectangulo moDimensionRelacionesDefecto = new Rectangulo(160, 29);
    private boolean mbPrimeroRelac=true;

    
    /** Creates new form JPanelBusqueda */
    public JPanelGenerico() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/formsGenericos/JPanelGenerico.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
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
        inicializar();
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            throw new IllegalStateException(ex);
            
        }
        
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
    public JTableViewCZ getTabla() {
        return jTableDatos;
    }
    @Override
    public ComboBox<JCMBLinea> getcmbFiltros(){
        return cmbFiltros;
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
        VBox loBox = new VBox();
        TitledPane loTitle = new TitledPane(psGrupo.compareTo("")==0 ? "General": psGrupo, loBox);
        jPanelRelacionadoGen.getPanes().add(loTitle);
        if(mbPrimeroRelac){
            mbPrimeroRelac = false;
            jPanelRelacionadoGen.setExpandedPane(loTitle);
        }
        jPanelRelacionadoGen.setVisible(true);
        return loBox;
    }


    @Override
    public void panelRelacionadoGenClear(){
        jPanelRelacionadoGen.getPanes().clear();
        jPanelRelacionadoGen.setVisible(false);
    }
    @Override
    public void propiedadesBotonRecienCreado(Button poBoton) {
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
        if(poBoton!=null && !poBoton.isEsPrincipal()){
            return moDimensionRelacionesDefecto;
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
    

