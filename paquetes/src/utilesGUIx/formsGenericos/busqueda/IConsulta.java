/*
 * IConsulta.java
 *
 * Created on 7 de octubre de 2004, 13:48
 */

package utilesGUIx.formsGenericos.busqueda;

import ListDatos.*;

/**Interfaz para busqueda de datos*/
public interface IConsulta {
    /**
     * Refresca el JListDatos
     * @param pbPasarACache si pasa a cache
     * @param pbLimpiarCache si limpia la cache
     * @throws Exception error
     */
    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception;
    /**
     * Devuelve el JListDatos
     * @return datos
     */
    public JListDatos getList();
    /**
     * Add una fila por clave
     * @param poFila fila
     * @throws Exception error
     */
    public void addFilaPorClave(IFilaDatos poFila) throws Exception;
    /**
     * Devuelve si es una tabla para pasar a cache
     * @return 
     */
    public boolean getPasarCache();
    
}
