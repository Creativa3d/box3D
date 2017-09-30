/*
 * JListaElementosIterator.java
 *
 * Created on 13 de enero de 2005, 18:29
 */

package utiles;
import java.util.Iterator;
/**
 * itera sobre la lista de elementos
 */
public class JListaElementosIterator implements Iterator {
    private static final long serialVersionUID = 33333327L;
    JListaElementos moLista;
    int mlIndex = -1;
    /**
     * Creates a new instance of JListaElementosIterator
     * @param poLista lista de elementos inicial
     */
    public JListaElementosIterator(JListaElementos poLista) {
        moLista = poLista;
    }

    public boolean hasNext() {
        return ((mlIndex+1) < moLista.size());
    }
    

    public Object next() {
        mlIndex++;
        return moLista.get(mlIndex);
    }

    public void remove() {
        moLista.remove(mlIndex);
        mlIndex--;
    }    

}
