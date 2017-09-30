/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.colores;

import ListDatos.IListDatosFiltro;
import ListDatos.JListDatos;
import utilesGUIx.ColorCZ;

public class JElementoColor {
    public int mlComparacion = JListDatos.mclTIgual;
    public int mlCampo1;
    public int mlCampo2;

    public IListDatosFiltro moFiltro;
    public ColorCZ moColorForeground;
    public ColorCZ moColorBackGround;
    public int mlColumna=-1;

    public JElementoColor(IListDatosFiltro poFiltro, ColorCZ poColorBackGround, ColorCZ poColorForeground){
        moFiltro = poFiltro ;
        moColorForeground = poColorForeground;
        moColorBackGround = poColorBackGround;
    }
    public JElementoColor(int plComparacion, int plCampo1, int plCampo2, ColorCZ poColorBackGround, ColorCZ poColorForeground){
        mlComparacion = plComparacion;
        mlCampo1 = plCampo1;
        mlCampo2 = plCampo2;
        moColorForeground = poColorForeground;
        moColorBackGround = poColorBackGround;

    }
}
