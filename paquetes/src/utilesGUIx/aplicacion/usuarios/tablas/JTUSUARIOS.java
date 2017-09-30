/*
* JTUSUARIOS.java
*
 */
package utilesGUIx.aplicacion.usuarios.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTUSUARIOS extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOUSUARIO;
    public static final int lPosiNOMBRE;
    public static final int lPosiCLAVE;
    public static final int lPosiPERMISO;
    public static final int lPosiACTIVO;
    public static final int lPosiLOGIN;
    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla = "usuarios";
    /**
     * NUmero de campos de la tabla
     */
    public static final int mclNumeroCampos;
    /**
     * Nombres de la tabla
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

        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "codigousuario", "", true, 19));
        lPosiCODIGOUSUARIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "nombre", "", false, 255));
        lPosiNOMBRE = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "clave", "", false, 255));
        lPosiCLAVE = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "permiso", "", false, 19));
        lPosiPERMISO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoBoolean, "activo", "", false, 1));
        lPosiACTIVO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "login", "", false, 50));
        lPosiLOGIN = lPosi;
        lPosi++;

        mclNumeroCampos = moCamposEstaticos.size();
        malCamposPrincipales = moCamposEstaticos.malCamposPrincipales();
        masNombres = moCamposEstaticos.msNombres();
        malTamanos = moCamposEstaticos.malTamanos();
        malTipos = moCamposEstaticos.malTipos();
    }

    /**
     * Crea una instancia de la clase intermedia para la tabla incluyendole el
     * servidor de datos
     */
    public JTUSUARIOS(IServerServidorDatos poServidorDatos) {
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
     * Crea una instancia de la clase intermedia para la tabla
     */
    public JTUSUARIOS() {
        this(null);
    }

    public static JFieldDefs getCamposEstaticos() {
        return moCamposEstaticos;
    }

    public JFieldDef getCODIGOUSUARIO() {
        return moList.getFields().get(lPosiCODIGOUSUARIO);
    }

    public static String getCODIGOUSUARIONombre() {
        return moCamposEstaticos.get(lPosiCODIGOUSUARIO).getNombre();
    }

    public JFieldDef getNOMBRE() {
        return moList.getFields().get(lPosiNOMBRE);
    }

    public static String getNOMBRENombre() {
        return moCamposEstaticos.get(lPosiNOMBRE).getNombre();
    }

    public JFieldDef getCLAVE() {
        return moList.getFields().get(lPosiCLAVE);
    }

    public static String getCLAVENombre() {
        return moCamposEstaticos.get(lPosiCLAVE).getNombre();
    }

    public JFieldDef getPERMISO() {
        return moList.getFields().get(lPosiPERMISO);
    }

    public static String getPERMISONombre() {
        return moCamposEstaticos.get(lPosiPERMISO).getNombre();
    }

    public JFieldDef getACTIVO() {
        return moList.getFields().get(lPosiACTIVO);
    }

    public static String getACTIVONombre() {
        return moCamposEstaticos.get(lPosiACTIVO).getNombre();
    }

    public JFieldDef getLOGIN() {
        return moList.getFields().get(lPosiLOGIN);
    }

    public static String getLOGINNombre() {
        return moCamposEstaticos.get(lPosiLOGIN).getNombre();
    }

    /**
     * recupera un objeto select segun la informaciOn actual
     *
     * @return objeto select
     */
    public static JSelect getSelectStatico() {
        JSelect loSelect = new JSelect(msCTabla);
        for (int i = 0; i < moCamposEstaticos.size(); i++) {
            loSelect.addCampo(msCTabla, moCamposEstaticos.get(i).getNombre());
        }
        return loSelect;
    }
}
