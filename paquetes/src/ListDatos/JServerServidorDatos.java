/*
 * ServidorDatos.java
 *
 * Created on 8 de julio de 2002, 21:27
 */
/**
 *
 * Es el que se enlaza con la pagina web y recoge datos y guarda datos
 */
 
package ListDatos;

import ListDatos.estructuraBD.*;

import utiles.*;
import utilesBD.servletAcciones.AGuardarDatos;
import utilesBD.servletAcciones.ASelectDatos;

/** 
 *   Servidor de datos para 
 * 1 internet a traves de serializacion
 * 2 internet a traves de serializacion comprimida
 * 3 base de datos directa
 * 4 internet texto a traves de parametros en la url
 */
public class JServerServidorDatos implements IServerServidorDatos {
    private static final long serialVersionUID =33333322L;
    /** Contante tipo de conexion Internet*/
    public static final int mclTipoInternet = 0;
    /** Contante tipo de conexion fichero*/
    public static final int mclTipoFichero = 1;
    /** Contante tipo de conexion bd*/
    public static final int mclTipoBD = 2;
    /** Contante tipo de conexion internet de recibir datos  comprimido*/
    public static final int mclTipoInternetComprimido = 3;
    /** Contante tipo de conexion internet de recibir/enviar datos comprimido */
    public static final int mclTipoInternetComprimido_I_O = 5;
    /** Contante tipo de conexion internet texto, por parametros en la conexion */
    public static final int mclTipoInternetTexto = 4;

    private JServerServidorDatosBD moServerBD = new JServerServidorDatosBD();
    private JServerServidorDatosInternet moServerInternet = new JServerServidorDatosInternet();
    private IServerServidorDatos moServerDefecto = null;
    
    /**tipo de conexion*/
    private int mlTipo = mclTipoInternet;
    
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatos() {
        super();
    }
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatos(JServerServidorDatosConexion poServer) throws Throwable {
        super();
        if(poServer.getTipoConexion() == mclTipoBD){
            mlTipo = mclTipoBD;
            moServerBD = new JServerServidorDatosBD(poServer.getClase(), poServer.getURL(), poServer.getUSUARIO(), poServer.getPASSWORD(), String.valueOf(poServer.getTipoBD()));
            moServerDefecto = moServerBD;            
        } else{
            moServerInternet = new JServerServidorDatosInternet(poServer.getURL(), ASelectDatos.mcsselectdatos+".ctrl", AGuardarDatos.mcsguardardatos + ".ctrl");
            mlTipo = mclTipoInternet;
            moServerDefecto = moServerInternet;
            moServerInternet.setLogin(new JServerServidorDatosInternetLogin(poServer.getUSUARIO(), poServer.getPASSWORD()));
            if(!JCadenas.isVacio(poServer.getUSUARIO())){
                if(!moServerInternet.autentificar()){
                    throw new Exception("Error de autentificación");
                }
            }
        }
    }
    /**
     * Crea una instancia de servidor de datos con una URL fija
     * @param psURLBase url base
     * @param psNombreSelect nombre servicio select
     * @param psNombreGuardar nombre servicio guardar
     */
    public JServerServidorDatos(final String psURLBase, final String psNombreSelect, final String psNombreGuardar) {
        super();
        moServerInternet = new JServerServidorDatosInternet(psURLBase, psNombreSelect, psNombreGuardar);
        mlTipo = mclTipoInternet;
        moServerDefecto = moServerInternet;
    }
    /**
     * Constructor
     * @param psURLBase url base
     * @param psNombreSelect nombre servicio select
     * @param psNombreGuardar nombre servicio guardar
     * @param plTipo Tipo recogida de datos
     */
    public JServerServidorDatos(final int plTipo, final String psURLBase, final String psNombreSelect, final String psNombreGuardar) {
        super();
        moServerInternet = new JServerServidorDatosInternet(plTipo, psURLBase, psNombreSelect, psNombreGuardar);
        mlTipo = plTipo;
        moServerDefecto = moServerInternet;
    }
    
    /**
     * Constructor
     * @param psDriver driver 
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     */
    public JServerServidorDatos(final String psDriver, final String psURL, final String psUsu, final String psPassword) throws Throwable{
        super();
        mlTipo = mclTipoBD;
        moServerBD = new JServerServidorDatosBD(psDriver, psURL, psUsu, psPassword);
        moServerDefecto = moServerBD;
    }
    /**
     * Constructor
     * @param psDriver driver 
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     * @param psTipoBDStandar BD Definidas en JSelectMotor
     */
    public JServerServidorDatos(final String psDriver, final String psURL, final String psUsu, final String psPassword, final String psTipoBDStandar) throws Throwable{
        this(psDriver, psURL, psUsu, psPassword);
        moServerBD.setTipoBDStandar(psTipoBDStandar);
        moServerDefecto = moServerBD;
    }
    
