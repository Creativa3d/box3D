/*
 * Login.java
 *
 * Created on 5 de mayo de 2004, 16:57
 */
package archivosPorWeb.servletAcciones;


import ListDatos.*;
import utilesBD.servletAcciones.*;

public class ALogin extends JAccionAbstract  {
    public static final String mcsLoginError = "loginError.jsp";
    private String msDir;
    
    /** Creates a new instance of Login */
    public ALogin(String psDir) {
        msDir = psDir;
    }
    
    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer1) throws Exception {
        //recuperamos los datos
        String lsLogin = (String)request.getParameter("login");
        String lsPass = (String)request.getParameter("pass");
        
        //comprobamos los null
        if(lsLogin == null) return mcsLoginError;
        if(lsPass == null) lsPass = "";
        
        JServerServidorDatosFichero loServer = new JServerServidorDatosFichero(msDir + "WEB-INF/classes/", ';', true);
        
        //recuperamos el usuario
        JTUSUARIOS loUsu = new JTUSUARIOS(loServer);
        loUsu.recuperarTodosNormalSinCache();
        
        loUsu.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND, 
                new JListDatosFiltroElem(
                    JListDatos.mclTIgual,
                    new int[]{JTUSUARIOS.lPosiLOGIN}, 
                    new String[]{lsLogin})
                    );
        loUsu.moList.filtrar();
        
        //comprobamos contraseña
        if(loUsu.moList.moveFirst()){
            if(lsPass.compareTo(loUsu.getCLAVE().getString())==0){
                request.getSession(true).setAttribute("Usuario", new UsuarioFicheros(loUsu.getLOGIN().getString(), loUsu.getRUTA().getString()));
                return "consulta.jsp";
            }else
                return mcsLoginError;
        }else{
            return mcsLoginError;
        }
        
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
