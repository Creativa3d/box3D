/*
 * IFormEdicionNavegador.java
 *
 * Created on 11 de abril de 2005, 16:48
 */

package utilesGUIx.formsGenericos.edicion;

import ListDatos.*;

/**Interfaz para usar el formulario edicion con navegacion general*/
public interface IFormEdicionNavegador extends IFormEdicion {
    /**
     * Recuperamos los datos en funcion del registro actual
     * @throws Exception error
     */
    public void recuperarDatos() throws Exception;
    
    /**
     * Devuelve la lista de elementos clonados, desconectados del formulario principal y del servidor
     * @return 
     * @throws Exception error
     */
    public JListDatos getDatos() throws Exception;
    
    /**
     * Bloquear/Desbloquear los controles
     * @param pbBloqueo
     * @throws java.lang.Exception
     */
    public void setBloqueoControles(boolean pbBloqueo) throws Exception;

    /**
     * nuevo registro
     * @throws Exception error
     */
    public void nuevo() throws Exception;
    
    /**
     * editar registro
     * @throws Exception error
     */
    public void editar() throws Exception;
    
    /**
     * borrar registro, luego se hace el recuperar+mostrar
     * @throws Exception error
     */
    public void borrar() throws Exception;
    
    /**
     * buscamos registro, luego se hace el recuperar+mostrar
     * @throws Exception error
     */
    public void buscar() throws Exception;
    
    /**
     * refrescar conj. de registros, luego se hace el recuperar+mostrar
     * @throws Exception error
     */
    public void refrescar() throws Exception;
    


}
