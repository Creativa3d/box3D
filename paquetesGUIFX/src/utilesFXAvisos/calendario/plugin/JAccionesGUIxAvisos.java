/*
 * JAccionesListados.java
 *
 * Created on 9 de febrero de 2008, 12:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesFXAvisos.calendario.plugin;


import utilesGUIx.ActionEventCZ;
import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JFieldDefs;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import utilesFX.JFXConfigGlobal;
import utilesFX.msgbox.JMsgBox;

import utilesFXAvisos.tablasControladoras.JT2GUIXEVENTOS;

import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIxAvisos.calendario.JDatosGenerales;

/**
 * Acción asociado a listados principales, para poder asociar eventos
 * @author eduardo
 */
public class JAccionesGUIxAvisos implements IEjecutarExtend {

    public static final String mcsAvisos = "Avisos";
    
    private IPanelControlador moControlador;
    private final IPlugInContexto moContexto;
    public String msGrupo = null;
    public String msCalendario = null;
    public int[] malCamposNombre=null;
    public String msUsuario = null;
    private final JDatosGenerales moDatosGenerales;


    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionesGUIxAvisos(JDatosGenerales poDatosGenerales, final IPanelControlador poControlador, IPlugInContexto poContexto) {
        moControlador = poControlador;
        moContexto=poContexto;
        moDatosGenerales=poDatosGenerales;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsAvisos) && plIndex!=null && plIndex.length>0){
            JFXConfigGlobal.getInstancia().inicializarFX();
            moControlador.getConsulta().getList().setIndex(plIndex[0]);
            JFieldDefs loCampos = moControlador.getConsulta().getList().getFields();
            String lsIden = msGrupo;
            String lsnombre = msGrupo;
            for(int i = 0 ; i < loCampos.size(); i++){
                if(loCampos.get(i).getPrincipalSN()){
                    lsIden += "-" + loCampos.get(i).getString() ;
                }
            }
            if(malCamposNombre!=null){
                for(int i = 0 ; i < malCamposNombre.length; i++){
                    lsnombre += "-" + loCampos.get(malCamposNombre[i]).getString() ;
                }
            }
            
            IServerServidorDatos loServer = moDatosGenerales.getServer();
            if(loServer == null){
                loServer = moControlador.getConsulta().getList().moServidor;
            }
            JT2GUIXEVENTOS loEventos = new JT2GUIXEVENTOS(
                    moDatosGenerales, lsIden, msCalendario, lsnombre, msUsuario);
            loEventos.getParametros().setPlugInPasados(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        loEventos.mostrarFormPrinciFrame();
                    } catch (Exception ex) {
                        JMsgBox.mensajeErrorYLog(JAccionesGUIxAvisos.this, ex);
                    }
                }
            });
            
            
        }
    }
    
}
