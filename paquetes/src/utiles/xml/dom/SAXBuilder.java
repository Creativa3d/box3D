/*
 * SAXBuilder.java
 *
 * Created on 12 de septiembre de 2006, 20:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utiles.xml.dom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.red.*;
import utiles.xml.sax.ISax;
import utiles.xml.sax.JAtributos;
import utiles.xml.sax.JEtiqueta;
import utiles.xml.sax.JSaxParser;

public class SAXBuilder implements ISax {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    private Element moRoot = null;
    private IListaElementos moPila = null;
    /** Creates a new instance of SAXBuilder */
    public SAXBuilder() {
    }
    
    public static InputStream getInputStream(String psFile) throws IOException {
        if(psFile.toLowerCase().indexOf("file:")>-1 || 
           psFile.toLowerCase().indexOf("http:")>-1 ||
           psFile.toLowerCase().indexOf("ftp:") >-1
           ){
            try{
                return msoOpenConnection.getConnection(new URL(psFile)).getInputStream();
//                return (new URL(psFile)).openConnection(ProxyConfig.getProxy()).getInputStream();
            }catch(Exception e){
                
                throw new  IOException(e.toString());
            }
        }else{
            return new FileInputStream(new File(psFile));
        }

    }

    public Document build(String psFile) throws JDOMException{
        InputStream loIn=null;
        try{
            loIn=getInputStream(psFile);
//            System.out.println("Despues getInputStream");
            return build(loIn);
        }catch(IOException ex){
            throw new JDOMException(ex.toString());
        }finally{
            if(loIn!=null){
                try {
                    loIn.close();
                } catch (IOException ex) {}
            }
        }
    }
    public Document build(File poFile) throws JDOMException{
        try{
            return build(new FileInputStream(poFile));
        }catch(IOException ex){
            throw new JDOMException(ex.toString());
        }
    }

    public Document build(InputStream poInput) throws JDOMException{
        try {
            moPila = new JListaElementos();
            JSaxParser loSax = new JSaxParser();
            loSax.parser(poInput, this);
//            System.out.println("Despues loSax.parser");
            return new Document(moRoot);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex); 
            throw new JDOMException(ex.toString());
        }
    }

    public void startDocument() throws Exception {
    }

    public void endDocument() throws Exception {
    }

    public void startElement(String name, JAtributos poAtributos) throws Exception {
        try{
            Element loElemActual = new Element(name);
            loElemActual.setAttributes(poAtributos);
            if(moRoot==null){
                moRoot = loElemActual;
            }else{
                ((Element)moPila.get(moPila.size()-1)).addContent(loElemActual);
            }
            moPila.add(loElemActual);
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Error en " + name);
            JDepuracion.anadirTexto(getClass().getName(), e); 
            throw e;
        }
    }

    public void endElement(JEtiqueta poEtiqueta) throws Exception {
        try{
            Element loElemUlt = ((Element)moPila.get(moPila.size()-1));
            if(loElemUlt.getNombre().compareTo(poEtiqueta.getNombre())!=0){
                throw new Exception("Se esperaba fin de " + loElemUlt.getNombre() + " pero se encontro " + poEtiqueta.getNombre());
            }
            loElemUlt.setValor(poEtiqueta.getValor());
            moPila.remove(moPila.size()-1);
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Error en " + poEtiqueta.getNombre());
            JDepuracion.anadirTexto(getClass().getName(), e); 
            throw e;
        }
    }
    
}
