/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFXAvisos.forms.util;

import ListDatos.IBusquedaListener;
import utilesFX.*;
import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 *
 * @author eduardo
 */
public class JTableViewEventos extends JTableViewCZ {

    private IBusquedaListener moListRecuperar;
    
    public JTableViewEventos() {
        super();
    }


    @Override
    public void setModel(JListDatos poList, ObservableList<IFilaDatos> poListObservable) throws Exception {
        moList = poList;
        asignarEventos();
        
        getColumns().clear();
        layout();


        TableColumn filaCol = new TableColumn("Tareas");
        filaCol.setCellFactory(column -> {
            return new TableCell<IFilaDatos, IFilaDatos>() {

                private JPanelTableRender moRenderEventos = new JPanelTableRender();

                private JTableRenderEventoParam moParam = new JTableRenderEventoParam();            
                @Override
                protected void updateItem(IFilaDatos item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {

                        moParam.setFila((IFilaDatos)item );
                        moRenderEventos.setValue(moParam, false);
                        setGraphic(moRenderEventos);

                    }
                }
            };
        });
                
        filaCol.setCellValueFactory(new TableCellValueEventos());
        filaCol.setOnEditCommit(null);
        filaCol.setId(String.valueOf(0));
        filaCol.setEditable(poList.getFields(0).isEditable());
        getColumns().add(filaCol);
        
        reengancharTabla(poList, poListObservable);
    }

            
}
