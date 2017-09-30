/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.aplicacion;

import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import org.controlsfx.control.NotificationPane;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.xml.dom.Element;
import utilesFX.EventHandlerWrapper;
import utilesFX.JFXConfigGlobal;
import utilesFX.aplicacion.avisosGUI.JLabelAvisos;
import utilesFX.aplicacion.componentes.JCompApliFactory;
import utilesFX.aplicacion.componentes.xml.JXMLDesktop;
import utilesFX.controlProcesos.JProcesoThreadGroup;
import utilesFX.formsGenericos.JInternalFrameFX;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JMostrarPantallaFormulario;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesFX.plugin.JPlugInUtilidadesFX;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ColorCZ;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IMostrarPantallaListener;
import utilesGUIx.formsGenericos.JMostrarPantallaEvent;
import utilesGUIx.formsGenericos.JTablaConfigAbstract;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupoModelo;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionModelo;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JFormPrincipa1  extends NotificationPane implements IPlugInFrame {
    public static final String mcsBaseDatos = "Base Datos";
    public static final String mcsArchivo = "Archivo";
    public static final String mcsPropiedades = "Propiedades";
    public static final String mcsEstilo = "Estilo";
    public static final String mcsLogin = "Login";
    public static final String mcsDESCKTOPPrinci="DESCKTOPPrinci";
    public static final String mcsSalir="Salir";



    @FXML
    private Menu jMenuABD;

    @FXML
    private MenuItem jMenuASalir;

    @FXML
    private MenuItem jMenuConexion;

    @FXML
    private Label lblMensaje;

    @FXML
    private Pane jDesktopPane1;

    @FXML
    private MenuItem jMenuLoginNuevo;

    @FXML
    private Menu jMenuArchivo;

    @FXML
    private ToolBar jToolBar1;

    @FXML
    private ProgressBar jProcesoThreadGroup1;

    @FXML
    private MenuBar jMenuBar1;

    @FXML
    private Menu jMenuLogin;

    @FXML
    private Button lblInformacion;

    @FXML
    private MenuItem jMenuActEstruc;
    
    private Menu jMenuVentanas;

    private JFormEdicionParametros moParametros = new JFormEdicionParametros();
    private boolean mbAnularAplicar=false;
    private JDatosGeneralesAplicacion moDatosGenerales;
    private JCompApliFactory moFactbotones;
    private boolean mbPrimeraVezVentanas=true;

    
    private JProcesoThreadGroup jProceAux ;
    private JLabelAvisos moGestionAvisos;
    /**
     * Initializes the controller class.
     */
    public JFormPrincipa1() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/aplicacion/JFormPrincipa1.fxml"));
        BorderPane root = new BorderPane();
        loader.setRoot(root);
        loader.setController(this);
        try {
            root = (BorderPane)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }        
        this.getStylesheets().add(
                        JFXConfigGlobal.getInstancia().getEstilo()
                            );
        jMenuASalir.setOnAction((ActionEvent t) -> {
            salir(null);
        });
        jMenuLoginNuevo.setOnAction((ActionEvent t) -> {
            try {
                moDatosGenerales.mostrarLogin();
            } catch (Exception ex) {
                moDatosGenerales.mensajeErrorYLog(JFormPrincipa1.this, ex, null);
            }
        });
        jMenuActEstruc.setOnAction((ActionEvent t) -> {
            try {
                moDatosGenerales.lanzaActualizarEstructuraYDatos();
            } catch (Exception ex) {
                moDatosGenerales.mensajeErrorYLog(JFormPrincipa1.this, ex, null);
            }
        });
        jMenuConexion.setOnAction((ActionEvent t) -> {
            try {
                moDatosGenerales.mostrarPropiedadesConexionBD();
            } catch (Exception ex) {
                moDatosGenerales.mensajeErrorYLog(JFormPrincipa1.this, ex, null);
            }
        });

        
        jProceAux = new JProcesoThreadGroup(jProcesoThreadGroup1, lblMensaje);
        
        jToolBar1.setId(IComponenteAplicacion.mcsGRUPOBASEINTERNO);
        
        setContent(root);
        setShowFromTop(false);
    }    
    public void inicializar(JDatosGeneralesAplicacion poDatosGenerales){
        moDatosGenerales = poDatosGenerales;
        jMenuLogin.setId(mcsLogin);
        jMenuABD.setId(mcsBaseDatos);
        jMenuArchivo.setId(mcsArchivo);
        jMenuASalir.setId(mcsSalir);
        
        
        moFactbotones=new JCompApliFactory(jToolBar1, jDesktopPane1, jMenuBar1, moDatosGenerales);
        
        
        JPlugInUtilidadesFX.addMenusFrame(this, jMenuBar1, false);
        
        moDatosGenerales.setDesktopPane1(
                jDesktopPane1,
                this,
                jProceAux
                );
        lblInformacion.setVisible(moDatosGenerales.getAvisos().size()>0);
        moGestionAvisos =  new JLabelAvisos(lblInformacion, this);
        moGestionAvisos.setAvisos(moDatosGenerales.getAvisos());
        
        mbAnularAplicar=true;
        try{
            moDatosGenerales.getDatosGeneralesPlugIn().getPlugInManager().procesarInicial(
                moDatosGenerales.getDatosGeneralesPlugIn().getPlugInContexto()
                );
        }finally{
            mbAnularAplicar=false;
        }
        try{        
            moFactbotones.copiaSeguridad();
    //        try {
    //            Element loElem = moDatosGenerales.getDatosGeneralesXML().getElemento(mcsDESCKTOPPrinci);
    //            if(loElem != null && loElem.getValue()!=null && loElem.getValue().length()>0){
    //                JComponenteAplicacionGrupoModelo loLista = JXMLDesktop.leerXML(loElem.getValue());
    //                for(int i = 0 ; i < loLista.size(); i++){
    //                    IComponenteAplicacion loApl = (IComponenteAplicacion)loLista.get(i);
    //                    asignarActionListener(loApl);
    //                }
    //                moFactbotones.setListaComponentes(loLista);
    //            }
    //        } catch (ClassNotFoundException ex) {
    //            JDepuracion.anadirTexto(getClass().getName(), ex);
    //        } catch (Throwable ex) {
    //            JDepuracion.anadirTexto(getClass().getName(), ex);
    //        }
            aplicarListaComponentesAplicacion();

            if(moDatosGenerales.getDatosGeneralesPlugIn().getPlugInManager().getPlugInSeguridad()!=null){
                moDatosGenerales.getDatosGeneralesPlugIn().getPlugInManager().getPlugInSeguridad().procesarInicial(
                        moDatosGenerales.getDatosGeneralesPlugIn().getPlugInContexto()
                        );
            }
    //
    //        ImageIcon loImage = moDatosGenerales.getImagenFondoQueToqueIcon();
    //        if(loImage!=null){
    //            cargaFondoDeEscritorio(loImage.getImage());
    //        }
    //

            String lsEstilo=getDesktopPane().getStyle();
            if(moDatosGenerales.getParametrosAplicacion().getColorFondo()!=null){
                ColorCZ loC=moDatosGenerales.getParametrosAplicacion().getColorFondo();
                lsEstilo+="-fx-background-color: "+JFXConfigGlobal.toRGBCode(loC)+";";
            }



            if(moDatosGenerales.getParametrosAplicacion().getImagenFondo()!=null){
    //            try {
//                    File outputFile = File.createTempFile("fondo", ".jpg");
//                    JFXConfigGlobal.saveToFile(
//                            (Image) moDatosGenerales.getParametrosAplicacion().getImagenFondo()
//                            , "jpg", outputFile);
//                    lsEstilo+="-fx-background-image: url("+outputFile.getAbsolutePath()+");";

//                    BackgroundImage myBI= new BackgroundImage(
//                            (Image) moDatosGenerales.getParametrosAplicacion().getImagenFondo()
//                            , BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT
//                            , BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//                    getDesktopPane().setBackground(new Background(myBI));
    //            } catch (IOException ex) {
    //                JDepuracion.anadirTexto(JFormPrincipa1.class.getName(), ex);
    //            }
            }
            getDesktopPane().setStyle(lsEstilo);            
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        
        jMenuVentanas = new Menu();
        jMenuVentanas.setText("Ventanas");
        jMenuBar1.getMenus().add(jMenuVentanas);
        moDatosGenerales.getMostrarPantalla().addMostrarListener(new IMostrarPantallaListener() {
            public void mostrarPantallaPerformed(JMostrarPantallaEvent poEvent) {
                calcularMenuVentanas();
            }
        });
        calcularMenuVentanas();
        //algunas veces cuando hay errores anterior los menus no estan bien pintados
        moFactbotones.mbGuardarDesktop=false;

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
        if(loComp !=null && loComp instanceof JComponenteAplicacionModelo){
            ((JComponenteAplicacionModelo)loComp).setAccion(getAccion(loComp.getNombre()));
            ((JComponenteAplicacionModelo)loComp).setPropiedades(moFactbotones.getPropiedades(loComp.getNombre()));
            
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
            MenuItem loM = JPlugInUtilidadesFX.getMenu(jMenuBar1, psNombre);
            if(loM!=null && loM.getOnAction()!=null){
                loResult = new EventHandlerWrapper(loM.getOnAction());
            }
        }
        return loResult;
    }
    public MenuBar getMenu() {
        return jMenuBar1;
    }    
    public Pane getDesktopPane(){
        return jDesktopPane1;
    }
    public IProcesoThreadGroup getThreadGroup(){
        return jProceAux;
    } 
    
    
    @Override
    public String getIdentificador() {
        return getClass().getName();
    }

    @Override
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return moFactbotones.getListaComponentes();
    }

    @Override
    public void aplicarListaComponentesAplicacion() {
        if(!mbAnularAplicar){
            try {
                moFactbotones.aplicarComp();
            } catch (Exception ex) {
               moDatosGenerales.mensajeErrorYLog(this, ex, null);
            }
        }
    }
