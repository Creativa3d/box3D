/*
 * JPanelConsultaPrincipal.java
 *
 * Created on 13 de septiembre de 2006, 18:29
 */
package utilesGUIx.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.text.Format;
import java.util.EventListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.JButtonCZ;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxUtil;
import utilesGUIx.JTableCZ;
import utilesGUIx.JTableRenderConColor;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.JPlugInUtilidades;


public abstract class JPanelGenericoAbstract extends javax.swing.JPanel implements ActionListener, IPanelGenerico, MouseListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;
    protected IPanelControlador moControlador;
    protected JTableModelDatosConFiltro moModeloDatos;
    protected JListDatos moDatos;
    protected ISalir moSalir;
    protected final IListaElementos moBotones = new JListaElementos();
    protected JTablaConfig moTablaConfig;
    protected boolean mbAnularEventos = true;
    protected IListaElementos moPanelRelaciones = new JListaElementos();
    protected JPanelGeneralPopUpMenu moPopUpMenu;
    private boolean mbEsBusqueda = false;
    //carga segundo plano
    protected Thread moThread;
    protected boolean mbEjecutando = false;
    private int lMax = 0;
    private Exception moError;
    private JMostrarPantallaParam moParam;
    protected JPanelGeneralFiltroModelo moFiltroModelo;
    
    private final ActionListenerCZ moListernerFiltro = new ActionListenerCZ() {
        @Override
        public void actionPerformed(ActionEventCZ e) {       
            JPanelGenericoAbstract.this.actionPerformed(new ActionEvent(this,0, e.getActionCommand()));    
        }
    };
    
    private JFieldDefs moCamposCache;
    /**
     * Creates new form JPanelBusqueda
     */
    public JPanelGenericoAbstract() {
        super();
        initComponents();
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
            getbtnConfig().addActionListener(this);
            getbtnConfig().setActionCommand(JPanelGenericoEvent.mcsAccionConfig);
        }
        if (getBtnNuevo() != null) {
            getBtnNuevo().addActionListener(this);
            getBtnNuevo().setActionCommand(JPanelGenericoEvent.mcsAccionNuevo);
        }
        if (getBtnEditar() != null) {
            getBtnEditar().addActionListener(this);
            getBtnEditar().setActionCommand(JPanelGenericoEvent.mcsAccionEditar);
        }
        if (getBtnBorrar() != null) {
            getBtnBorrar().addActionListener(this);
            getBtnBorrar().setActionCommand(JPanelGenericoEvent.mcsAccionBorrar);
        }
        if (getBtnRefrescar() != null) {
            getBtnRefrescar().addActionListener(this);
            getBtnRefrescar().setActionCommand(JPanelGenericoEvent.mcsAccionRefrescar);
        }
        if (getBtnAceptar() != null) {
            getBtnAceptar().addActionListener(this);
            getBtnAceptar().setActionCommand(JPanelGenericoEvent.mcsAccionAceptar);
        }
        if (getBtnCancelar() != null) {
            getBtnCancelar().addActionListener(this);
            getBtnCancelar().setActionCommand(JPanelGenericoEvent.mcsAccionCancelar);
        }
        moFiltroModelo = new JPanelGeneralFiltroModelo();     


        getTabla().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableDatosKeyPressed(evt);
            }
        });

        getTabla().addActionListener(this);
        getTabla().addMouseListener1(this);
        getTabla().getSelectionModel().addListSelectionListener(this);

        if (getcmbTipoFiltroRapido() != null) {
            getcmbTipoFiltroRapido().borrarTodo();
            getcmbTipoFiltroRapido().addLinea(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Filtro por campo"), JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo + JFilaDatosDefecto.mcsSeparacion1);
            getcmbTipoFiltroRapido().addLinea(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Filtro por todos campos"), JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos + JFilaDatosDefecto.mcsSeparacion1);
            getcmbTipoFiltroRapido().addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbTipoFiltroRapidoItemStateChanged(e);
                }
            });
        }
        if (getcmbConfig() != null) {
            getcmbConfig().addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbConfigItemStateChanged(e);
                }
            });
        }
        if(getcmbFiltros() != null){
            getcmbFiltros().addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    try{
                        if(!mbAnularEventos){
                            if(!moFiltroModelo.getTablaFiltro().moList.msTabla.equals(getcmbFiltros().getFilaActual().msCampo(0))){
                                moFiltroModelo.setFiltro(getcmbFiltros().getFilaActual().msCampo(0));
                                actionPerformed(new ActionEvent(getcmbFiltros(), 0, JPanelGeneralFiltroModelo.mcsCambioFiltro));
                            }
                        }
                    }catch(Exception ex){
                        utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(JPanelGenericoAbstract.this, ex, getClass().getName());
                    }
                }
            });
        }
        //OJO: se hace esto pq si no al reenlazar con un controlador nuevo, se quedan 
        //pillados los eventos con el moTablaConfig anterior, por lo q se duplican los eventos
        //ya q el objeto no se elimina de memoria al tener una referencia el JTable (Listener)
        //es decir, se debe hacer solo una vez la asignacion de listener, por lo q se
        //hace en el constructor
        moTablaConfig = new JTablaConfig(getTabla());

        if (getPanelGeneralFiltroLinea1() != null) {
            getPanelGeneralFiltroLinea1().setComponentes(
                    getTabla(),
                    getScrollPaneTablaDatos());
        }
        if (getPanelGeneralFiltroTodosCamp1() != null) {
            getPanelGeneralFiltroTodosCamp1().setComponentes(
                    getTabla(),
                    getScrollPaneTablaDatos(),
                    this);
        }

        moPopUpMenu = new JPanelGeneralPopUpMenu(this, getTabla(),
                getBtnAceptar(), getBtnBorrar(), getBtnCancelar(),
                getPanelGeneralFiltro1().jBtnCopiarTabla,
                getBtnEditar(), getBtnNuevo(), getBtnRefrescar());
        ((Component)getPanelInformacion()).setVisible(false);

