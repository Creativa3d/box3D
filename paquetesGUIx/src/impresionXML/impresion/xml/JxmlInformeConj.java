/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import utiles.IListaElementos;
import utiles.JListaElementos;

/**Impresion de varios jxmlinforme en un solo documento*/
public class JxmlInformeConj   extends JxmlAbstract  implements Printable {
    private static final long serialVersionUID = 1L;

    private IListaElementos moElementos = new JListaElementos();
//    private int mlIndex = 0;
    
    public  JxmlInformeConj(){
    }
    
    public void addInforme(JxmlInforme poInf){
        moElementos.add(poInf);
    }
    public IListaElementos getListaElementos(){
        return moElementos;
    }


    public void imprimir(final String psImpresora, final boolean pbPresentarDialogoImpresion) throws PrinterException{

        JxmlInforme loInf=null;
        for(int i = 0 ; i < moElementos.size(); i++){
            loInf = (JxmlInforme) moElementos.get(i);
            loInf.inicializarImpresion();
        }
//        mlIndex=0;
        if(moElementos.size()>0){
            PrinterJob pj=PrinterJob.getPrinterJob();
            String lsImpresora = psImpresora;
            if(lsImpresora!=null && !lsImpresora.equals("")){
                PrintService[] loImpresoras = PrintServiceLookup.lookupPrintServices(null, null);
                PrintService loImpresora=null;
                for(int i = 0; i < loImpresoras.length  ; i++){
                    if(loImpresoras[i].getName().equalsIgnoreCase(lsImpresora)){
                        loImpresora = loImpresoras[i];
                    }
                }
                if(loImpresora!=null){
                    pj.setPrintService(loImpresora);
                }
            }
            pj.setPrintable(this);
            if(pbPresentarDialogoImpresion){
                if (pj.printDialog(loInf.getConfigurarImpresion())) {
                    pj.print();
                }
            }else{
                pj.print(loInf.getConfigurarImpresion());
            }
            pj=null;
//            System.gc();
        }else{
            throw new PrinterException("No hay páginas a imprimir");
        }
        
        
    }
    
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int lResult = 0;

        if(pageIndex>=moElementos.size()){
            lResult = NO_SUCH_PAGE;
        }else{
            JxmlInforme loInf = (JxmlInforme) moElementos.get(pageIndex);
//            loInf.inicializarImpresion();
            loInf.print(graphics, pageFormat, 0);
            lResult = PAGE_EXISTS;
        }
//        mlIndex++;
        
        return lResult;
    }

    protected Object clone() throws CloneNotSupportedException {
        JxmlInformeConj loInf = (JxmlInformeConj) super.clone();
        loInf.moElementos = new JListaElementos();
        for(int i = 0 ; i < moElementos.size(); i++){
            JxmlInforme loInf1 = (JxmlInforme) moElementos.get(i);
            loInf.moElementos.add(loInf1.clone());
        }
        return loInf;
    }

    
}
