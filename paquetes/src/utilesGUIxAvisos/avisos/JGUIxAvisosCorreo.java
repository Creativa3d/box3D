/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import ListDatos.IResultado;
import ListDatos.ISelectEjecutarComprimido;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JServerEjecutarAbstract;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosInternet;
import java.net.URLConnection;
import utiles.JDateEdu;
import utilesBD.servletAcciones.ALeerCorreo;
import utilesGUIxAvisos.tablasExtend.JServerAccionLeerCorreo;

/**
 *
 * @author eduardo
 */
public class JGUIxAvisosCorreo implements Cloneable {
    private JGUIxAvisosCorreoEnviar moEnviar;
    private JGUIxAvisosCorreoLeer moLeer;
    private boolean mbDefecto = false;
    private int mlTipoModif = JListDatos.mclNada;
    private String msIdentificador;

    private transient IServerServidorDatos moServer;
    
    public JGUIxAvisosCorreo(){
        moEnviar = new JGUIxAvisosCorreoEnviar(this);
        moLeer = new JGUIxAvisosCorreoLeer(this);
        crearIdentificador();
    }
    
    public void inicializar(){
        moEnviar.inicializar();
        moLeer.inicializar();
        
    }
    /**
     * @return the moEnviar
     */
    public JGUIxAvisosCorreoEnviar getEnviar() {
        return moEnviar;
    }

    /**
     * @return the moLeer
     */
    public JGUIxAvisosCorreoLeer getLeer() {
        return moLeer;
    }

    /**
     * @return the mbDefecto
     */
    public boolean isDefecto() {
        return mbDefecto;
    }

    /**
     * @param mbDefecto the mbDefecto to set
     */
    public void setDefecto(boolean mbDefecto) {
        this.mbDefecto = mbDefecto;
    }

    /**
     * @return the mlTipoModif
     */
    public int getTipoModif() {
        return mlTipoModif;
    }

    /**
     * @param mlTipoModif the mlTipoModif to set
     */
    public void setTipoModif(int mlTipoModif) {
        this.mlTipoModif = mlTipoModif;
    }

    /**
     * @return the msIdentificador
     */
    public String getIdentificador() {
        return msIdentificador;
    }

    /**
     * @param msIdentificador the msIdentificador to set
     */
    public void setIdentificador(String msIdentificador) {
        this.msIdentificador = msIdentificador;
    }

    public void crearIdentificador() {
        msIdentificador=new JDateEdu().msFormatear("yyyyMMddHHmmss") + String.valueOf(Math.random()).substring(2,5);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        JGUIxAvisosCorreo loC = (JGUIxAvisosCorreo) super.clone(); 
        loC.moEnviar=(JGUIxAvisosCorreoEnviar) moEnviar.clone();
        loC.moLeer=(JGUIxAvisosCorreoLeer) moLeer.clone();
        
        loC.moEnviar.setCorreoGeneral(loC);
        return loC;
    }


    /**
     * @return the moServer
     */
    public IServerServidorDatos getServer() {
        return moServer;
    }

    /**
     * @param moServer the moServer to set
     */
    public void setServer(IServerServidorDatos moServer) {
        this.moServer = moServer;
    }

    public void recibirYGuardar() throws Exception {
        JServerServidorDatosInternet loServerInternet=null;
        if(moServer.getClass().isAssignableFrom(JServerServidorDatosInternet.class) ){
            loServerInternet = (JServerServidorDatosInternet) moServer;
        }
        if(moServer.getClass().isAssignableFrom(JServerServidorDatos.class) ){
            if(((JServerServidorDatos)moServer).getTipo() != JServerServidorDatos.mclTipoBD){
                loServerInternet = ((JServerServidorDatos)moServer).getServerInternet();
            }
        }
        if(loServerInternet!=null){
            URLConnection loConec = loServerInternet.enviarObjeto(ALeerCorreo.mcsAccion+".ctrl"
                    , new JServerAccionLeerCorreo(msIdentificador));
            IResultado loRespuesta = (IResultado) loServerInternet.recibirObjeto(loConec);
            if(!loRespuesta.getBien()){
                throw new Exception(loRespuesta.getMensaje());
            }
        } else {
            getLeer().recibirYGuardar();
        }
    }

}
