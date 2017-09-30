/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.servletAcciones;

import ListDatos.IResultado;
import ListDatos.ISelectEjecutarComprimido;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class JAccionAbstract implements IAccion {

    public boolean getNecesitaValidar(Usuario poUsuario) {
        return true;
    }

    public boolean getConexionEdicion() {
        return false;
    }

    public boolean getNecesitaConexionBD() {
        return true;
    }
    /**
     * Devuelve el resultado al cliente
     * @param response respuesta servlet
     * @param pbEsComprimido si se devuelve comprimido
     * @param poResultado objeto resultado a devolver
     * @throws ServletException error
     * @throws IOException error
     */
    protected void devolverResultado(HttpServletResponse response, boolean pbEsComprimido, IResultado poResultado)  throws ServletException, IOException {
        response.setContentType("application/x-java-serialized-object");
        ObjectOutputStream salida = null;
        GZIPOutputStream gzipout = null;
        try{
            if(pbEsComprimido){
                gzipout = new GZIPOutputStream(response.getOutputStream());
                salida = new ObjectOutputStream(gzipout);
            }else{
                salida = new ObjectOutputStream(response.getOutputStream());
            }
            salida.writeObject(poResultado);
            salida.flush();
            if(gzipout != null) {
                gzipout.flush();
            }
        }finally{
            if(gzipout != null) {
                gzipout.close();
            }
            if(salida != null) {
                salida.close();
            }
        }
    }
    /**
     * Devuelve el objeto actualizar/otros
     * @param request peticion servlet
     * @param pbEntradaZIP si es entrada zip
     * @throws Exception error
     * @return objeto update a ejecutar
     */
    public ISelectEjecutarComprimido getUpdateWeb(HttpServletRequest request, boolean pbEntradaZIP) throws Exception {
        ISelectEjecutarComprimido loActu = null;
        //recogemos los datos a actualizar
        ObjectInputStream entrada = null;
        if(pbEntradaZIP){
            entrada = new ObjectInputStream(new GZIPInputStream(request.getInputStream()));
        }else{
            entrada = new ObjectInputStream(request.getInputStream());
        }
        loActu = (ISelectEjecutarComprimido)entrada.readObject();
        return loActu;

    }
}
