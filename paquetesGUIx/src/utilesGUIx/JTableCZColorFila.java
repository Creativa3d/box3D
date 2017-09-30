/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import java.awt.Color;

/**
 * Color en una celda
 * @author eduardo
 */
public class JTableCZColorFila implements ITableCZColores {
    private Color moColorBackGround;
    private Color moColorForeground;
    private int mlRow;

    /** Creates a new instance of JPanelGeneralColores */
    public JTableCZColorFila() {
        super();
    }
    public JTableCZColorFila(Color poColorBack, Color poColorFore, int plRow) {
        super();
        setBackGround(poColorBack);
        setForeGround(poColorFore);
        setFila(plRow); 
    }

    public void setBackGround(Color poColor){
        moColorBackGround = poColor;
    }
    public void setForeGround(Color poColor){
        moColorForeground = poColor;
    }
    public void setFila(int plRow){
        mlRow = plRow;
    }

    public ColorCZ getColorBackground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if(row == mlRow && !isSelected && moColorBackGround!=null){
            return new ColorCZ(moColorBackGround.getRGB());
        }else{
            return null;
        }
    }

    public ColorCZ getColorForeground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if(row == mlRow && !isSelected && moColorForeground!=null){
            return new ColorCZ(moColorForeground.getRGB());
        }else{
            return null;
        }
    }
}
