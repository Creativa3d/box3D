/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesLogicaDifusa;

public class JOrdenacionLDAlgoritDamerauLevenshtein extends JOrdenacionLD {
    private static final long serialVersionUID = 1L;
    //para ahorrar memoria, se almacena modularmente y asi
    //no hace falta crear un array para cada busqueda
    private int[][] distance = new int[50][50];
    private boolean mbSoloCaracterIngles = true;

    public double getPorcentajeCadena(String s1, String s2) {
        if(s1==null){
            s1="";
        }
        double ldResult = ((double) (s2.length() - damerauLevenshteinDistance(s1.toCharArray(), s2.toCharArray())) / (double) s2.length()) * 100;
//        System.out.println(s1 + "/" + s2 + "=" + String.valueOf(ldResult));
        return ldResult;
    }

    //se pone sincronizado, this is the questions
    //pq si se pone sincronizado, se pierde velocidad.
    //q es una de las sosas q buscamos ciuando ponemos distance modular
    private int damerauLevenshteinDistance(char[] str1, char[] str2) {
        // d is a table with lenStr1+1 rows and lenStr2+1 columns
        if(distance.length <= (str1.length + 1) ||
           distance[0].length <=(str2.length + 1) ){
            distance = new int[Math.max(str1.length + 1, distance.length) ]
                              [Math.max(str2.length + 1, distance[0].length) ];
        }
        int cost;
        //inicializamos el array
        for (int i = 0; i <= str1.length; i++) {
            for (int j = 0; j <= str2.length; j++) {
                distance[i][j] = 0;
            }
        }
        //inicialimos los valores de la matriz de la 1? columna
        for (int i = 0; i <= str1.length; i++) {
            distance[i][0] = i;
        }
        //inicialimos los valores de la matriz de la 1? fila
        for (int j = 0; j <= str2.length; j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                if(mbSoloCaracterIngles){
                    if (getCaracterIngles(str1[i-1]) == getCaracterIngles(str2[j-1])) {
                        cost = 0;
                    } else {
                        cost = 1;
                    }
                }else{
                    if (str1[i-1] == str2[j-1]) {
                        cost = 0;
                    } else {
                        cost = 1;
                    }
                }
                distance[i][j] = JOrdenacionLDAlgoritLevenshtein.minimum(
                        distance[i - 1][j] + 1, // deletion
                        distance[i][j - 1] + 1, // insertion
                        distance[i - 1][j - 1] + cost // substitution
                        );
                boolean lbTransposicion;
                if(mbSoloCaracterIngles){
                    lbTransposicion =
                            i > 1 && j > 1 &&
                            getCaracterIngles(str1[i-1]) == getCaracterIngles(str2[j - 1-1]) &&
                            getCaracterIngles(str1[i - 1-1]) == getCaracterIngles(str2[j-1]);
                }else{
                    lbTransposicion =
                            i > 1 && j > 1 &&
                            str1[i-1] == str2[j - 1-1] &&
                            str1[i - 1-1] == str2[j-1];
                }
                if (lbTransposicion) {
                    distance[i][j] = Math.min(
                            distance[i][j],
                            distance[i - 2][j - 2] + cost // transposition,
                            );
                }
            }
        }


        return distance[str1.length][str2.length];
    }
    public static char getCaracterIngles(char lc2){
        char lResult=lc2;
        if (lc2 == 'á' ) {
            lResult='a';
        }else if (lc2 == 'é' ) {
            lResult='e';
        }else if (lc2 == 'í' ) {
            lResult='i';
        }else if (lc2 == 'ó' ) {
            lResult='o';
        }else if (lc2 == 'ú' ) {
            lResult='u';
        }else if (lc2 == 'ñ' ) {
            lResult='n';
        }
        return lResult;
    }



}