//        getPanelGeneralFiltroLinea1().requestFocus();
//        getPanelGeneralFiltroLinea1().requestFocusInWindow();

    }

    public void aplicarBoton(final IBotonRelacionado poBoton) {
        JButton loComp = getBoton(poBoton);
        if (loComp != null) {
            aplicarBoton(loComp, poBoton);
        }
        moPopUpMenu.aplicarBoton(poBoton);
    }

    public JButton getBoton(final IBotonRelacionado poBoton) {
        JButton loBoton = null;
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
                if (moBotones.get(i) == poBoton) {
                    loBoton = getBotonPrivado(i);
                }
            }
        }

        return loBoton;
    }

    private JButton getBotonPrivado(int plIndex) {
        JButton loBoton = null;
        if (getPanelEditar() != null) {
            loBoton = (JButton) JPlugInUtilidades.getComponente(getPanelEditar(), String.valueOf(plIndex));
        }
        if (loBoton == null && getPanelRelacionadoGen() != null) {
            loBoton = (JButton) JPlugInUtilidades.getComponente(getPanelRelacionadoGen(), String.valueOf(plIndex));
        }
        return loBoton;
    }

    /**
     * Devuelve el componente JPanelGeneralFiltro de la clase hija
     * @return 
     */
    public abstract utilesGUIx.formsGenericos.JPanelGeneralFiltro getPanelGeneralFiltro1();

    /**
     * Devuelve el componente JPanelGeneralFiltroLinea de la clase hija
     * @return 
     */
    public abstract utilesGUIx.formsGenericos.JPanelGeneralFiltroLinea getPanelGeneralFiltroLinea1();

    /**
     * Devuelve el componente JPanelGeneralFiltroTodosCamp de la clase hija
     * @return 
     */
    public abstract utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp getPanelGeneralFiltroTodosCamp1();

    /**
     * Devuelve el componente Aceptar de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getBtnAceptar();

    /**
     * Devuelve el componente Borrar de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getBtnBorrar();

    /**
     * Devuelve el componente Cancelar de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getBtnCancelar();

    /**
     * Devuelve el componente Editar de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getBtnEditar();

    /**
     * Devuelve el componente Nuevo de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getBtnNuevo();

    /**
     * Devuelve el componente Refrescar de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getBtnRefrescar();

    /**
     * Devuelve el componente Config de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JButtonCZ getbtnConfig();

    /**
     * Devuelve el componente combo copnfig de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JComboBoxCZ getcmbConfig();

    /**
     * Devuelve el componente combo indica el tipo de busqueda de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JComboBoxCZ getcmbTipoFiltroRapido();
    /**
     * Devuelve el componente combo indica el tipo de busqueda de la clase hija
     * @return 
     */
    public abstract utilesGUIx.JComboBoxCZ getcmbFiltros();

    /**
     * Devuelve el componente Tabla de la clase hija
     * @return 
     */
    public abstract JTableCZ getTabla();

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
    public abstract Container crearContenedorBotones(String psGrupo);

    /**
     * se llamada despues de crear un boton
     * @param poBoton
     */
    public abstract void propiedadesBotonRecienCreado(JButtonCZ poBoton);

    /**
     * Scroll de la tabla datos
     * @return 
     */
    public abstract javax.swing.JScrollPane getScrollPaneTablaDatos();

    /**
     * Panel de los botones relacionados
     * @return 
     */
    public abstract javax.swing.JPanel getPanelRelacionadoGen();

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
    public abstract Container getPanelEditar();

    /**
     * Dimension por defecto del boton
     * @param poBoton
     * @return 
     */
    public abstract Dimension getDimensionDefecto(final IBotonRelacionado poBoton);

    /**
     * Visualiza el splash
     * @param pbVisible
     * @param psTexto
     */
    public abstract void setVisibleSplash(boolean pbVisible, String psTexto);

    @Override
    public int[] getSelectedRows() {
//        return getTabla().getSelectedRows();        
        int[] lalRows = getTabla().getSelectedRows();
        try {
            for (int i = 0; i < lalRows.length; i++) {
                lalRows[i] = getTabla().convertRowIndexToModel(lalRows[i]);
            }
        } catch (Throwable e) {
//            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        return lalRows;
    }

    public int getSelectedRow() {
        int lIndice = -1;
        int[] lalIndices = getSelectedRows();
        if (lalIndices != null && lalIndices.length > 0) {
            lIndice = lalIndices[0];
        }
        return lIndice;
    }

    public static void comprobarSeleccion(JTable jTableDatos) {
        int[] lalIndex = jTableDatos.getSelectedRows();
//en algunos look and feel deja el indice 0 puesto!!!!, por lo que es mejor borrar las selecciones incorrectas        
//        jTableDatos.getSelectionModel().clearSelection();
        for (int i = 0; i < lalIndex.length; i++) {
            if (lalIndex[i] >= jTableDatos.getModel().getRowCount() || lalIndex[i] < 0) {
                jTableDatos.getSelectionModel().removeSelectionInterval(lalIndex[i], lalIndex[i]);
            }
        }
    }

    public static void rellenarConfig(final JTablaConfig poTablaConfig, final JComboBoxCZ pocmbConfig) {
        //config
        pocmbConfig.borrarTodo();
        for (int i = 0; i < poTablaConfig.getConfigTabla().size(); i++) {
            pocmbConfig.addLinea(
                    poTablaConfig.getConfigTabla().getConfig(i).getNombre(),
                    poTablaConfig.getConfigTabla().getConfig(i).getNombre() + JFilaDatosDefecto.mcsSeparacion1);
        }
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
            synchronized (this) {
                while (mbEjecutando) {
                    try {
                        wait(1000);
                    } catch (Throwable e) {
                    }
                }
                if (SwingUtilities.isEventDispatchThread()) {
                    setVisibleSplash(true, "Procesando....");
                } else {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            setVisibleSplash(true, "Procesando....");
                        }
                    });
                }
                EjecutarPanelGenericoA loEjecutar = new EjecutarPanelGenericoA(this);
                JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto()
                            .getThreadGroup().addProcesoYEjecutar(loEjecutar, false);
            }
        } else {
            recuperarYmostrarDatos();
        }
    }

    protected void setFormatosCampos(Format[] paoFormatosCampos) {
        if (paoFormatosCampos != null) {
            TableColumnModel m = getTabla().getColumnModel();
            for (int i = 0; i < paoFormatosCampos.length; i++) {
                if (paoFormatosCampos[i] != null) {
                    m.getColumn(i).setCellRenderer(
                            new JTableRenderConColor(
                            moModeloDatos.getColumnClass(i),
                            getTabla(),
                            paoFormatosCampos[i]));
                }
            }
        }
    }

    protected void recuperarYmostrarDatos() throws Exception {
        if (moControlador.getParametros().isActivado()) {
            recuperarDatosBD();
        }
        if (SwingUtilities.isEventDispatchThread()) {
            try {
                mostrarDatos();
            } catch (Throwable ex) {
                throw new Exception(ex);
            }
        } else {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        mostrarDatos();
                    } catch (Throwable ex) {
                        moError = new Exception(ex);
                    }
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
                if (JGUIxConfigGlobal.getInstancia().getPlugInFactoria() != null) {
                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                            JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto(),
                            moControlador);
                }
                //            System.out.println("recuperarDatos");
                mbAnularEventos = true;
                moTablaConfig.setInicializado(false);
                moTablaConfig.setAnularEventos(true);
                synchronized (this) {
                    mbEjecutando = true;
                }
                setVisibleSplash(true, "Procesando....");
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
                        if (loBoton!=null && loBoton.isActivo()) {
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
                    getPanelRelacionadoGen().setVisible(true);
                    moPanelRelaciones.clear();
                    getPanelRelacionadoGen().removeAll();
                    crearBotonesSoloRelacionados(loBotonesGenerales.getListaBotones());
                } else {
                    getPanelRelacionadoGen().setVisible(false);
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
                getPanelGeneralFiltro1().setDatos(moFiltroModelo);
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
                            getcmbTipoFiltroRapido().setValueTabla(moControlador.getParametros().msTipoFiltroRapido + JFilaDatosDefecto.mcsSeparacion1);
                            getcmbTipoFiltroRapido().setValorOriginal(null);
                        }
                    }
                }
                
                //visualizamos los datos 
                visualizarDatos();
                moModeloDatos.mbEditable = false;

                //leemos la config. guardada previamente
                if (getPanelGeneralFiltroLinea1() != null) {
                    getPanelGeneralFiltroLinea1().setAnularSetLong(true);
                }
                String lsAux = JTablaConfig.mcsNombreDefecto;
                try {
                    lsAux = moTablaConfig.getIndiceConfig();
                } catch (Throwable e) {
                }
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
                getcmbConfig().setValueTabla(lsAux + JFilaDatosDefecto.mcsSeparacion1);

                if (loBotonesGenerales.getFiltro().isActivo()) {
                    getPanelGeneralFiltroLinea1().setAnularSetLong(false);
                    getPanelGeneralFiltroLinea1().setDatos(
                            moFiltroModelo,
                            moTablaConfig.getConfigTabla().getCampoBusqueda());
                    setFiltroCamposVisibles();
                    getPanelGeneralFiltroTodosCamp1().setDatos(
                            moFiltroModelo);

                    cmbTipoFiltroRapidoItemStateChanged(null);
                    
                    moFiltroModelo.setFiltro(moFiltroModelo.getTablaConfig().getConfigTabla().getFiltroDefecto());
                    if (getcmbFiltros() != null) {
                        getcmbFiltros().RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
                        getcmbFiltros().mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
                    }
                    
                }

                moPopUpMenu.configurarPopup(
                        moBotones, loBotonesGenerales,
                        this, getDatos(),
                        moControlador.getParametros().getColumnasVisiblesConfig());
                moPopUpMenu.setVisibleCampos(getCamposVisibles());


            } finally {
                synchronized (this) {
                    mbEjecutando = false;
                    notifyAll();
                }
                setVisibleSplash(false, null);
                this.validate();
                if (moControlador.getParametros().getBotonesGenerales().getFiltro().isActivo()) {
                    cmbTipoFiltroRapidoItemStateChanged(null);
                }
                this.repaint();
                moTablaConfig.setAnularEventos(false);
                mbAnularEventos = false;
            }
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclFinInicializar, "");
        } else {
            setVisibleSplash(true,
                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(
                    "No hay suficientes permisos para visualizar estos datos"));
        }
    }

    protected void recuperarDatosBD() throws Exception {
        if (moControlador.getParametros().isActivado()) {

            if (moControlador.getParametros() == null || moControlador.getParametros().isRefrescarListDatos()) {
                moDatos = moControlador.getDatos();
            } else {
                moDatos = moControlador.getConsulta().getList();
            }
            moCamposCache = moDatos.getFields().Clone();
        }
    }

    protected Container getContainer(final String psGrupo) {
        Container loPanel = null;
        for (int i = 0; i < moPanelRelaciones.size() && loPanel == null; i++) {
            JElementoBotonesA loElem = (JElementoBotonesA) moPanelRelaciones.get(i);
            if (loElem.msGrupo.compareTo(psGrupo) == 0) {
                loPanel = loElem.moToolBar;
            }
        }
        if (loPanel == null) {
            loPanel = crearContenedorBotones(psGrupo);
            getPanelRelacionadoGen().add(loPanel);
            moPanelRelaciones.add(new JElementoBotonesA(psGrupo, loPanel));
        }
        return loPanel;
    }

    protected boolean[] getCamposVisibles() {
        boolean[] labVisibles = new boolean[getDatos().getFields().count()];
        for (int i = 0; i < labVisibles.length; i++) {
            try {
                labVisibles[Integer.valueOf(moTablaConfig.getConfigTablaConcreta().getColumna(i).getNombre()).intValue()] = moTablaConfig.getConfigTablaConcreta().getColumna(i).getLong() > 0;
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
        rellenarConfig(moTablaConfig, getcmbConfig());
        mbAnularEventos = lbAux;
    }

    protected void establecerBotones(final JPanelGeneralBotones poBotonesGenerales, final IListaElementos poBotonesRelacPrinci) {
        aplicarBoton(getBtnNuevo(), poBotonesGenerales.getNuevo());
        aplicarBoton(getBtnBorrar(), poBotonesGenerales.getBorrar());
        aplicarBoton(getBtnEditar(), poBotonesGenerales.getEditar());
        aplicarBoton(getBtnRefrescar(), poBotonesGenerales.getRefrescar());
        aplicarBoton(getBtnAceptar(), poBotonesGenerales.getAceptar());
        aplicarBoton(getBtnCancelar(), poBotonesGenerales.getCancelar());
        aplicarBoton(getPanelGeneralFiltro1().jBtnCopiarTabla, poBotonesGenerales.getCopiarTabla());
        mbEsBusqueda = poBotonesGenerales.getAceptar().isActivo();

        //vemos los componentes que hay por defecto
        if (lMax == 0) {
            lMax = getPanelEditar().getComponentCount();
        }
        //borramos todos los botones menos los generales de arriba q son los 6 primeros
        for (int i = getPanelEditar().getComponentCount() - 1; i >= lMax; i--) {
            getPanelEditar().remove(getPanelEditar().getComponent(i));
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

//    protected void establecerValoresBotones(final JButton poBoton, final IBotonRelacionado poProp) {
//        poBoton.setVisible(poProp.isActivo());
//        if (poProp.getCaption() != null) {
//            poBoton.setText(poProp.getCaption());
//        }
//        if (poProp.getIcono() != null) {
//            poBoton.setIcon(poProp.getIcono());
//        }
//        if (poProp.getDimension() != null) {
//            poBoton.setPreferredSize(poProp.getDimension());
//        } else if (getDimensionDefecto(null) != null) {
//            poBoton.setPreferredSize(getDimensionDefecto(null));
//        }
//        poBoton.setPreferredSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
//        poBoton.setMinimumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
//        poBoton.setMaximumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
//    }
//    
    protected void aplicarBoton(JButton poBoton, IBotonRelacionado poProp) {
        poBoton.setVisible(poProp.isActivo());
        if (poProp.getCaption() != null) {
            poBoton.setText(poProp.getCaption());
        }
        if (poProp.getIcono() != null) {
            try{
                poBoton.setIcon((Icon)poProp.getIcono());
            }catch(Exception e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        if (poProp.getDimension() != null) {
            poBoton.setPreferredSize(new Dimension(poProp.getDimension().width, poProp.getDimension().height ));
            poBoton.setMinimumSize(poBoton.getPreferredSize());
            poBoton.setMaximumSize(poBoton.getPreferredSize());
        } else if (getDimensionDefecto(poProp) != null) {
            poBoton.setPreferredSize(getDimensionDefecto(poProp));
            poBoton.setPreferredSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
            poBoton.setMinimumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
            poBoton.setMaximumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        }else{
            poBoton.setPreferredSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
            poBoton.setMinimumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
            poBoton.setMaximumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        }
    }

    protected void visualizarDatos() throws Throwable {
        //si ya existia eliminar listener
        if (moModeloDatos != null) {
            moModeloDatos.removeMouseListenerToHeaderInTable(getTabla());
        }
        moFiltroModelo.limpiar();
        //creamos el table model a partir de los datps
        moModeloDatos = new JTableModelDatosConFiltro(getDatos(), moFiltroModelo.getTablaFiltro());

        getTabla().setModel(moModeloDatos);
        setFormatosCampos(moControlador.getParametros().getFormatosCampos());
        moModeloDatos.addMouseListenerToHeaderInTable(getTabla());
        //solo se puede seleccionar una fila
        if (IEjecutarExtend.class.isAssignableFrom(moControlador.getClass())) {
            getTabla().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        } else {
            getTabla().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        //seleccionamos la primera fila por defecto
        if (getTabla().getRowCount() > 0) {
            getTabla().setRowSelectionInterval(0, 0);
        }
        setTotal(String.valueOf(getDatos().size()));
        ponerPosicion();

    }
    //crear botones relacionados

    protected void crearBotonesRelacionados(final Container poPanel, final IListaElementos poBotones) {
        int lIndexInicial = moBotones.size();
        for (int i = 0; i < poBotones.size(); i++) {
            moBotones.add(poBotones.get(i));
        }
        for (int i = lIndexInicial; i < moBotones.size(); i++) {
            crearBoton(poPanel, (IBotonRelacionado) moBotones.get(i), i);
        }
    }

    protected void crearBotonesSoloRelacionados(final IListaElementos poBotones) {
        for (int i = 0; i < poBotones.size(); i++) {
            IBotonRelacionado loBoton = (IBotonRelacionado) poBotones.get(i);
            if (!loBoton.isEsPrincipal()) {
                moBotones.add(loBoton);
                crearBoton(getContainer(loBoton.getGrupo()), loBoton, moBotones.size() - 1);
            }
        }
    }

    //crea un boton
    protected void crearBoton(final Container poPanel, final IBotonRelacionado poBoton, final int plIndex) {
        JButtonCZ loBotonReal = new JButtonCZ(poBoton.getCaption());
        loBotonReal.addActionListener(this);
        loBotonReal.setName(String.valueOf(plIndex));
        loBotonReal.setActionCommand(String.valueOf(plIndex));
        loBotonReal.setEnabled(isEnabled());
        aplicarBoton(loBotonReal, poBoton);
        propiedadesBotonRecienCreado(loBotonReal);
        poPanel.add(loBotonReal);
    }


    /**
     * ejecuta la accion del boton
     * @param evt
     */
    @Override
    public void actionPerformed(final ActionEvent evt) {
        if(isEnabled()){
            boolean lbContinuar = true;
            try {
                if (getBtnRefrescar() != null) {
                    if (evt.getSource() == getBtnRefrescar()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getBtnRefrescar().getActionCommand()))) {
                        lbContinuar = false;
                        if (moControlador.getConsulta() != null && moControlador.getConsulta().getList() != null && moControlador.getConsulta().getList().moServidor != null) {
                            moControlador.getConsulta().getList().moServidor.clearCache();
                        }
                        recuperarYmostrarDatos();
                    }
                }
                if (getbtnConfig() != null) {
                    if (evt.getSource() == getbtnConfig()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getbtnConfig().getActionCommand()))) {
                        lbContinuar = false;
                        accionconfig(null);
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionConfig);
                    }
                }
                if (getBtnAceptar() != null) {
                    if (evt.getSource() == getBtnAceptar()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getBtnAceptar().getActionCommand()))) {
                        lbContinuar = false;
                        accionAceptar(null);
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
                    }
                }
                if (getBtnCancelar() != null) {
                    if (evt.getSource() == getBtnCancelar()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getBtnCancelar().getActionCommand()))) {
                        lbContinuar = false;
                        accionCancelar(null);
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionCancelar);
                    }
                }
                if (getBtnEditar() != null) {
                    if (evt.getSource() == getBtnEditar()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getBtnEditar().getActionCommand()))) {
                        lbContinuar = false;
                        accionEditar(null);
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
                    }
                }
                if (getBtnNuevo() != null) {
                    if (evt.getSource() == getBtnNuevo()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getBtnNuevo().getActionCommand()))) {
                        lbContinuar = false;
                        accionNuevo(null);
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionNuevo);
                    }
                }
                if (getBtnBorrar() != null) {
                    if (evt.getSource() == getBtnBorrar()
                            || (evt.getActionCommand() != null && !evt.getActionCommand().equals("")
                            && evt.getActionCommand().equalsIgnoreCase(getBtnBorrar().getActionCommand()))) {
                        lbContinuar = false;
                        accionBorrar(null);
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionBorrar);
                    }
                }
                if (lbContinuar) {
                    if (evt.getActionCommand().equals(JPanelGeneralFiltroModelo.mcsCambioFiltro)) {
                        
                        refrescar();
                        comprobarSeleccion(getTabla());
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsCambioFiltro);
                        lbContinuar=false;
                    } 
                    
                }
                if (lbContinuar) {
                    if (evt.getActionCommand().equals(JPanelGeneralFiltroModelo.mcsCambioFiltroCombo)) {
                        if(getcmbFiltros()!=null && !mbAnularEventos){
                            System.out.println(JPanelGeneralFiltroModelo.mcsCambioFiltroCombo);
                            mbAnularEventos=true;
                            try{
                                getcmbFiltros().RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
                                getcmbFiltros().mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
                            }finally{
                                mbAnularEventos=false;
                            }
                        }
                        lbContinuar=false;
                    } 
                    
                }
                if (lbContinuar) {
                    if (evt.getSource() == getTabla()) {
                        if (evt.getActionCommand().equals(getTabla().mcsESC)) {
                            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JTableCZ.mcsESC);
                            if (moSalir != null) {
                                moSalir.salir();
                            }
                        } else {
                            if (mbEsBusqueda) {
                                accionAceptar(null);
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
                            } else {
                                if (getBtnEditar().isVisible()) {
                                    accionEditar(null);
                                }
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
                            }
                        }
                    } else {
                        comprobarSeleccion(getTabla());
                        IBotonRelacionado loBoton = ((IBotonRelacionado) moBotones.get(Integer.valueOf(evt.getActionCommand()).intValue()));
                        loBoton.ejecutar(getSelectedRows());
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, loBoton.getNombre());
                    }
                }
            } catch (Throwable e) {
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
            }
        }
    }

    protected void ponerPosicion() {
        try {
//el problema de este código es que no permite la seleccion de varias filas, siempre deja una
//supongo que lo que se quiere conseguir es que el getSelectedRow siempre este en una fila valida            
//            int liFilaActual = Math.min(getDatos().size() - 1, getSelectedRow());
//            if (liFilaActual < 0) {
//                getTabla().getSelectionModel().clearSelection();
//            } else {
//                getTabla().getSelectionModel().setSelectionInterval(liFilaActual, liFilaActual);
//            }
            //si la fila seleccionada es mayor que el tamaño de la tabla lo ponemos en la ultima fila
            if (getSelectedRow() >= getDatos().size() && getDatos().size() > 0) {
                getTabla().getSelectionModel().setSelectionInterval(getDatos().size() - 1, getDatos().size() - 1);
                //si la fila seleccionada es menor  que 0 y el tamaño de la tabla es mayor que 0, lo ponemos en la 1º fila
            } else if (getSelectedRow() < 0 && getDatos().size() > 0) {
                getTabla().getSelectionModel().setSelectionInterval(0, 0);
            }
            //elimina las selecciones no validas
            comprobarSeleccion(getTabla());

            setPosicion(String.valueOf(getSelectedRow() + 1));
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclPosicionarLinea, "");
        } catch (Throwable e1) {
            JDepuracion.anadirTexto(getClass().getName(), e1);
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!mbAnularEventos) {
            ponerPosicion();
        }
    }

    public void mouseReleased(final MouseEvent e) {
        //vacio
    }

    public void mousePressed(final MouseEvent e) {
        //vacio
    }

    public void mouseExited(final MouseEvent e) {
        //vacio
    }

    public void mouseEntered(final MouseEvent e) {
        //vacio
    }

    public void mouseClicked(final MouseEvent e) {
//        ponerPosicion();
        if (e.getClickCount() > 1) {
            actionPerformed(new ActionEvent(getTabla(), 0, ""));
        }
    }

    public void addListenerIPanelGenerico(IPanelGenericoListener poListener) {
        listenerList.add(IPanelGenericoListener.class, poListener);
    }

    public void removeListenerIPanelGenerico(IPanelGenericoListener poListener) {
        listenerList.remove(IPanelGenericoListener.class, poListener);
    }

    protected void llamarListenerIPanelGenericoList(int plTipo, String psComando) {
        JPanelGenericoEvent loEvent = new JPanelGenericoEvent(this, plTipo, new int[]{getSelectedRow()}, psComando);
        EventListener[] loList = JGUIxUtil.getListeners(listenerList, IPanelGenericoListener.class);
        for (int i = 0; i < loList.length; i++) {
            IPanelGenericoListener loListener = (IPanelGenericoListener) loList[i];
            loListener.eventPanelGenerico(loEvent);
        }

    }
    ////////////////
    ///Interfaz IPanelGenerico
    ////////////////

    public void refrescar() {
        if(isEnabled()){
            if (SwingUtilities.isEventDispatchThread()) {
                try {
                    refrescarInterno();
                } catch (Throwable ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            try {
                                refrescarInterno();
                            } catch (Throwable ex) {
                                JDepuracion.anadirTexto(getClass().getName(), ex);
                            }
                        }
                    });
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }
    private void refrescarInterno() throws Throwable {
        //EN SIGA DE VEZ ENCUANDO NO  VA BIEN
//        getTabla().revalidate();
//        getTabla().repaint();
//        jScrollPaneTablaDatos.revalidate();
//        jScrollPaneTablaDatos.repaint();        
        if(isCamposCambiados()){
            int lSelect = getSelectedRow();
            mostrarDatos();
            getTabla().getSelectionModel().setSelectionInterval(lSelect, lSelect);
            moCamposCache = moDatos.getFields().Clone();
        } else {
            getTabla().updateUI();
            setTotal(String.valueOf(getDatos().size()));
            ponerPosicion();
        }

        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclRefrescar, JPanelGenericoEvent.mcsAccionRefrescar);
    }

    private boolean isCamposCambiados(){
        boolean lbResult = false;
        if(moCamposCache!=null){
            if(moCamposCache.size()!=moDatos.getFields().size()){
                lbResult = true;
            }else{
                for(int i = 0 ; i < moCamposCache.size() && !lbResult; i++){
                    JFieldDef loCache = moCamposCache.get(i);
                    JFieldDef loCampo = moDatos.getFields(i);

                    if(!loCache.getNombre().equalsIgnoreCase(loCampo.getNombre())){
                        lbResult = true;
                    }
                    if(loCache.getTipo() != loCampo.getTipo() ){
                        lbResult = true;
                    }

                }
            }
        }
        return lbResult;
    }

    @Override
    public void seleccionarTodo() {
        getTabla().selectAll();
    }

    @Override
    public void seleccionarFila(int plFila, boolean pbSeleccionar) {
        if (pbSeleccionar) {
            getTabla().getSelectionModel().addSelectionInterval(plFila, plFila);
        } else {
            getTabla().getSelectionModel().removeIndexInterval(plFila, plFila);
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

    @Override
    public void setEnabled(boolean enabled) {
        try {
            JPanelGENERALBASE.setBloqueoControlesContainer(this, !enabled);
            getScrollPaneTablaDatos().setEnabled(true);
            getTabla().setEnabled(enabled);
            moModeloDatos.setEnabled(enabled);
//            getPanelEditar().setEnabled(enabled);
//            getPanelRelacionadoGen().setEnabled(enabled);
            super.setEnabled(enabled);
        } catch (Exception ex) {
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSplash = new javax.swing.JPanel();
        lblSplashLabel = new javax.swing.JLabel();

        jPanelSplash.setLayout(new java.awt.BorderLayout());

        lblSplashLabel.setFont(new java.awt.Font("Tahoma", 0, 10));
        lblSplashLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSplashLabel.setText("procesando...");
        jPanelSplash.add(lblSplashLabel, java.awt.BorderLayout.CENTER);

        setMinimumSize(new java.awt.Dimension(100, 25));
        setPreferredSize(new java.awt.Dimension(600, 200));
        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void accionCancelar(java.awt.event.ActionEvent evt) {
        try {
            moControlador.setIndexs(null);
            if(moControlador.getParametros().getCallBack()!=null){
                moControlador.getParametros().getCallBack().callBack(moControlador);
            }
            if (moSalir != null) {
                moSalir.salir();
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void accionAceptar(java.awt.event.ActionEvent evt) {
        try {
            moControlador.setIndexs(getSelectedRows());
            if(moControlador.getParametros().getCallBack()!=null){
                moControlador.getParametros().getCallBack().callBack(moControlador);
            }
            if (moSalir != null) {
                moSalir.salir();
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void cmbConfigItemStateChanged(java.awt.event.ItemEvent evt) {
        try {
            if (!mbAnularEventos) {
                getPanelGeneralFiltroLinea1().setAnularSetLong(true);
                moTablaConfig.setIndiceConfig(getcmbConfig().getFilaActual().msCampo(0));
                setFiltroCamposVisibles();
                getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            }
        } catch (Throwable e) {
            getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }

    private void accionBorrar(java.awt.event.ActionEvent evt) {
        try {
            comprobarSeleccion(getTabla());
            final int[] lIndexs = getSelectedRows();
            if (lIndexs.length > 0) {
                if (JOptionPane.showConfirmDialog(this,
                        (lIndexs.length > 1
                        ? "¿Estas seguro de borrar los registros actuales?"
                        : "¿Estas seguro de borrar el registro actual?"),
                        "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    this.setEnabled(false);
                    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    JProcesoEjecutarPAnelGeneAb loProc = new JProcesoEjecutarPAnelGeneAb(
                            lIndexs, getTabla(), getDatos(), moControlador, this);
//OJO El JListDatos se mueve, el setIndex se usa mucho             
//                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto()
//                            .getThreadGroup().addProcesoYEjecutar(loProc);
                    loProc.procesar();
                    
                }
            } else {
                JOptionPane.showMessageDialog(this, "No existe una fila actual");
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }

    private void accionEditar(java.awt.event.ActionEvent evt) {
        try {
            int lSelect = getSelectedRow();
            if (lSelect >= 0
                    && lSelect < getDatos().size()) {
                getDatos().setIndex(lSelect);
                moControlador.editar(lSelect);
//                refrescar();
            } else {
                JOptionPane.showMessageDialog(this, "No existe una fila actual");
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void accionNuevo(java.awt.event.ActionEvent evt) {
        try {
            moControlador.anadir();
//            refrescar();
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void accionconfig(java.awt.event.ActionEvent evt) {
        try {
            getPanelGeneralFiltroLinea1().setAnularSetLong(true);
            JPanelConfig loPanel = new JPanelConfig();
            loPanel.setDatos(
                    moTablaConfig, moModeloDatos,
                    moControlador.getParametros());
            
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(loPanel, 509, 450, getMostrarTipo());
            rellenarConfig();
            cmbConfigItemStateChanged(null);
            setFiltroCamposVisibles();
            getPanelGeneralFiltroLinea1().setAnularSetLong(false);
        } catch (Throwable ex) {
            getPanelGeneralFiltroLinea1().setAnularSetLong(false);
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());

        }
    }

    private void cmbTipoFiltroRapidoItemStateChanged(java.awt.event.ItemEvent evt) {
        try {
            if (getcmbTipoFiltroRapido().getFilaActual().msCampo(0).equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo)) {
//                ((CardLayout)JPanelTipoFiltroRapido.getLayout()).show(
//                        getPanelGeneralFiltroLinea1(), JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo);
                getPanelGeneralFiltroTodosCamp1().setVisible(false);
                getPanelGeneralFiltroLinea1().setVisible(true);
                getPanelGeneralFiltroLinea1().requestFocus();
                getPanelGeneralFiltroLinea1().requestFocusInWindow();
            } else if (getcmbTipoFiltroRapido().getFilaActual().msCampo(0).equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos)) {
//                ((CardLayout)JPanelTipoFiltroRapido.getLayout()).show(
//                        jPanelGeneralFiltroTodosCamp1, JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos);
                getPanelGeneralFiltroLinea1().setVisible(false);
                getPanelGeneralFiltroTodosCamp1().setVisible(true);
                getPanelGeneralFiltroTodosCamp1().requestFocus();
                getPanelGeneralFiltroTodosCamp1().requestFocusInWindow();
            }
        } catch (Throwable e) {
        }

    }

    private void jTableDatosKeyPressed(java.awt.event.KeyEvent evt) {

        if (!moControlador.getParametros().getBotonesGenerales().getCopiarTabla().isActivo()) {
            if (evt.isControlDown()) {
                evt.setKeyCode(0);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelSplash;
    private javax.swing.JLabel lblSplashLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the moDatos
     */
    public JListDatos getDatos() {
        return moDatos;
    }
    

    class JElementoBotonesA {

        public String msGrupo;
        public Container moToolBar;

        public JElementoBotonesA(String psGrupo, Container poToolBar) {
            msGrupo = psGrupo;
            moToolBar = poToolBar;
        }
    }

    class EjecutarPanelGenericoA extends JProcesoAccionAbstracX {

        private JPanelGenericoAbstract moPanel;

        public EjecutarPanelGenericoA(final JPanelGenericoAbstract poPanel) {
            moPanel = poPanel;
        }

        public String getTitulo() {
            return "Procesando datos";
        }

        public int getNumeroRegistros() {
            return -1;
        }

        public void procesar() throws Throwable {
            try {
                moPanel.recuperarYmostrarDatos();
            } catch (Throwable e) {
                moPanel.llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclError, e.toString());
                JMsgBox.mensajeErrorYLog(moPanel, e);
            }
        }

        public void mostrarMensaje(String psMensaje) {
        }
    }

    class JProcesoEjecutarPAnelGeneAb extends JProcesoAccionAbstracX {

        private final int[] malIndex;
        private final JTableCZ moTabla;
        private final JListDatos moDatos;
        private final IPanelControlador moControlador;
        private final JPanelGenericoAbstract moPanelGeneral;

        JProcesoEjecutarPAnelGeneAb(int[] lalIndex, JTableCZ tabla, JListDatos datos, IPanelControlador poControlador, JPanelGenericoAbstract poPanelGeneral) {
            malIndex = lalIndex;
            moTabla = tabla;
            moDatos = datos;
            moControlador=poControlador;
            moPanelGeneral=poPanelGeneral;
        }

        public String getTitulo() {
            return "Borrando";
        }

        public int getNumeroRegistros() {
            return malIndex.length;
        }

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
                if (moTabla.getRowCount() > 0 && lIndice < 0) {
                    lIndice = 0;
                }
                if (lIndice < 0) {
                    moTabla.clearSelection();
                } else {
                    moTabla.clearSelection();
                    moTabla.getSelectionModel().setSelectionInterval(lIndice, lIndice);
                }
            }finally{
                moPanelGeneral.setCursor(Cursor.getDefaultCursor());
                moPanelGeneral.setEnabled(true);
                moPanelGeneral.refrescar();
            }
        }

        public String getTituloRegistroActual() {
            return "";
        }

        public void mostrarMensaje(String psMensaje) {
        }
    }

}