/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Base64;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.codec.CodecUtil;
import org.apache.james.mime4j.dom.BinaryBody;
import org.apache.james.mime4j.dom.Body;
import org.apache.james.mime4j.dom.Entity;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.dom.MessageBuilder;
import org.apache.james.mime4j.dom.Multipart;
import org.apache.james.mime4j.dom.TextBody;
import org.apache.james.mime4j.dom.address.Mailbox;
import org.apache.james.mime4j.field.DefaultFieldParser;
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.DefaultMessageBuilder;
import org.apache.james.mime4j.message.DefaultMessageWriter;
import org.apache.james.mime4j.message.MessageImpl;
import org.apache.james.mime4j.message.MultipartImpl;
import org.apache.james.mime4j.storage.StorageBodyFactory;
import utiles.CadenaLargaOut;
import utiles.IListaElementos;
import utiles.JArchivo;
import utiles.JDateEdu;
import utiles.JDepuracion;


public class JGUIxAvisosBuildMensaje {

    private Message moMensaje;
    private JMensaje moMensajePropio;
    
    public void convertir(javax.mail.Message poMensaje) throws Exception{
        moMensajePropio=null;
        MessageBuilder loBuilder = new DefaultMessageBuilder();

        ByteArrayOutputStream loDestino = new ByteArrayOutputStream();
        poMensaje.writeTo(loDestino);
        moMensaje = loBuilder.parseMessage(new ByteArrayInputStream(loDestino.toByteArray()));
        parsear(moMensaje);        
    }
    public void convertir(String psRuta) throws Exception{
        if (new File(psRuta).exists()){
            MessageBuilder loBuilder = new DefaultMessageBuilder();
            FileInputStream loMensaje = new FileInputStream(psRuta);
            moMensaje = loBuilder.parseMessage(loMensaje);               
            loMensaje.close();
            parsear (moMensaje);
        } else {
            throw new FileNotFoundException("Ruta no existe");
        }
    }
    
    public JMensaje getMensaje(javax.mail.Message poMensaje) throws IOException, MimeException, MessagingException, Exception{
        convertir(poMensaje);
        return moMensajePropio;
    }

    public JMensaje getMensaje(String psRuta) throws FileNotFoundException, MimeException, IOException, Exception{
        convertir(psRuta);
        return moMensajePropio;
    }
        
    
    public void guardar(String psRuta) throws Exception{
        DefaultMessageWriter loWriter = new DefaultMessageWriter();
        FileOutputStream loFichero = new FileOutputStream(psRuta);
        loWriter.writeMessage(moMensaje, loFichero);
        loFichero.close();
       
    }
    
