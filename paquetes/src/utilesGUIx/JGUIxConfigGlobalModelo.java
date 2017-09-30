/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.IServerServidorDatos;
import ListDatos.config.JDevolverTextos;
import utilesCRM.JDatosGeneralesCRM;
import utilesDoc.JDocDatosGeneralesModelo;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.aplicacion.JGestionProyecto;
import utilesGUIx.aplicacion.avisos.JAvisosConj;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.JPanelGeneralParametros;
import utilesGUIx.formsGenericos.boton.ICargarIcono;
import utilesGUIx.plugin.IPlugInFactoria;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.calendario.JDatosGenerales;

public class JGUIxConfigGlobalModelo {
    private static JGUIxConfigGlobalModelo moInstancia;
    public static final int mclFormatoNormal=0;
    public static final int mclFormatoTablet=1;
    public static final int mclFormatoPantallasGrandes=2;

    private int mlTipoDefectoCadenaBD = JTipoTextoEstandar.mclTextCadena;
    
    private String msTipoFiltroRapidoDefecto = JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos;
    private String msFicheroLongTablas = "longTablas.xml";
    private ICargarIcono moCargarIcono;
    private IPlugInFactoria moPluginFactoria;
    
    private IMostrarPantalla moMostrarPantallaEstatico;
    private JDevolverTextos moTextosForms = new JDevolverTextos();

    private int mlFormato=JGUIxConfigGlobalModelo.mclFormatoNormal;
    
    private JDatosGenerales moDatosGeneralesCalendario;

    private JGUIxAvisosDatosGenerales moDatosGeneralesAvisos;
    
    private JDatosGeneralesCRM moDatosGeneralesCRM;
    
    private JDocDatosGeneralesModelo moGestorDocumental;
    
    private JAvisosConj moAvisosConj;
    private JGestionProyecto moGestionProyecto;
    private IPlugInSeguridadRW moPlugInSeguridad;
    private String msUsuario;
        
    private JGUIxConfigGlobalModelo(){
    }
    static {
        moInstancia = new JGUIxConfigGlobalModelo();
    }
    public static JGUIxConfigGlobalModelo getInstancia(){
        return moInstancia;
    }

    public IServerServidorDatos getServer(){
        return getPlugInFactoria().getPlugInContexto().getServer();
    }

    /**
     * Tipo de filtro rapido por defecto del JPanelGenerico y JPanelGenerico2
     * @return 
     */
    public String getTipoFiltroRapidoDefecto() {
        return msTipoFiltroRapidoDefecto;
    }

    /**
     * @see JGUIxConfigGlobal.getTipoFiltroRapidoDefecto
     * @param msTipoFiltroRapidoDefecto the msTipoFiltroRapidoDefecto to set
     */
    public void setTipoFiltroRapidoDefecto(String msTipoFiltroRapidoDefecto) {
        this.msTipoFiltroRapidoDefecto = msTipoFiltroRapidoDefecto;
    }
    /**
     * Fichero de longitudes/ordenes de JPanelGenerico y JPanelGenerico2
     * @return the msFicheroLongTablas
     */
    public String getFicheroLongTablas() {
        return msFicheroLongTablas;
    }

    /**
     * @see JGUIxConfigGlobal.getFicheroLongTablas
     * @param msFicheroLongTablas the msFicheroLongTablas to set
     */
    public void setFicheroLongTablas(String msFicheroLongTablas) {
        this.msFicheroLongTablas = msFicheroLongTablas;
    }

    /**
     * @return the moCargarIcono
     */
    public ICargarIcono getCargarIcono() {
        return moCargarIcono;
    }

    /**
     * @param moCargarIcono the moCargarIcono to set
     */
    public void setCargarIcono(ICargarIcono moCargarIcono) {
        this.moCargarIcono = moCargarIcono;
    }
    public void setPlugInFactoria(IPlugInFactoria poPluginFactoria){
        moPluginFactoria = poPluginFactoria;

    }
    public IPlugInFactoria getPlugInFactoria(){
        return moPluginFactoria;

    }
    /**
     * @return Devuelve el mostrar pantalla por defecto
     */
    public synchronized IMostrarPantalla getMostrarPantalla(){
        return moMostrarPantallaEstatico;
    }
    /**
     * Establece el mostrar pantalla por defecto
     * @param poMostrar
     */
    public synchronized void setMostrarPantalla(IMostrarPantalla poMostrar){
        moMostrarPantallaEstatico = poMostrar;
    }

