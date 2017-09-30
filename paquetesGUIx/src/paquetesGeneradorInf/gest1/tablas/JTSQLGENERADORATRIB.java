/*
* JTSQLGENERADORATRIB.java
*
*/

package paquetesGeneradorInf.gest1.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTSQLGENERADORATRIB extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOSQLGENERADOR = 0;
      public static final int lPosiATRIBUTODEF = 1;
      public static final int lPosiVALOR = 2;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="SQLGENERADORATRIB";
     /**
      * NUmero de campos de la tabla
      */
    public static final int mclNumeroCampos=3;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CODIGOSQLGENERADOR",
        "ATRIBUTODEF",
        "VALOR"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static final int[] malTamanos=    new int[] {
        10,
        50,
        255
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOSQLGENERADOR,
        lPosiATRIBUTODEF
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTSQLGENERADORATRIB(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTSQLGENERADORATRIB() {
      this(null);
    }
     public JFieldDef getCODIGOSQLGENERADOR(){
          return moList.getFields().get(lPosiCODIGOSQLGENERADOR);
     }
     public static String getCODIGOSQLGENERADORNombre(){
          return  masNombres[lPosiCODIGOSQLGENERADOR];
     }
     public JFieldDef getATRIBUTODEF(){
          return moList.getFields().get(lPosiATRIBUTODEF);
     }
     public static String getATRIBUTODEFNombre(){
          return  masNombres[lPosiATRIBUTODEF];
     }
     public JFieldDef getVALOR(){
          return moList.getFields().get(lPosiVALOR);
     }
     public static String getVALORNombre(){
          return  masNombres[lPosiVALOR];
     }
    /**
    *recupera un objeto select segun la informaciOn actual
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
