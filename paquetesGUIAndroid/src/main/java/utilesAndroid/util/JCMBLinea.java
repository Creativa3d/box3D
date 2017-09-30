/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesAndroid.util;

/**
 *
 * @author eduardo
 */
public class JCMBLinea {
    private String msDescripcion=null;
    private String msclave=null;

    public JCMBLinea(String psDescrip) {
        msDescripcion=psDescrip;
        msclave=psDescrip;
    }
    public JCMBLinea(String psDescrip, String psClave) {
        msDescripcion = psDescrip;
        msclave = psClave;
    }

    
    @Override
    public String toString() {
        return getDescripcion();
    }

    /**
     * @return the msDescripcion
     */
    public String getDescripcion() {
        return msDescripcion;
    }

    /**
     * @param msDescripcion the msDescripcion to set
     */
    public void setDescripcion(String msDescripcion) {
        this.msDescripcion = msDescripcion;
    }

    /**
     * @return the msclave
     */
    public String getclave() {
        return msclave;
    }

    /**
     * @param msclave the msclave to set
     */
    public void setclave(String msclave) {
        this.msclave = msclave;
    }
    
    
}
