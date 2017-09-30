/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.avisosGUI;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import utiles.JDepuracion;
import utilesGUIx.aplicacion.avisos.IAvisoListener;
import utilesGUIx.aplicacion.avisos.JAviso;
import utilesGUIx.aplicacion.avisos.JAvisosConj;

/**
 *
 * @author eduardo
 */
public class JLabelAvisos extends JLabel {
    private JPopupMenu moMenuAvisos = new JPopupMenu();
    private JPanelAvisosConj moPanelAvisos = new JPanelAvisosConj();
    private JAvisosConj moAvisos;
    private boolean mbVentanaInmediato=true;
    private IAvisoListener moListener = new IAvisoListener() {
            public void avisoPerformed(IAvisoListener.tiposAvisoListener plTipo, JAviso poAviso, JAvisosConj poConj) {
                setVisible(poConj.size()>0);
                setText(String.valueOf(poConj.size()));
                if(poAviso!=null && plTipo==IAvisoListener.tiposAvisoListener.addAviso && isVentanaInmediato()){
                    JVentanaAvisos loAvisos = new JVentanaAvisos();
                    loAvisos.maxX = (int)getLocationOnScreen().getX();
                    loAvisos.maxY = (int)getLocationOnScreen().getY();
                    if(loAvisos.maxX-loAvisos.getWidth() <0){
                        loAvisos.maxX+=loAvisos.getWidth();
                    }
                    loAvisos.ubicacionVentana();
                    JPanelAviso loAviso =  new JPanelAviso();
                    loAviso.setDatos(poAviso, getAvisos());
                    
                    loAvisos.setPanelAviso(loAviso);
                    loAvisos.hacerVisibleEInvisible(3);
                }
                if(poConj.size()<=0){
                    moMenuAvisos.setVisible(false);
                }
            }
        };
    public JLabelAvisos(){
        
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Bombilla-15.gif"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInformacionMouseClicked(evt);
            }
        });        
        moMenuAvisos.add(moPanelAvisos);
        setAvisos(new JAvisosConj());
        setText("");
    }
    private void lblInformacionMouseClicked(java.awt.event.MouseEvent evt) {                                            
        try{
            if(moMenuAvisos.isVisible()){
                moMenuAvisos.setVisible(false);
            }else{
                moPanelAvisos.setDatos(getAvisos());
                moMenuAvisos.setVisible(true);
                moMenuAvisos.setLocation(
                        getLocationOnScreen().x
                        , getLocationOnScreen().y-moMenuAvisos.getHeight());
            }            
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }

    }  
    public JAvisosConj getAvisos(){
        return moAvisos;
    }
    public void setAvisos(JAvisosConj poAvisos){
        if(moAvisos!=null){
            moAvisos.removeListener(moListener);
        }
        moAvisos=poAvisos;
        moAvisos.addListener(moListener);
        setText(String.valueOf(moAvisos.size()));
    }

    /**
     * @return the mbVentanaInmediato
     */
    public boolean isVentanaInmediato() {
        return mbVentanaInmediato;
    }

    /**
     * @param mbVentanaInmediato the mbVentanaInmediato to set
     */
    public void setVentanaInmediato(boolean mbVentanaInmediato) {
        this.mbVentanaInmediato = mbVentanaInmediato;
    }
    
}
