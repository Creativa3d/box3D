/*
 * JServidorArchivosWeb.java
 *
 * Created on 3 de mayo de 2006, 16:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import utiles.red.IOpenConnection;
import utiles.red.JOpenConnectionDefault;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import utiles.IListaElementos;
import utiles.JDepuracion;

public class JServidorArchivosWeb extends JServidorArchivosAbstract{
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    public static final String mcsFichero="fichero.ctrl";
    public static final String mcsDirectorio="directorio.ctrl";
    public static final String mcsCopiar="copiar.ctrl";
    public static final String mcsMover="mover.ctrl";
    public static final String mcsBorrar="borrar.ctrl";
    public static final String mcsBajarfichero="bajarfichero.ctrl";
    public static final String mcsSubirfichero="subirfichero.ctrl";
    public static final String mcsCrearCarpeta="crearCarpeta.ctrl";
    private String msURLBase1;
    private boolean mbTransmisionComprimida = false;
    private String msJsessionid=null;
    
    /** Creates a new instance of JServidorArchivosWeb */
    public JServidorArchivosWeb(String psDireccion) {
        msURLBase1=psDireccion;
    }
    
    public void setIDSession(String psJsessionid){
        msJsessionid=psJsessionid;
    }
    public String getIDSession(){
        return msJsessionid;
    }
    public String getURLBase1(){
        return msURLBase1;
    }
    public void setURLBase1(String psURL){
        msURLBase1=psURL;
        msJsessionid=null;
    }
    /**Establecemos que la entrada tb esta comprimida al enviarla al servidor*/
    public void setEntradaComprimida() throws Exception {
        mbTransmisionComprimida=false;
        //establecemos que queremos la entrada comprimida
        URL url = new URL(msURLBase1 + "entradaComprimida.ctrl;jsessionid=" + msJsessionid + "?EntradaComprimida=true");
        URLConnection connection = msoOpenConnection.getConnection(url);
        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        BufferedReader in = (new BufferedReader(
            new InputStreamReader(
            connection.getInputStream())));
        try{
            String l = in.readLine();
            mbTransmisionComprimida=l.equals("1");
        }finally{
            in.close();
        }
    }
    public JFichero getFichero(JFichero poPathCompleto) throws Exception{
        URLConnection loConec = enviarObjeto(mcsFichero, poPathCompleto);
        JWebComunicacion loComu = (JWebComunicacion)recibirObjeto(loConec);
        if(!loComu.mbBien){
            throw new Exception(loComu.msMensaje);
        }
        loComu.moFichero.setServidor(this);
        return loComu.moFichero;
    }
    public IListaElementos getListaFicheros(JFichero poPathCompleto) throws Exception{
        //mandamos la peticion al servidor 
        URLConnection loConec = enviarObjeto(mcsDirectorio, poPathCompleto);
        //recogemos la respuesta del servidor
        JWebComunicacion loComu = (JWebComunicacion)recibirObjeto(loConec);
        if(!loComu.mbBien){
            throw new Exception(loComu.msMensaje);
        }
        IListaElementos loLista=loComu.moListaArchivos;
        //establecemos el servidor de ficheros a si mismo que es el que lo ha recuperado
        for(int i =0; i<loLista.size();i++ ){
            JFichero loFiche = (JFichero)loLista.get(i);
            loFiche.setServidor(this);
        }
        return loLista;
    }
    private void subirArchivo(IServidorArchivos poServidorOrigen , JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
//        JWebComunicacion loComu;
//        URLConnection loConec;
//        loComu = new JWebComunicacion();
//        loComu.moFicheroDestino = poFicherodestino;
//        loComu.cargarArchivo(poServidorOrigen.getFlujoEntrada(poFicheroOrigen), poFicheroOrigen.getLenght());
//        //mandamos la peticion al servidor 
//        loConec = enviarObjeto(mcsSubirfichero, loComu);
//        //recogemos la respuesta del servidor
//        loComu = (JWebComunicacion)recibirObjeto(loConec);
//        if(!loComu.mbBien){
//            throw new Exception(loComu.msMensaje);
//        }
        OutputStream loOut = getFlujoSalida(poFicherodestino, false);
        InputStream loIn = poServidorOrigen.getFlujoEntrada(poFicheroOrigen);
        
        byte[] loBuffer = new byte[JWebOutputStream.mclTamanoBuffer];
        int lResul = loIn.read(loBuffer);
        while(lResul >= 0 ){
            loOut.write(loBuffer, 0, lResul);
            lResul = loIn.read(loBuffer);
        }
        loOut.close();
    }
    public void copiarFichero(IServidorArchivos poServidorOrigen , JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        JWebComunicacion loComu;
        URLConnection loConec;
        if(poServidorOrigen == this){
            loComu = new JWebComunicacion();
            loComu.moFichero = poFicheroOrigen;
            loComu.moFicheroDestino = poFicherodestino;
            //mandamos la peticion al servidor 
            loConec = enviarObjeto(mcsCopiar, loComu);
            //recogemos la respuesta del servidor
            loComu = (JWebComunicacion)recibirObjeto(loConec);
            if(!loComu.mbBien){
                throw new Exception(loComu.msMensaje);
            }
        }else{
            subirArchivo(poServidorOrigen, poFicheroOrigen, poFicherodestino);
        }
    }
    public void borrar(JFichero poFichero) throws Exception{
        //mandamos la peticion al servidor 
        URLConnection loConec = enviarObjeto(mcsBorrar, poFichero);
        //recogemos la respuesta del servidor
        JWebComunicacion loComu = (JWebComunicacion)recibirObjeto(loConec);
        if(!loComu.mbBien){
            throw new Exception(loComu.msMensaje);
        }
    }
    public void moverFichero(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        JWebComunicacion loComu;
        URLConnection loConec;
        if(poServidorOrigen == this){
            //operacion dentro del mismo servidor
            loComu = new JWebComunicacion();
            loComu.moFichero = poFicheroOrigen;
            loComu.moFicheroDestino = poFicherodestino;
            //mandamos la peticion al servidor 
            loConec = enviarObjeto(mcsMover, loComu);
        }else{
            //operacion desde otro servidor cualquiera a este
            loComu = new JWebComunicacion();
            loComu.moFichero = poFicherodestino;
            loComu.cargarArchivo(poServidorOrigen.getFlujoEntrada(poFicheroOrigen), poFicheroOrigen.getLenght());
            //mandamos la peticion al servidor 
            loConec = enviarObjeto(mcsSubirfichero, loComu);
        }
        //recogemos la respuesta del servidor
        loComu = (JWebComunicacion)recibirObjeto(loConec);
        if(!loComu.mbBien){
            throw new Exception(loComu.msMensaje);
        }
        if(poServidorOrigen != this){
            //borramos los datos del origen
            poServidorOrigen.borrar(poFicheroOrigen);
        }
    }
    JWebComunicacion getFlujoEntradaComu(JWebComunicacion poComu) throws Exception{
        //mandamos la peticion al servidor 
        URLConnection loConec = enviarObjeto(mcsBajarfichero, poComu);
        //recogemos la respuesta del servidor
        JWebComunicacion loComu = (JWebComunicacion)recibirObjeto(loConec);
        if(!loComu.mbBien){
            throw new Exception(loComu.msMensaje);
        }
        return loComu;
    }
    public InputStream getFlujoEntrada(JFichero poFichero) throws Exception{
        JWebComunicacion loComu = new JWebComunicacion();
        loComu.moFichero = poFichero;
        return new JWebInputStream(this, getFlujoEntradaComu(loComu));
    }
    public OutputStream getFlujoSalida(JFichero poFichero, boolean pbAppend) throws Exception{
        JWebComunicacion loComu = new JWebComunicacion();
        loComu.moFicheroDestino = poFichero;
        return new JWebOutputStream(this, loComu, pbAppend);
    }
    
    /**
     * envia un objeto al servidor, solo para tipos internet
     * @param psNombreServlet nombre del servlet
     * @param poActualizar Objeto a mandar
     * @throws Exception Exception en caso de error
     * @return la conexion hecha una vez mandado, para poder recoger el objeto devuelto
     */
    public URLConnection enviarObjeto(String psNombreServlet, Object poActualizar) throws Exception{
        //conectamos con url
        URLConnection connection=null;
        URL url=null;

        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),' ' + msURLBase1 + psNombreServlet);

        if(msJsessionid!=null){
            url = new URL(msURLBase1 + psNombreServlet + ";jsessionid=" + msJsessionid);
        }else{
            url = new URL(msURLBase1 + psNombreServlet);
        }
        
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes abrir conexión");
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
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes crear objeto del flujo");
            if(mbTransmisionComprimida){
                gzipout = new GZIPOutputStream(bs);
                salida = new ObjectOutputStream(gzipout);
                connection.setRequestProperty("Content-Type",
                                              "gzip");
            }else{
                salida = new ObjectOutputStream(bs);
                connection.setRequestProperty("Content-Type",
                                              "application/x-java-serialized-object");
            }
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar objeto al flujo");
            salida.writeObject(poActualizar);
            salida.flush(); salida.close(); salida=null;
            if(gzipout != null) {
                gzipout.flush(); gzipout.close(); gzipout=null;
            }
            connection.setRequestProperty("Content-Lenght", String.valueOf(bs.size()));
            utiles.JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar el flujo a la conexion");
            bs.writeTo(connection.getOutputStream());
            utiles.JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),"Despues mandar el flujo a la conexion");
        }catch(Exception e){
            System.out.println("Error al conextarse " + msURLBase1);
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
    public Object recibirObjeto(URLConnection connection) throws Exception{
        //recibimos el resultado
        ObjectInputStream entrada;
            if(mbTransmisionComprimida) {
                entrada = new ObjectInputStream(new GZIPInputStream(connection.getInputStream()));
            }else{
                entrada = new ObjectInputStream(connection.getInputStream());
            }
        utiles.JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes leer entrada");
        return  entrada.readObject();
    }
    public void crearCarpeta(JFichero poFichero) throws Exception{
        //mandamos la peticion al servidor 
        URLConnection loConec = enviarObjeto(mcsCrearCarpeta, poFichero);
        //recogemos la respuesta del servidor
        JWebComunicacion loComu = (JWebComunicacion)recibirObjeto(loConec);
        if(!loComu.mbBien){
            throw new Exception(loComu.msMensaje);
        }
    }
}
