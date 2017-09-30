/*
 * FaltaConfiguracionParametroException.java
 *
 * Created on 25 de agosto de 2003, 9:48
 */


package utiles.config;
/**Exception cuando no existe el parametro*/
public class FaltaConfiguracionParametroException extends Exception {
    
    /**
     * Creates a new instance of FaltaConfiguracionParametroException
     * @param psNombre nombre del parametro
     */
    public FaltaConfiguracionParametroException(String psNombre) { 
        super("Falta parametro de configuracion " + psNombre);
    }
    
    
}
