/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import com.sun.javafx.scene.traversal.Algorithm;
import com.sun.javafx.scene.traversal.Direction;
import com.sun.javafx.scene.traversal.ParentTraversalEngine;
import com.sun.javafx.scene.traversal.TraversalContext;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import utilesFX.JFXConfigGlobal;
import utilesGUIx.formsGenericos.ISalir;

/**
 *
 * @author eduardo
 */
public class JInternalFrameFX  extends BorderPane implements ISalir {


    private ChangeListener<Number> moListenerMax;
    
 
    private List<Node> moListaFocusableds = new ArrayList<>(10);
    private final JFrameCabezera moCabezera;
    private final JInternalFrameFXCabNode moCabezeraInter;
    private boolean mbMaximizado;
    
    
    public JInternalFrameFX (){
        moCabezeraInter = new JInternalFrameFXCabNode(this);
        moCabezera = new JFrameCabezera(moCabezeraInter);
        setStyle("-fx-border-color: #0060a0; -fx-border-width: 4; -fx-border-radius: 4;");
        
        setTop(moCabezera);
        this.setMinSize(100, 60);
        
        moCabezera.makeResizable(this);
        moCabezera.makeFocusable();
        
        moListenerMax = (ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            if(moCabezeraInter.isMaximized()){
                redimensionarMax();
            }
        };
        
        this.centerProperty().addListener(
                (ObservableValue<? extends Node> observableValue, Node oldValue, Node newValue)->{
                    moListaFocusableds.clear();
                    if((newValue instanceof Parent)){
                        rellenarFocusables(moListaFocusableds, (Parent) newValue);
                    } else if(newValue!=null && newValue.isFocusTraversable()){
                        moListaFocusableds.add(newValue);
                    }
                }
        );
        ParentTraversalEngine loFocus = new ParentTraversalEngine(this, new Algorithm() {
            @Override
            public Node select(Node node, Direction drctn, TraversalContext context) {
                moListaFocusableds.clear();
                rellenarFocusables(moListaFocusableds, JInternalFrameFX.this);
                
                int index = moListaFocusableds.indexOf(node);

                switch (drctn) {
                    case DOWN:
                    case RIGHT:
                    case NEXT:
                        index++;
                        break;
                    case LEFT:
                    case PREVIOUS:
                    case UP:
                        index--;
                }

                if (index < 0) {
                    index = moListaFocusableds.size() - 1;
                }
                index %= moListaFocusableds.size();

//                System.out.println("Select <" + index + ">");

//                moListaFocusableds.get(index).requestFocus();
                return moListaFocusableds.get(index);
            }

            @Override
            public Node selectFirst(TraversalContext context) {
                if(!moListaFocusableds.isEmpty()){
                    return moListaFocusableds.get(0);
                }else{
                    return JInternalFrameFX.this;
                }
            }

            @Override
            public Node selectLast(TraversalContext context) {
                if(!moListaFocusableds.isEmpty()){
                    return moListaFocusableds.get(moListaFocusableds.size()-1);
                }else{
                    return JInternalFrameFX.this;
                }
            }
        });
        
        setImpl_traversalEngine(loFocus);
    }      
    
    private void rellenarFocusables(List<Node> poList, Parent poNode){

        for(Node loNode : poNode.getChildrenUnmodifiable()){
            if(loNode.isFocusTraversable() && loNode.isVisible() && !loNode.isDisabled()){
                poList.add(loNode);
            }
            if(loNode instanceof Parent){
                rellenarFocusables(poList, (Parent) loNode);
            }
        }
    }
    
    public String getTitle(){
        return moCabezera.getTitle();
    }
    
    public void setNormal() {
        final double ldXMin = getDesktopPane1().getWidth()/2;
        final double ldYMin = (getDesktopPane1().getHeight()/2);
        mbMaximizado=false;
        try{
            getScene().widthProperty().removeListener(moListenerMax);
            getScene().heightProperty().removeListener(moListenerMax);
        }catch(Exception e){}
        
        
        
        ScaleTransition st = new ScaleTransition(Duration.seconds(JFXConfigGlobal.mcdTiempoTransiciones), this);
        st.setFromX(this.getScaleX());
        st.setFromY(this.getScaleY());
        st.setToX((ldXMin) / this.getWidth());
        st.setToY((ldYMin)/ this.getHeight());        
        st.setCycleCount(1);

        st.setOnFinished((ActionEvent t) -> {
            JInternalFrameFX.this.setScaleX(1);
            JInternalFrameFX.this.setScaleY(1);
            JInternalFrameFX.this.setPrefWidth(ldXMin);
            JInternalFrameFX.this.setPrefHeight(ldYMin);
            JInternalFrameFX.this.setLayoutX(ldXMin/2);
            JInternalFrameFX.this.setLayoutY(ldYMin/2);
        });        
        st.play();
        
        

    }
    public boolean isMaximizado(){
        return mbMaximizado;
    }

