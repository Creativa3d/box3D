/*
 * JServerCrearObjetoXML.java
 *
 * Created on 12 de enero de 2005, 17:23
 */

package ListDatos;

import java.io.*;

import utiles.xml.sax.*;
import utiles.*;

/**coge un entrada en xml y la transforma en un objeto*/
public class JXMLServerCrearObjeto implements ISax {
    private final JListaElementos moObjetos = new JListaElementos();
    private final JListaElementos moNombres = new JListaElementos();

    private IXMLDevolverObjetoResultado moCrearObjeto = null;
    
    /**Constructor*/
    public JXMLServerCrearObjeto(){
        moObjetos.add(new JXMLServerCrearJResultado());
        moNombres.add("JResultado");
    }
    
    /**
     * parseamos la entrada y devolvemos el objeto correspondiente
     * @return objeto parseado
     * @param poEntrada flujo de caracterres
     * @throws Exception exception
     */
    public Object parser(InputStream poEntrada) throws Exception {
        JSaxParser loParse = new JSaxParser();
        loParse.parser(poEntrada, this);
        return moCrearObjeto.getObjeto();
    }
    /**
     * anadimos una creacion de objeto
     * @param psNombre nombre de la accion que corresponde en xml
     * @param poCrear objeto creador de ese objeto
     */
    public void addCrearObjeto(String psNombre, IXMLDevolverObjetoResultado poCrear){
        moObjetos.add(poCrear);
        moNombres.add(psNombre);
    }
    
    public void startDocument() throws Exception {
    }
    
    public void endDocument() throws Exception {
    }

    public void startElement(String name, JAtributos poAtributos) throws Exception {
        if(moCrearObjeto!=null){
            moCrearObjeto.startElement(name, poAtributos);
        }
    }
    
    public void endElement(JEtiqueta poEtiqueta) throws Exception {
        if(poEtiqueta.getNombre().compareTo("nombre")==0){
            for(int i =0; i< moNombres.size();i++){
                if(((String)moNombres.get(i)).compareTo(poEtiqueta.getValor())==0){
                    moCrearObjeto = (IXMLDevolverObjetoResultado)moObjetos.get(i);
                }
            }
        }else{
            if(moCrearObjeto!=null){
                moCrearObjeto.endElement(poEtiqueta);
            }
        }
    }
    
    
}
