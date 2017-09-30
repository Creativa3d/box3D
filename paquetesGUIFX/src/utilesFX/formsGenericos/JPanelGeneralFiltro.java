/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JUtilTabla;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import utiles.JCadenas;
import utilesFX.Copiar;
import utilesFX.JCMBLinea;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.JTableViewCZ;
import utilesFX.TableCellComboBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.formsGenericos.JPanelGeneralFiltroModelo;
import utilesGUIx.formsGenericos.JTFiltro;
import utilesGUIx.formsGenericos.JTablaConfigTabla;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelGeneralFiltro extends BorderPane {

    @FXML
    private Button btnBorrar;

    @FXML
    private Button jBtnDuplicar;


    @FXML
    private ComboBox cmbFiltros;

    @FXML
    private JTableViewCZ moTabla;

    @FXML
    private Button jBtnLimpiar;

    @FXML
    private CheckBox chkFiltroDefecto;
    
    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView jBtnLimpiarImg;

    @FXML
    private ImageView jBtnCopiarTablaImg;

    @FXML
    private ImageView jBtnDuplicarImg;

    @FXML
    private Button jBtnCopiarTabla;

    @FXML
    private Button jBtnSalir;
    
    private JPanelGeneralFiltroModelo moFiltroModelo;
    private JFieldConComboBox moCMBFiltros;

    private transient boolean mbInicializando=false;
    
    public JPanelGeneralFiltro() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/utilesFX/formsGenericos/JPanelGeneralFiltro.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            final Node root = (Node) loader.load();

        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        jBtnCopiarTablaImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/Copy16.gif")));
        jBtnDuplicarImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/RowInsertAfter16.gif")));
        jBtnLimpiarImg.setImage(new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/RowDelete16.gif")));

        jBtnCopiarTabla.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                jBtnCopiarTablaActionPerformed();
            }
        });
        jBtnDuplicar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                jBtnDuplicarActionPerformed();
            }
        });
        jBtnLimpiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                jBtnLimpiarActionPerformed();
            }
        });
        jBtnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(JPanelGeneralFiltro.this);
            }
        });
        btnBorrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnBorrarActionPerformed();
            }
        });
