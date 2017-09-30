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
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesGUIx.ColorCZ;

/**
 *
 * @author eduardo
 */
public class TableCellWebViewConColor  extends TableCell {

    //fuente
    private JFieldDef moField;
    private JTableViewCZ moTable;
    private int mlColumn;
    //view
    private WebView webView = new WebView();    
    private ImageView imageView = new ImageView();
    
    //edit
    private HTMLEditor htmlEditor;
    private JFieldConHTMLEditor moHTMLEditConField;
    
    public TableCellWebViewConColor(final JListDatos poTabla, int plColumn, JTableViewCZ poTable) {
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
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }
    
    private synchronized void createHTMLEditor() {
        try {
            htmlEditor = new HTMLEditor();
            htmlEditor.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            htmlEditor.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    try {
                        if (t.getCode() == KeyCode.TAB 
                                || (t.getCode() == KeyCode.UP) || (t.getCode() == KeyCode.DOWN) 
//                                || (t.getCode() == KeyCode.LEFT) || (t.getCode() == KeyCode.RIGHT)
                                )  {               
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
                        } else if (t.getCode() == KeyCode.ENTER) {
                            t.consume();
                        }
                    } catch (Throwable ex) {
                        JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
                    }
                }
            });
            moHTMLEditConField = new JFieldConHTMLEditor(htmlEditor, true);
            moHTMLEditConField.setField(moField);
            
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }
    }

    @Override
    public void commitEdit(Object newValue) {
        try{
            moHTMLEditConField.establecerDatosBD();
            super.commitEdit(moField.getValue()); 
        } catch (Throwable ex) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, ex, null);
        }        
    }
    

    @Override
    public void startEdit() {
        super.startEdit();

        if (moHTMLEditConField == null) {
            createHTMLEditor();
        }
        IFilaDatos loFila = (IFilaDatos) getTableView().getSelectionModel().getSelectedItem();
        if(loFila!=null){
            try {
                moField.setValue(loFila.msCampo(getColumn()));
            } catch (ECampoError ex) {
                JDepuracion.anadirTexto(TableCellWebViewConColor.class.getName(), ex);
            }
        }
        moHTMLEditConField.mostrarDatosBD();        
        setGraphic(htmlEditor);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        htmlEditor.requestFocus();
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
                    if (moHTMLEditConField != null) {
                        try {
                            moField.setValue(item);
                            moHTMLEditConField.mostrarDatosBD();
                        } catch (ECampoError ex) {
                            JDepuracion.anadirTexto(getClass().getName(), ex);
                        }
                    }
                    setGraphic(htmlEditor);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    
                    if (item != null) {
                        webView.getEngine().loadContent(item.toString());
                        
                        double ldHeight = (item.toString().length()/70.0)*23.0;
                        if(ldHeight<=50){
                            ldHeight=50;
                        } else if(ldHeight>150){
                            ldHeight=150;
                        }
                        webView.setPrefHeight(ldHeight);
                        webView.setPrefWidth(600);
                        webView.setDisable(true);
//no va pq pagina cargando en 2º plano                        
//                        WritableImage loIm = new WritableImage((int)600, (int)ldHeight);
//                        webView.snapshot(null, loIm);
//                        try {
//                            JFXConfigGlobal.saveToFile(
//                                    loIm
//                                    , "jpg"
//                                    , File.createTempFile("tableview", ".jpg")
//                            );
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                        
//                        imageView.setImage(loIm);
//                        imageView.setFitHeight(webView.getHeight());
//                        imageView.setFitWidth(webView.getWidth());
                        
                        setGraphic(webView);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    } else {
                        setText("");
                        setContentDisplay(ContentDisplay.TEXT_ONLY);
                    }                                           
                }                    
                colores(item,empty);
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
            return ((value == null) ? "" : value.toString());
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

