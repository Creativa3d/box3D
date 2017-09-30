/*
 * JIMGTrata.java
 *
 * Created on 11 de noviembre de 2007, 14:37

 */
package utilesGUIx.imgTrata;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.imageio.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import utiles.JDepuracion;

public class JIMGTrata {

    private static JIMGTrata moTrata = null;

    /** Creates a new instance of JIMGTrata */
    private JIMGTrata() {
    }

    public static JIMGTrata getIMGTrata() {
        if (moTrata == null) {
            moTrata = new JIMGTrata();
        }
        return moTrata;
    }
    public ImageInfo getInfo(String psCamino) throws Exception{
        InputStream in ;
        if (psCamino.startsWith("http://")) {
            in = new URL(psCamino).openConnection().getInputStream();
        } else {
            in = new FileInputStream(psCamino);
        }
        return getInfo(in);
    }
    public ImageInfo getInfo(InputStream in ){
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setDetermineImageNumber(true);
        
        imageInfo.setInput(in);
        imageInfo.setDetermineImageNumber(false);
        imageInfo.setCollectComments(false);
        if (imageInfo.check()) {
            
        }
        return imageInfo;
        
    }
    public boolean imagenEsperar(final Image poImagen) throws Exception {
        final MediaTracker MTracker = new MediaTracker(new JLabel());
        MTracker.addImage(poImagen, 1);
        try {
            MTracker.waitForID(1);
        } catch (Exception IE) {
            JDepuracion.anadirTexto(JIMGTrata.class.getName(), IE);
        }
        return !MTracker.isErrorAny();
    }
    public ImageIcon getImagenCargada(final String psCamino) throws Exception {
        ImageIcon loImagenIco;
        File loFile = new File(psCamino);
        try{
            //usamos 1º el imaeio para evitar la cache de java
            BufferedImage loBI;
            if(loFile.exists()){
                loBI = ImageIO.read(loFile);
            }else{
                loBI = ImageIO.read(new URL(psCamino));
            }
            loImagenIco = new ImageIcon(loBI);
        } catch (Exception e) {
            if(loFile.exists()){
                loImagenIco = (new javax.swing.ImageIcon(psCamino));
            }else{
                try {
                    loImagenIco = (new javax.swing.ImageIcon(new URL(psCamino)));
                } catch (Exception e1) {
                    loImagenIco = (new javax.swing.ImageIcon(getClass().getResource(psCamino)));
                }
            }
            imagenEsperar(loImagenIco.getImage());
        }
        
        return loImagenIco;
    }

    public BufferedImage getImagen(final Image poImage) {
        return getImagenEscalada(poImage
                , poImage.getWidth(null), poImage.getHeight(null)
                , poImage.getWidth(null), poImage.getHeight(null));
    }
    
