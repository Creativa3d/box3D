/*
 * AFicheroPropiedades.java
 *
 * Created on 3 de mayo de 2006, 16:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.servletAcciones;

import archivosPorWeb.comun.JFichero;
import archivosPorWeb.comun.JWebComunicacion;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


import utiles.*;
import utiles.config.JDatosGeneralesXML;
import utilesBD.servletAcciones.*;

public class AFicheroPropiedades extends JAccionAbstract   {
    public static final int mclPropiedades = 0;
    public static final int mclListaFicheros = 1;
    public static final int mclMover = 2;
    public static final int mclCopiar = 3;
    public static final int mclBorrar = 4;
    public static final int mclCrearCarpeta = 5;
    int mlOperacion;
    /** Creates a new instance of ABajarFichero
     * @param plOperacion */
    public AFicheroPropiedades(int plOperacion) {
        mlOperacion = plOperacion;
    }
    
    @Override
    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer) throws Exception {
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
            //procesamos
            JFichero loFichero = null;
            JWebComunicacion loComu=null;
            switch(mlOperacion){
                case mclCopiar:
                    loComu = (JWebComunicacion)loObject;
                    loComu.moFichero.setPathRaiz(loUsuario.msRuta);
                    loComu.moFicheroDestino.setPathRaiz(loUsuario.msRuta);
                    JWebComun.getServidorArchivos().copiarA(
                            JWebComun.getServidorArchivos(),
                            JWebComun.getServidorArchivos().getFichero(loComu.moFichero),
                            JWebComun.getServidorArchivos().getFichero(loComu.moFicheroDestino));
                    loComu = new JWebComunicacion();
                    loComu.mbBien = true;
                    break;
                case mclMover:
                    loComu = (JWebComunicacion)loObject;
                    loComu.moFichero.setPathRaiz(loUsuario.msRuta);
                    loComu.moFicheroDestino.setPathRaiz(loUsuario.msRuta);
                    JWebComun.getServidorArchivos().mover(
                            JWebComun.getServidorArchivos(),
                            JWebComun.getServidorArchivos().getFichero(loComu.moFichero),
                            JWebComun.getServidorArchivos().getFichero(loComu.moFicheroDestino));
                    loComu = new JWebComunicacion();
                    loComu.mbBien = true;
                    break;
                case mclListaFicheros:
                    loFichero = (JFichero)loObject;
                    loFichero.setPathRaiz(loUsuario.msRuta);
                    IListaElementos loLista = JWebComun.getServidorArchivos().getListaFicheros(JWebComun.getServidorArchivos().getFichero(loFichero));
                    loComu = new JWebComunicacion();
                    loComu.moListaArchivos=loLista;
                    loComu.mbBien = true;
                    break;
                case mclBorrar:
                    loFichero = (JFichero)loObject;
                    loFichero.setPathRaiz(loUsuario.msRuta);
                    JWebComun.getServidorArchivos().borrar(JWebComun.getServidorArchivos().getFichero(loFichero));
                    loComu = new JWebComunicacion();
                    loComu.mbBien = true;
                    break;
                case mclPropiedades:
                    loFichero = (JFichero)loObject;
                    loFichero.setPathRaiz(loUsuario.msRuta);
                    loFichero = JWebComun.getServidorArchivos().getFichero(loFichero);
                    loComu = new JWebComunicacion();
                    loComu.moFichero=loFichero;
                    loComu.mbBien = true;
                    break;
                case mclCrearCarpeta:
                    loFichero = (JFichero)loObject;
                    loFichero.setPathRaiz(loUsuario.msRuta);
                    JWebComun.getServidorArchivos().crearCarpeta(JWebComun.getServidorArchivos().getFichero(loFichero));
                    loComu = new JWebComunicacion();
                    loComu.moFichero=loFichero;
                    loComu.mbBien = true;
                    break;
                default:
                    loComu = new JWebComunicacion();
                    loComu.msMensaje="Opción incorrecta";
                    loComu.mbBien = false;
            }
            
            //devolvemos el objeto
            JWebComun.devolverResultado(response,lbEntradaComprimida,loComu);
            
         }catch(Throwable e){
            System.out.println(getClass().getName() + "ERROR");
            e.printStackTrace();
            JWebComunicacion loComu = new JWebComunicacion();
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
