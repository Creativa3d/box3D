/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFXAvisos.forms.util;

import ListDatos.IFilaDatos;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author eduardo
 */
public class TableCellValueEventos  implements Callback<TableColumn.CellDataFeatures<IFilaDatos, Object>, ObservableValue> {


    public TableCellValueEventos() {


    }

    @Override
    public ObservableValue call(TableColumn.CellDataFeatures<IFilaDatos, Object> p) {
        return new SimpleObjectProperty(p.getValue());
    }

}
