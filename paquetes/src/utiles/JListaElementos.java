/*
 * JListaElementos.java
 *
 * Created on 13 de enero de 2005, 17:05
 */

package utiles;

import java.util.ArrayList;

/**
 *Objeto estandar Lista de elementos
 * @param <E>
 */
public class JListaElementos<E> extends ArrayList<E> implements IListaElementos<E>, Cloneable {
    private static final long serialVersionUID = 33333326L;


    /**
     * Constructor 
     */
    public JListaElementos() {
    }
    /**
     * Constructor 
     * @param plInicialCapacidad capacidad inicial
     */
    public JListaElementos(int plInicialCapacidad) {
        super(plInicialCapacidad);
    }

    public void insertElementAt(E obj, int index) {
        add(index, obj);
    }
    public String[] toArrayString(){
       String[] loList = new String[size()];
       for(int i = 0 ; i < loList.length; i++){
           if(get(i)!=null){
            loList[i]=get(i).toString();
           }
       }
       return loList;
    }
}
