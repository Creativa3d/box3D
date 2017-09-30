/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.usuarios.tablasExtend;

import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import utilesGUIx.aplicacion.IUsuario;
import utilesGUIx.aplicacion.usuarios.tablas.JTUSUARIOS;

/**
 *
 * @author eduardo
 */
public class JUsuarioInterfazMain implements IUsuario {

    private JTUSUARIOS moUsu;

    @Override
    public String getCodUsuario() {
        return moUsu.getCODIGOUSUARIO().getString();
    }

    @Override
    public String getNombre() {
        return moUsu.getLOGIN().getString();
    }

    @Override
    public String getPassWord() {
        return moUsu.getCLAVE().getString();
    }

    @Override
    public int getPermisos() {
        return moUsu.getPERMISO().getInteger();
    }

    @Override
    public boolean recuperarUsuario(IServerServidorDatos poServer, String psUsuario) throws Throwable {
        moUsu = new JTUSUARIOS(poServer);
        moUsu.recuperarFiltradosNormal(
                new JListDatosFiltroElem(
                        JListDatos.mclTIgual, moUsu.lPosiLOGIN, psUsuario),
                false
        );
        return moUsu.moList.moveFirst();
    }

    @Override
    public void aplicarFiltrosPorUsuario(IServerServidorDatos poServer, String psUsuario, String psCodUsuario) throws Throwable {
    }
}
