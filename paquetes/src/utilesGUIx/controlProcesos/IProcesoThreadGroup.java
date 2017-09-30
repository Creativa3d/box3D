/*
 * IProcesoGroup.java
 *
 * Created on 16 de septiembre de 2008, 9:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.controlProcesos;

import utiles.IListaElementos;
import utilesGUI.procesar.IProcesoAccion;

public interface IProcesoThreadGroup {
    /**Add un proceso y lo ejecutamos*/
    public void addProcesoYEjecutar(IProcesoAccion poProceso);
    /**Add un proceso y lo ejecutamos, le decimos si se muestra el form. pasado un tiempo*/
    public void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm);
    /**Devuelve la lista de IProcesoAccion*/
    public IListaElementos getListaProcesos();
        /**add Listener sobre los procesos*/
    public void addListener(IProcesoThreadGroupListener poListener);
    /**borra Listener sobre los procesos*/
    public void removeListener(IProcesoThreadGroupListener poListener);
    /**si hay procesos activos*/
    public boolean isProcesosActivos();
    /**porcentaje total del proceso*/
    public int getProcesoTotal();
    /**texto del proceso*/
    public String getProcesoTexto();
    /**Indice del elemento*/
    public int getIndice(Object elemento);
    /**lista de JProcesoElemento q contienen los procesos*/
    public IListaElementos getListaElementos();
    
}
