/*
 * AGuardarDatps.java
 *
 * Created on 9 de septiembre de 2004, 8:57
 */
package utilesBD.servletAcciones;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.*;

import javax.servlet.*;
import javax.servlet.http.*;


import ListDatos.*;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;

/**Ejecuta acciones guardar/otros en funcion de la entrada de un objeto serializado*/
public class AGuardarDatos extends JAccionAbstract  {
    public static final String mcsDIR = "DIR";
    public static final String mcsguardardatos = "guardardatos";
    
    private static final long serialVersionUID = 1L;
    protected JDatosGeneralesXML moDatosXML;
    private String msDir="";
    
    public AGuardarDatos(){
    }
    public AGuardarDatos(String psDir){
        msDir=psDir;
    }

    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer) throws Exception {
        moDatosXML = (JDatosGeneralesXML) poServletContext.getAttribute(JDatosGeneralesXML.class.getName());
        boolean lbEntradaComprimida = AEntradaComprimida.getEntradaComprimida(request, moDatosXML);
        procesarEntrada(request, response, lbEntradaComprimida, poServer);
        return null;
    }

    public boolean getNecesitaValidar(Usuario poUsuario) {
        return true;
    }

    /**
     * procesa la entrada
     * @param request peticion servlet
     * @param response respuesta servlet
     * @param pbEntradaZIP si es entrada zip
     * @throws ServletException error
     * @throws IOException error
     */
    public void procesarEntrada(HttpServletRequest request, HttpServletResponse response, boolean pbEntradaZIP, ListDatos.IServerServidorDatos poServer) throws Exception {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        IResultado loResultado = null;
        ISelectEjecutarComprimido loActu = null;
        try {
            loActu = getUpdateWeb(request, pbEntradaZIP);

            //actualizamos los datos
            if (loActu != null) {
                loResultado = procesar(loActu, poServer);
            }
        } catch (Throwable e) {
            loResultado = new JResultado(new JFilaDatosDefecto(""), "", "En Servidor=" + e.toString(), false, -1);
            JDepuracion.anadirTexto(this.getClass().getName(), e);
        }

        //devuelve el resultado
        devolverResultado(response, ((pbEntradaZIP) || (loActu.getComprimido())), loResultado);
    }



    /**
     * procesa el objeto loActu
     * @param loActu objeto actualizar a procesar
     * @return resultado
     */
    protected IResultado procesar(Object loActu, ListDatos.IServerServidorDatos poServer) throws Throwable {
        return procesarAct(loActu, poServer, msDir);
    }

    /**
     * procesamos el actualizador
     * @param loActu Objeto que cumple ISelectEjecutarUpdate
     * @return resultado
     */
    public static IResultado procesarAct(Object loActu, ListDatos.IServerServidorDatos poServer, String psDir) throws Throwable {
        if (IServerEjecutar.class.isAssignableFrom(loActu.getClass())) {
            ((IServerEjecutar)loActu).getParametros().addParametro(mcsDIR, psDir);
        } 
        return procesarAct(loActu, poServer);

    }
    /**
     * procesamos el actualizador
     * @param loActu Objeto que cumple ISelectEjecutarUpdate
     * @return resultado
     */
    public static IResultado procesarAct(Object loActu, ListDatos.IServerServidorDatos poServer) throws Throwable {
        IResultado loResultado = null;

        if (JActualizar.class.isAssignableFrom(loActu.getClass())) {
            //ejecutamos el actualizador
            loResultado = poServer.actualizar("", ((JActualizar) loActu));
        } else {
            if (IServerEjecutar.class.isAssignableFrom(loActu.getClass())) {
                loResultado = poServer.ejecutarServer((IServerEjecutar) loActu);
            } else {
                if (ISelectEjecutarSelect.class.isAssignableFrom(loActu.getClass())) {
                    loResultado = poServer.modificarEstructura((ISelectEjecutarSelect) loActu);
                } else {
                    throw new Exception("No cumple el interfaz IServerEjecutar, ni el ISelectEjecutarEstructura");
                }
            }
        }
        return loResultado;

    }

    public boolean getConexionEdicion() {
        return true;
    }

    public boolean getNecesitaConexionBD() {
        return true;
    }
}
