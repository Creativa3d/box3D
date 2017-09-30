/*
 * IFrame.java
 *
 * Created on 7 de septiembre de 2006, 17:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;

public interface IPlugInFrame extends IContainer {
    /**Identificador del formulario*/
    public String getIdentificador();
    /**Parametros*/
    public JFormEdicionParametros getParametros();
}
