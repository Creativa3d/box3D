/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;
import ListDatos.estructuraBD.*;

public class JGeneradorJTable implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;
        
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorJTable(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        JTableDef loTabla = moConex.getTablaBD(moConex.getTablaActual());
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );;
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JT" + moUtiles.msSustituirRaros(loTabla.getNombre()) + ".java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + "." + "tablas;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.estructuraBD.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JDepuracion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //**************************************************
        
        //CUERPO DE LA CLASE*******************************************
        lsText.append("public class JT"+ moUtiles.msSustituirRaros(loTabla.getNombre())+" extends JSTabla {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static final long serialVersionUID = 1L;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Variables para las posiciones de los campos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("      public static final int lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + 
                    ";");lsText.append(JUtiles.msRetornoCarro);
        }
    
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Variable nombre de tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public static final String msCTabla=\"" + (moProyecto.getOpciones().isMayusculas() ? loTabla.getNombre().toUpperCase() : loTabla.getNombre())+ "\";");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * NUmero de campos de la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int mclNumeroCampos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Nombres de la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final String[] masNombres;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int[] malTipos;");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    public static final int[] malTamanos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int[] malCamposPrincipales;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static final JFieldDefs moCamposEstaticos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    static {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moCamposEstaticos = new JFieldDefs();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moCamposEstaticos.setTabla(msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        int lPosi = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            String lsTipo=moUtiles.msTipo(loCampos.get(i).getTipo());
            String lsNombre=(moProyecto.getOpciones().isMayusculas() ? loCampos.get(i).getNombre().toUpperCase() : loCampos.get(i).getNombre());
            String lsPrinci=String.valueOf(loCampos.get(i).getPrincipalSN());
            String lsTamano=String.valueOf(loCampos.get(i).getTamano());
            String lsNombreSinRaros = moUtiles.msSustituirRaros(loCampos.get(i).getNombre());
            lsText.append("        moCamposEstaticos.addField(new JFieldDef("+lsTipo+", \""+lsNombre+"\", \"\", "+lsPrinci+", "+lsTamano+"));");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        lPosi"+lsNombreSinRaros+" = lPosi;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        lPosi++;");lsText.append(JUtiles.msRetornoCarro);
        }        
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        mclNumeroCampos = moCamposEstaticos.size();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        malCamposPrincipales = moCamposEstaticos.malCamposPrincipales();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        masNombres = moCamposEstaticos.msNombres();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        malTamanos = moCamposEstaticos.malTamanos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        malTipos = moCamposEstaticos.malTipos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }        ");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Crea una instancia de la clase intermedia para la tabla incluyendole el servidor de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JT"+ moUtiles.msSustituirRaros(loTabla.getNombre())+"(IServerServidorDatos poServidorDatos) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList = new JListDatos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList.setFields(moCamposEstaticos.Clone());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList.msTabla = msCTabla;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList.moServidor=poServidorDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList.addListener(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (CloneNotSupportedException ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), ex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Crea una instancia de la clase intermedia para la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JT"+ moUtiles.msSustituirRaros(loTabla.getNombre())+"() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      this(null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static JFieldDefs getCamposEstaticos(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moCamposEstaticos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        

        for(int i = 0; i < loCampos.count(); i++ ){
            String lsCampo = moUtiles.msSustituirRaros(loCampos.get(i).getNombre());
            lsText.append("     public JFieldDef get"+lsCampo+"(){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          return moList.getFields().get(lPosi"+lsCampo+");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("     }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("     public static String get"+lsCampo+"Nombre(){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          return moCamposEstaticos.get(lPosi"+lsCampo+").getNombre();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("     }");lsText.append(JUtiles.msRetornoCarro);
        }

     
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    *recupera un objeto select segun la informaciOn actual");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    *@return objeto select");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     public static JSelect getSelectStatico(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("         JSelect loSelect = new JSelect(msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("         for(int i = 0; i< moCamposEstaticos.size(); i++){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("           loSelect.addCampo(msCTabla, moCamposEstaticos.get(i).getNombre());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("         }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("         return loSelect;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return "tablas";
    }

    public String getNombre() {
        return "JT" + moUtiles.msSustituirRaros(moConex.getTablaActual()) + ".java";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "JTable";
    }
    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }

}
