/*
 * JDatosGenerales.java
 *
 * Creado el 29/7/2008
 */
package utilesFX.aplicacion;

import java.io.File;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utiles.JDepuracion;
import utiles.xml.dom.Element;
import utilesFX.JFXConfigGlobal;
import utilesFX.configForm.JT2CONEXIONES;
import utilesFX.controlProcesos.JProcesoElementoFactoryMethod;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JPanelGenerico2;
import utilesFXAvisos.tablasControladoras.JT2GUIXEVENTOS;
import utilesGUIx.aplicacion.IAplicacion;
import utilesGUIx.aplicacion.JDatosGeneralesAplicacionModelo;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.controlProcesos.JProcesoManejador;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtensionSoloFile;

public class JDatosGeneralesAplicacion extends JDatosGeneralesAplicacionModelo {


    protected Stage primaryStage;
    private JT2GUIXEVENTOS moGUIxEventos;

    public JDatosGeneralesAplicacion() {
        setAplicacion(new IAplicacion() {
            @Override
            public void mostrarFormPrincipal() {
                mostrarFormPrincipalInterno();
            }
            
            @Override
            public void mostrarPropiedadesConexionBD() throws Exception{

                JT2CONEXIONES loConexiones = new JT2CONEXIONES(moDatosGeneralesXML, (moMostrarPantalla == null ? JFXConfigGlobal.getInstancia().getMostrarPantalla():moMostrarPantalla));
                loConexiones.mostrarFormPrinci();

            }

            @Override
            public void mostrarLogin() throws Exception{
                mostrarFormLoginYPrincipal();
            }
        });
    }

    @Override
    public void mostrarPropiedadesConexionBD() throws Exception {

        JT2CONEXIONES loConexiones = new JT2CONEXIONES(moDatosGeneralesXML, (moMostrarPantalla == null ? JFXConfigGlobal.getInstancia().getMostrarPantalla() : moMostrarPantalla));
        loConexiones.mostrarFormPrinci();

    }

