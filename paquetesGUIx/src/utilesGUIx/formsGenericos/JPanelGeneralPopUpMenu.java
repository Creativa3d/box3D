/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.JButtonCZ;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.plugin.JPlugInUtilidades;

public class JPanelGeneralPopUpMenu implements MouseListener {
    protected JPopupMenu jPopupMenu1;

    private JMenuItem jMenuAceptar;
    private JMenuItem jMenuBorrar;
    private JMenu jMenuCampos;
    private JMenuItem jMenuCancelar;
    private JMenuItem jMenuCopiarTabla;
    private JMenuItem jMenuEditar;
    private JMenuItem jMenuNuevo;
    private JMenu jMenuOtrasAcciones;
    private JMenuItem jMenuRefrescar;
    
    private IListaElementos moListaCamposCheck = new JListaElementos();
    protected IPanelGenerico moPanel;
    
    private boolean mbEventosCampos = true;
    private boolean[] mabVisibles;
    private IListaElementos moBotones;
    private JPanelGeneralBotones moBotonesGenerales;
    
    public JPanelGeneralPopUpMenu(IPanelGenerico poPanel, Component jTableDatos,
            JButtonCZ poAceptar, JButtonCZ poBorrar, JButtonCZ poCancelar, 
            JButtonCZ poCopiarTabla, JButtonCZ poEditar, JButtonCZ poNuevo, 
            JButtonCZ poRefrescar){
        mbEventosCampos = false;
        moPanel = poPanel;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuNuevo = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenuItem();
        jMenuBorrar = new javax.swing.JMenuItem();
        jMenuRefrescar = new javax.swing.JMenuItem();
        jMenuAceptar = new javax.swing.JMenuItem();
        jMenuCancelar = new javax.swing.JMenuItem();
        jMenuCopiarTabla = new javax.swing.JMenuItem();
        jMenuCampos = new javax.swing.JMenu();
        jMenuOtrasAcciones = new javax.swing.JMenu();
        
        jTableDatos.addMouseListener(this);

        if(poNuevo!=null){
            asignarBotonAMenu( poNuevo, jMenuNuevo);
            jPopupMenu1.add(jMenuNuevo);
        }
        if(poAceptar!=null){
            asignarBotonAMenu( poAceptar, jMenuAceptar);
            jPopupMenu1.add(jMenuAceptar);
        }
        if(poBorrar!=null){
            asignarBotonAMenu( poBorrar, jMenuBorrar);
            jPopupMenu1.add(jMenuBorrar);
        }
        if(poCancelar!=null){
            asignarBotonAMenu( poCancelar, jMenuCancelar);
            jPopupMenu1.add(jMenuCancelar);
        }
        if(poCopiarTabla!=null){
            asignarBotonAMenu( poCopiarTabla, jMenuCopiarTabla);
            jPopupMenu1.add(jMenuCopiarTabla);
        }
        if(poEditar!=null){
            asignarBotonAMenu( poEditar, jMenuEditar);
            jPopupMenu1.add(jMenuEditar);
        }
        if(poRefrescar!=null){
            asignarBotonAMenu( poRefrescar, jMenuRefrescar);
            jPopupMenu1.add(jMenuRefrescar);
        }


        jMenuCampos.setText("Campos");
        jPopupMenu1.add(jMenuCampos);

        jMenuOtrasAcciones.setText("Otras acciones");
        jPopupMenu1.add(jMenuOtrasAcciones);        
        mbEventosCampos = true;
    }
    public void aplicarBoton(final IBotonRelacionado poBoton){
        JMenuItem loComp = getBoton(poBoton);
        if(loComp!=null){
            aplicarBoton(loComp, poBoton);
        }
    }    
    public JMenuItem getBoton(final IBotonRelacionado poBoton){
        JMenuItem loBoton = null;
        if(moBotonesGenerales!=null){
            if(moBotonesGenerales.getAceptar()==poBoton){
                loBoton = jMenuAceptar;
            }
            if(moBotonesGenerales.getCancelar()==poBoton){
                loBoton = jMenuCancelar;
            }
            if(moBotonesGenerales.getBorrar()==poBoton){
                loBoton = jMenuBorrar;
            }
            if(moBotonesGenerales.getEditar()==poBoton){
                loBoton = jMenuEditar;
            }
            if(moBotonesGenerales.getNuevo()==poBoton){
                loBoton = jMenuNuevo;
            }
            if(moBotonesGenerales.getRefrescar()==poBoton){
                loBoton = jMenuRefrescar;
            }
            if(moBotonesGenerales.getCopiarTabla()==poBoton){
                loBoton = jMenuCopiarTabla;
            }
            for(int i = 0 ; i < moBotones.size() && loBoton==null; i++){
                if(moBotones.get(i) == poBoton){
                    loBoton=getBotonPrivado(i);
                }
            }
        }        
        
        return loBoton;
    }
    private JMenuItem getBotonPrivado(int plIndex){
        JMenuItem loBoton = null;
        loBoton = (JMenuItem) JPlugInUtilidades.getMenu(
                jMenuOtrasAcciones
                , String.valueOf(plIndex));
        return loBoton;
    }
    private void asignarBotonAMenu(JButton poBoton, JMenuItem poMenu){
        try{
            poMenu.setIcon(poBoton.getIcon());
            poMenu.setText(poBoton.getText());
            if(poBoton.getText().equals("")){
                poMenu.setText(poBoton.getActionCommand());
            }
            poMenu.setActionCommand(poBoton.getActionCommand());
            poMenu.addActionListener(poBoton.getActionListeners()[0]);
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }
    
    private void visibleCampo(int i, boolean pbValor) {
        if(mbEventosCampos ){
            mabVisibles[i] = pbValor;
            if(pbValor){
                moPanel.getTablaConfig().getTablaConfigColumnaDeCampoReal(i).setLong(80);
            }else{
                moPanel.getTablaConfig().getTablaConfigColumnaDeCampoReal(i).setLong(0);
            }
            moPanel.getTablaConfig().aplicar();
        }
    }
    
    private void establecerValoresMenu(final JMenuItem poBoton, final IBotonRelacionado poProp){
        poBoton.setVisible(poProp.isActivo());
        if(poProp.getCaption() != null){
            poBoton.setText(poProp.getCaption());
        }
        if(poProp.getDimension() != null){
            poBoton.setPreferredSize(new Dimension(poProp.getDimension().width, poProp.getDimension().height) );
        }
    }

    public void configurarPopup(
            IListaElementos poBotones, JPanelGeneralBotones poBotonesGenerales, 
            ActionListener poAccion, JListDatos poDatos,
            boolean[] pabVisibleConfig){
        moBotones=poBotones;
        moBotonesGenerales=poBotonesGenerales;
        mbEventosCampos = false;
        //botones generales
        establecerValoresMenu(jMenuAceptar, poBotonesGenerales.getAceptar());
        establecerValoresMenu(jMenuBorrar, poBotonesGenerales.getBorrar());
        establecerValoresMenu(jMenuCancelar, poBotonesGenerales.getCancelar());
        establecerValoresMenu(jMenuCopiarTabla, poBotonesGenerales.getCopiarTabla());
        establecerValoresMenu(jMenuEditar, poBotonesGenerales.getEditar());
        establecerValoresMenu(jMenuNuevo, poBotonesGenerales.getNuevo());
        establecerValoresMenu(jMenuRefrescar, poBotonesGenerales.getRefrescar());
        
        if(moBotones==null || moBotones.size()==0){
            jMenuOtrasAcciones.setVisible(false);
        }else{
            jMenuOtrasAcciones.setVisible(true);
            //limpiamos las acciones previas
            for(int i = 0 ; i < jMenuOtrasAcciones.getItemCount(); i++){
                jMenuOtrasAcciones.getItem(i).removeActionListener(poAccion);
            }
            jMenuOtrasAcciones.removeAll();
            //añadimos las acciones
            for(int i = 0 ; i < moBotones.size(); i++){
                IBotonRelacionado loBoton = (IBotonRelacionado)moBotones.get(i);
                JMenuItem jMenuAux = new JMenuItem(loBoton.getCaption());
                jMenuAux.addActionListener(poAccion);
                jMenuAux.setName(String.valueOf(i));
                jMenuAux.setActionCommand(String.valueOf(i));
                aplicarBoton(jMenuAux, loBoton);
                jMenuOtrasAcciones.add(jMenuAux);
            }
        }
        //limpiamos los campos previos
        for(int i = 0 ; i < moListaCamposCheck.size(); i++){
            JCheckBoxMenuItem jMenuAux = (JCheckBoxMenuItem) moListaCamposCheck.get(i);
            jMenuAux.removeActionListener(poAccion);
        }
        jMenuCampos.removeAll();
        moListaCamposCheck.clear();
        //añadimos los campos
        ItemListener loAccion = new ItemListener() {
//            public void actionPerformed(ActionEvent e) {
//                visibleCampo(Integer.valueOf(e.getActionCommand()).intValue());
//            }

            public void itemStateChanged(ItemEvent e) {
                visibleCampo(
                    Integer.valueOf(
                        ((Component)e.getSource()).getName()
                    ).intValue(),
                    (e.getStateChange() == ItemEvent.SELECTED)
                        );
            }
        };
        JMenu loMenuCamposAux = jMenuCampos;
        int lAdd = 0;
        int lCamposGrupo = 1;
        for(int i = 0 ;  poDatos!=null && i < poDatos.getFields().size(); i++){
            JFieldDef loCampo = poDatos.getFields(i);
            JCheckBoxMenuItem jMenuAux = new JCheckBoxMenuItem(loCampo.getCaption());
            jMenuAux.addItemListener(loAccion);
            jMenuAux.setName(String.valueOf(i));
            jMenuAux.setActionCommand(String.valueOf(i));
            moListaCamposCheck.add(jMenuAux);
            if(pabVisibleConfig!=null && !pabVisibleConfig[i]){
                jMenuAux.setVisible(false);
            }else{
                if(lAdd % 10 == 0 && poDatos.getFields().size() > 15){
                    loMenuCamposAux = new JMenu("Grupo "+String.valueOf(lCamposGrupo));
                    jMenuCampos.add(loMenuCamposAux);
                    lCamposGrupo++;
                }
                lAdd++;
            }
            loMenuCamposAux.add(jMenuAux);
        }
        mbEventosCampos = true;

    }
    protected void aplicarBoton(JMenuItem loBotonReal,  IBotonRelacionado poBoton){
        loBotonReal.setText(poBoton.getCaption());
        if(poBoton.getIcono()!=null){
            try{
                loBotonReal.setIcon((Icon)poBoton.getIcono());
            }catch(Throwable e){JDepuracion.anadirTexto(this.getClass().getName(), e);}
        }
        loBotonReal.setVisible(poBoton.isActivo());
    }
    
    private void setCamposCheck(){
        try{
            mbEventosCampos = false;
            for(int i = 0 ; i < mabVisibles.length; i++){
                JCheckBoxMenuItem jMenuAux = (JCheckBoxMenuItem)moListaCamposCheck.get(i);
                jMenuAux.setSelected(mabVisibles[i]);
            }
        }catch(Exception e){
            
        }finally{
            mbEventosCampos = true;
        }
        
    }
    public void setVisibleCampos(boolean[] pabVisibles){
        mabVisibles = pabVisibles;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            setCamposCheck();
            jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
    

}
