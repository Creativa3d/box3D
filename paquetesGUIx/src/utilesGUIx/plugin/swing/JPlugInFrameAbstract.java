/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.plugin.swing;

import javax.swing.JPanel;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

public abstract class JPlugInFrameAbstract extends JPanel implements IPlugInFrame, IContainer {
    protected JFormEdicionParametros moParametros = new JFormEdicionParametros();
    
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    public void aplicarListaComponentesAplicacion() {
    }

    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public IContainer getContenedorI() {
        return this;
    }
    
    
    
    
    
}
