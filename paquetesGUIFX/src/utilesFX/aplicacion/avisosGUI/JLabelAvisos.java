/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.aplicacion.avisosGUI;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;
import utilesFX.JFXConfigGlobal;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.aplicacion.avisos.IAvisoListener;
import utilesGUIx.aplicacion.avisos.JAviso;
import utilesGUIx.aplicacion.avisos.JAvisosConj;

/**
 *
 * @author eduardo
 */
public class JLabelAvisos {

//    private JPopupMenu moMenuAvisos = new JPopupMenu();
//    private JPanelAvisosConj moPanelAvisos = new JPanelAvisosConj();
    private JAvisosConj moAvisos;
    private boolean mbVentanaInmediato=true;
    private IAvisoListener moListener = new IAvisoListener() {
            public void avisoPerformed(IAvisoListener.tiposAvisoListener plTipo, JAviso poAviso, JAvisosConj poConj) {
                lblInformacion.setVisible(poConj.size()>0);
                lblInformacion.setText(String.valueOf(poConj.size()));
                if(poAviso!=null && plTipo==IAvisoListener.tiposAvisoListener.addAviso && isVentanaInmediato()){
                    notificar(moAvisos, poAviso);
                }
//                if(poConj.size()<=0){
//                    moMenuAvisos.setVisible(false);
//                }
            }
        };
    private Button lblInformacion;
    private final NotificationPane moNotificationPane;

    public JLabelAvisos(Button lblInformacion, NotificationPane poNotificationPane) {
        this.lblInformacion=lblInformacion;
        moNotificationPane=poNotificationPane;
//        moNotificationPane.setOnHiding(e->{
//            if(moNotificationPane.getActions().size()>0){
//                ActionAvisos loAct = (ActionAvisos) moNotificationPane.getActions().get(0);
//                moAvisos.remove(loAct.getAviso());
//            }
//        });
        
//        moMenuAvisos.add(moPanelAvisos);
        setAvisos(new JAvisosConj());
        lblInformacion.setText("");

        
        lblInformacion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                lblInformacionMouseClicked(t);
                } catch (Exception ex) {
                    JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
                }
            }
        });    
    
    }
    static class ActionAvisos extends Action {

        private JAviso moAviso;
        public ActionAvisos(String psText, JAviso poAviso, Consumer<ActionEvent> cnsmr){
            super(psText, cnsmr);
            moAviso = poAviso;
        }

        @Override
        public void setEventHandler(Consumer<ActionEvent> eventHandler) {
            super.setEventHandler(eventHandler); 
        }
        
        public void setAviso(JAviso poAviso){
            moAviso=poAviso;
        }
        public JAviso getAviso(){
            return moAviso;
        }
        
    }
    
    
    private void lblInformacionMouseClicked(ActionEvent t) {                                            
        try{
            if(getAvisos().size()>0){
                notificar(getAvisos(), getAvisos().get(0));
            }
//            if(moMenuAvisos.isVisible()){
//                moMenuAvisos.setVisible(false);
//            }else{
//                moPanelAvisos.setDatos(getAvisos());
//                moMenuAvisos.setVisible(true);
//                moMenuAvisos.setLocation(
//                        getLocationOnScreen().x
//                        , getLocationOnScreen().y-moMenuAvisos.getHeight());
//            }            
        }catch(Exception e){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }

    }  

    public void notificar(final JAvisosConj poAvisosConj,final JAviso poAviso){
        
        ImageView image = null;
        switch(poAviso.getTipoMensaje()){
            case JOptionPaneFX.ERROR_MESSAGE:
                image=(new ImageView(JOptionPaneFX.imageERROR));
                break;
            case JOptionPaneFX.QUESTION_MESSAGE:
                image=(new ImageView(JOptionPaneFX.imageQUESTION));
                break;
            case JOptionPaneFX.INFORMATION_MESSAGE:
                image=(new ImageView(JOptionPaneFX.imageINFORMATION));
                break;
            case JOptionPaneFX.WARNING_MESSAGE:
                image=(new ImageView(JOptionPaneFX.imageWARNING));
                break;
        }
        image.setScaleX(0.5);
        image.setScaleY(0.5);
        
        JMostrarPantalla.notification(poAviso, image, poAviso.getMensaje(), Pos.BOTTOM_RIGHT
                , ae -> {
                        // accion
                        poAviso.getAccion().actionPerformed(null);
//                        // ocultar
//                        moNotificationPane.hide();
                        //borrar avisos
                        poAvisosConj.remove(poAviso);
                });
//        
//        moNotificationPane.setText(poAviso.getMensaje());
//
//        
//        moNotificationPane.setGraphic(image);
//
//        ActionAvisos loAction = new ActionAvisos(
//                poAviso.getAccionCaption(), poAviso
//                , ae -> {
//                        // accion
//                        poAviso.getAccion().actionPerformed(null);
//                        // ocultar
//                        moNotificationPane.hide();
//                        //borrar avisos
//                        poAvisosConj.remove(poAviso);
//                });
//
//        if(moNotificationPane.getActions().size()>0){
//            moNotificationPane.getActions().remove(0);
//        }
//        moNotificationPane.getActions().addAll(loAction);
//        
//        moNotificationPane.show();
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
        lblInformacion.setText(String.valueOf(moAvisos.size()));
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
