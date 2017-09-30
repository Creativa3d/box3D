/*
 * JServerServidorDatosBD.java
 *
 * Created on 16 de enero de 2007, 11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesBD.poolConexiones;

import ListDatos.*;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import utiles.*;

//con base de datos

public class JServerServidorDatosBDPool extends JServerServidorDatosBD {
    /**motor de select*/
    protected PoolObjetosImpl moPool;
//    protected PoolObjetosImpl moPoolEdit;problema de editar y luego hacer una cosnulta sobre lo editado
    private final Timer moTimer;
    
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosBDPool() {
        super();
        moTimer = new Timer();
        moTimer.schedule(new TimerTask() {
                public void run() {
//                    try{
//                       moPoolEdit.limpiarExpirados();
//                    }catch(Throwable e){
//                        moTimer.cancel();
//                    }
                    try{
                       moPool.limpiarExpirados();
                    }catch(Throwable e){
                        moTimer.cancel();
                    }
                }
            }, 30 * 60 * 1000, 30 * 60 * 1000);//30 min
        
    }

    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     */
    public JServerServidorDatosBDPool(String psDriver, String psURL, String psUsu, String psPassword) throws Throwable{
        this(psDriver, psURL, psUsu, psPassword, String.valueOf(JSelectMotor.mclAccess));
    }
    /**
     * Constructor
     * @param poConex Conexion
     */
    public JServerServidorDatosBDPool(JServerServidorDatosConexion poConex) throws Throwable{
        this(poConex.getClase(), poConex.getURL(), poConex.getUSUARIO(), poConex.getPASSWORD(), String.valueOf(poConex.getTipoBD()));
    }
    
    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     * @param psTipoBDStandar BD Definidas en JSelectMotor
     */
    public JServerServidorDatosBDPool(final String psDriver, final String psURL, final String psUsu, final String psPassword, final String psTipoBDStandar) throws Throwable{
        this();
        //conexiones consultas
        if(String.valueOf(JSelectMotor.mclAccess).equals(psTipoBDStandar)){
            moPool = new PoolObjetosImpl(1, psURL, psDriver, psUsu, psPassword, psTipoBDStandar);
        }else{
            moPool = new PoolObjetosImpl(3, psURL, psDriver, psUsu, psPassword, psTipoBDStandar);            
        }
        moPool.setTiempoExpiracion(15*60*1000);//15 min
        //la abrimos/cerramos para comprobar q la conexion funciona
        moPool.getServidorDatos().close();
        //conexiones edicion
//        moPoolEdit = new PoolObjetosImpl(1, psURL, psDriver, psUsu, psPassword, psTipoBDStandar);
//        moPoolEdit.setTiempoExpiracion(15*60*1000);//15 min
    }

    public void setConec(Connection poConec) {
        throw new NoClassDefFoundError("ERROR NO USAR");
    }

    public Connection getConec() {
        throw new NoClassDefFoundError("ERROR NO USAR");
    }

    protected synchronized void beginTrans() throws SQLException {
        throw new NoClassDefFoundError("ERROR NO USAR");
    }

    protected synchronized void commitTrans() throws SQLException {
        throw new NoClassDefFoundError("ERROR NO USAR");
    }

    protected synchronized void rollBackTrans() throws SQLException {
        throw new NoClassDefFoundError("ERROR NO USAR");
    }

    public JTableDefs getTableDefs() throws Exception {
        JServerServidorDatosBD loServer = null;
        try {
            loServer = moPool.getServidorDatos().getServerBD();
            loServer.setFiltros(getFiltros());
            loServer.setParam(moParam);
            return loServer.getTableDefs();
        } finally{
            if(loServer!=null){
                try {
                    loServer.close();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }

    
    
    public IResultado moActualizar(String psNombreServlet, JActualizar poActualizar, IServerEjecutar poEjecutar, ISelectEjecutarSelect poEstruc){
        JServerServidorDatosBD loServer = null;
        try {
//            //para evitar problemas de sincronizacion con la base datos
//            //obligamos a q la siguiente consulta sea con una conexion nueva
//            moPoolSelect.setUsarSiguienteConexionNueva(true);
            loServer = moPool.getServidorDatos().getServerBD();
            loServer.setFiltros(getFiltros());
            loServer.setParam(moParam);
            return loServer.moActualizar(psNombreServlet, poActualizar, poEjecutar, poEstruc);
        } catch (Exception ex) {
            return new JResultado(ex.toString(), false);
        } finally{
            if(loServer!=null){
                try {
                    loServer.close();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }


    public void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        JServerServidorDatosBD loServer = null;
        try {
            loServer = moPool.getServidorDatos().getServerBD();
            loServer.setFiltros(getFiltros());
            loServer.setParam(moParam);
            //introduccion de campos a la select de sistema
            poSelect.setPassWord(msPassWord);
            poSelect.setPermisos(msPermisos);
            poSelect.setUsuario(msUsuario);
            //recuperacion de datos fisica
            loServer.recuperarDatosBD(v, poSelect.msSQL(moPool.getSelectMotor()), psTabla);
        } finally{
            if(loServer!=null){
                try {
                    loServer.close();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }

    }
    public JListDatos getListDatos(final JSelect poSelect, String psTabla) throws Exception {
        JServerServidorDatosBD loServer = null;
        try {
            loServer = moPool.getServidorDatos().getServerBD();
            loServer.setFiltros(getFiltros());
            loServer.setParam(moParam);
            return loServer.getListDatos(poSelect, psTabla);
        } finally{
            if(loServer!=null){
                try {
                    loServer.close();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }

    public JTableDef getTableDef(String psSelect, String psTabla) throws Exception {
        JServerServidorDatosBD loServer = null;
        try {
            loServer = moPool.getServidorDatos().getServerBD();
            loServer.setFiltros(getFiltros());
            loServer.setParam(moParam);
            return loServer.getTableDef(psSelect, psTabla);
        } finally{
            if(loServer!=null){
                try {
                    loServer.close();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }

    
    public JTableDef getTableDef(String psTabla) throws Exception {
        JServerServidorDatosBD loServer = null;
        try {
            loServer = moPool.getServidorDatos().getServerBD();
            loServer.setFiltros(getFiltros());
            loServer.setParam(moParam);
            return loServer.getTableDef(psTabla);
        } finally{
            if(loServer!=null){
                try {
                    loServer.close();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
    }
    
    /**
     *
     * cerrar objeto, si es tipo base de datos es muy importante hacerlo
     * @throws Exception el error en caso de error
     */
    public void close() throws Exception{
        try{
            moTimer.cancel();
        }catch(Throwable e){
            
        }
        if(moPool !=null) {
            moPool.close();
        }
//        if(moPoolEdit !=null) {
//            moPoolEdit.close();
//        }
    }

}