//
//    private String getRutaLogo(){
//    
//        String ruta = "";
//        
//        JFileChooserFiltroPorExtension filtro = 
//                new JFileChooserFiltroPorExtension("Imágenes",new String[]{"gif","jpeg", "jpg", "png"});
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.addChoosableFileFilter(filtro);
//        fileChooser.setAcceptAllFileFilterUsed(false);
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//        int returnVal = fileChooser.showOpenDialog(this);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//           ruta = fileChooser.getSelectedFile().getAbsolutePath();    
//        }
//        return ruta;
//        
//    }
        
    private void calcularMenuVentanas(){
        if(jMenuVentanas!=null){
            jMenuVentanas.getItems().clear();
            boolean lbAlgunaVentana = false;
            if(JMostrarPantalla.class.isAssignableFrom(moDatosGenerales.getMostrarPantalla().getClass())){
                IListaElementos loElem = ((JMostrarPantalla)moDatosGenerales.getMostrarPantalla()).getElementos();
                for(int i = 0 ; i < loElem.size(); i++){
                    final JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario) loElem.get(i);
                    jMenuVentanas.getItems().add(getMenuItemVentana(loForm));
                    lbAlgunaVentana = true;
                }            
            }else{
                ObservableList<Node> loIn = jDesktopPane1.getChildren();
                for(Node loNode : loIn){
                    if(loNode!=null && loNode instanceof JInternalFrameFX){
                        jMenuVentanas.getItems().add(getMenuItemVentana((JInternalFrameFX) loNode));
                        lbAlgunaVentana = true;
                    }
                }
            }
            if(lbAlgunaVentana){
                jMenuVentanas.getItems().add(new SeparatorMenuItem());
            }
            MenuItem loCerrarTodas = new MenuItem("Cerrar todas las ventanas");
            if(JMostrarPantalla.class.isAssignableFrom(moDatosGenerales.getMostrarPantalla().getClass())){
                loCerrarTodas.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        IListaElementos loElem = ((JMostrarPantalla)moDatosGenerales.getMostrarPantalla()).getElementos();
                        for(int i = 0 ; i < loElem.size(); i++){
                            final JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario) loElem.get(i);
                            JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(loForm);
                        }
                    }
                });
            }else{
                loCerrarTodas.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ObservableList<Node> loIn = jDesktopPane1.getChildren();
                        for(Node loNode : loIn){
                            if(loNode!=null){
                            if(loNode!=null){
                                JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(loNode);
                            }
                        }
                    }
                    }
                });
            }
            jMenuVentanas.getItems().add(loCerrarTodas);
            MenuItem loMenui;
            loMenui = new MenuItem("Añadir grupo botones");
            loMenui.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    try {
                        moFactbotones.addGrupoActionPerformed();
                    }catch(Throwable ex){
                        JMsgBox.mensajeErrorYLog(JFormPrincipa1.this, ex, getClass().getName());
                    }
                }
            });
            jMenuVentanas.getItems().add(loMenui);