    /**
     * @return tipo por defecto de los cuadros de texto
     */
    public int getTipoDefectoCadenaBD() {
        return mlTipoDefectoCadenaBD;
    }

    /**
     * @param mlTipoDefectoCadenaBD tipo por defecto de los cuadros de texto
     */
    public void setTipoDefectoCadenaBD(int mlTipoDefectoCadenaBD) {
        this.mlTipoDefectoCadenaBD = mlTipoDefectoCadenaBD;
    }

    /**
     * @return the moTextosForms
     */
    public JDevolverTextos getTextosForms() {
        return moTextosForms;
    }

    /**
     * @param moTextosForms the moTextosForms to set
     */
    public void setTextosForms(JDevolverTextos moTextosForms) {
        this.moTextosForms = moTextosForms;
    }
    public int getFormato(){
        return mlFormato;
    }
    public void setFormato(int plFormato){
        mlFormato = plFormato;
    }

    /**
     * @return the moDatosGeneralesCalendario
     */
    public JDatosGenerales getDatosGeneralesCalendario() {
        return moDatosGeneralesCalendario;
    }

    public IProcesoThreadGroup getThreadGroup(){
        return getPlugInFactoria().getPlugInContexto().getThreadGroup();
    }
    /**
     * @param moDatosGeneralesCalendario the moDatosGeneralesCalendario to set
     */
    public void setDatosGeneralesCalendario(JDatosGenerales moDatosGeneralesCalendario) {
        this.moDatosGeneralesCalendario = moDatosGeneralesCalendario;
    }

    /**
     * @return the moDatosGeneralesAvisos
     */
    public JGUIxAvisosDatosGenerales getDatosGeneralesAvisos() {
        return moDatosGeneralesAvisos;
    }

    /**
     * @param moDatosGeneralesAvisos the moDatosGeneralesAvisos to set
     */
    public void setDatosGeneralesAvisos(JGUIxAvisosDatosGenerales moDatosGeneralesAvisos) {
        this.moDatosGeneralesAvisos = moDatosGeneralesAvisos;
    }

    /**
     * datos generales crm
     * @return the moDatosGeneralesCRM
     */
    public JDatosGeneralesCRM getDatosGeneralesCRM() {
        return moDatosGeneralesCRM;
    }

    /**
     * @param moDatosGeneralesCRM the moDatosGeneralesCRM to set
     */
    public void setDatosGeneralesCRM(JDatosGeneralesCRM moDatosGeneralesCRM) {
        this.moDatosGeneralesCRM = moDatosGeneralesCRM;
    }

    /**
     * @return the moAvisos
     */
    public JAvisosConj getAvisosConj() {
        return moAvisosConj;
    }

    /**
     * @param moAvisos the moAvisos to set
     */
    public void setAvisosConj(JAvisosConj moAvisos) {
        this.moAvisosConj = moAvisos;
    }

    /**
     * @return the moGestionProyecto
     */
    public JGestionProyecto getGestionProyecto() {
        return moGestionProyecto;
    }

    /**
     * @param moGestionProyecto the moGestionProyecto to set
     */
    public void setGestionProyecto(JGestionProyecto moGestionProyecto) {
        this.moGestionProyecto = moGestionProyecto;
    }

    /**
     * @return the moPlugInSeguridad
     */
    public IPlugInSeguridadRW getPlugInSeguridad() {
        return moPlugInSeguridad;
    }

    /**
     * @param moPlugInSeguridad the moPlugInSeguridad to set
     */
    public void setPlugInSeguridad(IPlugInSeguridadRW moPlugInSeguridad) {
        this.moPlugInSeguridad = moPlugInSeguridad;
    }
    

    /**
     * @return the moGestorDocumental
     */
    public JDocDatosGeneralesModelo getGestorDocumental() {
        return moGestorDocumental;
    }

    /**
     * @param moGestorDocumental the moGestorDocumental to set
     */
    public void setGestorDocumental(JDocDatosGeneralesModelo moGestorDocumental) {
        this.moGestorDocumental = moGestorDocumental;
    }
    
    /**
     * @return the msUsuario
     */
    public String getUsuario() {
        return msUsuario;
    }

    /**
     * @param msUsuario the msUsuario to set
     */
    public void setUsuario(String msUsuario) {
        this.msUsuario = msUsuario;
    }
    
    
    
}
