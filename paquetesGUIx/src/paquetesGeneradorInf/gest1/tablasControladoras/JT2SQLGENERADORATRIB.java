/*
* JT2SQLGENERADORATRIB.java
*
* Creado el 9/9/2013
*/
package paquetesGeneradorInf.gest1.tablasControladoras;

import java.awt.event.*;

import ListDatos.*;

import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import paquetesGeneradorInf.gest1.tablasExtend.*;
import paquetesGeneradorInf.gest1.forms.*;
import paquetesGeneradorInf.gest1.consultas.*;
import paquetesGeneradorInf.gest1.*;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.aplicacion.IGestionProyecto;

import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JT2SQLGENERADORATRIB  extends JT2GENERALBASE2 {
    private final JTFORMSQLGENERADORATRIB moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";

    /** Crea una nueva instancia de JT2SQLGENERADORATRIB */
    public JT2SQLGENERADORATRIB(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMSQLGENERADORATRIB(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADORATRIB.msCTabla));
        getParametros().setMostrarPantalla(poMostrarPantalla);
//        inicializarPlugIn(JDatosGeneralesP.getDatosGeneralesPlugIn());
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
//                Color.red);
    }

    public JT2SQLGENERADORATRIB(
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
        JTEESQLGENERADORATRIB loSQLGENERADORATRIB = new JTEESQLGENERADORATRIB(moServer);
        loSQLGENERADORATRIB.moList.addNew();
        valoresDefecto(loSQLGENERADORATRIB);

        JPanelSQLGENERADORATRIB loPanel = new JPanelSQLGENERADORATRIB();
        loPanel.setDatos(loSQLGENERADORATRIB, this);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEESQLGENERADORATRIB loSQLGENERADORATRIB = new JTEESQLGENERADORATRIB(moServer);
        loSQLGENERADORATRIB.moList.addNew();
        loSQLGENERADORATRIB.getCODIGOSQLGENERADOR().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOSQLGENERADOR).getValue());
        loSQLGENERADORATRIB.getATRIBUTODEF().setValue(moConsulta.moList.getFields(moConsulta.lPosiATRIBUTODEF).getValue());
        loSQLGENERADORATRIB.moList.update(false);

        loSQLGENERADORATRIB.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loSQLGENERADORATRIB.moList.moFila().clone();
        IResultado loResult = loSQLGENERADORATRIB.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEESQLGENERADORATRIB loSQLGENERADORATRIB = JTEESQLGENERADORATRIB.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOSQLGENERADOR).getString(),
                moConsulta.moList.getFields(moConsulta.lPosiATRIBUTODEF).getString(),
                moServer);
        valoresDefecto(loSQLGENERADORATRIB);
        JPanelSQLGENERADORATRIB loPanel = new JPanelSQLGENERADORATRIB();
        loPanel.setDatos(loSQLGENERADORATRIB, this);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public String getNombre() {
        return JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADORATRIB.msCTabla);
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMSQLGENERADORATRIB.mclNumeroCampos];

//        loInt[JTFORMSQLGENERADORATRIB.lPosiCODIGOSQLGENERADOR]=80;
//        loInt[JTFORMSQLGENERADORATRIB.lPosiATRIBUTODEF]=80;
//        loInt[JTFORMSQLGENERADORATRIB.lPosiVALOR]=80;
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
            return new JTEESQLGENERADORATRIB(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2SQLGENERADORATRIB(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEESQLGENERADORATRIB.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEESQLGENERADORATRIB loObj = JTEESQLGENERADORATRIB.getTabla(poFila, poServer);
            JPanelSQLGENERADORATRIB loPanel = new JPanelSQLGENERADORATRIB();
            loPanel.setDatos(loObj, null);
            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
//            IConsulta loConsulta = null;
//            JSTabla loTabla = null;
//            boolean lbCache = false;
//
//            loTabla = new JTEESQLGENERADORATRIB(poServer);
//            lbCache = JTEESQLGENERADORATRIB.getPasarACache();
//            if(lbCache){
//                loTabla.recuperarTodosNormal(lbCache);
//                loConsulta = new JConsulta(loTabla, true);
//            }else{
//                JTFORMSQLGENERADORATRIB loConsulta1 = new JTFORMSQLGENERADORATRIB(poServer);
//                loConsulta1.crearSelectSimple();
//                loConsulta = loConsulta1;
//            }
//            return JDatosGeneralesP.getDatosGenerales().mostrarBusqueda(loConsulta, loTabla);
            throw new ClassNotFoundException("No implementado");
            
       }
    
    }

}
