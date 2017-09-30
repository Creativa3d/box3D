/*
 * ILinea.java
 *
 * Created on 14 de septiembre de 2004, 10:33
 */

package impresionXML.impresion.estructura;

import java.awt.geom.*;

/**Interfaz para insertar lineas*/
public interface ILinea  extends IImprimir {
    /**
     * Insertar linea
     * @param poPunto1 punto 1
     * @param poPunto2 punto 2
     * @param poEstiloLinea Estilo línea
     */
    public void insertarLinea(
        Point2D poPunto1, Point2D poPunto2, 
        JEstiloLinea poEstiloLinea);
}
