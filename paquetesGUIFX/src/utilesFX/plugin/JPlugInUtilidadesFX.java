/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.plugin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesFX.EventHandlerWrapper;
import utilesGUIx.Rectangulo;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupoModelo;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionModelo;

/**
 *
 * @author eduardo
 */
public class JPlugInUtilidadesFX {
    /**Devuelve el submenu que tiene el actioncomand psComando*/
    public static MenuItem getMenu(MenuBar loMenuBar, String psComando){
        MenuItem loResult = null;
        for(int i = 0; i < loMenuBar.getMenus().size() && loResult == null;i++){
            Menu loMenuAux = loMenuBar.getMenus().get(i);
            if(loMenuAux!=null){
                if(loMenuAux.getId().equals(psComando)){
                    loResult = loMenuAux;
                }else{
                    loResult = getMenu(loMenuAux, psComando);
                }
            }
        }
        return loResult;
    }
    public static MenuItem getMenu(Menu poMenu, String psComando){
        return getMenu(poMenu, psComando, true);
    }
    /**Devuelve el submenu que tiene el actioncomand psComando*/
    public static MenuItem getMenu(Menu poMenu, String psComando, boolean pbRecurs){
        MenuItem loResult = null;
        for(int i = 0; i < poMenu.getItems().size() && loResult == null;i++){
            MenuItem loElem = poMenu.getItems().get(i);
            if(loElem instanceof MenuItem){
                MenuItem loMenu = (MenuItem)loElem;
                if(loMenu.getId().equals(psComando)){
                    loResult = loMenu;
                }else{
                    if(loMenu instanceof Menu && pbRecurs){
                        loResult = getMenu((Menu)loMenu, psComando);
                    }
                }
            }
        }
        return loResult;
    }
    
    
    
