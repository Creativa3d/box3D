/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.controlProcesos;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import utiles.JDepuracion;
import utilesGUI.procesar.IProcesoAccion;
import utilesGUIx.JGUIxConfigGlobal;

public class JProcesoElemento implements IProcesoElemento {

    private IProcesoAccion moAccion;
    private Throwable moError = null;
    private MiTarea moThread;
    private final JProcesoManejador moManejador;
    private final boolean mbConMostrarForm;

    public JProcesoElemento(IProcesoAccion poAccion, boolean pbConMostrarForm, JProcesoManejador poManejador) {
        moAccion = poAccion;
        moManejador = poManejador;
        mbConMostrarForm = pbConMostrarForm;
    }

    public IProcesoAccion getProceso() {
        return moAccion;
    }

    public void arrancar() {
        
         Handler mHandler = new Handler(Looper.myLooper()!=null ? Looper.myLooper() :  Looper.getMainLooper());
         mHandler.post(new Runnable() {
             public void run() {
                moThread = new MiTarea();
                if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
                    moThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    moThread.execute();
                }                
             }
         });
                
    }

    private class MiTarea extends AsyncTask<String, Float, Integer> {
        private ProgressDialog moDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mbConMostrarForm){
          
                if(moAccion.getParametros().getTag()==null){
                    moAccion.getParametros().setTag((Context)JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getContext());
                }
                moDialog = new ProgressDialog((Context)moAccion.getParametros().getTag());
                moDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                moDialog.setMessage(moAccion.getTitulo()+ "...");
                moDialog.setCancelable(moAccion.getParametros().isTieneCancelado());
                moDialog.setMax(100);
 
                moDialog.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        moAccion.setCancelado(true);
                        moDialog.dismiss();
                    }
                });

                moDialog.setProgress(0);
                moDialog.show();                
            }
        }

        protected Integer doInBackground(String... urls) {
            try {
                moAccion.procesar();
            } catch (Error ex) {
                moError = ex;
                JDepuracion.anadirTexto(JProcesoElemento.class.getName(), getError());
            } catch (Throwable ex) {
                moError = ex;
                JDepuracion.anadirTexto(JProcesoElemento.class.getName(), getError());
            } finally {
                moManejador.procesoFinalizado(JProcesoElemento.this, getError());
                moAccion.finalizar();
            }
            return new Integer(0);
        }

        @Override
        protected void onProgressUpdate(Float... valores) {
        	if(moAccion.getTituloRegistroActual()!=null){
        		moDialog.setMessage(moAccion.getTitulo()+ "-"+moAccion.getTituloRegistroActual());
        	}
        }
        
        @Override
        protected void onPostExecute(Integer bytes) {
            super.onPostExecute(bytes);
            if(mbConMostrarForm && moDialog!=null){
                moDialog.dismiss();
            }
            if (getError() != null) {
                moAccion.mostrarError(getError());
            } else {
                moAccion.mostrarMensaje("Proceso terminado");
            }
        }
    }

    public void arrancarSwing() {
        arrancar();
    }

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
