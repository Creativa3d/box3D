/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import utiles.IListaElementos;
import utiles.JListaElementos;

/**
 *
 * @author GONZE2
 */
public class JTableCZSelecCeldas implements TableColumnModelListener, ListSelectionListener {
    private final JTable moTable;
    private int mlRowActual=-1;
    private int mlColActual=-1;
    private boolean mbEventosActivos = true;
    private IListaElementos moListaEdicionAuto = new JListaElementos();

    public JTableCZSelecCeldas(JTable poTable){
        moTable = poTable;
        activar();
    }
//    public void addColumnaEdicionAutomatica(int plCol){
//        moListaEdicionAuto.add(new Integer(plCol));
//    }
    public void activar(){
        moTable.getColumnModel().removeColumnModelListener(this);
        moTable.getSelectionModel().removeListSelectionListener(this);
        moTable.getColumnModel().addColumnModelListener(this);
        moTable.getSelectionModel().addListSelectionListener(this);
    }
    public void desactivar(){
        moTable.getColumnModel().removeColumnModelListener(this);
        moTable.getSelectionModel().removeListSelectionListener(this);
    }
    //selecciona de columnas
    private int getRow(){
        int lRow;
        if(mlRowActual>=0){
            lRow = mlRowActual;
            mlRowActual=-1;
        }else{
            lRow = moTable.getSelectedRow();
            if(lRow<0){
                lRow = moTable.getEditingRow();
            }
        }
        return lRow;
    }
    //selecciona de columnas
    private int getCol(){
        int lCol;
        if(mlColActual>=0){
            lCol = mlColActual;
            mlColActual=-1;
        }else{
            lCol = moTable.getSelectedColumn();
            if(lCol<0){
                lCol = moTable.getEditingColumn();
            }
        }
        return lCol;
    }

    public void columnAdded(TableColumnModelEvent e) {
    }

    public void columnRemoved(TableColumnModelEvent e) {
    }

    public void columnMoved(TableColumnModelEvent e) {
    }

    public void columnMarginChanged(ChangeEvent e) {
    }

    public void columnSelectionChanged(ListSelectionEvent e) {
        if(mbEventosActivos){
            int lRowActual = getRow();
            int lColSiguiente = getCol();
            if(!isSeleccionable(lRowActual, lColSiguiente)){
                if(isUltColumn(lColSiguiente)){
                    if(!isFinFila(lRowActual)){
                        lRowActual = lRowActual+1;
                        lColSiguiente = getPrimeraCeldaSeleccionableDeFila(lRowActual);
                        if(lColSiguiente>=0){
                            mlRowActual=lRowActual;
                            mbEventosActivos=false;
                            try{
                                moTable.changeSelection(lRowActual, lColSiguiente, false, false);
                            }finally{
                                mbEventosActivos=true;
                            }
//no hace falta
//                            moTable.getSelectionModel().setSelectionInterval(lRowActual, lRowActual);
                        }
                    }
                }else{
                    mlColActual = lColSiguiente+1;
                    columnSelectionChanged(new ListSelectionEvent(moTable, lColSiguiente, lColSiguiente+1, true));
                }
            }else{
                mbEventosActivos=false;
                try{
                    moTable.changeSelection(lRowActual, lColSiguiente, false, false);
                }finally{
                    mbEventosActivos=true;
                }
            }
//            if(isEdicionAutom(lRowActual, lColSiguiente)){
//                final int lCol = lColSiguiente;
//                final int lRow = lRowActual;
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        mbEventosActivos=false;
//                        moTable.editCellAt(lRow, lCol);
//                    }
//                });
//            }
        }
    }
//    private boolean isEdicionAutom(int plRow, int plCol){
//        boolean lbResult = moTable.getModel().isCellEditable(plRow, plCol);
//        
//        if(lbResult){
//            lbResult=false;
//            for(int i = 0 ; i < moListaEdicionAuto.size(); i++){
//                if(((Integer)moListaEdicionAuto.get(i)).intValue()==plCol){
//                    lbResult=true;
//                }
//            }
//        }
//        
//        return lbResult;
//    }
    private int getPrimeraCeldaSeleccionableDeFila(int plRow){
        int lSelec = -1;
        for(int i = 0 ; i < moTable.getColumnCount() && lSelec==-1; i++ ){
            if(isSeleccionable(plRow, i)){
                lSelec = i;
            }
        }
        return lSelec;
    }
    private boolean isFinFila(int plRow){
        return plRow == moTable.getRowCount()-1;
    }

    private boolean isUltColumn(int plColumn){
        return plColumn == moTable.getColumnCount()-1;
    }
    private boolean isSeleccionable(int plRow, int plColumn){
        try{
            return
                moTable.getColumnModel().getColumn(plColumn).getWidth() > 0 &&
                moTable.isCellEditable(plRow, plColumn);
        }catch(Exception e){
            return true;
        }

    }

    public void valueChanged(ListSelectionEvent e) {
        if(mbEventosActivos){
            if(mlRowActual>-1){
                int lRowActual = getRow();
                moTable.changeSelection(lRowActual, getCol(), false, false);
            }
        }
    }

}
