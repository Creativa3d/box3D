

package utilesEjecutar;

import utilesEjecutar.ejecutar.IEjecutarInstruccion;
import utilesEjecutar.ejecutar.JEjecutarInstruccion;
import utilesEjecutar.ejecutar.JEjecutarJava;
import utilesEjecutar.ejecutar.acciones.JBorrarCarpeta;
import utilesEjecutar.ejecutar.acciones.JLink;
import utilesEjecutar.ejecutar.controlflujo.JCondicion;
import utilesEjecutar.ejecutar.destinos.JDestinoCarpeta;
import utilesEjecutar.fuentes.IFuente;
import utilesEjecutar.fuentes.JFuenteArchivo;
import utilesEjecutar.fuentes.JFuenteCarpetas;
import utilesEjecutar.fuentes.JFuenteConjArchivos;
import utilesEjecutar.fuentes.JFuenteFTP;
import utilesEjecutar.fuentes.JFuenteHTTP;


public class JAbstractFactoryEjecutar implements IAbstractFactoryEjecutar {
    private static final String mcsDestino = "destino";
    private static final String mcsEjecutar = "ejecutar";
    private static final String mcsEjecutarJava = "ejecutarJava";
    public static final String mcscarpeta="carpeta";
    public static final String mcsftp="ftp";
    public static final String mcshttp="http";
    public static final String mcsarchivos="archivos";
    public static final String mcsarchivo="archivo";
    public static final String mcsCondicion="condicion";
    public static final String mcsBorrar="borrar";
    public static final String mcsCrearAccesoDirecto="link";

    public IFuente getFuente(String psTipo, JControladorCoordinadorEjecutar poCoordinador) {
        IFuente loFuente = null;
        if(psTipo.equalsIgnoreCase(mcsftp)){
            loFuente = new JFuenteFTP(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcshttp)){
            loFuente = new JFuenteHTTP(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcscarpeta)){
            loFuente = new JFuenteCarpetas(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsarchivos)){
            loFuente = new JFuenteConjArchivos(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsarchivo)){
            loFuente = new JFuenteArchivo(poCoordinador);
        }

        return loFuente;
    }

    public IEjecutarInstruccion getEjecutar(String psTipo, JControladorCoordinadorEjecutar poCoordinador) {
        IEjecutarInstruccion loDestino = null;
        if(psTipo.equalsIgnoreCase(mcsDestino)){
            loDestino = new JDestinoCarpeta(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsEjecutar)){
            loDestino = new JEjecutarInstruccion(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsEjecutarJava)){
            loDestino = new JEjecutarJava(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsCondicion)){
            loDestino = new JCondicion(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsBorrar)){
            loDestino = new JBorrarCarpeta(poCoordinador);
        }
        if(psTipo.equalsIgnoreCase(mcsCrearAccesoDirecto)){
            loDestino = new JLink(poCoordinador);
        }

        return loDestino;
    }

}
