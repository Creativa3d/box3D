/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import ListDatos.ECampoError;
import utilesGUIx.plugin.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;

public class JPlugInSeguridadPermisosBase  extends JPlugInSeguridadPermisosBaseModelo {
    
    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, IPlugInFrame poFrame) throws ECampoError {
        addPermiso(poPermisos, poFrame.getIdentificador(), getIdenbotnito(poFrame), "");

    }


}
