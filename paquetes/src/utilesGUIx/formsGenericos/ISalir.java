/*
 * ISalir.java
 *
 * Created on 13 de octubre de 2004, 10:29
 */

package utilesGUIx.formsGenericos;

/**Interfaz para que el componente hijo pueda salir*/
public interface ISalir {
    /**salir*/
    public void salir();
    /**Establece el título*/
    public void setTitle(String psTitulo);
}
