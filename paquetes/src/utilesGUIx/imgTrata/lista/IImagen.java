/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.imgTrata.lista;

import ListDatos.IFilaDatos;

/**
 *
 * @author eduardo
 */
public interface IImagen {
    public void setDatos(IFilaDatos poFila)throws Throwable;
    public Object getImagen();
    public String getDescripcion();
    public void ver() throws Throwable;

}
