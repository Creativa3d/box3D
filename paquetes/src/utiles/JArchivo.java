/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import utiles.red.*;

public class JArchivo {

    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    public static final String mcsSeparacion = System.getProperty("file.separator");
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static void guardarArchivo(String psDatos, File poOut) throws Exception {
        PrintStream out = new PrintStream(poOut);
        try {
            out.print(psDatos);
        } finally {
            out.close();
        }
    }
    public static void guardarArchivo(byte[] pabDatos, File poOut) throws Exception {
        FileOutputStream out = new FileOutputStream(poOut);
        try {
            out.write(pabDatos);
        } finally {
            out.close();
        }
    }

    public static void guardarArchivo(InputStream in, OutputStream out) throws Exception {
        try {
            byte[] b1 = new byte[2048];
            int lLen = -1;
            while ((lLen = in.read(b1)) != -1) {
                out.write(b1, 0, lLen);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    public static void guardarArchivo(File loFIn, File loFOut) throws Exception {
        InputStream in = new FileInputStream(loFIn);
        FileOutputStream out = new FileOutputStream(loFOut);
        guardarArchivo(in, out);
    }

    public static void guardarArchivo(URL loFIn, File loFOut) throws Exception {
        InputStream in = loFIn.openStream();
        FileOutputStream out = new FileOutputStream(loFOut);
        guardarArchivo(in, out);
    }

    public static boolean borrarDirectorio(File poDir, boolean pbRecursivo) {
        boolean lbBorrado = true;
        if (poDir.exists()) {
            String[] lasFiles = poDir.list();
            for (int i = 0; lasFiles!=null && i < lasFiles.length; i++) {
                File loRuta = new File(poDir, lasFiles[i]);
                if (loRuta.isDirectory()) {
                    if (pbRecursivo) {
                        borrarDirectorio(loRuta, pbRecursivo);
                    }
                } else {
                    loRuta.delete();
                }
            }
            lbBorrado = poDir.delete();
        }
        return lbBorrado;
    }

    public static byte[] getArchivoEnBytes(String psRuta) throws Exception {
        byte[] loImage = null;
        if (psRuta.toLowerCase().indexOf("http:") >= 0 || psRuta.toLowerCase().indexOf("https:") >= 0) {
            loImage = getArchivoEnBytesURL(psRuta);
        } else {
            loImage = getArchivoEnBytesFile(psRuta);
        }
        return loImage;
    }
    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }
    public static int copy(File input, File output) throws IOException {
        FileInputStream loIn = new FileInputStream(input);
        FileOutputStream loOut = new FileOutputStream(output);
        try{
            return copy(loIn, loOut);
        }finally{
            loOut.close();
            loIn.close();
        }
        
        
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    public static byte[] toByteArray(URLConnection urlConn) throws IOException {
        InputStream inputStream = urlConn.getInputStream();
        try {
            return toByteArray(inputStream);
        } finally {
            inputStream.close();
        }
    }    
    public static byte[] toByteArray(InputStream input, int size) throws IOException {

        if (size < 0) {
            throw new IllegalArgumentException("size Debe ser igual o mayor que cero: " + size);
        }

        if (size == 0) {
            return new byte[0];
        }

        byte[] data = new byte[size];
        int offset = 0;
        int readed;

        while (offset < size && (readed = input.read(data, offset, size - offset)) != -1) {
            offset += readed;
        }

        if (offset != size) {
            throw new IOException("Tamaño leido inesperado. actual: " + offset + ", esperado: " + size);
        }

        return data;
    }    
    public static byte[] getArchivoEnBytesURL(String psRuta) throws Exception {
        URL url = new URL(psRuta);
        URLConnection connection = msoOpenConnection.getConnection(url);
//        int contentLength = connection.getContentLength();
//        InputStream in = connection.getInputStream();
//        if (contentLength >0) {
//            return toByteArray(in, contentLength);
//        } else {
            return toByteArray(connection);
//        }
    }

    public static byte[] getArchivoEnBytesFile(String psRuta) throws Exception {
        byte[] loImage = null;

        File loFile = new File(psRuta);
        if (loFile.exists()) {
            FileInputStream loIn = new FileInputStream(loFile);
            try {
                loImage = toByteArray(loIn, (int)loFile.length());
            } finally {
                loIn.close();
            }
        } else {
            throw new Exception("Fichero no existe (" + psRuta + ")");
        }

        return loImage;
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toUpperCase();
        }
        return ext;
    }
        
}
