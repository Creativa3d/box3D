/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import utiles.IListaElementos;
import utilesGUIx.formsGenericos.IPanelControlador;

public interface IPlugInManager {
    /**Crea el plugin seguridad, se controla a traves de IPlugInSeguridadRW*/
    public void crearPlugInSeguridad();
    /**Establece el plugin de seguridad
     * @param poSeguridad
     */
    public void setPlugInSeguridad(IPlugInManager poSeguridad);
    /**Devuelve el plugin seguridad
       * @return 
        */
    public IPlugInManager getPlugInSeguridad();
    /**Añadimos un plugin al sistema
         * @param psClase Cadena de la clase a instanciar
         */
    public void addPlugIn(String psClase);
    /**Añadimos un plugin al sistema
         * @param poClase Instancia Iplugin
         */
    public void addPlugIn(IPlugIn poClase);
    /**
       *  borra todas las clases que coincidan con el parametro
     * @param psClase Cadena de la clase a borrar de la lista
        */
    public void deletePlugIn(String psClase);
    /**
       *  borra la clases que coincida con la instancia del parametro
     * @param poClase Instancia Iplugin
        */
    public void deletePlugIn(IPlugIn poClase);
    /**Devuelve la lista plugin
     * @return */
    public IListaElementos<IPlugIn> getListaPlugIn();
    /**Añade listener
     * @param poList
     */
    public void addListener(IPlugInListener poList);
    /**Borra listener
     * @param poList
     */
    public void removeListener(IPlugInListener poList);
    /**Se pasan todos los plugin a la consulta pasada por parametro
     * @param poPlugIn
     */
    public void procesarConsulta(IPlugInContexto poPlugIn, IPlugInConsulta poConsulta);
    /**Se pasan todos los plugin inicialmente, se suelen cargar lso menus de la pantalla principal
     * @param poContexto
     */
    public void procesarInicial(IPlugInContexto poContexto);
    /**Se pasan todos los plugin al frame pasada por parametro
     * @param poContexto
     * @param poFrame
     */
    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame);
    /**Se pasan todos los plugin al controlador pasado por parametro
     * @param poContexto
     * @param poControlador
     */
    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador);

}
