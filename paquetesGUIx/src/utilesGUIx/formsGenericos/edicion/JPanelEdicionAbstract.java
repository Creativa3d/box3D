package utilesGUIx.formsGenericos.edicion;

import ListDatos.JSTabla;
import javax.swing.JPanel;
public abstract class JPanelEdicionAbstract extends JPanel implements IFormEdicion {
    private static final long serialVersionUID = 1L;
    protected JFormEdicionParametros moParametros = new JFormEdicionParametros();

    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    public JSTabla getTabla() {
        return null;
    }
    
    

//    public void recuperarDatos() throws Exception {
//    }
//    
    public boolean validarDatos() throws Exception {
        return true;
    }
}
