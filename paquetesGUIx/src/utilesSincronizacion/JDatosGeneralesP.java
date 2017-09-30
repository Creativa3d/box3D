/*
* JDatosGeneralesP.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion;

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
}
