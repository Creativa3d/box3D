/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import ListDatos.ECampoError;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;
import utilesFX.formsGenericos.edicion.JPanelEdicion;
import utilesFX.formsGenericos.edicion.JPanelEdicionNavegador;
import utilesFX.msgbox.JMsgBox;
import utilesFX.plugin.JPlugInUtilidadesFX;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.JMostrarPantallaEvent;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.JPanelGeneralParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;
import utilesGUIx.formsGenericos.edicion.ITextBD;
import utilesGUIx.plugin.IPlugInFrame;


public class JMostrarPantallaCargarForm  extends JProcesoAccionAbstracX {

    protected JMostrarPantalla moMostrar;
    protected JMostrarPantallaParam moParam;
    protected Object moComponenteFoco;
    private Throwable moError;
//    private JPanelGenerico3 moPanelBusqueda3;
    private JPanelGenerico2 moPanelBusqueda2;
    private JPanelGenerico  moPanelBusqueda1;
//    private Object moComponenteSwing;
    private SwingNode moNodeSwing;



    public JMostrarPantallaCargarForm(final JMostrarPantalla poMostrar,final JMostrarPantallaParam poParam){
        moMostrar = poMostrar;
        moParam = poParam;
         if(moParam.getControlador()!=null){
            moParametros.setTieneCancelado(false);
        }else{
            if(moParam.getComponente()!=null){
                moParametros.setTieneCancelado(false);
            }else{
            }
        }
         if(moParam.getPanel()!=null && moParam.getPanel().getParametros()!=null){
             moComponenteFoco = moParam.getPanel().getParametros().getComponenteDefecto();
             moParam.getPanel().getParametros().setMostrarPantalla(moMostrar);
         }
         if(moParam.getPanelNave()!=null && moParam.getPanelNave().getParametros()!=null){
             moComponenteFoco = moParam.getPanelNave().getParametros().getComponenteDefecto();
             moParam.getPanelNave().getParametros().setMostrarPantalla(moMostrar);
         }
         if(moParam.getComponente()!=null && moParam.getComponente() instanceof IPlugInFrame && ((IPlugInFrame)moParam.getComponente()).getParametros()!=null){
             moComponenteFoco = ((IPlugInFrame)moParam.getComponente()).getParametros().getComponenteDefecto();
             ((IPlugInFrame)moParam.getComponente()).getParametros().setMostrarPantalla(moMostrar);
         }

    }

    @Override
    public String getTitulo() {
        return JFXConfigGlobal.getInstancia().getTextosForms().getTexto("Mostrar form ") + moParam.getTitulo();
    }

    @Override
    public int getNumeroRegistros() {
        return -1;
    }
    
