/*
* JDatosGenerales.java
*
* Creado el 29/7/2008
*/

package utilesGUIx.aplicacion;


import java.util.ResourceBundle;
import ListDatos.*;
import ListDatos.config.ITextosFuente;
import ListDatos.config.JDevolverTextos;
import ListDatos.config.JTextosFuente;

import java.io.File;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import utiles.config.*;
import utiles.*;
import utiles.xml.dom.Element;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesBD.poolConexiones.JServerServidorDatosBDPool;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.aplicacion.actualizarEstruc.JActualizarEstrucProc;
import utilesGUIx.aplicacion.auxiliares.tablasExtend.JTEEDATOSGENERALES2;
import utilesGUIx.aplicacion.avisos.JAvisosConj;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JConexionIO;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.plugin.JPlugInContexto;
import utilesGUIx.plugin.seguridad.JPlugInSeguridad;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablasExtend.JTEECUENTASCORREO;
import utilesGUIxSeguridad.PBKDF2WithHmacSHA1;

public abstract class JDatosGeneralesAplicacionModelo extends JPlugInContexto implements IMostrarPantalla, IGestionProyecto {

    private static String msCalendarioDefecto;

    protected String msCodUsuario;
    protected String msUsuario;


    public static final String mcsLookAndFeelTema = "LookAndFeelTema";
    public static final String mcsDepuracionNivel= "DepuracionNivel";
    public static final String mcsSINCRONIZADO = "SINCRONIZADO";
    public static final String mcsImagenFondo = "ImagenFondo";
    public static final String mcsLookAndFeel = "LookAndFeel";
    
    public static final String mcsTIPOFORMSPRINCIPALES_TIPO = "TIPOFORMSPRINCIPALES/TIPO";
    public static final String mcsTIPOFORMS_PRINCIPALLAYOUT = "TIPOFORMS/PRINCIPALLAYOUT";
    public static final String mcsTIPOFORMS_EDICIONLAYOUT = "TIPOFORMS/EDICIONLAYOUT";
    public static final String mcsUltUsuario = "UltUsuario";
    public static final String mcsUltTipoConexion = "UltTipoConexion";
    public static final String mcsLoginPBKDF2 = "LoginPBKDF2";
    public static final String mcsPathPlantilla = "PathPlantilla";

    protected int mlTipo=0;

    protected JParametrosAplicacionModelo moParametrosAplicacion;
    protected JGestionProyecto moGestionProyecto = new JGestionProyecto();
    protected JDevolverTextos moTextos = new JDevolverTextos();
    protected JDatosGeneralesPlugIn moDatosGeneralesPlugIn;
    private ITextosFuente moTextoFuente;
    private JConexion moConex;
    private IAplicacion moAplicacion;
    private JAvisosConj moAvisosConj = new JAvisosConj();
    private JDatosGenerales moTareasAvisos1;
    private JGUIxAvisosDatosGenerales moDatosCorreos;
    

    public JDatosGeneralesAplicacionModelo() {
    }

    /**
     * 0º Si el nombre pasado por parametros tiene la ruta completa se usa este
     * 1º Si existe el fichero en el escritorio se coje ese
     * 2º Si NO se coje el de la ruta base
     */
    private File getRutaFichero(File loFileRoot, String psNombre){
        File loAux;
        //si no se le ha pasado una ruta absoluta se comprueban las rutas relativas
        if(!(psNombre.indexOf("/")>= 0 || psNombre.indexOf("\\")>=0)){
            loAux = new File(psNombre);
            if(!loAux.exists()){
                loAux = new File(loFileRoot, psNombre);
            }
        } else {
            loAux = new File(psNombre);
        }
        return loAux;


    }

