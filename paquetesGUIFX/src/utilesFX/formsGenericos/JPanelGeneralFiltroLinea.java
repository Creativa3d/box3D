/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import ListDatos.JListDatos;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import utiles.JConversiones;
import utilesFX.JTableViewCZ;
import utilesGUIx.formsGenericos.JPanelGeneralFiltroModelo;

/**
 *
 * @author eduardo
 */
public class JPanelGeneralFiltroLinea implements ListChangeListener, ChangeListener<Number>, EventHandler<KeyEvent>{


    
    private ChangeListener<Boolean> moChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1.booleanValue()){

                }else{
                    focusLost();
                }
            }
        };
    private final EventHandler<ScrollEvent> moChangeScroll = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent t) {

            if(jTableFiltroRapido.getHorizontalScrollbar()!=null){
                jTableFiltroRapido.getHorizontalScrollbar().setValue(jTableDatos.getHorizontalScrollbar().getValue());
            }
            
        }
    };
    private final EventHandler<MouseEvent> moMouseScroll = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moChangeScroll.handle(null);
            }
        };
    
    private final EventHandler<KeyEvent> moKeyScroll= new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                moChangeScroll.handle(null);
            }
        };
    private final JTableViewCZ jTableFiltroRapido;
    
    private JTableViewCZ jTableDatos;
    
    private JListDatos moFiltroRapido;
    
    private boolean mbAnularSetLong = false;
    private boolean mbInicializado=false;    
    private JPanelGeneralFiltroModelo moFiltro;
    private TableColumn moColumnaDefecto;
        
    public JPanelGeneralFiltroLinea(JTableViewCZ poTabla){
        jTableFiltroRapido = poTabla;

        jTableFiltroRapido.getStylesheets().add(
                this.getClass().getResource(
                    "/utilesFX/formsGenericos/JTableFiltroRapido.css" ).toExternalForm()
        );

        jTableFiltroRapido.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        jTableFiltroRapido.getSelectionModel().setCellSelectionEnabled(true);
        jTableFiltroRapido.setEditable(true);
        jTableFiltroRapido.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    // Get the table header
                    Pane header = (Pane)jTableFiltroRapido.lookup("TableHeaderRow");
                    if(header!=null && header.isVisible()) {
                      header.setMaxHeight(0);
                      header.setMinHeight(0);
                      header.setPrefHeight(0);
                      header.setVisible(false);
                      header.setManaged(false);
                    }
                }
            });

        jTableFiltroRapido.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if(jTableDatos.getHorizontalScrollbar()!=null){
                    jTableDatos.getHorizontalScrollbar().setValue(
                            jTableFiltroRapido.getHorizontalScrollbar().getValue()
                    );
                }
            }
        });
        
        jTableFiltroRapido.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent evt) {

                if(evt.getCode() == evt.getCode().DOWN){
                    jTableDatos.requestFocus();
                }

            }
        });
                

    }
    
    public JTableViewCZ getTable(){
        return jTableDatos;
    }
    public boolean isAnularSetLong() {
        return mbAnularSetLong;
    }

    public void setAnularSetLong(boolean mbAnularSetLong) {
        this.mbAnularSetLong = mbAnularSetLong;
        if(!mbAnularSetLong){
            setLongsTableFiltroRapido(true, true);
        }
    }
    public void setComponentes(final JTableViewCZ poTableDatos){
        jTableDatos = poTableDatos;
        
    }
    
    public void setDatos(JPanelGeneralFiltroModelo panelGeneralFiltro1, String psCampo) throws Exception {
        mbInicializado=true;
        moFiltro = panelGeneralFiltro1;
        
        jTableDatos.getColumns().removeListener(this);
        jTableDatos.getColumns().addListener(this);
        
        for(Object loColumn :  jTableDatos.getColumns()){
            ((TableColumn)loColumn).widthProperty().removeListener(this);
            ((TableColumn)loColumn).widthProperty().addListener(this);
        }
        jTableDatos.removeEventHandler(KeyEvent.KEY_PRESSED, this);
        jTableDatos.addEventHandler(KeyEvent.KEY_PRESSED, this);
        
        jTableDatos.focusedProperty().removeListener(moChangeListener);
        jTableDatos.focusedProperty().addListener(moChangeListener);
      
        jTableDatos.removeEventHandler(ScrollEvent.ANY, moChangeScroll);
        jTableDatos.removeEventHandler(MouseEvent.ANY, moMouseScroll);
        jTableDatos.removeEventHandler(KeyEvent.ANY, moKeyScroll);
        jTableDatos.addEventFilter(ScrollEvent.ANY, moChangeScroll);
        jTableDatos.addEventFilter(MouseEvent.ANY, moMouseScroll);
        jTableDatos.addEventFilter(KeyEvent.ANY, moKeyScroll);
        

        
//ojo hay q poner un metodo modular para poderlo quitar        
//        jTableDatos.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener() {
//            @Override
//            public void onChanged(Change change) {
//            try{
//                if(jTableDatos.getSelectionModel().getSelectedCells().size()>0){
//                    TablePosition loPosi = (TablePosition) jTableDatos.getSelectionModel().getSelectedCells().get(0);
//                    jTableFiltroRapido.getSelectionModel().clearAndSelect(0, JTablaConfig.getTableColumn(jTableDatos, loPosi.getTableColumn().getId()));
//                }
//            }catch(Exception e1){}
//            }
//        });
        
        if(JConversiones.isNumeric(psCampo)){
            moColumnaDefecto =  (TableColumn)jTableFiltroRapido.getColumns().get(JTablaConfig.getIndiceColumna(jTableFiltroRapido, psCampo));
        }

        moFiltroRapido = panelGeneralFiltro1.getFiltroPorCampo();
        

        jTableFiltroRapido.setModel(moFiltroRapido);
        
        
        setLongsTableFiltroRapido(true, true);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                jTableFiltroRapido.requestFocus();
                jTableFiltroRapido.getSelectionModel().clearAndSelect(0, moColumnaDefecto);
                moColumnaDefecto=null;
            }
        });
    }
    public void setLongsTableFiltroRapido(boolean pbOrden, boolean pbWidth){
        if(!isAnularSetLong() && jTableDatos != null && isVisible()){
            try{
                if(pbOrden){
                    List<TableColumn> loColumnsAux = new ArrayList<TableColumn>();

                    for(int i = 0; i< jTableDatos.getColumns().size(); i++){
                        TableColumn loCol = (TableColumn) jTableDatos.getColumns().get(i);
                        loColumnsAux.add((TableColumn)jTableFiltroRapido.getColumns().get(JTablaConfig.getIndiceColumna(jTableFiltroRapido, loCol.getId())));
                    }
                    jTableFiltroRapido.getColumns().clear();
                    jTableFiltroRapido.getColumns().addAll(loColumnsAux);
                }
                if(pbWidth){
                    for(int i = 0; i< jTableDatos.getColumns().size(); i++){
                        JTablaConfig.setLongColumna(
                                ((TableColumn)jTableFiltroRapido.getColumns().get(i)), 
                                ((TableColumn)jTableDatos.getColumns().get(i)).getWidth()
                                );
                    }
                }
            }catch(Exception e){

            }
        }
    }

    public boolean  isVisible(){
        return jTableFiltroRapido.isVisible();
    }
    public void setVisible(boolean aFlag) {
        jTableFiltroRapido.setVisible(aFlag);
        if(aFlag){
            setLongsTableFiltroRapido(true, true);
        }
    }
    @Override
    public void handle(KeyEvent t) {
        if(mbInicializado){
//ojo            
//        if(mbInicializado){
//            if(e.getSource() == jTableDatos && isVisible()){
//                String lsValor=null;
//                if(
//                   (e.getKeyChar() >= 'a' &&  e.getKeyChar() <= 'z') ||
//                   (e.getKeyChar() >= 'A' &&  e.getKeyChar() <= 'Z') ||
//                   (e.getKeyChar() >= '0' &&  e.getKeyChar() <= '9') ||
//                   e.getKeyChar() == '/' ||  e.getKeyChar() == '\\' ||
//                   e.getKeyChar() == ',' ||  e.getKeyChar() == '.' ||
//                   e.getKeyChar() == '-' ||  e.getKeyChar() == '_' ||
//                   e.getKeyChar() == ';'  ||  e.getKeyChar() == ':'    
//                ){
//                    lsValor =String.valueOf(e.getKeyChar());
//                }else{
//                    if(
//                       e.getKeyChar() == e.VK_BACK_SPACE ||  e.getKeyChar() == e.VK_DELETE
//                    ){
//                        lsValor ="";
//                    }
//                }
//
//                if(lsValor !=null){
//                    int lColumn = jTableDatos.getSelectedColumn();
//                    int lColumnReal = jTableDatos.getColumnModel().getColumn(jTableDatos.getSelectedColumn()).getModelIndex();
//                    jTableFiltroRapido.getModel().setValueAt( lsValor ,0, lColumnReal);
//                    jTableFiltroRapido.setColumnSelectionInterval(lColumn,lColumn);
//                    jTableFiltroRapido.setEditingColumn(lColumn);
//                    jTableFiltroRapido.requestFocus();
//
//    //                KeyEvent loEvent  = new KeyEvent(
//    //                        jTableFiltroRapido, 
//    //                        e.KEY_RELEASED, e.getWhen(), 0, 
//    //                        e.getKeyCode(), e.getKeyChar(), e.KEY_LOCATION_UNKNOWN
//    //                        );
//                    jTableFiltroRapido.editCellAt(0, lColumn);
//
//                }
//            }
//        }

        }


    }        


    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        if(mbInicializado){
            setLongsTableFiltroRapido(false, true);
        }
    }

    public void focusLost() {
        if(mbInicializado){
//            setLongsTableFiltroRapido(true, true);
        }
    }
    @Override
    public void onChanged(Change change) {
        if(mbInicializado){
            setLongsTableFiltroRapido(true, false);

        }   
    }




    public void requestFocus() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                jTableFiltroRapido.requestFocus();
            }
        });
        
    }

    
}
