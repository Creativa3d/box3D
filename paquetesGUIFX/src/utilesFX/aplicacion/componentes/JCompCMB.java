/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.aplicacion.componentes;


import javafx.scene.control.ComboBox;
import utilesGUIx.plugin.toolBar.ICompCMB;
import utilesGUIx.plugin.toolBar.JCompCMBElemento;

public class JCompCMB implements ICompCMB {
    private static final long serialVersionUID = 1L;
    
    private ComboBox<JCompCMBElemento> moCombo;

    public JCompCMB(){
    }
    public JCompCMB(ComboBox<JCompCMBElemento> poCombo){
        moCombo = poCombo;
    }

    public String getText() {
        return ((JCompCMBElemento)moCombo.getSelectionModel().getSelectedItem()).msDescripcion;
    }

    public String getCodigo() {
        return ((JCompCMBElemento)moCombo.getSelectionModel().getSelectedItem()).msCodigo;
    }

    public boolean setCodigo(String psValor){
        JCompCMBElemento loResult = null;
        for (JCompCMBElemento loElem : moCombo.getItems())  {
            if(loElem.msCodigo.equals(psValor)){
                loResult = loElem;
            }                
        }          
        moCombo.getSelectionModel().select(loResult);
        return loResult!=null;
    }

    public Object[] getSelectedObjects() {
        return new Object[]{getCodigo()};
    }

}
