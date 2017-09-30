/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesDoc;

import archivosPorWeb.comun.IServidorArchivos;
import archivosPorWeb.comun.JFichero;
import archivosPorWeb.comun.JServidorArchivos;
import archivosPorWeb.comun.JServidorArchivosAbstract;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.MimeTypes;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;

public class JServidorArchivosChemistry extends JServidorArchivosAbstract {

    private String msRaiz;
    private Session moSession;
    private final String msPassWord;
    private final String msUser;
    private final String msURL;
    private Folder moRoot;
    
    public JServidorArchivosChemistry(String psURL, String psUser, String psPassword, String psRaiz) {
        msRaiz = psRaiz;
        msURL=psURL;
        msUser=psUser;
        msPassWord=psPassword;
        
    }
    
    private synchronized Session getSesion(){
        if(moSession==null){
            SessionFactory factory = SessionFactoryImpl.newInstance();
            Map parameter = new HashMap(6);

    // user credentials
            parameter.put(SessionParameter.USER, msUser);
            parameter.put(SessionParameter.PASSWORD, msPassWord);

    // connection settings
            parameter.put(SessionParameter.ATOMPUB_URL, msURL);
            parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
    //parameter.put(SessionParameter.REPOSITORY_ID, "myRepository");

    // create session
            List<Repository> repositories = factory.getRepositories(parameter);
            moSession = repositories.get(0).createSession();
            
            moRoot = (Folder) moSession.getObjectByPath(msRaiz);
        }
        return moSession;
    }
    private synchronized Folder getRaiz(){
        if(moSession==null){
            getSesion();
        }
        return moRoot;
        
    }
    
    
    public JFichero getFichero(JFichero poFichero) throws Exception {
        JFichero loFichero = (JFichero) poFichero.clone();
        loFichero.setPathRaiz(msRaiz);
        CmisObject loObject = null;
        try{
            loObject = getSesion().getObjectByPath(loFichero.getPath());
        }catch(CmisObjectNotFoundException e){
            loFichero.setExiste(false);
            return loFichero;
        }
        return getFichero(poFichero.getPathSinNombre(), loFichero.getNombre(), loObject);
    }
    private JFichero getFichero(String psPath, String psNombre, CmisObject loObject) throws Exception {

        JFichero loFichero;
        if(loObject == null){
            loFichero = new JFichero(psPath, psNombre, false
                , 0
                , null);
            loFichero.setExiste(false);
        } else if(BaseTypeId.CMIS_DOCUMENT == loObject.getBaseTypeId()){
            Document loDoc = (Document) loObject;
            loFichero = new JFichero(psPath, psNombre, false
                    , loDoc.getContentStream().getLength()
                    , new JDateEdu(loDoc.getLastModificationDate().getTime()));
            loFichero.setExiste(true);
        } else if(BaseTypeId.CMIS_FOLDER == loObject.getBaseTypeId()){
            Folder loFolder = (Folder) loObject;
            loFichero = new JFichero(psPath, psNombre, true
                    , 0
                    , new JDateEdu(loFolder.getLastModificationDate().getTime()));
            loFichero.setExiste(true);
        } else {
            throw new Exception("Tipo documento incorrecto " + loObject.getBaseTypeId());
        }

        loFichero.setServidor(this);
        return loFichero;
    }
    public IListaElementos getListaFicheros(JFichero poFichero) throws Exception {
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        CmisObject loObject = getSesion().getObjectByPath(poFichero.getPath());
        IListaElementos loLista = new JListaElementos();
        if(loObject == null){
        } else if(BaseTypeId.CMIS_FOLDER == loObject.getBaseTypeId()){
            Folder loFolder = (Folder) loObject;
            for(CmisObject loHijo:loFolder.getChildren()){
                loLista.add(getFichero(poFichero.getPath(), loHijo.getName(), loHijo));
            }
        }
        
        return loLista;
    }
    
    public void copiarFichero(IServidorArchivos poServidorOrigen , JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        poFicherodestino=(JFichero) poFicherodestino.clone();
        poFicherodestino.setPathRaiz(msRaiz);
        Folder parent = (Folder) getSesion().getObjectByPath(poFicherodestino.getPathSinNombre());
        String name = poFicherodestino.getNombre();
        // properties 
        // (minimal set: name and object type id)
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, name);
        //properties.put("prueba", "pepe");->no se puede directamente

