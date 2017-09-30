/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetesguifx;

import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import utilesFX.JFXConfigGlobal;

/**
 *
 * @author eduardo
 */
public class JMostrarPantallaInitFX {

    public static void initJavaFX() {

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {

            @Override
            public void run() {
                @SuppressWarnings("unused")
                JFXPanel p = new JFXPanel();
                latch.countDown();
            }
        }, "JavaFX-initializer").start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
