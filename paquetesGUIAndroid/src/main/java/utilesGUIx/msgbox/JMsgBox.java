/*
 * JMsgBox.java
 *
 * Created on 21 de diciembre de 2004, 13:15
 */

package utilesGUIx.msgbox;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import utiles.JDepuracion;
import utilesAndroid.util.R;

/**Objeto para presentar mensajes*/
public class JMsgBox {
    private static int mclINFORMATION_MESSAGE=0;
    private static int mclERROR_MESSAGE=1;

    
//    /** Creates a new instance of JMsgBox */
//    private JMsgBox() {
//    }
    /**
     * presentar mensaje de informacion
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeInformacion(final Context poPadre, final String psMensaje){
        showMessageDialog(poPadre, psMensaje, "Información", mclINFORMATION_MESSAGE);
    }
    /**
     * presentar mensaje de error y tb lo envia al log, el grupo es el nombre de la clase poPadre
     * @param poPadre padre
     */
    public static void mensajeErrorYLog(final Context poPadre, final Throwable e){
        if(poPadre==null){
            mensajeErrorYLog(poPadre, e, "");
        }else{
            mensajeErrorYLog(poPadre, e, poPadre.getClass().getName());
        }
    }
    /**
     * presentar mensaje de error y tb lo envia al log
     * @param poPadre padre
     * @param psGrupo Grupo del log
     */
    public static void mensajeErrorYLog(final Context poPadre, final Throwable e, final String psGrupo){
        JDepuracion.anadirTexto(psGrupo, e);
        mensajeError(poPadre, e);
    }
    /**
     * presentar mensaje de error y tb lo envia al log
     * @param poPadre padre
     * @param psMensaje mensaje
     * @param psGrupo Grupo del log
     */
    public static void mensajeErrorYLog(final Context poPadre, final String psMensaje, final String psGrupo){
        JDepuracion.anadirTexto(psGrupo, psMensaje);
        mensajeError(poPadre, psMensaje);
    }
    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeError(final Context poPadre,final String psMensaje){
        showMessageDialog(poPadre, psMensaje, "Error", mclERROR_MESSAGE);
    }
    private static void showMessageDialog(Context poPadre, String psCadena, String psTitulo, int plERROR_MESSAGE) {
    	try{
          AlertDialog loA =  new AlertDialog.Builder(poPadre)
                .setIcon( plERROR_MESSAGE == mclERROR_MESSAGE ? R.drawable.error: R.drawable.documentinfo)
                .setTitle(psTitulo)
                .setMessage(psCadena)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    }
                }).create();
          loA.show();
		}catch(Throwable e){
			JDepuracion.anadirTexto(JMsgBox.class.getName(), e);
		}

    }
    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param e Error
     */
    public static void mensajeError(Context poPadre, Throwable e){
        String lsMensaje = e.getMessage();
        if(lsMensaje == null || lsMensaje.length() < 5){
            lsMensaje = e.toString();
        }
        mensajeError(poPadre, lsMensaje);
    }
    
    public static void mensajeFlotante(final Context poPadre, final String psMensaje){
    	try{
    		Toast.makeText(poPadre, psMensaje, Toast.LENGTH_LONG).show();        
    	}catch(Throwable e){
    		JDepuracion.anadirTexto(JMsgBox.class.getName(), e);
    	}
    }

    public static void mostrarOpcion(
            Context poActividad
            , String psTitulo
            , final Runnable poSI
            , final Runnable poNO
            ){
        AlertDialog.Builder loA = new AlertDialog.Builder(poActividad);
        loA.setIcon(R.drawable.queue);
        loA.setTitle("Pregunta?");
        loA.setMessage(psTitulo);
        loA.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(poSI!=null){
                        poSI.run();
                    }
//                    dialog.cancel();
                }
                });
        loA.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(poNO!=null){
                        poNO.run();
                    }
//                    dialog.cancel();
                }
            });
        AlertDialog loaa = loA.create();
        loaa.show();
    }
    
}
