/*
 * IXML.java
 *
 * Created on 23 de noviembre de 2004, 11:07
 */

package impresionXML.listDatos;

import java.io.*;

import impresionXML.tools.*;
//SAX
import org.xml.sax.SAXException;
import javax.xml.transform.Source;
import org.xml.sax.InputSource;

/**Interfaz que cumple los parseadores de fo*/
public interface IXML {
    /**
     * Parse lo que es el JListDatos + formato
     * @param handler handler
     * @throws IOException error
     * @throws SAXException error
     */
    public void parse(EasyGenerationContentHandlerProxy handler)throws IOException, SAXException ;
    /**
     * Devuelve el xml en formato Source
     * @return source
     */
    public Source getSource();
    /**
     * Devuelve el xls que procesa el xml de esta clase en formato Source
     * @return source
     */
    public Source getXSL();
    /**
     * crea el xsl-fo en formato InputSource
     * @return InputSource
     */
    public InputSource getXSLFO();
    /**
     * llama a los métodos de JConstruirXSL según propiedades, y este crea el xsl que procesa el xml para convertirlo a xsl-fo
     * @param poContruirXSL constructor de xsl
     */
    public void rellenarXSLParcial(JConstruirXSL poContruirXSL);
    /**
     * llama a los métodos de JConstruirXSLFO según propiedades, y este crea el xsl-fo diréctamente
     * @param poContruirXSLFO constructor
     */
    public void rellenarXSLParcialFO(JConstruirXSLFO poContruirXSLFO);
}
