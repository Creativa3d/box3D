/*
 * ITabla.java
 *
 * Created on 27 de septiembre de 2003, 9:04
 */

package utilesGUI.tabla;

import java.io.Serializable;

/**Interfaz que debe cumplir el objeto que quiera presentarse en una tabla*/
public interface ITabla extends Serializable {
    
    /**
    * Devuelve el numero de columnas
    * @return Numero columnas
    */
    public int getColumnCount() ;
    /**
     * Devuelve el numero de filas
     * @return numero de filas
     */
    public int getRowCount();
    /**
     * Devuelve el nombre de la columna
     * @return nombre
     * @param col columna
     */
    public String getColumnName(int col) ;
    /**
     * devuelve el valor de la celda
     * @param row fila
     * @param col columna
     * @return valor de la celda
     */
    public Object getValueAt(int row, int col) ;
    /**
    * devuelve la clase de la columna
    * @return clase de los datos de la columna
    * @param c indice de la columna
    */
    public Class getColumnClass(int c);

    /**
     * ordenar una columna
     * @param plColumn columna
     * @param pbAscendente si es ascendente el orden
     */
    public void sortByColumn(int plColumn, boolean pbAscendente);



    /*************************************************************/
    /*Edicion*/
    /*************************************************************/

    /**
     * indica si la celda es editble
     * @param row fila
     * @param col columna
     * @return si es editable
     */
    public boolean isCellEditable(int row, int col) ;
    /**
     * Establece el valor de la celda
     * @param value valor
     * @param row fila
     * @param col columna
     */
    public void setValueAt(Object value, int row, int col);

}
 