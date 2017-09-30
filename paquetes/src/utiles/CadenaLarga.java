/*
 * CadenaLarga.java
 *
 * Created on 24 de noviembre de 2004, 8:09
 */

package utiles;
import java.io.*;

/**convierte una caena a un flujo de entrada*/
public class CadenaLarga extends java.io.InputStream{
    /**Cadena*/
    public String msCadena;
    /**Posicion del cursor*/
    int mlCursor = 0;
    
    /**
     * Constructor
     * @param psCadena cadena
     */
    public CadenaLarga(String psCadena){
        super();
        msCadena = psCadena;
    }
    /**Lee un caracter*/
    public int read() throws IOException{
        int l;
        if(mlCursor>=msCadena.length()){
            l = -1;
        }else{
            l = (int)(msCadena.substring(mlCursor, mlCursor+1).charAt(0));
            mlCursor++;
        }
        return l;
    }
    public int read(char b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (char)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (char)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }
    
    /**Devuelve cuantos caracteres quedan por leer*/
    public int available() throws IOException {
	return msCadena.length() - mlCursor;
    }
    public String toString() {
        return msCadena;
    }
    
}
