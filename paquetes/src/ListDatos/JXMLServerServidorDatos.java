/*
 * JServerServidorDatosXML.java
 *
 * Created on 4 de enero de 2005, 15:55
 */

package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.*;
import utiles.*;
import utiles.red.*;
/** 
 *   Servidor de datos que transmite la informacion en formato XML 
 */
public class JXMLServerServidorDatos extends JServidorDatosAbtrac {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    /** Contante tipo de conexion Internet*/
    public static final int mclTipoInternet = 0;
    /** Contante tipo de conexion internet de recibir datos  comprimido*/
    public static final int mclTipoInternetComprimido = 3;
    /** Contante tipo de conexion internet de recibir/enviar datos comprimido */
    public static final int mclTipoInternetComprimido_I_O = 5;
    
    private String msURLBase1;
    private String msNombreSelect;
    private String msNombreGuardar;
    //cacheamos las conexiones de internet
    private URL moURLSelect = null;
    private URL moURLGuardar = null;
    //id de sesion
    private String msJsessionid = null;
    private int mlTipo = mclTipoInternet;
    //motor de select, para dar nombre a las select
    private ISelectMotor moSelectXML=new JXMLSelectMotor();
    private JXMLServerCrearObjeto moCreadorObjetos = new JXMLServerCrearObjeto();
    public IConstructorEstructuraBD moConstrucEstruc = null;
    public JTableDefs moTableDefs=null;
    
    /**
     * Creates a new instance of JServerServidorDatosXML
     * @param plTipo tipo de conexion
     * @param psUrlBase url base
     * @param psServletSelect nombre del servlet select
     * @param psServletGuardar nombre del servlet guardar
     */
    public JXMLServerServidorDatos(final int plTipo, final String psUrlBase,final  String psServletSelect, final String psServletGuardar) {
        super();
        mlTipo = plTipo;
        msURLBase1 = psUrlBase;
        msNombreSelect = psServletSelect;
        msNombreGuardar = psServletGuardar;
        jbInit();
    }
    /**
    * Crea componemos de depuracion
    */
    private void jbInit() {
//        this.setLayout(gridLayout1);
//        this.add(choiceEdu1, null);
//        choiceEdu1.addItem("Inicializado");
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Inicializado");
    }    
    /**
     * establece el nombre del servicio para select
     * @param psSelect select
     */
    public void setNombreSelect(final String psSelect){
      msNombreSelect = psSelect;
      moURLSelect = null;
    }
    /**
     * establece la url base
     * @param psURLBase url
     */
    public void setURLBase1(final String psURLBase){
      msURLBase1 = psURLBase;
      moURLSelect = null;
      moURLGuardar = null;
    }
    /**
     * establece el nombre del servicio para guardar
     * @param psGuardar guardar
     */
    public void setNombreGuardar(final String psGuardar){
      msNombreGuardar = psGuardar;
      moURLGuardar = null;
    }
    /**
     * establece el id de session
     * @param psIdSession id de sesion
     */
    public void setIDSession(final String psIdSession){
      msJsessionid = psIdSession;
      moURLSelect = null;
      moURLGuardar = null;
    }
    /**
     * devuelve la url base
     * @return url base
     */
    public String getURLBase1(){
        return msURLBase1;
    }
    /**
     * devuelve el nombre del servicio select
     * @return nombre
     */
    public String getNombreSelect(){
      return msNombreSelect;
    }
    /**
     * devuelve el nombre del servicio guardar
     * @return nombre
     */
    public String getNombreGuardar(){
      return msNombreGuardar;
    }
    public IResultado modificarEstructura(final ISelectEjecutarSelect poEstruc) {
        return moActualizar(msNombreGuardar, null, null, poEstruc);
    }
    public JTableDefs getTableDefs() throws Exception {
        if(moTableDefs==null){
            if(moConstrucEstruc==null){
                throw new ExceptionNoImplementado("No se ha asignado el constructor de definicion de campos");
            }
            moTableDefs = moConstrucEstruc.getTableDefs();
        }
        return moTableDefs;
    }
    /**
    * actualiza un conj. de filas/otros
    */
    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        IResultado loResult = moActualizar(msNombreGuardar, poActualizar, null, null);

