/*
 * JPanelBusqueda.java
 *
 * Created on 6 de septiembre de 2004, 12:07
 */

package utilesGUIxAvisos.forms;

import utilesGUIxAvisos.forms.util.JTableModelEVENTOS;
import utilesGUIxAvisos.forms.util.JTableRenderEventoParam;
import utilesGUIxAvisos.forms.util.JTableRenderEvento;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.JButtonCZ;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JTableCZ;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOS;


/**representa una panel de búsqueda/principal, con posibilidades de filtros, botones de edicón estandar y botones dinamicos personalizados*/
public class JPanelGenericoEVENTOS extends JPanelGenericoAbstract {
    private static final long serialVersionUID = 1L;

//    public static final int mclTipo = 0;
    public static Dimension moDimensionRelacionesDefecto = new Dimension(160, 29);
    private JTFORMGUIXEVENTOS moEventos;
    private JTableModelEVENTOS moModeloDatosEventos;
    private final ActionListenerCZ moListernerFiltro = new ActionListenerCZ() {
        @Override
        public void actionPerformed(ActionEventCZ e) {
            JPanelGenericoEVENTOS.this.actionPerformed(new ActionEvent(this,0, e.getActionCommand()));
        }
    };
    /** Creates new form JPanelBusqueda */
    public JPanelGenericoEVENTOS() {
        super();
        initComponents();
        jButtonAceptar.setMnemonic('a');
        jButtonCancelar.setMnemonic('c');
        jBtnBorrar.setMnemonic('b');
        jBtnNuevo.setMnemonic('n');
        jBtnEditar.setMnemonic('e');
        jBtnRefrescar.setMnemonic('r');

//        jPanelTareasFiltro.setAmpliado(false);
//        jPanelTareasFiltro.setText("Filtro avanzado");
//        jPanelTareasFiltro.getPanel().add(jPanelGeneralFiltro1);
        jTableDatos.setDefaultRenderer(JTableRenderEventoParam.class, new JTableRenderEvento());
        jTableDatos.setRowHeight(50);
        inicializar();
        moTablaConfig.borrarEventosTabla();
    }
    @Override
    public JPanelGeneralFiltro getPanelGeneralFiltro1() {
        return jPanelGeneralFiltro1;
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
    public JButtonCZ getBtnAceptar() {
        return jButtonAceptar;
    }

    @Override
    public JButtonCZ getBtnBorrar() {
        return jBtnBorrar;
    }

    @Override
    public JButtonCZ getBtnCancelar() {
        return jButtonCancelar;
    }

    @Override
    public JButtonCZ getBtnEditar() {
        return jBtnEditar;
    }

    @Override
    public JButtonCZ getBtnNuevo() {
        return jBtnNuevo;
    }

    @Override
    public JButtonCZ getBtnRefrescar() {
        return jBtnRefrescar;
    }

    @Override
    public JButtonCZ getbtnConfig() {
        return null;
    }

    @Override
    public JComboBoxCZ getcmbConfig() {
        return null;
    }

    @Override
    public JComboBoxCZ getcmbTipoFiltroRapido() {
        return null;
    }

    @Override
    public JTableCZ getTabla() {
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
    public Container crearContenedorBotones(String psGrupo) {

        utilesGUIx.JPanelTareas jPanelTareas1 = new utilesGUIx.JPanelTareas();
        jPanelTareas1.setText((psGrupo.compareTo("")==0 ? "General": psGrupo));
        jPanelTareas1.getPanel().setLayout(new FlowLayout());

        return jPanelTareas1;
    }

    @Override
    public JScrollPane getScrollPaneTablaDatos() {
        return jScrollPaneTablaDatos;
    }

    @Override
    public JComboBoxCZ getcmbFiltros() {
       return null;
    }
    @Override
    public JPanel getPanelRelacionadoGen() {
        return jPanelRelacionadoGen;
    }
    @Override
    public void propiedadesBotonRecienCreado(JButtonCZ poBoton) {
        
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
    public Container getPanelEditar() {
        return jPanelEditar;
    }

    @Override
    public Dimension getDimensionDefecto(final IBotonRelacionado poBoton) {
        if(poBoton!=null && !poBoton.isEsPrincipal()){
            return moDimensionRelacionesDefecto;
        }else{
            return null;
        }
    }

    @Override
    public JPanel getPanelInformacion() {
        return jPanelInformacion;
    }

    @Override
    public void setVisibleSplash(boolean pbVisible, String psTexto) {
        if(pbVisible){
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            add(jPanelSplash, gridBagConstraints);
        }else{
            remove(jPanelSplash);
        }
    }

    @Override
    protected void recuperarDatosBD() throws Exception {
        moEventos = (JTFORMGUIXEVENTOS) moControlador.getConsulta();
        if(moControlador.getParametros()==null || moControlador.getParametros().isRefrescarListDatos()){
            moControlador.getDatos();
//            }else{
//                moControlador.getConsulta().getList();
        }
        moDatos = moControlador.getConsulta().getList();
    }

    
    @Override
    protected void mostrarDatos() throws Throwable {
        try{
            if(JGUIxConfigGlobal.getInstancia().getPlugInFactoria()!=null){
                JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto(),
                    moControlador);
            }
//            System.out.println("recuperarDatos");
            mbAnularEventos = true;
            moTablaConfig.setInicializado(false);
            moTablaConfig.setAnularEventos(true);
            synchronized (this){
                mbEjecutando = true;
            }
            setVisibleSplash(true, "Procesando....");
            setVisiblePanelConfigyFiltroRap(moControlador.getParametros().isVisibleConfigYFiltroRap());
            //creamos los botones relacionados
            JPanelGeneralBotones loBotonesGenerales = moControlador.getParametros().getBotonesGenerales();
            moBotones.clear();
            boolean lbBotonesRelac = false;
            IListaElementos loBotonesRelac = loBotonesGenerales.getListaBotones();
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
                crearBotonesSoloRelacionados(loBotonesGenerales.getListaBotones());
            }else{
                getPanelRelacionadoGen().setVisible(false);
            }
            establecerBotones(loBotonesGenerales, loBotonesRelac);

            
//            getTabla().setTableCZColores(moControlador.getParametros().getColoresTabla());

            //len indicamos el panel que le visualiza los datos
            moControlador.setPanel(this);
            
            moTablaConfig.setDatos(
                    moControlador.getParametros().getNombre(),
                    moControlador.getParametros().getLongitudCampos(),
                    moControlador.getParametros().getOrdenCampos(),
                    moControlador.getParametros().getColumnasVisiblesConfig()
                    );
            //creamos el filtro
            moFiltroModelo.setDatos(getDatos(), moListernerFiltro, true, moTablaConfig);
            getPanelGeneralFiltro1().setDatos(moFiltroModelo);
            setVisiblePanelTareasFiltro(loBotonesGenerales.getFiltro().getVisible());
//            getPanelGeneralFiltroLinea1().setVisible(loBotonesGenerales.getFiltro().getVisible());
            getPanelGeneralFiltroTodosCamp1().setVisible(loBotonesGenerales.getFiltro().getVisible());
//            getcmbTipoFiltroRapido().setVisible(loBotonesGenerales.getFiltro().getVisible());
//            if(loBotonesGenerales.getFiltro().getVisible()){
//                if(moControlador.getParametros()!=null){
//                    getcmbTipoFiltroRapido().setValueTabla(moControlador.getParametros().msTipoFiltroRapido + JFilaDatosDefecto.mcsSeparacion1);
//                    getcmbTipoFiltroRapido().setValorOriginal(null);
//                }
//            }
            
            //visualizamos los datos 
            visualizarDatos();
//            moModeloDatosEventos.mbEditable = false;

//            //leemos la config. guardada previamente
//            getPanelGeneralFiltroLinea1().setAnularSetLong(true);
//
//            String lsAux = JTablaConfig.mcsNombreDefecto;
//            try{
//                lsAux = moTablaConfig.getIndiceConfig();
//            }catch(Throwable e){}
            JTablaConfig.setLongColumna(jTableDatos.getColumnModel().getColumn(0), 600);

            JTablaConfig.setLongColumna(jTableDatos.getColumnModel().getColumn(0), 600);
//            //si existe la config de tabla se establece, si no se pone la de por defecto
//            if(moTablaConfig.getConfigTabla().getConfig(lsAux)!=null){
//                moTablaConfig.setIndiceConfig(lsAux);
//            }else{
//                lsAux = JTablaConfig.mcsNombreDefecto;
//            }
//            //rellenamos el combo de config
//            rellenarConfig();
//            //establecemos la config en el combo
//            getcmbConfig().setValueTabla(lsAux + JFilaDatosDefecto.mcsSeparacion1);
//
            if(loBotonesGenerales.getFiltro().getVisible()){
//                getPanelGeneralFiltroLinea1().setAnularSetLong(false);
//                getPanelGeneralFiltroLinea1().setDatos(
//                        getPanelGeneralFiltro1().getTableModelFiltro(),
//                        moTablaConfig.getConfigTabla().getCampoBusqueda()
//
//                        );
//                setFiltroCamposVisibles();
                getPanelGeneralFiltroTodosCamp1().setDatos(moFiltroModelo);
//
//                cmbTipoFiltroRapidoItemStateChanged(null);
            }

            moPopUpMenu.configurarPopup(
                    moBotones, loBotonesGenerales,
                    this, null,
                    moControlador.getParametros().getColumnasVisiblesConfig());
            moPopUpMenu.setVisibleCampos(getCamposVisibles());

            setFormatosCampos(moControlador.getParametros().getFormatosCampos());
            
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
            setVisibleSplash(false,null);
            if(SwingUtilities.isEventDispatchThread()){
                this.validate();
            }else{
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JPanelGenericoEVENTOS.this.validate();
                    }
                });
            }
            this.repaint();
            moTablaConfig.setAnularEventos(false);
            mbAnularEventos = false;
        }
        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclFinInicializar, "");
    }
    @Override
    protected void setFiltroCamposVisibles(){
        
    }
    @Override
    protected void visualizarDatos() throws Throwable {

        moFiltroModelo.limpiar();
        //creamos el table model a partir de los datps
        moModeloDatosEventos = new JTableModelEVENTOS(moEventos);

        getTabla().setModel(moModeloDatosEventos);
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
        setTotal(String.valueOf(moEventos.getList().size()));
        ponerPosicion();
        
    }

        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelGeneralFiltro1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltro();
        jPanelSplash = new javax.swing.JPanel();
        lblSplashLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanelCabezera = new javax.swing.JPanel();
        jPanelConfigyFiltroRap = new javax.swing.JPanel();
        btnMasFiltros = new javax.swing.JButton();
        btnOcultarCabezera = new javax.swing.JButton();
        btnVerAnteriores = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblPosicion = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        JPanelTipoFiltroRapido = new javax.swing.JPanel();
        jPanelGeneralFiltroTodosCamp1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp();
        btnMostrarCabezera = new javax.swing.JButton();
        jScrollPaneTablaDatos = new javax.swing.JScrollPane();
        jTableDatos = new utilesGUIx.JTableCZ();
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

        lblSplashLabel.setFont(new java.awt.Font("Tahoma", 0, 10));
        lblSplashLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSplashLabel.setText("procesando..."); // NOI18N
        jPanelSplash.add(lblSplashLabel, java.awt.BorderLayout.CENTER);

        setBackground(new java.awt.Color(175, 181, 186));
        setMinimumSize(new java.awt.Dimension(100, 20));
        setPreferredSize(new java.awt.Dimension(600, 200));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBackground(new java.awt.Color(237, 234, 229));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanelCabezera.setLayout(new java.awt.CardLayout());

        jPanelConfigyFiltroRap.setOpaque(false);
        jPanelConfigyFiltroRap.setLayout(new java.awt.GridBagLayout());

        btnMasFiltros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/base_arrow_up.png"))); // NOI18N
        btnMasFiltros.setText("Mas filtros"); // NOI18N
        btnMasFiltros.setFocusable(false);
        btnMasFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasFiltrosActionPerformed(evt);
            }
        });
        jPanelConfigyFiltroRap.add(btnMasFiltros, new java.awt.GridBagConstraints());

        btnOcultarCabezera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/crossed_circle.png"))); // NOI18N
        btnOcultarCabezera.setText("Ocultar"); // NOI18N
        btnOcultarCabezera.setToolTipText("Ocultar botones y filtros"); // NOI18N
        btnOcultarCabezera.setFocusable(false);
        btnOcultarCabezera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOcultarCabezeraActionPerformed(evt);
            }
        });
        jPanelConfigyFiltroRap.add(btnOcultarCabezera, new java.awt.GridBagConstraints());

        btnVerAnteriores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/WebComponent16.gif"))); // NOI18N
        btnVerAnteriores.setText("Ver eventos anteriores"); // NOI18N
        btnVerAnteriores.setToolTipText(""); // NOI18N
        btnVerAnteriores.setFocusable(false);
        btnVerAnteriores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerAnterioresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanelConfigyFiltroRap.add(btnVerAnteriores, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblPosicion.setFont(new java.awt.Font("Dialog", 0, 12));
        lblPosicion.setForeground(new java.awt.Color(153, 51, 255));
        lblPosicion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPosicion.setText("1"); // NOI18N
        lblPosicion.setPreferredSize(new java.awt.Dimension(46, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblPosicion, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel7.setForeground(new java.awt.Color(153, 51, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(" de "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jLabel7, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Dialog", 0, 12));
        lblTotal.setForeground(new java.awt.Color(153, 51, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("9000"); // NOI18N
        lblTotal.setMaximumSize(new java.awt.Dimension(60, 19));
        lblTotal.setMinimumSize(new java.awt.Dimension(60, 19));
        lblTotal.setPreferredSize(new java.awt.Dimension(46, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanelConfigyFiltroRap.add(jPanel2, gridBagConstraints);

        JPanelTipoFiltroRapido.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        JPanelTipoFiltroRapido.add(jPanelGeneralFiltroTodosCamp1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelConfigyFiltroRap.add(JPanelTipoFiltroRapido, gridBagConstraints);

        jPanelCabezera.add(jPanelConfigyFiltroRap, "filtro");

        btnMostrarCabezera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/up16.png"))); // NOI18N
        btnMostrarCabezera.setToolTipText("Mostrar botones y filtros"); // NOI18N
        btnMostrarCabezera.setBorder(null);
        btnMostrarCabezera.setFocusable(false);
        btnMostrarCabezera.setMaximumSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.setMinimumSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.setPreferredSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCabezeraActionPerformed(evt);
            }
        });
        jPanelCabezera.add(btnMostrarCabezera, "filtromostrar");

        jPanel8.add(jPanelCabezera, java.awt.BorderLayout.NORTH);

        jScrollPaneTablaDatos.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPaneTablaDatos.setBorder(null);

        jTableDatos.setBackground(new java.awt.Color(102, 102, 102));
        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDatos.setEditingColumn(0);
        jTableDatos.setEditingRow(0);
        jTableDatos.setGridColor(new java.awt.Color(255, 255, 255));
        jTableDatos.setNextFocusableComponent(jButtonAceptar);
        jTableDatos.setRowHeight(50);
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
        jBtnNuevo.setText("Nuevo"); // NOI18N
        jPanelEditar.add(jBtnNuevo);

        jBtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Edit16.gif"))); // NOI18N
        jBtnEditar.setText("Editar"); // NOI18N
        jPanelEditar.add(jBtnEditar);

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        jBtnBorrar.setText("Borrar"); // NOI18N
        jPanelEditar.add(jBtnBorrar);

        jBtnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Refresh16.gif"))); // NOI18N
        jBtnRefrescar.setText("Refrescar"); // NOI18N
        jPanelEditar.add(jBtnRefrescar);

        jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jButtonAceptar.setText("Aceptar"); // NOI18N
        jPanelEditar.add(jButtonAceptar);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jButtonCancelar.setText("Cancelar"); // NOI18N
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

    private void mostrarCabezera() {
        ((CardLayout)(jPanelCabezera.getLayout())).show(
                jPanelCabezera,
                "filtro");
        jPanelConfigyFiltroRap.setVisible(true);
        btnMostrarCabezera.setVisible(false);
        jPanelCabezera.setPreferredSize(new Dimension(502, 72));
        jPanelCabezera.setMinimumSize(new Dimension(502, 72));
        updateUI();
    }
    private void ocultarCabezera() {
        ((CardLayout)(jPanelCabezera.getLayout())).show(
                jPanelCabezera,
                "filtromostrar");
        jPanelConfigyFiltroRap.setVisible(false);
        btnMostrarCabezera.setVisible(true);
        jPanelCabezera.setPreferredSize(new Dimension(50, 10));
        jPanelCabezera.setMinimumSize(new Dimension(50, 10));
        updateUI();
    }

    private void btnMostrarCabezeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCabezeraActionPerformed
        mostrarCabezera();
}//GEN-LAST:event_btnMostrarCabezeraActionPerformed

    private void btnMasFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasFiltrosActionPerformed
        try{
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(
                jPanelGeneralFiltro1, 640, 450, JMostrarPantalla.mclEdicionFrame);
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

}//GEN-LAST:event_btnMasFiltrosActionPerformed

    private void btnOcultarCabezeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcultarCabezeraActionPerformed
        ocultarCabezera();
    }//GEN-LAST:event_btnOcultarCabezeraActionPerformed

    private void btnVerAnterioresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerAnterioresActionPerformed
        try{
            btnVerAnteriores.setEnabled(false);
            moEventos.crearSelectStandarConGrupoPrevio("");
            recuperarYmostrarDatos();
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_btnVerAnterioresActionPerformed
   

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelTipoFiltroRapido;
    private javax.swing.JButton btnMasFiltros;
    private javax.swing.JButton btnMostrarCabezera;
    private javax.swing.JButton btnOcultarCabezera;
    private javax.swing.JButton btnVerAnteriores;
    private utilesGUIx.JButtonCZ jBtnBorrar;
    private utilesGUIx.JButtonCZ jBtnEditar;
    private utilesGUIx.JButtonCZ jBtnNuevo;
    private utilesGUIx.JButtonCZ jBtnRefrescar;
    private utilesGUIx.JButtonCZ jButtonAceptar;
    private utilesGUIx.JButtonCZ jButtonCancelar;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelCabezera;
    private javax.swing.JPanel jPanelConfigyFiltroRap;
    private javax.swing.JPanel jPanelEditar;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltro jPanelGeneralFiltro1;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp jPanelGeneralFiltroTodosCamp1;
    private javax.swing.JPanel jPanelInformacion;
    private utilesGUIx.JPanelTareasConj jPanelRelacionadoGen;
    private javax.swing.JPanel jPanelSplash;
    private javax.swing.JScrollPane jScrollPaneTablaDatos;
    private utilesGUIx.JTableCZ jTableDatos;
    private javax.swing.JLabel lblPosicion;
    private javax.swing.JLabel lblSplashLabel;
    private javax.swing.JLabel lblTotal;
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
