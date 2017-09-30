/*
* JT2CRMEMAILYNOTAS.java
*
* Creado el 27/4/2017
*/
package utilesFX.crm.tablasControladoras;


import ListDatos.*;
import java.text.Format;
import java.util.HashMap;
import javafx.event.EventHandler;
import utiles.JCadenas;
import utiles.JComunicacion;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;
import utilesCRM.consultas.JTFORMCRMEMAILYNOTAS;
import utilesFX.crm.forms.JPanelCRMEMAILYNOTAS;
import utilesCRM.tablasExtend.JTEECRMEMAILYNOTAS;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesFXAvisos.forms.JPanelGUIXEVENTOS;
import utilesFXAvisos.forms.JPanelMensajeFX;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.ColorCZ;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.cargaMasiva.ICargaMasiva;

import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;

import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

public class JT2CRMEMAILYNOTAS  extends JT2GENERALBASEModelo   implements ICargaMasiva {
//    public static final String mcsDatoRelacionado = "<html>Dato<br>relacionado</html>";
    public static final String mcsEMAIL = "eMail";
    private final JTFORMCRMEMAILYNOTAS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private String msGrupo;
    private String msUsu;
    private String msCodContacto;
    private String msEMailContacto;
    private String msAsunto;
    private HashMap<String, Object> moListaCampos;

    /** Crea una nueva instancia de JT2CRMEMAILYNOTAS */
    public JT2CRMEMAILYNOTAS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMCRMEMAILYNOTAS(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JFXConfigGlobal.getInstancia().getTextosForms().getTexto(JTEECRMEMAILYNOTAS.msCTabla));
        getParametros().setMostrarPantalla(poMostrarPantalla);
        Format[] loFormat = new Format[moConsulta.mclNumeroCampos];
        loFormat[moConsulta.lPosiDESCRIPCION] = new FormatHTML();
        getParametros().setFormatosCampos(loFormat);
        inicializarPlugIn(JFXConfigGlobal.getInstancia().getPlugInFactoria());
        
