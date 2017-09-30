/*
 * IListaElementos.java
 *
 * Created on 13 de enero de 2005, 19:38
 */

package utiles;

import java.io.Serializable;
import java.util.List;

/**Interfaz que cumple las lista de elementos*/
public interface IListaElementos<E> extends Serializable, Cloneable, List<E> {
    
}
