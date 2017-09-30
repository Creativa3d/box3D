/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import ListDatos.BusquedaEvent;
import ListDatos.IBusquedaListener;
import ListDatos.IFilaDatos;
import ListDatos.IListDatosEdicion;
import ListDatos.IResultado;
import ListDatos.JListDatos;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import utilesGUIx.ITableCZColores;

/**
 *
 * @author eduardo
 */
public class JTableViewCZ extends TableView {

    protected ITableCZColores moColores;
    protected IListDatosEdicion moListEdicion = null;
    protected JListDatos moList;
    protected IBusquedaListener moListRecuperar;

    public JTableViewCZ() {
        super();
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (isEditable()) {
                            if (!(t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB
                                    || (t.getCode() == KeyCode.UP) || (t.getCode() == KeyCode.DOWN)
                                    || (t.getCode() == KeyCode.LEFT) || (t.getCode() == KeyCode.RIGHT))
                                && getSelectionModel().getSelectedCells().size()>0
                                ) {
                                TablePosition tablePosition = (TablePosition) getSelectionModel().getSelectedCells().get(0);
                                edit(tablePosition.getRow(), tablePosition.getTableColumn());
                            }
                            if (t.getCode() == KeyCode.ENTER && getSelectionModel().getSelectedCells().size()>0) {
                                TablePosition tablePosition = (TablePosition) getSelectionModel().getSelectedCells().get(0);
                                
                                if(editingCellProperty().getValue()!= null && (editingCellProperty().getValue() instanceof TableCellWebViewConColor)){
                                    //html edit chapuza
                                } else {
                                    TableColumn nextColumn = getNextColumn(JTableViewCZ.this, true, tablePosition.getTableColumn());
                                    if (nextColumn != null) {
                                        int lIndex = getColumns().indexOf(nextColumn);
                                        if (lIndex <= tablePosition.getColumn()) {
                                            getSelectionModel().clearAndSelect(tablePosition.getRow() + 1, nextColumn);
                                        } else {
                                            getSelectionModel().clearAndSelect(tablePosition.getRow(), nextColumn);
                                        }
                                    }
                                    t.consume();
                                }
                            }
                        }
                    }
                });
    }

    public void setModel(JListDatos poList) throws Exception {
        setModel(poList, FXCollections.observableArrayList(poList));
    }

    protected void asignarEventos(){

        moListEdicion = new IListDatosEdicion() {
            @Override
            public void edicionDatos(final int plModo, final int plIndex, final IFilaDatos poDatos) {

                if (Platform.isFxApplicationThread()) {
                    edicionDatosInterno(plModo, plIndex, poDatos);
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            edicionDatosInterno(plModo, plIndex, poDatos);
                        }
                    });
                }
            }

            @Override
            public void edicionDatosAntes(int plModo, int plIndex) throws Exception {
            }
        };
        moListRecuperar = new IBusquedaListener() {
            @Override
            public void recuperacionDatosTerminada(BusquedaEvent e) {
                if(Platform.isFxApplicationThread()){
                    reengancharTabla(moList, FXCollections.observableArrayList(moList));
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            reengancharTabla(moList, FXCollections.observableArrayList(moList));
                        }
                    });
                }
                
            }
        };
                
        moList.addListenerEdicion(moListEdicion);
        moList.addListener(moListRecuperar);
                
    }
    public void setModel(JListDatos poList, ObservableList<IFilaDatos> poListObservable) throws Exception {
        //ojo antes de la asignacion del nuevo molist
        if (moListEdicion != null) {
            moList.removeListenerEdicion(moListEdicion);
            moList.removeListener(moListRecuperar);
        }        
        moList = poList;
        asignarEventos();
        getColumns().clear();
        layout();

        for (int i = 0; i < poList.getFields().size(); i++) {

            TableColumn filaCol = new TableColumn(poList.getFields().get(i).getCaption());
            filaCol.setCellFactory(new TableCellFactoryConColor(poList, i, this));
            filaCol.setCellValueFactory(new TableCellValue(poList, i));
            filaCol.setOnEditCommit(new EditCommitListDatos(this, poList, i));
            filaCol.setId(String.valueOf(i));
            filaCol.setEditable(poList.getFields(i).isEditable());
            getColumns().add(filaCol);
        }

        reengancharTabla(poList, poListObservable);
    }
    
    protected void reengancharTabla(JListDatos poList, ObservableList<IFilaDatos> poListObservable){
        getItems().clear();

        
        setItems(poListObservable);
        if (poList.size() > 0) {
            getSelectionModel().select(0);
        }
        
    }

    public void edicionDatosInterno(int plModo, int plIndex, IFilaDatos poDatos) {
        if (plModo == JListDatos.mclNuevo) {
            getItems().add(poDatos);
        }
        if (plModo == JListDatos.mclBorrar) {
            getItems().remove(poDatos);
        }
        if (plModo == JListDatos.mclEditar || plModo == JListDatos.mclNada) {
            int l = getItems().indexOf(poDatos);
            getItems().set(l, poDatos);
        }
    }

    public void setTableCZColores(final ITableCZColores poColores) {
        moColores = poColores;
    }

    public ITableCZColores getTableCZColores() {
        return moColores;
    }

    public void refrescar() {
        if(getItems()!=null && moList!=null && getItems().size()!=moList.size()){
            getItems().clear();
            layout();
            setItems(FXCollections.observableArrayList(moList));
        } else {
            try{
                refresh();
            }catch(Throwable e){}       
        }
    }

    public int getIndexModelo(int plIndexTabla) {
        return moList.indexOf(getItems().get(plIndexTabla));
    }
    
    public JListDatos getListDatos(){
        return moList;
    }

    /**
     *
     * @param forward true gets the column to the right, false the column to the
     * left of the current column
     * @return
     */
    public static TableColumn getNextColumn(TableView poTableView, boolean forward, TableColumn poColumn) {
        List columns = new ArrayList();
        for (Object column : poTableView.getColumns()) {
            columns.addAll(getLeaves((TableColumn) column));
        }
        //There is no other column that supports editing.
        if (columns.size() < 2) {
            return null;
        }
        int currentIndex = columns.indexOf(poColumn);
        int nextIndex = currentIndex;
        if (forward) {
            nextIndex++;
            if (nextIndex > columns.size() - 1) {
                nextIndex = 0;
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
            }
        }
        return (TableColumn) columns.get(nextIndex);
    }

    public static List<TableColumn> getLeaves(TableColumn root) {
        List columns = new ArrayList();
        if (root.getColumns().isEmpty()) {
            //We only want the leaves that are editable.
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        } else {
            for (Object column : root.getColumns()) {
                columns.addAll(getLeaves((TableColumn) column));
            }
            return columns;
        }
    }

    public ScrollBar getVerticalScrollbar() {
        ScrollBar result = null;
        for (Node n : this.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }       
        return result;
    }
            
    public ScrollBar getHorizontalScrollbar() {
        ScrollBar result = null;
        for (Node n : this.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.HORIZONTAL)) {
                    result = bar;
                }
            }
        }       
        return result;
    }
            
}

