/*
 * JLienzo.java
 *
 * Created on 13 de septiembre de 2004, 9:32
 */

package impresionXML.impresion.motorImpresion;

import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.awt.font.*;
import impresionXML.impresion.estructura.*;
import impresionXML.impresion.motorImpresion.rompeLineas.LineBreakPanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedString;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import utilesGUIx.imgTrata.JIMGTrata;

/**
 * Objeto página, en donde se imprime
 * conversion de cm a 1/72 parte de una pulgada(inch) 1 inch = 2.54 cm
 */
public class JPagina extends Canvas implements ILienzo {
    private static final long serialVersionUID = 1L;
    
    private final Point2D moUltPosImprimidaReal = new Point2D.Float();
    private final Rectangle2D moAreaImpresionOriginal;
    private Rectangle2D moAreaImpresion;
//    private final Graphics2D  g2Original;
    private transient Graphics2D  moGraphic2d;
    private Color moColorTransparencia=Color.white;
    
    /**
     * poAreaImpresion
     * margenes izq.,sup.  y tamaño TOTAL de la pagina
     * ejemplo: para margenes 1,2 y tamaño de pagina 21, 29 seria (1,2,21,29)
     * @param pdMargenIzq Margen izq.
     * @param pdMargenSup Margen sup.
     * @param pdAnchoTotal Ancho total
     * @param pdAltoTotal alto total
     * @param poPagina Formato de la página
     * @param g en donde pintar
     */
    public JPagina(float pdMargenIzq, float pdMargenSup, float pdAnchoTotal, float pdAltoTotal, PageFormat poPagina, Graphics g) {
        super();
        PageFormat loPagina = poPagina;
//        g2Original = (Graphics2D)g;
        moGraphic2d = (Graphics2D)g;

        //establecemos el area de impresion->
        moAreaImpresionOriginal = new Rectangle2D.Float();
        moAreaImpresionOriginal.setRect(
            mdConvertir(pdMargenIzq), 
            mdConvertir(pdMargenSup), 
            mdConvertir(pdAnchoTotal-pdMargenIzq), 
            mdConvertir(pdAltoTotal -pdMargenSup)
            );

        //establecemos la pagina
        Paper loPaper = loPagina.getPaper();

        loPagina.setPaper(loPaper);

        
        loPaper.setImageableArea(
            moAreaImpresionOriginal.getX(), 
            moAreaImpresionOriginal.getY(), 
            moAreaImpresionOriginal.getWidth(), 
            moAreaImpresionOriginal.getHeight()
            );
        loPagina.setPaper(loPaper);

        moUltPosImprimidaReal.setLocation(
            (float)moAreaImpresionOriginal.getX(),
            (float)moAreaImpresionOriginal.getY());

        restaurarAreaImpresionPagina();
    }
    public void setGraphics(Graphics2D g){
        moGraphic2d = g;
    }
    public void setColorTransparencia(Color pocolor){
        moColorTransparencia = pocolor;
    }
    /**
     * acepta cm devuelve pulgadas * 72
     */
    public static double mdConvertir(final double pdCM){
        return (pdCM * 72) / 2.54;
    }
    /**
     * acepta pulgadas*72 y devuelve cm
     */
    public static double mdConvertirACM(final double pdPulgadas72){
        return (pdPulgadas72 *  2.54) / 72;
    }
    
    public Rectangle2D getAreaImpresionOriginal() {
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setRect(
            mdConvertirACM(moAreaImpresionOriginal.getX()), 
            mdConvertirACM(moAreaImpresionOriginal.getY()), 
            mdConvertirACM(moAreaImpresionOriginal.getWidth()), 
            mdConvertirACM(moAreaImpresionOriginal.getHeight())
            );
        return loRect;
    }
    public Rectangle2D getAreaImpresion() {
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setRect(
            mdConvertirACM(moAreaImpresion.getX()), 
            mdConvertirACM(moAreaImpresion.getY()), 
            mdConvertirACM(moAreaImpresion.getWidth()), 
            mdConvertirACM(moAreaImpresion.getHeight()));
        return loRect;
    }
    
    public Point2D getUltPosicionImprimida() {
        Point2D loPunto = new Point2D.Float();
        loPunto.setLocation(
            mdConvertirACM(moUltPosImprimidaReal.getX()),
            mdConvertirACM(moUltPosImprimidaReal.getY()));
        return loPunto;
    }
    
