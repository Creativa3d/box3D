/*
 * Login.java
*
* Creado el 2/5/2009
 */
package main;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;
import utilesGUIx.aplicacion.*;

public class JMain {

    /**
     * Creates a new instance of JMain
     */
    public JMain() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            if (args != null && args.length > 0 && "swing".equalsIgnoreCase(args[0])) {
                JParametrosAplicacion loParam = new JParametrosAplicacion(
                        "CompartadorBD",
                        null,
                        new String[]{
                            "main.JPlugInPrincipal"
                        },
                        null,
                        null
                );
                JDatosGeneralesP.setDatosGenerales(new JDatosGenerales());
                JDatosGeneralesP.getDatosGenerales().inicializar(loParam);
                JDatosGeneralesP.getDatosGenerales().mostrarFormPrincipal();
            } else {
                utilesFX.aplicacion.JParametrosAplicacion loParam = new utilesFX.aplicacion.JParametrosAplicacion(
                        "CompartadorBD",
                        null,
                        new String[]{
                            "main.JPlugInPrincipal"
                        },
                        null,
                        null
                );
                loParam.setImagenFondo("/images/fondo.jpg");
                loParam.setColorFondo(Color.CHOCOLATE);
                loParam.setImagenIcono(new javafx.scene.image.Image(JMain.class.getResourceAsStream("/utilesFX/images/dialog-information16.png")));
                JDatosGeneralesP.setDatosGenerales(new JDatosGeneralesFX());
                JDatosGeneralesP.getDatosGenerales().inicializar(loParam);
                JFXConfigGlobal.getInstancia().inicializarFX();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JDatosGeneralesP.getDatosGenerales().mostrarFormPrincipal();
                        } catch (Exception ex) {
                            JDepuracion.anadirTexto("JMain", ex);
                            utilesGUIx.msgbox.JMsgBox.mensajeError(null, ex);
                        }
                    }
                });

            }

        } catch (Throwable ex) {
            JDepuracion.anadirTexto("JMain", ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(null, ex);
        }
    }

}
