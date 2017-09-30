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

/**
 * movemos todos los componenetes del informe
 * @author eduardo
 */
public class JOperacionMover implements IVisitorOperacion{
    private final JxmlInforme moInforme;
    private final double mdX;
    private final double mdY;
    private final double mdxAPartir;
    private final double mdyAPartir;
    
    public JOperacionMover(JxmlInforme poInforme, double x, double y, double xAPartir, double yAPartir){
        moInforme = poInforme;
        mdX=x;
        mdY=y;
        mdxAPartir=xAPartir;
        mdyAPartir=yAPartir;
    }

    public void operar(JxmlBanda poBanda) throws Throwable {
    }

    public void operar(JxmlCuadrado poCuadrado) throws Throwable {
        if(poCuadrado.getPunto1().getX()>=mdxAPartir 
                && poCuadrado.getPunto1().getY()>=mdyAPartir){

            poCuadrado.setPunto1y2(new Point2D.Double(
                    poCuadrado.getPunto1().getX()+mdX
                    , poCuadrado.getPunto1().getY()+mdY
                    ), new Point2D.Double(
                    poCuadrado.getPunto2().getX()+mdX
                    , poCuadrado.getPunto2().getY()+mdY
                    )
                    );
        }
    }

    public void operar(JxmlFuente poFuente) throws Throwable {
    }

    public void operar(JxmlImagen poImagen) throws Throwable {
        if(poImagen.getPosicionDestino().getX()>=mdxAPartir 
                && poImagen.getPosicionDestino().getY()>=mdyAPartir){
        poImagen.setPosicionDestino(new Rectangle2D.Double(
                poImagen.getPosicionDestino().getX()+mdX
                , poImagen.getPosicionDestino().getY()+mdY
                , poImagen.getPosicionDestino().getWidth()
                , poImagen.getPosicionDestino().getHeight()
                )
                );
        }
    }

    public void operar(JxmlLinea poLinea) throws Throwable {
        if(poLinea.getPunto1().getX()>=mdxAPartir 
                && poLinea.getPunto1().getY()>=mdyAPartir){
        poLinea.setPunto1y2(new Point2D.Double(
                poLinea.getPunto1().getX()+mdX
                , poLinea.getPunto1().getY()+mdY
                ), new Point2D.Double(
                poLinea.getPunto2().getX()+mdX
                , poLinea.getPunto2().getY()+mdY
                )
                );
        }
    }

    public void operar(JxmlTexto poTexto) throws Throwable {
        if(poTexto.getPosicionDestino().getX()>=mdxAPartir 
                && poTexto.getPosicionDestino().getY()>=mdyAPartir){
        poTexto.setPosicionDestino(new Rectangle2D.Double(
                poTexto.getPosicionDestino().getX()+mdX
                , poTexto.getPosicionDestino().getY()+mdY
                , poTexto.getPosicionDestino().getWidth()
                , poTexto.getPosicionDestino().getHeight()
                )
                );
        }
    }

    public void operar(JxmlInforme poBanda) throws Throwable {
    }
    
}
