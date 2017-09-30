/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.plugin.toolBar;


import java.awt.Dimension;
import java.awt.event.ActionListener;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;


public class JComponenteAplicacionGrupo extends JComponenteAplicacionGrupoModelo {
    private static final long serialVersionUID = 1L;

    public JComponenteAplicacionGrupo(){
    }
    public JComponenteAplicacionGrupo(String psGrupoBase, String psNombre, int plx,int plY, Dimension poDimension){
        super(psGrupoBase, psNombre, plx, plY, new Rectangulo(poDimension.width, poDimension.height ));
    }
    public JComponenteAplicacionGrupo(String psGrupoBase, String psNombre){
        super(psGrupoBase, psNombre, 0, 0, new Rectangulo(0, 0));
    }
    public JComponenteAplicacionGrupo(String psGrupoBase, String psNombre, int plx,int plY, Rectangulo poDimension){
        super(psGrupoBase, psNombre, plx, plY, poDimension);
    }

    @Override
    public IFormEdicion getEdicion() {
        JPanelPropiedadesCompApl loP = new JPanelPropiedadesCompApl();
        loP.setDatos(this);
        return loP;
    }
    public void setAccion(ActionListener poEjecutar){
    }
    
    
}
