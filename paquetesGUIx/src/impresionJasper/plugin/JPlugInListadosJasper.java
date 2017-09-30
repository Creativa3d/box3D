/*
 * JPlugInListados.java
 *
 * Created on 9 de febrero de 2008, 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionJasper.plugin; 

import utiles.IListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInListadosJasper implements IPlugIn {

    public String msImageBanner;
    public String msGrupoForm = JAccionesListados.mcsGrupoInformes;
    public boolean mbEsPrincipal = true;
    public boolean mbPantallaSeleccionPagina = false;
    public boolean mbSeleccionA3 = false;
    public String msImageICON = "/impresionJasper/images/Print16.gif";

    /** Creates a new instance of JPlugInImporExport */
    public JPlugInListadosJasper() {
    }

    public void procesarInicial(IPlugInContexto poContexto) {
    }


    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        IListaElementos loElem = poControlador.getParametros().getBotonesGenerales().getListaBotones();
        JAccionesListados loAcciones = new JAccionesListados(poControlador, poContexto);
        loAcciones.msImageBanner = msImageBanner;
        loAcciones.mbPantallaSeleccion=mbPantallaSeleccionPagina;
        loAcciones.mbSeleccionA3=mbSeleccionA3;
        JBotonRelacionado loBoton = new JBotonRelacionado(
                JAccionesListados.mcsListado,
                JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  JAccionesListados.mcsListado) ,
                msImageICON,
                null,
                (IEjecutarExtend)loAcciones, null ,msGrupoForm);
        loBoton.setEsPrincipal(mbEsPrincipal);
        loElem.add(loBoton);
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }
    public void procesarFinal(IPlugInContexto poContexto){

    }

    
}
