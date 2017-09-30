/*
 * JParamTextoLibre.java
 *
 * Created on 10 de febrero de 2008, 11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.estructura;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;


public class JParamTextoLibre implements Serializable{
    private static final long serialVersionUID = 1L;
    /**nombre*/
    public String msNombre;
    /**Texto a imprimir*/
    public String msTexto;
    /**x,y, ancho y alto */
    public Rectangle2D moPosicion;
    /**Fuente*/
    public Font moFuente;
    /**Si es multilinea*/
    public boolean mbMultilinea;
    /**Alineacion (mclAlineacionIzquierda ,mclAlineacionDerecha ,mclAlineacionCentro ,mclAlineacionJustificada)*/
    public int mlAlineacion;
    /**Color*/
    public Color moColor;
    /**Angulo*/
    public double mdAngulo;
    /**caso especial de fuente subrayada*/
    public boolean mbFuenteSubrayada=false;

    
    /**
     * @param psTexto Texto a imprimir
     * @param poPosicion x,y, ancho y alto
     * @param poFuente Fuente
     * @param pbMultilinea si es multilinea
     * @param plAlineacion tipo de justificación(mclAlineacionIzquierda ,mclAlineacionDerecha ,mclAlineacionCentro ,mclAlineacionJustificada)
     * @param poColor Color
     * @param pdAngulo Angulo del texto
    */
    public JParamTextoLibre(final String psNombre, final String psTexto, final Rectangle2D poPosicion, final Font poFuente, final boolean pbMultilinea, final int plAlineacion, final Color poColor, final double pdAngulo, final boolean pbFuenteSubrayada){
        msNombre = psNombre;
        msTexto = psTexto;
        moPosicion = poPosicion;
        moFuente = poFuente;
        mbMultilinea = pbMultilinea;
        mlAlineacion = plAlineacion;
        moColor = poColor;
        mdAngulo = pdAngulo;
        mbFuenteSubrayada = pbFuenteSubrayada;
    }
    /**
     * @param psTexto Texto a imprimir
     * @param poPosicion x,y, ancho y alto
     * @param poFuente Fuente
     * @param pbMultilinea si es multilinea
     * @param plAlineacion tipo de justificación(mclAlineacionIzquierda ,mclAlineacionDerecha ,mclAlineacionCentro ,mclAlineacionJustificada)
     * @param poColor Color
     * @param pdAngulo Angulo del texto
    */
    public JParamTextoLibre(final String psNombre, final String psTexto, final Rectangle2D poPosicion, final Font poFuente, final boolean pbMultilinea, final int plAlineacion, final Color poColor, final double pdAngulo){
        this(psNombre, psTexto, poPosicion, poFuente, pbMultilinea, plAlineacion,
             poColor, pdAngulo, false);
    }
    public JParamTextoLibre(final String psTexto, final Rectangle2D poPosicion, final Font poFuente, final boolean pbMultilinea, final int plAlineacion, final Color poColor, final double pdAngulo){
        this("", psTexto, poPosicion, poFuente, pbMultilinea, plAlineacion,
             poColor, pdAngulo, false);
    }
    public JParamTextoLibre(final String psTexto, final Rectangle2D poPosicion, final Font poFuente, final boolean pbMultilinea, final int plAlineacion, final Color poColor, final double pdAngulo, final boolean pbFuenteSubrayada){
        this("", psTexto, poPosicion, poFuente, pbMultilinea, plAlineacion,
             poColor, pdAngulo, pbFuenteSubrayada);
    }

    public boolean imprimir(final ILienzo poLienzo) {
        return poLienzo.imprimirTexto(this);
    }
}
