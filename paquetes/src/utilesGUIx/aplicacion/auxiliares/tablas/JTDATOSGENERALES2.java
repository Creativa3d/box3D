/*
* JTDATOSGENERALES2.java
*
*/

package utilesGUIx.aplicacion.auxiliares.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTDATOSGENERALES2 extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiATRIBUTO = 0;
      public static final int lPosiVALOR = 1;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="DATOSGENERALES2";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=2;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "ATRIBUTO",
        "VALOR"
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
        lPosiATRIBUTO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTDATOSGENERALES2(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTDATOSGENERALES2() {
      this(null);
    }
     public JFieldDef getATRIBUTO(){
          return moList.getFields().get(lPosiATRIBUTO);
     }
     public static String getATRIBUTONombre(){
          return  masNombres[lPosiATRIBUTO];
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
