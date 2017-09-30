/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.aplicacion;

import utilesEjecutar.IAbstractFactoryEjecutar;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInActualizarProgram implements IPlugIn {
    private final IAbstractFactoryEjecutar moFactoria;
    private final String[] masArray;
    private final JDatosGeneralesAplicacion moDatosGenerales;


    public JPlugInActualizarProgram(
            final String[] pasARRAY,
            IAbstractFactoryEjecutar poFactoria,
            JDatosGeneralesAplicacion poDatosGenerales
            ){
        masArray = pasARRAY;
        moFactoria = poFactoria;
        moDatosGenerales = poDatosGenerales;
    }


    public void procesarInicial(IPlugInContexto poContexto) {
        if(masArray!=null && masArray.length>=1){
            JProcesoActualizar loActu = new JProcesoActualizar(masArray, moFactoria);
            moDatosGenerales.getThreadGroup().addProcesoYEjecutar(loActu, false);
        }
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }

    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
    }

    public void procesarFinal(IPlugInContexto poContexto) {
    }

}
