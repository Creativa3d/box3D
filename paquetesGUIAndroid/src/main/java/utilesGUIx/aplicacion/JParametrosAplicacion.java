/*
 * JParametrosAplicacion.java
 *
 * Created on 30 de julio de 2008, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;

import android.content.Context;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;

public class JParametrosAplicacion extends JParametrosAplicacionModelo {
    private Context moContext;
    

    /** Creates a new instance of JParametrosAplicacion */
    public JParametrosAplicacion(
        Context poContext,    
        String psNombreProyecto,
        IUsuario poUsuario,
        String[] pasPlugIn,
        IPlugInSeguridadRW poPlugInSeguridad,
        IActualizarEstruc poEstructura
        ) {
        super(psNombreProyecto, poUsuario, pasPlugIn, poPlugInSeguridad, poEstructura);
    moContext = poContext;    
    }

    /**
     * @return the moContext
     */
    public Context getContext() {
        return moContext;
    }

    /**
     * @param moContext the moContext to set
     */
    public void setContext(Context moContext) {
        this.moContext = moContext;
    }
}
