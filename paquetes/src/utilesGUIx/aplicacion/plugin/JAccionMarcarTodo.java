/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.plugin;


import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;

public class JAccionMarcarTodo  implements IEjecutarExtend {
    public static final String mcsMarcar= "Marcar";

    private IPanelControlador moControlador;
    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionMarcarTodo(final IPanelControlador poControlador) {
        moControlador = poControlador;
    }

    public void actionPerformed(utilesGUIx.ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsMarcar)){
            moControlador.getPanel().seleccionarTodo();

        }
    }

}
