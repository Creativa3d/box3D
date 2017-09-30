/*
 * IComponentParaTabla.java
 *
 * Created on 5 de abril de 2004, 11:41
 */

package utilesGUI.tabla;

/**Interfaz que debe cumplir el componente a asignar en la tabla*/
public interface IComponentParaTabla {
    /**
     * Establece el valor y el valor original al componente
     * @param poValor valor
     * @throws Exception error
     */
    public void setValueTabla(Object poValor) throws Exception ;
    

    /**
     * Devuelve el valor actual del componente
     * @return valor
     */
    public Object getValueTabla();
    /**
     * Si el valor a cambiado con respecto al valor original
     * @return si ha cambiado
     */
    public boolean getTextoCambiado();

}
