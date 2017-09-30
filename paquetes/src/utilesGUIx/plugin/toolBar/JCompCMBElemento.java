/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.toolBar;

import java.io.Serializable;

public class JCompCMBElemento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String msCodigo;
    public String msDescripcion;

    public JCompCMBElemento(){
    }
    public JCompCMBElemento(String psDescr, String psCod){
        msCodigo = psCod;
        msDescripcion = psDescr;

    }

    public String toString() {
        return msDescripcion;
    }
    
    
}
