/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archivosPorWeb.servletAcciones;

import utilesBD.servletAcciones.Usuario;

public class UsuarioFicheros extends Usuario{
    public String msRuta;
    
    public UsuarioFicheros(String psNombre,int plCodigo,int plPermisos, String psRuta){
        super(psNombre, plCodigo, plPermisos);
        msRuta = psRuta;
    }
    
    public UsuarioFicheros(String psLogin, String psRuta){
        super(psLogin, 0, 10);
        msRuta = psRuta;
    }

}
