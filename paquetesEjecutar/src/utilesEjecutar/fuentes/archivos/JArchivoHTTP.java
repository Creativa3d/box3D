

package utilesEjecutar.fuentes.archivos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import utiles.JDateEdu;
import utiles.red.*;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**Representa un archivo HTTP*/
public class JArchivoHTTP  implements IArchivo {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();

    private String msCodeBase;
    private String msFichero;
    private URL moURL;
    private String msNombre;
    private String msRutaRelativa;
    private long mlLen=-1;
    private boolean mbCalculado=false;
    private JDateEdu moDate;

    private Thread moThread;
    private JControladorCoordinadorEjecutar moCoordinador;

    public JArchivoHTTP(JControladorCoordinadorEjecutar poCoordinador, String psCodeBase, String psFichero, JDateEdu poDate, long plLen) throws Throwable {
        moCoordinador = poCoordinador;
        msCodeBase = psCodeBase;
        msFichero = psFichero;
        moURL = new URL(msCodeBase + msFichero.replace(" ", "%20"));
        moDate = poDate;
        mlLen = plLen;
        mbCalculado = true;
        caminosRelativos();
    }
    public JArchivoHTTP(JControladorCoordinadorEjecutar poCoordinador, String psCodeBase, String psFichero) throws Throwable {
        this(poCoordinador, psCodeBase, psFichero, false);
    }
    public JArchivoHTTP(JControladorCoordinadorEjecutar poCoordinador, String psCodeBase, String psFichero, boolean pbCargaSegundoPlano) throws Throwable {
        moCoordinador = poCoordinador;
        msCodeBase = psCodeBase;
        msFichero = psFichero;
        moURL = new URL(msCodeBase + msFichero.replace(" ", "%20"));
        caminosRelativos();

        if(pbCargaSegundoPlano){
            moThread= new Thread(new Runnable() {
                public void run() {
                    try{
                        calcular();
                    }catch(Throwable e){
                        moCoordinador.addError(getClass().getName(), e);
                    }
                }
            });
            moThread.start();
        }else{
            calcular();
        }
        
    }

    private void caminosRelativos(){
        //camino relativo
        msNombre = JControladorCoordinadorEjecutar.getNombreFichero(msFichero);
        msRutaRelativa = JControladorCoordinadorEjecutar.getRutaRelativaFichero(msFichero);
        if(msRutaRelativa==null){
            msRutaRelativa = "";
        }

    }

    public void copiarFuenteA(String psRuta, boolean pbConBarra) throws Throwable {
        calcular();
        boolean lbContinuar = true;
        File loFileTMPFuente =
                new File(
                    new File(moCoordinador.getTmpFuentes(), getRutaRelativa()).getAbsolutePath(),
                    getNombre()
                );
        if(!loFileTMPFuente.exists()){
            //creamos los directorios previos
            JControladorCoordinadorEjecutar.crearDirPadres(loFileTMPFuente);

            try{
                //copiamos el archivo
                InputStream loIn;
                loIn = msoOpenConnection.getConnection(moURL).getInputStream();
    //            loIn = moURL.openConnection(ProxyConfig.getProxy()).getInputStream();
                if(loIn==null) {
                    lbContinuar=false;
                    moCoordinador.addError(
                            getClass().getName(),
                            new Throwable("Error->" +moURL.toString())
                            );
                }else{
                    //copiamos el archivo URL al direc. temporal de las fuentes
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
                    }
                }
            }catch(Throwable e){
                moCoordinador.addError(getClass().getName(),e);
                lbContinuar=false;
            }
        }
        if(lbContinuar){
            //copiamos el archivo descargadio en el temporal de las fuentes al destino
            JArchivoCarpetas loArchTMPFuente = new JArchivoCarpetas(loFileTMPFuente, null);
            loArchTMPFuente.copiarFuenteA(psRuta, pbConBarra);
        }
    }


    private synchronized void calcular() throws Throwable{
        if(!mbCalculado){
            mbCalculado = true;
            //abrimos conexion
            URLConnection loconnec = msoOpenConnection.getConnection(moURL);
            //fecha
            moDate = JArchivoCarpetas.getFechaFichero(loconnec.getLastModified());
            if(moDate.compareTo(new JDateEdu("1/1/70 1:0:0"))==0){
                throw new Throwable("Error de archivo http " + moURL.toString());
            }
            //tamaño
             try{
                 mlLen = loconnec.getContentLength();
             }catch(Throwable e){
                 mlLen=0;
             }


        }
    }

    public JDateEdu getFechaModificacion() throws Throwable {
        calcular();
        return moDate;
    }

    public long getTamano() throws Throwable {
        calcular();
        return mlLen;
    }

    public String getRutaRelativa() throws Throwable {
        return msRutaRelativa;
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

}
