/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.avisos;

import utilesGUIx.ActionListenerCZ;
import utilesGUIx.formsGenericos.IMostrarPantalla;

/**
 *
 * @author eduardo
 */
public class JAviso {
    private String msMensaje;
    private ActionListenerCZ moAccion;
    private String msAccionCaption="Acción";
    private int mlTipoMensaje=IMostrarPantalla.mclMensajeInformacion;
    public JAviso(String psMensaje, ActionListenerCZ poAccion){
        this(IMostrarPantalla.mclMensajeInformacion, psMensaje, poAccion);
    }
    public JAviso(int plTipoMensaje, String psMensaje, ActionListenerCZ poAccion){
        mlTipoMensaje=plTipoMensaje;
        msMensaje=psMensaje;
        moAccion=poAccion;
    }

    /**
     * @return the msMensaje
     */
    public String getMensaje() {
        return msMensaje;
    }

    /**
     * @param msMensaje the msMensaje to set
     */
    public void setMensaje(String msMensaje) {
        this.msMensaje = msMensaje;
    }

    /**
     * @return the moAccion
     */
    public ActionListenerCZ getAccion() {
        return moAccion;
    }

    /**
     * @param moAccion the moAccion to set
     */
    public void setAccion(ActionListenerCZ moAccion) {
        this.moAccion = moAccion;
    }

    /**
     * @return the msAccionCaption
     */
    public String getAccionCaption() {
        return msAccionCaption;
    }

    /**
     * @param msAccionCaption the msAccionCaption to set
     */
    public void setAccionCaption(String msAccionCaption) {
        this.msAccionCaption = msAccionCaption;
    }

    /**
     * @return the mlTipoMensaje
     */
    public int getTipoMensaje() {
        return mlTipoMensaje;
    }

    /**
     * @param mlTipoMensaje : IMostrarPantalla.mclMensajeInformacion, IMostrarPantalla.mclMensajeError, IMostrarPantalla.mclMensajeAdvertencia
     */
    public void setTipoMensaje(int mlTipoMensaje) {
        this.mlTipoMensaje = mlTipoMensaje;
    }

}
