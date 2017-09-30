/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IPlugInFrame;


public class JMostrarPantallaCargarForm  extends JProcesoAccionAbstracX {

    protected JMostrarPantallaSwing moMostrar;
    protected JMostrarPantallaParam moParam;
    protected Component moComponenteFoco;
    private Throwable moError;
    private JPanelGenerico3 moPanelBusqueda3;
    private JPanelGenerico2 moPanelBusqueda2;
    private JPanelGenerico moPanelBusqueda1;



    public JMostrarPantallaCargarForm(final JMostrarPantallaSwing poMostrar,final JMostrarPantallaParam poParam){
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
             moComponenteFoco = (Component)moParam.getPanel().getParametros().getComponenteDefecto();
             moParam.getPanel().getParametros().setMostrarPantalla(moMostrar);
         }
         if(moParam.getPanelNave()!=null && moParam.getPanelNave().getParametros()!=null){
             moComponenteFoco = (Component)moParam.getPanelNave().getParametros().getComponenteDefecto();
             moParam.getPanelNave().getParametros().setMostrarPantalla(moMostrar);
         }
         if(moParam.getComponente()!=null && moParam.getComponente() instanceof IPlugInFrame && ((IPlugInFrame)moParam.getComponente()).getParametros()!=null){
             moComponenteFoco = (Component)((IPlugInFrame)moParam.getComponente()).getParametros().getComponenteDefecto();
             ((IPlugInFrame)moParam.getComponente()).getParametros().setMostrarPantalla(moMostrar);
         }

    }

    public String getTitulo() {
        return JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Mostrar form ") + moParam.getTitulo();
    }

    public int getNumeroRegistros() {
        return -1;
    }
    private void crearPanelGenerico(final int plTipo) throws Exception{
        //creamos el JPanelGeneral en el eventdispatch
        if(SwingUtilities.isEventDispatchThread()){
            crearPanelGenericoInterno(plTipo);
        }else{
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try{
                        crearPanelGenericoInterno(plTipo);
                    }catch(Throwable e){
                        JDepuracion.anadirTexto(getClass().getName(), e);
                    }
                }
            });
        }            
    }
    private void crearPanelGenericoInterno(int plTipo){
        switch(plTipo){
            case JPanelGenerico3.mclTipo:
                moPanelBusqueda3 = new JPanelGenerico3();
                break;
            case JPanelGenerico2.mclTipo:
                moPanelBusqueda2 = new JPanelGenerico2();
                break;
            default:
                moPanelBusqueda1 = new JPanelGenerico();
        }
    }
    public JComponent getComponentePrinci(final IPanelControlador poControlador, final int plTipo) throws Exception{
        JPanelGenericoAbstract loResult;

        //creamos el jpanelgeneral dentro del eventdispatch
        crearPanelGenerico(plTipo);
        //asiganmos el controlador fuera del eventdispatch, ya q el setControlador del JPanelGeneral controla el eventdispatch
        switch(plTipo){
            case JPanelGenerico3.mclTipo:
                moPanelBusqueda3.setControlador(poControlador, new ISalir() {
                        public void salir() {
                            moMostrar.cerrarForm(moPanelBusqueda3);
                        }

                        public void setTitle(String psTitulo) {
                            moMostrar.setTitleM(moPanelBusqueda3,psTitulo);
                        }
                    });
                loResult = moPanelBusqueda3;
                break;
            case JPanelGenerico2.mclTipo:
                moPanelBusqueda2.setControlador(poControlador, new ISalir() {
                        public void salir() {
                            moMostrar.cerrarForm(moPanelBusqueda2);
                        }

                        public void setTitle(String psTitulo) {
                            moMostrar.setTitleM(moPanelBusqueda2,psTitulo);
                        }
                    });
                loResult = moPanelBusqueda2;
                break;
            default:
                moPanelBusqueda1.setControlador(poControlador, new ISalir() {
                        public void salir() {
                            moMostrar.cerrarForm(moPanelBusqueda1);
                        }

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
                moComponenteFoco=(loResult.getPanelGeneralFiltroLinea1());
            } else if (poControlador.getParametros().msTipoFiltroRapido.equals(JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos)) {
                moComponenteFoco=(loResult.getPanelGeneralFiltroTodosCamp1());
            }
        } catch (Throwable e) {
        }        
        return loResult;
    }

    public JPanelEdicion getPanelEdicion(final IFormEdicion poPanel, final Object poPanelMismo) throws Exception{
        final JPanelEdicion jPanelEdicion1;
        jPanelEdicion1 = new JPanelEdicion();
        jPanelEdicion1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelEdicion1.setPanel(poPanel, getComponent(poPanelMismo), new ISalir() {
            public void salir() {
                moMostrar.cerrarForm(jPanelEdicion1);
            }

            public void setTitle(String psTitulo) {
                moMostrar.setTitleM(jPanelEdicion1,psTitulo);
            }
        });
        return jPanelEdicion1;
    }
    public JPanelEdicionNavegador getPanelEdicionNave(final IFormEdicionNavegador poPanel, final Object poPanelMismo, final int plModoSalir, final int plInicioDefecto) throws Exception{
        final JPanelEdicionNavegador jPanelEdicion1;
        jPanelEdicion1 = new JPanelEdicionNavegador();
        jPanelEdicion1.setINICIODEFECTO(plInicioDefecto);
        jPanelEdicion1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelEdicion1.setPanel(poPanel, getComponent(poPanelMismo), new ISalir() {
            public void salir() {
                moMostrar.cerrarForm(jPanelEdicion1);
            }

            public void setTitle(String psTitulo) {
                moMostrar.setTitleM(jPanelEdicion1,psTitulo);
            }
        });
        jPanelEdicion1.setModoSalida(plModoSalir);
        
        return jPanelEdicion1;
    }
    public JPanelEdicionBlanco getPanelEdicionBlanco(final IFormEdicion  poPanel, final Object poPanelMismo) throws Exception{
        JPanelEdicionBlanco loB = new JPanelEdicionBlanco();
        loB.setPanel(poPanel, getComponent(poPanelMismo));
        return loB;
    }

    private Component getComponent(final Object poComp){
        Component loResult = null;
//        if(Component.class.isAssignableFrom(poComp.getClass())){
            loResult = (Component)poComp;
//si se pone lo siguiente casca si no tiene javafx el cliente            
//        }else{
//            try {
//                final javafx.embed.swing.JFXPanel fxPanel = new javafx.embed.swing.JFXPanel();
//                utilesFX.plugin.JPlugInUtilidadesFX.runAndWait(new Runnable() {
//                            @Override
//                            public void run() {
//                                javafx.scene.Scene scene = new javafx.scene.Scene((javafx.scene.Parent)poComp );
//                                fxPanel.setScene(scene);
//                            }
//                       });
//                loResult=fxPanel;
//            } catch (RuntimeException ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            } catch (Error ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            } catch (Throwable ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//            
//        }
        return loResult;        
    }
    public void mostrarEdicion() throws Exception {

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
        
        if(moParam.getTipoMostrar()==IMostrarPantalla.mclEdicionInternalBlanco){
            if(moParam.getPanel()!=null){
                moParam.setComponente(getPanelEdicionBlanco(
                       moParam.getPanel(), moParam.getPanel()));
                moParam.setTitulo(moParam.getPanel().getTitulo());
            }else{
                moParam.setComponente(getPanelEdicionBlanco(
                       moParam.getPanelNave(), moParam.getPanelNave()));
                moParam.setTitulo(moParam.getPanelNave().getTitulo());                
            }
        }else{
            if(moParam.getPanel()!=null){
                moParam.setComponente(getPanelEdicion(
                       moParam.getPanel(), moParam.getPanel()));
                moParam.setTitulo(moParam.getPanel().getTitulo());
            }else{
                moParam.setComponente(getPanelEdicionNave(
                       moParam.getPanelNave(), moParam.getPanelNave()
                        , JGUIxConfigGlobal.getInstancia().getEdicionNavegadorTipoSalida()
                        , JGUIxConfigGlobal.getInstancia().getEdicionNaveINICIODefect()
                        
                ));
                moParam.setTitulo(moParam.getPanelNave().getTitulo());
            }
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

        mostrarFormPrinciComp();


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
            case IMostrarPantalla.mclEdicionInternalBlanco:
            case IMostrarPantalla.mclEdicionInternal:
                final JInternalFrame loFormInter = new JInternalFrame();
                loFormInter.getContentPane().setLayout(new BorderLayout());
                loFormInter.getContentPane().add(getComponent(moParam.getComponente()), BorderLayout.CENTER);
                loFormInter.setIconifiable(true);
                loFormInter.setClosable(true);
                loFormInter.setResizable(true);
                loFormInter.setMaximizable(true);
                loFormInter.setDefaultCloseOperation(loFormInter.DO_NOTHING_ON_CLOSE);
                loFormInter.setTitle(moParam.getTitulo());
                try{
                    if(moParam.getImagenIcono()!=null){
                        loFormInter.setFrameIcon(
                            new ImageIcon(JIMGTrata.getIMGTrata().getImagenEscalada(
                                ((ImageIcon)moParam.getImagenIcono()).getImage()
                                , ((ImageIcon)moParam.getImagenIcono()).getImage().getHeight(null)
                                , ((ImageIcon)moParam.getImagenIcono()).getImage().getWidth(null)
                                , 16, 16)));
                    }
                }catch(Throwable e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }                
                
                moMostrar.getDesktopPane1().add(loFormInter, javax.swing.JLayeredPane.DEFAULT_LAYER);
                loFormInter.setBounds(0, 0, moParam.getAncho(), moParam.getAlto());
                if(moParam.getX()>0 && moParam.getY()>0){
                    loFormInter.setBounds(
                            moParam.getX(),moParam.getY(),
                            loFormInter.getBounds().width,
                            loFormInter.getBounds().height);
                }else{
                    loFormInter.setBounds(
                            Math.abs((moMostrar.getDesktopPane1().getBounds().width-loFormInter.getBounds().width)/2),
                            Math.abs((moMostrar.getDesktopPane1().getBounds().height-loFormInter.getBounds().height)/2),
                            loFormInter.getBounds().width,
                            loFormInter.getBounds().height);
                }
                if(moParam.isMaximizado()){
                    loFormInter.setBounds(
                            0, 0,
                            moMostrar.getDesktopPane1().getBounds().width,
                            moMostrar.getDesktopPane1().getBounds().height);
                }

                moMostrar.add(new JMostrarPantallaFormulario(
                        moMostrar, loFormInter, moParam)
                        );
                loFormInter.setVisible(true);
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            loFormInter.setMaximum(moParam.isMaximizado());
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(JMostrarPantallaCargarForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(moComponenteFoco!=null){
                            moComponenteFoco.requestFocusInWindow();
                        }
                    }
                });
                break;
            case IMostrarPantalla.mclEdicionFrame:
                final JFrame loFormFrame = new JFrame();
                loFormFrame.getContentPane().setLayout(new BorderLayout());
                loFormFrame.getContentPane().add(getComponent(moParam.getComponente()), BorderLayout.CENTER);
                loFormFrame.setResizable(true);
                loFormFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                loFormFrame.setTitle(moParam.getTitulo());
                loFormFrame.setAlwaysOnTop(moParam.isSiempreDelante());
                if(!moParam.isXCierra()){
                    loFormFrame.setUndecorated(true);
                }
                try{
                    if(moParam.getImagenIcono()!=null){
                        loFormFrame.setIconImage(((ImageIcon)moParam.getImagenIcono()).getImage());
                    }
                }catch(Throwable e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }                

                loFormFrame.setBounds(0, 0, moParam.getAncho(), moParam.getAlto());
                try{
                    Dimension loD=Toolkit.getDefaultToolkit().getScreenSize();
                    if(moParam.isMaximizado() ){
                        loFormFrame.setBounds(
                                0, 0,
                                loD.width, loD.height-20);
                    }else{
                        if(moParam.getX()>0 && moParam.getY()>0){
                            loFormFrame.setBounds(moParam.getX(), moParam.getY(), moParam.getAncho(), moParam.getAlto());
                        }else{
                            loFormFrame.setBounds(
                                (loD.getWidth()<(loFormFrame.getBounds().width+40) ? 0 :  Math.abs((loD.width-loFormFrame.getBounds().width)/2))
                                ,(loD.getHeight()<(loFormFrame.getBounds().height+40) ? 0 :  Math.abs((loD.height-loFormFrame.getBounds().height)/2))
                                ,loFormFrame.getBounds().width
                                ,loFormFrame.getBounds().height);
                        }
                    }
                }catch(Throwable e){}

                moMostrar.add(new JMostrarPantallaFormulario(
                        moMostrar, loFormFrame, moParam)
                        );
                loFormFrame.setVisible(true);
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
//                        loFormFrame.requestFocus();
//                        loFormFrame.requestFocusInWindow();                        
                        if(moParam.isMaximizado()){
                            loFormFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        }                    
                        if(moComponenteFoco!=null){
                            moComponenteFoco.requestFocusInWindow();
                        }
                    }
                });

                break;
            case IMostrarPantalla.mclEdicionDialog:
                final JDialog loFormDialog = new JDialog();
                loFormDialog.getContentPane().setLayout(new BorderLayout());
                loFormDialog.getContentPane().add(getComponent(moParam.getComponente()), BorderLayout.CENTER);
                loFormDialog.setResizable(true);
                loFormDialog.setTitle(moParam.getTitulo());
                loFormDialog.setDefaultCloseOperation(loFormDialog.DO_NOTHING_ON_CLOSE);
                try{
                    if(moParam.getImagenIcono()!=null){
                        loFormDialog.setIconImage(((ImageIcon)moParam.getImagenIcono()).getImage());
                    }
                }catch(Throwable e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }                       
                loFormDialog.setModal(true);
                if(moParam.getAncho()<=0){
                    loFormDialog.setBounds(0, 0, 640, 430);
                }else{
                    loFormDialog.setBounds(0, 0, moParam.getAncho(), moParam.getAlto());
                }
                try{
                    Dimension loD=Toolkit.getDefaultToolkit().getScreenSize();
                    if(moParam.isMaximizado()){
                        loFormDialog.setBounds(
                                0, 0,
                                loD.width, loD.height-20);
                    }else{
                        if(moParam.getX()>0 && moParam.getY()>0){
                            loFormDialog.setBounds(
                                moParam.getX(), moParam.getY()
                                , loFormDialog.getBounds().width
                                , loFormDialog.getBounds().height);
                        } else {
                            loFormDialog.setBounds(
                                (loD.getWidth()<(loFormDialog.getBounds().width+40) ? 0 :  Math.abs((loD.width-loFormDialog.getBounds().width)/2))
                                ,(loD.getHeight()<(loFormDialog.getBounds().height+40) ? 0 :  Math.abs((loD.height-loFormDialog.getBounds().height)/2))
                                ,loFormDialog.getBounds().width
                                ,loFormDialog.getBounds().height);
                        }
                    }
                }catch(Throwable e){
                }
                moMostrar.add(new JMostrarPantallaFormulario(
                        moMostrar, loFormDialog,  moParam)
                        );
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if(moComponenteFoco!=null){
                            moComponenteFoco.requestFocusInWindow();
                        }
                    }
                });
                
                loFormDialog.setVisible(true);
                break;
            default:
                throw new Exception(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Selección de tipo de form principal incorrecto"));
        };
        moMostrar.llamarListener(new JMostrarPantallaEvent(moMostrar, JMostrarPantallaEvent.mclAbrirDespues, (Component)moParam.getComponente(), moParam));
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
    public void procesar() throws Throwable {
        boolean lbContinuar = true;
        if(moParam.isUnaSolaInstancia()){
            lbContinuar = !encontrarYReMostrar();
        }
        if(lbContinuar){
            if(moParam.getControlador()!=null){
                //conseguimos el JPanelGeneral sin eventdispatch ya q el mismo controla este tema (asi se pueden conseguir los datos fuera del eventdispatch)
                moParam.setComponente(getComponentePrinci(moParam.getControlador(), moParam.getTipoForm()));
                moParam.setTitulo(moParam.getControlador().getParametros().getTitulo());
                moParam.setPanel(null);//tiene prioridad el controlador
                moParam.setPanelNave(null);//tiene prioridad el controlador
            }
            if(SwingUtilities.isEventDispatchThread()){
                if(moParam.getPanel()!=null || moParam.getPanelNave()!=null){
                    mostrarEdicion();
                }else{
                    mostrarFormPrinciComp();
                }
            }else if(moParam.getTipoMostrar()==JMostrarPantallaParam.mclEdicionDialog){
                
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        try {
                            if(moParam.getPanel()!=null || moParam.getPanelNave()!=null){
                                mostrarEdicion();
                            }else{
                                mostrarFormPrinciComp();
                            }
                        } catch (Throwable ex) {
                            moError=ex;
                        }
                    }
                });
            }else{
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            if(moParam.getPanel()!=null || moParam.getPanelNave()!=null){
                                mostrarEdicion();
                            }else{
                                mostrarFormPrinciComp();
                            }
                        } catch (Throwable ex) {
                            JMsgBox.mensajeErrorYLog(null, ex, JMostrarPantallaCargarForm.class.getName());
                        }
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

    public String getTituloRegistroActual() {
        try{
            return JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Mostrar form ") + moParam.getTitulo();
        }catch(Exception e){
            return JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Mostrar form");
        }
    }
    public void mostrarMensaje(String psMensaje) {

    }


}
