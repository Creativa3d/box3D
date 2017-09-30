/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesCRM;

import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import utilesCRM.tablasExtend.JTEECRMEMAILYNOTAS;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

/**
 *
 * @author eduardo
 */
public class JDatosGeneralesCRM {

    private boolean mbHabilitado;
    
    public void setHabilitadoCRM(boolean pbHabi){
        mbHabilitado = pbHabi;
    }
    
    public void guardarNotaCorreo(JMensaje poMensaje, IServerServidorDatos poServer) throws Exception{
        if(mbHabilitado){
            JTEECRMEMAILYNOTAS loNota = JTEECRMEMAILYNOTAS.getTablaPorCorreo(poMensaje.getAtributos().get(JTEEGUIXMENSAJESBD.msCTabla).toString(),poServer);
            if(!loNota.moveFirst()){
                loNota.addNew();
                loNota.valoresDefecto();
            }
            loNota.getTIPO().setValue(JTEECRMEMAILYNOTAS.mcsCorreo);
            loNota.getCODIGOCONTACTO().setValue(poMensaje.getAtributos().get(loNota.getCODIGOCONTACTONombre()));
            loNota.getCODIGOUSUARIO().setValue(poMensaje.getUsuario());
            loNota.getCODIGONEGOCIACION().setValue(poMensaje.getGrupo());
            loNota.getGUIXMENSAJESSENDCOD().setValue(poMensaje.getAtributos().get(JTEEGUIXMENSAJESBD.msCTabla));
            loNota.getASUNTO().setValue(poMensaje.getAsunto());
            loNota.getDESCRIPCION().setValue(poMensaje.getTexto());

            IResultado loResult = loNota.guardar();
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            }
        }
    }
    
    public void borrarNotaCorreo(JTEEGUIXMENSAJESBD poMensajeBD) throws Exception{
        if(mbHabilitado){
            JTEECRMEMAILYNOTAS loNotas = JTEECRMEMAILYNOTAS.getTablaPorCorreo(
                    poMensajeBD.getCODIGO().getString(), poMensajeBD.moList.moServidor);
            if(loNotas.moveFirst()){
                IResultado loResult = loNotas.borrar();
                if(!loResult.getBien()){
                    throw new Exception(loResult.getMensaje());
                }
            }
        }
    }

    public void guardarNotaTarea(JTEEGUIXEVENTOS poTarea) throws Exception {
        if(mbHabilitado){
            JTEECRMEMAILYNOTAS loNota = JTEECRMEMAILYNOTAS.getTablaPorTarea(
                    poTarea.getCALENDARIO().getString()
                    , poTarea.getCODIGO().getString()
                    , poTarea.moList.moServidor);
            if(!loNota.moveFirst()){
                loNota.addNew();
                loNota.valoresDefecto();
            }
            loNota.getTIPO().setValue(JTEECRMEMAILYNOTAS.mcsTarea);
            loNota.getCODIGOCONTACTO().setValue("");
            loNota.getCODIGOUSUARIO().setValue(poTarea.getUSUARIO().getString());
            loNota.getCODIGONEGOCIACION().setValue(poTarea.getGRUPO().getString());
            loNota.getGUIXMENSAJESSENDCOD().setValue("");
            loNota.getASUNTO().setValue(poTarea.getNOMBRE().getString());
            loNota.getDESCRIPCION().setValue(poTarea.getTEXTO().getString());
            loNota.getCODIGOCALENDARIO().setValue(poTarea.getCALENDARIO().getString());
            loNota.getCODIGOEVENTO().setValue(poTarea.getCODIGO().getString());

            IResultado loResult = loNota.guardar();
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            }
        }
    }
    
    public void borrarNotaTarea(JTEEGUIXEVENTOS poTarea) throws Exception{
        if(mbHabilitado){
            JTEECRMEMAILYNOTAS loNota = JTEECRMEMAILYNOTAS.getTablaPorTarea(
                    poTarea.getCALENDARIO().getString()
                    , poTarea.getCODIGO().getString()
                    , poTarea.moList.moServidor);
            if(loNota.moveFirst()){
                IResultado loResult = loNota.borrar();
                if(!loResult.getBien()){
                    throw new Exception(loResult.getMensaje());
                }
            }
        }
    }
}
