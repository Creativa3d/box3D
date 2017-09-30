/*
 * JTextoTipo.java
 *
 * Created on 17 de septiembre de 2004, 10:19
 */

package utilesGUIx;

import java.beans.*;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

/**Enumeración(de la paleta de diseño del programa NetBeans o similar) de valores para la propiedad Tipo del JTextBox/JTextArea*/
public class JTextoTipo extends PropertyEditorSupport {
    
    private static final String mcsCadena = "Cadena";
    private static final String mcsFecha = "Fecha";
    private static final String mcsNumero = "Numero";
    private static final String mcsMoneda3D = "Moneda3D";
    private static final String mcsMoneda = "Moneda";
    private static final String mcsPorciento3D = "Porciento3D";
    private static final String mcsPorciento = "Porciento";
    private static final String mcsDNICIF = "DNI/CIF";
    /**Constructor*/
    public JTextoTipo() {
        super();
        setValue(new Integer(JTipoTextoEstandar.mclTextCadena));
    }
    /**Devuelve la enumeración de valores*/
    public String[] getTags() {
        return new String[]{mcsCadena, mcsFecha, mcsNumero, mcsDNICIF, mcsMoneda3D, mcsMoneda, mcsPorciento3D, mcsPorciento};
    }
    /**Establece el texto de la enumeración, que se convertira al tipo concreto*/
    public void setAsText(final String sValue) {
        if (sValue.compareTo(mcsCadena)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextCadena));
        } else if (sValue.compareTo(mcsFecha)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextFecha));
        } else if (sValue.compareTo(mcsNumero)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextNumeroDoble));
        } else if (sValue.compareTo(mcsMoneda3D)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextMoneda3Decimales));
        } else if (sValue.compareTo(mcsMoneda)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextMoneda));
        } else if (sValue.compareTo(mcsPorciento3D)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextPorcentual3Decimales));
        } else if (sValue.compareTo(mcsPorciento)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextPorcentual));
        } else if (sValue.compareTo(mcsDNICIF)==0) {
            setValue(new Integer(JTipoTextoEstandar.mclTextDNI));
        } else{
            setValue(new Integer(JTipoTextoEstandar.mclTextCadena));
        }
        firePropertyChange();
    }

    /** Get the name of the PlotSpot being edited*/
    public String getAsText() {
        int iValue = ((Integer)getValue()).intValue();
        String s = "Illegal value";
         switch (iValue) {
             case JTipoTextoEstandar.mclTextCadena:
                 s = mcsCadena;
                 break;
             case JTipoTextoEstandar.mclTextFecha:
                 s = mcsFecha;
                 break;
             case JTipoTextoEstandar.mclTextNumeroDoble:
                 s = mcsNumero;
                 break;
             case JTipoTextoEstandar.mclTextMoneda3Decimales:
                 s = mcsMoneda3D;
                 break;
             case JTipoTextoEstandar.mclTextMoneda:
                 s = mcsMoneda;
                 break;
             case JTipoTextoEstandar.mclTextPorcentual3Decimales:
                 s = mcsPorciento3D;
                 break;
             case JTipoTextoEstandar.mclTextPorcentual:
                 s = mcsPorciento;
                 break;
             case JTipoTextoEstandar.mclTextDNI:
                 s = mcsDNICIF;
                 break;
             default:
         }
         return s;
    }
//    /**Añade un oyente para cuando cambia la propiedad*/
//    public void addPropertyChangeListener(PropertyChangeListener p) {
//        super.addPropertyChangeListener(p);
//    }
    /**devuelve el valor por defecto*/
    public String getJavaInitializationString() {
	return String.valueOf(JTipoTextoEstandar.mclTextCadena) ;
    }
}
