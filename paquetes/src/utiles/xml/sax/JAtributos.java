/*
 * JListaAtributos.java
 *
 * Created on 12 de enero de 2005, 10:34
 */

package utiles.xml.sax;

import java.util.AbstractList;
import java.util.Iterator;
import utiles.*;

/**Lista de atributos de la etiqueta*/
public class JAtributos extends AbstractList implements IListaElementos {
    private JListaElementos moAtributos = new JListaElementos();

    /**
     * Anadimos un atributo
     * @return atributo creado
     * @param psNombre nombre
     * @param psValor valor
     */
    public JAtributo addAtributo(String psNombre, String psValor){
        JAtributo loAtrib = new JAtributo(psNombre, psValor);
        moAtributos.add(loAtrib);
        return loAtrib;
    }
    /**
     * Devolvemos el atributo
     * @return Atributo 
     * @param i indice
     */
    public JAtributo getAtributo(int i){
        return (JAtributo)moAtributos.get(i);
    }
    /**
     * Devolvemos el atributo
     * @return atributo
     * @param psNombre nombre del atributo
     */
    public JAtributo getAtributo(String psNombre){
        JAtributo loAtrib=null;
        String lsNombre = JSaxParser.reemplazarCaracRaros(psNombre);
        for(int i =0; (i<moAtributos.size()) && (loAtrib==null);i++){
            JAtributo loAtribAux = (JAtributo)moAtributos.get(i);
            if(loAtribAux.getNombre().compareTo(lsNombre)==0){
                loAtrib=loAtribAux;
            }
        }
        return loAtrib;
    }

    public boolean isEmpty(){
        return moAtributos.size()==0;
    }    
    /**
     * Numero de atributos
     * @return numero de atributos
     */
    public int size(){
        return moAtributos.size();
    }

    public boolean add(Object poFilaDatos) {
        return moAtributos.add(poFilaDatos);
    }

    public Object get(int i) {
        return moAtributos.get(i);
    }

    public Object remove(int index) {
        return moAtributos.remove(index);
    }

    public boolean remove(Object poObject) {
        return moAtributos.remove(poObject);
    }

    public void clear() {
        moAtributos.clear();
    }

    public Iterator iterator() {
        return moAtributos.iterator();
    }
    
}

