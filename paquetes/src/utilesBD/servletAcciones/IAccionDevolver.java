/*
 * IAccionDevolver.java
 *
 * Created on 22 de noviembre de 2004, 8:36
 */

package utilesBD.servletAcciones;

import ListDatos.IServerServidorDatos;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**interfaz que debe cumplir el que devuelve un accion entre una lista de acciones*/
public interface IAccionDevolver extends Serializable {
    /**
     * Se llama antes que a ninguna accion y cuando arranca el controlador por primera vez, SE PUEDE LLAMAR 2 VECES
     */
    public void inicializar(ServletContext config) throws Throwable;
    /**
     * devuelve la accion concreta en funcion del nombre de la accion
     * @return Accion concreta
     * @param psAccion nombre accion
     * @param psDir directorio base
     */
    public IAccion getAccion(String psAccion, String psDir);
    /**
     * devuelve el servidor de la Base de datos
     */
    public IServerServidorDatos getServidor(
            boolean pbEdicion
            ) throws Throwable;

    /**
     * aplica filtros al servidor
     */
    public void aplicarFiltros(
            IServerServidorDatos poServer,
            boolean pbEdicion,
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext poServletContext,
            Usuario poUsuario
            ) throws Throwable;
    
    /**
     * desaplica filtros al servidor
     */
    public void desAplicarFiltros(
            IServerServidorDatos poServer,
            boolean pbEdicion,
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext poServletContext,
            Usuario poUsuario
            )throws Throwable;

    
    /**
     * Se llama cuando se destrulle el controlador, SE PUEDE LLAMAR 2 VECES
     */
    public void terminar(ServletContext config) throws Throwable;
}
