/*
 * JCuentaBancaria.java
 *
 * Created on 23 de febrero de 2007, 12:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utiles;


public class JCuentaBancaria {
    
    
    private String IBAN_inicio;
    private String cuenta;
    private String entidad;
    private String oficina;
    private String cc;
    /**
     * Creates a new instance of JCuentaBancaria
     */
    public JCuentaBancaria() {
    
    }
    public JCuentaBancaria(String CCC) throws Exception {
        setCuentaBancaria(CCC);
    }
    public void setCuentaBancaria(String CCC) throws Exception{
        CCC=CCC.replace(" ", "");
        if ((CCC != null) && (!CCC.equals(""))){
            CCC = CCC.toUpperCase();
            if(CCC.length()==20){
                if(!validarCuenta(CCC)){
                    throw new Exception(CCC+" CCC Incorrecta");
                }
                CCC = ObtenerIBAN(CCC, "ES");
            } else if(CCC.length()==24){
                if(!validarIBAN(CCC)){
                    throw new Exception(CCC + " IBAN Incorrecto");
                }
            }else{
                throw new Exception("Error en longitud");
            }
            IBAN_inicio = CCC.substring(0,4);
            entidad = CCC.substring(4,8);
            oficina = CCC.substring(8,12);
            cc = CCC.substring(12,14);
            cuenta = CCC.substring(14,CCC.length());            
        }else{
            IBAN_inicio = "";
            entidad = "";
            oficina = "";
            cc = "";
            cuenta = "";            
        }
    }
    public String getIBAN_Inicio(){
        return IBAN_inicio;
    }
    public String getCodPais(){
        return IBAN_inicio.substring(0, 2);
    }
    public String getControlIBAN(){
        return IBAN_inicio.substring(2, 4);
    }
    public String getCuenta(){
        return cuenta;
    }
    
    public String getEntidad(){
        return entidad;
    }
    
    public String getOficina(){
        return oficina;
    }
    
    public String getCC(){
        return cc;
    }    
    public String getCCC(){
        return getEntidad()+getOficina()+getCC()+getCuenta();
    }    
    public String getIBAN(){
        return getIBAN_Inicio()+getEntidad()+getOficina()+getCC()+getCuenta();
    }
    
    public  boolean validarCuenta(){
        return validarCuenta(getCCC());
    }
    
    public static boolean validarCuenta(String CCC){
        CCC=CCC.replace(" ", "");
        if (!CCC.trim().equals("")){
            if (CCC.trim().length() == 24){
                CCC = CCC.substring(4);
            }
            if (CCC.trim().length() == 20){
                String entidad = CCC.substring(0,4);
                String oficina = CCC.substring(4,8);
                String cc = CCC.substring(8,10);
                String cuenta = CCC.substring(10,CCC.length());
                if (!cc.equals(calculaDC(entidad, oficina, cuenta)))
                    return false;
            }
            else return false;
        }
        else return false;
        return true;
    }
    
    public static String calculaDC(String Entidad, String Oficina, String Cuenta){
        

        if (Entidad.equals(""))
            Entidad = "0";
        if (Oficina.equals(""))
            Oficina = "0";
        if (Cuenta.equals(""))
            Cuenta = "0";
        java.text.DecimalFormat formato = new java.text.DecimalFormat("0000");
        Entidad =  formato.format(new Integer(Entidad).intValue());
        formato = new java.text.DecimalFormat("0000");
        Oficina = formato.format(new Integer(Oficina).intValue());
        formato = new java.text.DecimalFormat("0000000000");
        Cuenta = formato.format(new Double(Cuenta).doubleValue());
        return checkPesos(Entidad + Oficina) + checkPesos(Cuenta);
    }
    
    private static String checkPesos(String cadena){
        int [] pesos = {1,2,4,8,5,10,9,7,3,6};
        
        int total = 0;
        int j = 9;
        
        for (int i = cadena.length()-1; i >=0; --i){
            total = total + ( new Integer(cadena.substring(i,i+1)).intValue() * pesos[j]);
            j--;
        }
        int i = 11- (total % 11);
        if (i == 10)
            i = 1;
        else {
            if (i == 11)
                i = 0;
        }
        return new String().valueOf(i);
    }
    
    public boolean validarIBAN(String IBAN) {
        boolean lbResult = false;
        if (IBAN!=null && IBAN.trim().length() == 24){
            IBAN = IBAN.toUpperCase();
            String abre = IBAN.substring(0,2);
            String dc = IBAN.substring(2,4);
            String ccc = IBAN.substring(4,24);

            lbResult = (ObtenerIBAN(ccc, abre).equalsIgnoreCase(IBAN));
        }
        return lbResult;
    }
    
    /**
     * Obtener el IBAN de una cuenta corriente
     *
     * @param cuenta
     * @param abreviation
     * @return
     */
    public String ObtenerIBAN(String cuenta, String abreviation) {
        try{
            //Substituyo todas las letras del BBAN por números
            String ccc = cuenta.replace(" ", "");
            for (int i = 0; i <= ccc.length() - 1; i++) {
                if (Character.isLetter(ccc.charAt(i))) {
                    if ("ES".equals(abreviation)) {
                        //En España no pueden haber letras
                        return "";
                    }
                    char letra = ccc.charAt(i);
                    int value = (int) letra - 55;
                    StringBuilder newString = new StringBuilder(ccc);
                    newString.replace(i, i + 1, String.valueOf(value));
                    ccc = newString.toString();
                }
            }
            if (!validarCuenta(ccc)) {
                //Validación no pasada
                return "";
            }
            //Calculo los dígitos del país
            abreviation = abreviation.toUpperCase();
            if (abreviation.isEmpty() || abreviation.length() != 2) {
                return "";
            }

            String dpais = "";
            for (int i = 0; i <= abreviation.length() - 1; i++) {
                char letra = abreviation.charAt(i);
                int value = (int) letra - 55;
                dpais += String.valueOf(value);
            }

            //Monto el BBAN para calcular el dígito de control
            String iban = ccc + dpais + "00";

            int modulus97 = calculateModulus97(iban);
            int charValue = (98 - modulus97);
            String checkDigit = Integer.toString(charValue);
            String dcIBAN = (charValue > 9 ? checkDigit : "0" + checkDigit);
            return abreviation + dcIBAN + cuenta;
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            return "";
        }
    }    

    /**
     * Calcular los dígitos de control del IBAN 
     * Código adapatado de
     * http://commons.apache.org/proper/commons-validator
     * 
     * @param iban
     * @return
     */
    private static int calculateModulus97(String iban) {
        long total = 0;
        for (int i = 0; i < iban.length(); i++) {
            int charValue = Character.getNumericValue(iban.charAt(i));
            total = (charValue > 9 ? total * 100 : total * 10) + charValue;
            if (String.valueOf(total).length() > 9) {
                total = (total % 97);
            }
        }
        return (int)(total % 97);
    }
    
    /**
     * Obtener el código SWIFT de un banco
     * 
     * @param oficina
     * @return
     */
    public static String ObtenerSWIFT(String oficina) {
        if (oficina.equals("2100"))
                return "CAIXESBBXXX";
        if (oficina.equals("2013"))
                return "CESCESBBXXX";
        if (oficina.equals("0049"))
                return "BSCHESMM";
        if (oficina.equals("0182"))
                return "BBVAESMMXXX";
        if (oficina.equals("0081"))
                return "BSABESBB";
        if (oficina.equals("0238"))
                return "PSTRESMMBAR";
        if (oficina.equals("2038"))
                return "CAHMESMMXXX";
        if (oficina.equals("0019"))
                return "BSCHESMM";
        if (oficina.equals("2103"))
                return "UCJAES2MXXX";
        if (oficina.equals("2107"))
                return "BVINES2BXXX";

        return "";
    }    
    public String getCCCFormateado(boolean pbReturnNumCuenta, boolean pbConBarras, boolean pbConIBAN){
        if(pbConIBAN){
            return getCCCFormateado(getIBAN(), pbReturnNumCuenta, pbConBarras);
        }else{
            return getCCCFormateado(getCCC(), pbReturnNumCuenta, pbConBarras);
        }
    }
    public String getCCCFormateado(boolean pbReturnNumCuenta, boolean pbConIBAN, String psSeparador){
        if(pbConIBAN){
            return getCCCFormateado(getIBAN(), pbReturnNumCuenta, psSeparador);
        }else{
            return getCCCFormateado(getCCC(), pbReturnNumCuenta, psSeparador);
        }
    }
    
    
    public static String getCCCFormateado(String psCCC, boolean pbReturnNumCuenta, boolean pbConBarras){
        String lsResult = "";
        if(!JCadenas.isVacio(psCCC)){
            lsResult = psCCC;
            if(psCCC.length()==20){
                lsResult = psCCC.substring(0, 4) + " " +
                            psCCC.substring(4, 8) + " " +
                            psCCC.substring(8, 10) + (pbReturnNumCuenta ? "\n" : " ") +
                            psCCC.substring(10);
            }
            if(psCCC.length()==24){
                lsResult = psCCC.substring(0, 4) + " " +
                            psCCC.substring(4, 8) + " " +
                            psCCC.substring(8, 12) + " " +
                            psCCC.substring(12, 14) + (pbReturnNumCuenta ? "\n" : " ") +
                            psCCC.substring(14);
            }
            if(pbConBarras){
                lsResult = lsResult.replace(" ", "/");
            }
        }
        return lsResult;
    }
    public static String getCCCFormateado(String psCCC, boolean pbReturnNumCuenta, String psSeparador){
        String lsResult = "";
        if(!JCadenas.isVacio(psCCC)){
            lsResult = psCCC;
            if(psCCC.length()==20){
                lsResult = psCCC.substring(0, 4) + " " +
                            psCCC.substring(4, 8) + " " +
                            psCCC.substring(8, 10) + (pbReturnNumCuenta ? "\n" : " ") +
                            psCCC.substring(10);
            }
            if(psCCC.length()==24){
                lsResult = psCCC.substring(0, 4) + " " +
                            psCCC.substring(4, 8) + " " +
                            psCCC.substring(8, 12) + " " +
                            psCCC.substring(12, 14) + (pbReturnNumCuenta ? "\n" : " ") +
                            psCCC.substring(14);
            }
            lsResult = lsResult.replace(" ", psSeparador);
        }
        return lsResult;
    }    
}