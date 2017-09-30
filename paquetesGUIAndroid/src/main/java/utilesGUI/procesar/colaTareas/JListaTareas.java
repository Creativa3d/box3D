/*
 * JListaTareas.java
 *
 * Created on 28 de marzo de 2005, 9:52
 */

package utilesGUI.procesar.colaTareas;


import utiles.*;
import utilesGUI.procesar.IProcesoAccion;

/**Almacena la lista de tareas*/
public class JListaTareas {
    private IListaElementos moTareas;
    private boolean mbFinalizado = false;
    
    /** Creates a new instance of JListaTareas */
    public JListaTareas() {
        moTareas = new JListaElementos();
    }
    
    /**Finaliza el proceso*/
    public void finalizar(){
        mbFinalizado = true;
        try{
            notifyAll();
        }catch(Exception e){
            
        }
    }
    
    /**Devuelve si se ha finalizado*/
    public boolean getFinalizado(){
        return mbFinalizado;
    }
    /**establece la variable de finalizado*/
    void setFinalizado(boolean pbFinaliza){
        mbFinalizado = pbFinaliza;
        try{
            notifyAll();
        }catch(Exception e){
            
        }
    }
    
    /**Aade una tarea y notifica que se ha aadido una tarea*/
    public synchronized void addTarea(IProcesoAccion poTarea){
        mbFinalizado = false;
        moTareas.add(poTarea);
        try{
            notifyAll();
        }catch(Exception e){
            
        }
    }
    /**Espera hasta que se acaban las tareas*/
    public synchronized void esperarHastaAcabar() {
        while((moTareas.size()>0)&&(!mbFinalizado)) {
            try{
                wait(10000);
            }catch(Exception e){
                
            }
            notifyAll();
        }
    }
    /**Devuelve una tarea, si no hay ninguna bloquea, puede devolver nulo si se ha finalizado*/
    public synchronized IProcesoAccion getTarea(){
        while((moTareas.size()==0)&&(!mbFinalizado)) {
            try{
                wait(5000);
            }catch(Exception e){
                
            }
            notifyAll();
        }
        
        IProcesoAccion loTarea = null;
        
        if(!mbFinalizado){
            loTarea = (IProcesoAccion)moTareas.get(0);
            moTareas.remove(0);
        }
        return loTarea;
        
    }
    public int size(){
        return moTareas.size();
    }
    
}
