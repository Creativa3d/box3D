/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xmlxsl;


import java.awt.Frame;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author pvillar
 */
public abstract class JXmlXslInforme {
    
    private String moPathXML;
    private String moPathXSLT;
    private PrintService loImpresora = null;
    
    public JXmlXslInforme (final String poPathXML, final String poPathXSLT){
        this.moPathXML = poPathXML;
        this.moPathXSLT = poPathXSLT;
    }
    
    
    
    public void imprimir(final String psImpresora,final int plCopias , final JXmlXslInformeParam poParam) throws TransformerConfigurationException, PrinterException,Exception{
        InputStream stylesheet = cargarFichero(moPathXSLT);

        if(stylesheet == null){
            throw new Exception("No se ha encontrado el archivo XSLT");
        }

        InputStream datafile = cargarFichero(moPathXML);

        if(datafile == null){
            throw new Exception("No se ha encontrado el archivo XML");
        }

        seleccionarImpresora(psImpresora);
                
        ByteArrayOutputStream loOutPutStream = construirFicheroSalida(stylesheet,datafile);

        ordenarImprimir(poParam, loOutPutStream,plCopias);

    }
    
    private void seleccionarImpresora(final String psImpresora){
        if(psImpresora!=null && !psImpresora.equals("")){
            PrintService[] loImpresoras = PrintServiceLookup.lookupPrintServices(null, null);
            loImpresora=null;
            for(int i = 0; i < loImpresoras.length  ; i++){
              if(loImpresoras[i].getName().equalsIgnoreCase(psImpresora)){
                  loImpresora = loImpresoras[i];
              }
            }
        }else {
            loImpresora = PrintServiceLookup.lookupDefaultPrintService();
        }
    }
    
    private ByteArrayOutputStream construirFicheroSalida(final InputStream poStylesheet, final InputStream poDatafile ) throws Exception{
         try {
            DocumentBuilderFactory loFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder loBbuilder;
       
            loBbuilder = loFactory.newDocumentBuilder();

            Document loDocument = loBbuilder.parse(poDatafile);

            // Use a Transformer for output
            TransformerFactory loTransformeFactory = TransformerFactory.newInstance();
            StreamSource loXsl = new StreamSource(poStylesheet);
            Transformer loTransformer = loTransformeFactory.newTransformer(loXsl);

            DOMSource loXml = new DOMSource(loDocument);
            ByteArrayOutputStream loOutPutStream = new ByteArrayOutputStream();
            StreamResult loResultado = new StreamResult(loOutPutStream);
            loTransformer.transform(loXml, loResultado);
          
            return loOutPutStream;
        } catch (ParserConfigurationException ex) {
             throw new Exception("Error construyendo la factoria de documentos",ex);
        } catch (SAXException ex) {
             throw new Exception("Error constuyendo el documento",ex);
        } catch (IOException ex) {
             throw new Exception("Error leyendo documento",ex);
        } catch (TransformerConfigurationException ex) {
             throw new Exception("Error leyendo XSL",ex);
        } catch (TransformerException ex) {
             throw new Exception("Errro transformando XML",ex);
        }
        
    }
    
    
    private void ordenarImprimir(final JXmlXslInformeParam poParam,final ByteArrayOutputStream poOutPutStream,final int plCopias){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    String loHtml = poOutPutStream.toString();
                    JEditorPane loEditor = new JEditorPane();
                    
                    HTMLEditorKit loHTMLEditorKit = new HTMLEditorKit();
                    String loHtmlCorregido = corregirEstilos(loHTMLEditorKit,loHtml);
                    if(loHtmlCorregido != null && !loHtmlCorregido.equals("")) {
                        loHtml = loHtmlCorregido;
                    }
                    loEditor.setEditorKit(loHTMLEditorKit);
                    loEditor.setEditable(false);
                    StringReader stringReader = new StringReader(loHtml);
                    loHTMLEditorKit.read(stringReader, loEditor.getDocument(), 0);
                    
                    PrintRequestAttributeSet loPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
                    loPrintRequestAttributeSet.add(poParam.getMoMediaName());
                    loPrintRequestAttributeSet.add(poParam.getMoOrientation());
                    int llCopias = plCopias;
                    if(llCopias < 1){
                        llCopias = 1;
                    }
                    loPrintRequestAttributeSet.add(new Copies(llCopias));   
 
                    loEditor.print(null, null, false, loImpresora, loPrintRequestAttributeSet, true);
   
                } catch (PrinterException ex) {
                    Logger.getLogger(JXmlXslInforme.class.getName()).log(Level.SEVERE, null, ex);
                    JMsgBox.mensajeError(new Frame(), "Error al imprimir",ex);
                } catch (IOException ex) {
                    Logger.getLogger(JXmlXslInforme.class.getName()).log(Level.SEVERE, null, ex);
                    JMsgBox.mensajeError(new Frame(), "Error al imprimir",ex);
                } catch (BadLocationException ex) {
                    Logger.getLogger(JXmlXslInforme.class.getName()).log(Level.SEVERE, null, ex);
                    JMsgBox.mensajeError(new Frame(), "Error al imprimir",ex);
                }
            }
           });
    }
    
    protected abstract String corregirEstilos(HTMLEditorKit poEditorPane,String poHtml);
    
    
    private InputStream cargarFichero(String loPath){
        InputStream loInput = JXmlXslInforme.class.getResourceAsStream(loPath);
        if(loInput  == null ){
            try {
                loInput = new FileInputStream(loPath);
            } catch (FileNotFoundException ex) {
            }
        }
        return loInput;
        
    }
    
}
