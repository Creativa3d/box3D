/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos.config;

import java.util.ResourceBundle;
import utiles.config.JLectorFicherosParametros;


public class JTextosFuente implements ITextosFuente {
    private ResourceBundle moTextos=null;
    private JLectorFicherosParametros moTextos2=null;
    
    /** Creates a new instance of JDevolverTextos */
    public JTextosFuente(ResourceBundle poTextos) {
        moTextos = poTextos;
    }
    public JTextosFuente(JLectorFicherosParametros poTextos2) {
        moTextos2= poTextos2;
    }
    public String getString(String psKey){
        String lsValor="";
        try{
            if(moTextos==null){
               lsValor = moTextos2.getParametro(JDevolverTextos.getKeySinRaros(psKey));
            }else{
               lsValor = moTextos.getString(JDevolverTextos.getKeySinRaros(psKey));
            }
        }catch(Exception e){}
        return lsValor;
    }
}