//            if(JMostrarPantalla.class.isAssignableFrom(moDatosGenerales.getMostrarPantalla().getClass())){
//                loMenui = new MenuItem("Vista pantallas abiertas");
//                loMenui.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
//                loMenui.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//                        try {
//                            ((JMostrarPantalla)moDatosGenerales.getMostrarPantalla()).mostrarFormsAbiertos();
//                        }catch(Throwable ex){
//                            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(JFormPrincipal.this, ex, getClass().getName());
//                        }
//                    }
//                });
//                jMenuVentanas.add(loMenui);
//            }
            loMenui = new MenuItem("Restaurar configuración de botones");
            loMenui.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    try {
                        JOptionPaneFX.showConfirmDialog(JFormPrincipa1.this
                                ,"¿Estas seguro de restaurar la configuración de botones inicial, perderas la configuracion de botones actual?"
                                , "", JOptionPaneFX.YES_NO_OPTION, JOptionPaneFX.INFORMATION_MESSAGE
                                , new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            restaurarBotonesOriginales();
                                        } catch (Exception ex) {
                                            JMsgBox.mensajeErrorYLog(JFormPrincipa1.this, ex, getClass().getName());
                                        }
                                    }
                                }
                                , new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                },
                                null
                        );
                    }catch(Throwable ex){
                    }
                }
            });
            jMenuVentanas.getItems().add(loMenui);
            
