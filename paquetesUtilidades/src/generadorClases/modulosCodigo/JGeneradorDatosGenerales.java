/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;
import utiles.*;

public class JGeneradorDatosGenerales implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyect;
        
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorDatosGenerales(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyect = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JDatosGenerales.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);

        lsText.append("import " + moConex.getDirPadre() + "."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);

        if(moProyect.getOpciones().isEdicionFX()){
            lsText.append("import utilesFX.aplicacion.JDatosGeneralesAplicacion;");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("import utilesGUIx.aplicacion.*;");lsText.append(JUtiles.msRetornoCarro);

        }
    
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("public class JDatosGenerales extends JDatosGeneralesAplicacion {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private boolean mbPrimeraVezAvisos1=false;");
//        lsText.append("    private JDevolverTextos moTextos=null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of JUsuarioActual */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JDatosGenerales() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("    public JDevolverTextos getTextosForms(){");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        if(moTextos==null){");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("            moTextos = new JDevolverTextos(ResourceBundle.getBundle(\"CaptionTablas\"));");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        return moTextos;");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public synchronized utilesGUIxAvisos.calendario.JDatosGenerales getTareasAvisos1() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        utilesGUIxAvisos.calendario.JDatosGenerales loAvisos = super.getTareasAvisos1();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(!mbPrimeraVezAvisos1){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            mbPrimeraVezAvisos1 = true;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loAvisos.addAvisosListener(new JutilesFXAvisosGUIxAvisos());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);        
        lsText.append("        return loAvisos; ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }        ");lsText.append(JUtiles.msRetornoCarro);
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JDatosGenerales.java";
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
