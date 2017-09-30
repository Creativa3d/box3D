/*
 * IComprimido.java
 *
 * Created on 17 de diciembre de 2004, 13:46
 */

package ListDatos;

import java.io.Serializable;

    /**
    * Para enviar un objeto al servidor debe cumplir este interfaz, ya que en el objeto JServerServidorDatos del cliente establece si la vuelta del servidor se espera comprimida, y en el servidor devuelve la salida comprimida en funcion de getComprimido 
    */
public interface ISelectEjecutarComprimido extends Serializable {
    /**
     * Indica si se devuelve comprimido 
     * @return si comprimido
     */
    public boolean getComprimido();
    /**
     * Establece si se devuelve comprimido 
     * @param pbValor si comprimido
     */
    public void setComprimido(boolean pbValor);    
}
