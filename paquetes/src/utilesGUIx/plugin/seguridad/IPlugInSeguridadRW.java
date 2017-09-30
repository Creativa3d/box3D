/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import ListDatos.IFilaDatos;

public interface IPlugInSeguridadRW {
    public JTPlugInGrupos getGrupos() throws Exception;
    public void guardarGrupo(IFilaDatos poFila) throws Exception;
    public JTPlugInListaPermisos getListaPermisosGrupo(String psGrupo) throws Exception;
    public void guardarListaPermisosGrupo(String psGrupo, JTPlugInListaPermisos poPermisos) throws Exception;

    public JTPlugInUsuarios getUsuarios() throws Exception;
    public void guardarUsuario(IFilaDatos poUsuario) throws Exception;
    public JTPlugInListaPermisos getListaPermisosUsuario(String psUsuario) throws Exception;
    public void guardarListaPermisosUsuario(String psUsuario, JTPlugInListaPermisos poPermisos) throws Exception;

    public String getSuperUsuario();

    public JTPlugInUsuariosGrupos getUsuariosGrupos() throws Exception;
    public void guardareUsuariosGrupos(JTPlugInUsuariosGrupos poUsuariosGrupos) throws Exception;

    public JTPlugInListaPermisos getListaPermisosBase() throws Exception ;
}
