/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.servletAcciones;

import java.io.PrintWriter;
import utiles.JCadenas;
import utilesBD.poolConexiones.JPoolObjetosJMX;

/**
 *
 * @author eduardo
 */
public class AEstadoServidor  extends JAccionAbstract  {
    private static final long serialVersionUID = 1L;

    public static final String mcsEstadoServidor="estadoServidor";
    
    /** Creates a new instance of JEntradaComprimida */
    public AEstadoServidor() {
    }
    
    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer)  throws Exception{
        try{
            String lsCerrar = request.getParameter("cerrar");
            if(!JCadenas.isVacio(lsCerrar) && Boolean.valueOf(lsCerrar)){
                new JPoolObjetosJMX().cerrarTodasConex();
            }
            JPoolObjetosJMX jmx = new JPoolObjetosJMX();
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<p>");
            out.println(jmx.getConexion());
            out.println("</p>");
            out.println(jmx.getSQLActivas());
            out.println("<p>");
            out.println(" <a href=\"estadoServidor.ctrl?cerrar=true\">cerrar conexiones</a> ");
            out.println("</p>");
            try{
                out.println(jmx.estadoMemoria());
            } catch(Throwable e){
            }
            
            out.println("</html>");
            out.close();
        }catch(Exception e){
            PrintWriter out = response.getWriter();
            out.println("Error");
            out.close();
        }
        return null;
    }
    
    public boolean getNecesitaValidar(Usuario poUsuario) {
        return false;
    }

    public boolean getConexionEdicion() {
        return false;
    }
    public boolean getNecesitaConexionBD(){
        return false;
    }
    
    
}
