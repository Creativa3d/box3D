/*
 * IResultado.java
 *
 * Created on 2 de diciembre de 2004, 11:12
 */

package ListDatos;

import utiles.*;

/** Resultado de una operacion de servidor */
public interface IResultado extends java.io.Serializable {
    /**
     * Devuelve si esta bien o no 
     * @return si bien
     */
    public boolean getBien();
    /**
     * Devuevle el mensaje en caso de mal
     * @return el mensaje de error
     */
    public String getMensaje();
    /**
     * Devuelve una lista de JListDatos que contienen los datos actualizados
     * @return Lista de elementos actualizados
     */
    public IListaElementos getListDatos();
    /**
     * Devuelve el Objeto en formato XML 
     * @return string xml
     */
    public String getXML();
     
}
