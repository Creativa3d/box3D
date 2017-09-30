/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionJasper;

import ar.com.fdvs.dj.domain.DJCalculation;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JInfGeneralJasperGrupo {
    public static final DJCalculation mcoSUM = null;

    private String msColumnaAAgrupar;
    private IListaElementos moListaOperaciones = new JListaElementos();

    /**
     * @return la Columna por la que se agrupa
     */
    public String getColumnaAAgrupar() {
        return msColumnaAAgrupar;
    }

    /**
     * @param msColumnaAAgrupar Columna por la que se agrupa
     */
    public void setColumnaAAgrupar(String msColumnaAAgrupar) {
        this.msColumnaAAgrupar = msColumnaAAgrupar;
    }

    /**
     * Añade una operacion por un campo en concreto
     * @param psNombreColumna columna a operar (a sumar, contar...)
     * @param poOperacion Tipo operacion
     * @param pbPie Si esta en el pie del grupo, si no esta en la cabezera
     */
    public void addOperacion(String psNombreColumna, DJCalculation poOperacion, boolean pbPie){
        moListaOperaciones.add(new JInfGeneralJasperGrupoOperacion(psNombreColumna, poOperacion, pbPie));
    }

    IListaElementos getOperaciones() {
        return moListaOperaciones;
    }
}
