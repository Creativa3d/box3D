/*
* JTGUIXPRIORIDAD.java
*
*/

package utilesGUIxAvisos.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTGUIXEVENTOSPRIORIDAD extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiGUIXPRIORIDAD = 0;
      public static final int lPosiNOMBRE = 1;
      public static final int lPosiCOLOR = 2;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="GUIXEVENTOSPRIORIDAD";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=3;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "GUIXPRIORIDAD",
        "NOMBRE",
        "COLOR"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static final int[] malTamanos=    new int[] {
        10,
        255,
        50
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiGUIXPRIORIDAD
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTGUIXEVENTOSPRIORIDAD(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTGUIXEVENTOSPRIORIDAD() {
      this(null);
    }
     public JFieldDef getGUIXPRIORIDAD(){
          return moList.getFields().get(lPosiGUIXPRIORIDAD);
     }
     public static String getGUIXPRIORIDADNombre(){
          return  masNombres[lPosiGUIXPRIORIDAD];
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return  masNombres[lPosiNOMBRE];
     }
     public JFieldDef getCOLOR(){
          return moList.getFields().get(lPosiCOLOR);
     }
     public static String getCOLORNombre(){
          return  masNombres[lPosiCOLOR];
     }
    /**
    *recupera un objeto select segun la información actual
    *@return objeto select
    */
     public static JSelect getSelectStatico(){
         JSelect loSelect = new JSelect(msCTabla);
         for(int i = 0; i< masNombres.length; i++){
           loSelect.addCampo(msCTabla, masNombres[i]);
         }
         return loSelect;
     }
}
