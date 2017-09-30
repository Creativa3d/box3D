/*
 * ChangePaginaEvent.java
 *
 * Created on 27 de noviembre de 2003, 13:32
 */

package utilesGUI.TabControl;

import java.util.EventObject;

/**Objeto con las propiedades del cambio de p�gina*/
public class ChangePaginaEvent  extends EventObject {
    private JPagina moPagina;
    /**
     * Creates a new instance of ChangePaginaEvent
     * @param poComponente componente
     */
    public ChangePaginaEvent(Object poComponente) {
        super(poComponente);
    }
    /**
     * Constructor
     * @param poComponente componente padre
     * @param poPagina P�gina
     */
    public ChangePaginaEvent(Object poComponente, JPagina poPagina) {
        this(poComponente);
        moPagina = poPagina;
    }
    /**
     * Devuelve la p�gina
     * @return p�gina
     */
    public JPagina getPagina(){
        return moPagina;
    }
    
}
