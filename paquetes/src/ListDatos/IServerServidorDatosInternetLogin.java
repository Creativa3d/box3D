/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

import java.io.Serializable;

/**
 *
 * @author eduardo
 */
public interface IServerServidorDatosInternetLogin extends Serializable{

    public void setServidorInternet(JServerServidorDatosInternet aThis);

    public boolean autentificar() throws Exception;
    
}