        String lsMimeType=getMimeType(name);
        //1º lo borramos, para igualar el comportamiento a fileoutputstream
        borrar(poFicherodestino);
        // content
        InputStream stream = poServidorOrigen.getFlujoEntrada(poFicheroOrigen);
        ContentStream contentStream = new ContentStreamImpl(
                name
                , BigInteger.valueOf(poFicheroOrigen.getLenght())
                , lsMimeType
                , stream);

        // create a major version
        Document newDoc = parent.createDocument(properties, contentStream, VersioningState.MAJOR);
      
    }
    
    public void borrar(JFichero poFichero) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        try{
            CmisObject loObject = getSesion().getObjectByPath(poFichero.getPath());
            loObject.delete();
        }catch(CmisObjectNotFoundException e){
        }
    }
    public void moverFichero(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        copiarA(poServidorOrigen, poFicheroOrigen, poFicherodestino);
        poServidorOrigen.borrar(poFicheroOrigen);
    }

    public void crearCarpeta(JFichero poFichero) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        CmisObject loObject = getSesion().getObjectByPath(poFichero.getPathSinNombre());
        if(loObject==null){
            throw new Exception("Ruta " + poFichero.getPath()+ "  no encontrada");
        }else if(BaseTypeId.CMIS_FOLDER == loObject.getBaseTypeId()){
            CmisObject loObject1 = null;
            try{
                loObject1 = getSesion().getObjectByPath(poFichero.getPath());
            }catch(Throwable e){
                loObject1=null;
            }
            if(loObject1==null){
                Folder loFolder = (Folder) loObject;
                HashMap parameter = new HashMap(2);
                parameter.put("cmis:objectTypeId", "cmis:folder");
                parameter.put("cmis:name", poFichero.getNombre());

                Folder newfolder = loFolder.createFolder(parameter);            
            }
         } else {
             throw new Exception("Tipo de ruta incorrecto");
         }
    }    
    public InputStream getFlujoEntrada(JFichero poFichero) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        CmisObject loObject = getSesion().getObjectByPath(poFichero.getPath());
        if(loObject==null){
            throw new Exception("Documento " + poFichero.getPath()+ "  no encontrado");
        }else if(BaseTypeId.CMIS_DOCUMENT == loObject.getBaseTypeId()){
            Document loDoc = (Document) loObject;
            return loDoc.getContentStream().getStream();
         } else {
             throw new Exception("Tipo de documento incorrecto");
         }
    }
    private String getMimeType(String psNombre){
        String lsMimeType;
        if(psNombre.toLowerCase().endsWith(".pdf")){
            lsMimeType="application/pdf";
        } else if(psNombre.toLowerCase().endsWith(".odt")){
            lsMimeType="application/vnd.oasis.opendocument.text";
        } else if(psNombre.toLowerCase().endsWith(".doc")){
            lsMimeType="application/msword";
        } else if(psNombre.toLowerCase().endsWith(".xls")){
            lsMimeType="application/vnd.ms-excel";
        } else if(psNombre.toLowerCase().endsWith(".txt")){
            lsMimeType="text/plain";
        } else if(psNombre.toLowerCase().endsWith(".png")){
            lsMimeType="image/png";
        } else if(psNombre.toLowerCase().endsWith(".jpg")){
            lsMimeType="image/jpeg";
        } else if(psNombre.toLowerCase().endsWith(".docx")){
            lsMimeType="application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else {
            lsMimeType=MimeTypes.getMIMEType(psNombre);
        }
        if(JCadenas.isVacio(lsMimeType)){
            lsMimeType="application/octet-stream";
        }
        return lsMimeType;
    }
    public OutputStream getFlujoSalida(JFichero poFichero, boolean pbAppend) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        final Folder parent = (Folder) getSesion().getObjectByPath(poFichero.getPathSinNombre());
        final String name = poFichero.getNombre();
        // properties 
        // (minimal set: name and object type id)
        final Map<String, Object> properties = new HashMap<String, Object>(2);
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, name);
                
        return new OutputStream() {
            private final ByteArrayOutputStreamEdu moOutstream = new ByteArrayOutputStreamEdu(1024*1);
            private ContentStream contentStream = null;
            private boolean mbFin=false;
            private Thread moThread;
            
            @Override
            public synchronized void write(int b) throws IOException {
                moOutstream.write(b);
                if(contentStream==null){
                    contentStream = new ContentStreamImpl(
                    name, null , getMimeType(name)
                    , new InputStream() {
                        int lPosi=0;
                        @Override
                        public synchronized int read() throws IOException {
                            if(mbFin && lPosi >= moOutstream.size()){
                                return -1;
                            } else if (lPosi < moOutstream.size()){
                                byte lb = moOutstream.getByte(lPosi);
                                lPosi++;
                                if(lPosi>1000){
                                    moOutstream.reset(lPosi);
                                    lPosi=0;
                                }
                                return lb;
                            } else {

                                while(lPosi >= moOutstream.size() && !mbFin){
                                    try {
                                        wait(100);
                                    } catch (InterruptedException ex) {}
                                }
                                if(lPosi >= moOutstream.size()){
                                    return -1;
                                } else {
                                    byte lb = moOutstream.getByte(lPosi);
                                    lPosi++;
                                    if(lPosi>1000){
                                        moOutstream.reset(lPosi);
                                        lPosi=0;
                                    }
                                    return lb;
                                }
                            }
                        }
                    });
                    moThread = new Thread(new Runnable() {
                        public void run() {
                            Document newDoc = parent.createDocument(properties, contentStream, VersioningState.MAJOR);
                        }
                    });
                    moThread.start();
                }
            }

            @Override
            public synchronized void close() throws IOException {
                mbFin=true;
            }
            
        };
        
    }
    class ByteArrayOutputStreamEdu extends ByteArrayOutputStream{
        ByteArrayOutputStreamEdu(int pArr){
            super(pArr);
        }
        public synchronized byte[] getBytes(){
            return buf;
        }
        public synchronized byte getByte(int plPosi){
            return buf[plPosi];
        }

        public synchronized void reset(int plPosi) {
            int lSizeD = count-plPosi;
            for(int i = plPosi; i < count; i++){
                buf[i-plPosi]=buf[i];
            }
            count=lSizeD;
        }
        
    }
    public static void main(String[] ps){
        try {
            //inicializamos servidores
            JServidorArchivosChemistry loSerAlfresco = new JServidorArchivosChemistry(
                    "http://doc.electronicadivertida.com:8080/alfresco/api/-default-/public/cmis/versions/1.0/atom"
                    , "Admin", "suse90", "/my first folder/");
            JServidorArchivos loSerLocal = new JServidorArchivos("/home/d/basu/");
            
            //borramos basu de alfresco
            JFichero loPDF = null;
            loPDF = loSerAlfresco.getFichero(new JFichero("","basu",true,0,null));
            if(loPDF.isExiste()){
                IListaElementos<JFichero> loLista =  loSerAlfresco.getListaFicheros(loPDF);
                for(JFichero loLocal : loLista){
                    if(!loLocal.getEsDirectorio()){
                        loSerAlfresco.borrar(loLocal);
                    }
                }
                loSerAlfresco.borrar(loPDF);
            }
            
            //copiamos basu a alfresco
            loPDF = null;
            loSerAlfresco.crearCarpeta(new JFichero("", "basu", true, 0, null));
            IListaElementos<JFichero> loLista =  loSerLocal.getListaFicheros(new JFichero("", "", true, 0, null));
            for(JFichero loLocal : loLista){
                if(!loLocal.getEsDirectorio() && !loLocal.getNombre().startsWith(".")){
                    if(loPDF==null && loLocal.getNombre().toLowerCase().endsWith(".pdf")){
                        loPDF = new JFichero("basu", loLocal.getNombre(), false, loLocal.getLenght(), loLocal.getDateEdu());
                    }
                    loSerAlfresco.copiarFichero(loSerLocal, loLocal, new JFichero("basu", loLocal.getNombre(), false, loLocal.getLenght(), loLocal.getDateEdu()));
                }
            }
            //bajdamos de alfresco un pdf
            loPDF = loSerAlfresco.getFichero(loPDF);
            JFichero loDest = new JFichero(
                    ""
                    , "Copia de " + loPDF.getNombre()
                    , false, 0, null);
            loSerLocal.copiarFichero(loSerAlfresco, loPDF, loDest);
            
            
            
        } catch (Exception ex) {
            JDepuracion.anadirTexto(JServidorArchivosChemistry.class.getName(), ex);
        }
        
        
    }    
    
}

