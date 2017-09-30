/*
 * JTipoTextoEstandar.java
 *
 * Created on 16 de noviembre de 2006, 9:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUI.tiposTextos;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import utiles.CIF_NIF;
import utiles.FechaMalException;
import utiles.JDateEdu;
import utiles.JDepuracion;

public class JTipoTextoEstandar extends JTipoTextoBaseAbstract {

    /**Tipo cadena*/
    public static final int mclTextCadena = 0;
    public static final int mclTextCadenaMayusculas = 5;
    /**Tipo fecha*/
    public static final int mclTextFecha = 1;
    /**Tipo número doble (redondea)*/
    public static final int mclTextNumeroDoble = 2;
    /**Tipo número moneda*/
    public static final int mclTextMoneda3Decimales = 11;
    public static final int mclTextMoneda = 12;
    /**Tipo número porcentual*/
    public static final int mclTextPorcentual3Decimales = 13;
    public static final int mclTextPorcentual = 14;
    /**Tipo número entero*/
    public static final int mclTextNumeroEntero = 4;
    /**Tipo DNI*/
    public static final int mclTextDNI = 3;
    /**Tipo DNI*/
    public static final int mclTextDNIMayusculas = 3;//igual q dni
    /**Tipo número doble sin redondeo*/
    public static final int mclTextNumeroDobleSinRedondeo = 7;
    /**Tipo fecha*/
    public static final int mclTextFechaSinLimite = 8;
    /**Tipo Hora*/
    public static final int mclTextHORA = 9;
    /**Tipo eMail*/
    public static final int mclTextEMAIL = 10; 
    private String msFormato = null;
    private DecimalFormat moFormatNumero = null;
    private SimpleDateFormat moFormatFecha = null;

    /** Creates a new instance of JTipoTextoEstandar */
    public JTipoTextoEstandar(final int plTipo) {
        super();
        mlTipo = plTipo;
        if (mlTipo == mclTextNumeroDoble || mlTipo == mclTextNumeroDobleSinRedondeo) {
            msFormato = "###,###,###,##0.00";
            moFormatNumero = new DecimalFormat(msFormato);
        }
        // Moneda.class
        if (mlTipo == mclTextMoneda3Decimales) {
            msFormato = "###,###,###,##0.000 \u00A4";
            moFormatNumero = new DecimalFormat(msFormato);
        }
        if (mlTipo == mclTextMoneda) {
            msFormato = "###,###,###,##0.00 \u00A4";
            moFormatNumero = new DecimalFormat(msFormato);
        }
        if (mlTipo == mclTextPorcentual3Decimales) {
            msFormato = "#,##0.000 %";
            moFormatNumero = new DecimalFormat(msFormato);
            moFormatNumero.setMultiplier(1);
        }
        if (mlTipo == mclTextPorcentual) {
            msFormato = "#,##0.00 %";
            moFormatNumero = new DecimalFormat(msFormato);
            moFormatNumero.setMultiplier(1);
        }
        if (mlTipo == mclTextHORA) {
            msFormato = "HH:mm";
            moFormatFecha = new SimpleDateFormat(msFormato);
        }
//        if(mlTipo == mclTextNumeroEntero){
//            msFormato = "###,###,###,##0";
//            moFormatNumero = new DecimalFormat(msFormato);
//        }

    }

    public JTipoTextoEstandar(final int plTipo, final String psFormato) {
        super();
        mlTipo = plTipo;
        if (psFormato != null && !psFormato.equals("")) {
            msFormato = psFormato;
            if (mlTipo == mclTextNumeroDoble || mlTipo == mclTextNumeroEntero
                    || mlTipo == mclTextNumeroDobleSinRedondeo
                    || mlTipo == mclTextMoneda3Decimales
                    || mlTipo == mclTextMoneda
                    || mlTipo == mclTextPorcentual3Decimales
                    || mlTipo == mclTextPorcentual) {
                moFormatNumero = new DecimalFormat(psFormato);
                //OJO Comprobar q hace
                if (psFormato.indexOf(".") != -1) {
                    moFormatNumero.setMaximumIntegerDigits(psFormato.substring(0, psFormato.indexOf(".")).length());
                    moFormatNumero.setMaximumFractionDigits(psFormato.substring(psFormato.indexOf(".") + 1).length());
                } else {
                    moFormatNumero.setMaximumIntegerDigits(psFormato.length());
                }
            }
            if (mlTipo == mclTextFecha || mlTipo == mclTextFechaSinLimite || mlTipo == mclTextHORA) {
                moFormatFecha = new SimpleDateFormat(psFormato);
            }
        }else{
            msFormato = null;
            moFormatFecha=null;
            moFormatNumero=null;
        }
    }

    public boolean isTipoCorrecto(final String psTexto) {
        boolean lbCorrecto = true;
        String lsTexto = psTexto.trim();
        if (lsTexto != null && !lsTexto.equals("")) {
            switch (mlTipo) {
                case mclTextEMAIL:
                    try{
                        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^([0-9a-zA-Z]([_.w]*[-.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)*([a-zA-Z]{1,9}.)+[a-zA-Z]{2,9})$");
                        java.util.regex.Matcher m = p.matcher(lsTexto);
                        if (!m.find()) {
                            lbCorrecto = false;
                        }
                    }catch(Throwable e){
                    }
                    break;
                case mclTextHORA:
                    lsTexto = JDateEdu.msHora(lsTexto);
                    if (!JDateEdu.isDate("1/1/2000 " + lsTexto)) {
                        lbCorrecto = false;
                    }
                    for (int i = 0; i < lsTexto.length() && lbCorrecto; i++) {
                        if (!((lsTexto.charAt(i) >= '0' && lsTexto.charAt(i) <= '9')
                                || lsTexto.charAt(i) == ':')) {
                            lbCorrecto = false;
                        }
                    }
                    break;
                case mclTextFecha:
                    if (!JDateEdu.isDate(lsTexto)) {
                        lbCorrecto = false;
                    } else {
                        try {
                            if (JDateEdu.CDate(lsTexto).compareTo(new JDateEdu(1800, 1, 1)) == JDateEdu.mclFechaMenor) {
                                lbCorrecto = false;
                            }
                        } catch (FechaMalException ex) {
                        }
                    }
                    break;
                case mclTextFechaSinLimite:
                    if (!JDateEdu.isDate(lsTexto)) {
                        lbCorrecto = false;
                    }
                    break;
                case mclTextNumeroEntero:
                case mclTextNumeroDobleSinRedondeo:
                case mclTextNumeroDoble:
                case mclTextMoneda3Decimales:
                case mclTextMoneda:
                case mclTextPorcentual3Decimales:
                case mclTextPorcentual:
                    try {
                        Double.valueOf(lsTexto.replace(',', '.'));
                    } catch (Exception e) {
                        lbCorrecto = false;
                    }
                    break;
//               case mclTextDNIMayusculas://igual q dni
                case mclTextDNI:
                    String lsLetra = CIF_NIF.obtainNIFLetter(lsTexto);
                    if (lsLetra != null) {
                        lsTexto = lsTexto + lsLetra;
                    }
                    try {
                        lbCorrecto = CIF_NIF.verifyCIF_NIF(lsTexto);
                    } catch (Exception e) {
                        lbCorrecto = false;
                    }

                    break;
                default:
            }
        }
        return lbCorrecto;
    }

    public String getTextoModificado(final String psTexto) {
        String lsTexto = psTexto;

        if (lsTexto != null && !lsTexto.equals("")) {
            switch (mlTipo) {
                case mclTextHORA:
                    lsTexto=JDateEdu.msHora(psTexto);
                    break;
                case mclTextFechaSinLimite:
                case mclTextFecha:
                    if (isTipoCorrecto(lsTexto)) {
                        JDateEdu loDate = JDateEdu.CDate(lsTexto.trim());
                        lsTexto = loDate.toString();
                    }
                    break;
                case mclTextDNI:
                    String lsLetra = CIF_NIF.obtainNIFLetter(lsTexto);
                    if (lsLetra != null) {
                        lsTexto = lsTexto + lsLetra;
                    }
//                  break;//igual q dni, pero siempre en mayusculas
//                case mclTextDNIMayusculas:
//                    lsLetra = CIF_NIF.obtainNIFLetter(lsTexto);
//                    if(lsLetra!=null){
//                      lsTexto = lsTexto + lsLetra;
//                    }
                    lsTexto = lsTexto.toUpperCase();
                    break;
                case mclTextCadenaMayusculas:
                    lsTexto = lsTexto.toUpperCase();
                    break;
                case mclTextNumeroEntero:
                case mclTextNumeroDobleSinRedondeo:
                case mclTextNumeroDoble:
                case mclTextMoneda3Decimales:
                case mclTextMoneda:
                case mclTextPorcentual3Decimales:
                case mclTextPorcentual:
                    lsTexto = lsTexto.replace(',', '.').trim();
                    break;

                default:
            }
        }
        return lsTexto;
    }

    public String getTextoError(final String psTexto) {
        String lsTextoError = null;
        String lsTexto = psTexto.trim();
        switch (mlTipo) {
            case mclTextEMAIL:
                if (!isTipoCorrecto(lsTexto)) {
                    lsTextoError = "eMail incorrecto (" + psTexto + ")";
                }
                break;
            case mclTextHORA:
                if (!isTipoCorrecto(lsTexto)) {
                    lsTextoError = "Hora incorrecta (" + psTexto + ")";
                }
                break;
            case mclTextFechaSinLimite:
            case mclTextFecha:
                if (!isTipoCorrecto(lsTexto)) {
                    lsTextoError = "Fecha incorrecta (" + psTexto + ")";
                }
                break;
            case mclTextNumeroEntero:
            case mclTextNumeroDobleSinRedondeo:
            case mclTextNumeroDoble:
            case mclTextMoneda3Decimales:
            case mclTextMoneda:
            case mclTextPorcentual3Decimales:
            case mclTextPorcentual:
                if (!isTipoCorrecto(lsTexto)) {
                    lsTextoError = "Número incorrecto (" + psTexto + ")";
                }
                break;
//            case mclTextDNIMayusculas://igual q dni
            case mclTextDNI:
                if (!isTipoCorrecto(lsTexto)) {
                    lsTextoError = "DNI/CIF incorrecto (" + psTexto + ")";
                }
            default:

        }
        return lsTextoError;
    }

    public String getTextFormateado() {
        String retValue = "";
        if ((msTexto != null && msTexto.equals("")) || msFormato == null) {
            if (mlTipo == mclTextCadenaMayusculas || mlTipo == mclTextDNIMayusculas) {
                retValue = super.getTextFormateado().toUpperCase();
            } else {
                retValue = super.getTextFormateado();
            }
        } else {
            if (mlTipo == mclTextFecha || mlTipo == mclTextFechaSinLimite) {
                try {
                    retValue = moFormatFecha.format(new JDateEdu(msTexto).moDate());
                } catch (FechaMalException ex) {
                    ex.printStackTrace();
                }
            }
            if (mlTipo == mclTextHORA) {
                try {
                    if(JDateEdu.isDate(msTexto)){
                        retValue = moFormatFecha.format(new JDateEdu(msTexto).moDate());
                    }else{
                        retValue = moFormatFecha.format(new JDateEdu("1/1/2000 " + msTexto).moDate());
                    }
                } catch (FechaMalException ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
            if (mlTipo == mclTextNumeroDoble 
                    || mlTipo == mclTextNumeroEntero
                    || mlTipo == mclTextMoneda3Decimales
                    || mlTipo == mclTextMoneda
                    || mlTipo == mclTextPorcentual3Decimales
                    || mlTipo == mclTextPorcentual) {
                retValue = moFormatNumero.format(Double.valueOf(msTexto).doubleValue());
            }
            if (mlTipo == mclTextNumeroDobleSinRedondeo) {
                BigDecimal decimal = new BigDecimal(msTexto);
                retValue = moFormatNumero.format(decimal.setScale(moFormatNumero.getMaximumFractionDigits(), BigDecimal.ROUND_DOWN).doubleValue());

            }
        }
        return retValue;
    }

    public void getTecla(final String psTexto, final KeyEventCZ poEvent) {
        if (mlTipo == mclTextCadenaMayusculas || mlTipo == mclTextDNIMayusculas) {
            poEvent.setKeyChar(Character.toUpperCase(poEvent.getKeyChar()));
        } else {
            super.getTecla(psTexto, poEvent);
        }
    }
}
