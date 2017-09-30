/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class JGeneradorDatosGeneralesP implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
        
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorDatosGeneralesP(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JDatosGeneralesP.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.IPlugInFactoria;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.*;");lsText.append(JUtiles.msRetornoCarro);

        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JDatosGeneralesP {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JDatosGenerales moDatosGenerales=null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private JDatosGeneralesP() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static JDatosGenerales getDatosGenerales(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(moDatosGenerales==null){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moDatosGenerales = new JDatosGenerales();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moDatosGenerales;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static IPlugInFactoria getDatosGeneralesPlugIn(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moDatosGenerales.getDatosGeneralesPlugIn();        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    } ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
          
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JDatosGeneralesP.java";
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
