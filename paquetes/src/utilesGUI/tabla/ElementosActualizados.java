/*
 * ElementosActualizados.java
 *
 * Created on 6 de abril de 2004, 13:18
 */

package utilesGUI.tabla;

import java.util.Iterator;
import utiles.*;
/**Lista de elementos actualizados*/
public class ElementosActualizados  implements java.io.Serializable{
    /**
     *Colección de celdas actualizadas
     */
    private final JListaElementos moList = new JListaElementos();
    /**
     *Colección de filas con alguna celda modificada
     */
    private final JListaElementos moFilas = new JListaElementos();
    

    /**
     * Buscamos si ya se ha añadido la celda a la colecccion de actualizaciones si ya se ha añadido se borra
     * se añade el nuevo elemento
     * Si la celda pertenece a una fila nueva se añade a la coleccion de filas actualizadas
     * @param poElemento elemento actualizado
     */
    public void add(ElementoActualizado poElemento){
        Iterator loEnum = moList.iterator();
        ElementoActualizado loElemento = null;
        ElementoActualizado loElementoEncon = null;
        boolean lbEncon = false;
        boolean lbEnconFila = false;
        for(;loEnum.hasNext() && !lbEncon;){
            loElemento = (ElementoActualizado)loEnum.next();
            lbEncon = (loElemento.comparePosicion(poElemento)==0);
            if (lbEncon) {
                loElementoEncon = loElemento;
            }
            if (loElemento.mlFila == poElemento.mlFila) {
                lbEnconFila = true;
            }
        }
        if (lbEncon) {
            moList.remove(loElementoEncon);
        }
        if (!lbEnconFila) {
            moFilas.add(new Integer(poElemento.mlFila));
        }
        moList.add(poElemento);
   }
    /**
     * Devuelve una coleción de filas con alguna celda actualizada
     * @return colección de filas
     */
   public IListaElementos getFilas() {
       return moFilas;
   }
   /**
    * Devuelve una colección de celdas actualizadas pertenecientes a la fila
    * pasada por parámetro
    * @return Lista de elementos que han cambiado valores
    * @param plFila fila
    */
   public IListaElementos getCeldasFila(int plFila){
        JListaElementos loCeldas = new JListaElementos();
        Iterator loEnum = moList.iterator();
        ElementoActualizado loElemento = null;
        for(;loEnum.hasNext();){
            loElemento = (ElementoActualizado)loEnum.next();
            if(loElemento.mlFila == plFila){
                loCeldas.add(loElemento);
            }
        }
        return loCeldas;
    }
   /**
    *borramos todas las actualizaciones apuntadas
    */
   public void clear(){
        moList.clear();
        moFilas.clear();
   }
   /**
    * Devuelve un iterador
    * @return iterador
    */
   public Iterator iterator(){
       return moList.iterator();
   }
   /**
    * devuelve si hay celdas actualizadas
    * @return si hay celdas actualizadas
    */
   public boolean isHayCeldasActualizadas(){
       return moList.size()>0;
   }
}
