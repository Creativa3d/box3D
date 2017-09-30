/*
 * BusquedaEvent.java
 *
 * Created on 21 de octubre de 2003, 3:01
 */

package ListDatos;


import java.util.EventObject;
/**
 * Evento que devuelve el objeto JListDatos, resultado de la consulta con el servidor 
 */
public final class BusquedaEvent extends EventObject{
    private static final long serialVersionUID = 3333331L;
    /** Objeto JListDatos resultado de la busqueda */
    public JListDatos moDatos;
    /** Objeto de uso libre  */
    public Object moLibre;
    /** Si ha habido algun error  */
    public boolean mbError = false;
    /** Objeto Error en caso de que lo haya */
    public Exception moError = null;
    
    /**
     * Crea una instancia de BusquedaEvent 
     * @param source objeto que lanza el evento
     * @param poDatos Objeto asociado
     */
    public BusquedaEvent(final Object source,final JListDatos poDatos) {
        super(source);
        moDatos=poDatos;
    }

}
