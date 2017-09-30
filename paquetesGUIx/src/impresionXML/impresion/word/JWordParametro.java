/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.word;

public class JWordParametro {
    private final String msNombre;
    private final String msValor;
    private final int mlTipo;

    public JWordParametro(String psNombre, String psValor, int plTipo) {
        msNombre = psNombre;
        msValor = psValor;
        mlTipo=plTipo;
    }

    /**
     * @return the msNombre
     */
    public String getNombre() {
        return msNombre;
    }

    /**
     * @return the msValor
     */
    public String getValor() {
        return msValor;
    }

    /**
     * @return the mlTipo
     */
    public int getTipo() {
        return mlTipo;
    }

}
