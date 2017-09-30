/*
 * JAccionArchivos.java
 *
 * Created on 5 de mayo de 2006, 8:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.cliente;

import archivosPorWeb.comun.IServidorArchivos;
import archivosPorWeb.comun.JFichero;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JArchivosClienteAccion {
    public static final int mclCopiar=0;
    public static final int mclCortar=1;
    public static final int mclPegar=2;
    
    public int mlAccion;
    public IListaElementos<JFichero> moElementos;
    public IServidorArchivos moServidor;
    
    public JArchivosClienteAccion(IServidorArchivos poServidor) {
        moServidor = poServidor;
        moElementos=new JListaElementos();
    }
    /** Creates a new instance of JAccionArchivos
     * @param poServidor
     * @param plAccion
     * @param poElementos */
    public JArchivosClienteAccion(IServidorArchivos poServidor,int plAccion, IListaElementos<JFichero> poElementos) {
        moServidor = poServidor;
        mlAccion = plAccion;
        moElementos = poElementos;
    }
    
    
}
