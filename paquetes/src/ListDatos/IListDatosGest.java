/*
 * IListDatosGest.java
 *
 * Created on 16 de julio de 2002, 20:14
 */
package ListDatos;

import java.util.EventListener;

interface IListDatosGest  extends EventListener {

   /**
   * Notificacion de que se ha anadido una fila a JList
   * por lo que se anade el puntero a esa fila a la lista de punteros
   */
    void addFilaDatos(int plIndex, IFilaDatos poFila) throws Exception ;
  /**
   * Notificacion de que se ha borrado una fila de JList desde otro JListDatos
   * y se rehace el orden y el filtro pq todos los punteros a partir de ese han cambiado
   */
    void removeFilaDatos(int plIndex, IFilaDatos poFila) throws Exception ;

}
