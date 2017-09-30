/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.event.EventType;
import javafx.scene.Node;
import javafx.stage.WindowEvent;

/**
 *
 * @author eduardo
 */
public interface IFrameCabezera {
    public double getX();
    public double getY();
    public void setX(double x);
    public void setY(double y);
    public boolean isMaximized();
    public double getWidth();
    public double getHeight();
    public void setWidth(double width);
    public void setHeight(double height);
    public void setMaximized(boolean pbMax);
    public boolean lanzarEventoCerrado(EventType<WindowEvent> poEvent);
    public void close();
    public void setIconified(boolean pbIcon);
    public void toFront();
    public Node getNode();
    
    public boolean isBotonIconified();
    public boolean isStage();
    
    
}
