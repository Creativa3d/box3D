package utilesGUIx.formsGenericos.edicion;

public abstract class JPanelEdicionNavegadorAbstract extends JPanelEdicionAbstract implements IFormEdicionNavegador {
    private static final long serialVersionUID = 1L;


    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
      JPanelGENERALBASE.setBloqueoControlesContainer(this, pbBloqueo);
    }

}
