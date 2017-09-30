/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.servletAcciones;

import ListDatos.IServerServidorDatos;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utiles.FechaMalException;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;

public class AListaPropiedadesHTTP extends JAccionAbstract  {
    private static final long serialVersionUID = 1L;
    public static final String mcslistaFicherosPropiedades = "listaFicherosPropiedades";
    public static final String mcslistaFicherosPropiedadesServlet = mcslistaFicherosPropiedades+".ctrl";

    private final String msDir;

    public AListaPropiedadesHTTP(String psDir){
        msDir = psDir;
    }

    public String ejecutar(HttpServletRequest request, HttpServletResponse response, ServletContext poServletContext, IServerServidorDatos poServer) throws Exception {
        ObjectInputStream entrada = null;
        entrada = new ObjectInputStream(request.getInputStream());
        IListaElementos loLista = (IListaElementos) entrada.readObject();
        JDepuracion.anadirTexto(getClass().getName(), "Lista archivos de tamaño:" + loLista.size());
        IListaElementos loResult = new JListaElementos();

        File loRaiz = new File(msDir);
        for(int i = 0 ; i < loLista.size(); i++){
            String lsNombre=(String) loLista.get(i);
            if(lsNombre.indexOf("subdirectorio=")==0){
                String lsSub = lsNombre.substring("subdirectorio=".length());
                loRaiz = new File(loRaiz, lsSub);
                JDepuracion.anadirTexto(getClass().getName(), "subdirectorio="+lsSub);
            }else{
                File loFile = new File(loRaiz, lsNombre);
                if(loFile.exists()){
                    JDepuracion.anadirTexto(getClass().getName(), "Encontrado="+loFile.getAbsolutePath());
                    String[] lasFila = new String[3];
                    lasFila[0] = lsNombre;
                    lasFila[1] = getFechaFichero(loFile.lastModified()).toString();
                    lasFila[2] = String.valueOf(loFile.length());
                    loResult.add(lasFila);
                } else {
                    JDepuracion.anadirTexto(getClass().getName(), "No existe="+loFile.getAbsolutePath());
                }
            }
        }
        devolverResultado(response, false, loResult);
        return null;
    }
    /**
     * Devuelve el resultado al cliente
     * @param response respuesta servlet
     * @param pbEsComprimido si se devuelve comprimido
     * @param poResultado objeto resultado a devolver
     * @throws ServletException error
     * @throws IOException error
     */
    protected void devolverResultado(HttpServletResponse response, boolean pbEsComprimido, IListaElementos poResultado)  throws ServletException, IOException {
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




    public boolean getNecesitaValidar(Usuario poUsuario) {
        return false;
    }

    public boolean getConexionEdicion() {
        return false;
    }

    public boolean getNecesitaConexionBD() {
        return false;
    }

    public static JDateEdu getFechaFichero(long pdtime) throws FechaMalException{
        JDateEdu loDate = new JDateEdu("1/1/70 1:0:0");
        loDate.add(loDate.mclSegundos, (int)(pdtime/1000));
        return loDate;
    }

}
