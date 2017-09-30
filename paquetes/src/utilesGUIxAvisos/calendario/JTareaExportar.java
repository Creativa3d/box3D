/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import java.util.TimerTask;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

/**
 * exporta (a gmail) los eventos de la cola general
 * @author eduardo
 */
public class JTareaExportar extends TimerTask {
    
    private boolean mbPrimeraVez = true;
    private final JDatosGenerales moDatosGenerales;
    
    private JDateEdu moDateImportar=null;
    
    public JTareaExportar(JDatosGenerales poDatosGenerales){
        moDatosGenerales=poDatosGenerales;
    }
    
    private void update(IFilaDatos loFila) throws Exception{
        JTEEGUIXEVENTOS loE = new JTEEGUIXEVENTOS(moDatosGenerales.getServer());
        loE.moList.add(loFila);
        loE.moveFirst();
        
        JTEEGUIXCALENDARIO loC = JTEEGUIXCALENDARIO.getTabla(loE.getCALENDARIO().getString(), loE.moList.moServidor);
        JDatosGenerales.sincronizar(loC, loE);
    }
    private void borrar(IFilaDatos loFila) throws Exception{
        JTEEGUIXEVENTOS loE = new JTEEGUIXEVENTOS(moDatosGenerales.getServer());
        loE.moList.add(loFila);
        loE.moveFirst();

        JTEEGUIXCALENDARIO loC = JTEEGUIXCALENDARIO.getTabla(loE.getCALENDARIO().getString(), loE.moList.moServidor);
        JDatosGenerales.sincronizarBorrar(loC, loE);
        
    }

    @Override
    public void run() {
        if(mbPrimeraVez){
            //recuperamos eventos pendientes y los exportamos, solo para eventos mayores que hoy
            mbPrimeraVez=false;
            try {
                JTEEGUIXEVENTOS loEventosModif = new JTEEGUIXEVENTOS(moDatosGenerales.getServer());
                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEEGUIXEVENTOS.lPosiIDENTIFICADOREXTERNO, "");
                JDateEdu loDate = new JDateEdu();
                loDate.setHora(0);
                loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, JTEEGUIXEVENTOS.lPosiFECHAHASTA, loDate.toString());
                loEventosModif.recuperarFiltradosNormal(loFiltro, false);
                int lMaxGolpe = 10;
                if(loEventosModif.moveFirst()){
                    do{
                        update(loEventosModif.moList.moFila());
                        lMaxGolpe--;
                    }while(loEventosModif.moveNext() && lMaxGolpe>0);
                }
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
        IListaElementos loAdd = new JListaElementos();
        //recuepramos fila a subir y borramos
        JDatosGenerales.EventosIntent loEvento = moDatosGenerales.getEventoSigYBorrar();
        int lMaxGolpe = 10;
        while(loEvento!=null && lMaxGolpe>0){
            
            try {
                //subimos
                if(loEvento.moFila.getTipoModif()==JListDatos.mclBorrar){
                    borrar(loEvento.moFila);
                }else{
                    update(loEvento.moFila);
                }
            } catch (ClassCastException ex) {
                //si error incrementamos intentos y add a la cola, solo 3 intentps
                loEvento.intentos++;
                if(loEvento.intentos<3){
                    loAdd.add(loEvento);
                }
                JDepuracion.anadirTexto(getClass().getName(), ex);
            } catch (Exception ex) {
                //si error incrementamos intentos y add a la cola, solo 3 intentps
                loEvento.intentos++;
                if(loEvento.intentos<3){
                    loAdd.add(loEvento);
                }
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
            lMaxGolpe--;
            if(lMaxGolpe>0){
                loEvento = moDatosGenerales.getEventoSigYBorrar();
            }
        }
        //los add despues pq se mezclan
        for(int i = 0 ; i < loAdd.size(); i++){
            moDatosGenerales.addEvento((JDatosGenerales.EventosIntent)loAdd.get(i));
        }
        
    }
}
