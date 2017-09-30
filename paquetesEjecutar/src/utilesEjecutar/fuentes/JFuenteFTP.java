package utilesEjecutar.fuentes;

import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.archivos.JArchivoFTP;

/**La fuente es un servidor FTP*/
public class JFuenteFTP implements IFuente {

    private static final String mcsNombre = "nombre";
    private static final String mcsCodeBase = "codebase";
    private static final String mcsPassword = "Password";
    private static final String mcsUsuario = "Usuario";
    private static final String mcsDirectorio  = "directorio";

    private String msCodeBase;
    private String msUsuario;
    private String msPassword;
    private String msNombre;
    private JControladorCoordinadorEjecutar moCoordinador;
    private IListaElementos moLista;
    private FTPClient moFTPClient;
    private String msDirectorio;

    public JFuenteFTP(JControladorCoordinadorEjecutar poCoordinador) {
        moCoordinador = poCoordinador;
    }
    public void setLista(IListaElementos poLista){
        moLista = poLista;
    }

    public FTPClient getFTPClient() throws Throwable {
        if(moFTPClient == null || !moFTPClient.isConnected()) {
            moFTPClient = new FTPClient();
            moFTPClient.connect(msCodeBase);
            moFTPClient.login(msUsuario, msPassword);
            JDepuracion.anadirTexto(
                    JDepuracion.mclINFORMACION,
                    getClass().getName(),
                    moFTPClient.getReplyString());
            if(msDirectorio!=null && !msDirectorio.equals("")){
                moFTPClient.changeWorkingDirectory(msDirectorio);
                JDepuracion.anadirTexto(
                        JDepuracion.mclINFORMACION,
                        getClass().getName(),
                        "Cambiado al directorio " + msDirectorio);
            }
             moFTPClient.setFileType(moFTPClient.BINARY_FILE_TYPE);

        }
        return moFTPClient;

    }

    public IListaElementos getLista() throws Throwable {
        if (moLista == null) {
            moLista = new JListaElementos();
            recorrer(getFTPClient().listFiles(), ".");
        }

        return moLista;

    }

    private void recorrer(FTPFile[] loFiles, String psRutaRelativa) throws Throwable {
        for (int i = 0; i < loFiles.length; i++) {
            FTPFile loAux = loFiles[i];
            if (loAux.isDirectory()) {
                FTPFile[] loFilesAux = getFTPClient().listFiles(psRutaRelativa +"/"+ loAux.getName());
                recorrer(loFilesAux, psRutaRelativa + "/" + loAux.getName());
            } else {
                moLista.add(
                        new JArchivoFTP(this, loAux, psRutaRelativa, moCoordinador));
            }
        }
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for (int i = 0; i < poLista.size(); i++) {
            JAtributo loAtrib = poLista.getAtributo(i);
            if (loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBase)) {
                msCodeBase = loAtrib.getValor();
            }
            if (loAtrib.getName().trim().equalsIgnoreCase(mcsUsuario)) {
                msUsuario = loAtrib.getValor();
            }
            if (loAtrib.getName().trim().equalsIgnoreCase(mcsPassword)) {
                msPassword = loAtrib.getValor();
            }
            if (loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)) {
                msNombre = loAtrib.getValor();
            }
            if (loAtrib.getName().trim().equalsIgnoreCase(mcsDirectorio)) {
                msDirectorio = loAtrib.getValor();
            }
        }
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public void desconectar() throws Throwable {
        if(moFTPClient!=null){
            moFTPClient.logout();
            moFTPClient.disconnect();
        }
    }
}
