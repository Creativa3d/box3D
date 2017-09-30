/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;


// Represents items in the list that can be selected
public class JCheckListItem {

    private String label;
    private String clave;
    private boolean isSelected = false;

    public JCheckListItem(String psClave, String label) {
        this.label = label;
        clave = psClave;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String toString() {
        return label;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    public IFilaDatos getFila(){
        return new JFilaDatosDefecto(getClave());
    }
}
