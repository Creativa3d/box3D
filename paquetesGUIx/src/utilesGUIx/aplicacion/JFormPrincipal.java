/*
 * JFormPrincipal.java
*
* Creado el 29/7/2008
*/

package utilesGUIx.aplicacion;

import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import java.awt.event.ActionEvent;
import javax.swing.*;
 
import ListDatos.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import utiles.IListaElementos;
import utiles.JDepuracion;        
import utiles.xml.dom.Element;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ActionListenerWrapper;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JPintaFondo;
import utilesGUIx.aplicacion.componentes.JCompApliFactory;
import utilesGUIx.aplicacion.componentes.xml.JXMLDesktop;
import utilesGUIx.formsGenericos.IMostrarPantallaListener;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaEvent;
import utilesGUIx.formsGenericos.JMostrarPantallaFormulario;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.lookAndFeel.*;
import utilesGUIx.formsGenericos.JTablaConfigAbstract;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.JPlugInUtilidades;
import utilesGUIx.plugin.seguridad.visual.JPanelGruposYUsuarios;
import utilesGUIx.plugin.toolBar.JComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupoModelo;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;


public class JFormPrincipal extends javax.swing.JFrame implements IPlugInFrame, IContainer {
    private static final long serialVersionUID = 1L;

    public static final String mcsBaseDatos = "Base Datos";
    public static final String mcsArchivo = "Archivo";
    public static final String mcsPropiedades = "Propiedades";
    public static final String mcsEstilo = "Estilo";
    public static final String mcsLogin = "Login";
    public static final String mcsDESCKTOPPrinci="DESCKTOPPrinci";
    public static final String mcsSalir="Salir";

    private JFormEdicionParametros moParametros = new JFormEdicionParametros();
    private boolean mbAnularAplicar=false;
    private final JDatosGeneralesAplicacion moDatosGenerales;
    private JMenu jMenuVentanas;
    private JCompApliFactory moFactbotones;
    private boolean mbPrimeraVezVentanas=true;
    
    /** Creates new form JFormPrincipal */
    public JFormPrincipal(JDatosGeneralesAplicacion poDatosGenerales) {
        super();
        moDatosGenerales = poDatosGenerales;
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this);

        moDatosGenerales.getLookAndFeelObjeto().setComponenteBase(this);
        ponerLookAndFeel(
                moDatosGenerales.getLookAndFeelObjeto(),
                moDatosGenerales.getLookAndFeel(),
                moDatosGenerales.getLookAndFeelTema()
                );

        initComponents();
        jToolBar1.setName(IComponenteAplicacion.mcsGRUPOBASEINTERNO);
        
        moFactbotones=new JCompApliFactory(jToolBar1, jDesktopPaneInterno, jMenuBar1, moDatosGenerales);


        int ancho = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        int alto = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        alto -= 40;
        Rectangle loRect =  new Rectangle(0,0,ancho,alto);
        setBounds(loRect);
        
