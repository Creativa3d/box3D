/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.toolBar;

import java.io.File;
import java.util.HashMap;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JComponenteAplicacion extends JComponenteAplicacionModelo {
    private static final long serialVersionUID = 1L;
    
    public JComponenteAplicacion() {
    }
    /**
     * Creates a new instance of JComponenteAplicacion
     * @param psNombre Nombre
     * @param psCaption Caption
     * @param psImageCamino Camino de la imagen
     * @param poEjecutar Objeto que ejecuta la acción
     */
    public JComponenteAplicacion(String psTipo,String psNombre, String psCaption, String psImageCamino, ActionListenerCZ poEjecutar) {
        this(psTipo,psNombre, psCaption, psImageCamino, poEjecutar, new Rectangulo(160, 25), null);
    }

    public JComponenteAplicacion(String psTipo, String psNombre, String psCaption, String psImageCamino, ActionListenerCZ poEjecutar, Rectangulo poDimension, Object poIcon) {
        this(psTipo,psNombre, psCaption, psImageCamino, poEjecutar, poDimension, poIcon, 2);
    }
    /**
     * Creates a new instance of JComponenteAplicacion
     * @param psNombre Nombre
     * @param psCaption Caption
     * @param psImageCamino Camino de la imagen
     * @param poEjecutar Objeto que ejecuta la acción sobre una fila
     * @param poEjecutarExtend Objeto que ejecuta la acción sobre multiples filas
     * @param poDimension Dimesion del boton
     * @param psGrupo Grupo del Boton
     */
    public JComponenteAplicacion(
            String psTipo, String psNombre, String psCaption,
            String psImageCamino, ActionListenerCZ poEjecutar, Rectangulo poDimension,
            Object poIcon,
            int plHorizontalTextAlignment) {
        super(psTipo, psNombre, psCaption, poEjecutar, poDimension, poIcon, plHorizontalTextAlignment);
    }

}
