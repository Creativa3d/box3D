/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesSincronizacion.sincronizacion.conflictos;

import utilesSincronizacion.sincronizacion.JSincronizar;

/**
 *
 * @author eduardo
 */
public interface IFormConflictos {
        public void setDatos(JListaElementosConflictos poConflictos, JSincronizar poSincronizacion)throws Exception;
        public void setVisible(boolean pbVisible);
    
}
