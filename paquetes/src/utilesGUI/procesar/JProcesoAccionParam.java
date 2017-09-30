/*
 * JProcesoAccionParam.java
 *
 * Created on 27 de agosto de 2008, 14:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUI.procesar;


public class JProcesoAccionParam {
    private boolean mbTieneCancelado = true;
    private Object moTag;
    /** Creates a new instance of JProcesoAccionParam */
    public JProcesoAccionParam() {
    }

    public boolean isTieneCancelado() {
        return mbTieneCancelado;
    }

    public void setTieneCancelado(boolean mbTieneCancelado) {
        this.mbTieneCancelado = mbTieneCancelado;
    }

    /**
     * @return the moTag
     */
    public Object getTag() {
        return moTag;
    }

    /**
     * @param moTag the moTag to set
     */
    public void setTag(Object moTag) {
        this.moTag = moTag;
    }
    
}
