/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFXAvisos.tablasControladoras;

import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JSTabla;
import javafx.application.Platform;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JT2GENERALBASE2;
import utilesFXAvisos.forms.JPanelGUIXEVENTOS;
import utilesFXAvisos.forms.JPanelGenericoEVENTOS;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.cargaMasiva.ICargaMasiva;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.JT2GENERALBASEModelo;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.calendario.IAvisosEventosEditListener;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

public class JT2GUIXEVENTOS  extends JT2GENERALBASE2 {
      private final JTFORMGUIXEVENTOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private String msGrupo = "";
    private String msCalendarioDefecto = "";
    protected String msNombreDefecto="";
    private String msUsu="";
    private String msFechaDefecto="";
    private final JDatosGenerales moDatosGenerales;
    
    /** 
     * Crea una nueva instancia de JT2GUIXEVENTOS
     * @param poDatosGenerales 
     */
    public JT2GUIXEVENTOS(JDatosGenerales poDatosGenerales) {
        super();
        moDatosGenerales=poDatosGenerales;

        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXEVENTOS(moServer);
        moConsulta.crearSelectStandar(new JDateEdu().msFormatear("dd/MM/yyyy"), "", "");
        addBotones();
        moParametros.setColumnasVisiblesConfig(getVisibleCampos());
        moParametros.setNombre(moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOS.msCTabla));
        getParametros().setMostrarPantalla(poDatosGenerales.getMostrarPantalla());
        setLong();
        moDatosGenerales.addAvisosEditListener(new IAvisosEventosEditListener() {
            @Override
            public void eventosEdit(JTEEGUIXEVENTOS poEventosModif) {
                try {
                    refrescar();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(JT2GUIXEVENTOS.class.getName(), ex);
                }
            }
        });
        
    }

    private void setLong(){

        moParametros.setForzarLong(true);
        int[] l = new int [1];
        l[0]=550;
        moParametros.setLongitudCampos(l);
        
    }
    public JT2GUIXEVENTOS(
            JDatosGenerales poDatosGenerales
            , String psGrupo, String psCalendario
            , String psNombreDefecto, String psUsu) {
        this(poDatosGenerales);
        msGrupo = psGrupo;
        msCalendarioDefecto=psCalendario;
        msNombreDefecto=psNombreDefecto;
        msUsu=psUsu;
        moConsulta.crearSelectStandar(
                new JDateEdu().msFormatear("dd/MM/yyyy")
                , msGrupo
                , msUsu);
    }
    
    public JT2GUIXEVENTOS(
            JDatosGenerales poDatosGenerales,
            String psNomTabRelac,
            IFilaDatos poDatosRelac) {
        this(poDatosGenerales);
        setTablaRelacionada(psNomTabRelac, poDatosRelac);
    }
    
    public void setFechaDefecto(String psFecha){
        msFechaDefecto=psFecha;
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
        JPanelGenericoEVENTOS loPanel = new JPanelGenericoEVENTOS();
        loPanel.setControlador(this);
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loPanel, 620, 600, JMostrarPantallaParam.mclEdicionInternal, "Tareas " + msNombreDefecto);
        loParam.setMaximizado(false);
        getParametros().getMostrarPantalla().mostrarForm(loParam);
    }
    public void mostrarFormPrinci(int plX, int plY, int plAncho, int plAlto) throws Exception {
        JPanelGenericoEVENTOS loPanel = new JPanelGenericoEVENTOS();
        loPanel.setControlador(this);
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loPanel, plAncho, plAlto, JMostrarPantalla.mclEdicionInternal, "Tareas " + msNombreDefecto);
        loParam.setX(plX);
        loParam.setY(plY);
        loParam.setMaximizado(false);
        getParametros().getMostrarPantalla().mostrarForm(loParam);
    }
    public void mostrarFormPrinciFrame() throws Exception {
        JPanelGenericoEVENTOS loPanel = new JPanelGenericoEVENTOS();
        loPanel.setControlador(this);
        getParametros().getMostrarPantalla().mostrarForm(new JMostrarPantallaParam(loPanel, 620, 600, JMostrarPantalla.mclEdicionFrame, "Tareas " + msNombreDefecto));
    }

    public void mostrarFormPrinciMES(IProcesoThreadGroup poThread) throws Exception {
        JGUIxEventosMES loMes = new JGUIxEventosMES();
        if(poThread!=null){
            poThread.addProcesoYEjecutar(loMes, false);
        }else{
            try {
                loMes.procesar();
            } catch (Throwable ex) {
                throw new Exception(ex);
            }
        }
    }
    class JGUIxEventosMES extends JProcesoAccionAbstracX{
        private Exception moError=null;

        public JGUIxEventosMES() {
        }
        @Override
        public String getTitulo() {
            return "Avisos";
        }
        @Override
        public int getNumeroRegistros() {
            return -1;
        }
        @Override
        public void procesar() throws Throwable {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
//                    try {
//                        JPanelGenericoEVENTOSMES loPanelMes = new JPanelGenericoEVENTOSMES();
//                        loPanelMes.setControlador(JT2GUIXEVENTOSFX.this);
//                        setPanel(loPanelMes);
//                        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loPanelMes, 800, 600, JMostrarPantalla.mclEdicionInternal, "Calendario");
//                        loParam.setMaximizado(true);
//                        JT2GUIXEVENTOSFX.this.getParametros().getMostrarPantalla().mostrarForm(loParam);
//                    } catch (Exception ex) {
//                        moError = ex;
//                    }
                }
            });
            if(moError!=null){
                throw moError;
            }
        }
        @Override
        public String getTituloRegistroActual() {
            return "";
        }
        @Override
        public void mostrarMensaje(String psMensaje) {}
    }
        

    @Override
    public void anadir() throws Exception {
        JTEEGUIXEVENTOS loGUIXEVENTOS = new JTEEGUIXEVENTOS(moServer);
        loGUIXEVENTOS.moList.addNew();
        loGUIXEVENTOS.valoresDefecto();
        valoresDefecto(loGUIXEVENTOS);

        JPanelGUIXEVENTOS loPanel = new JPanelGUIXEVENTOS();
        loPanel.setDatos(moDatosGenerales,loGUIXEVENTOS, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    
    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.getList().getModoTabla() == JListDatos.mclNuevo){
            poTabla.getField(JTEEGUIXEVENTOS.lPosiGRUPO).setValue(msGrupo);
            poTabla.getField(JTEEGUIXEVENTOS.lPosiCALENDARIO).setValue(msCalendarioDefecto);
            poTabla.getField(JTEEGUIXEVENTOS.lPosiNOMBRE).setValue(msNombreDefecto);
            poTabla.getField(JTEEGUIXEVENTOS.lPosiUSUARIO).setValue(msUsu);
            
            if(JDateEdu.isDate(msFechaDefecto)){
                JDateEdu loDate = new JDateEdu(msFechaDefecto);
                //si no tiene hora (salvo las 00, q es raro q se ponga esa hora), ponemos la hora actual + 1
                if(loDate.getHora()==0){
                    loDate.setHora(new JDateEdu().getHora()+1);
                }
                poTabla.getField(JTEEGUIXEVENTOS.lPosiFECHADESDE).setValue(loDate.toString());
                loDate.add(JDateEdu.mclHoras, 1);
                poTabla.getField(JTEEGUIXEVENTOS.lPosiFECHAHASTA).setValue(loDate.msFormatear("dd/MM/yyyy HH:00"));
            }
            
        }
    }

    @Override
    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEGUIXEVENTOS loGUIXEVENTOS = JTEEGUIXEVENTOS.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCALENDARIO).getString()
                ,moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
                ,moServer);
        IFilaDatos loFila = (IFilaDatos)loGUIXEVENTOS.moList.moFila().clone();
        IResultado loResult = loGUIXEVENTOS.borrar(moDatosGenerales);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    @Override
    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEGUIXEVENTOS loGUIXEVENTOS = new JTEEGUIXEVENTOS(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEEGUIXEVENTOS.lPosiCALENDARIO,
                JTEEGUIXEVENTOS.lPosiCODIGO
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiCALENDARIO).getString(),
                moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
            });
        loGUIXEVENTOS.recuperarFiltrados(loFiltro,false,false);

        valoresDefecto(loGUIXEVENTOS);
        JPanelGUIXEVENTOS loPanel = new JPanelGUIXEVENTOS();
        loPanel.setDatos(moDatosGenerales, loGUIXEVENTOS, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public String getNombre() {
        return moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOS.msCTabla);
    }

    public boolean[] getVisibleCampos() {
        boolean[] loInt = new boolean[JTFORMGUIXEVENTOS.mclNumeroCampos];
        for(int i = 0 ; i < loInt.length; i++){
            loInt[i]=false;
        }

        loInt[JTFORMGUIXEVENTOS.lPosiPRIORIDADNOMBRE]=true;
        loInt[JTFORMGUIXEVENTOS.lPosiCALENDARIONOMBRE]=true;
        loInt[JTFORMGUIXEVENTOS.lPosiFECHADESDE]=true;
        loInt[JTFORMGUIXEVENTOS.lPosiFECHAHASTA]=true;
        loInt[JTFORMGUIXEVENTOS.lPosiNOMBRE]=true;
        loInt[JTFORMGUIXEVENTOS.lPosiTEXTO]=true;
//        loInt[JTFORMGUIXEVENTOS.lPosiREPETICION]=true;
        loInt[JTFORMGUIXEVENTOS.lPosiGRUPO]=true;
        return loInt;
    }

    @Override
    public void datosactualizados(IFilaDatos poFila) throws Exception {
        super.datosactualizados(poFila);
        moConsulta.getList().ordenar(moConsulta.lPosiFECHADESDE);
        if(getPanel()!=null){
            getPanel().refrescar();
        }
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


}