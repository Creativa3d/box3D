/*
 * JT2CLIENTES.java
 *
 * Creado el 21/1/2006
 */
package utilesGUIx.formsGenericos;

import utilesGUIx.formsGenericos.busqueda.IConsulta;
import ListDatos.JSTabla;
import android.content.Context;
import android.view.ViewGroup;

public abstract class JT2GENERALBASE2 extends JT2GENERALBASEModelo {


    public JT2GENERALBASE2() {
    }
    
    public Context getContext(){
        return ((ViewGroup)getPanel()).getContext();
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        //vacio
    }

    public abstract IConsulta getConsulta();
}
