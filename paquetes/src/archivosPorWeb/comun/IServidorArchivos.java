/*
 * IServidorArchivos.java
 *
 * Created on 3 de mayo de 2006, 16:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import java.io.InputStream;
import java.io.OutputStream;

import utiles.IListaElementos;

/** 
 * Interfaz servidor de archivos
 */
public interface IServidorArchivos {
    /**
     *devuelve las propiedades de un fichero
     * @param poPathCompleto
     * @return 
     * @throws java.lang.Exception
     */
    public JFichero getFichero(JFichero poPathCompleto) throws Exception;
    /**
     *devuelve la lista de ficheros de un directorio
     * @param poPathCompleto
     * @return 
     * @throws java.lang.Exception
     */
    public IListaElementos<JFichero> getListaFicheros(JFichero poPathCompleto) throws Exception;
    /**
     *Copia el fichero desde un origen a un destino
     * @param poServidorOrigen
     * @param poFicheroOrigen
     * @param poFicherodestino
     * @throws java.lang.Exception
     */
    public void copiarA(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception;
    /**
     *Borra fichero 
     * @param poFichero
     * @throws java.lang.Exception
     */
    public void  borrar(JFichero poFichero) throws Exception;
    /**
     *mueve el fichero desde un origen a un destino
     * @param poServidorOrigen
     * @param poFicheroOrigen
     * @param poFicherodestino
     * @throws java.lang.Exception
     */
    public void mover(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception;
    /**
     * Devuelve un flujo de lectura
     * @param poFichero
     * @return 
     * @throws java.lang.Exception
     */
    public InputStream getFlujoEntrada(JFichero poFichero) throws Exception;
    /**
     * Devuelve un flujo de salida
     * @param poFichero
     * @param pbAppend
     * @return 
     * @throws java.lang.Exception
     */
    public OutputStream getFlujoSalida(JFichero poFichero, boolean pbAppend) throws Exception;
    /**
     *crea una carpeta
     * @param poFichero
     * @throws java.lang.Exception
     */
    public void crearCarpeta(JFichero poFichero) throws Exception;
}
