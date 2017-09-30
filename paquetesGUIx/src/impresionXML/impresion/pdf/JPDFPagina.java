/*
 * JLienzo.java
 *
 * Created on 13 de septiembre de 2004, 9:32
 */

package impresionXML.impresion.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import impresionXML.impresion.estructura.*;
import java.awt.Canvas;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.OutputStream;

/**
 * Objeto página, en donde se imprime
 * conversion de cm a 1/72 parte de una pulgada(inch) 1 inch = 2.54 cm
 */
public class JPDFPagina extends Canvas implements ILienzo {
    private static final long serialVersionUID = 1L;
    
    private final Point2D moUltPosImprimidaReal = new Point2D.Float();
    private final Rectangle2D moAreaImpresionOriginal;
    private Rectangle2D moAreaImpresion;
//    private final Graphics2D  g2Original;
//    private final Graphics2D  moGraphic2d;
    Document document;
    PdfWriter writer;
    private final float mdAnchoTotal;
    private final float mdAltoTotal;
    private final float mdMargenIzq;
    private final float mdMargenSup;
    private final float mdMargenDer;
    private final float mdMargenInf;
    /**
     * poAreaImpresion
     * margenes izq.,sup.  y tamaño TOTAL de la pagina
     * ejemplo: para margenes 1,2 y tamaño de pagina 21, 29 seria (1,2,21,29)
     * @param pdMargenIzq Margen izq.
     * @param pdMargenSup Margen sup.
     * @param pdAnchoTotal Ancho total
     * @param pdAltoTotal alto total
     * @param poPagina Formato de la página
     * @param g en donde pintar
     */
    public JPDFPagina(float pdAnchoTotal, float pdAltoTotal, float pdMargenIzq, float pdMargenSup, float pdMargenDer, float pdMargenInf, OutputStream poOut) throws DocumentException {
        super();
        mdAnchoTotal=pdAnchoTotal;
        mdAltoTotal=pdAltoTotal;
        mdMargenIzq=pdMargenIzq;
        mdMargenSup=pdMargenSup;
        mdMargenDer=pdMargenDer;
        mdMargenInf=pdMargenInf;

    	// step 1
        document = new Document(
                new Rectangle(
                    (int)mdConvertir(mdAnchoTotal),
                    (int)mdConvertir(mdAltoTotal)),
                (int)mdConvertir(mdMargenIzq),
                (int)mdConvertir(mdMargenDer),
                (int)mdConvertir(mdMargenSup),
                (int)mdConvertir(mdMargenInf));
        // step 2
        writer = PdfWriter.getInstance(document, poOut);
        // step 3
        document.open();        //establecemos el area de impresion->
        moAreaImpresionOriginal = new Rectangle2D.Float();
        moAreaImpresionOriginal.setRect(
            mdConvertir(pdMargenIzq), 
            mdConvertir(pdMargenSup), 
            mdConvertir(pdAnchoTotal-pdMargenIzq), 
            mdConvertir(pdAltoTotal -pdMargenSup)
            );

        moUltPosImprimidaReal.setLocation(
            (float)moAreaImpresionOriginal.getX(),
            (float)moAreaImpresionOriginal.getY());

        restaurarAreaImpresionPagina();
    }
    public void close(){
        document.close();
    }
    public void newPage(){
        document.newPage();
    }
    /**
     * acepta cm devuelve puntos
     */
    public static double mdConvertir(final double pdCM){
        return (pdCM * 72) / 2.54;
    }
    /**
     * acepta pulgadas y devuelve cm
     */
    public static double mdConvertirACM(final double pdPulgadas72){
        return (pdPulgadas72 *  2.54) / 72;
    }
    
    public Rectangle2D getAreaImpresionOriginal() {
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setRect(
            mdConvertirACM(moAreaImpresionOriginal.getX()), 
            mdConvertirACM(moAreaImpresionOriginal.getY()), 
            mdConvertirACM(moAreaImpresionOriginal.getWidth()), 
            mdConvertirACM(moAreaImpresionOriginal.getHeight())
            );
        return loRect;
    }
    public Rectangle2D getAreaImpresion() {
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setRect(
            mdConvertirACM(moAreaImpresion.getX()), 
            mdConvertirACM(moAreaImpresion.getY()), 
            mdConvertirACM(moAreaImpresion.getWidth()), 
            mdConvertirACM(moAreaImpresion.getHeight()));
        return loRect;
    }
    
    public Point2D getUltPosicionImprimida() {
        Point2D loPunto = new Point2D.Float();
        loPunto.setLocation(
            mdConvertirACM(moUltPosImprimidaReal.getX()),
            mdConvertirACM(moUltPosImprimidaReal.getY()));
        return loPunto;
    }
    
