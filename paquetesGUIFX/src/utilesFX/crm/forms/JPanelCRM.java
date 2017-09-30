/*
* JPanelCRMAGRUPACION.java
*
* Creado el 27/4/2017
*/

package utilesFX.crm.forms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TabPane;

import utilesFX.*;
import utilesGUIx.*;
import ListDatos.*;
import java.util.HashMap;


import javafx.scene.control.Tab;
import utilesFX.crm.tablasControladoras.JT2CRMEMAILYNOTAS;
import utilesFX.doc.tablasControladoras.JT2DOCUMENTOS;
import utilesFX.formsGenericos.JPanelGenerico2;
import utilesFX.formsGenericos.edicion.JPanelEdicionAbstract;
import utilesFXAvisos.forms.JPanelGenericoEVENTOS;
import utilesFXAvisos.tablasControladoras.JT2GUIXEVENTOS;

public class JPanelCRM extends JPanelEdicionAbstract {

    private JT2CRMEMAILYNOTAS moCRMANotas;

    @FXML  private TabPane jTabbedPane1;
    @FXML  private Tab tabNotas;
    @FXML  private JPanelGenerico2 jPanelGeneralNotas;
    @FXML  private Tab tabTareas;
    @FXML  private Tab tabDocumentos;
    @FXML  private JPanelGenerico2 jPanelGeneralDoc;
    private JT2DOCUMENTOS moDoc;
    private JT2GUIXEVENTOS moEvent;
    private final JPanelGenericoEVENTOS moPanelEvent;
    
    
    /** Creates new form JPanelCRMAGRUPACION*/
    public JPanelCRM() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("JPanelCRM.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        moPanelEvent = new JPanelGenericoEVENTOS();
        tabTareas.setContent(moPanelEvent);
    }

    public void setDatos(
            String psGrupo, String psGrupoIdent, String psCodContacto, String psUsu
            , String psCalenDefect, String psnombreTareas, String psEMail, boolean pbFiltroContacto
            , utilesGUIxAvisos.calendario.JDatosGenerales poDatosGenTareas
            , HashMap<String, Object> poListaCampos) throws Exception {
        moCRMANotas = new JT2CRMEMAILYNOTAS(
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getServer()
                , JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getMostrarPantalla()
                , psGrupo+"-"+psGrupoIdent, psUsu, psCodContacto, psEMail, pbFiltroContacto, psnombreTareas, poListaCampos);
       
        moDoc = new JT2DOCUMENTOS(
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getServer()
                , JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getMostrarPantalla()
                , psGrupo, psGrupoIdent);
        
        moEvent = new JT2GUIXEVENTOS(
                    poDatosGenTareas, psGrupo+"-"+psGrupoIdent, psCalenDefect, psnombreTareas, psUsu);
    }

    @Override
    public String getTitulo() {
        return "Notas";
    }

    @Override
    public JSTabla getTabla(){
        return null;
    }

    @Override
    public void rellenarPantalla() throws Exception {

    }

    @Override
    public void habilitarSegunEdicion() throws Exception {

    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
        super.mostrarDatos();
        jPanelGeneralNotas.setControlador(moCRMANotas);
        moPanelEvent.setControlador(moEvent);
        jPanelGeneralDoc.setControlador(moDoc);
        
    }

    @Override
    public void establecerDatos() throws Exception {
        super.establecerDatos();

    }

    @Override
    public void aceptar() throws Exception {

    }

    @Override
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 1024, 700);
    }

    @Override
    public void cancelar() throws Exception {
    }

}
