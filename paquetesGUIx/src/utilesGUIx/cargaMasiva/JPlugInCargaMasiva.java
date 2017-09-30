/*
 * JPlugInListados.java
 *
 * Created on 9 de febrero de 2008, 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.cargaMasiva; 

import java.awt.Dimension;
import javax.swing.ImageIcon;
import utiles.IListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInCargaMasiva implements IPlugIn {
    public String msGrupoForm = "";
    public boolean mbEsPrincipal = true;

    /** Creates a new instance of JPlugInImporExport */
    public JPlugInCargaMasiva() {
    }

    public void procesarInicial(IPlugInContexto poContexto) {
    }


    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        if(poControlador instanceof ICargaMasiva){
            IListaElementos loElem = poControlador.getParametros().getBotonesGenerales().getListaBotones();
            JAccionesCargaMasiva loAcciones = new JAccionesCargaMasiva((ICargaMasiva)poControlador, poContexto);
            JBotonRelacionado loBoton = new JBotonRelacionado(
                    JAccionesCargaMasiva.mcsAccion,
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  JAccionesCargaMasiva.mcsAccion),
                    "",
                    null,
                    (IEjecutarExtend)loAcciones, null ,msGrupoForm);
            loBoton.setEsPrincipal(mbEsPrincipal);
            loElem.add(loBoton);
        }
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }
    public void procesarFinal(IPlugInContexto poContexto){
        
    }

    
}
