
package utilesEjecutar.ejecutar;

import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**
 * controlador de ejecuciones, mantiene la lista de ejecuciones
 */
public class JEjecucionesControlador implements IEjecutarInstruccion {
    private static final String mcsNombre = "nombre";
    private static final String mcsEnable = "enable";

    private IListaElementos moLista;
    private JControladorCoordinadorEjecutar moCoordinador;
    private String msNombre = "";
    private boolean mbHabilitada=true;

    public JEjecucionesControlador(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador=poCoordinador;
        moLista = new JListaElementos();
    }

    /**
     * Devuelve la lista de IEjecutarInstruccion
     */
    public IListaElementos getEjecuciones(){
        return moLista;
    }
    /**
     * Devuelve un IEjecutarInstruccion q tenga el nombre psNombre
     */
    public IEjecutarInstruccion getEjecutar(String psNombre) throws Throwable{
        IEjecutarInstruccion loFuente = null;
        IEjecutarInstruccion loAux = null;
        for(int i = 0; i < moLista.size() && loFuente == null; i++ ){
            loAux = (IEjecutarInstruccion) moLista.get(i);
            if(loAux.getNombre()!=null && loAux.getNombre().equalsIgnoreCase(psNombre)){
                loFuente = loAux;
            }
        }
        return loFuente;
    }
    public boolean ejecutarFicticio() throws Throwable {
        return ejecutar(true);
    }
    public boolean ejecutar() throws Throwable {
        return ejecutar(false);
    }
    /**Ejecuta la lista de IEjecutarInstruccion*/
    public boolean ejecutar(boolean pbFicticio) throws Throwable {
        boolean lbResult = false;
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Comienzo ejecutar");

        //para cada ejecucion ejecutamos el ejecutar
        for(int i = 0 ; i < moLista.size()  && !moCoordinador.isCancelado(); i++){
            try{
                IEjecutarInstruccion loDestino = (IEjecutarInstruccion) moLista.get(i);
                if(loDestino.isHabilitada()){
                    if(pbFicticio){
                        lbResult |= loDestino.ejecutarFicticio();
                    }else{
                        lbResult |= loDestino.ejecutar();
                    }
                }
            }catch(Throwable e){
               moCoordinador.addError(getClass().getName(), e, true);
            }
        }
        return lbResult;
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEnable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }

        }
    }

    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }

    public void inicializar() {
    }

    public void finalizar() {
    }
    
}
