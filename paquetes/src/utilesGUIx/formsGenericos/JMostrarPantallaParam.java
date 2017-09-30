/*
 * JMostrarPantallaParam.java
 *
 * Created on 23 de agosto de 2008, 12:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;
import utilesGUIx.plugin.IPlugInFrame;

public class JMostrarPantallaParam {
    public static final int mclEdicionDialog = IMostrarPantalla.mclEdicionDialog;
    public static final int mclEdicionFrame = IMostrarPantalla.mclEdicionFrame;
    public static final int mclEdicionInternal = IMostrarPantalla.mclEdicionInternal;
    public static final int mclEdicionInternalBlanco = IMostrarPantalla.mclEdicionInternalBlanco;


    private IFormEdicion moPanel;
    private IFormEdicionNavegador moPanelNave;
    private IPanelControlador moControlador;

    private int mlAncho;
    private int mlAlto;
    private int mlTipoForm;
    private int mlTipoMostrar;
    
    private boolean mbMaximizado = false;
        
    private Object moComponente;
    private String msTitulo="";
    private boolean mbUnaSolaInstancia = false;
    private boolean mbSiempreDelante = false;
    private boolean mbXCierra = true;
    private int mlX;
    private int mlY;
    private IMostrarPantallaCrear moCrear;
    private String msNombreUnico;
    private Object moImagenIcono;
    
    private CallBack<JMostrarPantallaParam> moCallBack;
    

    /** Creates a new instance of JMostrarPantallaParam */
    public JMostrarPantallaParam() {
    }
    public JMostrarPantallaParam(IMostrarPantallaCrear poCrear){
        this(poCrear, null);
    }
    public JMostrarPantallaParam(IMostrarPantallaCrear poCrear, String psNombreUnico){
        moCrear=poCrear;
        msNombreUnico=psNombreUnico;
    }
    public JMostrarPantallaParam(IPanelControlador poControlador){
        moControlador = poControlador;
    }
    public JMostrarPantallaParam(final IFormEdicion poPanel, final IFormEdicionNavegador poPanelNave, final Object poPanelMismo, final int plTipoMostrar){
        moPanel = poPanel;
        moPanelNave=poPanelNave;
        mlTipoMostrar=plTipoMostrar;
    }
    public JMostrarPantallaParam(final IFormEdicion poPanel, final int plTipoMostrar){
        this(poPanel, null, poPanel, plTipoMostrar);
    }
    public JMostrarPantallaParam(final IFormEdicionNavegador poPanel, final int plTipoMostrar){
        this(null, poPanel, poPanel, plTipoMostrar);
    }
    public JMostrarPantallaParam(IPanelControlador poControlador, int plAncho, int plAlto, int plTipoForm, int plTipoMostrar) {
        moControlador=poControlador;
        mlAncho=plAncho;
        mlAlto=plAlto;
        mlTipoForm=plTipoForm;
        mlTipoMostrar=plTipoMostrar;
        mbMaximizado = (mlTipoMostrar==mclEdicionInternal)||(mlTipoMostrar==mclEdicionInternalBlanco);
    }
    public JMostrarPantallaParam(Object poPanel, int plAncho, int plAlto, int plTipoMostrar, String psTitulo) {
        moComponente=poPanel;
        mlAncho=plAncho;
        mlAlto=plAlto;
        mlTipoMostrar=plTipoMostrar;
        msTitulo=psTitulo; 
        mbMaximizado = (mlTipoMostrar==mclEdicionInternal)||(mlTipoMostrar==mclEdicionInternalBlanco);
    }
    public boolean isMismoIdentificador(JMostrarPantallaParam poParam) {
        boolean lbResult = false;
        if (moControlador != null && poParam.moControlador != null
                && moControlador.getParametros() != null && poParam.moControlador.getParametros() != null) {
            if (moControlador.getParametros().getNombre().equals(
                    poParam.moControlador.getParametros().getNombre())) {
                lbResult = true;
            }
        }
        IPlugInFrame loFormD = null;
        if (moPanel != null) {
            if (IPlugInFrame.class.isAssignableFrom(moPanel.getClass())) {
                loFormD = (IPlugInFrame) moPanel;
            }
        }
        if (moPanelNave != null) {
            if (IPlugInFrame.class.isAssignableFrom(moPanelNave.getClass())) {
                loFormD = (IPlugInFrame) moPanelNave;
            }
        }
        if (moComponente != null) {
            if (IPlugInFrame.class.isAssignableFrom(moComponente.getClass())) {
                loFormD = (IPlugInFrame) moComponente;
            }
        }
        IPlugInFrame loFormP = null;
        if (poParam.moPanel != null) {
            if (IPlugInFrame.class.isAssignableFrom(poParam.moPanel.getClass())) {
                loFormP = (IPlugInFrame) poParam.moPanel;
            }
        }
        if (poParam.moPanelNave != null) {
            if (IPlugInFrame.class.isAssignableFrom(poParam.moPanelNave.getClass())) {
                loFormP = (IPlugInFrame) poParam.moPanelNave;
            }
        }
        if (poParam.moComponente != null) {
            if (IPlugInFrame.class.isAssignableFrom(poParam.moComponente.getClass())) {
                loFormP = (IPlugInFrame) poParam.moComponente;
            }
        }
        if (loFormD != null && loFormP != null) {
            lbResult = loFormD.getIdentificador().equals(loFormP.getIdentificador());
        }
//        if(lbResult){
//            mostrarFrente();
//        }
        return lbResult;
    }

    public void setY(int plY) {
        mlY = plY;
    }

    public void setX(int plX) {
        mlX = plX;
    }

    /**
     * @return the mlX
     */
    public int getX() {
        return mlX;
    }

    /**
     * @return the mlY
     */
    public int getY() {
        return mlY;
    }

    /**
     * @return the moPanel
     */
    public IFormEdicion getPanel() {
        return moPanel;
    }

    /**
     * @param moPanel the moPanel to set
     */
    public void setPanel(IFormEdicion moPanel) {
        this.moPanel = moPanel;
    }

    /**
     * @return the moPanelNave
     */
    public IFormEdicionNavegador getPanelNave() {
        return moPanelNave;
    }

    /**
     * @param moPanelNave the moPanelNave to set
     */
    public void setPanelNave(IFormEdicionNavegador moPanelNave) {
        this.moPanelNave = moPanelNave;
    }

    /**
     * @return the moControlador
     */
    public IPanelControlador getControlador() {
        return moControlador;
    }

    /**
     * @param moControlador the moControlador to set
     */
    public void setControlador(IPanelControlador moControlador) {
        this.moControlador = moControlador;
    }

    /**
     * @return the mlAncho
     */
    public int getAncho() {
        return mlAncho;
    }

    /**
     * @param mlAncho the mlAncho to set
     */
    public void setAncho(int mlAncho) {
        this.mlAncho = mlAncho;
    }

    /**
     * @return the mlAlto
     */
    public int getAlto() {
        return mlAlto;
    }

    /**
     * @param mlAlto the mlAlto to set
     */
    public void setAlto(int mlAlto) {
        this.mlAlto = mlAlto;
    }

    /**
     * @return the mlTipoForm
     */
    public int getTipoForm() {
        return mlTipoForm;
    }

    /**
     * @param mlTipoForm the mlTipoForm to set
     */
    public void setTipoForm(int mlTipoForm) {
        this.mlTipoForm = mlTipoForm;
    }

    /**
     * @return the mlTipoMostrar
     */
    public int getTipoMostrar() {
        return mlTipoMostrar;
    }

    /**
     * @param mlTipoMostrar the mlTipoMostrar to set
     */
    public void setTipoMostrar(int mlTipoMostrar) {
        this.mlTipoMostrar = mlTipoMostrar;
    }

    /**
     * @return the mbMaximizado
     */
    public boolean isMaximizado() {
        return mbMaximizado;
    }

    /**
     * @param mbMaximizado the mbMaximizado to set
     */
    public void setMaximizado(boolean mbMaximizado) {
        this.mbMaximizado = mbMaximizado;
    }

    /**
     * @return the mbUnaSolaInstancia
     */
    public boolean isUnaSolaInstancia() {
        return mbUnaSolaInstancia;
    }

    /**
     * @param mbUnaSolaInstancia the mbUnaSolaInstancia to set
     */
    public void setUnaSolaInstancia(boolean mbUnaSolaInstancia) {
        this.mbUnaSolaInstancia = mbUnaSolaInstancia;
    }

    /**
     * @return the mbSiempreDelante
     */
    public boolean isSiempreDelante() {
        return mbSiempreDelante;
    }

    /**
     * @param mbSiempreDelante the mbSiempreDelante to set
     */
    public void setSiempreDelante(boolean mbSiempreDelante) {
        this.mbSiempreDelante = mbSiempreDelante;
    }

    /**
     * @return the mbXCierra
     */
    public boolean isXCierra() {
        return mbXCierra;
    }

    /**
     * @param mbXCierra the mbXCierra to set
     */
    public void setXCierra(boolean mbXCierra) {
        this.mbXCierra = mbXCierra;
    }

    /**
     * @return the msTitulo
     */
    public String getTitulo() {
        return msTitulo;
    }

    /**
     * @param msTitulo the msTitulo to set
     */
    public void setTitulo(String msTitulo) {
        this.msTitulo = msTitulo;
    }

    /**
     * @return the moComponente
     */
    public Object getComponente() {
        return moComponente;
    }

    /**
     * @param moComponente the moComponente to set
     */
    public void setComponente(Object moComponente) {
        this.moComponente = moComponente;
    }

    /**
     * @return the moCrear
     */
    public IMostrarPantallaCrear getCrear() {
        return moCrear;
    }

    /**
     * @param moCrear the moCrear to set
     */
    public void setCrear(IMostrarPantallaCrear moCrear) {
        this.moCrear = moCrear;
    }

    /**
     * @return the msNombreUnico
     */
    public String getNombreUnico() {
        return msNombreUnico;
    }

    /**
     * @param msNombreUnico the msNombreUnico to set
     */
    public void setNombreUnico(String msNombreUnico) {
        this.msNombreUnico = msNombreUnico;
    }

    /**
     * @return the moImagenIcono
     */
    public Object getImagenIcono() {
        return moImagenIcono;
    }

    /**
     * @param moImagenIcono the moImagenIcono to set
     */
    public void setImagenIcono(Object moImagenIcono) {
        this.moImagenIcono = moImagenIcono;
    }

    /**
     * @return the moCallBack
     */
    public CallBack<JMostrarPantallaParam> getCallBack() {
        return moCallBack;
    }

    /**
     * @param moCallBack the moCallBack to set
     */
    public void setCallBack(CallBack<JMostrarPantallaParam> moCallBack) {
        this.moCallBack = moCallBack;
    }

}
