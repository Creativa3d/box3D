/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.toolBar;

import java.awt.ItemSelectable;
import java.awt.event.ItemListener;
import utilesGUIx.JComboBoxCZ;

public class JCompCMB implements ICompCMB, ItemSelectable {
    private static final long serialVersionUID = 1L;
    
    private JComboBoxCZ moCombo;

    public JCompCMB(){
    }
    public JCompCMB(JComboBoxCZ poCombo){
        moCombo = poCombo;
    }

    public String getText() {
        return moCombo.getValueTabla().toString();
    }

    public String getCodigo() {
        return moCombo.getFilaActual().toString();
    }

    public boolean setCodigo(String psValor){
        return moCombo.mbSeleccionarClave(psValor);
    }

    public Object[] getSelectedObjects() {
        return new Object[]{getCodigo()};
    }

    public void addItemListener(ItemListener l) {
    }

    public void removeItemListener(ItemListener l) {
    }
}
