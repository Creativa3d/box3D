/*
 * ElementoActualizado.java
 *
 * Created on 6 de abril de 2004, 13:18
 */

package utilesGUI.tabla;

/**
 *Indica la celda actualizada con el valor
 */
public class ElementoActualizado implements java.io.Serializable{
    /**Fila*/
    public int mlFila;
    /**Columna*/
    public int mlCol;
    /**Valor*/
    public Object moValor;
    /**
     * Constructor
     * @param plFila fila
     * @param plCol columna
     * @param poValor Valor
     */
    public ElementoActualizado(int plFila, int plCol, Object poValor){
        mlFila = plFila;
        mlCol = plCol;
        moValor = poValor;
    }
    /**
     * Compara la posición por y,x
     * @return < 0 si es menor,si es igual, 1 si es mayor
     * @param o2 objeto a comparar
     */
    public int comparePosicion(ElementoActualizado o2) {
        int lCompare=0;
        if (o2.mlFila < mlFila) {
            lCompare=1;
        }
        if (o2.mlFila > mlFila) {
            lCompare=-1;
        }
        if (o2.mlCol > mlCol) {
            lCompare=1;
        }
        if (o2.mlCol < mlCol) {
            lCompare=-1;
        }
        return lCompare;
   }
}