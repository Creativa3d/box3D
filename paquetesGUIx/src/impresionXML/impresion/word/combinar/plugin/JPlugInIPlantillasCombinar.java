     /*
 * JPlugInListados.java
 *
 * Created on 9 de febrero de 2008, 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.word.combinar.plugin;


import impresionJasper.plugin.JAccionesListados;
import impresionXML.impresion.word.combinar.IPlantillas;
import impresionXML.impresion.word.combinar.IPlantillasFactoria;
import impresionXML.impresion.word.combinar.JPlantillasFactoriaDocum;
import impresionXML.impresion.word.combinar.JWordDocumental;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;

public class JPlugInIPlantillasCombinar implements IPlugIn {
    
    public String msGrupoForm = JAccionesListados.mcsGrupoInformes;
    public JGUIxAvisosDatosGenerales moCorreos;
    public IPlantillasFactoria moPlantillaFactoria;
    
    /** Creates a new instance of JPlugInImporExport */
    public JPlugInIPlantillasCombinar() {
        if(moPlantillaFactoria==null){
            moPlantillaFactoria=new JPlantillasFactoriaDocum();
        }
    }

    @Override
    public void procesarInicial(IPlugInContexto poContexto) {

    }


    @Override
    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {

        if(IPlantillasControlador.class.isAssignableFrom(poControlador.getClass())){
            JAccionesPlantillasCombinar loAcciones = new JAccionesPlantillasCombinar(
                    (IPlantillasControlador) poControlador, moCorreos, moPlantillaFactoria);
            poControlador.getParametros().getBotonesGenerales().addBoton(
                    new JBotonRelacionado(
                        JAccionesPlantillasCombinar.mcsPlantillasWord, JAccionesPlantillasCombinar.mcsPlantillasWord
                        , "/utilesGUIx/images/WordIcon.png", loAcciones
                        , msGrupoForm));
            poControlador.getParametros().getBotonesGenerales().addBoton(
                    new JBotonRelacionado(
                        JAccionesPlantillasCombinar.mcsCombinarWord, JAccionesPlantillasCombinar.mcsCombinarWord
                        , "/utilesGUIx/images/WordIcon.png", loAcciones
                        , msGrupoForm));
            if(moCorreos!=null && (((IPlantillasControlador) poControlador).getParamPlantilla().lPosiEMAIL>=0)){
                poControlador.getParametros().getBotonesGenerales().addBoton(
                        new JBotonRelacionado(
                            JAccionesPlantillasCombinar.mcsEMailWord, JAccionesPlantillasCombinar.mcsEMailWord
                            , "/utilesGUIx/images/WordIcon.png", loAcciones
                            , msGrupoForm));
            }
        }

    }

    @Override
    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    @Override
    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }

    @Override
    public void procesarFinal(IPlugInContexto poContexto) {
    }


    
}
