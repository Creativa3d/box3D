
package utilesEjecutar;

import utilesEjecutar.ejecutar.IEjecutarInstruccion;
import utilesEjecutar.fuentes.IFuente;

public interface IAbstractFactoryEjecutar {
    /**Crea una fuente del tipo dado*/
    public IFuente getFuente(String psTipo, JControladorCoordinadorEjecutar poCoordinador);
    /**Crea una ejecucion del tipo dado*/
    public IEjecutarInstruccion getEjecutar(String psTipo, JControladorCoordinadorEjecutar poCoordinador);

}
