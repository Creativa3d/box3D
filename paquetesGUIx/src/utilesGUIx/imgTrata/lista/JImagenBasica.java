/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.imgTrata.lista;

import ListDatos.IFilaDatos;
import impresionXML.impresion.pdf.JPDFManejo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.ImageIcon;
import utiles.JArchivo;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.imgTrata.JVISTAPRELIMINAR;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JImagenBasica implements IImagen {
    private final int mlDescrip;
    private final int mlImagen;
    protected IFilaDatos moFila;
    private ImageIcon moIcon;
    protected String msRutaBase;
    private boolean mbDocumento = false;
    private String msRuta;
    private String msTemp;

    public JImagenBasica(int plImagen, int plDescrip){
        mlImagen = plImagen;
        mlDescrip = plDescrip;
        moIcon=null;
    }
    public JImagenBasica(int plImagen, int plDescrip, String psRutaBase){
        this(plImagen, plDescrip);
        msRutaBase = psRutaBase;
    }

    protected synchronized void leerIconIMG(String psImagen) throws Exception{
//        File loFile = new File(psImagen);
//        if(loFile.exists()){
//            moIcon = JIMGTrata.getIMGTrata().getImagenCargada(psImagen);
//            mbDocumento = false;
//        }
//        if(moIcon == null){
//            URL loUrl = getClass().getResource(psImagen);
//            if(loUrl!=null){
//                moIcon = new javax.swing.ImageIcon(loUrl);
//                JIMGTrata.getIMGTrata().imagenEsperar(moIcon.getImage());
//                mbDocumento = false;
//            }
//        }
//        if(moIcon == null){
//            try{
//                moIcon = new javax.swing.ImageIcon(new URL(psImagen));
//                JIMGTrata.getIMGTrata().imagenEsperar(moIcon.getImage());
//                mbDocumento = false;
//            }catch(Throwable e){
//                JDepuracion.anadirTexto(getClass().getName() , e);
//            }
//        }
        moIcon = JIMGTrata.getIMGTrata().getImagenCargada(psImagen);
        mbDocumento = false;
    }
    protected synchronized void leerIconPDF(String psImagen) throws Exception{
        msTemp="";
        if(psImagen.toLowerCase().startsWith("http")){
            File loFile = File.createTempFile("doc", ".pdf");
            URL url = new URL(psImagen);
            URLConnection connection = url.openConnection();    
            JArchivo.guardarArchivo(connection.getInputStream(), new FileOutputStream(loFile));
            msTemp=loFile.getAbsolutePath();
        }else{
            msTemp=psImagen;
        }
        JPDFManejo loPDF = new JPDFManejo(msTemp);
        try{
            BufferedImage loImg = loPDF.getImage(0);
            moIcon = new javax.swing.ImageIcon(loImg);
            mbDocumento = true;
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName() , e);
        }
        
    }
    protected synchronized void leerIconWORD(String psImagen) throws Exception{
        msTemp="";
        try{
            moIcon = new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/WordIcon.png"));
            mbDocumento = true;
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName() , e);
        }
        
    }    
    public synchronized void setDatos(IFilaDatos poFila) throws Throwable {
        moFila = poFila;
        limpiar();
        if( moFila !=null && !moFila.msCampo(mlImagen).equals("")){
            msRuta = moFila.msCampo(mlImagen);
            if(msRutaBase!=null && !msRutaBase.equals("")){
                msRuta = msRutaBase + msRuta;
            }
            moIcon = null;
            leerRuta(msRuta);
        }
    }
    protected void limpiar(){
        moIcon=null;
    }
    protected void leerRuta(String psRuta) throws Exception{
        moIcon=null;
        msRuta=psRuta;
        msTemp="";
        if(msRuta.toLowerCase().contains(".pdf")){
            leerIconPDF(msRuta);
            if(isIconVacio()){
                leerIconIMG(msRuta);
            }
        }else if(msRuta.toLowerCase().contains(".doc") || msRuta.toLowerCase().contains(".docx")){
            leerIconWORD(msRuta);
            if(isIconVacio()){
                leerIconIMG(msRuta);
            }
        }else{
            leerIconIMG(msRuta);
            if(isIconVacio()){
                leerIconPDF(msRuta);
            }
        }
    }
    private boolean isIconVacio(){
        if(moIcon==null){
            return true;
        }else{
            return moIcon.getIconWidth()<=0 && moIcon.getIconWidth()<=0;
        }
    }
    public ImageIcon getImagen() {
        return moIcon;
    }

    public String getDescripcion() {
        if( moFila !=null ){
            return moFila.msCampo(mlDescrip);
        }else{
            return "";
        }
    }

    public void ver() throws Throwable {
        if(mbDocumento){
            if(msRuta.toLowerCase().startsWith("http") && JCadenas.isVacio(msTemp)){
                File loFile;
                if(msRuta.toLowerCase().contains(".pdf")){
                    loFile = File.createTempFile("doc", ".pdf");
                } else {
                    loFile = File.createTempFile("doc", ".doc");
                }
                URL url = new URL(msRuta);
                URLConnection connection = url.openConnection();    
                JArchivo.guardarArchivo(connection.getInputStream(), new FileOutputStream(loFile));
                msTemp=loFile.getAbsolutePath();
            }
            JEjecutar.abrirDocumento(msTemp);
        }else{
            JVISTAPRELIMINAR loVista = new JVISTAPRELIMINAR();
            loVista.setImagen(moIcon.getImage(), getDescripcion());
            loVista.setVisible(true);
        }
    }

}
