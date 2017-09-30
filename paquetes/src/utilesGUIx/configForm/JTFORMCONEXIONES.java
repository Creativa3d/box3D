/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.configForm;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosInternet;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JTFORMCONEXIONES implements IConsulta {
    public JListDatos moList;
    public static final int lPosiNombre = 0;
    public static final int lPosiTipo = 1;

    /**
     * Variable nombre de tabla
     */
    public static final String msCTabla="Conexiones";
    /**
     * Número de campos de la tabla
     */
    public static final int mclNumeroCampos=2;
    /**
     * Nombres de la tabla
     */
    public static final String[] masNombres=    new String[] {
        "Nombre",
        "Tipo conexión"
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
        lPosiNombre
    };
    private final JConexionLista moListaConex;

    public static String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);


    public JTFORMCONEXIONES(JConexionLista poLista){
        super();
        moListaConex = poLista;
        moList = new JListDatos(null,msCTabla, masNombres, malTipos, malCamposPrincipales,masCaption, malTamanos);
    }

    public static IFilaDatos getFilaDeConex(JConexion poConex){
        JFilaDatosDefecto loFila = new JFilaDatosDefecto();
        loFila.addCampo(poConex.getNombre());
        switch(poConex.getConexion().getTipoConexion()){
            case JServerServidorDatos.mclTipoBD:
                loFila.addCampo("Directo");
                break;
            case JServerServidorDatos.mclTipoInternet:
                loFila.addCampo("Internet");
                break;
            case JServerServidorDatos.mclTipoInternetComprimido:
                loFila.addCampo("Internet comprimido");
                break;
            case JServerServidorDatos.mclTipoInternetComprimido_I_O:
                loFila.addCampo("Internet comprimido IO");
                break;
        }
        return loFila;
    }

    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
        moList.clear();
        for(int i = 0 ; i < moListaConex.size(); i++){
            JConexion loConex = moListaConex.get(i);
            moList.add(getFilaDeConex(loConex));
        }
    }

    public JListDatos getList() {
        return moList;
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
            default:
                throw new Exception("Tipo modificación incorrecto");
        }

    }

    public boolean getPasarCache() {
        return false;
    }

}
