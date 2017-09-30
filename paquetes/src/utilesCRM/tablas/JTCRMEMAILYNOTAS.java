package utilesCRM.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTCRMEMAILYNOTAS extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOEMPRESA;
    public static final int lPosiCODIGONOTA;
    public static final int lPosiFECHA;
    public static final int lPosiTIPO;
    public static final int lPosiASUNTO;
    public static final int lPosiDESCRIPCION;
    public static final int lPosiCODIGOUSUARIO;
    public static final int lPosiCODIGOCONTACTO;
    public static final int lPosiCODIGONEGOCIACION;
    public static final int lPosiCODIGONOTAMAESTEMPRESA;
    public static final int lPosiCODIGONOTAMAEST;
    public static final int lPosiCODIGOCALENDARIO;
    public static final int lPosiCODIGOEVENTO;
    public static final int lPosiGUIXMENSAJESSENDCOD;
    /**
     * * Variable nombre de tabla
     */
    public static final String msCTabla = "CRMEMAILYNOTAS";
    /**
     * * NUmero de campos de la tabla
     */
    public static final int mclNumeroCampos;
    /**
     * * Nombres de la tabla
     */
    public static final String[] masNombres;
    public static final int[] malTipos;
    public static final int[] malTamanos;
    public static final int[] malCamposPrincipales;
    private static final JFieldDefs moCamposEstaticos;

    static {
        moCamposEstaticos = new JFieldDefs();
        moCamposEstaticos.setTabla(msCTabla);
        int lPosi = 0;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGOEMPRESA", "", true, 20));
        lPosiCODIGOEMPRESA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGONOTA", "", true, 10));
        lPosiCODIGONOTA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoFecha, "FECHA", "", false, 10));
        lPosiFECHA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "TIPO", "", false, 20));
        lPosiTIPO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "ASUNTO", "", false, 255));
        lPosiASUNTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "DESCRIPCION", "", false, 1000000));
        lPosiDESCRIPCION = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGOUSUARIO", "", false, 255));
        lPosiCODIGOUSUARIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGOCONTACTO", "", false, 255));
        lPosiCODIGOCONTACTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGONEGOCIACION", "", false, 255));
        lPosiCODIGONEGOCIACION = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGONOTAMAESTEMPRESA", "", false, 20));
        lPosiCODIGONOTAMAESTEMPRESA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGONOTAMAEST", "", false, 255));
        lPosiCODIGONOTAMAEST = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGOCALENDARIO", "", false, 255));
        lPosiCODIGOCALENDARIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOEVENTO", "", false, 255));
        lPosiCODIGOEVENTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "GUIXMENSAJESSENDCOD", "", false, 255));
        lPosiGUIXMENSAJESSENDCOD = lPosi;
        lPosi++;
        mclNumeroCampos = moCamposEstaticos.size();
        malCamposPrincipales = moCamposEstaticos.malCamposPrincipales();
        masNombres = moCamposEstaticos.msNombres();
        malTamanos = moCamposEstaticos.malTamanos();
        malTipos = moCamposEstaticos.malTipos();
    }

    /**
     * * Crea una instancia de la clase intermedia para la tabla incluyendole el
     * * servidor de datos
     */
    public JTCRMEMAILYNOTAS(IServerServidorDatos poServidorDatos) {
        super();
        try {
            moList = new JListDatos();
            moList.setFields(moCamposEstaticos.Clone());
            moList.msTabla = msCTabla;
            moList.moServidor = poServidorDatos;
            moList.addListener(this);
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    /**
     * * Crea una instancia de la clase intermedia para la tabla
     */
    public JTCRMEMAILYNOTAS() {
        this(null);
    }

    public static JFieldDefs getCamposEstaticos() {
        return moCamposEstaticos;
    }

    public JFieldDef getCODIGONOTA() {
        return moList.getFields().get(lPosiCODIGONOTA);
    }

    public static String getCODIGONOTANombre() {
        return moCamposEstaticos.get(lPosiCODIGONOTA).getNombre();
    }

    public JFieldDef getCODIGOUSUARIO() {
        return moList.getFields().get(lPosiCODIGOUSUARIO);
    }

    public static String getCODIGOUSUARIONombre() {
        return moCamposEstaticos.get(lPosiCODIGOUSUARIO).getNombre();
    }

    public JFieldDef getCODIGOEMPRESA() {
        return moList.getFields().get(lPosiCODIGOEMPRESA);
    }

    public static String getCODIGOEMPRESANombre() {
        return moCamposEstaticos.get(lPosiCODIGOEMPRESA).getNombre();
    }

    public JFieldDef getTIPO() {
        return moList.getFields().get(lPosiTIPO);
    }

    public static String getTIPONombre() {
        return moCamposEstaticos.get(lPosiTIPO).getNombre();
    }

    public JFieldDef getASUNTO() {
        return moList.getFields().get(lPosiASUNTO);
    }

    public static String getASUNTONombre() {
        return moCamposEstaticos.get(lPosiASUNTO).getNombre();
    }

    public JFieldDef getDESCRIPCION() {
        return moList.getFields().get(lPosiDESCRIPCION);
    }

    public static String getDESCRIPCIONNombre() {
        return moCamposEstaticos.get(lPosiDESCRIPCION).getNombre();
    }

    public JFieldDef getCODIGOCONTACTO() {
        return moList.getFields().get(lPosiCODIGOCONTACTO);
    }

    public static String getCODIGOCONTACTONombre() {
        return moCamposEstaticos.get(lPosiCODIGOCONTACTO).getNombre();
    }

    public JFieldDef getCODIGONEGOCIACION() {
        return moList.getFields().get(lPosiCODIGONEGOCIACION);
    }

    public static String getCODIGONEGOCIACIONNombre() {
        return moCamposEstaticos.get(lPosiCODIGONEGOCIACION).getNombre();
    }

    public JFieldDef getCODIGONOTAMAESTEMPRESA() {
        return moList.getFields().get(lPosiCODIGONOTAMAESTEMPRESA);
    }

    public static String getCODIGONOTAMAESTEMPRESANombre() {
        return moCamposEstaticos.get(lPosiCODIGONOTAMAESTEMPRESA).getNombre();
    }

    public JFieldDef getCODIGONOTAMAEST() {
        return moList.getFields().get(lPosiCODIGONOTAMAEST);
    }

    public static String getCODIGONOTAMAESTNombre() {
        return moCamposEstaticos.get(lPosiCODIGONOTAMAEST).getNombre();
    }

    public JFieldDef getCODIGOCALENDARIO() {
        return moList.getFields().get(lPosiCODIGOCALENDARIO);
    }

    public static String getCODIGOCALENDARIONombre() {
        return moCamposEstaticos.get(lPosiCODIGOCALENDARIO).getNombre();
    }

    public JFieldDef getCODIGOEVENTO() {
        return moList.getFields().get(lPosiCODIGOEVENTO);
    }

    public static String getCODIGOEVENTONombre() {
        return moCamposEstaticos.get(lPosiCODIGOEVENTO).getNombre();
    }

    public JFieldDef getGUIXMENSAJESSENDCOD() {
        return moList.getFields().get(lPosiGUIXMENSAJESSENDCOD);
    }

    public static String getGUIXMENSAJESSENDCODNombre() {
        return moCamposEstaticos.get(lPosiGUIXMENSAJESSENDCOD).getNombre();
    }
    public JFieldDef getFECHA(){
        return moList.getFields().get(lPosiFECHA);
    }
    public static String getFECHANombre(){
        return moCamposEstaticos.get(lPosiFECHA).getNombre();
    }

    /**
     * * recupera un objeto select segun la informaciOn actual * * @return
     * objeto select
     */
    public static JSelect getSelectStatico() {
        JSelect loSelect = new JSelect(msCTabla);
        for (int i = 0; i < moCamposEstaticos.size(); i++) {
            loSelect.addCampo(msCTabla, moCamposEstaticos.get(i).getNombre());
        }
        return loSelect;
    }
}
