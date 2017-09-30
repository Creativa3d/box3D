/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;


import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilesFX.JFXConfigGlobal;

/**
 *
 * @author eduardo
 */
public class JMostrarPantallaInitFX {
    public static void initJavaFX() {

        if(!JFXConfigGlobal.getInstancia().isInicializadoJAVAFX()){
            final CountDownLatch latch = new CountDownLatch(1);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    JFXPanel moPanelFX = new JFXPanel();
                    latch.countDown();
                    JFXConfigGlobal.getInstancia().setInicializadoJAVAFX(true);
                }
            }, "JavaFX-initializer").start();
            try {
                latch.await();
                Platform.setImplicitExit(false);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                moStageFX=new Stage(StageStyle.TRANSPARENT);
//                Region loReg = new Region();
//                loReg.setPrefWidth(1);
//                loReg.setPrefHeight(1);
//                loReg.setMaxHeight(1);
//                loReg.setMaxHeight(1);
//                
//                Scene loScene = new Scene(loReg, javafx.scene.paint.Color.TRANSPARENT);
//                moStageFX.setScene(loScene);
//                moStageFX.setResizable(false);
//                moStageFX.setWidth(1);
//                moStageFX.setHeight(1);
//                moStageFX.getScene().getRoot().setScaleX(0.1);
//                moStageFX.getScene().getRoot().setScaleY(0.1);
//                moStageFX.setX(2000);
//                moStageFX.setY(2000);
//                moStageFX.show();
                    }
                });
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void initJavaFX2(){
        
            Object lo = new Object();
            AplicacionFX.main(null);
            while(!JFXConfigGlobal.getInstancia().isInicializadoJAVAFX()){
                synchronized(lo){
                    try{
                        lo.wait(1000);
                    }catch(Exception e){}
                }
            }
    }
    public static class AplicacionFX extends Application {

        public AplicacionFX() {
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            JFXConfigGlobal.getInstancia().setInicializadoJAVAFX(true);

        }

        public static void main(String[] strings) {
            launch(strings);
        }

    }
}
