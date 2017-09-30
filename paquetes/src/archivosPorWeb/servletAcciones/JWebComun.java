/*
 * JWebComun.java
 *
 * Created on 4 de mayo de 2006, 17:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.servletAcciones;

import archivosPorWeb.comun.JServidorArchivos;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import utilesBD.servletAcciones.AEntradaComprimida;

public class JWebComun {
//    public static final String mcsEsComprimido="EntradaComprimidaArchivos";
//    private static boolean mbEsComprimido=false;
//    private static boolean mbEsComprimidoIni=false;
    private static JServidorArchivos moServidorArchivos=null;
    
    /** Creates a new instance of JWebComun */
    private JWebComun() {
    }
    
//    public static boolean getEsComprimido(javax.servlet.http.HttpServletRequest request){
////        if(!mbEsComprimidoIni){
////            try{
////                mbEsComprimido = Boolean.valueOf(ConfigurationParametersManager.getParametro(mcsEsComprimido)).booleanValue();
////            }catch(Exception e){
////                e.printStackTrace();
////            }
////            mbEsComprimidoIni=true;
////        }
////        return mbEsComprimido;
////
//        String lsEsComprimido = (String) request.getSession(false).getAttribute(AEntradaComprimida.PARAMETRO_EntradaComprimida);
//        boolean lbEsComprimido = lsEsComprimido != null && lsEsComprimido.equals("1");
//        return lbEsComprimido;
//    }
    
    public static JServidorArchivos getServidorArchivos(){
        if(moServidorArchivos==null){
            moServidorArchivos = new JServidorArchivos();
        }
        return moServidorArchivos;
    }
    /**
     * Devuelve el resultado al cliente
     * @param response respuesta servlet
     * @param pbEsComprimido si se devuelve comprimido
     * @param poResultado objeto resultado a devolver
     * @throws ServletException error
     * @throws IOException error
     */
    protected static void devolverResultado(HttpServletResponse response, boolean pbEsComprimido, Object poResultado)  throws ServletException, IOException {
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
    
}
