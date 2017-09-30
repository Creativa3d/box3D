/*
 * Interface.java
 *
 * Created on 7 de septiembre de 2004, 10:43
 */

package utilesGUIx.formsGenericos;

import utiles.IListaElementos;

/**Iterfaz que cumplen los paneles genericos*/
public interface IPanelGenerico {
    /**
     *refresca los datos del formulario
     */
    public void refrescar()throws Exception;
    /**
     *selecciona todos los datos
     */
    public void seleccionarTodo();
    /**
     *selecciona/deselecciona una fila
     */
    public void seleccionarFila(int plFila, boolean pbSeleccionar);
    /**
     * devuelve el obj. configurador de columnas
     */
    public ITablaConfig getTablaConfig();
    /**
     * Devuelve la lista de botones IBotonRelaccionado
     */
    public IListaElementos getBotones();
    /**
     * Devuelve el panel de informacion, se suele usar para presentar informacion personalizada
     */
    public Object getPanelInformacion();
    /**
     * Add un listener, para "escuchar" cuando se mueven las filas, se refresca...
     * @param poListener
     */
    public void addListenerIPanelGenerico(IPanelGenericoListener poListener);
    /**
     * Borra un listener, para "escuchar" cuando se mueven las filas, se refresca...
     * @param poListener
     */
    public void removeListenerIPanelGenerico(IPanelGenericoListener poListener);
    
    /**Devuelve las filas seleccionadas*/
    public int[] getSelectedRows();
    
}
