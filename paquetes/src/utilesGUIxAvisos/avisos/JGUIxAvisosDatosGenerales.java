/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import ListDatos.IServerServidorDatos;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import utiles.JCadenas;

public class JGUIxAvisosDatosGenerales {

    public static final int mclSeguridadNada = JGUIxAvisosCorreoEnviar.mclSeguridadNada;
    public static final int mclSeguridadSSL = JGUIxAvisosCorreoEnviar.mclSeguridadSSL;
    public static final int mclSeguridadstarttls = JGUIxAvisosCorreoEnviar.mclSeguridadstarttls;
//    
//    private static JGUIxAvisosDatosGenerales moInstancia;
//
//    public static JGUIxAvisosDatosGenerales getInstancia() {
//        return moInstancia;
//    }
//    
//    public static void setInstancia(JGUIxAvisosDatosGenerales poDatosG) {
//        moInstancia = poDatosG;
//    }
    
    public static JGUIxAvisosCorreo getAvisosCorreo(List<JGUIxAvisosCorreo> poListaCorreos,String psIdent){
        JGUIxAvisosCorreo lResult = null;
        for(JGUIxAvisosCorreo loCorreo: poListaCorreos){
            if(loCorreo.getIdentificador().equalsIgnoreCase(psIdent)){
                lResult = loCorreo;
            }
        }
        return lResult;
    }
    
    
    private IGUIxAvisosDatos moDatos;
    private JGUIxAvisosSMS moEnviarSMS = new JGUIxAvisosSMSMensario();
    private final ArrayList<JGUIxAvisosCorreo> moListaCorreos = new ArrayList<JGUIxAvisosCorreo>();
    private JGUIxAvisosSMSFactory factoriaSMS = null;

    
    public void inicializar(IGUIxAvisosDatos poDatos) {
        moDatos=poDatos;
    }
    
    public void aceptar() throws Exception{
        moDatos.aceptar(this);
    }
    public void cancelar() throws Exception{
        moDatos.cancelar(this);
    }
    
    public synchronized JGUIxAvisosSMSFactory getSMSFactory(){
        if(factoriaSMS==null){
            factoriaSMS = new JGUIxAvisosSMSFactory();
            factoriaSMS.addClient(JGUIxAvisosSMSMensamundi.class.getName(), "Mensamundi");
            factoriaSMS.addClient(JGUIxAvisosSMSMensario.class.getName(), "Mensario");
        }
        return factoriaSMS;
    }
    
    public Map<String, String> getClientesDisponibles() {
        return getSMSFactory().getAvailableClients();
    }
    
    public void setSMSTipo(String text) {
        if(JCadenas.isVacio(text)){
            text=JGUIxAvisosSMSMensario.class.getName();
        }
        if(moEnviarSMS==null || !text.equalsIgnoreCase(getSMSTipo())){
            moEnviarSMS = getSMSFactory().createSender(text);
        }
    }
    public String getSMSTipo(){
        return moEnviarSMS.getClass().getName();
    }
    public synchronized JGUIxAvisosSMS getSMS(){
        if(moEnviarSMS==null){
            setSMSTipo("");
        }
        return moEnviarSMS;
    }
    public List<JGUIxAvisosCorreo> getListaCorreos() {
        return moListaCorreos;
    }
    public JGUIxAvisosCorreo getCorreo(String psIdent){
        JGUIxAvisosCorreo loResult=null;
        if(!JCadenas.isVacio(psIdent)){
            for(JGUIxAvisosCorreo loCorreo: getListaCorreos()){
                if(loCorreo.getIdentificador().equals(psIdent)){
                    loResult = loCorreo;
                }
            }
        }
        if(loResult==null){
            loResult=getCorreoDefecto();
        }
        return loResult;
    }
    
    public JGUIxAvisosCorreo getCorreoDefecto(){
        JGUIxAvisosCorreo loResult = null;
        if(getListaCorreos().isEmpty()){
            loResult = new JGUIxAvisosCorreo();
            getListaCorreos().add(loResult);
        }
        loResult=getListaCorreos().get(0);
        for(JGUIxAvisosCorreo loCorreo: getListaCorreos()){
            if(loCorreo.isDefecto()){
                loResult = loCorreo;
            }
        }
        
        return loResult;
    }
    
    public String getCorreoSMTP() {
        return getCorreoDefecto().getEnviar().getServidor();
    }

    public String getCorreoSMTPPassword() {
        return getCorreoDefecto().getEnviar().getPassword();
    }

    public String getCorreoSMTPUsuario() {
        return getCorreoDefecto().getEnviar().getUsuario();
    }

