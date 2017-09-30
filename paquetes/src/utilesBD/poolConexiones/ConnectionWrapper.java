/*
 * ConnectionWrapper.java
 *
 * Created on 2 de agosto de 2003, 1:40
 */


package utilesBD.poolConexiones;

import java.sql.*;
import java.util.Properties;
/**encapsula una conexion, pero en vez de cerrar la conexion la devuelve a la lista de conexiones*/
public class ConnectionWrapper implements Connection {
    private Connection moConexion;
    private final IPoolObjetos moPool ;
    private final JElementoConnection moElemConexion;
    /**
     * Creates a new instance of ConnectionWrapper
     * @param poPool pool de cnex.
     * @param poElemConexion elemento conexion concreta
     */
    public ConnectionWrapper(IPoolObjetos poPool ,JElementoConnection  poElemConexion) {
        moElemConexion = poElemConexion;
        moConexion = poElemConexion.getConex();
        moPool = poPool;
    }

    Connection getConexion_() {
        return moConexion;
    }
    public void clearWarnings() throws SQLException {
        moConexion.clearWarnings();
    }

    /**Devuelve la conexion a la lista de conexiones*/
    public void close() {
        if (moConexion != null){
            moPool.returnConnection(moConexion);
        }
        moConexion = null;
    }
    /**finalizar close*/
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
    public void commit() throws SQLException {
        moConexion.commit();
    }
    public Statement createStatement() throws SQLException {
        return moConexion.createStatement();
    }
    public boolean getAutoCommit() throws SQLException {
        return moConexion.getAutoCommit();
    }
    public String getCatalog() throws SQLException {
        return moConexion.getCatalog();
    }
    public DatabaseMetaData getMetaData() throws SQLException {
        return moConexion.getMetaData();
    }
    public int getTransactionIsolation() throws SQLException {
        return moConexion.getTransactionIsolation();
    }
    public SQLWarning getWarnings() throws SQLException {
        return moConexion.getWarnings();
    }
    public boolean isClosed() throws SQLException {
        return moConexion.isClosed();
    }
    public boolean isReadOnly() throws SQLException {
        return moConexion.isReadOnly();
    }
    public String nativeSQL(String sql) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.nativeSQL(sql);
    }
    public CallableStatement prepareCall(String sql) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareCall(sql);
    }
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareStatement(sql);
    }
    public void rollback() throws SQLException {
        moConexion.rollback();
    }
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        moConexion.setAutoCommit(autoCommit);
    }
    public void setCatalog(String catalog) throws SQLException {
        moConexion.setCatalog(catalog);
    }
    public void setReadOnly(boolean readOnly) throws SQLException {
        moConexion.setReadOnly(readOnly);
    }
    public void setTransactionIsolation(int level) throws SQLException {
        moConexion.setTransactionIsolation(level); 
    }
    
    
    /////////////////////////////////77
    ////////// version JVM 1.2
    /////////////////////////////////77
    
           
    public java.util.Map getTypeMap() throws SQLException {
        return moConexion.getTypeMap();
    }
    public void setTypeMap(java.util.Map map) throws SQLException {
        moConexion.setTypeMap(map);
    }       
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareCall(sql, resultSetType, resultSetConcurrency);
    }
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return moConexion.createStatement(resultSetType, resultSetConcurrency);
    }
        
    /////////////////////////////////77
    ////////// version JVM 1.4
    /////////////////////////////////77
            
    
    
    
    
    
    
    
    
    
    
    
    
    

    public Savepoint setSavepoint() throws SQLException {
        return moConexion.setSavepoint();
    }
    
    public Savepoint setSavepoint(String name) throws SQLException {
        return moConexion.setSavepoint(name);
    }
    
    public void setHoldability(int holdability) throws SQLException {
        moConexion.setHoldability(holdability);
    }
    
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return moConexion.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareStatement(sql, columnNames);
    }
    
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareStatement(sql, autoGeneratedKeys);
    }
    
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareStatement(sql, columnIndexes);
    }
    

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        moConexion.releaseSavepoint(savepoint);
    }

    public int getHoldability() throws SQLException {
        return moConexion.getHoldability();
    }
    
    public void rollback(Savepoint savepoint) throws SQLException {
        moConexion.rollback(savepoint);
    }
    
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        moElemConexion.setLast_sql(sql);
        return moConexion.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }



    
    /////////////////////////////////77
    ////////// version JVM 1.6
    /////////////////////////////////77
    
    public Clob createClob() throws SQLException {
        return moConexion.createClob();
    }

    public Blob createBlob() throws SQLException {
        return moConexion.createBlob();
    }

    public NClob createNClob() throws SQLException {
        return moConexion.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return moConexion.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        return moConexion.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        moConexion.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        moConexion.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        return moConexion.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        return moConexion.getClientInfo();
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return moConexion.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return moConexion.createStruct(typeName, attributes);
    }

    public Object unwrap(Class iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isWrapperFor(Class iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNetworkTimeout(java.util.concurrent.Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSchema(String schema) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void abort(java.util.concurrent.Executor executor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    
}
