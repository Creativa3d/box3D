/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.JListDatos;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import utiles.IListaElementos;


public class JTableRendererBooleanColor extends JCheckBox implements TableCellRenderer {
    private static final long serialVersionUID = 1L;
    private JTableCZ moTable;
    
    public JTableRendererBooleanColor() {
        this(null);
    }
    public JTableRendererBooleanColor(final JTableCZ poTable) {
        super();
        moTable = poTable;
        setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(
            final JTable table, final Object value, final boolean isSelected, 
            final boolean hasFocus, final int row, final int column) {
        if (!isSelected) {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        
        //colores
        JTableCZ loTable = moTable;
        if(moTable == null && JTableCZ.class.isAssignableFrom(table.getClass())){
            loTable = ((JTableCZ)table);
        }
        if(!table.isCellEditable(row, column)){
            if(loTable!=null && loTable.getColorBackgroundDesac()!=null){
                this.setBackground(loTable.getColorBackgroundDesac());
            }
        }
        int lColumnModelIndex = table.convertColumnIndexToModel(column);        
        if(loTable!=null && loTable.getTableCZColores()!=null){
            ColorCZ loColor = loTable.getTableCZColores().getColorBackground(
                    value, isSelected, hasFocus, row, lColumnModelIndex);
            if(loColor!=null){
                setBackground(new Color(loColor.getColor()));
            }
            loColor = loTable.getTableCZColores().getColorForeground(
                    value, isSelected, hasFocus, row, lColumnModelIndex);
            if(loColor!=null){
                setForeground(new Color(loColor.getColor()));
            }
        }
        if(value == null){
            setSelected(false);
        }else{
            if(value.getClass().isAssignableFrom(Boolean.class)){
                setSelected(((Boolean)value).booleanValue());
            }else{
                setSelected(
                        value.toString().equalsIgnoreCase("true") ||
                        value.toString().equalsIgnoreCase(JListDatos.mcsTrue)
                        );
            }
        }
        TableModel tableModel = table.getModel();
        if (tableModel instanceof JTableModel) {
            if (((JTableModel) table.getModel()).getSortColumns().size() > 0) {
                IListaElementos loSort = ((JTableModel) table.getModel()).getSortColumns();
                boolean lbEncontrado = false;
                boolean lbAscendente = true;
                for (int i = 0; i < loSort.size(); i++) {
                    int orden = ((Integer) loSort.get(i)).intValue();
                    int model = table.convertColumnIndexToModel(column);
                    if (model == orden) {
                        lbEncontrado = true;
                        lbAscendente = ((JTableModel) table.getModel()).isSortAscendente();
                    }
                }
                this.setBackground(
                        lbEncontrado
                        ? (lbAscendente
                        ? new Color(199, 218, 203)
                        : new Color(254, 192, 200))
                        : this.getBackground());
            }
        }
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        }
        return this;
    }
}
