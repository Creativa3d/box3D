

package utilesEjecutar.ejecutar;

import utiles.IListaElementos;
import utiles.xml.sax.JAtributos;


public interface IEjecutarInstruccion {
    /**
     * Devuelve el nombre de la instruccion a ejecutar
     */
    public String getNombre()throws Throwable;
    /**
     * Establece los atributos e hijos del xml
     */
    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable;
    /**
     * ejecuta la instruccion, devuelve verdadero si hace algo
     * el parametro ficticia sirve para indicar q no realize la accion, solo la simule
     */
    public boolean ejecutar() throws Throwable;

    /**
     * hace una simulacion de la ejecucion, devuelve verdadero si hace alguna actualizacion
     */
    public boolean ejecutarFicticio() throws Throwable;

    /**
     * Indica si la accion esta habilitada
     */
    public boolean isHabilitada() throws Throwable;

}
