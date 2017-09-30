/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.controlProcesos;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import utilesGUI.procesar.JProcesoAccionParam;



public class JProcesoControl  extends BorderPane {
    @FXML
    private Button btnCancelar;

    @FXML
    private Label lblMensase;

    @FXML
    private ProgressBar jProgressBar1;
    
    private JProcesoElemento moAccion;
    
    public JProcesoControl(final JProcesoElemento poAccion) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/controlProcesos/JProcesoControl.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }        
        
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                moAccion.getProceso().setCancelado(true);
            }

        });
            
        moAccion=poAccion;

    }

    public JProcesoElemento getElemento(){
        return moAccion;
    }
    private void procesar(final int plnumeroRegistro, final int plnumeroRegistros,final String psTituloVentana, final String pstitulo, final JProcesoAccionParam poParam){
        String lsL = "";
        int ldL = -1;
        if(plnumeroRegistros>0){
            ldL=(plnumeroRegistro*100)/plnumeroRegistros;
            lsL = String.valueOf(ldL) + "% " ;
        } else {
            lsL = "Paso: "  + String.valueOf(plnumeroRegistro)  + " ";  
        }
        if(psTituloVentana.equalsIgnoreCase(pstitulo)){
            lblMensase.setText(lsL+ psTituloVentana);
        }else{
            lblMensase.setText(lsL + psTituloVentana + " - " +  pstitulo);
        }
        if(plnumeroRegistro<=0){
            jProgressBar1.setProgress(-1);
        }else{
            jProgressBar1.setProgress(ldL/100.0);
        }
        if(poParam!=null){
            btnCancelar.setVisible(poParam.isTieneCancelado());
        }
    }
    public void establecer(final int plnumeroRegistro, final int plnumeroRegistros,final String psTituloVentana, final String pstitulo, final JProcesoAccionParam poParam) {
         if (Platform.isFxApplicationThread()) {
            procesar(plnumeroRegistro, plnumeroRegistros,psTituloVentana, pstitulo, poParam);
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    procesar(plnumeroRegistro, plnumeroRegistros,psTituloVentana, pstitulo, poParam);
                }
            });
        }
    }
  

    
}
