/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.plugin;

import impresionJasper.plugin.JAccionesListados;
import javax.swing.ImageIcon;
import utiles.IListaElementos;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInGeneradorExportarWORD implements IPlugIn {

    public String msGrupoForm = JAccionesListados.mcsGrupoInformes;
    public boolean mbEsPrincipal = true;
    /** Creates a new instance of JPlugInImporExport */
    public JPlugInGeneradorExportarWORD() {
    }

    public void procesarInicial(IPlugInContexto poContexto) {
    }


    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        IListaElementos loElem = poControlador.getParametros().getBotonesGenerales().getListaBotones();
        String lsIcon = null;
        try{
            lsIcon = "/paquetesGeneradorInf/images/word24.png";
            ImageIcon loIcon = new ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/word24.png"));
        }catch(Throwable e){
            try{
                lsIcon = "/images/word24.png";
                ImageIcon loIcon = new ImageIcon(getClass().getResource("/images/word24.png"));
            }catch(Throwable e1){
            }
        }
        JAccionesGeneradorConsultas loAcciones = new JAccionesGeneradorConsultas(
                JAccionesGeneradorConsultas.mcsExportarWORD,poControlador, poContexto.getThreadGroup());
        JBotonRelacionado loBoton = new JBotonRelacionado(
                JAccionesGeneradorConsultas.mcsExportarWORD,
                JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  JAccionesGeneradorConsultas.mcsExportarWORD),
                lsIcon,
                null,
                (IEjecutarExtend)loAcciones,
                null,
                msGrupoForm);
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
