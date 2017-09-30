

package utilesEjecutar.fuentes.archivos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTPFile;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.JFuenteFTP;

/**Representa un archivo FTP*/
public class JArchivoFTP implements IArchivo {
    private FTPFile moFile;
    private String msRutaRelativa;
    private JDateEdu moDate;
    private long mlLen = -1;
    private String msNombre;
    private JControladorCoordinadorEjecutar moCoordinador;
    private JFuenteFTP moFuenteFTP;

    public JArchivoFTP(JFuenteFTP poFuenteFTP, FTPFile poFile, String psRutaRelativa){
        moFuenteFTP = poFuenteFTP;
        moFile = poFile;
        msRutaRelativa = psRutaRelativa;
        msNombre = poFile.getName();
        if(msRutaRelativa.startsWith("./")){
            msRutaRelativa = msRutaRelativa.substring(2);
        }
        if(msRutaRelativa.startsWith(".")){
            msRutaRelativa = msRutaRelativa.substring(1);
        }

    }
    public JArchivoFTP(JFuenteFTP poFuenteFTP, FTPFile poFile, String psRutaRelativa, JControladorCoordinadorEjecutar poCoord){
        this(poFuenteFTP, poFile, psRutaRelativa);
        moCoordinador = poCoord;
    }

    public void copiarFuenteA(String psRuta, boolean pbConBarra) throws Throwable {
        boolean lbContinuar = true;
        File loFileTMPFuente =
                new File(
                    new File(moCoordinador.getTmpFuentes(), getRutaRelativa()).getAbsolutePath(),
                    getNombre()
                );
        try{
            if(!loFileTMPFuente.exists()){

                //creamos los directorios previos
                moCoordinador.crearDirPadres(loFileTMPFuente);

                //copiamos el archivo
                InputStream loIn;
                if(msRutaRelativa==null||msRutaRelativa.equals("")||msRutaRelativa.equals(".")){
                    loIn = moFuenteFTP.getFTPClient().retrieveFileStream(moFile.getName());
                }else{
                    loIn = moFuenteFTP.getFTPClient().retrieveFileStream("./"+ msRutaRelativa + "/" +  moFile.getName());
                }
                if(loIn==null) {
                    lbContinuar=false;
                    JDepuracion.anadirTexto(
                            JDepuracion.mclWARNING,
                            getClass().getName(),
                            "Error->" +msRutaRelativa + "/" + msNombre + "->" + String.valueOf(moFuenteFTP.getFTPClient().getReplyCode()));
                }else{
                    FileOutputStream loOut = new FileOutputStream(loFileTMPFuente);
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
                        moFuenteFTP.getFTPClient().completePendingCommand();
                    }
                }
            }
        }catch(Throwable e){
            moCoordinador.addError(getClass().getName(),e);
            lbContinuar=false;
        }        
        if(lbContinuar){
            //copiamos el archivo descargadio en el temporal de las fuentes al destino
            JArchivoCarpetas loArchTMPFuente = new JArchivoCarpetas(loFileTMPFuente, null);
            loArchTMPFuente.copiarFuenteA(psRuta, pbConBarra);
        }

    }


    public JDateEdu getFechaModificacion() throws Throwable {
        if(moDate==null){
            moDate = new JDateEdu(moFile.getTimestamp().getTime());
        }
        return moDate;
    }

    public long getTamano() throws Throwable {
        if(mlLen<0){
            mlLen = moFile.getSize();
        }
        return mlLen;
    }

    public String getRutaRelativa() throws Throwable {
        return msRutaRelativa;
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

}
