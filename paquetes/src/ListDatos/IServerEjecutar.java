/*
 * IServerEjecutar.java
 *
 * Created on 14 de julio de 2005, 10:14
 */

package ListDatos;

import java.io.Serializable;

public interface IServerEjecutar extends Serializable, ISelectEjecutarComprimido  {

    /**
     * Todo lo q se ejecute en este metodo estara dentro de una
     * transaccion y se ejecutara en el servidor directamente
     *
     * El objeto q implemente este interfaz para ejecutarse debe ser llamado por 
     * ((IServerServidorDatos)moServer).ejecutarServer((IServerEjecutar)Objeto) 
     *
     * @param poServer Objeto de base datos se pasa por parametro
     * @return IResultado un objeto q cumpla el interfaz IResultado (Ejemplo JResultado)
     */
    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception;
    /**
     * Devuelve el Objeto en formato XML
     * @return String xml
     */
    public String getXML();
    /**
     * Devuelve lo parametros
     * @return parametros
     */
    public JServerEjecutarParametros getParametros();
}
