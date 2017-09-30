/*
* JDatosGenerales.java
*
* Creado el 3/11/2011
*/

package utilesDoc;

import ListDatos.IFilaDatos;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import ListDatos.JSTabla;
import ListDatos.config.JDevolverTextos;
import archivosPorWeb.comun.IServidorArchivos;
import archivosPorWeb.comun.JServidorArchivos;
import java.util.ResourceBundle;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.config.JDatosGeneralesXML;
import utiles.config.JLectorFicherosParametros;
import utilesDoc.tablasExtend.IImagenFactoryDoc;
import utilesDoc.tablasExtend.JDocActualizarEstruc;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.actualizarEstruc.JActualizarEstrucProc;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;


public abstract class JDocDatosGeneralesModelo {
    protected static JDevolverTextos moTextos;


    /**
     * Textos de los formularios
     * @return 
     */
    public static JDevolverTextos getTextosForms(){
        if(moTextos==null){
            try{
                moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablasutilesDoc"));
            }catch(Throwable e){
                try{
                    moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablasutilesDoc"));
                }catch(Throwable e1){
                    moTextos = new JDevolverTextos(new JLectorFicherosParametros("CaptionTablasutilesDoc.properties"));
                    JDepuracion.anadirTexto(JDocDatosGeneralesModelo.class.getName(), e1);
                }
            }
        }
        return moTextos;
    }

    public static void mostrarBusqueda(final IConsulta loConsulta, final JSTabla loTabla, final CallBack<IFilaDatos> poCall) throws Exception{

        JBusqueda loBusq = new JBusqueda( 
                loConsulta,
                loConsulta.getList().msTabla);
        loBusq.mostrarBusq(new CallBack<IPanelControlador>() {
            public void callBack(IPanelControlador poControlador) {

                if(poControlador.getIndex()>=0){
                    try {
                        loTabla.recuperarFiltradosNormal(
                                new JListDatosFiltroElem(
                                        JListDatos.mclTIgual,
                                        loTabla.getList().getFields().malCamposPrincipales(),
                                        loConsulta.getList().getFields().masCamposPrincipales())
                                , false);
                        
                        poCall.callBack(loTabla.moList.moFila());
                    } catch (Exception ex) {
                        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(null, ex, null);
                    }
                }
            }
        });


    }


    protected IServidorArchivos moServidorArchivos;
    protected IImagenFactoryDoc moImagenBasicaFactory;
    protected IServidorArchivos moServidorArchivosLocal;
    protected boolean mbSubidaSegundoPlano = true;
    protected JDatosGeneralesXML moXML;

    /** Creates a new instance of JUsuarioActual */
    public JDocDatosGeneralesModelo() {
        super();
    }    
    
    public void inicializar(
            IServidorArchivos poServidorArchivos) throws Exception{

        moServidorArchivos=poServidorArchivos;

        moServidorArchivosLocal = new JServidorArchivos();
        moXML = new JDatosGeneralesXML("ConfiguracionDoc");
        moXML.leer();        
    }

      
    /**
     * El servidor datos
     * @return 
     */
    public IServerServidorDatos getServer(){
        return JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getServer();
    }
    
    /**
     * Para mostrar las pantallas
     * @return 
     */
    public IMostrarPantalla getMostrarPantalla(){
        IMostrarPantalla loM;
        loM=JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla();
        return loM;
    }
    
    
    public void lanzaActualizarEstructuraYDatosEsperar() throws Throwable{
        IListaElementos loList = new JListaElementos();
        loList.add(new JDocActualizarEstruc());
        JActualizarEstrucProc loPro = new JActualizarEstrucProc(
                loList,
                getServer());
        loPro.procesar();

    }
    /**
     * @return the moServidorArchivos
     */
    public IServidorArchivos getServidorArchivos() {
        return moServidorArchivos;
    }

    /**
     * @param poServidorArchivos the moServidorArchivos to set
     */
    public void setServidorArchivos(IServidorArchivos poServidorArchivos) throws Exception {
        inicializar(poServidorArchivos);
    }

    /**
     * @return the moImagenBasicaFactory
     */
    public synchronized IImagenFactoryDoc getImagenBasicaFactory() {
        moImagenBasicaFactory.setServer(JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getServer());
        return moImagenBasicaFactory;
    }
    
    

    /**
     * @param moImagenBasicaFactory the moImagenBasicaFactory to set
     */
    public void setImagenBasicaFactory(IImagenFactoryDoc moImagenBasicaFactory) {
        this.moImagenBasicaFactory = moImagenBasicaFactory;
    }

    /**
     * @return the moServidorArchivosLocal
     */
    public IServidorArchivos getServidorArchivosLocal() {
        return moServidorArchivosLocal;
    }

    /**
     * @return the mbSubidaSegundoPlano
     */
    public boolean isSubidaSegundoPlano() {
        return mbSubidaSegundoPlano;
    }

    /**
     * @param mbSubidaSegundoPlano the mbSubidaSegundoPlano to set
     */
    public void setSubidaSegundoPlano(boolean mbSubidaSegundoPlano) {
        this.mbSubidaSegundoPlano = mbSubidaSegundoPlano;
    }

    /**
     * @return the moXML
     */
    public JDatosGeneralesXML getDatosGeneralesXML() {
        return moXML;
    }

    /**
     * @param moXML the moXML to set
     */
    public void setDatosGeneralesXML(JDatosGeneralesXML moXML) {
        this.moXML = moXML;
    }

}
