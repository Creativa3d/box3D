/*
* JTTABLASINCRONIZACIONGENERAL.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTTABLASINCRONIZACIONGENERAL extends JSTabla {
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiNOMBRE = 0;
      public static final int lPosiVALOR = 1;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="TablaSincronizacionGeneral";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=2;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "Nombre",
        "Valor"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static final int[] malTamanos=    new int[] {
        50,
        1073741823
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiNOMBRE
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTTABLASINCRONIZACIONGENERAL(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTTABLASINCRONIZACIONGENERAL() {
      this(null);
    }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return  masNombres[lPosiNOMBRE];
     }
     public JFieldDef getVALOR(){
          return moList.getFields().get(lPosiVALOR);
     }
     public static String getVALORNombre(){
          return  masNombres[lPosiVALOR];
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
