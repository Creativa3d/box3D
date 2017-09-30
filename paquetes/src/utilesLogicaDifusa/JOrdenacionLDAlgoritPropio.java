/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesLogicaDifusa;

public class JOrdenacionLDAlgoritPropio extends JOrdenacionLD {
    private static final long serialVersionUID = 1L;

    public double getPorcentajeCadena(String s1, String s2) {
        double ldResult = 0;

        for (int i = 0; i < s2.length() && i < s1.length(); i++) {
            char lc2 = s2.charAt(i);
            char lc1 = s1.charAt(i);
            if (lc2 == lc1) {
                ldResult += 1;
            } else {
                boolean lbContinuar = true;
                if (lc2 == 'á' && lbContinuar) {
                    if ('a' == lc1) {
                        ldResult += 0.95;
                        lbContinuar = false;
                    }
                }
                if (lc2 == 'é' && lbContinuar) {
                    if ('e' == lc1) {
                        ldResult += 0.95;
                        lbContinuar = false;
                    }
                }
                if (lc2 == 'í' && lbContinuar) {
                    if ('i' == lc1) {
                        ldResult += 0.95;
                        lbContinuar = false;
                    }
                }
                if (lc2 == 'ó' && lbContinuar) {
                    if ('o' == lc1) {
                        ldResult += 0.95;
                        lbContinuar = false;
                    }
                }
                if (lc2 == 'ú' && lbContinuar) {
                    if ('u' == lc1) {
                        ldResult += 0.95;
                        lbContinuar = false;
                    }
                }
                if (lc2 == 'ñ' && lbContinuar) {
                    if ('n' == lc1) {
                        ldResult += 0.95;
                        lbContinuar = false;
                    }
                }

                if (i > 0 && lbContinuar) {
                    if (s2.charAt(i - 1) == lc1) {
                        ldResult += 0.75;
                        lbContinuar = false;
                    }
                }
                if (i < s2.length() - 1 && lbContinuar) {
                    if (s2.charAt(i + 1) == lc1) {
                        ldResult += 0.75;
                        lbContinuar = false;
                    }
                }

            }
        }
        return (ldResult / s2.length()) * 100;
    }


}
