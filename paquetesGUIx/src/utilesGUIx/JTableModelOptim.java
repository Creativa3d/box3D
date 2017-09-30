package utilesGUIx;

import javax.swing.table.AbstractTableModel;
import javax.swing.event.TableModelEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.event.TableModelListener;


import ListDatos.*;
import ListDatos.estructuraBD.JFieldDefs;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUI.tabla.*;

/**Datos para una tabla*/
public class JTableModelOptim extends AbstractTableModel implements TableModelListener, ITabla {

    /**
     *Objeto fuente de datos
     */
    public final JListDatos moList;
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
    private MouseAdapter moListMouseListener;
    private IListaElementos moSortcolumn = new JListaElementos();
    private boolean mbSortAscendente = true;
    private int mlRowCountAux = 0;

    private JFieldDefs moCamposCopia;
    private int mlrow;
    private boolean mbSucio=false;
    /**
     * Contructor
     * @param poList Datos
     */
    public JTableModelOptim(JListDatos poList) {
        super();
        moList = poList;
        moList.addListenerEdicion(new IListDatosEdicion() {
            public void edicionDatos(int plModo, int plIndex, IFilaDatos poDatos) {
                mbSucio = true;
            }
            public void edicionDatosAntes(int plModo, int plIndex) throws Exception {}
        });
        try {
            moCamposCopia = moList.getFields().Clone();
        } catch (CloneNotSupportedException ex) {
        }
    }

    /**
     * Devuelve el numero de columnas
     * @return Número columnas
     */
    public int getColumnCount() {
        return moList.getFields().count();
    }

    /**
     * Devuelve el numero de filas
     * @return número de filas
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
            if (mlrow != row || moList.size()!=mlRowCountAux || mbSucio) {//para los borrados
                mbSucio=false;
                mlRowCountAux=moList.size();
                mlrow = row;
                try {
                    moCamposCopia.cargar((IFilaDatos)moList.get(row));
                } catch (ECampoError ex) {
                }
            }
            return moCamposCopia.get(col).getValue();
        }
    }

    /**
     * devuelve la clase de la columna
     * @return clase de los datos de la columna
     * @param c índice de la columna
     */
    public Class getColumnClass(int c) {
        return moList.getFields().get(c).getClase();
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
            fireTableCellUpdated(row, col);
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(
                    new javax.swing.JLabel(), e, getClass().getName());
        }
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
        tableChanged(new TableModelEvent(this));
    }

    /**
     * Borra un mouse listener para la cabezera de la tabla, para poder controlar que columna pulsar y ordenar por esa columna
     * @param table Componente tabla
     */
    public void removeMouseListenerToHeaderInTable(JTable table) {
        final JTable tableView = table;
        JTableHeader th = tableView.getTableHeader();
        th.removeMouseListener(moListMouseListener);
    }

    /**
     * Añade un mouse listener para la cabezera de la tabla, para poder controlar que columna pulsar y ordenar por esa columna
     * @param table Componente tabla
     */
    public void addMouseListenerToHeaderInTable(JTable table) {
        final JTable tableView = table;
        tableView.setColumnSelectionAllowed(false);
        moListMouseListener = new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                TableColumnModel columnModel = tableView.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = tableView.convertColumnIndexToModel(viewColumn);
                if (e.getClickCount() == 1 && column != -1) {
                    //si no hay ninguna columna previamente ordenada siempre ascendente
                    if(moSortcolumn.size() == 0){
                        setSortAscendente(true);
                    }

                    //solo cuando hay una columna seleccionada se selecciona si es ascendente o descendente
                    if(moSortcolumn.size() == 1){
                        //si es la misma columna:
                        //   si es ascendente->descendente
                        //   si es descendente ->la quitamos del orden
                        //si no misma columna la añadimos al orden
                        if (((Integer) moSortcolumn.get(0)).intValue() == column) {
                            if(isSortAscendente()){
                                setSortAscendente(false);
                            }else{
                                clearSortByColumns();
                            }
                        } else {
                            addSortByColumn(column);
                        }
                    }else{
                        //comprobamos si ya esta en la ordenacion, si es asi la quitamos
                        boolean lbADD = true;
                        for (int i = 0; i < moSortcolumn.size(); i++) {
                            int lCol = ((Integer) moSortcolumn.get(i)).intValue();
                            if(column==lCol){
                                moSortcolumn.remove(i);
                                lbADD = false;
                            }
                        }
                        if(lbADD){
                            addSortByColumn(column);
                        }

                    }
                    sort();
                }
            }
        };
        JTableHeader th = tableView.getTableHeader();
        th.addMouseListener(moListMouseListener);
    }


}
