/*
 * JTrozear.java
 *
 * Created on 29 de abril de 2005, 8:59
 */

package trozearImages;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;


public class JTrozear implements ImageObserver{
    
    /** Creates a new instance of JTrozear */
    public JTrozear() {
    }
    
    public void trozear(Image poImage,String psPathFicheSinExtension, int plPixelX, int plPixelY, double pdX, double pdY, double pdResolucionX, double pdResolucionY) throws Exception {
        int lAnchoTotal = poImage.getWidth(this);
        int lAltoTotal = poImage.getHeight(this);
        
        int lNumeroX = lAnchoTotal / plPixelX;
        int lNumeroY = lAltoTotal / plPixelY;
        if((lAnchoTotal % plPixelX)>0.0){
            lNumeroX++;
        }
        if((lAltoTotal % plPixelY)>0.0){
            lNumeroY++;
        }
        BufferedImage loImageAux = new BufferedImage(plPixelX, plPixelY, BufferedImage.TYPE_INT_RGB);
        Graphics2D loG= (Graphics2D)loImageAux.getGraphics();
        int lX=0;
        int lY=0;
        int lAncho;
        int lAlto;
        for(int i = 0; i < lNumeroX; i++) {
            lY=0;
            for(int ii = 0; ii < lNumeroY; ii++){
                lAncho = plPixelX;
                lAlto =plPixelY;
                if((lX+lAncho) > lAnchoTotal){
                    lAncho = lAnchoTotal - lX;
                }
                if((lY+lAlto) > lAltoTotal){
                    lAlto = lAltoTotal - lY;
                }
                if((lAncho != loImageAux.getWidth())||(lAlto != loImageAux.getHeight()) ){
                    loImageAux = new BufferedImage(lAncho, lAlto, BufferedImage.TYPE_INT_RGB);
                    loG= (Graphics2D)loImageAux.getGraphics();
                }
                loG.drawImage(poImage,0,0,lAncho,lAlto,lX,lY,lX+lAncho,lY+lAlto,this);
                escribir(loImageAux, psPathFicheSinExtension, i, ii, (lX * pdResolucionX) + pdX, (lY * pdResolucionY) + pdY, pdResolucionX,pdResolucionY);
                lY+=plPixelY;
            }
            lX+=plPixelX;
        }

    }
    private void escribir(BufferedImage loImageAux, String psPathFicheSinExtension, int i, int ii, double pdX, double pdY, double pdResolucionX, double pdResolucionY) throws Exception {
        String lsNombreCompletoSinExt = psPathFicheSinExtension + "_" + String.valueOf(i) +"_" + String.valueOf(ii);
        //guardar image
        File loFile = new File(lsNombreCompletoSinExt + ".jpg");
        loFile.delete();
        ImageIO.write(loImageAux, "jpg", loFile);
        //guardar xml
        FileOutputStream loOut = new FileOutputStream(lsNombreCompletoSinExt + ".xml");
        try{
            PrintWriter out = new PrintWriter(loOut);
            out.println("<?xml version=\"1.0\"?>");
            out.println("<metadata>");
            out.println("<meta id=\"142\" content=\""+String.valueOf(pdX)+"\"/>");
            out.println("<meta id=\"143\" content=\""+String.valueOf(pdY)+"\"/>");
            out.println("<meta id=\"144\" content=\""+String.valueOf(pdResolucionX)+"\"/>");
            out.println("<meta id=\"145\" content=\""+String.valueOf(pdResolucionY)+"\"/>");
            out.println("</metadata>");
            out.close();
        }finally{
            loOut.close();
        }

    }
    
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if((infoflags & 0xc0) != 0){
            return false;
        } else {
            return true;
        }
    }
    
}
