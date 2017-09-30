/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import java.io.File;
import utiles.*;

public class JGeneradorALogin implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades

    public JGeneradorALogin(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * Login.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ".servletAcciones;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesBD.servletAcciones.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class ALogin implements IAccion{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final String mcsLoginError = \"loginError.jsp\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of Login */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public ALogin() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //recuperamos los datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        String lsLogin = (String)request.getParameter(\"login\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        String lsPass = (String)request.getParameter(\"pass\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //comprobamos los null");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(lsLogin == null) return mcsLoginError;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(lsPass == null) lsPass = \"\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //recuperamos el usuario");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JTUSUARIOS loUsu = new JTUSUARIOS(poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loUsu.recuperarFiltrados(new JListDatosFiltroElem(JListDatos.mclTIgual,new int[]{JTUSUARIOS.lPosiLOGIN}, new String[]{lsLogin}), false, false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //comprobamos contraseNa");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loUsu.moList.moveFirst()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(lsPass.compareTo(loUsu.getPASSWORD().getString())==0){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                request.getSession(true).setAttribute(\"Usuario\", new Usuario(loUsu.getNOMBRE().getString(),loUsu.getCODIGOUSUARIO().getInteger(), loUsu.getPERMISO().getInteger()));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                return \"consulta.jsp\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }else");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                return mcsLoginError;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return mcsLoginError;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public boolean getNecesitaValidar(Usuario poUsuario) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public boolean getConexionEdicion() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public boolean getNecesitaConexionBD() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return true;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }

    public String getRutaRelativa() {
        return "servletAcciones";
    }

    public String getNombre() {
        return "ALogin.java";
    }

    public boolean isGeneral() {
        return true;
    }

    public String getNombreModulo() {
        return getNombre();
    }

    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }
}
