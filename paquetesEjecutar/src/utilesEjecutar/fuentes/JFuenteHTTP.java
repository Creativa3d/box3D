
package utilesEjecutar.fuentes;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPOutputStream;
import utiles.CadenaLarga;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.red.*;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.SAXBuilder;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesBD.servletAcciones.AListaPropiedadesHTTP;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.archivos.JArchivoHTTP;


/**La fuente es un servidor HTTP*/
public class JFuenteHTTP implements IFuente {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    private static final String mcsNombre = "nombre";
    private static final String mcsCodeBase = "codebase";
    private static final String mcsCodeBaseSubDirec = "subdirectorio";
    private static final String mcsPassword = "Password";
    private static final String mcsUsuario = "Usuario";
    private static final String mcslistaFicheros = "listaFicheros";
    private static final String mcsresources="resources";
    private static final String mcsjar="jar";
    private static final String mcshref="href";
    private static final String mcsArchivo = "archivo";
    private static final String mcs2Plano="segundoPlano";

    private String msCodeBase;
    private String msCodeBaseSubDirec;
    private String msUsuario;
    private String msPassword;
    private String msNombre;
    private String msListaFicheros;
    private boolean mb2Plano=false;

    private JControladorCoordinadorEjecutar moCoordinador;
    
    private IListaElementos moLista;


