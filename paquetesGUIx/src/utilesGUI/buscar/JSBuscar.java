/*
 * JSBuscar.java
 *
 * Created on 4 de septiembre de 2003, 20:09
 */
package utilesGUI.buscar;
import ListDatos.*;

/**Objeto para presentar un form. para buscar un datos de una tabla*/
public class JSBuscar {
    /**Datos*/
    public JListDatos moTabla;
    /**si es cancelado*/
    public boolean mbCancelado = false;

    /**
     * Constructor
     * @param poTabla Datos en donde buscar
     */
    public JSBuscar(JListDatos poTabla) {
        moTabla = poTabla;
    }
    /**
     * muestra el form. de busq.
     * @param parent padre
     * @param modal si es modal
     */
    public void mostrar(java.awt.Frame parent, boolean modal){
        JFormBuscar loBuscar;
	if(parent == null){
            loBuscar = new JFormBuscar(new java.awt.Frame(), modal, this); 
        }else{
            loBuscar = new JFormBuscar(parent, modal, this); 
        }

        loBuscar.show();
    }
    
}