    public void inicializar(JParametrosAplicacionModelo poParam){
        moParametrosAplicacion = poParam;
        System.setProperty("org.apache.commons.logging.LogFactory", "utiles.JDepuracionFACTORY");
        //ruta base de los ficheros
        File loFileRoot;
        if(JCadenas.isVacio(poParam.getRutaBase())){
            loFileRoot = new File(System.getProperty("user.home"), "listDatos");
        }else{
            loFileRoot = new File(poParam.getRutaBase());
        }
        loFileRoot.mkdirs();

        JDepuracion.mbDepuracion=true;
        JDepuracion.mlNivelDepuracion=JDepuracion.mclWARNING;
        if(!poParam.isEsServidor()){
            String lsRutaLog=poParam.getRutaLog();
            if(JCadenas.isVacio(lsRutaLog)){
                lsRutaLog = new File(loFileRoot, "log").getAbsolutePath();
            }
            try{
                JDepuracion.moIMPL = new JDepuracionLOG4J(
                        lsRutaLog,
                        poParam.getNombreProyecto());
            }catch(Error e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        
        //Fichero de config. sin extension
        String lsFicheroSinExtension = moParametrosAplicacion.getFicheroConfiguracion();
        if(!poParam.isEsServidor()){
            //le añadimos la extension y devolvemos el fichero en funcion de la ruta base
            File loAux = getRutaFichero(loFileRoot, lsFicheroSinExtension+".xml");
            //fichero CON la ruta y la EXTENSION
            lsFicheroSinExtension=loAux.getAbsolutePath();
            //quitamos la extension
            lsFicheroSinExtension=lsFicheroSinExtension.substring(0, lsFicheroSinExtension.length()-4);
        }
        
        moDatosGeneralesXML = new JDatosGeneralesXML(lsFicheroSinExtension);
        try {
            moDatosGeneralesXML.leer();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        
        JDepuracion.mlNivelDepuracion=getDepuracionNivel();
        
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(),"despues xml");
        
        if(!poParam.isEsServidor()){
            //el fichero de longitudes en funcion de la ruta base
            JGUIxConfigGlobalModelo.getInstancia().setFicheroLongTablas( 
                    getRutaFichero(loFileRoot, "ConfigurationLong"+poParam.getNombreProyecto()+".xml").getAbsolutePath()
                    );
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,getClass().getName(),"despues setFicheroLongTablas");
        
        //añadimos los plugin
        for(int i = 0 ; i < poParam.getPlugIn().length; i++){
            try{
            getDatosGeneralesPlugIn().getPlugInManager().addPlugIn(
                    poParam.getPlugIn()[i]
                );
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,getClass().getName(),"despues cargar plugin " + poParam.getPlugIn()[i]);
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
        }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,getClass().getName(),"despues cargar TODOS plugin");
        if(!poParam.isEsServidor()){
            //añadimos el pluging de seguridad
            if(moParametrosAplicacion.getPlugInSeguridadRW()!=null){
                try {
                    JPlugInSeguridad loPlugSegu = new JPlugInSeguridad(
                            moParametrosAplicacion.getPlugInSeguridadRW()
                            );

                    getDatosGeneralesPlugIn().getPlugInManager().crearPlugInSeguridad();
                    getDatosGeneralesPlugIn().getPlugInManager().getPlugInSeguridad().addPlugIn(loPlugSegu);
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }

            JGUIxConfigGlobalModelo.getInstancia().setPlugInFactoria(getDatosGeneralesPlugIn());
        }
        JGUIxConfigGlobalModelo.getInstancia().setTextosForms(getTextosForms());
        JGUIxConfigGlobalModelo.getInstancia().setGestionProyecto(moGestionProyecto);
        JGUIxConfigGlobalModelo.getInstancia().setAvisosConj(moAvisosConj);
        JGUIxConfigGlobalModelo.getInstancia().setPlugInSeguridad(getParametrosAplicacion().getPlugInSeguridadRW());
        
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,getClass().getName(),"despues PlugInSeguridadRW");

    }
    public static boolean mbComparar(String psCadena1, String psCadena2){
        boolean lbResult = true;
        if(psCadena1==null || psCadena1.equals("")){
            if(psCadena2==null || psCadena2.equals("")){
                lbResult = true;
            }else{
                lbResult = false;
            }
        }else{
            if(psCadena2==null || psCadena2.equals("")){
                lbResult = false;
            }else{
                lbResult = (psCadena1.equals(psCadena2));
            }
        }

        return lbResult;
    }
    public JDatosGeneralesPlugIn getDatosGeneralesPlugIn(){
        if(moDatosGeneralesPlugIn==null){
            moDatosGeneralesPlugIn = new JDatosGeneralesPlugIn(this);
        }
        return moDatosGeneralesPlugIn;
    }

    public int getTipoBD(){
        return mlTipo;
    }
    public void setServidor(final String psNombre) throws Throwable {
        JConexionIO loIO = new JConexionIO();
        loIO.setLector(getDatosGeneralesXML());

        moConex = loIO.leerListaConexiones().get(psNombre);

        mlTipo = getConex().getConexion().getTipoConexion();

        switch(mlTipo){
            case JServerServidorDatos.mclTipoBD:
                    if(isSincronizadoXML()){
                        setServer(new utilesSincronizacion.sincronizacion.JServerServidorDatosBDSincro(
                                getConex().getConexion().getClase(), getConex().getConexion().getURL(),
                                getConex().getConexion().getUSUARIO(), getConex().getConexion().getPASSWORD(),
                                String.valueOf(getConex().getConexion().getTipoBD())
                            ));
                        ((utilesSincronizacion.sincronizacion.JServerServidorDatosBDSincro)getServer()).setConstrucEstruc (
                                new utilesBD.estructuraBD.JConstructorEstructuraBDConnection(
                                    ((utilesSincronizacion.sincronizacion.JServerServidorDatosBDSincro)getServer()).getConec(),
                                    true));
                    }else{
                        seguridad();
                        setServer(new JServerServidorDatosBDPool(
                                getConex().getConexion().getClase(), getConex().getConexion().getURL(),
                                getConex().getConexion().getUSUARIO(), getConex().getConexion().getPASSWORD(),
                                String.valueOf(getConex().getConexion().getTipoBD())
                            ));
                    }

                break;
            default:
                seguridad();
                setServer(new ListDatos.JServerServidorDatos(
                        mlTipo,  getConex().getConexion().getURL(),
                        "selectDatos.ctrl", "guardarDatos.ctrl")
                        );
                ((JServerServidorDatos)getServer()).setConstrucEstruc(
                        new JConstructorEstructuraBDInternet(
                                (JServerServidorDatos)getServer()
                                )
                            );

        }
//        if(moParam.getEstablecerServidor()!=null){
//            moParam.getEstablecerServidor().setServidor(getServer());
//        }
    }
    protected void seguridad()  {
        //seguridad para https sin comprobar con servidor certificados
        try {
            SSLContext localSSLContext = SSLContext.getInstance("SSL");
            HttpsURLConnection.setDefaultHostnameVerifier((HostnameVerifier) new utilesGUIxSeguridad.https.JTramitacionOnLineHostNameVerifier(true));
            localSSLContext.init(new KeyManager[]{new utilesGUIxSeguridad.https.JTramitacionOnLineKeyManager()}, new TrustManager[]{new utilesGUIxSeguridad.https.JTramitacionOnLineTrustManager(true)}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext.getSocketFactory());
        } catch (Error ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex.toString());
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex.toString());
        }
    }
    public boolean isSincronizado(){
        return !getServer().getClass().isAssignableFrom(JServerServidorDatosBD.class) ||
               getServer().getClass().isAssignableFrom(utilesSincronizacion.sincronizacion.JServerServidorDatosBDSincro.class);
    }
    public  boolean isSincronizadoXML(){
        boolean lbSincronizado = false;
        try{
            lbSincronizado = moDatosGeneralesXML.getPropiedad(mcsSINCRONIZADO, "0").equals("1");
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e.toString());
        }
        return lbSincronizado;
    }
    public  boolean isLoginPBKDF2(){ 
        boolean lbLoginPBKDF2 = false;
        try{
            lbLoginPBKDF2 = moDatosGeneralesXML.getPropiedad(mcsLoginPBKDF2, "0").equals("1");
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e.toString());
        }
        return lbLoginPBKDF2;
    }

    /**
     * recupera el usuario
     * @param psUsuario Codigo usuario
     * @throws java.lang.Exception error
     */

    public void recuperarUsuario(String psUsuario) throws Throwable {
        if(moParametrosAplicacion.getUsuario().recuperarUsuario(moServer, psUsuario)){
            msCodUsuario = moParametrosAplicacion.getUsuario().getCodUsuario();
            msUsuario = moParametrosAplicacion.getUsuario().getNombre();
            getPARAMETROS().setUsuario(msCodUsuario);
            moParametrosAplicacion.getUsuario().aplicarFiltrosPorUsuario(moServer, psUsuario, msCodUsuario);
        }
    }

    public void hacerLogin(String psLogin, String psPassWord) throws Throwable {
        if(mlTipo==JServerServidorDatos.mclTipoBD){
            //recuperamos el usuario
            if(moParametrosAplicacion.getUsuario().recuperarUsuario(moServer,psLogin)){
                //comprobamos contraseña
                if(!moParametrosAplicacion.isLoginPBKDF2()) {
                    if(psPassWord.equalsIgnoreCase(moParametrosAplicacion.getUsuario().getPassWord().trim())){
                        recuperarUsuario(psLogin);
                    }else{
                        throw new Exception("Contraseña incorrecta");
                    }
                } else {
                    if(PBKDF2WithHmacSHA1.ValidarPassword(psPassWord, moParametrosAplicacion.getUsuario().getPassWord().trim())) {
                        recuperarUsuario(psLogin);
                    }else{
                        throw new Exception("Contraseña incorrecta");
                    }
                }
            }else{
                throw new Exception("Usuario no existe");
            }
        }else{

            JServerServidorDatosInternetLogin loLogin = new
                    JServerServidorDatosInternetLogin(psLogin, psPassWord);
            ((JServerServidorDatos)moServer).setLogin(loLogin);
            if(loLogin.autentificar()){
                recuperarUsuario(psLogin);
            }else{
                throw new Exception("Login INCORRECTO");
            }
        }
    }

    public boolean getComprobarClave(JSTabla poTabla, String[] pasValores) throws Exception {
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual, 
            poTabla.moList.getFields().malCamposPrincipales(), pasValores);
        loFiltro.inicializar(poTabla.moList.msTabla, poTabla.moList.getFields().malTipos(), poTabla.moList.getFields().msNombres());
        poTabla.recuperarFiltradosNormal(loFiltro, false);
        if (poTabla.moList.moveFirst()) 
            return true;
        else
            return false;
    }

    public String getDireccionRemoto(){
            return moDatosGeneralesXML.getPropiedad(JDatosGeneralesXML.mcsCONEXIONSERVIDOR + "/" + JDatosGeneralesXML.mcsParametroRemoto, "http://creativa3d.com:8080/superMercadosServidor/");
    }

    public String getLookAndFeel(){
            return moDatosGeneralesXML.getPropiedad(mcsLookAndFeel);
    }
    public  String getLookAndFeelTema(){
            return moDatosGeneralesXML.getPropiedad(mcsLookAndFeelTema);
    }

    public void mostrarPropiedades() throws Exception{
    }
    
    public JParametrosAplicacionModelo getParametrosAplicacion(){
        return moParametrosAplicacion;
    }
    
    
    public void autentificar(String user,String pass) throws Throwable{
        String lsNombreServidor;
        
        //Seleccionamos el servidor
        JConexionIO loIO = new JConexionIO();
        loIO.setLector(getDatosGeneralesXML());
        moConex = loIO.leerListaConexiones().get(0);        
        
        lsNombreServidor = moConex.getNombre();

        autentificar(lsNombreServidor, user, pass);
    }
    

    public void setAplicacion(IAplicacion poAplicacion){
        moAplicacion = poAplicacion;
    }
    public IAplicacion getAplicacion(){
        return moAplicacion;
    }
    
    public void mostrarFormPrincipal() throws Exception{
        moAplicacion.mostrarFormPrincipal();
    }
    public void mostrarLogin() throws Exception{
        moAplicacion.mostrarLogin();
    }
    public void mostrarPropiedadesConexionBD() throws Exception{
        moAplicacion.mostrarPropiedadesConexionBD();
    }
    
    //Autentificar directamente con el nombre del servidor
    public void autentificar(String psServidor, String psLogin, String psPassWord) throws Throwable {
        setServidor(psServidor);
        try {
            hacerLogin(psLogin, psPassWord);
        } catch (Throwable e) {
            setServer(null);
            throw e;
        }
        String lsCon = getDatosGeneralesXML().getPropiedad(mcsUltTipoConexion);
        String lsUsu = getDatosGeneralesXML().getPropiedad(mcsUltUsuario);

        getDatosGeneralesXML().setPropiedad(mcsUltTipoConexion, psServidor);
        getDatosGeneralesXML().setPropiedad(mcsUltUsuario, psLogin);
        try {
            if (!(mbComparar(lsCon, getDatosGeneralesXML().getPropiedad(mcsUltTipoConexion))
                    && mbComparar(lsUsu, getDatosGeneralesXML().getPropiedad(mcsUltUsuario)))) {

                getDatosGeneralesXML().guardarFichero();

            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }
    public String getUltUsuario() {
        return getDatosGeneralesXML().getPropiedad(mcsUltUsuario);
    }

    public String getUltTipoConexion() {
        try {
            return getDatosGeneralesXML().getPropiedad(mcsUltTipoConexion);
        } catch (Exception e) {
            return "";
        }
    }
    
    

    public int getDepuracionNivel(){
        try{
            return Integer.valueOf(moDatosGeneralesXML.getPropiedad(mcsDepuracionNivel, String.valueOf(JDepuracion.mlNivelDepuracion))).intValue();
        }catch(Exception e){
        }
        return JDepuracion.mclWARNING;
        
    }
    

    public String getCodUsuario() {
        return msCodUsuario;
    }

    public String getUsuario() {
        return msUsuario;
    }


    public void mostrarEdicion(IFormEdicion poPanel, IFormEdicionNavegador poPanelNave, Object poPanelMismo, int plTipoEdicion) throws Exception {
        getMostrarPantalla().mostrarEdicion(poPanel, poPanelNave, poPanelMismo, plTipoEdicion);
    }

    public void mostrarEdicion(IFormEdicion poPanel, Object poPanelMismo) throws Exception {
        getMostrarPantalla().mostrarEdicion(poPanel, poPanelMismo);
    }

    public void mostrarEdicion(IFormEdicionNavegador poPanel, Object poPanelMismo) throws Exception {
        getMostrarPantalla().mostrarEdicion(poPanel, poPanelMismo);
    }

    public void mostrarFormPrinci(IPanelControlador poControlador, int plAncho, int plAlto) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poControlador, plAncho, plAlto);
    }
    public void mostrarFormPrinci(IPanelControlador poControlador, int plAncho, int plAlto, int plTipoForm, int plTipoMostrar) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poControlador, plAncho, plAlto, plTipoForm, plTipoMostrar);
    }
    public void mostrarFormPrinci(Object poPanel, int plAncho, int plAlto, int plTipoMostrar) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poPanel, plAncho, plAlto, plTipoMostrar);
    }
    public void mostrarForm(JMostrarPantallaParam poParam) throws Exception {
        getMostrarPantalla().mostrarForm(poParam);
    }
    public void cerrarForm(Object poForm) {
        getMostrarPantalla().cerrarForm(poForm);
    }
    public void addMostrarListener(IMostrarPantallaListener poListener) {
        getMostrarPantalla().addMostrarListener(poListener);
    }
    public void removeMostrarListener(IMostrarPantallaListener poListener) {
        getMostrarPantalla().removeMostrarListener(poListener);
    }
    
    
    public void mostrarFormPrinci(IPanelControlador poControlador) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poControlador);
    }

    public void mostrarForm(IMostrarPantallaCrear poPanel) throws Exception {
        getMostrarPantalla().mostrarForm(poPanel);
    }

    public void mostrarOpcion(Object poActividad, String psTitulo, Runnable poSI, Runnable poNO) {
        getMostrarPantalla().mostrarOpcion(poActividad, psTitulo, poSI, poNO);
    }

    public void mensajeErrorYLog(Object poContex, Throwable e, Runnable poOK) {
        getMostrarPantalla().mensajeErrorYLog(poContex, e, poOK);
    }

    public void mensaje(Object poPadre, String psMensaje, int plMensajeTipo, Runnable poOK) {
        getMostrarPantalla().mensaje(poPadre, psMensaje, plMensajeTipo, poOK);
    }
    public void mensajeFlotante(Object poPadre, String psMensaje) {
        getMostrarPantalla().mensajeFlotante(poPadre, psMensaje);
    }
    public JMostrarPantallaParam getParam(String psNumeroForm) {
        return getMostrarPantalla().getParam(psNumeroForm);
    }

    public void setActividad(Object poAct) {
        getMostrarPantalla().setActividad(poAct);
    }

    public Object getContext() {
        return getMostrarPantalla().getContext();
    }
    /**icono de la aplicación */
    public Object getImagenIcono(){
        return getMostrarPantalla().getImagenIcono();
    }
    /**icono de la aplicación */
    public void setImagenIcono(Object moImagenIcono){
        getMostrarPantalla().setImagenIcono(moImagenIcono);
    }    
    

    public Object getActiveInternalFrame() {
        return getMostrarPantalla().getActiveInternalFrame();
    }
    public void setMostrarPantalla(IMostrarPantalla poMostrar){
        super.setMostrarPantalla(poMostrar);
        //config defecto de mostrar pantalla
        JGUIxConfigGlobalModelo.getInstancia().setMostrarPantalla(poMostrar);
    }

    public JGestionProyecto getGestionProyecto(){
        return moGestionProyecto;
    }

    public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
        return moGestionProyecto.getTabla(poServer, psTabla);
    }

    public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        return moGestionProyecto.getControlador(poServer, poMostrar, psTabla, psTablaRelac, poDatosRelac);
    }

    public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
        return moGestionProyecto.getParamPanelBusq(poServer, poMostrar, psTabla);
    }

    public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
        moGestionProyecto.mostrarEdicion(poServer, poMostrar, psTabla, poFila);
    }

    public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
        return moGestionProyecto.buscar(poServer, poMostrar, psTabla);
    }
    public synchronized void setTextosFuente(ITextosFuente poTextoFuente){
        moTextoFuente=poTextoFuente;
        moTextos.setFuente(poTextoFuente);
    }
    public synchronized ITextosFuente getTextosFuente(){
        if(moTextoFuente==null){
            try{
                setTextosFuente(new JTextosFuente(ResourceBundle.getBundle("CaptionTablas" + getParametrosAplicacion().getNombreProyecto())));
            }catch(Throwable e){
                try{
                    setTextosFuente(new JTextosFuente(ResourceBundle.getBundle("CaptionTablas")));
                }catch(Throwable e1){
                    setTextosFuente(new JTextosFuente(new JLectorFicherosParametros("CaptionTablas.properties")));
                    JDepuracion.anadirTexto(getClass().getName(), e1);
                }
            }
        }
        return moTextoFuente;
    }
    public void setTextosForms(JDevolverTextos poTextos){
        moTextos = poTextos;
        JGUIxConfigGlobalModelo.getInstancia().setTextosForms(moTextos);
    }
    public JDevolverTextos getTextosForms(){
        getTextosFuente();//se llama para q se instancie moTextosFuente en caso de q sea null
        return moTextos;
    }

    /**
     * @return the moConex
     */
    public JConexion getConex() {
        return moConex;
    }

    public void lanzaActualizarEstructuraYDatosEsperar() throws Throwable{
        JActualizarEstrucProc loPro = new JActualizarEstrucProc(
                getParametrosAplicacion().getEstructuraLista(),
                getServer());
        loPro.procesar();

    }
    public void lanzaActualizarEstructuraYDatos(){
        JActualizarEstrucProc loPro = new JActualizarEstrucProc(
                getParametrosAplicacion().getEstructuraLista(),
                getServer());
        getThreadGroup().addProcesoYEjecutar(loPro);
    }

    public JAvisosConj getAvisos(){
        return moAvisosConj;
    }
    public void cerrarAplicacion(){
        
    }

    public IFilaDatos mostrarBusqueda(IConsulta loConsulta, JSTabla loTabla) throws Exception {
        IFilaDatos loResult = null;
        JBusqueda loBusq = new JBusqueda(
                loConsulta,
                loConsulta.getList().msTabla);
        loBusq.mostrarFormPrinci();

        if (loBusq.getIndex() >= 0) {
            loTabla.recuperarFiltradosNormal(
                    new JListDatosFiltroElem(
                            JListDatos.mclTIgual,
                            loTabla.getList().getFields().malCamposPrincipales(),
                            loConsulta.getList().getFields().masCamposPrincipales()), false);

            loResult = loTabla.moList.moFila();
        }

        return loResult;

    }

    public void mostrarBusqueda(final IConsulta loConsulta, final JSTabla loTabla, final CallBack<IFilaDatos> poCallBack) throws Exception {
        JBusqueda loBusq = new JBusqueda(
                loConsulta,
                loConsulta.getList().msTabla);
        loBusq.mostrarBusq(new CallBack<IPanelControlador>() {
            public void callBack(IPanelControlador poControlador) {
            if (poControlador.getIndex() >= 0) {
                try {
                    loTabla.recuperarFiltradosNormal(
                            new JListDatosFiltroElem(
                                    JListDatos.mclTIgual,
                                    loTabla.getList().getFields().malCamposPrincipales(),
                                    loConsulta.getList().getFields().masCamposPrincipales()), false);
                    
                    poCallBack.callBack(loTabla.moList.moFila());
                } catch (Exception ex) {
                    getMostrarPantalla().mensajeErrorYLog(null, ex, null);
                }
            }            }
        });

    }
    

    public synchronized utilesGUIxAvisos.calendario.JDatosGenerales getTareasAvisos1() throws Exception {
        if (moTareasAvisos1 == null) {
            moTareasAvisos1 = new utilesGUIxAvisos.calendario.JDatosGenerales();
            moTareasAvisos1.setSMS(false);
            moTareasAvisos1.seteMail(true);
            moTareasAvisos1.setServer(new JServerServidorDatosProxy(this));
            moTareasAvisos1.setMostrarPantalla(this);
            moTareasAvisos1.setDatosRelacionados(true);
            moTareasAvisos1.getPlanificador().arrancarTodo(true, false, false);
            JGUIxConfigGlobalModelo.getInstancia().setDatosGeneralesCalendario(moTareasAvisos1);
            
        }
        return moTareasAvisos1;
    }

    public static JGUIxAvisosDatosGenerales getDatosCorreos(IServerServidorDatos poServer) throws Exception{
        JGUIxAvisosDatosGenerales loDatosCorreos = new JGUIxAvisosDatosGenerales();
        
        JTEECUENTASCORREO loCC = new JTEECUENTASCORREO(poServer);
        loCC.recuperarTodosNormalSinCache();
        loDatosCorreos.getListaCorreos().clear();
        if(loCC.moveFirst()){
            do{
                loDatosCorreos.getListaCorreos().add(loCC.getCorreo());
            }while(loCC.moveNext());
        }
        JTEEDATOSGENERALES2 loDatos = new JTEEDATOSGENERALES2(poServer);
        loDatosCorreos.setSMSTipo(loDatos.getSMSTipo());
        loDatosCorreos.getSMS().setSMSClave(loDatos.getSMSClave());
        loDatosCorreos.getSMS().setSMSLicencia(loDatos.getSMSLicencia());
        loDatosCorreos.getSMS().setSMSUsuario(loDatos.getSMSUsuario());
        loDatosCorreos.setGuardarHistorico(false);

        return loDatosCorreos;
    }    
    public synchronized JGUIxAvisosDatosGenerales getDatosCorreos() throws Exception{
        if(moDatosCorreos==null){
            moDatosCorreos = getDatosCorreos(getServer());
            moDatosCorreos.setPathPlantilla(getPathPlantilla());
            moDatosCorreos.setServer(getServer());
            moDatosCorreos.setGuardarHistorico(true);
            JGUIxConfigGlobalModelo.getInstancia().setDatosGeneralesAvisos(moDatosCorreos);
        }
        return moDatosCorreos;
    }    
    
    public String getPathPlantilla() {
        try {
            return getDatosGeneralesXML().getPropiedad(mcsPathPlantilla);
        } catch (Throwable ex) {
            return "";
        }
    }
    
    public static String getCalendarioDefecto(IServerServidorDatos poServer) throws Exception{
        if(msCalendarioDefecto==null){
            utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO loCalen = new utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO(poServer);
            loCalen.recuperarTodosNormalSinCache();
            if(loCalen.moveFirst()){
                msCalendarioDefecto = loCalen.getCALENDARIO().getString();
            }else{
                msCalendarioDefecto="";
            }
        }
        return msCalendarioDefecto;
    }    
}
