/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesEjecutar.IAbstractFactoryEjecutar;
import utilesEjecutar.JAbstractFactoryEjecutar;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.gui.JFormErrores;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;

/**
 * Lanza el proceso de actualizacion a traves de un servidor externo
 * este proceso no se puede lanzar dos veces a la vez
*/
public class JProcesoActualizar extends JProcesoAccionAbstracX {
    private JControladorCoordinadorEjecutar moCoordinador;
    private IAbstractFactoryEjecutar moFactoria;
    private String msFichero;
    private boolean mbActualizado=false;
    private boolean mbPresentarErrores;
    private boolean mbPresentarMensajes=false;

    private static boolean mbMonitor = false;
    private IListaElementos moListeners = new JListaElementos();

    public static synchronized void setMonitor(boolean pbMonitor){
        mbMonitor=pbMonitor;
    }
    public static synchronized boolean isMonitorActivo(){
        return mbMonitor;
    }

    public void addListener(IProcesoActualizarListener poListener){
        moListeners.add(poListener);
    }
    private void llamarListener(String psMensaje, Throwable e){
        for(int i = 0 ;i < moListeners.size(); i++){
            IProcesoActualizarListener loL = (IProcesoActualizarListener) moListeners.get(i);
            if(e!=null){
                loL.mostrarError(this, e);
            }else{
                loL.mostrarMensaje(this, psMensaje);
            }
        }
    }

    /**
     * Constructor
     * @param pasARRAY lista de parametros, el primer valor debe ser siempre el path
     * del fichero ejecutar.xml, los otros param son del formato param=valor
     * @param poFactoria Factoria de creacion de objetos, si se pasa null crea la factoria por defecto
     */
    public JProcesoActualizar(
            final String[] pasARRAY,
            IAbstractFactoryEjecutar poFactoria,
            boolean pbPresentarErrores,
            boolean pbPresentarMensaje
            ){
        mbPresentarErrores=pbPresentarErrores;
        mbPresentarMensajes=pbPresentarMensaje;
        moFactoria = poFactoria;
        if( moFactoria==null){
            moFactoria = new JAbstractFactoryEjecutar();
        }
        mbFin=false;
        moCoordinador = new JControladorCoordinadorEjecutar();
        if(pasARRAY!= null && pasARRAY.length>0){
            msFichero = pasARRAY[0];
            for(int i = 1 ; i < pasARRAY.length;i++){
                String lsParam = pasARRAY[i];
                int lPosi = lsParam.indexOf('=');
                if(lPosi>=0 && lPosi < (lsParam.length()-1)){
                    moCoordinador.setVariableYReemp(
                        lsParam.substring(0, lPosi),
                        lsParam.substring(lPosi+1, lsParam.length()));
                }else{
                    moCoordinador.setVariableYReemp(lsParam, "");
                }
            }
        }
    }

    public JProcesoActualizar(
            final String[] pasARRAY,
            IAbstractFactoryEjecutar poFactoria){
        this(pasARRAY, poFactoria, true, true);
    }
    public String getTitulo() {
        return "Actualizando...";
    }

    public int getNumeroRegistros() {
        return moCoordinador.getTotal();
    }

    public void procesar() throws Throwable {
        if(!isMonitorActivo()){
            setMonitor(true);
            try{
                mbActualizado = moCoordinador.ejecutarCompleto(
                    msFichero,
                    moFactoria);
            } finally {
                setMonitor(false);
            }
        }
        mbFin=true;
    }

    public int getNumeroRegistro() {
        return moCoordinador.getCompletado();
    }

    public String getTituloRegistroActual() {
        return moCoordinador.getTextoGUI();
    }

    public void setCancelado(boolean pbCancelado) {
        mbCancelado = pbCancelado;
        moCoordinador.cancelar();
    }

    public void mostrarMensaje(String psMensaje) {
        if(moCoordinador.getErrores().size()>0 && mbPresentarErrores){
               SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                JFormErrores loFormErrores = new JFormErrores(moCoordinador);
                                loFormErrores.setBounds(
                                        0, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-250,
                                        350, 200);
                                loFormErrores.setVisible(true);
                            }
                        });
        }
        if(isActualizado() && mbPresentarMensajes){
            utilesGUIx.msgbox.JMsgBox.mensajeInformacion(null, "Se ha descargado una nueva versión del programa, reiniciar la aplicación para aplicar los cambios");
        }
        llamarListener(psMensaje, null);
    }

    public void mostrarError(Throwable e) {
        if(mbPresentarErrores){
            utilesGUIx.msgbox.JMsgBox.mensajeError(null, e);
        }
        llamarListener(null, e);
    }

    public void finalizar() {
    }

    /**
     * @return the mbActualizado
     */
    public boolean isActualizado() {
        return mbActualizado;
    }
}
