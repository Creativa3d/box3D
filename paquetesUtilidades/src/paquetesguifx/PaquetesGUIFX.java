/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetesguifx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author eduardo
 */
public class PaquetesGUIFX extends Application {
    
    @Override
    public void start(Stage primaryStage)  throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/utilesFX/aplicacion/JFormPrincipa1.fxml"));
    
        Scene scene = new Scene(root, 300, 275);
    
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
