/*
 * IAccion.java
 *
 * Created on 4 de mayo de 2004, 19:03
 */

package utilesBD.servletAcciones;


import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import ListDatos.*;
/**interfaz accion, lo deben cumplir todo lo el que quiera ejecutar algo en el servidor*/
public interface IAccion extends Serializable {
    /**
     * ejecuta la accion y devuelve la vista (jsp)
     * @param request petion servelt
     * @param response respueta servelt
     * @param poServletContext contexto servlet
     * @param poServer servidor datos
     * @throws Exception Exception 
     * @return vista
     */
    public String ejecutar(HttpServletRequest request, HttpServletResponse response, ServletContext poServletContext, IServerServidorDatos poServer) throws  Exception;
    /**
     * indica si necesita validar
     * @return si necesita validar
     * @param poUsuario usuario
     */
    public boolean getNecesitaValidar(Usuario poUsuario);
    
    /**
     * Indica si la conexion a usar debe ser la de edicion
     * @return si conexion es edicion
     */
    public boolean getConexionEdicion();
    
    /**
     * Indica si necesita conexion con una bd para funcionar
     * @return si necesita conexion 
     */
    public boolean getNecesitaConexionBD();
}
