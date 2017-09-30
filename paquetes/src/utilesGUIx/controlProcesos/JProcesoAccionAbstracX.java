/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.controlProcesos;

import utilesGUI.procesar.JProcesoAccionAbstrac;
import utilesGUIx.JGUIxConfigGlobalModelo;

public abstract class JProcesoAccionAbstracX extends JProcesoAccionAbstrac {
    
    public JProcesoAccionAbstracX(){
    }
    public JProcesoAccionAbstracX(Object poTAG){
        moParametros.setTag(poTAG);
    }

    public void mostrarError(Throwable e){
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(moParametros.getTag(), e, null);
    }
}
