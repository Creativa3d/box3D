/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import java.io.IOException;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import utilesFX.JFXConfigGlobal;
import utilesGUIx.formsGenericos.ISalir;

/**
 *
 * @author eduardo
 */
public class JFrameCabezera extends GridPane implements ISalir  {
    private boolean RESIZE_BOTTOM;
    private boolean RESIZE_RIGHT;    
    @FXML
    private Button btnMax;
    @FXML
    private Button btnMin;

    
    @FXML
    private Button btnCerrar;

    @FXML
    private Label lblTitul;
        @FXML
    private ImageView imgIcon;

    private Image moImgMax = new Image(getClass().getResourceAsStream("/utilesFX/images/maximizar.png"));
    private Image moImgNormal = new Image(getClass().getResourceAsStream("/utilesFX/images/normal.png"));

    private final IFrameCabezera moStage;
    private double initX;
    private double initY;
    
    public JFrameCabezera (IFrameCabezera poFrame){
        moStage=poFrame;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/formsGenericos/JFrameCabezera.fxml"));
        
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        btnCerrar.setOnAction((ActionEvent t) -> {
            btnCerrarActionPerformed();
        });
        btnMax.setOnAction((ActionEvent t) -> {
            btnMaxActionPerformed();
        });
        btnMin.setOnAction((ActionEvent e)->moStage.setIconified(true));
        lblTitul.setOnMouseClicked((MouseEvent e)->{
            if(e.getClickCount()>1){
                btnMaxActionPerformed();
            }
            });
        btnMin.setVisible(moStage.isBotonIconified());
        //drag only by title
        makeDragable(lblTitul);
        makeFocusable();
        
        this.requestFocus();
        
    }        
    
    public void setIcono(Image poIm){
        imgIcon.setImage(poIm);
    }
    public void makeFocusable() {
        moStage.getNode().setOnMouseClicked(mouseEvent -> {
            moStage.toFront();
        });
    }

    //we can select nodes that react drag event
    public void makeDragable(Node what) {
        if(moStage.isStage()){
            what.setOnMousePressed(mouseEvent -> {
                initX = mouseEvent.getScreenX() - moStage.getX();

                initY = mouseEvent.getScreenY() - moStage.getY();
                            //also bring to front when moving
                toFront();
            });
            what.setOnMouseDragged(mouseEvent -> {
                moStage.setX(mouseEvent.getScreenX() - initX);
                moStage.setY(mouseEvent.getScreenY() - initY);
            });
        } else {
            what.setOnMousePressed(mouseEvent -> {
                initX = moStage.getX() - mouseEvent.getScreenX();
                initY = moStage.getY() - mouseEvent.getScreenY();
                //also bring to front when moving
                moStage.toFront();
            });
            what.setOnMouseDragged(mouseEvent -> {
                moStage.setX(mouseEvent.getScreenX() + initX);
                moStage.setY(mouseEvent.getScreenY() + initY);
            });            
        }
    }


    public void makeResizable(final Node poNode) {
        poNode.addEventFilter(MouseEvent.MOUSE_MOVED, (MouseEvent mouseEvent) -> {
            if (moStage.isMaximized()) {
                RESIZE_RIGHT = false;
                RESIZE_BOTTOM = false;
                poNode.setCursor(Cursor.DEFAULT);
            } else {
                //local window's coordiantes
                double mouseX = mouseEvent.getX();
                double mouseY = mouseEvent.getY();
                //window size
                double width = moStage.getWidth();
                double height = moStage.getHeight();
                //if we on the edge, change state and cursor
                RESIZE_RIGHT = Math.abs(mouseX - width) < (10) && mouseY>22;
                RESIZE_BOTTOM = Math.abs(mouseY - height) < 10;
                if(RESIZE_BOTTOM && RESIZE_RIGHT){
                    poNode.setCursor(Cursor.NW_RESIZE);
                } else if(RESIZE_BOTTOM){
                    poNode.setCursor(Cursor.N_RESIZE);
                } else if(RESIZE_RIGHT){
                    poNode.setCursor(Cursor.W_RESIZE);
                } else {
                    poNode.setCursor(Cursor.DEFAULT);
                }
                
            }
        });
        poNode.setOnMouseDragged(mouseEvent -> {

            //resize logic depends on state
            if (RESIZE_BOTTOM && RESIZE_RIGHT) {
                moStage.setWidth(mouseEvent.getX());
                moStage.setHeight(mouseEvent.getY());
            } else if (RESIZE_RIGHT) {
                moStage.setWidth(mouseEvent.getX());
            } else if (RESIZE_BOTTOM) {
                moStage.setHeight(mouseEvent.getY());
            }
        });
    }



    public String getTitle(){
        return lblTitul.getText();
    }
    private void btnMaxActionPerformed() {
        if(moStage.isMaximized()){
            setNormal();
        } else {
            setMaximizado();
        }
    }
    public void setNormal() {
        moStage.setMaximized(false);
        ((ImageView)btnMax.getGraphic()).setImage(moImgNormal);
        
    }
    public void setMaximizado() {
        moStage.setMaximized(true);
        ((ImageView)btnMax.getGraphic()).setImage(moImgMax);
        
    }
    private void btnCerrarActionPerformed() {
        close(WindowEvent.WINDOW_HIDDEN);
    }
    
    public String getName(){
        if(getId()!=null){
            return getId().toString();
        }else{
            return getTitle();
        }
    }
    public void close(){
        close(WindowEvent.WINDOW_CLOSE_REQUEST);
    }
    
    public void close(EventType<WindowEvent> poE){
        if(moStage.lanzarEventoCerrado(poE)){

            ScaleTransition scaleTransition = new ScaleTransition(
                    Duration.seconds(JFXConfigGlobal.mcdTiempoTransiciones)
                    , moStage.getNode());
            scaleTransition.setToX(0.1);
            scaleTransition.setToY(0.1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setOnFinished((a)->moStage.close());
            scaleTransition.play();            
        }
    }
            
    @Override
    public void salir() {
        close();
    }

    @Override
    public void setTitle(String psTitulo) {
        lblTitul.setText(psTitulo);
    }

    
}
