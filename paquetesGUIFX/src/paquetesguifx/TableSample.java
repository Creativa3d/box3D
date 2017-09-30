package paquetesguifx;

/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;
import utilesFX.JTableViewCZ;
import utilesFX.TableCellComboBox;
import utilesFX.TableCellValueComboBox;
import utilesFX.TableCellWebViewConColor;
import utilesFX.formsGenericos.JPanelGeneralFiltro;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ColorCZ;
import utilesGUIx.formsGenericos.JPanelGeneralFiltroModelo;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;
 
/**
 * A sample showing use of an an asynchronous Task to populate a table.
 *
 * @see javafx.collections.FXCollections
 * @see javafx.concurrent.Task
 * @see javafx.scene.control.ProgressIndicator
 * @see javafx.scene.control.TableColumn
 * @see javafx.scene.control.TableView
 */
public class TableSample extends Application {
 
    JTableViewCZ tableView;
    JListDatos moList;
    private void init(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    JFXConfigGlobal.getInstancia().getEstilo()
                        );
            primaryStage.setScene(scene);
            primaryStage.setHeight(600);
            primaryStage.setWidth(1000);
            

            tableView = new JTableViewCZ();
            final VBox vBox = new VBox();
            
             moList= new JListDatos(null, "prueba"
                    , new String[]{"Numero", "Fecha", "Cadena", "Boolean", "NumeroDoble","desplegable"}
                    , new int[]{JListDatos.mclTipoNumero, JListDatos.mclTipoFecha, JListDatos.mclTipoCadena, JListDatos.mclTipoBoolean, JListDatos.mclTipoNumeroDoble, JListDatos.mclTipoCadena}
                    , new int[]{0});
            

            
            for (int i = 0; i < 500; i++) {
                moList.addNew();
                moList.getFields(0).setValue(i);
                moList.getFields(1).setValue(String.valueOf(i%30+1)+"/"+String.valueOf(i%12+1)+"/2011");
                moList.getFields(2).setValue("Cadena" + i);
                moList.getFields(3).setValue(i%5==0);
                moList.getFields(4).setValue((double)i/100.0);
                moList.getFields(5).setValue(i%5);
                moList.update(false);
                
            }
            JPanelGenericoColores loColores = new JPanelGenericoColores(moList);
            
            loColores.addCondicion(
                    new JListDatosFiltroElem(JListDatos.mclTMayor, 0, "12")
                        , new ColorCZ(Integer.parseInt("FF0000", 16))
                        , new ColorCZ(Integer.parseInt("00FF00", 16)));
            loColores.addCondicion(
                    new JListDatosFiltroElem(JListDatos.mclTMenor, 0, "12")
                        , new ColorCZ(Integer.parseInt("00FF00", 16))
                        , null); 
            
    
            loColores.setDatos(moList);
            tableView.setTableCZColores(loColores);

            tableView.setModel(moList);
            TableColumn loColum = (TableColumn) tableView.getColumns().get(2);
            loColum.setCellFactory(new Callback<TableColumn, TableCell>() {
                            @Override
                            public TableCell call(TableColumn param) {
                                return new TableCellWebViewConColor(moList, 2, tableView);
                            }
                        });
                        
            final JListDatos loListDespe = new JListDatos(null, "", new String[]{"cod","desc"}, new int[]{0,0}, new int[]{0});
            loListDespe.add(new JFilaDatosDefecto(new String[]{"0","Dexcrip 0"}));
            loListDespe.add(new JFilaDatosDefecto(new String[]{"1","Dexcrip 1"}));
            loListDespe.add(new JFilaDatosDefecto(new String[]{"2","Dexcrip 2"}));
            loListDespe.add(new JFilaDatosDefecto(new String[]{"3","Dexcrip 3"}));
            loListDespe.add(new JFilaDatosDefecto(new String[]{"4","Dexcrip 4"}));
            TableColumn loColumn = (TableColumn) tableView.getColumns().get(5);
            loColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn p) {
                    return new TableCellComboBox(moList, 5, tableView, loListDespe, new int[]{0,1}, new int[]{0});
                }
            });
            loColumn.setCellValueFactory(new TableCellValueComboBox(moList, 5, loListDespe, new int[]{0,1}, new int[]{0})); 
            
            tableView.setEditable(true);
            tableView.getSelectionModel().setCellSelectionEnabled(true);
            Button loB = new Button("accion");
            loB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {

                        JPanelGeneralFiltroModelo loModelo = new JPanelGeneralFiltroModelo();
                        loModelo.setDatos(moList, new ActionListenerCZ() {
                            @Override
                            public void actionPerformed(ActionEventCZ e) {
                               tableView.refrescar();
                            }
                        }, true, null);
                        JPanelGeneralFiltro loControladorGUI = new JPanelGeneralFiltro();
                        loControladorGUI.setDatos(loModelo);
                        
                        Scene scene = new Scene(loControladorGUI, 300, 275);
                        scene.getStylesheets().add(
                                JFXConfigGlobal.getInstancia().getEstilo()
                                    );
                        
                        final Stage dialog = new Stage();
                        dialog.setScene(scene);
                        dialog.setTitle("Filtro");
                        dialog.initModality(Modality.NONE);
                        dialog.show();
                        
                        JOptionPaneFX.showConfirmDialog(vBox, "confir", new Runnable() {
                            
                            @Override
                            public void run() {
                                JOptionPaneFX.showInputDialog(vBox, moList.getFields(4).getCaption(), new EventHandler<JOptionPaneFX.EventInput>() {
                                    
                                    @Override
                                    public void handle(JOptionPaneFX.EventInput t) {
                                        try{
                                            String lsValor = t.getInput();
                                            
                                            moList.setIndex(tableView.getSelectionModel().getSelectedIndex());
                                            moList.getFields(4).setValue(lsValor);
                                            moList.update(false);
                                        } catch (Exception ex) {
                                        }
                                        for(int i = 0 ; i < tableView.getColumns().size(); i++){
                                            TableColumn loCol = (TableColumn) tableView.getColumns().get(i);
                                            System.out.println(loCol.getText());
                                        }
                                    }
                                });
                                
                            }
                        }, null);
                    } catch (Throwable ex) {
                        JMsgBox.mensajeError(vBox, ex);
                    }
                }
            });
            vBox.getChildren().addAll(tableView, loB);
            vBox.setPrefHeight(55);
            root.setCenter(vBox);
//            
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        try {    
//
//                            Thread.sleep(5000);
//                        } catch (Exception ex) {
//                            JDepuracion.anadirTexto("", ex);
//                        }
//
//
//                        moList.addNew();
//                        moList.getFields(0).setValue(1000);
//                        moList.getFields(1).setValue("1/1/2011");
//                        moList.getFields(2).setValue("Cadena1000");
//                        moList.getFields(3).setValue(true);
//                        moList.getFields(4).setValue(100.1010101);
//                        moList.update(false);
//                        
//                        moList.moveFirst();
//                        moList.getFields(0).setValue("1111");
//                        moList.update(false);
//                        moList.moveNext();
//                        moList.borrar(false);
//                        moList.moveNext();
//                        moList.borrar(false);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                try{
//                                tableView.refrescar();
//                                } catch (Exception ex) {
//                        JDepuracion.anadirTexto("", ex);
//                    }
//                            }
//                        });
//                        
//                    } catch (Exception ex) {
//                        JDepuracion.anadirTexto("", ex);
//                    }
//                    
//                }
//            }).start();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }
 
    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}
