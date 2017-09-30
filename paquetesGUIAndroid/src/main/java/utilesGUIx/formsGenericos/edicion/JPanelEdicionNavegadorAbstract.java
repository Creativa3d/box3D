package utilesGUIx.formsGenericos.edicion;

import utilesGUIx.Rectangulo;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import android.content.Context;

public abstract class JPanelEdicionNavegadorAbstract extends JPanelEdicionAbstract implements IFormEdicionNavegador {
    private static final long serialVersionUID = 1L;

    public JPanelEdicionNavegadorAbstract(Context context) {
        super(context);
    }

    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
    }

}
