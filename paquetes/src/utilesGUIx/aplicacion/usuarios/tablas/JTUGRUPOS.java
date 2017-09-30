/*
* JTUGRUPOS.java
*
 */
package utilesGUIx.aplicacion.usuarios.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTUGRUPOS extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOGRUPO;
    public static final int lPosiNOMBRE;
    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla = "ugrupos";
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

        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "codigogrupo", "", true, 19));
        lPosiCODIGOGRUPO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "nombre", "", false, 255));
        lPosiNOMBRE = lPosi;
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
    public JTUGRUPOS(IServerServidorDatos poServidorDatos) {
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
    public JTUGRUPOS() {
        this(null);
    }

    public static JFieldDefs getCamposEstaticos() {
        return moCamposEstaticos;
    }

    public JFieldDef getCODIGOGRUPO() {
        return moList.getFields().get(lPosiCODIGOGRUPO);
    }

    public static String getCODIGOGRUPONombre() {
        return moCamposEstaticos.get(lPosiCODIGOGRUPO).getNombre();
    }

    public JFieldDef getNOMBRE() {
        return moList.getFields().get(lPosiNOMBRE);
    }

    public static String getNOMBRENombre() {
        return moCamposEstaticos.get(lPosiNOMBRE).getNombre();
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
