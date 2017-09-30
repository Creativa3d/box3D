/*
* JCUSUARIOS.java
*
* Creado el 24/5/2006
*/
package utilesGUIx.aplicacion.usuarios.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.aplicacion.usuarios.tablas.JTUSUARIOS;
import utilesGUIx.aplicacion.usuarios.tablasExtend.JTEEUSUARIOS;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JTFORMUSUARIOS extends JSTabla  implements IConsulta {
    JSelect moSelect;
    /**
     * Variables para las posiciones de los campos
     */
    public static int lPosiCODIGOUSUARIO = 0;
    public static int lPosiNOMBRE = 1;
    public static int lPosiPERMISO = 2;
    public static int lPosiACTIVO = 3;
    /**
     * Variable nombre de tabla
     */
    public static String msCTabla="USUARIOS";
    /**
     * Número de campos de la tabla
     */
    public static int mclNumeroCampos=4;
    /**
     * Nombres de la tabla
     */
    public static String[] masNombres=    new String[] {
        JTUSUARIOS.masNombres[JTUSUARIOS.lPosiCODIGOUSUARIO],
        JTUSUARIOS.masNombres[JTUSUARIOS.lPosiNOMBRE],
        JTUSUARIOS.masNombres[JTUSUARIOS.lPosiPERMISO],
        JTUSUARIOS.masNombres[JTUSUARIOS.lPosiACTIVO]
    };
    public static String[] masCaption=    new String[] {
        JTEEUSUARIOS.masCaption[JTEEUSUARIOS.lPosiCODIGOUSUARIO],
        JTEEUSUARIOS.masCaption[JTEEUSUARIOS.lPosiNOMBRE],
        JTEEUSUARIOS.masCaption[JTEEUSUARIOS.lPosiPERMISO],
        JTEEUSUARIOS.masCaption[JTEEUSUARIOS.lPosiACTIVO]
    };
    public static int[] malTipos=    new int[] {
        JTUSUARIOS.malTipos[JTUSUARIOS.lPosiCODIGOUSUARIO],
        JTUSUARIOS.malTipos[JTUSUARIOS.lPosiNOMBRE],
        JTUSUARIOS.malTipos[JTUSUARIOS.lPosiPERMISO],
        JTUSUARIOS.malTipos[JTUSUARIOS.lPosiACTIVO]
    };
    public static int[] malTamanos=    new int[] {
        JTUSUARIOS.malTamanos[JTUSUARIOS.lPosiCODIGOUSUARIO],
        JTUSUARIOS.malTamanos[JTUSUARIOS.lPosiNOMBRE],
        JTUSUARIOS.malTamanos[JTUSUARIOS.lPosiPERMISO],
        JTUSUARIOS.malTamanos[JTUSUARIOS.lPosiACTIVO]
    };
    public static int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOUSUARIO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla USUARIOSincluyendole el servidor de datos
      */
    public JTFORMUSUARIOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,masCaption, malTamanos);
        moList.addListener(this);
    }
     public boolean getPasarCache(){
        return true;
    }

   public JSelect getSelect(){
     return moSelect;
   }
    public void addFilaPorClave(IFilaDatos poFila) throws Exception {
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
        }
    }

    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
        moList.recuperarDatos(moSelect, getPasarCache(), JListDatos.mclSelectNormal, pbLimpiarCache);
    }

    public JListDatos getList() {
        return moList;
    }

    public void crearSelectSimple(){
        moSelect = new JSelect(JTUSUARIOS.msCTabla);
//         moSelect.getFrom().addTabla(
//            new JSelectUnionTablas(
//                JSelectUnionTablas.mclLeft, JTUSUARIOS.msCTabla, JTCLIENTES.msCTabla, 
//                new String[]{
//                    JTUSUARIOS.getNombreCODIGOCLIENTE()
//                },
//                new String[]{
//                    JTCLIENTES.getNombreCODIGOCLIENTES()
//                }
//            )
//            );

        moSelect.addCampo(JTUSUARIOS.msCTabla, JTUSUARIOS.getCODIGOUSUARIONombre());
        moSelect.addCampo(JTUSUARIOS.msCTabla, JTUSUARIOS.getNOMBRENombre());
        moSelect.addCampo(JTUSUARIOS.msCTabla, JTUSUARIOS.getPERMISONombre());
        moSelect.addCampo(JTUSUARIOS.msCTabla, JTUSUARIOS.getACTIVONombre());
    }

    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac){
        crearSelectSimple();
        if(psTabla!=null){
        }
    }
}
