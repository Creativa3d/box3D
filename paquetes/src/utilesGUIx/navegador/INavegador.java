/*
 * INavegador.java
 *
 * Created on 11 de abril de 2005, 11:13
 */

package utilesGUIx.navegador;

/**Interfaz para el navegador con las funciones basicas*/
public interface INavegador {
    
    /**
     * moverse al siguiente registro
     * @return si ha tenido exito
     * @throws Exception error
     */
    public boolean siguiente() throws Exception;
    /**
     * moverse al anterior registro
     * @return si ha tenido exito
     * @throws Exception error
     */
    public boolean anterior() throws Exception;
    /**
     * moverse al primero registro
     * @return si ha tenido exito
     * @throws Exception error
     */
    public boolean primero() throws Exception;
    /**
     * moverse al ultimo registro
     * @return si ha tenido exito
     * @throws Exception error
     */
    public boolean ultimo() throws Exception;
    
    /**
     * nuevo registro
     * @throws Exception error
     */
    public void nuevo() throws Exception;
    /**editar registro
     * @throws Exception error
     */
    public void editar() throws Exception;
    /**
     * borrar registro
     * @throws Exception error
     */
    public void borrar() throws Exception;
    
    /**
     * buscamos registro
     * @throws Exception error
     */
    public void buscar() throws Exception;
    
    /**refrescar conj. de registros
     * @throws Exception error
     */
    public void refrescar() throws Exception;
    /**
     * Indica si se ve el boton nuevo
     * @return si se ve
     */
    public boolean getNuevoSN();
    /**
     * Indica si se ve el boton editar
     * @return si se ve
     */
    public boolean getEditarSN();
    /**
     * Indica si se ve el boton borrar
     * @return si se ve
     */
    public boolean getBorrarSN();
    /**
     * Indica si se ve el boton refrescar
     * @return si se ve
     */
    public boolean getRefrescarSN();    
    /**
     * Indica si se ve el boton buscar
     * @return si se ve
     */
    public boolean getBuscarSN();    
    /**
     * boton aceptar
     * @throws Exception error
     */
    public void aceptar() throws Exception;
    /**
     * boton cancelar
     * @throws Exception error
     */
    public void cancelar() throws Exception;    
    /**
     * Indica si el navegador esta integrado con el form de edicion
     */
    public boolean getDentroFormEdicionSN();
    
}