    public BufferedImage getImagenEscalada(final Image poImage, final int plAnchoO, final int plAltoO, final int plAnchoD, final int plAltoD) {
        double ldZoomW = (double) plAnchoD / (double) plAnchoO;
        double ldZoomH = (double) plAltoD / (double) plAltoO;
        double ldZoom = 1;
        if (ldZoomW > ldZoomH) {
            ldZoom = ldZoomH;
        } else {
            ldZoom = ldZoomW;
        }
        int lAncho = (int) (plAnchoO * ldZoom);
        int lAlto = (int) (plAltoO * ldZoom);

        //transformamos la imagen a bufferimage
        BufferedImage loBuffer = new BufferedImage(lAncho, lAlto, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = loBuffer.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(poImage, 0, 0, lAncho, lAlto, null);
        graphics2D.dispose();

        return loBuffer;
    }

    public void guardarImagenTIFF(BufferedImage image, String psCamino) throws Exception {
        guardarImagenTIFF(image, psCamino, "LZW");
    }
    public void guardarImagenTIFF(BufferedImage image, String psCamino, String psCompresion) throws Exception {
        guardarImagenTIFF(image, psCamino, "LZW", 72);
    }
    public void guardarImagenTIFF(BufferedImage image, String psCamino, String psCompresion, int ppp) throws Exception {
        JIMGTrataTIFF.guardarImagenTIFF(image, psCamino, psCompresion, ppp);
    }
    public BufferedImage getImage(int [] pixels, int w, int h){
//        int [] pixels = new int[w * h];
//        //creamos la image
//        MemoryImageSource loMemo = new MemoryImageSource(
//                w, h, poDocOrigen.moMemoryImage, 0, w);
//        Image loImage = Toolkit.getDefaultToolkit().createImage(loMemo);
//        //la pasamos a un bufferimage
//        int [] pixels = new int[w * h];
//        PixelGrabber pg = new PixelGrabber(loImage,0,0,w,h,pixels,0,w);
//        try {
//          pg.grabPixels();
//        } catch(InterruptedException ie) {
//          ie.printStackTrace();
//        }

        BufferedImage bimg = null;
        bimg = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        bimg.setRGB(0,0,w,h,pixels,0,w);

        return bimg;
    }
    /**Guarda un BufferedImagen como JPG en un archivo*/
    public void guardarImagenJPG(
            final BufferedImage poImagen, final String psCamino
            , final float pdCalidad) throws IOException {
        //TODO OJO falta la calidad ver TIFF
        //
        //creamos los directorios previos
        //
        File loFile = new File(psCamino);
        if(loFile.exists()){
            loFile.delete();
        }
        if(loFile.getParentFile()!=null){
            loFile.getParentFile().mkdirs();
        }

        //Asignamos la calidad con la que se va a guardar la imagen de 0-100
        //y se guarda
        ImageIO.write(poImagen, "jpg", loFile);
    }

    /**Guarda un BufferedImagen como JPG en un OutPutStream*/
    public void guardarImagenJPG(
            final BufferedImage poImagen, final OutputStream poOut
            , final float pdCalidad) throws IOException {
        //TODO OJO falta la calidad ver TIFF
        //Asignamos la calidad con la que se va a guardar la imagen de 0-100
        //y se guarda
        ImageIO.write(poImagen, "jpg", poOut);
    }

    /**Devuelve un BufferedImagen del ImageIcon pasado por parametro*/
    public BufferedImage getBufferedImage(final ImageIcon poImageIcon) throws Exception {
        int lAncho = (int) poImageIcon.getIconWidth();
        int lAlto = (int) poImageIcon.getIconHeight();

        //transformamos la imagen a bufferimage
        BufferedImage loBuffer = new BufferedImage(lAncho, lAlto, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = loBuffer.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(poImageIcon.getImage(), 0, 0, lAncho, lAlto, null);
        graphics2D.dispose();

        return loBuffer;
    }

    /**Guarda la imagen psOrigen en psDestino escalandola al nuevo tamaño*/
    public void escalarImagenYGuardar(final String psOrigen, final String psDestino, final int plWitdh, final int plHeight) throws Exception {
        //cargamos la imagen
        ImageIcon loImageIcon = getImagenCargada(psOrigen);

        //escalamos
        BufferedImage loBuffer = getImagenEscalada(loImageIcon.getImage(), loImageIcon.getIconWidth(), loImageIcon.getIconHeight(), plWitdh, plHeight);

        //guardamos la imagen
        guardarImagenJPG(loBuffer, psDestino, 80);
    }

    public void mostrarVistaPreliminar(final String psCamino, final String psDescrip) throws Exception {
        final JVISTAPRELIMINAR loVista = new JVISTAPRELIMINAR();
        loVista.setImagen(psCamino, psDescrip);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loVista.show();
            }
        });

    }
    public void mostrarVistaPreliminar(final Image poImage, final String psDescrip) throws Exception {
        final JVISTAPRELIMINAR loVista = new JVISTAPRELIMINAR();
        loVista.setImagen(poImage, psDescrip);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loVista.show();
            }
        });

    }
    
    /**Devuelve una imagen con el ancho-alto cortando la imagen original*/
    public BufferedImage cortar(BufferedImage originalImage, int plWidth, int plHeight) {
         BufferedImage result =
                 new BufferedImage(
                    plWidth,
                    plHeight,
                    originalImage.getType());
         Graphics2D g = (Graphics2D) result.getGraphics();
         g.drawImage(originalImage, 0, 0, plWidth, plHeight, 0 , 0, plWidth, plHeight, null);
         g.dispose();
         return result;
    }

    /**Devuelve la imagen pasada por parametro como imagen de 4 colores*/
    public BufferedImage transformarA4Colores(BufferedImage originalImage) {
         BufferedImage blackAndWhiteImage =
                 new BufferedImage(
                    originalImage.getWidth(null),
                    originalImage.getHeight(null),
                    BufferedImage.TYPE_BYTE_BINARY,
                    new IndexColorModel(2, 4,
                        new byte[]{(byte)0x00,(byte)0x44,(byte)0x88,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x44,(byte)0x88,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x44,(byte)0x88,(byte)0xff})
                    );
         Graphics2D g = (Graphics2D) blackAndWhiteImage.getGraphics();
         g.drawImage(originalImage, 0, 0, null);
         g.dispose();
         return blackAndWhiteImage;
    }
    /**Devuelve la imagen pasada por parametro como imagen de16 colores*/
    public BufferedImage transformarA16Colores(BufferedImage originalImage) {
         BufferedImage blackAndWhiteImage =
                 new BufferedImage(
                    originalImage.getWidth(null),
                    originalImage.getHeight(null),
                    BufferedImage.TYPE_BYTE_BINARY,
                    new IndexColorModel(4, 16,
                        new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55,(byte)0x66,(byte)0x77,(byte)0x88,(byte)0x99,(byte)0xaa,(byte)0xbb,(byte)0xcc,(byte)0xdd,(byte)0xee,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55,(byte)0x66,(byte)0x77,(byte)0x88,(byte)0x99,(byte)0xaa,(byte)0xbb,(byte)0xcc,(byte)0xdd,(byte)0xee,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55,(byte)0x66,(byte)0x77,(byte)0x88,(byte)0x99,(byte)0xaa,(byte)0xbb,(byte)0xcc,(byte)0xdd,(byte)0xee,(byte)0xff}
                        )
                    );
         Graphics2D g = (Graphics2D) blackAndWhiteImage.getGraphics();
         g.drawImage(originalImage, 0, 0, null);
         g.dispose();
         return blackAndWhiteImage;
    }
    public BufferedImage transformarA8Colores(BufferedImage originalImage) {
         BufferedImage blackAndWhiteImage =
                 new BufferedImage(
                    originalImage.getWidth(null),
                    originalImage.getHeight(null),
                    BufferedImage.TYPE_BYTE_BINARY,
                    new IndexColorModel(3, 8,
                        new byte[]{(byte)0x00,(byte)0x22,(byte)0x44,(byte)0x66,(byte)0x99,(byte)0xcc,(byte)0xee,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x22,(byte)0x44,(byte)0x66,(byte)0x99,(byte)0xcc,(byte)0xee,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x22,(byte)0x44,(byte)0x66,(byte)0x99,(byte)0xcc,(byte)0xee,(byte)0xff}
                        )
                    );
         Graphics2D g = (Graphics2D) blackAndWhiteImage.getGraphics();
         g.drawImage(originalImage, 0, 0, null);
         g.dispose();
         return blackAndWhiteImage;
    }
    public BufferedImage transformarA3Colores(BufferedImage originalImage) {
         BufferedImage blackAndWhiteImage =
                 new BufferedImage(
                    originalImage.getWidth(null),
                    originalImage.getHeight(null),
                    BufferedImage.TYPE_BYTE_BINARY,
                    new IndexColorModel(2, 3,
                        new byte[]{(byte)0x00,(byte)0x88,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x88,(byte)0xff},
                        new byte[]{(byte)0x00,(byte)0x88,(byte)0xff})
                    );
         Graphics2D g = (Graphics2D) blackAndWhiteImage.getGraphics();
         g.drawImage(originalImage, 0, 0, null);
         g.dispose();
         return blackAndWhiteImage;
    }
    public BufferedImage transformarABlancoYNegro(BufferedImage originalImage) {
         BufferedImage blackAndWhiteImage =
                 new BufferedImage(
                    originalImage.getWidth(null),
                    originalImage.getHeight(null),
                    BufferedImage.TYPE_BYTE_BINARY
                    );
         Graphics2D g = (Graphics2D) blackAndWhiteImage.getGraphics();
         g.drawImage(originalImage, 0, 0, null);
         g.dispose();
         return blackAndWhiteImage;
    }
    public BufferedImage transformarABlancoYNegro2(BufferedImage inputImage) {

// Create a binary image for the results of processing

        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_BINARY);

