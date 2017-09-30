/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos.estructuraBD;

import ListDatos.IFilaDatos;
import java.io.Serializable;

public interface IFieldDefCalculado extends Serializable {
    public String getValorCalculado();
    public String getValorCalculado(IFilaDatos poFila);
}
