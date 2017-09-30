

package archivosPorWeb.ejecutar;

import archivosPorWeb.comun.JFichero;
import archivosPorWeb.comun.JServidorArchivos;
import java.io.File;
import utiles.JDateEdu;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.archivos.IArchivo;
import utilesEjecutar.fuentes.archivos.JArchivoCarpetas;

/**Representa un archivo WEB*/
public class JArchivoWEB implements IArchivo {
    private JFichero moFile;
    private String msRutaRelativa;
    private JDateEdu moDate;
    private long mlLen = -1;
    private String msNombre;
    private JControladorCoordinadorEjecutar moCoordinador;
    private JFuenteWEB moFuenteWEB;

    public JArchivoWEB(JFuenteWEB  poFuenteWEB, JFichero poFile, String psRutaRelativa){
        moFuenteWEB = poFuenteWEB;
        moFile = poFile;
        msRutaRelativa = psRutaRelativa;
        msNombre = poFile.getNombre();
        if(msRutaRelativa.startsWith("./")){
            msRutaRelativa = msRutaRelativa.substring(2);
        }
        if(msRutaRelativa.startsWith(".")){
            msRutaRelativa = msRutaRelativa.substring(1);
        }
        mlLen=poFile.getLenght();

    }
    public JArchivoWEB(JFuenteWEB poFuenteWEB, JFichero poFile, String psRutaRelativa, JControladorCoordinadorEjecutar poCoord){
        this(poFuenteWEB, poFile, psRutaRelativa);
        moCoordinador = poCoord;
    }

    public void copiarFuenteA(String psRuta, boolean pbConBarra) throws Throwable {
        boolean lbContinuar = true;
        File loFileTMPFuente =
                new File(
                    new File(moCoordinador.getTmpFuentes(), getRutaRelativa()).getAbsolutePath(),
                    getNombre()
                );
        if(!loFileTMPFuente.exists()){

            //creamos los directorios previos
            moCoordinador.crearDirPadres(loFileTMPFuente);
            
            //creamos servidor archivos local
            JServidorArchivos loArchivosLocal =new JServidorArchivos();

            //copiamos el archivo de la web al temporal
            loArchivosLocal.copiarFichero(
                    moFuenteWEB.getServidorArchivosWeb(),
                    moFile,
                    new JFichero(
                        loFileTMPFuente.getParent(),
                        loFileTMPFuente.getName(),
                        false, 0, null
                        ));

        }
        if(lbContinuar){
            //copiamos el archivo descargadio en el temporal de las fuentes al destino
            JArchivoCarpetas loArchTMPFuente = new JArchivoCarpetas(loFileTMPFuente, null, moCoordinador);
            loArchTMPFuente.copiarFuenteA(psRuta, pbConBarra);
        }

    }


    public JDateEdu getFechaModificacion() throws Throwable {
        if(moDate==null){
            moDate = moFile.getDateEdu();
        }
        return moDate;
    }

    public long getTamano() throws Throwable {
        if(mlLen<0){
            mlLen = moFile.getLenght();
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
