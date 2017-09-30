package utilesGUIx.aplicacion.avisosGUI;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import utilesGUI.tabla.ITabla;
import utilesGUIx.aplicacion.avisos.JAvisosConj;

/**Datos para una tabla*/
public class JTableModelAvisos extends AbstractTableModel implements TableModelListener, ITabla {

    /**
     *Objeto fuente de datos
     */
    public JAvisosConj moEventos;

    /**
     * Contructor
     * @param poList Datos
     */
    public JTableModelAvisos(JAvisosConj poList) {
        super();
        moEventos = poList;
    }

    /**
     * Devuelve el numero de columnas
     * @return Número columnas
     */
    public int getColumnCount() {
        return 1;
    }

    /**
     * Devuelve el numero de filas
     * @return número de filas
     */
    public int getRowCount() {

            return moEventos.size();
        
    }

    /**
     * Devuelve el nombre de la columna
     * @return nombre
     * @param col columna
     */
    public String getColumnName(int col) {
        return "";
    }

    /**
     * devuelve el valor de la celda
     * @param row fila
     * @param col columna
     * @return valor de la celda
     */
    public Object getValueAt(int row, int col) {
        if(row >= moEventos.size()){
            return null;
        }else{
            return moEventos.get(row);
        }
    }

    /**
     * devuelve la clase de la columna
     * @return clase de los datos de la columna
     * @param c índice de la columna
     */
    public Class getColumnClass(int c) {
        return JTableRenderAvisos.class;
    }

    public void tableChanged(TableModelEvent e) {
        fireTableChanged(e);
    }


    /**
     * indica si la celda es editble
     * @param row fila
     * @param col columna
     * @return si es editable
     */
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     * Establece el valor de la celda
     * @param value valor
     * @param row fila
     * @param col columna
     */
    public void setValueAt(Object value, int row, int col) {
       
    }

    /**
     * Devolvemos el componente
     * @return Componente
     * @param row fila
     * @param col columna
     */
    public java.awt.Component getComponent(int row, int col) {
        return null;
    }

    @Override
    public void sortByColumn(int plColumn, boolean pbAscendente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
