/*
* JDatosGenerales.java
*
* Creado el 3/11/2011
*/

package paquetesGeneradorInf.gest1;

import ListDatos.IServerServidorDatos;
import ListDatos.config.JDevolverTextos;
import java.util.ResourceBundle;
import paquetesGeneradorInf.gest1.tablasExtend.JActualizarEstruc;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.config.JLectorFicherosParametros;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.aplicacion.actualizarEstruc.JActualizarEstrucProc;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.plugin.IPlugInContexto;


public class JDatosGenerales {
    protected JDevolverTextos moTextos;
    private IPlugInContexto moPlugInContexto;
    
    /** Creates a new instance of JUsuarioActual */
    public JDatosGenerales() {
        super();
    }
    
    public void inicializar(){
        
    }
    
    /**
     * Textos de los formularios
     */
    public JDevolverTextos getTextosForms(){
        if(moTextos==null){
            try{
                moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablaspaquetesGeneradorInf"));
            }catch(Throwable e){
                try{
                    moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablaspaquetesGeneradorInf"));
                }catch(Throwable e1){
                    moTextos = new JDevolverTextos(new JLectorFicherosParametros("CaptionTablaspaquetesGeneradorInf.properties"));
                    JDepuracion.anadirTexto(JDatosGenerales.class.getName(), e1);
                }
            }
        }
        return moTextos;
    }
    
    /**
     * El servidor datos
     */
    public IServerServidorDatos getServer(){
        return getPlugInContexto().getServer();
    }
    
    /**
     * Para mostrar las pantallas
     */
    public IMostrarPantalla getMostrarPantalla(){
        IMostrarPantalla loM=null;
        if(getPlugInContexto()!=null){
            loM = getPlugInContexto().getMostrarPantalla();
        }
        if(loM==null){
            loM=JGUIxConfigGlobal.getInstancia().getMostrarPantalla();
        }
        return loM;
    }
    
    public void lanzaActualizarEstructuraYDatosEsperar() throws Throwable{
        IListaElementos loList = new JListaElementos();
        loList.add(new JActualizarEstruc());
        JActualizarEstrucProc loPro = new JActualizarEstrucProc(
                loList,
                getServer());
        loPro.procesar();

    }
    
    public synchronized void setPlugInContexto(IPlugInContexto poPlug){
        moPlugInContexto=poPlug;
    }
    public synchronized IPlugInContexto getPlugInContexto(){
        return moPlugInContexto;
    }
}
