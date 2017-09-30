/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

import org.apache.commons.logging.Log;


public interface IDepuracionIMPL extends Log {
    public void anadirTexto(int plNivel, String psGrupo, Throwable e, Object poExtra);
    public void anadirTexto(int plNivel, String psGrupo, String psTexto, Object poExtra);

}
