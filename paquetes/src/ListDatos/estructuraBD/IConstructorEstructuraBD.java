/*
 * IEstructuraMetadatos.java
 *
 * Created on 20 de abril de 2005, 11:37
 */

package ListDatos.estructuraBD;

import java.io.Serializable;


public interface IConstructorEstructuraBD extends Serializable{
    public JTableDefs getTableDefs() throws Exception;
}
