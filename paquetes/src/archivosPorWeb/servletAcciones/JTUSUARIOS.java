/*
* JTUSUARIOS.java
*
* Creado el 13/11/2007
*/

package archivosPorWeb.servletAcciones;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTUSUARIOS extends JSTabla {
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiLOGIN = 0;
      public static final int lPosiCLAVE = 1;
      public static final int lPosiRUTA = 2;
      public static final int lPosiACTIVO = 3;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="USUARIOS.properties";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=4;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "LOGIN",
        "CLAVE",
        "RUTA",
        "ACTIVO"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoBoolean
    };
    public static final int[] malTamanos=    new int[] {
        255,
        50,
        255,
        1
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiLOGIN
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTUSUARIOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTUSUARIOS() {
      this(null);
    }
     public JFieldDef getLOGIN(){
          return moList.getFields().get(lPosiLOGIN);
     }
     public static String getLOGINNombre(){
          return  masNombres[lPosiLOGIN];
     }
     public JFieldDef getCLAVE(){
          return moList.getFields().get(lPosiCLAVE);
     }
     public static String getCLAVENombre(){
          return  masNombres[lPosiCLAVE];
     }
     public JFieldDef getRUTA(){
          return moList.getFields().get(lPosiRUTA);
     }
     public static String getRUTANombre(){
          return  masNombres[lPosiRUTA];
     }
     public JFieldDef getACTIVO(){
          return moList.getFields().get(lPosiACTIVO);
     }
     public static String getACTIVONombre(){
          return  masNombres[lPosiACTIVO];
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
