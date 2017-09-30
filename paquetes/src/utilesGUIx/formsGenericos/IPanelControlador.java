/*
 * IPanelGenerico.java
 *
 * Created on 7 de septiembre de 2004, 8:54
 */

package utilesGUIx.formsGenericos;

import ListDatos.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

/**Interfaz para usar el form. de listado general*/
public interface IPanelControlador {
    /** 
     * Devuelve la consulta terminada por getDatos(), 
     * este metodo no debe refrescar la consulta
     * @return 
     */
    public IConsulta getConsulta();
    /**
     * Add un nuevo registro
     * @throws Exception error
     */
    public void anadir() throws Exception;
    
    /**
     * pone los valores por defecto al editar o Add
     * @param poTabla
     * @throws Exception error
     */
    public void valoresDefecto(JSTabla poTabla) throws Exception;
    /**
     * Borra un nuevo registro
     * @param plIndex Posicion de la tabla a borrar
     * @throws Exception error
     */
    public void borrar(int plIndex) throws Exception;
    /**
     * Edita un registro
     * @param plIndex indice del reg. en tabla
     * @throws Exception error
     */
    public void editar(int plIndex) throws Exception;
    
    /**
     * devuelve el JListDatos, refrescando el listdatos
     * @throws Exception error 
     * @return datos
     */
    public JListDatos getDatos()  throws Exception ;
    
    
    /**
     * Vuelve a recuperar los datos
     * @throws Exception error
     */
    public void refrescar() throws Exception;
    /**
     * devuelve el panel
     * @return poPanel Interfaz que cumple el IPanelGenerico
     */
    public IPanelGenerico getPanel();
    /**
     * establecemos el panel
     * @param poPanel Interfaz que cumple el JPanelGenerico para refrescar los datos 
     */
    public void setPanel(IPanelGenerico poPanel);
    /**
     * indicamos que se ha actualizado el datos desde un form . de edicion
     * @param poFila fila
     * @throws Exception error
     */
    public void datosactualizados(IFilaDatos poFila) throws Exception ;
    /**mostrar el form principal
     * @throws java.lang.Exception
     */
    public void mostrarFormPrinci() throws Exception;
    /**
     * establece los indice seleccionados
     * @param plIndex indices de los datos
     * @throws ListDatos.EBookmarkIncorrecto
     */
    public void setIndexs(int[] plIndex) throws EBookmarkIncorrecto;
    /**
     * devuelve los indices seleccionados
     * @return 
     */
    public int[] getIndexs();
    /**
     * devuelve el indice seleccionado, -1 si no hay ninguno o cancelar
     * @return 
     */
    public int getIndex();
    

    /**Devuelve los parametros de inicializacio
     * @return n*/
    public JPanelGeneralParametros getParametros();
        
}
