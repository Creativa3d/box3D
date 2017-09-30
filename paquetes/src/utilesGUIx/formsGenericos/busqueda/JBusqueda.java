/*
 * JBusqueda.java
 *
 * Created on 13 de octubre de 2004, 16:55
 */

package utilesGUIx.formsGenericos.busqueda;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import utilesGUIx.ActionEventCZ;

import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;

/**Clase de para ejecutar un form. de búsqueda*/
public class JBusqueda extends  JT2GENERALBASEModelo {
    protected IConsulta moConsulta;
    public int mlAncho = 740;
    public int mlAlto = 480;
    public boolean mbFiltro = true;
    /**
     * Contructor
     * @param poConsulta consulta
     * @param psTitulo titulo
     * @param pbConCancelar si tiene cancelar
     */
    public JBusqueda(IConsulta poConsulta, String psTitulo, boolean pbConCancelar){
        moConsulta = poConsulta;
        getParametros().setNombre(psTitulo);
        addBotones();
        getParametros().setPlugInPasados(true);
//        mbConCancelar = pbConCancelar;
    }



    /**
     * Contructor
     * @param poConsulta consulta
     * @param psTitulo título
     */
    public JBusqueda(IConsulta poConsulta, String psTitulo){
        this(poConsulta, psTitulo, true);
    }
/**
     * Muestra el form. de busq.
     * @param poCall
     * @throws Exception error
     */
    public void mostrarBusq(CallBack<IPanelControlador> poCall) throws Exception {
        if(poCall!=null){
            moParametros.setCallBack(poCall);
        }
        if(moParametros.getCallBack()!=null){
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarFormPrinci(this, mlAncho, mlAlto, 0 ,IMostrarPantalla.mclEdicionFrame);
        }else{
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mostrarFormPrinci(this, mlAncho, mlAlto, 0 ,IMostrarPantalla.mclEdicionDialog);
        }
    }
    
    /**
     * Muestra el form. de busq.
     * @throws Exception error
     */
    public void mostrarBusq() throws Exception {
        mostrarBusq(null);

    }

    public void addBotones() {
        JPanelGeneralBotones loBotones = getParametros().getBotonesGenerales();
        loBotones.setVisibleModoBusqueda();
        loBotones.getFiltro().setVisible(mbFiltro);
    }
    
    public void setLongitudCampos(int[] palLongCampos){
        getParametros().setLongitudCampos(palLongCampos);
    }

    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void anadir() throws Exception {
    }

    public void borrar(int plIndex) throws Exception {
    }

    public void editar(int plIndex) throws Exception {
    }

    public void mostrarFormPrinci() throws Exception {
        mostrarBusq(null);
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
    }
    
    public static void mostrarList(final JListDatos poList) throws Exception{
            JBusqueda loBus = new JBusqueda(new IConsulta() {

                public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
                }

                public JListDatos getList() {
                    return poList;
                }

                public void addFilaPorClave(IFilaDatos poFila) throws Exception {
                }

                public boolean getPasarCache() {
                    return false;
                }
            }, "");
            loBus.mostrarBusq();        
    }

    public static void mostrarList(final JListDatos poList, CallBack<IPanelControlador> poCall) throws Exception{
            JBusqueda loBus = new JBusqueda(new IConsulta() {

                public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
                }

                public JListDatos getList() {
                    return poList;
                }

                public void addFilaPorClave(IFilaDatos poFila) throws Exception {
                }

                public boolean getPasarCache() {
                    return false;
                }
            }, "");
            loBus.mostrarBusq(poCall);        
    }    
}
