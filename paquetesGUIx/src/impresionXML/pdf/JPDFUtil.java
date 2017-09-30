/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.pdf;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author eduardo
 */
public class JPDFUtil {
    /**Imprime un PDF por la impresora*/
    public static void imprimirPDF(File pofile, String psImpresora) throws Exception {

        FileInputStream inputStream = null;
        inputStream = new FileInputStream(pofile);
        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        
        PrinterJob pj=PrinterJob.getPrinterJob();
        String lsImpresora = psImpresora;
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        if(lsImpresora!=null && !lsImpresora.equals("")){
            PrintService[] loImpresoras = PrintServiceLookup.lookupPrintServices(null, null);
            for(int i = 0; i < loImpresoras.length  ; i++){
                if(loImpresoras[i].getName().equalsIgnoreCase(lsImpresora)){
                    defaultPrintService = loImpresoras[i];
                }
            }
        }
        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            printJob.print(document, attributeSet);
        } else {
            throw new Exception("No existen impresoras instaladas");
        }

        inputStream.close();
    }
    
    public static void main(String args[]) {
        try {
            JPDFUtil.imprimirPDF(new File("/home/d/DocuTrabajo/itv/dgt/itici/DGT.Proyecto ATRI.Interfaces ATRI.anotacionITV.V01.4.pdf"), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
