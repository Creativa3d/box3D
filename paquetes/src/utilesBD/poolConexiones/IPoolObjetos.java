/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.poolConexiones;

import java.sql.Connection;

/**
 *
 * @author eduardo
 */
public interface IPoolObjetos {
    public void returnConnection(Connection c);
    public void close();
}
