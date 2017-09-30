/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles.red;

import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author eduardo
 */
public interface IOpenConnection {
    /**Devuelve una conexion de una URL pasada por parametro,por ejemplo para usar proxy o conexiones seguras*/
    public URLConnection getConnection(URL poURL) throws Exception;
}
