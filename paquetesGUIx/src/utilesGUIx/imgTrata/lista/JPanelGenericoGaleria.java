/*
 * JPanelBusqueda.java
 *
 * Created on 6 de septiembre de 2004, 12:07
 */

package utilesGUIx.imgTrata.lista;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesDoc.tablasExtend.JTEEDOCUMENTOS;
import utilesGUIx.JButtonCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.JGUIxUtil;
import utilesGUIx.JTableCZ;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.IPanelGenerico;
import utilesGUIx.formsGenericos.IPanelGenericoListener;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.ITablaConfig;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.JPanelGeneralFiltro;
import utilesGUIx.formsGenericos.JPanelGeneralPopUpMenu;
import utilesGUIx.formsGenericos.JPanelGenericoEvent;
import utilesGUIx.formsGenericos.JTablaConfig;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;


/**representa una panel de búsqueda/principal, con posibilidades de filtros, botones de edicón estandar y botones dinamicos personalizados*/
public class JPanelGenericoGaleria extends JPanel  implements ActionListener, IPanelGenerico, MouseListener, ListSelectionListener {
    private static final long serialVersionUID = 1L;

    public static final int mclTipo = 0;
    public static Dimension moDimensionRelacionesDefecto = new Dimension(160, 29);

    protected IPanelControlador moControlador;
    protected JListDatos moDatos;
    protected ISalir moSalir;

    protected final IListaElementos moBotones = new JListaElementos();

    protected boolean mbAnularEventos = true;
    protected IListaElementos moPanelRelaciones = new JListaElementos();

    protected JPanelGeneralPopUpMenu moPopUpMenu;

    private boolean mbEsBusqueda = false;

    //carga segundo plano
    protected Thread moThread;
    protected boolean mbEjecutando = false;

//    private int mlImagen;
//    private int mlDescrip;
    private IImagenFactory moImagen;
    private JTablaConfig moTablaConfig;
    
