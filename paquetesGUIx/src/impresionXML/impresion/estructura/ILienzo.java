/*
 * ILienzo.java
 *
 * Created on 13 de septiembre de 2004, 9:32
 */

package impresionXML.impresion.estructura;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

/**
 *Interfaz lienzo, siempre las unidades en cm
 */
public interface ILienzo extends Serializable {
    
    /**
     * Establece el nuevo area de impresi�n, siempre CM
     * @param poR X=margen Iz, Y=Margen Sup. Width=ancho(Indep. de margenes), height=alto(Indep. de margenes)
     */
    public void setAreaImpresion(Rectangle2D poR);
    /**
     * Devuelve el area de impresi�n actual
     * @return area de impresi�n
     */
    public Rectangle2D getAreaImpresion();
    /**
     * Devuelve el area de impresi�n original
     * @return Area de imrpesi�n
     */
    public Rectangle2D getAreaImpresionOriginal();
    /**
     * restaura el area de impresi�n de la pagina
     */
    public void restaurarAreaImpresionPagina();
    /**
     * Devuelve Ult. posicion imprimida
     * @return ult. posici�n
     */
    public Point2D getUltPosicionImprimida();

    /**
     * Imprime el texto, unidades siempre en cm
     * @return si ha tenido �xito
     */
    public boolean imprimirTexto(JParamTextoLibre poParam);

    /**
     * Imprime una linea
     * @param poPunto1 punto 1
     * @param poPunto2 punto 2
     * @param poEstiloLinea Estilo linea
     * @return si ha tenido �xito
     */
    public boolean imprimirLinea(
        Point2D poPunto1, Point2D poPunto2, 
        JEstiloLinea poEstiloLinea);
    
    /**
     * Imprime una imagen
     * @param poImage Imagen
     * @param poPosicionDestino x,y, ancho y alto en el destino
     * @return si ha tenido �xito
     */
    public boolean imprimirImagen(Image poImage, Rectangle2D poPosicionDestino);
    
}
