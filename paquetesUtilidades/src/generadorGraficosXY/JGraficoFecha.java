/*
 * JGraficoFecha.java
 *
 * Created on 4 de enero de 2006, 12:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorGraficosXY;

import utilesGUI.grafxy2.util.IDouble;

import utiles.*;

public class JGraficoFecha implements IDouble {
    JDateEdu moFecha = new JDateEdu();
    public static String msFormato="MM/yyyy";
    /** Creates a new instance of JGraficoFecha */
    public JGraficoFecha() {
    }

    public IDouble AsignarDouble(Double pd) {
        try{
            moFecha = new JDateEdu(pd.doubleValue());
        }catch(Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public String toString(String psFormat) {
        return moFecha.msFormatear(msFormato);
    }

    public String toString() {
        return moFecha.msFormatear(msFormato);
    }

    public double convertirDouble() {
        return moFecha.getFechaEnNumero();
    }
    
}
