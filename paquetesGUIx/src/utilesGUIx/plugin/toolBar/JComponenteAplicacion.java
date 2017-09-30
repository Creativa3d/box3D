/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.toolBar;


import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import utiles.IListaElementos;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ActionListenerWrapper;
import utilesGUIx.JGUIxConfigGlobal;
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
    public JComponenteAplicacion(String psTipo,String psNombre, String psCaption, String psImageCamino, ActionListener poEjecutar) {
        this(psTipo,psNombre, psCaption, psImageCamino, poEjecutar, new Dimension(160, 25), null);
    }

    public JComponenteAplicacion(String psTipo, String psNombre, String psCaption, String psImageCamino, ActionListener poEjecutar, Dimension poRectangulo, Icon poIcon) {
        this(psTipo,psNombre, psCaption, psImageCamino, poEjecutar, poRectangulo, poIcon, 2);
    }
    public JComponenteAplicacion(JMenuItem poMenuI) {
        this(mcsTipoBOTON,poMenuI.getActionCommand()
                , poMenuI.getText(), null, poMenuI.getActionListeners()[0], new Dimension(160, 25), poMenuI.getIcon(), 
                SwingConstants.RIGHT);
    }
    /**
     * Creates a new instance of JComponenteAplicacion
     * @param psNombre Nombre
     * @param psCaption Caption
     * @param psImageCamino Camino de la imagen
     * @param poEjecutar Objeto que ejecuta la acción sobre una fila
     * @param poEjecutarExtend Objeto que ejecuta la acción sobre multiples filas
     * @param poRectangulo Dimesion del boton
     * @param psGrupo Grupo del Boton
     */
    public JComponenteAplicacion(
            String psTipo, String psNombre, String psCaption,
            String psImageCamino, ActionListener poEjecutar, Dimension poRectangulo,
            Icon poIcon,
            int plHorizontalTextAlignment) {
        super(psTipo, psNombre, psCaption
                , new ActionListenerWrapper(poEjecutar)
                , poRectangulo ==null ? null : new Rectangulo(poRectangulo.width, poRectangulo.height)
                , null, plHorizontalTextAlignment);
        Icon loIcon = poIcon;
        if(loIcon==null && psImageCamino!= null && !psImageCamino.equals("")){
            loIcon=(Icon) JGUIxConfigGlobal.getInstancia().getCargarIcono().cargarIcono(psImageCamino, poEjecutar, null);
        }
        setIcono(loIcon);
    }
    
    public JComponenteAplicacion(
        String psTipo, String psNombre, String psCaption,
        ActionListenerCZ poEjecutar, Rectangulo poRectangulo,
        Object poIcon,
        int plHorizontalTextAlignment) {
        super(psTipo, psNombre, psCaption, poEjecutar, poRectangulo, poIcon, plHorizontalTextAlignment);
    }


    public void setAccion(ActionListener poEjecutar){
        super.setAccion(new ActionListenerWrapper(poEjecutar));
    }
    public void setIcon(ImageIcon poimageIcon) {
        super.setIcono(poimageIcon);
    }
    public Icon getIcono() {
        return (Icon) super.getIcono();
    }

    public IListaElementos getListaBotones() {
        return null;
    }

    public IFormEdicion getEdicion() {
        JPanelPropiedadesCompApl loP = new JPanelPropiedadesCompApl();
        loP.setDatos(this);
        return loP;
    }
}
