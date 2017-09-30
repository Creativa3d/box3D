/*
* JTUSUARIOSLISTAPERMISOS.java
*
 */
package utilesGUIx.aplicacion.usuarios.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTUSUARIOSLISTAPERMISOS extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOUSUARIO;
    public static final int lPosiOBJETO;
    public static final int lPosiACCION;
    public static final int lPosiACTIVOSN;
    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla = "usuarioslistapermisos";
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
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "objeto", "", true, 230));
        lPosiOBJETO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "accion", "", true, 170));
        lPosiACCION = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoBoolean, "activosn", "", false, 1));
        lPosiACTIVOSN = lPosi;
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
    public JTUSUARIOSLISTAPERMISOS(IServerServidorDatos poServidorDatos) {
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
    public JTUSUARIOSLISTAPERMISOS() {
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

    public JFieldDef getOBJETO() {
        return moList.getFields().get(lPosiOBJETO);
    }

    public static String getOBJETONombre() {
        return moCamposEstaticos.get(lPosiOBJETO).getNombre();
    }

    public JFieldDef getACCION() {
        return moList.getFields().get(lPosiACCION);
    }

    public static String getACCIONNombre() {
        return moCamposEstaticos.get(lPosiACCION).getNombre();
    }

    public JFieldDef getACTIVOSN() {
        return moList.getFields().get(lPosiACTIVOSN);
    }

    public static String getACTIVOSNNombre() {
        return moCamposEstaticos.get(lPosiACTIVOSN).getNombre();
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
