/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.aplicacion.componentes;

import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesFX.EventHandlerWrapper;
import utilesFX.formsGenericos.JInternalFrameFX;
import utilesFX.formsGenericos.JInternalFrameFXEvent;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesFX.plugin.JPlugInUtilidadesFX;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ItemListenerCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JCompCMBElemento;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupoModelo;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionModelo;


public class JCompApliFactory {

    private EventHandler moListenerInternal = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            maybeShowPopupBotones(t);
        }
    };
    private EventHandler<JInternalFrameFXEvent> moListenerInternalClose = new EventHandler<JInternalFrameFXEvent>() {

        @Override
        public void handle(JInternalFrameFXEvent e) {
            String lsnombre = ((JInternalFrameFX)e.getSource()).getName();
            if(lsnombre!=null){
                IComponenteAplicacion loC = getCompApliPorComp(getListaComponentes()
                        , lsnombre
                        , IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
                getListaComponentes().remove(loC);
                mbGuardarDesktop = true;
            }
        }
    };
    private final ToolBar jToolBar1;
    private final Pane jDesktopPaneInterno;
    private final MenuBar jMenuBar1;
    private JComponenteAplicacionGrupoModelo moListaComponentes = new JComponenteAplicacionGrupoModelo();
    public boolean mbGuardarDesktop = false;
    private boolean mbAccionesCargadas=false;

    Node moIn;
    Node moInSource;
    private final IMostrarPantalla moMostrar;
    private JComponenteAplicacionGrupoModelo moListaComponeCS;
    private ContextMenu jPopupMenu1;
    private ContextMenu jPopupMenuBotones;

    public JCompApliFactory(ToolBar poToolBar1, Pane poDesktopPaneInterno, MenuBar poMenuBar1, IMostrarPantalla poMostrar) {
        jToolBar1 = poToolBar1;
        jDesktopPaneInterno = poDesktopPaneInterno;
        jMenuBar1 = poMenuBar1;
        moMostrar=poMostrar;
        init();
    }
    private void init(){
        jToolBar1.setOnMouseClicked(moListenerInternal);
        jDesktopPaneInterno.setOnMouseClicked(moListenerInternal);
        
        
    }

    /**
     * @return the moListaComponentes
     */
    public JComponenteAplicacionGrupoModelo getListaComponentes() {
        return moListaComponentes;
    }
    
    /**
     * @param moListaComponentes the moListaComponentes to set
     */
    public void setListaComponentes(JComponenteAplicacionGrupoModelo moListaComponentes) {
        this.moListaComponentes = moListaComponentes;
    }

    private Object crearComponente(IComponenteAplicacion poParam, Object loResult, int plVerticalTextAlineacionDefecto) {
        if (poParam.getListaBotones()==null || poParam.getListaBotones().isEmpty() && poParam.getTipo()!=null) {
            if (poParam.getTipo().equals(JComponenteAplicacionModelo.mcsTipoBOTON)) {
                loResult = construirBoton(poParam, (Button) loResult, plVerticalTextAlineacionDefecto);
            }
            if (poParam.getTipo().equals(JComponenteAplicacionModelo.mcsTipoCMB)) {
                loResult = construirCMB(poParam, (ComboBox) loResult);
            }
            if (poParam.getTipo().equals(JComponenteAplicacionModelo.mcsTipoLABEL)) {
                loResult = construirLABEL(poParam, (Label) loResult, plVerticalTextAlineacionDefecto);
            }
            if (poParam.getTipo().equals(JComponenteAplicacionModelo.mcsTipoMENU)) {
                loResult = construirMenuItem(poParam, (MenuItem) loResult, plVerticalTextAlineacionDefecto);
            }
        }
        if ((poParam.getListaBotones()!=null && poParam.getListaBotones().size()>0) || poParam.getTipo()==null) {

            if(poParam.getGrupoBase().equalsIgnoreCase(IComponenteAplicacion.mcsGRUPOMENU)){
                Menu loMenu = null;
                if (loResult == null) {
                    loMenu = new Menu();
                } else {
                    loMenu = (Menu) loResult;
                }
                loMenu.setText(poParam.getCaption());
                loMenu.setVisible(poParam.isActivo());
                loMenu.setId(poParam.getNombre());
                if (poParam.getIcono() != null) {
                    loMenu.setGraphic((ImageView)poParam.getIcono());
                }
                if (loResult == null) {
                    for (int i = 0; i < poParam.getListaBotones().size(); i++) {
                        IComponenteAplicacion loC = (IComponenteAplicacion) poParam.getListaBotones().get(i);
                        Object loComp = crearComponente(loC, null, IComponenteAplicacion.BOTTOM);
                        loMenu.getItems().add((MenuItem)loComp);
                    }
                }
                
                loResult = loMenu;
            }else{
                JInternalFrameFX jInternalFrame1 = null;
                if (loResult == null) {
                    jInternalFrame1 = new JInternalFrameFX();
                } else {
                    jInternalFrame1 = (JInternalFrameFX) loResult;
                }
                final JInternalFrameFX loFormInter = jInternalFrame1;
                
                if(poParam.getDimension()!=null){
                    jInternalFrame1.setPrefSize(
                            (int) poParam.getDimension().getWidth()
                            , (int) poParam.getDimension().getHeight());
                    
                    jInternalFrame1.setLayoutX((int) poParam.getX());
                    jInternalFrame1.setLayoutY((int) poParam.getY());
                }
                jInternalFrame1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                jInternalFrame1.setMinSize(100, 60);
                jInternalFrame1.setId(poParam.getNombre());
                jInternalFrame1.setTitle(poParam.getCaption());
                jInternalFrame1.setVisible(poParam.isActivo());
                jInternalFrame1.setId(poParam.getNombre());
                jInternalFrame1.setStyle(
                        jInternalFrame1.getStyle() 
                        +"-fx-background-color: rgba(0,0,0,0.1);"
                );
                int lColumns = 1;
                if (poParam.getPropiedades().get("Distribucion") != JComponenteAplicacionGrupoModelo.Distribucion.Libre) {
                    GridPane loGrid = new GridPane();
                    loGrid.setStyle("-fx-background-color: rgba(0,0,0,0.1)");
                    loGrid.prefHeight(jInternalFrame1.getPrefHeight()-20);
                    loGrid.prefWidth(jInternalFrame1.getPrefWidth()-10);                    
                    loGrid.maxHeight(Double.MAX_VALUE);
                    loGrid.maxWidth(Double.MAX_VALUE);
                    if(poParam.getPropiedades().get("ColumnasDeBotones")!=null){
                        lColumns = ((Integer)poParam.getPropiedades().get("ColumnasDeBotones")).intValue();
                        for(int i = 0 ; i < lColumns; i++){
                            loGrid.getColumnConstraints().add(
                                    new ColumnConstraints(
                                            10
                                            , 10
                                            , Double.MAX_VALUE
                                            , Priority.ALWAYS
                                            , HPos.CENTER
                                            , true
                                    )
                            );
                        }
                    }
                    int lRowAdd=0;
                    if(poParam.getListaBotones().size() % 2 != 0){
                        lRowAdd=1;
                    }
                    int lRows = (poParam.getListaBotones().size()/lColumns+lRowAdd);
                    for(int i = 0 ; i < lRows; i++){
                        loGrid.getRowConstraints().add(
                                    new RowConstraints(
                                            10
                                            , 10
                                            , Double.MAX_VALUE
                                            , Priority.ALWAYS
                                            , VPos.CENTER
                                            , true
                                    )                        );
                    }
                    loFormInter.setCenter(loGrid);
                    if (loResult == null) {
                        int lColumn=0;
                        int lRow=0;
                        for (int i = 0; i < poParam.getListaBotones().size(); i++) {
                            IComponenteAplicacion loC = (IComponenteAplicacion) poParam.getListaBotones().get(i);
                            Object loComp = crearComponente(loC, null, 3);
                            if(lColumn>=lColumns){
                                lColumn=0;
                                lRow++;
                            }
                            ((Control)loComp).prefHeightProperty().set(Double.MAX_VALUE);
                            ((Control)loComp).prefWidthProperty().set(Double.MAX_VALUE);
                            ((Control)loComp).maxHeight(Double.MAX_VALUE);
                            ((Control)loComp).maxWidth(Double.MAX_VALUE);
                            loGrid.add((Node)loComp, lColumn++, lRow);
                            loGrid.autosize();
                        }
                    }
                    
                } else {
                    FlowPane loFloat = new FlowPane();
                    loFormInter.setCenter(loFloat);
                    if (loResult == null) {
                        for (int i = 0; i < poParam.getListaBotones().size(); i++) {
                            IComponenteAplicacion loC = (IComponenteAplicacion) poParam.getListaBotones().get(i);
                            Object loComp = crearComponente(loC, null, 3);
                            ((Pane)loFormInter.getCenter()).getChildren().add((Node)loComp);
                            if (loC.getDimension() != null) {
                                ((Control)loComp).setLayoutX(loC.getX());
                                ((Control)loComp).setLayoutX(loC.getY());
                                ((Control)loComp).prefWidthProperty().set(loC.getDimension().getWidth());
                                ((Control)loComp).prefHeightProperty().set(loC.getDimension().getHeight());
                            }
                        }
                    }
                }
//                jInternalFrame1.getContentPane().setBackground(new Color(0, 0, 0, 100));
                jInternalFrame1.setOnCloseRequest(moListenerInternalClose);
                loResult = jInternalFrame1;
            }
        }
//        if (loResult != null) {
//            loResult.addMouseListener(moListenerInternal);
//            loResult.addComponentListener(moListenerInternalResize);
//        }

        return loResult;

    }

    private Button construirBoton(IComponenteAplicacion poParam,
            Button loResult, int plVerticalTextAlineacionDefecto) {
        if (loResult == null) {
            loResult = new Button(poParam.getCaption());
            loResult.setFocusTraversable(false);
        }
        loResult.setTooltip(new Tooltip(poParam.getCaption()));
        if (poParam.getIcono() != null) {
            loResult.setGraphic((Node) poParam.getIcono());
        }
        loResult.setOnAction(new ActionListenerModeloWrapper(poParam.getAccion()));
        if (poParam.getNombre() != null) {
            loResult.setId(poParam.getNombre());
        }
        if (poParam.getDimension() != null) {
            loResult.setPrefSize(poParam.getDimension().width, poParam.getDimension().height);
        }
        loResult.setLayoutX(poParam.getX());
        loResult.setLayoutY(poParam.getY());
        loResult.setVisible(poParam.isActivo());
//        loResult.setHorizontalTextPosition(poParam.getHorizontalTextAlignment());
//        if(poParam.getVerticalTextAlignment()==-1){
//            loResult.setVerticalTextPosition(plVerticalTextAlineacionDefecto);
//        }else{
//            loResult.setVerticalTextPosition(poParam.getVerticalTextAlignment());
//        }
        return loResult;
    }
    private MenuItem construirMenuItem(IComponenteAplicacion poParam,
            MenuItem loResult, int plVerticalTextAlineacionDefecto) {
        if (loResult == null) {
            loResult = new MenuItem(poParam.getCaption());
        }
        if (poParam.getIcono() != null) {
            loResult.setGraphic((Node) poParam.getIcono());
        }
        loResult.setOnAction(new ActionListenerModeloWrapper(poParam.getAccion()));
        if (poParam.getNombre() != null) {
            loResult.setId(poParam.getNombre());
        }
        loResult.setVisible(poParam.isActivo());
        return loResult;
    }

    private ComboBox construirCMB(final IComponenteAplicacion poParam, ComboBox poResult) {
        final ComboBox loResult;
        if (poResult == null) {
            loResult = new ComboBox();
        } else {
            loResult = poResult;
        }
        loResult.setTooltip(new Tooltip(poParam.getCaption()));

        if (poParam.getNombre() != null) {
            loResult.setId(poParam.getNombre());
        }
        if (poParam.getDimension() != null) {
            loResult.setPrefSize(poParam.getDimension().width, poParam.getDimension().height);
        }
        loResult.setLayoutX(poParam.getX());
        loResult.setLayoutY(poParam.getY());

        
        IListaElementos loFilas = (IListaElementos) poParam.getPropiedades().get(JComponenteAplicacionModelo.mcsPropiedadCMBElementos);
        if (loFilas != null) {
            JCompCMBElemento[] laArray = new JCompCMBElemento[loFilas.size()];
            for (int i = 0; i < loFilas.size(); i++) {
                JCompCMBElemento loAux = (JCompCMBElemento) loFilas.get(i);
                laArray[i]=loAux;
            }
             poResult.setItems(FXCollections.observableArrayList(laArray));
        }
        final JCompCMB loWrapper = new JCompCMB(loResult);
        loWrapper.setCodigo(poParam.getCaption());
        if (poParam.getAccion() != null) {
            loResult.setOnAction(new ActionListenerModeloWrapper(poParam.getAccion()));
            //llamamos al combo al terminar de construirse
            poParam.getAccion().actionPerformed(
                    new ActionEventCZ(loResult, 0, poParam.getNombre()));
        }
        final ItemListenerCZ loItemListener = (ItemListenerCZ) poParam.getPropiedades().get(JComponenteAplicacionModelo.mcsPropiedadCMBItemStateChange);
//        if (loItemListener != null) {
//            loResult.setonI(
//                    new ItemListener() {
//
//                        public void itemStateChanged(ItemEvent e) {
//                            loItemListener.itemStateChanged(
//                                    new ItemEvent(
//                                    loWrapper,
//                                    e.getID(),
//                                    loResult.getFilaActual(),
//                                    e.getStateChange()));
//                        }
//                    });
//        }

        return loResult;
    }

    private Label construirLABEL(IComponenteAplicacion poParam, Label loResult, int plVerticalTextAlineacionDefecto) {
        if (loResult == null) {
            loResult = new Label(poParam.getCaption());
        }
        loResult.setTooltip(new Tooltip(poParam.getCaption()));
        if (poParam.getIcono() != null) {
            loResult.setGraphic((Node)poParam.getIcono());
        }
        if (poParam.getNombre() != null) {
            loResult.setId(poParam.getNombre());
        }
        if (poParam.getDimension() != null) {
            loResult.setPrefSize(poParam.getDimension().width, poParam.getDimension().height);
        }
        loResult.setLayoutX(poParam.getX());
        loResult.setLayoutY(poParam.getY());

//        loResult.setHorizontalAlignment(poParam.getHorizontalTextAlignment());
//        if(poParam.getVerticalTextAlignment()==-1){
//            loResult.setVerticalTextPosition(plVerticalTextAlineacionDefecto);
//        }else{
//            loResult.setVerticalTextPosition(poParam.getVerticalTextAlignment());
//        }

        return loResult;
    }

    private Node getComponente(IComponenteAplicacion poParam, Parent poContenedor) {
        Node loResult=null;
        if(!JCadenas.isVacio(poParam.getNombre())){
            List<Node> loListNodes = poContenedor.getChildrenUnmodifiable();
            if(poContenedor instanceof ToolBar){
                loListNodes = ((ToolBar)poContenedor).getItems();
            }
            
            for(Node loHijo : loListNodes){
                if(poParam.getNombre().equalsIgnoreCase(loHijo.getId())){
                    loResult=loHijo;
                }
                if(loResult==null){
                    if(loHijo instanceof Parent){
                        loResult=getComponente(poParam, (Parent) loHijo);
                    }
                }
                if(loResult!=null) break;
            }
        }
        return loResult;
    }

    private IComponenteAplicacion getCompApliPorComp(IListaElementos poLista, String psNombre, String psGrupo) {
        IComponenteAplicacion loResult = null;
        for (int i = 0; poLista != null && i < poLista.size(); i++) {
            IComponenteAplicacion loApl = (IComponenteAplicacion) poLista.get(i);
            if ((psGrupo == null || loApl.getGrupoBase().equals(psGrupo))
                    && (psNombre != null && psNombre.equalsIgnoreCase(loApl.getNombre()))) {
                loResult = loApl;
            }
        }
        return loResult;
    }

    public void aplicarComp() throws Exception {
        JPlugInUtilidadesFX.transformarAFX(getListaComponentes());
        aplicarComp(null, getListaComponentes().getListaBotones());
    }
    private void aplicarComp(Parent poContenedor, IListaElementos poBotones) throws Exception {
        for (int i = 0; i < poBotones.size(); i++) {
            IComponenteAplicacion loComp = (IComponenteAplicacion) poBotones.get(i);
            aplicarComp(poContenedor, loComp);
        }
    }

    private void aplicarComp(Parent poContenedor, IComponenteAplicacion poComp) throws Exception {
        
        int lVertical = IComponenteAplicacion.BOTTOM;
        if (poContenedor == null && poComp.getGrupoBase().equals(poComp.mcsGRUPOMENU)) {
            MenuItem loMenuD = JPlugInUtilidadesFX.getMenu(jMenuBar1, poComp.getNombre());
            MenuItem loMenuO =  (MenuItem) crearComponente(poComp, null, 0);
            if(loMenuD==null){
                jMenuBar1.getMenus().add((Menu)loMenuO);
            }else{
                JPlugInUtilidadesFX.addMenus((Menu)loMenuO, (Menu)loMenuD);
            }
        }else{
            ObservableList loItems;
            if (poContenedor == null) {
                if (poComp.getGrupoBase().equals(poComp.mcsGRUPOBASEDESKTOP)) {
                    poContenedor = jDesktopPaneInterno;
                    loItems = jDesktopPaneInterno.getChildren();
                } else {
                    poContenedor = jToolBar1;
                }
            }
            Node loComp = null;
            if(poContenedor instanceof ToolBar){
                lVertical = IComponenteAplicacion.CENTER;
                loItems = ((ToolBar)poContenedor).getItems();
                loComp = (Node)getComponente(poComp, poContenedor);
            }else if(poContenedor instanceof JInternalFrameFX){
                lVertical = IComponenteAplicacion.BOTTOM;
                loItems = ((Pane)((JInternalFrameFX)poContenedor).getCenter()).getChildren();
                loComp = (Node)getComponente(poComp, (Parent)((JInternalFrameFX)poContenedor).getCenter());            
            }else{
                lVertical = IComponenteAplicacion.BOTTOM;
                loItems = ((Pane)poContenedor).getChildren();
                loComp = (Node)getComponente(poComp, poContenedor);
                
            }
            if (loComp == null) {
                loComp = (Node) crearComponente(poComp, null, lVertical);
                loItems.add(loComp);
            }
            loComp.setVisible(poComp.isActivo());
            if (poComp.getListaBotones() != null) {
                aplicarComp((Parent)loComp, poComp.getListaBotones());
            }
        }
    }

    private void visualBotones(Object poSource) {
        Node loIn = null;
        String lsName = null;
        Rectangulo loRect = null;
        if (poSource instanceof Node){
            loIn = (Node) poSource;
            lsName=((Node)poSource).getId();
        }
        IComponenteAplicacion loApl = null;
        if (loIn != null) {
            loApl = getCompApliPorComp(getListaComponentes(), lsName, null);
        }
        
        if (loIn != null && loApl != null && loApl.getDimension()!=null && loApl.getGrupoBase().equals(IComponenteAplicacion.mcsGRUPOBASEDESKTOP)) {
            loApl.setX((int) loIn.getLayoutX());
            loApl.setY((int) loIn.getLayoutY());
            loApl.getDimension().setSize((int)loIn.getBoundsInParent().getWidth(), (int)loIn.getBoundsInParent().getHeight());
            mbGuardarDesktop = true;
        }


    }

    public ActionListenerCZ getAccion(String psNombre) {
        ActionListenerCZ loResult = null;
        IComponenteAplicacion loComp = getCompApliPorComp(getListaComponentes(), psNombre, null);
        for (int i = 0; loComp == null && i < getListaComponentes().size(); i++) {
            IComponenteAplicacion loAux = (IComponenteAplicacion) getListaComponentes().get(i);
            loComp = getCompApliPorComp(loAux.getListaBotones(), psNombre, null);
        }
        if (loComp != null && loComp instanceof JComponenteAplicacionModelo) {
            JComponenteAplicacionModelo loCompA = (JComponenteAplicacionModelo) loComp;
            loResult = loCompA.getAccion();
        }
        return loResult;
    }
    public HashMap getPropiedades(String psNombre) {
        HashMap loResult = null;
        IComponenteAplicacion loComp = getCompApliPorComp(getListaComponentes(), psNombre, null);
        for (int i = 0; loComp == null && i < getListaComponentes().size(); i++) {
            IComponenteAplicacion loAux = (IComponenteAplicacion) getListaComponentes().get(i);
            loComp = getCompApliPorComp(loAux.getListaBotones(), psNombre, null);
        }
        if (loComp != null && loComp instanceof JComponenteAplicacionModelo) {
            JComponenteAplicacionModelo loCompA = (JComponenteAplicacionModelo) loComp;
            loResult = loCompA.getPropiedades();
        }else{
            loResult = new HashMap();
        }
        
        return loResult;
    }

    private void crearMenus(MenuBar poBar, Menu poDestino) {
        for (int i = 0; i < poBar.getMenus().size(); i++) {
            Menu loMenu = poBar.getMenus().get(i);
            if (loMenu != null && loMenu.isVisible()) {
                cargarMenu(loMenu, poDestino);
            }
        }
    }

    private void cargarMenu(Menu poOrigen, Menu poDestino) {
        Menu loDestino = crearMenu(poOrigen, poDestino);
        for (MenuItem loComp :poOrigen.getItems()) {
            if (loComp != null && loComp.isVisible()) {
                if (loComp instanceof Menu) {
                    cargarMenu((Menu) loComp, loDestino);
                } else if (loComp instanceof MenuItem) {
                    crearMenuItem(((MenuItem) loComp), loDestino);
                } else {
                }
            }

        }
    }

    private MenuE crearMenu(Menu poOrigen, Menu poDestino) {
        MenuE loM = new MenuE(poOrigen, this);
        poDestino.getItems().add(loM);
        return loM;
    }

    private MenuItemE crearMenuItem(MenuItem poOrigen, Menu poDestino) {
        MenuItemE loM = new MenuItemE(poOrigen, this);
        poDestino.getItems().add(loM);
        return loM;
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY)  {
            jPopupMenu1.show((Node) e.getSource(), e.getX(), e.getY());
        }
    }
    private void maybeShowPopupBotones(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY)  {
//            if (!mbAccionesCargadas) {
//                mbAccionesCargadas = true;
//                crearMenus(jMenuBar1, jMenuAcciones);
//            }
//            jMenuEliminarFrame.setVisible(true);
//            moInSource=(Node) e.getSource();
//            if (e.getSource() instanceof JInternalFrame || e.getSource() instanceof JToolBar) {
//                moIn = (JComponent) e.getSource();
//                jMenuQuitar.setVisible(false);
//                if (e.getSource() instanceof JToolBar) {
//                    jMenuEliminarFrame.setVisible(false);
//                }
//            
//            } else if (JMostrarPantalla.getFramePadre(e.getSource()) instanceof JInternalFrame) {
//                moIn = (JComponent) JMostrarPantalla.getFramePadre(e.getSource());
//                jMenuQuitar.setVisible(true);
//            } else if (((JComponent) e.getSource()).getParent() instanceof JToolBar) {
//                moIn = (JComponent) ((JComponent) e.getSource()).getParent();
//                jMenuQuitar.setVisible(true);
//                jMenuEliminarFrame.setVisible(false);
//            }
//            jPopupMenuBotones.show(e.getComponent(), e.getX(), e.getY());
        }
    }
