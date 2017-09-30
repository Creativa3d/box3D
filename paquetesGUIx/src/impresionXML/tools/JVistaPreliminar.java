/*
 * JVistaPreliminar.java
 *
 * Created on 24 de noviembre de 2004, 14:21
 */

package impresionXML.tools;

//Java
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.URL;
import java.util.Vector;

//JAXP
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.fop.layout.Page;
import org.apache.fop.render.PrintRenderer;
import org.jfor.jfor.converter.Converter;
import org.xml.sax.InputSource;

//Avalon
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;

//FOP
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.FOPException;
import org.apache.fop.render.awt.AWTRenderer;
import org.apache.fop.viewer.PreviewDialog;
import org.apache.fop.viewer.SecureResourceBundle;
import org.apache.fop.viewer.Translator;
import org.apache.fop.viewer.UserMessage;
import org.apache.fop.messaging.MessageHandler;
import utiles.CadenaLarga;
import utiles.JDepuracion;

/**Vista preliminar*/
public class JVistaPreliminar {
    /**traducciones*/
    public static final String TRANSLATION_PATH =
        "/org/apache/fop/viewer/resources/";

    /**
     * Crea el dialogo de previsualización
     * @param renderer reder
     * @param res trans
     * @return Dialogo
     */
    protected PreviewDialog createPreviewDialog(
                AWTRenderer renderer,
                Translator res) {
        PreviewDialog frame = new PreviewDialog(renderer, res);
        frame.validate();
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent we) {
                    //nada
                }
            });

        // center window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height){
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width){
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        return frame;
    }

    private SecureResourceBundle getResourceBundle(String path) throws IOException {
        URL url = getClass().getResource(path);
        if (url == null) {
            // if the given resource file not found, the english resource uses as default
            url = getClass().getResource(path.substring(0, path.lastIndexOf(".")) + ".en");
        }
        return new SecureResourceBundle(url.openStream());
    }
    /**
     * Vista preliminar del xml fuente + xsl
     * @param psXml cadena xml
     * @param psXsl cadena xsl
     * @throws IOException error
     * @throws FOPException error
     * @throws TransformerException error
     */
    public void vistaPreliminar(String psXml, String psXsl) throws IOException, FOPException, TransformerException {
        vistaPreliminar(new CadenaLarga(psXml), new CadenaLarga(psXsl));
    }

    /**
     * Vista preliminar del xml fuente + xsl
     * @param poXml InputStream
     * @param poXsl InputStream
     * @throws IOException error
     * @throws FOPException error
     * @throws TransformerException error
     */
    public void vistaPreliminar(InputStream poXml, InputStream poXsl) throws IOException, FOPException, TransformerException {
        vistaPreliminar(new StreamSource(poXml), new StreamSource(poXsl));
    }

    /**
     * Vista preliminar de xsl-fo directo
     * @param xslFO cadena
     * @throws IOException error
     * @throws FOPException error
     * @throws TransformerException error
     */
    public void vistaPreliminar(String xslFO) throws IOException, FOPException, TransformerException {
        vistaPreliminar(new CadenaLarga(xslFO));
    }
    /**
     * Vista preliminar de xsl-fo directo
     * @param xslFO InputStrem xsl-fo
     * @throws IOException error
     * @throws FOPException error
     * @throws TransformerException error
     */
    public void vistaPreliminar(java.io.InputStream xslFO) throws IOException, FOPException, TransformerException {
        vistaPreliminar(new InputSource(xslFO));
    }
    /**
     * Vista preliminar de xsl-fo directo
     * @param xslFO InputSource del xsl-fo
     * @throws IOException error
     * @throws FOPException error
     * @throws TransformerException error
     */
    public void vistaPreliminar(InputSource xslFO) throws IOException, FOPException, TransformerException {
        //Setup l18n
        String language = System.getProperty("user.language");
        Translator translator = getResourceBundle(
            TRANSLATION_PATH + "resources." + language);
        translator.setMissingEmphasized(false);

        UserMessage.setTranslator(getResourceBundle(
            TRANSLATION_PATH + "messages." + language));

        //Setup renderer
        AWTRenderer renderer = new AWTRenderer(translator);
        
        //Create preview dialog (target for the AWTRenderer)
        PreviewDialog frame = createPreviewDialog(renderer, translator);
        renderer.setProgressListener(frame);
        renderer.setComponent(frame);

        //Setup Driver
        Driver driver = new Driver();
        driver.setLogger(new ConsoleLogger(ConsoleLogger.LEVEL_WARN));
        driver.setRenderer(renderer);

        try {
            // build FO tree: time
            frame.progress(translator.getString("Contruyendo informe") + " ...");

//            driver.setRenderer(Driver.RENDER_PDF);
//            driver.setOutputStream(new FileOutputStream("/home/eduardo/out.pdf"));
            driver.setInputSource(xslFO);
            driver.run();
            
            //Show page
            frame.progress(translator.getString("Mostrando"));
            frame.showPage();

        } catch (FOPException e) {
            frame.reportException(e);
            throw (FOPException)e;
        } catch (Exception e) {
            frame.reportException(e);
            throw new FOPException(e);
        }
    }
    /**
     * Vista preliminar del xml fuente + xsl
     * @param xml source xml
     * @param xsl source xsl
     * @throws IOException error
     * @throws FOPException error
     * @throws TransformerException error
     */
    public void vistaPreliminar(Source xml, Source xsl) throws IOException, FOPException, TransformerException {

//        //para sacarlo a fichero
//        OutputStream out = new java.io.FileOutputStream("/home/eduardo/fo.txt");
//        try {
//            //Setup XSLT
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer transformer = factory.newTransformer(xsl);
//        
//            //Setup input for XSLT transformation
//            Source src = xml;
//        
//            //Resulting SAX events (the generated FO) must be piped through to FOP
//            Result res = new StreamResult(out);
//
//            //Start XSLT transformation and FOP processing
//            transformer.transform(src, res);
//        } finally {
//            out.close();
//        }        
        
        //Setup l18n
        String language = System.getProperty("user.language");
        Translator translator = getResourceBundle(
            TRANSLATION_PATH + "resources." + language);
        translator.setMissingEmphasized(false);

        UserMessage.setTranslator(getResourceBundle(
            TRANSLATION_PATH + "messages." + language));

        //Setup renderer
        AWTRenderer renderer = new AWTRenderer(translator);

        //Create preview dialog (target for the AWTRenderer)
        PreviewDialog frame = createPreviewDialog(renderer, translator);
        renderer.setProgressListener(frame);
        renderer.setComponent(frame);

        //Setup Driver
        Driver driver = new Driver();
        driver.setLogger(new ConsoleLogger(ConsoleLogger.LEVEL_WARN));
        driver.setRenderer(renderer);

        try {
            // build FO tree: time
            frame.progress(translator.getString("Contruyendo informe") + " ...");

            //Load XSL-FO file (you can also do an XSL transformation here)
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsl);
            Result res = new SAXResult(driver.getContentHandler());
            transformer.transform(xml, res);

            //Show page
            frame.progress(translator.getString("Mostrando"));
            frame.showPage();

        } catch (Exception e) {
            frame.reportException(e);
            throw new FOPException(e);
        }
    }
    
    public void impresionDirecta(InputStream poFO) throws IOException, FOPException, TransformerException {
        //Setup logger
        Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
        final InputSource input = new InputSource(poFO);
        Driver driver = new Driver(input, null);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PrintRenderer renderer = new PrintRenderer(pj);

        driver.setLogger(logger);
        driver.setRenderer(renderer);
        driver.run();
    }
    
    public void transformarAFo(InputStream poXml, InputStream poXsl, OutputStream out) throws IOException, TransformerException {
        //Setup XSLT
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(poXsl));

        //Setup input for XSLT transformation
        Source src = new StreamSource(poXml);

        //Resulting SAX events (the generated FO) must be piped through to FOP
        Result res = new StreamResult(out);

        //Start XSLT transformation and FOP processing
        transformer.transform(src, res);
    }
    
    public void transformarAPDF(InputStream poXml, InputStream poXsl, OutputStream out) throws IOException, TransformerException {
        //Construct driver
        Driver driver = new Driver();
        
        //Setup logger
        Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
        driver.setLogger(logger);
        MessageHandler.setScreenLogger(logger);

        //Setup Renderer (output format)        
        driver.setRenderer(Driver.RENDER_PDF);
        
        //Setup output
        try {
            driver.setOutputStream(out);

            //Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(poXsl));
        
            //Setup input for XSLT transformation
            Source src = new StreamSource(poXml);
        
            //Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(driver.getContentHandler());

            //Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } finally {
            out.close();
        }        
    }

    public void transformarAPDF(InputStream poFO, OutputStream out) throws IOException, FOPException {
        
        //Construct driver
        Driver driver = new Driver();
        
        //Setup logger
        Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
        driver.setLogger(logger);
        MessageHandler.setScreenLogger(logger);

        //Setup Renderer (output format)        
        driver.setRenderer(Driver.RENDER_PDF);
        
        try {
            driver.setOutputStream(out);

            //Setup input
            driver.setInputSource(new InputSource(poFO));

            //Process FO
            driver.run();
        } finally {
            out.close();
        }
    }
    public void transformarARTF(InputStream poFO, File poOutRTF) throws Exception{
        
        final InputSource input = new InputSource(poFO);
        final Writer output = new BufferedWriter(new FileWriter(poOutRTF));

        new Converter(input,output,Converter.createConverterOption ());
    }
    
    // This is stolen from PrintStarter
    class PrintRenderer extends AWTRenderer {

        private static final int EVEN_AND_ALL = 0;
        private static final int EVEN = 1;
        private static final int ODD = 2;

        private final int startNumber;
        private int endNumber;
        private int mode;
        private final PrinterJob printerJob;

        PrintRenderer(PrinterJob printerJob) {
            super(null);

            this.printerJob = printerJob;
            startNumber = 0 ;
            endNumber = -1;

            printerJob.setPageable(this);

            mode = EVEN_AND_ALL;
            String str = System.getProperty("even");
            if (str != null) {
                try {
                    mode = Boolean.getBoolean(str) ? EVEN : ODD;
                } catch (Exception e) {
                }

            }
        }

        public void stopRenderer(OutputStream outputStream)
        throws IOException {
            super.stopRenderer(outputStream);

            if (endNumber == -1){
                endNumber = getPageCount();
            }

            Vector numbers = getInvalidPageNumbers();
            for (int i = numbers.size() - 1; i > -1; i--){
                removePage(
                  Integer.valueOf((String) numbers.elementAt(i)).intValue());
            }
            try {
                printerJob.print();
            } catch (PrinterException e) {
                JDepuracion.anadirTexto(getClass().getName(), e);
                throw new IOException("Unable to print: " +
                                      e.getClass().getName() + ": " + e.getMessage());
            }
        }

        public void renderPage(Page page) {
            pageWidth = (int)((float) page.getWidth() / 1000f);
            pageHeight = (int)((float) page.getHeight() / 1000f);
            super.renderPage(page);
        }


        private Vector getInvalidPageNumbers() {

            Vector vec = new Vector();
            int max = getPageCount();
            boolean isValid;
            for (int i = 0; i < max; i++) {
                isValid = true;
                if (i < startNumber || i > endNumber) {
                    isValid = false;
                } else if (mode != EVEN_AND_ALL) {
                    if (mode == EVEN && ((i + 1) % 2 != 0)){
                        isValid = false;
                    }else {
                        if (mode == ODD && ((i + 1) % 2 != 1)){
                            isValid = false;
                        }
                    }
                }

                if (!isValid){
                    vec.add(i + "");
                }
            }

            return vec;
        }
    } // class PrintRenderer
    
}
