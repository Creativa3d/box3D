/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.componentes;

import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import utilesGUIx.plugin.toolBar.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import utilesGUIx.JButtonCZ;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JLabelCZ;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ItemEventCZ;
import utilesGUIx.ItemListenerCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.aplicacion.JFormPrincipal;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.JPlugInUtilidades;


public class JCompApliFactory {

    private MouseListener moListenerInternal = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
            maybeShowPopupBotones(e);
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopupBotones(e);
        }

        public void mouseEntered(MouseEvent e) {
        }
    };
    private InternalFrameListener moListenerInternalClose = new InternalFrameListener() {

        public void internalFrameOpened(InternalFrameEvent e) {
        }

        public void internalFrameClosing(InternalFrameEvent e) {
            IComponenteAplicacion loC = getCompApliPorComp(getListaComponentes(), e.getInternalFrame().getName(), IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
            getListaComponentes().remove(loC);
            mbGuardarDesktop = true;
        }

        public void internalFrameClosed(InternalFrameEvent e) {
        }

        public void internalFrameIconified(InternalFrameEvent e) {
        }

        public void internalFrameDeiconified(InternalFrameEvent e) {
        }

        public void internalFrameActivated(InternalFrameEvent e) {
        }

        public void internalFrameDeactivated(InternalFrameEvent e) {
        }
    };
    private ComponentListener moListenerInternalResize = new ComponentListener() {

        public void componentResized(ComponentEvent e) {
            visualBotones(e);
        }

        public void componentMoved(ComponentEvent e) {
            visualBotones(e);
        }

        public void componentShown(ComponentEvent e) {
        }

        public void componentHidden(ComponentEvent e) {
        }
    };
    private JToolBar jToolBar1;
    private JDesktopPane jDesktopPaneInterno;
    private JMenuBar jMenuBar1;
    private JComponenteAplicacionGrupoModelo moListaComponentes = new JComponenteAplicacionGrupoModelo();
    public boolean mbGuardarDesktop = false;
    private boolean mbAccionesCargadas=false;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenuBotones;
    private javax.swing.JMenu jMenuAcciones;
    private javax.swing.JMenuItem jMenuAddGrupo;
    private javax.swing.JMenuItem jMenuQuitar;
    private javax.swing.JMenuItem jMenuEliminarFrame;
    private javax.swing.JMenuItem jMenuPropiedades;
    JComponent moIn;
    JComponent moInSource;
    private final IMostrarPantalla moMostrar;
    private JComponenteAplicacionGrupoModelo moListaComponeCS;
    private ImageIcon moIcon;

    public JCompApliFactory(JToolBar poToolBar1, JDesktopPane poDesktopPaneInterno, JMenuBar poMenuBar1, IMostrarPantalla poMostrar) {
        jToolBar1 = poToolBar1;
        jDesktopPaneInterno = poDesktopPaneInterno;
        jMenuBar1 = poMenuBar1;
        moMostrar=poMostrar;
        try {
            moIcon = JIMGTrata.getIMGTrata().getImagenCargada("/utilesGUIx/images/folder_blue.png");
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        
        init();
    }
    private void init(){
        jToolBar1.addMouseListener(moListenerInternal);
        jDesktopPaneInterno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                maybeShowPopup(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                maybeShowPopup(evt);
            }
        });
        
        jPopupMenuBotones = new javax.swing.JPopupMenu();
        jMenuPropiedades = new javax.swing.JMenuItem();
        jMenuAcciones = new javax.swing.JMenu();
        jMenuAddGrupo = new javax.swing.JMenuItem();
        jMenuQuitar = new javax.swing.JMenuItem();
        jMenuEliminarFrame = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();


        jMenuAddGrupo.setText("Añadir grupo");
        jMenuAddGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    addGrupoActionPerformed();
                }catch(Exception e){
                    JMsgBox.mensajeErrorYLog(null, e, getClass().getName());
                }    
            }
        });
        jPopupMenu1.add(jMenuAddGrupo);
        
        jMenuPropiedades.setText("Propiedades");
        jMenuPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propiedadesActionPerformed(evt);
            }
        });
        jPopupMenuBotones.add(jMenuPropiedades);

        jMenuAcciones.setText("Acciones");
        jPopupMenuBotones.add(jMenuAcciones);

        jMenuQuitar.setText("Quitar botón");
        jMenuQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    quitarActionPerformed(evt);
                }catch(Exception e){
                    JMsgBox.mensajeErrorYLog(null, e, getClass().getName());
                }    
            }

        });
        jPopupMenuBotones.add(jMenuQuitar);
        
        jMenuEliminarFrame.setText("Eliminar grupo");
        jMenuEliminarFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    eliminarFrameActionPerformed(evt);
                }catch(Exception e){
                    JMsgBox.mensajeErrorYLog(null, e, getClass().getName());
                }    
            }

        });
        jPopupMenuBotones.add(new javax.swing.JPopupMenu.Separator());
        jPopupMenuBotones.add(jMenuEliminarFrame);
        
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

    private JComponent crearComponente(IComponenteAplicacion poParam, JComponent loResult, int plVerticalTextAlineacionDefecto) {
        if (poParam instanceof JComponenteAplicacionModelo) {
            JComponenteAplicacionModelo loParam = (JComponenteAplicacionModelo) poParam;
            if (JComponenteAplicacion.mcsTipoBOTON.equals(loParam.getTipo())) {
                loResult = construirBoton(loParam, (JButtonCZ) loResult, plVerticalTextAlineacionDefecto);
            }
            if (JComponenteAplicacion.mcsTipoCMB.equals(loParam.getTipo())) {
                loResult = construirCMB(loParam, (JComboBoxCZ) loResult);
            }
            if (JComponenteAplicacion.mcsTipoLABEL.equals(loParam.getTipo())) {
                loResult = construirLABEL(loParam, (JLabelCZ) loResult, plVerticalTextAlineacionDefecto);
            }
            if (JComponenteAplicacion.mcsTipoMENU.equals(loParam.getTipo())) {
                loResult = construirMenuItem(loParam, (JMenuItem) loResult, plVerticalTextAlineacionDefecto);
            }
        }
        if (poParam instanceof JComponenteAplicacionGrupoModelo) {
            JComponenteAplicacionGrupoModelo loParam = (JComponenteAplicacionGrupoModelo) poParam;

            if(IComponenteAplicacion.mcsGRUPOMENU.equalsIgnoreCase(loParam.getGrupoBase())){
                javax.swing.JMenu loMenu = null;
                if (loResult == null) {
                    loMenu = new JMenu();
                } else {
                    loMenu = (JMenu) loResult;
                }
                loMenu.setText(loParam.getCaption());
                loMenu.setVisible(loParam.isActivo());
                loMenu.setName(loParam.getNombre());
                loMenu.setActionCommand(loParam.getNombre());
                if (loParam.getIcono() != null) {
                    loMenu.setIcon((Icon) loParam.getIcono());
                }
                if (loResult == null) {
                    for (int i = 0; i < loParam.getListaBotones().size(); i++) {
                        IComponenteAplicacion loC = (IComponenteAplicacion) loParam.getListaBotones().get(i);
                        JComponent loComp = crearComponente(loC, null, SwingConstants.BOTTOM);
                        loMenu.add(loComp);
                        if (loC.getDimension() != null) {
                            loComp.setBounds(loC.getX(), loC.getY(), (int) loC.getDimension().getWidth(), (int) loC.getDimension().getHeight());
                        }
                    }
                }
                
                loResult = loMenu;
            }else{
                javax.swing.JInternalFrame jInternalFrame1 = null;
                if (loResult == null) {
                    jInternalFrame1 = new JInternalFrame();
                } else {
                    jInternalFrame1 = (JInternalFrame) loResult;
                }
                if(loParam.getDimension()!=null){
                    jInternalFrame1.setBounds(loParam.getX(), loParam.getY(), (int) loParam.getDimension().getWidth(), (int) loParam.getDimension().getHeight());
                }
                jInternalFrame1.setClosable(false);
                jInternalFrame1.setIconifiable(loParam.isIconificable());
                jInternalFrame1.setMaximizable(loParam.isMaximizable());
                jInternalFrame1.setResizable(loParam.isResizable());
                jInternalFrame1.setTitle(loParam.getCaption());
                jInternalFrame1.setVisible(loParam.isActivo());
                jInternalFrame1.setOpaque(false);
                jInternalFrame1.setName(loParam.getNombre());
                jInternalFrame1.getContentPane().setBackground(new Color(0, 0, 0, 100));
                if(moMostrar!=null && moMostrar.getImagenIcono()!=null){
                    jInternalFrame1.setFrameIcon(
                            new ImageIcon(JIMGTrata.getIMGTrata().getImagenEscalada(
                                ((ImageIcon)moIcon).getImage()
                                , ((ImageIcon)moIcon).getImage().getHeight(null)
                                , ((ImageIcon)moIcon).getImage().getWidth(null)
                                , 16, 16)));
                }
                if (loParam.getDistribucion() == JComponenteAplicacionGrupo.Distribucion.Libre) {
                    jInternalFrame1.setLayout(null);
                } else {
                    jInternalFrame1.setLayout(new GridLayout(0, loParam.getColumnasDeBotones(), 5, 5));
                }
                jInternalFrame1.addInternalFrameListener(moListenerInternalClose);
                if (loResult == null) {
                    for (int i = 0; i < loParam.getListaBotones().size(); i++) {
                        IComponenteAplicacion loC = (IComponenteAplicacion) loParam.getListaBotones().get(i);
                        JComponent loComp = crearComponente(loC, null, SwingConstants.BOTTOM);
                        jInternalFrame1.add(loComp);
                        if (loC.getDimension() != null) {
                            loComp.setBounds(loC.getX(), loC.getY(), (int) loC.getDimension().getWidth(), (int) loC.getDimension().getHeight());
                        }
                    }
                }
                jInternalFrame1.addInternalFrameListener(null);
                loResult = jInternalFrame1;
            }
        }
        if (loResult != null) {
            loResult.addMouseListener(moListenerInternal);
            loResult.addComponentListener(moListenerInternalResize);
        }

        return loResult;

    }

    private JComponent construirBoton(JComponenteAplicacionModelo poParam,
            JButtonCZ loResult, int plVerticalTextAlineacionDefecto) {
        if (loResult == null) {
            loResult = new JButtonCZ(poParam.getCaption());
        }
        loResult.setToolTipText(poParam.getCaption());
        if (poParam.getIcono() != null) {
            try{
                loResult.setIcon((Icon)poParam.getIcono());
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        loResult.addActionListener(new ActionListenerModeloWrapper(poParam.getAccion()));
        if (poParam.getNombre() != null) {
            loResult.setName(poParam.getNombre());
            loResult.setActionCommand(poParam.getNombre());
        }
        if (poParam.getDimension() != null) {
            loResult.setPreferredSize(new Dimension(poParam.getDimension().width, poParam.getDimension().height));
        }
        loResult.setLocation(poParam.getX(), poParam.getY());
        loResult.setHorizontalTextPosition(poParam.getHorizontalTextAlignment());
        if(poParam.getVerticalTextAlignment()==-1){
            loResult.setVerticalTextPosition(plVerticalTextAlineacionDefecto);
        }else{
            loResult.setVerticalTextPosition(poParam.getVerticalTextAlignment());
        }
        return loResult;
    }
    private JComponent construirMenuItem(JComponenteAplicacionModelo poParam,
            JMenuItem loResult, int plVerticalTextAlineacionDefecto) {
        if (loResult == null) {
            loResult = new JMenuItem(poParam.getCaption());
        }
        if (poParam.getIcono() != null) {
            try{
                loResult.setIcon((Icon)poParam.getIcono());
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        loResult.addActionListener(new ActionListenerModeloWrapper(poParam.getAccion()));
        if (poParam.getNombre() != null) {
            loResult.setName(poParam.getNombre());
            loResult.setActionCommand(poParam.getNombre());
        }
        loResult.setVisible(poParam.isActivo());
        return loResult;
    }

    private JComponent construirCMB(final JComponenteAplicacionModelo poParam, JComboBoxCZ poResult) {
        final JComboBoxCZ loResult;
        if (poResult == null) {
            loResult = new JComboBoxCZ();
        } else {
            loResult = poResult;
        }
        loResult.setToolTipText(poParam.getCaption());

        if (poParam.getNombre() != null) {
            loResult.setName(poParam.getNombre());
            loResult.setActionCommand(poParam.getNombre());
        }
        if (poParam.getDimension() != null) {
            loResult.setPreferredSize(new Dimension(poParam.getDimension().width, poParam.getDimension().height));
        }
        loResult.setLocation(poParam.getX(), poParam.getY());

        loResult.borrarTodo();
        IListaElementos loFilas = (IListaElementos) poParam.getPropiedades().get(JComponenteAplicacion.mcsPropiedadCMBElementos);
        if (loFilas != null) {
            for (int i = 0; i < loFilas.size(); i++) {
                JCompCMBElemento loAux = (JCompCMBElemento) loFilas.get(i);
                loResult.addLinea(loAux.msDescripcion, loAux.msCodigo);
            }
        }
        loResult.mbSeleccionarClave(poParam.getCaption());
        final JCompCMB loWrapper = new JCompCMB(loResult);
        if (poParam.getAccion() != null) {
            loResult.addActionListener(
                    new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            poParam.getAccion().actionPerformed(
                                    new ActionEventCZ(
                                            loWrapper,
                                            e.getID(),
                                            poParam.getNombre()));
                        }
                    });
            //llamamos al combo al terminar de construirse
            loResult.getActionListeners()[0].actionPerformed(
                    new ActionEvent(loResult, 0, loResult.getActionCommand()));
        }
        final ItemListenerCZ loItemListener = (ItemListenerCZ) poParam.getPropiedades().get(JComponenteAplicacion.mcsPropiedadCMBItemStateChange);
        if (loItemListener != null) {
            loResult.addItemListener(
                    new ItemListener() {

                        public void itemStateChanged(ItemEvent e) {
                            loItemListener.itemStateChanged(
                                    new ItemEventCZ(
                                    loWrapper,
                                    e.getID(),
                                    loResult.getFilaActual(),
                                    e.getStateChange()));
                        }
                    });
        }

        return loResult;
    }

    private JComponent construirLABEL(JComponenteAplicacionModelo poParam, JLabelCZ loResult, int plVerticalTextAlineacionDefecto) {
        if (loResult == null) {
            loResult = new JLabelCZ(poParam.getCaption());
        }
        loResult.setToolTipText(poParam.getCaption());
        if (poParam.getIcono() != null) {
            try{
                loResult.setIcon((Icon)poParam.getIcono());
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        if (poParam.getNombre() != null) {
            loResult.setName(poParam.getNombre());
        }
        if (poParam.getDimension() != null) {
            loResult.setPreferredSize(new Dimension(poParam.getDimension().width, poParam.getDimension().height));
        }
        loResult.setLocation(poParam.getX(), poParam.getY());

        loResult.setHorizontalAlignment(poParam.getHorizontalTextAlignment());
        if(poParam.getVerticalTextAlignment()==-1){
            loResult.setVerticalTextPosition(plVerticalTextAlineacionDefecto);
        }else{
            loResult.setVerticalTextPosition(poParam.getVerticalTextAlignment());
        }

        return loResult;
    }

    private JComponent getComponente(IComponenteAplicacion poParam, Container poContenedor) {
        JComponent loResult = JPlugInUtilidades.getComponente(poContenedor, poParam.getNombre());
        return loResult;
    }

    private IComponenteAplicacion getCompApliPorComp(IListaElementos poLista, String psNombre, String psGrupo) {
        IComponenteAplicacion loResult = null;
        for (int i = 0; poLista != null && i < poLista.size() && loResult == null; i++) {
            IComponenteAplicacion loApl = (IComponenteAplicacion) poLista.get(i);
            if ((psGrupo == null || loApl.getGrupoBase().equals(psGrupo))
                    && (psNombre != null && psNombre.equalsIgnoreCase(loApl.getNombre()))) {
                loResult = loApl;
            }
            IListaElementos loListaHijos = loApl.getListaBotones();
            if(loListaHijos!=null && loListaHijos.size()>0 && loResult==null){
                loResult = getCompApliPorComp(loListaHijos, psNombre, psGrupo);
            }
        }
        return loResult;
    }

    public void aplicarComp() throws Exception {
        aplicarComp(null, getListaComponentes().getListaBotones());
    }
    private void aplicarComp(Container poContenedor, IListaElementos poBotones) throws Exception {
        for (int i = 0; i < poBotones.size(); i++) {
            IComponenteAplicacion loComp = (IComponenteAplicacion) poBotones.get(i);
            aplicarComp(poContenedor, loComp);
        }
    }

    private void aplicarComp(Container poContenedor, IComponenteAplicacion poComp) throws Exception {
        JComponent loComp = null;
        int lVertical = SwingConstants.BOTTOM;
        if (poContenedor == null && IComponenteAplicacion.mcsGRUPOMENU.equals(poComp.getGrupoBase())) {
            JMenuItem loMenuD = JPlugInUtilidades.getMenu(jMenuBar1, poComp.getNombre());
            JMenuItem loMenuO =  (JMenuItem) crearComponente(poComp, null, 0);
            if(loMenuD==null){
                jMenuBar1.add(loMenuO);
            }else{
                JPlugInUtilidades.addMenus((JMenu)loMenuO, (JMenu)loMenuD);
            }
        }else{
            if (poContenedor == null) {
                if (IComponenteAplicacion.mcsGRUPOBASEDESKTOP.equals(poComp.getGrupoBase())) {
                    poContenedor = jDesktopPaneInterno;
                } else {
                    poContenedor = jToolBar1;
                }
            }
            if(poContenedor instanceof JToolBar){
                lVertical = SwingConstants.CENTER;
            }else{
                lVertical = SwingConstants.BOTTOM;
            }
            loComp = getComponente(poComp, poContenedor);
            if (loComp == null) {
                loComp = crearComponente(poComp, null, lVertical);
                if (loComp != null) {
                    poContenedor.add(loComp);
                    poContenedor.validate();
                }
            }
            if (loComp != null) {            
                loComp.setVisible(poComp.isActivo());
                if (poComp.getListaBotones() != null) {
                    aplicarComp(loComp, poComp.getListaBotones());
                }
            }
        }
    }

    private void visualBotones(ComponentEvent e) {
        JComponent loIn = null;
        if (e.getSource() instanceof JComponent) {
            loIn = (JComponent) e.getSource();
        }
        IComponenteAplicacion loApl = null;
        if (loIn != null) {
            loApl = getCompApliPorComp(getListaComponentes(), loIn.getName(), null);
        }
        if (loIn != null && loApl != null && loApl.getDimension()!=null && loApl.getGrupoBase().equals(IComponenteAplicacion.mcsGRUPOBASEDESKTOP)) {
            loApl.setX(loIn.getX());
            loApl.setY(loIn.getY());
            loApl.getDimension().setSize(loIn.getSize().width, loIn.getSize().height);
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
        if (loComp != null && loComp instanceof JComponenteAplicacion) {
            JComponenteAplicacion loCompA = (JComponenteAplicacion) loComp;
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
        if (loComp != null && loComp instanceof JComponenteAplicacion) {
            JComponenteAplicacion loCompA = (JComponenteAplicacion) loComp;
            loResult = loCompA.getPropiedades();
        }else{
            loResult = new HashMap();
        }
        
        return loResult;
    }

    private void crearMenus(JMenuBar poBar, JMenu poDestino) {
        for (int i = 0; i < poBar.getMenuCount(); i++) {
            if (poBar.getMenu(i) != null && poBar.getMenu(i).isVisible()) {
                cargarMenu(poBar.getMenu(i), poDestino);
            }
        }
    }

    private void cargarMenu(JMenu poOrigen, JMenu poDestino) {
        JMenu loDestino = crearMenu(poOrigen, poDestino);
        for (int i = 0; i < poOrigen.getMenuComponentCount(); i++) {
            Component loComp = poOrigen.getMenuComponent(i);
            if (loComp != null && loComp.isVisible()) {
                if (loComp instanceof JMenu) {
                    cargarMenu((JMenu) loComp, loDestino);
                } else if (loComp instanceof JMenuItem) {
                    crearMenuItem(((JMenuItem) loComp), loDestino);
                } else {
                }
            }

        }
    }

    private JMenuE crearMenu(JMenu poOrigen, JMenu poDestino) {
        JMenuE loM = new JMenuE(poOrigen, this);
        poDestino.add(loM);
        return loM;
    }

    private JMenuItemE crearMenuItem(JMenuItem poOrigen, JMenu poDestino) {
        JMenuItemE loM = new JMenuItemE(poOrigen, this);
        poDestino.add(loM);
        return loM;
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            jPopupMenu1.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    private void maybeShowPopupBotones(MouseEvent e) {
        if (e.isPopupTrigger() || e.getButton() > 1) {
            if (!mbAccionesCargadas) {
                mbAccionesCargadas = true;
                crearMenus(jMenuBar1, jMenuAcciones);
            }
            jMenuEliminarFrame.setVisible(true);
            moInSource=(JComponent) e.getSource();
            if (e.getSource() instanceof JInternalFrame || e.getSource() instanceof JToolBar) {
                moIn = (JComponent) e.getSource();
                jMenuQuitar.setVisible(false);
                if (e.getSource() instanceof JToolBar) {
                    jMenuEliminarFrame.setVisible(false);
                }
            
            } else if (JMostrarPantalla.getFramePadre(e.getSource()) instanceof JInternalFrame) {
                moIn = (JComponent) JMostrarPantalla.getFramePadre(e.getSource());
                jMenuQuitar.setVisible(true);
            } else if (((JComponent) e.getSource()).getParent() instanceof JToolBar) {
                moIn = (JComponent) ((JComponent) e.getSource()).getParent();
                jMenuQuitar.setVisible(true);
                jMenuEliminarFrame.setVisible(false);
            }
            jPopupMenuBotones.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    private void eliminarFrameActionPerformed(java.awt.event.ActionEvent e) {
        if(moIn!=null && moIn instanceof JInternalFrame ){
            moListenerInternalClose.internalFrameClosing(new InternalFrameEvent(((JInternalFrame )moIn), 0));
            ((JInternalFrame )moIn).dispose();
            mbGuardarDesktop=true;
        }
    }

    private void quitarActionPerformed(java.awt.event.ActionEvent e) {

        if(moIn!=null && moIn.getName()!=null){
            //localizamos el grupo
            IListaElementos loBotones = null;
            IComponenteAplicacion loApl = getCompApliPorComp(getListaComponentes(),moIn.getName(), IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
            if(loApl==null){
                loApl = getCompApliPorComp(getListaComponentes(),moIn.getName(), null);
                loBotones = getListaComponentes();
            }else{
                loBotones = loApl.getListaBotones();
            }
            if(loBotones!=null){
                try {
                    IComponenteAplicacion loBtApl = getCompApliPorComp(loBotones, moInSource.getName(), null);
                    if(loBtApl!=null){
                        loBotones.remove(loBtApl);
                    }
                    moIn.remove(moInSource);
                    moIn.updateUI();
                } catch (Exception ex) {
                    JMsgBox.mensajeErrorYLog(null, ex, getClass().getName());
                }
            }
            mbGuardarDesktop=true;
        }
    }
    public void addGrupoActionPerformed() throws Exception {
        String lsTitulo = JOptionPane.showInputDialog(null, "", "Título", JOptionPane.QUESTION_MESSAGE);
        if(lsTitulo!=null){
            JComponenteAplicacionGrupo loG = new JComponenteAplicacionGrupo(JComponenteAplicacionGrupo.mcsGRUPOBASEDESKTOP,"grupo" + new JDateEdu().msFormatear("ddMMyyyyhhmmss"), jDesktopPaneInterno.getWidth() / 2, jDesktopPaneInterno.getHeight() / 2, new Dimension(200, 100));
            loG.setCaption(lsTitulo);
            getListaComponentes().add(loG);
            aplicarComp(jDesktopPaneInterno, (IComponenteAplicacion)loG);
            mbGuardarDesktop = true;
        }
    }
    private void propiedadesActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        if(evt.getSource() instanceof JInternalFrame){
            moIn = (JInternalFrame) evt.getSource();
        }else if(JMostrarPantalla.getFramePadre(evt.getSource()) instanceof JInternalFrame){
            moIn = (JInternalFrame) JMostrarPantalla.getFramePadre(evt.getSource());
        }

        if(moIn!=null && moIn.getName()!=null){
            final IComponenteAplicacion loApl = getCompApliPorComp(getListaComponentes(),moIn.getName(), IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
            if(loApl!=null){
                try {
                    final IFormEdicion loEdi = loApl.getEdicion();
                    JMostrarPantallaParam loParam = new JMostrarPantallaParam(loEdi, null, (Component)loEdi, JMostrarPantalla.mclEdicionFrame);
                    loParam.setCallBack(new CallBack<JMostrarPantallaParam>() {
                        public void callBack(JMostrarPantallaParam poControlador) {
                            crearComponente(loApl, moIn, SwingConstants.BOTTOM);
                        }
                    });
                    moMostrar.mostrarForm(loParam);
                } catch (Exception ex) {
                    JMsgBox.mensajeErrorYLog(null, ex, getClass().getName());
                }
            }
            mbGuardarDesktop=true;
        }

    }                                                
    void addComp(Container poInternal, IComponenteAplicacion poComp) throws Exception {
        mbGuardarDesktop=true;
        if(poInternal instanceof JToolBar){
            getListaComponentes().add(poComp);
            if(poComp instanceof JComponenteAplicacion){
                ((JComponenteAplicacion)poComp).setHorizontalTextAlignment(SwingConstants.RIGHT);
            }
            aplicarComp(poInternal, poComp);
        }else{
            if(poComp instanceof JComponenteAplicacion){
                ((JComponenteAplicacion)poComp).setHorizontalTextAlignment(SwingConstants.CENTER);
            }
            IComponenteAplicacion loApl = getCompApliPorComp(getListaComponentes(),poInternal.getName(), IComponenteAplicacion.mcsGRUPOBASEDESKTOP);
            if(loApl!=null){
                if(getCompApliPorComp(loApl.getListaBotones(), poComp.getNombre(), null)==null){
                    loApl.getListaBotones().add(poComp);
                    aplicarComp(poInternal, poComp);
                }
            }
        }
        poInternal.validate();
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
            jDesktopPaneInterno.removeAll();
            jToolBar1.removeAll();
            //restauramos cs
            moListaComponentes=getClone(moListaComponeCS);
            //aplicamos cs
            aplicarComp();
        }
    }

    public JPopupMenu getPopUpDesktop() {
        return jPopupMenu1;
    }
    public JPopupMenu getPopUpBotones() {
        return jPopupMenuBotones;
    }
}
class JMenuE extends JMenu implements ActionListener {

    public ActionListener[] moListener;
    private final JCompApliFactory moPadre;

    JMenuE(JMenu poMenu, JCompApliFactory poPadre) {
        super(poMenu.getText());
        setActionCommand(poMenu.getActionCommand());
        setIcon(poMenu.getIcon());
        if (poMenu.getActionListeners().length > 0) {
            addActionListener(this);
        }
        moListener = poMenu.getActionListeners();
        moPadre = poPadre;

    }

    public void actionPerformed(ActionEvent e) {
        if (moPadre.moIn != null && moPadre.moIn.getName() != null) {
            JComponenteAplicacion loB = new JComponenteAplicacion(this);
            loB.setAccion(moListener[0]);
            try {
                moPadre.addComp(moPadre.moIn, loB);
            } catch (Exception ex) {
                JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
            }
        }
    }
    
}

class JMenuItemE extends JMenuItem implements ActionListener {

    public ActionListener[] moListener;
    private final JCompApliFactory moPadre;

    JMenuItemE(JMenuItem poMenu, JCompApliFactory poPadre) {
        super(poMenu.getText());
        setActionCommand(poMenu.getActionCommand());
        setIcon(poMenu.getIcon());
        if (poMenu.getActionListeners().length > 0) {
            addActionListener(this);
        }
        moListener = poMenu.getActionListeners();
        moPadre = poPadre;
    }

    public void actionPerformed(ActionEvent e) {
        if (moPadre.moIn != null && moPadre.moIn.getName() != null) {
            JComponenteAplicacion loB = new JComponenteAplicacion(this);
            loB.setAccion(moListener[0]);
            try {
                moPadre.addComp(moPadre.moIn, loB);
            } catch (Exception ex) {
                JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
            }
        }
    }
}
