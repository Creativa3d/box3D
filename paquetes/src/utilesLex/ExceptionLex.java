/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesLex;

import utiles.IListaElementos;

public class ExceptionLex extends Exception {
    private IListaElementos moCollecResul;

    public ExceptionLex(IListaElementos loCollecResul) {
        super("La cadena no ha sido bien procesada");
        moCollecResul = loCollecResul;
    }

    public IListaElementos getCollecResult(){
        return moCollecResul;
    }

}
