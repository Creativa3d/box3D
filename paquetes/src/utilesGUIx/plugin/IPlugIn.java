/*
 * IPlugIn.java
 *
 * Created on 13 de septiembre de 2006, 18:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import utilesGUIx.formsGenericos.IPanelControlador;

public interface IPlugIn {
    /**Este metodo se llama justo despues de inicializar el form. principal
     * @param poContexto
     */
    public void procesarInicial(IPlugInContexto poContexto);
    /**Este metodo se llama justo despues de inicializar un form. principal de tipo consulta
     * @param poContexto
     * @param poConsulta
     */
    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta);
    /**Este metodo se llama justo despues de inicializar el form. edicion
     * @param poContexto
     * @param poFrame
     */
    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame);
    /**Este metodo se llama justo despues de inicializar el controlador (JT2controlador)
     * @param poContexto
     * @param poControlador
     */
    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador);
    /**Este metodo se llama justo antes de finalizar la aplicacion
     * @param poContexto
     */
    public void procesarFinal(IPlugInContexto poContexto);
}
