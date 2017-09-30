/*
* JT2DOCUMTIPOS.java
*
* Creado el 19/10/2016
*/
package utilesDoc.tablasControladoras;


import ListDatos.*;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.boton.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.cargaMasiva.ICargaMasiva;

import utilesDoc.tablas.*;
import utilesDoc.tablasExtend.*;
import utilesDoc.forms.*;
import utilesDoc.consultas.*;
import utilesDoc.*;
import utilesGUIx.aplicacion.IGestionProyecto;

import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JT2DOCUMTIPOS  extends  JT2GENERALBASE2   implements ICargaMasiva {
    private final JTFORMDOCUMTIPOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";

    /** Crea una nueva instancia de JT2DOCUMTIPOS */
    public JT2DOCUMTIPOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMDOCUMTIPOS(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JDocDatosGeneralesGUIx.getTextosForms().getTexto(JTEEDOCUMTIPOS.msCTabla));
        getParametros().setMostrarPantalla(poMostrarPantalla);
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

    public JT2DOCUMTIPOS(
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
        JTEEDOCUMTIPOS loDOCUMTIPOS = new JTEEDOCUMTIPOS(moServer);
        loDOCUMTIPOS.moList.addNew();
        valoresDefecto(loDOCUMTIPOS);

        JPanelDOCUMTIPOS loPanel = new JPanelDOCUMTIPOS();
        loPanel.setDatos(loDOCUMTIPOS, this);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.moList.getModoTabla() == JListDatos.mclNuevo) {
            ((JTEEDOCUMTIPOS)poTabla).valoresDefecto();
        }
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEDOCUMTIPOS loDOCUMTIPOS = new JTEEDOCUMTIPOS(moServer);
        loDOCUMTIPOS.moList.addNew();
        loDOCUMTIPOS.getCODIGOTIPODOC().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOTIPODOC).getValue());
        loDOCUMTIPOS.moList.update(false);

        loDOCUMTIPOS.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loDOCUMTIPOS.moList.moFila().clone();
        IResultado loResult = loDOCUMTIPOS.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEDOCUMTIPOS loDOCUMTIPOS = JTEEDOCUMTIPOS.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOTIPODOC).getString(),
                moServer);
        valoresDefecto(loDOCUMTIPOS);
        JPanelDOCUMTIPOS loPanel = new JPanelDOCUMTIPOS();
        loPanel.setDatos(loDOCUMTIPOS, this);

        if(moConsulta.moList.size()>1){
            getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
        }else{
            getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        }            
    }

    public String getNombre() {
        return JDocDatosGeneralesGUIx.getTextosForms().getTexto(JTEEDOCUMTIPOS.msCTabla);
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMDOCUMTIPOS.mclNumeroCampos];

//        loInt[JTFORMDOCUMTIPOS.lPosiCODIGOTIPODOC]=80;
//        loInt[JTFORMDOCUMTIPOS.lPosiDESCRIPCION]=80;
//        loInt[JTFORMDOCUMTIPOS.lPosiIMAGENSN]=80;
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
            return new JTEEDOCUMTIPOS(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2DOCUMTIPOS(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEEDOCUMTIPOS.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEEDOCUMTIPOS loObj = JTEEDOCUMTIPOS.getTabla(poFila, poServer);
            JPanelDOCUMTIPOS loPanel = new JPanelDOCUMTIPOS();
            loPanel.setDatos(loObj, null);
            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
//            IConsulta loConsulta = null;
//            JSTabla loTabla = null;
//            boolean lbCache = false;
//
//            loTabla = new JTEEDOCUMTIPOS(poServer);
//            lbCache = JTEEDOCUMTIPOS.getPasarACache();
//            if(lbCache){
//                loTabla.recuperarTodosNormal(lbCache);
//                loConsulta = new JConsulta(loTabla, true);
//            }else{
//                JTFORMDOCUMTIPOS loConsulta1 = new JTFORMDOCUMTIPOS(poServer);
//                loConsulta1.crearSelectSimple();
//                loConsulta = loConsulta1;
//            }
//            return JDocDatosGeneralesGUIx.mostrarBusqueda(loConsulta, loTabla);
//            
            return null;
       }
    
    }

    public JListDatos getTablaBase() {
        return new JTEEDOCUMTIPOS(moServer).getList();
    }
}
