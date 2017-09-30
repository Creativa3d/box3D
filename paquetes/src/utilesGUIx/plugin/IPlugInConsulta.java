/*
 * IPlugInConsulta.java
 *
 * Created on 7 de septiembre de 2006, 18:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import utilesGUIx.formsGenericos.IPanelControladorConsulta;

public interface IPlugInConsulta extends IPlugInFrame {
    public void addControlador(IPanelControladorConsulta poControlador);
    public void removeControlador(IPanelControladorConsulta poControlador);
    public IPanelControladorConsulta getControlador(int i);
    public  IPanelControladorConsulta getControladorActual();
    public void getControladorSize();
}
