
package ListDatos;


import ListDatos.*;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import java.io.Serializable;
import java.util.Iterator;
import utiles.*;
/**
 *Objeto base para crear una tabla intermedia
 */
public abstract class JSTabla  implements IBusquedaListener, Serializable{
    private static final long serialVersionUID = 33333314L;
    protected String msCodigoRelacionado = "";
    
    
    /**innecesario*/
//  public JSTabla(){
//
//  }
    /**
     * Listeners, Esto no es seriealizable
     */
    protected transient JListaElementos moListeners = new JListaElementos();
    /**
     * ListDatos
     */
    public JListDatos moList= null;
    
    /**
     * establecemos el servidor de datos manualmente
     * @param poServidor servidor
     */
    public void setServidor(final IServerServidorDatos poServidor){
        moList.moServidor = poServidor;
    }
    
    /**
     * borra la referencia a los Listener
     */
    public void close() {
        moList.removeListener(this);
        removeAllListener();
    }
    
/////////////////////////////////777
//Gestor de listeners
///////////////////////////////////7
    /**
     * Anade un listener busqueda
     * @param poListener el listener
     */
    public void addListener(final IBusquedaListener poListener) {
        moListeners.add(poListener);
    }
    
    /**
     * Borra un listener
     * @param poListener el listener
     */
    public void removeListener(final IBusquedaListener poListener) {
        moListeners.remove(poListener);
    }
    
    /**
     * Borra todos los listeners
     */
    public void removeAllListener() {
        moListeners.clear();
    }
    ////////////////////////////////////
    //Implementacion del interfaz IBusquedaListener
    ///////////////////////////////////
    
    public void recuperacionDatosTerminada(final BusquedaEvent e) {
        Iterator loEnum = moListeners.iterator();
        IBusquedaListener loListener;
        for (; loEnum.hasNext(); ) {
            loListener = (IBusquedaListener) loEnum.next();
            loListener.recuperacionDatosTerminada(e);
        }
    }
    /////////////////////////////////////
    //Utilidades de base de datos
    /////////////////////////////////////
    /**
     * recupera toda la tabla, no asincrono y sin cache
     * @throws Exception exception en caso de error
     */
    public void recuperarTodosNormalSinCache() throws Exception{
        recuperarTodos(false, false, true);
    }
    /**
     * recupera toda la tabla, no asincrono
     * @param pbPasarCache Se almacena en la cache
     * @throws Exception exception
     */
    public void recuperarTodosNormal(final boolean pbPasarCache) throws Exception{
        recuperarTodos(pbPasarCache, false, !pbPasarCache);
    }
    /**
     * recupera toda la tabla, no asincrono y a cache
     * @throws Exception exception
     */
    public void recuperarTodosNormalCache() throws Exception{
        recuperarTodos(true, false, false);
    }
    /**
     * recupera toda la tabla, no asincrono y a cache
     * @throws Exception exception
     */
    public void recuperarTodosAsinCache() throws Exception{
        recuperarTodos(true, true, false);
    }
    /**
     * recupera toda la tabla
     * @param pbPasarCache Se almacena en la cache
     * @param pbAsincrono se carga en segundo plano
     * @throws Exception exception
     */
    public void recuperarTodos(final boolean pbPasarCache, final boolean pbAsincrono) throws Exception{
        recuperarTodos(pbPasarCache, pbAsincrono, !pbPasarCache);
    }
    /**
     * recupera toda la tabla
     * @param pbRefrecarCache si refresca la cache
     * @param pbPasarCache Se almacena en la cache
     * @param pbAsincrono se carga en segundo plano
     * @throws Exception exception
     */
    public void recuperarTodos(final boolean pbPasarCache, final boolean pbAsincrono, final boolean pbRefrecarCache) throws Exception{
//        //apano para que todo pase por el refrescar de IConsulta, ya q en muchisimas consultas se usa el refrescar 
//        //para recuperar los datos con acciones personalizadas, es decir, que las clases que heredan es el que 
//        //realmente ejecuta la select y acciones personalizadas
//        //RESUMEN: la idea es q da igual q ejecutes recuperarTodos o refrescar, que se ejecute el mismo codigo
//        //RESUMEN2: pues no se puede ya q se pueden forman llamadas recursevas infinitas, ya exiten muchos refrescar q llaman a recuperarTodos
//        if(!pbAsincrono){
//            refrescar(pbPasarCache, pbRefrecarCache);
//        }else{
            JSelect loSelect = getSelect();
            moList.recuperarDatos(loSelect, pbPasarCache, (pbAsincrono ? JListDatos.mclSelectSegundoPlano : JListDatos.mclSelectNormal), pbRefrecarCache);
//        }
    }
    /**
     * Refresca el JListDatos, las clases hijas pueden redefinir este metodo para acciones personalizadas
     * @param pbPasarACache si pasa a cache
     * @param pbLimpiarCache si limpia la cache
     * @throws Exception error
     */
    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception{
        JSelect loSelect = getSelect();
        moList.recuperarDatos(loSelect, pbPasarACache, JListDatos.mclSelectNormal, pbLimpiarCache);
    }    
    
