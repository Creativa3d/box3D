/*
 * IBusqueda.java
 *
 * Created on 16 de julio de 2002, 20:14
 */

package ListDatos;

/**
 * 
 *Interfaz para recuperacion de datos, generalmente se usa para una recuperacion asincrona 
 */
import java.util.EventListener;

/**
 * Interfaz para recuperacion de datos, generalmente se usa para una recuperacion
 * asincrona
 */
public interface IBusquedaListener extends EventListener{
    /**
     * metodo que notifica que ha terminado la consulta del servidor
     * @param e evento
     */
    public void recuperacionDatosTerminada(BusquedaEvent e);
}