    public static void addMenusFrame(IPlugInFrame poFrame, MenuBar jMenuBar1, boolean pbAplicar){
        for(int i = 0 ; i < jMenuBar1.getMenus().size(); i++ ){
            Menu loMenuAux = jMenuBar1.getMenus().get(i);
            if(loMenuAux!=null){
                IComponenteAplicacion loMenu = poFrame.getListaComponentesAplicacion().getComponente(IComponenteAplicacion.mcsGRUPOMENU, loMenuAux.getId());
                if(loMenu!=null){
                    JComponenteAplicacionGrupoModelo loMenuG = (JComponenteAplicacionGrupoModelo) loMenu;
                    JComponenteAplicacionGrupoModelo loGrupo = (JComponenteAplicacionGrupoModelo) getComponenteAplicacion(loMenuAux);
                    mezclaBotones(loGrupo, loMenuG);
                }else{
                    JComponenteAplicacionGrupoModelo loGrupo = (JComponenteAplicacionGrupoModelo) getComponenteAplicacion(loMenuAux);
                    poFrame.getListaComponentesAplicacion().getListaBotones().add(loGrupo);
                }
            }
        }
        if(pbAplicar){
            poFrame.aplicarListaComponentesAplicacion();
        }
    }
    /**Add los menus de jMenuBar1 al poFrame*/
    public static void addMenusFrame(IPlugInFrame poFrame, MenuBar jMenuBar1){
        addMenusFrame(poFrame, jMenuBar1, true);
    }
    public static void addMenusFrame(IPlugInFrame poFrame, Menu jMenu){
        MenuBar jMenuBar1 = new MenuBar();
        jMenuBar1.getMenus().add(jMenu);
        addMenusFrame(poFrame, jMenuBar1, true);
    }
    
    
    /**
     * @deprecated 
     * completa los menus de jMenuDestino con los menus de jMenuOrigen */
    public static void addMenus(IPlugInFrame poFrame, Menu jMenuOrigen, Menu jMenuDestino){
        addMenus(jMenuOrigen, jMenuDestino);
    }
    /**completa los menus de jMenuDestino con los menus de jMenuOrigen */
    public static void addMenus(Menu jMenuOrigen, Menu jMenuDestino){
        while(jMenuOrigen.getItems().size()>0){
            MenuItem loAux = jMenuOrigen.getItems().get(0);
            if(loAux instanceof MenuItem){
                MenuItem loMenu = getMenu(
                                    jMenuDestino,
                                    ((MenuItem) loAux).getId(),
                                    false
                                    );
                if(loMenu!=null){
                    aplicarMenu((MenuItem)loAux, (MenuItem)loMenu);
                    if(loAux instanceof Menu && loMenu instanceof Menu){
                        addMenus(((Menu) loAux) , (Menu)loMenu );
                    }
                }else{
                    jMenuDestino.getItems().add(loAux);
                }
            }else{
                jMenuDestino.getItems().add(loAux);
            }
            jMenuOrigen.getItems().remove(loAux);
        }
    }
    private static void aplicarMenu(MenuItem loMenuO, MenuItem loMenuD){
        loMenuD.setText(loMenuO.getText());
        loMenuD.setGraphic(loMenuO.getGraphic());
//        loMenuD.setMnemonic(loMenuO.getMnemonic());
        loMenuD.setVisible(loMenuO.isVisible());
        EventHandler<ActionEvent> lo  = loMenuO.getOnAction();
        loMenuO.setOnAction(null);
        loMenuD.setOnAction(lo);
    }
    /**Add los menus de jMenuBar1 al poFrame*/
    public static void addMenus(MenuBar jMenuBar1FrameDest, MenuBar jMenuBar1){

        for(int i = 0 ; i < jMenuBar1.getMenus().size(); i++ ){
            Menu loMenuAux = jMenuBar1.getMenus().get(i);
            if(loMenuAux!=null){
                Menu loMenu = (Menu) getMenu(jMenuBar1FrameDest, loMenuAux.getId());
                if(loMenu!=null){
                    addMenus(loMenuAux , loMenu);
                }else{
                    jMenuBar1FrameDest.getMenus().add(loMenuAux);
                    i--;
                }
            }
        }
    }
    /**add un menbu al menu padre en la posicion correspondiente, si piPosicion es negativo lo inserta por el final*/
    public static void addMenu(Menu poMenuPadre, MenuItem poElemento, int piPosicion) {
        int liLongitud = poMenuPadre.getItems().size();
        int liPosSeparador = -1;
        if (piPosicion < 0) {
            poMenuPadre.getItems().add((liPosSeparador != -1 ? liPosSeparador : liLongitud) + piPosicion, poElemento);
        } else {
            poMenuPadre.getItems().add((liPosSeparador != -1 ? liPosSeparador : 0) + piPosicion, poElemento);
        }
    }
    
