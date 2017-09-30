package utilesGUIx.formsGenericos.edicion;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import utiles.JDepuracion;
import utilesAndroid.util.R;

public class JFragmentoActividad  extends android.support.v4.app.Fragment {
	private View rootView;
	private View moVista;
	
	
	public JFragmentoActividad(){
		
	}

	public void setVista(View poVista){
        moVista=poVista;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
        rootView = inflater.inflate(R.layout.fragmentoactividad, container, false);
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.root);
        if(moVista!=null) {
	        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        ll.addView(moVista, lp);
        }
        }catch(Exception e){
        	JDepuracion.anadirTexto(getClass().getName(), e);
        }
        return rootView;
    }
    	
}
