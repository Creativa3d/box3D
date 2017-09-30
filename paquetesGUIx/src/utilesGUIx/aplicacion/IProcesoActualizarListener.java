/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;


public interface IProcesoActualizarListener {
    public void mostrarMensaje(JProcesoActualizar poFuente, String psMensaje) ;

    public void mostrarError(JProcesoActualizar poFuente, Throwable e);
}
