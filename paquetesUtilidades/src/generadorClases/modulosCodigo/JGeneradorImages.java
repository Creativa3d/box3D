/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import utiles.JDepuracion;

public class JGeneradorImages implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades

    public JGeneradorImages(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        byte[] buffer = new byte[1024];
        try {
        
            InputStream loIn = this.getClass().getResourceAsStream("/generadorClases/images.zip");


            //get the zip file content
            ZipInputStream zis = 
                    new ZipInputStream(loIn);
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            File loAux = new File(moConex.getRutaProyectoClasesRaiz() ,  getRutaRelativa());
            if(!loAux.exists()) {
                loAux.mkdirs();
            }

            while(ze!=null){

                FileOutputStream fos = null;
                String fileName = ze.getName();
                File newFile = new File(loAux, fileName);

                
                new File(newFile.getParent()).mkdirs();
                fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }   fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(this.getClass().getName(), ex);
        } finally {
        }

        return null;
    }

    public String getRutaRelativa() {
        return "images";
    }

    public String getNombre() {
        return "images";
    }

    public boolean isGeneral() {
        return true;
    }

    public String getNombreModulo() {
        return getNombre();
    }

    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }
}
