/*
 * IFormBorrado.java
 *
 * Created on 26 de noviembre de 2004, 10:22
 */

package utilesBD.relaciones;


import ListDatos.*;
import utiles.*;

/**Interfaz para borrado en cascada*/
public interface IFormBorrado {
    /**
     * Borrado de datos relacionados
     * @param poTabla lista de registros relacionados
     * @param poComu para devolver si ha aceptado o no
     */
    public void setTabla(JRelacionTablaRegistros poTabla, JComunicacion poComu);
    /**Mostramos el formulario*/
    public void show();
    
}
