/*
 * ALoginAplicacion.java
 *
 * Created on 9 de septiembre de 2004, 9:05
 */

package archivosPorWeb.servletAcciones;
import java.io.*;
import utiles.JDepuracion;
import utilesBD.servletAcciones.*;

public class ALoginAplicacion extends JAccionAbstract   {
    private String msDir;
        
    /** Creates a new instance of ALoginAplicacion */
    public ALoginAplicacion(String psDir) {
        msDir = psDir;
    }

    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer) throws Exception {
        PrintWriter out = response.getWriter();
        
        IAccion loLogin = new ALogin(msDir);
        try{
            
            //ejecuta la accion y obtiene la vista
            String lsVista = loLogin.ejecutar(request, response, poServletContext, poServer);
            if(lsVista.compareTo(ALogin.mcsLoginError)!=0){
                out.println("1");
                out.println(request.getSession(false).getId());
            }else
                out.println("0");
        }catch(Exception e){
            e.printStackTrace(response.getWriter());
            JDepuracion.anadirTexto(getClass().getName(), e);
        }finally{
        }
        
        out.close();
        return null;
    }
    
    public boolean getNecesitaValidar(Usuario poUsuario) {
        return false;
    }

    public boolean getConexionEdicion() {
        return false;
    }
    public boolean getNecesitaConexionBD() {
        return false;
    }
        
}
