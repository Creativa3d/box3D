/*
 * JSincronizarParam.java
 *
 * Created on 14 de octubre de 2008, 10:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion.sincronizacion;

import utilesSincronizacion.sincronizacion.conflictos.IFormConflictos;

public class JSincronizarParam {
    
    public boolean mbVisual = true;
    public boolean mbConflictosDefectoGanaCliente = true;
    public String msCorreo = "edouardo.gonzalez@snclavalin.com";
    public IFormConflictos moConflictos;
    
    
    /** Creates a new instance of JSincronizarParam */
    public JSincronizarParam(IFormConflictos poConflictos) {
        moConflictos = poConflictos;
    }
    
}
