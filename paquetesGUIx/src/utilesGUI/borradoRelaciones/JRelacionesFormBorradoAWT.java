/*
 * JRelacionesFormBorrado.java
 *
 * Created on 26 de noviembre de 2004, 10:14
 */

package utilesGUI.borradoRelaciones;

import utilesBD.relaciones.*;

/**Muestra un form. con los registros relacionados en jvm 1.1*/
public class JRelacionesFormBorradoAWT implements IFormBorrado {
    JFormBorradoAWT moForm;
    
//    /** Creates a new instance of JRelacionesFormBorrado */
//    public JRelacionesFormBorradoAWT() {
//    }

    public void setTabla(JRelacionTablaRegistros poTabla, utiles.JComunicacion poComu) {
        moForm = new JFormBorradoAWT(new java.awt.Frame(), true);
        moForm.setTabla(poTabla, poComu);
    }
    public void show(){
        moForm.show();
    }

    
}
