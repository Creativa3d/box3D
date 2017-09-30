/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import ListDatos.IResultado;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.PreencodedMimeBodyPart;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

public class JGUIxAvisosCorreoEnviar implements Cloneable {

    public static final int mclSeguridadNada = 0;
    public static final int mclSeguridadSSL = 1;
    public static final int mclSeguridadstarttls = 2;
    
    private transient Session moSesion;
    private transient Transport transport;
    private String msServidor;
    private String msPassword;
    private int mlPuerto=0;
    private String msCorreoNombre;
    private String msUsuario;
    private String msCorreo;
    private String msPathPlantilla;
    private int mlSeguridad=mclSeguridadNada;
    private String msCarpetaCorreo;
    private boolean mbGuardarHistorico=false;
    private JGUIxAvisosCorreo moCorreoGeneral;
    
    public JGUIxAvisosCorreoEnviar(JGUIxAvisosCorreo poCorreoGeneral){
        moCorreoGeneral=poCorreoGeneral;
    }
    
    public void setCorreoGeneral(JGUIxAvisosCorreo poCorreoGeneral){
        moCorreoGeneral=poCorreoGeneral;
    }
    
    public synchronized void inicializar(){
        moSesion=null;
        transport=null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        JGUIxAvisosCorreoEnviar loEn = (JGUIxAvisosCorreoEnviar) super.clone();
        loEn.inicializar();
        return loEn;
    }
    
    public String getServidor() {
        return msServidor;
    }

    public String getPassword() {
        return msPassword;
    }

    public String getUsuario() {
        return msUsuario;
    }

    public String getCorreo() {
        return msCorreo;
    }

    public String getCorreoNombre() {
        return msCorreoNombre;
    }

    public int getPuerto() {
        return mlPuerto;
    }

    public int getSeguridad() {
        return mlSeguridad;
    }
    
    public String getPathPlantilla() {
        return msPathPlantilla;
    }

    public void setServidor(String text) {
        msServidor=text;
    }

    public void setPassword(String text) {
        msPassword=text;
    }

    public void setUsuario(String text) {
        msUsuario=text;
    }

    public void setCorreo(String text) {
        msCorreo=text;
    }

    public void setCorreoNombre(String text) {
        msCorreoNombre=text;
    }

    public void setSeguridad(int text) {
        mlSeguridad=text;
    }

    public void setPuerto(int text) {
        mlPuerto=text;
    }
    
    public void setPathPlantilla(String text) {
        msPathPlantilla=text;
    }

