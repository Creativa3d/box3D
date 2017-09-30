/*
 * JMostrarPantalla.java
 *
 * Created on 6 de febrero de 2008, 11:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesFX.JFXConfigGlobal;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IMostrarPantallaCrear;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantallaAbstract;
import utilesGUIx.formsGenericos.JMostrarPantallaEvent;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.plugin.IPlugInConsulta;

public class JMostrarPantalla extends JMostrarPantallaAbstract {

    protected Pane moDesktopPane1;
    protected JListaElementos moElementos = new JListaElementos();
    

    /** Creates a new instance of JMostrarPantalla
     * @param poDesktopPane1
     * @param plTipoEdicion
     * @param plTipoPrincipalMostrar 
     */
    public JMostrarPantalla(final Pane poDesktopPane1, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        moDesktopPane1 = poDesktopPane1;
        mlTipoPrincipalMostrar = plTipoPrincipalMostrar;
        mlTipoEdicion = plTipoEdicion;
    }

    public JMostrarPantalla(final IProcesoThreadGroup poGrupoThreads, final Pane poDesktopPane1, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        this(poDesktopPane1, plTipoEdicion, plTipoPrincipalMostrar);
        moGrupoThreads = poGrupoThreads;
    }
    public JMostrarPantalla(final IProcesoThreadGroup poGrupoThreads, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        this((Pane)null, plTipoEdicion, plTipoPrincipalMostrar);
        moGrupoThreads = poGrupoThreads;
    }
    public JMostrarPantallaCargarForm crearcargarForm(JMostrarPantallaParam poParam) {
        return new JMostrarPantallaCargarForm(this, poParam);
    }

    @Override
    public void mostrarForm(JMostrarPantallaParam poParam) throws Exception {
//        if(!JFXConfigGlobal.getInstancia().isInicializadoJAVAFX()){
//            JMostrarPantallaInitFX.initJavaFX();
//        }
        if(poParam.getImagenIcono()==null){
            poParam.setImagenIcono(getImagenIcono());
        }

        JMostrarPantallaCargarForm loCargar = crearcargarForm(poParam);
        
        llamarListener(new JMostrarPantallaEvent(this,JMostrarPantallaEvent.mclAbrirAntes,null, poParam));

        if (moGrupoThreads != null && poParam.getTipoMostrar() != mclEdicionDialog) {
            moGrupoThreads.addProcesoYEjecutar(loCargar);
        } else {
            try {
                loCargar.procesar();

            } catch (Throwable e) {
                if (e instanceof Exception) {
                    throw (Exception) e;
                } else {
                    throw new Exception(e);
                }
            }
        }
    }

    public void setDesktopPane1(final Pane poDesktopPane1) {
        moDesktopPane1 = poDesktopPane1;
    }


    public Pane getDesktopPane1() {
        return moDesktopPane1;
    }

