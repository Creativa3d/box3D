/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import ListDatos.IResultado;
import java.util.TimerTask;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

/**
 *
 * @author eduardo
 */
public class JTareaEventosLanzar  extends TimerTask {
    public static final String mcsAvisadoSN = "AvisadoSN";
    private final JTareaEventosRecuperar moTareaEventos;
    private final JDatosGenerales moDatosGenerales;
    
    public JTareaEventosLanzar (JDatosGenerales poDatosGenerales, JTareaEventosRecuperar poEventos){
        moTareaEventos=poEventos;
        moDatosGenerales=poDatosGenerales;
    }

    @Override
    public void run() {
        try{
            JTEEGUIXEVENTOS moEventos = moTareaEventos.getEventos();
            //vemos notificaciones
            if (moEventos!=null && moEventos.moveFirst()) {
                JDateEdu loDate = new JDateEdu();
                //añadimos x segundos para que el intervalo no sea muy alto
                loDate.add(loDate.mclSegundos, 20);
                boolean lbContinue = false;
                do {
                    lbContinue = false;
                    //si existe registro (seguridad)
                    // y fecha desde < fecha ahora
                    // y tarea no terminada y no avisado
                    if (moEventos.moList.size()>0 
                        &&  moEventos.getFECHADESDE().getDateEdu().compareTo(loDate) == JDateEdu.mclFechaMenor
                        && !moEventos.getEVENTOSN().getBoolean()
                        && !moEventos.getField(mcsAvisadoSN).getBoolean()
                            ) {
                        try {
                            //marcamos como avisada
                            moEventos.getField(mcsAvisadoSN).setValue(true);
                            IResultado loResult = moEventos.update(false);
                            if(loResult.getBien()){
                                JTEEGUIXEVENTOS loAux = new JTEEGUIXEVENTOS(moEventos.getList().moServidor);
                                loAux.moList.add(moEventos.moList.moFila());
                                loAux.moList.moveFirst();
                                moDatosGenerales.mandarAvisosListener(loAux);
//no las quitamos de la lista pq es un estado temporal
//                                //la quitamos de la lista
//                                moEventos.moList.borrar(false);
//                                lbContinue=moEventos.moList.moveFirst();
                            }else{
                                throw new Exception(loResult.getMensaje());
                            }
                        } catch (Throwable ex) {
                            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(null, ex, null);
                        }
                    }
                } while (lbContinue || moEventos.moveNext());
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, "Los eventos del calendario han sido desactivados", IMostrarPantalla.mclMensajeError, null);
            this.cancel();
        }
    }
    
}
