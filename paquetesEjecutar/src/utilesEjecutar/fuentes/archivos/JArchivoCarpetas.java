
package utilesEjecutar.fuentes.archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import utiles.FechaMalException;
import utiles.JDateEdu;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**Representa un archivo local*/
public class JArchivoCarpetas implements IArchivo {
    private File moFile;
    private String msRutaRelativa;
    private JDateEdu moDate;
    private long mlLen = -1;
    private String msNombre;
    private JControladorCoordinadorEjecutar moCoordinador;

    public JArchivoCarpetas(File poFile, String psRutaRelativa){
        moFile = poFile;
        msRutaRelativa = JControladorCoordinadorEjecutar.getDirLocalconSeparadorCorrecto(psRutaRelativa);
        msNombre = poFile.getName();
    }
    public JArchivoCarpetas(File poFile, String psRutaRelativa, JControladorCoordinadorEjecutar poCoord){
        this(poFile, psRutaRelativa);
        moCoordinador = poCoord;
    }

    public void copiarFuenteA(String psRuta, boolean pbConBarra) throws Throwable {
        File loFile = new File(psRuta);
        //creamos los directorios previos
        moCoordinador.crearDirPadres(loFile);
        //copiamos el archivo
        FileInputStream loIn = new FileInputStream(moFile);
        FileOutputStream loOut = new FileOutputStream(loFile);
        try{
            byte[] buffer = new byte[255];
            int lLen = 0;
            while( (lLen = loIn.read(buffer)) != -1 ){
                loOut.write(buffer, 0 , lLen);
                if(pbConBarra && moCoordinador != null){
                    moCoordinador.addCompletadoGUI(lLen);
                }
            }
        }finally{
            loOut.close();
            loIn.close();
        }
        
    }


    public JDateEdu getFechaModificacion() throws Throwable {
        if(moDate==null){
            moDate = getFechaFichero(moFile.lastModified());
        }
        return moDate;
    }

    public long getTamano() throws Throwable {
        if(mlLen<0){
            mlLen = moFile.length();
        }
        return mlLen;
    }

    public String getRutaRelativa() throws Throwable {
        return msRutaRelativa;
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }
    public static JDateEdu getFechaFichero(long pdtime) throws FechaMalException{
        JDateEdu loDate = new JDateEdu("1/1/70 1:0:0");
        loDate.add(loDate.mclSegundos, (int)(pdtime/1000));
        return loDate;
    }
}
