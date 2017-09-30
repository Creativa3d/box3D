/*
 * JServidorDatosAbtrac.java
 *
 * Created on 30 de enero de 2004, 18:15
 * Sirve de base para Servidores de datos
 * Metodos para realizar la cache de datos
 */

package ListDatos;

import ListDatos.estructuraBD.JFieldDefs;
import java.util.Iterator;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;


/**Objeto servidor base, lleva la implementacion de la cache sincronizada*/
public abstract class JServidorDatosAbtrac implements IServerServidorDatos {

    //listener
    private IListaElementos moListeners = new JListaElementos();
    /**Lista de caches*/
    private IListaElementos moCache = new JListaElementos();
    /**Lista de pendientes*/
    private IListaElementos moPendientes = new JListaElementos();



    /**Lista de filtros*/
    private IListaElementos moFiltros = new JListaElementos();
    //establecemos el usuario
    protected String msUsuario=null;
    protected String msPassWord=null;
    protected String msPermisos=null;
    private JServerServidorDatosParam moParam=new JServerServidorDatosParam();

    public JServerServidorDatosParam getParametros() {
        return moParam;
    }



    ///////////////////////////////////
    ////Filtros para todas las select
    ///////////////////////////////////

    public void setFiltros(IListaElementos poFiltros){
        moFiltros=poFiltros;
    }
    public IListaElementos<JElementoFiltro>  getFiltros(){
        return moFiltros;
    }
    public void addFiltro(String psTabla, IListDatosFiltro poFiltro){
        moFiltros.add( new JElementoFiltro(psTabla, poFiltro));
    }
    public void addFiltro(int plTipo,String psNombre,String psTabla, IListDatosFiltro poFiltro){
        moFiltros.add( new JElementoFiltro(plTipo, psNombre, psTabla, poFiltro));
    }
    public IListDatosFiltro getFiltro(int i){
        return ((JElementoFiltro)moFiltros.get(i)).moFiltro;
    }
    public String getFiltroTabla(int i){
        return ((JElementoFiltro)moFiltros.get(i)).msTabla;
    }
    public void borrarFiltrosTodos(){
        moFiltros.clear();
        if(getParametros()!=null){
            getParametros().setSoloLectura(false);
        }
    }
    /**aplicamos los filtros generales*/
    protected JSelect aplicarFiltros(final JSelect poSelect) throws CloneNotSupportedException {
        return aplicarFiltros(moFiltros, poSelect);
    }
    /**aplicamos los filtros generales*/
    public static JSelect aplicarFiltros(IListaElementos<JElementoFiltro> poFiltros,final JSelect poSelect) throws CloneNotSupportedException {

        JSelect loResult = null;
        //vemos si es aplicable algun filtro
        for(int i = 0 ; poFiltros!=null && i < poFiltros.size(); i++){
            JElementoFiltro loElem = (JElementoFiltro) poFiltros.get(i);
            if(poSelect.getFrom().estaLaTablaoAliasSN(loElem.msTabla) && loElem.mlTipoEdicion == mclFiltroTipoSelect){
                //clonamos la select para no tocar la original
                if(loResult==null){
                    loResult = (JSelect) poSelect.clone();
                }
                //si hay algun filtro tenemos q asegurarops de q se hace and con
                //el nuevo filtro
                if(loResult.getWhere().mbAlgunaCond()){
                    JListDatosFiltroConj loFiltro = loResult.getWhere().Clone();
                    loResult.getWhere().clear();
                    loResult.getWhere().addCondicion(JListDatosFiltroConj.mclAND,(IListDatosFiltro) loFiltro.clone());
                    loResult.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loElem.moFiltro);
                }else{
                    loResult.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loElem.moFiltro);
                }
            }
        }
        if(loResult==null){
            loResult = poSelect;
        }
        return loResult;
    }
    protected IFilaDatos getFila(IListDatosFiltro poFiltro, String psTabla, JFieldDefs poCampos){
        //OJO: como se hace bien:
        //te creas una clase q derive de ISelect, llamas al metodo msSQL de poFiltro con esa clase nueva, y vas sacando los campos
        //necesarios, luego con los campos necesarios los cojes de poCampos y creas una IFIlaDatos nueva con los campos en su sitio
        //y la devuelves
        //por ahora se hace si y punto, pq lo normal es q sean los mismos campos
        return poCampos.moFilaDatos();
    }
    protected void comprobarRestriciones(int plTipo, String psSelect, String psTabla, JFieldDefs poCampos) throws Exception {
        for(int i = 0 ; i < moFiltros.size(); i++){
            JElementoFiltro loElem = (JElementoFiltro) moFiltros.get(i);
            if(psTabla.equalsIgnoreCase(loElem.msTabla)){
                if (loElem.moFiltro.mbCumpleFiltro(poCampos.moFilaDatos()) && loElem.mlTipoEdicion == plTipo) {
                    String lsTipo="";
                    switch(plTipo){
                        case mclFiltroTipoBorrar:
                            lsTipo="Borrar";
                            break;
                        case mclFiltroTipoEdicion:
                            lsTipo="Editar";
                            break;
                        case mclFiltroTipoNuevo:
                            lsTipo="Nuevo";
                            break;
                    }
                    throw new Exception("No se ha podido " + lsTipo + " por restricción: " + loElem.msNombre);
                }
            }
        }
    }


    ///////////////////////////////////777
    ////Gestion cache
    /////////////////////////////////////77

    public synchronized void clearCache(){
        moCache.clear();
    }
    public synchronized void addCache(JListDatos v) throws CloneNotSupportedException {
        moCache.add(v.clone());
    }

    public void actualizarDatosCacheConj(final IListaElementos poResult, final String psSelect) {
        if(poResult!=null){
            for(int i = 0; i<poResult.size();i++){
                try {
                    actualizarDatosCache(
                      ((JListDatos)poResult.get(i)).msTabla,
                      psSelect,
                      (JListDatos)poResult.get(i)
                      );
                } catch (CloneNotSupportedException ex) {
                    JDepuracion.anadirTexto(JDepuracion.mclCRITICO, this.getClass().getName(),ex);
                }
            }
        }
    }
    /**
     * actualiza la cache de una tabla
     * @param psTabla tabla base 
     * @param psSelect select original
     * @param poActualizar datos a actuializar
     */
    protected synchronized void actualizarDatosCache(final String psTabla, final String psSelect, final JListDatos poActualizar) throws CloneNotSupportedException{
        Iterator loEnum = moCache.iterator();
        JListDatos loDatos;
        while(loEnum.hasNext()){
         loDatos = (JListDatos)loEnum.next();
         if(loDatos.msTabla.compareTo(psTabla)==0){
           if (loDatos.msSelect.compareTo(psSelect)!=0){
             loDatos.actualizarDatos(poActualizar);
           }    
         }
        }
    }
    public synchronized JListDatos borrarEnCache(final String psSelect){
        JListDatos loList;
        JListDatos loListResult = null;
        Iterator loEnum  = moCache.iterator();
        while(loEnum.hasNext()&&(loListResult==null)){
            loList = (JListDatos)loEnum.next();
            if (loList.msSelect.compareTo(psSelect)==0){
                loEnum.remove();
                loListResult = loList; 
            }
        }
        return loListResult;
    }
    public synchronized JListDatos getEnCache(final String psSelect){
        JListDatos loList = null;
        JListDatos loListResult = null;
        Iterator loEnum  = moCache.iterator();
        while(loEnum.hasNext()&&(loListResult==null)){
          loList = (JListDatos)loEnum.next();
          if (loList.msSelect.compareTo(psSelect)==0){
            loListResult = loList; 
          }
        }
        return loListResult;
  
    }
    public synchronized JListDatos getEnCache(final JListDatos poDatos){
        JListDatos loList = null;
        JListDatos loListResult = null;
        Iterator loEnum  = moCache.iterator();
        while(loEnum.hasNext()&&(loListResult==null)){
          loList = (JListDatos)loEnum.next();
          if (loList.getJList() == poDatos.getJList()){
            loListResult = loList; 
          }
        }
        return loListResult;
    }
    /**
     * borra la select pendiente de la lista de pendientes
     * @param psSelect Select del listDatos
     */
    protected synchronized void borrarPediente(final String psSelect){
      for(int i = 0; i<moPendientes.size();i++){
        if (((String)moPendientes.get(i)).compareTo(psSelect)==0){
            moPendientes.remove(i);
            break;
        }
      }
    }
    /**
     * devuelve si el objeto esta pediente en funcion de la select pendiente
     * @param psSelect select de JListDatos
     * @return si esta pendiente
     */
    protected synchronized boolean mbPendiente(final String psSelect){
        Iterator loEnum  = moPendientes.iterator();
        String msPendiente;
        boolean lbPendiente = false;
        while(loEnum.hasNext()&&(!lbPendiente)){
          msPendiente = (String)loEnum.next();
          if (msPendiente.compareTo(psSelect)==0){
            lbPendiente = true;
          }
        }
        return lbPendiente;
    }
  
    ////////////////////////////////////7
    ///gestion de listeners
    ////////////////////////////////////
 
    /**
    * Borra todos los listeners
    */
    public void removeAllListener(){
        moListeners.clear();
    }
    /**
    * Anade un listener
    * @param poListener el listener
    */
    public void addListener(final IBusquedaListener poListener){
        moListeners.add(poListener);
    }
    /**
    * Borra un listener
    * @param poListener el listener
    */
    public void removeListener(final IBusquedaListener poListener){
        moListeners.remove(poListener);
    }
    /**
     * llamamos a listener propia JListDatos pasado por parametro
     * @param v JListDatos recien recuperada
     * @param pbError si ha habido algun error
     * @param poError El error
     */
    protected void llamarListener(final JListDatos v,final  boolean pbError, final Exception poError){
        BusquedaEvent e = new BusquedaEvent(this, v);
        e.mbError=pbError;
        e.moError=poError;
        v.recuperacionDatosTerminada(e);
        //llamamos a los listener que ha terminado la select correspondiente
        IBusquedaListener loListener;
        Iterator loEnum = moListeners.iterator();
        while(loEnum.hasNext()) {
          loListener = (IBusquedaListener) loEnum.next();
          loListener.recuperacionDatosTerminada(e);
        }
    }
 
    ////////////////////////////////////////////////////////////////
    ///Semi implemetacion de metodos de ayuda para las clases hijas
    ///////////////////////////////////////////////////////////////


    public void recuperarDatos(final JListDatos v, final JSelect poSelect1,final String psTabla,final boolean pbPasarACache,final boolean pbRefrescarACache, final int plOpciones)  throws Exception  {
        JSelect loSelect = aplicarFiltros(poSelect1);//aplicamos los filtros generales
        String lsSelect = loSelect.toString();
        boolean lbEnCache = false;
        boolean lbError = false;
        Exception loError = null;
        
        try {
            synchronized(this){
                //mientras este pendiente esperar
                while(mbPendiente(lsSelect)) {
                    try{
                        wait(1000);
                    }catch(Exception e){
                        
                    }
                }
                //si se va a almacenar en cache lo pasamos a pendientes
                if(pbPasarACache) {
                    moPendientes.add(lsSelect);
                }
                //si se refresca la cache se borra
                if(pbRefrescarACache){
                    borrarEnCache(lsSelect);
                }
            }
            //buscamos la select en cache
            JListDatos loList=getEnCache(lsSelect);
            //si esta el ListDatos se replica
            if (loList!=null) {
                v.replicar(loList, true);
                lbEnCache = true;
            }

            //si no esta en cache recuperamos la infor. 
            if (!lbEnCache) {
              //anulamos eventos de insercion, borrado, ...
              v.eventosGestAnular();

              try{
                //recuperamos la informacion
                lbError = true;
                recuperarInformacion( v, loSelect, psTabla);
                //si llega hasta aqui es que no ha habido ningun error
                lbError = false;
              }finally{
                //reactivamos eventos de insercion, borrado, ...
                v.eventosGestActivar();
              }
            }
        } catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, this.getClass().getName(), e);
            loError = e;
            throw e;
        } finally {
            v.moveFirst();
            //anadimos a las tablas de cache si procede
            if (pbPasarACache){
              synchronized(this){
                  //si ya estaba en cache(se ha replicado) o ha habido algun error mi introducirlo en cache
                  if ((!lbEnCache) && (!lbError)){
                      addCache(v);
                  }
                  //borramos la select pendiente de la lista de pendientes
                  borrarPediente(lsSelect);
                  //notificamos 
                  notifyAll();
              }
            }
            //llamamos a los listener que se ha terminado la recuperacion
            llamarListener(v, lbError, loError);
        }
    }
    /**
     * Metodo a implementar por la clase hija para recuperar informacion con toda la gestion
     * de cache y de listener echa
     * @param v JListDatos
     * @param poSelect objeto select
     * @param psTabla nombre tabla
     * @throws Exception exeption
     */
    protected abstract void recuperarInformacion(JListDatos v, JSelect poSelect,String psTabla) throws Exception;

    public IResultado ejecutarSQL(final ISelectEjecutarSelect poEstruc) {
        return modificarEstructura(poEstruc);
    }
    public IResultado borrar(String psSelect, String psTabla, JFieldDefs poCampos) {
        try {
            comprobarRestriciones(mclFiltroTipoBorrar, psSelect, psTabla, poCampos);
            JActualizar loActu = new JActualizar(poCampos, psTabla, JListDatos.mclBorrar, msUsuario, msPassWord, msPermisos);
            return actualizar(psSelect, loActu);
        } catch (Exception ex) {
            return new JResultado(ex.getMessage(), false);
        }
    }
    public IResultado modificar(String psSelect, String psTabla, JFieldDefs poCampos) {
        try {
            comprobarRestriciones(mclFiltroTipoEdicion, psSelect, psTabla, poCampos);
            JActualizar loActu = new JActualizar(poCampos, psTabla, JListDatos.mclEditar, msUsuario, msPassWord, msPermisos);
            return actualizar(psSelect, loActu);
        } catch (Exception ex) {
            return new JResultado(ex.getMessage(), false);
        }
    }
    public IResultado anadir(String psSelect, String psTabla, JFieldDefs poCampos) {
        try {
            comprobarRestriciones(mclFiltroTipoNuevo, psSelect, psTabla, poCampos);
            JActualizar loActu = new JActualizar(poCampos, psTabla, JListDatos.mclNuevo, msUsuario, msPassWord, msPermisos);
            return actualizar(psSelect, loActu);
        } catch (Exception ex) {
            return new JResultado(ex.getMessage(), false);
        }
    }
    
    public void close() throws Exception{
        Iterator loEnum  = moCache.iterator();
        while(loEnum.hasNext()){
            JListDatos loList = (JListDatos)loEnum.next();
            loList.close();
        }
        moCache.clear();
    }

    
}
