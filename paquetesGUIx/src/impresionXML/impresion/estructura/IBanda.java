/*
 * IElementos.java
 *
 * Created on 14 de septiembre de 2004, 10:18
 */

package impresionXML.impresion.estructura;

/**Intefaz banda, defina una banda en impresión, es como una unidad lógica dinámica y lo de abajo se reposiciona*/
public interface IBanda extends IImprimir {
    /**
     * Si se ajusta a lo que tiene que imprimir
     * @return Si se ajusta 
     */
    public boolean getAutoExtensible();
    /**
     * Alto de la banda
     * @return alto
     */
    public float getAlto();
//    public float getAncho();
    
    /**
     * Inserta una banda y lo devuelve, esto es para bandas recursivas
     * @return banda
     */
    public IBanda insertarBanda();
    /**
     * Inserta una tabla y lo devuelve
     * @return tabla
     */
    public ITable insertarTabla();
    /**
     * Inserta un párrafo y lo devuelve
     * @return párrafo
     */
    public IParrafo insertarParrafo();
    /**
     * Inserta una imagen y lo devuelve
     * @return imagen
     */
    public IImagen insertarImagen();
    /**
     * Inserta un texto y lo devuelve
     * @return texto libre
     */
    public ITextoLibre insertarTexto();
    /**
     * Inserta una línea y lo devuelve
     * @return línea
     */
    public ILinea insertarLinea();
}
