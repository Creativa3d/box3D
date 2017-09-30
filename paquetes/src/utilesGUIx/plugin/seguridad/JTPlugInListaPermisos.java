/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JFieldDef;

public class JTPlugInListaPermisos extends JSTabla {
    private static final long serialVersionUID = 523551429889358847L;

     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOGRUPOUSUARIO = 0;
      public static final int lPosiOBJETO = 1;
      public static final int lPosiCAPTIONOBJETO = 2;
      public static final int lPosiACCION = 3;
      public static final int lPosiACTIVOSN = 4;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="LISTAPERMISOS";
     /**
      * Numero de campos de la tabla
      */
    public static final int mclNumeroCampos=5;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
      "CODIGOGRUPOUSUARIO",
      "OBJETO",
      "TITULOOBJETO",
      "ACCION",
      "ACTIVOSN"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoBoolean
    };
    public static final int[] malTamanos=    new int[] {
        255,
        255,
        255,
        255,
        0
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOGRUPOUSUARIO,
        lPosiOBJETO,
        lPosiACCION
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTPlugInListaPermisos (IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTPlugInListaPermisos () {
      this(null);
    }
    
     public JFieldDef getCODIGOGRUPOUSUARIO(){
          return moList.getFields().get(lPosiCODIGOGRUPOUSUARIO);
     }
     public static String getCODIGOGRUPOUSUARIONombre(){
          return  masNombres[lPosiCODIGOGRUPOUSUARIO];
     }
     public JFieldDef getOBJETO(){
          return moList.getFields().get(lPosiOBJETO);
     }
     public JFieldDef getCAPTIONOBJETO(){
          return moList.getFields().get(lPosiCAPTIONOBJETO);
     }
     public static String getOBJETONombre(){
          return  masNombres[lPosiOBJETO];
     }
     public JFieldDef getACTIVOSN(){
          return moList.getFields().get(lPosiACTIVOSN);
     }
     public static String getACTIVOSNNombre(){
          return  masNombres[lPosiACTIVOSN];
     }
     public JFieldDef getACCION(){
          return moList.getFields().get(lPosiACCION);
     }
     public static String getACCIONNombre(){
          return  masNombres[lPosiACCION];
     }

    public JTPlugInListaPermisos getPermisosEliminarDuplicadosMenosPermisivos() throws Exception{
        return JTPlugInListaPermisosUtil.getPermisosEliminarDuplicadosMenosPermisivos(this);
    }

}


