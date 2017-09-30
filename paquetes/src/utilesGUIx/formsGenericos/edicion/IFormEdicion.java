/*
 * IFormBase.java
 *
 * Created on 10 de septiembre de 2004, 12:23
 */

package utilesGUIx.formsGenericos.edicion;

import ListDatos.JSTabla;
import utilesGUIx.Rectangulo;

/**Interfaz para usar el formulario edicion general*/
public interface IFormEdicion {
        
    public JSTabla getTabla();
    /**
     * boton aceptar
     * @throws Exception error
     */
    public void aceptar() throws Exception;
    /**
     * boton cancelar
     * @throws Exception error
     */
    public void cancelar() throws Exception;
    /**
     * rellenamos los combos y demas
     * @throws Exception error
     */
    public void rellenarPantalla() throws Exception;
    /**
     * mostramos los datos especificos de la tabla a editar
     * @throws Exception error
     */
    public void mostrarDatos() throws Exception;
    /**
     * establecemos los datos puestos por el usuario en la tabla a editar
     * @throws Exception error
     */
    public void establecerDatos() throws Exception;
    /**
     * habilita los campos segun el tipo de edicion(si es editar se desabilitan los campos clave)
     * Para saber si se esta en modo Add es
     * moTABLA.moList.getModoTabla()==JListDatos.mclNuevo
     * @throws Exception error
     */
    public void habilitarSegunEdicion() throws Exception;
    /**
     * pone el tipo de los textos
     * @throws Exception error
     */
    public void ponerTipoTextos() throws Exception;
    /**
     * Ancho, Alto
     * @return tamano
     */
    public Rectangulo getTanano();
    /**
     * Titulo
     * @return titulo
     */
    public String getTitulo();
    /**
     * Icono
     * @return 
     */
    public JFormEdicionParametros getParametros();
    
    /**
     * comprueba que los datos son correctos
     * @throws Exception error
     */
    public boolean validarDatos() throws Exception;

}
