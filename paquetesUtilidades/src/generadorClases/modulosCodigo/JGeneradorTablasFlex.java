/*
* clase generada con metaGenerador 
*
* Creado el 19/11/2012
*/

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;

public class JGeneradorTablasFlex implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public JGeneradorTablasFlex(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }

    @Override
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        JTableDef loTabla;
        JFieldDefs loCampos;

        loTabla = moConex.getTablaBD(moConex.getTablaActual());
        loCampos = moConex.getCamposBD(moConex.getTablaActual());
        

        lsText.append("package tablas {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JT" + moUtiles.msSustituirRaros(loTabla.getNombre()) + " {");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * Variables para las posiciones de los campos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("    public static const lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + 
                    ":int = " + String.valueOf(i) +";");lsText.append(JUtiles.msRetornoCarro);
        }
        
	lsText.append("    public function getCTabla():String {");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    	return(msCTabla);");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    public function getNumeroCampos():int {");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    	return(mclNumeroCampos);");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    public function getNombres():Array {");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    	return(masNombres);");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    public function getTipos():Array {");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    	return(malTipos);");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    public function getTamanos():Array {");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    	return(malTamanos);");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    public function getCamposPrincipales():Array {");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    	return(malTamanos);");lsText.append(JUtiles.msRetornoCarro);
	lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Variable nombre de tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public static const msCTabla:String=\""+moUtiles.msSustituirRaros(loTabla.getNombre())+"\";");lsText.append(JUtiles.msRetornoCarro);
        
        
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * NUmero de campos de la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static const mclNumeroCampos:int="+loCampos.count()+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Nombres de la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static const masNombres:Array = [");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(i == (loCampos.count()-1)){
                lsText.append("        \"" + loCampos.get(i).getNombre() + 
                        "\"");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        \"" + loCampos.get(i).getNombre() + 
                        "\",");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("    ];");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public static const malTipos:Array = [");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(i == (loCampos.count()-1)){
                lsText.append("        " + moUtiles.msTipoFlex(loCampos.get(i).getTipo()) + "");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        " + moUtiles.msTipoFlex(loCampos.get(i).getTipo()) + ",");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("    ];");lsText.append(JUtiles.msRetornoCarro);
        
        
        lsText.append("    public static const malTamanos:Array = [");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(i == (loCampos.count()-1)){
                lsText.append("        " + String.valueOf(loCampos.get(i).getTamano()) + "");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        " + String.valueOf(loCampos.get(i).getTamano()) + ",");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("    ];");lsText.append(JUtiles.msRetornoCarro);
        
        
        lsText.append("    public static const malCamposPrincipales:Array = [");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("    ];");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("};");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("};");lsText.append(JUtiles.msRetornoCarro);
        
        
        return lsText.toString();
    }

    @Override
    public String getRutaRelativa() {
        return "tablasFlex";
    }

    @Override
    public String getNombre() {
        return "JT" + moUtiles.msSustituirRaros(moConex.getTablaActual()) + ".as";
    }

    @Override
    public boolean isGeneral() {
        return false;
    }

    @Override
    public String getNombreModulo() {
        return "JTableFlex";
    }

    @Override
    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }
}
