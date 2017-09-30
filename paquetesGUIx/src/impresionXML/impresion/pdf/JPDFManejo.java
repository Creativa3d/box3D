/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.pdf;

import impresionXML.impresion.xml.JxmlInforme;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFTextStripper;
import utiles.CadenaLargaOut;

/**
 * Convert a PDF document to an image.
 *
 * @author <a href="ben@benlitchfield.com">Ben Litchfield</a>
 * @version $Revision: 1.1 $
 */
public class JPDFManejo {

    private static final String PASSWORD = "-password";
    private static final String START_PAGE = "-startPage";
    private static final String END_PAGE = "-endPage";
    private static final String IMAGE_FORMAT = "-imageType";
    private static final String OUTPUT_PREFIX = "-outputPrefix";
    private static final String COLOR = "-color";
    private static final String RESOLUTION = "-resolution";

    private String msPDFFile;
    private String msPassword;
    private int imageType = BufferedImage.TYPE_INT_RGB;
    private int mlResolucion=96;
    private PDDocument document = null;
    
    public JPDFManejo(String psPDFFile) {
        this(psPDFFile, "");
    }
    public JPDFManejo(String psPDFFile, String psPassword) {
        msPDFFile = psPDFFile;
        msPassword=psPassword;
    }
    public PDDocument getDocumento() throws Exception {
        if(document==null){
            if(msPDFFile.indexOf("://")>=0){
                document = PDDocument.load(new URL(msPDFFile));
            }else{
                document = PDDocument.load(msPDFFile);
            }
            if (document.isEncrypted()) {
                document.decrypt(getPassword());
            }
        }
        return document;
    }
    
    public void print(String psImpresora) throws Exception{
        PrinterJob job = JxmlInforme.getPrintJob(psImpresora);
        PDDocument doc = getDocumento();
        doc.silentPrint(job);
    }
    
    public int size() throws Exception{
        return getDocumento().getNumberOfPages();
    }

    public BufferedImage getImage(int plPagina) throws Exception {
        List pages = getDocumento().getDocumentCatalog().getAllPages();
        PDPage page = (PDPage) pages.get(plPagina);
        return page.convertToImage(getImageType(), getResolucion());
    }
    
    public String getTexto() throws Exception{
        CadenaLargaOut os = new CadenaLargaOut();
        OutputStreamWriter writer = new OutputStreamWriter(os);
 
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.writeText(getDocumento(), writer);    
        return os.toString();
    }
    public void extraerImagenes(String psRutaExtraccion) throws Exception{
        List pages = getDocumento().getDocumentCatalog().getAllPages();
        Iterator iter = pages.iterator(); 
        int i =1;
        String name = null;

        while (iter.hasNext()) {
            PDPage page = (PDPage) iter.next();
            PDResources resources = page.getResources();
            Map pageImages = resources.getImages();
            if (pageImages != null) { 
                Iterator imageIter = pageImages.keySet().iterator();
                while (imageIter.hasNext()) {
                    String key = (String) imageIter.next();
                    PDXObjectImage image = (PDXObjectImage) pageImages.get(key);
                    image.write2file(psRutaExtraccion+"/image" + i);
                    i ++;
                }
            }
        }        
    }

    private void close() throws IOException{
        if(document!=null){
            document.close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); 
        close();
    }

    /**
     * @return the msPassword
     */
    public String getPassword() {
        return msPassword;
    }

    /**
     * @param msPassword the msPassword to set
     */
    public void setPassword(String msPassword) {
        this.msPassword = msPassword;
    }

    /**
     * @return the imageType
     */
    public int getImageType() {
        return imageType;
    }

    /**
     * @param imageType the imageType to set
     */
    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    /**
     * @return the mlResolucion
     */
    public int getResolucion() {
        return mlResolucion;
    }

    /**
     * @param mlResolucion the mlResolucion to set
     */
    public void setResolucion(int mlResolucion) {
        this.mlResolucion = mlResolucion;
    }
    
    
}
