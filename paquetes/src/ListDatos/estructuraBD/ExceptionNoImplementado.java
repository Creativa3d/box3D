/*
 * ExceptionNoImplementado.java
 *
 * Created on 11 de agosto de 2005, 12:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package ListDatos.estructuraBD;

public class ExceptionNoImplementado extends java.lang.Exception {
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new instance of <code>ExceptionNoImplementado</code> without detail message.
     */
    public ExceptionNoImplementado() {
        this("No implementado");
    }

    /**
     * Constructs an instance of <code>ExceptionNoImplementado</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ExceptionNoImplementado(String msg) {
        super(msg);
    }
}
