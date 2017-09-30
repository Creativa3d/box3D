/*
 * INotificaciones.java
 *
 * Created on 3 de mayo de 2005, 9:26
 */

package colaTareas;

/**Notifica al cliente los eventos*/
public interface INotificaciones {

    
    public void error(Exception e);
    
    public void finalizado(Thread poThread, ITarea poTarea);
}