    /**
     * recupera la tabla segun el filtro
     * @param poFiltro el filtro
     * @throws Exception exception
     */
    public void recuperarFiltradosNormal(final IListDatosFiltro poFiltro) throws Exception{
        recuperarFiltrados(poFiltro, false, false, true);
    }
    /**
     * recupera la tabla segun el filtro
     * @param poFiltro el filtro
     * @param pbPasarCache Se almacena en la cache
     * @throws Exception exception
     */
    public void recuperarFiltradosNormal(final IListDatosFiltro poFiltro, final boolean pbPasarCache) throws Exception{
        recuperarFiltrados(poFiltro, pbPasarCache, false, !pbPasarCache);
    }
    /**
     * recupera la tabla segun el filtro
     * @param poFiltro el filtro
     * @param pbPasarCache Se almacena en la cache
     * @param pbAsincrono se carga en segundo plano
     * @throws Exception exception
     */
    public void recuperarFiltrados(final IListDatosFiltro poFiltro, final boolean pbPasarCache, final boolean pbAsincrono) throws Exception{
        recuperarFiltrados(poFiltro, pbPasarCache, pbAsincrono, !pbPasarCache);
    }
    /**
     * recupera la tabla segun el filtro
     * @param pbRefrescarCache si refresca la cache
     * @param poFiltro el filtro
     * @param pbPasarCache Se almacena en la cache
     * @param pbAsincrono se carga en segundo plano
     * @throws Exception exception
     */
    public void recuperarFiltrados(final IListDatosFiltro poFiltro, final boolean pbPasarCache, final boolean pbAsincrono, final boolean pbRefrescarCache) throws Exception{
        JSelect loSelect = getSelect();
        poFiltro.inicializar(moList.getTablaOAlias(), moList.getFields().malTipos(), moList.getFields().msNombres());
        loSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, poFiltro);
        moList.recuperarDatos(loSelect, pbPasarCache,(pbAsincrono ? JListDatos.mclSelectSegundoPlano : JListDatos.mclSelectNormal), pbRefrescarCache);
        loSelect =null;
    }
    /**
     *recupera un objeto select segun la informacion actual
     *@return objeto select
     */
    public JSelect getSelect(){
        return moList.getSelect();
    }
    /**
     * lo implementa el cliente, sirve para validar los valores de los campos
     * @throws Exception Exception si algun campo no valido
     */
    public void validarCampos() throws Exception {
    }
    /**
     * lo implementa el cliente, sirve para poner valores por defecto
     * @throws Exception Exception si algun campo no valido
     */
    public void valoresDefecto() throws Exception {
    }
    
    public JListDatos getList(){
        return moList;
    }

    protected void setClave(String psClave){
        msCodigoRelacionado=psClave;
    }
    public String getClave(){
        return getClave(moList.getFields().moFilaDatos(), moList.getModoTabla());
    }
    
    public String getClave(IFilaDatos poFila, int plModo){
        StringBuilder lsClave = new StringBuilder();
        //se pone asi pq cuando editas un form. el modo por defecto es JListDatos.mclNada
        if(plModo==JListDatos.mclNuevo){
            lsClave.append(JListDatos.mclNuevo);
        }else{
            for(int i = 0; i < moList.getFields().size(); i++){
                if(moList.getFields(i).getPrincipalSN()){
                    lsClave.append(poFila.msCampo(i));
                    lsClave.append(JFilaDatosDefecto.mcsSeparacion1);
                }
            }
        }
        return lsClave.toString();
    }
    
    protected boolean isMismaClave(){
        return msCodigoRelacionado != null && getClave().equals(msCodigoRelacionado);
    }

    public boolean moveFirst(){
        return moList.moveFirst();
    }
    public boolean moveLast(){
        return moList.moveLast();
    }
    public boolean moveNext(){
        return moList.moveNext();
    }
    public boolean movePrevious(){
        return moList.movePrevious();
    }
    public JFieldDefs getFields(){
        return moList.getFields();
    }
    public JFieldDef getField(final String psNombre){
        return moList.getFields().get(psNombre);
    }
    public JFieldDef getField(final int plPosi){
        return moList.getFields().get(plPosi);
    }

    public void addNew() throws Exception {
        moList.addNew();
        msCodigoRelacionado="";
        valoresDefecto();
    }
    public IResultado update(boolean pbActualizarServidor){
        return moList.update(pbActualizarServidor);
    }

    /**Cuando solo se quieren actualizar ciertos campos de la tabla, no usar con tablas en cache
        * @param pbActualizarServidor si se actualiza en el servidor
        * @param poCampos lista de campos a actualizar, ya deben de tener el valor a actualizar y deben ser referencias al moList.getFields() de esta misma tabla
        * @return resultado
        */
    public IResultado update(boolean pbActualizarServidor, JFieldDef[] poCampos){
        JFieldDefs loCampos = new JFieldDefs();
        loCampos.setTabla(moList.getFields().getTabla());
        for(JFieldDef loCampo: moList.getFields().getListaCampos()){
            if(loCampo.getPrincipalSN()){
                loCampos.addField(loCampo);
            }
        }
        for (JFieldDef loCampo : poCampos) {
            loCampos.addField(loCampo);
        }
        return moList.update(pbActualizarServidor, true, loCampos);
    } 
    
    public void validarCamposNoNulos() throws Exception{
        validarCamposNoNulos(true);
    }
    public void validarCamposNoNulos(boolean pbConClavesPrimatiras) throws Exception{
        for(JFieldDef loCampo : moList.getFields().getListaCampos()){
            if(!loCampo.getNullable() && loCampo.isVacio() && (pbConClavesPrimatiras || !loCampo.getPrincipalSN())){
                throw new Exception("El campo "+loCampo.getCaption() + " es obligatorio");
            }
        }
    }

    public IResultado guardar() throws Exception {
        return update(true);
    }
}