        JListDatosFiltroConj loElem = new JListDatosFiltroConj();
        loElem.addCondicionAND(
                JListDatos.mclTIgual, JTFORMCRMEMAILYNOTAS.lPosiTIPO, JTEECRMEMAILYNOTAS.mcsCorreo);
        loElem.inicializar(JTFORMCRMEMAILYNOTAS.getNombreTabla(), JTFORMCRMEMAILYNOTAS.getFieldsEstaticos().malTipos(), JTFORMCRMEMAILYNOTAS.getFieldsEstaticos().msNombres());
        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
                loElem,
                null,
                ColorCZ.BLUE); 
        loElem = new JListDatosFiltroConj();
        loElem.addCondicionAND(
                JListDatos.mclTIgual, JTFORMCRMEMAILYNOTAS.lPosiTIPO, JTEECRMEMAILYNOTAS.mcsTarea);
        loElem.inicializar(JTFORMCRMEMAILYNOTAS.getNombreTabla(), JTFORMCRMEMAILYNOTAS.getFieldsEstaticos().malTipos(), JTFORMCRMEMAILYNOTAS.getFieldsEstaticos().msNombres());
        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
                loElem,
                null,
                ColorCZ.GREEN_DARK);        
    }
 
    public JT2CRMEMAILYNOTAS(
            final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla
            , String psGrupo, String psUsu, String psCodContacto, String psEMailContacto
            , boolean pbFiltroContacto, String psAsunto, HashMap<String, Object> poListaCampos) {
        this(poServidorDatos, poMostrarPantalla);
        msGrupo = psGrupo;
        msCodContacto=psCodContacto;
        msUsu=psUsu;
        msEMailContacto=psEMailContacto;
        msAsunto = psAsunto;
        moListaCampos=poListaCampos;
        if(pbFiltroContacto){
            moConsulta.crearSelectContacto(msCodContacto);

        } else {
            moConsulta.crearSelect(msGrupo);
        }
    }
    public JT2CRMEMAILYNOTAS(
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
    @Override
    public IConsulta getConsulta() {
        return moConsulta;
    }

    @Override
    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    @Override
    public void anadir() throws Exception {
        JTEECRMEMAILYNOTAS loCRMEMAILYNOTAS = new JTEECRMEMAILYNOTAS(moServer);
        loCRMEMAILYNOTAS.moList.addNew();
        valoresDefecto(loCRMEMAILYNOTAS);

        JPanelCRMEMAILYNOTAS loPanel = new JPanelCRMEMAILYNOTAS();
        loPanel.setDatos(loCRMEMAILYNOTAS, this);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.moList.getModoTabla() == JListDatos.mclNuevo) {
            ((JTEECRMEMAILYNOTAS)poTabla).valoresDefecto();
            ((JTEECRMEMAILYNOTAS)poTabla).getCODIGONEGOCIACION().setValue(msGrupo);
            ((JTEECRMEMAILYNOTAS)poTabla).getCODIGOUSUARIO().setValue(msUsu);
            ((JTEECRMEMAILYNOTAS)poTabla).getCODIGOCONTACTO().setValue(msCodContacto);
            ((JTEECRMEMAILYNOTAS)poTabla).getASUNTO().setValue(msAsunto);
            
        }
    }

    @Override
    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEECRMEMAILYNOTAS loCRMEMAILYNOTAS = new JTEECRMEMAILYNOTAS(moServer);
        loCRMEMAILYNOTAS.moList.addNew();
        loCRMEMAILYNOTAS.getCODIGOEMPRESA().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOEMPRESA).getValue());
        loCRMEMAILYNOTAS.getCODIGONOTA().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGONOTA).getValue());
        loCRMEMAILYNOTAS.moList.update(false);

        loCRMEMAILYNOTAS.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loCRMEMAILYNOTAS.moList.moFila().clone();
        IResultado loResult = loCRMEMAILYNOTAS.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    @Override
    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEECRMEMAILYNOTAS loCRMEMAILYNOTAS = JTEECRMEMAILYNOTAS.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOEMPRESA).getString(),
                moConsulta.moList.getFields(moConsulta.lPosiCODIGONOTA).getString(),
                moServer);
        valoresDefecto(loCRMEMAILYNOTAS);
        

        if(!loCRMEMAILYNOTAS.getCODIGOEVENTO().isVacio()){
            JTEEGUIXEVENTOS loEvent = JTEEGUIXEVENTOS.getTabla(
                    loCRMEMAILYNOTAS.getCODIGOCALENDARIO().getString()
                    , loCRMEMAILYNOTAS.getCODIGOEVENTO().getString()
                    , loCRMEMAILYNOTAS.moList.moServidor);
            JPanelGUIXEVENTOS loPanel = new JPanelGUIXEVENTOS();
            loPanel.setDatos(
                    JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCalendario()
                    , loEvent, null, null);
            JMostrarPantallaParam loParam = new JMostrarPantallaParam((IFormEdicion)loPanel, JMostrarPantallaParam.mclEdicionFrame);
            loParam.setCallBack(new CallBack<JMostrarPantallaParam>() {
                @Override
                public void callBack(JMostrarPantallaParam poControlador) {
                    try {
                            refrescar();
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }                    
                }
            });
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarForm(loParam);
        } else  if(!loCRMEMAILYNOTAS.getGUIXMENSAJESSENDCOD().isVacio()){
            JTEEGUIXMENSAJESBD loEvent = JTEEGUIXMENSAJESBD.getTabla(
                    loCRMEMAILYNOTAS.getGUIXMENSAJESSENDCOD().getString()
                    , loCRMEMAILYNOTAS.moList.moServidor);
            JPanelMensajeFX loPanel = new JPanelMensajeFX();
            JMensaje loMen = loEvent.getMensaje();
            loMen.setCampos(moListaCampos);
            final JComunicacion loComu = new JComunicacion();
            loPanel.setDatos(loMen, loComu, null, null, new CallBack() {
                @Override
                public void callBack(Object poControlador) {
                    try {
                        if(loComu.moObjecto.equals("1")){
                            refrescar();
                        }
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                }
            }, false);

            loPanel.getParametros().setSoloLectura(true);

            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        } else {
            JPanelCRMEMAILYNOTAS loPanel = new JPanelCRMEMAILYNOTAS();
            loPanel.setDatos(loCRMEMAILYNOTAS, this);

            if(moConsulta.moList.size()>1){
                getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
            }else{
                getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
            }
        }        
                    
    }

    @Override
    public void datosactualizados(IFilaDatos poFila) throws Exception {
        if(poFila.getTipoModif() == JListDatos.mclNuevo){
            refrescar();
        } else {
            super.datosactualizados(poFila);
        }
    }
    
    public String getNombre() {
        return JFXConfigGlobal.getInstancia().getTextosForms().getTexto(JTEECRMEMAILYNOTAS.msCTabla);
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMCRMEMAILYNOTAS.mclNumeroCampos];
        loInt[JTFORMCRMEMAILYNOTAS.lPosiCODIGOEMPRESA]=0;
        loInt[JTFORMCRMEMAILYNOTAS.lPosiCODIGONOTA]=0;
        loInt[JTFORMCRMEMAILYNOTAS.lPosiCODIGOUSUARIO]=80;

        loInt[JTFORMCRMEMAILYNOTAS.lPosiFECHA]=120;
        loInt[JTFORMCRMEMAILYNOTAS.lPosiTIPO]=80;
        loInt[JTFORMCRMEMAILYNOTAS.lPosiASUNTO]=200;
        loInt[JTFORMCRMEMAILYNOTAS.lPosiDESCRIPCION]=500;
        return loInt;
    }