// Work on a copy of input image because it is modified by diffusion

        WritableRaster input = inputImage.copyData(null);
        WritableRaster output = outputImage.getRaster();

        final int threshold = 128;
        float value, error;

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {

                value = input.getSample(x, y, 0);

// Threshold value and compute error

                if (value < threshold) {
                    output.setSample(x, y, 0, 0);
                    error = value;
                } else {
                    output.setSample(x, y, 0, 1);
                    error = value - 255;
                }

// Spread error amongst neighbouring pixels

                if ((x > 0) && (y > 0) && (x < (w - 1)) && (y < (h - 1))) {
                    value = input.getSample(x + 1, y, 0);
                    input.setSample(x + 1, y, 0, clamp(value + 0.4375f * error));
                    value = input.getSample(x - 1, y + 1, 0);
                    input.setSample(x - 1, y + 1, 0, clamp(value + 0.1875f * error));
                    value = input.getSample(x, y + 1, 0);
                    input.setSample(x, y + 1, 0, clamp(value + 0.3125f * error));
                    value = input.getSample(x + 1, y + 1, 0);
                    input.setSample(x + 1, y + 1, 0, clamp(value + 0.0625f * error));
                }

            }
        }
        return outputImage;

    }

// Forces a value to a 0-255 integer range
    public int clamp(float value) {
        return Math.min(Math.max(Math.round(value), 0), 255);
    }

    public static void main(String[] args) throws Exception {
        File loFile = new File(".");
        File[] loFiles = loFile.listFiles();
        for(int i = 0 ; i < loFiles.length; i++){
            loFile = loFiles[i];
            if(loFile.getAbsolutePath().substring(loFile.getAbsolutePath().length()-3).equalsIgnoreCase("jpg")){
                ImageIcon loIcon =  getIMGTrata().getImagenCargada(loFile.getAbsolutePath());
                BufferedImage loIm = getIMGTrata().getBufferedImage(loIcon);
//                loIm = getIMGTrata().cortar(loIm, loIcon.getIconWidth() * 73 /100 , loIcon.getIconHeight() * 73 /100);
                getIMGTrata().guardarImagenTIFF(
                    getIMGTrata().transformarA4Colores(loIm),
                    loFile.getAbsolutePath().substring(0, loFile.getAbsolutePath().length()-3)+"TIF"
                    , "LZW"
                    , 200);
            }
        }
    }
}
