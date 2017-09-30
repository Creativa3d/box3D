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
import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;

/**
 *
 * @author eduardo
 */
public class JTareaAvisosLanzar  extends TimerTask {
    private final JTareaAvisosRecuperar moTareaAvisos;
    private final JDatosGenerales moDatosGenerales;
    
    public JTareaAvisosLanzar (JDatosGenerales poDatosGenerales, JTareaAvisosRecuperar poEventos){
        moTareaAvisos=poEventos;
        moDatosGenerales=poDatosGenerales;
    }

    @Override
    public void run() {
        try{
            JTEEGUIXAVISOS loAvisos = moTareaAvisos.getAvisos();
            //vemos notificaciones
            if (loAvisos!=null && loAvisos.moveFirst()) {
                JDateEdu loDate = new JDateEdu();
                //añadimos x segundos para que el intervalo no sea muy alto
                loDate.add(loDate.mclSegundos, 20);
                boolean lbContinue = false;
                do {
                    lbContinue = false;
                    //si existe registro (seguridad)
                    // y fecha desde < fecha ahora
                    // y no avisado
                    if (loAvisos.moList.size()>0 
                        &&  loAvisos.getFECHACONCRETA().getDateEdu().compareTo(loDate) == JDateEdu.mclFechaMenor
                        && !loAvisos.getAVISADOSN().getBoolean()
                            ) {
                        try {
                            //actualizamos estado
                            loAvisos.getAVISADOSN().setValue(true);
                            IResultado loResult = loAvisos.update(true);
                            if(loResult.getBien()){
                                JTEEGUIXAVISOS loAux = new JTEEGUIXAVISOS(loAvisos.getList().moServidor);
                                loAux.moList.add(loAvisos.moList.moFila());
                                loAux.moList.moveFirst();
                                moDatosGenerales.mandarAvisosListener(loAux);                                

                                //la quitamos de la lista
                                loAvisos.moList.borrar(false);
                                lbContinue=loAvisos.moList.moveFirst();
                            }else{
                                throw new Exception(loResult.getMensaje());
                            }
                        } catch (Throwable ex) {
                            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(null, ex, null);
                        }
                    }
                } while (lbContinue || loAvisos.moveNext());
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, "Los avisos del calendario han sido desactivados", IMostrarPantalla.mclMensajeError, null);
            this.cancel();
        }
    }
    
}
