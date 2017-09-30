/*
 * ABajarFichero.java
 *
 * Created on 2 de mayo de 2006, 17:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.servletAcciones;

import archivosPorWeb.comun.JWebComunicacion;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesBD.servletAcciones.*;

public class ABajarFichero  extends JAccionAbstract   {
    
    /** Creates a new instance of ABajarFichero */
    public ABajarFichero() {
    }
    
    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer) throws Exception {
        JWebComunicacion loComu = null;
        GZIPOutputStream gzipout = null;
        PrintWriter out = null;
        boolean lbEntradaComprimida = false;
        try{
            JDatosGeneralesXML loDatosXML = (JDatosGeneralesXML) poServletContext.getAttribute(JDatosGeneralesXML.class.getName());
            lbEntradaComprimida = AEntradaComprimida.getEntradaComprimida(request, loDatosXML);
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        
        try{
            UsuarioFicheros loUsuario = (UsuarioFicheros) request.getSession(false).getAttribute("Usuario");

            //recogemos los datos de entrada
            ObjectInputStream entrada = null;
            if(lbEntradaComprimida){
                entrada = new ObjectInputStream(new GZIPInputStream(request.getInputStream()));
            }else{
                entrada = new ObjectInputStream(request.getInputStream());
            }
            Object loObject = entrada.readObject();
            loComu = (JWebComunicacion)loObject;
            loComu.moFichero.setPathRaiz(loUsuario.msRuta);

            //procesamos
            try{
                loComu.moFichero = JWebComun.getServidorArchivos().getFichero(loComu.moFichero);
                loComu.cargarArchivo(JWebComun.getServidorArchivos().getFlujoEntrada(loComu.moFichero), loComu.moFichero.getLenght());
            }catch(Exception e){
                e.printStackTrace();
                loComu = new JWebComunicacion();
                loComu.mbBien = false;
                loComu.msMensaje = e.toString();
            }
            
            //devolvemos el objeto
            JWebComun.devolverResultado(response,lbEntradaComprimida,loComu);
            
         }catch(Exception e){
            e.printStackTrace();
            loComu = new JWebComunicacion();
            loComu.mbBien=false;
            loComu.msMensaje=e.toString();
            JWebComun.devolverResultado(response,lbEntradaComprimida,loComu);
         }finally{
            //vacioamos el buffer de salida
            if (out!=null) {
                out.flush();out.close();
            }
            if (gzipout!=null) {
                gzipout.close();
            }
        }
        return null;
    }
    
    public boolean getNecesitaValidar(Usuario poUsuario) {
        return true;
    }
    
    public boolean getConexionEdicion() {
        return false;
    }
    public boolean getNecesitaConexionBD(){
        return false;
    }
}
