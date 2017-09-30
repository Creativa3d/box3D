/*
 * JPanelGeneralColores.java
 *
 * Created on 26 de marzo de 2007, 20:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

import java.awt.Color;

/**Pone un color a las celdas rellenadas*/
public class JTableCZColorRellenados implements ITableCZColores {
    private ColorCZ moColorBackGround;
    private ColorCZ moColorForeground;
    /** Creates a new instance of JPanelGeneralColores */
    public JTableCZColorRellenados() {
        super();
    }

    public void setBackGround(Color poColor){
        moColorBackGround = new ColorCZ(poColor.getRGB());
    }
    public void setForeGround(Color poColor){
        moColorForeground = new ColorCZ(poColor.getRGB());
    }

    public ColorCZ getColorBackground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if(value!=null && value.toString().equals("") && !isSelected){
            return moColorBackGround;
        }else{
            return null;
        }
    }

    public ColorCZ getColorForeground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if(value!=null && value.toString().equals("") && !isSelected){
            return moColorForeground;
        }else{
            return null;
        }
    }
}