//    public static BufferedImage getImage(Component poComp) throws Exception{
//        BufferedImage loImage = new BufferedImage(poComp.getWidth(), poComp.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        poComp.paint(loImage.getGraphics());
////        ((JFrame)poComp).paintComponents(loImage.getGraphics());
////        poComp.print(loImage.getGraphics());
////        JIMGTrata.getIMGTrata().mostrarVistaPreliminar(loImage, "");
//        return loImage;
//    }

    public static Object getFramePadre( Node poComponente) {
        Object loResult = getFrameInterno(poComponente);
        if(loResult==null){
            loResult = poComponente.getScene().getWindow();
        }
        return loResult;
    }
    public static Object getFrameInterno( Node poComponente) {
        Object loResult = null;
        Parent loPadre = poComponente.getParent();
        if(loPadre!=null){
            if(loPadre instanceof JInternalFrameFX){
                loResult = loPadre;
            } else {
                loResult = getFrameInterno(loPadre);
            }
        }
        return loResult;
    }
    
    @Override
    public Object getActiveInternalFrame(){
        Object loResult = null;
        if(moDesktopPane1!=null && moDesktopPane1.getChildren().size()>0){
            loResult = (JInternalFrameFX) moDesktopPane1.getChildren().get(0);
        }
        return loResult;
    }
    public void mostrarFormsAbiertos() throws Exception{
//        if(moElementos.size()>=1){
//            JMostrarPantallaFormsAbiertos loF = new JMostrarPantallaFormsAbiertos();
//            loF.setDatos(this);
//            loF.setVisible(true);
//        }
    }
    @Override
    public void cerrarForm(final Object poComponente) {
        if(Platform.isFxApplicationThread()){
            cerrarFormInterno(poComponente);
        }else{
            try {
                Platform.runLater(new Runnable() {
                    public void run() {
                        cerrarFormInterno(poComponente);
                    }
                });
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }        
    }
    private void cerrarFormInterno(Object poComponente) {
        boolean lbCerrado = false;
        try{
            if(poComponente instanceof JMostrarPantallaFormulario){
                JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario) poComponente;
                try {
                    loForm.dispose();
                } catch (Throwable e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }
                remove(loForm);
                lbCerrado = true;
            }
            for (int i = 0; i < moElementos.size() && !lbCerrado; i++) {
                JMostrarPantallaFormulario loForm = (JMostrarPantallaFormulario) moElementos.get(i);
                if (loForm.isComponente(poComponente)) {
                    try {
                        loForm.dispose();
                    } catch (Throwable e) {
                        JDepuracion.anadirTexto(getClass().getName(), e);
                    }
                    remove(loForm);
                    lbCerrado = true;
                }
            }
            if (!lbCerrado && poComponente != null) {
                if (Node.class.isAssignableFrom(poComponente.getClass())) {
                    try {
                        Object loComp = getFramePadre((Node) poComponente);
                        if(JInternalFrameFX.class.isAssignableFrom(loComp.getClass())  ){
                            JInternalFrameFX loInt = (JInternalFrameFX) loComp;
                            loInt.close();
                            llamarListener(new JMostrarPantallaEvent(this,JMostrarPantallaEvent.mclCerrarDespues,loInt, null));
                        } else if(Stage.class.isAssignableFrom(loComp.getClass())  ){
                            Stage loW = (Stage) loComp;
                            loW.close();
                            llamarListener(new JMostrarPantallaEvent(this,JMostrarPantallaEvent.mclCerrarDespues,loW, null));
                        } 
                    } catch (Throwable e) {
                        JDepuracion.anadirTexto(getClass().getName(), e);
                    }

                }
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        System.gc();
    }

    public static void setTitleM(Object poComp, String psTitulo) {
        try {
            Object loComp = getFramePadre((Node)poComp);
            if(Stage.class.isAssignableFrom(loComp.getClass())){
                ((Stage) loComp).setTitle(psTitulo);
            } else  if(JInternalFrameFX.class.isAssignableFrom(loComp.getClass())){
                ((JInternalFrameFX) loComp).setTitle(psTitulo);
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(JMostrarPantallaCargarForm.class.getName(), e);
        }
    }
    public static void mostrarFrente(final Object poForm, final JMostrarPantallaParam poParam) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (poForm instanceof Stage) {
                    ((Stage) poForm).show();
                    ((Stage) poForm).toFront();
                    ((Stage) poForm).requestFocus();
                } else if (poForm instanceof JInternalFrameFX) {
                    JInternalFrameFX loInternal = ((JInternalFrameFX) poForm);
                    loInternal.setVisible(true);
                    
                    loInternal.toFront();
                    loInternal.requestFocus();
                } 
                if (poParam!=null && poParam.getComponente() != null && IPlugInConsulta.class.isAssignableFrom(poParam.getComponente().getClass())) {
                    try {
                        IPlugInConsulta loC = ((IPlugInConsulta) poParam.getComponente());
                        if (loC.getControladorActual() != null) {
                            loC.getControladorActual().refrescar();
                        }
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                }
                if (poParam!=null && poParam.getControlador() != null) {
                    try {
                        poParam.getControlador().refrescar();
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                }
            }
        });
    }

    public void add(JMostrarPantallaFormulario poPan){
            moElementos.add(poPan);
    }
    public void remove(JMostrarPantallaFormulario poPan) {
        //se comprueba si existe el form en la lista se manda el evento, pq puede llamarse mas de una vez
        if(moElementos.indexOf(poPan)>=0){
            moElementos.remove(poPan);
            llamarListener(new JMostrarPantallaEvent(this,JMostrarPantallaEvent.mclCerrarDespues,poPan.getForm(), poPan.getParam()));
        }
    }

    public IListaElementos getElementos(){
        return moElementos;
    }

    @Override
    public void mostrarFormPrinci(IPanelControlador poControlador) throws Exception {
        mostrarForm(new JMostrarPantallaParam(poControlador, 800, 600, JPanelGenerico2.mclTipo, JMostrarPantalla.mclEdicionInternal));
    }

    @Override
    public void mostrarForm(IMostrarPantallaCrear poPanel)  throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JMostrarPantallaParam getParam(String psNumeroForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActividad(Object poAct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarOpcion(final Object poActividad, final String psTitulo, final Runnable poSI, final Runnable poNO) {
        JOptionPaneFX.showConfirmDialog(poActividad, psTitulo, poSI, poNO);
    }

    @Override
    public void mensajeErrorYLog(Object poActividad, final Throwable e, final Runnable poOK) {
        JMsgBox.mensajeErrorYLog(poActividad, e, poOK);
    }

    @Override
    public void mensaje(final Object poActividad, final String psMensaje, final int plMensajeTipo, final Runnable poOK) {
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(plMensajeTipo==mclMensajeError){
                    JMsgBox.mensajeError(poActividad, psMensaje);
                }else{
                    JMsgBox.mensajeInformacion(poActividad, psMensaje);
                }
                if(poOK!=null){
                    poOK.run();
                }
            }
        });
    }
    
    @Override
    public void mensajeFlotante(Object poPadre, String psMensaje) {
        ImageView graphic = new ImageView(JOptionPaneFX.imageINFORMATION);
        graphic.setScaleX(0.5);
        graphic.setScaleY(0.5);
        notification(poPadre, graphic,  psMensaje, Pos.CENTER, null);
    }
    
    public static void notification(Object poPadre, ImageView graphic, String text, Pos pos, EventHandler<ActionEvent> poEvent) {
        Notifications notificationBuilder = Notifications.create()
                .title("")
                .text(text)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3))
                .position(pos);
        if(poEvent!=null){
            notificationBuilder.onAction(poEvent);
        }
        if(poPadre!=null && (poPadre instanceof Node) && ((Node)poPadre).getScene()!=null ){
            notificationBuilder.owner(((Node)poPadre).getScene().getWindow());
        }
        
        if(Platform.isFxApplicationThread()){
            notificationBuilder.showInformation();
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    notificationBuilder.showInformation();                
                }
            });
        }
    }
        
}
