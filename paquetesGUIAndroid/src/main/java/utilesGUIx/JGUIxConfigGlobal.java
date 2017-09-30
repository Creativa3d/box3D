/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.config.JDevolverTextos;
import utilesGUIx.plugin.*;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.JPanelGeneralParametros;
import utilesGUIx.formsGenericos.boton.ICargarIcono;

public class JGUIxConfigGlobal {
    private static JGUIxConfigGlobal moInstancia;

    private int moBackColorFocoDefecto ;
    private int moBackColorConDatosDefecto ;
    private int moLabelColorObligatorio ;
    private int moForeColorCambio ;

    private int mlTipoDefectoCadenaBD = 0;

    private String msEdicionNavegadorMensajeSoloLectura="El formulario es de solo lectura";
    private int mlEdicionNavegadorTipoSalida = 0;

    private boolean mbLabelHTMLDefecto = false;

    private JDevolverTextos moToolTipTextLabels;
    private JDevolverTextos moAyudaURLLabels;

    private JGUIxConfigGlobal(){
    }
    static {
        moInstancia = new JGUIxConfigGlobal();
    }
    public static JGUIxConfigGlobal getInstancia(){
        return moInstancia;
    }

    /**
     * @return el color del los cuadros de introduccion de datos por defecto cuando el control coje el foco
     */
    public int getBackColorFocoDefecto(){
        return moBackColorFocoDefecto;
    }
    /**
     * el color del los cuadros de introduccion de datos por defecto cuando el control coje el foco
     * @param moBackColorFocoDefecto
     */
    public void setBackColorFocoDefecto(int moBackColorFocoDefecto) {
        this.moBackColorFocoDefecto = moBackColorFocoDefecto;
    }
    /**
     *
     * @return el color por defecto de los cuadros de datos cuando tienen datos (port ejemplo en JTextFieldCZ hay algun texto escrito)
     */
    public int getBackColorConDatos(){
        return moBackColorConDatosDefecto;
    }

    /**
     * el color por defecto de los cuadros de datos cuando tienen datos (port ejemplo en JTextFieldCZ hay algun texto escrito)
     * @param moBackColorConDatosDefecto
     */
    public void setBackColorConDatosDefecto(int moBackColorConDatosDefecto) {
        this.moBackColorConDatosDefecto = moBackColorConDatosDefecto;
    }

    /**
     *
     * @return El color por defecto de los label cuando el cuadro asociado es obligatorio
     */
    public int getLabelColorObligatorio(){
        return moLabelColorObligatorio;
    }

    /**
     * @param moLabelColorObligatorio the moLabelColorObligatorio to set
     */
    public void setLabelColorObligatorio(int moLabelColorObligatorio) {
        this.moLabelColorObligatorio = moLabelColorObligatorio;
    }

    /**
     * @return Devuelve el color de la letra cuando se ha modificado el valor original
     */
    public int getForeColorCambio() {
        return moForeColorCambio;
    }

