/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionJasper.infGenerico;





import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.busqueda.IConsulta;


public class JInfGENERICO extends utilesGUIx.formsGenericos.JT2GENERALBASE2 {
    private IConsultaGENERICO moConsulta;

    public JInfGENERICO(IConsultaGENERICO poConsulta) throws Exception{
        moConsulta = poConsulta;
        getParametros().getBotonesGenerales().setVisibleModoBusqueda();
        getParametros().getBotonesGenerales().getAceptar().setVisible(false);
        getParametros().getBotonesGenerales().getCancelar().setVisible(false);
        
        getParametros().getBotonesGenerales().addBotones(poConsulta.getListaAcciones());


        getParametros().setLongitudCampos(poConsulta.getLongitudes());
        getParametros().setNombre(poConsulta.getNombre());
        getParametros().setTitulo(poConsulta.getTitulo());
    }
    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void anadir() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void borrar(int plIndex) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void editar(int plIndex) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarFormPrinci() throws Exception {
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(this);
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        
    }
}