    public JFuenteHTTP(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }
    public void setLista(IListaElementos poLista){
        moLista = poLista;
    }
    private static void rellenatListaArchivosComu(
            JControladorCoordinadorEjecutar poCoordinador,
            String psCodeBase,
            String psCodeBaseSubDirec,
            IListaElementos poHijos,
            IListaElementos poComu) throws Exception {
        for(int i = 0 ; i < poHijos.size() && (poCoordinador==null || !poCoordinador.isCancelado()); i++){
            Element loAux = (Element) poHijos.get(i);
            if(loAux.getName().equalsIgnoreCase(mcsArchivo)){
                poComu.add(loAux.getAttribute(mcshref).getValue());
            }
            //formato jar
            if(loAux.getName().equalsIgnoreCase(mcsjar)){
                JAtributo loAtrib = loAux.getAttribute(mcshref);
                if(loAtrib!=null){
                    poComu.add(loAtrib.getValue());
                }
            }
            //formato jnlp
            if(loAux.getName().equalsIgnoreCase(mcsresources)){
                rellenatListaArchivosComu(
                            poCoordinador,
                            psCodeBase,
                            psCodeBaseSubDirec,
                            loAux.getChildren(),
                            poComu);
            }

        }
    }
    /**
     * Devuelve la lista de archivos, como tiene q cargar propiedades via web se lanza
     * el cargador de propiedades de cada archivo al mismo tiempo (2º plano), opcionalmente puedes
     * hacer q el ultimo no se carge en 2º plano, por si no hay conexion el casque
     */
    public static IListaElementos getListaPorXml(
            JControladorCoordinadorEjecutar poCoordinador,
            String psCodeBase,
            IListaElementos poHijos,
            boolean pb2Plano) throws Throwable{
        return getListaPorXml(poCoordinador, psCodeBase, null, poHijos, pb2Plano);
    }
    /**
     * Devuelve la lista de archivos, como tiene q cargar propiedades via web se lanza
     * el cargador de propiedades de cada archivo al mismo tiempo (2º plano), opcionalmente puedes
     * hacer q el ultimo no se carge en 2º plano, por si no hay conexion el casque
     */
    public static IListaElementos getListaPorXml(
            JControladorCoordinadorEjecutar poCoordinador,
            String psCodeBase,
            String psCodeBaseSubDirectorio,
            IListaElementos poHijos,
            boolean pb2Plano) throws Throwable{

        IListaElementos loLista = new JListaElementos();
        IListaElementos loResult = null;
        try{
            IListaElementos loComu = new JListaElementos();
            if(psCodeBaseSubDirectorio!=null && !psCodeBaseSubDirectorio.equals("")){
                loComu.add("subdirectorio="+psCodeBaseSubDirectorio);
            }
            //primero intentamos traer todas las propiuedades de los ficheros de golpe
            rellenatListaArchivosComu(poCoordinador, psCodeBase, psCodeBaseSubDirectorio, poHijos, loComu);
            JServidorConexion loServer = new JServidorConexion(psCodeBase);
//            URLConnection loCon = loServer.enviarObjeto(AListaPropiedadesHTTP.mcslistaFicherosPropiedadesServlet, loComu);
            URLConnection loCon = loServer.enviarObjeto(AListaPropiedadesHTTP.mcslistaFicherosPropiedadesServlet, loComu);
            loResult = loServer.recibirObjeto(loCon);
        }catch(Throwable e){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING,JFuenteHTTP.class.getName(), "Fuente: " + psCodeBase + "/" + psCodeBaseSubDirectorio +  " Error al leer listaFicherosPropiedades.ctrl ");
            JDepuracion.anadirTexto(JFuenteHTTP.class.getName(), e);
            loResult = null;
        }
        if(psCodeBaseSubDirectorio!=null && !psCodeBaseSubDirectorio.equals("")){
            psCodeBase=psCodeBase+psCodeBaseSubDirectorio;
        }
        //si bien, hemos traido todas las propiedades de golpe y eso es muyyy bueno
        if(loResult!=null && loResult.size()>0){
            for(int i = 0 ; i < loResult.size(); i++){
                String[] lasFila = (String[]) loResult.get(i);
                    JArchivoHTTP loArchi =
                    new JArchivoHTTP(
                            poCoordinador,
                            psCodeBase,
                            lasFila[0],
                            new  JDateEdu(lasFila[1]),
                            new Integer(lasFila[2]).intValue()
                            );
                    loLista.add(loArchi);
            }

        }else{
            for(int i = 0 ; i < poHijos.size() && (poCoordinador==null || !poCoordinador.isCancelado()); i++){
                Element loAux = (Element) poHijos.get(i);
                if(loAux.getName().equalsIgnoreCase(mcsArchivo)){
                    JArchivoHTTP loArchi =
                            new JArchivoHTTP(
                                    poCoordinador,
                                    psCodeBase,
                                    loAux.getAttribute(mcshref).getValue(),
                                    pb2Plano);
                    loLista.add(loArchi);
                }
                //formato jar
                if(loAux.getName().equalsIgnoreCase(mcsjar)){
                    JAtributo loAtrib = loAux.getAttribute(mcshref);
                    if(loAtrib!=null){
                        JArchivoHTTP loArchi =
                                new JArchivoHTTP(
                                    poCoordinador,
                                    psCodeBase,
                                    loAtrib.getValue(),
                                    pb2Plano);
                        loLista.add(loArchi);
                    }
                }
                //formato jnlp
                if(loAux.getName().equalsIgnoreCase(mcsresources)){
                    IListaElementos loListaAux =
                            getListaPorXml(
                                poCoordinador,
                                psCodeBase,
                                null,
                                loAux.getChildren(),
                                pb2Plano);
                    for(int lI = 0 ; lI < loListaAux.size(); lI++){
                        loLista.add(loListaAux.get(lI));
                    }
                }

            }
        }
        return loLista;
    }

    public IListaElementos getLista() throws Throwable {
        if(moLista==null){
            URL loURL;
            if(msCodeBaseSubDirec!=null && !msCodeBaseSubDirec.equals("")){
                loURL = new URL(msCodeBase +msCodeBaseSubDirec+ msListaFicheros);
            }else{
                loURL = new URL(msCodeBase + msListaFicheros);
            }
            URLConnection loConec = msoOpenConnection.getConnection(loURL);
            
            InputStream loIn = loConec.getInputStream();
            StringBuffer lasBuffer = new StringBuffer();
            try{
                char[] lac = new char[2000];
                int lLen = -1;
                BufferedReader in = 
                  (new BufferedReader(
                      new InputStreamReader(
                        loIn)));
                while((lLen = in.read(lac))!=-1){
                    lasBuffer.append(lac, 0, lLen);
                }
            }finally{
                loIn.close();
            }
            SAXBuilder loSax = new SAXBuilder();
            Document loD = loSax.build(
                    new CadenaLarga(lasBuffer.toString())
                    );
            Element loELem = loD.getRootElement();
            try{
                moLista = getListaPorXml(
                            moCoordinador,
                            msCodeBase,
                            msCodeBaseSubDirec,
                            loELem.getChildren(),
                            mb2Plano);
            }catch(Throwable e){
                moLista = new JListaElementos();
                throw e;
            }
        }
        return moLista;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBase)){
                msCodeBase = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBaseSubDirec)){
                msCodeBaseSubDirec = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsUsuario)){
                msUsuario = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsPassword)){
                msPassword = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcslistaFicheros)){
                msListaFicheros = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcs2Plano)){
                mb2Plano = loAtrib.getValor().equals("1");
            }

        }
    }
    public String getNombre() throws Throwable {
        return msNombre;
    }

    public void desconectar() throws Throwable {
        
    }

}
class JServidorConexion {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();

