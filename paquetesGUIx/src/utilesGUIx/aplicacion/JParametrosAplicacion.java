/*
 * JParametrosAplicacion.java
 *
 * Created on 30 de julio de 2008, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;

import java.awt.Color;
import utilesGUIx.ColorCZ;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;

public class JParametrosAplicacion extends JParametrosAplicacionModelo {

    /** Creates a new instance of JParametrosAplicacion */
    public JParametrosAplicacion(
        String psNombreProyecto,
        IUsuario poUsuario,
        String[] pasPlugIn,
        IPlugInSeguridadRW poPlugInSeguridad,
        IActualizarEstruc poEstructura
        ) {
        super(psNombreProyecto, poUsuario, pasPlugIn, poPlugInSeguridad, poEstructura);
        setColorFondo(Color.white);
    }

    public void setImagenLogin(String psImagenLogin) {
        try {
            super.setImagenLogin(JIMGTrata.getIMGTrata().getImagenCargada(psImagenLogin));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setColorFondo(Color poColor){
        setColorFondo(new ColorCZ(poColor.getRGB())); ;
    }
    public void setImagenFondo(String psImagen) {
        try {
            super.setImagenFondo(JIMGTrata.getIMGTrata().getImagenCargada(psImagen));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setImagenIcono(String psImagen) {
        try {
            super.setImagenIcono(JIMGTrata.getIMGTrata().getImagenCargada(psImagen));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
