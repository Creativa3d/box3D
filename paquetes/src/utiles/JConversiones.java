/*
 * JConversiones.java
 *
 * Created on 6 de abril de 2004, 12:38
 */

package utiles;

/**Conversiones/comprobaciones de cadenas a numeros*/
public class JConversiones {

    public static boolean isNumeric(final char pcValor) {
        return (pcValor >= '0' && pcValor <= '9');
    }
    
    /** Creates a new instance of JConversiones */
    private JConversiones() {
    }
    
    /**
     * devuelve si el string ers numero o no
     * @param psValor cadena numero
     * @return si es numero
     */
    public static boolean isNumeric(String psValor){
        boolean lbValor=false;
        try{
            Double.valueOf(psValor.replace(',','.'));
            lbValor=true;
        }catch(Exception e){
            lbValor=false;
        }
        return lbValor;
    }
    /**
     * devuelve el string en numero
     * @return valor double
     * @param psValor cadena numero
     */
    public static double cdbl(String psValor) {
        double ldValor;
        try{
            ldValor=Double.valueOf(psValor.replace(',','.')).doubleValue();
        }catch(Exception e){
            if(psValor!=null && psValor.contains(".") && psValor.contains(",")){//1.000,12
                try{
                    ldValor=Double.valueOf(psValor.replace(".", "").replace(',', '.')).doubleValue();
                }catch(Exception e1){
                    ldValor=0.0;
                }
            }else{
                ldValor=0.0;
            }
        }
        return ldValor;
    }
    
    /**
     * devuelve si el string es booleano o no
     * @param psValor cadena booleano
     * @return booleano
     */
    public static boolean isBool(String psValor){
        boolean lbValor=false;
        try{
            Boolean.valueOf(psValor);
            lbValor=true;
        }catch(Exception e){
            lbValor=false;
        }
        return lbValor;
    }
    /**
     * devuelve el string en booleano
     * @param psValor cadena booleano
     * @return valor booleano
     */
    public static boolean cBool(String psValor) {
        boolean lbValor;
        try{
            lbValor=Boolean.valueOf(psValor).booleanValue() || psValor.equals("1");
        }catch(Exception e){
            lbValor=false;
        }
        return lbValor;
    }
    public static int mlComparaDoubles(double pd1, double pd2, double pdMargen){
        int lComparacion = 0;
        if((pd1-pdMargen) <= pd2 &&  (pd1+pdMargen) >= pd2){
            lComparacion = 0;
        }else{
            if(pd1>pd2){
                lComparacion = 1;
            }else{
                lComparacion = -1;
            }
        }
        return lComparacion;
        
    }
    public static double numeroDecimales(double ldNumero, int plNumeroDecimales){
        return JConversiones.cdbl(JFormat.msFormatearNumero(new Double(ldNumero), plNumeroDecimales, false));
    }
    public static double numeroDecimalesAlAlza(double ldNumero, int plNumeroDecimales){
        double ldResult = ldNumero+(1/Math.pow(10, plNumeroDecimales+1));
        ldResult= JConversiones.cdbl(
                    JFormat.msFormatearNumero(
                        new Double(ldResult),
                        plNumeroDecimales,
                        false)
                );
        return ldResult;
    }
    
    
}
