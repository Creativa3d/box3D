/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos;

import java.util.Comparator;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JOrdenacionConj  implements Comparator<IFilaDatos> {
    private static final long serialVersionUID = 1L;
    
    private IListaElementos moLista = new JListaElementos();

    public void add(final Comparator<IFilaDatos> poOrd){
        moLista.add(poOrd);
    }

    public void add(JListDatos poList, int plOrden, boolean pbAscendente){
        add(poList,new int[]{plOrden}, pbAscendente);
    }

    public void add(JListDatos poList, int[] plOrden, boolean pbAscendente){
        moLista.add(poList.crearOrdenacion(plOrden, pbAscendente));
    }

    public int compare(IFilaDatos o1, IFilaDatos o2) {
        int lResult = JOrdenacion.mclIgual;
        for(int i = 0 ; i < moLista.size() && lResult == JOrdenacion.mclIgual; i++){
            Comparator<IFilaDatos> loOrd = (Comparator<IFilaDatos>) moLista.get(i);
            lResult = loOrd.compare(o1, o2);
        }
        return lResult;
    }

}
