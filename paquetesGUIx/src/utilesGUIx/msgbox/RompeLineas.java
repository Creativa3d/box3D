/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.msgbox;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class RompeLineas{

    String msTexto;
    int mlComienzoNueva = 0;
    int mlLen;
    boolean mbHayRetornoCarroUltLinea=false;

    public RompeLineas(String psTexto){
        msTexto = psTexto;
        mlLen = msTexto.length();
    }
    public boolean isHayRetornoCarroUltLinea(){
        return mbHayRetornoCarroUltLinea;
    }
    public boolean isHayMasLineas(){
        return mlComienzoNueva < msTexto.length();
    }
    public String getNuevaLinea(final Graphics2D g, final float pdWidth){
        //cogemso el FontMetric para hacer calculos
        FontMetrics loMetric = g.getFontMetrics();
        String lsReturn=null;
        mbHayRetornoCarroUltLinea=false;
        //si se ha superado la linea devolvcemos nulo, indicando que no hay mas texto
        if(mlComienzoNueva < msTexto.length()) {
            //cogemos el parrfo restante
            String lsTexto = msTexto.substring(mlComienzoNueva);
            String lsTextoADevolver = "";
            String lsPalabra = "";
            int lIndex = 0;
//            int lUltEspacio = 0;
            boolean lbFin = false;
            //mientras el texto a devoelver sea menor que el ancho
            while (!lbFin){
                //cogemos una palabra
                lsPalabra = msDevolverPalabra(lsTexto, lIndex);
                if(lsPalabra==null) {
                    lbFin = true;
                } else{
                    //si añadiendo la palabra superamos el ancho finalizamos el bucle
                    //si añadiendo la palabra NO superamos el ancho añadimos la palabra
                    if (loMetric.stringWidth(lsPalabra + lsTextoADevolver) > pdWidth){
                        lbFin = true;
                    }else{
                        lsTextoADevolver = lsTextoADevolver +  lsPalabra;
                        lIndex += lsPalabra.length();
                        //si ya no hay caracteres fin del bucle
                        if(lIndex >= lsTexto.length()) {
                            lbFin = true;
                        }else{
                            //si el caracter que hay es un retorno de carro finalizamos el bucle
                            if (lsTexto.charAt(lIndex) == '\n'){
                                lbFin = true;
                                mbHayRetornoCarroUltLinea=true;
                            }else{
                                lsTextoADevolver += ' ' ;
                            }
                            lIndex++;
                        }
                    }
                }
            }
            //si el texto a devoelver es vacio es pq no hay ninguna palabra que quepa en
            //el ancho especificado por los que tenemso que coger letras hasta llenar el
            //ancho y si hace falta truncar palabra
            if((lsTextoADevolver.compareTo("")==0) && (lIndex ==0 )){
                lIndex = 0;
                //mientras el texto a devoelver sea menor que el ancho
                while (loMetric.stringWidth(lsTextoADevolver + lsTexto.charAt(lIndex) ) < pdWidth){
                    lsTextoADevolver += lsTexto.charAt(lIndex);
                    lIndex++;
                }
            }
            //incrementamos la siguiente linea de comienzo
            mlComienzoNueva += lIndex;
            //devolvemos el texto
            lsReturn = lsTextoADevolver;
        }
        return lsReturn;
    }

    private String msDevolverPalabra(final String psCadena, int plIndex){
        String lsReturn = null;
        int lIndexIni = plIndex;
        if(plIndex<psCadena.length() ) {
            while ((psCadena.charAt(plIndex) != ' ') && (psCadena.charAt(plIndex) != '\n')){
                plIndex++;
                if(plIndex>=psCadena.length()) {
                    return psCadena.substring(lIndexIni, psCadena.length());
                }
            }
            lsReturn = psCadena.substring(lIndexIni, plIndex);
        }
        return lsReturn;
    }
}
