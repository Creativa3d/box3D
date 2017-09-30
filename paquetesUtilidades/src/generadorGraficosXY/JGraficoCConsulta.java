 
/*
 * 
 *
 * Created on 17 de marzo de 2004, 18:54
 */

package generadorGraficosXY;

import ListDatos.*;
import ListDatos.estructuraBD.*;

import utiles.*;
import utilesGUI.tabla.*;
import utiles.*;


public class JGraficoCConsulta extends JSTabla {
     /**
      * Variables para las posiciones de los campos
      */
     
    public static final int lPosiCodigo=0;
    public static final int lPosiGrupo=1;
    public static final int lPosiFecha=2;
    public static final int lPosiValor=3;


    
     /**
      * Variable nombre de tabla
      */
    public static String msCTabla="CONSULTAGRAFICOS";

     /**
      * Número de campos de la tabla
      */
    public static int mclNumeroCampos=3;
     /**
      * Nombres de la tabla
      */
    public static String[] masNombres=    new String[] {
        "Codigo", "Grupo", "Fecha", "Valor"
    };
    public static int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoFecha, JListDatos.mclTipoNumeroDoble
    };
    public static int[] malTamanos=    new int[] {
        255,255, 0, 0
    };
    
    
    public static int[] malCamposPrincipales=    new int[] {0,1,2};
    
    /**
     * Crea una instancia de la clase intermedia para la tabla ANALITICAS incluyendole el servidor de datos
     */
    public JGraficoCConsulta(JServerServidorDatos poServidorDatos) {
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }

     /**
      * Crea una instancia de la clase intermedia para la tabla ANALITICAS
      */
    public JGraficoCConsulta() {
      this(null);
    }


    public JFieldDef getCodigo(){
        return moList.getFields(lPosiCodigo);
    }
    public JFieldDef getGrupo(){
        return moList.getFields(lPosiGrupo);
    }
    public JFieldDef getFecha(){
        return moList.getFields(lPosiFecha);
    }
    public JFieldDef getValor(){
        return moList.getFields(lPosiValor);
    }
}
