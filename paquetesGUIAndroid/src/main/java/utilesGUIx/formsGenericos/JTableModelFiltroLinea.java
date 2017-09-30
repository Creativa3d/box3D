/*
 * JTableModelFiltroLinea.java
 *
 * Created on 20 de noviembre de 2006, 12:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import android.view.View;
import utilesGUI.tabla.ITabla;
import utilesGUIx.JTableCZ;

public class JTableModelFiltroLinea  implements ITabla {
    private JTableCZ jTableDatos;
    private final JTableModelFiltro moFiltro;
    /** Creates a new instance of JTableModelFiltroLinea */
    public JTableModelFiltroLinea(final JTableModelFiltro poFiltro, final JTableCZ poTableDatos) {
        super();
        moFiltro = poFiltro;
        jTableDatos = poTableDatos;
    }
    public int getColumnCount() { 
        return jTableDatos.getModel().getColumnCount(); 
    } 
    public int getRowCount() { 
        return 1;
    } 
    public Class getColumnClass(final int c) {
        return String.class;
    } 
    public boolean isCellEditable(final int row, final int col) {
        return true;
    } 
    
    
    public String getColumnName(final int column) {
        return jTableDatos.getModel().getColumnName(column);
    } 
    public Object getValueAt(final int row, final int column) {
        Object lsResult = "";
        int lFila = moFiltro.getFilaOrigenSinDuplicados(column);
        if(lFila!=-1){
            lsResult = moFiltro.getValueAt(lFila, moFiltro.mclValor);
        }
        return lsResult;
    } 
    public void setValueAt(final Object aValue, final int row, final int column) {
        int lFila = moFiltro.getFilaOrigenSinDuplicados(column);
        if(lFila!=-1){
            moFiltro.anularCambios();
            try{
                for(int i = 0 ; i < moFiltro.getRowCount(); i++){
                    moFiltro.setValueAt(JTableModelFiltro.mcsY, i, JTableModelFiltro.mclUnion);
                }
                moFiltro.setValueAt(moFiltro.mcsComo, lFila, moFiltro.mclComparacion);
            }finally{
                moFiltro.activarCambios();
            }
            moFiltro.setValueAt(aValue, lFila, moFiltro.mclValor);
        }
    }

    public void sortByColumn(int plColumn, boolean pbAscendente) {
    }

    public View getComponent(int row, int col) {
        return null;
    }
    
}
