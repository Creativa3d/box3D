/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos.tablasControladoras;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.JT2GENERALBASE;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.avisos.consulta.JTFORMCUENTASCORREO;
import utilesGUIxAvisos.avisos.forms.JPanelCUENTASCORREO;
/**
 *
 * @author cmorales
 */
public class JT2CUENTASCORREO extends JT2GENERALBASE  {

    private JTFORMCUENTASCORREO moConsulta;
    public JT2CUENTASCORREO(List<JGUIxAvisosCorreo> poListaCorreos) {
        moConsulta = new JTFORMCUENTASCORREO(poListaCorreos);
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(getNombre());
        moParametros.setPlugInPasados(true);
        
    }

    @Override
    public IConsulta getConsulta() {
        return moConsulta;
    }

    @Override
    public void mostrarFormPrinci() throws Exception {
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(this, 530, 300);
    }

    @Override
    public void anadir() throws Exception {
        JGUIxAvisosCorreo loCorreo = new JGUIxAvisosCorreo();
        loCorreo.setTipoModif(JListDatos.mclNuevo);
        loCorreo.setServer(JGUIxConfigGlobalModelo.getInstancia().getServer());
        valoresDefecto(loCorreo);
        
        
        JPanelCUENTASCORREO loPanel = new JPanelCUENTASCORREO();
        loPanel.setDatos(loCorreo, this);

        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion) loPanel, loPanel);
    }

    public void valoresDefecto(JGUIxAvisosCorreo poTabla) throws Exception {
        if(poTabla.getTipoModif()== JListDatos.mclNuevo){
            poTabla.crearIdentificador();
        }
    }

    @Override
    public void borrar(int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);
        
        JGUIxAvisosCorreo loCorreo = JGUIxAvisosDatosGenerales.getAvisosCorreo(moConsulta.getListaCorreos(), moConsulta.getField(moConsulta.lPosiIdentificador).getString());
        loCorreo.setTipoModif(JListDatos.mclBorrar);
        
        IFilaDatos loFila = JTFORMCUENTASCORREO.getFilaDeConex(loCorreo);
        datosactualizados(loFila);

    }

    public void datosactualizados(JGUIxAvisosCorreo poFila) throws Exception {
        if(poFila.getTipoModif()==JListDatos.mclNuevo){
            moConsulta.getListaCorreos().add(poFila);
        }
        datosactualizados(JTFORMCUENTASCORREO.getFilaDeConex(poFila));
    }
    
    @Override
    public void editar(int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);

        JGUIxAvisosCorreo loCorreo = JGUIxAvisosDatosGenerales.getAvisosCorreo(moConsulta.getListaCorreos(), moConsulta.getField(moConsulta.lPosiIdentificador).getString());
        loCorreo.setTipoModif(JListDatos.mclEditar);
        valoresDefecto(loCorreo);
        
        JPanelCUENTASCORREO loPanel = new JPanelCUENTASCORREO();
        loPanel.setDatos(loCorreo, this);

        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion) loPanel, loPanel);
    }

    @Override
    public String getNombre() {
        return "Cuentas de correo";
    }

    @Override
    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMCUENTASCORREO.mclNumeroCampos];


        loInt[JTFORMCUENTASCORREO.lPosiIdentificador] = 0;
        loInt[JTFORMCUENTASCORREO.lPosiNOMBRE] = 80;
        loInt[JTFORMCUENTASCORREO.lPosiDIRECCION] = 80;
        loInt[JTFORMCUENTASCORREO.lPosiSERVIDORENTRANTE] = 80;
        loInt[JTFORMCUENTASCORREO.lPosiSERVIDORSALIENTE] = 80;
        return loInt;
    }

    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
    }
}
