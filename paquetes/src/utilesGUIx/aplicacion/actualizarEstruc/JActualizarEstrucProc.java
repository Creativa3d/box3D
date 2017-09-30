/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.actualizarEstruc;

import ListDatos.IServerServidorDatos;
import utiles.IListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIx.aplicacion.actualizarEstruc.JActualizarEstruc;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;


public class JActualizarEstrucProc extends JProcesoAccionAbstracX {
    private final IListaElementos moLista;
    private final IServerServidorDatos moServer;
    private String msMensajes="";

    public JActualizarEstrucProc(IListaElementos poLista, IServerServidorDatos poServer){
        moLista=poLista;
        moServer=poServer;
    }

    public String getTitulo() {
        return "Actualizar estructura";
    }

    public int getNumeroRegistros() {
        return moLista.size();
    }

    public void procesar() throws Throwable {
        for(mlRegistroActual = 0 ; mlRegistroActual<moLista.size() && !mbCancelado; mlRegistroActual++){
            JActualizarEstruc loEstruc =
                    new JActualizarEstruc(
                    (IActualizarEstruc) moLista.get(mlRegistroActual),
                    moServer
                    );
            loEstruc.setMensajes(false);
            loEstruc.actualizar();
            msMensajes+=loEstruc.getMensajesResultado();
        }
        mbFin=true;
    }

    public String getTituloRegistroActual() {
        return "";
    }

    public void mostrarMensaje(String psMensaje) {
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, psMensaje + "\n" + msMensajes, IMostrarPantalla.mclMensajeInformacion, null);
    }

}
