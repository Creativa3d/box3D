/*
* JTSQLGENERADOR.java
*
*/

package paquetesGeneradorInf.gest1.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTSQLGENERADOR extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOSQLGENERADOR = 0;
      public static final int lPosiNOMBRE = 1;
      public static final int lPosiPALABRASCLAVE = 2;
      public static final int lPosiPADRE = 3;
      public static final int lPosiTABLAPRINCIPAL = 4;
      public static final int lPosiSQL = 5;
      public static final int lPosiOBSERVACIONES = 6;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="SQLGENERADOR";
     /**
      * NUmero de campos de la tabla
      */
    public static final int mclNumeroCampos=7;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CODIGOSQLGENERADOR",
        "NOMBRE",
        "PALABRASCLAVE",
        "PADRE",
        "TABLAPRINCIPAL",
        "SQL",
        "OBSERVACIONES"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static final int[] malTamanos=    new int[] {
        10,
        255,
        255,
        255,
        255,
        1073741823,
        1073741823
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOSQLGENERADOR
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTSQLGENERADOR(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTSQLGENERADOR() {
      this(null);
    }
     public JFieldDef getCODIGOSQLGENERADOR(){
          return moList.getFields().get(lPosiCODIGOSQLGENERADOR);
     }
     public static String getCODIGOSQLGENERADORNombre(){
          return  masNombres[lPosiCODIGOSQLGENERADOR];
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return  masNombres[lPosiNOMBRE];
     }
     public JFieldDef getPALABRASCLAVE(){
          return moList.getFields().get(lPosiPALABRASCLAVE);
     }
     public static String getPALABRASCLAVENombre(){
          return  masNombres[lPosiPALABRASCLAVE];
     }
     public JFieldDef getPADRE(){
          return moList.getFields().get(lPosiPADRE);
     }
     public static String getPADRENombre(){
          return  masNombres[lPosiPADRE];
     }
     public JFieldDef getTABLAPRINCIPAL(){
          return moList.getFields().get(lPosiTABLAPRINCIPAL);
     }
     public static String getTABLAPRINCIPALNombre(){
          return  masNombres[lPosiTABLAPRINCIPAL];
     }
     public JFieldDef getSQL(){
          return moList.getFields().get(lPosiSQL);
     }
     public static String getSQLNombre(){
          return  masNombres[lPosiSQL];
     }
     public JFieldDef getOBSERVACIONES(){
          return moList.getFields().get(lPosiOBSERVACIONES);
     }
     public static String getOBSERVACIONESNombre(){
          return  masNombres[lPosiOBSERVACIONES];
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