    public boolean imprimirImagen(final java.awt.Image poImage, final Rectangle2D poPosicionDestino) {
        //las imagenes siempre tienen exito
        boolean lbExito = true;
        if(!mbSeSale(mdX(mdConvertir(poPosicionDestino.getY() + poPosicionDestino.getHeight())))){
            try {
                moUltPosImprimidaReal.setLocation(mdX(mdConvertir(poPosicionDestino.getX())) + mdConvertir(poPosicionDestino.getWidth()), mdY(mdConvertir(poPosicionDestino.getY())) + mdConvertir(poPosicionDestino.getHeight()));
                Image loImage = Image.getInstance(poImage, null);

                
                loImage.setAbsolutePosition((int) mdX(mdConvertir(poPosicionDestino.getX())), (int) mdY(mdConvertir(poPosicionDestino.getY()+poPosicionDestino.getHeight())));
                loImage.scaleToFit((int)mdConvertir(poPosicionDestino.getWidth()), (int) mdConvertir(poPosicionDestino.getHeight()));
                document.add(loImage);
                lbExito = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return lbExito;
            
    }
    
    public boolean imprimirLinea(final java.awt.geom.Point2D poPunto1, final java.awt.geom.Point2D poPunto2, final JEstiloLinea poEstiloLinea) {
        PdfContentByte canvas = writer.getDirectContent();
        canvas.setColorStroke(new BaseColor(poEstiloLinea.moColor));
        canvas.setLineWidth(poEstiloLinea.mdGrosor);

        switch(poEstiloLinea.mlEstilo){ 
            case JEstiloLinea.mclSolido:
                canvas.setLineCap(canvas.LINE_CAP_BUTT);
                canvas.setLineDash(0);
                break;
            case JEstiloLinea.mclRayado:
                canvas.setLineCap(canvas.LINE_CAP_BUTT);
                canvas.setLineJoin(canvas.LINE_JOIN_MITER);
                canvas.setLineDash(10);
                 break;
            case JEstiloLinea.mclPunteado:
                canvas.setLineCap(canvas.LINE_CAP_BUTT);
                canvas.setLineJoin(canvas.LINE_JOIN_MITER);
                canvas.setLineDash(2);
                break;
            default:
        }

        canvas.moveTo((int)mdX(mdConvertir(poPunto1.getX())),(int)mdY(mdConvertir(poPunto1.getY()) ));
        canvas.lineTo((int)mdX(mdConvertir(poPunto2.getX())),(int)mdY(mdConvertir(poPunto2.getY()) ));
        canvas.stroke();
        moUltPosImprimidaReal.setLocation(
            Math.max(mdX(mdConvertir(poPunto1.getX())),mdX(mdConvertir(poPunto2.getX()))), 
            Math.max(mdY(mdConvertir(poPunto1.getY())),mdY(mdConvertir(poPunto2.getY())))
            );
        return true;
            
    }
    private boolean mbSeSale(final double pdAlto){
        return (pdAlto > (moAreaImpresionOriginal.getHeight() + moAreaImpresionOriginal.getY()));
    }
    private double mdX(final double pdX){
        return pdX+moAreaImpresion.getX();
    }
    private double mdY(final double pdY){
        return moAreaImpresion.getHeight()-pdY;
    }
    public boolean imprimirTexto(final JParamTextoLibre poParam) {
        boolean lbExito = true;
        Rectangle2D loRect = new Rectangle2D.Float();
        loRect.setRect(
            mdX(mdConvertir(poParam.moPosicion.getX() - (poParam.mdAngulo>180 ? 0.3: 0) )  ), 
            mdY(mdConvertir(poParam.moPosicion.getY() - (poParam.mdAngulo>180 ? poParam.moPosicion.getHeight(): 0)))+2.5,
            mdConvertir(poParam.moPosicion.getWidth()), 
            mdConvertir(poParam.moPosicion.getHeight()+0.1)
            );

        if(mbSeSale((loRect.getY() + .4))){
            lbExito=false;
        }
        if(lbExito){
            String lsFuente = poParam.moFuente.getName();
            if(lsFuente.equalsIgnoreCase("Courier New") 
                    ||lsFuente.equalsIgnoreCase("Monospaced") ){
                lsFuente = "COURIER";
            } else if(lsFuente.equalsIgnoreCase("Arial") 
                    ||lsFuente.equalsIgnoreCase("SansSerif") 
                    ||lsFuente.equalsIgnoreCase("Dialog")){
                lsFuente = "HELVETICA";
            }

            Font loFont = new Font (
                    Font.getFamily(lsFuente),
                    poParam.moFuente.getSize(),
                    (poParam.moFuente.isBold() ? Font.BOLD : 0)+
                        (poParam.moFuente.isItalic() ? Font.ITALIC : 0)+
                        (poParam.mbFuenteSubrayada ? Font.UNDERLINE: 0),
                    (poParam.moColor==null ||
                        (poParam.moColor.getBlue()==0 &&
                         poParam.moColor.getRed()==0 &&
                         poParam.moColor.getGreen()==0) ? BaseColor.BLACK:new BaseColor(poParam.moColor))
                    );
            PdfPTable table = new PdfPTable(1);
            table.setTotalWidth((float)loRect.getWidth());
            table.setLockedWidth(true);
            table.setSpacingAfter(0);
            table.setSpacingBefore(0);

            PdfPCell cell;
            // row 1, cell 1
            cell = new PdfPCell();
            cell.setRotation(-(int) poParam.mdAngulo);
            cell.setBorder(0);
            cell.setIndent(0);
            cell.setPadding(0);
            cell.setBorderWidth(0);
            cell.setRight(0);
            cell.setTop(0);
            cell.setLeft(0);
            cell.setBottom(0);

//NO HACE FALTA            cell.setMinimumHeight((float) loRect.getHeight());
            if(poParam.mbMultilinea){
                cell.setFixedHeight((float) loRect.getHeight());
            }else{
                //@TODO OJO Como se calcula el tamaño de la fuente?
                cell.setFixedHeight((float) loRect.getHeight());
            }
//            cell.setNoWrap(!poParam.mbMultilinea); si se pone esto la linea no tiene fin

            cell.setVerticalAlignment(Element.ALIGN_TOP);
            Paragraph loFrase = new Paragraph(poParam.msTexto,loFont);
            switch(poParam.mlAlineacion){
                case ITextoLibre.mclAlineacionIzquierda:
                    //para la celda, es importante pq si no desaparecen textos
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    //para la frase, es el que realmente pone la alineacion
                    loFrase.setAlignment(Element.ALIGN_LEFT);
                    break;
                case ITextoLibre.mclAlineacionCentro:
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    loFrase.setAlignment(Element.ALIGN_CENTER);
                    break;
                case ITextoLibre.mclAlineacionDerecha:
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    loFrase.setAlignment(Element.ALIGN_RIGHT);
                    break;
                case ITextoLibre.mclAlineacionJustificada:
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    loFrase.setAlignment(Element.ALIGN_JUSTIFIED);
                    break;
            }
            cell.addElement(loFrase);
            table.addCell(cell);
//            System.out.println(poParam.msTexto + " = " + String.valueOf(cell.getWidth()));

            table.writeSelectedRows(0, -1, (float)loRect.getX(), (float)loRect.getY(), writer.getDirectContent());

//            NO IMPRIME EN LA POSICION ABSOLUTA
//            writer.getDirectContent().moveTo((float)loRect.getX(), (float)loRect.getY());
//            writer.getDirectContent().moveText((float)loRect.getX(), (float)loRect.getY());
//            //mientras table.getRowHeight(0)> height -> quitar texto
//            try {
//                document.add(table);
//
//            } catch (DocumentException ex) {
//                ex.printStackTrace();
//            }

        }
        return lbExito;
    }

    public void restaurarAreaImpresionPagina() {
        //establecemos el area de impresion original
        //no se tiene en cuenta la x y la y pq ya se lo hemos puesto
        //al formato de pagina
        moAreaImpresion = new Rectangle2D.Float();
        moAreaImpresion.setRect(
            moAreaImpresionOriginal.getX(),
            moAreaImpresionOriginal.getY(),
            moAreaImpresionOriginal.getWidth(),
            moAreaImpresionOriginal.getHeight()
            );
//        //establecemos el area de impresion en el objeto grafico
//        document.setPageSize(
//                new Rectangle(
//                    (int)mdConvertir(mdAnchoTotal),
//                    (int)mdConvertir(mdAltoTotal))
//            );
//        document.setMargins(
//                (int)mdConvertir(mdMargenIzq),
//                (int)mdConvertir(mdMargenDer),
//                (int)mdConvertir(mdMargenSup),
//                (int)mdConvertir(mdMargenInf)
//            );
    }
    public void setAreaImpresion(final java.awt.geom.Rectangle2D poR) {
        //convertimos de cm a 1/72 de pulgada
        double ldX = Math.max(mdConvertir(poR.getX()), moAreaImpresionOriginal.getX());
        double ldY = Math.max(mdConvertir(poR.getY()), moAreaImpresionOriginal.getY());
        double ldWidth = mdConvertir(poR.getWidth());
        double ldHeight = mdConvertir(poR.getHeight());

        //nos aseguramos de que no superen el area de impresion
        if((ldWidth + ldX) > (moAreaImpresionOriginal.getWidth() +  moAreaImpresionOriginal.getX())){
            ldWidth = moAreaImpresionOriginal.getWidth();
        }
        if((ldHeight + ldY) > (moAreaImpresionOriginal.getHeight() +  moAreaImpresionOriginal.getY())  ){
            ldHeight = moAreaImpresionOriginal.getHeight();
        }
        
        //establecemos el area de impresion
        moAreaImpresion = new Rectangle2D.Float();
        moAreaImpresion.setRect(ldX, ldY, ldWidth, ldHeight);
        
        //establecemos el area de impresion en el objeto grafico
//        document.setMargins(
//            (int)moAreaImpresion.getX(), (int)moAreaImpresion.getY(),
//            (int)moAreaImpresion.getWidth(), (int)moAreaImpresion.getHeight()
//            );
//         writer.setBoxSize("art",
//            new Rectangle(
//                (int)moAreaImpresion.getX()+(int)moAreaImpresion.getWidth(),
//                (int)moAreaImpresion.getY()+(int)moAreaImpresion.getHeight(),
//                (int)moAreaImpresion.getX(),
//                (int)moAreaImpresion.getY()
//            ));

    }
}
