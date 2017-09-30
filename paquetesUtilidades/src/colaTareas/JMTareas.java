/*
 * JCola.java
 *
 * Created on 28 de marzo de 2005, 9:48
 */

package colaTareas;

import utiles.*;

/**Controlador de ejecucion de tareas*/
public class JMTareas {
    
    private JListaTareas moLista;
    private JEjecutaTarea[] moEjecutaTareas;
    private INotificaciones moNoti;
    
    /**Contructor*/
    public JMTareas(int plTareasParalelo, INotificaciones poNoti) {
        moLista = new JListaTareas();
        moNoti = poNoti;
        moEjecutaTareas = new JEjecutaTarea[plTareasParalelo];
        for(int i = 0; i < moEjecutaTareas.length; i++ ){
            moEjecutaTareas[i] = new JEjecutaTarea(moLista, moNoti);
            moEjecutaTareas[i].start();
        }
    }

    /**Constructor*/
    public JMTareas() {
        this(1, null);
    }
    
    /**Devuelve la lista de tareas*/
    public JListaTareas getListaTareas(){
        return moLista;
    }
    
    /**Para el proceso*/
    public void pararProcesos(){
        moLista.finalizar();
    }
    
    /**Reanuda el proceso*/
    public void reanudarProceso(){
        for(int i = 0; i < moEjecutaTareas.length; i++ ){
            if(moEjecutaTareas[i]!=null){
                moEjecutaTareas[i].finalizar();
            }
            moEjecutaTareas[i] = new JEjecutaTarea(moLista, moNoti);
            moEjecutaTareas[i].start();
        }
    }
    
}
