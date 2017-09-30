/*
 * IFilaCrear.java
 *
 * Created on 20 de marzo de 2007, 9:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos;

import java.io.Serializable;

public interface IFilaCrear extends Serializable {
    
    public IFilaDatos getFilaDatos(final String psTabla);
    
}
