/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.plugin;

import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**Contenedor de componenetes del formulario*/
public interface IContainer {
    /**Lista de JComponenteAplicacion*/
    public IComponenteAplicacion getListaComponentesAplicacion();
    /**Crea/deshabilita los la lista de JComponenteAplicacion*/
    public void aplicarListaComponentesAplicacion();
    
}
