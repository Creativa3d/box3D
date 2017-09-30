/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import java.util.TimerTask;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;

/**
 * exporta (a gmail) los eventos de la cola general
 * @author eduardo
 */
public class JTareaImportar extends TimerTask {
    
    private final JDatosGenerales moDatosGenerales;
    
    private JDateEdu moDateImportar=null;
    
    public JTareaImportar(JDatosGenerales poDatosGenerales){
        moDatosGenerales=poDatosGenerales;
    }
    

    @Override
    public void run() {
        try {
            JTEEGUIXCALENDARIO loCalendarios = new JTEEGUIXCALENDARIO(moDatosGenerales.getServer());
            loCalendarios.recuperarTodosNormalSinCache();
            if(loCalendarios.moveFirst()){
                do{
                    if(!loCalendarios.getIDENTIFICADOREXTERNO().isVacio()){
                        try {
                            JDatosGenerales.sincronizarImportar(loCalendarios, moDateImportar);
                        } catch (Exception ex1) {
                            JDepuracion.anadirTexto(JTareaImportar.class.getName(), ex1);
                        }
                    }
                }while(loCalendarios.moveNext());
            }
            moDateImportar=new JDateEdu();


        } catch (Exception ex) {
            JDepuracion.anadirTexto(JTareaImportar.class.getName(), ex);
        }
    }
}
