/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utiles.JDepuracion;
import utilesGUIx.ColorCZ;

/**
 *
 * @author eduardo
 */
public class TableCellComboBox extends TableCell {

    private JFieldDef moField;
    private JTableViewCZ moTable;
    private int mlColumn;


    //edit
    private ComboBox comboBox;
    private JFieldConComboBox moComboBoxConField;
    private JListDatos moRelleno;
    private int[] malDescrips;
    private int[] malCods;
    
    private boolean mbActivarCambiosEdit = false;
    
    public TableCellComboBox(final JListDatos poTabla, int plColumn, JTableViewCZ poTable, JListDatos poRelleno,int[] palDescrips, int[] palCods) {
        try {
            mlColumn = plColumn;
            moField = poTabla.getFields(plColumn).Clone();
            moTable = poTable;
            moRelleno=poRelleno;
            malDescrips=palDescrips;
            malCods=palCods;
            
            //fila seleccionada
            moTable.getSelectionModel()
                        .getSelectedItems()
                        .addListener(new ListChangeListener<Object>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Object> change) {
                        while (change.next()) {
                            if (getTableRow() != null) {
                                if (change.wasRemoved() && change.getRemoved().contains(getTableRow().getItem())) {
                                    colores(getTableRow().getItem(), false);
                                }
                                if (change.wasAdded() && change.getList().contains(getTableRow().getItem())) {
                                    setStyle("");
                                }
                            }
                        }                    
                    }
                });                  
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    private void createComboBox() {
        try {
            comboBox = new ComboBox();
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.setOnAction(new EventHandler() {

                @Override
                public void handle(Event t) {
                    if(mbActivarCambiosEdit ){
                            commitEdit(null);
                            updateItem(getItem(), false);
                        }
                    }
            });
            comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    try {
                        if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB 
//                                || (t.getCode() == KeyCode.UP) || (t.getCode() == KeyCode.DOWN) -> sirven para moverse por valores combobox
                                || (t.getCode() == KeyCode.LEFT) || (t.getCode() == KeyCode.RIGHT)
                                ) {    
                                mbActivarCambiosEdit=false;
                                commitEdit(null);
                                updateItem(getItem(), false);
                                if((t.getCode() == KeyCode.UP) && getTableRow().getIndex()>0){
                                    getTableView().getSelectionModel().clearAndSelect(getTableRow().getIndex()-1, getTableColumn());
                                }else if((t.getCode() == KeyCode.DOWN) && (getTableRow().getIndex()+1)<getTableView().getItems().size()){
                                    getTableView().getSelectionModel().clearAndSelect(getTableRow().getIndex()+1, getTableColumn());
                                }else{
                                    boolean forward = !t.isShiftDown();
                                    if(forward){
                                        forward = !(t.getCode() == KeyCode.LEFT);
                                    }
                                    TableColumn nextColumn = getNextColumn(forward);
                                    if (nextColumn != null) {
                                        int lIndex = getTableView().getColumns().indexOf(nextColumn);
                                        if(lIndex<getColumn() && (getTableRow().getIndex()+1)<getTableView().getItems().size() ){
                                            getTableView().getSelectionModel().clearAndSelect(getTableRow().getIndex()+1, nextColumn);
                                            getTableView().requestFocus();
                                        }else if(getTableRow().getIndex()>=0 && lIndex>=getColumn()) {
                                            getTableView().getSelectionModel().clearAndSelect(getTableRow().getIndex(), nextColumn);
                                            getTableView().requestFocus();
                                        }

                                    }                                
                                }
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    } catch (Throwable ex) {
                        JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
                    }
                }
            });
            mbActivarCambiosEdit=false;
            moComboBoxConField = new JFieldConComboBox(comboBox, true);
            moComboBoxConField.setField(moField);
            moComboBoxConField.RellenarCombo(moRelleno, malDescrips, malCods);
            
            IFilaDatos loFila = (IFilaDatos) moTable.getSelectionModel().getSelectedItem();
            if(loFila!=null){
                moField.setValue(loFila.msCampo(getColumn()));
            }
            
            
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }
    }
    @Override
    public void commitEdit(Object newValue) {
        try{
            moComboBoxConField.establecerDatosBD();
            super.commitEdit(moField.getValue()); 
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }        
    }
 
    @Override
    public void startEdit() {
        super.startEdit();
        mbActivarCambiosEdit=false;
        if (moComboBoxConField == null) {
            createComboBox();
        }

        IFilaDatos loFila = (IFilaDatos) getTableView().getSelectionModel().getSelectedItem();
        if(loFila!=null){
            try {
                moField.setValue(loFila.msCampo(getColumn()));
            } catch (ECampoError ex) {
                JDepuracion.anadirTexto(TableCellWebViewConColor.class.getName(), ex);
            }
        } 
        moComboBoxConField.mostrarDatosBD();
        setGraphic(comboBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                comboBox.requestFocus(); 
                comboBox.show();
                mbActivarCambiosEdit=true;
            }
        });        
        
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        updateItem(getLabel(getItem()), false);
    }
    private int getIndexModelo(){
        try{
            return moTable.getIndexModelo(getIndex());
        }catch(Exception e){
            return -1;
        }
    }
    @Override
    public void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        setStyle("");
        if (getIndex() >= 0) {
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (moComboBoxConField != null) {
                        try {
                            moField.setValue(item);
                            moComboBoxConField.mostrarDatosBD();
                        } catch (ECampoError ex) {
                            JDepuracion.anadirTexto(getClass().getName(), ex);
                        }
                    }
                    setGraphic(comboBox);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    colores(item, empty);
                    if (item != null) {
                        setText(getLabel(item));
//                        JDepuracion.anadirTexto(getClass().getName(), String.valueOf(getIndex()) + "," + String.valueOf(mlColumn) + ",isSelected=" + isSelected() + "->" + item.toString());
                    } else {
                        setText("");
                    }           
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }            
        }
    }

    private void colores(Object item, boolean empty) {
        JTableViewCZ loTable = moTable;
        if (getTableView() != null && JTableViewCZ.class.isAssignableFrom(getTableView().getClass())) {
            loTable = (JTableViewCZ) getTableView();
        }

        //colores
        if (loTable != null && loTable.getTableCZColores() != null) {
//                        boolean isSelectedNow = isSelected();
//                        if (getTableView().getSelectionModel() != null) {
//                            isSelectedNow = getTableView().getSelectionModel().isSelected(getIndex(), getTableColumn());
//                        }
            boolean isSelectedNow = false;//por ahora todo desactivado
            String lsStyle = "";
            ColorCZ loColor
                    = loTable.getTableCZColores().getColorBackground(item, isSelectedNow, isFocused(), getIndexModelo(), getColumn());
            if (loColor != null) {
                String hexColor = String.format("#%06X", (0xFFFFFF & loColor.getColor()));
                lsStyle += ("-fx-background-color:" + hexColor + ";");
            }
            loColor = loTable.getTableCZColores().getColorForeground(item, isSelectedNow, isFocused(), getIndexModelo(), getColumn());
            if (loColor != null) {
                String hexColor = String.format("#%06X", (0xFFFFFF & loColor.getColor()));
                lsStyle += ("-fx-text-fill: " + hexColor + ";");
            }
            setStyle(lsStyle);

        }    
    }
    private String getLabel(Object value) {
        try {
            moField.setValue(value);
            value = moField.getValue();
            return ((value == null) ? "" : value.toString());
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            return ((value == null) ? "" : value.toString());
        }
    }
 /**
     *
     * @param forward true gets the column to the right, false the column to the left of the current column
     * @return
     */
    private TableColumn getNextColumn(boolean forward) {
        return JTableViewCZ.getNextColumn(getTableView(), forward, getTableColumn());
    }

    /**
     * @return the mlColumn
     */
    public int getColumn() {
        return mlColumn;
    }

    /**
     * @param mlColumn the mlColumn to set
     */
    public void setColumn(int mlColumn) {
        this.mlColumn = mlColumn;
    }

}