//    public void enviar2Plano(final JMensaje poCorreo) throws Exception{
//        JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstracX() {
//            public String getTitulo() {return "Enviando eMail";}
//            public int getNumeroRegistros() {return -1;}
//            public void procesar() throws Throwable {
//        JGUIxAvisosDatosGenerales loDa = JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos();
//        loDa.inicializarEmail();
//        loDa.enviarEmail(poCorreo);
//                refrescar();
//            }
//            public String getTituloRegistroActual() {return "";}
//            public void mostrarMensaje(String psMensaje) {}
//        });        
//    }
    private void enviarEmailIni() throws Exception{
        final JMensaje loMensaje = new JMensaje();

        loMensaje.addEmailTO(msEMailContacto);
        loMensaje.getAtributos().put(JTEECRMEMAILYNOTAS.getCODIGOCONTACTONombre(), msCodContacto);
        loMensaje.setUsuario(msUsu);
        loMensaje.setGrupo(msGrupo);
        loMensaje.setAsunto(msAsunto);
        loMensaje.setCampos(moListaCampos);
        
        JPanelMensajeFX loPanelMensaje = new JPanelMensajeFX();
        final JComunicacion loComu = new JComunicacion();
        loPanelMensaje.setDatos(loMensaje, loComu, JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().getPathPlantilla(), null, new CallBack() {
            @Override
            public void callBack(Object poControlador) {
                try {
                    if(loComu.moObjecto.equals("1")){
                        refrescar();
                    }
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }, true);
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanelMensaje, loPanelMensaje);
        
    }
    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if(e.getActionCommand().equalsIgnoreCase(mcsEMAIL)){
            if(JCadenas.isVacio(msEMailContacto)){
                JOptionPaneFX.showInputDialog("Introducir eMail", new EventHandler<JOptionPaneFX.EventInput>() {
                    @Override
                    public void handle(JOptionPaneFX.EventInput event) {
                        msEMailContacto = event.getInput();
                        if(!JCadenas.isVacio(msEMailContacto)){
                            try {
                                enviarEmailIni();
                            } catch (Exception ex) {
                                JMsgBox.mensajeErrorYLog(JT2CRMEMAILYNOTAS.this, ex);
                            }
                        } 
                    }
                });
            } else {
                enviarEmailIni();
            }

        } else {
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                    moConsulta.moList.setIndex(plIndex[i]);
//                    
//                    if(e.getActionCommand().equalsIgnoreCase(mcsDatoRelacionado)){
//                        JTEECRMEMAILYNOTAS loCRMEMAILYNOTAS = JTEECRMEMAILYNOTAS.getTabla(
//                            moConsulta.moList.getFields(moConsulta.lPosiCODIGOEMPRESA).getString(),
//                            moConsulta.moList.getFields(moConsulta.lPosiCODIGONOTA).getString(),
//                            moServer);
//
//                    }
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
        }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsDatoRelacionado, mcsDatoRelacionado, "", this));
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsEMAIL, mcsEMAIL
                , JFXConfigGlobal.getImagenCargada("/utilesFX/images/SendMail24.gif"), null, this, null, ""));

    }

    public static class GestionProyectoTabla implements IGestionProyecto {

        @Override
        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEECRMEMAILYNOTAS(poServer);
        }

        @Override
        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2CRMEMAILYNOTAS(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        @Override
        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEECRMEMAILYNOTAS.getParamPanelBusq(poServer, poMostrar);
        }

        @Override
        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEECRMEMAILYNOTAS loObj = JTEECRMEMAILYNOTAS.getTabla(poFila, poServer);
            JPanelCRMEMAILYNOTAS loPanel = new JPanelCRMEMAILYNOTAS();
            loPanel.setDatos(loObj, null);
            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        @Override
        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            IConsulta loConsulta = null;
            JSTabla loTabla = null;
            boolean lbCache = false;

            loTabla = new JTEECRMEMAILYNOTAS(poServer);
            lbCache = JTEECRMEMAILYNOTAS.getPasarACache();
            if(lbCache){
                loTabla.recuperarTodosNormal(lbCache);
                loConsulta = new JConsulta(loTabla, true);
            }else{
                JTFORMCRMEMAILYNOTAS loConsulta1 = new JTFORMCRMEMAILYNOTAS(poServer);
                loConsulta1.crearSelectSimple();
                loConsulta = loConsulta1;
            }
            return null;
            
       }
    
    }

    @Override
    public JListDatos getTablaBase() {
        return new JTEECRMEMAILYNOTAS(moServer).getList();
    }
}