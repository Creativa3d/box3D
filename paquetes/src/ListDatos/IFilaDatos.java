/*
 * IFilaDatos.java
 *
 * Created on 20 de marzo de 2007, 8:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos;

import java.io.Serializable;

public interface IFilaDatos extends Serializable, Cloneable {
    
    /**
     * Tipo modificacion de la fila, JListDatos.mclEditar, JListDatos.mclNuevo, JListDatos.mclBorrar
     * , JListDatos.mclNada
     * @return 
     */
    public int getTipoModif();
    /**
     * Tipo modificacion de la fila, JListDatos.mclEditar, JListDatos.mclNuevo, JListDatos.mclBorrar
     * , JListDatos.mclNada
     * @param plTipoModif*/
    public void setTipoModif(final int plTipoModif);
    /**
     * Valor del campo en la posicion i
     * @param i
     * @return 
     */
    public String msCampo(final int i);
    /**
     * Anade un campo al final
     * @param psCampo
     */
    public void addCampo(final String psCampo);
    /**
     * Anade un conj. de campos al final
     * @param psCampos
     */
    public void addCampos(final String[] psCampos);
    /**
     * Clona la fila
     * @return */
    public Object clone();
    /**
     * Establece un array de datgos, quitando lo anterior
     * @param pasDatos
     */
    public void setArray(final String[] pasDatos);
    /**
     * Devuelve un array de datos
     * @return 
     */
    public String[] moArrayDatos();
    /*
     * Numero de campos
     **/
    public int mlNumeroCampos();
}
