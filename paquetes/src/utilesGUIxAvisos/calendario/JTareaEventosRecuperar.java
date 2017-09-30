/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import ListDatos.IListDatosFiltro;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.estructuraBD.JFieldDef;
import java.util.TimerTask;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

/**
 * lanza notificaciones cuando se cumplen los eventos
 * @author eduardo
 */
public class JTareaEventosRecuperar extends TimerTask {
    private JTEEGUIXEVENTOS moEventos;
    private JDateEdu moFechaConsulta;
    private final Planificador moPadre;
    private final JDatosGenerales moDatosGenerales;

    JTareaEventosRecuperar(JDatosGenerales poDatosGenerales, Planificador po){
        moPadre = po;
        moDatosGenerales=poDatosGenerales;
    }
    
    public synchronized JTEEGUIXEVENTOS getEventos() throws CloneNotSupportedException{
        JTEEGUIXEVENTOS loEventos = null;
        if(moEventos!=null && moEventos.moveFirst()){
            loEventos = new JTEEGUIXEVENTOS (moEventos.moList.moServidor);
            loEventos.moList = moEventos.moList.Clone();
        }
        return loEventos;
    }
    
    @Override
    public void run() {
        try {
            synchronized(this){
                //OJO: optimizar
                if(moDatosGenerales.getFechaBorrado()!=null 
                        && moFechaConsulta.compareTo(moDatosGenerales.getFechaBorrado())==JDateEdu.mclFechaMenor){
                    moEventos = null;
                }
                //recuperamos los eventos iniciales, no avisados y fecha desde no mas de 3 dias
                if (moEventos == null) {
                    moFechaConsulta = new JDateEdu();
                    moEventos = new JTEEGUIXEVENTOS(moDatosGenerales.getServer());
                    moEventos.recuperarFiltradosNormal(getFiltroEvento(), false);
                    moEventos.getList().getFields().addField(new JFieldDef(JTareaEventosLanzar.mcsAvisadoSN));
                }
            }
            //recuperamos eventos recientemente modificados
            JTEEGUIXEVENTOS loEventosModif = new JTEEGUIXEVENTOS(moDatosGenerales.getServer());
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, loEventosModif.lPosiFECHAMODIFICACION, moFechaConsulta.toString());
            loFiltro.addCondicionAND(getFiltroEvento());
            moFechaConsulta = new JDateEdu();
            loEventosModif.recuperarFiltradosNormal(loFiltro, false);
            synchronized(this){
                if (loEventosModif.moveFirst()) {
                    moDatosGenerales.lanzarEventosEdit(loEventosModif);
                    do {
                        if (!moEventos.moList.buscar(JListDatos.mclTIgual
                                , new int[]{
                                    moEventos.lPosiCALENDARIO, moEventos.lPosiCODIGO
                                }, new String[]{
                                    loEventosModif.getCALENDARIO().getString(), loEventosModif.getCODIGO().getString()
                                })) {
                            moEventos.moList.addNew();
                        }
                        moEventos.moList.getFields().cargar(loEventosModif.moList.moFila());
                        moEventos.moList.update(false);
                        moEventos.moList.moFila().setTipoModif(JListDatos.mclNada);
                    } while (loEventosModif.moveNext());
                }
            }
            
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
//            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, "La recuperacion de eventos nuevos del calendario han sido desactivados", IMostrarPantalla.mclMensajeError, null);
            this.cancel();
        }

    }

    private IListDatosFiltro getFiltroEvento() {

        JListDatosFiltroConj loFiltro2 = new JListDatosFiltroConj();
        loFiltro2.addCondicionAND(JListDatos.mclTIgual, moEventos.lPosiEVENTOSN, "");
        loFiltro2.addCondicionOR(JListDatos.mclTIgual, moEventos.lPosiEVENTOSN, JListDatos.mcsFalse);

        JListDatosFiltroConj loFiltro3 = new JListDatosFiltroConj();
        JDateEdu loDate = new JDateEdu();
        JDateEdu loDate3mas = new JDateEdu();
        loDate3mas.add(loDate.mclDia, 3);
        loFiltro3.addCondicionAND(JListDatos.mclTMenorIgual, moEventos.lPosiFECHADESDE, loDate3mas.toString());

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(loFiltro2);
        loFiltro.addCondicionAND(loFiltro3);
        return loFiltro;

    }
}
