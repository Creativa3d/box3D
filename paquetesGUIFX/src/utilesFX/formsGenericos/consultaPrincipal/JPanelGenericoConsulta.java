/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos.consultaPrincipal;

import ListDatos.JFilaDatosDefecto;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.CustomTextField;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JListaElementos;
import utilesFX.JCMBLinea;
import utilesFX.JFieldConComboBox;
import utilesFX.formsGenericos.JPanelGenerico2;
import utilesGUIx.formsGenericos.IPanelControladorConsulta;
import utilesGUIx.formsGenericos.consultaPrincipal.JPanelGenericoConsFiltro;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**
 *
 * @author eduardo
 */
public class JPanelGenericoConsulta  extends BorderPane  implements IPlugInConsulta {
    private static final long serialVersionUID = 1L;
    
    @FXML private ComboBox<JCMBLinea> cmbTabla;
    @FXML private ComboBox<JCMBLinea> cmbCampo;
    @FXML private GridPane jPanelFiltros;
    @FXML private CustomTextField txtDesde;
    @FXML private CustomTextField txtHasta;    
    private JPanelGenerico2 jPanelConsultaGenerico1;
    
    
    private final IListaElementos moControladores;
    private boolean mbDesactiva = false;
    
    private IListaElementos moFiltroActual;

    private String msUltimoFiltro = "";
    private final JFormEdicionParametros moParametros = new JFormEdicionParametros();

    private String msNombre="consulta";
    private JFieldConComboBox cmbCampoModelo;
    private final JFieldConComboBox cmbTablaModelo;
        
    public JPanelGenericoConsulta(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/formsGenericos/consultaPrincipal/JPanelGenericoConsulta.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        jPanelConsultaGenerico1 = new JPanelGenerico2();
        setCenter(jPanelConsultaGenerico1);
        mbDesactiva = true;
        moControladores = new JListaElementos();
        
        mbDesactiva = false;
        cmbCampoModelo =  new JFieldConComboBox(cmbCampo,false);
        cmbTablaModelo =  new JFieldConComboBox(cmbTabla,false);

        
        rellenarCombos();
    }
    
    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }

    public void setNombre(String psNombre) throws Exception{
        msNombre=psNombre;
    }

//    @Override
//    public void setEnabled(boolean enabled) {
//        try {
//            JPanelGENERALBASE.setBloqueoControlesContainer(this, !enabled);
//            super.setEnabled(enabled);
//        } catch (Exception ex) {
//        }
//    }

    
    private void rellenarCombos(){
        boolean lbDesaAux = mbDesactiva;
        mbDesactiva = true;
        try{
            
            cmbTablaModelo.borrarTodo();
        
            for(int i = 0 ; i < moControladores.size(); i++){
                IPanelControladorConsulta loContr = (IPanelControladorConsulta)moControladores.get(i);
                cmbTablaModelo.addLinea(loContr.getParametros().getTitulo(), String.valueOf(i) + JFilaDatosDefecto.mcsSeparacion1);
            }
        }finally{
            mbDesactiva=lbDesaAux;
        }
    }
    public void setControlador(int plIndex) throws Exception{
        boolean lbDesaAux = mbDesactiva;
        mbDesactiva = true;
        try{
            cmbTablaModelo.mbSeleccionarClave(String.valueOf(plIndex) + JFilaDatosDefecto.mcsSeparacion1);
        } finally {  
            mbDesactiva=lbDesaAux;
        }
        setControladorInterno(plIndex);
        
    }
    private void setControladorInterno(int plIndex) throws Exception{
        
        boolean lbDesaAux = mbDesactiva;
        mbDesactiva = true;
        try{
            System.gc();

            IPanelControladorConsulta loContro = (IPanelControladorConsulta)moControladores.get(plIndex);

            //rellenamos las fechas
            cmbCampoModelo.borrarTodo();

            moFiltroActual = loContro.getFiltros();
            if(moFiltroActual == null || moFiltroActual.size()==0){
                jPanelFiltros.setVisible(false);
            }else{
                jPanelFiltros.setVisible(true);

                for(int i = 0; i < moFiltroActual.size();i++){
                    JPanelGenericoConsFiltro loFiltro = (JPanelGenericoConsFiltro)moFiltroActual.get(i);
                    cmbCampoModelo.addLinea(
                            loFiltro.msCaption, 
                            String.valueOf(loFiltro.mlCampo) + JFilaDatosDefecto.mcsSeparacion1 + 
                                String.valueOf(i) + JFilaDatosDefecto.mcsSeparacion1 
                            );

                }
                jPanelFiltros.setVisible(true);

            }

            refrescarControlador(plIndex);

        } finally {  
            mbDesactiva=lbDesaAux;
        }
    }
    private void refrescarControlador(int plIndex) throws Exception{
        String lsFiltro = 
                String.valueOf(plIndex) + ";" + 
                txtDesde.getText() + ";" +  
                txtHasta.getText() + ";" + 
                cmbCampoModelo.getFilaActual().msCampo(0) + ";";
        
        if(!lsFiltro.equals(msUltimoFiltro)){
            msUltimoFiltro = lsFiltro;
            
            IPanelControladorConsulta loContro = (IPanelControladorConsulta)moControladores.get(plIndex);
            //personalizamos el controlador
            loContro.setFechas(txtDesde.getText(), txtHasta.getText(), (int)JConversiones.cdbl(cmbCampoModelo.getFilaActual().msCampo(0)));

            //lo establecemos al form
            jPanelConsultaGenerico1.setControlador(loContro);

        }
        

    }
    @Override
    public IPanelControladorConsulta getControladorActual() {
        return (IPanelControladorConsulta) jPanelConsultaGenerico1.getControlador();
    }

    @Override
    public void addControlador(IPanelControladorConsulta poControlador){
        moControladores.add(poControlador);
        msUltimoFiltro="";
        rellenarCombos();
    }
    public void removeAllControlador(){
        moControladores.clear();
        msUltimoFiltro="";
        rellenarCombos();
    }
    @Override
    public void removeControlador(IPanelControladorConsulta poControlador){
        moControladores.remove(poControlador);
        msUltimoFiltro="";
        rellenarCombos();
    }
    @Override
    public IPanelControladorConsulta getControlador(int i){
        return (IPanelControladorConsulta)moControladores.get(i);
    }
    @Override
    public void getControladorSize(){

        moControladores.size();
    }
    public int getControladorIndex(){
        return (int)JConversiones.cdbl(cmbTablaModelo.getFilaActual().msCampo(0));
    }
    
    public MenuBar getMenu() {
        return null;
    }

    @Override
    public String getIdentificador() {
        return msNombre;
    }

    @Override
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }
}
