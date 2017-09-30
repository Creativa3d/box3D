/*
* JDatosGenerales.java
*
* Creado el 7/4/2009
*/

package utilesGUIx.aplicacion;


import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import ListDatos.*;
import java.util.Iterator;


import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;


public class JGestionProyecto implements IGestionProyecto {

    private IListaElementos moListaProyectos = new JListaElementos();


//    public IListaElementos getListaTablas() throws Exception{
//        JListaElementos loLista = new JListaElementos();
//        for(int i = 0 ; i < moListaProyectos.size(); i++){
//            IGestionProyecto loGest = (IGestionProyecto) moListaProyectos.get(i);
//
//            IListaElementos loAux = loGest.getListaTablas();
//            for(int l = 0 ; l < loAux.size();l++){
//                String loObj = (String) loAux.get(l);
//                loLista.add(loObj);
//            }
//        }
//
//        return loLista;
//    }

    public IListaElementos getListaProyectos(){
        return moListaProyectos;
    }
    public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
        JPanelBusquedaParametros loResult = null;
        for(int i = 0 ; i < moListaProyectos.size() && loResult ==null; i++){
            IGestionProyecto loGest = (IGestionProyecto) moListaProyectos.get(i);
            try{
                loResult = loGest.getParamPanelBusq(poServer, poMostrar, psTabla);
            }catch(Exception e){
                JDepuracion.anadirTexto(
                        JDepuracion.mclINFORMACION,
                        loGest.getClass().getName(),
                        e.toString());
            }
        }
        if(loResult==null){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
        return loResult;
    }
        
    public void mostrarEdicion(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla, final IFilaDatos poFila) throws Exception {
        boolean lbError = true;
        for(int i = 0 ; i < moListaProyectos.size() && lbError; i++){
            lbError = false;
            IGestionProyecto loGest = (IGestionProyecto) moListaProyectos.get(i);
            try{
                loGest.mostrarEdicion(poServer, poMostrar, psTabla, poFila);
            }catch(Exception e){
                lbError = true;
            }
        }
        if(lbError){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
    }
    public JSTabla getTabla(final IServerServidorDatos poServer, final String psTabla) throws Exception{
        JSTabla loResult = null;
        for(int i = 0 ; i < moListaProyectos.size() && loResult ==null; i++){
            IGestionProyecto loGest = (IGestionProyecto) moListaProyectos.get(i);
            try{
                loResult = loGest.getTabla(poServer, psTabla);
            }catch(Exception e){

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
        for(int i = 0 ; i < moListaProyectos.size() && lbError; i++){
            lbError = false;
            IGestionProyecto loGest = (IGestionProyecto) moListaProyectos.get(i);
            try{
                loResult = loGest.buscar(poServer, poMostrar, psTabla);
            }catch(Exception e){
                lbError = true;
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
        for(int i = 0 ; i < moListaProyectos.size() && loResult ==null; i++){
            IGestionProyecto loGest = (IGestionProyecto) moListaProyectos.get(i);
            try{
                loResult = loGest.getControlador(poServer, poMostrar, psTabla, psTablaRelac, poDatosRelac);
            }catch(Exception e){
                JDepuracion.anadirTexto(
                        JDepuracion.mclINFORMACION,
                        loGest.getClass().getName(),
                        e.toString());
            }
        }
        if(loResult==null){
            throw new Exception("Tabla no encontrada " + psTabla);
        }
        return loResult;
    }
}
