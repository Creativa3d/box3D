/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.servletAcciones;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utiles.JDepuracion;

/**
 *
 * @author eduardo
 */
public class JServletListener  implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            IAccionDevolver loACC = (IAccionDevolver) sce.getServletContext().getAttribute(IAccionDevolver.class.getName());
            if(loACC!=null){
                loACC.terminar(sce.getServletContext());
            }
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

}