    public static void mezclaBotones(JComponenteAplicacionGrupoModelo poCompO, JComponenteAplicacionGrupoModelo poCompD){
        for(int i = 0 ; i < poCompO.size();i++){
            IComponenteAplicacion loObjO = (IComponenteAplicacion) poCompO.get(i);
            IComponenteAplicacion loObjD = poCompD.getComponente(loObjO.getGrupoBase(), loObjO.getNombre(), false);
            if(loObjD==null){
                poCompD.add(loObjO);
            }else{
                aplicar(loObjO, loObjD);
                if(loObjO instanceof JComponenteAplicacionGrupoModelo && loObjD instanceof JComponenteAplicacionGrupoModelo){
                    mezclaBotones((JComponenteAplicacionGrupoModelo)loObjO, (JComponenteAplicacionGrupoModelo)loObjD);
                }
            }
        }
    }
    public static void aplicar(IComponenteAplicacion poCompO, IComponenteAplicacion poCompD){
        //por ahora nada
    }
    public static IComponenteAplicacion getComponenteAplicacion(EventTarget loAux){
            IComponenteAplicacion loComp=null;
            if(loAux instanceof Menu){
                Menu loMenu = (Menu) loAux;
                JComponenteAplicacionGrupoModelo loCompG = new JComponenteAplicacionGrupoModelo(
                    JComponenteAplicacionGrupoModelo.mcsGRUPOMENU,
                    loMenu.getId(),    
                    0, 0,
                    new Rectangulo()
                    );
                loComp=loCompG;
                loCompG.setCaption(loMenu.getText());
                loCompG.setNombre(loMenu.getId());
                loCompG.setActivo(loMenu.isVisible());
                loCompG.setIcon(loMenu.getGraphic());
                loCompG.setAccion(new EventHandlerWrapper(loMenu.getOnAction()));
                addBotones(loCompG, loMenu);
            } else if(loAux instanceof MenuItem){
                MenuItem loMenuItem = (MenuItem) loAux;
                loComp = new JComponenteAplicacionModelo(
                    JComponenteAplicacionModelo.mcsTipoMENU
                    , loMenuItem.getId()
                    , loMenuItem.getText()
                    , loMenuItem.getOnAction()!=null
                        ? new EventHandlerWrapper(loMenuItem.getOnAction())
                        : null
                    , new Rectangulo() 
                    , loMenuItem.getGraphic()
                    , 0);
                loComp.setActivo(loMenuItem.isVisible());
//                ((JComponenteAplicacionModelo)loComp).setVerticalTextAlignment(loMenuItem.getVerticalTextPosition());
//                ((JComponenteAplicacionModelo)loComp).setX(loMenuItem.getX());
//                ((JComponenteAplicacionModelo)loComp).setY(loMenuItem.getY());
            } else if(loAux instanceof MenuBar){
                MenuBar loMenu = (MenuBar) loAux;
                JComponenteAplicacionGrupoModelo loCompG = new JComponenteAplicacionGrupoModelo(
                    JComponenteAplicacionGrupoModelo.mcsGRUPOMENU,
                    loMenu.getId(),    
                    (int)loMenu.getLayoutX(), (int)loMenu.getLayoutY(),
                    new Rectangulo((int)loMenu.getPrefWidth(), (int)loMenu.getPrefHeight())
                    );
                loComp=loCompG;
                loCompG.setCaption("");
                loCompG.setNombre(loMenu.getId());
                loCompG.setActivo(loMenu.isVisible());
                addBotones(loCompG, loMenu);
        }
            return loComp;
    }
    public static IComponenteAplicacion getComponenteAplicacion(Node loAux){
            IComponenteAplicacion loComp=null;
            if(loAux instanceof ComboBox){
                ComboBox loCMB = (ComboBox) loAux;
                loComp = new JComponenteAplicacionModelo(
                    JComponenteAplicacionModelo.mcsTipoCMB,
                    loCMB.getId(),
                    loCMB.getSelectionModel().getSelectedItem()!=null 
                        ?loCMB.getSelectionModel().getSelectedItem().toString()
                        :"",
                    null,
                    (loCMB.getOnAction()!=null ?
                        new EventHandlerWrapper(loCMB.getOnAction()):
                        null),
                    new Rectangulo((int)loCMB.getPrefWidth(), (int)loCMB.getPrefHeight())
                    );
                if(loCMB.getItems().size()>0){
                    IListaElementos loElementos = new JListaElementos();
                    for(int lfila = 0 ; lfila < loCMB.getItems().size(); lfila++){
                        loElementos.add(loCMB.getItems().get(lfila));
                    }
                    ((JComponenteAplicacionModelo)loComp).getPropiedades().put(
                        JComponenteAplicacionModelo.mcsPropiedadCMBElementos,
                        loElementos);
                }
                ((JComponenteAplicacionModelo)loComp).setX((int)loCMB.getLayoutX());
                ((JComponenteAplicacionModelo)loComp).setY((int)loCMB.getLayoutY());
            } else  if(loAux instanceof Label){
                Label loLabel = (Label) loAux;
                loComp = new JComponenteAplicacionModelo(
                    JComponenteAplicacionModelo.mcsTipoLABEL,
                    loLabel.getId(),
                    loLabel.getText(),
                    null,
                    null,
                    new Rectangulo((int)loLabel.getPrefWidth(), (int)loLabel.getPrefHeight())
                    );
//                ((JComponenteAplicacionModelo)loComp).setVerticalTextAlignment(loLabel.getVerticalTextPosition());
                ((JComponenteAplicacionModelo)loComp).setX((int)loLabel.getLayoutX());
                ((JComponenteAplicacionModelo)loComp).setY((int)loLabel.getLayoutY());
            } else if(loAux instanceof Button){
                Button loBoton = (Button) loAux;
                loComp = new JComponenteAplicacionModelo(
                    JComponenteAplicacionModelo.mcsTipoBOTON,
                    loBoton.getId(),
                    loBoton.getText(),
                    null,
                    (loBoton.getOnAction()!=null ?
                        new EventHandlerWrapper(loBoton.getOnAction()):
                        null),
                    new Rectangulo((int)loBoton.getPrefWidth(), (int)loBoton.getPrefHeight())
                );
                loComp.setActivo(loBoton.isVisible());
//                ((JComponenteAplicacionModelo)loComp).setVerticalTextAlignment(loBoton.getVerticalTextPosition());
                ((JComponenteAplicacionModelo)loComp).setX((int)loBoton.getLayoutX());
                ((JComponenteAplicacionModelo)loComp).setY((int)loBoton.getLayoutY());
                ((JComponenteAplicacionModelo)loComp).setIcono(loBoton.getGraphic());
            } else if(loAux instanceof Parent){
                Pane loInternal = (Pane) loAux;
                JComponenteAplicacionGrupoModelo loCompG = new JComponenteAplicacionGrupoModelo(
                    JComponenteAplicacionGrupoModelo.mcsGRUPOBASEDESKTOP,
                    loInternal.getId(), 
                    (int)loInternal.getLayoutX(), (int)loInternal.getLayoutY(),
                    new Rectangulo((int)loInternal.getPrefWidth(), (int)loInternal.getPrefHeight())
                    );
                loComp=loCompG;
                loCompG.setCaption("");
                loCompG.setCloseable(true);
                loCompG.setIconificable(true);
                loCompG.setMaximizable(true);
                loCompG.setResizable(loInternal.isResizable());
                loCompG.setNombre(loInternal.getId());
                if(loInternal instanceof GridPane) {
                    loCompG.setDistribucion(JComponenteAplicacionGrupoModelo.Distribucion.Rejilla);
                    int lColumn = ((GridPane)loInternal).getColumnConstraints().size();
                    if(lColumn>0){
                        loCompG.setColumnasDeBotones(lColumn);
                    }
                }else{
                    loCompG.setDistribucion(JComponenteAplicacionGrupoModelo.Distribucion.Libre);
                }
                loCompG.setActivo(loInternal.isVisible());
                addBotones(loCompG, loInternal);
            }
            return loComp;
    }
    private static void addBotones(JComponenteAplicacionGrupoModelo poCompG, Menu jMenuOrigen){
        for(MenuItem loAux : jMenuOrigen.getItems()){
            if(loAux!=null){
                IComponenteAplicacion loComp=getComponenteAplicacion(loAux);
                if(loComp!=null){
                    poCompG.add(loComp);
                }
            }
        }
    }
    public static void addBotones(JComponenteAplicacionGrupoModelo poCompG, Parent poFrame){
        ObservableList<Node> loHijos = poFrame.getChildrenUnmodifiable();
        for(Node loNodo : loHijos){
            IComponenteAplicacion loComp=getComponenteAplicacion(loNodo);
            if(loComp!=null){
                poCompG.add(loComp);
            }
        }
    }
    
