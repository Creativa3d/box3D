/*
 * ISax.java
 *
 * Created on 12 de enero de 2005, 10:32
 */

package utiles.xml.sax;

/**Interfaz que debe cumplir el receptor del xml procesado*/
public interface ISax {

    /**
     * empiezo a parsear el documento
     * @throws Exception error
     */
    public void startDocument () throws Exception;

    /**
     * fin de parseo el documento
     * @throws Exception error
     */
    public void endDocument () throws Exception;

    /**
     * empieza una etiqueta
     * @param name nombre etiqueta
     * @param poAtributos lista de atributos
     * @throws Exception error
     */
    public void startElement (String name, JAtributos poAtributos) throws Exception;

    /**
     * fin de una etiqueta
     * @param poEtiqueta objeto etiqueta
     * @throws Exception error
     */
    public void endElement (JEtiqueta poEtiqueta) throws Exception;
}
