/*
 * EstiloPunto.java
 *
 * Created on 21 de abril de 2004, 9:48
 */

package utilesGUI.grafxy2.estilos;

/**Estilo punto*/
public class EstiloPunto {
    /**Valor X*/
    public Object moX;
    /**Estilo serie*/
    public Object moEstiloSerie;
    /**
     * Constructor
     * @param x valor X
     * @param poEstilo estilo
     */
    public EstiloPunto(Object x, Object poEstilo){
        moEstiloSerie = poEstilo;
        moX = x;
    }
}
