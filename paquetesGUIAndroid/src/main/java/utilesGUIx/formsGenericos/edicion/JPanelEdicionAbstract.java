package utilesGUIx.formsGenericos.edicion;
import ListDatos.JSTabla;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

public abstract class JPanelEdicionAbstract extends LinearLayout implements IFormEdicionAndroid {
    private static final long serialVersionUID = 1L;
    protected JFormEdicionParametros moParametros = new JFormEdicionParametros();
	private Activity moActividad;

    public JPanelEdicionAbstract(Context context) {
        super(context);
    }

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

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}
	public void onResume(){
		
	}
	public void startActivityForResult(Intent intent, int requestCode){
		moActividad.startActivityForResult(intent, requestCode);
	}
	public void setActivity(Activity poActivity){
		moActividad = poActivity;
	}
	public Activity getActivity(){
		return moActividad;
	}
	
}
