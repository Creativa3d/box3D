/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

import java.util.EventListener;

/**
 *
 * @author eduardo
 */
public interface IListDatosMoverListener  extends EventListener {
    /**
     * listener de eventos de filtrado de datos
     * @param poDatos JListDatos
     */
    public void filtrado(JListDatos poDatos);
    /**
     * listener de eventos de mover datos, se llama despues de que el JListDatos haya movido
     * @param plIndexActual Indice de la posicion de los datos
     * @param poDatos JListDatos
     */
    public void moverDatos(int plIndexActual, JListDatos poDatos);

    /**
     * listener de eventos de mover datos, se llama antes de que el JListDatos haya movido
     * @param plIndexActual Indice de la posicion de los datos
     * @param plIndexFuturo Indice de la posicion de los datos en el que se va a mover
     * @param poDatos JListDatos
     */
    public void moverDatosAntes(int plIndexActual, int plIndexFuturo, JListDatos poDatos);
    
}
