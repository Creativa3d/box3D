  /*
 * JAccionesListados.java
 *
 * Created on 9 de febrero de 2008, 12:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar.plugin;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.JUtilTabla;
import impresionXML.impresion.word.combinar.IPlantillas;
import impresionXML.impresion.word.combinar.IPlantillasFactoria;
import impresionXML.impresion.word.combinar.JT2PLANTILLAS;
import impresionXML.impresion.word.combinar.JWord;
import utiles.JComunicacion;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.forms.JPanelMensaje;
import utilesGUIxAvisos.word.JCombinarPlantillaYEmail;
import utilesDoc.JDocDatosGeneralesGUIx;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;

public class JAccionesPlantillasCombinar implements IEjecutarExtend {

    public static final String mcsPlantillasWord = "Plantillas";
    public static final String mcsCombinarWord="Combinar";
    public static final String mcsEMailWord="eMail";


    private final IPlantillasControlador moControlador;
    private final JGUIxAvisosDatosGenerales moCorreos;
    private final IPlantillasFactoria moPlantillasMotor;
    
    /**
     * Creates a new instance of JAccionesExportImport
     * @param poControlador
     * @param poCorreos
     * @param poPlantillasMotor
     */
    public JAccionesPlantillasCombinar(
            final IPlantillasControlador poControlador
            , JGUIxAvisosDatosGenerales poCorreos
            , IPlantillasFactoria poPlantillasMotor) {
        moControlador = poControlador;
        moPlantillasMotor=poPlantillasMotor;
        moCorreos=poCorreos;
    }

    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        JListDatos loListDatos = moControlador.getListDatosPlantilla(plIndex);

        if(e.getActionCommand().equals(mcsPlantillasWord)){
            JT2PLANTILLAS loWord = new JT2PLANTILLAS(
                    JGUIxConfigGlobal.getInstancia().getMostrarPantalla()
                    , loListDatos, moPlantillasMotor);
            loWord.mostrarFormPrinci();

        }
        if(e.getActionCommand().equals(mcsCombinarWord)){
            final IPlantillas loWord = moPlantillasMotor.getNuevaPlantilla(loListDatos.msTabla);
            final JListDatos loListPlantillas = loWord.getListaPlantillas();
            
            JBusqueda loBusq = new JBusqueda(new IConsulta() {
                public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {}
                public JListDatos getList() {
                    return loListPlantillas;
                }
                public void addFilaPorClave(IFilaDatos poFila) throws Exception {}

                public boolean getPasarCache() {
                    return false;
                }
            }, "Combinar");
            loBusq.getParametros().setPlugInPasados(true);
            loBusq.mostrarBusq();
            if(loBusq.getIndex()>=0){
                loListPlantillas.setIndex(loBusq.getIndex());
                loWord.combinarPlantilla(
                        loListPlantillas.getFields(0).getString()
                        , loListDatos);
            }
        }
        if(e.getActionCommand().equals(mcsEMailWord)){
            final IPlantillas loWord = moPlantillasMotor.getNuevaPlantilla(loListDatos.msTabla);
            final JListDatos loListPlantillas = loWord.getListaPlantillas();
            JPlantillaControladorParam loParam = ((IPlantillasControlador)moControlador).getParamPlantilla();
            JBusqueda loBusq = new JBusqueda(new IConsulta() {
                public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {}
                public JListDatos getList() {
                    return loListPlantillas;
                }
                public void addFilaPorClave(IFilaDatos poFila) throws Exception {}

                public boolean getPasarCache() {
                    return false;
                }
            }, "Combinar y eMail");
            loBusq.getParametros().setPlugInPasados(true);
            loBusq.mostrarBusq();
            if(loBusq.getIndex()>=0){
                loListPlantillas.setIndex(loBusq.getIndex());
                JMensaje loMensaje = new JMensaje();
                loMensaje.setUsuario(JGUIxConfigGlobalModelo.getInstancia().getUsuario());
                JPanelMensaje loPanelMensaje = new JPanelMensaje();
                JComunicacion loComu = new JComunicacion();
                String lsPathPlantilla = moCorreos.getPathPlantilla();
                loPanelMensaje.setDatos(loMensaje, loComu, lsPathPlantilla, null, null, false);
                JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarEdicion(loPanelMensaje, null, loPanelMensaje, JMostrarPantalla.mclEdicionDialog);
                if (loComu.moObjecto != null && loComu.moObjecto.toString().equals("1")) {
                
                    JCombinarPlantillaYEmail loWordEMail = new JCombinarPlantillaYEmail(
                            loWord
                            , loListPlantillas.getFields(0).getString()
                            , loListDatos
                            , loParam.lPosiEMAIL
                            , loParam.lPosiIDENTIFICAR
                            , moCorreos
                            , loMensaje
                            );
                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(loWordEMail);
                }                
            }
        }
    }
   
}