//    private void eliminarFrameActionPerformed(java.awt.event.ActionEvent e) {
//        if(moIn!=null && moIn instanceof JInternalFrame ){
//            moListenerInternalClose.internalFrameClosing(new InternalFrameEvent(((JInternalFrame )moIn), 0));
//            ((JInternalFrame )moIn).dispose();
//            mbGuardarDesktop=true;
//        }
//    }
//
//    private void quitarActionPerformed(java.awt.event.ActionEvent e) {
//
//        if(moIn!=null && moIn.getName()!=null){
//            //localizamos el grupo
//            IListaElementos loBotones = null;
//            IComponenteAplicacion loApl = getCompApliPorComp(getListaComponentes(),moIn.getName(), IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
//            if(loApl==null){
//                loApl = getCompApliPorComp(getListaComponentes(),moIn.getName(), null);
//                loBotones = getListaComponentes();
//            }else{
//                loBotones = loApl.getListaBotones();
//            }
//            if(loBotones!=null){
//                try {
//                    IComponenteAplicacion loBtApl = getCompApliPorComp(loBotones, moInSource.getName(), null);
//                    if(loBtApl!=null){
//                        loBotones.remove(loBtApl);
//                    }
//                    moIn.remove(moInSource);
//                    moIn.updateUI();
//                } catch (Exception ex) {
//                    JMsgBox.mensajeErrorYLog(null, ex, getClass().getName());
//                }
//            }
//            mbGuardarDesktop=true;
//        }
//    }
    public void addGrupoActionPerformed() throws Exception {
        JOptionPaneFX.showInputDialog(null, "", "Título", "", new EventHandler<JOptionPaneFX.EventInput>() {
            @Override
            public void handle(JOptionPaneFX.EventInput event) {
                String lsTitulo = event.getInput();
                if(lsTitulo!=null){
                    JComponenteAplicacionGrupoModelo loG = new JComponenteAplicacionGrupoModelo(
                            JComponenteAplicacionGrupoModelo.mcsGRUPOBASEDESKTOP
                            ,"grupo" + new JDateEdu().msFormatear("ddMMyyyyhhmmss")
                            , (int)(jDesktopPaneInterno.getWidth() / 2)
                            , (int)(jDesktopPaneInterno.getHeight() / 2)
                            , new Rectangulo(200, 100));
                    loG.setCaption(lsTitulo);
                    getListaComponentes().add(loG);
                    try {
                        aplicarComp(jDesktopPaneInterno, (IComponenteAplicacion)loG);
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(JCompApliFactory.class.getName(), ex);
                    }
                    mbGuardarDesktop = true;
                }
            }
        }
        );
        
    }
