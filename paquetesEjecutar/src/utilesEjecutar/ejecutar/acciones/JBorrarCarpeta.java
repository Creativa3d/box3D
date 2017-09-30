

package utilesEjecutar.ejecutar.acciones;

import java.io.File;
import utiles.IListaElementos;
import utiles.JArchivo;
import utiles.JDepuracion;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.ejecutar.IEjecutarInstruccion;

/**
 * Copia las fuentes a una carpeta local
 */
public class JBorrarCarpeta implements IEjecutarInstruccion {
    private static final String mcsNombre = "nombre";
    private static final String mcsCodeBase = "codebase";
    private static final String mcsEnable = "enable";
    private static final String mcsRecursivo = "recursivo";

    private String msCodeBase;
    private String msNombre = "";
    private JControladorCoordinadorEjecutar moCoordinador;
    private boolean mbBorradoAlgo=false;
    private boolean mbFicticio;
    private boolean mbHabilitada=true;
    private boolean mbRecursivo=false;

    public JBorrarCarpeta(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }

    public boolean ejecutar() throws Throwable {
        mbBorradoAlgo = false;

        //si es una fuente en concreto se procesa solo esa fuente
        try{
            mbBorradoAlgo |= borrarFuente(msCodeBase);
        }catch(Throwable e){
            moCoordinador.addError(getClass().getName(), e);
        }

        return mbBorradoAlgo;
    }

    private boolean borrarFuente(String psRuta) throws Throwable {
        boolean lbBorradoAlgo = false;

        File loFileDestino;
        //fichero a borrar en el destino
        loFileDestino = new File(psRuta);

        //si ha cambiado el tamaño o la fecha es menor q la de la fuente
        if(loFileDestino.exists()){
            lbBorradoAlgo = true;
            if(!mbFicticio){

                JDepuracion.anadirTexto(
                        JDepuracion.mclINFORMACION,
                        getClass().getName(),
                        "Se borrará el archivo " +
                            loFileDestino.getAbsolutePath());
                if(!JArchivo.borrarDirectorio(loFileDestino, mbRecursivo)) {
                    JDepuracion.anadirTexto(
                        JDepuracion.mclINFORMACION,
                        getClass().getName(),
                        loFileDestino.getAbsolutePath() + " no se ha borrado");

                }
            }
        }
        return lbBorradoAlgo;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBase)){
                msCodeBase = JControladorCoordinadorEjecutar.getDirLocalconSeparadorCorrecto(loAtrib.getValor());
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEnable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsRecursivo)){
                mbRecursivo = loAtrib.getValor().trim().equals("1");
            }

        }
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public boolean ejecutarFicticio() throws Throwable {
        mbFicticio=true;
        try{
            ejecutar();
        }finally{
            mbFicticio=false;
        }
        return mbBorradoAlgo;
    }

    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }

}
