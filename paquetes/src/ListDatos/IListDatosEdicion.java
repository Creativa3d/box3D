/*
 * IListDatosEdicion.java
 *
 * Created on 15 de diciembre de 2004, 10:22
 */

package ListDatos;

import java.util.EventListener;

/**
 * Interfaz que puede implementar el cliente de JListDatos, JListDatos notifica de cualquier edicion/borrado/nuevo de datos que tenga 
 *
 */
public interface IListDatosEdicion  extends EventListener {
    /**
     * listener de eventos de edicion de datos, se llama despues de que el JListDatos haya editado, anadido o borrado
     * @param plModo Modo edicion
     * @param plIndex Indice de la posicion de los datos
     * @param poDatos Fila de Datos
     */
    public void edicionDatos(int plModo, int plIndex, IFilaDatos poDatos);
    
    /**
     * listener de eventos de edicion de datos, Se llama antes de que el JListDatos edite, borre o anada un registro
     * @param plModo Modo de edicion
     * @param plIndex Indice de la posicion de los datos
     */
    public void edicionDatosAntes(int plModo, int plIndex) throws Exception;
    
}
