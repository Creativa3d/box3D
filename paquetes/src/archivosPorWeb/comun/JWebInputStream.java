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
import java.io.InputStream;


public class JWebInputStream extends InputStream {
    private static final long serialVersionUID = 1110003L;
    private JWebComunicacion moComu;
    private JServidorArchivosWeb moServidor;
    int mlPosi=0;
    /** Creates a new instance of JWebInputStram */
    public JWebInputStream(JServidorArchivosWeb poServidor, JWebComunicacion poComu) {
        moComu=poComu;
        moServidor=poServidor;
    }
    private int getPosiGeneral(){
        return moComu.getDesplazamientoBase() + mlPosi;
    }
    public int read() throws IOException {
        int lReturn = -1;
        if(getPosiGeneral()<moComu.moArchivo.length){
            lReturn=moComu.moArchivo[mlPosi];
            mlPosi++;
        }else{
            if(moComu.mbAMedio){
                bajarSiguienteTrozo();
                lReturn = read();
            }
        }
        return lReturn;
    }
    private void bajarSiguienteTrozo() throws IOException {
        moComu.mlTrozo++;
        mlPosi=0;
        try{
            moComu = moServidor.getFlujoEntradaComu(moComu);
        }catch(Exception e){
            throw new IOException(e.toString());
        }
    }
    public int read(byte[] palBytes) throws IOException {
        int lReturn = -1;
        if(getPosiGeneral()<moComu.moFichero.getLenght()){
            int lDespl=0;
            boolean lbAMedio = moComu.mbAMedio;
            while(lbAMedio && (lReturn < palBytes.length ) || (lReturn==-1)){
                if((mlPosi+palBytes.length+lDespl) >= moComu.moArchivo.length){
                    System.arraycopy(moComu.moArchivo, mlPosi, palBytes,lDespl,moComu.moArchivo.length-mlPosi);
                    lReturn = moComu.moArchivo.length-mlPosi+lDespl;
                    lbAMedio=false;
                    if(moComu.mbAMedio){
                        lDespl+=(moComu.moArchivo.length-mlPosi);
                        bajarSiguienteTrozo();
                        lbAMedio=true;
                    }else{
                        mlPosi+=(moComu.moArchivo.length-mlPosi-lDespl);
                    }
                    
                }else{
                    System.arraycopy(moComu.moArchivo, mlPosi, palBytes,lDespl,palBytes.length-lDespl);
                    lReturn = palBytes.length;
                    mlPosi+=palBytes.length-lDespl;
                }
            }
        }
        return lReturn;
    }
    public int read(byte[] palBytes, int off, int len) throws IOException {
        throw new InternalError("No implementado");
    }
    
//    public int length(){
//        int lLen = -1;
//        if(moComu.moArchivo!=null){
//            lLen = moComu.moArchivo.length;
//        }
//        return lLen;
//    }
    
    public void close() throws IOException {
        moComu=null;
        moServidor=null;
        System.gc();
    }

    
    
}
