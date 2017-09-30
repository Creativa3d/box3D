/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.IListDatosEdicion;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import utilesFX.JCMBLinea;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.JTableViewCZ;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.JPanelGeneralParametros;
import utilesGUIx.formsGenericos.JTablaConfigTablaConfig;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelConfig  extends BorderPane implements ISalir {
    static final int mclPosiIndice = 0;
    static final int mclPosiNombre = 1;
    static final int mclPosiLong = 2;
    static final int mclPosiOrden = 3;
    static final int mclPosiVisible = 4;
    
    private transient JTablaConfig moConfigOriginal;
    private transient JListDatos moListDatos;
    private transient JListDatos moListDatosOrigen;
    private transient boolean mbInicializando=false;
    private JPanelGeneralParametros moParam;
    private JFieldConComboBox cmbConfigModelo;
    private JFieldConComboBox cmbColumnaDefectoModelo;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnBorrar;
    @FXML private Button btnSalir;
    @FXML private ComboBox<JCMBLinea> cmbConfig;
    @FXML private ComboBox<JCMBLinea> cmbColumnaDefecto;
    @FXML private JTableViewCZ jTableConfig;
    
    private EventHandler<ActionEvent> moPadre;
    
    public JPanelConfig(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/formsGenericos/JPanelConfig.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
            
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        btnNuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnNuevoActionPerformed();
            }
        });
        btnBorrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnBorrarActionPerformed();
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(JPanelConfig.this);
            }
        });
        
        cmbColumnaDefecto.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1){
                    cmbColumnaDefectoFocusLost();
                }
            }
        });
        cmbConfig.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<JCMBLinea>() {

            @Override
            public void changed(ObservableValue<? extends JCMBLinea> ov, JCMBLinea t, JCMBLinea t1) {
                cmbConfigItemStateChanged();                
            }
        });
        

        jTableConfig.setEditable(true);
        jTableConfig.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        jTableConfig.getSelectionModel().setCellSelectionEnabled(true);
        
        
    }    
    public void setDatos(final JTablaConfig poConfig, final JListDatos poModelo, final JPanelGeneralParametros poParam, EventHandler<ActionEvent> poPadre) throws ECampoError, Exception{
        //inicializacion de variables
        mbInicializando=true;
        moParam = poParam;
        moListDatosOrigen = poModelo;
        moConfigOriginal = poConfig;
        moPadre=poPadre;
        cmbConfigModelo = new JFieldConComboBox(cmbConfig);
        cmbColumnaDefectoModelo = new JFieldConComboBox(cmbColumnaDefecto);
        
        moListDatos = new JListDatos(null, "",
                new String[]{
                    "Indice Columna","Nombre Columna",
                    "Ancho","Orden",
                    "Visible"},
                new int[]{
                    JListDatos.mclTipoNumero, JListDatos.mclTipoCadena,
                    JListDatos.mclTipoNumero, JListDatos.mclTipoNumero,
                    JListDatos.mclTipoBoolean} ,
                new int[]{0});
        moListDatos.getFields(mclPosiIndice).setEditable(false);
        moListDatos.getFields(mclPosiNombre).setEditable(false);
        moListDatos.getFields(mclPosiVisible).setEditable(false);
        
        jTableConfig.setModel(moListDatos);
        moListDatos.addListenerEdicion(new IListDatosEdicion() {
            @Override
            public void edicionDatos(int plModo, int plIndex, IFilaDatos poDatos) {
                if(!mbInicializando){
                    actionPerformed();
                }
            }

            @Override
            public void edicionDatosAntes(int plModo, int plIndex) throws Exception {
            }
        });

        JTablaConfig.setLongColumna((TableColumn) jTableConfig.getColumns().get(mclPosiVisible), 0);
        JTablaConfig.setLongColumna((TableColumn) jTableConfig.getColumns().get(mclPosiIndice), 0);

        
        cmbColumnaDefectoModelo.borrarTodo();
        for(int i = 0 ; i < moListDatosOrigen.getFields().size(); i++){
            cmbColumnaDefectoModelo.addLinea(
                    String.valueOf(i) + "-" + moListDatosOrigen.getFields(i).getCaption()
                    ,  String.valueOf(i) + JFilaDatosDefecto.mcsSeparacion1);
        }
        
        leerConfig();
        
        //datos defecto
        cmbColumnaDefectoModelo.mbSeleccionarClave(poConfig.getConfigTabla().getCampoBusqueda() + JFilaDatosDefecto.mcsSeparacion1);
        cmbConfigModelo.mbSeleccionarClave(poConfig.getConfigTablaConcreta().getNombre() + JFilaDatosDefecto.mcsSeparacion1);
        mostrarConfig(poConfig.getConfigTablaConcreta().getNombre());
        mbInicializando=false;
    }
    private void leerConfig(){
        //config
        cmbConfigModelo.borrarTodo();
        boolean lbAddLineaDefecto = true;
        for(int i = 0; i < moConfigOriginal.getConfigTabla().size();i++){
            if(JTablaConfig.mcsNombreDefecto.equals(moConfigOriginal.getConfigTabla().getConfig(i).getNombre())){
                lbAddLineaDefecto = false;
            }
            cmbConfigModelo.addLinea(
                    moConfigOriginal.getConfigTabla().getConfig(i).getNombre(), 
                    moConfigOriginal.getConfigTabla().getConfig(i).getNombre() + JFilaDatosDefecto.mcsSeparacion1);
        }
        if(lbAddLineaDefecto){
            cmbConfigModelo.addLinea(JTablaConfig.mcsNombreDefecto, JTablaConfig.mcsNombreDefecto + JFilaDatosDefecto.mcsSeparacion1);
        }
    }
    
    private void mostrarConfig(final String psIndice) throws ECampoError{

        moConfigOriginal.setIndiceConfig(psIndice);
        moConfigOriginal.aplicar();
        
        actualizarTablaDatos();
    }
    private void actualizarTablaDatos() throws ECampoError{
        moListDatos.clear();
        for(int i = 0 ; i < moListDatosOrigen.getFields().size() ; i++){
            moListDatos.addNew();
            moListDatos.getFields(mclPosiIndice).setValue(
                    i
                    );
            moListDatos.getFields(mclPosiNombre).setValue(
                    moListDatosOrigen.getFields(i).getCaption()
                    );
            try{

                moListDatos.getFields(mclPosiLong).setValue(
                    moConfigOriginal.getConfigTablaConcreta().getTablaConfigColumnaDeCampoReal(
                        i
                        ).getLong()
                    );
            }catch(Exception e){
            }
            try{
                moListDatos.getFields(mclPosiOrden).setValue(
                    moConfigOriginal.getConfigTablaConcreta().getColumna(i).getOrden() 
                    );
            }catch(Exception e){
            }
            try{
                moListDatos.getFields(mclPosiVisible).setValue(true);
                if(moParam.getColumnasVisiblesConfig()!= null){
                    moListDatos.getFields(mclPosiVisible).setValue(
                        moParam.getColumnasVisiblesConfig()[i]
                        );
                }
            }catch(Exception e){
            }
            moListDatos.update(false);
        }
        //quitamos los campos no visilbes de config.
        moListDatos.getFiltro().Clear();
        moListDatos.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                mclPosiVisible,
                JListDatos.mcsTrue
                );
        moListDatos.filtrar();
        jTableConfig.refrescar();

    }
    public void actionPerformed() {
        try{
            if(moListDatos.moveFirst()){
                do{
                    moConfigOriginal.getConfigTablaConcreta().getColumna(
                                moListDatos.getFields(mclPosiIndice).getString()
                            ).setLong(
                                moListDatos.getFields(mclPosiLong).getInteger()
                            );
                    moConfigOriginal.getConfigTablaConcreta().getColumna(
                                moListDatos.getFields(mclPosiIndice).getString()
                            ).setOrden(
                                moListDatos.getFields(mclPosiOrden).getInteger()
                            );
                }while(moListDatos.moveNext());
            }
            
            moConfigOriginal.getConfigTabla().setCampoBusqueda(cmbColumnaDefectoModelo.getFilaActual().msCampo(0));
            moConfigOriginal.aplicar();
//            moConfigOriginal.guardarConfig();
        }catch(Exception e1){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e1, null);
        }
        
    }    

    private void cmbColumnaDefectoFocusLost() {                                            
        actionPerformed();
    }                                           

    private void btnNuevoActionPerformed() {                                         

        JOptionPaneFX.showInputDialog(this, "Introducir nombre de la nueva configuración", new EventHandler<JOptionPaneFX.EventInput>() {

            @Override
            public void handle(JOptionPaneFX.EventInput t) {
                try{
                    String lsNombre = t.getInput();
                    if(!lsNombre.equals("")){
                        JTablaConfigTablaConfig loConfig = new JTablaConfigTablaConfig();
                        loConfig.setNombre(lsNombre);
                        moConfigOriginal.getConfigTabla().addConfig(loConfig);
                        mostrarConfig(lsNombre);
        //                moConfigOriginal.guardarConfig();

                        cmbConfigModelo.addLinea(lsNombre, lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                        cmbConfigModelo.mbSeleccionarClave(lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                    }                    

                }catch(Throwable e){
                    JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
                }                    
            }
        });


    }                                        

    private void btnBorrarActionPerformed() {                                          
        try{
            if(cmbConfigModelo.getFilaActual().msCampo(0).compareTo("0")==0){
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensaje(this, "No se puede borrar la configuración por defecto", JOptionPaneFX.INFORMATION_MESSAGE, null);
            }else{
                JOptionPaneFX.showConfirmDialog(this, "¿Deseas borrar la configuración actual?", new Runnable() {

                    @Override
                    public void run() {
                        try{
                            mbInicializando=true;
                            moConfigOriginal.getConfigTabla().removeConfig( 
                                    cmbConfigModelo.getFilaActual().msCampo(0)
                                    );
                            leerConfig();
                            //datos defecto
                            cmbConfigModelo.mbSeleccionarClave(JTablaConfig.mcsNombreDefecto + JFilaDatosDefecto.mcsSeparacion1);
                            mostrarConfig(JTablaConfig.mcsNombreDefecto);
                            mbInicializando=false;
                        }catch(Throwable e){
                            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
                        }                        
                    }
                }, null);
                        
            }
        }catch(Throwable e){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
        
    }                                         

    private void cmbConfigItemStateChanged() {                                           
        try{
            if(!mbInicializando){
                mostrarConfig(cmbConfigModelo.getFilaActual().msCampo(0));
            }
        }catch(Throwable e){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }                                          

    @Override
    public void salir() {
        try{
            moPadre.handle(null);
            JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(JPanelConfig.this);
        }catch(Throwable e){
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }

    @Override
    public void setTitle(String psTitulo) {
        
    }
        
    
}
