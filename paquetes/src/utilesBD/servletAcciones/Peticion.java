/*
 * Peticicion.java
 *
 * Created on 4 de mayo de 2004, 18:45
 */
package utilesBD.servletAcciones;

import java.io.Serializable;
import javax.servlet.http.*;

/**gestiona las peticiones a las acciones*/
public class Peticion implements Serializable {
    private static final long serialVersionUID = 1L;

    String msDir;
    HttpServletRequest request;
    IAccion moAccion;
    String msAccion;
    /**
     * Creates a new instance of Peticicion
     * @param poRequest objeto HttpServletRequest
     * @param psDir dir del servlet
     * @param poAccionDevolver interfaz lista de acciones
     */
    public Peticion(HttpServletRequest poRequest, String psDir, IAccionDevolver poAccionDevolver){
        msDir = psDir;
        request = poRequest;
        
        String uri = request.getRequestURI();
        int posIni = uri.lastIndexOf('/');
        int posFin = uri.lastIndexOf('.');
        
        msAccion = uri.substring(posIni + 1, posFin);
        
        moAccion = poAccionDevolver.getAccion(msAccion, psDir);
        
    }
    /**
     * si hay error de navegacion
     * @return si hay error
     */
    public boolean errorNavegacion(){
        return false;
    }
    /**
     * Devuelve si necesita validad
     * @param poUsuario usuario
     * @return si debe validar
     */
    public boolean necesitaValidar(Usuario poUsuario){
        boolean lbNecesita = false;
        if(moAccion.getNecesitaValidar(poUsuario)){
            lbNecesita = (poUsuario==null);
        }
        return lbNecesita;
    }
    /**
     * Devuelve la accion actual
     * @return accion
     */
    public IAccion getAccion(){
        return moAccion;
    }
    
}
