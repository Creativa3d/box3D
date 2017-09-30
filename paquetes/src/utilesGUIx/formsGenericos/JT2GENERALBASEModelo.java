/*
 * JT2CLIENTES.java
 *
 * Creado el 21/1/2006
 */
package utilesGUIx.formsGenericos;

import ListDatos.*;
import java.util.Comparator;
import utiles.IListaElementos;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;
import utilesGUIx.formsGenericos.boton.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFactoria;
import utilesGUIx.plugin.IPlugInManager;

public abstract class JT2GENERALBASEModelo implements IPanelControladorConsulta, IEjecutarExtend {

    private transient IPanelGenerico moPanel;
    /**Indice seleccionado, si es -1 es que se ha cancelado*/
    private int[] mlIndexs = new int[0];
    protected transient JPanelGeneralParametros moParametros = new JPanelGeneralParametros();

    public JT2GENERALBASEModelo() {
    }


    public void setPanel(final IPanelGenerico poPanel) {
        moPanel = (IPanelGenerico) poPanel;
    }


    public IPanelGenerico getPanel() {
        return moPanel;
    }


    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        //vacio
    }


    public void datosactualizados(final IFilaDatos poFila) throws Exception {
        if (!getConsulta().getList().getEnCache() && (poFila != null)) {
            switch (poFila.getTipoModif()) {
                case JListDatos.mclEditar:
                case JListDatos.mclNada:
                    poFila.setTipoModif(JListDatos.mclEditar);
                case JListDatos.mclBorrar:
                    //OJO Se usan los campos principales de Consulta
                    //y se deberian usar los campos principales de la tabla
                    int[] lalCamposPrinci = getConsulta().getList().getFields().malCamposPrincipales();
                    String[] lasCampos = new String[lalCamposPrinci.length];
                    for (int i = 0; i < lalCamposPrinci.length; i++) {
                        lasCampos[i] = poFila.msCampo(lalCamposPrinci[i]);
                    }
                    if (getConsulta().getList().buscar(JListDatos.mclTIgual, lalCamposPrinci, lasCampos)) {
                        getConsulta().addFilaPorClave(poFila);
                    }
                    break;
                case JListDatos.mclNuevo:
                    getConsulta().addFilaPorClave(poFila);
                    break;
                default:
            }
        }
        //se refresca el panel
        if (moPanel != null) {
            moPanel.refrescar();
        }
    }

    public ListDatos.JListDatos getDatos() throws Exception {
        JListDatos retVal = null;
        try {
	    getConsulta().refrescar(getConsulta().getPasarCache(), false);
	    if (moParametros.getColoresTabla() instanceof JPanelGenericoColores) {
	         ((JPanelGenericoColores) moParametros.getColoresTabla()).setDatos(getConsulta().getList());
	    }
	     retVal = getConsulta().getList();

        } finally {
        }
        return retVal;
    }


    public void refrescar() throws Exception {
        Comparator loOrden = getConsulta().getList().getOrdenacion();
        JListDatosFiltroConj loFiltro = getConsulta().getList().getFiltro();
        boolean lbFiltrado = getConsulta().getList().getEsFiltrado();

        try {
	    getConsulta().refrescar(getConsulta().getPasarCache(), true);
	    if (moParametros.getColoresTabla() instanceof JPanelGenericoColores) {
	         ((JPanelGenericoColores) moParametros.getColoresTabla()).setDatos(getConsulta().getList());
	    }

	    if (lbFiltrado) {
	        getConsulta().getList().filtrar();
	    }
	    getConsulta().getList().ordenar(loOrden);

	    //se refresca el panel
	    if (getPanel() != null) {
	        getPanel().refrescar();
	    }
	    System.gc();

        } finally {
        }
    }
    
//    public ListDatos.JListDatos getDatos() throws Exception {
//        getConsulta().refrescar(getConsulta().getPasarCache(), false);
//        if (moParametros.getColoresTabla() instanceof JPanelGenericoColores) {
//            ((JPanelGenericoColores) moParametros.getColoresTabla()).setDatos(getConsulta().getList());
//        }
//        return getConsulta().getList();
//    }
//
//    public void refrescar() throws Exception {
//        IListDatosOrdenacion loOrden = getConsulta().getList().getOrdenacion();
//        JListDatosFiltroConj loFiltro = getConsulta().getList().getFiltro();
//        boolean lbFiltrado = getConsulta().getList().getEsFiltrado();
//
//
//        getConsulta().refrescar(getConsulta().getPasarCache(), true);
//
//        if (lbFiltrado) {
//            getConsulta().getList().filtrar();
//        }
//        getConsulta().getList().ordenar(loOrden);
//
//        //se refresca el panel
//        if (getPanel() != null) {
//            moPanel.refrescar();
//        }
//        System.gc();
//    }

    public void setIndexs(final int[] plIndex) throws EBookmarkIncorrecto {
        if (plIndex == null) {
            mlIndexs = new int[0];
        } else if (plIndex.length == 0) {
            mlIndexs = plIndex;
        } else {
            mlIndexs = new int[plIndex.length];
            for (int i = plIndex.length-1; i >=0 ; i--) {
                getConsulta().getList().setIndex(plIndex[i]);
                JListDatosBookMark loBook = getConsulta().getList().getBookmark();
                getConsulta().getList().getFiltro().Clear();
                getConsulta().getList().filtrarNulo();
                getConsulta().getList().setBookmark(loBook);
                mlIndexs[i] = getConsulta().getList().getIndex();
            }
        }
    }


    public int[] getIndexs() {
        return mlIndexs;
    }

    public int getIndex() {
        if(mlIndexs.length>0){
            return mlIndexs[0];
        }else{
            return -1;
        }
    }

    public JPanelGeneralParametros getParametros() {
        try {
            if (moParametros.getLongitudCampos() != null && moPanel != null && moParametros.isForzarLong()) {
                for (int i = 0; i < moParametros.getLongitudCampos().length; i++) {
                    moPanel.getTablaConfig().getTablaConfigColumnaDeCampoReal(i).setLong(
                            moParametros.getLongitudCampos()[i]);

                }
                moPanel.getTablaConfig().aplicar();
            }
        } catch (Throwable e) {
        }
        return moParametros;
    }

    public void inicializarPlugIn(final IPlugInFactoria poFactoria) {
        poFactoria.getPlugInManager().procesarControlador(
                poFactoria.getPlugInContexto(),
                this);
    }

    public void inicializarPlugIn(final IPlugInContexto poContexto, final IPlugInManager poManager) {
        poManager.procesarControlador(poContexto, this);
    }

    public abstract IConsulta getConsulta();
    
    public IListaElementos getFiltros(){
        return null;
    }
    public void setFechas(String psFecha1, String psFecha2, int plCampo) throws Exception{
        
    }    
}
