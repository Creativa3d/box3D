/*
 * JCopiarObjetos.java
 *
 * Created on 6 de marzo de 2007, 12:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utiles;

import java.awt.datatransfer.*;
import utiles.JDepuracion;
import utiles.JDepuracion;
import utiles.JDepuracion;


public class JCopiarObjetos implements Transferable, ClipboardOwner {
    
    private final DataFlavor moDataFlavorObjeto;
    private final DataFlavor [] moFlavorsSoportados;
    private Object moObjeto;
    
    public JCopiarObjetos(Object poObjeto) {
        this(poObjeto.getClass());
        moObjeto=poObjeto;
    }
    public JCopiarObjetos(Class poClass) {
        moDataFlavorObjeto = new DataFlavor(poClass, poClass.getName());
        moFlavorsSoportados= new DataFlavor []{moDataFlavorObjeto};
    }
    public void setObjeto(Object poObjeto){
        moObjeto=poObjeto;
    }
    public synchronized DataFlavor [] getTransferDataFlavors() {
        return moFlavorsSoportados;
    }
    public boolean isDataFlavorSupported(final DataFlavor parFlavor) {
        return parFlavor.equals(moDataFlavorObjeto);
    }
    public synchronized Object getTransferData(final DataFlavor parFlavor) throws UnsupportedFlavorException {
        Object loObjeto=null;
        if (parFlavor.equals(moDataFlavorObjeto)){
            loObjeto = moObjeto;
        } else{
            throw new UnsupportedFlavorException(moDataFlavorObjeto);
        }
        return loObjeto;
        
    }
    
    public void lostOwnership(final Clipboard parClipboard, final Transferable parTransferable) {
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), "Lost ownership");
    }
}
