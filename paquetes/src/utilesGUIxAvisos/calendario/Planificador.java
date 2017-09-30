/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import java.util.Timer;

public class Planificador {
    //intervalo eventos/avisos 3 minutos 
    private final int mclIntervalo = 3 * 60 * 1000;
    //intervalo export gmail 20 sg
    private final int mclIntervaloExport = 20 * 1000;
    //intervalo import gmail 10 minutos
    private final int mclIntervaloImport = 10 * 60 * 1000;
    //intervalo lanzar eventos 20 sg
    private final int mclIntervaloLanzar = 20 * 1000;
    //intervalo correo lector 30 min.
    private final int mclIntervaloCorreoLector = 30 * 60 * 1000;
    
    private Timer timer;
    private JTareaEventosRecuperar moEventosRecuperar;
    private JTareaAvisosRecuperar moAvisosRecuperar;
    private JTareaExportar moExportar;
    private JTareaEventosLanzar moEventosLanzar;
    private JTareaAvisosLanzar moAvisosLanzar;
    private final JDatosGenerales moDatosGenerales;
    private JTareaImportar moImportar;
    private JTareaCorreosLector moCorreoLector;

    public Planificador(JDatosGenerales poDatos) {
        moDatosGenerales = poDatos;
    }
    /**
     * Cancelamos el timer de las tareas
     */
    public void cancelarTimer(){
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer=null;
            
        }
    }
    /**
     * Arrancamos el timer de las tareas
     */
    public void arrancarTimer(){
        cancelarTimer();
        //se crea un planificador que planificara x tareas
        timer = new Timer();
    }
    /**
     * Arrancamos todo
     */
    public void arrancarTodo(){
        arrancarTodo(true, false, false);
    }
    public void arrancarTodo(boolean pbPantalla, boolean pbEmail, boolean pbSMS){
        arrancarTimer();
        arrancarGMailExportar();
        arrancarEventos();
        arrancarAvisos(pbPantalla, pbEmail, pbSMS);
    }
    
    /**
     * Arrancamos exportacion gmail
     */
    public void arrancarGMailExportar(){
        if(moExportar!=null){
            moExportar.cancel();
            moExportar=null;
        }        
        //tarea que sube los eventos a gmail
        moExportar = new JTareaExportar(moDatosGenerales);
        timer.schedule(moExportar, mclIntervaloExport , mclIntervaloExport);
    }
    /**
     * Arrancamos importacion gmail
     */
    public void arrancarGMailImportar(){
        if(moImportar!=null){
            moImportar.cancel();
            moImportar=null;
        }        
        //tarea que sube los eventos a gmail
        moImportar = new JTareaImportar(moDatosGenerales);
        timer.schedule(moImportar, mclIntervaloImport , mclIntervaloImport);
    }
    
    /**
     * Arrancamos los eventos
     */
    public void arrancarEventos(){
        if(moEventosRecuperar!=null){
            moEventosRecuperar.cancel();
            
        }
        if(moEventosLanzar!=null){
            moEventosLanzar.cancel();
            
        }
        moEventosRecuperar = new JTareaEventosRecuperar(moDatosGenerales,this );
        moEventosLanzar = new JTareaEventosLanzar(moDatosGenerales,moEventosRecuperar);
        
        //la Tarea1 se ejecuta pasado 1 intervalo y luego periódicamente cada intervalo
        timer.schedule(moEventosRecuperar, mclIntervaloLanzar, mclIntervalo);
        timer.schedule(moEventosLanzar, mclIntervaloLanzar, mclIntervaloLanzar);
    }
    /**
     * Arrancamos los avisos
     * @param pbPantalla
     * @param pbEmail
     * @param pbSMS
     */
    public void arrancarAvisos(boolean pbPantalla, boolean pbEmail, boolean pbSMS){
        if(moAvisosRecuperar!=null){
            moAvisosRecuperar.cancel();
        }
        if(moAvisosLanzar!=null){
            moAvisosLanzar.cancel();
            
        }
        moAvisosRecuperar = new JTareaAvisosRecuperar(moDatosGenerales,this,pbPantalla,pbEmail,pbSMS);
        moAvisosLanzar= new JTareaAvisosLanzar(moDatosGenerales,moAvisosRecuperar);
        //la Tarea1 se ejecuta pasado 1 intervalo y luego periódicamente cada intervalo
        timer.schedule(moAvisosRecuperar, mclIntervaloLanzar, mclIntervalo);
        timer.schedule(moAvisosLanzar, mclIntervaloLanzar, mclIntervaloLanzar);
    }
    
    
    /**
     * Arrancamos importacion gmail
     */
    public void arrancarCorreoLector(){
        if(moCorreoLector!=null){
            moCorreoLector.cancel();
            moCorreoLector=null;
        }        
        //tarea que sube los eventos a gmail
        moCorreoLector = new JTareaCorreosLector(moDatosGenerales, this);
        timer.schedule(moCorreoLector, mclIntervaloCorreoLector, mclIntervaloCorreoLector);
    }

    public JTareaCorreosLector getCorreoLector(){
        return moCorreoLector;
    }
}
