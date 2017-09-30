/*
 * IParrafo.java
 *
 * Created on 14 de septiembre de 2004, 10:29
 */

package impresionXML.impresion.estructura;

import java.awt.Font;
/**Interfaz para insertar p�rrafos*/
public interface IParrafo extends IImprimir {
    /**
     * Insertar texto
     * @param psTexto Texto
     * @param plAlineacion alineaci�n
     * @param poFuente fuente
     */
    public void insertarTexto(String psTexto, int plAlineacion, Font poFuente);
}
