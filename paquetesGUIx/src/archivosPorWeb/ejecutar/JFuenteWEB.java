package archivosPorWeb.ejecutar;

import archivosPorWeb.comun.JFichero;
import archivosPorWeb.comun.JServidorArchivosWEBLogin;
import archivosPorWeb.comun.JServidorArchivosWeb;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.IFuente;

/**La fuente es un servidor WEB*/
public class JFuenteWEB implements IFuente {

    protected static final String mcsNombre = "nombre";
    protected static final String mcsCodeBase = "codebase";
    protected static final String mcsPassword = "Password";
    protected static final String mcsUsuario = "Usuario";
    protected static final String mcsDirectorio  = "directorio";

    protected String msCodeBase;
    protected String msUsuario;
    protected String msPassword;
    protected String msNombre;
    protected JControladorCoordinadorEjecutar moCoordinador;
    protected IListaElementos moLista;
    protected JServidorArchivosWeb moServidorArchivosWeb;
    protected JFichero moDirectorio = new JFichero("", "", true, 0, null);

    public JFuenteWEB(JControladorCoordinadorEjecutar poCoordinador) {
        moCoordinador = poCoordinador;
    }
    public void setLista(IListaElementos poLista){
        moLista = poLista;
    }

    public JServidorArchivosWeb getServidorArchivosWeb() throws Throwable {
        if(moServidorArchivosWeb == null || moServidorArchivosWeb.getIDSession()==null) {
            moServidorArchivosWeb = new JServidorArchivosWeb(msCodeBase);
            JServidorArchivosWEBLogin loLogin = new JServidorArchivosWEBLogin(msUsuario, msPassword);
            loLogin.setServidorInternet(moServidorArchivosWeb);
            if(!loLogin.autentificar()){
                throw new Exception("Error en la autentificación");
            }else{
                try{
                    moServidorArchivosWeb.setEntradaComprimida();
                }catch(Throwable e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }
            }

        }
        return moServidorArchivosWeb;

    }

    public IListaElementos getLista() throws Throwable {
        if (moLista == null) {
            moLista = new JListaElementos();
            recorrer(getServidorArchivosWeb().getListaFicheros(moDirectorio), ".");
        }

        return moLista;

    }

    private void recorrer(IListaElementos loFiles, String psRutaRelativa) throws Throwable {
        for (int i = 0; i < loFiles.size(); i++) {
            JFichero loAux = (JFichero) loFiles.get(i);
            if (loAux.getEsDirectorio()) {
                IListaElementos loFilesAux = getServidorArchivosWeb().getListaFicheros(loAux);
                recorrer(loFilesAux, psRutaRelativa + "/" + loAux.getNombre());
            } else {
                moLista.add(
                        new JArchivoWEB(this, loAux, psRutaRelativa, moCoordinador)
                        );
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
                moDirectorio = new JFichero(loAtrib.getValor(), null, true, 0, null);
            }
        }
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public void desconectar() throws Throwable {
        moServidorArchivosWeb=null;
    }
}
