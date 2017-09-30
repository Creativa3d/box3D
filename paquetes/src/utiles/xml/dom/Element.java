/*
 * Element.java
 *
 * Created on 12 de septiembre de 2006, 8:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utiles.xml.dom;

import java.io.Serializable;
import java.util.Iterator;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utiles.xml.sax.JSaxParser;

public class Element  implements  Serializable {
    private static final long serialVersionUID = 1L;
    
    /**Nombre del atributo*/
    private String msNombre;
    /**valor del atributo*/
    private String msValor;

    private JAtributos moAtributos = new JAtributos();
    private JListaElementos moElementos = new JListaElementos();
    
    public Element(String psNombre,String psValor) {
        msNombre = JSaxParser.reemplazarCaracRaros(psNombre);
        msValor = JSaxParser.reemplazarCaracRaros(psValor);
    }
    public Element(String psNombre) {
        msNombre = psNombre;
    }
    public JAtributos getAttributes(){
        return moAtributos;
    }
    public IListaElementos getChildren(){
        return moElementos;
    }
    public IListaElementos getChildren(String psTag ){
        IListaElementos loElementos = new JListaElementos();
        for(int i = 0; i < moElementos.size();i++){
            Element loElemento = (Element)moElementos.get(i);
            if(loElemento.getName().compareTo(psTag)==0){
                loElementos.add(loElemento);
            }
        }
        return loElementos;
    }
    public Element getChild(String psTag){
        Element loResult = null;
        for(int i = 0; i < moElementos.size();i++){
            Element loElemento = (Element)moElementos.get(i);
            if(loElemento.getName().compareTo(psTag)==0){
                loResult  = loElemento;
                break;
            }
        }
        return loResult ;
    }
    public boolean hasChildren(){
        return moElementos.size()>0;
    }
    public String getTextTrim(){
        return msValor.trim();
    }
    public String getText(){
        return msValor;
    }
    public void addContent(Element poElemento){
        moElementos.add(poElemento);
    }
    public JAtributo getAttribute(String psNombre){
        return moAtributos.getAtributo(psNombre);
    }
    public void setAttribute(JAtributo poAtributo){
        for(int i = 0 ; i < moAtributos.size(); i++){
            JAtributo loAtributo = (JAtributo)moAtributos.get(i);
            if(loAtributo.getName().compareTo(poAtributo.getName())==0){
                moAtributos.remove(i);
                break;
            }
        }
        moAtributos.add(poAtributo);
    }
    public void setAttributes(JAtributos poAtributos){
        moAtributos = poAtributos;
    }
    public void setText(String psValor){
        msValor = psValor;
    }

    /**
     * Nombre de la atributo
     * @return nombre
     */
    public String getNombre(){
        return msNombre;
    }
    /**
     * Nombre de la atributo
     * @return nombre
     */
    public String getName(){
        return msNombre;
    }
    /**
     * Nombre de la atributo
     * @param psNombre el nombre
     */
    public void setNombre(String psNombre){
        if(psNombre==null){
            psNombre="";
        }
        msNombre = JSaxParser.reemplazarCaracRaros(psNombre);
    }
    /**
     * Valor de la atributo
     * @return valor
     */
    public String getValor(){
        return msValor;
    }
    /**
     * Valor de la atributo
     * @return valor
     */
    public String getValue(){
        return msValor;
    }
    /**
     * Valor de la atributo
     * @param psValor establece el valor
     */
    public void setValor(String psValor){
        if(psValor==null){
            psValor="";
        }
        msValor=JSaxParser.reemplazarCaracRaros(psValor);
    }

    /**
     * Numero de atributos
     * @return numero de atributos
     */
    public int size(){
        return moElementos.size();
    }

    public boolean add(Object poFilaDatos) {
        return moElementos.add(poFilaDatos);
    }

    public Object get(int i) {
        return moElementos.get(i);
    }

    public Object remove(int index) {
        return moElementos.remove(index);
    }

    public Object remove(Object poObject) {
        return moElementos.remove(poObject);
    }

    public void clear() {
        moElementos.clear();
    }

    public Iterator iterator() {
        return moElementos.iterator();
    }
        
  /**
   * Recursively prints out the DOM structure underneath a Node.
   * The prefix parameter is used in the recursive call to indent properly,
   * but it can also be used in the initial call to provide an initial prefix
   * or indentation.
   * @param n the Node to print out
   * @param prefix the prefix to use
   */
  public static void printNode( Element n, String prefix ) {
    System.out.println( prefix + n.toString() );
    IListaElementos nl = n.getChildren();
    for( int i = 0; i < nl.size(); i++ ) {
      printNode( (Element)nl.get( i ), prefix + "  " );
    }
  }

  public static String atributoNode(IListaElementos n, String prefix ) {
      String lsResult = null;
      for(int i = 0 ; i < n.size(); i++){
          JAtributo loAtributo = (JAtributo)n.get(i);
          if(loAtributo.getName().equals(prefix)){
              lsResult = loAtributo.getValue();
              break;
          }
      }
      return lsResult;
  }

  private static Element simpleXPathHijos( Element parent, String xpath ) {
    String name;
    String nextPath = null;
    if( xpath.indexOf( '/' ) > 0 ) {
      name = xpath.substring( 0, xpath.indexOf( '/' ) );
      nextPath = xpath.substring( xpath.indexOf( '/' ) + 1 );
    } else {
      name = xpath;
    }
    IListaElementos nl = parent.getChildren();
    for( int i = 0; i < nl.size(); i++ ) {
      Element n = (Element)nl.get( i );
      if( n.getName().equals( name ) ) {
        if( nextPath == null ) {
          return n;
        }else {
          return simpleXPathHijos( n, nextPath );
        }
      }
    }
    return null;
  }

  
  /**
   * A very simple XPath implementation.
   * Recursively drills down into the DOM tree, starting at the given parent
   * Node, following the provided XPath. The XPath string is a slash-delimited
   * list of element names to drill down into, the node with the last name in
   * the list is returned
   * @param parent the parent node to search into
   * @param xpath the simplified XPath search string
   * @return the Node found at the end of the search, or null if the search
   * failed to find the specified node.
   */ 
  public static Element simpleXPath( Element parent, String xpath ) {
    String name;
    String nextPath = null;
    if( xpath.indexOf( '/' ) > 0 ) {
      name = xpath.substring( 0, xpath.indexOf( '/' ) );
      nextPath = xpath.substring( xpath.indexOf( '/' ) + 1 );
    } else {
      name = xpath;
    }
    Element loResult = null;
    if(parent.getName().equals(name)){
        loResult = simpleXPathHijos(parent, nextPath);
    }
    return loResult;
  }

}