    /**
     * establece color de la letra cuando se ha modificado el valor original
     * @param moForeColorCambio 
     */
    public void setForeColorCambio(int moForeColorCambio) {
        this.moForeColorCambio = moForeColorCambio;
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
     * @return Devuelve el mostrar pantalla por defecto
     */
    public synchronized IMostrarPantalla getMostrarPantalla(){
        return JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla();
    }
    /**
     * Establece el mostrar pantalla por defecto
     */
    public synchronized void setMostrarPantalla(IMostrarPantalla poMostrar){
        JGUIxConfigGlobalModelo.getInstancia().setMostrarPantalla(poMostrar);
    }
    /**
     * Tipo de filtro rapido por defecto del JPanelGenerico y JPanelGenerico2
     * @return 
     */
    public String getTipoFiltroRapidoDefecto() {
        return JGUIxConfigGlobalModelo.getInstancia().getTipoFiltroRapidoDefecto();
    }

    /**
     * @see JGUIxConfigGlobal.getTipoFiltroRapidoDefecto
     * @param msTipoFiltroRapidoDefecto the msTipoFiltroRapidoDefecto to set
     */
    public void setTipoFiltroRapidoDefecto(String msTipoFiltroRapidoDefecto) {
        JGUIxConfigGlobalModelo.getInstancia().setTipoFiltroRapidoDefecto(msTipoFiltroRapidoDefecto);
    }

    /**
     * Fichero de longitudes/ordenes de JPanelGenerico y JPanelGenerico2
     * @return the msFicheroLongTablas
     */
    public String getFicheroLongTablas() {
        return JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas();
    }

    /**
     * @see JGUIxConfigGlobal.getFicheroLongTablas
     * @param msFicheroLongTablas the msFicheroLongTablas to set
     */
    public void setFicheroLongTablas(String msFicheroLongTablas) {
        JGUIxConfigGlobalModelo.getInstancia().setFicheroLongTablas(msFicheroLongTablas);
    }

    /**
     * Mensaje de solo lectura, para el form. de edicion JPanelEdicionNavegador
     * @return the msMensajeSoloLectura
     */
    public String getEdicionNavegadorMensajeSoloLectura() {
        return msEdicionNavegadorMensajeSoloLectura;
    }

    /**
     * @see JGUIxConfigGlobal.getEdicionNavegadorMensajeSoloLectura
     * @param msMensajeSoloLectura the msMensajeSoloLectura to set
     */
    public void setEdicionNavegadorMensajeSoloLectura(String psMensajeSoloLectura) {
        msEdicionNavegadorMensajeSoloLectura = psMensajeSoloLectura;
    }

    /**
     * Si el JPanelEdicionNavegador sale y esta en edicion que tipo de pregunta hace
     * @see JPanelEdicionNavegador
     *   JPanelEdicionNavegador.mclSalidaCancelar
     *   JPanelEdicionNavegador.mclSalidaGuardar
     *   JPanelEdicionNavegador.mclSalidaNada
     * @return the mlEdicionNavegadorTipoSalida
     */
    public int getEdicionNavegadorTipoSalida() {
        return mlEdicionNavegadorTipoSalida;
    }

    /**
     * @param mlEdicionNavegadorTipoSalida the mlEdicionNavegadorTipoSalida to set
     */
    public void setEdicionNavegadorTipoSalida(int plEdicionNavegadorTipoSalida) {
        mlEdicionNavegadorTipoSalida = plEdicionNavegadorTipoSalida;
    }
    
    public void setPlugInFactoria(IPlugInFactoria poPluginFactoria){
        JGUIxConfigGlobalModelo.getInstancia().setPlugInFactoria(poPluginFactoria);
    }
    public IPlugInFactoria getPlugInFactoria(){
        return JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria();
    }

    /**
     * @return the mbLabelHTMLDefecto
     */
    public boolean isLabelHTMLDefecto() {
        return mbLabelHTMLDefecto;
    }

    /**
     * @param mbLabelHTMLDefecto the mbLabelHTMLDefecto to set
     */
    public void setLabelHTMLDefecto(boolean mbLabelHTMLDefecto) {
        this.mbLabelHTMLDefecto = mbLabelHTMLDefecto;
    }

    /**
     * @return the moToolTipTextLabels
     */
    public JDevolverTextos getToolTipTextLabels() {
        return moToolTipTextLabels;
    }

    /**
     * @param moToolTipTextLabels the moToolTipTextLabels to set
     */
    public void setToolTipTextLabels(JDevolverTextos moToolTipTextLabels) {
        this.moToolTipTextLabels = moToolTipTextLabels;
    }

    /**
     * @return the moAyudaURLLabels
     */
    public JDevolverTextos getAyudaURLLabels() {
        return moAyudaURLLabels;
    }

    /**
     * @param moAyudaURLLabels the moAyudaURLLabels to set
     */
    public void setAyudaURLLabels(JDevolverTextos moAyudaURLLabels) {
        this.moAyudaURLLabels = moAyudaURLLabels;
    }

    /**
     * @return the moCargarIcono
     */
    public ICargarIcono getCargarIcono() {
        return JGUIxConfigGlobalModelo.getInstancia().getCargarIcono();
    }

    /**
     * @param moCargarIcono the moCargarIcono to set
     */
    public void setCargarIcono(ICargarIcono moCargarIcono) {
        JGUIxConfigGlobalModelo.getInstancia().setCargarIcono(moCargarIcono);
    }
    


}
