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
import java.text.DecimalFormat;
import java.text.Format;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.ColorCZ;

/**
 *
 * @author eduardo
 */
public class TableCellConColor  extends TableCell {

    private JFieldDef moField;
    private Format formatter = null;
    private JTableViewCZ moTable;
    private Pos mlAlig = Pos.BASELINE_LEFT;
    private int mlColumn;


    //edit
    private TextField textField;
    private JFieldConTextField moTextFieldConField;
    
    public TableCellConColor(final JListDatos poTabla, int plColumn, JTableViewCZ poTable) {
        try {
            mlColumn = plColumn;
            moField = poTabla.getFields(plColumn).Clone();
            moTable = poTable;
            

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
            if (moField.isNumerico()) {
                mlAlig = Pos.BASELINE_RIGHT;
            }
            if (moField.getTipo() == JListDatos.mclTipoBoolean) {
                mlAlig = Pos.BASELINE_CENTER;
            }
            if (moField.getTipo() == JListDatos.mclTipoNumeroDoble && formatter == null) {
                formatter = new DecimalFormat("###,###,###,###,###.############");
            }
            if (moField.getTipo() == JListDatos.mclTipoMoneda3Decimales && formatter == null) {
                formatter = new DecimalFormat("###,###,###,###,##0.000 \u00A4");
            }
            if (moField.getTipo() == JListDatos.mclTipoMoneda && formatter == null) {
                formatter = new DecimalFormat("###,###,###,###,##0.00 \u00A4");
            }
            if (moField.getTipo() == JListDatos.mclTipoPorcentual3Decimales && formatter == null) {
                formatter = new DecimalFormat("#,##0.000 %");
                ((DecimalFormat) formatter).setMultiplier(1);
            }
            if (moField.getTipo() == JListDatos.mclTipoPorcentual && formatter == null) {
                formatter = new DecimalFormat("#,##0.00 %");
                ((DecimalFormat) formatter).setMultiplier(1);
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    public void setFormat(Format poFormat) {
        formatter = poFormat;
    }

    
    private synchronized void createTextField() {
        try {
            textField = new TextField();
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    try {
                        if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB 
                                || (t.getCode() == KeyCode.UP) || (t.getCode() == KeyCode.DOWN) 
//                                || (t.getCode() == KeyCode.LEFT) || (t.getCode() == KeyCode.RIGHT)
                                ) {               
//                            moTextFieldConField.establecerDatosBD();
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
                                    if(lIndex<mlColumn && (getTableRow().getIndex()+1)<getTableView().getItems().size() ){
                                        getTableView().getSelectionModel().clearAndSelect(getTableRow().getIndex()+1, nextColumn);
                                        getTableView().requestFocus();
                                    }else if(getTableRow().getIndex()>=0 && lIndex>=mlColumn) {
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
            moTextFieldConField = new JFieldConTextField(textField, true);
            moTextFieldConField.setField(moField);
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }
    }

    @Override
    public void commitEdit(Object newValue) {
        try{
            moTextFieldConField.establecerDatosBD();
            super.commitEdit(moField.getValue()); 
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }        
    }
    

    @Override
    public void startEdit() {
        super.startEdit();

        if (moTextFieldConField == null) {
            createTextField();
        }
        IFilaDatos loFila = (IFilaDatos) getTableView().getSelectionModel().getSelectedItem();
        if(loFila!=null){
            try {
                moField.setValue(loFila.msCampo(getColumn()));
            } catch (ECampoError ex) {
                JDepuracion.anadirTexto(TableCellWebViewConColor.class.getName(), ex);
            }
        }
        moTextFieldConField.mostrarDatosBD();
        
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        updateItem(getLabel(getItem()), false);
    }
    private int getIndexModelo(){
        try{
            return ((JTableViewCZ)getTableView()).getIndexModelo(getIndex());
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
                    if (moTextFieldConField != null) {
                        try {
                            moField.setValue(item);
                            moTextFieldConField.mostrarDatosBD();
                        } catch (ECampoError ex) {
                            JDepuracion.anadirTexto(getClass().getName(), ex);
                        }
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    

                    setAlignment(mlAlig);
                    colores(item,empty);
                    if (item != null) {
                        setText(getLabel(item));
//                        JDepuracion.anadirTexto(getClass().getName(), String.valueOf(getIndex()) + "," + String.valueOf(getColumn()) + ",isSelected=" + isSelected() + "->" + item.toString());
                    } else {
                        setText("");
                    }           
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }            
        }
    }

    private void colores(Object item, boolean empty) {
//colores
        JTableViewCZ loTable = (JTableViewCZ) getTableView();
        if (getTableView() != null && JTableViewCZ.class.isAssignableFrom(getTableView().getClass())) {
            loTable = (JTableViewCZ) getTableView();
        }
        String lsStyle = "";
        if (loTable != null && loTable.getTableCZColores() != null) {
            //AQUI NO SE USA LA SELECCION
//                        boolean isSelectedNow = isSelected();
//                        if (getTableView().getSelectionModel() != null) {
//                            isSelectedNow = getTableView().getSelectionModel().isSelected(getIndex(), getTableColumn());
//                        }
            boolean isSelectedNow = false;//por ahora todo desactivado
            ColorCZ loColor
                    = loTable.getTableCZColores().getColorBackground(
                            item, isSelectedNow, isFocused(), getIndexModelo(), getColumn());
            if (loColor != null) {
                String hexColor = String.format("#%06X", (0xFFFFFF & loColor.getColor()));
                lsStyle += ("-fx-background-color:" + hexColor + ";");
             }
            loColor = loTable.getTableCZColores().getColorForeground(
                    item, isSelectedNow, isFocused(), getIndexModelo(), getColumn());
            if (loColor != null) {
                String hexColor = String.format("#%06X", (0xFFFFFF & loColor.getColor()));
                lsStyle += ("-fx-text-fill: " + hexColor + ";");
//                            setTextFill(JFXConfigGlobal.toColor(loColor));
            }
        }
        if(JCadenas.isVacio(lsStyle) && !getTableColumn().isEditable() && getTableView().isEditable()){
            lsStyle = ("-fx-background-color:" + JFXConfigGlobal.toRGBCode(Color.gray(0.8)) + ";");
        }
        setStyle(lsStyle);    
    }
    private String getLabel(Object value) {
        try {
            moField.setValue(value);
            value = moField.getValue();
            if (formatter == null) {
                return ((value == null) ? "" : value.toString());
            } else {
                if (value != null && JDateEdu.class.isAssignableFrom(value.getClass())) {
                    return formatter.format(((JDateEdu) value).moDate());
                } else {
                    return ((value == null) ? "" : formatter.format(value));
                }
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            return ((value == null) ? "" : value.toString());
        }
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
    
 /**
     *
     * @param forward true gets the column to the right, false the column to the left of the current column
     * @return
     */
    private TableColumn getNextColumn(boolean forward) {
        return JTableViewCZ.getNextColumn(getTableView(), forward, getTableColumn());
    }

}

