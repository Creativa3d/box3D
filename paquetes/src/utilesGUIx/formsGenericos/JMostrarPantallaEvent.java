/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;
/**
 *
 * @author eduardo
 */
public class JMostrarPantallaEvent {
    private static final long serialVersionUID = 3333331L;
    public static final int mclAbrirAntes = 0;
    public static final int mclAbrirDespues = 1;
    public static final int mclCerrarDespues = 2;
    
    private JMostrarPantallaParam moDatos;
    private final Object moComp;
    private int mlAccion;
    protected transient Object  source;

    public JMostrarPantallaEvent(final IMostrarPantalla source, int plAccion, final Object poComp,final JMostrarPantallaParam poDatos) {
        this.source=source;
        moComp=poComp;
        moDatos=poDatos;
        mlAccion=plAccion;
    }
    /**
     * The object on which the Event initially occurred.
     *
     * @return   The object on which the Event initially occurred.
     */
    public Object getSource() {
        return source;
    }

    /**
     * Returns a String representation of this EventObject.
     *
     * @return  A a String representation of this EventObject.
     */
    public String toString() {
        return getClass().getName() + "[source=" + source + "]";
    }

    /**
     * Devuelve los parametros con los que se ha abierto el componente
     * @return Los parametros con los que se ha abierto el componente
     */
    public JMostrarPantallaParam getParam(){
        return moDatos;
    }

    /**
     * Devuelve el componente que ha modificado el estado
     * @return el componente
     */
    public Object getComp() {
        return moComp;
    }

    /**
     * @return la accion realizada con el componente/Param
     */
    public int getAccion() {
        return mlAccion;
    }
    
    
}
