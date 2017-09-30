/*
 * ITable.java
 *
 * Created on 14 de septiembre de 2004, 10:29
 */

package impresionXML.impresion.estructura;

import ListDatos.*;
/**Interfaz para una tabla*/
public interface ITable extends IImprimir {
    /**
     * Insertamos los datos
     * @param poListDatos datos
     */
    public void insertarDatos(JListDatos poListDatos);
    /**
     * Si ponemos la cabezera
     * @param pbPonerCabezera si cabezera visible
     */
    public void setPonerCabezera(boolean pbPonerCabezera);
    /**
     * Devuelve el estilo de una columna de datos
     * @param i Columna
     * @return estilo columna
     */
    public JTColumna getColumna(int i);
    /**
     * Devuelve el estilo de una columna de cabezera
     * @param i columna
     * @return estilo columna
     */
    public JTColumna getColumnaCabe(int i);
    /**
     * Devuelve  el estilo de una celda
     * @param x Columna
     * @param y fila
     * @return estilo columna
     */
    public JTColumna getCelda(int x, int y);
}
