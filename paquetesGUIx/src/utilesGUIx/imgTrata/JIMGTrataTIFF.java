/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.imgTrata;

import com.sun.media.imageio.plugins.tiff.BaselineTIFFTagSet;
import com.sun.media.imageio.plugins.tiff.TIFFTag;
import com.sun.media.imageioimpl.plugins.tiff.TIFFField;
import com.sun.media.imageioimpl.plugins.tiff.TIFFIFD;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageMetadata;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author eduardo
 */
public class JIMGTrataTIFF {
    public static void guardarImagenTIFF(BufferedImage image, String psCamino) throws Exception {
        guardarImagenTIFF(image, psCamino, "LZW");
    }
    public static void guardarImagenTIFF(BufferedImage image, String psCamino, String psCompresion) throws Exception {
        guardarImagenTIFF(image, psCamino, "LZW", 72);
    }
    public static void guardarImagenTIFF(BufferedImage image, String psCamino, String psCompresion, int ppp) throws Exception {
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

        File tiffFile = new File(psCamino);
        ImageOutputStream ios = null;
        ImageWriter writer = null;


// find an appropriate writer
        Iterator it = ImageIO.getImageWritersByFormatName("TIF");
        if (it.hasNext()) {
            writer = (ImageWriter) it.next();
        } else {
            throw new Exception("No existe el escritor de TIF");
        }

// setup writer
        ios = ImageIO.createImageOutputStream(tiffFile);
        writer.setOutput(ios);

        ImageWriteParam writeParam = writer.getDefaultWriteParam();

        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
// see writeParam.getCompressionTypes() for available compression type strings
        String[] las = writeParam.getCompressionTypes();
        for(int i = 0 ;i < las.length; i++){
            if(las[i].trim().equalsIgnoreCase(psCompresion.trim())){
                writeParam.setCompressionType(las[i]);
            }
        }
        writeParam.setCompressionQuality(1.0f);

// convert to an IIOImage
        IIOImage iioImage = new IIOImage(image, null, getMetadata(writer, image, writeParam, ppp));

// write it!
        writer.write(null, iioImage, writeParam);
    }
    private static IIOMetadata getIIOMetadata(BufferedImage image, ImageWriter imageWriter, ImageWriteParam param) {
        ImageTypeSpecifier spec = ImageTypeSpecifier.createFromRenderedImage(image);
        IIOMetadata metadata = imageWriter.getDefaultImageMetadata(spec, param);
        return metadata;
    }
    private static IIOMetadata getMetadata(ImageWriter imageWriter, BufferedImage image, ImageWriteParam param, int dpi) {
        TIFFImageMetadata tiffMetadata = (TIFFImageMetadata) getIIOMetadata(image, imageWriter, param);
        TIFFIFD rootIFD = tiffMetadata.getRootIFD();
        BaselineTIFFTagSet base = BaselineTIFFTagSet.getInstance();
//        char[] COMPRESSION = new char[]{BaselineTIFFTagSet.COMPRESSION_CCITT_T_4};

        TIFFTag tagResUnit = base.getTag(BaselineTIFFTagSet.TAG_RESOLUTION_UNIT);
        TIFFTag tagXRes = base.getTag(BaselineTIFFTagSet.TAG_X_RESOLUTION);
        TIFFTag tagYRes = base.getTag(BaselineTIFFTagSet.TAG_Y_RESOLUTION);
        TIFFTag tagBitSample = base.getTag(BaselineTIFFTagSet.TAG_BITS_PER_SAMPLE);
        TIFFTag tagRowStrips = base.getTag(BaselineTIFFTagSet.TAG_ROWS_PER_STRIP);
//        TIFFTag tagCompression = base.getTag(BaselineTIFFTagSet.TAG_COMPRESSION);

        TIFFField fieldResUnit = new TIFFField(tagResUnit, TIFFTag.TIFF_SHORT, 1, new char[]{2});
        TIFFField fieldXRes = new TIFFField(tagXRes, TIFFTag.TIFF_RATIONAL, 1, new long[][]{{dpi, 1}});
        TIFFField fieldYRes = new TIFFField(tagYRes, TIFFTag.TIFF_RATIONAL, 1, new long[][]{{dpi, 1}});
        TIFFField fieldBitSample = new TIFFField(tagBitSample, TIFFTag.TIFF_SHORT, 1, new char[]{1});
        TIFFField fieldRowStrips = new TIFFField(tagRowStrips, TIFFTag.TIFF_LONG, 1, new long[]{image.getHeight()});
//        TIFFField fieldCompression = new TIFFField(tagCompression, TIFFTag.TIFF_SHORT, 1, COMPRESSION);
        rootIFD.addTIFFField(fieldResUnit);
        rootIFD.addTIFFField(fieldXRes);
        rootIFD.addTIFFField(fieldYRes);
        rootIFD.addTIFFField(fieldBitSample);
        rootIFD.addTIFFField(fieldRowStrips);
//        rootIFD.addTIFFField(fieldCompression);

        return tiffMetadata;
    }
}
