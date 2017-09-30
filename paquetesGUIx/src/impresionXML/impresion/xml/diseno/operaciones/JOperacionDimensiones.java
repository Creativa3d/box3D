/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.operaciones;

import impresionXML.impresion.xml.IVisitorOperacion;
import impresionXML.impresion.xml.JxmlBanda;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlImagen;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlLinea;
import impresionXML.impresion.xml.JxmlTexto;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import utiles.JCadenas;

/**
 * Limpiamos todos los textos con nombre
 * @author eduardo
 */
public class JOperacionDimensiones implements IVisitorOperacion{
    private final JxmlInforme moInforme;
    private final double mdAlto;
    private final double mdAncho;
    private final boolean mbSoloTexto;
    
    public JOperacionDimensiones (JxmlInforme poInforme, double pdAlto, double pdAncho){
        this(poInforme, pdAlto, pdAncho, true);
    }
    public JOperacionDimensiones (JxmlInforme poInforme, double pdAlto, double pdAncho, boolean pbSoloTexto){
        moInforme = poInforme;
        mdAlto=pdAlto;
        mdAncho=pdAncho;
        mbSoloTexto = pbSoloTexto;
    }

    public void operar(JxmlBanda poBanda) throws Throwable {
    }

    public void operar(JxmlCuadrado poCuadrado) throws Throwable {
        if(!mbSoloTexto){
            Rectangle2D loRect = poCuadrado.getRectangulo();
            
            loRect.setRect(loRect.getX(), loRect.getY(), loRect.getWidth() + mdAncho, loRect.getHeight() + mdAlto);
            poCuadrado.setPunto1y2(
                    new Point2D.Double(loRect.getX(),loRect.getY())
                    , new Point2D.Double(loRect.getX()+loRect.getWidth(),loRect.getY()+loRect.getHeight()));
        }
    }

    public void operar(JxmlFuente poFuente) throws Throwable {
    }

    public void operar(JxmlImagen poImagen) throws Throwable {
        if(!mbSoloTexto){
            Rectangle2D loRect = poImagen.getPosicionDestino();
            loRect.setRect(loRect.getX(), loRect.getY(), loRect.getWidth() + mdAncho, loRect.getHeight() + mdAlto);
            poImagen.setPosicionDestino(loRect);
        }
    }

    public void operar(JxmlLinea poLinea) throws Throwable {
        if(!mbSoloTexto){
            Rectangle2D loRect = poLinea.getRectangulo();
            
            loRect.setRect(loRect.getX(), loRect.getY(), loRect.getWidth() + mdAncho, loRect.getHeight() + mdAlto);
            poLinea.setPunto1(new Point2D.Double(loRect.getX(),loRect.getY()));
            poLinea.setPunto2(new Point2D.Double(loRect.getX()+loRect.getWidth(),loRect.getY()+loRect.getHeight()));
        }
    }

    public void operar(JxmlTexto poTexto) throws Throwable {
        Rectangle2D loRect = poTexto.getPosicionDestino();
        loRect.setRect(loRect.getX(), loRect.getY(), loRect.getWidth() + mdAncho, loRect.getHeight() + mdAlto);
        poTexto.setPosicionDestino(loRect);
    }

    public void operar(JxmlInforme poBanda) throws Throwable {
    }
    
}
