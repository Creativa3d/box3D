/*
* JTUSUARIOSATRIB.java
*
 */
package utilesGUIx.aplicacion.usuarios.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTUSUARIOSATRIB extends JSTabla {

    private static final long serialVersionUID = 1L;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOUSUARIO;
    public static final int lPosiCODIGOUSUARIOATRIBDEF;
    public static final int lPosiVALOR;
    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla = "USUARIOSATRIB";
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

        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOUSUARIO", "", true, 10));
        lPosiCODIGOUSUARIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOUSUARIOATRIBDEF", "", true, 10));
        lPosiCODIGOUSUARIOATRIBDEF = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "VALOR", "", false, 2147483647));
        lPosiVALOR = lPosi;
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
    public JTUSUARIOSATRIB(IServerServidorDatos poServidorDatos) {
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
    public JTUSUARIOSATRIB() {
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

    public JFieldDef getCODIGOUSUARIOATRIBDEF() {
        return moList.getFields().get(lPosiCODIGOUSUARIOATRIBDEF);
    }

    public static String getCODIGOUSUARIOATRIBDEFNombre() {
        return moCamposEstaticos.get(lPosiCODIGOUSUARIOATRIBDEF).getNombre();
    }

    public JFieldDef getVALOR() {
        return moList.getFields().get(lPosiVALOR);
    }

    public static String getVALORNombre() {
        return moCamposEstaticos.get(lPosiVALOR).getNombre();
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
