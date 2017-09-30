/*
 * PoolObjetos.java
 *
 * Created on 30 de julio de 2003, 13:27
 */
package utilesBD.poolConexiones;



import ListDatos.*;
import java.sql.*;
import java.util.HashSet;
import utiles.*;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;


/**pool de conexiones*/
public class PoolObjetosImpl implements IPoolObjetos {
    private long mlTiempoExpiracion=4*60*1000;

    private final JListaElementos bloqueados = new JListaElementos();
    private final JListaElementos nobloqueados = new JListaElementos();
    
    private int mlNumeroConexiones=10;

    private JConstructorEstructuraBDConnection moEstructura;
    
    private String msConexion = null;
    private String msClass = null;
    private String msUsuario = null;
    private String msPassword = null;
    private String msTipoSQL = null;
    private boolean mbCerrarConexionDespuesUsar=false;
    private boolean mbUsarSiguienteConexionNueva=false;
    private String msSQLValidacion;
    
    public PoolObjetosImpl(int plNumeroConexiones,
        String psConexion, String psClass, String psUsuario, String psPassword, String psTipoSQL
            ) {
        mlNumeroConexiones=plNumeroConexiones;
        msConexion = psConexion;
        msClass = psClass;
        msUsuario = psUsuario ;
        msPassword = psPassword;
        msTipoSQL=psTipoSQL;
    }
    /**Despues de cada operacion cierra la conexion*/
    public synchronized void setCerrarConexionDespuesUsar(boolean pbCerrar){
        mbCerrarConexionDespuesUsar=pbCerrar;
    }
    /**Obliga a que la siguiente conexion sea nueva*/
    public synchronized void setUsarSiguienteConexionNueva(boolean pbNueva){
        mbUsarSiguienteConexionNueva=pbNueva;
    }
    public synchronized void setSQLValidacion(String psSQLValidacion){
        msSQLValidacion=psSQLValidacion;
    }
    /**Devuelve una conexion*/
    private synchronized JElementoConnection checkOut() throws SQLException{
        long ahora = System.currentTimeMillis();
        while(mlBloqueados()>=mlNumeroConexiones){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING,PoolObjetosImpl.class.getName()
                    , "PoolObjetos: Todos bloqueados: Antes de bloquear conexion " + String.valueOf(mlBloqueados()));
            try{
                wait(5 * 1000);
            }catch(Exception e){
                //vacio
            }
//DIABLOS!!! puede ser q se cierre en mitad de una operacion, ASI QUE DOBLAMOS EL TIEMPO EXPIRACION
            //NO USAR ITERADORES
            Object[] lao = bloqueados.toArray();
            for (int i = 0 ; i < lao.length; i++){
                JElementoConnection loElem = (JElementoConnection) lao[i];
                ahora = System.currentTimeMillis();
                if ( ( ahora - loElem.getCreada() ) > mlTiempoExpiracion*2){
                    //el objeto a expirado
                    bloqueados.remove(loElem);
                    expirar( loElem );loElem = null;
                }
            }
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName()
                    , "PoolObjetos: Todos bloqueados: Despues de Bloquear conexion");
        }
        if( nobloqueados.size()>0){
            //1 expiramos todas las conexiones q tengan q expirar
            limpiarExpirados();
            //2 vemos conexion activa
            Object[] lao = nobloqueados.toArray();
            if(lao.length>0){
                //GC-608: Se escoge el pool de conexiones mas antiguo, para coger el mismo, por problemas con actualizaciones en los listados
                JElementoConnection loElemMin=(JElementoConnection) lao[0];
                for (Object lao1 : lao) {
                    JElementoConnection loElem = (JElementoConnection) lao1;
                    if (loElem.getCreada() < loElemMin.getCreada()){
                        loElemMin=loElem;
                    }
                }
                JElementoConnection loElem = loElemMin;
                //si la siguiente conexion nueva
                if(mbUsarSiguienteConexionNueva){
                    //si ha alcanzado el numero de conexiones maximas cierra una
                    if(lao.length>=mlNumeroConexiones){
                        nobloqueados.remove (loElem);
                        expirar(loElem);
                    }
                    mbUsarSiguienteConexionNueva=false;
                }else{
                    //devuelve una conexion y la mete en la lista de bloqueados
                    nobloqueados.remove (loElem);
                    bloqueados.add(loElem);
                    return loElem;
                }
            }
        }
        JElementoConnection loElem = crear();
        bloqueados.add(loElem);
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName(),"PoolObjetos bloqueados " +String.valueOf(mlBloqueados()));
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName(),"PoolObjetos NO bloqueados " +String.valueOf(mlNOBloqueados()));
        return loElem ;
    }
    public synchronized void limpiarExpirados() {
        long ahora = System.currentTimeMillis();
        JElementoConnection o;
        //1 expiramos todas las conexiones q tengan q expirar
        Object[] lao = nobloqueados.toArray();
        for (int i = 0 ; i < lao.length; i++){
            o = (JElementoConnection) lao[i];
            if ( ( ahora - o.getCreada() ) > mlTiempoExpiracion){
                //el objeto a expirado
                nobloqueados.remove(o);
                expirar( o );o = null;
            } else {
                if (!validar(o)){
                    nobloqueados.remove(o);
                    expirar(o);
                    o=null;
                }
            }
        }
        notifyAll();
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName(),"PoolObjetos bloqueados " +String.valueOf(bloqueados.size()));
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName(),"PoolObjetos NO bloqueados " +String.valueOf(nobloqueados.size()));
    }
    private synchronized int mlBloqueados() throws SQLException {
        int lBloq = 0;
        Object[] lao = bloqueados.toArray();
        for (int i = 0 ; i < lao.length; i++){
            JElementoConnection o = (JElementoConnection) lao[i];
            if(validar(o)){
                lBloq++;
            }
        }
        return lBloq;
    }
    private synchronized int mlNOBloqueados() throws SQLException {
        int lBloq = 0;
        Object[] lao = nobloqueados.toArray();
        for (int i = 0 ; i < lao.length; i++){
            JElementoConnection o = (JElementoConnection) lao[i];
            if(validar(o)){
                lBloq++;
            }
        }
        return lBloq ;
    }
    private synchronized void checkIn(Connection poConex){
        JElementoConnection loResult = null;
        Object[] lao = bloqueados.toArray();
        for (int i = 0 ; i < lao.length; i++){
            JElementoConnection o = (JElementoConnection) lao[i];
            if(o.getConex()==poConex){
                loResult = o;
            }
        }
        if(loResult!=null){
            bloqueados.remove(loResult);
            if(mbCerrarConexionDespuesUsar){
                expirar(loResult);
            }else{
                nobloqueados.add(loResult);
            }
        }else{
            expirar(new JElementoConnection(poConex, 0));
        }
        notifyAll();
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName(),"PoolObjetos bloqueados " +bloqueados.size());
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,PoolObjetosImpl.class.getName(),"PoolObjetos NO bloqueados " +nobloqueados.size());
    }
    private synchronized JElementoConnection crear() throws SQLException{
        String lsConexion = null;
        String lsClass = null;
        String lsUsuario = null;
        String lsPassword = null;
        try{
            lsClass = msClass;
            lsConexion = msConexion;
            lsUsuario = msUsuario;
            lsPassword = msPassword;

            Class.forName(lsClass);
        }catch(Exception e){
            JDepuracion.anadirTexto(PoolObjetosImpl.class.getName(), e);
        }
        
        return new JElementoConnection(DriverManager.getConnection(lsConexion, lsUsuario , lsPassword ), System.currentTimeMillis());
    }
    private synchronized void expirar(JElementoConnection o) {
        try{
            o.getConex().close();
        }catch(Throwable e){
            JDepuracion.anadirTexto(PoolObjetosImpl.class.getName(), e);
        }
        notifyAll();
    }
    private synchronized boolean validar(JElementoConnection o) {
        try {
            boolean lbValidar = o!=null && o.getConex()!=null && (!o.getConex().isClosed());
            if(lbValidar && !JCadenas.isVacio(msSQLValidacion)){
                try{
                    Statement stmt = o.getConex().createStatement();
                    ResultSet rs = stmt.executeQuery(msSQLValidacion);
                } catch (Throwable ex) {
                    lbValidar=false;
                }
            }
            return lbValidar;
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(PoolObjetosImpl.class.getName(), ex);
            return false;
        }
    }
    /**
     * Devuelve una conexion libre/creada
     * @return conexion
     * @throws SQLException error
     */
    public Connection getConnection() throws SQLException{
        return new ConnectionWrapper(this,checkOut() );
    }
    /**devuelve la conexion a la lista de conexiones libres*/
    public void returnConnection(Connection c){
        checkIn(c);
    }
    /**
     * Devuelve una conexion libre/creada
     * @param username usuario
     * @param password password
     * @throws SQLException error
     * @return conexion
     */
    public Connection getConnection(String username, String password) throws SQLException{
        return new ConnectionWrapper(this, checkOut());
    }

    /**
     * Deveulve el motor de conversion de objeto a SQL
     * @return select motor
     */
    public ISelectMotor getSelectMotor() throws Exception {
        String lsTipo = msTipoSQL;
        ISelectMotor loSelect = JSelectMotorFactory.getInstance().getSelectMotor(lsTipo);
        if(loSelect==null){
            JSelectMotorFactory.getInstance().registrarMotor(lsTipo, (ISelectMotor)Class.forName(lsTipo).newInstance(), lsTipo);
            loSelect = JSelectMotorFactory.getInstance().getSelectMotor(lsTipo);
        }
        return loSelect;
    }

    public synchronized JConstructorEstructuraBDConnection getEstructura(Connection poConex) throws SQLException{
        //se hace asi pq se supone q el poolObjetos continen conexiones a la misma BD
        if(moEstructura == null){
            moEstructura = new JConstructorEstructuraBDConnection(poConex, true);
        }
        //para aplicaciones locales siempre es valida la ultima conexion
        moEstructura.setConec(poConex);
        return moEstructura;
//        return new JConstructorEstructuraBDConnection(poConex);
    }
    public JServerServidorDatos getServidorDatos() throws Exception {
        JServerServidorDatos loServer = new JServerServidorDatos();
        loServer.setTipo(JServerServidorDatos.mclTipoBD);
        loServer.setConec(getConnection());
        loServer.setSelect(getSelectMotor());
        loServer.setConstrucEstruc(getEstructura(loServer.getConec()));
        return loServer;
    }

    public void close() {
        if( nobloqueados.size()>0){
            Object[] lao = nobloqueados.toArray();
            for (int i = 0 ; i < lao.length; i++){
                expirar((JElementoConnection) lao[i]);
            }
        }
        if( bloqueados.size()>0){
            Object[] lao = bloqueados.toArray();
            for (int i = 0 ; i < lao.length; i++){
                expirar((JElementoConnection) lao[i]);
            }
        }
        nobloqueados.clear();
        bloqueados.clear();
        
    }

    /**
     * @return the mlTiempoExpiracion
     */
    public long getTiempoExpiracion() {
        return mlTiempoExpiracion;
    }

    /**
     * @param mlTiempoExpiracion the mlTiempoExpiracion to set
     */
    public void setTiempoExpiracion(long mlTiempoExpiracion) {
        this.mlTiempoExpiracion = mlTiempoExpiracion;
    }

    public Object[] getElementosConexion(boolean pbBloqueados) {
        if(pbBloqueados){
            return bloqueados.toArray();
        }else{
            return nobloqueados.toArray();
        }
    }
