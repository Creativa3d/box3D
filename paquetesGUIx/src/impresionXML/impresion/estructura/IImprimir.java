/*
 * IImprimir.java
 *
 * Created on 14 de septiembre de 2004, 10:47
 */

package impresionXML.impresion.estructura;

import java.io.OutputStream;
import java.io.Serializable;

/**Intefaz final lde impresión */
public interface IImprimir extends Serializable {
    /**
     * imprimir,
     * o usando los metodos del lienzo(Impresora/imagen)
     * o usando el outputStream un Fichero(html, xml, ...)
     * devuelve si la impresión a tenido exito
     * @param poLienzo Lienzo en donde imprimir
     * @param poOut fichero de salida en donde imprimir
     * @return si ha tenido éxito
     */
    public boolean imprimir(ILienzo poLienzo, OutputStream poOut);
}
