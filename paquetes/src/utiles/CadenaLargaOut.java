/*
 * JWebInputStram.java
 *
 * Created on 3 de mayo de 2006, 18:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utiles;

import java.io.IOException;
import java.io.OutputStream;


public class CadenaLargaOut extends OutputStream {
    public static final int mclTamanoBuffer = 5 * 1024;//1kb
    public StringBuffer moStringBuffer;
    /** Creates a new instance of JWebInputStram */
    public CadenaLargaOut() {
        moStringBuffer = new StringBuffer(mclTamanoBuffer);
    }

    public void write(int b) throws IOException {
        moStringBuffer.append((char)b);
    }
    
    public void close() throws IOException {
//        System.gc();
    }

    public String toString() {
        return moStringBuffer.toString();
    }
    
}
