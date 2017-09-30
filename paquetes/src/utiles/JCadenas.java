/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

/**
 *
 * @author eduardo
 */
public class JCadenas {
    public static final String mcsCaracteresSeparacion = " .,\t\n;:";
    
    public static boolean isVacio(String psCadena1){
        return psCadena1==null || psCadena1.equals("");
    }
    public static boolean isEquals(String psCadena1, String psCadena2){
        return isEquals(psCadena1, psCadena2, false);
    }
    public static boolean isEquals(String psCadena1, String psCadena2, boolean pbIgnoreCase){
        boolean lbResult = true;
        if(psCadena1==null || psCadena1.equals("")){
            if(psCadena2==null || psCadena2.equals("")){
                lbResult = true;
            }else{
                lbResult = false;
            }
        }else{
            if(psCadena2==null || psCadena2.equals("")){
                lbResult = false;
            }else{
                lbResult = pbIgnoreCase ? psCadena1.equalsIgnoreCase(psCadena2) : psCadena1.equals(psCadena2); 
            }
        }

        return lbResult;
    }
    /**
     * anade/trunca de la cadena hasta el numero de caracteres (numCar)
     * @param psCadena1 cadena inicial
     * @param psRelleno caracter de relleno
     * @param numCar numero de caracteres max/min
     * @return cadena rellenada/truncada
     */
    public static String msRellenarIzq(String psCadena1, String psRelleno, int numCar) {
        return msRellenar(psCadena1, psRelleno, numCar, false);
    }

    /**
     * anade/trunca de la cadena hasta el numero de caracteres (numCar)
     * @param psCadena1 cadena inicial
     * @param psRelleno caracter de relleno
     * @param numCar numero de caracteres max/min
     * @return cadena rellenada/truncada
     */
    public static String msRellenarDer(String psCadena1, String psRelleno, int numCar) {
        return msRellenar(psCadena1, psRelleno, numCar, true);
    }

    /**
     * anade/trunca de la cadena hasta el numero de caracteres (numCar)
     * @param psCadena1 cadena inicial
     * @param psRelleno caracter de relleno
     * @param numCar numero de caracteres max/min
     * @return cadena rellenada/truncada
     */
    public static String msRellenar(String psCadena1, String psRelleno, int numCar, boolean pbDerecha) {
        StringBuilder cad = new StringBuilder();
        int i;
        String lsCadena = psCadena1;
        cad.setLength(0);
        if (lsCadena.length() < numCar) {
            for (i = 1; i <= (numCar - lsCadena.length()); i++) {
                cad.append(psRelleno);
            }
            if (pbDerecha) {
                lsCadena = (lsCadena + cad.toString());
            } else {
                lsCadena = (cad.toString() + lsCadena);
            }
        } else {
            lsCadena = lsCadena.substring(0, numCar);
        }
        return (lsCadena);
    }
    public static String[] getPalabras(String psFrase){
        return getPalabras(psFrase, mcsCaracteresSeparacion);
    }
    public static String[] getPalabras(String psFrase, String psCaractSeparacion){
        String[] loResult = null;
        int lPalabras = 0;
        boolean lbAnt = true;
        boolean lbAlgunaLetra=false;
        for(int i = 0 ; i < psFrase.length(); i++){
            char lcCarac = psFrase.charAt(i);
            if((psCaractSeparacion.indexOf(lcCarac)>=0
//                   lcCarac == ' ' ||
//                   lcCarac == '.' ||
//                   lcCarac == ',' ||
//                   lcCarac == '\t' ||
//                   lcCarac == '\n' ||
//                   lcCarac == ';' ||
//                   lcCarac == ':'
                   ) &&
                   i !=0 &&
                   i != psFrase.length()
               ){
                if(!lbAnt){
                    lPalabras++;
                    lbAlgunaLetra=false;
                }
                lbAnt = true;
            }else{
                lbAlgunaLetra=true;
                lbAnt = false;
            }
        }
        if(lbAlgunaLetra){
            lPalabras++;
        }
        loResult = new String[lPalabras];
        for(int i = 0 ; i< loResult.length;i++){
            loResult[i]="";
        }
        int lIndex = 0;
        lbAnt=true;
        lbAlgunaLetra=false;
        StringBuilder lsAux = new StringBuilder();
        for(int i = 0 ; i < psFrase.length(); i++){
            char lcCarac = psFrase.charAt(i);
            if((psCaractSeparacion.indexOf(lcCarac)>=0
//                   lcCarac == ' ' ||
//                   lcCarac == '.' ||
//                   lcCarac == ',' ||
//                   lcCarac == '\t' ||
//                   lcCarac == '\n' ||
//                   lcCarac == ';' ||
//                   lcCarac == ':'
                   )
               ){
                if(i !=0 &&
                   i != psFrase.length() &&
                   !lbAnt){
                    loResult[lIndex] = lsAux.toString();
                    lIndex++;
                    lsAux = new StringBuilder();
                    lbAlgunaLetra=false;
                }
                lbAnt=true;
            }else{
                lbAnt=false;
                lbAlgunaLetra=true;
                lsAux.append(lcCarac);
            }
        }
        if(lbAlgunaLetra){
            loResult[lIndex] = lsAux.toString();
        }

        return loResult;
    }
    /**
     * Se Reemplaza psCadena1 por psCadena2 en psCadena 
     * @param psCadena
     * @param psCadena1
     * @param psCadena2
     * @return el resultado del reemplazo
     */
    public static String replace2(final String psCadena, final String psCadena1, final String psCadena2) {
        int llPosicion;
        String lsSustituir = psCadena;
        llPosicion = lsSustituir.indexOf(psCadena1);
        for (; llPosicion >= 0;) {
            lsSustituir = lsSustituir.substring(0, llPosicion) + psCadena2 + lsSustituir.substring(llPosicion + psCadena1.length());
            llPosicion = lsSustituir.indexOf(psCadena1, llPosicion + psCadena2.length());
        }
        return lsSustituir;
    }
    /**
     * substring, hace lo mismo que String.substring, pero sin errores , por nulos o longitudes
     */
    public static String substring(String psCadena,int plOrigen, int plFin){
        if(psCadena!=null && !psCadena.equals("")){
            if(psCadena.length()<plFin){
                plFin = psCadena.length();
            }
        }else{
            return "";
        }
        return psCadena.substring(plOrigen, plFin);
    }

    public static boolean isAlfanumerico(char psC) {
        String ls="abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ01234567890";
        return ls.indexOf(psC)>=0;
    }
    public static boolean isAlfanumericoExtend(char psC) {
        boolean lbResult = isAlfanumerico(psC);
        if(!lbResult){
            String ls="_-.,;:!¡/\\%()=?¿";
            if(ls.indexOf(psC)>=0){
                lbResult=true;
            }
        }
        return lbResult;
    }
    
}
