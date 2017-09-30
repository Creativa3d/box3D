/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.poolConexiones;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

/**
 *
 * @author eduardo
 */
public class JPoolObjetosJMX implements IPoolObjetosJMXMXBean{

    public String getConexion() {
        return "  URL: "+ PoolObjetos.getConexion()
                + "  Class:" + PoolObjetos.getClassJDBC() 
                + "  Usuario: "+ PoolObjetos.getUsuario()
                + "  TipoSQL: "+ PoolObjetos.getTipoSQL();
    }
    public String estadoMemoria() {
        StringBuilder loList = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
 
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
 
        loList.append("Max   Memory  :" + (maxMemory / 1024.0) + " KB");
        loList.append("<br>Total Memory  :" + (totalMemory / 1024.0) + " KB");
        loList.append("<br>Free  Memory  :" + (freeMemory / 1024.0) + " KB");
        loList.append("<br>Used  Memory  :" + (totalMemory / 1024.0 - freeMemory / 1024.0) + " KB");
 
        loList.append("<br>         ===============         ");
        MemoryMXBean mx = ManagementFactory.getMemoryMXBean();
 
        long initMemory = mx.getHeapMemoryUsage().getInit() + mx.getNonHeapMemoryUsage().getInit();
        long usedMemory = mx.getHeapMemoryUsage().getUsed() + mx.getNonHeapMemoryUsage().getUsed();
        long committedMemory = mx.getHeapMemoryUsage().getCommitted() + mx.getNonHeapMemoryUsage().getCommitted();
        long _maxMemory = mx.getHeapMemoryUsage().getMax() + mx.getNonHeapMemoryUsage().getMax();
 
        loList.append("<br>Max       memory:" + (_maxMemory / 1024.0) + " KB");
        loList.append("<br>Committed memory:" + (committedMemory / 1024.0) + " KB");
        loList.append("<br>Init      memory:" + (initMemory / 1024.0) + " KB");
        loList.append("<br>Used      memory:" + (usedMemory / 1024.0) + " KB");
 
        return loList.toString();
    }
    public String getEstado() {
        StringBuilder loList = new StringBuilder();
        
        loList.append(PoolObjetos.getPoolObjetos().toDepura());
        loList.append(PoolObjetos.getPoolObjetosEdicion().toDepura());
        
        return loList.toString();
    }
    public String getSQLActivas() {
        StringBuilder loList = new StringBuilder();
        
        loList.append("<html>");
        loList.append(PoolObjetos.getPoolObjetos().toDepura("select"));
        loList.append(PoolObjetos.getPoolObjetosEdicion().toDepura("edicion"));
        loList.append("</html>");
        
        return loList.toString();
    }
    
    public void cerrarTodasConex() {
        PoolObjetos.close();
    }
    
}
