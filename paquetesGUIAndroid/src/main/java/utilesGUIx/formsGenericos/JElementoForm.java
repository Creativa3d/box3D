/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;


import java.io.Serializable;

/**
 *
 * @author eduardo
 */
public class JElementoForm  implements Serializable {
    private String msTipo;
    private JMostrarPantallaParam moParam;

    public JElementoForm(String psTipo, JMostrarPantallaParam poParam) {
        msTipo=psTipo;
        moParam=poParam;
    }


    /**
     * @return the msTipo
     */
    public String getTipo() {
        return msTipo;
    }

    /**
     * @return the moParam
     */
    public JMostrarPantallaParam getParam() {
        return moParam;
    }

    /**
     * @param moParam the moParam to set
     */
    public void setParam(JMostrarPantallaParam moParam) {
        this.moParam = moParam;
    }
}
