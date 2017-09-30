/*
* JDatosGenerales.java
*
* Creado el 3/11/2011
*/

package utilesGUIxAvisos.calendario;

import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.config.JDevolverTextos;
import java.util.ResourceBundle;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.config.JLectorFicherosParametros;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.actualizarEstruc.JActualizarEstrucProc;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIxAvisos.calendario.gmail.JCalendarGMAIL;
import utilesGUIxAvisos.tablasExtend.JActualizarEstrucGUIXAvisos;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;


public class JDatosGenerales {
    protected static JDevolverTextos moTextos;
    public static String msRutaTrabajo=System.getProperty("user.home");
    private IServerServidorDatos moServer;
    private IMostrarPantalla moMostrarPantalla;
    private final IListaElementos<IAvisosListener> moListener = new JListaElementos();
    private final IListaElementos<IAvisosEventosEditListener> moListenerEdit = new JListaElementos();
    private boolean mbDatosRelacionados;
    private Planificador moPlanificador;
    private final IListaElementos moFilasEventos = new JListaElementos();
    private JDateEdu moFechaDD = null;
    private JDateEdu moFechaUpdate = null;
    private JDateEdu moFechaBorrado = null;
    
    private boolean mbSMS=false;
    private boolean mbeMail=false;
    
    /** Creates a new instance of JUsuarioActual */
    public JDatosGenerales() {
        super();
    }
    /**
     * Textos de los formularios
     * @return 
     */
    public static JDevolverTextos getTextosForms(){
        if(moTextos==null){
            try{
                moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablasGUIXAvisos"));
            }catch(Throwable e){
                try{
                    moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablasGUIXAvisos"));
                }catch(Throwable e1){
                    moTextos = new JDevolverTextos(new JLectorFicherosParametros("CaptionTablasGUIXAvisos.properties"));
                    JDepuracion.anadirTexto(JDatosGenerales.class.getName(), e1);
                }
            }
        }
        return moTextos;
    }
    /**
     * Configura gmail(u otro externo) el calendario
     */
    private static JCalendarGMAIL getGMAIL(JTEEGUIXCALENDARIO poCalendario) throws Exception{
        if(poCalendario.getCLIENTID().isVacio()){
            return null;
        }else{
            JCalendarGMAIL loGMAIL = new JCalendarGMAIL();
            loGMAIL.authorize(poCalendario.getCLIENTID().getString(), poCalendario.getCLIENTSECRET().getString(), msRutaTrabajo);
            return loGMAIL;
        }
    }
    /**
     * añade o actualiza de gmail(u otro externo) el calendario
     * @param poCalendario
     * @throws java.lang.Exception
     */
    public static void sincronizar(JTEEGUIXCALENDARIO poCalendario) throws Exception{
        JCalendarGMAIL loExt = getGMAIL(poCalendario);
        if(loExt!=null){
            loExt.addoUpdateCalendar(poCalendario);
        }
    }
    /**
     * añade o actualiza de gmail(u otro externo) el evento
     * @param poCalendario
     * @param poEvento
     * @throws java.lang.Exception
     */
    public static void sincronizar(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws Exception{
        JCalendarGMAIL loExt = getGMAIL(poCalendario);
        if(loExt!=null){
            loExt.addoUpdateEvent(poCalendario, poEvento);
        }
    }
    /**
     * borra de gmail(u otro externo) el calendario
     * @param poCalendario
     * @throws java.lang.Exception
     */
    public static void sincronizarBorrar(JTEEGUIXCALENDARIO poCalendario) throws Exception{
        JCalendarGMAIL loExt = getGMAIL(poCalendario);
        if(loExt!=null){
            loExt.deleteCalendar(poCalendario);
        }
    }
    /**
     * borra de gmail(u otro externo) el evento
     * @param poCalendario
     * @param poEvento
     * @throws java.lang.Exception
     */
    public static void sincronizarBorrar(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws Exception{
        JCalendarGMAIL loExt = getGMAIL(poCalendario);
        if(loExt!=null && !poEvento.getIDENTIFICADOREXTERNO().isVacio()){
            loExt.deleteEvent(poCalendario, poEvento);
        }
    }
    
    public static void sincronizarImportar(JTEEGUIXCALENDARIO poCalendario, JDateEdu poUltModifAPartir) throws Exception{
        JCalendarGMAIL loExt = getGMAIL(poCalendario);
        if(loExt!=null){
            JTEEGUIXEVENTOS loEvent = loExt.getEvents(poCalendario, poUltModifAPartir);
            
            if(loEvent.moveFirst()){
                do{
                    JTEEGUIXEVENTOS loAux = JTEEGUIXEVENTOS.getTablaIdentExt(loEvent.getIDENTIFICADOREXTERNO().getString(), poCalendario.moList.moServidor);
                    if(!loAux.moveFirst()){
                        loAux.addNew();
                        loAux.moList.getFields().cargar(loEvent.moList.moFila());
                        IResultado loResult = loAux.guardar(null, true);
                        if(!loResult.getBien()){
                            throw new Exception(loResult.getMensaje());
                        }
                    }
                }while(loEvent.moveNext());
            }
                    
        }
    }
    
    /**
     * El servidor datos
     * @param poServer
     */
    public void setServer(IServerServidorDatos poServer){
        moServer=poServer;
    }    
    /**
     * El servidor datos
     * @return 
     */
    public IServerServidorDatos getServer(){
        return moServer;
    }
    
    /**
     * Para mostrar las pantallas
     * @return 
     */
    public IMostrarPantalla getMostrarPantalla(){
        IMostrarPantalla loM = moMostrarPantalla;
        if(loM==null){
            loM=JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla();
        }
        return loM;
    }
    
    /**
     * Para mostrar las pantallas
     * @param poMostrarPantalla
     */
    public void setMostrarPantalla(IMostrarPantalla poMostrarPantalla){
        moMostrarPantalla=poMostrarPantalla;
    }
    
    /**
     * Añade un listener
     * @param poLis
     */
    public void addAvisosListener(IAvisosListener poLis){
        moListener.add(poLis);
    }
    /**
     * Borra un listener
     * @param poLis
     */
    public void removeAvisosListener(IAvisosListener poLis){
        moListener.remove(poLis);
    }
    /**
     * Añade un listener
     * @param poLis
     */
    public void addAvisosEditListener(IAvisosEventosEditListener poLis){
        moListenerEdit.add(poLis);
    }
    /**
     * Borra un listener
     * @param poLis
     */
    public void removeAvisosEditListener(IAvisosEventosEditListener poLis){
        moListenerEdit.remove(poLis);
    }
    /**
     * Establece si la aplicación principal (la que usa los avisos) 
     * implementa el mostrar los datos relacionados
     * @param pbDatosRelacionados
     */
    public void setDatosRelacionados(boolean pbDatosRelacionados){
        mbDatosRelacionados=pbDatosRelacionados;
    }
    /**
     * Indica si la aplicación principal (la que usa los avisos) 
     * implementa el mostrar los datos relacionados
     * debe haber un listener como mínimo para que funcione los datos relacionados
     * @return 
     */
    public boolean isDatosRelacionados(){
        return mbDatosRelacionados && moListener.size()>0; 
    }

    /**Muestra los datos relacionados(de la aplicación principal) con el evento poGUIXEVENTO
     * @param poGUIXEVENTOS
     * @throws java.lang.Throwable
     */
    public void mostrarDatosRelacionados(JTEEGUIXEVENTOS poGUIXEVENTOS) throws Throwable {
        for(int i = 0 ; i < moListener.size(); i++){
            IAvisosListener loL =  (IAvisosListener) moListener.get(i);
            loL.mostrar(poGUIXEVENTOS);
        }
    }

    public void mandarAvisosListener(JTEEGUIXAVISOS poAviso) throws Throwable {
        for(int i = 0 ; i < moListener.size(); i++){
            IAvisosListener loAvisos = (IAvisosListener) moListener.get(i);
            loAvisos.avisos(poAviso);
        }
    }
    public void mandarAvisosListener(JTEEGUIXEVENTOS poEvento) throws Throwable{
        for(int i = 0 ; i < moListener.size(); i++){
            IAvisosListener loAvisos = (IAvisosListener) moListener.get(i);
            loAvisos.eventos(poEvento);
        }
    }
    
    public void lanzarEventosEdit(JTEEGUIXEVENTOS poEventosModif) {
        for(int i = 0 ; i < moListenerEdit.size(); i++){
            IAvisosEventosEditListener loAvisos = (IAvisosEventosEditListener) moListenerEdit.get(i);
            loAvisos.eventosEdit(poEventosModif);
        }
    }
    
    public synchronized Planificador getPlanificador(){
        if(moPlanificador==null){
            moPlanificador = new Planificador(this);
            moPlanificador.arrancarTimer();
        }
        return moPlanificador;
    }
    
    
    public synchronized void addFilaEvento(IFilaDatos poFila){
        switch (poFila.getTipoModif()) {
            case JListDatos.mclBorrar:
                moFechaBorrado = new JDateEdu();
                break;
            case JListDatos.mclNuevo:
                moFechaDD= new JDateEdu();
                break;
            default:
                moFechaUpdate = new JDateEdu();
                break;
        }
        moFilasEventos.add(new EventosIntent(poFila));
    }
    public synchronized void removeEvento(EventosIntent poFila){
        moFilasEventos.remove(poFila);
    }
    public synchronized EventosIntent getEventoSigYBorrar(){
        EventosIntent loResult = null;
        if(moFilasEventos.size()>0){
            loResult = (EventosIntent) moFilasEventos.get(0);
            moFilasEventos.remove(0);
        }
        return loResult;
    }
    public synchronized void addEvento(EventosIntent loEvento) {
        moFilasEventos.add(loEvento);
    }

    public JDateEdu getFechaDD() {
        return moFechaDD;
    }

    public JDateEdu getFechaUpdate() {
        return moFechaUpdate;
    }

    public JDateEdu getFechaBorrado() {
        return moFechaBorrado;
    }

    public void lanzaActualizarEstructuraYDatosEsperar() throws Throwable{
        IListaElementos loList = new JListaElementos();
        loList.add(new JActualizarEstrucGUIXAvisos());
        JActualizarEstrucProc loPro = new JActualizarEstrucProc(
                loList,
                getServer());
        loPro.procesar();

    }
    public static class EventosIntent {
        public int intentos = 0;
        public IFilaDatos moFila;

        public EventosIntent(IFilaDatos moFila) {
            this.moFila = moFila;
        }
        
        
    }

    /**
     * @return the mbSMS
     */
    public boolean isSMS() {
        return mbSMS;
    }

    /**
     * @param mbSMS the mbSMS to set
     */
    public void setSMS(boolean mbSMS) {
        this.mbSMS = mbSMS;
    }

    /**
     * @return the mbeMail
     */
    public boolean iseMail() {
        return mbeMail;
    }

    /**
     * @param mbeMail the mbeMail to set
     */
    public void seteMail(boolean mbeMail) {
        this.mbeMail = mbeMail;
    }
}
