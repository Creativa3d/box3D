/*
 * Copiar.java
 *
 * Created on 26 de febrero de 2007, 18:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utiles;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**Objeto que pasa a memoria la cadena dada*/
public class Copiar implements ClipboardOwner {

    private static Copiar moSCopiar;
    
    static {
         moSCopiar = new Copiar();
    }
    
    public static Copiar getInstance(){
        return moSCopiar;
    }
    

    public Copiar() {
    }

    public void lostOwnership(final Clipboard clipboard, final Transferable contents) {
        //vacio a posta
    }

    public void setClip(final String psTexto) {
        Clipboard loClip;
        if (psTexto != null) {
            if (psTexto.length() > 0) {
                loClip = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection contents = new StringSelection(psTexto);
                loClip.setContents(contents, this);
            }
        }
    }

    public String getClipboardString() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception ex) {
                //highly unlikely since we are using a standard DataFlavor
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return result;
    }
}
