/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFXAvisos.forms;

import utilesFX.formsGenericos.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utiles.JDepuracion;
import utilesFX.JCMBLinea;
import utilesFX.JTableViewCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOS;

/**
 *
 * @author eduardo
 */
public class JPanelGenericoEVENTOS  extends JPanelGenericoAbstract {
    private static final long serialVersionUID = 1L;

    
    @FXML private BorderPane jPanelInformacion;

    @FXML private FlowPane jPanelEditar;

    @FXML private Button jBtnNuevo;

    @FXML private Button jBtnEditar;

    @FXML private Button jBtnBorrar;

    @FXML private Button jBtnRefrescar;

    @FXML private Button jButtonAceptar;

    @FXML private Button jButtonCancelar;

    @FXML private JTableViewCZ jTableDatos;

    @FXML private StackPane jPanelCabezera;

    @FXML private GridPane jPanelConfigyFiltroRap;

    @FXML private Button btnMasFiltros;

    @FXML private Button btnOcultarCabezera;

    @FXML private Button btnVerAnteriores;

    @FXML private Label lblPosicion;

    @FXML private Label lblTotal;

    @FXML private TextField txtBusqueda;

    @FXML private Button btnMostrarCabezera;

    @FXML private Accordion jPanelRelacionadoGen;
    private  JPanelGeneralFiltroTodosCamp jPanelGeneralFiltroTodosCamp1;
    
    public static final int mclTipo = 0;
    public static Rectangulo moDimensionRelacionesDefecto = new Rectangulo(160, 29);
    private boolean mbPrimeroRelac=true;
    private JTFORMGUIXEVENTOS moEventos;

    
    /** Creates new form JPanelBusqueda */
    public JPanelGenericoEVENTOS() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFXAvisos/forms/JPanelGenericoEVENTOS.fxml"));
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
            btnVerAnteriores.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    btnVerAnterioresActionPerformed();
                }
            });

            jPanelGeneralFiltroTodosCamp1 = new JPanelGeneralFiltroTodosCamp(txtBusqueda);

            inicializar();
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            throw new IllegalStateException(ex);
            
        }
        
    }

    @Override
    protected boolean[] getCamposVisibles() {
         boolean[] labVisibles = new boolean[getDatos().getFields().count()];
        for (int i = 0; i < labVisibles.length; i++) {
         
                labVisibles[i] = true;
 
        }
        return labVisibles;
    }

    @Override
    protected void recuperarDatosBD() throws Exception {
        moEventos = (JTFORMGUIXEVENTOS) moControlador.getConsulta();
        super.recuperarDatosBD();
    }

    
    @Override
    public JPanelGeneralFiltroLinea getPanelGeneralFiltroLinea1() {
        return null;
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
        return null;
    }
    @Override
    public Button getbtnMasFiltros() {
        return btnMasFiltros;
    }
    @Override
    public ComboBox<JCMBLinea> getcmbConfig() {
        return null;
    }

    @Override
    public ComboBox<JCMBLinea> getcmbTipoFiltroRapido() {
        return null;
    }

    @Override
    public JTableViewCZ getTabla() {
        return jTableDatos;
    }
    @Override
    public ComboBox<JCMBLinea> getcmbFiltros(){
        return null;
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
     private void btnVerAnterioresActionPerformed() {                                                 
        try{
            btnVerAnteriores.setDisable(true);
            moEventos.crearSelectStandarConGrupoPrevio("");
            recuperarYmostrarDatos();
        }catch(Throwable e){
            utilesFX.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }            
}
    