    private void redimensionarMax(){
        final double ldXMax = (getScene().widthProperty().get());
        final double ldYMax = (getScene().heightProperty().get()-90);
        JInternalFrameFX.this.setScaleX(1);
        JInternalFrameFX.this.setScaleY(1);
        JInternalFrameFX.this.setLayoutX(0);
        JInternalFrameFX.this.setLayoutY(0);        
        JInternalFrameFX.this.setTranslateX(0);
        JInternalFrameFX.this.setTranslateY(0);
        JInternalFrameFX.this.setPrefWidth(ldXMax);
        JInternalFrameFX.this.setPrefHeight(ldYMax);
        JInternalFrameFX.this.setWidth(ldXMax);
        JInternalFrameFX.this.setHeight(ldYMax);
//        autosize();
//        getDesktopPane1().autosize();
        
//        TranslateTransition translateTransition =
//            new TranslateTransition(Duration.seconds(0.6), this);
//        translateTransition.setFromX(this.getLayoutX());
//        translateTransition.setToX(0);
//        translateTransition.setFromY(this.getLayoutY());
//        translateTransition.setToY(0);        
//        translateTransition.setCycleCount(1);
//
//        ScaleTransition st = new ScaleTransition(Duration.seconds(0.6), this);
//        st.setFromX(this.getScaleX());
//        st.setFromY(this.getScaleY());
//        st.setToX((ldXMax) / this.getWidth());
//        st.setToY((ldYMax)/ this.getHeight());        
//        
//
//        ParallelTransition parallelTransition = new ParallelTransition();
//        parallelTransition.getChildren().addAll(
//                translateTransition,st
//        );
//        parallelTransition.setCycleCount(1);
//        parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent t) {
//                JInternalFrameFX.this.setScaleX(1);
//                JInternalFrameFX.this.setScaleY(1);
//                JInternalFrameFX.this.setLayoutX(0);
//                JInternalFrameFX.this.setLayoutY(0);        
//                JInternalFrameFX.this.setTranslateX(0);
//                JInternalFrameFX.this.setTranslateY(0);
//                JInternalFrameFX.this.setPrefWidth(ldXMax);
//                JInternalFrameFX.this.setPrefHeight(ldYMax);
//                JInternalFrameFX.this.setWidth(ldXMax);
//                JInternalFrameFX.this.setHeight(ldYMax);
//                autosize();
//                getDesktopPane1().autosize();
//            }
//        });        
//        parallelTransition.play();
        
    }       
    public Pane getDesktopPane1() {
        return (Pane)getParent();
    }
        
    public void setMaximizado() {
        mbMaximizado=true;
        
        redimensionarMax();

        try{
            getScene().widthProperty().removeListener(moListenerMax);
            getScene().heightProperty().removeListener(moListenerMax);
        }catch(Exception e){}
        getScene().widthProperty().addListener(moListenerMax);
        getScene().heightProperty().addListener(moListenerMax);
    }
    
    public void setOnCloseRequest(EventHandler<JInternalFrameFXEvent> poEvent){
        moCabezeraInter.setOnCloseRequest(poEvent);
    }

    @Override
    public void setWidth(double value) {
        super.setWidth(value); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHeight(double value) {
        super.setHeight(value); //To change body of generated methods, choose Tools | Templates.
    }
    

    
    public String getName(){
        if(getId()!=null){
            return getId().toString();
        }else{
            return getTitle();
        }
    }
    
            
    @Override
    public void salir() {
        moCabezera.close();
    }

    @Override
    public void setTitle(String psTitulo) {
        moCabezera.setTitle(psTitulo);
    }

    public void close() {
        moCabezera.close();
    }




}