    /**url base*/
    private String msURLBase1;

    //id de sesion
    private String msJsessionid = null;


    /** Creates a new instance of ServidorDatos */
    public JServidorConexion() {
        super();
    }

    /**
     * Crea una instancia de servidor de datos con una URL fija
     * @param psURLBase url base
     * @param psNombreSelect nombre servicio select
     * @param psNombreGuardar nombre servicio guardar
     */
    public JServidorConexion(final String psURLBase) {
        this();
        msURLBase1=psURLBase;
    }
    /**
     * establece la url base
     * @param psURLBase url
     */
    public void setURLBase1(final String psURLBase){
      msURLBase1 = psURLBase;
    }
    /**
     * devuelve la url base
     * @return url
     */
    public String getURLBase1(){
        return msURLBase1;
    }
    /**
     * Devuelve el id de session
     */
    public String getIDSession(){
        return msJsessionid;
    }
    /**
     * establece el id de session
     * @param psIdSession id de session
     */
    public void setIDSession(final String psIdSession){
      msJsessionid = psIdSession;
    }

    public URLConnection enviarObjeto(final String psNombreServlet,final IListaElementos poActualizar) throws Exception{
        //conectamos con url
        URLConnection connection=null;
        URL url=null;
        if(msJsessionid==null){
            url = new URL(msURLBase1 + psNombreServlet);
        }else{
            url = new URL(msURLBase1 + psNombreServlet + ";jsessionid=" + msJsessionid);
        }
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes abrir conexión");
        connection = msoOpenConnection.getConnection(url);
        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        //Pasamos a flujo de datos del objeto
        ByteArrayOutputStream bs = null;
        ObjectOutputStream salida = null;
        GZIPOutputStream gzipout = null;
        try{
            bs = new ByteArrayOutputStream(512);
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes crear objeto del flujo");
            salida = new ObjectOutputStream(bs);
            connection.setRequestProperty("Content-Type",
                                          "application/x-java-serialized-object");
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar objeto al flujo");
            salida.writeObject(poActualizar);
            salida.flush(); salida.close(); salida=null;
            if(gzipout != null) {
                gzipout.flush(); gzipout.close(); gzipout=null;
            }
            connection.setRequestProperty("Content-Lenght", String.valueOf(bs.size()));
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar el flujo a la conexion");
            bs.writeTo(connection.getOutputStream());
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Despues mandar el flujo a la conexion");
        }catch(Exception e){
            JDepuracion.anadirTexto(JDepuracion.mclCRITICO, this.getClass().getName(), "Error al conextarse " + msURLBase1);
            JDepuracion.anadirTexto(JDepuracion.mclCRITICO, this.getClass().getName(), " y " + url.toString());
            throw e;
        }finally{
            if(bs != null) {
                bs.close();
            }
            if(gzipout != null) {
                gzipout.close();
            }
            if(salida != null) {
                salida.close();
            }
        }
        return connection;
    }
    /**
     * devuelve un objeto del servidor, solo para tipos internet
     * @param connection conexion hecha de enviarObjeto
     * @throws Exception Exception en caso de errpor
     * @return el objeto devuelto por la conexion
     */
    public IListaElementos recibirObjeto(final URLConnection connection) throws Exception{
        //recibimos el resultado
        ObjectInputStream entrada;
        entrada = new ObjectInputStream(connection.getInputStream());
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes leer entrada");
        return  (IListaElementos) entrada.readObject();
    }

}
