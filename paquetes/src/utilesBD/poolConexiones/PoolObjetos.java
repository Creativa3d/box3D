/*
 * PoolObjetos.java
 *
 * Created on 30 de julio de 2003, 13:27
 */
package utilesBD.poolConexiones;

import utiles.config.*;
import utiles.*;
import ListDatos.*;
import java.io.File;

/**pool de conexiones*/
public class PoolObjetos {
    public static final String PARAMETRO_TipoSQL = "SimpleDataSource/TipoSQL";
    public static final String PARAMETRO_DRIVER_CLASS_NAME ="SimpleDataSource/driverClassName";
    public static final String PARAMETRO_Conexion ="SimpleDataSource/url";
    public static final String PARAMETRO_Usuario = "SimpleDataSource/user";
    public static final String PARAMETRO_Password = "SimpleDataSource/password";
    public static final String PARAMETRO_ConexionesMaximasSelect = "SimpleDataSource/conexionesMaximasSelect";
    public static final String PARAMETRO_ConexionesMaximasEdicion = "SimpleDataSource/conexionesMaximasEdicion";
    public static final String PARAMETRO_ConexionesMaximasSelectXML = "SimpleDataSource_conexionesMaximasSelect";
    public static final String PARAMETRO_ConexionesMaximasEdicionXML = "SimpleDataSource_conexionesMaximasEdicion";
    public static final String PARAMETRO_SQLValidacion = "SimpleDataSource_SQLValidacion";
    
    
    private static PoolObjetosImpl moPoolConection = null;
    private static PoolObjetosImpl moPoolConectionEdi = null;
    
    private static int mclConexionesMaximasSelect=10;
    private static int mclConexionesMaximasEdicion=10;

    private static String msConexion = null;
    private static String msClass = null;
    private static String msUsuario = null;
    private static String msPassword = null;
    private static String msTipoSQL = null;
    private static String msSQLValidacion = null;
    private static boolean mbInicializado = false;

    /** Creates a new instance of PoolObjetos */
    private PoolObjetos() {
    }
    
