/*
 * JMsgBox.java
 *
 * Created on 21 de diciembre de 2004, 13:15
 */

package utilesGUIx.msgbox;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import utiles.CadenaLargaOut;
import utiles.JDepuracion;

/**Objeto para presentar mensajes*/
public class JMsgBox {
    public static final int mclTipoSWING=0;
    public static final int mclTipoAWT=1;
    
    private static int mlTipo=mclTipoSWING;
    
//    /** Creates a new instance of JMsgBox */
//    private JMsgBox() {
//    }
    /**
     * presentar mensaje de información modal
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeInformacion(final Component poPadre, final String psMensaje){
        mensajeInformacion(poPadre, psMensaje, true);
    }
    /**
     * presentar mensaje de información NO MODAL
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeInformacionNoModal(final Component poPadre, final String psMensaje){
        mensajeInformacion(poPadre, psMensaje, false);
    }
    
    /**
     * presentar mensaje de información
     * @param poPadre padre
     * @param psMensaje mensaje
     * @param pbModal si es modal el mensaje
     */
    public static void mensajeInformacion(final Component poPadre, final String psMensaje, final boolean pbModal){
        if(getTipo() == mclTipoSWING){
            if(SwingUtilities.isEventDispatchThread()){
                showMessageDialog(poPadre, psMensaje, "Información", JOptionPane.INFORMATION_MESSAGE,null, pbModal);
            }else{
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            showMessageDialog(poPadre, psMensaje, "Información", JOptionPane.INFORMATION_MESSAGE,null, pbModal);
                        }
                    });
                } catch (Throwable ex) {
                    JDepuracion.anadirTexto("JMsgBox", ex);
                }
            }
        }else{
            utilesGUI.msgbox.JDialogo.showDialog(new Frame(), psMensaje);
        }
    }
    /**
     * presentar mensaje de error y tb lo envia al log, el grupo es el nombre de la clase poPadre
     * @param poPadre padre
     */
    public static void mensajeErrorYLog(final Component poPadre, final Throwable e){
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
    public static void mensajeErrorYLog(final Component poPadre, final Throwable e, final String psGrupo){
        JDepuracion.anadirTexto(psGrupo, e);
        mensajeError(poPadre, e);
    }
    /**
     * presentar mensaje de error y tb lo envia al log
     * @param poPadre padre
     * @param psMensaje mensaje
     * @param psGrupo Grupo del log
     */
    public static void mensajeErrorYLog(final Component poPadre, final String psMensaje, final String psGrupo){
        JDepuracion.anadirTexto(psGrupo, psMensaje);
        mensajeError(poPadre, psMensaje);
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
    public static void mensajeError(final Component poPadre,final String psMensaje, final Throwable e){
        mensajeError(poPadre, psMensaje, e, true);
    }

    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param psMensaje mensaje
     * @param e
     * @param pbModal
     */
    public static void mensajeError(final Component poPadre,final String psMensaje, final Throwable e, boolean pbModal){
        if(getTipo() == mclTipoSWING){
            if(SwingUtilities.isEventDispatchThread()){
                showMessageDialog(poPadre, psMensaje, "Error", JOptionPane.ERROR_MESSAGE, getDetallesError(e), pbModal);
            }else{
                try {
                    if(pbModal){
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                showMessageDialog(poPadre, psMensaje, "Error", JOptionPane.ERROR_MESSAGE, getDetallesError(e), true);
                            }
                        });
                    } else {
                        
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                showMessageDialog(poPadre, psMensaje, "Error", JOptionPane.ERROR_MESSAGE, getDetallesError(e));
                            }
                        });
                    }
                } catch (Throwable ex) {
                    JDepuracion.anadirTexto("JMsgBox", ex);
                }
            }
        }else{
            utilesGUI.msgbox.JDialogo.showDialog(new Frame(), psMensaje);
        }
    }
    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param psMensaje mensaje
     */
    public static void mensajeError(final Component poPadre,final String psMensaje){
        mensajeError(poPadre, psMensaje, null);
    }
    private static void showMessageDialog(Component poPadre, String psCadena, String psTitulo, int ERROR_MESSAGE, String psDetalles) {
        showMessageDialog(poPadre, psCadena, psTitulo, ERROR_MESSAGE, psDetalles, true);
    }
    private static void showMessageDialog(Component poPadre, String psCadena, String psTitulo, int ERROR_MESSAGE, String psDetalles, boolean pbModal) {
        if(psCadena==null){
            psCadena="";
        }
        if(psCadena.length()>1000 || (psDetalles!=null && !psDetalles.equals("")) || !pbModal){
            JFormMensaje loForm = new JFormMensaje(new Frame(), pbModal, psCadena, psDetalles);
            loForm.setTitle(psTitulo);
            loForm.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(poPadre, msCadenaFormateada(psCadena), psTitulo, ERROR_MESSAGE);
        }
    }
    public static void mensajeError(Component poPadre, Throwable e){
        mensajeError(poPadre, e, true);
    }

    /**
     * presentar mensaje de error
     * @param poPadre padre
     * @param e Error
     */
    public static void mensajeError(Component poPadre, Throwable e, boolean pbModal){
        String lsMensaje = e.getMessage();
        if(lsMensaje == null || lsMensaje.length() < 5){
            lsMensaje = e.toString();
        }
        mensajeError(poPadre, lsMensaje, e, pbModal);
    }
    private static String msCadenaFormateada(String psMensaje){
        if(psMensaje==null){
            return "Error";
        }else{
            if(psMensaje.toUpperCase().indexOf("<HTML>")>=0){
                return psMensaje;
            }else{
                String lsResult = "";
                Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
                float formatWidth = (float) 600;
                RompeLineas loRompeLineas = new RompeLineas(psMensaje);
                int lLen = psMensaje.length();
                while (loRompeLineas.mlComienzoNueva < lLen) {
                    String lsLinea = loRompeLineas.getNuevaLinea(graphics2D, formatWidth);

                    if(lsLinea!= null && !lsLinea.equals("")){
                        lsResult += lsLinea + '\n';
                    }
                }
                return lsResult;
            }
        }
    }

    public static int getTipo() {
        return mlTipo;
    }

    public static void setTipo(int aMlTipo) {
        mlTipo = aMlTipo;
    }
    
}
