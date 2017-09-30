/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionJasper.infGenerico;

import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.busqueda.IConsulta;


public interface IConsultaGENERICO extends IConsulta {

    
    public String getNombre();
    
    public String getTitulo();
    
    public int[] getLongitudes();
    
    public IBotonRelacionado[] getListaAcciones();
}
