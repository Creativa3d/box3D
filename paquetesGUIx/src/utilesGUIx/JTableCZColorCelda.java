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
public class JTableCZColorCelda implements ITableCZColores {
    private Color moColorBackGround;
    private Color moColorForeground;
    private int mlCol;
    private int mlRow;
    /** Creates a new instance of JPanelGeneralColores */
    public JTableCZColorCelda() {
        super();
    }
    public JTableCZColorCelda(Color poColorBack, Color poColorFore, int plRow, int plCol) {
        super();
        setBackGround(poColorBack);
        setForeGround(poColorFore);
        setCelda(plRow, plCol); 
    }

    public void setBackGround(Color poColor){
        moColorBackGround = poColor;
    }
    public void setForeGround(Color poColor){
        moColorForeground = poColor;
    }
    public void setCelda(int plRow, int plCol){
        mlRow = plRow;
        mlCol = plCol;
    }

    public ColorCZ getColorBackground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if(row == mlRow && column == mlCol && !isSelected && moColorBackGround!=null){
            return new ColorCZ(moColorBackGround.getRGB());
        }else{
            return null;
        }
    }

    public ColorCZ getColorForeground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if(row == mlRow && column == mlCol && !isSelected && moColorForeground!=null){
            return new ColorCZ(moColorForeground.getRGB());
        }else{
            return null;
        }
    }
}
