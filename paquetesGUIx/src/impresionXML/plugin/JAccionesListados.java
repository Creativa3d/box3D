  /*
 * JAccionesListados.java
 *
 * Created on 9 de febrero de 2008, 12:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.plugin;

import impresionXML.tools.JInfGeneral;
import java.awt.event.ActionEvent;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;

public class JAccionesListados implements IEjecutarExtend {
    public static final String mcsListado = "Listado";
    public static final String mcsGrupoInformes = "Informes";

    private IPanelControlador moControlador;
    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionesListados(final IPanelControlador poControlador) {
        moControlador = poControlador;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsListado)){
            JInfGeneral loInforme = new JInfGeneral();
            loInforme.generarListado(
                    moControlador.getConsulta().getList(), 
                    moControlador.getPanel(), 
                    moControlador.getParametros().getNombre());
            
        }
    }
    
}
