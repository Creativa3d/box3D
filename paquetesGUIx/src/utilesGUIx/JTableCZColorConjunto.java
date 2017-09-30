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
import utiles.IListaElementos;
import utiles.JListaElementos;

/**Almacena un conjuntos de interfaces ITableCZColores y comprueba todos ellos para poner un color*/
public class JTableCZColorConjunto implements ITableCZColores {
    private IListaElementos moLista = new JListaElementos();
    
    /** Creates a new instance of JPanelGeneralColores */
    public JTableCZColorConjunto() {
        super();
    }
    
    public void add(ITableCZColores poColores){
        moLista.add(poColores);
    } 
    
    public void add(int plRow, int plCol, Color poFore, Color poBack){
        add(new JTableCZColorCelda(poBack, poFore, plRow, plCol));
    }
    
    public void addFila(int plRow, Color poFore, Color poBack) {
        add(new JTableCZColorFila(poBack, poFore, plRow));
    }
    
    public ColorCZ getColorBackground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        ColorCZ loColor = null;
        for(int i = 0 ; i < moLista.size(); i++){
            ITableCZColores loElem= (ITableCZColores) moLista.get(i);
            ColorCZ loColorAux = loElem.getColorBackground(value, isSelected, hasFocus, row, column);
            if(loColorAux!=null){
                loColor=loColorAux;
            }
        }
        return loColor;
    }

    public ColorCZ getColorForeground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        ColorCZ loColor = null;
        for(int i = 0 ; i < moLista.size(); i++){
            ITableCZColores loElem= (ITableCZColores) moLista.get(i);
            ColorCZ loColorAux = loElem.getColorForeground(value, isSelected, hasFocus, row, column);
            if(loColorAux!=null){
                loColor=loColorAux;
            }
        }
        return loColor;
    }
}

