/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.opciones;

import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.plugin.IPlugInFrame;

/**
 *
 * @author eduardo
 */
public interface IPanelOpciones extends IPlugInFrame{
    public void add(IFormEdicion poForm);
    public void remove(IFormEdicion poForm);
    public IFormEdicion get(int i);
    public int count();
    
}
