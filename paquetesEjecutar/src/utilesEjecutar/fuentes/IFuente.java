
package utilesEjecutar.fuentes;

import utiles.IListaElementos;
import utiles.xml.sax.JAtributos;


public interface IFuente {
    /**
     * Devuelve la lista de IArchivo de la fuente
     * @return 
     * @throws java.lang.Throwable
     */
    public IListaElementos getLista() throws Throwable;
    /**
     * Establece la lista de IArchivo de la fuente
     * @param poLista
     */
    public void setLista(IListaElementos  poLista);
    /**
     * Devuelve el nombre de la fuente
     * @return 
     * @throws java.lang.Throwable
     */
    public String getNombre()throws Throwable;
    /**
     * Establece los atributos e hijos del xml
     * @param poLista
     * @param poHijos
     * @throws java.lang.Throwable
     */
    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable;
    /**
     * Desconecta la fuente, al final del proceso
     * @throws java.lang.Throwable
     */
    public void desconectar() throws Throwable;
}
