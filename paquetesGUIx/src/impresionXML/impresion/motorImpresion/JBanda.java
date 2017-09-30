/*
 * JBanda.java
 *
 * Created on 14 de septiembre de 2004, 13:37
 */

package impresionXML.impresion.motorImpresion;

import impresionXML.impresion.estructura.*;
import java.awt.geom.*;
import java.util.Iterator;
import utiles.*;

/**Objeto banda*/
public class JBanda implements IBanda{
    private static final long serialVersionUID = 1L;
    
    private final IListaElementos moObjetos = new JListaElementos();
    
    /**Alto CM*/
    private float mdAlto = 0;
    /**si es autoextensible*/
    private boolean mbAutoExtensible = true;
    
    /**Constructor*/
    public JBanda() {
        super();
    }
    /**
     * Creates a new instance of JBanda
     * @param pdAlto alto CM
     * @param pbAutoExtensible si es autoextensible
     */
    public JBanda(float pdAlto, boolean pbAutoExtensible) {
        super();
        setAlto(pdAlto);
        setAutoExtensible(pbAutoExtensible);
    }
    
    public float getAlto() {
        return mdAlto;
    }
    
    public boolean getAutoExtensible() {
        return isAutoExtensible();
    }
    
    public boolean imprimir(final ILienzo poLienzo, final java.io.OutputStream poOut) {
        if(isAutoExtensible()){
            poLienzo.setAreaImpresion(
                new Rectangle2D.Float(
                    (float)poLienzo.getAreaImpresionOriginal().getX(),
                    (float)poLienzo.getUltPosicionImprimida().getY(),
                    (float)poLienzo.getAreaImpresionOriginal().getWidth(),
                    (float)poLienzo.getAreaImpresionOriginal().getHeight()
                    )
                );
        }else{
            poLienzo.setAreaImpresion(
                new Rectangle2D.Float(
                    (float)poLienzo.getAreaImpresionOriginal().getX(),
                    (float)poLienzo.getUltPosicionImprimida().getY(),
                    (float)poLienzo.getAreaImpresionOriginal().getWidth(),
                    (float)getAlto()
                    )
                );
        }
        Iterator loEnum = moObjetos.iterator();
        boolean lbImprimido = true;
        for(;loEnum.hasNext() && lbImprimido;){
            IImprimir loImp = (IImprimir)loEnum.next();
            lbImprimido = loImp.imprimir(poLienzo, poOut);
        }
        return lbImprimido;
    }
    
    public IBanda insertarBanda() {
        IBanda loBanda = new JBanda();
        moObjetos.add(loBanda);
        return loBanda;
    }
    
    public IImagen insertarImagen() {
        IImagen loImagen = new JImagen();
        moObjetos.add(loImagen);
        return loImagen;
    }
    
    public ILinea insertarLinea() {
        ILinea loLinea = new JLinea();
        moObjetos.add(loLinea);
        return loLinea;
    }
    
    public IParrafo insertarParrafo() {
        return null;
    }
    public ITable insertarTabla() {
        return null;
    }
    public ITextoLibre insertarTexto() {
        ITextoLibre loTexto = new JTextoLibre();
        moObjetos.add(loTexto);
        return loTexto;
    }

    public void setAlto(final float pdAlto) {
        this.mdAlto = pdAlto;
    }

    public boolean isAutoExtensible() {
        return mbAutoExtensible;
    }

    public void setAutoExtensible(final boolean pbAutoExtensible) {
        this.mbAutoExtensible = pbAutoExtensible;
    }
}
