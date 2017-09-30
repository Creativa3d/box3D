/*
* JDatosGeneralesP.java
*
* Creado el 24/5/2006
*/

package impresionXML.impresion.xml.diseno;

import utilesGUIx.plugin.IPlugInFactoria;

public class JDatosGeneralesP {
    private static JDatosGenerales moDatosGenerales=null;

    private JDatosGeneralesP() {
    }

    public static JDatosGenerales getDatosGenerales(){
        if(moDatosGenerales==null){
            moDatosGenerales = new JDatosGenerales();
        }
        return moDatosGenerales;
    }
    public static IPlugInFactoria getDatosGeneralesPlugIn(){
        return moDatosGenerales.getDatosGeneralesPlugIn();
    }
}        
