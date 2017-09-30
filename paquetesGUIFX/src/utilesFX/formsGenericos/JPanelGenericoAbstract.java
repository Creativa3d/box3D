/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import utilesGUIx.formsGenericos.FormatHTML;
import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JUtilTabla;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesFX.Copiar;
import utilesFX.JCMBLinea;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.JTableViewCZ;
import utilesFX.TableCellConColor;
import utilesFX.TableCellWebViewConColor;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesFX.plugin.JPlugInUtilidadesFX;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.IPanelGenerico;
import utilesGUIx.formsGenericos.IPanelGenericoListener;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.ITablaConfig;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.JPanelGeneralFiltroModelo;
import utilesGUIx.formsGenericos.JPanelGeneralParametros;
import utilesGUIx.formsGenericos.JPanelGenericoEvent;
import utilesGUIx.formsGenericos.JTablaConfigTablaConfig;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;

/**
 *
 * @author eduardo
 */
public abstract class JPanelGenericoAbstract extends StackPane implements EventHandler<ActionEvent>, IPanelGenerico{
//, MouseListener, ListSelectionListener 
    private static final long serialVersionUID = 1L;
    protected IPanelControlador moControlador;
    protected JListDatos moDatos;
    protected ISalir moSalir;
    protected final IListaElementos<JPanelGenericoBotonGenerico> moBotones = new JListaElementos<>();
    protected JTablaConfig moTablaConfig;
    protected boolean mbAnularEventos = true;
    protected IListaElementos<JElementoBotonesA> moPanelRelaciones = new JListaElementos<>();
    protected JPanelGeneralPopUpMenu moPopUpMenu;
    private boolean mbEsBusqueda = false;
    //carga segundo plano
    protected Thread moThread;
    protected boolean mbEjecutando = false;
    private int lMax = 0;
    private Exception moError;
    private JFieldConComboBox moCMBTipoFiltroRapido;
    private JFieldConComboBox moCMBConfig;
    
    private final List listenerList = new ArrayList();
    protected JPanelGeneralFiltroModelo moFiltroModelo;
    private final Button jBtnCopiarTabla = new Button();
    private ObservableList<IFilaDatos> moDatosObserv;
    private Region moRegion;
    private ProgressIndicator moProgreso;
    private JMostrarPantallaParam moParam;
    private ActionListenerCZ moListernerFiltro = new ActionListenerCZ() {
        @Override
        public void actionPerformed(ActionEventCZ e) {
            Button loB = new Button();
            loB.setId(e.getActionCommand());
            handle(new ActionEvent(loB, null));
        }
    };
    private JFieldConComboBox moCMBFiltros;
    /**
     * Creates new form JPanelBusqueda
     */
    public JPanelGenericoAbstract() {
        super();
        initComponents();
        jBtnCopiarTabla.setText("Copiar");
        jBtnCopiarTabla.setGraphic(new ImageView(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/Copy16.gif"))));
        jBtnCopiarTabla.setOnAction((ActionEvent t) -> {
            try {
                JListDatos loList = JTablaConfig.getListOrdenado(
                        moFiltroModelo.getDatos(), moFiltroModelo.getTablaConfig().getConfigTablaConcreta());
                loList.msTabla = moFiltroModelo.getDatos().msTabla;
                Copiar.getInstance().setClip(JUtilTabla.getListDatos2String(loList));
            } catch (Exception ex) {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(JPanelGenericoAbstract.this, ex, null);
            }
        });
    }
    
    public void setMostrarDatosParam(JMostrarPantallaParam poParam) {
        moParam = poParam;
    }
    
  public int getMostrarTipo(){
        if(moParam!=null){
            if(moParam.getTipoMostrar()==JMostrarPantalla.mclEdicionInternal
                    || moParam.getTipoMostrar()==JMostrarPantalla.mclEdicionInternalBlanco){
                return JMostrarPantalla.mclEdicionFrame;
            }else{
                return moParam.getTipoMostrar();
            }
        } else {
            return JMostrarPantalla.mclEdicionFrame;
        }
    }
    
    /**
     * Inicializa, se usa en el contructor de las clases hijas
     */
    public void inicializar() {
        if (getbtnConfig() != null) {
            getbtnConfig().addEventHandler(ActionEvent.ACTION, this);
            getbtnConfig().setId(JPanelGenericoEvent.mcsAccionConfig);
        }
        if (getBtnNuevo() != null) {
            getBtnNuevo().addEventHandler(ActionEvent.ACTION, this);
            getBtnNuevo().setId(JPanelGenericoEvent.mcsAccionNuevo);
        }
        if (getBtnEditar() != null) {
            getBtnEditar().addEventHandler(ActionEvent.ACTION, this);
            getBtnEditar().setId(JPanelGenericoEvent.mcsAccionEditar);
        }
        if (getBtnBorrar() != null) {
            getBtnBorrar().addEventHandler(ActionEvent.ACTION, this);
            getBtnBorrar().setId(JPanelGenericoEvent.mcsAccionBorrar);
        }
        if (getBtnRefrescar() != null) {
            getBtnRefrescar().addEventHandler(ActionEvent.ACTION, this);
            getBtnRefrescar().setId(JPanelGenericoEvent.mcsAccionRefrescar);
        }
        if (getBtnAceptar() != null) {
            getBtnAceptar().addEventHandler(ActionEvent.ACTION, this);
            getBtnAceptar().setId(JPanelGenericoEvent.mcsAccionAceptar);
        }
        if (getBtnCancelar() != null) {
            getBtnCancelar().addEventHandler(ActionEvent.ACTION, this);
            getBtnCancelar().setId(JPanelGenericoEvent.mcsAccionCancelar);
        }
        if (getbtnMasFiltros() != null) {
            getbtnMasFiltros().addEventHandler(ActionEvent.ACTION, this);
            getbtnMasFiltros().setId(JPanelGenericoEvent.mcsAccionMasFiltro);
        }
        
        moFiltroModelo = new JPanelGeneralFiltroModelo();        



        getTabla().addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            JPanelGenericoAbstract.this.handle(t);
        });
        getTabla().addEventHandler(ActionEvent.ACTION, (ActionEvent t) -> {
            Button lob = new Button();
            lob.setId(JPanelGenericoEvent.mcsENTER);
            JPanelGenericoAbstract.this.handle(new ActionEvent(lob, null));
        });
        getTabla().addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            JPanelGenericoAbstract.this.handle(t);
        });
        getTabla().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getClickCount() > 1) {
                Button lob = new Button();
                lob.setId(JPanelGenericoEvent.mcsENTER);
                JPanelGenericoAbstract.this.handle(new ActionEvent(lob, null));
            }
        });
        

        getTabla().getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            if (!mbAnularEventos) {
                ponerPosicion();
            }
        });

        if (getcmbTipoFiltroRapido() != null) {
            moCMBTipoFiltroRapido = new JFieldConComboBox(getcmbTipoFiltroRapido());
            moCMBTipoFiltroRapido.borrarTodo();
            moCMBTipoFiltroRapido.addLinea("Filtro por campo", JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo + JFilaDatosDefecto.mcsSeparacion1);
            moCMBTipoFiltroRapido.addLinea("Filtro por todos campos", JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos + JFilaDatosDefecto.mcsSeparacion1);
            getcmbTipoFiltroRapido().valueProperty().addListener(new ChangeListener<JCMBLinea>() {
                @Override 
                public void changed(ObservableValue ov, JCMBLinea t, JCMBLinea t1) {                
                    cmbTipoFiltroRapidoItemStateChanged();
                }    
            });
        }
        if (getcmbConfig() != null) {
            moCMBConfig= new JFieldConComboBox(getcmbConfig());
            getcmbConfig().valueProperty().addListener(new ChangeListener<JCMBLinea>() {
                @Override 
                public void changed(ObservableValue ov, JCMBLinea t, JCMBLinea t1) {                
                    cmbConfigItemStateChanged();
                }    
            });
        }

        if(getcmbFiltros() != null){
            moCMBFiltros= new JFieldConComboBox(getcmbFiltros());
            getcmbFiltros().valueProperty().addListener(new ChangeListener<JCMBLinea>() {
                @Override 
                public void changed(ObservableValue ov, JCMBLinea t, JCMBLinea t1) {                
                    try{
                        if(!mbAnularEventos){
                            if(!moFiltroModelo.getTablaFiltro().moList.msTabla.equals(moCMBFiltros.getFilaActual().msCampo(0))){
                                moFiltroModelo.setFiltro(moCMBFiltros.getFilaActual().msCampo(0));
                                Button lob = new Button();
                                lob.setId(JPanelGeneralFiltroModelo.mcsCambioFiltro);
                                JPanelGenericoAbstract.this.handle(new ActionEvent(lob, null));
                            }
                        }
                    }catch(Exception ex){
                        utilesFX.msgbox.JMsgBox.mensajeErrorYLog(JPanelGenericoAbstract.this, ex, getClass().getName());
                    }                }    
            });
                
                
        }        
        //OJO: se hace esto pq si no al reenlazar con un controlador nuevo, se quedan 
        //pillados los eventos con el moTablaConfig anterior, por lo q se duplican los eventos
        //ya q el objeto no se elimina de memoria al tener una referencia el JTable (Listener)
        //es decir, se debe hacer solo una vez la asignacion de listener, por lo q se
        //hace en el constructor
        moTablaConfig = new JTablaConfig(getTabla());

        if (getPanelGeneralFiltroLinea1() != null) {
            getPanelGeneralFiltroLinea1().setComponentes(getTabla());
        }
        if (getPanelGeneralFiltroTodosCamp1() != null) {
            getPanelGeneralFiltroTodosCamp1().setComponentes(
                    getTabla(), this);
        }

        moPopUpMenu = new JPanelGeneralPopUpMenu(this, getTabla(),
                getBtnAceptar(), getBtnBorrar(), getBtnCancelar(),
                jBtnCopiarTabla,
                getBtnEditar(), getBtnNuevo(), getBtnRefrescar());
        ((Node)getPanelInformacion()).setVisible(false);

