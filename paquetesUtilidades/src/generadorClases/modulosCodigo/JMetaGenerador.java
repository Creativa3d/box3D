/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;
import java.io.*;

public class JMetaGenerador implements IModuloProyecto {
    private LineNumberReader moRead;
    private final File moEntrada;
    
        
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JMetaGenerador(File poEntrada) throws FileNotFoundException {
        moEntrada = poEntrada;
        FileReader loBuf = new FileReader(poEntrada);
        moRead = new LineNumberReader(loBuf);
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        String lsLinea = "";
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* clase generada con metaGenerador ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        lsText.append("package generadorClases.modulosCodigo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import generadorClases.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JGeneradorJTable implements IModuloProyecto {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private JConexion moConex;  //Conexion a la base de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private JUtiles moUtiles;   //Clase de utilidades");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JGeneradorJTable(JProyecto poProyec) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moUtiles = new JUtiles(poProyec);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moConex = poProyec.getConex();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getCodigo() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        StringBuffer lsText = new StringBuffer(100);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        do {
            try {
                lsLinea = moRead.readLine();
            } catch (IOException ex) {
                break;
            }
            if(lsLinea != null) {
                lsText.append("        lsText.append(\"" + msRetocarLinea(lsLinea) + "\");lsText.append(JUtiles.msRetornoCarro);");lsText.append(JUtiles.msRetornoCarro);
            }
        } while(lsLinea != null);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return lsText.toString();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JModuloProyectoParametros getParametros() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JModuloProyectoParametros loParam = new JModuloProyectoParametros();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loParam;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getRutaRelativa() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return \".\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getNombre() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return \""+moEntrada.getName()+"\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public boolean isGeneral() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return true;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getNombreModulo() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return getNombre();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        return lsText.toString();        
    }
    
    private String msRetocarLinea(String linea) {
        int i;
        char c = 92;
        String a,sol = "";
        
        for(i=0;i<=linea.length()-1;i++) {
            a = linea.substring(i,i+1);
            if(a.compareTo("\"") == 0) sol += c;
            sol += a;
        }
        
        return sol;
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "MetaGenerador";
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
