/*
 * IActualizarEstruc.java
 *
 * Created on 30 de julio de 2008, 13:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.actualizarEstruc;

import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JTableDefs;

public interface IActualizarEstruc {
    public JTableDefs getTablasOrigen() throws Exception;
    public void postProceso(IServerServidorDatos poServer) throws Exception;
}