//            javax.swing.MenuItem jMenuCambiarfondo;
//            jMenuCambiarfondo = new javax.swing.MenuItem();
//            jMenuCambiarfondo.setText("Cambiar fondo");
//            jMenuCambiarfondo.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    jMenuCambiarfondoActionPerformed(evt);
//                }
//            });
//            jMenuVentanas.add(jMenuCambiarfondo);
            
//            if(mbPrimeraVezVentanas){
//                jMenuCambiarfondo = new javax.swing.MenuItem();
//                jMenuCambiarfondo.setText("Cambiar fondo");
//                jMenuCambiarfondo.addActionListener(new java.awt.event.ActionListener() {
//                    public void actionPerformed(java.awt.event.ActionEvent evt) {
//                        jMenuCambiarfondoActionPerformed(evt);
//                    }
//                });
//                moFactbotones.getPopUpDesktop().add(jMenuCambiarfondo);
//            }
            mbPrimeraVezVentanas=false;
        }

    }
//    
//    private void jMenuCambiarfondoActionPerformed(ActionEvent evt) {
//        try{
//            String lsRuta = getRutaLogo();
//            if(lsRuta!=null && !lsRuta.equals("")){
//                moDatosGenerales.getImagenFondoElem().setValor(lsRuta);
//                moDatosGenerales.getDatosGeneralesXML().guardarFichero();
//                ImageIcon loImg = moDatosGenerales.getImagenFondoQueToqueIcon();
//                if(loImg!=null){
//                    cargaFondoDeEscritorio(loImg.getImage());
//                }
//            }
//        } catch (ClassNotFoundException ex1) {
//            JMsgBox.mensajeErrorYLog(this,ex1,getClass().getName());
//        } catch (Throwable ex) {
//            JMsgBox.mensajeErrorYLog(this,ex,getClass().getName());
//            
//        }
//    }

    private MenuItem getMenuItemVentana(final JInternalFrameFX poInternal){
        MenuItem loItem = new MenuItem(poInternal.getTitle());
        loItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JMostrarPantalla.mostrarFrente(poInternal, null);
            }
        });
        return loItem;

    }
    private MenuItem getMenuItemVentana(final JMostrarPantallaFormulario poInternal){
        MenuItem loItem = new MenuItem(poInternal.getParam().getTitulo());
        loItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                poInternal.mostrarFrente();
            }
        });
        return loItem;

    }

    void salir(WindowEvent t) {
//        //se llaman a los listener windows closing, menos al 1º q es a si mismo
//        //si alguno de estos lanza una exception se para el cerrado de la aplicacion
//        WindowListener[] loLists = getWindowListeners();
//        for(int i = 1; i <loLists.length;i++){
//            if(loLists[i].getClass().getName().indexOf("utilesGUIx.aplicacion.JFormPrincipal")<0){
//                loLists[i].windowClosing(evt);
//            }
//        }
        if(moDatosGenerales.getThreadGroup().getListaProcesos().size()>0){
            if(t!=null){
                t.consume();
            }
            JOptionPaneFX.showConfirmDialog(this,
                    ""
                    + "Existen tareas pendientes "
                    + "¿Desea seguir cerrando el programa?"
                    + "", "",
                    JOptionPaneFX.YES_NO_OPTION, JOptionPaneFX.WARNING_MESSAGE
                    ,new Runnable() {
                        @Override
                        public void run() {
                            cerrarInterno();
                        }
                    }
                    ,null
                    ,null
            );
        }else{
            cerrarInterno();
        }

    }
    private void cerrarInterno(){
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

        Platform.exit();        
        System.exit(0);
    }
 
    
}
