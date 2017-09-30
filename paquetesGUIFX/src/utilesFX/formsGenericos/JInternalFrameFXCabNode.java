/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

/**
 *
 * @author eduardo
 */
public class JInternalFrameFXCabNode implements IFrameCabezera {

    private final JInternalFrameFX moInternalFrame;
    private EventHandler<JInternalFrameFXEvent> moEvent;
    
    public JInternalFrameFXCabNode(JInternalFrameFX poStage){
        moInternalFrame=poStage;
    }
    public void setOnCloseRequest(EventHandler<JInternalFrameFXEvent> poEvent){
        moEvent = poEvent;
    }
    
    @Override
    public double getX() {
        return moInternalFrame.getLayoutX();
    }

    @Override
    public double getY() {
        return moInternalFrame.getLayoutY();
    }

    @Override
    public void setX(double x) {
        moInternalFrame.setLayoutX(x);
    }

    @Override
    public void setY(double y) {
        moInternalFrame.setLayoutY(y);
    }

    @Override
    public boolean isMaximized() {
        return moInternalFrame.isMaximizado();
    }
    @Override
    public double getWidth() {
        return moInternalFrame.getWidth();
    }

    @Override
    public double getHeight() {
        return moInternalFrame.getHeight();
    }

    @Override
    public void setWidth(double width) {
        moInternalFrame.setPrefWidth(width);
        moInternalFrame.setWidth(width);

    }
    @Override
    public void setHeight(double height) {
        moInternalFrame.setPrefHeight(height);
        moInternalFrame.setHeight(height);    
    }

    @Override
    public void setMaximized(boolean pbMax) {
        if(pbMax){
            moInternalFrame.setMaximizado();
        }else {
            moInternalFrame.setNormal();
        }
    }

    @Override
    public boolean lanzarEventoCerrado(EventType<WindowEvent> poEvent) {
        JInternalFrameFXEvent loE = new JInternalFrameFXEvent(this, poEvent);
        if(moEvent!=null){
            moEvent.handle(loE);
        }
        return !loE.isConsumed();
    }

    @Override
    public void close() {
        getDesktopPane1().getChildren().remove(moInternalFrame);
    }

    @Override
    public void setIconified(boolean pbIcon) {
    }

    @Override
    public void toFront() {
        moInternalFrame.toFront();
    }

    @Override
    public Node getNode() {
        return moInternalFrame;
    }

    @Override
    public boolean isBotonIconified() {
        return false;
    }

    @Override
    public boolean isStage() {
        return false;
    }

    private Pane getDesktopPane1() {
        return (Pane)moInternalFrame.getParent();
    }
    
}
