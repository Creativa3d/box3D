package utilesFX.formsGenericos.edicion;

import javafx.scene.Parent;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;

public abstract class JPanelEdicionNavegadorAbstract extends JPanelEdicionAbstract implements IFormEdicionNavegador {
    private static final long serialVersionUID = 1L;


    @Override
    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        setBloqueoControlesContainer(this, pbBloqueo);
    }

    /**
     *
     * @param poParent
     * @param pbBloqueo
     */
    public void setBloqueoControlesContainer(Parent poParent, boolean pbBloqueo){
        poParent.setDisable(pbBloqueo);
    }    

}
