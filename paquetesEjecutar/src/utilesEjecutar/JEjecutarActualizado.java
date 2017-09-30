

package utilesEjecutar;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utiles.JDepuracion;
import utilesEjecutar.gui.JFormCargador;


public class JEjecutarActualizado {
    public static void main(final String[] pasARRAY){


        //nivel depuracion= informacion para sacar todo lo q hace
        JDepuracion.mlNivelDepuracion = JDepuracion.mclINFORMACION;
        //creamos la fabrica de clases
        final JAbstractFactoryEjecutar loFac = new JAbstractFactoryEjecutar();
        //creamos un coordinador
        final JControladorCoordinadorEjecutar loCoor = new JControladorCoordinadorEjecutar();

        //creamos el form
        final Thread loThread2 = new Thread(new Runnable() {
            public void run() {
                
                try {
                    UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (Throwable ex) {}

                        
                JFormCargador loForm = new JFormCargador();
                loForm.setCoordinador(loCoor);
            }
        });
        loThread2.start();

        //ejecutamos
        final Thread loThread = new Thread(new Runnable() {
            public void run() {
                //lo ejecutamos
                if(pasARRAY.length>0){
                    for(int i = 1 ; i < pasARRAY.length;i++){
                        String lsParam = pasARRAY[i];
                        int lPosi = lsParam.indexOf('=');
                        if(lPosi>=0 && lPosi < (lsParam.length()-1)){
                            loCoor.addVariable(
                                lsParam.substring(0, lPosi),
                                lsParam.substring(lPosi+1, lsParam.length()));
                        }else{
                            loCoor.addVariable(lsParam, "");
                        }
                    }
                    loCoor.ejecutarCompleto(pasARRAY[0], loFac);
                }else{
                    loCoor.ejecutarCompleto("ejecucion.xml", loFac);
                }
            }
        });
        loThread.start();


    }
}
