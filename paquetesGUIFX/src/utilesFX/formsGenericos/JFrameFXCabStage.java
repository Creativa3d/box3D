/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.event.EventType;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author eduardo
 */
public class JFrameFXCabStage implements IFrameCabezera {

    private final Stage moStage;
    public JFrameFXCabStage(Stage poStage){
        moStage=poStage;
    }
    @Override
    public double getX() {
        return moStage.getX();
    }

    @Override
    public double getY() {
        return moStage.getY();
    }

    @Override
    public void setX(double x) {
        moStage.setX(x);
    }

    @Override
    public void setY(double y) {
        moStage.setY(y);
    }

    @Override
    public boolean isMaximized() {
        return moStage.isMaximized();
    }

    @Override
    public double getWidth() {
        return moStage.getWidth();
    }

    @Override
    public double getHeight() {
        return moStage.getHeight();
    }

    @Override
    public void setWidth(double width) {
        moStage.setWidth(width);
    }

    @Override
    public void setHeight(double height) {
        moStage.setHeight(height);
    }

    @Override
    public void setMaximized(boolean pbMax) {
        moStage.setMaximized(pbMax);
    }

    @Override
    public boolean lanzarEventoCerrado(EventType<WindowEvent> poE) {
        WindowEvent loEvent = new WindowEvent(moStage, poE);
        
        if(moStage.onCloseRequestProperty()!=null 
                && moStage.onCloseRequestProperty().get()!=null){
            moStage.onCloseRequestProperty().get().handle(loEvent);
        }
        
        return !loEvent.isConsumed();
    }

    @Override
    public void close() {
        moStage.close();
    }

    @Override
    public void setIconified(boolean pbIcon) {
        moStage.setIconified(pbIcon);
    }

    @Override
    public void toFront() {
        moStage.toFront();
    }

    @Override
    public Node getNode() {
        return moStage.getScene().getRoot();
    }

    @Override
    public boolean isBotonIconified() {
        return true;
    }

    @Override
    public boolean isStage() {
        return true;
    }
    
}
