/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.colores;

import utiles.IListaElementos;
import utiles.JListaElementos;

public class JCacheColor {
    private int mlSizeCache=100;
    private IListaElementos moElementos=new JListaElementos();
    public JElementoColor moColorNeutro=new JElementoColor(null,null,null);

//    public void add(int row, String psClave, JElementoColor poUltElem){
//        add(row, -1, psClave, poUltElem);
//    }
    public void add(int row,int col, String psClave, JElementoColor poUltElem){
        if(moElementos.size()>mlSizeCache){
            moElementos.remove(0);
        }
        if(poUltElem==null){
            moElementos.add(new JCacheColorElemento(row, col, psClave, moColorNeutro));
        }else{
            moElementos.add(new JCacheColorElemento(row, col, psClave, poUltElem));
        }
    }
    
    public void setSizeCache(int plSize){
        mlSizeCache=plSize;
    }

    public JElementoColor getElementoColor(int row, int col, String psClave){
        JElementoColor loResult=null;
        boolean lbColYRow=false;
        for(int i = 0 ; i < moElementos.size() && !lbColYRow; i++){
            JCacheColorElemento loElem = (JCacheColorElemento) moElementos.get(i);
            if(loElem.isFilaCorrecta(row,col, psClave)){
                loResult=loElem.moUltElem;
                lbColYRow = loElem.mlUltRow!=-1 && loElem.mlUltcol!=-1 && col!=-1 && row !=-1;
            }
        }
        return loResult;
    }

}
