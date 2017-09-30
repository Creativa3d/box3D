/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX;

import ListDatos.ECampoError;
import ListDatos.estructuraBD.JFieldDef;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author eduardo
 */
public class JFieldControl  implements  IFieldControl {
    private final IFieldControl moField;

    public JFieldControl(TextInputControl poText, JFieldDef poCampo){
        moField = new JFieldConTextField(poText, poCampo);
    }

    public JFieldControl(CheckBox poCheck, JFieldDef poCampo){
        moField = new JFieldConCheckBox(poCheck, poCampo);
    }
    public JFieldControl(ComboBox poCheck, JFieldDef poCampo){
        moField = new JFieldConComboBox(poCheck, poCampo);
    }
    public JFieldControl(ComboBox poCheck, JFieldDef[] poCampos){
        moField = new JFieldConComboBox(poCheck, poCampos);
    }
    public IFieldControl getFieldControlReal(){
        return moField;
    }
    
    @Override
    public void setValueTabla(Object poValor) throws Exception {
        moField.setValueTabla(poValor);
    }

    @Override
    public Object getValueTabla() {
        return moField.getValueTabla();
    }

    @Override
    public boolean getTextoCambiado() {
        return moField.getTextoCambiado();
    }

    @Override
    public void mostrarDatosBD() {
        moField.mostrarDatosBD();
    }

    @Override
    public void establecerDatosBD() throws ECampoError {
        moField.establecerDatosBD();
    }

    @Override
    public JFieldDef getCampo() {
        return moField.getCampo();
    }
    
}
