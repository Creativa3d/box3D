/*
 * JPlugInManager.java
 *
 * Created on 7 de septiembre de 2006, 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import java.util.ArrayList;
import java.util.List;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.IPanelControlador;

public class JPlugInManager implements IPlugInManager {
    protected IListaElementos<IPlugIn> moListaPlugIn = new JListaElementos<IPlugIn>();
    private IPlugInManager moSeguridad;
    private List<IPlugInListener> moListeners = new ArrayList<IPlugInListener>();
            
    
    public JPlugInManager(final String[] pasListaClases) {
        if(pasListaClases!=null){
            for (String pasListaClase : pasListaClases) {
                addPlugIn(pasListaClase);
            }
        }
    }
    
    public void addListener(IPlugInListener poList){
        moListeners.add(poList);
    }
    public void removeListener(IPlugInListener poList){
        moListeners.remove(poList);
    }
    
    
    @Override
    public void crearPlugInSeguridad(){
        moSeguridad = new JPlugInManager(null);
    }
    @Override
    public void setPlugInSeguridad(IPlugInManager poSeguridad){
        moSeguridad = poSeguridad;
    }
    @Override
    public IPlugInManager getPlugInSeguridad(){
        return moSeguridad;
    }
    @Override
    public void addPlugIn(String psClase){
        try{
            IPlugIn loApli = (IPlugIn)Class.forName(psClase).newInstance();
            moListaPlugIn.add(loApli);
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e.toString());
        }
        
    }
    @Override
    public void addPlugIn(IPlugIn loApli){
        moListaPlugIn.add(loApli);
    }
    @Override
    public void deletePlugIn(String psClase) {
        IListaElementos<IPlugIn> loBorrar = new JListaElementos<IPlugIn>();
        for(IPlugIn loO : moListaPlugIn){
            if(loO.getClass().getName().equalsIgnoreCase(psClase)){
                loBorrar.add(loO);
            }
        }        
        moListaPlugIn.removeAll(loBorrar);
    }

    @Override
    public void deletePlugIn(IPlugIn poClase) {
        IListaElementos<IPlugIn> loBorrar = new JListaElementos<IPlugIn>();
        for(IPlugIn loO : moListaPlugIn){
            if(loO == poClase){
                loBorrar.add(loO);
            }
        }
        moListaPlugIn.removeAll(loBorrar);
    }
    @Override
    public IListaElementos<IPlugIn> getListaPlugIn(){
        return moListaPlugIn;
    }
    @Override
    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta){
        if(!poConsulta.getParametros().isPlugInPasados()){
            for(int i = 0; i < moListaPlugIn.size();i++ ){
                IPlugIn loPlugIn = (IPlugIn)moListaPlugIn.get(i);
                loPlugIn.procesarConsulta(poContexto, poConsulta);
            }
            poConsulta.getParametros().setPlugInPasados(true);
        }
        if(moSeguridad!=null){
            boolean lbAux = poConsulta.getParametros().isPlugInPasados();
            poConsulta.getParametros().setPlugInPasados(false);
            moSeguridad.procesarConsulta(poContexto, poConsulta);
            poConsulta.getParametros().setPlugInPasados(lbAux);
        }
        for(IPlugIn loListener : moListeners){
            loListener.procesarConsulta(poContexto, poConsulta);
        }
    }
    
    @Override
    public void procesarInicial(IPlugInContexto poContexto) {
        for(int i = 0; i < moListaPlugIn.size();i++ ){
            IPlugIn loPlugIn = (IPlugIn)moListaPlugIn.get(i);
            try{
                loPlugIn.procesarInicial(poContexto);
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        if(moSeguridad!=null){
            moSeguridad.procesarInicial(poContexto);
        }
        for(IPlugIn loListener : moListeners){
            loListener.procesarInicial(poContexto);
        }
    }

    @Override
    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
        if(!poFrame.getParametros().isPlugInPasados()){
            for(int i = 0; i < moListaPlugIn.size();i++ ){
                IPlugIn loPlugIn = (IPlugIn)moListaPlugIn.get(i);
                loPlugIn.procesarEdicion(poContexto, poFrame);
            }
            poFrame.getParametros().setPlugInPasados(true);
        }
        if(moSeguridad!=null){
            boolean lbAux = poFrame.getParametros().isPlugInPasados();
            poFrame.getParametros().setPlugInPasados(false);
            moSeguridad.procesarEdicion(poContexto, poFrame);
            poFrame.getParametros().setPlugInPasados(lbAux);
        }

        for(IPlugIn loListener : moListeners){
            loListener.procesarEdicion(poContexto, poFrame);
        }        
    }

    @Override
    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        if(!poControlador.getParametros().isPlugInPasados()){
            for(int i = 0; i < moListaPlugIn.size();i++ ){
                IPlugIn loPlugIn = (IPlugIn)moListaPlugIn.get(i);
                loPlugIn.procesarControlador(poContexto, poControlador);
            }
            poControlador.getParametros().setPlugInPasados(true);
        }
        if(moSeguridad!=null){
            boolean lbAux = poControlador.getParametros().isPlugInPasados();
            poControlador.getParametros().setPlugInPasados(false);
            moSeguridad.procesarControlador(poContexto, poControlador);
            poControlador.getParametros().setPlugInPasados(lbAux);
        }

        for(IPlugIn loListener : moListeners){
            loListener.procesarControlador(poContexto, poControlador);
        }          
    }


}
