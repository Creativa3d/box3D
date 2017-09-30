/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;
import utiles.*;

public class JGeneradorGestionProyectoNuevo implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorGestionProyectoNuevo(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JGestionProyecto.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablasControladoras.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + "."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("public class JGestionProyecto  extends JGestionProyectoPorTablas { ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of JGestionProyecto */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JGestionProyecto() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0 ; i <  moConex.getTablasBD().getListaTablas().size(); i++){
            JTableDef loTabla = (JTableDef) moConex.getTablasBD().getListaTablas().get(i);
            JFieldDefs loCampos = loTabla.getCampos();
             if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
                lsText.append("        addTabla("+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".msCTabla,"
                    + " new "+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".GestionProyectoTabla());");lsText.append(JUtiles.msRetornoCarro);
             }else{

                lsText.append("        addTabla("+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".msCTabla,"
                    + " new JT2"+moUtiles.msSustituirRaros(loTabla.getNombre()) +".GestionProyectoTabla());");lsText.append(JUtiles.msRetornoCarro);

             }
        }
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();        
    } 
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JGestionProyecto.java";
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
