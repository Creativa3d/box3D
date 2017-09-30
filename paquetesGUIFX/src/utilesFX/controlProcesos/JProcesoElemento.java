/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.controlProcesos;

import utiles.JDepuracion;
import utilesGUI.procesar.IProcesoAccion;
import utilesGUIx.controlProcesos.IProcesoElemento;
import utilesGUIx.controlProcesos.JProcesoManejador;


public class JProcesoElemento implements IProcesoElemento {
    private final IProcesoAccion moAccion;
    private Throwable moError = null;
    private Thread moThread;
    private final JProcesoManejador moManejador;
    private final boolean mbConMostrarForm;
    
    public JProcesoElemento(IProcesoAccion poAccion, boolean pbConMostrarForm, JProcesoManejador poManejador){
        moAccion = poAccion;
        moManejador=poManejador;
        mbConMostrarForm=pbConMostrarForm;
    }
    public IProcesoAccion getProceso() {
        return moAccion;
    }
    public void arrancar() {
        moThread = new Thread(new Runnable() {
            public void run() {
                try {
                    moAccion.procesar();
                } catch (Error ex) {
                    moError = ex;
                    JDepuracion.anadirTexto(JProcesoElemento.class.getName(),getError());
                } catch (Throwable ex) {
                    moError = ex;
                    JDepuracion.anadirTexto(JProcesoElemento.class.getName(),getError());
                } finally{
                    moManejador.procesoFinalizado(JProcesoElemento.this,getError());
                    if(getError()!=null){
                        moAccion.mostrarError(getError());
                    }else{
                        moAccion.mostrarMensaje("Proceso terminado");
                    }
                    moAccion.finalizar();
                }
            }
        });

        moThread.start();
    }
//
//    public void arrancarSwing() {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    moAccion.procesar();
//                } catch (Error ex) {
//                    moError = ex;
//                    JDepuracion.anadirTexto(JProcesoElemento.class.getName(),getError());
//                } catch (Throwable ex) {
//                    moError = ex;
//                    JDepuracion.anadirTexto(JProcesoElemento.class.getName(),getError());
//                } finally{
//                    moManejador.procesoFinalizado(JProcesoElemento.this,getError());
//                    if(getError()!=null){
//                        moAccion.mostrarError(getError());
//                    }else{
//                        moAccion.mostrarMensaje("Proceso terminado");
//                    }
//                    moAccion.finalizar();
//                }
//            }
//        });
//    }

    
    /**
     * @return the mbConMostrarForm
     */
    public boolean isConMostrarForm() {
        return mbConMostrarForm;
    }

    /**
     * @return the moError
     */
    public Throwable getError() {
        return moError;
    }

    
}
