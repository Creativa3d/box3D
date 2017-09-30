package utilesFX.formsGenericos.edicion;
import ListDatos.ECampoError;
import ListDatos.JSTabla;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utilesFX.IFieldControl;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.ITextBD;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;

public abstract class JPanelEdicionAbstract extends GridPane implements IFormEdicion {
    private static final long serialVersionUID = 1L;
    protected JFormEdicionParametros moParametros = new JFormEdicionParametros();

    private List<ITextBD> moListaITextBD = new ArrayList();
    
    @Override
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    @Override
    public JSTabla getTabla() {
        return null;
    }
    
    public List<ITextBD> getListaITextBD(){
        return moListaITextBD;
    }
    public void addFieldControl(IFieldControl poFieldControl){
        moListaITextBD.add(poFieldControl);
    }
    
    public IFieldControl getFieldControl(String psNombreCampo){
        IFieldControl loResult = null;
        for(ITextBD loT : moListaITextBD){
            if(IFieldControl.class.isAssignableFrom(loT.getClass())){
                IFieldControl loFi = (IFieldControl) loT;
                if(loFi.getCampo()!=null && loFi.getCampo().getNombre().equalsIgnoreCase(psNombreCampo)){
                    loResult=loFi;
                }
            }
        }
        return loResult;
    }
    
    public void mostrarDatosBD(){
        for(ITextBD loBD: moListaITextBD ){
            loBD.mostrarDatosBD();
        }
    }    
    
    public void establecerDatosBD() throws ECampoError{
        for(ITextBD loBD: moListaITextBD ){
            loBD.establecerDatosBD();
        }
    }  
    @Override
    public void mostrarDatos() throws Exception{
        mostrarDatosBD();
    }

    @Override
    public void establecerDatos() throws Exception {
        establecerDatosBD();
    }
  

//    public void recuperarDatos() throws Exception {
//    }
//    
    @Override
    public boolean validarDatos() throws Exception {
        return true;
    }
}