        if (loResult.getBien()) {
            actualizarDatosCacheConj(loResult.getListDatos(), psSelect);
        }
        return loResult;
    }
    public IResultado ejecutarServer(final IServerEjecutar poEjecutar) {
        IResultado loResult = moActualizar(msNombreGuardar, null,poEjecutar, null);

        if (loResult.getBien()) {
            actualizarDatosCacheConj(loResult.getListDatos(), "");
        }
        return loResult;
    }
  
    private URLConnection enviarObjeto(final String psNombreServlet, final String psActualizar) throws Exception{
        //conectamos con url
        URLConnection connection=null;
        URL url=null;
        boolean lbEsSelect = (psNombreServlet.compareTo(msNombreSelect)==0);
        boolean lbEsGuardar = (psNombreServlet.compareTo(msNombreGuardar)==0);
//        choiceEdu1.addItem(' ' + msURLBase1 + psNombreServlet);
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),' ' + msURLBase1 + psNombreServlet);
        if ((lbEsSelect) && (moURLSelect!=null)) {
            url = moURLSelect;
        } else {
            if ((lbEsGuardar) && (moURLGuardar!=null)) {
                url = moURLGuardar;
            } else {
                if(msJsessionid==null){
                    url = new URL(msURLBase1 + psNombreServlet);
                }else{
                    url = new URL(msURLBase1 + psNombreServlet + ";jsessionid=" + msJsessionid);
                }
                if (lbEsSelect) {
                    moURLSelect = url;
                }else{
                    if (lbEsGuardar) {
                        moURLGuardar = url;
                    }
                }
            }
        }
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes abrir conexion");
        connection = msoOpenConnection.getConnection(url);
        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        //Pasamos a flujo de datos del objeto
        ByteArrayOutputStream bs = null;
        try{
            bs = new ByteArrayOutputStream(512);
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes crear objeto del flujo");
            if(mlTipo==mclTipoInternetComprimido_I_O){
                connection.setRequestProperty("Content-Type", "gzip");
                utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar objeto al flujo");
                OutputStream salida = new GZIPOutputStream(bs);
                salida.write(psActualizar.getBytes());
                salida.flush(); salida.close(); salida=null;
            }else{
                connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
                utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar objeto al flujo");
                bs.write(psActualizar.getBytes());
            }

            connection.setRequestProperty("Content-Lenght", String.valueOf(bs.size()));
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes mandar el flujo a la conexion");
            bs.writeTo(connection.getOutputStream());
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Despues mandar el flujo a la conexion");
        }finally{
            if(bs != null){
                bs.close();
            }
        }
        return connection;
    }

    private Object recibirObjeto(final URLConnection connection) throws Exception{
        //recibimos el resultado
        InputStream entrada;
        if((mlTipo==mclTipoInternetComprimido_I_O)||(mlTipo==mclTipoInternetComprimido)) {
            entrada = new GZIPInputStream(connection.getInputStream());
        }else{
            entrada = connection.getInputStream();
        }
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes leer entrada");
        return moCreadorObjetos.parser(entrada);
    }

    private IResultado moActualizar(final String psNombreServlet, final JActualizar poActualizar, final IServerEjecutar poEjecutar, final ISelectEjecutarSelect poEstruc){
        IResultado loResult = null;
        try{
            StringBuilder lsXml = new StringBuilder(120);
            lsXml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            lsXml.append("<transferencia>");
            lsXml.append("<vueltaComprimida>"+String.valueOf(((mlTipo == mclTipoInternetComprimido)||(mlTipo == mclTipoInternetComprimido_I_O)))+"</vueltaComprimida>");
            if(poActualizar==null){
                if(poEjecutar==null){
                    lsXml.append(poEstruc.msSQL(moSelectXML));
                }else{
                    lsXml.append(poEjecutar.getXML());
                }
            }else{
                lsXml.append(poActualizar.getXML());
            }
            lsXml.append("</transferencia>");
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,"xml", lsXml.toString());

            URLConnection connection = enviarObjeto(psNombreServlet, lsXml.toString());
            //recibimos el resultado
            loResult = (IResultado) recibirObjeto(connection);
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
            loResult = new JResultado(new JFilaDatosDefecto(), "", e.toString(), false, 0);
        }
        return loResult;
    }
    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception {
        //recuperacion de datos fisica
        switch(mlTipo){
          case mclTipoInternet:
            recuperarDatosInternet(v, poSelect,psTabla, false);
            break;
          case mclTipoInternetComprimido:
          case mclTipoInternetComprimido_I_O:
            recuperarDatosInternet(v, poSelect,psTabla, true);
            break;
          default:
              throw new Exception(this.getClass().getName()+"(mlTipo)->Tipo de servidor incorrecto");
        }
    }
    private JListDatos  recuperarDatosInternet(final JListDatos v, final JSelect poSelect, final String psTabla, final boolean pbComprimido)throws Exception  {
        //conectamos con url
        URLConnection connection=null;
        StringBuilder lsXml = new StringBuilder(120);
        lsXml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
        lsXml.append("<transferencia>");
        lsXml.append("<vueltaComprimida>"+String.valueOf(pbComprimido)+"</vueltaComprimida>");
        lsXml.append(poSelect.msSQL(moSelectXML));
        lsXml.append("</transferencia>");
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(), lsXml.toString());
        connection = enviarObjeto(msNombreSelect, lsXml.toString());
        //creamos el buffer de lectura
        BufferedReader in=null;
        try{
            if (pbComprimido) {
              in = (new BufferedReader(
                  new InputStreamReader(new GZIPInputStream(connection.getInputStream()))
                  )
                    );

            } else{
              in = (new BufferedReader(
                  new InputStreamReader(
                  connection.getInputStream())));
            }

        //Ojo: Falta leer definicion de los campos
        //recorremos el buffer y rellenamos un vector de filas de datos
        //la primera linea siempre es chr(10), se hace asi porque si no casca
        //cuando sea el resultado vacio
        //si la primera linea no es "" es que el servidor devuelve error
            String inputLine = in.readLine();
            if(inputLine != null && inputLine.compareTo("")!=0) {
                throw new Exception(inputLine);
            }
            inputLine = in.readLine();
            while ( inputLine != null) {
                //destransparentamos los cambios de linea
                if((inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea10)>0)||
                   (inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea13)>0)){
                    inputLine = inputLine.replace(JFilaDatosDefecto.mccTransparentacionCambioLinea10, (char)10).replace(JFilaDatosDefecto.mccTransparentacionCambioLinea13,(char)13);
                }
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                loFila.setArray(JFilaDatosDefecto.moArrayDatos(inputLine, JFilaDatosDefecto.mccSeparacion1));
                
                v.add(loFila);
                inputLine = in.readLine();
            }
            return v;
        }finally{
            if(in!=null){
                in.close();
            }
        }
    }

    
}
