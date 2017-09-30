/*
 * JTGUIXEVENTOS.java
 *
 */
package utilesGUIxAvisos.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTGUIXEVENTOS extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCALENDARIO = 0;//CALENDARIO EVENTO
    public static final int lPosiCODIGO = 1; //CODIGO EVENTO
    public static final int lPosiFECHADESDE = 2;//FECHA DESDE
    public static final int lPosiFECHAHASTA = 3;//FECHA HASTA
    public static final int lPosiNOMBRE = 4;//NOMBRE
    public static final int lPosiTEXTO = 5;//DESCRIPCION
    public static final int lPosiREPETICION = 6;//REPETICION, CADA DIA,SEMANA, MES, AÑO
    public static final int lPosiGRUPO = 7;//SI PERTENECE A UN GRUPO, EJEMPLO: EXPEDIENTES+codigo, nos sirve para asociar avisos a registros de los listados
    public static final int lPosiPRIORIDAD = 8;//PARA DAR UN COLOR
    public static final int lPosiORIGINALSN = 9;//SI ES EL EVENTO ORIGINAL, SOLO EVENTOS ORIGINALES GENERAN NUEVOS EVENTOS
    public static final int lPosiIDENTIFICADORSERIE = 10;//IDENTIFICADOR DE LA SERIE, PARA TRATAMIENTO CONJUTNO DE EVENTOS Q SE REPITEN O SE DUPLICAN EN DISTINTOS CALENDARIOS
    public static final int lPosiUSUARIO = 11; //el usuario que crea el evento
    public static final int lPosiIDENTIFICADOREXTERNO = 12;//identificador asociado exgterno, por ejemplo de google calendar
    public static final int lPosiFECHAMODIFICACION = 13;//Importantisimo, para eficiencia eventos, debe terner indice
    public static final int lPosiEVENTOSN = 14;//SI SE HA LANZADO
    public static final int lPosiUSUARIOASIGNADO = 15;//USUARIO ASIGNADO
    
    //NOTA4: que se puedan generar los calendarios que quieran y cada usuario ve un conjunto de ellos
    
    
    
    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla = "GUIXEVENTOS";
    /**
     * Número de campos de la tabla
     */
    public static final int mclNumeroCampos = 16;
    /**
     * Nombres de la tabla
     */
    public static final String[] masNombres = new String[]{
        "CALENDARIO",
        "CODIGO",
        "FECHADESDE",
        "FECHAHASTA",
        "NOMBRE",
        "TEXTO",
        "REPETICION",
        "GRUPO",
        "PRIORIDAD",
        "ORIGINALSN",
        "IDENTIFICADORSERIE",
        "USUARIO",
        "IDENTIFICADOREXTERNO",
        "FECHAMODIFICACION",
        "EVENTOSN",
        "USUARIOASIGNADO",
        
    };
    public static final int[] malTipos = new int[]{
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoFecha,
        JListDatos.mclTipoFecha,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoBoolean,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoFecha,
        JListDatos.mclTipoBoolean,
        JListDatos.mclTipoCadena,
    };
    public static final int[] malTamanos = new int[]{
        255,
        10,
        23,
        23,
        255,
        1073741823,
        255,
        255,
        10,
        0,
        255,
        255,
        255,
        0,0,255
            
    };
    public static final int[] malCamposPrincipales = new int[]{
        lPosiCALENDARIO,
        lPosiCODIGO
    };

    /**
     * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
     */
    public JTGUIXEVENTOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos, msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }

    /**
     * Crea una instancia de la clase intermedia para la tabla FAMILIAS
     */
    public JTGUIXEVENTOS() {
        this(null);
    }

    public JFieldDef getCALENDARIO() {
        return moList.getFields().get(lPosiCALENDARIO);
    }

    public static String getCALENDARIONombre() {
        return masNombres[lPosiCALENDARIO];
    }

    public JFieldDef getCODIGO() {
        return moList.getFields().get(lPosiCODIGO);
    }

    public static String getCODIGONombre() {
        return masNombres[lPosiCODIGO];
    }

    public JFieldDef getFECHADESDE() {
        return moList.getFields().get(lPosiFECHADESDE);
    }

    public static String getFECHADESDENombre() {
        return masNombres[lPosiFECHADESDE];
    }

    public JFieldDef getFECHAHASTA() {
        return moList.getFields().get(lPosiFECHAHASTA);
    }

    public static String getFECHAHASTANombre() {
        return masNombres[lPosiFECHAHASTA];
    }

    public JFieldDef getNOMBRE() {
        return moList.getFields().get(lPosiNOMBRE);
    }

    public static String getNOMBRENombre() {
        return masNombres[lPosiNOMBRE];
    }

    public JFieldDef getTEXTO() {
        return moList.getFields().get(lPosiTEXTO);
    }

    public static String getTEXTONombre() {
        return masNombres[lPosiTEXTO];
    }

    public JFieldDef getREPETICION() {
        return moList.getFields().get(lPosiREPETICION);
    }

    public static String getREPETICIONNombre() {
        return masNombres[lPosiREPETICION];
    }

    public JFieldDef getGRUPO() {
        return moList.getFields().get(lPosiGRUPO);
    }

    public static String getGRUPONombre() {
        return masNombres[lPosiGRUPO];
    }

    public JFieldDef getPRIORIDAD() {
        return moList.getFields().get(lPosiPRIORIDAD);
    }

    public static String getPRIORIDADNombre() {
        return masNombres[lPosiPRIORIDAD];
    }

    public JFieldDef getORIGINALSN() {
        return moList.getFields().get(lPosiORIGINALSN);
    }

    public static String getORIGINALSNNombre() {
        return masNombres[lPosiORIGINALSN];
    }

    public JFieldDef getIDENTIFICADORSERIE() {
        return moList.getFields().get(lPosiIDENTIFICADORSERIE);
    }

    public static String getIDENTIFICADORSERIENombre() {
        return masNombres[lPosiIDENTIFICADORSERIE];
    }
    public JFieldDef getUSUARIO() {
        return moList.getFields().get(lPosiUSUARIO);
    }

    public static String getUSUARIONombre() {
        return masNombres[lPosiUSUARIO];
    }

    public JFieldDef getIDENTIFICADOREXTERNO() {
        return moList.getFields().get(lPosiIDENTIFICADOREXTERNO);
    }

    public static String getIDENTIFICADOREXTERNONombre() {
        return masNombres[lPosiIDENTIFICADOREXTERNO];
    }
    public JFieldDef getFECHAMODIFICACION() {
        return moList.getFields().get(lPosiFECHAMODIFICACION);
    }

    public static String getFECHAMODIFICACIONNombre() {
        return masNombres[lPosiFECHAMODIFICACION];
    }
    public JFieldDef getEVENTOSN() {
        return moList.getFields().get(lPosiEVENTOSN);
    }

    public static String getEVENTOSNNombre() {
        return masNombres[lPosiEVENTOSN];
    }
    public JFieldDef getUSUARIOASIGNADO() {
        return moList.getFields().get(lPosiUSUARIOASIGNADO);
    }

    public static String getUSUARIOASIGNADONombre() {
        return masNombres[lPosiUSUARIOASIGNADO];
    }

    /**
     *recupera un objeto select segun la información actual
     *@return objeto select
     */
    public static JSelect getSelectStatico() {
        JSelect loSelect = new JSelect(msCTabla);
        for (int i = 0; i < masNombres.length; i++) {
            loSelect.addCampo(msCTabla, masNombres[i]);
        }
        return loSelect;
    }
    
}
