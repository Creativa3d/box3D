/*
* JT2SQLGENERADOR.java
*
* Creado el 9/9/2013
*/
package paquetesGeneradorInf.gest1.tablasControladoras;

import java.awt.event.*;

import ListDatos.*;
import javax.swing.JOptionPane;

import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import paquetesGeneradorInf.gest1.tablasExtend.*;
import paquetesGeneradorInf.gest1.forms.*;
import paquetesGeneradorInf.gest1.consultas.*;
import paquetesGeneradorInf.gest1.*;
import paquetesGeneradorInf.gui.CallBackGenInf;
import paquetesGeneradorInf.gui.JGuiConsulta;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import paquetesGeneradorInf.gui.resultados.JGuiResultadosControlador;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;

import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JT2SQLGENERADOR  extends JT2GENERALBASE2 {
    private static final String mcsEjecutar = "Ejecutar";
    private static final String mcsDuplicar = "Duplicar";
    
    private final JTFORMSQLGENERADOR moConsulta;
    private final IServerServidorDatos moServer;

    private String msPadre="";
    private JGuiConsultaDatos moDatos;
    /** Crea una nueva instancia de JT2SQLGENERADOR */
    public JT2SQLGENERADOR(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMSQLGENERADOR(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADOR.msCTabla));
        moParametros.msTipoFiltroRapido=JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos;
        getParametros().setMostrarPantalla(poMostrarPantalla);
        getParametros().setPlugInPasados(true);
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

    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600, JPanelGenerico2.mclTipo, JMostrarPantalla.mclEdicionFrame);
    }
    public void mostrarFormPrinciMenu() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }
    
    public void setPadre(String psPadre){
        moConsulta.crearSelect(psPadre);
        msPadre=psPadre;
    }
    public void setDatos(JGuiConsultaDatos datos) {
        moDatos = datos;
    }

    public void anadir() throws Exception {
        final JTEESQLGENERADOR loSQLGENERADOR = new JTEESQLGENERADOR(moServer);
        loSQLGENERADOR.moList.addNew();
        valoresDefecto(loSQLGENERADOR);

//        JPanelSQLGENERADOR loPanel = new JPanelSQLGENERADOR();
//        loPanel.setDatos(loSQLGENERADOR, this);
//
//        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        
        if(moDatos == null){
            moDatos = new JGuiConsultaDatos(moServer);
        }
        JGuiConsultaDatos loDatos = (JGuiConsultaDatos) moDatos.clone();
        loDatos.setCallBack(new CallBackGenInf() {
            public void callBack(JGuiConsultaDatos poDatos) throws Exception {
                if(!poDatos.isCancelado()){
                    loSQLGENERADOR.setDatos(poDatos);
                    IResultado loResult = loSQLGENERADOR.guardar();
                    if(!loResult.getBien()){
                        throw new Exception(loResult.getMensaje());
                    }
                    loSQLGENERADOR.moList.moFila().setTipoModif(JListDatos.mclNuevo);
                    datosactualizados(loSQLGENERADOR.moList.moFila());

                }
            }
        });
        JGuiConsulta loGuiConsulta = new JGuiConsulta();
        loGuiConsulta.setDatos(loDatos);
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                new JMostrarPantallaParam(
                loGuiConsulta, 600, 500,
                JMostrarPantalla.mclEdicionFrame,
                "Consulta"));
        
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.getList().getModoTabla()==JListDatos.mclNuevo){
            poTabla.getField(JTEESQLGENERADOR.lPosiPADRE).setValue(msPadre);
        }
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        
        JTEESQLGENERADOR loSQLGENERADOR = new JTEESQLGENERADOR(moServer);
        loSQLGENERADOR.moList.addNew();
        loSQLGENERADOR.getCODIGOSQLGENERADOR().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOSQLGENERADOR).getValue());
        loSQLGENERADOR.moList.update(false);
        loSQLGENERADOR.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loSQLGENERADOR.moList.moFila().clone();
        IResultado loResult = loSQLGENERADOR.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        final JTEESQLGENERADOR loSQLGENERADOR = JTEESQLGENERADOR.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOSQLGENERADOR).getString(),
                moServer);
        valoresDefecto(loSQLGENERADOR);

        JGuiConsultaDatos loDatos = loSQLGENERADOR.getDatos();
        loDatos.setCallBack(new CallBackGenInf() {
            public void callBack(JGuiConsultaDatos poDatos) throws Exception {
                if(!poDatos.isCancelado() ){
                    loSQLGENERADOR.setDatos(poDatos);
                    IResultado loResult = loSQLGENERADOR.guardar();
                    if(!loResult.getBien()){
                        throw new Exception(loResult.getMensaje());
                    }
                    datosactualizados(loSQLGENERADOR.moList.moFila());
                }
            }
        });
        JGuiConsulta loGuiConsulta = new JGuiConsulta();
        loGuiConsulta.setDatos(loDatos);
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                new JMostrarPantallaParam(
                loGuiConsulta, 600, 500,
                JMostrarPantalla.mclEdicionFrame,
                "Consulta"));
        
        
        
    }

    public String getNombre() {
        return JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADOR.msCTabla);
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMSQLGENERADOR.mclNumeroCampos];

        loInt[JTFORMSQLGENERADOR.lPosiCODIGOSQLGENERADOR]=0;
        loInt[JTFORMSQLGENERADOR.lPosiNOMBRE]=180;
        loInt[JTFORMSQLGENERADOR.lPosiPALABRASCLAVE]=0;
        loInt[JTFORMSQLGENERADOR.lPosiPADRE]=180;
        loInt[JTFORMSQLGENERADOR.lPosiTABLAPRINCIPAL]=0;
        loInt[JTFORMSQLGENERADOR.lPosiSQL]=0;
        loInt[JTFORMSQLGENERADOR.lPosiOBSERVACIONES]=280;
        return loInt;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                    moConsulta.moList.setIndex(plIndex[i]);
                    if(e.getActionCommand().equals(mcsEjecutar)){
                        final JTEESQLGENERADOR loSQLGENERADOR = JTEESQLGENERADOR.getTabla(
                               moConsulta.moList.getFields(moConsulta.lPosiCODIGOSQLGENERADOR).getString(),
                               moServer);
                        JGuiResultadosControlador loResult = new JGuiResultadosControlador(loSQLGENERADOR.getDatos());
                        loResult.inicializar();
                        getParametros().getMostrarPantalla().mostrarFormPrinci(loResult, 800, 600, JPanelGenerico2.mclTipo, JMostrarPantalla.mclEdicionFrame);
                    }
                    if(e.getActionCommand().equals(mcsDuplicar)){
                        final JTEESQLGENERADOR loSQLGENERADOR = JTEESQLGENERADOR.getTabla(
                               moConsulta.moList.getFields(moConsulta.lPosiCODIGOSQLGENERADOR).getString(),
                               moServer);
                        JGuiConsultaDatos loDatos = loSQLGENERADOR.getDatos();
                        loDatos.setCallBack(new CallBackGenInf() {
                            public void callBack(JGuiConsultaDatos poDatos) throws Exception {
                                if(!poDatos.isCancelado() ){
                                    String lsPadre = loSQLGENERADOR.getPADRE().getString();
                                    loSQLGENERADOR.addNew();
                                    valoresDefecto(loSQLGENERADOR);
                                    loSQLGENERADOR.getPADRE().setValue(lsPadre);
                                    loSQLGENERADOR.setDatos(poDatos);
                                    IResultado loResult = loSQLGENERADOR.guardar();
                                    if(!loResult.getBien()){
                                        throw new Exception(loResult.getMensaje());
                                    }
                                    loSQLGENERADOR.moList.moFila().setTipoModif(JListDatos.mclNuevo);
                                    datosactualizados(loSQLGENERADOR.moList.moFila());
                                }
                            }
                        });
                        JGuiConsulta loGuiConsulta = new JGuiConsulta();
                        loGuiConsulta.setDatos(loDatos);
                        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                                new JMostrarPantallaParam(
                                loGuiConsulta, 600, 500,
                                JMostrarPantalla.mclEdicionFrame,
                                "Consulta"));
                    }
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsEjecutar, mcsEjecutar, "/paquetesGeneradorInf/gest1/images/Play24.gif", this));
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsDuplicar, mcsDuplicar, "/paquetesGeneradorInf/gest1/images/duplicar.png", this));
        
    }

    public static class GestionProyectoTabla implements IGestionProyecto {

        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEESQLGENERADOR(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
            IPanelControlador loResult = null;
            loResult = new JT2SQLGENERADOR(poServer, poMostrar);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEESQLGENERADOR.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEESQLGENERADOR loObj = JTEESQLGENERADOR.getTabla(poFila, poServer);
            JPanelSQLGENERADOR loPanel = new JPanelSQLGENERADOR();
            loPanel.setDatos(loObj, null);
            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
//            IConsulta loConsulta = null;
//            JSTabla loTabla = null;
//            boolean lbCache = false;
//
//            loTabla = new JTEESQLGENERADOR(poServer);
//            lbCache = JTEESQLGENERADOR.getPasarACache();
//            if(lbCache){
//                loTabla.recuperarTodosNormal(lbCache);
//                loConsulta = new JConsulta(loTabla, true);
//            }else{
//                JTFORMSQLGENERADOR loConsulta1 = new JTFORMSQLGENERADOR(poServer);
//                loConsulta1.crearSelectSimple();
//                loConsulta = loConsulta1;
//            }
//            return JDatosGeneralesP.getDatosGenerales().getMostrarPantalla().mostrarBusqueda(loConsulta, loTabla);
            throw new ClassNotFoundException("No implementado");
       }
    
    }

}
