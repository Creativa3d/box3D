/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.JDesktopPane;
import utiles.IListaElementos;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;

/**
 *
 * @author eduardo
 */
public class JMostrarPantalla extends JMostrarPantallaAbstract {

    public static final int mclEdicionDialog = IMostrarPantalla.mclEdicionDialog;
    public static final int mclEdicionFrame = IMostrarPantalla.mclEdicionFrame;
    public static final int mclEdicionInternal = IMostrarPantalla.mclEdicionInternal;
    public static final int mclEdicionInternalBlanco = IMostrarPantalla.mclEdicionInternalBlanco;
    
    protected JMostrarPantallaSwing moSwing;
    protected utilesFX.formsGenericos.JMostrarPantalla moFX;
    

    /** Creates a new instance of JMostrarPantalla */
    public JMostrarPantalla(final JDesktopPane poDesktopPane1, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        moSwing = new JMostrarPantallaSwing(poDesktopPane1, plTipoEdicion, plTipoPrincipalMostrar);

    }

    public JMostrarPantalla(final IProcesoThreadGroup poGrupoThreads, final JDesktopPane poDesktopPane1, final int plTipoEdicion, final int plTipoPrincipalMostrar) {
        this(poDesktopPane1, plTipoEdicion, plTipoPrincipalMostrar);
        moSwing.setGrupoThreads(poGrupoThreads);
    }
    
    private IMostrarPantalla getFX(){
        if(moFX == null){    
            moFX = new utilesFX.formsGenericos.JMostrarPantalla(moSwing.getGrupoThreads(), null, moSwing.getTipoEdicion(), moSwing.getTipoPrincipalMostrar());
        }
        return moFX;
    } 

    public void mostrarForm(JMostrarPantallaParam poParam) throws Exception {
        if(poParam.getImagenIcono()==null){
            poParam.setImagenIcono(getImagenIcono());
        }
        if(poParam.getTipoMostrar()==IMostrarPantalla.mclEdicionInternal
                || poParam.getTipoMostrar()==IMostrarPantalla.mclEdicionInternalBlanco
                || poParam.getControlador()!=null
                || (poParam.getComponente()!=null && Component.class.isAssignableFrom(poParam.getComponente().getClass()))
                || (poParam.getPanel()!=null && Component.class.isAssignableFrom(poParam.getPanel().getClass()))
                || (poParam.getPanelNave()!=null && Component.class.isAssignableFrom(poParam.getPanelNave().getClass()))
                ){
            moSwing.mostrarForm(poParam);
        }else{
            getFX().mostrarForm(poParam);
        }
    }

    @Override
    public void setGrupoThreads(IProcesoThreadGroup poGrupoThreads) {
        super.setGrupoThreads(poGrupoThreads); 
        moSwing.setGrupoThreads(poGrupoThreads);
    }
    
    public void setDesktopPane1(final JDesktopPane poDesktopPane1) {
        moSwing.setDesktopPane1(poDesktopPane1);
    }

    public JDesktopPane getDesktopPane1() {
        return moSwing.getDesktopPane1();
    }

    public static BufferedImage getImage(Object poComp) throws Exception{
        if (poComp != null) {
            if (Component.class.isAssignableFrom(poComp.getClass())) {
                return JMostrarPantallaSwing.getImage((Component)poComp);
            }
        }
        return null;
    }

    public static Component getFramePadre( Object poComponente) {
        Component retVal = null;
        if (poComponente != null) {
            if (Component.class.isAssignableFrom(poComponente.getClass())) {
                retVal = JMostrarPantallaSwing.getFramePadre(poComponente);
            }
        }
        return retVal;
    }
    public Object getActiveInternalFrame(){
        return moSwing.getActiveInternalFrame();
    }
    public void mostrarFormsAbiertos() throws Exception{
        moSwing.mostrarFormsAbiertos();
    }
    public void cerrarForm(final Object poComponente) {
        moSwing.cerrarForm(poComponente);
        if(moFX!=null){
            moFX.cerrarForm(poComponente);
        }
    }

    public static void setTitleM(Object poComp, String psTitulo) {
        if(poComp !=null ){
            if(Component.class.isAssignableFrom(poComp.getClass())){
                JMostrarPantallaSwing.setTitleM((Component)poComp, psTitulo);        
//            }else{
//                utilesFX.formsGenericos.JMostrarPantalla.setTitleM(poComp, psTitulo);
            }
        }
    }
    public static void mostrarFrente(final Object poForm, final JMostrarPantallaParam poParam) {
        if(poForm !=null ){
            if(Component.class.isAssignableFrom(poForm.getClass())){
                JMostrarPantallaSwing.mostrarFrente((Component)poForm, poParam);        
//            }else{
//                utilesFX.formsGenericos.JMostrarPantalla.mostrarFrente((Node)poForm, poParam);
            }
        }
    }
    
    public void mostrarOpcion(final Object poActividad, final String psTitulo, final Runnable poSI, final Runnable poNO) {
        moSwing.mostrarOpcion(poActividad, psTitulo, poSI, poNO);
    }

    public void mensajeErrorYLog(Object poActividad, final Throwable e, final Runnable poOK) {
        moSwing.mensajeErrorYLog(poActividad, e, poOK);
    }

    public void mensaje(Object poActividad, final String psMensaje, final int plMensajeTipo, final Runnable poOK) {
        moSwing.mensaje(poActividad, psMensaje, plMensajeTipo, poOK);
    }
    
    public void mensajeFlotante(Object poPadre, String psMensaje) {
        moSwing.mensajeFlotante(poPadre, psMensaje);
    }

    public void add(JMostrarPantallaFormulario poPan){
        moSwing.add(poPan);
    }
    public void remove(JMostrarPantallaFormulario poPan) {
        moSwing.remove(poPan);
    }

    public IListaElementos getElementos(){
        return moSwing.getElementos();
    }

    public void mostrarFormPrinci(IPanelControlador poControlador) throws Exception {
        moSwing.mostrarFormPrinci(poControlador);
    }

    public void mostrarForm(IMostrarPantallaCrear poPanel)  throws Exception {
        moSwing.mostrarForm(poPanel);
    }

    public JMostrarPantallaParam getParam(String psNumeroForm) {
        return moSwing.getParam(psNumeroForm);
    }

    public void setActividad(Object poAct) {
        moSwing.setActividad(poAct);
    }

    public Object getContext() {
        return moSwing.getContext();
    }

    @Override
    public void setImagenIcono(Object moImagenIcono) {
        super.setImagenIcono(moImagenIcono); 
        moSwing.setImagenIcono(moImagenIcono);
    }

}
