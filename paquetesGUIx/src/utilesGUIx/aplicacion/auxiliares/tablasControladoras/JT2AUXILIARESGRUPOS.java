/*
* JT2AUXILIARESGRUPOS.java
*
* Creado el 17/10/2014
*/
package utilesGUIx.aplicacion.auxiliares.tablasControladoras;


import utilesGUIx.aplicacion.auxiliares.forms.JPanelAUXILIARESGRUPOS;
import ListDatos.*;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.aplicacion.auxiliares.consultas.JTFORMAUXILIARESGRUPOS;
import utilesGUIx.aplicacion.auxiliares.tablasExtend.JTEEAUXILIARESGRUPOS;

import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JT2AUXILIARESGRUPOS  extends  JT2GENERALBASE2 {
    private final JTFORMAUXILIARESGRUPOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";

    /** Crea una nueva instancia de JT2AUXILIARESGRUPOS */
    public JT2AUXILIARESGRUPOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMAUXILIARESGRUPOS(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JTEEAUXILIARESGRUPOS.msCTabla);
        moParametros.setTitulo(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARESGRUPOS.msCTabla));
        getParametros().setMostrarPantalla(poMostrarPantalla);
        inicializarPlugIn(JGUIxConfigGlobal.getInstancia().getPlugInFactoria());
//EJEMPLOS de coloreo        
//        JListDatosFiltroConj loElem = new JListDatosFiltroConj();
//        loElem.addCondicion(JListDatosFiltroConj.mclAND,
//                JListDatos.mclTMayorIgual, JTEEARTICULOS.lPosiDESCRIPCION, "*");
//        loElem.addCondicion(JListDatosFiltroConj.mclAND,
//                JListDatos.mclTMenorIgual, JTEEARTICULOS.lPosiDESCRIPCION, "*ZZZZZZZZZ");
//        loElem.inicializar(JTEEARTICULOS.msCTabla, JTEEARTICULOS.malTipos, JTEEARTICULOS.masNombres);
//        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
//                loElem,
//                null,
//                new ColorCZ(Color.red));
    }

    public JT2AUXILIARESGRUPOS(
            IServerServidorDatos poServer,
            IMostrarPantalla poMostrar,
            String psNomTabRelac,
            IFilaDatos poDatosRelac) {
        this(poServer, poMostrar);
        setTablaRelacionada(psNomTabRelac, poDatosRelac);
    }

    public void setTablaRelacionada(
            String psNomTabRelac,
            IFilaDatos poDatosRelac){
        msNomTabRelac = psNomTabRelac;
        moFilaDatosRelac = poDatosRelac;
        moConsulta.crearSelect(msNomTabRelac, moFilaDatosRelac);
    }
    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    public void anadir() throws Exception {
        JTEEAUXILIARESGRUPOS loAUXILIARESGRUPOS = new JTEEAUXILIARESGRUPOS(moServer);
        loAUXILIARESGRUPOS.moList.addNew();
        valoresDefecto(loAUXILIARESGRUPOS);

        JPanelAUXILIARESGRUPOS loPanel = new JPanelAUXILIARESGRUPOS();
        loPanel.setDatos(loAUXILIARESGRUPOS, this);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEAUXILIARESGRUPOS loAUXILIARESGRUPOS = new JTEEAUXILIARESGRUPOS(moServer);
        loAUXILIARESGRUPOS.moList.addNew();
        loAUXILIARESGRUPOS.getCODIGOGRUPOAUX().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOGRUPOAUX).getValue());
        loAUXILIARESGRUPOS.moList.update(false);

        loAUXILIARESGRUPOS.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loAUXILIARESGRUPOS.moList.moFila().clone();
        IResultado loResult = loAUXILIARESGRUPOS.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEAUXILIARESGRUPOS loAUXILIARESGRUPOS = JTEEAUXILIARESGRUPOS.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOGRUPOAUX).getString(),
                moServer);
        valoresDefecto(loAUXILIARESGRUPOS);
        JPanelAUXILIARESGRUPOS loPanel = new JPanelAUXILIARESGRUPOS();
        loPanel.setDatos(loAUXILIARESGRUPOS, this);

        if(moConsulta.moList.size()>1){
            getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
        }else{
            getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        }            
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMAUXILIARESGRUPOS.mclNumeroCampos];

//        loInt[JTFORMAUXILIARESGRUPOS.lPosiCODIGOGRUPOAUX]=80;
//        loInt[JTFORMAUXILIARESGRUPOS.lPosiDESCRIPCION]=80;
//        return loInt;
        return null;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                    moConsulta.moList.setIndex(plIndex[i]);
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImpListado, mcsImpListado, "/images/Print16.gif", this));
        
    }

    public static class GestionProyectoTabla implements IGestionProyecto {

        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEEAUXILIARESGRUPOS(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2AUXILIARESGRUPOS(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEEAUXILIARESGRUPOS.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEEAUXILIARESGRUPOS loObj = JTEEAUXILIARESGRUPOS.getTabla(poFila, poServer);
            JPanelAUXILIARESGRUPOS loPanel = new JPanelAUXILIARESGRUPOS();
            loPanel.setDatos(loObj, null);
            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            IConsulta loConsulta = null;
            JSTabla loTabla = null;
            boolean lbCache = false;

            loTabla = new JTEEAUXILIARESGRUPOS(poServer);
            lbCache = JTEEAUXILIARESGRUPOS.getPasarACache();
            if(lbCache){
                loTabla.recuperarTodosNormal(lbCache);
                loConsulta = new JConsulta(loTabla, true);
            }else{
                JTFORMAUXILIARESGRUPOS loConsulta1 = new JTFORMAUXILIARESGRUPOS(poServer);
                loConsulta1.crearSelectSimple();
                loConsulta = loConsulta1;
            }
//            return JDatosGeneralesP.getDatosGenerales().mostrarBusqueda(loConsulta, loTabla);
            return null;
            
       }
    
    }

}