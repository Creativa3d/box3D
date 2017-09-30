/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.aplicacion;

import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.*;

public class JDatosGeneralesPlugIn implements IPlugInFactoria {
    private IPlugInManager moPlugIngManager = null;
    private final IPlugInContexto moDatosGenerales;
    public JDatosGeneralesPlugIn(IPlugInContexto poDatosGenerales){
        moDatosGenerales = poDatosGenerales;
        moPlugIngManager  = new JPlugInManager(
                new String[]{}
        );
    }
    public IPlugInContexto getPlugInContexto() {
        return moDatosGenerales;
    }
    public IPlugInManager getPlugInManager(){
        return moPlugIngManager;
    }

    public void setPlugIngContexto(IPlugInContexto poPlugIngContexto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPlugIngManager(IPlugInManager poPlugIngManager) {
        moPlugIngManager = poPlugIngManager;
    }

}
