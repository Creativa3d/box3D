/*
 * JWebInputStram.java
 *
 * Created on 3 de mayo de 2006, 18:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;


public class JWebOutputStream extends OutputStream {
    private static final long serialVersionUID = 1110004L;
    public static final int mclTamanoBuffer = 2 * 1024 * 1024;//mb
    private JWebComunicacion moComu;
    private JServidorArchivosWeb moServidor;
    int mlPosi = 0;
    private boolean mbAppend=false;
    /** Creates a new instance of JWebInputStram */
    public JWebOutputStream(JServidorArchivosWeb poServidor, JWebComunicacion poComu, boolean pbAppend) {
        moComu = poComu;
        moComu.moArchivo = new byte[mclTamanoBuffer]; 
        moServidor = poServidor;
        mbAppend=pbAppend;
        if(mbAppend) {
            moComu.mlTrozo=1;
        }
    }

    private void subirArchivo() throws IOException{
        JWebComunicacion loComu;
        URLConnection loConec;
        try{
            //si la posicion es menor q el tamaño maximo
            //es q es el ultimo trozo
            if(mlPosi<mclTamanoBuffer){
                byte[] loAux = new byte[mlPosi];
                System.arraycopy(
                        moComu.moArchivo, 0, 
                        loAux, 0, 
                        mlPosi);
                moComu.moArchivo = loAux;
            }
            //mandamos la peticion al servidor 
            loConec = moServidor.enviarObjeto(moServidor.mcsSubirfichero, moComu);
            //recogemos la respuesta del servidor
            loComu = (JWebComunicacion)moServidor.recibirObjeto(loConec);
            if(!loComu.mbBien){
                throw new Exception(loComu.msMensaje);
            }
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }
    
    public void write(int b) throws IOException {
        if(mlPosi>=mclTamanoBuffer){
            subirArchivo();
            moComu.mlTrozo++;
            mlPosi=0;
        }
        moComu.moArchivo[mlPosi] = (byte)b;
        mlPosi++;
    }
    
    public void close() throws IOException {
        if(mlPosi>0){
            subirArchivo();
        }
        moComu=null;
        moServidor=null;
        System.gc();
    }
}
