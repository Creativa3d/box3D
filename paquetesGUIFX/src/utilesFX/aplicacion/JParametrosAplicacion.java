/*
 * JParametrosAplicacion.java
 *
 * Created on 30 de julio de 2008, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesFX.aplicacion;

import javafx.scene.paint.Color;
import utilesFX.JFXConfigGlobal;
import utilesGUIx.ColorCZ;
import utilesGUIx.aplicacion.IUsuario;
import utilesGUIx.aplicacion.JParametrosAplicacionModelo;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
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
        setColorFondo(Color.WHITE);
    }

    public void setImagenLogin(String psImagenLogin) {
        try {
            super.setImagenLogin(JFXConfigGlobal.getImagenCargada(psImagenLogin));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setColorFondo(Color poColor){
        setColorFondo(new ColorCZ(JFXConfigGlobal.toRGB(poColor)));
    }
    public void setImagenFondo(String psImagen) {
        try {
            super.setImagenFondo(JFXConfigGlobal.getImagenCargada(psImagen));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setImagenIcono(String psImagen) {
        try {
            super.setImagenIcono(JFXConfigGlobal.getImagenCargada(psImagen));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
