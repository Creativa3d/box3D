/*
 * JTCargaDatos.java
 *
 * Created on 11 de noviembre de 2003, 9:22
 */

package utilesGUI.CargaDatos;

import ListDatos.*;
import utiles.*;
import utilesGUI.JBarraProceso;
/**
 * clase para cargar varias tablas de forma asincrona, con barra de proceso opcional
 */
public class JTCargaDatos implements ListDatos.IBusquedaListener {
    //barra de proceso para ver porcentaje visual
    private JBarraProceso moBarra = null;
    //array de carga de datos
    private final JListaElementos moTablas = new JListaElementos();
    //objeto que recibira los datos
    private final ListDatos.IBusquedaListener moPadre;
    
    //numero de tablas cargadas
    private int mlCargadas =0;
    
    /**
     * Constructor
     * @param poPadre listener
     */
    public JTCargaDatos(ListDatos.IBusquedaListener poPadre) {
        moPadre = poPadre;
    }
    /**
     * establecemos la barra de proceso
     * @param poBarra barra de proceso
     */
    public synchronized  void setBarraProceso (JBarraProceso poBarra){
        moBarra = poBarra;
    }
    /**
     * Añadimos una tabla para cargar los datos
     * @param poTabla tabla
     * @param poFiltro Filtro a aplicar
     * @param pbPasarCache si se pasa a cache
     * @param pbAsincrono si es asincrono
     * @param pbRefrescarCache si refresca cache
     */
    public void addTabla(JSTabla poTabla, ListDatos.IListDatosFiltro poFiltro, boolean pbPasarCache, boolean pbAsincrono, boolean pbRefrescarCache){
        //borramos el listener this por si ya se habia añadido
        poTabla.removeListener(this);
        //añadimos this como listener a la tabla para que cuando se carge lance el evento
        poTabla.addListener(this);
        //creamos la clase de carga
        JTCarga loCarga = new JTCarga(poTabla, poFiltro, pbPasarCache, pbAsincrono, pbRefrescarCache);
        //añadimos a la lista de cargas
        moTablas.add(loCarga);
    }
    /**
     * iniciamos la carga de todas las tablas
     * @throws Exception error
     */
    public void cargarTablas() throws Exception {
        for(int i =0; i< moTablas.size(); i++){
            JTCarga loCarga = (JTCarga)moTablas.get(i);
            loCarga.cargarDatos();
        }
    }
    /**
     * Indica si todas las tablas han sido cargadas
     * @return si esta todo cargado
     */
    public synchronized boolean getTodosCargados(){
        return (mlCargadas == moTablas.size());
    }
    
    
    /**evento que se dispara cada vez que termina una tabla de cargarse*/
    public synchronized void recuperacionDatosTerminada(BusquedaEvent e) {
        if (moBarra != null){
            //ponemos el porcentaje en la barra
            moBarra.setPorcentaje((mlCargadas * 100)/moTablas.size());
            //ponemos la tabla en la barra
            moBarra.setTexto(e.moDatos.msTabla);
        }
        //incrementamos el numero de tablas cargadas
        mlCargadas++;
        //indicamos al padre la tabla cargada 
        moPadre.recuperacionDatosTerminada(e);
    }
    
}
/**realiza la carga de una consulta concreta*/
class JTCarga {
    
    //datos y parametros a cargar
    JSTabla moTabla = null;
    ListDatos.IListDatosFiltro moFiltro = null;
    boolean mbPasarCache = false;
    boolean mbAsincrono = false;
    boolean mbRefrescarCache = false;

    //si se ha llamado al proceso de carga se pone a true
    boolean mbCargado = false;
    
    //contructor
    public JTCarga(JSTabla poTabla, ListDatos.IListDatosFiltro poFiltro, boolean pbPasarCache, boolean pbAsincrono, boolean pbRefrescarCache){
        moTabla = poTabla;
        moFiltro = poFiltro;
        mbPasarCache = pbPasarCache;
        mbAsincrono = pbAsincrono;
        mbRefrescarCache = pbRefrescarCache;
    }
    //llama a la carga de datos, este procedimiento solo se ejecuta una vez
    public void cargarDatos() throws Exception {
        //nos aseguramos que no se llamara al proceso de cargar datos mas de una vez
        if (!mbCargado) {
            mbCargado = true;
            //segun si tiene filtro o no recuperamos todos o filtrados
            if (moFiltro == null){
                moTabla.recuperarTodos(mbPasarCache, mbAsincrono, mbRefrescarCache);
            }else{
                moTabla.recuperarFiltrados(moFiltro, mbPasarCache, mbAsincrono, mbRefrescarCache);
            }
        }
            
    }
}