//    private void propiedadesActionPerformed(java.awt.event.ActionEvent evt) {                                                 
//        if(evt.getSource() instanceof JInternalFrame){
//            moIn = (JInternalFrame) evt.getSource();
//        }else if(JMostrarPantalla.getFramePadre(evt.getSource()) instanceof JInternalFrame){
//            moIn = (JInternalFrame) JMostrarPantalla.getFramePadre(evt.getSource());
//        }
//
//        if(moIn!=null && moIn.getName()!=null){
//            IComponenteAplicacion loApl = getCompApliPorComp(getListaComponentes(),moIn.getName(), IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
//            if(loApl!=null){
//                try {
//                    IFormEdicion loEdi = loApl.getEdicion();
//                    moMostrar.mostrarEdicion(loEdi, null, (Component)loEdi, JMostrarPantalla.mclEdicionDialog);
//                    crearComponente(loApl, moIn, SwingConstants.BOTTOM);
//                } catch (Exception ex) {
//                    JMsgBox.mensajeErrorYLog(null, ex, getClass().getName());
//                }
//            }
//            mbGuardarDesktop=true;
//        }
//
//    }                                                
    void addComp(Node poInternal, IComponenteAplicacion poComp) throws Exception {
        mbGuardarDesktop=true;
        if(poInternal instanceof ToolBar){
            getListaComponentes().add(poComp);
            if(poComp instanceof JComponenteAplicacionModelo){
                ((JComponenteAplicacionModelo)poComp).setHorizontalTextAlignment(IComponenteAplicacion.RIGHT);
            }
            aplicarComp((Parent)poInternal, poComp);
        }else{
            if(poComp instanceof JComponenteAplicacionModelo){
                ((JComponenteAplicacionModelo)poComp).setHorizontalTextAlignment(IComponenteAplicacion.CENTER);
            }
            IComponenteAplicacion loApl = getCompApliPorComp(
                    getListaComponentes(),poInternal.getId()
                    , IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
            if(loApl!=null){
                if(getCompApliPorComp(loApl.getListaBotones(), poComp.getNombre(), null)==null){
                    loApl.getListaBotones().add(poComp);
                    aplicarComp((Parent)poInternal, poComp);
                }
            }
        }
    }
    public void copiaSeguridad() throws CloneNotSupportedException{
        moListaComponeCS = getClone(moListaComponentes);

    }

    private JComponenteAplicacionGrupoModelo getClone(JComponenteAplicacionGrupoModelo poLista) throws CloneNotSupportedException{
        JComponenteAplicacionGrupoModelo loListaBotones=new JComponenteAplicacionGrupoModelo();
        for(int i = 0 ; i <poLista.size(); i++){
            IComponenteAplicacion loAux = (IComponenteAplicacion) poLista.get(i);
            loListaBotones.add(loAux.clone());
        }
        return loListaBotones;
        
    }
    public void restaurarOriginal() throws Exception{
        if(moListaComponeCS!=null){
            mbGuardarDesktop = true;
            //borramos todos los botones
            jDesktopPaneInterno.getChildren().removeAll();
            jToolBar1.getItems().removeAll();
            //restauramos cs
            moListaComponentes=getClone(moListaComponeCS);
            //aplicamos cs
            aplicarComp();
        }
    }

    public ContextMenu getPopUpDesktop() {
        return jPopupMenu1;
    }
    public ContextMenu getPopUpBotones() {
        return jPopupMenuBotones;
    }
}
class MenuE extends Menu {

