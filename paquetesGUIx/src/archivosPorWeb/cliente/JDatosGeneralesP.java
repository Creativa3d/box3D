package archivosPorWeb.cliente;

/*
 * JDatosGeneralesP.java
 *
 * Created on 15 de abril de 2005, 13:00
 */


public class JDatosGeneralesP {
    
    private static JDatosGenerales moDatosGenerales=null;
    /** Creates a new instance of JDatosGeneralesP */
    private JDatosGeneralesP() {
    }
    
    public static JDatosGenerales getDatosGenerales(){
        if(moDatosGenerales==null){
            moDatosGenerales = new JDatosGenerales();
        }
        return moDatosGenerales;
    }
    
}
