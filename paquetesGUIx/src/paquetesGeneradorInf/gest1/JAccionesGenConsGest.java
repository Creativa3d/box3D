/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gest1;

import ListDatos.IResultado;
import ListDatos.JSelect;
import java.awt.event.ActionEvent;
import paquetesGeneradorInf.gest1.tablasControladoras.JT2SQLGENERADOR;
import paquetesGeneradorInf.gest1.tablasExtend.JTEESQLGENERADOR;
import paquetesGeneradorInf.gui.CallBackGenInf;
import paquetesGeneradorInf.gui.JGuiConsulta;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import paquetesGeneradorInf.plugin.JAccionesGeneradorConsultas;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;

public class JAccionesGenConsGest implements IEjecutarExtend {
    public static final String mcsGestorConsultas = "<html>Gestor<br>Consultas</html>";
    public static final String mcsGenerador = "<html>Generador<br>Consultas</html>";

    private IPanelControlador moControlador;
    private final IProcesoThreadGroup moProceso;
    JGuiConsultaDatos moDatos;
    
    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionesGenConsGest(
            final IPanelControlador poControlador,
            final IProcesoThreadGroup poProceso) {
        moControlador = poControlador;
        moProceso=poProceso;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsGestorConsultas)){
            
            JT2SQLGENERADOR loControlador = new JT2SQLGENERADOR(
                    JDatosGeneralesP.getDatosGenerales().getServer()
                    , JDatosGeneralesP.getDatosGenerales().getMostrarPantalla()
                    );
            loControlador.setPadre(moControlador.getParametros().getNombre());
            loControlador.setDatos(JAccionesGeneradorConsultas.getDatos(moControlador, moDatos, moProceso));
            loControlador.mostrarFormPrinci();


        }
        if (e.getActionCommand().equals(mcsGenerador)) {
            JGuiConsultaDatos loDatos = JAccionesGeneradorConsultas.getDatos(moControlador, moDatos, moProceso);
            loDatos.setCallBack(new CallBackGenInf() {
                public void callBack(JGuiConsultaDatos poDatos) throws Exception {
                    if(!poDatos.isCancelado()){
                        JTEESQLGENERADOR loGene = new JTEESQLGENERADOR(poDatos.getServer());
                        loGene.addNew();
                        loGene.getPADRE().setValue(moControlador.getParametros().getNombre());
                        loGene.setDatos(poDatos);
                        IResultado loResult = loGene.guardar();
                        if(!loResult.getBien()){
                            throw new Exception(loResult.getMensaje());
                        }
                    }
                }
            });
            JGuiConsulta loGuiConsulta = new JGuiConsulta();
            loGuiConsulta.setDatos(loDatos);
            loDatos.getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(
                        loGuiConsulta, 600, 500,
                        JMostrarPantalla.mclEdicionFrame,
                        "Consulta"));


        }
        
    }

}
