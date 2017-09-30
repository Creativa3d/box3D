/*
 * JProcesar.java
 *
 * Created on 24 de enero de 2004, 21:43
 */

package utilesGUI.procesar;

import utiles.JDepuracion;

/**Barra de proceso con formulario aparte*/
public class JProcesar implements Runnable {
    JProcesoForm moProceso=null;
    java.awt.Frame moparent;
    /**título*/
    public String msTitulo;
    /**Número de registros*/
    public long mlNumeroReg;

//    private Thread moThread;
    
    /**
     * Creates a new instance of JProcesar
     * @param parent padre
     * @param psTitulo titulo
     * @param plNumeroReg número de registros totales
     */
    public JProcesar(java.awt.Frame parent, String psTitulo, long plNumeroReg) {
        super();
        moparent=parent;
        msTitulo=psTitulo;
        mlNumeroReg=plNumeroReg;
        moProceso=new JProcesoForm((moparent == null ? new java.awt.Frame() : moparent), false);
    }
    
    public synchronized void start() {
        moProceso.init(msTitulo, mlNumeroReg);
        moProceso.show();
    }
    /**
     * Establece una barra dinamica que se mueve continuamente
     */
    public synchronized void setBarraDinamica(boolean pbBoolean, double pdDuracionIntervalo, int plNumeroIntervalos){
        try{
            if(moProceso!=null){
                moProceso.setBarraDinamica(pbBoolean, pdDuracionIntervalo, plNumeroIntervalos);
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
        }
    }
    /**
     * Incrementa Número y pone la cadena actual
     * @param plNumero número
     * @param psCadenaRegActual cadena
     */
    public synchronized void incrementar(long plNumero, String psCadenaRegActual){
        try{
            if(moProceso!=null){
                moProceso.incrementar(plNumero, psCadenaRegActual);
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
        }

    }
    /**
     * establece la barra de proceso
     * @param plNumeroActual Número actual
     * @param plTotal total de registros
     * @param psCadenaRegActual Cadena a visualizar
     */
    public synchronized  void establecer(long plNumeroActual, int plTotal, String psCadenaRegActual){
        try{
            if(moProceso!=null){
                moProceso.establecer(plNumeroActual, plTotal, psCadenaRegActual);
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
        }
    }
    
    /**
     * si ha sido cancelado
     * @return valor
     */
    public synchronized boolean getCancelado(){
        boolean lbCancel = false;
        try{
            if(moProceso==null){
                lbCancel=false;
            }else{
                lbCancel=moProceso.mbCancelado;
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
        }
        return lbCancel;
    }
    /**Cierra el form. del proceso*/
    public synchronized void cerrar(){
        try{
            if(moProceso!=null){
                moProceso.cerrar();
            }
            moProceso=null;
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
        }
        System.gc();
    }
    /**No hace nada por ahora Abre el form. de proceso, en un thread aparte porque si no no refresca bien*/
    public void run() {
    }
    
}
