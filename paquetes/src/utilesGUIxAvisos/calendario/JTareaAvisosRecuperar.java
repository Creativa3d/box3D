/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import ListDatos.IListDatosFiltro;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import java.util.TimerTask;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;

/**
 * lanza notificaciones cuando se cumplen los avisos
 * @author eduardo
 */
public class JTareaAvisosRecuperar extends TimerTask {
    private JTEEGUIXAVISOS moAVISOS;
    private JDateEdu moFechaConsulta;
    private final Planificador moPadre;
    private final JDatosGenerales moDatosGenerales;
    private final boolean mbPantalla;
    private final boolean mbEmail;
    private final boolean mbSMS;

    JTareaAvisosRecuperar(JDatosGenerales poDatosGenerales, Planificador po, boolean pbPantalla, boolean pbEmail, boolean pbSMS){
        moPadre = po;
        mbPantalla=pbPantalla;
        mbEmail=pbEmail;
        mbSMS=pbSMS;
        moDatosGenerales=poDatosGenerales;
    }
    public synchronized JTEEGUIXAVISOS getAvisos() throws CloneNotSupportedException{
        JTEEGUIXAVISOS loAvisos = null;
        if(moAVISOS!=null && moAVISOS.moveFirst()){
            loAvisos = new JTEEGUIXAVISOS(moAVISOS.moList.moServidor);
            loAvisos.moList = moAVISOS.moList.Clone();
        }
        return loAvisos;
    }
        
    @Override
    public void run() {
        try{
            synchronized(this){
                //OJO: optimizar
                if(moDatosGenerales.getFechaBorrado()!=null && moFechaConsulta.compareTo(moDatosGenerales.getFechaBorrado())==JDateEdu.mclFechaMenor){
                    moAVISOS = null;
                }            
                //recuperamos los AVISOS iniciales, no avisados y fecha desde no mas de 3 dias
                if (moAVISOS == null) {
                    moFechaConsulta = new JDateEdu();
                    moAVISOS = new JTEEGUIXAVISOS(moDatosGenerales.getServer());
                    moAVISOS.recuperarFiltradosNormal(getFiltroEvento(), false);
                }
            }
            //recuperamos AVISOS recientemente modificados
            JTEEGUIXAVISOS loAVISOSModif = new JTEEGUIXAVISOS(moDatosGenerales.getServer());
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, loAVISOSModif.lPosiFECHAMODIFICACION, moFechaConsulta.toString());
            loFiltro.addCondicionAND(getFiltroEvento());
            moFechaConsulta = new JDateEdu();
            loAVISOSModif.recuperarFiltradosNormal(loFiltro, false);
            synchronized(this){
                if (loAVISOSModif.moveFirst()) {
                    do {
                        if (!moAVISOS.moList.buscar(JListDatos.mclTIgual
                                , new int[]{
                                    moAVISOS.lPosiCALENDARIO, moAVISOS.lPosiCODIGO
                                }, new String[]{
                                    loAVISOSModif.getCALENDARIO().getString(), loAVISOSModif.getCODIGO().getString()
                                })) {
                            moAVISOS.moList.addNew();
                        }
                        moAVISOS.moList.getFields().cargar(loAVISOSModif.moList.moFila());
                        moAVISOS.moList.update(false);
                        moAVISOS.moList.moFila().setTipoModif(JListDatos.mclNada);
                    } while (loAVISOSModif.moveNext());
                }
            }
            
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, "La  recuperacion de avisos nuevos del calendario han sido desactivados", IMostrarPantalla.mclMensajeError, null);
            this.cancel();
        }

    }

    private IListDatosFiltro getFiltroEvento() {

        JListDatosFiltroConj loFiltro2 = new JListDatosFiltroConj();
        loFiltro2.addCondicionAND(JListDatos.mclTIgual, moAVISOS.lPosiAVISADOSN, "");
        loFiltro2.addCondicionOR(JListDatos.mclTIgual, moAVISOS.lPosiAVISADOSN, JListDatos.mcsFalse);

        JListDatosFiltroConj loFiltro3 = new JListDatosFiltroConj();
        JDateEdu loDate = new JDateEdu();
        JDateEdu loDate3mas = new JDateEdu();
        loDate3mas.add(loDate.mclDia, 3);
        loFiltro3.addCondicionAND(JListDatos.mclTMenorIgual, moAVISOS.lPosiFECHACONCRETA, loDate3mas.toString());

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(loFiltro2);
        loFiltro.addCondicionAND(loFiltro3);
        
        if(!mbPantalla){
            JListDatosFiltroConj loFiltroAux = new JListDatosFiltroConj();
            loFiltroAux.addCondicionAND(JListDatos.mclTIgual, moAVISOS.lPosiPANTALLASN, JListDatos.mcsFalse);
            loFiltroAux.addCondicionOR(JListDatos.mclTIgual, moAVISOS.lPosiPANTALLASN, "");
            loFiltro.addCondicionAND(loFiltroAux);
        }
        if(!mbEmail){
            JListDatosFiltroConj loFiltroAux = new JListDatosFiltroConj();
            loFiltroAux.addCondicionAND(JListDatos.mclTIgual, moAVISOS.lPosiEMAIL, "");
            loFiltro.addCondicionAND(loFiltroAux);
        }
        if(!mbSMS){
            JListDatosFiltroConj loFiltroAux = new JListDatosFiltroConj();
            loFiltroAux.addCondicionAND(JListDatos.mclTIgual, moAVISOS.lPosiTELF, "");
            loFiltro.addCondicionAND(loFiltroAux);
        }
        
        
        return loFiltro;

    }
}
