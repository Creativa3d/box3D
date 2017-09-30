/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.img;

import impresionXML.impresion.motorImpresion.JPagina;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlInformeConj;
import impresionXML.impresion.xml.JxmlLectorInforme;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.File;
import javax.imageio.ImageIO;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.imgTrata.JIMGTrata;


public class JIMGImprimirInforme {

    public static void imprimir(JxmlInforme poInforme, BufferedImage poImage) throws Exception{

        PageFormat lopagef = new PageFormat();
        Paper loPaper = new Paper();
        loPaper.setSize(JPagina.mdConvertir(poInforme.getAnchoTotalReal()), JPagina.mdConvertir(poInforme.getAltoTotalReal()));
        //se usa este metodo pq se hacen muchas cosas
        PrintRequestAttributeSet loPrintPropi = JxmlInforme.getConfigurarImpresion(
                poInforme.getAncho(),
                poInforme.getAlto(), 
                poInforme.getOrientacion());
        MediaPrintableArea loMediaPrint = (MediaPrintableArea) loPrintPropi.get(MediaPrintableArea.class);
        loPaper.setImageableArea(
                loMediaPrint.getX(loMediaPrint.INCH)*72, loMediaPrint.getY(loMediaPrint.INCH)*72
                , loMediaPrint.getWidth(loMediaPrint.INCH)*72, loMediaPrint.getHeight(loMediaPrint.INCH)*72);
        lopagef.setPaper(loPaper);
        OrientationRequested loOri = (OrientationRequested) loPrintPropi.get(OrientationRequested.class);
        lopagef.setOrientation(loOri == loOri.LANDSCAPE ? lopagef.LANDSCAPE : lopagef.PORTRAIT);
        
        Graphics2D loG = (Graphics2D) poImage.getGraphics();
        loG.setTransform(new AffineTransform(2, 0, 0, 2, 0, 0));
        JPagina loPag = new JPagina(
                poInforme.getMargenIzquierdo(),
                poInforme.getMargenSuperior(),
                poInforme.getAnchoTotalReal(),
                poInforme.getAltoTotalReal(),
                lopagef,
                loG);
        loPag.setColorTransparencia(null);
        poInforme.imprimir(loPag);
    }
    public static BufferedImage imprimir(JxmlInforme poInforme) throws Exception{
        
        BufferedImage loImage = new BufferedImage(
                (int)JPagina.mdConvertir(poInforme.getAnchoTotalReal())*2
                , (int)JPagina.mdConvertir(poInforme.getAltoTotalReal())*2
                , BufferedImage.TYPE_INT_ARGB);
        Graphics2D loG = (Graphics2D) loImage.getGraphics();
        loG.setColor(Color.white);
        loG.fillRect(0, 0, loImage.getWidth(), loImage.getHeight());
        loG.setColor(Color.black);
        
        imprimir(poInforme, loImage);
        
        return loImage;
    }
    
    public static IListaElementos imprimir(JxmlInformeConj poInformes) throws Exception{
        IListaElementos loLista = new JListaElementos();
        for(int i = 0; i < poInformes.getListaElementos().size() ;i++){
            JxmlInforme loInf = (JxmlInforme) poInformes.getListaElementos().get(i);
            loLista.add(imprimir(loInf));
        }
        System.gc();
        
        return loLista;
    }

    public static void main(String args[]) {
        try {
            JDepuracion.mbDepuracion = true;
            JDepuracion.mlNivelDepuracion = JDepuracion.mclINFORMACION;
            String lsInforme = "informe.xml";
            if (args.length > 0) {
                lsInforme = args[0];
            }
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), "imprimiendo " + lsInforme);
            JxmlInforme loInforme = JxmlLectorInforme.leerInforme(lsInforme);
//            loInforme.imprimir("pdf", true);
            ImageIO.write(imprimir(loInforme), "png", new File("informe.png"));

        } catch (Exception e) {
            JDepuracion.anadirTexto(JxmlLectorInforme.class.getName(), e);
        }
    }
}
