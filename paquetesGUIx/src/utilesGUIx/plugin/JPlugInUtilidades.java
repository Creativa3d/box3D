/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import ListDatos.IFilaDatos;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.*;
import utilesGUIx.plugin.toolBar.JCompCMBElemento;
import utilesGUIx.plugin.toolBar.JComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupo;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupoModelo;

public class JPlugInUtilidades {
    public static void addMenusFrame(IPlugInFrame poFrame, JMenuBar jMenuBar1, boolean pbAplicar){
        for(int i = 0 ; i < jMenuBar1.getMenuCount(); i++ ){
            JMenu loMenuAux = jMenuBar1.getMenu(i);
            if(loMenuAux!=null){
                IComponenteAplicacion loMenu = poFrame.getListaComponentesAplicacion().getComponente(IComponenteAplicacion.mcsGRUPOMENU, loMenuAux.getActionCommand());
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
    public static void addMenusFrame(IPlugInFrame poFrame, JMenuBar jMenuBar1){
        addMenusFrame(poFrame, jMenuBar1, true);
    }
    public static void addMenusFrame(IPlugInFrame poFrame, JMenu jMenu){
        JMenuBar jMenuBar1 = new JMenuBar();
        jMenuBar1.add(jMenu);
        addMenusFrame(poFrame, jMenuBar1, true);
    }
    
    
    /**
     * @deprecated 
     * completa los menus de jMenuDestino con los menus de jMenuOrigen */
    public static void addMenus(IPlugInFrame poFrame, JMenu jMenuOrigen, JMenu jMenuDestino){
        addMenus(jMenuOrigen, jMenuDestino);
    }
    /**completa los menus de jMenuDestino con los menus de jMenuOrigen */
    public static void addMenus(JMenu jMenuOrigen, JMenu jMenuDestino){
        while(jMenuOrigen.getMenuComponentCount()>0){
            Component loAux = jMenuOrigen.getMenuComponent(0);
            if(loAux instanceof JMenuItem){
                JMenuItem loMenu = getMenu(
                                    jMenuDestino,
                                    ((JMenuItem) loAux).getActionCommand(),
                                    false
                                    );
                if(loMenu!=null){
                    aplicarMenu((JMenuItem)loAux, (JMenuItem)loMenu);
                    if(loAux instanceof JMenu && loMenu instanceof JMenu){
                        addMenus(((JMenu) loAux) , (JMenu)loMenu );
                    }
                }else{
                    jMenuDestino.add(loAux);
                }
            }else{
                jMenuDestino.add(loAux);
            }
            jMenuOrigen.remove(loAux);
        }
    }
    private static void aplicarMenu(JMenuItem loMenuO, JMenuItem loMenuD){
        loMenuD.setText(loMenuO.getText());
        loMenuD.setIcon(loMenuO.getIcon());
        loMenuD.setMnemonic(loMenuO.getMnemonic());
        loMenuD.setVisible(loMenuO.isVisible());
        ActionListener[] lo  = loMenuO.getListeners(ActionListener.class);
        ActionListener[] loD  = loMenuD.getListeners(ActionListener.class);
        for(int i = 0; i < loD.length; i++){
            loMenuD.removeActionListener(loD[i]);
        }
        for(int i = 0; i < lo.length; i++){
            loMenuD.addActionListener(lo[i]);
        }
    }
    /**Add los menus de jMenuBar1 al poFrame*/
    public static void addMenus(JMenuBar jMenuBar1FrameDest, JMenuBar jMenuBar1){

        for(int i = 0 ; i < jMenuBar1.getMenuCount(); i++ ){
            JMenu loMenuAux = jMenuBar1.getMenu(i);
            if(loMenuAux!=null){
                JMenu loMenu = (JMenu) getMenu(jMenuBar1FrameDest, loMenuAux.getActionCommand());
                if(loMenu!=null){
                    addMenus(jMenuBar1.getMenu(i) , loMenu);
                }else{
                    jMenuBar1FrameDest.add(jMenuBar1.getMenu(i));
                    i--;
                }
            }
        }
    }
    /**add un menbu al menu padre en la posicion correspondiente, si piPosicion es negativo lo inserta por el final*/
    public static void addMenu(JMenu poMenuPadre, JMenuItem poElemento, int piPosicion) {
        int liLongitud = poMenuPadre.getMenuComponentCount();
        int liPosSeparador = -1;
        if (piPosicion < 0) {
            poMenuPadre.insert(poElemento, (liPosSeparador != -1 ? liPosSeparador : liLongitud) + piPosicion);
        } else {
            poMenuPadre.insert(poElemento, (liPosSeparador != -1 ? liPosSeparador : 0) + piPosicion);
        }
    }
    /**
     * @deprecated 
     * El parametro pbEsPrincipal no sirve de nada
     */
    public static void addBotones(IPlugInFrame poFrame, JToolBar jToolBar1, boolean pbEsPrincipal){
        addBotones(poFrame, jToolBar1);
    }
    public static IComponenteAplicacion getComponenteAplicacion(Component loAux){
            IComponenteAplicacion loComp=null;
            if(loAux instanceof JComboBoxCZ){
                JComboBoxCZ loCMB = (JComboBoxCZ) loAux;
                loComp = new JComponenteAplicacion(
                    JComponenteAplicacion.mcsTipoCMB,
                    loCMB.getName(),
                    loCMB.getFilaActual().toString(),
                    null,
                    (loCMB.getActionListeners().length>0 ?
                        loCMB.getActionListeners()[0]:
                        null),
                    loCMB.getPreferredSize(),
                    null,
                    0);
                if(loCMB.getItemListeners().length>0){
                    ((JComponenteAplicacion)loComp).getPropiedades().put(
                        JComponenteAplicacion.mcsPropiedadCMBItemStateChange,
                        loCMB.getItemListeners()[0]);
                }
                if(loCMB.getItemCount()>0){
                    IListaElementos loElementos = new JListaElementos();
                    for(int lfila = 0 ; lfila < loCMB.getItemCount(); lfila++){
                        IFilaDatos loFila = loCMB.getFila(lfila);
                        String lsDescri = loCMB.getItemAt(lfila).toString();
                        loElementos.add(new JCompCMBElemento(lsDescri, loFila.toString()));
                    }
                    ((JComponenteAplicacion)loComp).getPropiedades().put(
                        JComponenteAplicacion.mcsPropiedadCMBElementos,
                        loElementos);
                }
                ((JComponenteAplicacion)loComp).setX(loCMB.getX());
                ((JComponenteAplicacion)loComp).setY(loCMB.getY());
            } else  if(loAux instanceof JLabel){
                JLabel loLabel = (JLabel) loAux;
                loComp = new JComponenteAplicacion(
                    JComponenteAplicacion.mcsTipoLABEL,
                    loLabel.getName(),
                    loLabel.getText(),
                    null,
                    null,
                    loLabel.getPreferredSize(),
                    loLabel.getIcon(),
                    loLabel.getHorizontalTextPosition());
                ((JComponenteAplicacion)loComp).setVerticalTextAlignment(loLabel.getVerticalTextPosition());
                ((JComponenteAplicacion)loComp).setX(loLabel.getX());
                ((JComponenteAplicacion)loComp).setY(loLabel.getY());
            } else if(loAux instanceof JButton){
                JButton loBoton = (JButton) loAux;
                loComp = new JComponenteAplicacion(
                    JComponenteAplicacion.mcsTipoBOTON,
                    loBoton.getActionCommand(),
                    loBoton.getText(),
                    null,
                    (loBoton.getActionListeners().length>0 ?
                        loBoton.getActionListeners()[0]:
                        null),
                    loBoton.getPreferredSize(),
                    loBoton.getIcon(),
                    loBoton.getHorizontalTextPosition());
                loComp.setActivo(loBoton.isVisible());
                ((JComponenteAplicacion)loComp).setVerticalTextAlignment(loBoton.getVerticalTextPosition());
                ((JComponenteAplicacion)loComp).setX(loBoton.getX());
                ((JComponenteAplicacion)loComp).setY(loBoton.getY());
            } else if(loAux instanceof JMenu){
                JMenu loMenu = (JMenu) loAux;
                JComponenteAplicacionGrupo loCompG = new JComponenteAplicacionGrupo(
                    JComponenteAplicacionGrupo.mcsGRUPOMENU,
                    loMenu.getActionCommand(),    
                    loMenu.getX(), loMenu.getY(),
                    loMenu.getSize()
                    );
                loComp=loCompG;
                loCompG.setCaption(loMenu.getText());
                loCompG.setNombre(loMenu.getActionCommand());
                loCompG.setActivo(loMenu.isVisible());
                try{
                    loCompG.setIcon(loMenu.getIcon());
                }catch(Throwable e){
                    JDepuracion.anadirTexto(JPlugInUtilidades.class.getName(), e);
                }
                if(loMenu.getActionListeners().length>0){
                    loCompG.setAccion(loMenu.getActionListeners()[0]);
                }
                addBotones(loCompG, loMenu);
            } else if(loAux instanceof JMenuItem){
                JMenuItem loMenuItem = (JMenuItem) loAux;
                loComp = new JComponenteAplicacion(
                    JComponenteAplicacion.mcsTipoMENU,
                    loMenuItem.getActionCommand(),
                    loMenuItem.getText(),
                    null,
                    (loMenuItem.getActionListeners().length>0 ?
                        loMenuItem.getActionListeners()[0]:
                        null),
                    loMenuItem.getPreferredSize(),
                    loMenuItem.getIcon(),
                    loMenuItem.getHorizontalTextPosition());
                loComp.setActivo(loMenuItem.isVisible());
                ((JComponenteAplicacion)loComp).setVerticalTextAlignment(loMenuItem.getVerticalTextPosition());
                ((JComponenteAplicacion)loComp).setX(loMenuItem.getX());
                ((JComponenteAplicacion)loComp).setY(loMenuItem.getY());
            } else if(loAux instanceof JMenuBar){
                JMenuBar loMenu = (JMenuBar) loAux;
                JComponenteAplicacionGrupo loCompG = new JComponenteAplicacionGrupo(
                    JComponenteAplicacionGrupo.mcsGRUPOMENU,
                    loMenu.getName(),    
                    loMenu.getX(), loMenu.getY(),
                    loMenu.getSize()
                    );
                loComp=loCompG;
                loCompG.setCaption("");
                loCompG.setNombre(loMenu.getName());
                loCompG.setActivo(loMenu.isVisible());
                addBotones(loCompG, loMenu);
            } else if(loAux instanceof JInternalFrame){
                JInternalFrame loInternal = (JInternalFrame) loAux;
                JComponenteAplicacionGrupo loCompG = new JComponenteAplicacionGrupo(
                    JComponenteAplicacionGrupo.mcsGRUPOBASEDESKTOP,
                    loInternal.getName(), 
                    loInternal.getX(), loInternal.getY(),
                    loInternal.getSize()
                    );
                loComp=loCompG;
                loCompG.setCaption(loInternal.getTitle());
                loCompG.setCloseable(loInternal.isClosable());
                loCompG.setIconificable(loInternal.isIconifiable());
                loCompG.setMaximizable(loInternal.isMaximizable());
                loCompG.setResizable(loInternal.isResizable());
                loCompG.setNombre(loInternal.getName());
                if(loInternal.getContentPane().getLayout() instanceof GridLayout) {
                    loCompG.setDistribucion(JComponenteAplicacionGrupoModelo.Distribucion.Rejilla);
                    int lColumn = ((GridLayout)loInternal.getContentPane().getLayout()).getColumns();
                    if(lColumn>0){
                        loCompG.setColumnasDeBotones(lColumn);
                    }
                }else{
                    loCompG.setDistribucion(JComponenteAplicacionGrupoModelo.Distribucion.Libre);
                }
                loCompG.setActivo(loInternal.isVisible());
                addBotones(loCompG, loInternal.getContentPane());
            }
            return loComp;
    }
    
    public static void mezclaBotones(JComponenteAplicacionGrupoModelo poCompO, IComponenteAplicacion poCompD){
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
    private static void addBotones(JComponenteAplicacionGrupoModelo poCompG, JMenu jMenuOrigen){
        for(int i = 0 ; i < jMenuOrigen.getMenuComponentCount(); i++ ){
            Component loAux = jMenuOrigen.getMenuComponent(i);
            if(loAux!=null){
                IComponenteAplicacion loComp=getComponenteAplicacion(loAux);
                if(loComp!=null){
                    poCompG.add(loComp);
                }
            }
        }
    }
    public static void addBotones(JComponenteAplicacionGrupoModelo poCompG, Container poFrame){
        for(int i = 0 ; i < poFrame.getComponentCount(); i++ ){
            JComponent loAux = (JComponent) poFrame.getComponent(i);
            IComponenteAplicacion loComp=getComponenteAplicacion(loAux);
            if(loComp!=null){
                poCompG.add(loComp);
            }
        }
    }
    
    /**add botones/combo/label del toolbar a poFrame*/
    public static void addBotones(IPlugInFrame poFrame, JToolBar jToolBar1){
        for(int i = 0 ; i < jToolBar1.getComponentCount(); i++ ){
            JComponent loAux = (JComponent) jToolBar1.getComponentAtIndex(i);
            IComponenteAplicacion loComp=getComponenteAplicacion(loAux);
            if(loComp!=null){
                poFrame.getListaComponentesAplicacion().getListaBotones().add(loComp);
            }
        }
    }
    public static void addBotones(IPlugInFrame poFrame, JDesktopPane poPane){
        JInternalFrame loFrames[] = poPane.getAllFrames();
        for(int i = 0 ; i < loFrames.length; i++ ){
            JInternalFrame loFrame =loFrames[i];
            IComponenteAplicacion loComp= getComponenteAplicacion(loFrame);
            if(loComp!=null){
                poFrame.getListaComponentesAplicacion().getListaBotones().add(loComp);
            }
        }
        
    }

    /**Devuelve el submenu que tiene el actioncomand psComando*/
    public static JMenuItem getMenu(JMenuBar loMenuBar, String psComando){
        JMenuItem loResult = null;
        for(int i = 0; i < loMenuBar.getMenuCount() && loResult == null;i++){
            JMenu loMenuAux = loMenuBar.getMenu(i);
            if(loMenuAux!=null){
                if(loMenuAux.getActionCommand().equals(psComando)){
                    loResult = loMenuBar.getMenu(i);
                }else{
                    loResult = getMenu(loMenuBar.getMenu(i), psComando);
                }
            }
        }
        return loResult;
    }
    public static JMenuItem getMenu(JMenu poMenu, String psComando){
        return getMenu(poMenu, psComando, true);
    }
    /**Devuelve el submenu que tiene el actioncomand psComando*/
    public static JMenuItem getMenu(JMenu poMenu, String psComando, boolean pbRecurs){
        JMenuItem loResult = null;
        for(int i = 0; i < poMenu.getMenuComponentCount() && loResult == null;i++){
            Component loElem = poMenu.getMenuComponent(i);
            if(loElem instanceof JMenuItem){
                JMenuItem loMenu = (JMenuItem)loElem;
                if(loMenu.getActionCommand().equals(psComando)){
                    loResult = loMenu;
                }else{
                    if(loMenu instanceof JMenu && pbRecurs){
                        loResult = getMenu((JMenu)loMenu, psComando);
                    }
                }
            }
        }
        return loResult;
    }
    /**Devuelve el componente que tiene el actioncomand psComando*/
    public static JComponent getComponente(Container poContenedor, String psAccion){
        JComponent loResult = null;
        for(int i = 0 ; i < poContenedor.getComponentCount() && loResult == null; i++){
            Component loComp = poContenedor.getComponent(i);
            if(loResult == null &&
               JButton.class.isAssignableFrom(loComp.getClass()) ){
                if(((JButton)loComp).getActionCommand()!=null &&
                   ((JButton)loComp).getActionCommand().equals(psAccion)){
                    loResult = ((JButton)loComp);
                }
            }
            if(loResult == null &&
               JLabel.class.isAssignableFrom(loComp.getClass()) ){
                if(((JLabel)loComp).getName()!=null &&
                   ((JLabel)loComp).getName().equals(psAccion)){
                    loResult = ((JLabel)loComp);
                }
            }
            if(loResult == null &&
               JComboBox.class.isAssignableFrom(loComp.getClass()) ){
                if(((JComboBox)loComp).getName()!=null &&
                   ((JComboBox)loComp).getName().equals(psAccion)){
                    loResult = ((JComboBox)loComp);
                }
            }
            if(loResult == null &&
               JInternalFrame.class.isAssignableFrom(loComp.getClass()) ){
                if(((JInternalFrame)loComp).getName()!=null &&
                   ((JInternalFrame)loComp).getName().equals(psAccion)){
                    loResult = ((JInternalFrame)loComp);
                }
            }
            if(loResult == null &&
               Container.class.isAssignableFrom(loComp.getClass()) ){
                loResult = getComponente(((Container)loComp), psAccion);
            }
//            if(loComp.getClass().isAssignableFrom(JToolBar.class) ){
//                loResult = getComponente(((JToolBar)loComp), psAccion);
//            }
        }
        return loResult;
    }

}
