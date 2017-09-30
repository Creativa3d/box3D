/*
 * JAccionesModificacionMasiva.java
 *
 * Created on 08/06/2017
 *
 */
package utilesGUIx.modificacionMasiva;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.plugin.IPlugInContexto;

/**
 *
 * @author rtenor
 */
public class JAccionesModificacionMasiva implements IEjecutarExtend {

    public static final String mcsAccion = "Modif. Masiva";

    private IModificacionMasiva moControlador;
    private final IPlugInContexto moContexto;

    public JAccionesModificacionMasiva(final IModificacionMasiva poControlador, IPlugInContexto poContexto) {
        moControlador = poControlador;
        moContexto = poContexto;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsAccion)) {
            if(plIndex.length>0){
                moControlador.modificacionMasiva(plIndex);
            }else{
                throw new Exception("No existe una fila actual");
            }
            
        }
    }

}
