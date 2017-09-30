/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFXAvisos.forms.util;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import utilesGUIx.ColorCZ;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelTableRender extends BorderPane {

    @FXML private BorderPane jPanelCompleto;
    @FXML private Label lblPrioridad;
    @FXML private WebView lblHora;
    @FXML private Label lblTarea;
    
    private JTableRenderEventoParam moTableRenderAvisoParam;

    public JPanelTableRender() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFXAvisos/forms/util/JPanelTableRender.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }        

    }
    void setValue(JTableRenderEventoParam poPAram, boolean pbIsSelected) {
        moTableRenderAvisoParam=poPAram;
        ColorCZ loColor = poPAram.getPrioridad();
        if (loColor != null) {
            String hexColor = String.format("#%06X", (0xFFFFFF & loColor.getColor()));
            lblPrioridad.setStyle(("-fx-background-color:" + hexColor + ";"));
        }
        lblPrioridad.setText("  ");
        lblTarea.setText(poPAram.getNombre());
        lblHora.getEngine().loadContent(poPAram.toStringFechas());
//        
//
//        if(poPAram.isAviso()){
//            lblTarea.setStyle(".label .text { -fx-strikethrough: true; }");
//            } else {
//            lblTarea.setStyle("");
//        }
//        
    }
    
    public JTableRenderEventoParam getParam(){
        return moTableRenderAvisoParam;
    }
}
