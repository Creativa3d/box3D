/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.colores;

public class JCacheColorElemento {
    public int mlUltRow=-1;
    public int mlUltcol=-1;
    public JElementoColor moUltElem=null;
    public String msUltClave=null;

    JCacheColorElemento(int row, int col, String psClave, JElementoColor poUltElem){
        mlUltRow=row;
        mlUltcol=col;
        msUltClave=psClave;
        moUltElem=poUltElem;
    }

    public boolean isFilaCorrecta(int row, int col, String psClave){
        return (mlUltRow == row || mlUltRow == -1 || row == -1) &&
               (mlUltcol==col || mlUltcol==-1 || col == -1) &&
               psClave.equals(msUltClave);
    }

}