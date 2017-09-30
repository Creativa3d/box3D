/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.motorImpresion.rompeLineas;

import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.estructura.JParamTextoLibre;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

public class LineBreakPanel {
    //Atributos
    private final String msParrafo;
    private final int mlAlineacion;
    private final Font moFuente;
    private final JParamTextoLibre moParam;

    public LineBreakPanel(final JParamTextoLibre poParam) {
        msParrafo = poParam.msTexto;
        mlAlineacion = poParam.mlAlineacion;
        moFuente = poParam.moFuente;
        moParam = poParam;
    }
    //pinta el texto y te devuelve el alto
    public float pintar(final Graphics g, final Rectangle2D size) {
        Graphics2D graphics2D = (Graphics2D) g;
        //cogemso el FontMetric para hacer calculos
        FontMetrics loMetric = g.getFontMetrics();

        float formatWidth = (float) size.getWidth();
        float drawPosY = (float) size.getY();

        boolean lbSePasa = false;

        RompeLineas loRompeLineas = new RompeLineas(msParrafo);

        int lLen = msParrafo.length();
        FontRenderContext loFontRender = null;

        while (loRompeLineas.mlComienzoNueva < lLen && !lbSePasa) {

            String lsLinea = loRompeLineas.getNuevaLinea(graphics2D, formatWidth);

            // Mueve la  coordinada y por el ascent de el layout.
            drawPosY += loMetric.getAscent();

            // Computa la posicion X.  si el parrafo es
            // right-to-left, nosotros queremos alinear el texto
            // al lado derecho.
            float drawPosX;
            switch(mlAlineacion){
                case ITextoLibre.mclAlineacionDerecha:
                    lsLinea = lsLinea.trim();
                    drawPosX = (float)size.getX() + formatWidth - loMetric.stringWidth(lsLinea);
                    break;
                case ITextoLibre.mclAlineacionCentro:
                    lsLinea = lsLinea.trim();
                    drawPosX = (float)size.getX() + (formatWidth - loMetric.stringWidth(lsLinea))/2;
                    break;
                case ITextoLibre.mclAlineacionJustificada:
                    if(loFontRender==null){
                        loFontRender = new FontRenderContext(null, true, true);
                    }
                    drawPosX = (float)size.getX();
                    break;
                default:
                    drawPosX = (float)size.getX();
                    break;
            }
            if(lsLinea!= null && !lsLinea.equals("")){
                AttributedString loAtrib = new AttributedString(lsLinea);
                loAtrib.addAttribute(TextAttribute.FONT, graphics2D.getFont());
                if(moParam.mbFuenteSubrayada){
                    loAtrib.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                }
                // Dibuja el TextLayout en la posicion (drawPosX, drawPosY).
                if(mlAlineacion == ITextoLibre.mclAlineacionJustificada &&
                   loRompeLineas.isHayMasLineas() &&
                   !loRompeLineas.isHayRetornoCarroUltLinea()){
                    TextLayout layout = new TextLayout(loAtrib.getIterator(), loFontRender);
                    TextLayout justifyLayout = layout.getJustifiedLayout((float) size.getWidth());
                     //--- Dibujamos el texto justificado
                     justifyLayout.draw(graphics2D, drawPosX, drawPosY);

                }else{

                    graphics2D.drawString(loAtrib.getIterator(), drawPosX, drawPosY);
                }
            }
            // Mueve la coordenada y para prepararse pàra el proximo Textlayout.
            drawPosY += loMetric.getDescent() + loMetric.getLeading();

            //devuelve -1 para indicar que no se ha imprimido todo el texto
            //si la siguiente linea (loMetric.getAscent()) no cabe se deja de imprimir
            if((drawPosY + (float)loMetric.getAscent() - (float) size.getY()) > size.getHeight()){
                lbSePasa = true;
            }
        }
        float ldValor = -1;
        if(!lbSePasa){
            ldValor=drawPosY - (float) size.getY();
        }
        return ldValor;
    }
}
