/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import ListDatos.JListDatos;
import ListDatos.JUtilTabla;

import utiles.Copiar;

/**
 *
 * @author eduardo
 */
public class JUtilTablaGUIx {
    public static Copiar moCopiar = null;    
    /**
     * copiar el JListDatos
     * @param poList el objeto a copiar
     */
    public static void copiar(final JListDatos poList){
        moCopiar = new Copiar();
        copiar(poList, moCopiar);
    }
    /**
     * copiar el JListDatos
     * @param poList el objeto a copiar
     * @param poCopiar el objeto en donde se copia
     */
    public static void copiar(final JListDatos poList, final Copiar poCopiar){
        //lo copiamos al clipboard
        poCopiar.setClip(JUtilTabla.getListDatos2String(poList));
    }    
}
