/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

/**
 *
 * @author chema
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class JPintaFondo implements Border {
    private   Image image ;
    private boolean mbConservarProporciones=true;
    private Color moColorRelleno=Color.white;
    private int mlAlineacionHorizontal=SwingConstants.CENTER;
    private int mlAlineacionVertical=SwingConstants.CENTER;
    private boolean mbTamanoOriginal;

    public JPintaFondo(Image image ) {
        this.image=image;
    }
    public void setAlineacionHorizontal(int alineacionFondo) {
        mlAlineacionHorizontal = alineacionFondo;
    }
    public void setAlineacionVertical(int alineacionFondo) {
        mlAlineacionVertical = alineacionFondo;
    }
    public void setConservarProporciones(boolean pbValor){
        mbConservarProporciones = pbValor;
    }
    public void setTamanoOriginal(boolean pbValor){
        mbTamanoOriginal = pbValor;
    }
    public void setColorRelleno(Color poColor){
        moColorRelleno = poColor;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if(moColorRelleno!=null){
            g.setColor(moColorRelleno);
            g.fillRect(0, 0, width, height);
        }
        if(mbConservarProporciones){
            //se pone en la posicion destino pero conservando la proporcion
            double ldHeight;
            double ldWidth;
            if(mbTamanoOriginal){
                ldWidth = image.getWidth(null);
                ldHeight = image.getHeight(null);
            }else{
                ldHeight = (double)width * (double)image.getHeight(null) / (double)image.getWidth(null);
                ldWidth = (double)height * (double)image.getWidth(null) / (double)image.getHeight(null);
            }
            int lx=0;
            int ly=0;
            if(ldHeight >= height){
                ldHeight = height;
            }
            if(ldWidth >= width){
                ldWidth = width;
            }
            if(mlAlineacionHorizontal==SwingConstants.CENTER){
                lx=(int)(width-ldWidth)/2;
            }else if(mlAlineacionHorizontal==SwingConstants.RIGHT){
                lx=(int)(width-ldWidth);
            }
            if(mlAlineacionVertical==SwingConstants.CENTER){
                ly=(int)(height-ldHeight)/2;
            }else if(mlAlineacionVertical==SwingConstants.BOTTOM){
                ly=(int)(height-ldHeight);
            }
            if(mbTamanoOriginal){
                g.drawImage(image,
                        lx,  ly,
//                        image.getWidth(null), image.getHeight(null),
                        null
                        );
                
            }else{
                g.drawImage(image,
                        lx,  ly,
                        (int)ldWidth, (int)ldHeight,
                        null
                        );
            }
        }else{

            if(width==0 & height==0) {
                g.drawImage(image,0,0,null);
            } else {
                g.drawImage(image,0,0,width,height,null);
            }
        }
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }

    public boolean isBorderOpaque() {
        return true;
    }

}


