/*
 * JProcesoAccion.java
 *
 * Created on 17 de junio de 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUI.procesar;

import java.awt.Frame;
import utiles.JDepuracion;

public class JProcesoAccion implements Runnable {
    private IProcesoAccion moAccion;
    private Throwable moError = null;
    private int mlSegundosRefresco = 1;

    /** Creates a new instance of JProcesoAccion */
    public JProcesoAccion(final IProcesoAccion poAccion) {
        moAccion = poAccion;
    }
    
    public JProcesoAccion(final IProcesoAccion poAccion, final int plSegundosRefresco) {
        moAccion = poAccion;
        mlSegundosRefresco = plSegundosRefresco;
    }
    
    public void setSegundosRefresco(int plSegundosrefresco){
        mlSegundosRefresco = plSegundosrefresco;
    }

    public void run() {
        JProcesar loProcesar = new JProcesar(new Frame(), moAccion.getTitulo(), moAccion.getNumeroRegistros());
        try{
            loProcesar.start();
            loProcesar.incrementar(0, "Inicializando");
            Thread lothread = new Thread(new Runnable() {
                public void run() {
                    try {
                        moAccion.procesar();
                    } catch (Throwable ex) {
                        moError = ex;
                        JDepuracion.anadirTexto(JProcesoAccion.class.getName(), moError);
                    } finally{
                        if(moError!=null){
                            moAccion.mostrarError(moError);
                        }else{
                            moAccion.mostrarMensaje("Proceso terminado");
                        }
                        moAccion.finalizar();
                    }
                }
            });

            lothread.start();

            while (!moAccion.isFin() && moError == null){
                synchronized (this){
                    try {
                        wait(mlSegundosRefresco * 1000);
                    } catch (InterruptedException ex) {
                    }
                }

                if(moError != null){
                    loProcesar.establecer(
                            moAccion.getNumeroRegistro(), 
                            moAccion.getNumeroRegistros(), 
                            moError.toString());
                }else{
                    loProcesar.establecer(
                            moAccion.getNumeroRegistro(), 
                            moAccion.getNumeroRegistros(), 
                            moAccion.getTituloRegistroActual());
                    moAccion.setCancelado(loProcesar.getCancelado());
                }
            }

        }finally{
            
            loProcesar.cerrar();
        }
        
        
        
        
    }
    
    
    
}
