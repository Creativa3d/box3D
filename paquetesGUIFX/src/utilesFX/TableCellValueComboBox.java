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
public class TableCellValueComboBox  implements Callback<TableColumn.CellDataFeatures<IFilaDatos, Object>, ObservableValue> {

    private int mlColumn;
    private JListDatos moTabla;
    private JFieldDef moField;
    private JListDatos moRelleno;
    private int[] malDescrips;
    private int[] malCods;

    public TableCellValueComboBox(final JListDatos poTabla, int plColumn, JListDatos poRelleno,int[] palDescrips, int[] palCods) {
        try {
            moTabla=poTabla;
            mlColumn = plColumn;
            moField = poTabla.getFields(plColumn).Clone();
            moRelleno=poRelleno.Clone();
            malDescrips=palDescrips;
            malCods=palCods;
            moRelleno.ordenar(palCods);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }
    @Override
    public ObservableValue call(TableColumn.CellDataFeatures<IFilaDatos, Object> p) {
        try {
            moField.setValue(p.getValue().msCampo(mlColumn));
        } catch (ECampoError ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        if(moRelleno.buscarBinomial(malCods, new String[]{moField.getString()})){
            return new SimpleObjectProperty(JFieldConComboBox.getDescrip(moRelleno.moFila(), malDescrips));
        }else{
            return new SimpleObjectProperty("");
        }
    }

}
