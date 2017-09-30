/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.plugin.javafx;

import javafx.scene.layout.GridPane;
import javax.swing.JPanel;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

public abstract class JPlugInFrameFXAbstract extends GridPane implements IPlugInFrame, IContainer {
    protected JFormEdicionParametros moParametros = new JFormEdicionParametros();
    
    @Override
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    @Override
    public void aplicarListaComponentesAplicacion() {
    }

    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public IContainer getContenedorI() {
        return this;
    }
    
    
    
    
    
}
