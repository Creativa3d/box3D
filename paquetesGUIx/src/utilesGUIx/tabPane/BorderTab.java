/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.tabPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

public class BorderTab  extends AbstractBorder {
    protected Color highlightOuter;
    protected Color highlightInner;
    protected Color shadowInner;
    protected Color shadowOuter;
    private boolean mbActivo;
    private boolean mbPrimera;
    private boolean mbUltima;
    
    private int mclBordes = 5;

    public BorderTab (final boolean pbActivo, final boolean pbPrimera, final boolean pbUltima) {
        mbActivo = pbActivo;
        mbPrimera = pbPrimera;
        mbUltima = pbUltima;
    }
    /**
     * Returns the insets of the border.
     * @param c the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c)       {
	return new Insets(2, mclBordes, 2, mclBordes);
    }

    /** 
     * Reinitialize the insets parameter with this Border's current Insets. 
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.right = mclBordes;
        insets.left = 2;
        insets.top = insets.bottom = 2;
        return insets;
    }

    /**
     * Returns the outer highlight color of the bevel border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * @param c the component for which the highlight may be derived
     */
    public Color getHighlightOuterColor(Component c)   {
        Color highlight = getHighlightOuterColor();
        return highlight != null? highlight : 
                                       c.getBackground().brighter().brighter();
    }

    /**
     * Returns the inner highlight color of the bevel border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * @param c the component for which the highlight may be derived
     */
    public Color getHighlightInnerColor(Component c)   {
        Color highlight = getHighlightInnerColor();
        return highlight != null? highlight :
                                       c.getBackground().brighter();
    }

    /**
     * Returns the inner shadow color of the bevel border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * @param c the component for which the shadow may be derived
     */
    public Color getShadowInnerColor(Component c)      {
        Color shadow = getShadowInnerColor();
        return shadow != null? shadow :
                                    c.getBackground().darker();
    }

    /**
     * Returns the outer shadow color of the bevel border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * @param c the component for which the shadow may be derived
     */
    public Color getShadowOuterColor(Component c)      {
        Color shadow = getShadowOuterColor();
        return shadow != null? shadow :
                                    c.getBackground().darker().darker();
    }

    /**
     * Returns the outer highlight color of the bevel border.
     * Will return null if no highlight color was specified
     * at instantiation.
     */
    public Color getHighlightOuterColor()   {
        return highlightOuter;
    }

    /**
     * Returns the inner highlight color of the bevel border.
     * Will return null if no highlight color was specified
     * at instantiation.
     */
    public Color getHighlightInnerColor()   {
        return highlightInner;
    }

    /**
     * Returns the inner shadow color of the bevel border.
     * Will return null if no shadow color was specified
     * at instantiation.
     */
    public Color getShadowInnerColor()      {
        return shadowInner;
    }

    /**
     * Returns the outer shadow color of the bevel border.
     * Will return null if no shadow color was specified
     * at instantiation.
     */
    public Color getShadowOuterColor()      {
        return shadowOuter;
    }

    /**
     * Returns whether or not the border is opaque.
     */
    public boolean isBorderOpaque() { return true; }

    /**
     * Paints the border for the specified component with the specified
     * position and size.
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        paintBorde(c, g, x, y, width, height, mbActivo);
    }
    protected void paintBorde(Component c, Graphics g, int x, int y,
                                    int width, int height, boolean pbActivo)  {
        Color oldColor = g.getColor();
        int h = height;
        int w = width;

        g.translate(x, y);

        g.setColor(getHighlightOuterColor(c));
        //izq
        g.drawLine(0, 0, 0, h-2);
        g.drawLine(1, 1, 1, h-3);
        
        //abajo
        if(!pbActivo){
            g.drawLine(1, h-2, w-2, h-2);
            g.drawLine(0, h-1, w-1, h-1);
        }
        
        //arriba
        if(pbActivo){
            g.setColor(getShadowOuterColor(c));
            g.drawLine(1, 0, w-2-mclBordes, 0);
            g.setColor(getHighlightInnerColor(c));
            g.drawLine(2, 1, w-3-mclBordes, 1);
        }else{
            g.setColor(getHighlightInnerColor(c));
            g.drawLine(1, 0, w-2-mclBordes, 0);
            g.setColor(getShadowOuterColor(c));
            g.drawLine(2, 1, w-3-mclBordes, 1);
        }

        //derecha
        g.setColor(getShadowOuterColor(c));
        g.drawLine(w-1-mclBordes, 0, w-1, h/2-2);
        g.setColor(getShadowInnerColor(c));
        g.drawLine(w-2-mclBordes, 1, w-2, h/2-3);

        g.translate(-x, -y);
        g.setColor(oldColor);

    }

}
