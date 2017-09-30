/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui;

import ListDatos.JListDatos;
import utilesGUIx.JTableModel;

public class JGuiSelectCamposTableModel extends JTableModel {

    public static final int lPosiCampos = 0;
    public static final int lPosiTablas = 1;
    public static final int lPosiAgrupacion = 2;
    public static final int lPosiOrden = 3;
    public static final int lPosiMostrar = 4;
    public static final int lPosiCriterios = 5;
    private JGuiSelectCampos moSelectCampos;

    /**
     * Contructor
     * @param poList Datos
     */
    public JGuiSelectCamposTableModel(JGuiSelectCampos poSelectCampos) {
        super(null);
        moSelectCampos = poSelectCampos;
        moList = new JListDatos(
                null, "",
                new String[255],
                new int[255],
                new int[0]);
        for (int i = 0; i < moList.getFields().size(); i++) {
            moList.getFields(i).setCaption(" ");
        }
        for (int i = 0; i < 20; i++) {
            moList.addNew();
            moList.update(false);
        }
        mbEditable = true;
        mbActualizarServidor = false;
    }

    public int getColumnCount() {
        return moList.getFields().count() + 1;
    }

    public boolean isCellEditable(int row, int col) {
        return (col == 0 ? false : super.isCellEditable(row, col - 1));
    }

    public String getColumnName(int col) {
        return (col == 0 ? "" : super.getColumnName(col - 1));
    }

    public Object getValueAt(int row, int col) {
        Object loResult = null;
        if (col == 0) {
            switch (row) {
                case lPosiCampos:
                    loResult = "Campo: ";
                    break;
                case lPosiTablas:
                    loResult = "Tabla: ";
                    break;
                case lPosiAgrupacion:
                    loResult = "Total: ";
                    break;
                case lPosiOrden:
                    loResult = "Orden: ";
                    break;
                case lPosiMostrar:
                    loResult = "Mostrar: ";
                    break;
                case lPosiCriterios:
                    loResult = "Criterios: ";
                    break;
                default:
                    loResult = "O";
            }
        } else {
            loResult = super.getValueAt(row, col - 1);
        }
        return loResult;
    }

    public Class getColumnClass(int c) {
        return (c==0 ? String.class : moList.getFields().get(c).getClase());
    }

    public void setValueAt(final Object value, final int row, final int col) {
        if(col>0){
            super.setValueAt(value, row, col-1);
            if(row == 0 && (value == null || value.toString().equals(""))){
                super.setValueAt("", lPosiTablas, col-1);
                super.setValueAt("", lPosiOrden, col-1);
                super.setValueAt("", lPosiAgrupacion, col-1);
                super.setValueAt(new Boolean(false), lPosiMostrar, col-1);
                super.setValueAt("", lPosiCriterios, col-1);
                for(int i = lPosiCriterios; i < getRowCount(); i++){
                    super.setValueAt("", i, col-1);
                }
            }
        }

    }
}

