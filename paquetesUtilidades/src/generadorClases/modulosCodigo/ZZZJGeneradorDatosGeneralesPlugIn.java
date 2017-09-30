/*
* clase generada con metaGenerador 
*
* Creado el 24/2/2008
*/
package generadorClases.modulosCodigo;


import generadorClases.*;
import utiles.*;

public class ZZZJGeneradorDatosGeneralesPlugIn implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades

    public ZZZJGeneradorDatosGeneralesPlugIn(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * To change this template, choose Tools | Templates");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * and open the template in the editor.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JDatosGeneralesPlugIn implements IPlugInFactoria{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private IPlugInManager moPlugIngManager = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JDatosGeneralesPlugIn(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moPlugIngManager  = new JPlugInManager(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                new String[]{\"impresionJasper.plugin.JPlugInListadosJasper\"});");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public IPlugInContexto getPlugInContexto() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return JDatosGeneralesP.getDatosGenerales();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public IPlugInManager getPlugInManager(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moPlugIngManager;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JDatosGeneralaesPlugIn.java";
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
