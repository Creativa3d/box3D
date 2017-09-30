/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import ListDatos.estructuraBD.*;
import ListDatos.*;
import utilesGUIx.formsGenericos.busqueda.*;

public class JTPlugInGrupos  extends JSTabla implements IConsulta {
    public static final String mcsAdministradores = "Administradores";
    private static final long serialVersionUID = 7837968855040457610L;

     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOGRUPO = 0;
      public static final int lPosiNOMBRE = 1;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="GRUPOS";
     /**
      * Numero de campos de la tabla
      */
    public static final int mclNumeroCampos=2;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CodigoGRUPO",
        "NOMBRE"
    };
    public static final String[] masCaption=    new String[] {
        "Codigo grupo",
        "Nombre"
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
        lPosiCODIGOGRUPO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTPlugInGrupos(IServerServidorDatos poServidorDatos) {
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
    public JTPlugInGrupos () {
      this(null);
    }
     public JFieldDef getCODIGOGRUPO(){
          return moList.getFields().get(lPosiCODIGOGRUPO);
     }
     public static String getCODIGOGRUPONombre(){
          return  masNombres[lPosiCODIGOGRUPO];
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return  masNombres[lPosiNOMBRE];
     }
     //debe existir un administradores, si no se le Add
     public void addAdministradores() throws ECampoError{
         if(!moList.buscar(JListDatos.mclTIgual, lPosiNOMBRE, mcsAdministradores)){
             moList.addNew();
             getCODIGOGRUPO().setValue("");
             getNOMBRE().setValue(mcsAdministradores);
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
    }
}
