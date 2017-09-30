/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

/**
 *
 * @author eduardo
 */
public class JRUT {
    public static String getDigitoVerificador(String psRUT) {
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        if(psRUT.length()!=8){
            return null;
        }
        return getDigitoVerificadorGenerico(psRUT);
    }
    public static String getDigitoVerificadorGenerico(String psRUT) {
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        int[] lalSerie = new int[]{2,3,4,5,6,7,2,3,4,5};
        
        int lPosi=0;
        int lTotal = 0;
        for(int i = psRUT.length()-1; i >=0; i--){
            lTotal += Integer.valueOf(String.valueOf(psRUT.charAt(i)))*lalSerie[lPosi];
            lPosi++;
        }
        
        int lDivEntera = lTotal / 11;
        int lResta = lTotal - (11*lDivEntera);
        int lResult = 11 - lResta;
        if(lResult==11){
            return "0";
        } else if(lResult==10){
            return "K";
        } else {
            return String.valueOf(lResult);
        }
    }

    
    public static boolean isRUTOK(String psRUT){
        if(JCadenas.isVacio(psRUT)){
            return false;
        }
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        if(psRUT.length()!=9){
            return false;
        }
        String lsNum = psRUT.substring(0, 8);
        String lsDV = psRUT.substring(8);

        return getDigitoVerificador(lsNum).equalsIgnoreCase(lsDV);
    }
    
    public static String getRUTFormateado(String psTexto){
        if(JCadenas.isVacio(psTexto)){
            return "";
        }
        if(psTexto.length()==9){
            return psTexto.substring(0, 8) + "-"+psTexto.substring(8, 9);
        }else{
            return psTexto;
        }
    }
    public static String extraerDV(String psRUT){
        if(JCadenas.isVacio(psRUT)){
            return "";
        }
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        if(psRUT.length()==9){
            return psRUT.substring(8, 9);
        }else{
            return "";
        }
    }
    public static String extraerNUMERO(String psRUT){
        if(JCadenas.isVacio(psRUT)){
            return "";
        }
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        if(psRUT.length()==9 || psRUT.length()==8){
            return psRUT.substring(0, 8);
        }else{
            return "";
        }
    }
    public static void main(String[] args) {
        try {
            System.out.println(getDigitoVerificador("30.686.957"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
}
