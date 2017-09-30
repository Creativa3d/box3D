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

public class JGeneradorJTableAntig implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;
        
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorJTableAntig(JProyecto poProyec) {
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
                    " = " + String.valueOf(i) +";");lsText.append(JUtiles.msRetornoCarro);
        }
    
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Variable nombre de tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final String msCTabla=\"" +loTabla.getNombre()+ "\";");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * NUmero de campos de la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int mclNumeroCampos="+loCampos.count()+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Nombres de la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final String[] masNombres=    new String[] {");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(i == (loCampos.count()-1)){
                lsText.append("        \"" + loCampos.get(i).getNombre() + 
                        "\"");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        \"" + loCampos.get(i).getNombre() + 
                        "\",");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("    };");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int[] malTipos=    new int[] {");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(i == (loCampos.count()-1)){
                lsText.append("        " + moUtiles.msTipo(loCampos.get(i).getTipo()) + "");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        " + moUtiles.msTipo(loCampos.get(i).getTipo()) + ",");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("    };");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    public static final int[] malTamanos=    new int[] {");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(i == (loCampos.count()-1)){
                lsText.append("        " + String.valueOf(loCampos.get(i).getTamano()) + "");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        " + String.valueOf(loCampos.get(i).getTamano()) + ",");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("    };");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int[] malCamposPrincipales=    new int[] {");lsText.append(JUtiles.msRetornoCarro);
        int[] lalCamposP = loCampos.malCamposPrincipales();
        if(lalCamposP!=null){
            for(int i = 0; i < lalCamposP.length; i++ ){
                if(i == (lalCamposP.length-1)){
                    lsText.append("        lPosi" + moUtiles.msSustituirRaros(loCampos.get(lalCamposP[i]).getNombre()) + "");lsText.append(JUtiles.msRetornoCarro);
                }else{
                    lsText.append("        lPosi" + moUtiles.msSustituirRaros(loCampos.get(lalCamposP[i]).getNombre()) + ",");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        lsText.append("    };");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JT"+ moUtiles.msSustituirRaros(loTabla.getNombre())+"(IServerServidorDatos poServidorDatos) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.getFields().setTabla(msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.addListener(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Crea una instancia de la clase intermedia para la tabla FAMILIAS");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JT"+ moUtiles.msSustituirRaros(loTabla.getNombre())+"() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      this(null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        

        for(int i = 0; i < loCampos.count(); i++ ){
            String lsCampo = moUtiles.msSustituirRaros(loCampos.get(i).getNombre());
            lsText.append("     public JFieldDef get"+lsCampo+"(){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          return moList.getFields().get(lPosi"+lsCampo+");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("     }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("     public static String get"+lsCampo+"Nombre(){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          return  masNombres[lPosi"+lsCampo+"];");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("     }");lsText.append(JUtiles.msRetornoCarro);
        }

     
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    *recupera un objeto select segun la informaciOn actual");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    *@return objeto select");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     public static JSelect getSelectStatico(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("         JSelect loSelect = new JSelect(msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("         for(int i = 0; i< masNombres.length; i++){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("           loSelect.addCampo(msCTabla, masNombres[i]);");lsText.append(JUtiles.msRetornoCarro);
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
