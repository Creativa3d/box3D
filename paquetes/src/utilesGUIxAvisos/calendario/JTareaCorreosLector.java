/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import ListDatos.IServerServidorDatos;
import java.util.TimerTask;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.tablasExtend.JTEECUENTASCORREO;

/**
 * lanza notificaciones cuando se cumplen los eventos
 * @author eduardo
 */
public class JTareaCorreosLector extends TimerTask {

    private final Planificador moPadre;
    private final JDatosGenerales moDatosGenerales;
    private JDateEdu moDate;
    public JTareaCorreosLector(JDatosGenerales poDatosGenerales, Planificador po){
        moPadre = po;
        moDatosGenerales=poDatosGenerales;
    }
    
    
    @Override
    public synchronized void run() {
        try {
            ejecutar(null, moDatosGenerales.getServer());
        } catch (Throwable ex) {  
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(JTareaCorreosLector.this,  ex, null);
            this.cancel();
        }

    }
    
    public synchronized void ejecutar(String psIdentificador, IServerServidorDatos poServer) throws Throwable{
        if(moDate == null || JDateEdu.diff(JDateEdu.mclSegundos, new JDateEdu(), moDate)>10 ){
            IServerServidorDatos loServer = poServer;
            JTEECUENTASCORREO loCorreos = new JTEECUENTASCORREO(loServer);
            loCorreos.recuperarTodosNormalSinCache();

            if(loCorreos.moveFirst()){
                do{
                    if(psIdentificador==null 
                            || loCorreos.getIdentificadorCorreo().equalsIgnoreCase(psIdentificador) ){
                        JGUIxAvisosCorreo loCorreo = loCorreos.getCorreo();
                        if(!JCadenas.isVacio(loCorreo.getLeer().getCarpetaCorreo())){
                            loCorreo.getLeer().recibirYGuardar();
                        }
                    }
                }while(loCorreos.moveNext());
            }
            moDate = new JDateEdu();
        }
    }

}
