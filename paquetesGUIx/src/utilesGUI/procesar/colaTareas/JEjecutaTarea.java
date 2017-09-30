/*
 * JEjecutaTarea.java
 *
 * Created on 28 de marzo de 2005, 9:54
 */

package utilesGUI.procesar.colaTareas;

import utiles.JDepuracion;
import utilesGUI.procesar.IProcesoAccion;

/**Clase que ejecuta las tareas en thread aparte*/
public class JEjecutaTarea extends Thread {
    private JListaTareas moListaTareas;
    private boolean mbFinalizado = false;
    
    /** Creates a new instance of JEjecutaTarea */
    public JEjecutaTarea(JListaTareas poListaTareas) {
        moListaTareas = poListaTareas;
    }
    void finalizar(){
        mbFinalizado = true;
    }
    public void run(){
        while(!moListaTareas.getFinalizado() && !mbFinalizado){
            IProcesoAccion loTarea = moListaTareas.getTarea();
            if(loTarea!=null){
                try{
                    loTarea.procesar();
                }catch(Throwable e){
                    JDepuracion.anadirTexto(JDepuracion.mclCRITICO, getClass().getName(),e);
                    loTarea.mostrarError(e);
                }
//                moListaTareas.setFinalizado(!JDatosGeneralesP.getDatosGenerales().getControladorProceso().esEstadoCorrecto());
//                mbFinalizado=!JDatosGeneralesP.getDatosGenerales().getControladorProceso().esEstadoCorrecto();
            }
        }
    }
    
}
