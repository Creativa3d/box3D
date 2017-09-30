/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.JPanelGeneralFiltroModelo;
import utilesGUIx.formsGenericos.JPanelGenericoEvent;
import utilesGUIx.formsGenericos.JTFiltro;

/**
 *
 * @author eduardo
 */
public class JPanelGeneralFiltroTodosCamp {

    private final TextField txtBusqueda;
    private boolean mbInicializado = false;
    private JPanelGeneralFiltroModelo moFiltro;
    private TableView jTableDatos;
    private EventHandler<ActionEvent> moPadre;

    public JPanelGeneralFiltroTodosCamp(final TextField poBusqueda) {
        txtBusqueda = poBusqueda;
        txtBusqueda.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                JPanelGeneralFiltroTodosCamp.this.changed();
            }
        });
        
        txtBusqueda.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (mbInicializado) {
                    if (poBusqueda.isVisible()) {
                        KeyCode loCode = t.getCode();
                        String lc = t.getCharacter();
                        if (loCode == KeyCode.ESCAPE) {
                            t.consume();
                            Button lob = new Button();
                            lob.setId(JPanelGenericoEvent.mcsESC);
                            moPadre.handle(new ActionEvent(lob, null));
                        }
                        if (loCode == KeyCode.ENTER) {
                            t.consume();
                            Button lob = new Button();
                            lob.setId(JPanelGenericoEvent.mcsENTER);
                            moPadre.handle(new ActionEvent(lob, null));
                        }                        
                        if (loCode == KeyCode.DOWN || loCode == KeyCode.UP
                                || loCode == KeyCode.PAGE_DOWN || loCode == KeyCode.PAGE_UP) {
                            t.consume();
                            int lRow = jTableDatos.getSelectionModel().getSelectedIndex();
                            if (loCode==KeyCode.DOWN) {
                                lRow++;
                            }          
                            if (loCode==KeyCode.UP) {
                                lRow--;
                            }
                            if (loCode==KeyCode.PAGE_DOWN) {
                                lRow+=10;
                            }
                            if (loCode==KeyCode.PAGE_UP) {
                                lRow-=10;
                            }
                            if (lRow < 0) {
                                lRow = 0;
                            }
                            if (lRow >= jTableDatos.getItems().size()) {
                                lRow = jTableDatos.getItems().size() - 1;
                            }
                            jTableDatos.getSelectionModel().clearAndSelect(lRow);
                            jTableDatos.scrollTo(lRow);
                        }
                    }

//                    if (e.getSource() == jTableDatos && isVisible()) {
//                        String lsValor = null;
//                        if ((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z')
//                                || (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')
//                                || (e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
//                                || e.getKeyChar() == '/' || e.getKeyChar() == '\\'
//                                || e.getKeyChar() == ',' || e.getKeyChar() == '.'
//                                || e.getKeyChar() == '-' || e.getKeyChar() == '_'
//                                || e.getKeyChar() == ';' || e.getKeyChar() == ':') {
//                            lsValor = String.valueOf(e.getKeyChar());
//                        } else {
//                            if (e.getKeyChar() == KeyCode.BACK_SPACE || e.getKeyChar() == KeyCode.DELETE) {
//                                lsValor = "";
//                            }
//                        }
//
//                        if (lsValor != null) {
//                            txtBusqueda.setText(lsValor);
//                            txtBusqueda.requestFocus();
//                        }
//                    }
                }
            }
        });

    }

    public void setComponentes(final TableView poTableDatos,  final EventHandler<ActionEvent> poPadre){
        jTableDatos = poTableDatos;
                moPadre=poPadre;
    }
    public void setDatos(
            final JPanelGeneralFiltroModelo  loFiltro
            ) {
        moFiltro = loFiltro;

        mbInicializado = true;
    }

    public void setVisible(boolean activo) {
        txtBusqueda.setVisible(activo);
    }

    /**
     * @return the txtBusqueda
     */
    public TextField getBusqueda() {
        return txtBusqueda;
    }

    public void requestFocus() {
        txtBusqueda.requestFocus();
    }
    private void changed(){
        moFiltro.buscarTodosCampos(txtBusqueda.getText());
    }    
}
