/*
 * IImagen.java
 *
 * Created on 14 de septiembre de 2004, 10:29
 */

package impresionXML.impresion.estructura;

import java.awt.*;
import java.awt.geom.*;

/**Intefaz imagen, para imprimir imagenes */
public interface IImagen extends IImprimir{
    /**
     * Imagen a imprimir, en la posición correspondiente, siempre CM
     * @param poImagen imagen
     * @param poPosicionDestino rectangulo de destino
     */
    public void insertarImagen(Image poImagen, Rectangle2D poPosicionDestino);
}
