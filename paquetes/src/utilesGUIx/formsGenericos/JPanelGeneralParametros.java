/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;


import java.text.Format;
import utilesGUIx.ITableCZColores;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;

public class JPanelGeneralParametros {
    
    public static final String mcsTipoFiltroRapidoPorCampo="0";
    public static final String mcsTipoFiltroRapidoPorTodos="1";
    
    public String msTipoFiltroRapido = JGUIxConfigGlobalModelo.getInstancia().getTipoFiltroRapidoDefecto();
    public boolean mbSegundoPlano = false;
    
    private JPanelGeneralBotones moBotonesGenerales = new JPanelGeneralBotones();
    private int[] malLongitudCampos;
    private int[] malOrdenCampos;
    private boolean[] mabColumnasVisiblesConfig;
    private Format[] moFormatosCampos;

    private String msNombre;
    private String msTitulo;
    
    private ITableCZColores moColoresTabla = null;
    
    private IMostrarPantalla moMostrarPantalla;

    private boolean mbActivado = true;

    private boolean mbPlugInPasados=false;
    private boolean mbForzarLong=false;
    private boolean mbVisibleConfigYFiltroRap=true;
    private boolean mbRefrescarListDatos=true;

    private int mlADVSiNumRegistrosSupera=-1;
    private CallBack<IPanelControlador> moCallBack;



    /**
     * Nombre del formulario
     */
    public String getNombre(){
        if(msNombre==null){
            return msTitulo;
        }
        return msNombre;
    }
    /**
     * Nombre del formulario
     */
    public void setNombre(final String psNombre){
        msNombre = psNombre;
    }
    /**
     * @return Titulo
     */
    public String getTitulo() {
        if(msTitulo == null){
            return msNombre;
        }
        return msTitulo;
    }

    /**
     * @param Titulo
     */
    public void setTitulo(String msTitulo) {
        this.msTitulo = msTitulo;
    }
    /**
     * Devuelve un objeto para poder colorear las celdas
     * @return Objeto q cumple el interfaz ITableCZColores
     */
    public ITableCZColores getColoresTabla() {
        if(moColoresTabla == null){
            moColoresTabla = new JPanelGenericoColores(null);
        }
        return moColoresTabla;
    }
    /**
     * Establece objeto para poder colorear las celdas (opcional)
     */
    public void setColoresTabla(ITableCZColores poColores) {
        moColoresTabla = poColores;
    }
    /**
     * Devuelve la long. de los campos
     * @return lista de long.
     */
    public int[] getLongitudCampos(){
        return malLongitudCampos;
    }
    /**
     * Establece la long. de los campos (opcional)
     */
    public void setLongitudCampos(int[] palLong){
        malLongitudCampos = palLong;
    }
    /**
     * Devuelve el orden de los campos
     * @return lista de orden
     */
    public int[] getOrdenCampos(){
        return malOrdenCampos;
    }
    /**
     * Establece el Orden de los campos (opcional), es un array de enteros, uno por columna del JListDatos
     * ejem: 
     * array[1] = 0
     * array[0] = 1
     * en un JListDatos de 2 campos invertiria el orden de las columnas, 
     * esto solo sirve para la 1º vez luego se usa el orden que hay guardado en el
     * fichero de configuracion de Long
     */
    public void setOrdenCampos(int[] palOrden){
        malOrdenCampos = palOrden;
    }

    /**
     *Indican propiedades de ciertos botones ya existentes
     * @return Objeto con todas las propiedades de los botones
     */
    public JPanelGeneralBotones getBotonesGenerales(){
        return moBotonesGenerales;
    }

    public IMostrarPantalla getMostrarPantalla() {
        return moMostrarPantalla;
    }

    /**
     * Establece el mostrar pantalla (opcional)
     * @param moMostrarPantalla Objeto IMostrarPantalla
     */
    public void setMostrarPantalla(IMostrarPantalla moMostrarPantalla) {
        this.moMostrarPantalla = moMostrarPantalla;
    }

    /**
     * @return las columnas visibles  en la configuracion de columnas
     */
    public boolean[] getColumnasVisiblesConfig() {
        return mabColumnasVisiblesConfig;
    }

    /**
     * Establece las columnas visibles en la configuracion de columnas (opcional)
     * @param pabColumnasVisibles columnas visibles
     */
    public void setColumnasVisiblesConfig(boolean[] pabColumnasVisibles) {
        this.mabColumnasVisiblesConfig = pabColumnasVisibles;
    }

    /**
     * @return si esta activado
     */
    public boolean isActivado() {
        return mbActivado;
    }

    /**
     * @param mbActivado establece si esta activado o no
     */
    public void setActivado(boolean pbActivado) {
        mbActivado = pbActivado;
    }

    /**
     * @return the mbPlugInPasados
     */
    public boolean isPlugInPasados() {
        return mbPlugInPasados;
    }

    /**
     * @param mbPlugInPasados the mbPlugInPasados to set
     */
    public void setPlugInPasados(boolean mbPlugInPasados) {
        this.mbPlugInPasados = mbPlugInPasados;
    }

    /**
     * @return the mbForzarLong
     */
    public boolean isForzarLong() {
        return mbForzarLong;
    }

    /**
     * @param mbForzarLong the mbForzarLong to set
     */
    public void setForzarLong(boolean mbForzarLong) {
        this.mbForzarLong = mbForzarLong;
    }

    public boolean isVisibleConfigYFiltroRap() {
        return mbVisibleConfigYFiltroRap;
    }
    public void setVisibleConfigYFiltroRap(boolean pbValor) {
        mbVisibleConfigYFiltroRap=pbValor;
    }

    /**
     * Si es true (defecto) cuando se abre el JPanelGenerico se refresca la consulta
     * si no se usa el JListDatos directo de getConsulta()
     * @return the mbRefrescarListDatos
     */
    public boolean isRefrescarListDatos() {
        return mbRefrescarListDatos;
    }

    /**
     * Si es true (defecto) cuando se abre el JPanelGenerico se refresca la consulta
     * si no se usa el JListDatos directo de getConsulta()
     * @param mbRefrescarListDatos the mbRefrescarListDatos to set
     */
    public void setRefrescarListDatos(boolean mbRefrescarListDatos) {
        this.mbRefrescarListDatos = mbRefrescarListDatos;
    }

    /**
     * @return the mlADVSiNumRegistrosSupera
     */
    public int getADVSiNumRegistrosSupera() {
        return mlADVSiNumRegistrosSupera;
    }

    /**
     * @param mlADVSiNumRegistrosSupera the mlADVSiNumRegistrosSupera to set
     */
    public void setADVSiNumRegistrosSupera(int mlADVSiNumRegistrosSupera) {
        this.mlADVSiNumRegistrosSupera = mlADVSiNumRegistrosSupera;
    }

    /**
     * @return the msFormatosCampos
     */
    public Format[] getFormatosCampos() {
        return moFormatosCampos;
    }

    /**
     * @param msFormatosCampos the msFormatosCampos to set
     */
    public void setFormatosCampos(Format[] msFormatosCampos) {
        this.moFormatosCampos = msFormatosCampos;
    }
    public void setCallBack(CallBack<IPanelControlador> poCall) {
        moCallBack = poCall;
    }
    public CallBack<IPanelControlador> getCallBack(){
        return moCallBack;
    }

    
}
