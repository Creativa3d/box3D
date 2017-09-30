/*
* JDatosGeneralesP.java
*
* Creado el 24/5/2006
*/

package main;

import utilesGUIx.aplicacion.JDatosGeneralesAplicacionModelo;
import utilesGUIx.plugin.IPlugInFactoria;

public class JDatosGeneralesP {
    private static JDatosGeneralesAplicacionModelo moDatosGenerales=null;

    private JDatosGeneralesP() {
    }

    public static JDatosGeneralesAplicacionModelo getDatosGenerales(){

        return moDatosGenerales;
    }
    public static IPlugInFactoria getDatosGeneralesPlugIn(){
        return moDatosGenerales.getDatosGeneralesPlugIn();
    }
    
    public static void setDatosGenerales(JDatosGeneralesAplicacionModelo poDatos){
        moDatosGenerales=poDatos;
    }
}        
