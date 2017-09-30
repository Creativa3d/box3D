/*
 * JNodoArbol.java
 *
 * Created on 4 de mayo de 2006, 19:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.cliente;

import archivosPorWeb.comun.JFichero;

public class JArchivosNodoArbol {
    
    public JFichero moFichero;
    
    /** Creates a new instance of JNodoArbol */
    public JArchivosNodoArbol(JFichero poFichero) {
        moFichero=poFichero;
    }
    public String toString() {
        return moFichero.getNombre();
    }
    
}
