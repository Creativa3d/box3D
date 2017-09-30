/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.avisos;

/**
 *
 * @author eduardo
 */
public interface IAvisoListener {
    public static enum tiposAvisoListener{
        addAviso, removeAviso
    }
    
    public void avisoPerformed(tiposAvisoListener plTipo,JAviso poAviso, JAvisosConj poConj);
}