    /**add botones/combo/label del toolbar a poFrame*/
    public static void addBotones(IPlugInFrame poFrame, ToolBar jToolBar1){
        ObservableList<Node> loHijos = jToolBar1.getItems();
        for(Node loAux : loHijos){
            IComponenteAplicacion loComp=getComponenteAplicacion(loAux);
            if(loComp!=null){
                poFrame.getListaComponentesAplicacion().getListaBotones().add(loComp);
            }
        }
    }
    public static void addBotones(IPlugInFrame poFrame, Parent poPane){
        IComponenteAplicacion loComp= getComponenteAplicacion(poPane);
        if(loComp!=null){
            poFrame.getListaComponentesAplicacion().getListaBotones().add(loComp);
        }
    }
    
    public static void transformarAFX(IComponenteAplicacion poComp){
        if(poComp instanceof JComponenteAplicacionModelo){
            ((JComponenteAplicacionModelo)poComp).setIcono(null);
        }
        if(poComp instanceof JComponenteAplicacionGrupoModelo){
            ((JComponenteAplicacionGrupoModelo)poComp).setIcon(null);
        }
        if(poComp.getListaBotones()!=null){
            for(int i = 0 ; i < poComp.getListaBotones().size(); i++ ){
                transformarAFX((IComponenteAplicacion) poComp.getListaBotones().get(i));
            }
        }
        
    }
    /**Devuelve el componente que tiene el actioncomand psComando*/
    public static Node getComponente(Parent poContenedor, String psAccion){
        Node loResult = null;
        ObservableList<Node> loChil = poContenedor.getChildrenUnmodifiable();
        for(int i = 0 ; i < loChil.size()&& loResult == null; i++){
            Node loComp = loChil.get(i);
            if(loResult == null &&
               Button.class.isAssignableFrom(loComp.getClass()) ){
                if(((Button)loComp).getId()!=null &&
                   ((Button)loComp).getId().equals(psAccion)){
                    loResult = ((Button)loComp);
                }
            }
            if(loResult == null &&
               Label.class.isAssignableFrom(loComp.getClass()) ){
                if(((Label)loComp).getId()!=null &&
                   ((Label)loComp).getId().equals(psAccion)){
                    loResult = ((Label)loComp);
                }
            }
            if(loResult == null &&
               ComboBox.class.isAssignableFrom(loComp.getClass()) ){
                if(((ComboBox)loComp).getId()!=null &&
                   ((ComboBox)loComp).getId().equals(psAccion)){
                    loResult = ((ComboBox)loComp);
                }
            }
            if(loResult == null &&
               Parent.class.isAssignableFrom(loComp.getClass()) ){
                loResult = getComponente(((Parent)loComp), psAccion);
            }
//            if(loComp.getClass().isAssignableFrom(JToolBar.class) ){
//                loResult = getComponente(((JToolBar)loComp), psAccion);
//            }
        }
        return loResult;
    }
    
    
    

