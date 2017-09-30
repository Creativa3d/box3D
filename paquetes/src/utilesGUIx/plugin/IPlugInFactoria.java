/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

public interface IPlugInFactoria {
    public IPlugInContexto getPlugInContexto();
    public IPlugInManager getPlugInManager();
    public void setPlugIngContexto(IPlugInContexto poPlugIngContexto);
    public void setPlugIngManager(IPlugInManager poPlugIngManager);
}
