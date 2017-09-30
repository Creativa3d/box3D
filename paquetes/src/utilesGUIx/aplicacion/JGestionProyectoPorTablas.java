/*
* JDatosGenerales.java
*
* Creado el 7/4/2009
*/

package utilesGUIx.aplicacion;


import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import ListDatos.*;

import java.util.HashMap;
import java.util.Iterator;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;



public class JGestionProyectoPorTablas implements IGestionProyecto {

    private HashMap moTablas = new HashMap();

    public IListaElementos getListaTablas() throws Exception{
        IListaElementos loLista = new JListaElementos();
        Iterator loIter= moTablas.keySet().iterator();
        while(loIter.hasNext()){
            loLista.add(loIter.next().toString()); 
        }
        return loLista;
    }

    public void addTabla(String psTabla, IGestionProyecto poTabla){
        moTablas.put(psTabla, poTabla);
    }
    public IGestionProyecto getTabla(String psTabla){
        IGestionProyecto loAux = (IGestionProyecto) moTablas.get(psTabla);
        if(loAux==null){
            loAux = (IGestionProyecto) moTablas.get(psTabla.toLowerCase());
        }
        if(loAux==null){
            loAux = (IGestionProyecto) moTablas.get(psTabla.toUpperCase());
        }
        return loAux;
    }

    public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
        JPanelBusquedaParametros loResult = null;
        if(loResult==null){
            IGestionProyecto loT = getTabla(psTabla);
            if(loT!=null){
                loResult = loT.getParamPanelBusq(poServer, poMostrar, psTabla);
            }
        }
        if(loResult==null){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
        return loResult;
    }
        
    public void mostrarEdicion(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla, final IFilaDatos poFila) throws Exception {
        boolean lbError = true;
        if(lbError){
            IGestionProyecto loT = getTabla(psTabla);
            if(loT!=null){
                loT.mostrarEdicion(poServer, poMostrar, psTabla, poFila);
                lbError = false;
            }
        }
        if(lbError){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
    }
    public JSTabla getTabla(final IServerServidorDatos poServer, final String psTabla) throws Exception{
        JSTabla loResult = null;
        if(loResult==null){
            IGestionProyecto loT = getTabla(psTabla);
            if(loT!=null){
                loResult = loT.getTabla(poServer, psTabla);
            }
        }
        if(loResult==null){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
        return loResult;

    }

    public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
        boolean lbError = true;
        IFilaDatos loResult = null;
        if(lbError){
            IGestionProyecto loT = getTabla(psTabla);
            if(loT!=null){
                loResult = loT.buscar(poServer, poMostrar, psTabla);
                lbError = false;
            }
        }
        if(lbError){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
        return loResult;
    }
    public IPanelControlador getControlador(
            IServerServidorDatos poServer, IMostrarPantalla poMostrar, 
            String psTabla, 
            String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        
        IPanelControlador loResult = null;
        if(loResult==null){
            IGestionProyecto loT = getTabla(psTabla);
            if(loT!=null){
                loResult = loT.getControlador(poServer, poMostrar, psTabla, psTablaRelac, poDatosRelac);
            }
        }
        if(loResult==null){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
        return loResult;
    }
}
