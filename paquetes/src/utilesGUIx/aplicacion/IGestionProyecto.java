/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;

import ListDatos.IFilaDatos;
import ListDatos.IServerServidorDatos;
import ListDatos.JSTabla;
import utiles.IListaElementos;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

public interface IGestionProyecto {
//    public IListaElementos getListaTablas() throws Exception;    
    public JSTabla getTabla(final IServerServidorDatos poServer, final String psTabla) throws Exception;
    public IPanelControlador getControlador (final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla, final String psTablaRelac, final IFilaDatos poDatosRelac) throws Exception;
    public JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla) throws Exception;
    public void mostrarEdicion(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla, IFilaDatos poFila) throws Exception;
    public IFilaDatos buscar(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla) throws Exception;
}