    public boolean imprimirImagen(final Image poImage, final Rectangle2D poPosicionDestino) {
        boolean lbExito = false;
//        try {
//            BufferedImage loAux=JIMGTrata.getIMGTrata().getImagen(poImage);
//            JIMGTrata.getIMGTrata().guardarImagenJPG(loAux, "image.jpg", 1);
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//        if(!mbSeSale(mdX(mdConvertir(poPosicionDestino.getY() + poPosicionDestino.getHeight())))){
//con que la imagen este dentro de la pagina se imprime, no tenemos en cuenta el height, puede haber problemas con margenes y
//encontrar el error es complicado        
        if(!mbSeSale(mdX(mdConvertir(poPosicionDestino.getY())))){
            moUltPosImprimidaReal.setLocation(
                mdX(mdConvertir(poPosicionDestino.getX()))+mdConvertir(poPosicionDestino.getWidth()), 
                mdY(mdConvertir(poPosicionDestino.getY()))+mdConvertir(poPosicionDestino.getHeight())
                );
            moGraphic2d.drawImage(poImage, 
                (int)mdX(mdConvertir(poPosicionDestino.getX())),(int)mdY(mdConvertir(poPosicionDestino.getY())),
                (int)mdConvertir(poPosicionDestino.getWidth()),(int)mdConvertir(poPosicionDestino.getHeight()),
                moColorTransparencia,
                this
                );
            lbExito = true;
        }
        return lbExito;
            
    }
    
    public boolean imprimirLinea(final java.awt.geom.Point2D poPunto1, final java.awt.geom.Point2D poPunto2, final JEstiloLinea poEstiloLinea) {
        moGraphic2d.setColor(poEstiloLinea.moColor);
        switch(poEstiloLinea.mlEstilo){ 
            case JEstiloLinea.mclSolido:
                moGraphic2d.setStroke(new BasicStroke(poEstiloLinea.mdGrosor, 0, BasicStroke.JOIN_MITER));
                break;
            case JEstiloLinea.mclRayado:
		moGraphic2d.setStroke(new BasicStroke(poEstiloLinea.mdGrosor, 
                        BasicStroke.CAP_BUTT, 
                        BasicStroke.JOIN_MITER, 
                        10.0f,new float[] {10.0f}, 0.0f));
                 break;
            case JEstiloLinea.mclPunteado:
		moGraphic2d.setStroke(new BasicStroke(poEstiloLinea.mdGrosor, 
                        BasicStroke.CAP_BUTT, 
                        BasicStroke.JOIN_MITER, 
                        2.0f,new float[] {2.0f}, 0.0f));
                break;
            default:
        }
        moGraphic2d.drawLine(
            (int)mdX(mdConvertir(poPunto1.getX())),(int)mdY(mdConvertir(poPunto1.getY())),
            (int)mdX(mdConvertir(poPunto2.getX())),(int)mdY(mdConvertir(poPunto2.getY())));
        
        moUltPosImprimidaReal.setLocation(
            Math.max(mdX(mdConvertir(poPunto1.getX())),mdX(mdConvertir(poPunto2.getX()))), 
            Math.max(mdY(mdConvertir(poPunto1.getY())),mdY(mdConvertir(poPunto2.getY())))
            );
        return true;
            
    }
    private boolean mbSeSale(final double pdAlto){
        return (pdAlto > (moAreaImpresionOriginal.getHeight() + moAreaImpresionOriginal.getY()));
    }
    private double mdX(final double pdX){
        return pdX + moAreaImpresion.getX();
    }
    private double mdY(final double pdY){
        return pdY + moAreaImpresion.getY();
    }
    public boolean imprimirTexto(final JParamTextoLibre poParam) {
        boolean lbExito = true;
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setRect(
            mdX(mdConvertir(poParam.moPosicion.getX())), 
            mdY(mdConvertir(poParam.moPosicion.getY())), 
            mdConvertir(poParam.moPosicion.getWidth()), 
            mdConvertir(poParam.moPosicion.getHeight())
            );
        Font loFuenteAux = moGraphic2d.getFont();
        Color loColorAux = moGraphic2d.getColor();
        AffineTransform loTransformAux = moGraphic2d.getTransform();
        try{
            //cambiamos el color
            if(poParam.moColor!=null) {
                moGraphic2d.setColor(poParam.moColor);
            }
            //primero cambiamos el tipo de fuente, esto siemrpe funciona
            Font loFuente = moGraphic2d.getFont().deriveFont(poParam.moFuente.getStyle(), poParam.moFuente.getSize());
            moGraphic2d.setFont(loFuente);
            //ahora intentamos poner la fuente del parametro, esto ha veces falla
            //si la fuente no existe en el contexto
            moGraphic2d.setFont(poParam.moFuente);
            
            if(poParam.mdAngulo>0.00001){
                textoEnAngulo(loRect, poParam);
                //por ahora asi, ya q es dificil controlar con el angulo la ult. posicion imp.
                moUltPosImprimidaReal.setLocation(loRect.getX(), loRect.getY());
            }else{
                if(!poParam.mbMultilinea){
                    loRect.setRect(
                        loRect.getX(), loRect.getY(), 
                        loRect.getWidth(), 
                        moGraphic2d.getFontMetrics().getAscent());
                }
                if(mbSeSale((loRect.getY() + moGraphic2d.getFontMetrics().getAscent()))){
                    lbExito=false;
                }else{
                    moUltPosImprimidaReal.setLocation(loRect.getX(), loRect.getY()+ loRect.getHeight());
                    if(poParam.mbMultilinea || poParam.mlAlineacion != ITextoLibre.mclAlineacionIzquierda){
                        LineBreakPanel loLinea = new LineBreakPanel(poParam);
                        float lAlto = loLinea.pintar(moGraphic2d, new Rectangle2D.Float((float)loRect.getX(), (float)loRect.getY(), (float)loRect.getWidth(), (float)loRect.getHeight() ));
                        //si el alto es <0 es que no se ha imprimido el parrafo entero
                        if(lAlto<0){
                            lbExito=false;
                        }
                    }else{
                        String lsAux = msAcortarTexto(poParam.msTexto, loRect.getWidth());
                        if(lsAux != null && !lsAux.equals("")){
                            AttributedString loAtrib = new AttributedString(lsAux);
                            loAtrib.addAttribute(TextAttribute.FONT, moGraphic2d.getFont());
                            if(poParam.mbFuenteSubrayada){
                                loAtrib.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            }
                            moGraphic2d.drawString(loAtrib.getIterator(), (float)loRect.getX(), (float)(loRect.getY() + loRect.getHeight()));
                        }
                    }
                }
            }
        }finally{
            moGraphic2d.setFont(loFuenteAux);
            moGraphic2d.setColor(loColorAux);
            moGraphic2d.setTransform(loTransformAux);
        }
        return lbExito;
    }
    private void textoEnAngulo(final Rectangle2D loRect, final JParamTextoLibre poParam) {
        if(poParam.msTexto!=null && !poParam.msTexto.equals("")){
            AffineTransform af = new AffineTransform();
            af.translate(loRect.getX() ,loRect.getY());
            af.rotate(Math.toRadians(poParam.mdAngulo));
            FontRenderContext renderContext = new FontRenderContext(null, false, false);
            moGraphic2d.transform(af);

            if(poParam.msTexto != null && !poParam.msTexto.equals("")){
                AttributedString loAtrib = new AttributedString(poParam.msTexto);
                loAtrib.addAttribute(TextAttribute.FONT, moGraphic2d.getFont());
                if(poParam.mbFuenteSubrayada){
                    loAtrib.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                }

                TextLayout layout =
                    new TextLayout(
                        loAtrib.getIterator(),
                        renderContext);
                layout.draw(moGraphic2d, 0, 0);
            }
        }
    }
    
