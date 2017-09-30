/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.forms.util;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableRenderEvento  implements TableCellRenderer {
    private JPanelTableRender moPanel = new JPanelTableRender();
    private JLabel moLabel = new JLabel();
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component loResult = null;
        if(value !=null){
            if(value instanceof JTableRenderEventoParam){
                JTableRenderEventoParam lo = (JTableRenderEventoParam)value;
//                if(lo.getCodigo()==null || lo.getCodigo().equals("")){
//                    moLabel.setText(lo.getNombre());
//                    loResult=moLabel;
//                }else{
                    moPanel.setValue((JTableRenderEventoParam)value, isSelected);
                    loResult=moPanel;
//                }
            }else{
                moLabel.setText(value.toString());
                loResult=moLabel;
            }
        }
        if(loResult != null){
            if(loResult == moPanel && moPanel.getHeight()>0){
                table.setRowHeight(row, moPanel.getHeight());
            }
            if(loResult == moLabel && moLabel.getHeight()>0){
                table.setRowHeight(row, moLabel.getHeight());
            }
        }
        return loResult;
    }
    
}
