/*
 * IDevolverObjetoResultado.java
 *
 * Created on 13 de enero de 2005, 9:10
 */

package ListDatos;

import utiles.xml.sax.*;

/**Objeto para procesar y devolver un objeto IResultado */
public interface IXMLDevolverObjetoResultado extends ISax{
    /**
     * Objeto creado
     * @return objeto
     */
    public Object getObjeto();
}
