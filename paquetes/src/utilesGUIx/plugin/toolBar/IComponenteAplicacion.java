/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.plugin.toolBar;

import java.io.Serializable;
import java.util.HashMap;
import utiles.IListaElementos;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public interface IComponenteAplicacion extends Serializable, Cloneable {
    public static final String mcsGRUPOMENU = "GRUPOMENU";
    public static final String mcsGRUPOBASEINTERNO = "GRUPOBASEINTERNO";
    public static final String mcsGRUPOBASEDESKTOP = "GRUPOBASEDESKTOP";

    public static final String mcsTipoBOTON = "Boton";
    public static final String mcsTipoCMB = "Combo";
    public static final String mcsTipoLABEL = "Label";
    public static final String mcsTipoMENU = "MENU";

    public static final String mcsPropiedadCMBElementos = "CMBElementos";
    public static final String mcsPropiedadCMBItemStateChange = "CMBItemStateChange";
    /**
     * The central position in an area. Used for
     * both compass-direction constants (NORTH, etc.)
     * and box-orientation constants (TOP, etc.).
     */
    public static final int CENTER  = 0;

    //
    // Box-orientation constant used to specify locations in a box.
    //
    /**
     * Box-orientation constant used to specify the top of a box.
     */
    public static final int TOP     = 1;
    /**
     * Box-orientation constant used to specify the left side of a box.
     */
    public static final int LEFT    = 2;
    /**
     * Box-orientation constant used to specify the bottom of a box.
     */
    public static final int BOTTOM  = 3;
    /**
     * Box-orientation constant used to specify the right side of a box.
     */
    public static final int RIGHT   = 4;
    
    public String getCaption();
    public void setCaption(String ps);
    
    public String getNombre() ;
    public boolean isActivo();
    public void setActivo(boolean mbActivo);
    public Rectangulo getDimension();
    public void setDimension(Rectangulo poRect);
    public int getX();
    public void setX(int x);
    public int getY();
    public void setY(int y);
    public Object getIcono();
    public ActionListenerCZ getAccion();
    public void setAccion(ActionListenerCZ poEjecutar);
    /**
     * 3 VALORES 
     *  mcsGRUPOBASEINTERNO
     *  mcsGRUPOBASEDESKTOP
     *  mcsGRUPOMENU
     * @return 
     */
    public String getGrupoBase();
    
    public String getTipo();
    
    public IListaElementos getListaBotones();
    public IFormEdicion getEdicion();
    public Object clone() throws CloneNotSupportedException;
    public IComponenteAplicacion getComponente(String psGrupoBase, String psName_ActionComand);
    public IComponenteAplicacion getComponente(String psGrupoBase, String psName_ActionComand, boolean pbRecursivo);
    public void add(IComponenteAplicacion poParam);
    
    public HashMap getPropiedades();
    public void setPropiedades(HashMap po);
    
}
