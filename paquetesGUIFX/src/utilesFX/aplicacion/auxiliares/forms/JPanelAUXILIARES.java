/*
* JPanelAUXILIARES.java
*
* Creado el 13/9/2017
*/

package utilesFX.aplicacion.auxiliares.forms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

import utilesFX.*;
import utilesFX.formsGenericos.edicion.*;
import utilesGUIx.*;
import utilesGUIx.formsGenericos.IPanelControlador;
import ListDatos.*;
import org.controlsfx.control.textfield.CustomTextField;

import utilesGUIx.aplicacion.auxiliares.tablasExtend.JTEEAUXILIARES;

public class JPanelAUXILIARES extends JPanelGENERALBASE {

    private JTEEAUXILIARES moAUXILIARES;

    @FXML private Label lblCODIGOAUXILIAR;
    @FXML private CustomTextField txtCODIGOAUXILIAR;
    @FXML private Label lblCODIGOGRUPOAUX;
    @FXML private CustomTextField txtCODIGOGRUPOAUX;
    @FXML private Label lblACRONIMO;
    @FXML private CustomTextField txtACRONIMO;
    @FXML private Label lblDESCRIPCION;
    @FXML private CustomTextField txtDESCRIPCION;
    /** Creates new form JPanelAUXILIARES*/
    public JPanelAUXILIARES() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelAUXILIARES.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }             
    }

    public void setDatos(final JTEEAUXILIARES poAUXILIARES, final IPanelControlador poPadre) throws Exception {
        moAUXILIARES = poAUXILIARES;
        setDatos(poPadre);
    }

    @Override
    public String getTitulo() {
        String lsResult;
        if(moAUXILIARES.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JFXConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARES.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=JFXConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARES.msCTabla)  + 
                moAUXILIARES.getCODIGOAUXILIAR().getString();
        }
        return lsResult;
    }

    @Override
    public JSTabla getTabla(){
        return moAUXILIARES;
    }

    @Override
    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOAUXILIAR.setText(moAUXILIARES.getCODIGOAUXILIAR().getCaption());
        addFieldControl(new JFieldControl(txtCODIGOAUXILIAR,moAUXILIARES.getCODIGOAUXILIAR()));
        lblCODIGOGRUPOAUX.setText(moAUXILIARES.getCODIGOGRUPOAUX().getCaption());
        addFieldControl(new JFieldControl(txtCODIGOGRUPOAUX,moAUXILIARES.getCODIGOGRUPOAUX()));
        lblACRONIMO.setText(moAUXILIARES.getACRONIMO().getCaption());
        addFieldControl(new JFieldControl(txtACRONIMO,moAUXILIARES.getACRONIMO()));
        lblDESCRIPCION.setText(moAUXILIARES.getDESCRIPCION().getCaption());
        addFieldControl(new JFieldControl(txtDESCRIPCION,moAUXILIARES.getDESCRIPCION()));
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
        if(moAUXILIARES.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOAUXILIAR.setDisable(false);
        }else{
            txtCODIGOAUXILIAR.setDisable(true);
        }
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
        super.mostrarDatos();

    }

    @Override
    public void establecerDatos() throws Exception {
        super.establecerDatos();
        moAUXILIARES.validarCampos();
    }

    @Override
    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moAUXILIARES.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 400);
    }

}