    private void parsear(Message poMensaje) throws Exception{
        moMensajePropio=new JMensaje();
        
        moMensajePropio.setAsunto(poMensaje.getSubject());
        moMensajePropio.setFecha(new JDateEdu(poMensaje.getDate()));
        moMensajePropio.setEmailFrom(poMensaje.getFrom().size()>0 ? poMensaje.getFrom().get(0).getAddress():"");
        
        if(moMensaje.getMessageId()==null){
            moMensaje.createMessageId(null);
            moMensajePropio.setIdMensaje(moMensaje.getMessageId());
        }
        else{
            moMensajePropio.setIdMensaje(moMensaje.getMessageId());
        }
        
        JDepuracion.anadirTexto(getClass().getName(),moMensajePropio.getAsunto() + " Fecha " + moMensajePropio.getFecha().msFormatear("dd/MM/yyyy"));
        for (int i = 0; poMensaje.getTo()!= null && i < poMensaje.getTo().size(); i++){
            moMensajePropio.getEmailTO().add(((Mailbox)poMensaje.getTo().get(i)).getAddress());
        }
        for (int i = 0; poMensaje.getCc() != null && i < poMensaje.getCc().size(); i++) {
            moMensajePropio.getEmailCC().add(((Mailbox) poMensaje.getCc().get(i)).getAddress());
        }
        for (int i = 0; poMensaje.getBcc() != null && i < poMensaje.getBcc().size(); i++) {
            moMensajePropio.getEmailBCC().add(((Mailbox) poMensaje.getBcc().get(i)).getAddress());
        }

        StringBuilder lsContenidoPlano= new StringBuilder();
        StringBuilder lsContenidoHTML= new StringBuilder();

        obtenerContenido(poMensaje, lsContenidoPlano, lsContenidoHTML);

        if(lsContenidoHTML.length()>0){
            moMensajePropio.setTexto(lsContenidoHTML.toString());
            moMensajePropio.setTextoTipoContenido(JMensaje.mcsTextoHTML);
        } else {
            moMensajePropio.setTexto(lsContenidoPlano.toString());
            moMensajePropio.setTextoTipoContenido(JMensaje.mcsTextoPlano);
        }
        
        obtenerAdjuntos(poMensaje, moMensajePropio.getFicheroAdjunto());

        try{
            moMensajePropio.procesarAtributosEnvio();
        } catch (Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }

    private void obtenerAdjuntos(Entity poEntidad, IListaElementos poAdjuntos) throws Exception{
        
        
        Body loCuerpo = poEntidad.getBody();
        
        
        if (loCuerpo instanceof Multipart){
            
            for (Entity loPart :((Multipart)loCuerpo).getBodyParts()){
                obtenerAdjuntos(loPart, poAdjuntos);
            }

        } else {
            if ( loCuerpo instanceof MessageImpl){
                obtenerAdjuntos((MessageImpl)loCuerpo, poAdjuntos);
            }
            else {
                if (loCuerpo instanceof BinaryBody){
                    if (loCuerpo.getParent().getFilename()!=null){
                        String lsNombreCompleto = loCuerpo.getParent().getFilename().replace(' ','_');
                        
                        File loFile;
                        int lPunto = lsNombreCompleto.lastIndexOf('.');
                        if(lPunto >0){
                            loFile = File.createTempFile(lsNombreCompleto.substring(0, lPunto)+ "zzzzzzzz", lsNombreCompleto.substring(lPunto));
                        }else{
                            loFile = File.createTempFile(lsNombreCompleto+ "zzzzzzzz", "otro");
                        }
                        poAdjuntos.add(loFile.getAbsolutePath());

                        FileOutputStream loDestino = new FileOutputStream(loFile);
                        CodecUtil.copy(((BinaryBody)loCuerpo).getInputStream(), loDestino);
                        loDestino.close();
                    }
                }
            }
        
        }
        
    }
    
    private void obtenerContenido(Entity poEntidad, StringBuilder psContenidoPlano, StringBuilder psContenidoHTML) throws Exception{
        
        
        Body loCuerpo = poEntidad.getBody();
        
        if (loCuerpo instanceof Multipart){
            
            for (Entity loPart : ((Multipart)loCuerpo).getBodyParts()){
                obtenerContenido(loPart, psContenidoPlano, psContenidoHTML);
            }

        } else {
            if ( loCuerpo instanceof MessageImpl){
                obtenerContenido((MessageImpl)loCuerpo, psContenidoPlano, psContenidoHTML);
            } else  if (loCuerpo instanceof TextBody){

                StringBuilder lsTexto = new StringBuilder();

                Reader r = ((TextBody)loCuerpo).getReader();
                int c;
                while ((c = r.read()) != -1) {
                    lsTexto.append((char) c);
                }

                if (poEntidad.getMimeType().equalsIgnoreCase(JMensaje.mcsTextoPlano)){
                    psContenidoPlano.append("\n"); 
                    psContenidoPlano.append(lsTexto.toString());
                }

                if (poEntidad.getMimeType().equalsIgnoreCase(JMensaje.mcsTextoHTML)){
                    psContenidoHTML.append("<BR> </BR>");
                    psContenidoHTML.append( lsTexto.toString());
                }

            } else if(loCuerpo instanceof BinaryBody){
                if(poEntidad.getHeader().getField("Content-ID")!=null){
                    ByteArrayOutputStream loBinary = new ByteArrayOutputStream();
                    ((BinaryBody)loCuerpo).writeTo(loBinary);



                    String ls = "data:"
                            +poEntidad.getHeader().getField("Content-Type").getBody()
                            +";"+poEntidad.getHeader().getField("Content-Transfer-Encoding").getBody()
                            +","+java.util.Base64.getEncoder().encodeToString(loBinary.toByteArray());

                    String lsID = "cid:"+poEntidad.getHeader().getField("Content-ID").getBody().replace("<", "").replace(">", "");

                    int lPosi = psContenidoHTML.indexOf(lsID);
                    if(lPosi>=0){
                        psContenidoHTML.replace(lPosi, lPosi+lsID.length(), ls);
                    }
                }                
            } 

        
        }
        
    }     

    public void anadirImagen (String psRutaSello) throws Exception{
        if (moMensaje.isMultipart()){
        
            Multipart loCuerpo = (Multipart) moMensaje.getBody();
            
            StorageBodyFactory bodyFactory = new StorageBodyFactory();            
            FileDataSource fds = new FileDataSource(psRutaSello);
            BinaryBody body = bodyFactory.binaryBody(fds.getInputStream());
            BodyPart bodyPart = new BodyPart();
            bodyPart.setBody(body, "image/"+JArchivo.getExtension(new File(psRutaSello)));
            bodyPart.getHeader().addField(DefaultFieldParser.parse("Content-ID: <logo>"));
            bodyPart.setContentTransferEncoding("base64");
            bodyPart.setFilename(fds.getName());
            
            loCuerpo.addBodyPart(bodyPart, loCuerpo.getCount());
        }
        else {
        
        } 
      
    }
    
        
    public void anadirContenido (String psNuevoContenido, String psCharset) throws Exception{
        
                       
            MessageBuilder builder = new DefaultMessageBuilder();
            Message message = builder.newMessage(moMensaje);
            
            Multipart newMultipartBody = new MultipartImpl("mixed");
                      
            StorageBodyFactory bodyFactory = new StorageBodyFactory();            
            TextBody loNuevoTexto = bodyFactory.textBody(psNuevoContenido, psCharset);
            BodyPart bodyPart = new BodyPart();
            bodyPart.setBody(loNuevoTexto, "text/html");
            bodyPart.setContentTransferEncoding("quoted-printable");
           
            newMultipartBody.addBodyPart(message);
            
            newMultipartBody.addBodyPart(bodyPart);
            
            
            moMensaje.removeBody();
            ((MessageImpl)moMensaje).setMultipart(newMultipartBody);
            
    }
    
}