    private void crearPanelGenerico(int plTipo){
        switch(plTipo){
//            case JPanelGenerico3.mclTipo:
//                moPanelBusqueda3 = new JPanelGenerico3();
//                break;
            case JPanelGenerico2.mclTipo:
                moPanelBusqueda2 = new JPanelGenerico2();
                break;
            default:
                moPanelBusqueda1 = new JPanelGenerico();
        }
    }
    public Node getComponentePrinci(final IPanelControlador poControlador, final int plTipo) throws Exception{
        JPanelGenericoAbstract loResult=null;

        crearPanelGenerico(plTipo);
        //creamos el jpanelgeneral dentro del eventdispatch
//        JPlugInUtilidadesFX.runAndWait(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    crearPanelGenerico(plTipo);
//                } catch (Exception ex) {
//                    throw new Error(ex);
//                }
//            }
//        });
        
        //asiganmos el controlador fuera del eventdispatch, ya q el setControlador del JPanelGeneral controla el eventdispatch
        switch(plTipo){
//            case JPanelGenerico3.mclTipo:
//                moPanelBusqueda3.setControlador(poControlador, new ISalir() {
//                        public void salir() {
//                            moMostrar.cerrarForm(moPanelBusqueda3);
//                        }
//
//                        public void setTitle(String psTitulo) {
//                            moMostrar.setTitleM(moPanelBusqueda3,psTitulo);
//                        }
//                    });
//                loResult = moPanelBusqueda3;
//                break;
            case JPanelGenerico2.mclTipo:
                moPanelBusqueda2.setControlador(poControlador, new ISalir() {
                        @Override
                        public void salir() {
                            moMostrar.cerrarForm(moPanelBusqueda2);
                        }

                        @Override
                        public void setTitle(String psTitulo) {
                            moMostrar.setTitleM(moPanelBusqueda2,psTitulo);
                        }
                    });
                loResult = moPanelBusqueda2;
                break;
            default:
                moPanelBusqueda1.setControlador(poControlador, new ISalir() {
                        @Override
                        public void salir() {
                            moMostrar.cerrarForm(moPanelBusqueda1);
                        }

                        @Override
                        public void setTitle(String psTitulo) {
                            moMostrar.setTitleM(moPanelBusqueda1,psTitulo);
                        }
                    });
                loResult = moPanelBusqueda1;
                break;
        }
        loResult.setMostrarDatosParam(moParam);
        try {
            if (poControlador.getParametros().msTipoFiltroRapido.equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorCampo)) {
                moComponenteFoco=(loResult.getPanelGeneralFiltroLinea1().getTable());
            } else if (poControlador.getParametros().msTipoFiltroRapido.equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos)) {
                moComponenteFoco=(loResult.getPanelGeneralFiltroTodosCamp1().getBusqueda());
            }
        } catch (Throwable e) {
        }                
        return loResult;
    }

    public JPanelEdicion getPanelEdicion(final IFormEdicion poPanel, final Object poPanelMismo) throws Exception{
        final JPanelEdicion jPanelEdicion1;
        jPanelEdicion1 = new JPanelEdicion();
        jPanelEdicion1.setPanel(poPanel, getNode(poPanelMismo));
        return jPanelEdicion1;
    }
    public JPanelEdicionNavegador getPanelEdicionNave(final IFormEdicionNavegador poPanel, final Object poPanelMismo, final int plModoSalir, final int plInicioDefecto) throws Exception{
        final JPanelEdicionNavegador jPanelEdicion1;
        jPanelEdicion1 = new JPanelEdicionNavegador();
        jPanelEdicion1.setINICIODEFECTO(plInicioDefecto);
        jPanelEdicion1.setPanel(poPanel, getNode(poPanelMismo));
        jPanelEdicion1.setModoSalida(plModoSalir);
        return jPanelEdicion1;
    }