    public EventHandler<ActionEvent> moListener;
    private final JCompApliFactory moPadre;

    MenuE(final Menu poMenu, JCompApliFactory poPadre) {
        super(poMenu.getText());
        setId(poMenu.getId());
        setGraphic(poMenu.getGraphic());
        if (poMenu.getOnAction()!=null) {
            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    if (moPadre.moIn != null && moPadre.moIn.getId()!= null  && !moPadre.moIn.getId().equals("")) {
                        JComponenteAplicacionModelo loB = 
                                new JComponenteAplicacionModelo(
                                    JComponenteAplicacionModelo.mcsTipoBOTON, poMenu.getId()
                                    , poMenu.getText()
                                    , null
                                    , new Rectangulo(160, 25)
                                    , poMenu.getGraphic()
                                    , IComponenteAplicacion.RIGHT);
                        loB.setAccion(new EventHandlerWrapper (moListener));
                        try {
                            moPadre.addComp(moPadre.moIn, loB);
                        } catch (Exception ex) {
                            JMsgBox.mensajeErrorYLog(null, ex, getClass().getName());
                        }
                    }
                }
            });
        }
        moListener = poMenu.getOnAction();
        moPadre = poPadre;

    }

    
}

class MenuItemE extends MenuItem {

    public EventHandler<ActionEvent> moListener;
    private final JCompApliFactory moPadre;

    MenuItemE(final MenuItem poMenu, JCompApliFactory poPadre) {
        super(poMenu.getText());
        setId(poMenu.getId());
        setGraphic(poMenu.getGraphic());
        if (poMenu.getOnAction()!=null) {
            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    if (moPadre.moIn != null && moPadre.moIn.getId()!= null  && !moPadre.moIn.getId().equals("")) {
                        JComponenteAplicacionModelo loB = 
                                new JComponenteAplicacionModelo(
                                    JComponenteAplicacionModelo.mcsTipoBOTON, poMenu.getId()
                                    , poMenu.getText()
                                    , null
                                    , new Rectangulo(160, 25)
                                    , poMenu.getGraphic()
                                    , JComponenteAplicacionModelo.RIGHT);
                        loB.setAccion(new EventHandlerWrapper (moListener));
                        try {
                            moPadre.addComp(moPadre.moIn, loB);
                        } catch (Exception ex) {
                            JMsgBox.mensajeErrorYLog(null, ex, getClass().getName());
                        }
                    }
                }
            });
        }
        moListener = poMenu.getOnAction();
        moPadre = poPadre;
    }
}