//        getPanelGeneralFiltroLinea1().requestFocus();
//        getPanelGeneralFiltroLinea1().requestFocusInWindow();

    }

    public void aplicarBoton(final IBotonRelacionado poBoton) {
        Button loComp = getBoton(poBoton);
        if (loComp != null) {
            aplicarBoton(loComp, poBoton);
        }
        moPopUpMenu.aplicarBoton(poBoton);
    }

    public Button getBoton(final IBotonRelacionado poBoton) {
        Button loBoton = null;
        if (moControlador.getParametros() != null && moControlador.getParametros().getBotonesGenerales() != null) {
            JPanelGeneralBotones loBotonesGen = moControlador.getParametros().getBotonesGenerales();
            if (loBotonesGen.getAceptar() == poBoton) {
                loBoton = getBtnAceptar();
            }
            if (loBotonesGen.getCancelar() == poBoton) {
                loBoton = getBtnCancelar();
            }
            if (loBotonesGen.getBorrar() == poBoton) {
                loBoton = getBtnBorrar();
            }
            if (loBotonesGen.getEditar() == poBoton) {
                loBoton = getBtnEditar();
            }
            if (loBotonesGen.getNuevo() == poBoton) {
                loBoton = getBtnNuevo();
            }
            if (loBotonesGen.getRefrescar() == poBoton) {
                loBoton = getBtnRefrescar();
            }

            for (int i = 0; i < moBotones.size() && loBoton == null; i++) {
                if (moBotones.get(i).moBotonRelac == poBoton) {
                    loBoton = moBotones.get(i).moButton;
                }
            }
        }

        return loBoton;
    }

    /**
     * Devuelve el componente JPanelGeneralFiltroLinea de la clase hija
     * @return 
     */
    public abstract JPanelGeneralFiltroLinea getPanelGeneralFiltroLinea1();

    /**
     * Devuelve el componente JPanelGeneralFiltroTodosCamp de la clase hija
     * @return 
     */
    public abstract JPanelGeneralFiltroTodosCamp getPanelGeneralFiltroTodosCamp1();

    /**
     * Devuelve el componente Aceptar de la clase hija
     * @return 
     */
    public abstract Button getBtnAceptar();

    /**
     * Devuelve el componente Borrar de la clase hija
     * @return 
     */
    public abstract Button getBtnBorrar();

    /**
     * Devuelve el componente Cancelar de la clase hija
     * @return 
     */
    public abstract Button getBtnCancelar();

    /**
     * Devuelve el componente Editar de la clase hija
     * @return 
     */
    public abstract Button getBtnEditar();

    /**
     * Devuelve el componente Nuevo de la clase hija
     * @return 
     */
    public abstract Button getBtnNuevo();

    /**
     * Devuelve el componente Refrescar de la clase hija
     * @return 
     */
    public abstract Button getBtnRefrescar();

    /**
     * Devuelve el componente Config de la clase hija
     * @return 
     */
    public abstract Button getbtnConfig();
    /**
     * Devuelve el componente mas filtros de la clase hija
     * @return 
     */
    public abstract Button getbtnMasFiltros();
    /**
     * Devuelve el componente combo copnfig de la clase hija
     * @return 
     */
    public abstract ComboBox<JCMBLinea> getcmbConfig();

    /**
     * Devuelve el componente combo indica el tipo de busqueda de la clase hija
     * @return 
     */
    public abstract ComboBox<JCMBLinea> getcmbTipoFiltroRapido();
    
    public abstract ComboBox<JCMBLinea> getcmbFiltros();
    /**
     * Devuelve el componente Tabla de la clase hija
     * @return 
     */
    public abstract JTableViewCZ getTabla();

    /**
     * Establece el total en la clase hija
     * @param psValor
     */
    public abstract void setTotal(String psValor);

    /**
     * Establece la posicion en la clase hija
     * @param psValor
     */
    public abstract void setPosicion(String psValor);

    /**
     * Crea un nuevo contenedor en la clase hija
     * @param psGrupo
     * @return 
     */
    public abstract Pane crearContenedorBotonesYADD(String psGrupo);

    /**
     * se llamada despues de crear un boton
     * @param poBoton
     */
    public abstract void propiedadesBotonRecienCreado(Button poBoton);

    /**
     * Panel de los botones relacionados 
     */
    public abstract void panelRelacionadoGenClear();
    
    /**
     * Establece la visibilidad del config y filtro rapido
     * @param pbVisible
     */
    public abstract void setVisiblePanelConfigyFiltroRap(boolean pbVisible);

    /**
     * Establece la visibilidad del panel del filtro avanzado
     * @param pbVisible
     */
    public abstract void setVisiblePanelTareasFiltro(boolean pbVisible);

    /**
     * Devuelve el Panel de la clase hija en donde estan los botones principales
     * @return 
     */
    public abstract Pane getPanelEditar();

    /**
     * Dimension por defecto del boton
     * @param poBoton
     * @return 
     */
    public abstract Rectangulo getDimensionDefecto(final IBotonRelacionado poBoton);

    @Override
    public int[] getSelectedRows() {
        int[] lalRows;
        ObservableList loRows = getTabla().getSelectionModel().getSelectedItems();
        lalRows=new int[loRows.size()];
        try {
            for (int i = 0; i < loRows.size(); i++) {
                Object loo = loRows.get(i);                
                lalRows[i] = getDatos().indexOf(loo);
            }
        } catch (Throwable e) {
//            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        return lalRows;
    }

    public int getSelectedRow() {
        int lIndice = getTabla().getSelectionModel().getSelectedIndex();
        if(lIndice<0 || lIndice>getDatos().size()) {
            int[] lalIndices = getSelectedRows();
            if (lalIndices != null && lalIndices.length > 0) {
                lIndice = lalIndices[0];
            }
        }
        return lIndice;
    }

    public static void comprobarSeleccion(JTableViewCZ jTableDatos) {
//        ObservableList lalIndex = jTableDatos.getSelectionModel().getSelectedIndices();
//        jTableDatos.getSelectionModel().clearSelection();
//        for (int i = 0; i < lalIndex.size(); i++) {
//            int l =  ((Integer)lalIndex.get(i)).intValue();
//            
//            if (l >= jTableDatos.getItems().size() || l < 0) {
//                jTableDatos.getSelectionModel().select(l);
//            }
//        }
    }


    /**
     * Devolvemos el controlador del panel
     * @return 
     */
    public IPanelControlador getControlador() {
        return moControlador;
    }

    /**
     * Establecemos el controlador del panel
     *
     * @param poControlador controlador
     * @throws Exception error
     */
    public void setControlador(final IPanelControlador poControlador) throws Exception {
        setControlador(poControlador, null);
    }

    /**
     * Establecemos el controlador del panel
     *
     * @param poControlador controlador
     * @param poSalir clase padre, puede ser un frame, internalframe,dialog,
     * pero que cumpla el interfaz salir
     * @throws Exception error
     */
    public void setControlador(final IPanelControlador poControlador, final ISalir poSalir) throws Exception {
        moSalir = poSalir;
        //si ya existia un controlador previo borramos el listener
        if (moControlador != null && moControlador instanceof IPanelGenericoListener) {
            removeListenerIPanelGenerico((IPanelGenericoListener) moControlador);
        }
        //establecemos el controlador
        moControlador = poControlador;
        //si el controlador es de tipo IPanelGenericoListener, a?adimos el listener
        if (moControlador instanceof IPanelGenericoListener) {
            addListenerIPanelGenerico((IPanelGenericoListener) moControlador);
        }

        if (moControlador.getParametros().mbSegundoPlano) {
            mostrarProceso();
            synchronized (JPanelGenericoAbstract.this) {
                while (mbEjecutando) {
                    try {
                        wait(1000);
                    } catch (Throwable e) {
                    }
                }
                mbEjecutando=true;
                EjecutarPanelGenericoA loEjecutar = new EjecutarPanelGenericoA(this);
                moThread = new Thread(loEjecutar);
                moThread.start();
            }
        } else {
            recuperarYmostrarDatos();
        }
    }
    protected void mostrarProceso() throws Exception{
        if (Platform.isFxApplicationThread()) {
            if(moRegion==null){
                moRegion = new Region();
                moRegion.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
                moProgreso = new ProgressIndicator();
                moProgreso.setMaxSize(150, 150);

     
                getChildren().addAll(moRegion, moProgreso);
                
            }

            moRegion.setVisible(true);
            moProgreso.setVisible(true);     
        }else{
            JPlugInUtilidadesFX.runAndWait(() -> {
                if(moRegion==null){
                    moRegion = new Region();
                    moRegion.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
                    moProgreso = new ProgressIndicator();
                    moProgreso.setMaxSize(150, 150);
                    getChildren().addAll(moRegion, moProgreso);
                }
                moRegion.setVisible(true);
                moProgreso.setVisible(true);
            });
        }
    }
    protected void ocultarProceso() throws InterruptedException, ExecutionException{
        if(moRegion!=null){
            if (Platform.isFxApplicationThread()) {
                    moRegion.setVisible(false);
                    moProgreso.setVisible(false);  
            }else{
                JPlugInUtilidadesFX.runAndWait(() -> {
                    moRegion.setVisible(false);
                    moProgreso.setVisible(false);
                });
            }
        }
    }

    protected void setFormatosCampos(Format[] paoFormatosCampos) {
        if (paoFormatosCampos != null) {
            for (int i = 0; i < paoFormatosCampos.length; i++) {
                if (paoFormatosCampos[i] != null) {
                    if(paoFormatosCampos[i] instanceof FormatHTML){
                        TableColumn loColumn = getTableColumnTable(i);
                        final int lColLocal = i;
                        loColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                            @Override
                            public TableCell call(TableColumn param) {
                                return new TableCellWebViewConColor(moDatos, lColLocal, getTabla());
                            }
                        });
                    }else {
                        TableCellConColor loColum = getTableColumn(i);
                        if(loColum!=null){
                            loColum.setFormat(paoFormatosCampos[i]);
                        }
                    }
                }
            }
        }
    }
    private TableColumn getTableColumnTable(int plColumn){
        TableColumn loResult=null;
        for (int i = 0; i < getTabla().getColumns().size(); i++) {
            TableColumn loColum = (TableColumn) getTabla().getColumns().get(i);
            if(Integer.parseInt(loColum.getId())==plColumn){
                loResult=loColum;
            }
        }
        return loResult;
    }
    private TableCellConColor getTableColumn(int plColumn){
        TableCellConColor loResult=null;
            for (int i = 0; i < getTabla().getColumns().size(); i++) {
                TableColumn loColum = (TableColumn) getTabla().getColumns().get(i);
                if(TableCellConColor.class.isAssignableFrom(loColum.getCellFactory().getClass()) ){
                    TableCellConColor loRender = (TableCellConColor) loColum.getCellFactory();
                    if(loRender.getColumn()==plColumn){
                        loResult=loRender;
                    }
                }

            }
        return loResult;
    }

    protected void recuperarYmostrarDatos() throws Exception {
        synchronized (JPanelGenericoAbstract.this) {
            mbEjecutando = true;
        }    
        if (moControlador.getParametros().isActivado()) {
            recuperarDatosBD();
        }
        if (Platform.isFxApplicationThread()) {
            try {
                mostrarDatos();
            } catch (Throwable ex) {
                throw new Exception(ex);
            }
        } else {
            JPlugInUtilidadesFX.runAndWait(() -> {
                try {
                    mostrarDatos();
                } catch (Throwable ex) {
                    moError = new Exception(ex);
                }
            });
            if (moError != null) {
                throw moError;
            }
        }
    }

    protected void mostrarDatos() throws Throwable {
        if (moControlador.getParametros().isActivado()) {
            try {
                if (JFXConfigGlobal.getInstancia().getPlugInFactoria() != null) {
                    JFXConfigGlobal.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                            JFXConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto(),
                            moControlador);
                }
                //            System.out.println("recuperarDatos");
                mbAnularEventos = true;
                moTablaConfig.setInicializado(false);
                moTablaConfig.setAnularEventos(true);

                setVisiblePanelConfigyFiltroRap(moControlador.getParametros().isVisibleConfigYFiltroRap());
                //creamos los botones relacionados
                JPanelGeneralBotones loBotonesGenerales = moControlador.getParametros().getBotonesGenerales();
                moBotones.clear();
                boolean lbBotonesRelac = false;
                IListaElementos loBotonesRelac = loBotonesGenerales.getListaBotones();
                if (loBotonesRelac != null && loBotonesRelac.size() > 0) {
                    //vemos si hay algun boton relacionado, es decir, no principal
                    //si el boton NO es activo se borra
                    for (int i = 0; i < loBotonesRelac.size(); i++) {
                        IBotonRelacionado loBoton = (IBotonRelacionado) loBotonesRelac.get(i);
                        if (loBoton.isActivo()) {
                            if (!loBoton.isEsPrincipal()) {
                                lbBotonesRelac = true;
                            }
                        } else {
                            loBotonesRelac.remove(i);
                            i--;
                        }
                    }
                } else {
                    lbBotonesRelac = false;
                }

                if (lbBotonesRelac) {
                    moPanelRelaciones.clear();
                    panelRelacionadoGenClear();
                    crearBotonesSoloRelacionados(loBotonesGenerales.getListaBotones());
                } else {
                    panelRelacionadoGenClear();
                }
                establecerBotones(loBotonesGenerales, loBotonesRelac);

                getTabla().setTableCZColores(moControlador.getParametros().getColoresTabla());

                //len indicamos el panel que le visualiza los datos
                moControlador.setPanel(this);
      
                moTablaConfig.setDatos(
                        moControlador.getParametros().getNombre(),
                        moControlador.getParametros().getLongitudCampos(),
                        moControlador.getParametros().getOrdenCampos(),
                        moControlador.getParametros().getColumnasVisiblesConfig());
                
                //creamos el filtro
                moFiltroModelo.setDatos(getDatos(), moListernerFiltro, true, moTablaConfig);
                setVisiblePanelTareasFiltro(loBotonesGenerales.getFiltro().isActivo());
                if (getPanelGeneralFiltroLinea1() != null) {
                    getPanelGeneralFiltroLinea1().setVisible(loBotonesGenerales.getFiltro().isActivo());
                }
                if (getPanelGeneralFiltroTodosCamp1() != null) {
                    getPanelGeneralFiltroTodosCamp1().setVisible(loBotonesGenerales.getFiltro().isActivo());
                }
                if (getcmbTipoFiltroRapido() != null) {
                    getcmbTipoFiltroRapido().setVisible(loBotonesGenerales.getFiltro().isActivo());
                    if (loBotonesGenerales.getFiltro().isActivo()) {
                        if (moControlador.getParametros() != null) {
                            moCMBTipoFiltroRapido.setValueTabla(moControlador.getParametros().msTipoFiltroRapido + JFilaDatosDefecto.mcsSeparacion1);
                            moCMBTipoFiltroRapido.setValorOriginal(null);
                        }
                    }
                }

                //visualizamos los datos 
                visualizarDatos();
                getTabla().setEditable(false);

                //leemos la config. guardada previamente
                if (getPanelGeneralFiltroLinea1() != null) {
                    getPanelGeneralFiltroLinea1().setAnularSetLong(true);
                }
                String lsAux = JTablaConfig.mcsNombreDefecto;
                try {
                    lsAux = moTablaConfig.getIndiceConfig();
                } catch (Throwable e) {
                }
                if(moCMBConfig!=null){
                    try {
                        //si existe la config de tabla se establece, si no se pone la de por defecto
                        if (moTablaConfig.getConfigTabla().getConfig(lsAux) != null) {
                            moTablaConfig.setIndiceConfig(lsAux);
                        } else {
                            lsAux = JTablaConfig.mcsNombreDefecto;
                            moTablaConfig.setIndiceConfig(lsAux);

                        }
                    } catch (Throwable e) {//ignoramos el error por si las moscas, por un fallo aqui no debe parar ejecucion
                        JDepuracion.anadirTexto(getClass().getName(), e);
                    }
                    //rellenamos el combo de config
                    rellenarConfig();
                    //establecemos la config en el combo
                    moCMBConfig.setValueTabla(lsAux + JFilaDatosDefecto.mcsSeparacion1);
                }
                if (loBotonesGenerales.getFiltro().isActivo()) {
                    if(getPanelGeneralFiltroLinea1()!=null){
                        getPanelGeneralFiltroLinea1().setAnularSetLong(false);
                        getPanelGeneralFiltroLinea1().setDatos(
                                moFiltroModelo, 
                                moTablaConfig.getConfigTabla().getCampoBusqueda());
                    }
                    setFiltroCamposVisibles();
                    getPanelGeneralFiltroTodosCamp1().setDatos(
                            moFiltroModelo);

                    cmbTipoFiltroRapidoItemStateChanged();

                    moFiltroModelo.setFiltro(moFiltroModelo.getTablaConfig().getConfigTabla().getFiltroDefecto());
                    if (getcmbFiltros() != null) {
                        moCMBFiltros.RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
                        moCMBFiltros.mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
                    }                    
                    
                }

                moPopUpMenu.configurarPopup(
                        moBotones, loBotonesGenerales,
                        this, getDatos(),
                        moControlador.getParametros().getColumnasVisiblesConfig());
                moPopUpMenu.setVisibleCampos(getCamposVisibles());


            } finally {
                ocultarProceso();
                synchronized (JPanelGenericoAbstract.this) {
                    mbEjecutando = false;
                    notifyAll();
                }
                if (moControlador.getParametros().getBotonesGenerales().getFiltro().isActivo()) {
                    cmbTipoFiltroRapidoItemStateChanged();
                }
                moTablaConfig.setAnularEventos(false);
                mbAnularEventos = false;
            }
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclFinInicializar, "");
        } else {
            Region loRegion = new Region();
            loRegion.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
            Label loLabel = new Label("No hay suficientes permisos para visualizar estos datos");

            getChildren().addAll(loRegion, loLabel);
        }
    }

    protected void recuperarDatosBD() throws Exception {
        if (moControlador.getParametros().isActivado()) {
            synchronized (JPanelGenericoAbstract.this) {
                mbEjecutando = true;
                notifyAll();
            }
        

            if (moControlador.getParametros() == null || moControlador.getParametros().isRefrescarListDatos()) {
                moDatos = moControlador.getDatos();
            } else {
                moDatos = moControlador.getConsulta().getList();
            }
            moDatosObserv = FXCollections.observableArrayList(moDatos);
        }
    }

    protected Pane getContainer(final String psGrupo) {
        Pane loPanel = null;
        for (int i = 0; i < moPanelRelaciones.size() && loPanel == null; i++) {
            JElementoBotonesA loElem = (JElementoBotonesA) moPanelRelaciones.get(i);
            if (loElem.msGrupo.compareTo(psGrupo) == 0) {
                loPanel = loElem.moToolBar;
            }
        }
        if (loPanel == null) {
            loPanel = crearContenedorBotonesYADD(psGrupo);
            moPanelRelaciones.add(new JElementoBotonesA(psGrupo, loPanel));
        }
        return loPanel;
    }

    protected boolean[] getCamposVisibles() {
        boolean[] labVisibles = new boolean[getDatos().getFields().count()];
        for (int i = 0; i < labVisibles.length; i++) {
            try {
                labVisibles[Integer.parseInt(moTablaConfig.getConfigTablaConcreta().getColumna(i).getNombre())] = moTablaConfig.getConfigTablaConcreta().getColumna(i).getLong() > 0;
            } catch (Throwable e) {
            }
        }
        return labVisibles;
    }

    protected void setFiltroCamposVisibles() {
        boolean[] labVisibles = getCamposVisibles();
        moFiltroModelo.setVisibles(labVisibles);
        moPopUpMenu.setVisibleCampos(labVisibles);
    }

    protected void rellenarConfig() {
        boolean lbAux = mbAnularEventos;
        mbAnularEventos = true;
        //config
        if(moCMBConfig!=null){
            moCMBConfig.borrarTodo();

            for (int i = 0; i < moTablaConfig.getConfigTabla().size(); i++) {
                moCMBConfig.addLinea(
                        moTablaConfig.getConfigTabla().getConfig(i).getNombre(),
                        moTablaConfig.getConfigTabla().getConfig(i).getNombre() + JFilaDatosDefecto.mcsSeparacion1);
            }
        }
        
        mbAnularEventos = lbAux;
    }

    protected void establecerBotones(final JPanelGeneralBotones poBotonesGenerales, final IListaElementos poBotonesRelacPrinci) {
        aplicarBoton(getBtnNuevo(), poBotonesGenerales.getNuevo());
        aplicarBoton(getBtnBorrar(), poBotonesGenerales.getBorrar());
        aplicarBoton(getBtnEditar(), poBotonesGenerales.getEditar());
        aplicarBoton(getBtnRefrescar(), poBotonesGenerales.getRefrescar());
        aplicarBoton(getBtnAceptar(), poBotonesGenerales.getAceptar());
        aplicarBoton(getBtnCancelar(), poBotonesGenerales.getCancelar());
        aplicarBoton(jBtnCopiarTabla, poBotonesGenerales.getCopiarTabla());
        mbEsBusqueda = poBotonesGenerales.getAceptar().isActivo();

        //vemos los componentes que hay por defecto
        if (lMax == 0) {
            lMax = getPanelEditar().getChildren().size();
        }
        //borramos todos los botones menos los generales de arriba q son los 6 primeros
        for (int i = getPanelEditar().getChildren().size() - 1; i >= lMax; i--) {
            getPanelEditar().getChildren().remove(getPanelEditar().getChildren().get(i));
        }
        boolean lbRelacPrinci = false;
//        crearBotonesRelacionados(jPanelEditar, poBotonesGenerales.getListaBotones());
        if (poBotonesRelacPrinci != null && poBotonesRelacPrinci.size() > 0) {
            IListaElementos loElem = new JListaElementos();
            for (int i = 0; i < poBotonesRelacPrinci.size(); i++) {
                IBotonRelacionado loBoton = (IBotonRelacionado) poBotonesRelacPrinci.get(i);
                if (loBoton.isEsPrincipal()) {
                    loElem.add(loBoton);
                    lbRelacPrinci = true;
                }
            }
            crearBotonesRelacionados(getPanelEditar(), loElem);
        }

        if (!(poBotonesGenerales.getNuevo().isActivo() || poBotonesGenerales.getBorrar().isActivo()
                || poBotonesGenerales.getEditar().isActivo() || poBotonesGenerales.getRefrescar().isActivo()
                || poBotonesGenerales.getAceptar().isActivo() || poBotonesGenerales.getCancelar().isActivo()
                || lbRelacPrinci)) {
            getPanelEditar().setVisible(false);
        } else {
            getPanelEditar().setVisible(true);
        }


    }

    protected void aplicarBoton(Button poBoton, IBotonRelacionado poProp) {
        poBoton.setVisible(poProp.isActivo());
        poBoton.setManaged(poProp.isActivo());
        if (poProp.getCaption() != null) {
            poBoton.setText(
                poProp.getCaption().replace("<html>", "").replace("</html>", "").replace("<br>", "\n")
            );
            poBoton.setTooltip(new Tooltip(poBoton.getText()));
        }
        if (poProp.getIcono() != null) {
            if(Node.class.isAssignableFrom(poProp.getIcono().getClass())){
                poBoton.setGraphic((Node)poProp.getIcono());
            }else if(Image.class.isAssignableFrom(poProp.getIcono().getClass())){             
                ImageView loVie = new ImageView((Image)poProp.getIcono());
                loVie.setFitHeight(16);
                loVie.setFitWidth(16);
                loVie.setPreserveRatio(true);
                poBoton.setGraphic(loVie);
            } else {
                JDepuracion.anadirTexto(getClass().getName(), "Icono no stablecido en botón " +  poProp.getCaption() + "("+poProp.getNombre()+")");
            }
        }
        if (poProp.getDimension() != null) {
            poBoton.setPrefSize(poProp.getDimension().width, poProp.getDimension().height );
        } else if (getDimensionDefecto(poProp) != null) {
            poBoton.setPrefSize(getDimensionDefecto(poProp).width, getDimensionDefecto(poProp).height);
        }
        poBoton.setPrefSize(poBoton.getPrefWidth(), getBtnAceptar().getPrefHeight());
        propiedadesBotonRecienCreado(poBoton);
    }

    protected void visualizarDatos() throws Throwable {
        //si ya existia eliminar listener
        moFiltroModelo.limpiar();
        //creamos el table model a partir de los datps

        getTabla().setModel(moDatos, moDatosObserv);
        setFormatosCampos(moControlador.getParametros().getFormatosCampos());
        //solo se puede seleccionar una fila
        if (IEjecutarExtend.class.isAssignableFrom(moControlador.getClass())) {
            getTabla().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } else {
            getTabla().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
        //seleccionamos la primera fila por defecto
        if (moDatos.size() > 0) {
            getTabla().getSelectionModel().clearAndSelect(0);
        }
        setTotal(String.valueOf(getDatos().size()));
        ponerPosicion();

    }
    //crear botones relacionados

    protected void crearBotonesRelacionados(final Pane poPanel, final IListaElementos<IBotonRelacionado> poBotones) {
        int lIndexInicial = moBotones.size();
        for (int i = 0; i < poBotones.size(); i++) {
            Button loButon = crearBoton(poPanel, (IBotonRelacionado) poBotones.get(i), lIndexInicial+i);
            moBotones.add(new JPanelGenericoBotonGenerico(poBotones.get(i), loButon));
        }
    }

    protected void crearBotonesSoloRelacionados(final IListaElementos poBotones) {
        for (int i = 0; i < poBotones.size(); i++) {
            IBotonRelacionado loBoton = (IBotonRelacionado) poBotones.get(i);
            if (!loBoton.isEsPrincipal()) {
                Button loButon = crearBoton(getContainer(loBoton.getGrupo()), loBoton, moBotones.size());
                moBotones.add(new JPanelGenericoBotonGenerico(loBoton, loButon));
            }
        }
    }

    //crea un boton
    protected Button crearBoton(final Pane poPanel, final IBotonRelacionado poBoton, final int plIndex) {
        Button loBotonReal = new Button(poBoton.getCaption());
        loBotonReal.addEventHandler(ActionEvent.ACTION, this);
        loBotonReal.setId(String.valueOf(plIndex));
        loBotonReal.setDisable(isDisable());
        aplicarBoton(loBotonReal, poBoton);
//        propiedadesBotonRecienCreado(loBotonReal);
        poPanel.getChildren().add(loBotonReal);
        return loBotonReal;
    }

    public void handle(final KeyEvent evt){
        if(evt.getCode()==KeyCode.ENTER){
            if (mbEsBusqueda) {
                accionAceptar();
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
            } else {
                if (getBtnEditar().isVisible()) {
                    accionEditar();
                }
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
            }            
        }
        if(evt.getCode()==KeyCode.ESCAPE){
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsESC);
            if (moSalir != null) {
                moSalir.salir();
            }
            
        }
        
    }

    /**
     * ejecuta la accion del boton
     * @param evt
     */
    @Override
    public void handle(final ActionEvent evt){
        if(!isDisable()){
            boolean lbContinuar = true;
            String lsAccionCommand = "";
            if(evt.getSource()!=null){
                if(Control.class.isAssignableFrom(evt.getSource().getClass())){
                    lsAccionCommand = ((Control)evt.getSource()).getId();
                }
                if(MenuItem.class.isAssignableFrom(evt.getSource().getClass())){
                    lsAccionCommand = ((MenuItem)evt.getSource()).getId();
                }
            }
            try {
                if (getBtnRefrescar() != null) {
                    if (evt.getSource() == getBtnRefrescar()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getBtnRefrescar().getId()))) {
                        lbContinuar = false;
                        if (moControlador.getConsulta() != null && moControlador.getConsulta().getList() != null && moControlador.getConsulta().getList().moServidor != null) {
                            moControlador.getConsulta().getList().moServidor.clearCache();
                        }
                        synchronized (JPanelGenericoAbstract.this) {
                            while (mbEjecutando) {
                                try {
                                    wait(1000);
                                } catch (Throwable e) {
                                }
                            }
                            mbEjecutando=true;
                            mostrarProceso();
                            Task loTask = new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    recuperarDatosBD();
                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    super.succeeded();                        
                                    try{
                                        moFiltroModelo.setDatos(getDatos(), moListernerFiltro, true, moTablaConfig);
                                        visualizarDatos();
                                        refrescar(); 
                                        ocultarProceso();
                                    }catch(Throwable e){
                                        JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(JPanelGenericoAbstract.this, e, null);
                                    }                  
                                    synchronized (JPanelGenericoAbstract.this) {
                                        mbEjecutando=false;
                                    }                                    
                                }

                                @Override
                                protected void failed() {
                                    super.failed(); //To change body of generated methods, choose Tools | Templates.
                                    JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(JPanelGenericoAbstract.this, getException(), null);
                                    succeeded();
                                }
                            };
                            Thread th = new Thread(loTask);
                            th.setDaemon(true);
                            th.start();
                            
                        }                        
                                                
                        
                        
                        

                    }
                }
                if (getbtnConfig() != null) {
                    if (evt.getSource() == getbtnConfig()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getbtnConfig().getId()))) {
                        lbContinuar = false;
                        accionconfig();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionConfig);
                    }
                }
                if (getBtnAceptar() != null) {
                    if (evt.getSource() == getBtnAceptar()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getBtnAceptar().getId()))) {
                        lbContinuar = false;
                        accionAceptar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
                    }
                }
                if (getBtnCancelar() != null) {
                    if (evt.getSource() == getBtnCancelar()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getBtnCancelar().getId()))) {
                        lbContinuar = false;
                        accionCancelar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionCancelar);
                    }
                }
                if (getBtnEditar() != null) {
                    if (evt.getSource() == getBtnEditar()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getBtnEditar().getId()))) {
                        lbContinuar = false;
                        accionEditar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
                    }
                }
                if (getBtnNuevo() != null) {
                    if (evt.getSource() == getBtnNuevo()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getBtnNuevo().getId()))) {
                        lbContinuar = false;
                        accionNuevo();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionNuevo);
                    }
                }
                if (getBtnBorrar() != null) {
                    if (evt.getSource() == getBtnBorrar()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getBtnBorrar().getId()))) {
                        lbContinuar = false;
                        accionBorrar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionBorrar);
                    }
                }
                if (getbtnMasFiltros()!= null) {
                    if (evt.getSource() == getbtnMasFiltros()
                            || (lsAccionCommand != null && !lsAccionCommand.equals("")
                            && lsAccionCommand.equalsIgnoreCase(getbtnMasFiltros().getId()))) {
                        lbContinuar = false;
                        accionMasFiltros();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionMasFiltro);
                    }
                }
                if (lbContinuar) {
                    if (lsAccionCommand.equals(JPanelGeneralFiltroModelo.mcsCambioFiltro)) {
                        refrescar();
                        comprobarSeleccion(getTabla());
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsCambioFiltro);
                    } else if (lsAccionCommand.equals(JPanelGenericoEvent.mcsESC)) {
                            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsESC);
                            if (moSalir != null) {
                                moSalir.salir();
                            }
                    } else if (lsAccionCommand.equals(JPanelGenericoEvent.mcsENTER)) {
                            if (mbEsBusqueda) {
                                accionAceptar();
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
                            } else {
                                if (getBtnEditar().isVisible()) {
                                    accionEditar();
                                }
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
                            }
                    } else if (lsAccionCommand.equals(JPanelGeneralFiltroModelo.mcsCambioFiltroCombo)) {
                        if(getcmbFiltros()!=null && !mbAnularEventos){
                            mbAnularEventos=true;
                            try{
                                moCMBFiltros.RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
                                moCMBFiltros.mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
                            }finally{
                                mbAnularEventos=false;
                            }
                        }
                        lbContinuar=false;
                    } else {
                            comprobarSeleccion(getTabla());
                            IBotonRelacionado loBoton = moBotones.get(Integer.parseInt(lsAccionCommand)).moBotonRelac;
                            loBoton.ejecutar(getSelectedRows());
                            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, loBoton.getNombre());
                    }
                }

            } catch (Throwable e) {
                e.printStackTrace();
                JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
            }
        }
    }

    protected void ponerPosicion() {
        try {
            //si la fila seleccionada es mayor que el tamaño de la tabla lo ponemos en la ultima fila
            if (getSelectedRow() >= getDatos().size() && getDatos().size() > 0) {
                getTabla().getSelectionModel().select(getDatos().size() - 1);
                //si la fila seleccionada es menor  que 0 y el tamaño de la tabla es mayor que 0, lo ponemos en la 1º fila
            } else if (getSelectedRow() < 0 && getDatos().size() > 0) {
                getTabla().getSelectionModel().clearAndSelect(0);
            }
            //elimina las selecciones no validas
            comprobarSeleccion(getTabla());

            setPosicion(String.valueOf(getTabla().getSelectionModel().getSelectedIndex() + 1));
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclPosicionarLinea, "");
        } catch (Throwable e1) {
            JDepuracion.anadirTexto(getClass().getName(), e1);
        }
    }


    @Override
    public void addListenerIPanelGenerico(IPanelGenericoListener poListener) {
        listenerList.add(poListener);
    }

    @Override
    public void removeListenerIPanelGenerico(IPanelGenericoListener poListener) {
        listenerList.remove(poListener);
    }

    protected void llamarListenerIPanelGenericoList(int plTipo, String psComando) {
        JPanelGenericoEvent loEvent = new JPanelGenericoEvent(this, plTipo, new int[]{getSelectedRow()}, psComando);
        for (int i = 0; i < listenerList.size(); i++) {
            IPanelGenericoListener loListener = (IPanelGenericoListener) listenerList.get(i);
            loListener.eventPanelGenerico(loEvent);
        }

    }
    ////////////////
    ///Interfaz IPanelGenerico
    ////////////////

    @Override
    public void refrescar() throws Exception{
        if(!isDisable()){
            if (Platform.isFxApplicationThread()) {
                refrescarInterno();
            } else {
                try {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            refrescarInterno();
                        }
                    });
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }

    private void refrescarInterno() {
        int lRow = getSelectedRow();
        getTabla().refrescar();
        if(lRow>=0){
            getTabla().getSelectionModel().clearAndSelect(lRow);
        }
        setTotal(String.valueOf(getDatos().size()));
        ponerPosicion();
        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclRefrescar, JPanelGenericoEvent.mcsAccionRefrescar);
    }

    @Override
    public void seleccionarTodo() {
        getTabla().getSelectionModel().selectAll();
    }

    @Override
    public void seleccionarFila(int plFila, boolean pbSeleccionar) {
        if (pbSeleccionar) {
            getTabla().getSelectionModel().select(plFila);
        } else {
//            getTabla().getSelectionModel().removeIndexInterval(plFila, plFila);
        }
    }

    @Override
    public ITablaConfig getTablaConfig() {
        return moTablaConfig;
    }

    @Override
    public IListaElementos getBotones() {
        return moBotones;
    }

    public static JListDatos getListOrdenado(final JListDatos poList, final JTablaConfigTablaConfig poConfig) throws CloneNotSupportedException, ECampoError {
        return getListOrdenado(poList, poConfig, false);
    }

    public static JListDatos getListOrdenado(final JListDatos poList, final JTablaConfigTablaConfig poConfig, final boolean pbCONColumnOcultas) throws CloneNotSupportedException, ECampoError {
        return JTablaConfig.getListOrdenado(poList, poConfig, pbCONColumnOcultas);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

    }// </editor-fold>                        

    private void accionCancelar() {
        try {
            moControlador.setIndexs(null);
            if(moControlador.getParametros().getCallBack()!=null){
                moControlador.getParametros().getCallBack().callBack(moControlador);
            }
            if (moSalir != null) {
                moSalir.salir();
            }
        } catch (Throwable e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }
    }

    private void accionAceptar() {
        try {
            moControlador.setIndexs(getSelectedRows());
            if(moControlador.getParametros().getCallBack()!=null){
                moControlador.getParametros().getCallBack().callBack(moControlador);
            }
            if (moSalir != null) {
                moSalir.salir();
            }
        } catch (Throwable e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }
    }

    private void cmbConfigItemStateChanged() {
        try {
            if (!mbAnularEventos) {
                getPanelGeneralFiltroLinea1().setAnularSetLong(true);
                moTablaConfig.setIndiceConfig(moCMBConfig.getFilaActual().msCampo(0));
                setFiltroCamposVisibles();
                getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            }
        } catch (Throwable e) {
            getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }

    }

    private void accionBorrar() {
        try {
            comprobarSeleccion(getTabla());
            final int[] lIndexs = getSelectedRows();
            if (lIndexs.length > 0) {
                JOptionPaneFX.showConfirmDialog(this,
                        (lIndexs.length > 1
                        ? "¿Estas seguro de borrar los registros actuales?"
                        : "¿Estas seguro de borrar el registro actual?")
                        , new Runnable() {

                    @Override
                    public void run() {
                        try{
                            JPanelGenericoAbstract.this.setDisable(true);
                            JProcesoEjecutarPAnelGeneAb loProc = new JProcesoEjecutarPAnelGeneAb(
                                    lIndexs, getTabla(), getDatos(), moControlador, JPanelGenericoAbstract.this);
        //OJO El JListDatos se mueve, el setIndex se usa mucho             
        //                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto()
        //                            .getThreadGroup().addProcesoYEjecutar(loProc);
                            loProc.procesar();
                        } catch (Throwable e) {
                            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                                    this, e, null);
                        }
                    }
                }, null);
            } else {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensaje(this, "No existe una fila actual", IMostrarPantalla.mclMensajeInformacion, null);
            }
        } catch (Throwable e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }

    }

    private void accionEditar() {
        try {
            int lSelect = getSelectedRow();
            if (lSelect >= 0
                    && lSelect < getDatos().size()) {
                getDatos().setIndex(lSelect);
                moControlador.editar(lSelect);
//                refrescar();
            } else {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensaje(this, "No existe una fila actual", IMostrarPantalla.mclMensajeInformacion, null);
            }
        } catch (Throwable e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }
    }

    private void accionNuevo() {
        try {
            moControlador.anadir();
//            refrescar();
        } catch (Throwable e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }
    }

    private void accionconfig() {
        try {
            getPanelGeneralFiltroLinea1().setAnularSetLong(true);
            JPanelConfig loPanelControlador = new JPanelConfig();
            loPanelControlador.setDatos(moTablaConfig, moDatos,moControlador.getParametros(), (ActionEvent t) -> {
                rellenarConfig();
                moCMBConfig.getComponente().getSelectionModel().select(0);
                cmbConfigItemStateChanged();
                setFiltroCamposVisibles();
                getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            });
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(loPanelControlador, 509, 450, getMostrarTipo());
            
        } catch (Throwable ex) {
            getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, ex, null);

        }
    }
    private void accionMasFiltros() {
        try{
            JPanelGeneralFiltro jPanelGeneralFiltroGUI = new JPanelGeneralFiltro();
            jPanelGeneralFiltroGUI.setDatos(moFiltroModelo);
            JMostrarPantallaParam loParam = new JMostrarPantallaParam(
                    jPanelGeneralFiltroGUI, 640, 450, getMostrarTipo(), "Filtro");
            loParam.setMaximizado(false);
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(loParam);
        }catch(Throwable e){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
                    this, e, null);
        }        
    }
    private void cmbTipoFiltroRapidoItemStateChanged() {
        try {
            if (moCMBTipoFiltroRapido.getFilaActual().msCampo(0).equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo)) {
//                ((CardLayout)JPanelTipoFiltroRapido.getLayout()).show(
//                        getPanelGeneralFiltroLinea1(), JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo);
                getPanelGeneralFiltroTodosCamp1().setVisible(false);
                getPanelGeneralFiltroLinea1().setVisible(true);
                getPanelGeneralFiltroLinea1().requestFocus();

            } else if (moCMBTipoFiltroRapido.getFilaActual().msCampo(0).equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos)) {
//                ((CardLayout)JPanelTipoFiltroRapido.getLayout()).show(
//                        jPanelGeneralFiltroTodosCamp1, JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos);
                getPanelGeneralFiltroLinea1().setVisible(false);
                getPanelGeneralFiltroTodosCamp1().setVisible(true);
                getPanelGeneralFiltroTodosCamp1().requestFocus();
            }
        } catch (Throwable e) {
        }

    }        

    /**
     * @return the moDatos
     */
    public JListDatos getDatos() {
        return moDatos;
    }


}
class JElementoBotonesA {

