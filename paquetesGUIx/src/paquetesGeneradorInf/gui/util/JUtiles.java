/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.util;

import javax.swing.table.TableColumn;

/**
 *
 * @author GONZE2
 */
public class JUtiles {
    public static void ponerAncho(TableColumn loColumn, int plAncho){
        if(plAncho==0){
            //invisibles las 2 primeras columnas
            loColumn.setMinWidth(0);
            loColumn.setPreferredWidth(0);
            loColumn.setWidth(0);
            loColumn.setMaxWidth(0);
        }else{
            loColumn.setPreferredWidth(plAncho);
            loColumn.setWidth(plAncho);
        }

    }

}