    public void mostrarLogin(Runnable poOk, Runnable poCancel) throws Exception{
        JFXConfigGlobal.getInstancia().setInicializadoJAVAFX(true);

        JFormLogin loLogin = new JFormLogin();
        loLogin.setDatos(this, poOk, poCancel);

        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loLogin, 600, 480, JMostrarPantalla.mclEdicionFrame, "Login");
        JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(loParam);
    }
    @Override
    public int getDepuracionNivel(){
        try{
            return Integer.parseInt(moDatosGeneralesXML.getPropiedad(mcsDepuracionNivel, String.valueOf(JDepuracion.mlNivelDepuracion)));
        }catch(Exception e){
        }
        return JDepuracion.mclINFORMACION;
    }

    @Override
    public void autentificar(String psServidor, String user, String pass) throws Throwable {
        autentificar(psServidor, user, pass, this);
    }

    //Autentificar directamente con el nombre del servidor
    public static void autentificar(String psServidor, String psLogin, String psPassWord, JDatosGeneralesAplicacion poDatosGenerales) throws Throwable {
        poDatosGenerales.setServidor(psServidor);
        try {
            poDatosGenerales.hacerLogin(psLogin, psPassWord);
        } catch (Throwable e) {
            poDatosGenerales.setServer(null);
            throw e;
        }
        String lsCon = poDatosGenerales.getDatosGeneralesXML().getPropiedad(mcsUltTipoConexion);
        String lsUsu = poDatosGenerales.getDatosGeneralesXML().getPropiedad(mcsUltUsuario);

        poDatosGenerales.getDatosGeneralesXML().setPropiedad(mcsUltTipoConexion, psServidor);
        poDatosGenerales.getDatosGeneralesXML().setPropiedad(mcsUltUsuario, psLogin);
        try {
            if (!(mbComparar(lsCon, poDatosGenerales.getDatosGeneralesXML().getPropiedad(mcsUltTipoConexion))
                    && mbComparar(lsUsu, poDatosGenerales.getDatosGeneralesXML().getPropiedad(mcsUltUsuario)))) {

                poDatosGenerales.getDatosGeneralesXML().guardarFichero();

            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(JDatosGeneralesAplicacion.class.getName(), ex);
        }
    }
    public void mostrarFormLoginYPrincipal() throws Exception {
        mostrarLogin(() -> {
            mostrarFormPrincipalInterno();
        }, () -> {
            mostrarFormPrincipalInterno();
        });
    }

    private void mostrarFormPrincipalInterno() {
        JFXConfigGlobal.getInstancia().setInicializadoJAVAFX(true);
        final JFormPrincipa1 loFormPrincipal = new JFormPrincipa1();
        loFormPrincipal.inicializar(this);

        Scene scene = new Scene(loFormPrincipal, 800, 600);


        primaryStage=new Stage();
        primaryStage.setTitle(this.getParametrosAplicacion().getCaptionProyecto());
        primaryStage.setScene(scene);
        if(getParametrosAplicacion().getImagenIcono()!=null){
            primaryStage.getIcons().add((Image)getParametrosAplicacion().getImagenIcono());
        }        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());        

        this.setDesktopPane1(
                loFormPrincipal.getDesktopPane()
                , loFormPrincipal
                , loFormPrincipal.getThreadGroup());
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                loFormPrincipal.salir(t);
            }
        });
    }

    public void setDesktopPane1(final Pane poDesktopPane1, IPlugInFrame poFramePrinci, IProcesoThreadGroup poThreadGroup) {
        moProcesoGroup = poThreadGroup;

        setFormPrincipal(poFramePrinci);
        setThreadGroup(poThreadGroup);
        JMostrarPantalla loMostrar = (JMostrarPantalla) JFXConfigGlobal.getInstancia().getMostrarPantalla();
        loMostrar.setDesktopPane1(poDesktopPane1);
        loMostrar.setGrupoThreads(poThreadGroup);

        if(moParametrosAplicacion.getImagenIcono()!=null){
            loMostrar.setImagenIcono(moParametrosAplicacion.getImagenIcono());
        }
        
        loMostrar.setTipoPrincipal(
                Integer.parseInt(moDatosGeneralesXML.getPropiedad(mcsTIPOFORMSPRINCIPALES_TIPO, String.valueOf(JPanelGenerico2.mclTipo)))
        );
        loMostrar.setTipoEdicion(
                Integer.parseInt(moDatosGeneralesXML.getPropiedad(mcsTIPOFORMS_EDICIONLAYOUT, String.valueOf(JMostrarPantalla.mclEdicionFrame)))
        );
        loMostrar.setTipoPrincipalMostrar(
                Integer.parseInt(moDatosGeneralesXML.getPropiedad(mcsTIPOFORMS_PRINCIPALLAYOUT, String.valueOf(JMostrarPantalla.mclEdicionInternal)))
        );

        setMostrarPantalla(loMostrar);
    }

    @Override
    public synchronized IProcesoThreadGroup getThreadGroup() {
        if (moProcesoGroup == null) {
            moProcesoGroup = new JProcesoManejador(new JProcesoElementoFactoryMethod());
        }
        return moProcesoGroup;
    }


    public Element getImagenFondoElem() {
        Element loElem = moDatosGeneralesXML.getElemento(mcsImagenFondo);
        if (loElem == null) {
            loElem = new Element(mcsImagenFondo);
            getDatosGeneralesXML().getDocumento().getRootElement().add(loElem);
        }
        return loElem;
    }
    public String getImagenFondoQueToque(){
        String lsResult = "";
        if(getImagenFondoElem().getValor()!=null && !getImagenFondoElem().getValor().equals("")){
            File loDir = new File(getImagenFondoElem().getValor());
            if(loDir.exists()){
                if(loDir.isDirectory()){
                    File[] loFiles = loDir.listFiles(
                            new JFileChooserFiltroPorExtensionSoloFile("Imágenes",new String[]{"gif","jpeg", "jpg", "png"})
                            );
                    if(loFiles.length>1){
                        lsResult = loFiles [(int)((double)loFiles.length*Math.random())].getAbsolutePath();
                    }else if (loFiles.length==1){
                        lsResult = loFiles[0].getAbsolutePath();
                    } else {
                        lsResult = "";
                    }
                }else{
                    lsResult = loDir.getAbsolutePath();
                }
            }
        }
        return lsResult;
    }

    public void restaurarBotonesOriginales() throws Exception {
        if(getFormPrincipal() instanceof JFormPrincipa1){
            ((JFormPrincipa1)getFormPrincipal()).restaurarBotonesOriginales();
        }
    }
    
    public JT2GUIXEVENTOS getEventos() throws Exception{
        if(moGUIxEventos==null){
            moGUIxEventos = new JT2GUIXEVENTOS(getTareasAvisos1()
                , "", getCalendarioDefecto(getServer()), ""
                , getCodUsuario() );
            moGUIxEventos.getParametros().setPlugInPasados(true);
            
        }
        return moGUIxEventos;
    }
}
