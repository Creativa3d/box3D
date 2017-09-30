/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFXAvisos.forms.util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import utiles.JComunicacion;
import utilesFX.JFXConfigGlobal;
import utilesFX.formsGenericos.edicion.JPanelGENERALBASE;
import utilesFX.plugin.javafx.JPlugInFrameFXAbstract;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelOpcionesRepe  extends JPlugInFrameFXAbstract {
    private JFormEdicionParametros moParametros = new JFormEdicionParametros();
    private JComunicacion moComu;
    private JDatosGenerales moDatosGenerales;

    @FXML
    private Button btnSoloEste;

    @FXML
    private Button btnSiguientes;

    @FXML
    private Button btnTodos;
    private CallBack<JComunicacion> moCallBack;

    /**
     * Creates new form JPanelOpcionesRepe
     */
    public JPanelOpcionesRepe() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelOpcionesRepe.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }  
    }

    public void setDatos(JDatosGenerales poDatosGenerales, JComunicacion poComu, CallBack<JComunicacion> poCallBack){
        moDatosGenerales=poDatosGenerales;
        moComu=poComu;
        moCallBack=poCallBack;
    }
    
    @FXML
    void btnSiguientesOnAction(ActionEvent event) {
        moComu.moObjecto=String.valueOf(JTEEGUIXEVENTOS.mclSiguientes);
        moDatosGenerales.getMostrarPantalla().cerrarForm(this);
        moCallBack.callBack(moComu);
    }

    @FXML
    void btnSoloEsteOnAction(ActionEvent event) {
        moComu.moObjecto=String.valueOf(JTEEGUIXEVENTOS.mclSoloEste);
        moDatosGenerales.getMostrarPantalla().cerrarForm(this);
        moCallBack.callBack(moComu);
    }

    @FXML
    void btnTodosOnAction(ActionEvent event) {
                 
        moComu.moObjecto=String.valueOf(JTEEGUIXEVENTOS.mclTodos);
        moDatosGenerales.getMostrarPantalla().cerrarForm(this);
        moCallBack.callBack(moComu);
    }
    @Override
    public String getIdentificador() {
        return this.getClass().getName();
    }

    @Override
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }
}
