/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesLogicaDifusa;

public class JOrdenacionLDAlgoritLevenshtein extends JOrdenacionLD {
    private static final long serialVersionUID = 1L;
    //para ahorrar memoria, se almacena modularmente y asi
    //no hace falta crear un array para cada busqueda
    private int[][] distance = new int[50][50];
    private boolean mbSoloCaracterIngles = true;

    public double getPorcentajeCadena(String s1, String s2) {
        return ((double)(s2.length()-computeLevenshteinDistance(s1.toCharArray(), s2.toCharArray())) / (double)s2.length()) * 100;
    }
    //se pone sincronizado, this is the questions
    //pq si se pone sincronizado, se pierde velocidad.
    //q es una de las sosas q buscamos ciuando ponemos distance modular
    private int computeLevenshteinDistance(char[] str1, char[] str2) {
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
                    if (JOrdenacionLDAlgoritDamerauLevenshtein.getCaracterIngles(str1[i-1])
                            ==
                        JOrdenacionLDAlgoritDamerauLevenshtein.getCaracterIngles(str2[j-1])) {
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
                distance[i][j] = minimum(distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + cost);
            }
        }

        return distance[str1.length][str2.length];
    }

    public static int minimum(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        }
        if (b <= a && b <= c) {
            return b;
        }
        return c;
    }

}
