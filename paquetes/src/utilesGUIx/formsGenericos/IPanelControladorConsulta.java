/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import utiles.IListaElementos;

public interface IPanelControladorConsulta extends IPanelControlador {
    public IListaElementos getFiltros();
    public void setFechas(String psFecha1, String psFecha2, int plCampo) throws Exception;
}
