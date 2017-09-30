/*
 * JRespuesta.java
 *
 * Created on 14 de agosto de 2005, 9:38
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesGUIx.automatas;

/**
 *
 * @author chema
 */
public class JRespuesta {
    
    private int mnCodAutomata;
    private String msNombreAutomata;
    private int mnAccion;
    private String[] msParametros;
    
    /** Creates a new instance of JRespuesta 
     * pnCodAut -> Es el código asignado al automata.
     * psNomAut -> Es el nombre del automata.
     * pnAccion -> Identifica la acción devuelta por el automata
     * psParametros -> Son los parametros de la acción
     */
    public JRespuesta(int pnCodAut,String psNomAut,int pnAccion,String[] psParametros) {
        mnCodAutomata = pnCodAut;
        msNombreAutomata = psNomAut;
        mnAccion = pnAccion;
        msParametros = psParametros;
    }
    
    public String getNombreAutomata() {
        return msNombreAutomata;
    }
    
    public int getCodigoAutomata() {
        return mnCodAutomata;
    }
    
    public int getAccion() {
        return mnAccion;
    }
    
    public String getParametro(int key) {
        return msParametros[key];
    }
}