//    public static Object getPropValue(Object bean, String prop) throws Exception {
//        java.lang.reflect.Field f;
//        try {
//            f = bean.getClass().getDeclaredField(prop);
//            f.setAccessible(true);
//            
//        } catch (NoSuchFieldException e) {
//            Class superClass = bean.getClass().getSuperclass();
//            if (superClass == null) {
//                f=null;
//            } else {
//                f = bean.getClass().getDeclaredField(prop);
//                f.setAccessible(true);
//            }
//        }        
//        if (f!=null)
//            return f.get(bean);
//        
//        return null;
//    }
//    
//    
//    public static String Connection_get_sql(Connection aconn) {
//        
//        if (aconn==null)
//            return null;
//
//        Connection conn = aconn;
//        if (aconn instanceof ConnectionWrapper)
//            conn = ((ConnectionWrapper)conn).getConexion_();
//        
//        try {
//            Object a = getPropValue(conn, "activeStatements");
//            
//            if (a!=null) {
//                HashSet<Object> a1 = (HashSet<Object>) a;
//                Object[] a2 = a1.toArray();
//                for (int i=0; i<a2.length; i++) {
//                    Object a3 = a2[i];
//                    Object b = getPropValue(a3, "fixedStmt");
//                    
//                    if (b!=null) {
//                        Object c = getPropValue(b, "statement");
//                        if (c != null)
//                            return c.toString();
//                    }
//                }
//            }
//            
//            return null;
//        
//        } catch (Throwable e) {
//            System.out.print(e.getMessage());
//        }
//                
//        
//        return null;
//    }
//    
//    
    public String toDepura() {
        return toDepura("");
    }
    public String toDepura(String psNombre) {
        StringBuilder sb = new StringBuilder();

        try {

            sb.append("\n<br/>Pool user " + psNombre+ " =").append(msUsuario)
                    .append(" max_conexiones=").append(mlNumeroConexiones)
                    .append(" size(bloqueados)=").append(bloqueados.size())
                    .append(" size(nobloqueados)=").append(nobloqueados.size());

            sb.append("<table border='1'>")
                    .append("<tr><td>tipo</td>")
                    .append("<td>from(sg)</td>")
                    .append("<td>create(sg)</td>")
                    .append("<td>sql</td>")
                    .append("</tr>");
            int lBloq = 0;
            Object[] lao = bloqueados.toArray();
            long ahora = System.currentTimeMillis();
            for (int i = 0; i < lao.length; i++) {
                JElementoConnection loElem = (JElementoConnection) lao[i];
                double a1 = (ahora - loElem.getServicio()) / 1000;
                double a2 = (ahora - loElem.getCreada()) / 1000;
                sb.append("<tr><td>bloqueado</td>")
                        .append("<td>").append( (int)(Math.rint(a1 * 1000) / 1000)).append("</td>")
                        .append("<td>").append( (int)(Math.rint(a2 * 1000) / 1000)).append("</td>")
                        .append("<td>").append(loElem.getLast_sql())
//                        .append("<br/>reflect:").append(Connection_get_sql(loElem.getConex()))
                        .append("</td>")
                        .append("</tr>");
            }
            lBloq = 0;
            lao = nobloqueados.toArray();
            for (int i = 0; i < lao.length; i++) {
                JElementoConnection loElem = (JElementoConnection) lao[i];
                double a1 = (ahora - loElem.getServicio()) / 1000;
                double a2 = (ahora - loElem.getCreada()) / 1000;
                sb.append("<tr><td>nobloqueado</td>")
                        .append("<td>").append((int)(Math.rint(a1 * 1000) / 1000)).append("</td>")
                        .append("<td>").append((int)(Math.rint(a2 * 1000) / 1000)).append("</td>")
                        //.append("<td>").append( loElem.last_sql ).append("</td>")
                        .append("<td></td>")
                        .append("</tr>");

            }
            sb.append("</table>");

        } catch (Throwable e) {
            //
        }
        return sb.toString();
    }
}
