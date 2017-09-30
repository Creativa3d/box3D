/*
* JTTABLASINCRONIZACIONBORRADOS.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTTABLASINCRONIZACIONBORRADOS extends JSTabla {
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOBORRADO = 0;
      public static final int lPosiTABLA = 1;
      public static final int lPosiREGISTRO = 2;
      public static final int lPosiNUMEROTRANSACSINCRO = 3;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="TablaSincronizacionBorrados";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=4;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "codigoBorrado",
        "TABLA",
        "REGISTRO",
        "NumeroTransacSincro"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoNumero
    };
    public static final int[] malTamanos=    new int[] {
        10,
        100,
        1073741823,
        10
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOBORRADO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTTABLASINCRONIZACIONBORRADOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTTABLASINCRONIZACIONBORRADOS() {
      this(null);
    }
     public JFieldDef getCODIGOBORRADO(){
          return moList.getFields().get(lPosiCODIGOBORRADO);
     }
     public static String getCODIGOBORRADONombre(){
          return  masNombres[lPosiCODIGOBORRADO];
     }
     public JFieldDef getTABLA(){
          return moList.getFields().get(lPosiTABLA);
     }
     public static String getTABLANombre(){
          return  masNombres[lPosiTABLA];
     }
     public JFieldDef getREGISTRO(){
          return moList.getFields().get(lPosiREGISTRO);
     }
     public static String getREGISTRONombre(){
          return  masNombres[lPosiREGISTRO];
     }
     public JFieldDef getNUMEROTRANSACSINCRO(){
          return moList.getFields().get(lPosiNUMEROTRANSACSINCRO);
     }
     public static String getNUMEROTRANSACSINCRONombre(){
          return  masNombres[lPosiNUMEROTRANSACSINCRO];
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
