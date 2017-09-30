/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionJasper;

import ar.com.fdvs.dj.domain.DJCalculation;

/**
 *
 * @author eduardo
 */
public class JInfGeneralJasperGrupoOperacion {
    public String msNombreColumna;
    public DJCalculation moOperacion;
    public boolean mbPie;

    JInfGeneralJasperGrupoOperacion(String psNombreColumna, DJCalculation poOperacion, boolean pbPie){
        msNombreColumna=psNombreColumna;
        moOperacion=poOperacion;
        mbPie=pbPie;
    }
}
