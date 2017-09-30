/*
 * JPlugInModificacionMasiva.java
 *
 * Created on 08/06/2017
 *
 */
package utilesGUIx.modificacionMasiva;

import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

/**
 *
 * @author rtenor
 */
public class JPlugInModificacionMasiva implements IPlugIn {

    public String msGrupoForm = "";
    public boolean mbEsPrincipal = true;

    /**
     * Creates a new instance of JPlugInModificacionMasiva
     */
    public JPlugInModificacionMasiva() {
    }

    public void procesarInicial(IPlugInContexto poContexto) {
    }

    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        if (poControlador instanceof IModificacionMasiva) {

            JPanelGeneralBotones retValue = poControlador.getParametros().getBotonesGenerales();
            JAccionesModificacionMasiva loAcciones = new JAccionesModificacionMasiva((IModificacionMasiva) poControlador, poContexto);

            retValue.addBotonPrincipal(
                    new JBotonRelacionado(
                            JAccionesModificacionMasiva.mcsAccion,
                            JAccionesModificacionMasiva.mcsAccion,
                            "/images/ModifMasiva24.png",
                            null,
                            (IEjecutarExtend) loAcciones,
                            null,
                            null
                    ),
                    1
            );

        }
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }

    @Override
    public void procesarFinal(IPlugInContexto poContexto) {
    }
    

}
