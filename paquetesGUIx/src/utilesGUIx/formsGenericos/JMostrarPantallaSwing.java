/*
 * JMostrarPantalla.java
 *
 * Created on 6 de febrero de 2008, 11:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.image.BufferedImage;
import javax.swing.*;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IPlugInConsulta;

public class JMostrarPantallaSwing extends JMostrarPantallaAbstract {

    public static final int mclEdicionDialog = IMostrarPantalla.mclEdicionDialog;
    public static final int mclEdicionFrame = IMostrarPantalla.mclEdicionFrame;
    public static final int mclEdicionInternal = IMostrarPantalla.mclEdicionInternal;
    public static final int mclEdicionInternalBlanco = IMostrarPantalla.mclEdicionInternalBlanco;
    
    protected JDesktopPane moDesktopPane1;
    protected JListaElementos moElementos = new JListaElementos();
    

    /** Creates a new instance of JMostrarPantalla */
    public JMostrarPantallaSwing(final JDesktopPane poDesktopPane1, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        moDesktopPane1 = poDesktopPane1;
        mlTipoPrincipalMostrar = plTipoPrincipalMostrar;
        mlTipoEdicion = plTipoEdicion;
    }

    public JMostrarPantallaSwing(final IProcesoThreadGroup poGrupoThreads, final JDesktopPane poDesktopPane1, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        this(poDesktopPane1, plTipoEdicion, plTipoPrincipalMostrar);
        moGrupoThreads = poGrupoThreads;
    }
    public IProcesoThreadGroup getGrupoThreads(){
        return moGrupoThreads;
    }

    public JMostrarPantallaCargarForm crearcargarForm(JMostrarPantallaParam poParam) {
        return new JMostrarPantallaCargarForm(this, poParam);
    }

    public void mostrarForm(JMostrarPantallaParam poParam) throws Exception {
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

    public void setDesktopPane1(final JDesktopPane poDesktopPane1) {
        moDesktopPane1 = poDesktopPane1;
    }

    public JDesktopPane getDesktopPane1() {
        return moDesktopPane1;
    }

    public static BufferedImage getImage(Component poComp) throws Exception{
        BufferedImage loImage = new BufferedImage(poComp.getWidth(), poComp.getHeight(), BufferedImage.TYPE_INT_ARGB);
        poComp.paint(loImage.getGraphics());
//        ((JFrame)poComp).paintComponents(loImage.getGraphics());
//        poComp.print(loImage.getGraphics());
//        JIMGTrata.getIMGTrata().mostrarVistaPreliminar(loImage, "");
        return loImage;
    }

    public static Component getFramePadre( Object poComponente) {
        Component retVal = null;
        if (poComponente != null) {
            if (Component.class.isAssignableFrom(poComponente.getClass())) {
                Component loComp = (Component) poComponente;
                while (loComp.getParent() != null && retVal == null) {
                    if (RootPaneContainer.class.isAssignableFrom(loComp.getParent().getClass())) {
                        retVal = ((Component) loComp.getParent());
                    } else {
                        loComp = loComp.getParent();
                    }
                }
            }
        }
        return retVal;
    }
    public Object getActiveInternalFrame(){
        JInternalFrame loResult = null;
        if(moDesktopPane1!=null){
            JInternalFrame[] loFrames = moDesktopPane1.getAllFrames();
            int lMin = 32000;
            for(int i = 0 ; i < loFrames.length; i++){
                JInternalFrame loFram = loFrames[i];
                int lMinAux = moDesktopPane1.getComponentZOrder(loFram);
                if(lMin>lMinAux && lMinAux>=0){
                    lMin=lMinAux;
                    loResult = loFram;
                }
            }
        }
        return loResult;
    }
    public void mostrarFormsAbiertos() throws Exception{
        if(moElementos.size()>=1){
            JMostrarPantallaFormsAbiertos loF = new JMostrarPantallaFormsAbiertos();
            loF.setDatos(this);
            loF.setVisible(true);
        }
    }
    public void cerrarForm(final Object poComponente) {
        if(SwingUtilities.isEventDispatchThread()){
            cerrarFormInterno(poComponente);
        }else{
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
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
                if (poComponente.getClass().isAssignableFrom(Component.class)) {
                    try {
                        Component loComp = getFramePadre(poComponente);
                        if(Window.class.isAssignableFrom(loComp.getClass())  ){
                            Window loW = (Window) loComp;
                            loW.dispose();
                            llamarListener(new JMostrarPantallaEvent(this,JMostrarPantallaEvent.mclCerrarDespues,loW, null));
                        } else if(JInternalFrame.class.isAssignableFrom(loComp.getClass())  ){
                            JInternalFrame loInt = (JInternalFrame) loComp;
                            if (loInt != null) {
                                loInt.dispose();
                                llamarListener(new JMostrarPantallaEvent(this,JMostrarPantallaEvent.mclCerrarDespues,loInt, null));
                            }
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

    public static void setTitleM(Component poComp, String psTitulo) {
        try {
            Component loComp = getFramePadre(poComp);
            if(Frame.class.isAssignableFrom(loComp.getClass())){
                ((Frame) loComp).setTitle(psTitulo);
            }
            if(Dialog.class.isAssignableFrom(loComp.getClass())){
                ((Dialog) loComp).setTitle(psTitulo);
            }
            if(JInternalFrame.class.isAssignableFrom(loComp.getClass())){
                ((JInternalFrame) loComp).setTitle(psTitulo);
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(JMostrarPantallaCargarForm.class.getName(), e);
        }
    }
    public static void mostrarFrente(final Object poForm, final JMostrarPantallaParam poParam) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (poForm instanceof JWindow) {
                    ((JWindow) poForm).setVisible(true);
                    ((JWindow) poForm).toFront();
                    ((JWindow) poForm).requestFocusInWindow();
                } else if (poForm instanceof JInternalFrame) {
                    ((JInternalFrame) poForm).setVisible(true);
                    try {
                        if(((JInternalFrame) poForm).isIcon()){
                            ((JInternalFrame) poForm).setIcon(false);
                        }
                        ((JInternalFrame) poForm).setSelected(true);
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                    ((JInternalFrame) poForm).moveToFront();
                    ((JInternalFrame) poForm).toFront();
                    ((JInternalFrame) poForm).requestFocusInWindow();
                } else if (poForm instanceof Window) {
                    ((Window) poForm).setVisible(true);
                    ((Window) poForm).toFront();
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
    
    public void mostrarOpcion(final Object poActividad, final String psTitulo, final Runnable poSI, final Runnable poNO) {
        final Component loPadre;
        if(poActividad!=null && poActividad instanceof Component){
            loPadre=(Component) poActividad;
        }else{
            loPadre=new JLabel();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(JOptionPane.showConfirmDialog(loPadre, psTitulo, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    if(poSI!=null){
                        poSI.run();
                    }
                }else{
                    if(poNO!=null){
                        poNO.run();
                    }
                }
            }
        });
    }

    public void mensajeErrorYLog(Object poActividad, final Throwable e, final Runnable poOK) {
        final Component loPadre;
        if(poActividad!=null && poActividad instanceof Component){
            loPadre=(Component) poActividad;
        }else{
            loPadre=new JLabel();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JMsgBox.mensajeErrorYLog(loPadre, e);
                if(poOK!=null){
                    poOK.run();
                }
            }
        });
    }

    public void mensaje(Object poActividad, final String psMensaje, final int plMensajeTipo, final Runnable poOK) {
        final Component loPadre;
        if(poActividad!=null && poActividad instanceof Component){
            loPadre=(Component) poActividad;
        }else{
            loPadre=new JLabel();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(plMensajeTipo==mclMensajeError){
                    JMsgBox.mensajeError(loPadre, psMensaje);
                }else{
                    JMsgBox.mensajeInformacion(loPadre, psMensaje);
                }
                if(poOK!=null){
                    poOK.run();
                }
            }
        });
    }
    
    public void mensajeFlotante(Object poPadre, String psMensaje) {
        JVentanaFlotante loV = new JVentanaFlotante();
        loV.setPosicionH(loV.mclCentro);
        loV.setPosicionV(loV.mclCentro);
        loV.setTexto(psMensaje);
        loV.hacerVisibleEInvisible(3);
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

    public void mostrarFormPrinci(IPanelControlador poControlador) throws Exception {
        mostrarForm(new JMostrarPantallaParam(poControlador, 800, 600, JPanelGenerico2.mclTipo, JMostrarPantalla.mclEdicionInternal));
    }

    public void mostrarForm(IMostrarPantallaCrear poPanel)  throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public JMostrarPantallaParam getParam(String psNumeroForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setActividad(Object poAct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
