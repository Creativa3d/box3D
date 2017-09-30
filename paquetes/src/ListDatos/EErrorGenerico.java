/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

/**
 *
 * @author eduardo
 */
public class EErrorGenerico extends Exception {
    
    public EErrorGenerico(Exception ex) {
        super(ex.toString());

    }
    public EErrorGenerico(String psMensaje) {
        super(psMensaje);
    }
    
}
