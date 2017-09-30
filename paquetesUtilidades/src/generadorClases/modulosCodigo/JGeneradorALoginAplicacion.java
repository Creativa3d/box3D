/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import java.io.File;
import utiles.*;

public class JGeneradorALoginAplicacion implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades

    public JGeneradorALoginAplicacion(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * ALoginAplicacion.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ".servletAcciones;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.io.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.net.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.servlet.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.servlet.http.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesBD.servletAcciones.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class ALoginAplicacion implements IAccion {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of ALoginAplicacion */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public ALoginAplicacion() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        PrintWriter out = response.getWriter();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IAccion loLogin = new ALogin();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            //ejecuta la accion y obtiene la vista");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            String lsVista = loLogin.ejecutar(request, response, poServletContext, poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(lsVista.compareTo(ALogin.mcsLoginError)!=0){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                out.println(\"1\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                out.println(request.getSession().getId());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }else");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                out.println(\"0\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            e.printStackTrace(response.getWriter());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            e.printStackTrace();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }finally{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        out.close();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return null;");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }


    public String getRutaRelativa() {
        return "servletAcciones";
    }

    public String getNombre() {
        return "ALoginAplicacion.java";
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
