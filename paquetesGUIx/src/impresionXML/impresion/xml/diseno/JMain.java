/*
 * Login.java
*
* Creado el 2/5/2009
*/

package impresionXML.impresion.xml.diseno;


import utilesGUIx.aplicacion.*;
import utiles.JDepuracion;

public class JMain {
    
    /** Creates a new instance of JMain */
    public JMain() {
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            JParametrosAplicacion loParam = new JParametrosAplicacion(
                    "impresionXML",
                    null,
                    new String[]{
                        "impresionXML.impresion.xml.diseno.JPlugInPrincipal",

                    },
                    null,
                    null
                    );

            JDatosGeneralesP.getDatosGenerales().inicializar(loParam);
//            JFormPrincipal.ponerLookAndFeelReal(
//                    JDatosGeneralesP.getDatosGenerales().getLookAndFeelObjeto(),
//                    JDatosGeneralesP.getDatosGenerales().getLookAndFeel(),
//                    JDatosGeneralesP.getDatosGenerales().getLookAndFeelTema());

//            JPanelDESIGN.mcdResolucionPantalla = Toolkit.getDefaultToolkit().getScreenResolution(); 
            JDatosGeneralesP.getDatosGenerales().mostrarFormPrincipal();
        } catch (Throwable ex) {
            JDepuracion.anadirTexto("JMain",ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(null, ex);
        }
    }

}
