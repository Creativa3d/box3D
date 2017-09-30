/*
* JDatosGeneralesP.java
*
* Creado el 9/9/2013
*/

package paquetesGeneradorInf.gest1;

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
