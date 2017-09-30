/*
 * JServidorDatos.java
 *
 * Created on 16 de enero de 2007, 11:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.*;
import utiles.*;
import utiles.red.*;
import utiles.timer.ITemporizador;
import utiles.timer.JTimer;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesBD.servletAcciones.AEntradaComprimida;

public class JServerServidorDatosInternet extends JServidorDatosAbtrac implements ITemporizador {
    private IOpenConnection msoOpenConnection = new JOpenConnectionDefault();

    //Timer, para decir al servidor q seguimos vivos, cada 10 minutos, EL PROBLEMA ES Q USA CONEXIONES DE BD EN SERVIDOR, SI HAY 1000 USUARIOS...
    protected JTimer moTimer = new JTimer(this);
    private JServerServidorDatosInternetAccionNeutra moAccionNeutra = new JServerServidorDatosInternetAccionNeutra();
    
    /**url base*/
    private String msURLBase1;
    private String msNombreSelect;
    private String msNombreGuardar;
    /**tipo de conexion*/
    private int mlTipo = JServerServidorDatos.mclTipoInternet;

    //cacheamos las conexiones de internet

    private URL moURLSelect = null;
    private URL moURLGuardar = null;
    /**motor de select*/
    private ISelectMotor moSelect =  JSelectMotorFactory.getInstance().getSelectMotorDefecto();

    private IConstructorEstructuraBD moConstrucEstruc = new JConstructorEstructuraBDInternet(this);
    private JTableDefs moTableDefs=null;

    //id de sesion
    private String msJsessionid = null;

    private IServerServidorDatosInternetLogin moLogin;
    private boolean mbTipoCambiado=true;
    protected JServerServidorDatosInternetParam moParam = new JServerServidorDatosInternetParam();

    
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosInternet() {
        super();
        //Timer, para decir al servidor q seguimos vivos, cada 4 minutos
        moTimer.setIntervalo(4 * 60 * 1000);
    }
    
    /**
     * Crea una instancia de servidor de datos con una URL fija
     * @param psURLBase url base
     * @param psNombreSelect nombre servicio select
     * @param psNombreGuardar nombre servicio guardar
     */
    public JServerServidorDatosInternet(final String psURLBase, final String psNombreSelect, final String psNombreGuardar) {
        this();
        msURLBase1=psURLBase;
        mlTipo = JServerServidorDatos.mclTipoInternet;
        msNombreSelect = psNombreSelect;
        msNombreGuardar = psNombreGuardar;
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Inicializado");
    }
    /**
     * Constructor
     * @param psURLBase url base
     * @param psNombreSelect nombre servicio select
     * @param psNombreGuardar nombre servicio guardar
     * @param plTipo Tipo recogida de datos
     */
    public JServerServidorDatosInternet(final int plTipo, final String psURLBase, final String psNombreSelect, final String psNombreGuardar) {
      this();
      msURLBase1=psURLBase;
      msNombreSelect = psNombreSelect;
      msNombreGuardar = psNombreGuardar;
      mlTipo = plTipo;
      utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Inicializado");
    }
    /**
     * establece el nombre del servicio para select
     * @param psSelect nombre del servlet
     */
    public void setNombreSelect(final String psSelect){
      msNombreSelect = psSelect;
      moURLSelect = null;
    }
    public void setConstrucEstruc(final IConstructorEstructuraBD poConstrucEstruc){
        moConstrucEstruc=poConstrucEstruc;
    }
    public IConstructorEstructuraBD getConstrucEstruc(){
        return moConstrucEstruc;
    }
    public void setTableDefs(final JTableDefs poTableDefs){
        moTableDefs=poTableDefs;
    }
    public void setTipo(final int plTipo){
        mlTipo = plTipo;
        mbTipoCambiado=true;
    }
    public int getTipo(){
        return mlTipo;
    }
    public void setSelect(final ISelectMotor poSelect){
        moSelect = poSelect;
    }
    public ISelectMotor getSelect() {
        return moSelect;
    }
    public IServerServidorDatosInternetLogin  getLogin(){
        return moLogin;
    }
    public void setLogin(IServerServidorDatosInternetLogin poLogin){
        moLogin = poLogin;
        moLogin.setServidorInternet(this);
    }
    public boolean autentificar() throws Exception {
        return moLogin.autentificar();
    }
    public JServerServidorDatosInternetParam getParamInternet(){
        return moParam;
    }
    public JServerServidorDatosParam getParametros() {
        return moParam;
    }
    /**
     * devuelve la url base
     * @return url
     */
    public String getURLBase1(){
        return msURLBase1;
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
     * @param psGuardar nombre del servlet
     */
    public void setNombreGuardar(final String psGuardar){
      msNombreGuardar = psGuardar;
      moURLGuardar = null;
    }
    /**
     * devuelve el nombre del servicio guardar
     * @return nombre
     */
    public String getNombreGuardar(){
      return msNombreGuardar;
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
      moURLSelect = null;
      moURLGuardar = null;
      mbTipoCambiado=true;
      moTimer.stop();
      moTimer.start();
    }
    /**Establecemos que la entrada tambien esta comprimida al enviarla al servidor*/
    public void setEntradaComprimida(boolean pbComprimida) throws Exception {
        //establecemos que queremos la entrada comprimida
        URL url = new URL(msURLBase1 + AEntradaComprimida.mcsEntradaComprimida +  ".ctrl" + (getIDSession()==null ? "":";jsessionid="+getIDSession()) + "?EntradaComprimida="+String.valueOf(pbComprimida));
        URLConnection connection = msoOpenConnection.getConnection(url);
        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        BufferedReader in = (new BufferedReader(
            new InputStreamReader(
            connection.getInputStream())));
        try{
            in.readLine();
            if(pbComprimida){
                mlTipo=JServerServidorDatos.mclTipoInternetComprimido_I_O;
            }else{
                if(mlTipo == JServerServidorDatos.mclTipoInternetComprimido_I_O){
                    mlTipo=JServerServidorDatos.mclTipoInternetComprimido;
                }
            }
            mbTipoCambiado=false;
        }finally{
            in.close();
        }
    }
    /**
     * devuelve el nombre del servicio select
     * @return nombre del servlet
     */
    public String getNombreSelect(){
      return msNombreSelect;
    }
    /**
     * establece el usuario no se usa
     * @param psUsuario usuario
     * @param psPassword password
     * @param psPermisos permisos
     */
    public void setUsuario(final String psUsuario,final String psPassword,final String psPermisos){
      msUsuario=psUsuario;
      msPassWord=psPassword;
      msPermisos=psPermisos;
    }
    /**
     * devuelve el usuario no se usa
     * @return usuario
     */
    public String getUsuario(){
      return msUsuario;
    }


    ////////////////////////////////////////77
    ////Actualizaciones y sincronizacion fisica
    //////////////////////////////////////////
    public JTableDefs getTableDefs() throws Exception {
        if(moTableDefs==null){
            if(moConstrucEstruc==null){
                throw new ExceptionNoImplementado("No se ha asignado el constructor de definicion de campos");
            }
            moTableDefs = moConstrucEstruc.getTableDefs();
        }
        return moTableDefs;
    }
    public IResultado modificarEstructura(final ISelectEjecutarSelect poEstruc) {
        IResultado loResult = null;
        try{
            loResult = moActualizar(msNombreGuardar, null, null, poEstruc);
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
            loResult = new JResultado(new JFilaDatosDefecto(), "", e.toString(), false, 0);
        }
        return loResult;
    }
    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        IResultado loResult;
        if(getParametros().isSoloLectura()){
            loResult = new JResultado("Solo lectura", false);
        }else{
            loResult = null;
            try{
                loResult = moActualizar(msNombreGuardar, poActualizar, null, null);
            }catch(Exception e){
                JDepuracion.anadirTexto(this.getClass().getName(), e);
                loResult = new JResultado(new JFilaDatosDefecto(), "", e.toString(), false, 0);
            }

            if (loResult.getBien()) {
                actualizarDatosCacheConj(loResult.getListDatos(), psSelect);
            }
        }
        return loResult;
    }

    public IResultado ejecutarServer(final IServerEjecutar poEjecutar){
        IResultado loResult = null;
        boolean lbCont = true;
        try{
            if(getParametros().isSoloLectura()){
                if(JActualizarConj.class.isAssignableFrom(poEjecutar.getClass()) ||
                   JActualizar.class.isAssignableFrom(poEjecutar.getClass())      ){
                    loResult = new JResultado("Solo lectura", false);
                    lbCont=false;
                }
            }
            if(lbCont){
                loResult = moActualizar(msNombreGuardar, null,poEjecutar, null);
                if(loResult==null){
                    loResult = new JResultado(new JFilaDatosDefecto(), "", "Resultado del servidor nulo", false, 0);
                }
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
            loResult = new JResultado(new JFilaDatosDefecto(), "", e.toString(), false, 0);
        }

        if (loResult.getBien()) {
            actualizarDatosCacheConj(loResult.getListDatos(), "");
        }
        return loResult;
    }
//    OJO: no funciona bien
//    public boolean getEsConexionActiva(){
//        boolean lbConex = true;
//        try {
//            URL url=null;
//            if(msJsessionid==null){
//                url = new URL("http://172.16.0.187:8080/servidorCampo/" + msNombreSelect );
//            }else{
//                url = new URL("http://172.16.0.187:8080/servidorCampo/" + msNombreSelect + ";jsessionid=" + msJsessionid);
//            }
//            URLConnection connection = url.openConnection();
//            lbConex = (connection != null);
//        }catch(Exception e){
//            lbConex = false;
//        }
//        return lbConex;
//    }
    
    
    public byte[] enviarParametrosSinSesion(String psServlet,String psParametros) throws Exception {

        //conectamos con url
        URLConnection connection = null;
        URL url = new URL(getURLBase1() + psServlet);
        connection = getOpenConnection().getConnection(url);
        try{
            //solo esta para la version 1.5
            connection.setConnectTimeout(5000);//tiempo de respuesta maximo en milisegundos
        }catch(Throwable e){}//por si el metodo no existe

        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        //paso de parametros
        connection.getOutputStream().write(psParametros.getBytes());

        byte[] lbbytes;
        InputStream in = connection.getInputStream();
        try{        
            lbbytes = JArchivo.toByteArray(in);
        }finally{
            in.close();
        }
        return lbbytes;
    }
    public byte[] enviarParametros(String psNombreServlet ,String psParametros) throws Exception {

        //conectamos con url
        URLConnection connection = null;
        URL url;
        if(getIDSession()==null){
            url = new URL(getURLBase1() + psNombreServlet);
        }else{
            url = new URL(getURLBase1() + psNombreServlet + ";jsessionid=" + getIDSession());
        }        
        connection = getOpenConnection().getConnection(url);

        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        //paso de parametros
        connection.getOutputStream().write(psParametros.getBytes());

        byte[] lbbytes;
        InputStream in = connection.getInputStream();
        try{        
            lbbytes = JArchivo.toByteArray(in);
        }finally{
            in.close();
        }
        return lbbytes;
    }
    
    /**
     * envia un objeto al servidor, solo para tipos internet
     * @param psNombreServlet nombre del servlet
     * @param poActualizar Objeto a mandar
     * @throws Exception Exception en caso de error
     * @return la conexion hecha una vez mandado, para poder recoger el objeto devuelto
     */
    public URLConnection enviarObjeto(final String psNombreServlet,final ISelectEjecutarComprimido poActualizar) throws Exception{
        URLConnection loCon = null;
        try{
            loCon = enviarObjetoReal(psNombreServlet, poActualizar);
        }catch(Exception e){
            try{
                if(autentificar()){
                    loCon = enviarObjetoReal(psNombreServlet, poActualizar);
                }else{
                    throw e;
                }
            }catch(Exception e1){
                throw e1;
            }
        }
        return loCon;
    }
    protected URLConnection enviarObjetoReal(final String psNombreServlet,final ISelectEjecutarComprimido poActualizar) throws Exception{
        if(mbTipoCambiado){
            //ignoramos el error q algunas veces da problemas con los servicios q no requieren autentificacion como recuperar la lista de usuarios
            try{
                setEntradaComprimida(mlTipo==JServerServidorDatos.mclTipoInternetComprimido_I_O);
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }finally{
                mbTipoCambiado=false;
            }
        }
        //conectamos con url
        URLConnection connection=null;
        URL url=null;
        boolean lbEsSelect = (psNombreServlet.compareTo(msNombreSelect)==0);
        boolean lbEsGuardar = (psNombreServlet.compareTo(msNombreGuardar)==0);
//        choiceEdu1.addItem(' ' + msURLBase1 + psNombreServlet);
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),' ' + msURLBase1 + psNombreServlet);
        if ((lbEsSelect) && (moURLSelect!=null)) {
            url = moURLSelect;
        }
        else{
            if ((lbEsGuardar) && (moURLGuardar!=null)) {
                url = moURLGuardar;
            }
            else{
                if(getIDSession()==null){
                    url = new URL(msURLBase1 + psNombreServlet);
                }else{
                    url = new URL(msURLBase1 + psNombreServlet + ";jsessionid=" + getIDSession());
                }
                if (lbEsSelect) {
                    moURLSelect = url;
                } else{
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
        connection.setDoInput(true);
        //Pasamos a flujo de datos del objeto
        ByteArrayOutputStream bs = null;
        ObjectOutputStream salida = null;
        GZIPOutputStream gzipout = null;
        if((mlTipo == JServerServidorDatos.mclTipoInternetComprimido)||(mlTipo == JServerServidorDatos.mclTipoInternetComprimido_I_O)){
            poActualizar.setComprimido(true);
        }
        try{
            bs = new ByteArrayOutputStream(512);
            utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes crear objeto del flujo");
            if(mlTipo==JServerServidorDatos.mclTipoInternetComprimido_I_O){
                gzipout = new GZIPOutputStream(bs);
                salida = new ObjectOutputStream(gzipout);
                connection.setRequestProperty("Content-Type", "application/gzip");
            }else{
                salida = new ObjectOutputStream(bs);
                connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
            }
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
        }catch(Throwable e){
            JDepuracion.anadirTexto(JDepuracion.mclCRITICO, this.getClass().getName(), "Error al conextarse " + msURLBase1);
            JDepuracion.anadirTexto(JDepuracion.mclCRITICO, this.getClass().getName(), " y " + url.toString());
            if(Exception.class.isAssignableFrom(e.getClass())){
                throw (Exception)e;
            }else{
                throw new Exception(e);
            }
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
    public Object recibirObjeto(final URLConnection connection) throws Exception{
        Object loCon = null;
        try{
            loCon = recibirObjetoReal(connection);
        }catch(Throwable e){
            //cuidaditocon esto, pq puede haber otros tipos de errores y crear sesiones infinitas, por eso se usa un metodo seguro: setEntradaComprimida
            try{
//                setEntradaComprimida(mlTipo==JServerServidorDatos.mclTipoInternetComprimido_I_O);// no vale
                URLConnection connection1 = enviarObjetoReal(msNombreGuardar, moAccionNeutra);
                recibirObjetoReal(connection1);                 
            }catch(Throwable ex){
                try{
                    if(autentificar()){
                        loCon = recibirObjetoReal(connection);
                    }else{
                        throw new Exception(e);
                    }
                }catch(Throwable e1){
                    throw new Exception(e1);
                }
            }finally{
                mbTipoCambiado=false;
            }
        }
        return loCon;
    }
    protected Object recibirObjetoReal(final URLConnection connection) throws Exception{
        //recibimos el resultado
        ObjectInputStream entrada;
            if((mlTipo==JServerServidorDatos.mclTipoInternetComprimido_I_O)||(mlTipo==JServerServidorDatos.mclTipoInternetComprimido)) {
                entrada = new ObjectInputStream(new GZIPInputStream(connection.getInputStream()));
            }else{
                entrada = new ObjectInputStream(connection.getInputStream());
            }
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Antes leer entrada");
        return  entrada.readObject();
    }

    protected IResultado moActualizar(final String psNombreServlet, final JActualizar poActualizar,final IServerEjecutar poEjecutar, final ISelectEjecutarSelect poEstruc) throws Exception {
        IResultado loResult = null;
        switch(mlTipo){
            case JServerServidorDatos.mclTipoInternetComprimido:
            case JServerServidorDatos.mclTipoInternetComprimido_I_O:
            case JServerServidorDatos.mclTipoInternet:
                URLConnection connection;
                if(poActualizar == null){
                    if(poEjecutar==null){
                        connection = enviarObjeto(psNombreServlet, poEstruc);
                    }else{
                        connection = enviarObjeto(psNombreServlet, poEjecutar);
                    }
                }else{
                    connection = enviarObjeto(psNombreServlet, poActualizar);
                }
                //recibimos el resultado
                loResult = (IResultado) recibirObjeto(connection);
                break;
            default:
                loResult = new JResultado(new JFilaDatosDefecto(), "", "Tipo de base de datos no implementa la actualización", false, 0);
        }

        
        return loResult;
    }


    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        //introduccion de campos a la select de sistema
        poSelect.setPassWord(msPassWord);
        poSelect.setPermisos(msPermisos);
        poSelect.setUsuario(msUsuario);
        //recuperacion de datos fisica
        switch(mlTipo){
          case JServerServidorDatos.mclTipoInternet:
            recuperarDatosInternet(v, poSelect,psTabla, false, false);
            break;
          case JServerServidorDatos.mclTipoInternetComprimido:
          case JServerServidorDatos.mclTipoInternetComprimido_I_O:
            recuperarDatosInternet(v, poSelect,psTabla, true, false);
            break;
          case JServerServidorDatos.mclTipoInternetTexto:
            recuperarDatosInternet(v, poSelect,psTabla, false, true);
            break;
          default:
              throw new Exception(this.getClass().getName()+"(mlTipo)->Tipo de servidor incorrecto");

        }

    }
    private JListDatos  recuperarDatosInternet(final JListDatos v,final JSelect poSelect,final String psTabla,final boolean pbComprimido,final boolean pbSoloTexto)throws Exception  {
        JListDatos loCon = null;
        try{
            loCon = recuperarDatosInternetReal(v,poSelect,psTabla,pbComprimido,pbSoloTexto);
        }catch(EErrorGenerico eControlado){
            throw eControlado;
        }catch(Exception e){
            try{
                if(autentificar()){
                    loCon = recuperarDatosInternetReal(v,poSelect,psTabla,pbComprimido,pbSoloTexto);
                }else{
                    throw e;
                }
            }catch(Exception e1){
                throw e;
            }
        }
        return loCon;
    }

    private JListDatos  recuperarDatosInternetReal(final JListDatos v,final JSelect poSelect,final String psTabla,final boolean pbComprimido,final boolean pbSoloTexto)throws Exception  {
        //conectamos con url
        URLConnection connection=null;
        if(pbSoloTexto){
          String psSelect = poSelect.msSQL(moSelect);
//          choiceEdu1.addItem(psSelect + ' ' + msURLBase1 + msNombreSelect);
          utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),psSelect + ' ' + msURLBase1 + msNombreSelect);
          URL url = new URL(msURLBase1 + msNombreSelect +
                            "?select=" + URLEncoder.encode(psSelect) +
                            (pbComprimido ? "&comprimido=si" : "&comprimido=no"));
          connection = msoOpenConnection.getConnection(url);
          //para evitar paginas estaticas
          connection.setUseCaches(false);
          //forzamos a que se ejecute el script
          connection.setDoOutput(true);
          //le enviamos los parametros
          PrintWriter out = new PrintWriter(
              connection.getOutputStream());
          out.close();
        }else{
          poSelect.setComprimido(pbComprimido);
          connection = enviarObjeto(msNombreSelect, poSelect);
          connection.connect();
        }
        //creamos el buffer de lectura
        BufferedReader in=null;
        try{
            try{
                //primero intentamos con codificacion ISO-8859
                if (pbComprimido) {
                  in = (new BufferedReader(new InputStreamReader(
                          new GZIPInputStream(connection.getInputStream()), "ISO-8859-1")
                      ));
                } else {
                  in = (new BufferedReader(new InputStreamReader(
                          connection.getInputStream(), "ISO-8859-1")));
                }
            }catch(Throwable e){
                //si pega casque intentamos SIN codificacion
                if (pbComprimido) {
                  in = (new BufferedReader(new InputStreamReader(
                          new GZIPInputStream(connection.getInputStream()))
                      ));
                } else {
                  in = (new BufferedReader(new InputStreamReader(
                          connection.getInputStream())));
                }
            }

        //Ojo: Falta leer definicion de los campos
        //recorremos el buffer y rellenamos un vector de filas de datos
        //la primera linea siempre es chr(10), se hace asi porque si no casca
        //cuando sea el resultado vacio
        //los servidores antiguos si la primera linea no es "" devuelven en la primera linea el error
        //FUTURO: los servidores modernos devuelven un xml, q contiene varios elementos, el error si lo hubiera y el numero de registros
            String inputLine = in.readLine();
            

            if(inputLine !=null && !inputLine.equals("")) {
//                try{
//                    SAXBuilder loBuid = new SAXBuilder();
//                    Document loDoc = loBuid.build(new CadenaLarga(inputLine));
//                    Element loError =Element.simpleXPath(loDoc.getRootElement(), "error");
//                    if(loError!=null){
//                        throw new Exception(loError.getValue());
//                    }
//                    Element loNum =Element.simpleXPath(loDoc.getRootElement(), "nregistros");
//                    if(loNum!=null){
//                        int lNumeroReg=-1;
//                        if(JConversiones.isNumeric(loNum.getValue())){
//                            lNumeroReg=(int)JConversiones.cdbl(loNum.getValue());
//                        }
//                        if(lNumeroReg>50){
//                            v.getJList().setIncrementoTamano(lNumeroReg);
//                        }
//                    }
//
//                }catch(Exception e){
                    throw new EErrorGenerico(inputLine);
//                }
            }
            int lFila = 0;
            inputLine = in.readLine();
            boolean lbContinuar = true;
            while ( inputLine != null && lbContinuar) {
                //destransparentamos los cambios de linea
                if((inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea10)>0)||
                   (inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea13)>0)){
                    inputLine = inputLine.replace(JFilaDatosDefecto.mccTransparentacionCambioLinea10, (char)10).replace(JFilaDatosDefecto.mccTransparentacionCambioLinea13,(char)13);
                }
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                String[] lasArray = JFilaDatosDefecto.moArrayDatos(inputLine, JFilaDatosDefecto.mccSeparacion1);
                if(getParametros().isEliminarEspaciosDerechaSiempre()){
                    for(int i = 0 ; i < lasArray.length;i++){
                        lasArray[i] = JServerServidorDatosBD.rTrim(lasArray[i]);
                    }
                }
                loFila.setArray(lasArray);
                
                v.add(loFila);
                inputLine = in.readLine();
                
                lFila++;
                //dentro jlistdatos se incrementa ^2
//                if(lFila > 1000 && v.getIncrementoMemoria()<1000){
//                    v.setIncrementoMemoria(1000);
//                }
//                if(lFila > 10000 && v.getIncrementoMemoria()<10000){
//                    v.setIncrementoMemoria(10000);
//                }
//                if(lFila > 100000 && v.getIncrementoMemoria()<100000){
//                    v.setIncrementoMemoria(100000);
//                }
                if(lFila>moParam.getNumeroMaximoRegistros() && moParam.getNumeroMaximoRegistros()>0){
                    lbContinuar=false;
                }                
            }
            return v;
        }finally{
            if(in!=null){
                in.close();
            }
//            v.getJList().setIncrementoTamano(JListaElementos.mclIncrementoDefecto);
        }
    }
    /**
     *
     * cerrar objeto, si es tipo base de datos es muy importante hacerlo
     * @throws Exception el error en caso de error
     */
    public void close() throws Exception{
        try{
            moTimer.stop();
        }catch(Throwable e){
            
        }
    }

    public void timerArrancado(JTimer JTimer) {
    }

    public void timerParado(JTimer JTimer) {
    }

    public void timerMuerto(JTimer JTimer) {
    }

    public void timerIntervalo(JTimer JTimer) {
        try{
            //cuidadito con esto q puede crear sesiones infinitas, por eso se usa enviarObjetoReal y recibirObjetoReal, para q no se creen sesiones en caso de fallo
            if(getIDSession()!=null && !getIDSession().equals("")){
                JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), "Lanzada accion neutra");
                //Se ejecuta esta accion para no perder la sesion del servidor
                URLConnection connection = enviarObjetoReal(msNombreGuardar, moAccionNeutra);
                recibirObjetoReal(connection); 
            }
        }catch(Throwable e){
            moTimer.stop();
        }
    }

    /**
     * @return the moOpenConnection
     */
    public IOpenConnection getOpenConnection() {
        return msoOpenConnection;
    }

    /**
     * @param moOpenConnection the moOpenConnection to set
     */
    public void setOpenConnection(IOpenConnection moOpenConnection) {
        msoOpenConnection = moOpenConnection;
    }
    

}
