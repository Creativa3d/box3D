/*
* JT2USUARIOS.java
*
* Creado el 6/10/2015
*/
package utilesGUIx.aplicacion.usuarios.tablasControladoras;


import ListDatos.*;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.aplicacion.usuarios.consultas.JTFORMUSUARIOS;
import utilesGUIx.aplicacion.usuarios.tablasExtend.JTEEUSUARIOS;

import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

public class JT2USUARIOS  extends  JT2GENERALBASE2 {
    private final JTFORMUSUARIOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";

    /** Crea una nueva instancia de JT2USUARIOS */
    public JT2USUARIOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMUSUARIOS(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JTEEUSUARIOS.msCTabla);
        moParametros.setTitulo(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEUSUARIOS.msCTabla));
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

    public JT2USUARIOS(
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
//        JTEEUSUARIOS loUSUARIOS = new JTEEUSUARIOS(moServer);
//        loUSUARIOS.moList.addNew();
//        valoresDefecto(loUSUARIOS);
//
//        JPanelUSUARIOS loPanel = new JPanelUSUARIOS();
//        loPanel.setDatos(loUSUARIOS, this);
//
//        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
//        moConsulta.moList.setIndex(plIndex);
//        JTEEUSUARIOS loUSUARIOS = new JTEEUSUARIOS(moServer);
//        loUSUARIOS.moList.addNew();
//        loUSUARIOS.getCODIGOUSUARIO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOUSUARIO).getValue());
//        loUSUARIOS.moList.update(false);
//
//        loUSUARIOS.moList.moFila().setTipoModif(JListDatos.mclNada);
//        IFilaDatos loFila = (IFilaDatos)loUSUARIOS.moList.moFila().clone();
//        IResultado loResult = loUSUARIOS.borrar();
//        if(!loResult.getBien()){
//            throw new Exception(loResult.getMensaje());
//        }
//        loFila.setTipoModif (JListDatos.mclBorrar);
//        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
//        moConsulta.moList.setIndex(plIndex);
//
//        JTEEUSUARIOS loUSUARIOS = JTEEUSUARIOS.getTabla(
//                moConsulta.moList.getFields(moConsulta.lPosiCODIGOUSUARIO).getString(),
//                moServer);
//        valoresDefecto(loUSUARIOS);
//        JPanelUSUARIOS loPanel = new JPanelUSUARIOS();
//        loPanel.setDatos(loUSUARIOS, this);
//
//        if(moConsulta.moList.size()>1){
//            getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
//        }else{
//            getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
//        }            
    }


    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMUSUARIOS.mclNumeroCampos];

//        loInt[JTFORMUSUARIOS.lPosiCODIGOUSUARIO]=80;
//        loInt[JTFORMUSUARIOS.lPosiNOMBRE]=80;
//        loInt[JTFORMUSUARIOS.lPosiCLAVE]=80;
//        loInt[JTFORMUSUARIOS.lPosiPERMISO]=80;
//        loInt[JTFORMUSUARIOS.lPosiACTIVO]=80;
//        loInt[JTFORMUSUARIOS.lPosiLOGIN]=80;
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
        
        retValue.getBorrar().setActivo(false);
        retValue.getNuevo().setActivo(false);
        retValue.getEditar().setActivo(false);
        
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImpListado, mcsImpListado, "/images/Print16.gif", this));
        
    }

    public static class GestionProyectoTabla implements IGestionProyecto {

        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEEUSUARIOS(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2USUARIOS(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEEUSUARIOS.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
//            JTEEUSUARIOS loObj = JTEEUSUARIOS.getTabla(poFila, poServer);
//            JPanelUSUARIOS loPanel = new JPanelUSUARIOS();
//            loPanel.setDatos(loObj, null);
//            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            IConsulta loConsulta = null;
            JSTabla loTabla = null;
            boolean lbCache = false;

            loTabla = new JTEEUSUARIOS(poServer);
            lbCache = JTEEUSUARIOS.getPasarACache();
            if(lbCache){
                loTabla.recuperarTodosNormal(lbCache);
                loConsulta = new JConsulta(loTabla, true);
            }else{
                JTFORMUSUARIOS loConsulta1 = new JTFORMUSUARIOS(poServer);
                loConsulta1.crearSelectSimple();
                loConsulta = loConsulta1;
            }
//            return JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarBusqueda(loConsulta, loTabla);
            return null;
       }
    
    }

}