/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.plugin;

import impresionJasper.plugin.JAccionesListados;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import utiles.IListaElementos;
import ListDatos.config.JDevolverTextos;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInGeneradorConsultas implements IPlugIn {

    public String msImageBanner;
    public String msGrupoForm = JAccionesListados.mcsGrupoInformes;
    public boolean mbEsPrincipal = true;
    public JGuiConsultaDatos moDatos = new JGuiConsultaDatos(null);
    /** Creates a new instance of JPlugInImporExport */
    public JPlugInGeneradorConsultas() {
    }

    public void procesarInicial(IPlugInContexto poContexto) {
    }


    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        IListaElementos loElem = poControlador.getParametros().getBotonesGenerales().getListaBotones();
        String lsIcon = null;
        try{
            lsIcon = "/paquetesGeneradorInf/images/Print16.gif";
            ImageIcon loIcon = new ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/Print16.gif"));
        }catch(Throwable e){
            try{
                lsIcon = "/images/Print16.gif";
                ImageIcon loIcon = new ImageIcon(getClass().getResource("/images/Print16.gif"));
            }catch(Throwable e1){
                lsIcon="";
            }
        }
        JAccionesGeneradorConsultas loAcciones = new JAccionesGeneradorConsultas(
                JAccionesGeneradorConsultas.mcsGenerador,
                poControlador,
                poContexto.getThreadGroup());
        loAcciones.moDatos = moDatos;
        JBotonRelacionado loBoton = new JBotonRelacionado(
                JAccionesGeneradorConsultas.mcsGenerador,
                JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  JAccionesGeneradorConsultas.mcsGenerador),
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