//        btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent t) {
//                btnGuardarActionPerformed();
//            }
//        });
        btnNuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnNuevoActionPerformed();
            }
        });
        
        chkFiltroDefecto.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                chkFiltroDefectoItemStateChanged();
            }
        });
                
                
        moCMBFiltros = new JFieldConComboBox(cmbFiltros);
        
        cmbFiltros.valueProperty().addListener(new ChangeListener<JCMBLinea>() {
                @Override 
                public void changed(ObservableValue ov, JCMBLinea t, JCMBLinea t1) {                
                    cmbFiltrosItemStateChanged();
                }    
            });

    }


    public void setDatos(final JPanelGeneralFiltroModelo poDatos) throws Exception{
        moFiltroModelo=poDatos;
        mbInicializando=true;
        try{
            crearCombos();
            mostrarTabla();
        }finally{
            mbInicializando=false;
        }
    }

    private void crearCombos() throws ECampoError{
       
        moCMBFiltros.RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
        moCMBFiltros.mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
        
    }
        
    //rellena la tabla
    private void mostrarTabla() throws Exception {
        moFiltroModelo.setAnularEvento(true);
        try {        
            moTabla.setEditable(true);
            moTabla.setModel(moFiltroModelo.getTablaFiltro().getList());

            TableColumn loColumn = (TableColumn) moTabla.getColumns().get(JTFiltro.lPosiComparacion);
            loColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn p) {
                    return new TableCellComboBox(moFiltroModelo.getTablaFiltro().getList(), JTFiltro.lPosiComparacion, moTabla, moFiltroModelo.getListComparaciones(), new int[]{0}, new int[]{0});
                }
            });
            loColumn = (TableColumn) moTabla.getColumns().get(JTFiltro.lPosiUnion);
            loColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn p) {
                    return new TableCellComboBox(moFiltroModelo.getTablaFiltro().getList(), JTFiltro.lPosiUnion, moTabla, moFiltroModelo.getListUniones(), new int[]{0}, new int[]{0});
                }
            });
        //        moTabla.setColorBackgroundDesac(Color.lightGray);

                // creamos los combos de las comparaciones
            //alto de fila
    //            moTabla.setRowHeight(20); 
            //ancho columnas
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiCodigo), 0);
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiNombre), 150);
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiComparacion), 100);
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiValor), 200);
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiUnion), 40);
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiDuplicadoSN), 0);
            JTablaConfig.setLongColumna((TableColumn) moTabla.getColumns().get(JTFiltro.lPosiVisibleSN), 0);

            moTabla.getSelectionModel().setCellSelectionEnabled(true);
            moTabla.getSelectionModel().select(0, (TableColumn) moTabla.getColumns().get(JTFiltro.lPosiValor));
        } finally {
            moFiltroModelo.setAnularEvento(false);
        }
    }

    public void duplicar() throws Exception {
        if (moTabla.getSelectionModel().getSelectedIndex() >= 0) {
            moFiltroModelo.duplicar(moTabla.getSelectionModel().getSelectedIndex());
        } else {
            throw new Exception("no existe la fila a duplicar");
        }
    }

    private void jBtnDuplicarActionPerformed() {
        try {
            duplicar();
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }

    private void jBtnLimpiarActionPerformed() {
        try {
            moFiltroModelo.limpiar();
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }

    private void jBtnCopiarTablaActionPerformed() {
        try {
            JListDatos loList = JTablaConfig.getListOrdenado(
                    moFiltroModelo.getDatos(), moFiltroModelo.getTablaConfig().getConfigTablaConcreta());
            loList.msTabla = moFiltroModelo.getDatos().msTabla;
            Copiar.getInstance().setClip(JUtilTabla.getListDatos2String(loList));
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }

    }

    private void cmbFiltrosItemStateChanged() {                                            
        try{
            if(!mbInicializando){
                if(!moFiltroModelo.getTablaFiltro().moList.msTabla.equals(moCMBFiltros.getFilaActual().msCampo(0))){
                    moFiltroModelo.setFiltro(moCMBFiltros.getFilaActual().msCampo(0));
                    chkFiltroDefecto.setSelected(
                            moCMBFiltros.getFilaActual().msCampo(0)
                                    .equalsIgnoreCase(moFiltroModelo.getTablaConfig().getConfigTabla().getFiltroDefecto())
                    );
                    moTabla.refrescar();                }
            }
        }catch(Exception e){
            utilesFX.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }                                           
    private void chkFiltroDefectoItemStateChanged() {                                                  
        if(!mbInicializando){
            if(chkFiltroDefecto.isSelected()){
                moFiltroModelo.getTablaConfig().getConfigTabla().setFiltroDefecto(moCMBFiltros.getFilaActual().msCampo(0));
            }
        }
    }   
    private void btnNuevoActionPerformed() {                                         
        JOptionPaneFX.showInputDialog(
                "Introducir nombre de la nueva configuración"
                , new EventHandler<JOptionPaneFX.EventInput>() {
            @Override
            public void handle(JOptionPaneFX.EventInput event) {
                try{
                    mbInicializando=true;
                    String lsNombre = event.getInput();
                    if(!JCadenas.isVacio(lsNombre)){
                        moFiltroModelo.addFiltro(lsNombre);
                        moCMBFiltros.addLinea(lsNombre, lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                        moCMBFiltros.mbSeleccionarClave(lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                        moFiltroModelo.setFiltro(lsNombre);
                        moTabla.refrescar();
                    }

                }catch(Exception e){
                    utilesFX.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
                } finally{
                    mbInicializando=false;
                }                    
            }
        }
        );
            
    }                                        

    private void btnBorrarActionPerformed() {                                          

        if(moCMBFiltros.getFilaActual().msCampo(0).compareTo("0")==0){
            utilesFX.msgbox.JMsgBox.mensajeInformacion(this, "No se puede borrar la configuración por defecto");
        }else{

            JOptionPaneFX.showInputDialog(
                    "¿Deseas borrar la configuración actual?"
                    , new EventHandler<JOptionPaneFX.EventInput>() {
                    @Override
                    public void handle(JOptionPaneFX.EventInput event) {
                        try{
                            mbInicializando=true;
                            moFiltroModelo.borrarFiltro(moCMBFiltros.getFilaActual().msCampo(0));
                            moCMBFiltros.RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
                            //datos defecto
                            moCMBFiltros.mbSeleccionarClave(JTablaConfigTabla.mcsSinFiltro + JFilaDatosDefecto.mcsSeparacion1);
                            moFiltroModelo.setFiltro(JTablaConfigTabla.mcsSinFiltro);
                            moTabla.refrescar();
                        }catch(Exception e){
                            utilesFX.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
                        } finally{
                            mbInicializando=false;
                        }                    
                    }
                }
            );                

        }
    }                                         

//    private void btnGuardarActionPerformed() {                                           
//
//        try {
//            mbInicializando=true;
//         
//            if(moCMBFiltros.getFilaActual().msCampo(0).equalsIgnoreCase(JTablaConfigTabla.mcsSinFiltro)){
//                JOptionPaneFX.showInputDialog(
//                        "Introducir nombre de la nueva configuración"
//                        , new EventHandler<JOptionPaneFX.EventInput>() {
//                        @Override
//                        public void handle(JOptionPaneFX.EventInput event) {
//                            try{
//                                mbInicializando=true;
//                                String lsNombre = event.getInput();
//                                if(!JCadenas.isVacio(lsNombre)){
//                                    JTFiltro loFiltro = moFiltroModelo.addFiltro(lsNombre);
//                                    moFiltroModelo.pasarDatos(moFiltroModelo.getTablaFiltro(), loFiltro);
//                                    loFiltro.moList.msTabla=lsNombre;
//                                    moFiltroModelo.setFiltro(lsNombre);
//                                    moCMBFiltros.addLinea(lsNombre, lsNombre + JFilaDatosDefecto.mcsSeparacion1);
//                                    moCMBFiltros.mbSeleccionarClave(lsNombre + JFilaDatosDefecto.mcsSeparacion1);
//                                }
//                                moTabla.refrescar();
//                                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeFlotante(JPanelGeneralFiltro.this, "Guardado correctamente");
//                            }catch(Exception e){
//                                utilesFX.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
//                            } finally{
//                                mbInicializando=false;
//                            }                    
//                        }
//                    }
//                );               
//            }else{
//                moFiltroModelo.pasarDatosAModelo();
//                moTabla.refrescar();
//                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeFlotante(this, "Guardado correctamente");
//            }
//        } catch (Exception ex) {
//            utilesFX.msgbox.JMsgBox.mensajeErrorYLog(this, ex);
//        } finally{
//            mbInicializando=false;
//        }
//        
//        
//    }                                          
    
}