    public JServerServidorDatosInternet getServerInternet(){
        return moServerInternet;
    }
    public JServerServidorDatosBD getServerBD(){
        return moServerBD;
    }

    public void setTipo(final int plTipo){
        moServerInternet.setTipo(plTipo);
        mlTipo = plTipo;
        if(mlTipo == mclTipoBD){
            moServerDefecto = moServerBD;
        } else{
            moServerDefecto = moServerInternet;
        }
    }
    /**tipo de conexion, mclTipoInternet, mclTipoBD, mclTipoInternetComprimido,...*/
    public int getTipo(){
        return mlTipo;
    }
    public void setConec(final java.sql.Connection poConec){
        moServerBD.setConec(poConec);
    }
    public java.sql.Connection getConec(){
        return moServerBD.getConec();
    }
//    public void setLogin(JServerServidorDatosInternetLogin poLogin){
//        setLogin((IServerServidorDatosInternetLogin)poLogin);
//    }
    public void setLogin(IServerServidorDatosInternetLogin poLogin){
        moServerInternet.setLogin(poLogin);
    }
    public void setSelect(final ISelectMotor poSelect){
        moServerBD.setSelect(poSelect);
        moServerInternet.setSelect(poSelect);
    }
    public void setConstrucEstruc(final IConstructorEstructuraBD poConstrucEstruc){
        moServerBD.setConstrucEstruc(poConstrucEstruc);
        moServerInternet.setConstrucEstruc(poConstrucEstruc);
    }
    public void setTableDefs(final JTableDefs poTableDefs){
        moServerBD.setTableDefs(poTableDefs);
        moServerInternet.setTableDefs(poTableDefs);
    }
    /**
     * establece el nombre del servicio para select
     * @param psSelect nombre del servlet
     */
    public void setNombreSelect(final String psSelect){
        moServerInternet.setNombreSelect(psSelect);
    }
    /**
     * establece la url base
     * @param psURLBase url
     */
    public void setURLBase1(final String psURLBase){
        moServerInternet.setURLBase1(psURLBase);
    }
    /**
     * establece el nombre del servicio para guardar
     * @param psGuardar nombre del servlet
     */
    public void setNombreGuardar(final String psGuardar){
        moServerInternet.setNombreGuardar(psGuardar);
    }
    /**
     * devuelve la url base
     * @return url
     */
    public String getURLBase1(){
        return moServerInternet.getURLBase1();
    }
    /**
     * devuelve el nombre del servicio guardar
     * @return nombre
     */
    public String getNombreGuardar(){
      return moServerInternet.getNombreGuardar();
    }
    /**
     * establece el id de session
     * @param psIdSession id de session
     */  
    public void setIDSession(final String psIdSession){
        moServerInternet.setIDSession(psIdSession);
    }
    /**Establecemos que la entrada tb esta comprimida al enviarla al servidor*/
    public void setEntradaComprimida() throws Exception {
        moServerInternet.setEntradaComprimida(true);
    }
    /**Establecemos que la entrada tb esta comprimida al enviarla al servidor*/
    public void setEntradaComprimida(boolean pbComprimida) throws Exception {
        moServerInternet.setEntradaComprimida(pbComprimida);
    }
    /**
     * devuelve el nombre del servicio select
     * @return nombre del servlet
     */
    public String getNombreSelect(){
      return  moServerInternet.getNombreSelect();
    }
    /**
     * establece el usuario no se usa
     * @param psUsuario usuario
     * @param psPassword password
     * @param psPermisos permisos
     */
    public void setUsuario(final String psUsuario,final String psPassword,final String psPermisos){
        moServerBD.setUsuario(psUsuario,psPassword,psPermisos);
        moServerInternet.setUsuario(psUsuario,psPassword,psPermisos);
    }
    /**
     * devuelve el usuario no se usa
     * @return usuario
     */
    public String getUsuario(){
        String lsUsus=null;
        if(mlTipo==mclTipoBD){
            lsUsus = moServerBD.getUsuario();
        }else{
            lsUsus = moServerInternet.getUsuario();
        }
        return lsUsus;
        
    }
    ////////////////////////////////////////77
    ////Actualizaciones y sincronizacion fisica
    //////////////////////////////////////////
    public JTableDefs getTableDefs() throws Exception {
        JTableDefs loTableDefs = null;
        loTableDefs = moServerDefecto.getTableDefs();
        return loTableDefs;
    }
    public IResultado modificarEstructura(final ISelectEjecutarSelect poEstruc) {
        IResultado loResult = null;
        loResult = moServerDefecto.modificarEstructura(poEstruc);
        return loResult;
        
    }

