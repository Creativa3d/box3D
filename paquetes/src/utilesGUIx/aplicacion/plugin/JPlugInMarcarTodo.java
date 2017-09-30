/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.plugin;

import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;


public class JPlugInMarcarTodo implements IPlugIn {
    
    private IListaElementos moLista = new JListaElementos();

    public JPlugInMarcarTodo(){
    }
    public JPlugInMarcarTodo(String psControlador){
        addControlador(psControlador);
    }
    public void addControlador(String psControlador){
        moLista.add(psControlador);
    }

    public void procesarInicial(IPlugInContexto poContexto) {
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }
    private boolean isPermitido(String psNombre){
        boolean lbAdd = true;
        if(moLista.size()>0){
            lbAdd=false;
            for(int i = 0 ; i < moLista.size() && !lbAdd; i++){
                if(psNombre!=null &&  psNombre.equals(moLista.get(i).toString())){
                    lbAdd=true;
                }
            }
        }

        return lbAdd;

    }
    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        if(isPermitido(poControlador.getParametros().getNombre())){
        poControlador.getParametros().getBotonesGenerales().addBotonPrincipal(
                new JBotonRelacionado(
                    JAccionMarcarTodo.mcsMarcar, JAccionMarcarTodo.mcsMarcar,
                    "/utilesGUIx/aplicacion/images/marcar.png",
                    (IEjecutarExtend)new JAccionMarcarTodo(poControlador))
                );
        }
    }

    public void procesarFinal(IPlugInContexto poContexto) {
    }

}
