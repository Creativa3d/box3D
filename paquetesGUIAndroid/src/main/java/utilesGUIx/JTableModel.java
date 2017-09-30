package utilesGUIx;



import utilesGUI.tabla.ElementoActualizado;
import utilesGUI.tabla.ElementosActualizados;
import ListDatos.*;
import android.view.View;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUI.tabla.*;

/**Datos para una tabla*/
public class JTableModel implements ITabla {

    /**
     *Objeto fuente de datos
     */
    public JListDatos moList;
    /**
     *Si es editable
     */
    public boolean mbEditable = false;
    /**
     *
     */
    public boolean mbUltFilaAdd = false;
    /**
     *Si se actualiza en el servidor
     */
    public boolean mbActualizarServidor = false;
    /**
     *Lista de celdas actualizadas
     */
    public ElementosActualizados moListaActualizados = new ElementosActualizados();
    /**Si guardamos las celdas que se van actualizando*/
    public boolean mbLlevarCeldasActualizadas = false;

    private IListaElementos moSortcolumn = new JListaElementos();
    private boolean mbSortAscendente = true;

    /**
     * Contructor
     * @param poList Datos
     */
    public JTableModel(JListDatos poList) {
        super();
        moList = poList;
    }

    /**
     * Devuelve el numero de columnas
     * @return Nmero columnas
     */
    public int getColumnCount() {
        return moList.getFields().count();
    }

    /**
     * Devuelve el numero de filas
     * @return nmero de filas
     */
    public int getRowCount() {
        if(mbUltFilaAdd && mbEditable && !mbActualizarServidor){
            return moList.size()+1;
        }else{
            return moList.size();
        }
    }

    /**
     * Devuelve el nombre de la columna
     * @return nombre
     * @param col columna
     */
    public String getColumnName(int col) {
        return moList.getFields().get(col).getCaption();
    }

    /**
     * devuelve el valor de la celda
     * @param row fila
     * @param col columna
     * @return valor de la celda
     */
    public Object getValueAt(int row, int col) {
        if(row >= moList.size()){
            return null;
        }else{
            if (moList.getIndex() != row) {
                moList.setIndex(row);
            }
            return moList.getFields().get(col).getValue();
        }
    }

    /**
     * devuelve la clase de la columna
     * @return clase de los datos de la columna
     * @param c ndice de la columna
     */
    public Class getColumnClass(int c) {
        return moList.getFields().get(c).getClase();
    }



    /**
     * indica si la celda es editble
     * @param row fila
     * @param col columna
     * @return si es editable
     */
    public boolean isCellEditable(int row, int col) {
        return mbEditable && moList.getFields(col).isEditable();
    }

    /**
     * Establece el valor de la celda
     * @param value valor
     * @param row fila
     * @param col columna
     */
    public void setValueAt(Object value, int row, int col) {
        try {
            if(row>=moList.size()){
                moList.addNew();
            }else if (moList.getIndex() != row) {
                moList.setIndex(row);
            }
            moList.getFields().get(col).setValue(value);
            IResultado loResul = moList.update(mbActualizarServidor);
            if (loResul.getBien()) {
                if (mbLlevarCeldasActualizadas) {
                    moListaActualizados.add(new ElementoActualizado(row, col, value));
                }
            } else {
                throw new Exception(loResul.getMensaje());
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(
                    null, e, getClass().getName());
        }
    }


    /**
     * ordenar una columna
     * @param plColumn columna
     * @param pbAscendente si es ascendente el orden
     */
    public void sortByColumn(int plColumn, boolean pbAscendente) {
        clearSortByColumns();
        setSortAscendente(pbAscendente);
        addSortByColumn(plColumn);
        sort();

    }

    public IListaElementos getSortColumns() {
        if(moList.getOrdenacion()==null){
            moSortcolumn.clear();
        }
        return moSortcolumn;
    }

    public void clearSortByColumns() {
        moSortcolumn.clear();
    }

    public boolean isSortAscendente() {
        return mbSortAscendente;
    }

    public void setSortAscendente(boolean mbSortAscendente) {
        this.mbSortAscendente = mbSortAscendente;
    }

    public void addSortByColumn(int plColumn) {
        if (plColumn >= 0) {
            moSortcolumn.add(new Integer(plColumn));
        }
    }

    private void sort() {
        int[] lalColumn = new int[moSortcolumn.size()];
        for (int i = 0; i < moSortcolumn.size(); i++) {
            lalColumn[i] = ((Integer) moSortcolumn.get(i)).intValue();
        }
        moList.ordenar(lalColumn, isSortAscendente());
    }

    public Object getComponent(int row, int col) {
        return null;
    }

}
