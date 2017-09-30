/*
 * RowColChangeListener.java
 *
 * Created on 27 de septiembre de 2003, 12:03
 */

package utilesGUI.tabla;

/**
 * evento cuando cambia la fila/columna de la tabla
 */
public interface RowColChangeListener {
    /**
     * Metodo cuando cambia la fila/columna de la tabla
     * @param e evento
     */
    public void CambioFila(RowColChangeEvent e);
    
}
