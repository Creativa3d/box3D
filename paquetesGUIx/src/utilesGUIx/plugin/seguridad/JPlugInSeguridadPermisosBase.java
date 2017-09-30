/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import ListDatos.ECampoError;
import java.awt.Component;
import java.awt.Container;
import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import utiles.JDepuracion;

public class JPlugInSeguridadPermisosBase extends JPlugInSeguridadPermisosBaseModelo {
    public JPlugInSeguridadPermisosBase (){
        super();
    }


    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, String psObjeto, String psObjCaption, JMenu poMenu) throws ECampoError {
        for(int i = 0 ; i < poMenu.getMenuComponentCount(); i++){
            Component loComp = poMenu.getMenuComponent(i);
            if(loComp!=null){
                if(loComp instanceof JMenu){
                    addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, ((JMenu)loComp).getActionCommand());
                    rellenarPermisos(poPermisos, psObjeto, psObjCaption, ((JMenu)loComp));
                }else if(loComp instanceof JMenuItem){
                    addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, ((JMenuItem)loComp).getActionCommand());
                } else{
                    addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, loComp.getName());
                }
            }
        }
    }
    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, String psObjeto, String psObjCaption, JPopupMenu poMenuPop) throws ECampoError {
        for(int i = 0 ; i < poMenuPop.getComponentCount(); i++){
            Component loComp = poMenuPop.getComponent(i);
            if(loComp instanceof JMenu){
                addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, ((JMenu)loComp).getActionCommand());
                rellenarPermisos(poPermisos, psObjeto, psObjCaption, ((JMenu)loComp));
            }else if(loComp instanceof AbstractButton){
                addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, ((AbstractButton)loComp).getActionCommand());
            } else{
                JDepuracion.anadirTexto(JPlugInSeguridadPermisosBase.class.getName(), "Componente popup menu desconocido " + loComp.getClass().getName());
            }
        }        
    }
    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, String psObjeto, String psObjCaption, Container poContainer) throws ECampoError {
        for(int i = 0 ; i < poContainer.getComponentCount(); i++){
            Component loComp = poContainer.getComponent(i);
            if(loComp instanceof JMenu){
                addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, ((JMenu)loComp).getActionCommand());
                rellenarPermisos(poPermisos, psObjeto, psObjCaption, ((JMenu)loComp));
            }else if(loComp instanceof AbstractButton){
                addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, ((AbstractButton)loComp).getActionCommand());
            }else if(loComp instanceof Container){
                addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, loComp.getName());
                rellenarPermisos(poPermisos, psObjeto, psObjCaption, ((Container)loComp));
            }else {
                addPermisoSiAccion(poPermisos, psObjeto, psObjCaption, loComp.getName());
            }
        }        
    }
    


}
