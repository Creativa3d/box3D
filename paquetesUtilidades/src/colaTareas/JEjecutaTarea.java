/*
 * JEjecutaTarea.java
 *
 * Created on 28 de marzo de 2005, 9:54
 */

package colaTareas;

/**Clase que ejecuta las tareas en thread aparte*/
public class JEjecutaTarea extends Thread {
    private JListaTareas moListaTareas;
    private INotificaciones moNoti;
    private boolean mbFinalizar = false;
    
    /** Creates a new instance of JEjecutaTarea */
    public JEjecutaTarea(JListaTareas poListaTareas, INotificaciones poNoti) {
        moListaTareas = poListaTareas;
        moNoti = poNoti;
    }

    public void finalizar(){
        mbFinalizar = true;
        try{
            this.notifyAll();
        }catch(Exception e){
            
        }
    }
    
    public void run(){
        while((!moListaTareas.getFinalizado())&&(!mbFinalizar)){
            ITarea loTarea=null;
            try{
                loTarea = moListaTareas.getTarea();
                if(loTarea!=null){
                    loTarea.ejecutar();
                    if(moNoti!=null){
                       moNoti.finalizado(this, loTarea);
                    }
                }
            }catch(Exception e){
                if(moNoti!=null){
                    moNoti.error(e);
                }else{
                    if(loTarea!=null){
                        System.out.println("Tarea error: " + loTarea.getNombre());
                    }
                    e.printStackTrace();
                }
            }
        }
    }
    
}
