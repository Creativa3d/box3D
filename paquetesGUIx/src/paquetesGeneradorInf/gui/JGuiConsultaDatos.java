/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui;

import ListDatos.IServerServidorDatos;
import ListDatos.JSelect;
import impresionJasper.JInfGeneralJasper;
import java.util.ResourceBundle;
import ListDatos.config.JDevolverTextos;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.config.JDatosGeneralesXML;
import utiles.config.JLectorFicherosParametros;
import utilesBD.filasPorColumnas.JTEEATRIBUTOS;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.controlProcesos.JProcesoThreadGroup;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public class JGuiConsultaDatos implements Cloneable{
    private JDevolverTextos moTextos;
    private IServerServidorDatos moServer;
    private JSelect moSelect = null;
    private String msNombre="Consulta1";
    private boolean mbUsarNombresFisicos = false;
    private IMostrarPantalla moMostrar;
    private String msImpresionLogo;
    private String msImpresionTitulo;
    private int mlImpresionLineas = JInfGeneralJasper.mclEstiloLineasHorizontales;
    private IProcesoThreadGroup moThread;
    private JDatosGeneralesXML moDatosGeneralesXML;
    private JListaElementos moTablasAExcluir = new JListaElementos();

    private JListaElementos moCamposRapidosListaTablasExtra = new JListaElementos();
    
    private CallBackGenInf moCallBack=null;
    private boolean mbCancelado;
    private String msObservaciones;

    public JGuiConsultaDatos(
            IServerServidorDatos poServer
            ){
        moServer = poServer;
    }
    public JGuiConsultaDatos(
            IServerServidorDatos poServer,
            JDevolverTextos poTextos,
            JSelect poSelect
            ){
        moServer = poServer;
        moTextos = poTextos;
        moSelect = poSelect;
    }

    public synchronized void setSelect(JSelect poSelect) {
        moSelect = poSelect;
    }
    public synchronized JSelect getSelect(){
        if(moSelect==null){
            moSelect = new JSelect();
        }
        return moSelect;
    }

    public synchronized void setTextosForms(JDevolverTextos poTextos){
        moTextos = poTextos;
    }
    public synchronized void setTextosForms(String psFichero){
        moTextos = new JDevolverTextos(ResourceBundle.getBundle(psFichero));
    }
    public synchronized JDevolverTextos getTextosForms(){
        if(moTextos==null){
            try{
                setTextosForms("CaptionTablas");
            }catch(Exception e){
                moTextos = new JDevolverTextos(
                        new JLectorFicherosParametros("CaptionTablas.properties")
                        );
            }
        }
        return moTextos;
    }

    /**
     * @return the moServer
     */
    public IServerServidorDatos getServer() {
        return moServer;
    }

    /**
     * @param moServer the moServer to set
     */
    public void setServer(IServerServidorDatos moServer) {
        this.moServer = moServer;
    }


    /**
     * @return the msNombre
     */
    public String getNombre() {
        return msNombre;
    }

    /**
     * @param msNombre the msNombre to set
     */
    public void setNombre(String msNombre) {
        this.msNombre = msNombre;
    }

    /**
     * @return the mbUsarNombresFisicos
     */
    public boolean isUsarNombresFisicos() {
        return mbUsarNombresFisicos;
    }

    /**
     * @param mbUsarNombresFisicos the mbUsarNombresFisicos to set
     */
    public void setUsarNombresFisicos(boolean mbUsarNombresFisicos) {
        this.mbUsarNombresFisicos = mbUsarNombresFisicos;
    }
    /**
     * @return the moMostrar
     */
    public synchronized IMostrarPantalla getMostrarPantalla() {
        if(moMostrar == null){
            moMostrar = JGUIxConfigGlobal.getInstancia().getMostrarPantalla();
        }
        return moMostrar;
    }
    public synchronized void setMostrarPantalla(IMostrarPantalla poMostrar){
        moMostrar = poMostrar;
    }

    public String getImpresionLogo() {
        return msImpresionLogo;
    }
    public void setImpresionLogo(String psImagen) {
        msImpresionLogo = psImagen;
    }

    public String getImpresionTitulo() {
        return msImpresionTitulo;
    }
    public void setImpresionTitulo(String psTitulo) {
        msImpresionTitulo=psTitulo;
    }

    public int getImpresionLineas() {
        return mlImpresionLineas;
    }

    public void setImpresionLineas(int plEstiloLineas) {
        mlImpresionLineas = plEstiloLineas;
    }

    public synchronized void setProcesoThread(IProcesoThreadGroup poProceso){
        moThread = poProceso;
    }
    public synchronized IProcesoThreadGroup getProcesoThread(){
        if(moThread == null){
            moThread = new JProcesoThreadGroup();
        }
        return moThread;
    }

    public void addCamposRapidosTablaExtra(JTEEATRIBUTOS poAtrib){
        moCamposRapidosListaTablasExtra.add(poAtrib);
    }
    public IListaElementos getCamposRapidosListaTablasExtra(){
        return moCamposRapidosListaTablasExtra;
    }

    /**
     * @return the moDatosGeneralesXML
     */
    public JDatosGeneralesXML getDatosGeneralesXML() {
        return moDatosGeneralesXML;
    }

    /**
     * @param moDatosGeneralesXML the moDatosGeneralesXML to set
     */
    public void setDatosGeneralesXML(JDatosGeneralesXML moDatosGeneralesXML) {
        this.moDatosGeneralesXML = moDatosGeneralesXML;
    }
    public void addTablaAExcluir(String psTabla){
        moTablasAExcluir.add(psTabla);
    }
    public boolean isTablaAExcluir(String psTabla){
        boolean lbResult = false;
        for(int i = 0 ; i < moTablasAExcluir.size() && !lbResult; i++){
            lbResult = ((String)moTablasAExcluir.get(i)).equalsIgnoreCase(psTabla);
        }
        return lbResult;
    }
    public IListaElementos getTablasAExcluir(){
        return moTablasAExcluir;
    }

    public Object clone() throws CloneNotSupportedException {
        JGuiConsultaDatos loC = (JGuiConsultaDatos) super.clone();
        if(moSelect!=null){
            loC.moSelect = (JSelect) moSelect.clone();
        }
        loC.moCamposRapidosListaTablasExtra = (JListaElementos) moCamposRapidosListaTablasExtra.clone();
        loC.moTablasAExcluir = (JListaElementos) moTablasAExcluir.clone();
        return loC;
    }

    /**
     * @return the mbEnlazado
     */
    public CallBackGenInf getCallBack() {
        return moCallBack;
    }

    /**
     * @param mbEnlazado the mbEnlazado to set
     */
    public void setCallBack(CallBackGenInf poCallBack) {
        this.moCallBack = poCallBack;
    }

    public void setCancelado(boolean b) {
        mbCancelado = b;
    }
    public boolean isCancelado() {
        return mbCancelado;
    }

    /**
     * @return the msObservaciones
     */
    public String getObservaciones() {
        return msObservaciones;
    }

    /**
     * @param msObservaciones the msObservaciones to set
     */
    public void setObservaciones(String msObservaciones) {
        this.msObservaciones = msObservaciones;
    }


}
