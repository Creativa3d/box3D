package utilesGUIx;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class JButtonGroupCZ extends ButtonGroup {

    private static final long serialVersionUID = 1L;

    @Override
    public void setSelected(ButtonModel model, boolean selected) {
        // Se sobreescribe el metodo setSelected para permitir que no este seleccionado ninguno de los botones del grupo
        if (selected) {
            super.setSelected(model, selected);
        } else {
            clearSelection();
        }
    }
}
