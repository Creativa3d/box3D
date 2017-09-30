/*
 * JFormat.java
 *
 * Created on 19 de noviembre de 2003, 17:34
 */
package utiles;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *Formateo de cadenas
 */
public class JFormat {

    private static final IListaElementos moFormatos = new JListaElementos();
    public static final String mcsHHmmss = "HH:mm:ss";
    public static final String mcsHHmm = "HH:mm";
    public static final String mcsddMMyyyy = "dd/MM/yyyy";
    public static final String mcsddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";
    public static final String mcsyyyyMMddHHmmssGuiones = "yyyy-MM-dd HH:mm:ss";
    public static final String mcsMMddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
    public static final String mcsDOUBLENOCIENTIFICO = "###############.###############";

    /** Creates a new instance of JFormat */
    static {
        //formatos frecuentes
        moFormatos.add(new JFormatElemento(mcsHHmmss, new SimpleDateFormat(mcsHHmmss)));
        moFormatos.add(new JFormatElemento(mcsddMMyyyy, new SimpleDateFormat(mcsddMMyyyy)));
        moFormatos.add(new JFormatElemento(mcsddMMyyyyHHmmss, new SimpleDateFormat(mcsddMMyyyyHHmmss)));
        moFormatos.add(new JFormatElemento(mcsyyyyMMddHHmmssGuiones, new SimpleDateFormat(mcsyyyyMMddHHmmssGuiones)));
        moFormatos.add(new JFormatElemento(mcsMMddyyyyHHmmss, new SimpleDateFormat(mcsMMddyyyyHHmmss)));
        moFormatos.add(new JFormatElemento(mcsDOUBLENOCIENTIFICO, new DecimalFormat(mcsDOUBLENOCIENTIFICO)));
    }

    /**
     * por optimizacion la aplicacion puede precargar formatos de uso frecuente
     */
    public synchronized static void addFormatoFrecuenteFecha(String psFormato) {
        moFormatos.add(new JFormatElemento(psFormato, new SimpleDateFormat(psFormato)));
    }

    /**
     * por optimizacion la aplicacion puede precargar formatos de uso frecuente
     */
    public synchronized static void addFormatoFrecuenteNumero(String psFormato) {
        moFormatos.add(new JFormatElemento(psFormato, new DecimalFormat(psFormato)));
    }

    /**
     * formatear un numero
     * @param psFormato Formato de entrada
     * @param pdNumero Numero a formatear
     * @return Cadena del numero formateado
     */
    public synchronized static String msFormatearDouble(Double pdNumero, String psFormato) {
        String lsNumero = null;
        if (pdNumero == null) {
            lsNumero = "";
        } else {
            Format formatter = null;
            for (int i = 0; i < moFormatos.size() && formatter == null; i++) {
                JFormatElemento loElem = (JFormatElemento) moFormatos.get(i);
                if (loElem.msFormato.equals(psFormato)) {
                    formatter = loElem.moFormat;
                }
            }

            if (formatter == null) {
                formatter = new DecimalFormat(psFormato);
            }
            lsNumero = formatter.format(pdNumero);
        }

        return lsNumero;
    }

    /**
     * formatear un numero
     * @param psFormato Formato de entrada
     * @param pdNumero Numero a formatear
     * @return Cadena del numero formateado
     */
    public synchronized static String msFormatearDouble(double pdNumero, String psFormato) {
        return msFormatearDouble(new Double(pdNumero), psFormato);
    }

    /**
     * formatea la fecha actual a string con formato
     * @param poDate Fecha a formatear
     * @param psFormato formato de fecha
     * @return fecha formateada
     */
    public synchronized static String msFormatearFecha(Date poDate, String psFormato) {
        String lsFecha;
        Format formatter = null;
        for (int i = 0; i < moFormatos.size() && formatter == null; i++) {
            JFormatElemento loElem = (JFormatElemento) moFormatos.get(i);
            if (loElem.msFormato.equals(psFormato)) {
                formatter = loElem.moFormat;
            }
        }
        if (formatter == null) {
            if (psFormato.equals("")) {
                formatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
            } else {
                formatter = new SimpleDateFormat(psFormato);
            }
        }
        lsFecha = formatter.format(poDate);
        return lsFecha;
    }

    /**
     * Formatea el numero pasado por parametro
     * @param psNumero Numero pasado por parametro
     * @param plDecimales Numero de decimales permitido
     * @param pbForzar Si el numero no tiene decimales se le anaden
     * @return Numero formateado o si no es numerico el mismo valor
     */
    public static String msFormatearNumeroComaInternet(double pdNumero, int plDecimales, boolean pbForzar) {
        return msFormatearNumeroComaInternet(new Double(pdNumero), plDecimales, pbForzar);
    }

