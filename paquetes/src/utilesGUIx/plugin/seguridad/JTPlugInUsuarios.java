/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;


import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.*;

public class JTPlugInUsuarios extends JSTabla  implements IConsulta {
    public static final String mcsAdministrador="Administrador";
    private static final long serialVersionUID = 3526741372721616324L;

     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOUSUARIO = 0;
      public static final int lPosiNOMBRE = 1;
      public static final int lPosiPASSWORD = 2;
      public static final int lPosiACTIVOSN = 3;
      public static final int lPosiNOMBRECOMPLETO = 4;
      
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="USUARIOS";
     /**
      * Numero de campos de la tabla
      */
    public static final int mclNumeroCampos=4;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CodigoUsuario",
        "NOMBRE",
        "PASSWORD",
        "ACTIVOSN",
        "NOMBRECOMPLETO"
    };
     /**
      * Nombres de la tabla
      */
    public static final String[] masCaption=    new String[] {
        "Codigo usuario",
        "Login",
        "Password",
        "Es Activo?",
        "Nombre completo"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoBoolean,
        JListDatos.mclTipoCadena,
    };
    public static final int[] malTamanos=    new int[] {
        255,
        255,
        255,
        0,
        255
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOUSUARIO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTPlugInUsuarios(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(
                poServidorDatos,
                msCTabla,
                masNombres,
                malTipos,
                malCamposPrincipales,
                masCaption,
                malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTPlugInUsuarios() {
      this(null);
    }
     public JFieldDef getCODIGOUSUARIO(){
          return moList.getFields().get(lPosiCODIGOUSUARIO);
     }
     public static String getCODIGOUSUARIONombre(){
          return  masNombres[lPosiCODIGOUSUARIO];
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return  masNombres[lPosiNOMBRE];
     }

     public JFieldDef getPASSWORD(){
          return moList.getFields().get(lPosiPASSWORD);
     }
     public static String getPASSWORDNombre(){
          return  masNombres[lPosiPASSWORD];
     }
     public JFieldDef getACTIVOSN(){
          return moList.getFields().get(lPosiACTIVOSN);
     }
     public static String getACTIVOSNNombre(){
          return  masNombres[lPosiACTIVOSN];
     }

     public JFieldDef getNOMBRECOMPLETO(){
          return moList.getFields().get(lPosiNOMBRECOMPLETO);
     }
     public static String getNOMBRECOMPLETONombre(){
          return  masNombres[lPosiNOMBRECOMPLETO];
     }

     //debe existir un administrador, si no se le Add
     public void addAdministrador() throws ECampoError{
         if(!moList.buscar(JListDatos.mclTIgual, lPosiNOMBRE, mcsAdministrador)){
             moList.addNew();
             getCODIGOUSUARIO().setValue("");
             getNOMBRE().setValue(mcsAdministrador);
             getNOMBRECOMPLETO().setValue(mcsAdministrador);
             getPASSWORD().setValue("");
             getACTIVOSN().setValue(true);
             moList.update(false);
         }
     }
    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
    }

    public void addFilaPorClave(final IFilaDatos poFila) throws Exception {
        switch(poFila.getTipoModif()){
            case JListDatos.mclBorrar:
                moList.borrar(false);
                break;
            case JListDatos.mclEditar:
                moList.getFields().cargar(poFila);
                moList.update(false);
                break;
            case JListDatos.mclNuevo:
                moList.addNew();
                moList.getFields().cargar(poFila);
                moList.update(false);
                break;
            default:
                throw new Exception("Tipo modificacion incorrecto");
        }
    }

    public boolean getPasarCache() {
        return false;
    }

    public void validarCampos() throws Exception {
        super.validarCampos();
//        if(getCODIGOUSUARIO().isVacio()){
//            throw new Exception("El codigo de usuario no debe ser vacio");
//        }
    }

 
}
