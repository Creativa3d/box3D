package ListDatos;

import java.util.Comparator;
import utiles.*;

/**
 * Ejecuta la ordenacion de un JListDatos 
 */
public final class JOrdenacionHacer implements IListDatosOrdenacionHacer {
    private static final long serialVersionUID = 1L;

    /**Constante ordenacion mergesort*/
    public static final int mclMergesort = 0;
    /**Constante ordenacion quicksort*/
    public static final int mclQuickSort = 1;
    private Comparator<IFilaDatos> moOrdenacion = null;
    private int[] malPunteros;
    private int mlTipo = mclMergesort;

    /**
     * Constructor
     * @param plTipo tipo ordenación mclMergesort, mclQuickSort
     */
    public JOrdenacionHacer(int plTipo) {

        mlTipo = plTipo;
    }

    /**
     * Ejecuta la ordenacion
     */
    public void ordenar(int[] palPunteros, Comparator<IFilaDatos> poOrdenacion,
            IListaElementos poList) {
        moOrdenacion = poOrdenacion;
        malPunteros = palPunteros;
        switch (mlTipo) {
            case mclMergesort:
                mergesort((int[]) malPunteros.clone(), malPunteros, 0,
                        malPunteros.length, poList);
                break;
            case mclQuickSort:
                //hacer la QuickSort
                mergesort((int[]) malPunteros.clone(), malPunteros, 0,
                        malPunteros.length, poList);
                break;
            default:
                mergesort((int[]) malPunteros.clone(), malPunteros, 0,
                        malPunteros.length, poList);
        }
    }

    //El numero de comparaciones varia entre n-1 y nlogn dependiendo del orden
    //inicial
    private void mergesort(int from[], int to[], int low, int high,
            IListaElementos poList) {
        if (high - low < 2) {
            return;
        }
        int middle = (low + high) / 2;
        mergesort(to, from, low, middle, poList);
        mergesort(to, from, middle, high, poList);

        int p = low;
        int q = middle;

        /* Para cada llamada recursiva vemos si los elmentos en este subconj.
        son ya ordenados. si es asi, no se necesitan mas coparaciones y el
        subarray puede ser copiado, El array debe ser copiado de modo que
        sea asignado en llamadas hijas en la recursion podria no sincronizarse(
        The array must be copied rather than assigned otherwise sister calls in
        the recursion might get out of sinc.)
        Cuando el numero de elementos es 3 ellos son particionadod para el primer conj.
        [low, mid], tiene un elemento y el segundo [mid, hight] tiene 2.
        We skip the optimisation when the number of elements is three or less as
        the first compare in the normal merge will produce the same
        sequence of steps. This optimisation seems to be worthwhile
        for partially ordered lists but some analysis is needed to
        find out how the performance drops to Nlog(N) as the initial
        order diminishes - it may drop very quickly.  */

        if ((high - low) >= 4
                && moOrdenacion.compare(
                (IFilaDatos) poList.get(from[middle - 1]),
                (IFilaDatos) poList.get(from[middle])) <= 0) {
            System.arraycopy(from, low, to, low, high - low);
            return;
        }

        // mezcla normal

        for (int i = low; i < high; i++) {
            if (q >= high
                    || (p < middle
                    && moOrdenacion.compare(
                    (IFilaDatos) poList.get(from[p]),
                    (IFilaDatos) poList.get(from[q])) <= 0)) {
                to[i] = from[p++];
            } else {
                to[i] = from[q++];
            }
        }
    }
}