    public String msGrupo;
    public Pane moToolBar;

    JElementoBotonesA(String psGrupo, Pane poToolBar) {
        msGrupo = psGrupo;
        moToolBar = poToolBar;
    }
}

class EjecutarPanelGenericoA implements Runnable {

    private final JPanelGenericoAbstract moPanel;

    EjecutarPanelGenericoA(final JPanelGenericoAbstract poPanel) {
        moPanel = poPanel;
    }

    @Override
    public void run() {
        try {
            moPanel.recuperarYmostrarDatos();
        } catch (Throwable e) {
            JMsgBox.mensajeError(moPanel, e);
        }
    }
}

class JProcesoEjecutarPAnelGeneAb extends JProcesoAccionAbstracX {

    private final int[] malIndex;
    private final JTableViewCZ moTabla;
    private final JListDatos moDatos;
    private final IPanelControlador moControlador;
    private final JPanelGenericoAbstract moPanelGeneral;

    JProcesoEjecutarPAnelGeneAb(int[] lalIndex, JTableViewCZ tabla, JListDatos datos, IPanelControlador poControlador, JPanelGenericoAbstract poPanelGeneral) {
        malIndex = lalIndex;
        moTabla = tabla;
        moDatos = datos;
        moControlador=poControlador;
        moPanelGeneral=poPanelGeneral;
    }

