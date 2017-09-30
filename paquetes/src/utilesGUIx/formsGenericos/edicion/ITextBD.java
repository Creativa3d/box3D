/*
 * ITextBD.java
 *
 * Created on 10 de febrero de 2007, 10:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.edicion;

import ListDatos.ECampoError;

public interface ITextBD {
    public void mostrarDatosBD();
    public void establecerDatosBD() throws ECampoError;
}
