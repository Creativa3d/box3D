/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos.edicion;

import utiles.IListaElementos;


public interface IFormEdicionLista {
    /**
     * A�adimos una edici�n y la inicializamos
     * @param poPanel
     * @throws java.lang.Exception
     */
    public void addEdicion(final IFormEdicion poPanel) throws Exception;
    /**
     * @return devolvemos la lista de ediciones
     */
    public IListaElementos getListaEdiciones() ;
    
}