    @Override
    public String getTitulo() {
        return "Borrando";
    }

    @Override
    public int getNumeroRegistros() {
        return malIndex.length;
    }

    @Override
    public void procesar() throws Throwable {
        try{
            for (int i = malIndex.length - 1; i >= 0 && !mbCancelado; i--) {
                mlRegistroActual=malIndex.length-i;
                if (malIndex[i] >= 0
                        && malIndex[i] < moDatos.size()) {
                    moDatos.setIndex(malIndex[i]);
                    moControlador.borrar(malIndex[i]);
                }
            }
            mlRegistroActual=malIndex.length;
            //se hace esto para q refresque bien
            moDatos.setIndex(moDatos.size() - 1);
            //cambia a seleccion de columnas
            int lIndice = malIndex[0] - 1;
            if (moTabla.getItems().size()> 0 && lIndice < 0) {
                lIndice = 0;
            }
            if (lIndice < 0) {
                moTabla.getSelectionModel().clearSelection();
            } else {
                moTabla.getSelectionModel().clearSelection();
                moTabla.getSelectionModel().select(lIndice);
            }
        }finally{
            moPanelGeneral.setDisable(false);
            moPanelGeneral.refrescar();
        }
    }

    @Override
    public String getTituloRegistroActual() {
        return "";
    }

    @Override
    public void mostrarMensaje(String psMensaje) {
    }
 }
