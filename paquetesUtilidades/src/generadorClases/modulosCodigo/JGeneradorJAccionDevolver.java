/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class JGeneradorJAccionDevolver implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyect;

    public JGeneradorJAccionDevolver(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyect = poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * JAccionDevolver.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        
        lsText.append("package " + moConex.getDirPadre() + ".servletAcciones;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesBD.servletAcciones.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".JDatosGeneralesP;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.servlet.ServletContext;");lsText.append(JUtiles.msRetornoCarro);
        
        if(moProyect.getOpciones().isEdicionFX()){
            lsText.append("import utilesFX.aplicacion.*;");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("import utilesGUIx.aplicacion.JParametrosAplicacion;");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JAccionDevolver  extends utilesBD.servletAcciones.JAccionDevolver  {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private boolean mbInicial = false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of JAccionDevolver */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JAccionDevolver() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void inicializar(ServletContext context) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super.inicializar(context); ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        synchronized(this){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(!mbInicial){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                mbInicial=true;");lsText.append(JUtiles.msRetornoCarro);
        if(moProyect.getOpciones().isEdicionFX()){
            lsText.append("                JParametrosAplicacion loParam = JMainFX.crearParametrosAplicacion();");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("                JParametrosAplicacion loParam = JMain.crearParametrosAplicacion();");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("                loParam.setEsServidor(true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                loParam.setPlugIn(new String[]{});");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                loParam.setFicheroConfiguracion(\"ConfigurationParameters\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                JDatosGeneralesP.getDatosGenerales().inicializar(loParam);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                JDatosGeneralesP.getDatosGenerales().setServer(new JServerServidorDevolverProxy(this));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moDatosGenerAvisos.setDatosRelacionados(true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moDatosGenerAvisos.addAvisosListener(new J"+moConex.getDirPadre()+"GUIxAvisos());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moDatosGenerAvisos.getPlanificador().arrancarTimer();        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moDatosGenerAvisos.getPlanificador().arrancarAvisos(false, true, true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moDatosGenerAvisos.getPlanificador().arrancarGMailImportar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moDatosGenerAvisos.getPlanificador().arrancarCorreoLector();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void terminar(ServletContext context) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(moDatosGenerAvisos!=null){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moDatosGenerAvisos.getPlanificador().cancelarTimer();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super.terminar(context); ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
            
        lsText.append("    public IAccion getAccion(String psAccion, String psDir) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if (psAccion.equalsIgnoreCase(\"login\") ) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return new ALogin();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } else if (psAccion.equalsIgnoreCase(\"loginAplicacion\") ) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return new ALoginAplicacion();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } else if(psAccion.equalsIgnoreCase(AEstadoServidor.mcsEstadoServidor)){");lsText.append(JUtiles.msRetornoCarro);
         lsText.append("           return new AEstadoServidor();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } else {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return super.getAccion(psAccion, psDir);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }
    public String getRutaRelativa() {
        return "servletAcciones";
    }

    public String getNombre() {
        return "JAccionDevolver.java";
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
