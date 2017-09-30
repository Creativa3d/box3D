/*
 * JCola.java
 *
 * Created on 28 de marzo de 2005, 9:48
 */

package utilesGUI.procesar.colaTareas;

/**Controlador de ejecucion de tareas*/
public class JMTareas {
    
    private JListaTareas moLista;
    private JEjecutaTarea[] moEjecutaTareas;
    
    /**Contructor*/
    public JMTareas(int plTareasParalelo) {
        moLista = new JListaTareas();
        moEjecutaTareas = new JEjecutaTarea[plTareasParalelo];
        for(int i = 0; i < moEjecutaTareas.length; i++ ){
            moEjecutaTareas[i] = new JEjecutaTarea(moLista);
            moEjecutaTareas[i].start();
        }
    }

    /**Constructor*/
    public JMTareas() {
        this(1);
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
            try{
                moEjecutaTareas[i].finalizar();
            }catch(Exception e){
            }
            moEjecutaTareas[i] = new JEjecutaTarea(moLista);
            moEjecutaTareas[i].start();
        }
        
        moLista.setFinalizado(false);
    }
    protected void finalize () throws Throwable {
        pararProcesos();
        super.finalize();
    }    
}
