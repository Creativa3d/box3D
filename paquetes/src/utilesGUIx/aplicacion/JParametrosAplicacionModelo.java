/*
 * JParametrosAplicacion.java
 *
 * Created on 30 de julio de 2008, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;

import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.ColorCZ;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;

public class JParametrosAplicacionModelo {
    private final IListaElementos moEstructuraLista = new JListaElementos();
    private IUsuario moUsuario;
    private String msNombreProyecto;
    private String msCaptionProyecto;
    private String[] masPlugIn;

    private Object moImagenLogin;
    private IPlugInSeguridadRW moPlugInSeguridad;
    private String msFicheroActualizar;
    private String msFicheroConfiguracion=null;
    private boolean mbConexionLogin = true;
    private Object moImagenFondo;
    private ColorCZ moColorFondo= new ColorCZ(0);// Color.white;
    private int mlAlineacionHFondo = 0;
    private int mlAlineacionVFondo = 0;
    private boolean mbConservarProporcionesFondo=true;
    private boolean mbTamanoOriginalFondo=false;
    
    private boolean mbEsServidor=false;
    private String msRutaLog;
    private String msRutaBase;
    private Object moImagenIcono;
    private boolean mbLoginPBKDF2=false;
    
    

    /** Creates a new instance of JParametrosAplicacion */
    public JParametrosAplicacionModelo(
        String psNombreProyecto,
        IUsuario poUsuario,
        String[] pasPlugIn,
        IPlugInSeguridadRW poPlugInSeguridad,
        IActualizarEstruc poEstructura
        ) {
    moUsuario = poUsuario;
    msNombreProyecto = psNombreProyecto;
    msCaptionProyecto = psNombreProyecto;
    masPlugIn = pasPlugIn;
    moPlugInSeguridad = poPlugInSeguridad;
    addEstructura(poEstructura);

    }

    public IListaElementos getEstructuraLista(){
        return moEstructuraLista;
    }
    public int getEstructuraSize(){
        return moEstructuraLista.size();
    }
    public IActualizarEstruc getEstructura(int i) {
        return (IActualizarEstruc) moEstructuraLista.get(i);
    }

    public void addEstructura(IActualizarEstruc poEstructura) {
        if(poEstructura!=null){
            moEstructuraLista.add(poEstructura);
        }
    }

    public IUsuario getUsuario() {
        return moUsuario;
    }

    public void setUsuario(IUsuario poUsuario) {
        moUsuario = poUsuario;
    }

    public String getNombreProyecto() {
        return msNombreProyecto;
    }

    public void setNombreProyecto(String psNombreProyecto) {
        msNombreProyecto = psNombreProyecto;
    }

    public String[] getPlugIn() {
        return masPlugIn;
    }

    public void setPlugIn(String[] pasPlugIn) {
        masPlugIn = pasPlugIn;
    }

    public Object getImagenLogin() {
        return moImagenLogin;
    }
    public void setImagenLogin(Object poImagenLogin) {
        moImagenLogin=poImagenLogin;
    }
    public ColorCZ getColorFondo(){
        return moColorFondo;
    }
    public void setColorFondo(ColorCZ poColor){
        moColorFondo=poColor;
    }
    public boolean getConservarProporcionesFondo(){
        return mbConservarProporcionesFondo;
    }
    public void setConservarProporcionesFondo(boolean pbValor){
        mbConservarProporcionesFondo=pbValor;
    }
    public Object getImagenFondo() {
        return moImagenFondo;
    }
    public void setImagenFondo(Object poImagen) {
        moImagenFondo=poImagen;
    }
    public IPlugInSeguridadRW getPlugInSeguridadRW() {
        return moPlugInSeguridad;
    }
    public void setPlugInSeguridadRW(IPlugInSeguridadRW poSegu) {
        moPlugInSeguridad=poSegu;
    }

    /**
     * Devuelve
     * Fichero xml(generalmente URL) en formato de paquetesEjecutar
     * @return the msFicheroActualizar
     */
    public String getFicheroActualizar() {
        return msFicheroActualizar;
    }

    /**
     * Establece
     * Fichero xml(generalmente URL) en formato de paquetesEjecutar,
     * sirve para actualizar la aplicacion
     * @param msFicheroActualizar the msFicheroActualizar to set
     */
    public void setFicheroActualizar(String msFicheroActualizar) {
        this.msFicheroActualizar = msFicheroActualizar;
    }

    public void setFicheroConfiguracion(String psFicheroConfiguracion) {
        msFicheroConfiguracion=psFicheroConfiguracion;
    }
    public String getFicheroConfiguracion() {
        if(msFicheroConfiguracion!=null){
            return msFicheroConfiguracion;
        }else{
            return "ConfigurationParameters" + getNombreProyecto();
        }
    }

    /**
     * @return the mbConexionLogin
     */
    public boolean isConexionLogin() {
        return mbConexionLogin;
    }

    /**
     * @param mbConexionLogin the mbConexionLogin to set
     */
    public void setConexionLogin(boolean mbConexionLogin) {
        this.mbConexionLogin = mbConexionLogin;
    }

    public String getCaptionProyecto() {
        return msCaptionProyecto;
    }

    public void setCaptionProyecto(String psCaption) {
        msCaptionProyecto = psCaption;
    }

    /**
     * @return the mlAlineacionFondo
     */
    public int getAlineacionHFondo() {
        return mlAlineacionHFondo;
    }
    public int getAlineacionVFondo() {
        return mlAlineacionVFondo;
    }
    

    /**
     * @param mlAlineacionFondo the mlAlineacionFondo to set
     */
    public void setAlineacionHFondo(int plAlineacionFondo) {
        this.mlAlineacionHFondo = plAlineacionFondo;
    }
    /**
     * @param mlAlineacionFondo the mlAlineacionFondo to set
     */
    public void setAlineacionVFondo(int plAlineacionFondo) {
        this.mlAlineacionVFondo = plAlineacionFondo;
    }
    

    /**
     * @return the mbEsServidor
     */
    public boolean isEsServidor() {
        return mbEsServidor;
    }

    /**
     * @return the mbLoginPBKDF2
     */
    public boolean isLoginPBKDF2() {
        return mbLoginPBKDF2;
    }

    /**
     * @param mbLoginPBKDF2 the mbLoginPBKDF2 to set
     */
    public void setLoginPBKDF2(boolean mbLoginPBKDF2) {
        this.mbLoginPBKDF2 = mbLoginPBKDF2;
    }

    /**
     * @param mbEsServidor the mbEsServidor to set
     */
    public void setEsServidor(boolean mbEsServidor) {
        this.mbEsServidor = mbEsServidor;
    }

    /**
     * @return the mbTamanoOriginal
     */
    public boolean isTamanoOriginalFondo() {
        return mbTamanoOriginalFondo;
    }

    /**
     * @param mbTamanoOriginal the mbTamanoOriginal to set
     */
    public void setTamanoOriginalFondo(boolean mbTamanoOriginal) {
        this.mbTamanoOriginalFondo = mbTamanoOriginal;
    }

    public void setRutaLog(String psRuta){
        msRutaLog=psRuta;
    }
    public String getRutaLog(){
        return msRutaLog;
    }

    /**
     * @return the msRutaBase
     */
    public String getRutaBase() {
        return msRutaBase;
    }

    /**
     * @param msRutaBase the msRutaBase to set
     */
    public void setRutaBase(String msRutaBase) {
        this.msRutaBase = msRutaBase;
    }

    public Object getImagenIcono() {
        return moImagenIcono;
    }
    public void setImagenIcono(Object poImagen) {
        moImagenIcono=poImagen;
    }
    

}
