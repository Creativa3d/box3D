/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.JSelect;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

/**
 *
 * @author eduardo
 */
public class JTFORMPLANTILLAS  extends JSTabla implements IConsulta {

    private JSelect moSelect;

    public static final int lPosiNOMBRE = 0;
   

    public static final int mclNumeroCampos = 27;

    public static String msCTabla = "WORD";

    public static String[] masNombres = new String[]{
        "NOMBRE"
    };
    public static String[] masCaption = new String[]{
        "Nombre"
    };
    public static int[] malTipos = new int[]{
        JListDatos.mclTipoCadena
    };
    public static int[] malTamanos = new int[]{
        0
    };
    public static int[] malCamposPrincipales = new int[]{
        lPosiNOMBRE
    };
    private final IPlantillas moWord;

    public JTFORMPLANTILLAS(IPlantillas poWord) throws Exception {
        super();
        moWord=poWord;
        moList = moWord.getListaPlantillas();
        moList.addListener(this);
    }

    public boolean getPasarCache() {
        return false;
    }

    public JSelect getSelect() {
        return moSelect;
    }

    private void cargar(IFilaDatos poFila) throws Exception {
        moList.getFields().cargar(poFila);

    }

    public void crearSelectSimple() {
    }

    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {
        
        moWord.rellenarListaPlantillas(moList);
    }

    public JListDatos getList() {
        return moList;
    }


    public void addFilaPorClave(IFilaDatos poFila) throws Exception {
        switch (poFila.getTipoModif()) {
            case JListDatos.mclBorrar:
                moList.borrar(false);
                break;
            case JListDatos.mclEditar:
                cargar(poFila);
                moList.update(false);
                break;
            case JListDatos.mclNuevo:
                moList.addNew();
                cargar(poFila);
                moList.update(false);
                break;
            default:
                throw new Exception("Tipo modif. Incorrecto");
        }

    }

}
