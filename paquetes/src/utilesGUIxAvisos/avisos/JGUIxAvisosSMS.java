/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;


/**
 *
 * @author cristian
 */
public abstract class JGUIxAvisosSMS {
    
    
    protected String msSMSClave;
    protected String msSMSLicencia;
    protected String msSMSUsuario;
    protected int mlSMSPuerto;
 
    public void inicializar() {
    }
    
    public String getSMSLicencia() {
        return msSMSLicencia;
    }

    public String getSMSUsuario() {
        return msSMSUsuario;
    }

    public String getSMSClave() {
        return msSMSClave;
    }

    public void setSMSClave(String text) {
        msSMSClave=text;
    }

    public void setSMSLicencia(String text) {
        msSMSLicencia=text;
    }

    public void setSMSUsuario(String text) {
        msSMSUsuario=text;
    }

    public int getSMSPuerto() {
        return mlSMSPuerto;
    }

    public void setSMSPuerto(int plSMSPuerto) {
        this.mlSMSPuerto = plSMSPuerto;
    }
    
    public abstract void inicializarSMS();
    
    public abstract void enviarSMS(String psTelef, String psSender, 
                                   String psTexto, String pdDate) throws Exception;
    
}