//    public JPanelEdicionBlanco getPanelEdicionBlanco(final IFormEdicion  poPanel, final Object poPanelMismo) throws Exception{
//        JPanelEdicionBlanco loB = new JPanelEdicionBlanco();
//        loB.setPanel(poPanel, (Component)poPanelMismo);
//        return loB;
//    }

    private void procesarEdicion() throws Exception {

//        //COMPONENTE CONTENEDOR
        if(moMostrar.getPlugInFactoria()!=null){
            if(moParam.getPanel()!=null){
                if(IPlugInFrame.class.isAssignableFrom(moParam.getPanel().getClass())){
                    moMostrar.getPlugInFactoria().getPlugInManager().procesarEdicion(
                            moMostrar.getPlugInFactoria().getPlugInContexto(),
                            (IPlugInFrame)moParam.getPanel()
                            );
                }
            }else{
                if(IPlugInFrame.class.isAssignableFrom(moParam.getPanelNave().getClass())){
                    moMostrar.getPlugInFactoria().getPlugInManager().procesarEdicion(
                            moMostrar.getPlugInFactoria().getPlugInContexto(),
                            (IPlugInFrame)moParam.getPanelNave()
                            );
                }
            }
//ya se hace en mostrarFormPrinciComp     
//            if(moParam.moComponente!=null){
//                if(IPlugInFrame.class.isAssignableFrom(moParam.moComponente.getClass())){
//                    moMostrar.getPlugInFactoria().getPlugInManager().procesarEdicion(
//                            moMostrar.getPlugInFactoria().getPlugInContexto(),
//                            (IPlugInFrame)moParam.moComponente
//                            );
//                }
//            }
        }
        
//        if(moParam.getTipoMostrar()==JMostrarPantalla.mclEdicionInternalBlanco){
//            if(moParam.getPanel()!=null){
//                moParam.setComponente(getPanelEdicionBlanco(
//                       moParam.getPanel(), moParam.getPanel()));
//                moParam.setTitulo(moParam.getPanel().getTitulo());
//            }else{
//                moParam.setComponente(getPanelEdicionBlanco(
//                       moParam.getPanelNave(), moParam.getPanelNave()));
//                moParam.setTitulo(moParam.getPanelNave().getTitulo());
//            }
//        }else{
            if(moParam.getPanel()!=null){
                moParam.setComponente(getPanelEdicion(
                       moParam.getPanel(), moParam.getPanel()));
                moParam.setTitulo(moParam.getPanel().getTitulo());
            }else{
                moParam.setComponente(getPanelEdicionNave(
                       moParam.getPanelNave(), moParam.getPanelNave()
                        , JFXConfigGlobal.getInstancia().getEdicionNavegadorTipoSalida()
                        , JFXConfigGlobal.getInstancia().getEdicionNaveINICIODefect()                )
                );
                moParam.setTitulo(moParam.getPanelNave().getTitulo());
            }

        if(moParam.getPanel()!=null){
            moParam.setAlto((int) moParam.getPanel().getTanano().getHeight());
            moParam.setAncho((int) moParam.getPanel().getTanano().getWidth());
            moParam.setTitulo(moParam.getPanel().getTitulo());
            moParam.getPanel().getParametros().setMostrarParam(moParam);
        }else{
            moParam.setAlto((int) moParam.getPanelNave().getTanano().getHeight());
            moParam.setAncho((int) moParam.getPanelNave().getTanano().getWidth());
            moParam.setTitulo(moParam.getPanelNave().getTitulo());
            moParam.getPanelNave().getParametros().setMostrarParam(moParam);
        }
        

    }
    private Node getNode(final Object poNode){
        Node loNode = null;
        if(Node.class.isAssignableFrom(poNode.getClass())){
            loNode = (Node)poNode;
        }else{
            try {
                final javafx.embed.swing.SwingNode swingNode = new javafx.embed.swing.SwingNode();
                if(javax.swing.SwingUtilities.isEventDispatchThread()){
                    swingNode.setContent((javax.swing.JComponent)poNode);
//                    ((javax.swing.JComponent)poNode).setMinimumSize(new Dimension(10, 10));
//                    ((javax.swing.JComponent)poNode).setPreferredSize(new Dimension(10, 10));
//                    ((javax.swing.JComponent)poNode).setMaximumSize(new Dimension(10, 10));
                    ((javax.swing.JComponent)poNode).requestFocus();
                }else{
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        swingNode.setContent((javax.swing.JComponent)poNode);
//                        ((javax.swing.JComponent)poNode).setMinimumSize(new Dimension(10, 10));
//                        ((javax.swing.JComponent)poNode).setPreferredSize(new Dimension(10, 10));
//                        ((javax.swing.JComponent)poNode).setMaximumSize(new Dimension(10, 10));
                        ((javax.swing.JComponent)poNode).requestFocus();
                    });
                }
                 
                ((SwingNode)swingNode).layoutXProperty().addListener((ObservableValue<? extends Object> ov, Object t, Object t1) -> {
                    swingNode.getContent().updateUI();
//                         swingNode.autosize();//, se queda el swing minusculo, pero donde realmente se pone los desplegables
                });
                ((SwingNode)swingNode).layoutBoundsProperty().addListener((ObservableValue<? extends Object> ov, Object t, Object t1) -> {
                    swingNode.getContent().updateUI();
                });
               
                moNodeSwing=swingNode;
                loNode = swingNode;
            } catch (RuntimeException ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            } catch (Error ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            } catch (Throwable ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
            
        }
        return loNode;
    }
    public void mostrarFormPrinciComp() throws Exception {
        if (IPlugInFrame.class.isAssignableFrom(moParam.getComponente().getClass())) {
            if(((IPlugInFrame)moParam.getComponente()).getParametros()!=null){
                ((IPlugInFrame) moParam.getComponente()).getParametros().setMostrarParam(moParam);
            }
            if(moMostrar.getPlugInFactoria()!=null){
                if (((IPlugInFrame) moParam.getComponente()).getParametros() == null
                        || !((IPlugInFrame) moParam.getComponente()).getParametros().isPlugInPasados()) {
                    moMostrar.getPlugInFactoria().getPlugInManager().procesarEdicion(
                            moMostrar.getPlugInFactoria().getPlugInContexto(),
                            (IPlugInFrame) moParam.getComponente()
                    );
                }
            }
        }
        switch(moParam.getTipoMostrar()){
            case JMostrarPantalla.mclEdicionInternalBlanco:
            case JMostrarPantalla.mclEdicionInternal:
                
                final JInternalFrameFX loFormInter = new JInternalFrameFX();
                moMostrar.getDesktopPane1().getChildren().add(loFormInter);
                loFormInter.setPrefWidth(moParam.getAncho());
                loFormInter.setPrefHeight(moParam.getAlto());
                loFormInter.getStylesheets().add(
                        JFXConfigGlobal.getInstancia().getEstilo()
                            );
                                                
                if(moParam.isMaximizado()){
                    loFormInter.setMaximizado();
                    
                }
                Node loNode = getNode(moParam.getComponente());
                if(loNode instanceof IPadreInterno){
                    ((IPadreInterno)loNode).setPadre(loFormInter);
                }
                loFormInter.setCenter(loNode);
                
                loFormInter.setTitle(moParam.getTitulo());
                
                moMostrar.add(new JMostrarPantallaFormulario(
                        moMostrar, loFormInter, moParam)
                        );
                
                
                loFormInter.setScaleX(0.1);
                loFormInter.setScaleY(0.1);
                
                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(JFXConfigGlobal.mcdTiempoTransiciones), loFormInter);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                scaleTransition.setCycleCount(1);
                //bug javafx swing, para los desplegables
                scaleTransition.setOnFinished((ActionEvent e) -> {
                    if(moNodeSwing!=null){
                        loFormInter.setPrefWidth(loFormInter.getPrefWidth()-1);
                        moNodeSwing.getContent().updateUI();
                    }                    
                });
                
                scaleTransition.play();
                loFormInter.setVisible(true);
                
                Platform.runLater(() -> {
                    loFormInter.requestFocus();
                    if(moComponenteFoco!=null && Node.class.isAssignableFrom(moComponenteFoco.getClass())){
                        ((Node)moComponenteFoco).requestFocus();
                    }
                    loFormInter.requestLayout();
                    if(moNodeSwing!=null){
                        moNodeSwing.requestFocus();
                        if(moComponenteFoco!=null && JComponent.class.isAssignableFrom(moComponenteFoco.getClass())){
                            ((JComponent)moComponenteFoco).requestFocus();
                        }
                    }
                });
                break;
            case JMostrarPantalla.mclEdicionFrame:
            case JMostrarPantalla.mclEdicionDialog:
                final Stage dialog = new Stage(StageStyle.TRANSPARENT);
                
                JFrameFX loFrame = new JFrameFX();
                Node loNodeP = getNode(moParam.getComponente());
                if(loNodeP instanceof IPadreInterno){
                    ((IPadreInterno)loNodeP).setPadre(loFrame);
                }
                
                loFrame.setCenter(loNodeP);
                
                Scene s = new Scene(loFrame, Color.TRANSPARENT);

                s.getStylesheets().add(
                        JFXConfigGlobal.getInstancia().getEstilo()
                            );
                
                dialog.setScene(s);
                loFrame.setStage(dialog);
                if(!moParam.isXCierra()){
                    loFrame.setTop(null);
                    loFrame.getCabezera().setVisible(moParam.isXCierra());
                }
                dialog.setTitle(moParam.getTitulo());
                loFrame.setTitle(moParam.getTitulo());
                dialog.setResizable(true);
                try{
                    dialog.setAlwaysOnTop(moParam.isSiempreDelante());
                }catch(Throwable e1){
                    JDepuracion.anadirTexto(getClass().getName(), e1);
                }
                dialog.centerOnScreen();
                dialog.setWidth(moParam.getAncho());
                dialog.setHeight(moParam.getAlto());
                if(moParam.getImagenIcono()!=null){
                    try{
                        dialog.getIcons().add(((Image)moParam.getImagenIcono()));
                        loFrame.setIcono((Image) moParam.getImagenIcono());
                    }catch(Throwable e){
                        JDepuracion.anadirTexto(getClass().getName(), e);
                    }
                }
                
                
                

                dialog.setMaximized(moParam.isMaximizado());
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                if(moParam.isMaximizado()){
                    dialog.setX(bounds.getMinX());
                    dialog.setY(bounds.getMinY());
                    dialog.setWidth(bounds.getWidth());
                    dialog.setHeight(bounds.getHeight());        
                }else{
                    if(dialog.getWidth()>bounds.getWidth()){
                        dialog.setWidth(bounds.getWidth());
                    }
                    if(dialog.getHeight()>bounds.getHeight()){
                        dialog.setHeight(bounds.getHeight());
                    }
                }              
                moMostrar.add(new JMostrarPantallaFormulario(
                        moMostrar, dialog, moParam)
                        );

                dialog.getScene().getRoot().setScaleX(0.1);
                dialog.getScene().getRoot().setScaleY(0.1);
                
                scaleTransition = new ScaleTransition(Duration.seconds(JFXConfigGlobal.mcdTiempoTransiciones), dialog.getScene().getRoot());
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                scaleTransition.setCycleCount(1);
                //bug javafx swing, para los desplegables
                scaleTransition.setOnFinished((ActionEvent e) -> {
                    if(moNodeSwing!=null){
                        dialog.setMaximized(false);
                        dialog.setWidth(dialog.getWidth()-1);
                        moNodeSwing.getContent().updateUI();
                    }                    
                });
                scaleTransition.play();
                
                Platform.runLater(() -> {
                    dialog.requestFocus();
                    if(moComponenteFoco!=null && moComponenteFoco instanceof Node ){
                        ((Node)moComponenteFoco).requestFocus();
                    }
                    if(moNodeSwing!=null){
                        moNodeSwing.requestFocus();
                        if(moComponenteFoco!=null && JComponent.class.isAssignableFrom(moComponenteFoco.getClass())){
                            ((JComponent)moComponenteFoco).requestFocus();
                        }
//                        if(moParam.isMaximizado()){//los desplegables siguen sin funcionar
//                            dialog.setMaximized(false);
//                            dialog.setWidth(dialog.getWidth()-10);
//                            dialog.setHeight(dialog.getHeight()-1);                        
//                            dialog.setWidth(dialog.getWidth()+1);
//                            dialog.setHeight(dialog.getHeight()+1);
//                        }
                    }                    
                });
                
                if(moParam.getTipoMostrar()==JMostrarPantalla.mclEdicionDialog){
                    dialog.showAndWait();
                }else{
                    dialog.show();
                }
                                 
                break;
            default:
                throw new Exception("Selección de tipo de form principal incorrecto");
        }
        moMostrar.llamarListener(new JMostrarPantallaEvent(moMostrar, JMostrarPantallaEvent.mclAbrirDespues, moParam.getComponente(), moParam));
    }

    private boolean encontrarYReMostrar() {
        boolean lbResult = false;
        IListaElementos loElem = moMostrar.getElementos();
        for(int i = 0 ; i < loElem.size(); i++){
            JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario)loElem.get(i);
            if(loForm.isMismoIdentificador(moParam)){
                lbResult=true;
                loForm.mostrarFrente();
            }

        }
        return lbResult;
    }
    @Override
    public void procesar() throws Throwable {
        boolean lbContinuar = true;
        if(moParam.isUnaSolaInstancia()){
            lbContinuar = !encontrarYReMostrar();
        }
        if(lbContinuar){
            if(moParam.getControlador()!=null){
                //conseguimos el JPanelGeneral sin eventdispatch ya q el mismo controla este tema (asi se pueden conseguir los datos fuera del eventdispatch)
                if((moParam.getTipoMostrar() == JMostrarPantalla.mclEdicionDialog
                        || moParam.getTipoMostrar() == JMostrarPantalla.mclEdicionFrame)
                   && JFXConfigGlobal.getInstancia().isControladoresConSalir()
                   && !moParam.getControlador().getParametros().getBotonesGenerales().getCancelar().isActivo()
                        ){
                    moParam.getControlador().getParametros().getBotonesGenerales().getCancelar().setActivo(true);
                    moParam.getControlador().getParametros().getBotonesGenerales().getCancelar().setCaption("Salir");
                }
                moParam.setComponente(getComponentePrinci(moParam.getControlador(), moParam.getTipoForm()));
                moParam.setTitulo(moParam.getControlador().getParametros().getTitulo());
                moParam.setPanel(null);//tiene prioridad el controlador
                moParam.setPanelNave(null);//tiene prioridad el controlador
            }
            if(moParam.getPanel()!=null || moParam.getPanelNave()!=null){
                JPlugInUtilidadesFX.runAndWait(() -> {
                    try {
                        procesarEdicion();
                    } catch (Throwable ex) {
                        moError=ex;
                    }
                });
                
            }
            if(moParam.getTipoMostrar()==JMostrarPantallaParam.mclEdicionDialog){
                JPlugInUtilidadesFX.runAndWait(() -> {
                    try {
                        mostrarFormPrinciComp();
                    } catch (Throwable ex) {
                        moError=ex;
                    }
                });

            }else{

                Platform.runLater(() -> {
                    try {
                        mostrarFormPrinciComp();
                    } catch (Throwable ex) {
                        JMsgBox.mensajeErrorYLog(null, ex, JMostrarPantallaCargarForm.class.getName());
                    }
                });                        


            }
        }
        if(moError!=null){
            throw moError;
        }        
        mlRegistroActual = 2;
        mbFin=true;

    }

    @Override
    public String getTituloRegistroActual() {
        try{
            return JFXConfigGlobal.getInstancia().getTextosForms().getTexto("Mostrar form ") + moParam.getTitulo();
        }catch(Exception e){
            return JFXConfigGlobal.getInstancia().getTextosForms().getTexto("Mostrar form");
        }
    }
    @Override
    public void mostrarMensaje(String psMensaje) {

    }
    private static ECampoError moErrorSwing;
    public static void setBloqueoControlesSiSwing(final IFormEdicion poPanel, boolean pbBloqueado) throws AWTException{
        if(poPanel instanceof java.awt.Container && !pbBloqueado){
            //bug javafx, control del foco
            Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
        }
    }
    public static void mostrarSiSwing(final IFormEdicion poPanel) throws AWTException, InterruptedException, InvocationTargetException{
        if(poPanel instanceof java.awt.Container ){
            if(SwingUtilities.isEventDispatchThread()){
                    mostrarDatosBD((Container) poPanel);
            }else{
                SwingUtilities.invokeAndWait(() -> {
                    mostrarDatosBD((Container) poPanel);
                });
            }
        }
    }
    public static void establecerSiSwing(final IFormEdicion poPanel) throws ECampoError, InterruptedException, InvocationTargetException{
        if(poPanel instanceof java.awt.Container ){
            if(SwingUtilities.isEventDispatchThread()){
                try {
                    establecerDatosBD((Container) poPanel);
                } catch (ECampoError ex) {
                    moErrorSwing=ex;
                }
            }else{
                SwingUtilities.invokeAndWait(() -> {
                    try {
                        establecerDatosBD((Container) poPanel);
                    } catch (ECampoError ex) {
                        moErrorSwing=ex;
                    }
                });
            }
            
        }
        if(moErrorSwing != null){
            ECampoError loE = moErrorSwing;
            moErrorSwing=null;
            throw loE;
        }
    }
    public static void mostrarDatosBD(final java.awt.Container  poComponente){
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            //establecemos el bloque al componente concreto
            if(ITextBD.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                ITextBD loText = (ITextBD)poComponente.getComponent(i);
                loText.mostrarDatosBD();
            }
            //llamada recursiva para los contenedores
            if(java.awt.Container.class.isAssignableFrom(poComponente.getComponent(i).getClass()) &&
               poComponente != poComponente.getComponent(i)
              ){
                mostrarDatosBD((java.awt.Container)poComponente.getComponent(i));
            }
        }
    }
    public static void establecerDatosBD(final java.awt.Container  poComponente) throws ECampoError{
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            //establecemos el bloque al componente concreto
            if(ITextBD.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                ITextBD loText = (ITextBD)poComponente.getComponent(i);
                loText.establecerDatosBD();
            }
            //llamada recursiva para los contenedores
            if(java.awt.Container.class.isAssignableFrom(poComponente.getComponent(i).getClass()) &&
               poComponente != poComponente.getComponent(i)
              ){
                establecerDatosBD((java.awt.Container)poComponente.getComponent(i));
            }
        }
    }
    
}