    public IResultado ejecutarSQL(ISelectEjecutarSelect poEstruc) {
        return modificarEstructura(poEstruc);
    }
    
    public IResultado borrar(final String psSelect, final String psTabla, final JFieldDefs poCampos) {
        IResultado loResult = null;
        loResult = moServerDefecto.borrar(psSelect, psTabla, poCampos);
        return loResult;
    }
    public IResultado modificar(final String psSelect, final String psTabla, final JFieldDefs poCampos) {
        IResultado loResult = null;
        loResult = moServerDefecto.modificar(psSelect, psTabla, poCampos);
        return loResult;
    }
    public IResultado anadir(final String psSelect, final String psTabla, final JFieldDefs poCampos) {
        IResultado loResult = null;
        loResult = moServerDefecto.anadir(psSelect, psTabla, poCampos);
        return loResult;
    }
    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        IResultado loResult = null;
        loResult = moServerDefecto.actualizar(psSelect, poActualizar);
        return loResult;
    }
//    public IResultado actualizarConj(final ISelectEjecutarUpdate poActuConj) {
//        IResultado loResult = null;
//        loResult = moServerDefecto.actualizarConj(poActuConj);
//        return loResult;
//    }
    public IResultado ejecutarServer(final IServerEjecutar poEjecutar){
        IResultado loResult = null;
        loResult = moServerDefecto.ejecutarServer(poEjecutar);
        return loResult;
    }

    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        if(mlTipo==mclTipoBD){
            moServerBD.recuperarInformacion(v, poSelect, psTabla);
        }else{
            moServerInternet.recuperarInformacion(v, poSelect, psTabla);
        }
    }
    /**
     *
     * cerrar objeto, si es tipo base de datos es muy importante hacerlo
     * @throws Exception el error en caso de error
     */
    public void close() throws Exception{
        moServerBD.close();
        moServerInternet.close();
    }

    public ISelectMotor getSelect() {
        ISelectMotor loREsult = null;
        if(mlTipo==mclTipoBD){
            loREsult = moServerBD.getSelect();
        }else{
            loREsult = moServerInternet.getSelect();
        }
        return loREsult;
    }

    public void recuperarDatos(final JListDatos v, final JSelect poSelect, final String psTabla, final boolean pbPasarACache, final boolean pbRefrescarACache, final int plOpciones) throws Exception {
        moServerDefecto.recuperarDatos(v, poSelect, psTabla, pbPasarACache, pbRefrescarACache, plOpciones);
    }

    public void clearCache() {
        moServerDefecto.clearCache();
    }

    public void actualizarDatosCacheConj(final IListaElementos poResult, final String psSelect) {
        moServerDefecto.actualizarDatosCacheConj(poResult, psSelect);
    }

    public JListDatos getEnCache(final String psSelect) {
        JListDatos loResult = null;
        loResult = moServerDefecto.getEnCache(psSelect);
        return loResult ;
    }

    public JListDatos getEnCache(final JListDatos poDatos) {
        JListDatos loResult = null;
        loResult = moServerDefecto.getEnCache(poDatos);
        return loResult ;
    }

    public JListDatos borrarEnCache(final String psSelect) {
        JListDatos loResult = null;
        loResult = moServerDefecto.borrarEnCache(psSelect) ;
        return loResult ;
    }

    public void addFiltro(String psTabla, IListDatosFiltro poFiltro) {
        moServerDefecto.addFiltro(psTabla, poFiltro);
    }
    public void addFiltro(int plTipo, String psNombre, String psTabla, IListDatosFiltro poFiltro) {
        moServerDefecto.addFiltro(plTipo, psNombre, psTabla, poFiltro);
    }

    public IListDatosFiltro getFiltro(int i) {
        return moServerDefecto.getFiltro(i);
    }

    public String getFiltroTabla(int i) {
        return moServerDefecto.getFiltroTabla(i);
    }

    public void borrarFiltrosTodos() {
        moServerDefecto.borrarFiltrosTodos();
    }

    public JServerServidorDatosParam getParametros() {
        return moServerDefecto.getParametros();
    }
    public IListaElementos<JElementoFiltro> getFiltros(){
        return moServerDefecto.getFiltros();
    }
}