class EditCommitListDatos implements EventHandler<TableColumn.CellEditEvent<IFilaDatos, String>> {

    private final JListDatos moList;
    private final int mlCol;
    private final JTableViewCZ moTable;

    EditCommitListDatos(JTableViewCZ poTable, JListDatos poList, int plCol) {
        moList = poList;
        mlCol = plCol;
        moTable = poTable;
    }

    @Override
    public void handle(CellEditEvent<IFilaDatos, String> t) {
        try {
            moList.setIndex(moTable.getIndexModelo(t.getTablePosition().getRow()));
            moList.getFields(mlCol).setValue(t.getNewValue());
            IResultado loResult = moList.update(true);
            if (!loResult.getBien()) {
                throw new Exception(loResult.getMensaje());
            }
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }
    }
}

class TableCellFactoryConColor implements Callback<TableColumn, TableCell> {

    private final int mlCol;
    private final JListDatos moTabla;
    private final JTableViewCZ moTableView;

    TableCellFactoryConColor(final JListDatos poTabla, final int plCol, JTableViewCZ poTableView) {
        moTabla = poTabla;
        mlCol = plCol;
        moTableView = poTableView;
    }

    @Override
    public TableCell call(TableColumn param) {
        TableCell cell;
        if (moTabla.getFields(mlCol).getTipo() == JListDatos.mclTipoBoolean) {
            cell = new TableCellBooleanConColor(moTabla, mlCol, moTableView);
        } else {
            cell = new TableCellConColor(moTabla, mlCol, moTableView);
        }
        return cell;

    }
}