    public String getCorreo() {
        return getCorreoDefecto().getEnviar().getCorreo();
    }

    public String getCorreoNombre() {
        return getCorreoDefecto().getEnviar().getCorreoNombre();
    }

    public int getCorreoSMTPPuerto() {
        return getCorreoDefecto().getEnviar().getPuerto();
    }

    public int getCorreoSMTPSeguridad() {
        return getCorreoDefecto().getEnviar().getSeguridad();
    }
    
    public String getPathPlantilla() {
        return getCorreoDefecto().getEnviar().getPathPlantilla();
    }

    public void setCorreoSMTP(String text) {
        getCorreoDefecto().getEnviar().setServidor(text);
    }

    public void setCorreoSMTPPassword(String text) {
        getCorreoDefecto().getEnviar().setPassword(text);
    }

    public void setCorreoSMTPUsuario(String text) {
        getCorreoDefecto().getEnviar().setUsuario(text);
    }

    public void setCorreo(String text) {
        getCorreoDefecto().getEnviar().setCorreo(text);
    }

    public void setCorreoNombre(String text) {
        getCorreoDefecto().getEnviar().setCorreoNombre(text);
    }

    public void setCorreoSMTPSeguridad(int text) {
        getCorreoDefecto().getEnviar().setSeguridad(text);
    }

    public void setCorreoSMTPPuerto(int text) {
        getCorreoDefecto().getEnviar().setPuerto(text);
    }
    
    public void setPathPlantilla(String text) {
        getCorreoDefecto().getEnviar().setPathPlantilla(text);
    }

    public void inicializarSMS() {
        moEnviarSMS.inicializarSMS();
    }

    public void enviarSMS(String psTelef, String psSender, String psTexto) throws Exception {
        moEnviarSMS.enviarSMS(psTelef, psSender, psTexto, "");
    }
    
    public void enviarSMS(String psTelef, String psSender, String psTexto, String psDate) throws Exception {
        moEnviarSMS.enviarSMS(psTelef, psSender, psTexto, psDate);
    }

    public void inicializarEmail() throws NoSuchProviderException, MessagingException {
        getCorreoDefecto().getEnviar().inicializarEmail();
    }

    public void enviarEmail(String psEmailTO, String psAsunto, String psTexto) throws UnsupportedEncodingException, MessagingException, Exception {
        enviarEmail(psEmailTO, psAsunto, psTexto, (String[])null);
    }
    public void enviarEmail(String psEmailTO, String psAsunto, String psTexto, String psFicheroAdjunto) throws UnsupportedEncodingException, MessagingException, Exception {
        enviarEmail(psEmailTO, psAsunto, psTexto, new String []{psFicheroAdjunto});
    }
    public void enviarEmail(String psEmailTO, String psAsunto, String psTexto, String[] psFicheroAdjunto) throws UnsupportedEncodingException, MessagingException, Exception {
        enviarEmail(new String[]{psEmailTO}, psAsunto, psTexto, psFicheroAdjunto);
    }
    public void enviarEmail(String[] psEmailTO, String psAsunto, String psTexto, String[] psFicheroAdjunto) throws UnsupportedEncodingException, MessagingException, Exception {
        enviarEmail(new JMensaje(psEmailTO, psAsunto, psTexto, psFicheroAdjunto));
    }
    
    public void enviarEmail(JMensaje poMensaje) throws UnsupportedEncodingException, MessagingException, Exception {
        getCorreo(poMensaje.getIdentificadorEnvio()).getEnviar().enviarEmail(poMensaje);
    }

    /**
     * @return the mbGuardarHistorico
     */
    public boolean isGuardarHistorico() {
        return getCorreoDefecto().getEnviar().isGuardarHistorico();
    }

    /**
     * @param mbGuardarHistorico the mbGuardarHistorico to set
     */
    public void setGuardarHistorico(boolean mbGuardarHistorico) {
        for(JGUIxAvisosCorreo loCorreo : getListaCorreos()){
            loCorreo.getEnviar().setGuardarHistorico( mbGuardarHistorico);
        }
    }

    /**
     * @return the moServer
     */
    public IServerServidorDatos getServer() {
        return getCorreoDefecto().getServer();
    }

    /**
     * @param poServer the moServer to set
     */
    public void setServer(IServerServidorDatos poServer) {
        for(JGUIxAvisosCorreo loCorreo : getListaCorreos()){
            loCorreo.setServer(poServer);
        }
    }
    
    public synchronized void recibirYGuardar() throws Exception{
        for(JGUIxAvisosCorreo loCorreo : getListaCorreos()){
            loCorreo.recibirYGuardar();
        }
    }

    
}
