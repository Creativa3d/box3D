/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos;

import java.io.Serializable;
import java.util.Comparator;
import utiles.IListaElementos;


public interface IListDatosOrdenacionHacer extends Serializable {
     public void ordenar(int[] palPunteros, Comparator<IFilaDatos> poOrdenacion,
                          IListaElementos poList );
}
