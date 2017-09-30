/*
 * JListaTareas.java
 *
 * Created on 28 de marzo de 2005, 9:52
 */

package colaTareas;


import utiles.*;

/**Almacena la lista de tareas*/
public class JListaTareas {
    private IListaElementos moTareas;
    private boolean mbFinalizado = false;
    
    /** Creates a new instance of JListaTareas */
    public JListaTareas() {
        moTareas = new JListaElementos();
    }
    
    /**Finaliza todo el proceso*/
    public void finalizar(){
        mbFinalizado = true;
        notifyAll();
    }
    
    /**Devuelve si se ha finalizado*/
    boolean getFinalizado(){
        return mbFinalizado;
    }
    
    /**Añade una tarea y notifica que se ha añadido una tarea*/
    public synchronized void addTarea(ITarea poTarea){
        moTareas.add(poTarea);
        notifyAll();
    }
    
    /**Devuelve una tarea, si no hay ninguna bloquea, puede devolver nulo si se ha finalizado*/
    public synchronized ITarea getTarea(){
        while((moTareas.size()==0)&&(!mbFinalizado)) {
            try{
                wait();
            }catch(Exception e){
                
            }
        }
        
        ITarea loTarea = null;
        
        if(!mbFinalizado){
            loTarea = (ITarea)moTareas.get(0);
            moTareas.remove(0);
        }
        return loTarea;
        
    }
}
