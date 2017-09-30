/*
 * JTGUIXEVENTOS.java
 *
 */
package utilesGUIxAvisos.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTGUIXCALENDARIO extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCALENDARIO = 0;
    public static final int lPosiNOMBRE = 1;
    public static final int lPosiIDENTIFICADOREXTERNO = 2;
    public static final int lPosiCLIENTID = 3;
    public static final int lPosiCLIENTSECRET = 4;
    
    
    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla = "GUIXCALENDARIO";
    /**
     * Número de campos de la tabla
     */
    public static final int mclNumeroCampos = 5;
    /**
     * Nombres de la tabla
     */
    public static final String[] masNombres = new String[]{
        "CALENDARIO",
        "NOMBRE",
        "IDENTIFICADOREXTERNO",
        "CLIENTID",
        "CLIENTSECRET"
    };
    public static final int[] malTipos = new int[]{
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
    };
    public static final int[] malTamanos = new int[]{
        255,
        255,
        255,
        255,
        255,
    };
    public static final int[] malCamposPrincipales = new int[]{
        lPosiCALENDARIO
    };

    /**
     * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
     */
    public JTGUIXCALENDARIO(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos, msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }

    /**
     * Crea una instancia de la clase intermedia para la tabla FAMILIAS
     */
    public JTGUIXCALENDARIO() {
        this(null);
    }

    public JFieldDef getCALENDARIO() {
        return moList.getFields().get(lPosiCALENDARIO);
    }

    public static String getCALENDARIONombre() {
        return masNombres[lPosiCALENDARIO];
    }


    public JFieldDef getNOMBRE() {
        return moList.getFields().get(lPosiNOMBRE);
    }

    public static String getNOMBRENombre() {
        return masNombres[lPosiNOMBRE];
    }

    public JFieldDef getIDENTIFICADOREXTERNO() {
        return moList.getFields().get(lPosiIDENTIFICADOREXTERNO);
    }
    public static String getIDENTIFICADOREXTERNONombre() {
        return masNombres[lPosiIDENTIFICADOREXTERNO];
    }

    public JFieldDef getCLIENTID() {
        return moList.getFields().get(lPosiCLIENTID);
    }
    public static String getCLIENTIDNombre() {
        return masNombres[lPosiCLIENTID];
    }

    public JFieldDef getCLIENTSECRET() {
        return moList.getFields().get(lPosiCLIENTSECRET);
    }
    public static String getCLIENTSECRETNombre() {
        return masNombres[lPosiCLIENTSECRET];
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
