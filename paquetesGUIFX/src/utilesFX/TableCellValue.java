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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import utiles.JDepuracion;

/**
 *
 * @author eduardo
 */
public class TableCellValue  implements Callback<TableColumn.CellDataFeatures<IFilaDatos, Object>, ObservableValue> {

    private final int mlCol;
    private final JListDatos moTabla;
    private JFieldDef moField;

    public TableCellValue(final JListDatos poTabla, final int plCol) {

        moTabla = poTabla;
        mlCol = plCol;
        try {
            moField = poTabla.getFields(plCol).Clone();
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }

    }

    @Override
    public ObservableValue call(TableColumn.CellDataFeatures<IFilaDatos, Object> p) {
        try {
            moField.setValue(p.getValue().msCampo(mlCol));
        } catch (ECampoError ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        if (moField.getTipo() == JListDatos.mclTipoBoolean) {
            return new SimpleBooleanProperty(moField.getBoolean());
        } else {
            return new SimpleObjectProperty(moField.getValue());
        }
    }

}
