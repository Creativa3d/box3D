/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;

import impresionXML.impresion.xml.JxmlInforme;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 *
 * @author eduardo
 */
public class JRegla extends JPanel {

    public static final int mclVertical=1;
    public static final int mclHorizontal=3;
    private BasicStroke moFina = new BasicStroke(1, 0, BasicStroke.JOIN_MITER);
    private BasicStroke moGorda = new BasicStroke(2, 0, BasicStroke.JOIN_MITER);
    
    private double mdSeparaciones=2;
    
    private int mlOrientacion= mclVertical;
    private JPanelDESIGN moPanelDesign;
    private int mlx;
    private int mly;
    public JRegla(){
        setBackground(Color.white);
        mdSeparaciones=4;
    }
    public void setOrientacion(int plHorientacion){
        mlOrientacion=plHorientacion;
    }
    
    public void paint(Graphics g) {
        super.paint(g); 
        try{
        Graphics2D loGraphic2d=(Graphics2D) g;
        loGraphic2d.setFont(new Font("Dialog", 0, 8));
        int lPeq = 10;
        if(mlOrientacion==mclHorizontal){
            g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
            int lSeparaciones = (int)(((double)getWidth() / JPanelDESIGN.getMultiCMaPixel(moPanelDesign.getInforme().getDiseno().getZoom()))*mdSeparaciones);
            for(int i = 0 ; i < lSeparaciones; i++){
                double ldx=((i*JPanelDESIGN.getMultiCMaPixel(moPanelDesign.getInforme().getDiseno().getZoom()))/mdSeparaciones);
                loGraphic2d.setStroke(moFina);
                if ((i % mdSeparaciones) == 0){
                    loGraphic2d.setStroke(moGorda);
                    g.drawString(String.valueOf((int)(i / mdSeparaciones)) , (int)ldx+2, 7  );
                    lPeq = 0;
                }else{
                    lPeq = 10;
                }
                g.drawLine((int)ldx, lPeq, (int)ldx, getHeight());
            }
            loGraphic2d.setStroke(moGorda);
            loGraphic2d.setColor(Color.MAGENTA);
            g.drawLine(mlx, 0, mlx, getHeight());
            
            
        }else{
            g.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
            int lSeparaciones = (int)(((double)getHeight()/ JPanelDESIGN.getMultiCMaPixel(moPanelDesign.getInforme().getDiseno().getZoom()))*mdSeparaciones);
            for(int i = 0 ; i < lSeparaciones; i++){
                double ldy=((i*JPanelDESIGN.getMultiCMaPixel(moPanelDesign.getInforme().getDiseno().getZoom()))/mdSeparaciones);
                loGraphic2d.setStroke(moFina);
                if ((i % mdSeparaciones) == 0){
                    loGraphic2d.setStroke(moGorda);
                    g.drawString(String.valueOf((int)(i / mdSeparaciones)), 0, (int)ldy+10);
                    lPeq = 0;
                }else{
                    lPeq = 10;
                }
                g.drawLine(lPeq, (int)ldy, getWidth(), (int)ldy);
            }
            loGraphic2d.setStroke(moGorda);
            loGraphic2d.setColor(Color.MAGENTA);
            g.drawLine(0, mly, getWidth(), mly);
        }
        
        }catch(Exception e){}
    
    }

    void setPanelDesign(JPanelDESIGN jPanelDESIGN1) {
        moPanelDesign = jPanelDESIGN1;
        moPanelDesign.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                mlx=e.getX();
                mly=e.getY();
                repaint();
            }
        });
    }
    
    
}
