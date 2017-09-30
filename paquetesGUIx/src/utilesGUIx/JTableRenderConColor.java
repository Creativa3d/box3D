/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.tipos.Moneda;
import utiles.tipos.Moneda3Decimales;
import utiles.tipos.Porcentual;
import utiles.tipos.Porcentual3Decimales;

public class JTableRenderConColor extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final Class mcTipo;
    private Format formatter = null;
    private JTableCZ moTable;
//    private int mlRowHeight=0;

    public JTableRenderConColor(final Class pcTipo) {
        this(pcTipo, null);
    }
    public JTableRenderConColor(final Class pcTipo, final JTableCZ poTable) {
        super();
        mcTipo = pcTipo;
        moTable = poTable;
        if (mcTipo == Number.class) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("###,###,###,###,###.############");
        }
        if (mcTipo == Boolean.class) {
            setHorizontalAlignment(JLabel.CENTER);
        }
        if (mcTipo == Double.class && formatter == null) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("###,###,###,###,###.############");
        }
        if (mcTipo == Integer.class && formatter == null) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("###,###,###,###,###");
        }
        if (mcTipo == Moneda3Decimales.class && formatter == null) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("###,###,###,###,##0.000 \u00A4");
        }
        if (mcTipo == Moneda.class && formatter == null) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("###,###,###,###,##0.00 \u00A4");
        }
        if (mcTipo == Porcentual3Decimales.class && formatter == null) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("#,##0.000 %");
            ((DecimalFormat)formatter).setMultiplier(1);
        }
        if (mcTipo == Porcentual.class && formatter == null) {
            setHorizontalAlignment(JLabel.RIGHT);
            formatter = new DecimalFormat("#,##0.00 %");
            ((DecimalFormat)formatter).setMultiplier(1);
        }
        if (mcTipo == Date.class && formatter == null) {
            formatter = DateFormat.getDateInstance();
        }
//        if(moTable!=null){
//            mlRowHeight=moTable.getRowHeight();
//            if(mlRowHeight!=JGUIxConfigGlobal.getInstancia().getTableAltoFilas()){
//                moTable.setRowHeight(JGUIxConfigGlobal.getInstancia().getTableAltoFilas());
//                mlRowHeight=JGUIxConfigGlobal.getInstancia().getTableAltoFilas();
//            }
//        }
                
    }
    public JTableRenderConColor(final Class pcTipo, final JTableCZ poTable, Format poFormat) {
        this(pcTipo, poTable);
        formatter = poFormat;
    }
    public void setFormat(Format poFormat){
        formatter=poFormat;
    }

    public void setValue(final Object value) {
        try{
            if (mcTipo == Double.class) {
                setText((value == null) ? "" : formatter.format((Double.valueOf(value.toString()))));
            } else if (mcTipo == Integer.class) {
                setText((value == null) ? "" : formatter.format(((Integer) value)));
            } else  if (mcTipo == Moneda3Decimales.class) {
                setText((value == null) ? "" : formatter.format((new Moneda3Decimales(((Double)value)).doubleValue())));
            } else  if (mcTipo == Moneda.class) {
                setText((value == null) ? "" : formatter.format((new Moneda(((Double)value)).doubleValue())));
            } else  if (mcTipo == Porcentual3Decimales.class) {
                setText((value == null) ? "" : formatter.format((new Porcentual3Decimales(((Double)value)).doubleValue())));
            } else  if (mcTipo == Porcentual.class) {
                setText((value == null) ? "" : formatter.format((new Porcentual(((Double)value)).doubleValue())));
            } else  if (mcTipo == Date.class) {
                setText((value == null) ? "" : formatter.format(value));
            } else  if (mcTipo == JDateEdu.class) {
                setText((value == null) ? "" : formatter.format( ((JDateEdu)value).moDate() ));
            } else {
                setText((value == null) ? "" : value.toString());
            }
        }catch(Throwable e){
            setText((value == null) ? "" : value.toString());
            JDepuracion.anadirTexto(getClass().getName(), e);
        }        
    }

    public Component getTableCellRendererComponent(
            final JTable table, final Object value, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {

        if (!isSelected) {
            super.setForeground(table.getForeground());
            super.setBackground(table.getBackground());
        }
        
        setFont(table.getFont());

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            if (table.isCellEditable(row, column)) {
                super.setForeground(UIManager.getColor("Table.focusCellForeground"));
                super.setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(noFocusBorder);
        }

        setValue(value);

  
        
        JTableCZ loTable = moTable;
        if(moTable == null && JTableCZ.class.isAssignableFrom(table.getClass())){
            loTable = ((JTableCZ)table);
        }
        //colores
        if (!table.isCellEditable(row, column)) {
            if (loTable!=null && loTable.getColorBackgroundDesac() != null) {
                this.setBackground(loTable.getColorBackgroundDesac());
            }
        }
        int lColumnModelIndex = table.convertColumnIndexToModel(column);
        int lRowModelIndex = table.convertRowIndexToModel(row);
        if (loTable!=null && loTable.getTableCZColores() != null) {
            ColorCZ loColor = 
                    loTable.getTableCZColores().getColorBackground(
                    value, isSelected, hasFocus, lRowModelIndex, lColumnModelIndex);
            if (loColor != null) {
                this.setBackground(new Color(loColor.getColor()));
            }
            loColor = loTable.getTableCZColores().getColorForeground(
                    value, isSelected, hasFocus, lRowModelIndex, lColumnModelIndex);
            if (loColor != null) {
                this.setForeground(new Color(loColor.getColor()));
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
            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        }
        return this;
    }
}
