/*
 * IUsuario.java
 *
 * Created on 30 de julio de 2008, 13:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;

import ListDatos.IServerServidorDatos;

public interface IUsuario {
    public boolean recuperarUsuario(IServerServidorDatos poServer, String psUsuario) throws Throwable;
    public String getCodUsuario();
    public String getNombre();
    public String getPassWord();
    public int getPermisos();
    public void aplicarFiltrosPorUsuario(IServerServidorDatos poServer, String psUsuario, String psCodUsuario) throws Throwable;
    
    
    
}