    /**
     * Formatea el numero pasado por parametro
     * @param psNumero Numero pasado por parametro
     * @param plDecimales Numero de decimales permitido
     * @param pbForzar Si el numero no tiene decimales se le anaden
     * @return Numero formateado o si no es numerico el mismo valor
     */
    public static String msFormatearNumero(Object psNumero, int plDecimales, boolean pbForzar) {
        return msFormatearNumeroComa(psNumero, plDecimales, pbForzar).replace(',', '.');
    }
    /**
     * Formatea el numero pasado por parametro
     * @param psNumero Numero pasado por parametro
     * @param plDecimales Numero de decimales permitido
     * @param pbForzar Si el numero no tiene decimales se le anaden
     * @return Numero formateado o si no es numerico el mismo valor
     */
    public static String msFormatearNumeroComaInternet(Object psNumero, int plDecimales, boolean pbForzar) {
        String lsValor = msFormatearNumeroComa(psNumero, plDecimales, pbForzar);
        if (lsValor.length() == 0) {
            lsValor = "&nbsp;";
        }
        return lsValor;

    }

    /**
     * Formatea el numero pasado por parametro
     * @return Numero formateado o si no es numerico el mismo valor
     * @param plDecimales1 numero de decimales
     * @param psNumero Numero pasado por parametro
     * @param pbForzar Si el numero no tiene decimales se le anaden
     */
    public static String msFormatearNumeroComa(Object psNumero, int plDecimales1, boolean pbForzar) {
        String lsValor;
        int lPosi;
        int lLong;
        int lDecimales = plDecimales1;
        //pasamos el numero a cadena sustituyendo las comas por punto
        if (psNumero == null) {
            lsValor = "";
        } else {
            lsValor = psNumero.toString().toUpperCase().replace(',', '.');
        }
        try {
            //redondeamos
            double ldAux = Double.valueOf(lsValor).doubleValue();
            double ldPor = 1;
            for (int i = 1; i <= plDecimales1; i++) {
                ldPor *= 10;
            }
            lsValor = String.valueOf(Math.round(ldAux * ldPor) / ldPor);
            //vemos la long
            lLong = lsValor.length();

        } catch (Exception e) {
            lLong = 0;
            if (psNumero == null) {
                lsValor = "";
            } else {
                lsValor = psNumero.toString().toUpperCase();
            }
        }
        if (lLong != 0) {
            //quitamos la E del Numero
            lPosi = lsValor.indexOf('E');
            if (lPosi > 0) {
                int lMul;
                //sacamos el numero de ceros
                String lsMul = lsValor.substring(lPosi + 1);
                //quitamos la E
                String lsNumero = lsValor.substring(0, lPosi);
                try {
                    lMul = Integer.valueOf(lsMul).intValue();
                    //Vemos la posicion del decimal
                    lPosi = lsNumero.indexOf('.');
                    //quitamos el decimal
                    lsNumero = lsNumero.substring(0, lPosi) + lsNumero.substring(lPosi + 1);
                    //abveriguamos si solo hay que correr la coma o anadir ceros
                    if (lPosi != -1) {
                        if ((lsNumero.length() - lPosi) < lMul) {
                            lMul -= (lsNumero.length() - lPosi);
                            lPosi = -1;
                        } else {
                            lPosi += lMul;
                            lMul = -1;
                        }
                    }
                    //si lPosi > -1 entonces es que se corre la coma del numero tantas veces como lPosi
                    //si lMul > -1 entonces se anadenm tantos ceros como lMul
                    if (lPosi > 0) {
                        if (lPosi != lsNumero.length()) {
                            lsNumero = lsNumero.substring(0, lPosi) + "." + lsNumero.substring(lPosi);
                        }
                    } else {
                        for (int i = 1; i < lMul; i++) {
                            lsNumero = lsNumero + "0";
                        }
                    }
                    lsValor = lsNumero;
                    //vemos la long
                    lLong = lsValor.length();
                } catch (Exception e) {
                    JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, "JFormat", e);
                }
            }


            //buscamos un punto
            lPosi = lsValor.indexOf('.');
            if (lPosi != -1) {
                //si tiene coma ponemos los decimales adecuados
                if (lDecimales != 0) {
                    //se quitan los decimales que sobran
                    if (lLong >= (lPosi + 1 + lDecimales)) {
                        lsValor = lsValor.substring(0, lPosi) + lsValor.substring(lPosi, lPosi + 1 + lDecimales);
                    } else {
                        lsValor = lsValor.substring(0, lPosi) + lsValor.substring(lPosi, lLong);
                    }
                    //si se fuerzan los decimales y no tiene los suficientes se le anaden
                    if (pbForzar) {
                        lDecimales = lDecimales - (lsValor.length() - (lPosi + 1));
                    }
                } else {
                    lsValor = lsValor.substring(0, lPosi);
                }
            }
            if (pbForzar) {
                if (lDecimales != 0) {
                    String lsDecimales = (new Integer(10 * lDecimales)).toString();
                    lsDecimales = lsDecimales.substring(1, lsDecimales.length());
                    if (lPosi != -1) {
                        lsValor = lsValor + lsDecimales;
                    } else {
                        lsValor = lsValor + "," + lsDecimales;
                    }
                }
            }
            lsValor = lsValor.replace('.', ',');
        }
        return lsValor;
    }
    /**Una frase que empieza por mayusculas y el resto en minusculas*/
    public static String getMinusculMenos1(String psCadena){
        String lsResult = psCadena.toLowerCase();
        if(lsResult.length()>1){
            lsResult=lsResult.substring(0,1).toUpperCase()+lsResult.substring(1);
        }
        return lsResult;
    }
    /**Un nombre y apellidos, es decir, todos los comienzaos de palabra en mayusculas*/
    public static String getMinusculMenosPrimerasPalabras(String psCadena){
        StringBuilder loResult = new StringBuilder();
        boolean lbAnt = true;
        boolean lbAlgunaLetra=false;
//        int lIndex = 0;
        lbAnt=true;
        lbAlgunaLetra=false;
        StringBuilder lsAux = new StringBuilder();
        psCadena=psCadena.toLowerCase();
        for(int i = 0 ; i < psCadena.length(); i++){
            char lcCarac = psCadena.charAt(i);
            if((
                   lcCarac == ' ' ||
                   lcCarac == '.' ||
                   lcCarac == ',' ||
                   lcCarac == '\t' ||
                   lcCarac == '\n' ||
                   lcCarac == ';' ||
                   lcCarac == ':'
                   )
               ){
                if(i !=0 &&
                   i != psCadena.length() &&
                   !lbAnt){
                    loResult.append( getMinusculMenos1(lsAux.toString() ));
//                    lIndex++;
                    lsAux = new StringBuilder();
                    lbAlgunaLetra=false;
                }
                loResult.append(lcCarac);
                lbAnt=true;
            }else{
                lbAnt=false;
                lbAlgunaLetra=true;
                lsAux.append(lcCarac);
            }
        }
        if(lbAlgunaLetra){
            loResult.append(getMinusculMenos1(lsAux.toString() ));
        }
        return loResult.toString();
    }

    /**
     * devuelve el array de palabras que hay en psFrase
     * @param psFrase
     * @return array de palabras
     */
    public static String[] getPalabras(String psFrase){
        return JCadenas.getPalabras(psFrase);
    }
    /**
     * Se Reemplaza psCadena1 por psCadena2 en psCadena 
     * @param psCadena
     * @param psCadena1
     * @param psCadena2
     * @return el resultado del reemplazo
     */
    public static String replace2(final String psCadena, final String psCadena1, final String psCadena2) {
        return JCadenas.replace2(psCadena, psCadena1, psCadena2);
    }
    /**
     * anade/trunca de la cadena hasta el numero de caracteres (numCar)
     * @param psCadena1 cadena inicial
     * @param psRelleno caracter de relleno
     * @param numCar numero de caracteres max/min
     * @return cadena rellenada/truncada
     */
    public static String msRellenarIzq(String psCadena1, String psRelleno, int numCar) {
        return JCadenas.msRellenar(psCadena1, psRelleno, numCar, false);
    }

    /**
     * anade/trunca de la cadena hasta el numero de caracteres (numCar)
     * @param psCadena1 cadena inicial
     * @param psRelleno caracter de relleno
     * @param numCar numero de caracteres max/min
     * @return cadena rellenada/truncada
     */
    public static String msRellenarDer(String psCadena1, String psRelleno, int numCar) {
        return JCadenas.msRellenar(psCadena1, psRelleno, numCar, true);
    }

    /**
     * anade/trunca de la cadena hasta el numero de caracteres (numCar)
     * @param psCadena1 cadena inicial
     * @param psRelleno caracter de relleno
     * @param numCar numero de caracteres max/min
     * @return cadena rellenada/truncada
     */
    public static String msRellenar(String psCadena1, String psRelleno, int numCar, boolean pbDerecha) {
        return JCadenas.msRellenar(psCadena1, psRelleno, numCar, pbDerecha);
    }
}

class JFormatElemento {

    String msFormato;
    Format moFormat;

    JFormatElemento(String psFormato, Format poFormat) {
        msFormato = psFormato;
        moFormat = poFormat;
    }
}
