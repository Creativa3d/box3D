/*
 * JAccionDevolver.java
 *
 * Created on 22 de noviembre de 2004, 8:41
 */

package archivosPorWeb.servletAcciones;
import archivosPorWeb.comun.JServidorArchivosWeb;


import utilesBD.servletAcciones.*;

public class JAccionDevolver extends utilesBD.servletAcciones.JAccionDevolver {
    private static final long serialVersionUID = 1L;
    
    /** Creates a new instance of JAccionDevolver */
    public JAccionDevolver() {
    }
    
    @Override
    public IAccion getAccion(String psAccion, String psDir) {
        psAccion=psAccion+".ctrl";
        if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsFichero))
            return new AFicheroPropiedades(AFicheroPropiedades.mclPropiedades);
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsDirectorio))
            return new AFicheroPropiedades(AFicheroPropiedades.mclListaFicheros);
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsCopiar))
            return new AFicheroPropiedades(AFicheroPropiedades.mclCopiar);
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsMover))
            return new AFicheroPropiedades(AFicheroPropiedades.mclMover);
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsBorrar))
            return new AFicheroPropiedades(AFicheroPropiedades.mclBorrar);
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsCrearCarpeta))
            return new AFicheroPropiedades(AFicheroPropiedades.mclCrearCarpeta);
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsBajarfichero))
            return new ABajarFichero();
        else if(psAccion.equalsIgnoreCase(JServidorArchivosWeb.mcsSubirfichero))
            return new ASubirFichero();
        else if(psAccion.equalsIgnoreCase("login.ctrl"))
            return new ALogin(psDir);
        else if(psAccion.equalsIgnoreCase("loginAplicacion.ctrl"))
            return new ALoginAplicacion(psDir);
        else if(psAccion.equalsIgnoreCase("entradacomprimida.ctrl")){
            return new AEntradaComprimida();
        }
        return null;
    }
}
