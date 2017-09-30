/*
 * Copiar.java
 *
 * Created on 26 de febrero de 2007, 18:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesFX;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;


/**Objeto que pasa a memoria la cadena dada*/
public class Copiar {

    private static Copiar moSCopiar;
    
    static {
         moSCopiar = new Copiar();
    }
    
    public static Copiar getInstance(){
        return moSCopiar;
    }
    

    public Copiar() {
    }

    public void setClip(final String psTexto) {
        if (psTexto != null) {
            if (psTexto.length() > 0) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(psTexto);
                clipboard.setContent(content);
            }
        }
    }

    public String getClipboardString() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        
        return clipboard.getString();
    }
}
