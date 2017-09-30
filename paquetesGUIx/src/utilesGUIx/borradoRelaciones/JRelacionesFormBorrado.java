/*
 * JRelacionesFormBorrado.java
 *
 * Created on 26 de noviembre de 2004, 10:14
 */

package utilesGUIx.borradoRelaciones;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import utiles.JDepuracion;
import utilesBD.relaciones.*;

/**Muestra un form. con los registros relacionados*/
public class JRelacionesFormBorrado implements IFormBorrado {
    JFormBorrado moForm;
    
//    /** Creates a new instance of JRelacionesFormBorrado */
//    public JRelacionesFormBorrado() {
//    }
    
    public void setTabla(JRelacionTablaRegistros poTabla, utiles.JComunicacion poComu) {
        moForm = new JFormBorrado(new java.awt.Frame(), true);
        moForm.setTabla(poTabla, poComu);
    }
    
    public void show(){
        if(SwingUtilities.isEventDispatchThread()){
            moForm.show();
        }else{
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        moForm.show();
                    }
                });
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
        
    }

    
}
