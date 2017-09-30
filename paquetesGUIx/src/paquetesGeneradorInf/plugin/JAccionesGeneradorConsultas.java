/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.plugin;

import ListDatos.JListDatos;
import ListDatos.JSelect;
import impresionJasper.JInfGeneralJasper;
import java.awt.event.ActionEvent;
import paquetesGeneradorInf.gui.JGuiConsulta;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import paquetesGeneradorInf.gui.camposRapido.JGuiSelectCamposRapidoAsistente;
import paquetesGeneradorInf.gui.resultados.exportar.IExportar;
import paquetesGeneradorInf.gui.resultados.exportar.JExportarExcel;
import paquetesGeneradorInf.gui.resultados.exportar.JExportarTexto;
import paquetesGeneradorInf.gui.resultados.exportar.JPanelExportar;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.JPanelGenericoAbstract;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;

public class JAccionesGeneradorConsultas implements IEjecutarExtend {
    public static final String mcsGenerador = "<html>Generador<br>Consultas</html>";
    public static final String mcsExportar = "Exportar";
    public static final String mcsExportarWORD = "<html>Combinar<br>WORD</html>";
    public static final String mcsExportarEXCEL = "<html>Excel</html>";

    private IPanelControlador moControlador;
    private final IProcesoThreadGroup moProceso;
    private final String msAccion;
    
    public JGuiConsultaDatos moDatos = new JGuiConsultaDatos(null);
    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionesGeneradorConsultas(
            final String psAccion,
            final IPanelControlador poControlador,
            final IProcesoThreadGroup poProceso) {
        msAccion=psAccion;
        moControlador = poControlador;
        moProceso=poProceso;
    }
    
    public static JGuiConsultaDatos getDatos(IPanelControlador moControlador, JGuiConsultaDatos moDatos, IProcesoThreadGroup moProceso) throws CloneNotSupportedException{
        JGuiConsultaDatos loDatos = new JGuiConsultaDatos(moControlador.getConsulta().getList().moServidor);
        loDatos.setNombre(moControlador.getParametros().getNombre());
        loDatos.setImpresionTitulo(moControlador.getParametros().getNombre());
        loDatos.setSelect((JSelect) moControlador.getConsulta().getList().getSelectDatosRecuperados().clone());
        loDatos.setTextosForms(moDatos.getTextosForms());
        loDatos.setImpresionLogo(moDatos.getImpresionLogo());
        loDatos.setMostrarPantalla(moControlador.getParametros().getMostrarPantalla());
        loDatos.setProcesoThread(moProceso);
        loDatos.setDatosGeneralesXML(moDatos.getDatosGeneralesXML());
        for(int i = 0 ; i < moDatos.getTablasAExcluir().size(); i++){
            loDatos.addTablaAExcluir((String) moDatos.getTablasAExcluir().get(i));
        }
        return loDatos;
    } 

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsGenerador)){
            JGuiConsultaDatos loDatos = getDatos(moControlador, moDatos, moProceso);
            JGuiConsulta loGuiConsulta = new JGuiConsulta();
            loGuiConsulta.setDatos(loDatos);
            loDatos.getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(
                        loGuiConsulta, 600, 500,
                        JMostrarPantalla.mclEdicionFrame,
                        "Consulta"));


        }
        if (e.getActionCommand().equals(mcsExportar)){
            JGuiConsultaDatos loDatos = new JGuiConsultaDatos(moControlador.getConsulta().getList().moServidor);
            loDatos.setNombre(moControlador.getParametros().getNombre());
            loDatos.setImpresionTitulo(moControlador.getParametros().getNombre());
            loDatos.setSelect((JSelect) moControlador.getConsulta().getList().getSelectDatosRecuperados().clone());
            loDatos.setTextosForms(moDatos.getTextosForms());
            loDatos.setImpresionLogo(loDatos.getImpresionLogo());
            loDatos.setMostrarPantalla(moControlador.getParametros().getMostrarPantalla());
            loDatos.setProcesoThread(moProceso);
            loDatos.setDatosGeneralesXML(moDatos.getDatosGeneralesXML());
            for(int i = 0 ; i < moDatos.getTablasAExcluir().size(); i++){
                loDatos.addTablaAExcluir((String) moDatos.getTablasAExcluir().get(i));
            }


            JGuiSelectCamposRapidoAsistente loGuiConsulta = new JGuiSelectCamposRapidoAsistente();
            loGuiConsulta.setConsulta(loDatos);
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(
                        loGuiConsulta, 600, 500,
                        JMostrarPantalla.mclEdicionFrame,
                        "Exportación"));
        }
        if (e.getActionCommand().equals(mcsExportarWORD)){
            JPanelExportar loExp = new JPanelExportar();
            loExp.setDatos(
                    moControlador.getConsulta().getList()
                    , new IExportar[]{new JExportarTexto(false, '\t')}
                    , null
                    , JGUIxConfigGlobal.getInstancia().getMostrarPantalla());
            
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(
                        loExp, 600, 500,
                        JMostrarPantalla.mclEdicionFrame,
                        "Exportación"));
        }
        if (e.getActionCommand().equals(mcsExportarEXCEL)){
            JListDatos loList = JPanelGenericoAbstract.getListOrdenado(
                    moControlador.getConsulta().getList()
                    , moControlador.getPanel().getTablaConfig().getConfigTablaConcreta());
            JPanelExportar loExp = new JPanelExportar();
            loExp.setDatos(
                    loList
                    , new IExportar[]{new JExportarExcel()}
                    , null
                    , JGUIxConfigGlobal.getInstancia().getMostrarPantalla());
            
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(
                        loExp, 600, 500,
                        JMostrarPantalla.mclEdicionFrame,
                        "Exportación"));
        }
    }

}
