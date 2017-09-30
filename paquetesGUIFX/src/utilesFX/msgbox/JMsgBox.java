/*
 * JMsgBox.java
 *
 * Created on 21 de diciembre de 2004, 13:15
 */

package utilesFX.msgbox;


import java.io.PrintStream;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utiles.CadenaLargaOut;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;

/**Objeto para presentar mensajes*/
public class JMsgBox {
    private static int mclINFORMATION_MESSAGE=0;
    private static int mclERROR_MESSAGE=1;

//    /** Creates a new instance of JMsgBox */
//    private JMsgBox() {
//    }
    /**
     * presentar mensaje de información modal
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeInformacion(final Object poPadre, final String psMensaje){
        mensajeInformacion(poPadre, psMensaje, true);
    }
    /**
     * presentar mensaje de información NO MODAL
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeInformacionNoModal(final Object poPadre, final String psMensaje){
        mensajeInformacion(poPadre, psMensaje, false);
    }
    
    /**
     * presentar mensaje de información
     * @param poPadre padre
     * @param psMensaje mensaje
     * @param pbModal si es modal el mensaje
     */
    public static void mensajeInformacion(final Object poPadre, final String psMensaje, final boolean pbModal){
        
        if(Platform.isFxApplicationThread()){
            showMessageDialog(poPadre, psMensaje, "Información", mclINFORMATION_MESSAGE,null, pbModal, null);
        }else{
            try {
                Platform.runLater(() -> {
                    showMessageDialog(poPadre, psMensaje, "Información", mclINFORMATION_MESSAGE,null, pbModal, null);
                });
            } catch (Throwable ex) {
                JDepuracion.anadirTexto("JMsgBox", ex);
            }
        }
    }
    /**
     * presentar mensaje de error y tb lo envia al log, el grupo es el nombre de la clase poPadre
     * @param poPadre padre
     */
    public static void mensajeErrorYLog(final Object poPadre, final Throwable e){
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
    public static void mensajeErrorYLog(final Object poPadre, final Throwable e, final String psGrupo){
        JDepuracion.anadirTexto(psGrupo, e);
        mensajeError(poPadre, e);
    }
    /**
     * presentar mensaje de error y tb lo envia al log
     * @param poPadre padre
     * @param psMensaje mensaje
     * @param psGrupo Grupo del log
     */
    public static void mensajeErrorYLog(final Object poPadre, final String psMensaje, final String psGrupo){
        JDepuracion.anadirTexto(psGrupo, psMensaje);
        mensajeError(poPadre, psMensaje);
    }
    /**
     * presentar mensaje de error y tb lo envia al log
     * @param poPadre padre
     */
    public static void mensajeErrorYLog(final Object poPadre, final Throwable e, final Runnable poOk){
        JDepuracion.anadirTexto(poPadre!=null ? poPadre.getClass().getName():"", e);
        showMessageDialog(poPadre, JCadenas.isVacio(e.getMessage()) ? e.toString() : e.getMessage(), "Error", mclERROR_MESSAGE, getDetallesError(e), true, poOk);
    }
    
    private static String getDetallesError(Throwable e){
        String lsResult = null;
        if(e!=null){
            try {
                CadenaLargaOut loOut = new CadenaLargaOut();
                e.printStackTrace(new PrintStream(loOut, true, "ISO-8859-1"));
                lsResult=loOut.moStringBuffer.toString();
            } catch (Throwable ex) {
                JDepuracion.anadirTexto(JMsgBox.class.getName(), ex);
            }
        }
        return lsResult;
    }
    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeError(final Object poPadre,final String psMensaje, final Throwable e){
        if(Platform.isFxApplicationThread()){
            showMessageDialog(poPadre, psMensaje, "Error", mclERROR_MESSAGE, getDetallesError(e), true, null);
        }else{
            try {
                Platform.runLater(new Runnable() {
                    public void run() {
                        showMessageDialog(poPadre, psMensaje, "Error", mclERROR_MESSAGE, getDetallesError(e), true, null);
                    }
                });
            } catch (Throwable ex) {
                JDepuracion.anadirTexto("JMsgBox", ex);
            }
        }
    }
    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeError(final Object poPadre,final String psMensaje){
        mensajeError(poPadre, psMensaje, null);
    }
    private static void showMessageDialog(final Object poPadre, final String psCadena, final String psTitulo, final int ERROR_MESSAGE, final String psDetalles, final boolean pbModal, final Runnable poOk ) {
        if (Platform.isFxApplicationThread()) {
            showMessageDialogInterno(poPadre, psCadena, psTitulo, ERROR_MESSAGE, psDetalles, pbModal, poOk);
        } else {
            Platform.runLater(() -> {
                try {
                    showMessageDialogInterno(poPadre, psCadena, psTitulo, ERROR_MESSAGE, psDetalles, pbModal, poOk);
                } catch (Throwable e) {
                    JDepuracion.anadirTexto(JMsgBox.class.getName(), e);
                }
            });
        }        
    }
    private static void showMessageDialogInterno(Object poPadre, String psCadena, String psTitulo, int ERROR_MESSAGE, String psDetalles, boolean pbModal, final Runnable poOk ) {
        if(psCadena==null){
            psCadena="";
        }
        if(psCadena.length()>1000 || (psDetalles!=null && !psDetalles.equals("")) || !pbModal){
            try {
                final Stage dialog = new Stage();
                JFormMensaje loControlador = new JFormMensaje();
                loControlador.setDatos(psCadena, psDetalles, dialog);
                loControlador.setOk(poOk);
                
                Scene s = new Scene(loControlador);
                s.getStylesheets().add(
                        JFXConfigGlobal.getInstancia().getEstilo()
                            );
                
                dialog.setScene(s);
                dialog.setTitle(psTitulo);
                try{
                    dialog.setAlwaysOnTop(true);
                }catch(Throwable e){
                    JDepuracion.anadirTexto(JMsgBox.class.getName(), e);
                }
                if(pbModal){
                    dialog.showAndWait();
                } else {
                    dialog.show();
                }
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JMsgBox.class.getName(), ex);
            }
        }else{
            JOptionPaneFX.showMessageDialog(poPadre, msCadenaFormateada(psCadena), psTitulo, ERROR_MESSAGE, poOk);
        }
    }
    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param e Error
     */
    public static void mensajeError(Object poPadre, Throwable e){
        String lsMensaje = e.getMessage();
        if(lsMensaje == null || lsMensaje.length() < 5){
            lsMensaje = e.toString();
        }
        mensajeError(poPadre, lsMensaje, e);
    }
    private static String msCadenaFormateada(String psMensaje){
        if(psMensaje==null){
            return "Error";
        }else{
            if(psMensaje.toUpperCase().indexOf("<HTML>")>=0){
                return psMensaje;
            }else{
//                String lsResult = "";
//                Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
//                float formatWidth = (float) 600;
//                RompeLineas loRompeLineas = new RompeLineas(psMensaje);
//                int lLen = psMensaje.length();
//                while (loRompeLineas.mlComienzoNueva < lLen) {
//                    String lsLinea = loRompeLineas.getNuevaLinea(graphics2D, formatWidth);
//
//                    if(lsLinea!= null && !lsLinea.equals("")){
//                        lsResult += lsLinea + '\n';
//                    }
//                }
                return psMensaje;
            }
        }
    }

}
