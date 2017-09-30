/*
 * RowColChangeEvent.java
 *
 * Created on 27 de septiembre de 2003, 12:05
 */

package utilesGUI.tabla;

import java.util.EventObject;

/**objeto evento del cambio de fila de la tabla*/
public class RowColChangeEvent extends EventObject {
    /**Fila anterior*/
    public int mlFilaAnterior;
    /**columna anterior*/
    public int mlColAnterior;
    /**
     * Creates a new instance of RowColChangeEvent
     * @param poComponente componente del que parte el evento
     */
    public RowColChangeEvent(Object poComponente) {
        super(poComponente);
    }
    /**
     * Constructor
     * @param poComponente componente
     * @param plFilaAnt fila anterior
     * @param plColAnt col. anterior
     */
    public RowColChangeEvent(Object poComponente, int plFilaAnt, int plColAnt) {
        this(poComponente);
        mlFilaAnterior = plFilaAnt;
        mlColAnterior = plColAnt;
    }
    
    
}