    /**
     * Simple helper class.
     * 
     * @author hendrikebbers
     * 
     */
    private static class ThrowableWrapper {
        Throwable t;
    }
 
    /**
     * Invokes a Runnable in JFX Thread and waits while it's finished. Like
     * SwingUtilities.invokeAndWait does for EDT.
     * 
     * @param run
     *            The Runnable that has to be called on JFX thread.
     * @throws InterruptedException
     *             f the execution is interrupted.
     * @throws ExecutionException
     *             If a exception is occurred in the run method of the Runnable
     */
    public static void runAndWait(final Runnable run)
            throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            try {
                run.run();
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        } else {
            final Lock lock = new ReentrantLock();
            final Condition condition = lock.newCondition();
            final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
            lock.lock();
            try {
                Platform.runLater(new Runnable() {
 
                    @Override
                    public void run() {
                        lock.lock();
                        try {
                            run.run();
                        } catch (Throwable e) {
                            throwableWrapper.t = e;
                        } finally {
                            try {
                                condition.signal();
                            } finally {
                                lock.unlock();
                            }
                        }
                    }
                });
                condition.await();
                if (throwableWrapper.t != null) {
                    throw new ExecutionException(throwableWrapper.t);
                }
            } finally {
                lock.unlock();
            }
        }
    }    
}
