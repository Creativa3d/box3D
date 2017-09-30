/*
 * JLinea.java
 *
 * Created on 25 de enero de 2007, 8:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.motorImpresion;

import impresionXML.impresion.estructura.ILienzo;
import impresionXML.impresion.estructura.ILinea;
import impresionXML.impresion.estructura.JEstiloLinea;
import java.awt.geom.Point2D;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JLinea implements ILinea {
    private static final long serialVersionUID = 1L;
    private final IListaElementos moList;
    
    /** Creates a new instance of JLinea */
    public JLinea() {
        super();
        moList = new JListaElementos();
    }
    

    public void insertarLinea(final Point2D poPunto1, final Point2D poPunto2, final JEstiloLinea poEstiloLinea) {
        moList.add(new CLinea(poPunto1, poPunto2, poEstiloLinea));
    }

    public boolean imprimir(final ILienzo poLienzo, final OutputStream poOut) {
        Iterator loEnum = moList.iterator();
        boolean lbImprimido = true;
        for(;loEnum.hasNext();){
            CLinea loLinea = (CLinea)loEnum.next();
            lbImprimido &= poLienzo.imprimirLinea(
                    loLinea.moPunto1, 
                    loLinea.moPunto2,
                    loLinea.moEstiloLinea);
        }
        return lbImprimido;
    }
    
}

class CLinea implements Serializable {
    Point2D moPunto1;
    Point2D moPunto2;
    JEstiloLinea moEstiloLinea;
    
    public CLinea(final Point2D poPunto1, final Point2D poPunto2, final JEstiloLinea poEstiloLinea){
        moPunto1= poPunto1;
        moPunto2=poPunto2;
        moEstiloLinea=poEstiloLinea;
    }
}