    public static synchronized void inicializar(    
            int pclConexionesMaximasSelect, int pclConexionesMaximasEdicion
            ,String psConexion ,String psClass 
            ,String psUsuario , String psPassword 
            ,String psTipoSQL, String psSQLValidacion ){
        mbInicializado=true;
        mclConexionesMaximasSelect=pclConexionesMaximasSelect;
        mclConexionesMaximasEdicion=pclConexionesMaximasEdicion;
        msConexion = psConexion ;
        msClass =psClass;
        msUsuario =psUsuario ;
        msPassword =psPassword ;
        msTipoSQL =psTipoSQL ;        
        msSQLValidacion = psSQLValidacion;
    }
    public static synchronized void inicializar(){
        if(!mbInicializado){
            mbInicializado=true;
            File loFileDir=new java.io.File(PoolObjetos.class.getClassLoader().getResource("/").getFile());
            File loFileXml = new File(loFileDir,"ConfigurationParameters.xml");
            if(!loFileXml.exists()){
                inicializarProperties();
            }else{
                //fichero CON la ruta y la EXTENSION
                String lsFicheroSinExtension = loFileXml.getAbsolutePath();
                //quitamos la extension
                lsFicheroSinExtension=lsFicheroSinExtension.substring(0, lsFicheroSinExtension.length()-4);
                JDatosGeneralesXML loDatosGeneralesXML = new JDatosGeneralesXML(lsFicheroSinExtension);
                try {
                    loDatosGeneralesXML.leer();
                    inicializar(loDatosGeneralesXML);
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(PoolObjetos.class.getName(), ex);
                }
                
            }
            
        }
    }
    public static synchronized void inicializarProperties(){
        mbInicializado=true;
        try{
            msClass = ConfigurationParametersManager.getParametro(PARAMETRO_DRIVER_CLASS_NAME );
            msConexion = ConfigurationParametersManager.getParametro(PARAMETRO_Conexion);
            msUsuario = ConfigurationParametersManager.getParametro(PARAMETRO_Usuario);
            msPassword = ConfigurationParametersManager.getParametro(PARAMETRO_Password);     
            msTipoSQL = ConfigurationParametersManager.getParametro(PARAMETRO_TipoSQL);     
            msSQLValidacion = ConfigurationParametersManager.getParametro(PARAMETRO_SQLValidacion);
        }catch(Exception e){
            JDepuracion.anadirTexto(PoolObjetos.class.getName(), e);
        }
        try{
            String lsValor = ConfigurationParametersManager.getParametro(PARAMETRO_ConexionesMaximasSelect);
            if(JConversiones.isNumeric(lsValor)){
                mclConexionesMaximasSelect = Integer.valueOf(lsValor).intValue();
            }else{
                JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PARAMETRO_ConexionesMaximasSelect);
            }
            if(mclConexionesMaximasSelect<=0){
                mclConexionesMaximasSelect=10;
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PARAMETRO_ConexionesMaximasSelect);
        }
        try{
            String lsValor = ConfigurationParametersManager.getParametro(PARAMETRO_ConexionesMaximasEdicion);
            if(JConversiones.isNumeric(lsValor)){
                mclConexionesMaximasEdicion = Integer.valueOf(lsValor).intValue();
            }else{
                JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PARAMETRO_ConexionesMaximasEdicion);
            }
            if(mclConexionesMaximasEdicion<=0){
                mclConexionesMaximasEdicion=10;
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PARAMETRO_ConexionesMaximasEdicion);
        }
    }
    public static synchronized void inicializar(JDatosGeneralesXML poDatos){
        mbInicializado=true;
        //conexion directa
        try{
            msClass = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME );
            msConexion = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_Conexion);
            msUsuario = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_Usuario);
            msPassword = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_Password);     
            msTipoSQL = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_TipoSQL);     
            msSQLValidacion = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+PARAMETRO_SQLValidacion);     
        }catch(Exception e){
            JDepuracion.anadirTexto(PoolObjetos.class.getName(), e);
        }
        //si no hay datos se usa la q usaria la aplicacion de escritorio con la listaconexiones
        if(msClass==null || msClass.equals("")){
            try{
                msClass = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME );
                msConexion = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_Conexion);
                msUsuario = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_Usuario);
                msPassword = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_Password);     
                msTipoSQL = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+JDatosGeneralesXML.PARAMETRO_TipoSQL);     
                msSQLValidacion = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+PARAMETRO_SQLValidacion);     
            }catch(Exception e){
                JDepuracion.anadirTexto(PoolObjetos.class.getName(), e);
            }
            
        }
        try{
            String lsValor = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+PoolObjetos.PARAMETRO_ConexionesMaximasSelectXML);
            if(!JConversiones.isNumeric(lsValor)){
                lsValor = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+PoolObjetos.PARAMETRO_ConexionesMaximasSelectXML);
            }
            if(JConversiones.isNumeric(lsValor)){
                mclConexionesMaximasSelect = Integer.valueOf(lsValor).intValue();
            }else{
                JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PoolObjetos.PARAMETRO_ConexionesMaximasSelectXML);
            }
            if(mclConexionesMaximasSelect<=0){
                mclConexionesMaximasSelect=10;
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PoolObjetos.PARAMETRO_ConexionesMaximasSelectXML);
        }
        try{
            String lsValor = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+PoolObjetos.PARAMETRO_ConexionesMaximasEdicionXML);
            if(!JConversiones.isNumeric(lsValor)){
                lsValor = poDatos.getPropiedad(JDatosGeneralesXML.mcsCONEXIONES+"/"+JDatosGeneralesXML.mcsCONEXIONDIRECTA+"/"+PoolObjetos.PARAMETRO_ConexionesMaximasEdicionXML);
            }
            if(JConversiones.isNumeric(lsValor)){
                mclConexionesMaximasEdicion = Integer.valueOf(lsValor).intValue();
            }else{
                JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PoolObjetos.PARAMETRO_ConexionesMaximasEdicionXML);
            }
            if(mclConexionesMaximasEdicion<=0){
                mclConexionesMaximasEdicion=10;
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetos.class.getName(), "No establecida la variable " + PoolObjetos.PARAMETRO_ConexionesMaximasEdicionXML);
        }
    }
    
    public static boolean isInicializado() {
        return mbInicializado;
    }
            
    public static JServerServidorDatos getServidorDatos(boolean pbEsEdicion) throws Exception {
        JServerServidorDatos loServer=null;
        if(pbEsEdicion){
            loServer = getPoolObjetosEdicion().getServidorDatos();
        }else{
            loServer = getPoolObjetos().getServidorDatos();
        }
        return loServer;
    }

    /**
     * implementacion de single_object
     * @return pool de conexiones
     */
    public static synchronized PoolObjetosImpl getPoolObjetos(){
        if (moPoolConection == null) {
            inicializar();
            if(msConexion!=null){
                moPoolConection = new PoolObjetosImpl(
                    mclConexionesMaximasSelect, msConexion, msClass
                    , msUsuario, msPassword, msTipoSQL 
                    );
                moPoolConection.setSQLValidacion(msSQLValidacion);
            }
        }
        return moPoolConection;
    }
    /**
     * implementacion de single_object
     * @return pool de conexiones
     */
    public static synchronized PoolObjetosImpl getPoolObjetosEdicion(){
        if (moPoolConectionEdi == null) {
            inicializar();
            if(msConexion!=null){
                moPoolConectionEdi = new PoolObjetosImpl(
                    mclConexionesMaximasEdicion, msConexion, msClass
                    , msUsuario, msPassword, msTipoSQL 
                    );
                moPoolConectionEdi.setSQLValidacion(msSQLValidacion);
            }
        }
        return moPoolConectionEdi;
    }
    public static synchronized void close(){
        if(moPoolConection!=null){
            moPoolConection.close();
        }
        if(moPoolConectionEdi!=null){
            moPoolConectionEdi.close();
        }
        moPoolConection=null;
        moPoolConectionEdi=null;
        
    }
    /**
     * @return the msConexion
     */
    public static String getConexion() {
        return msConexion;
    }

    /**
     * @return the msClass
     */
    public static String getClassJDBC() {
        return msClass;
    }

    /**
     * @return the msUsuario
     */
    public static String getUsuario() {
        return msUsuario;
    }

    /**
     * @return the msPassword
     */
    public static String getPassword() {
        return msPassword;
    }

    /**
     * @return the msTipoSQL
     */
    public static String getTipoSQL() {
        return msTipoSQL;
    }
        
}

