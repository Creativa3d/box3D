/*
 * JEntradaComprimida.java
 *
 * Created on 28 de abril de 2005, 17:00
 */

package utilesBD.servletAcciones;

import java.io.*;
import javax.servlet.http.HttpSession;
import utiles.JDepuracion;

import utiles.config.*;

public class AEntradaComprimida extends JAccionAbstract {
    private static final long serialVersionUID = 1L;
    public static final String mcsEntradaComprimida = "entradacomprimida";

    /**parametro del fichero de configuracion*/
    public static final String PARAMETRO_EntradaComprimida = "EntradaComprimida";
    
    /** Creates a new instance of JEntradaComprimida */
    public AEntradaComprimida() {
    }
    /**Devuelve si la entrada es comprimida*/
    public static boolean getEntradaComprimida(javax.servlet.http.HttpServletRequest request, JDatosGeneralesXML poDatos){
        boolean lbEntradaComprimida = false;
        Boolean lobEntradaComprimida = null;
        HttpSession loSesion = request.getSession(false);
        lobEntradaComprimida = (Boolean)loSesion.getAttribute(PARAMETRO_EntradaComprimida);
        if(lobEntradaComprimida==null){
            try{
                lbEntradaComprimida = (poDatos.getPropiedad(PARAMETRO_EntradaComprimida).compareTo("1")==0 ? true : false );
            }catch(Throwable e){
                poDatos.setPropiedad(PARAMETRO_EntradaComprimida, "0");
                try {
                    poDatos.guardarFichero();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(AEntradaComprimida.class.getName(), ex);
                }

            }
        }else{
            lbEntradaComprimida = lobEntradaComprimida.booleanValue();
        }

        return lbEntradaComprimida;
    }
    
    
    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer)  throws Exception{
        String lsEntradaComprimida = request.getParameter(PARAMETRO_EntradaComprimida);
        try{
            request.getSession(false).setAttribute(PARAMETRO_EntradaComprimida, Boolean.valueOf(lsEntradaComprimida));
            PrintWriter out = response.getWriter();
            out.println("1");
            out.close();
        }catch(Exception e){
            PrintWriter out = response.getWriter();
            out.println("0");
            out.close();
        }
        return null;
    }
    
    public boolean getNecesitaValidar(Usuario poUsuario) {
        return false;
    }

    public boolean getConexionEdicion() {
        return false;
    }
    public boolean getNecesitaConexionBD(){
        return false;
    }
    
    
}
