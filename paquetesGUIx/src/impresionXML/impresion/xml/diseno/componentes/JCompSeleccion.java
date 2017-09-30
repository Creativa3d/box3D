/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.diseno.IXMLDesign;
import impresionXML.impresion.xml.diseno.JPanelDESIGN;
import impresionXML.impresion.xml.diseno.PaintListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author eduardo
 */
public class JCompSeleccion implements PaintListener {
    private final JPanelDESIGN moDesign;
    private final IXMLDesign moCompXML;
    public  JCompSeleccion(JPanelDESIGN poDesign, IXMLDesign poCompXML){
        moDesign=poDesign;
        moCompXML=poCompXML;
//        moDesign.addPaintListener(this);
    } 
    
    public IXMLDesign getDesigXML(){
        return moCompXML;
    }

    public void paint(Graphics2D g) {
//        g.setColor(Color.red);
////        g.getStroke()
//        Rectangle lr = moCompXML.getVisualizacion().getBounds();
//        g.drawRect(lr.x-1, lr.y-1, lr.width+2, lr.height+2);
    }
    
    public void liberar(){
//        moDesign.removePaintListener(this);
    }
}