    public synchronized void inicializarEmail() throws NoSuchProviderException, MessagingException {
        //
        //Sesion
        //
        transport = null;
        Properties loProp = new Properties();
        int lPuerto = getPuerto();
        if (getSeguridad() == mclSeguridadstarttls) {

            if(lPuerto == 0){
                lPuerto=587;
            }
            loProp.put("mail.smtp.host", getServidor());
            loProp.put("mail.smtp.auth", "true");
            loProp.put("mail.smtp.ssl.trust", "*");
            loProp.put("mail.smtp.starttls.enable", "true");
            loProp.put("mail.smtp.port", String.valueOf(lPuerto));
            loProp.put("mail.smtp.localhost", getServidor());
            
//                        loProp.put("mail.smtp.socketFactory.port", String.valueOf(JDatosGeneralesAvisos.getCorreoSMTPPuerto()));
            moSesion = Session.getInstance(loProp,
                    new javax.mail.Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(getUsuario(), getPassword());
                        }
                    });
            //creamos el transporte
            transport = moSesion.getTransport("smtp");
            transport.connect(getServidor(),
                    getUsuario(),
                    getPassword());
        } else if (getSeguridad() == mclSeguridadSSL) {
            if(lPuerto == 0){
                lPuerto=465;
            }
            loProp.put("mail.smtp.host", getServidor());
            loProp.put("mail.smtp.socketFactory.port", String.valueOf(lPuerto));
            loProp.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            loProp.put("mail.smtp.auth", "true");
            loProp.put("mail.smtp.ssl.trust", "*");
            loProp.put("mail.smtp.port", String.valueOf(lPuerto));
            moSesion = Session.getInstance(loProp,
                    new javax.mail.Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(getUsuario(), getPassword());
                        }
                    });
            transport = moSesion.getTransport("smtp");
            transport.connect();
        } else {
            if(lPuerto == 0){
                lPuerto=25;
            }
            loProp.put("mail.smtp.socketFactory.port", String.valueOf(lPuerto));
            loProp.put("mail.smtp.host", getServidor());
            loProp.put("mail.smtp.port", String.valueOf(lPuerto));   
            loProp.put("mail.smtp.localhost", getServidor());         
            moSesion = Session.getInstance(loProp, null);
            //creamos el transporte
            transport = moSesion.getTransport("smtp");
            transport.connect(getServidor(),
                    lPuerto,
                    getUsuario(),
                    getPassword());
        }

    }

    private static String getTextoHTMLConIDs(String psHTML, Map<String, String> poIDS){
        StringBuffer lsResult = new StringBuffer(psHTML);
        int lPosi = lsResult.indexOf("<img");
        int lIndex = 0;
        while(lPosi>=0){
            int lPosi2 =lsResult.indexOf("/>", lPosi);
            int lPosi2OTRO =lsResult.indexOf(">", lPosi);
            if(lPosi2<0 || lPosi2 > lPosi2OTRO){
                lPosi2 = lPosi2OTRO;
            }
            if(lPosi2>=0){
                String[] lsAux = getIMGID(lsResult.substring(lPosi, lPosi2), lIndex);
                if(lsAux!=null){
                    poIDS.put(lsAux[0], lsAux[1]);
                    lsResult.delete(lPosi, lPosi2);
                    lsResult.insert(lPosi, lsAux[2]);
                    lIndex++;
                }
            }
            lPosi = lsResult.indexOf("<img", lPosi+4);
        }
        return lsResult.toString();
    }
    private static String[] getIMGID(String psIMG, int plIndex){
        int lPosi = psIMG.indexOf("src");
        String lsPArte1;
        String lsPArte2;
        if(lPosi>=0){
            lsPArte1=psIMG.substring(0,lPosi+10);
            lsPArte2=psIMG.substring(lPosi+10);
        } else {
            lsPArte1="";
            lsPArte2=psIMG;
        }
        lsPArte1=lsPArte1
                .replace("\n", "").replace("\r", "")
                .replace("src =", "src=").replace("src= ", "src=").replace("src==", "src=")
                .replace("src=3D\"", "src=\"");
        
        StringBuffer lsResult = new StringBuffer(lsPArte1+lsPArte2);
        lPosi = lsResult.indexOf("src=\"");
        int lPosi2 = lsResult.indexOf("\"", lPosi+5);
        if(lPosi>=0 && lPosi2>lPosi){
            String lsIMG = lsResult.substring(lPosi+5, lPosi2);
            boolean lbContinuar = true;
            if(lsIMG.startsWith("file:///")){
                if(lsIMG.contains("\\")){//windows
                    lsIMG = lsIMG.substring(8);
                }else{
                    lsIMG = lsIMG.substring(7);
                }
//                new File(lsIMG).exists();
            }
            //ignoramos las URL
            try{
                new URL(lsIMG);
                lbContinuar=false;
            }catch(Exception e){
            }
            if(lbContinuar){
                String lsAux = "cid:la-img-"+String.valueOf(plIndex);
                lsResult.delete(lPosi+5, lPosi2);
                lsResult.insert(lPosi+5, lsAux);
                return new String[]{lsAux.substring(4), lsIMG, lsResult.toString()};
            }
        }
        return null;
    }
    public synchronized Session getSesion() throws MessagingException {
        if (moSesion == null) {
            inicializarEmail();
        }
        return moSesion;
    }
    
    public static MimeMessage getMessage(JMensaje poMensaje, Session poSession, String psCorreo, String psCorreoNombre) throws MessagingException, Exception{
        //atributos mensaje
        StringBuilder loAtrib = new StringBuilder();
        loAtrib.append("<Grupo>"+poMensaje.getGrupo()+"</Grupo>");
        for(Map.Entry<String, Object> lo : poMensaje.getAtributos().entrySet()){
            if(lo.getValue() instanceof String) {
                loAtrib.append(
                        "<"+lo.getKey()+">"+lo.getValue().toString()+"<"+lo.getKey()+">"
                        );
            }
        }
        String lsTexto = poMensaje.getTexto();
        int lPosi = lsTexto.indexOf("</html>");
        if(lPosi>=0){
            lsTexto = lsTexto.substring(0, lPosi)
                    + poMensaje.getAtributosEnvio()
                    + lsTexto.substring(lPosi);
        } else {
            lsTexto = lsTexto
                    + poMensaje.getAtributosEnvio();
        }
        
        //cuerpo mensaje
        MimeMessage loMensaje = new MimeMessage(poSession);
        //texto mensaje
        BodyPart loTexto = new MimeBodyPart();
        HashMap<String, String> loIDS = new HashMap<String, String>();
        try{
            loTexto.setContent(getTextoHTMLConIDs(lsTexto, loIDS), "text/html");
        }catch(Exception e){
            //por si las moscas
            JDepuracion.anadirTexto(JGUIxAvisosCorreoEnviar.class.getName(), e);
            loTexto.setContent(lsTexto, "text/html");
        }
        
        
        //los unimos
        Multipart loMultiParte = new MimeMultipart("related");
        loMultiParte.addBodyPart(loTexto);
        
        //add imagenes en html
        Iterator<String> loIteradorImg = loIDS.keySet().iterator();
        while(loIteradorImg.hasNext()){
            String lsKey = loIteradorImg.next();
            String lsIMG = loIDS.get(lsKey);
            try{
                //si la imagen existe, la leemos y la adjuntamos, si no existe suponemos que es base64
                if(new File(lsIMG).exists()){
                    MimeBodyPart imgPart=new MimeBodyPart();
                    // leemos img
                    DataSource ds=new FileDataSource(lsIMG);
                    imgPart.setDataHandler(new DataHandler(ds));

                    //identificador (coincide con el puesto en html)
                    imgPart.attachFile(lsIMG);
                    imgPart.setContentID("<" + lsKey + ">");
                    imgPart.setDisposition(MimeBodyPart.INLINE);
                    loMultiParte.addBodyPart(imgPart);
                } else {

                    PreencodedMimeBodyPart pmp = new PreencodedMimeBodyPart( "base64" );
                    pmp.setContentID("<" + lsKey + ">");
                    pmp.setDisposition( MimeBodyPart.INLINE );
                    int lPosiDosPuntos = lsIMG.indexOf(":");
                    int lPosiPuntoyComa = lsIMG.indexOf(";", lPosiDosPuntos);
                    int lPosiComa = lsIMG.indexOf(",", lPosiPuntoyComa);
                    if(lPosiComa>=0 && lPosiDosPuntos>=0 && lPosiComa>=0){
                        pmp.setText( lsIMG.substring(lPosiComa+1) );
                        pmp.addHeader("Content-Type", lsIMG.substring(lPosiDosPuntos+1, lPosiPuntoyComa));
                        pmp.addHeader("Content-Transfer-Encoding", "base64");
                        loMultiParte.addBodyPart( pmp );
                    }
                }
            }catch(Exception e){
                //por si las moscas
                JDepuracion.anadirTexto(JGUIxAvisosCorreoEnviar.class.getName(), e);
            }            
            
        }

        //adjunto
        for (int i = 0;poMensaje.getFicheroAdjunto()!=null && i < poMensaje.getFicheroAdjunto().size(); i++){
            BodyPart loAdjunto = new MimeBodyPart();
            String lsFichero = (String)poMensaje.getFicheroAdjunto().get(i);
            File loFile = JTEEGUIXMENSAJESBD.getFileDeAdjunto(lsFichero);
            if(!loFile.exists()){
                throw new Exception("Fichero " + poMensaje.getFicheroAdjunto() + " no existe");
            }
            loAdjunto.setDataHandler(new DataHandler(new FileDataSource(loFile.getAbsolutePath())));
            loAdjunto.setFileName(loFile.getName());
            loMultiParte.addBodyPart(loAdjunto);
        }

        //lo ponemos en el mensaje
        loMensaje.setContent(loMultiParte);

        
        poMensaje.setFecha(new JDateEdu());
        loMensaje.setSentDate(poMensaje.getFecha().getDate());
        
        loMensaje.setSubject(poMensaje.getAsunto());
        //desde
        if(JCadenas.isVacio(poMensaje.getEmailFrom())){
            loMensaje.setFrom(
                new InternetAddress(psCorreo, psCorreoNombre));
            poMensaje.setEmailFrom(psCorreo);
        } else {
            loMensaje.setFrom(
                new InternetAddress(poMensaje.getEmailFrom()));
        }
        //to, hasta
        for(int i = 0; i < poMensaje.getEmailTO().size(); i++){
            loMensaje.addRecipient(poMensaje.isBCC() ? Message.RecipientType.BCC : Message.RecipientType.TO, new InternetAddress((String)poMensaje.getEmailTO().get(i)));
        }
        for(int i = 0; i < poMensaje.getEmailBCC().size(); i++){
            loMensaje.addRecipient(Message.RecipientType.BCC , new InternetAddress((String)poMensaje.getEmailBCC().get(i)));
        }
        for(int i = 0; i < poMensaje.getEmailCC().size(); i++){
            loMensaje.addRecipient(Message.RecipientType.CC , new InternetAddress((String)poMensaje.getEmailCC().get(i)));
        }
        //mandamos el mensaje
        loMensaje.saveChanges(); // implicito con send()
        
        return loMensaje;
    }
    
    public void enviarEmail(JMensaje poMensaje) throws UnsupportedEncodingException, MessagingException, Exception {
        MimeMessage loMensaje = getMessage(poMensaje, getSesion(), getCorreo(), getCorreoNombre());
        //enviamos el mensaje
        if (transport == null) {
            Transport.send(loMensaje);
        } else {
            try{
                transport.sendMessage(loMensaje, loMensaje.getAllRecipients());
            }catch(Throwable e){
                inicializar();
                getSesion();
                loMensaje = getMessage(poMensaje, getSesion(), getCorreo(), getCorreoNombre());
                transport.sendMessage(loMensaje, loMensaje.getAllRecipients());
            }
        }
        if(mbGuardarHistorico){
            JTEEGUIXMENSAJESBD loHist = new JTEEGUIXMENSAJESBD(moCorreoGeneral.getServer());
            loHist.addNew();
            loHist.getFOLDER().setValue(loHist.getIdentificadorFOLDER(moCorreoGeneral,JTEEGUIXMENSAJESBD.mcsENVIADOS));
            loHist.setMensaje(poMensaje);
            IResultado loResult =  loHist.guardar();
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            } else {
                poMensaje.getAtributos().put(loHist.msCTabla, loHist.getCODIGO().getString());
            }
        }
        //si no es vacia la carpeta de correo y es de tipo imap, guardamos el mensaje en la carpeta correo
        if(!JCadenas.isVacio(msCarpetaCorreo) && moCorreoGeneral.getLeer().getTipoEntrante().equalsIgnoreCase(JGUIxAvisosCorreoLeer.mcsServidorIMAP)){
            moCorreoGeneral.getLeer().addMensajeACarpeta(loMensaje, msCarpetaCorreo);
        }
    }

    /**
     * @return the mbGuardarHistorico
     */
    public boolean isGuardarHistorico() {
        return mbGuardarHistorico;
    }

    /**
     * @param mbGuardarHistorico the mbGuardarHistorico to set
     */
    public void setGuardarHistorico(boolean mbGuardarHistorico) {
        this.mbGuardarHistorico = mbGuardarHistorico;
    }

    /**
     * @return the msCarpetaCorreo
     */
    public String getCarpetaCorreo() {
        return msCarpetaCorreo;
    }

    /**
     * @param msCarpetaCorreo the msCarpetaCorreo to set
     */
    public void setCarpetaCorreo(String msCarpetaCorreo) {
        this.msCarpetaCorreo = msCarpetaCorreo;
    }

    
}
