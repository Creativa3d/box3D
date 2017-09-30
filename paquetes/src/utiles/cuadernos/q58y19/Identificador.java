/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import java.io.Serializable;
import java.util.HashMap;
import utiles.JConversiones;
import utiles.JFormat;

public class  Identificador implements Serializable {

    private int mlDigitoControl;
    private String msNIF;
    private String msSufijo;
    private String msPais;
    private HashMap moHas;

    public  Identificador() {
        
    }
    public  Identificador(String nif, String psSufijo) {

        this.msNIF = nif;
        this.msSufijo = psSufijo;
        msPais="ES";
        this.mlDigitoControl = calcularDigitoControl();
    }
    public  Identificador(String nif, String psSufijo, String psPais) {

        this.msNIF = nif;
        this.msSufijo = psSufijo;
        msPais=psPais;
        this.mlDigitoControl = calcularDigitoControl();
    }

    public static Identificador parse(String line) {
        Identificador orderIdentifier = new Identificador();

        orderIdentifier.setPais(line.substring(0, 2));
        orderIdentifier.setDigitoControl((int) Integer.valueOf(line.substring(2, 4)));
        orderIdentifier.setSufijo(line.substring(4, 7));
        orderIdentifier.setNIF(line.substring(7, 17));
        
        return orderIdentifier;
    }
    public String  getIdentificador() {
        return JFormat.msRellenarDer(msPais, " ", 2) 
                + JFormat.msFormatearDouble(getDigitoControl(), "00") 
                + JFormat.msRellenarIzq(getSufijo().toUpperCase(), "0", 3) 
                + getNIF();
    }

    

/**
 * Calcula el identificador de acreedor/presentador para la norma SEPA 19.14 En Españal formato es este: ESZZXXXAAAAAAAAA
 * , siendo: ES: código del país España según la norma ISO 3166 ZZ: dígitos de control (cuyo cálculo se explica a continuación) 
 * XXX: sufijo (normalmente 000, pero el acreedor puede gestionar más de un canal de adeudos poniendo otros valores) 
 * AAAAAAAAA: CIF del acreedor, frecuentemente una letra seguida de 8 cifras, sin espacios, guiones u otros símbolos. 
 * Los dígitos de control se calculan en base al NIF, aplicando el modelo 97-10 (regla de cálculo definida en la norma ISO 7604 
 * y ampliamente usada en la norma ISO 20022, UNIFI). Tomamos posiciones de la 8 a la 15, es decir el CIF
 * , añadiendo ES y 00 Por ejemplo, B85626240ES00 Convertimos letras a números
 * , considerando que la A es 10, la B es 11, ? la E es 14, ? la S es 28, ? hasta que la Z es 35.
 * Por ejemplo, 1185626240142800 Aplicamos modelo 97-10 (dado un número, lo dividimos entre 97 
 * y restamos a 98 el resto de la operación. Si se obtiene un único dígito, se completa con un cero a la izquierda) 
 * Por ejemplo, el resto de dividir 1185626240142800 entre 97 sale 21 y 98-21=77, por tanto el código completo: ES77000B85626240
*/
    private int  calcularDigitoControl() {
        String lsCadena = getNIF()+getPais()+"00";
        String lsBase ="";
        for(int i = 0 ; i < lsCadena.length(); i++){
            char l = lsCadena.charAt(i);
            if(JConversiones.isNumeric(l)){
                lsBase += l;
            }else{
                int lnum = l;
                int lsepa = lnum - 55;  
                lsBase += String.valueOf(lsepa);
            }
        }
//        
//        int nifLetterAscii = getNIF().charAt(0);
//        int sepaEquivalent = nifLetterAscii - 55;  // Magic
//        String baseString = sepaEquivalent + getNIF().substring(1) + "1428" + "00";
        long lBase = Long.parseLong(lsBase);
        int lDigitoControl = 98 - (int) (lBase % 97);
        return lDigitoControl;
    }


    /**
     * @return the mlDigitoControl
     */
    public int getDigitoControl() {
        return mlDigitoControl;
    }

    /**
     * @param mlDigitoControl the mlDigitoControl to set
     */
    public void setDigitoControl(int mlDigitoControl) {
        this.mlDigitoControl = mlDigitoControl;
    }

    /**
     * @return the msNIF
     */
    public String getNIF() {
        return msNIF;
    }

    /**
     * @param msNIF the msNIF to set
     */
    public void setNIF(String msNIF) {
        this.msNIF = msNIF;
    }

    /**
     * @return the msSufijo
     */
    public String getSufijo() {
        return msSufijo;
    }

    /**
     * @param msSufijo the msSufijo to set
     */
    public void setSufijo(String msSufijo) {
        this.msSufijo = msSufijo;
    }

    /**
     * @return the msPais
     */
    public String getPais() {
        return msPais;
    }

    /**
     * @param msPais the msPais to set
     */
    public void setPais(String msPais) {
        this.msPais = msPais;
    }
}
