/*
 * JAccionesListados.java
 *
 * Created on 9 de febrero de 2008, 12:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.cargaMasiva;
import ListDatos.JListDatos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IPlugInContexto;

public class JAccionesCargaMasiva implements IEjecutarExtend {
    public static final String mcsAccion = "<html>Carga<br>masiva</html>";

    private ICargaMasiva moControlador;
    private final IPlugInContexto moContexto;

    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionesCargaMasiva(final ICargaMasiva poControlador, IPlugInContexto poContexto) {
        moControlador = poControlador;
        moContexto=poContexto;
    }

    public void actionPerformed(utilesGUIx.ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsAccion)){
            JPanelCargaMasiva loPanel = new JPanelCargaMasiva();
            JListDatos loList = moControlador.getTablaBase();
            loPanel.setDatos(loList);
            loPanel.setAccionListenerTerminada(new ActionListenerCZ() {
                public void actionPerformed(ActionEventCZ e) {
                    if(moControlador instanceof IPanelControlador){
                        try {
                            ((IPanelControlador)moControlador).refrescar();
                        } catch (Exception ex) {
                            JMsgBox.mensajeError(null, ex);
                        }
                    }
                    
                }
            });
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(loPanel, 800, 600, JMostrarPantallaParam.mclEdicionFrame, "Carga masiva de " + loList.msTabla));
            
        }
    }
    
}
