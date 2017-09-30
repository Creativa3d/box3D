/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bidi;

import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class JBarCodeUTIL {
    public static final int mclCode128 = 0;
    public static final int mclEAN128 = 1;
    public static final int mclEAN128DIG8 = 2;
    public static final int mclDATAMATRIX=3;
    public static final int mclQR=4;
    public static final int mclPDF=5;
    
    
    public static final String mcsDATAMATRIX=String.valueOf(mclDATAMATRIX);
    public static final String mcsQR=String.valueOf(mclQR);
    public static final String mcsPDF=String.valueOf(mclPDF);


    
    public static Image getPDF417( String texto ) throws IOException {
        JBarCodeUTILConfig loConfig = new JBarCodeUTILConfig();

        //Create the barcode bean
        PDF417Bean bean = new PDF417Bean();
        
//        bean.setColumns(loConfig.getPDFConfigMaxCols());
        
//        bean.doQuietZone(true);
        bean.setMaxCols(loConfig.getPDFConfigMaxCols());
        bean.setMinCols(loConfig.getPDFConfigMinCols());
        bean.setMinRows(loConfig.getPDFConfigMinRows());
        bean.setMaxRows(loConfig.getPDFConfigMaxRows());
//
        bean.setErrorCorrectionLevel(loConfig.getPDFConfigErrorCorrectionLevel());
//no usar q es lo mismo q BarHeight pero restando un factor "desconocido"
//        bean.setHeight(loConfig.getPDFConfigHeight());
        bean.setBarHeight(loConfig.getPDFConfigBarHeight());

        bean.setModuleWidth(loConfig.getPDFConfigModuleWidth()); //makes the narrow bar
        bean.setWidthToHeightRatio(loConfig.getPDFConfigWidthToHeightRatio());
        
        final int dpi = loConfig.getPDFConfigdpi();
 
        //Set up the canvas provider for monochrome JPEG output
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_GRAY, false, 0);

        bean.calcDimensions(texto);
        //Generate the barcode
        bean.generateBarcode(canvas, texto.trim());
                
        //Signal end of generation
        canvas.finish();
        
//        ImageIO.write(canvas.getBufferedImage(), "png", new File("/tmp/out.png"));
        return canvas.getBufferedImage();

//        Image loImage = Toolkit.getDefaultToolkit().getImage("/home/eduardo/d/pdf.png");
//        MediaTracker loMedia = new MediaTracker(new JLabel());
//        loMedia.addImage(loImage, 1);
//        try {
//            loMedia.waitForAll();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        return loImage;
        
    }    
//    /**Devuelve un Cod. Barras en 128, el alto en cm*/
//    public static BufferedImage getCode128( String texto, double plAlto ) throws IOException {
//        return getCode128(texto, plAlto, false);
//    }
//    

    /**Devuelve un Cod. Barras en 128, el alto en cm*/
    public static BufferedImage getBarras( String texto, double plAlto, boolean pbConTitulo, int plTipo ) throws IOException {
        BufferedImage loImg=null;
        if(texto.trim().equals("")){
            loImg = new BufferedImage (1,1,BufferedImage.TYPE_INT_RGB);
            Graphics2D loGR = (Graphics2D)loImg.getGraphics();
            loGR.setColor(Color.white);
                    
                    
            loGR.drawRect(0, 0, 1, 1);
        }else{

            AbstractBarcodeBean bean;
            //Create the barcode bean
            switch(plTipo){
                case mclCode128:
                    bean= new Code128Bean();
                    break;
                case mclEAN128:
                    bean= new EAN128Bean();
                    ((EAN128Bean)bean).setTemplate("("+texto.substring(0, 2) +")an"+String.valueOf(texto.length()-2));
                    ((EAN128Bean)bean).setOmitBrackets(true);
                    break;
                case mclEAN128DIG8:
                    bean= new EAN128Bean();
                    ((EAN128Bean)bean).setTemplate("(99)n6");
                    ((EAN128Bean)bean).setOmitBrackets(true);
                    break;
                default:
                    throw new IOException("Opción incorrecta");
            }
             

            bean.setHeight(plAlto*10);
            bean.setBarHeight(plAlto*10);
            
            bean.setFontSize(9.0);
            
            if(!pbConTitulo){
                bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            }
            final int dpi = 40;

            //Configure the barcode generator
            bean.setModuleWidth(1.5); //makes the narrow bar
            //width exactly one pixel
    //        bean.setWideFactor(3);

            bean.doQuietZone(false);

            //Set up the canvas provider for monochrome JPEG output
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            //Generate the barcode
            
            bean.generateBarcode(canvas, texto.trim());
            

            //Signal end of generation
            canvas.finish();

            loImg = canvas.getBufferedImage();
        }
        return loImg;
        
    }
    
    /**Devuelve un Cod. Barras en 128, el alto en cm*/
    public static BufferedImage getCode128( String texto, double plAlto, boolean pbConTitulo ) throws IOException {
        return getBarras(texto, plAlto, pbConTitulo, mclCode128 );
    }
    
    /**Devuelve un Cod. Barras en EAN 128, el alto en cm*/
    public static BufferedImage getEAN128( String texto, double plAlto, boolean pbConTitulo ) throws IOException {
        return getBarras(texto, plAlto, pbConTitulo, mclEAN128 );
    }

    /**Devuelve un Cod. Barras en EAN 128 con 8 dígitos, el alto en cm*/
    public static BufferedImage getEAN128DIG8( String texto, double plAlto, boolean pbConTitulo ) throws IOException {
        return getBarras(texto, plAlto, pbConTitulo, mclEAN128DIG8 );
    }

    public static BufferedImage getCode128( String texto ) throws IOException {
        return getCode128(texto, 3.5, true);
    }
    public static BufferedImage getEAN128( String texto ) throws IOException {
        return getEAN128(texto, 3.5, true);
    }

    public static BufferedImage getEAN128DIG8( String texto ) throws IOException {
        return getEAN128DIG8(texto, 3.5, true);
    }
    public static BufferedImage getDATAMATRIX( String texto ) throws IOException {
        JBarCodeUTILConfig loConfig = new JBarCodeUTILConfig();
        //Create the barcode bean
        DataMatrixBean bean = new DataMatrixBean();
 
        //Set up the canvas provider for monochrome JPEG output
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(loConfig.getPDFConfigdpi(), BufferedImage.TYPE_BYTE_GRAY, false, 0);

//        bean.setShape(SymbolShapeHint.FORCE_Rectangulo);
        bean.setMinSize(new Dimension(24, 24));
        bean.setQuietZone(1);
        
        bean.calcDimensions(texto);
        //Generate the barcode
        bean.generateBarcode(canvas, texto.trim());
        
        //Signal end of generation
        canvas.finish();
        
//        ImageIO.write(canvas.getBufferedImage(), "png", new File("/tmp/out.png"));
        return canvas.getBufferedImage();
         
    }
    
    public static Image getQR(String psTexto, int plPixelAncho, int plPixelAlto){
        Map loMa = new HashMap();
        loMa.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        loMa.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
        BarcodeQRCode lo = new BarcodeQRCode(psTexto, plPixelAncho, plPixelAlto, loMa);
        return lo.createAwtImage(Color.black, Color.white);
    }
    
}


