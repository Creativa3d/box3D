/*
 * IPanelGenericoColores.java
 *  
 * Created on 26 de marzo de 2007, 17:28
 *  
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

public interface ITableCZColores {
    public ColorCZ getColorBackground(
            Object value, boolean isSelected, boolean hasFocus, int row, int column);
    public ColorCZ getColorForeground(
            Object value, boolean isSelected, boolean hasFocus, int row, int column);
}
