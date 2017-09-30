/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utiles.JDepuracion;
import utilesGUIx.ColorCZ;

/**
 *
 * @author eduardo
 */
public class TableCellBooleanConColor  extends CheckBoxTableCell {

    private JFieldDef moField;
    private JTableViewCZ moTable;
    private final Pos mlAlig = Pos.BASELINE_CENTER;
    private int mlColumn;
    private CheckBox chekBox;
    private JFieldConCheckBox moCheckBoxConField;

    
    public TableCellBooleanConColor(final JListDatos poTabla, int plColumn, JTableViewCZ poTable) {
        try {
            mlColumn = plColumn;
            moField = poTabla.getFields(plColumn).Clone();
            moTable = poTable;
            

            moTable.getSelectionModel()
                        .getSelectedItems()
                        .addListener(new ListChangeListener<Object>() {
                    @Override
                    public void onChanged(Change<? extends Object> change) {
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
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    
    private void createCheckBox() {
        try {
            chekBox = new CheckBox("");
            chekBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            chekBox.setOnMouseClicked((MouseEvent e)->{
                if(!isEditing() && isEditable() && getTableView().isEditable()){
                    startEdit();
                }
            });
            chekBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    try {
                        if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB 
                                || (t.getCode() == KeyCode.UP) || (t.getCode() == KeyCode.DOWN) 
                                || (t.getCode() == KeyCode.LEFT) || (t.getCode() == KeyCode.RIGHT)
                                ) {               
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

            moCheckBoxConField = new JFieldConCheckBox(chekBox, true);
            moCheckBoxConField.setField(moField);
            
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }
    }
    @Override
    public void commitEdit(Object newValue) {
        try{
            moCheckBoxConField.establecerDatosBD();
            super.commitEdit(moField.getValue()); 
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }        
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (moCheckBoxConField == null) {
            createCheckBox();
        }
        IFilaDatos loFila = (IFilaDatos) moTable.getSelectionModel().getSelectedItem();
        if(loFila!=null){
            try {
                moField.setValue(loFila.msCampo(getColumn()));
            } catch (ECampoError ex) {
                JDepuracion.anadirTexto(TableCellWebViewConColor.class.getName(), ex);
            }
        }
        moCheckBoxConField.mostrarDatosBD();        
        setGraphic(chekBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        chekBox.requestFocus();
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
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (moCheckBoxConField != null) {
                    try {
                        moField.setValue(item);
                        moCheckBoxConField.mostrarDatosBD();
                    } catch (ECampoError ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                }
                setGraphic(chekBox);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                
                setAlignment(mlAlig);
                
                colores(item, empty);

                
                        
            }
        }        
        if(chekBox!=null){
            chekBox.disableProperty().bind(Bindings.not(
                    getTableView().editableProperty().and(
                    getTableColumn().editableProperty()).and(
                    editableProperty())
                ));
        }
    }
    
    private void colores(Object item, boolean empty){
                JTableViewCZ loTable = moTable;
                if (getTableView() != null && JTableViewCZ.class.isAssignableFrom(getTableView().getClass())) {
                    loTable = (JTableViewCZ) getTableView();
                }
//colores
                if (loTable != null && loTable.getTableCZColores() != null) {
//                    boolean isSelectedNow = isSelected();
//                    if (getTableView().getSelectionModel() != null) {
//                        isSelectedNow = getTableView().getSelectionModel().isSelected(getIndex(), getTableColumn());
//                    }
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