    /** Creates new form JPanelBusqueda */
    public JPanelGenericoGaleria() {
        super();
        initComponents();
        jButtonAceptar.setMnemonic('a');
        jButtonCancelar.setMnemonic('c');
        jBtnBorrar.setMnemonic('b');
        jBtnNuevo.setMnemonic('n');
        jBtnEditar.setMnemonic('e');
        jBtnRefrescar.setMnemonic('r');


        inicializar();
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelGenerico");

    }
    public void inicializar(){

        getBtnNuevo().addActionListener(this);
        getBtnEditar().addActionListener(this);
        getBtnBorrar().addActionListener(this);
        getBtnRefrescar().addActionListener(this);
        getBtnAceptar().addActionListener(this);
        getBtnCancelar().addActionListener(this);

        getBtnRefrescar().setActionCommand(JPanelGenericoEvent.mcsAccionRefrescar);
        getBtnNuevo().setActionCommand(JPanelGenericoEvent.mcsAccionNuevo);
        getBtnEditar().setActionCommand(JPanelGenericoEvent.mcsAccionEditar);
        getBtnBorrar().setActionCommand(JPanelGenericoEvent.mcsAccionBorrar);
        getBtnAceptar().setActionCommand(JPanelGenericoEvent.mcsAccionAceptar);
        getBtnCancelar().setActionCommand(JPanelGenericoEvent.mcsAccionCancelar);


        getTabla().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableDatosKeyPressed(evt);
            }
        });

        getTabla().addActionListener(this);
        getTabla().addMouseListener(this);
        getTabla().getSelectionModel().addListSelectionListener(this);

        moPopUpMenu = new JPanelGeneralPopUpMenu(this, getTabla(),
                getBtnAceptar(), getBtnBorrar(), getBtnCancelar(),
                null,
                getBtnEditar(), getBtnNuevo(), getBtnRefrescar()
                );
        ((Component)getPanelInformacion()).setVisible(false);

        moTablaConfig = new JTablaConfig(null);

    }

    public static void comprobarSeleccion(JPanelListaImagenes jTableDatos){
        int[] lalIndex = jTableDatos.getSelectedRows();
        jTableDatos.getSelectionModel().clearSelection();
        for(int i = 0; i < lalIndex.length; i++){
            if(lalIndex[i]<jTableDatos.getModel().size()){
                jTableDatos.getSelectionModel().addSelectionInterval(lalIndex[i], lalIndex[i]);
            }
        }
    }


    /**
     * Devolvemos el controlador del panel
     */
    public IPanelControlador getControlador(){
        return moControlador;
    }
    /**
     * Establecemos el controlador del panel
     * @param poControlador controlador
     * @throws Exception error
     */
    public void setControlador(final IPanelControlador poControlador, int plImagen, int plDescrip) throws Exception {
        setControlador(poControlador, null,plImagen, plDescrip);
    }

    /**
     * Establecemos el controlador del panel
     * @param poControlador controlador
     * @param poSalir clase padre, puede ser un frame, internalframe,dialog, pero que cumpla el interfaz salir
     * @throws Exception error
     */
    public void setControlador(final IPanelControlador poControlador, final ISalir poSalir, int plImagen, int plDescrip) throws Exception {
        setControlador(poControlador, poSalir, new JImagenBasicaFactory(plImagen, plDescrip));
    }
    public void setControlador(final IPanelControlador poControlador, final ISalir poSalir, IImagenFactory poImagen) throws Exception {
        moSalir = poSalir;
        moImagen=poImagen;
        //si ya existia un controlador previo borramos el listener
        if(moControlador!=null && moControlador instanceof IPanelGenericoListener){
            removeListenerIPanelGenerico((IPanelGenericoListener) moControlador);
        }
        //establecemos el controlador
        moControlador = poControlador;
        //si el controlador es de tipo IPanelGenericoListener, a?adimos el listener
        if(moControlador instanceof IPanelGenericoListener){
            addListenerIPanelGenerico((IPanelGenericoListener) moControlador);
        }

        if(moControlador.getParametros().isActivado()){
            if(moControlador.getParametros().mbSegundoPlano){
                synchronized(this){
                    while(mbEjecutando){
                        try{
                            wait(1000);
                        }catch(Throwable e){

                        }
                    }
                    EjecutarPanelGenericoA loEjecutar = new EjecutarPanelGenericoA(this);
                    moThread = new Thread(loEjecutar);
                    moThread.start();
                }
            }else{
                recuperarDatos();
            }
        }else{
            jPanelSplash.setBackground(new java.awt.Color(200,200,200));
            jPanelSplash.setVisible(true);
            lblSplashLabel.setText("No hay suficientes permisos para visualizar estos datos");
        }
    }


    void recuperarDatos() throws Exception {
        try{
            if(JGUIxConfigGlobal.getInstancia().getPlugInFactoria()!=null){
                JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto(),
                    moControlador);
            }
//            System.out.println("recuperarDatos");
            mbAnularEventos = true;
            synchronized (this){
                mbEjecutando = true;
            }
            jPanelSplash.setBackground(new java.awt.Color(200,200,200,200));
            jPanelSplash.setVisible(true);
//                add(jPanelSplash, BorderLayout.CENTER);
            //creamos los botones relacionados
            JPanelGeneralBotones loBotonesGenerales = moControlador.getParametros().getBotonesGenerales();
            moBotones.clear();
            boolean lbBotonesRelac = false;
            IListaElementos loBotonesRelac = moControlador.getParametros().getBotonesGenerales().getListaBotones();
            if(loBotonesRelac != null && loBotonesRelac.size()>0){
                //vemos si hay algun boton relacionado, es decir, no principal
                //si el boton NO es activo se borra
                for(int i = 0; i < loBotonesRelac.size(); i++){
                    IBotonRelacionado loBoton = (IBotonRelacionado)loBotonesRelac.get(i);
                    if(loBoton.isActivo()){
                        if(!loBoton.isEsPrincipal()){
                            lbBotonesRelac = true;
                        }
                    }else{
                        loBotonesRelac.remove(i);
                        i--;
                    }
                }
            }else{
                lbBotonesRelac = false;
            }

            if(lbBotonesRelac){
                getPanelRelacionadoGen().setVisible(true);
                moPanelRelaciones.clear();
                getPanelRelacionadoGen().removeAll();
                crearBotonesSoloRelacionados(moControlador.getParametros().getBotonesGenerales().getListaBotones());
            }else{
                getPanelRelacionadoGen().setVisible(false);
            }
            establecerBotones(loBotonesGenerales, loBotonesRelac);

            if(moControlador.getParametros()==null || moControlador.getParametros().isRefrescarListDatos()){
                moDatos = moControlador.getDatos();
            }else{
                moDatos = moControlador.getConsulta().getList();
            }

            //len indicamos el panel que le visualiza los datos
            moControlador.setPanel(this);

            //visualizamos los datos
            visualizarDatos();

            String lsAux = JTablaConfig.mcsNombreDefecto;

            moPopUpMenu.configurarPopup(
                    moBotones, loBotonesGenerales,
                    this, moDatos,
                    moControlador.getParametros().getColumnasVisiblesConfig());
            
            moTablaConfig.setDatos(
                    moControlador.getParametros().getNombre(),
                    moControlador.getParametros().getLongitudCampos(),
                    moControlador.getParametros().getOrdenCampos(),
                    moControlador.getParametros().getColumnasVisiblesConfig()
                    );
        } catch (Throwable e){
            if(moControlador.getParametros().mbSegundoPlano){
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
            }else{
                throw new Exception(e);
            }
        }finally{
            synchronized (this){
                mbEjecutando = false;
                notifyAll();
            }
            jPanelSplash.setVisible(false);
            if(SwingUtilities.isEventDispatchThread()){
                this.validate();
            }else{
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JPanelGenericoGaleria.this.validate();
                    }
                });
            }
            this.repaint();
            mbAnularEventos = false;
        }
        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclFinInicializar, "");
    }
    private Container getContainer(final String psGrupo){
        Container loPanel = null;
        for(int i = 0 ; i < moPanelRelaciones.size() && loPanel == null; i++){
            JElementoBotonesA loElem = (JElementoBotonesA)moPanelRelaciones.get(i);
            if(loElem.msGrupo.compareTo(psGrupo)==0){
                loPanel = loElem.moToolBar;
            }
        }
        if(loPanel==null){
            loPanel = crearContenedorBotones(psGrupo);
            getPanelRelacionadoGen().add(loPanel);
            moPanelRelaciones.add(new JElementoBotonesA(psGrupo, loPanel));
        }
        return loPanel;
    }

    private void establecerBotones(final JPanelGeneralBotones poBotonesGenerales, final IListaElementos poBotonesRelacPrinci){
        establecerValoresBotones(getBtnNuevo(),poBotonesGenerales.getNuevo());
        establecerValoresBotones(getBtnBorrar(),poBotonesGenerales.getBorrar());
        establecerValoresBotones(getBtnEditar(),poBotonesGenerales.getEditar());
        establecerValoresBotones(getBtnRefrescar(),poBotonesGenerales.getRefrescar());
        establecerValoresBotones(getBtnAceptar(),poBotonesGenerales.getAceptar());
        establecerValoresBotones(getBtnCancelar(),poBotonesGenerales.getCancelar());
        mbEsBusqueda = poBotonesGenerales.getAceptar().getVisible();


        //borramos todos los botones menos los generales de arriba q son los 6 primeros
        for(int i = getPanelEditar().getComponentCount()-1; i >= 6; i--){
            getPanelEditar().remove(getPanelEditar().getComponent(i));
        }
        boolean lbRelacPrinci = false;
//        crearBotonesRelacionados(jPanelEditar, poBotonesGenerales.getListaBotones());
        if(poBotonesRelacPrinci!=null && poBotonesRelacPrinci.size()>0){
            IListaElementos loElem = new JListaElementos();
            for(int i = 0 ; i < poBotonesRelacPrinci.size();i++ ){
                IBotonRelacionado loBoton = (IBotonRelacionado)poBotonesRelacPrinci.get(i);
                if(loBoton.isEsPrincipal()){
                    loElem.add(loBoton);
                    lbRelacPrinci = true;
                }
            }
            crearBotonesRelacionados(getPanelEditar(), loElem);
        }

        if(!(poBotonesGenerales.getNuevo().getVisible() || poBotonesGenerales.getBorrar().getVisible() ||
             poBotonesGenerales.getEditar().getVisible() || poBotonesGenerales.getRefrescar().getVisible() ||
             poBotonesGenerales.getAceptar().getVisible() || poBotonesGenerales.getCancelar().getVisible() ||
             lbRelacPrinci
            )
          ){
            getPanelEditar().setVisible(false);
        }else{
            getPanelEditar().setVisible(true);
        }


    }
    private void establecerValoresBotones(final JButton poBoton, final IBotonRelacionado poProp){
        poBoton.setVisible(poProp.getVisible());
        if(poProp.getCaption() != null){
            poBoton.setText(poProp.getCaption());
        }
        if(poProp.getDimension() != null){
            poBoton.setPreferredSize(new Dimension(poProp.getDimension().width, poProp.getDimension().height));
        }else if(getDimensionDefecto(null) != null){
            poBoton.setPreferredSize(getDimensionDefecto(null));
        }
        poBoton.setPreferredSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        poBoton.setMinimumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        poBoton.setMaximumSize(new java.awt.Dimension(poBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
    }
    private void visualizarDatos() throws Throwable {

        getTabla().setModel(moDatos,moImagen);
        //solo se puede seleccionar una fila
        if(IEjecutarExtend.class.isAssignableFrom(moControlador.getClass())){
            getTabla().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }else{
            getTabla().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        //seleccionamos la primera fila por defecto
        if(getTabla().getRowCount()>0){
            getTabla().setRowSelectionInterval(0,0);
        }
        setTotal(String.valueOf(moDatos.size()));
        ponerPosicion();

    }
    //crear botones relacionados
    private void crearBotonesRelacionados(final Container poPanel,final IListaElementos poBotones){
        int lIndexInicial = moBotones.size();
        for(int i = 0; i < poBotones.size();i++){
            moBotones.add(poBotones.get(i));
        }
        for(int i = lIndexInicial ; i<moBotones.size(); i++){
            crearBoton(poPanel, (IBotonRelacionado)moBotones.get(i), i);
        }
    }
    private void crearBotonesSoloRelacionados(final IListaElementos poBotones){
        for(int i = 0; i < poBotones.size();i++){
            IBotonRelacionado loBoton = (IBotonRelacionado)poBotones.get(i);
            if(!loBoton.isEsPrincipal()){
                moBotones.add(loBoton);
                crearBoton(getContainer(loBoton.getGrupo()), loBoton, moBotones.size()-1);
            }
        }
    }

    //crea un boton
    private void crearBoton(final Container poPanel,final IBotonRelacionado poBoton, final int plIndex){
        JButtonCZ loBoton = new JButtonCZ(poBoton.getCaption());
        if(poBoton.getIcono()!=null){
            try{
                loBoton.setIcon((Icon)poBoton.getIcono());
            }catch(Throwable e){JDepuracion.anadirTexto(this.getClass().getName(), e);}
        }
        loBoton.addActionListener(this);
        loBoton.setName(String.valueOf(plIndex));
        loBoton.setActionCommand(String.valueOf(plIndex));
        if(poBoton.getDimension()!=null){
            loBoton.setPreferredSize(new Dimension(poBoton.getDimension().width, poBoton.getDimension().height ));
        }else if(getDimensionDefecto(poBoton) != null){
            loBoton.setPreferredSize(getDimensionDefecto(poBoton));
        }
        loBoton.setPreferredSize(new java.awt.Dimension(loBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        loBoton.setMinimumSize(new java.awt.Dimension(loBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        loBoton.setMaximumSize(new java.awt.Dimension(loBoton.getPreferredSize().width, getBtnAceptar().getPreferredSize().height));
        propiedadesBotonRecienCreado(loBoton);
        poPanel.add(loBoton);
    }

    /**ejecuta la accion del boton*/
    public void actionPerformed(final ActionEvent evt) {
        boolean lbContinuar = true;
        try{
            if(evt.getSource() == getBtnRefrescar() ||
               (evt.getActionCommand() != null && !evt.getActionCommand().equals("") &&
                evt.getActionCommand().equalsIgnoreCase(getBtnRefrescar().getActionCommand()))
              ){
                lbContinuar = false;
                if(moControlador.getConsulta()!=null && moControlador.getConsulta().getList()!=null && moControlador.getConsulta().getList().moServidor!=null){
                    moControlador.getConsulta().getList().moServidor.clearCache();
                }
                setControlador(moControlador,null,moImagen);
                getTabla().refrescarImagenes(true);
                refrescar();
            }
            if(evt.getSource() == getBtnAceptar() ||
               (evt.getActionCommand() != null && !evt.getActionCommand().equals("") &&
                evt.getActionCommand().equalsIgnoreCase(getBtnAceptar().getActionCommand()))
              ){
                lbContinuar = false;
                jBtnAceptarActionPerformed(null);
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
            }
            if(evt.getSource() == getBtnCancelar() ||
               (evt.getActionCommand() != null && !evt.getActionCommand().equals("") &&
                evt.getActionCommand().equalsIgnoreCase(getBtnCancelar().getActionCommand()))
              ){
                lbContinuar = false;
                jBtnCancelarActionPerformed(null);
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionCancelar);
            }
            if(evt.getSource() == getBtnEditar() ||
               (evt.getActionCommand() != null && !evt.getActionCommand().equals("") &&
                evt.getActionCommand().equalsIgnoreCase(getBtnEditar().getActionCommand()))
              ){
                lbContinuar = false;
                jBtnEditarActionPerformed(null);
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
            }
            if(evt.getSource() == getBtnNuevo() ||
               (evt.getActionCommand() != null && !evt.getActionCommand().equals("") &&
                evt.getActionCommand().equalsIgnoreCase(getBtnNuevo().getActionCommand()))
              ){
                lbContinuar = false;
                jBtnNuevoActionPerformed(null);
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionNuevo);
            }
            if(evt.getSource() == getBtnBorrar() ||
               (evt.getActionCommand() != null && !evt.getActionCommand().equals("") &&
                evt.getActionCommand().equalsIgnoreCase(getBtnBorrar().getActionCommand()))
              ){
                lbContinuar = false;
                jBtnBorrarActionPerformed(null);
                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionBorrar);
            }

            if(lbContinuar){
                if(evt.getActionCommand().equals(JPanelGeneralFiltro.mcsCambioFiltro)){
                    refrescar();
                    comprobarSeleccion(getTabla());
                    llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsCambioFiltro);
                }else{
                    if(evt.getSource()==getTabla()){
                        if(evt.getActionCommand().equals(JTableCZ.mcsESC)){
                            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JTableCZ.mcsESC);
                            moSalir.salir();
                        }else{
                            if(mbEsBusqueda){
                                jBtnAceptarActionPerformed(null);
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionAceptar);
                            }else{
                                if(getBtnEditar().isVisible()){
                                    jBtnEditarActionPerformed(null);
                                }
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsAccionEditar);
                            }
                        }
                    }else{
                        comprobarSeleccion(getTabla());
                        ((IBotonRelacionado)moBotones.get(Integer.valueOf(evt.getActionCommand()).intValue())).ejecutar(getTabla().getSelectedRows());
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, JPanelGenericoEvent.mcsCambioFiltro);
                    }
                }
            }
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, evt.getActionCommand());
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }
    private void ponerPosicion() {
        try{
            setPosicion(String.valueOf(getTabla().getSelectedRow()+1));
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclPosicionarLinea, "");
        }catch(Throwable e1){
            JDepuracion.anadirTexto(getClass().getName(), e1);
        }
   }
    public void refrescar(IFilaDatos poFila){
        JElementoImagen loImg = getTabla().buscar(poFila);
        loImg.setFila(poFila, loImg.getImagen());
    }

    public void valueChanged(ListSelectionEvent e) {
        if(!mbAnularEventos){
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
        ponerPosicion();
        if (e.getClickCount()>1){
            if(mbEsBusqueda){
                jBtnAceptarActionPerformed(null);
            }else{
                if(getBtnEditar().isVisible()){
                    jBtnEditarActionPerformed(null);
                }
            }
        }
    }

    public void addListenerIPanelGenerico(IPanelGenericoListener poListener){
       listenerList.add(IPanelGenericoListener.class, poListener);
    }

    public void removeListenerIPanelGenerico(IPanelGenericoListener poListener){
       listenerList.remove(IPanelGenericoListener.class, poListener);
    }

    private void llamarListenerIPanelGenericoList(int plTipo, String psComando){
        JPanelGenericoEvent loEvent = new JPanelGenericoEvent(this, plTipo, new int[]{getTabla().getSelectedRow()}, psComando);
        EventListener[] loList  = JGUIxUtil.getListeners(listenerList, IPanelGenericoListener.class);
        for(int i = 0 ; i < loList.length; i++ ){
            IPanelGenericoListener loListener = (IPanelGenericoListener)loList[i];
            loListener.eventPanelGenerico(loEvent);
        }

    }
    ////////////////
    ///Interfaz IPanelGenerico
    ////////////////
    public void refrescar() throws Exception{
        if(isEnabled()){
            if (SwingUtilities.isEventDispatchThread()) {
                refrescarInterno();
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
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
    public void refrescarInterno()  {
        //EN SIGA DE VEZ ENCUANDO NO  VA BIEN
//        getTabla().revalidate();
//        getTabla().repaint();
//        jScrollPaneTablaDatos.revalidate();
//        jScrollPaneTablaDatos.repaint();
//        getTabla().refrescarImagenes();
        getTabla().updateUI();
        setTotal(String.valueOf(moDatos.size()));
        ponerPosicion();
        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclRefrescar, JPanelGenericoEvent.mcsAccionRefrescar);
    }

    public void seleccionarTodo() {
        getTabla().selectAll();
    }
    public void seleccionarFila(int plFila, boolean pbSeleccionar) {
        if(pbSeleccionar) {
            getTabla().getSelectionModel().addSelectionInterval(plFila, plFila);
        }else{
            getTabla().getSelectionModel().removeIndexInterval(plFila, plFila);
        }
    }
    public ITablaConfig getTablaConfig() {
        return moTablaConfig;
    }

    public IListaElementos getBotones() {
        return moBotones;
    }


    public JButtonCZ getBtnAceptar() {
        return jButtonAceptar;
    }

    public JButtonCZ getBtnBorrar() {
        return jBtnBorrar;
    }

    public JButtonCZ getBtnCancelar() {
        return jButtonCancelar;
    }

    public JButtonCZ getBtnEditar() {
        return jBtnEditar;
    }

    public JButtonCZ getBtnNuevo() {
        return jBtnNuevo;
    }

    public JButtonCZ getBtnRefrescar() {
        return jBtnRefrescar;
    }

    public int[] getSelectedRows() {
        return getTabla().getSelectedRows();
    }

    public JPanelListaImagenes getTabla() {
        return jTableDatos;
    }

    public void setTotal(String psValor) {
//        lblTotal.setText(psValor);
    }

    public void setPosicion(String psValor) {
//        lblPosicion.setText(psValor);
    }

    public Container crearContenedorBotones(String psGrupo) {

        utilesGUIx.JPanelTareas jPanelTareas1 = new utilesGUIx.JPanelTareas();
        jPanelTareas1.setText((psGrupo.compareTo("")==0 ? JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  "General"): psGrupo));
//        jPanelTareas1.getPanel().setLayout(new FlowLayout());

        return jPanelTareas1;
    }

    public JScrollPane getScrollPaneTablaDatos() {
        return jScrollPaneTablaDatos;
    }

    public JPanel getPanelRelacionadoGen() {
        return jPanelRelacionadoGen;
    }
    public void propiedadesBotonRecienCreado(JButtonCZ poBoton) {
        
    }


    public Container getPanelEditar() {
        return jPanelEditar;
    }

    public Dimension getDimensionDefecto(final IBotonRelacionado poBoton) {
        if(poBoton!=null && !poBoton.isEsPrincipal()){
            return moDimensionRelacionesDefecto;
        }else{
            return null;
        }
    }

    public Object getPanelInformacion() {
        return jPanelInformacion;
    }

        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelSplash = new javax.swing.JPanel();
        lblSplashLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPaneTablaDatos = new javax.swing.JScrollPane();
        jTableDatos = new utilesGUIx.imgTrata.lista.JPanelListaImagenes();
        jPanel3 = new javax.swing.JPanel();
        jPanelInformacion = new javax.swing.JPanel();
        jPanelEditar = new javax.swing.JPanel();
        jBtnNuevo = new utilesGUIx.JButtonCZ();
        jBtnEditar = new utilesGUIx.JButtonCZ();
        jBtnBorrar = new utilesGUIx.JButtonCZ();
        jBtnRefrescar = new utilesGUIx.JButtonCZ();
        jButtonAceptar = new utilesGUIx.JButtonCZ();
        jButtonCancelar = new utilesGUIx.JButtonCZ();
        jPanelRelacionadoGen = new utilesGUIx.JPanelTareasConj();

        jPanelSplash.setLayout(new java.awt.BorderLayout());

        lblSplashLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblSplashLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSplashLabel.setText("procesando...");
        jPanelSplash.add(lblSplashLabel, java.awt.BorderLayout.CENTER);

        setBackground(new java.awt.Color(175, 181, 186));
        setMinimumSize(new java.awt.Dimension(100, 20));
        setPreferredSize(new java.awt.Dimension(600, 200));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBackground(new java.awt.Color(237, 234, 229));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(237, 234, 229));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jScrollPaneTablaDatos.setBackground(new java.awt.Color(237, 234, 229));
        jScrollPaneTablaDatos.setBorder(null);
        jScrollPaneTablaDatos.setViewportView(jTableDatos);

        jPanel8.add(jScrollPaneTablaDatos, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanelInformacion.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanelInformacion, gridBagConstraints);

        jPanelEditar.setBackground(new java.awt.Color(175, 181, 186));
        jPanelEditar.setOpaque(false);

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Add16.gif"))); // NOI18N
        jBtnNuevo.setText("Nuevo");
        jPanelEditar.add(jBtnNuevo);

        jBtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Edit16.gif"))); // NOI18N
        jBtnEditar.setText("Editar");
        jPanelEditar.add(jBtnEditar);

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        jBtnBorrar.setText("Borrar");
        jPanelEditar.add(jBtnBorrar);

        jBtnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Refresh16.gif"))); // NOI18N
        jBtnRefrescar.setText("Refrescar");
        jPanelEditar.add(jBtnRefrescar);

        jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jButtonAceptar.setText("Aceptar");
        jPanelEditar.add(jButtonAceptar);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jPanelEditar.add(jButtonCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanelEditar, gridBagConstraints);

        jPanel8.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel4, gridBagConstraints);

        jPanelRelacionadoGen.setMinimumSize(new java.awt.Dimension(170, 34));
        jPanelRelacionadoGen.setPreferredSize(new java.awt.Dimension(170, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanelRelacionadoGen, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
   

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            moControlador.setIndexs(null);
            if(moSalir!=null){
                moSalir.salir();
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void jBtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            moControlador.setIndexs(getTabla().getSelectedRows());
            if(moSalir!=null){
                moSalir.salir();
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }


    private void jBtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            comprobarSeleccion(getTabla());
            int[] lIndexs = getTabla().getSelectedRows();
            if (lIndexs.length>0){
                if (JOptionPane.showConfirmDialog(this,
                        (lIndexs.length>1 ?
                            "¿Estas seguro de borrar los registros actuales?":
                            "¿Estas seguro de borrar el registro actual?"),
                        "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    for(int i = lIndexs.length-1;i>=0;i--){
                        if (lIndexs[i]>=0 &&
                            lIndexs[i]<getTabla().getRowCount()){
                        moDatos.setIndex(lIndexs[i]);
                        moControlador.borrar(lIndexs[i]);
                    }
                    }
                    //se hace esto para q refresque bien
                    moDatos.setIndex(moDatos.size()-1);
                    //cambia a seleccion de columnas
                    int lIndice = lIndexs[0]-1;
//                    if(lIndice<0){
//                        lIndice=0;
//                    }
//                    getTabla().getSelectionModel().setSelectionInterval(lIndice,lIndice);
                    if(lIndice<0){
                        getTabla().clearSelection();
                    }
                    else
                        getTabla().setRowSelectionInterval(lIndice,lIndice);

                }
                refrescar();
            }else {
                JOptionPane.showMessageDialog(this, "No existe una fila actual");
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            if (getTabla().getSelectedRow()>=0 &&
                getTabla().getSelectedRow()<getTabla().getRowCount()){
                moDatos.setIndex(getTabla().getSelectedRow());
                moControlador.editar(getTabla().getSelectedRow());
                refrescar();
            }else {
                JOptionPane.showMessageDialog(this, "No existe una fila actual");
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            moControlador.anadir();
            refrescar();
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }
    private void jTableDatosKeyPressed(java.awt.event.KeyEvent evt) {

        if(!moControlador.getParametros().getBotonesGenerales().getCopiarTabla().getVisible()){
            if(evt.isControlDown()){
                evt.setKeyCode(0);
            }
        }
    }


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ jBtnBorrar;
    private utilesGUIx.JButtonCZ jBtnEditar;
    private utilesGUIx.JButtonCZ jBtnNuevo;
    private utilesGUIx.JButtonCZ jBtnRefrescar;
    private utilesGUIx.JButtonCZ jButtonAceptar;
    private utilesGUIx.JButtonCZ jButtonCancelar;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelEditar;
    private javax.swing.JPanel jPanelInformacion;
    private utilesGUIx.JPanelTareasConj jPanelRelacionadoGen;
    private javax.swing.JPanel jPanelSplash;
    private javax.swing.JScrollPane jScrollPaneTablaDatos;
    private utilesGUIx.imgTrata.lista.JPanelListaImagenes jTableDatos;
    private javax.swing.JLabel lblSplashLabel;
    // End of variables declaration//GEN-END:variables

    
}
class JElementoBarraInterno {
    public String msGrupo;
    public Container moBarra;
    
    public JElementoBarraInterno(final String psGrupo, final Container poBarra){
        super();
        msGrupo = psGrupo;
        moBarra = poBarra;
    }
} 

class JElementoBotonesA {
    public String msGrupo;
    public Container moToolBar;

    public JElementoBotonesA(String psGrupo, Container poToolBar){
        msGrupo = psGrupo;
        moToolBar = poToolBar;
    }
}

class EjecutarPanelGenericoA implements Runnable {
    private JPanelGenericoGaleria moPanel;

    public EjecutarPanelGenericoA(final JPanelGenericoGaleria poPanel){
        moPanel = poPanel;
    }

    public void run() {
        try{
            moPanel.recuperarDatos();
        }catch(Throwable e){

        }
    }

}
