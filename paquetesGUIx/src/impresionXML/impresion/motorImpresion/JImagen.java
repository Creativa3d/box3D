/*
 * JImagen.java
 *
 * Created on 21 de septiembre de 2004, 13:04
 */

package impresionXML.impresion.motorImpresion;

import impresionXML.impresion.estructura.*;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Iterator;
import utiles.*;

/**Objeto imagen*/
public class JImagen implements IImagen {
    private static final long serialVersionUID = 1L;
    private final IListaElementos moList;
    
    /** Creates a new instance of JImagen */
    public JImagen() {
        super();
        moList = new JListaElementos();
    }
    
    public void insertarImagen(final Image poImagen, final Rectangle2D poPosicionDestino){
        moList.add(new CImagen(poImagen, poPosicionDestino));
    }
    
    public boolean imprimir(final ILienzo poLienzo, final java.io.OutputStream poOut) {
        Iterator loEnum = moList.iterator();
        boolean lbImprimido = true;
        for(;loEnum.hasNext();){
            CImagen loImage = (CImagen)loEnum.next();
            lbImprimido &= poLienzo.imprimirImagen(loImage.moImagen, loImage.moPosicionDestino);
        }
        return lbImprimido;
    }
}

class CImagen implements Serializable {
    public Image moImagen;
    public Rectangle2D moPosicionDestino;
    
    public CImagen(Image poImagen, Rectangle2D poPosicionDestino){
        moImagen = poImagen;
        moPosicionDestino = poPosicionDestino;
        
    }
}