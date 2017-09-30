/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.avisosGUI;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import utilesGUIx.aplicacion.avisos.JAviso;
import utilesGUIx.aplicacion.avisos.JAvisosConj;

public class JTableRenderAvisos  implements TableCellRenderer {
    private JPanelTableRenderAvisos moPanel = new JPanelTableRenderAvisos();
    private JLabel moLabel = new JLabel();
    private final JAvisosConj moAvisos;
    
    public JTableRenderAvisos(JAvisosConj poAvisos){
        moAvisos = poAvisos;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component loResult = null;
        if(value !=null){
            if(value instanceof JAviso){
                JAviso lo = (JAviso)value;
//                if(lo.getCodigo()==null || lo.getCodigo().equals("")){
//                    moLabel.setText(lo.getNombre());
//                    loResult=moLabel;
//                }else{
                    moPanel.setValue((JAviso)value, isSelected, moAvisos);
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
