/*
 * JComunicacion.java
 *
 * Created on 3 de mayo de 2006, 17:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import java.io.InputStream;
import java.io.Serializable;

import utiles.IListaElementos;

public class JWebComunicacion implements Serializable {
    private static final long serialVersionUID = 1110002L;
    final int mlTamanoMax = (1024*1024*1);//1 mb
    public JFichero moFichero;
    public JFichero moFicheroDestino;
    public byte[] moArchivo=null;
    public String msMensaje=null;
    public boolean mbBien=true;
    public IListaElementos moListaArchivos=null;
    public boolean mbAMedio=false;
    public int mlTrozo=0;
    /** Creates a new instance of JComunicacion */
    public JWebComunicacion() {
    }
    public int getDesplazamientoBase(){
        return mlTrozo * mlTamanoMax;
    }
    public void cargarArchivo(InputStream poIn, long plLen) throws Exception {
        if(plLen<mlTamanoMax){
            moArchivo = new byte[(int)plLen];
            poIn.read(moArchivo);
            poIn.close();
            mbAMedio=false;
        }else{
            //creamos el array de datos
            int loff = getDesplazamientoBase();
            byte[] loAux=null;
            if((plLen-loff) >  mlTamanoMax){
                moArchivo = new byte[mlTamanoMax];
                loAux=moArchivo;
                mbAMedio = true;
            }else{
                moArchivo = new byte[(int)plLen-loff];
                loAux = new byte[mlTamanoMax];
                mbAMedio = false;
            }
            //leemos los trozos previos
            for(int i =0; i<mlTrozo;i++){
                poIn.read(loAux);
            }
            //leemos el array real
            loff = poIn.read(moArchivo, 0, moArchivo.length);
            //comprobamos la long leida
            if(loff!=moArchivo.length){
                System.out.println("Error al leer el archivo");
            }
        }
        poIn.close();
    }
    
}