        if(poDatosGenerales.getParametrosAplicacion().getImagenIcono()!=null){
            try{
                this.setIconImage(((ImageIcon) poDatosGenerales.getParametrosAplicacion().getImagenIcono()).getImage());
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        
        
        lblInformacion.setVisible(moDatosGenerales.getAvisos().size()>0);
        lblInformacion.setAvisos(moDatosGenerales.getAvisos());
    }
    public void inicializar(){

        jMenuLogin.setActionCommand(mcsLogin);
        jMenuABD.setActionCommand(mcsBaseDatos);
        jMenuArchivo.setActionCommand(mcsArchivo);
        jMenuAPropiedades.setActionCommand(mcsPropiedades);
        jMenuAEstilo.setActionCommand(mcsEstilo);
        jMenuASalir.setActionCommand(mcsSalir);
        if(moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW()==null){
            jMenuASeguridad.setVisible(false);
        }
        
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, jMenuBar1);
        
        JPlugInUtilidades.addMenusFrame(this, jMenuBar1, false);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        moDatosGenerales.setDesktopPane1(
                jDesktopPane1,
                this,
                jProcesoThreadGroup1
                );
        moDatosGenerales.setMenu(jMenuBar1);

        mbAnularAplicar=true;
        try{
            moDatosGenerales.getDatosGeneralesPlugIn().getPlugInManager().procesarInicial(
                moDatosGenerales.getDatosGeneralesPlugIn().getPlugInContexto()
                );
        }finally{
            mbAnularAplicar=false;
        }
        
        try {
            moFactbotones.copiaSeguridad();
//            Element loElem = moDatosGenerales.getDatosGeneralesXML().getElemento(mcsDESCKTOPPrinci);
//            if(loElem != null && loElem.getValue()!=null && loElem.getValue().length()>0){
//                JComponenteAplicacionGrupoModelo loLista = JXMLDesktop.leerXML(loElem.getValue());
//                for(int i = 0 ; i < loLista.size(); i++){
//                    IComponenteAplicacion loApl = (IComponenteAplicacion)loLista.get(i);
//                    asignarActionListener(loApl);
//                }
//                if(loLista.size()>0){
//                    moFactbotones.setListaComponentes(loLista);
//                }
//            }

        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        aplicarListaComponentesAplicacion();
        
        if(moDatosGenerales.getDatosGeneralesPlugIn().getPlugInManager().getPlugInSeguridad()!=null){
            moDatosGenerales.getDatosGeneralesPlugIn().getPlugInManager().getPlugInSeguridad().procesarInicial(
                    moDatosGenerales.getDatosGeneralesPlugIn().getPlugInContexto()
                    );
        }
        setTitle(moDatosGenerales.getParametrosAplicacion().getCaptionProyecto());

        ImageIcon loImage = moDatosGenerales.getImagenFondoQueToqueIcon();
        if(loImage!=null){
            cargaFondoDeEscritorio(loImage.getImage());
        }

        jMenuVentanas = new javax.swing.JMenu();
        jMenuVentanas.setText("Ventanas");
        jMenuBar1.add(jMenuVentanas);
        moDatosGenerales.getMostrarPantalla().addMostrarListener(new IMostrarPantallaListener() {
            public void mostrarPantallaPerformed(JMostrarPantallaEvent poEvent) {
                calcularMenuVentanas();
            }
        });
        calcularMenuVentanas();
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, jMenuArchivo);
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, jMenuVentanas);
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, jMenuABD);
        //algunas veces cuando hay errores anterior los menus no estan bien pintados
        jMenuBar1.updateUI();
        jToolBar1.updateUI();
        moFactbotones.mbGuardarDesktop=false;

    }
    public JCompApliFactory getFactBotones(){
        return moFactbotones;
    }
    public void restaurarBotonesOriginales() throws Exception{
        moFactbotones.restaurarOriginal();
        Element loElem = moDatosGenerales.getDatosGeneralesXML().getElemento(mcsDESCKTOPPrinci);
        if(loElem != null){
            loElem.setValor("");
            moDatosGenerales.getDatosGeneralesXML().getDocumento().getRootElement().remove(loElem);
            moDatosGenerales.getDatosGeneralesXML().guardarFichero();
        }
        moFactbotones.mbGuardarDesktop=false;
    }
    private void asignarActionListener(IComponenteAplicacion loComp){
        if(loComp !=null && loComp instanceof JComponenteAplicacion){
            ((JComponenteAplicacion)loComp).setAccion(getAccion(loComp.getNombre()));
            ((JComponenteAplicacion)loComp).setPropiedades(moFactbotones.getPropiedades(loComp.getNombre()));
            
        }
        IListaElementos loLista = loComp.getListaBotones();
        for(int i = 0 ; loLista !=null && i < loLista.size(); i++){
            IComponenteAplicacion loAux = (IComponenteAplicacion) loLista.get(i);
            asignarActionListener(loAux);
        }
    }
    private ActionListenerCZ getAccion(String psNombre){
        ActionListenerCZ loResult = moFactbotones.getAccion(psNombre);
        if(loResult ==null){
            JMenuItem loM = JPlugInUtilidades.getMenu(jMenuBar1, psNombre);
            if(loM!=null && loM.getActionListeners().length>0){
                loResult = new ActionListenerWrapper(loM.getActionListeners()[0]);
            }
        }
        return loResult;
    }
    public void cargaFondoDeEscritorio(java.awt.Image imagenExterna) {
        try{
            JPintaFondo f = new JPintaFondo(imagenExterna);
            f.setConservarProporciones(moDatosGenerales.getParametrosAplicacion().getConservarProporcionesFondo());
            f.setColorRelleno(new Color(moDatosGenerales.getParametrosAplicacion().getColorFondo().getColor()));
            f.setAlineacionHorizontal(moDatosGenerales.getParametrosAplicacion().getAlineacionHFondo());
            f.setAlineacionVertical(moDatosGenerales.getParametrosAplicacion().getAlineacionVFondo());
            f.setTamanoOriginal(moDatosGenerales.getParametrosAplicacion().isTamanoOriginalFondo());
            jDesktopPaneInterno.setBorder(f);
        } catch (Exception e){
            System.out.println("No se puede cargar fondo de pantalla");
        }
    }
    public JMenuBar getMenu() {
        return jMenuBar1;
    }

    public String getIdentificador() {
        return getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    public IComponenteAplicacion getListaComponentesAplicacion() {
        return moFactbotones.getListaComponentes();
    }

    public void aplicarListaComponentesAplicacion() {
        if(!mbAnularAplicar){
            JPlugInUtilidades.addMenusFrame(this, jMenuBar1, false);
            try {
                moFactbotones.aplicarComp();
            } catch (Exception ex) {
               JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
            }
        }
    }

    public static void ponerLookAndFeel(final JLookAndFeel poLoop, final String psLookAndFeel, final String psTema) {
        if(SwingUtilities.isEventDispatchThread()){
            ponerLookAndFeelReal(poLoop, psLookAndFeel, psTema);
        }else{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ponerLookAndFeelReal(poLoop, psLookAndFeel, psTema);
                }
            });
        }
    }
    public static void ponerLookAndFeelReal(final JLookAndFeel poLoop, final String psLookAndFeel, final String psTema) {
        try{
            if(psLookAndFeel.compareTo("")!=0){

                try{
                    if(psLookAndFeel.equals("com.birosoft.liquid.LiquidLookAndFeel") ||
                       psLookAndFeel.equals("Líquido") ||
                       psLookAndFeel.indexOf("Substance")>0){
                        setDefaultLookAndFeelDecorated(true);
                    }
                    if(psLookAndFeel.equals("com.birosoft.liquid.LiquidLookAndFeel") ||
                       psLookAndFeel.equals("Líquido")){
                        com.birosoft.liquid.LiquidLookAndFeel.setLiquidDecorations(true, "mac");
                    }

                    if(psLookAndFeel.equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel") ||
                       psLookAndFeel.equals("Nimbus")){
//                        UIManager.put("TitledBorder.position", "DEFAULT_POSITION");
//                        UIManager.put("TextPane.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("FormattedTextField.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("Table.cellNoFocusBorder", new Insets(0, 0, 0, 0));
//                        UIManager.put("Table.focusCellHighlightBorder", new Insets(0, 0, 0, 0));
//                        UIManager.put("TextArea.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("PasswordField.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("InternalFrame.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("Button.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("ComboBox.padding", new Insets(3, 3, 3, 3));
//                        UIManager.put("EditorPane.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("ToggleButton.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("TabbedPane:TabbedPaneTabArea.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("List.cellNoFocusBorder", new Insets(0, 0, 0, 0));
//                        UIManager.put("List.focusCellHighlightBorder", new Insets(0, 0, 0, 0));
//                        UIManager.put("RadioButtonMenuItem.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("ScrollPane.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("TextField.contentMargins", new Insets(0, 0, 0, 0));
//                        UIManager.put("\"Table.editor\".contentMargins", new Insets(0, 0, 0, 0));
//                        javax.swing.plaf.BorderUIResource.LineBorderUIResource lo=(javax.swing.plaf.BorderUIResource.LineBorderUIResource) UIManager.get("TitledBorder.border");

//                        UIManager.put("TitledBorder.border", new Insets(10, 10, 10, 10));


                    }
                }catch(Throwable e){}

                poLoop.setEstilo(psLookAndFeel);
                poLoop.setTema(psTema);           
            }else{
                poLoop.setEstilo(javax.swing.UIManager.getSystemLookAndFeelClassName());
//                LookAndFeelManager.applyLookAndFeel();
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(JFormPrincipal.class.getName(), e);
        }
    }
    public static void rellenarLookAndFeel(){
        try{
            JLookAndFeel.rellenarLookAndFeel();
        }catch(Throwable e){}
    }
    private String getRutaLogo(){
    
        String ruta = "";
        
        JFileChooserFiltroPorExtension filtro = 
                new JFileChooserFiltroPorExtension("Imágenes",new String[]{"gif","jpeg", "jpg", "png"});
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           ruta = fileChooser.getSelectedFile().getAbsolutePath();    
        }
        return ruta;
        
    }
        
    private void calcularMenuVentanas(){
        if(jMenuVentanas!=null){
            jMenuVentanas.removeAll();
            boolean lbAlgunaVentana = false;
            if(JMostrarPantalla.class.isAssignableFrom(moDatosGenerales.getMostrarPantalla().getClass())){
                IListaElementos loElem = ((JMostrarPantalla)moDatosGenerales.getMostrarPantalla()).getElementos();
                for(int i = 0 ; i < loElem.size(); i++){
                    final JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario) loElem.get(i);
                    jMenuVentanas.add(getMenuItemVentana(loForm));
                    lbAlgunaVentana = true;
                }            
            }else{
                JInternalFrame[] loIn = jDesktopPane1.getAllFrames();
                for(int i = 0 ; i < loIn.length; i++){
                    if(loIn[i]!=null){
                        jMenuVentanas.add(getMenuItemVentana(loIn[i]));
                        lbAlgunaVentana = true;
                    }
                }
            }
            if(lbAlgunaVentana){
                javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();            
                jMenuVentanas.add(jSeparator1);
            }
            JMenuItem loCerrarTodas = new JMenuItem("Cerrar todas las ventanas");
            if(JMostrarPantalla.class.isAssignableFrom(moDatosGenerales.getMostrarPantalla().getClass())){
                loCerrarTodas.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        IListaElementos loElem = ((JMostrarPantalla)moDatosGenerales.getMostrarPantalla()).getElementos();
                        for(int i = 0 ; i < loElem.size(); i++){
                            final JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario) loElem.get(i);
                            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(loForm);
                        }
                    }
                });
            }else{
                loCerrarTodas.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JInternalFrame[] loIn = jDesktopPane1.getAllFrames();
                        for(int i = 0 ; i < loIn.length; i++){
                            if(loIn[i]!=null){
                                JGUIxConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(loIn[i]);
                            }
                        }
                    }
                });
            }
            jMenuVentanas.add(loCerrarTodas);
            JMenuItem loMenui = new JMenuItem("Añadir grupo botones");
            loMenui.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        moFactbotones.addGrupoActionPerformed();
                    }catch(Throwable ex){
                        utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(JFormPrincipal.this, ex, getClass().getName());
                    }
                }
            });
            jMenuVentanas.add(loMenui);
            if(JMostrarPantalla.class.isAssignableFrom(moDatosGenerales.getMostrarPantalla().getClass())){
                loMenui = new JMenuItem("Vista pantallas abiertas");
                loMenui.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
                loMenui.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            ((JMostrarPantalla)moDatosGenerales.getMostrarPantalla()).mostrarFormsAbiertos();
                        }catch(Throwable ex){
                            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(JFormPrincipal.this, ex, getClass().getName());
                        }
                    }
                });
                jMenuVentanas.add(loMenui);
            }
            loMenui = new JMenuItem("Restaurar configuración de botones");
            loMenui.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(JOptionPane.showConfirmDialog(JFormPrincipal.this
                                ,"¿Estas seguro de restaurar la configuración de botones inicial, perderas la configuracion de botones actual?"
                                , "", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                            restaurarBotonesOriginales();
                        }
                    }catch(Throwable ex){
                        utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(JFormPrincipal.this, ex, getClass().getName());
                    }
                }
            });
            jMenuVentanas.add(loMenui);
            
            javax.swing.JMenuItem jMenuCambiarfondo;
            jMenuCambiarfondo = new javax.swing.JMenuItem();
            jMenuCambiarfondo.setText("Cambiar fondo");
            jMenuCambiarfondo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jMenuCambiarfondoActionPerformed(evt);
                }
            });
            jMenuVentanas.add(jMenuCambiarfondo);
            
            if(mbPrimeraVezVentanas){
                jMenuCambiarfondo = new javax.swing.JMenuItem();
                jMenuCambiarfondo.setText("Cambiar fondo");
                jMenuCambiarfondo.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jMenuCambiarfondoActionPerformed(evt);
                    }
                });
                moFactbotones.getPopUpDesktop().add(jMenuCambiarfondo);
            }
            mbPrimeraVezVentanas=false;
        }

    }
    
    private void jMenuCambiarfondoActionPerformed(ActionEvent evt) {
        try{
            String lsRuta = getRutaLogo();
            if(lsRuta!=null && !lsRuta.equals("")){
                moDatosGenerales.getImagenFondoElem().setValor(lsRuta);
                moDatosGenerales.getDatosGeneralesXML().guardarFichero();
                ImageIcon loImg = moDatosGenerales.getImagenFondoQueToqueIcon();
                if(loImg!=null){
                    cargaFondoDeEscritorio(loImg.getImage());
                }
            }
        } catch (ClassNotFoundException ex1) {
            JMsgBox.mensajeErrorYLog(this,ex1,getClass().getName());
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this,ex,getClass().getName());
            
        }
    }

    private JMenuItem getMenuItemVentana(final JInternalFrame poInternal){
        JMenuItem loItem = new JMenuItem(poInternal.getTitle());
        loItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JMostrarPantalla.mostrarFrente(poInternal, null);
            }
        });
        return loItem;

    }
    private JMenuItem getMenuItemVentana(final JMostrarPantallaFormulario poInternal){
        JMenuItem loItem = new JMenuItem(poInternal.getParam().getTitulo());
        loItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                poInternal.mostrarFrente();
            }
        });
        return loItem;

    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jToolBar1 = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jDesktopPaneInterno = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProcesoThreadGroup1 = new utilesGUIx.controlProcesos.JProcesoThreadGroup();
        lblInformacion = new utilesGUIx.aplicacion.avisosGUI.JLabelAvisos();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuArchivo = new javax.swing.JMenu();
        jMenuASalir = new javax.swing.JMenuItem();
        jMenuAPropiedades = new javax.swing.JMenuItem();
        jMenuAEstilo = new javax.swing.JMenuItem();
        jMenuLogin = new javax.swing.JMenu();
        jMenuLoginNuevo = new javax.swing.JMenuItem();
        jMenuABD = new javax.swing.JMenu();
        jMenuConexion = new javax.swing.JMenuItem();
        jMenuActEstruc = new javax.swing.JMenuItem();
        jMenuASeguridad = new javax.swing.JMenuItem();

        setTitle("Gestión");
        setName("Gestión"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setAutoscrolls(true);

        jDesktopPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jDesktopPane1ComponentResized(evt);
            }
        });
        jDesktopPane1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jDesktopPane1ComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jDesktopPane1ComponentRemoved(evt);
            }
        });
        jDesktopPane1.add(jDesktopPaneInterno);
        jDesktopPaneInterno.setBounds(10, 30, 380, 140);

        jScrollPane1.setViewportView(jDesktopPane1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabel1, gridBagConstraints);

        jProcesoThreadGroup1.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        jPanel1.add(jProcesoThreadGroup1, gridBagConstraints);

        lblInformacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Bombilla-15.gif"))); // NOI18N
        jPanel1.add(lblInformacion, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jMenuArchivo.setText("Archivo");

        jMenuASalir.setText("Salir");
        jMenuASalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuASalirActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuASalir);

        jMenuAPropiedades.setText("Propiedades");
        jMenuAPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAPropiedadesActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuAPropiedades);

        jMenuAEstilo.setText("Estilo visual");
        jMenuAEstilo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAEstiloActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuAEstilo);

        jMenuLogin.setText("Login");

        jMenuLoginNuevo.setText("Nuevo login");
        jMenuLoginNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLoginNuevoActionPerformed(evt);
            }
        });
        jMenuLogin.add(jMenuLoginNuevo);

        jMenuArchivo.add(jMenuLogin);

        jMenuABD.setText("Base datos");

        jMenuConexion.setText("Config. Conexión BD");
        jMenuConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConexionActionPerformed(evt);
            }
        });
        jMenuABD.add(jMenuConexion);

        jMenuActEstruc.setText("Actualizar Estructura y Datos");
        jMenuActEstruc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuActEstrucActionPerformed(evt);
            }
        });
        jMenuABD.add(jMenuActEstruc);

        jMenuArchivo.add(jMenuABD);

        jMenuASeguridad.setText("Seguridad");
        jMenuASeguridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuASeguridadActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuASeguridad);

        jMenuBar1.add(jMenuArchivo);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(555, 401));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuActEstrucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuActEstrucActionPerformed
        try{
            moDatosGenerales.lanzaActualizarEstructuraYDatos();
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_jMenuActEstrucActionPerformed
    private void jMenuAEstiloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAEstiloActionPerformed
        try{
            moDatosGenerales.getLookAndFeelObjeto().mostrar();
            moDatosGenerales.getDatosGeneralesXML().setPropiedad(
                    JDatosGeneralesAplicacion.mcsLookAndFeel,
                    moDatosGenerales.getLookAndFeelObjeto().getEstilo().msEstilo);
            IFilaDatos loFila = moDatosGenerales.getLookAndFeelObjeto().getTema();
            if(loFila==null){
                moDatosGenerales.getDatosGeneralesXML().setPropiedad(JDatosGeneralesAplicacion.mcsLookAndFeelTema, "");
            }else{
                moDatosGenerales.getDatosGeneralesXML().setPropiedad(JDatosGeneralesAplicacion.mcsLookAndFeelTema, loFila.msCampo(JLookAndFeelEstilo.mclTema));
            }
            moDatosGenerales.getDatosGeneralesXML().guardarFichero();
            ponerLookAndFeelReal(
                moDatosGenerales.getLookAndFeelObjeto(),
                moDatosGenerales.getLookAndFeel(),
                moDatosGenerales.getLookAndFeelTema()
                    );
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jMenuAEstiloActionPerformed

    private void jMenuConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConexionActionPerformed
        try{
            moDatosGenerales.mostrarPropiedadesConexionBD();
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_jMenuConexionActionPerformed

    private void jMenuAPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAPropiedadesActionPerformed
        try{
            moDatosGenerales.mostrarPropiedades();
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jMenuAPropiedadesActionPerformed

    private void jMenuLoginNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLoginNuevoActionPerformed
        
        try{
            moDatosGenerales.mostrarLogin();
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }        
    }//GEN-LAST:event_jMenuLoginNuevoActionPerformed

    private void jMenuASalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuASalirActionPerformed
        
        exitForm(null);
    }//GEN-LAST:event_jMenuASalirActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm

        //se llaman a los listener windows closing, menos al 1º q es a si mismo
        //si alguno de estos lanza una exception se para el cerrado de la aplicacion
        WindowListener[] loLists = getWindowListeners();
        for(int i = 1; i <loLists.length;i++){
            if(loLists[i].getClass().getName().indexOf("utilesGUIx.aplicacion.JFormPrincipal")<0){
                loLists[i].windowClosing(evt);
            }
        }
        boolean lbCerrar = true;
        if(moDatosGenerales.getThreadGroup().getListaProcesos().size()>0 && loLists.length<=1){
            lbCerrar=JOptionPane.showConfirmDialog(new JLabel(),
                                "<html><center>"
                                + "Existen tareas pendientes <br>"
                                + "¿Desea seguir cerrando el programa?"
                                + "</center><html>", "",
                                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE
                                )==JOptionPane.YES_OPTION;

        }
        if(lbCerrar){
            try {
                JTablaConfigAbstract.guardarConfig();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
            if(moFactbotones.mbGuardarDesktop){
                try {
                    JComponenteAplicacionGrupoModelo loLista = moFactbotones.getListaComponentes();
    //                JListaElementos loLista = new JListaElementos();
    //                for(int i = 0 ; i < moListaComponentes.size(); i++){
    //                    IComponenteAplicacion loCompApli = (IComponenteAplicacion) moListaComponentes.get(i);
    //                    if(loCompApli.getGrupoBase().equals(IComponenteAplicacion.mcsGRUPOBASEDESKTOP)){
    //                        JComponent loComp=null;
    //                        loComp = moFactbotones.getComponente(loCompApli, this);
    //                        if(loComp!=null){
    //                            loLista.add(loCompApli);
    //                        }
    //                    }
    //                }

                    Element loElem = moDatosGenerales.getDatosGeneralesXML().getElemento(mcsDESCKTOPPrinci);
                    if(loElem == null){
                        loElem =new Element(mcsDESCKTOPPrinci);
                        moDatosGenerales.getDatosGeneralesXML().getDocumento().getRootElement().addContent(loElem);
                    }
                    loElem.clear();
                    loElem.setValor(JXMLDesktop.guardarXML(loLista));
                    moDatosGenerales.getDatosGeneralesXML().guardarFichero();
                } catch (ClassNotFoundException ex1) {
                    JDepuracion.anadirTexto(getClass().getName(), ex1);
                } catch (Throwable ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
            moDatosGenerales.cerrarAplicacion();
            dispose();
            System.exit(0);
        }

    }//GEN-LAST:event_exitForm

    private void jDesktopPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentResized
        try{
            jDesktopPaneInterno.setBounds(
                    0, 0, 
                    (int)jDesktopPane1.getBounds().getWidth(), 
                    (int)jDesktopPane1.getBounds().getHeight());
            
        }catch(Exception e){
            
        }
        
    }//GEN-LAST:event_jDesktopPane1ComponentResized

    private void jDesktopPane1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentAdded
//        calcularMenuVentanas();

    }//GEN-LAST:event_jDesktopPane1ComponentAdded

    private void jDesktopPane1ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentRemoved
//        calcularMenuVentanas();

    }//GEN-LAST:event_jDesktopPane1ComponentRemoved

    private void jMenuASeguridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuASeguridadActionPerformed
        try{
            JPanelGruposYUsuarios loSeg = new JPanelGruposYUsuarios();
            loSeg.setDatos(moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW());
            moDatosGenerales.mostrarForm(new JMostrarPantallaParam(loSeg, 800, 600, JMostrarPantallaParam.mclEdicionFrame, "Seguridad"));
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(null, e.toString());
        }
    }//GEN-LAST:event_jMenuASeguridadActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPaneInterno;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JMenu jMenuABD;
    protected javax.swing.JMenuItem jMenuAEstilo;
    protected javax.swing.JMenuItem jMenuAPropiedades;
    protected javax.swing.JMenuItem jMenuASalir;
    private javax.swing.JMenuItem jMenuASeguridad;
    protected javax.swing.JMenuItem jMenuActEstruc;
    protected javax.swing.JMenu jMenuArchivo;
    protected javax.swing.JMenuBar jMenuBar1;
    protected javax.swing.JMenuItem jMenuConexion;
    protected javax.swing.JMenu jMenuLogin;
    protected javax.swing.JMenuItem jMenuLoginNuevo;
    private javax.swing.JPanel jPanel1;
    protected utilesGUIx.controlProcesos.JProcesoThreadGroup jProcesoThreadGroup1;
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JToolBar jToolBar1;
    private utilesGUIx.aplicacion.avisosGUI.JLabelAvisos lblInformacion;
    // End of variables declaration//GEN-END:variables



    
}
