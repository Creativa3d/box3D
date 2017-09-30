/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;


import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTPlugInUsuariosGrupos extends JSTabla {
    private static final long serialVersionUID = -3677199664537916938L;

     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOUSUARIO = 0;
      public static final int lPosiCODIGOGRUPO = 1;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="USUARIOSGRUPOS";
     /**
      * Numero de campos de la tabla
      */
    public static final int mclNumeroCampos=2;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CodigoUsuario",
        "CODIGOGRUPO"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static final int[] malTamanos=    new int[] {
        255,
        255
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOUSUARIO,
        lPosiCODIGOGRUPO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTPlugInUsuariosGrupos(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTPlugInUsuariosGrupos() {
      this(null);
    }
     public JFieldDef getCODIGOUSUARIO(){
          return moList.getFields().get(lPosiCODIGOUSUARIO);
     }
     public static String getCODIGOUSUARIONombre(){
          return  masNombres[lPosiCODIGOUSUARIO];
     }
     public JFieldDef getCODIGOGRUPO(){
          return moList.getFields().get(lPosiCODIGOGRUPO);
     }
     public static String getCODIGOGRUPONombre(){
          return  masNombres[lPosiCODIGOGRUPO];
     }
 
}