    private String msAcortarTexto(String psTexto,final  double pdAncho){
        FontMetrics loFontMetric = moGraphic2d.getFontMetrics();
        for(; pdAncho < loFontMetric.stringWidth(psTexto) ;){
            psTexto = psTexto.substring(0, psTexto.length()-1);
        }
        return psTexto;
    }
    public void restaurarAreaImpresionPagina() {
        //establecemos el area de impresion original
        //no se tiene en cuenta la x y la y pq ya se lo hemos puesto
        //al formato de pagina
        moAreaImpresion = new Rectangle2D.Float();
        moAreaImpresion.setRect(
            moAreaImpresionOriginal.getX(),
            moAreaImpresionOriginal.getY(),
            moAreaImpresionOriginal.getWidth(),
            moAreaImpresionOriginal.getHeight()
            );

        //establecemos el area de impresion en el objeto grafico
        moGraphic2d.setClip(
            (int)moAreaImpresion.getX(), (int)moAreaImpresion.getY(),
            (int)moAreaImpresion.getWidth(),(int)moAreaImpresion.getHeight()
            );
    }
    public void setAreaImpresion(final java.awt.geom.Rectangle2D poR) {
        //convertimos de cm a 1/72 de pulgada
        double ldX = Math.max(mdConvertir(poR.getX()), moAreaImpresionOriginal.getX());
        double ldY = Math.max(mdConvertir(poR.getY()), moAreaImpresionOriginal.getY());
       double ldWidth = mdConvertir(poR.getWidth());
        double ldHeight = mdConvertir(poR.getHeight());

        //nos aseguramos de que no superen el area de impresion
        if((ldWidth + ldX) > (moAreaImpresionOriginal.getWidth() +  moAreaImpresionOriginal.getX())){
            ldWidth = moAreaImpresionOriginal.getWidth();
        }
        if((ldHeight + ldY) > (moAreaImpresionOriginal.getHeight() +  moAreaImpresionOriginal.getY())  ){
            ldHeight = moAreaImpresionOriginal.getHeight();
        }
        
        //establecemos el area de impresion
        moAreaImpresion = new Rectangle2D.Float();
        moAreaImpresion.setRect(ldX, ldY, ldWidth, ldHeight);
        
        //establecemos el area de impresion en el objeto grafico
        moGraphic2d.setClip(
            (int)moAreaImpresion.getX(), (int)moAreaImpresion.getY(),
            (int)moAreaImpresion.getWidth(), (int)moAreaImpresion.getHeight()
            );
    }
}
