/*
 * IListDatosFiltro.java
 *
 * Created on 16 de julio de 2002, 20:14
 */

package ListDatos;

import ListDatos.estructuraBD.JFieldDefs;
import java.io.Serializable;

/** 
 * Patron de diseno compositer todos los filtros cumplen este interfaz 
 */

public interface IListDatosFiltro extends Serializable, Cloneable {
    /**
     * Inicializa el filtro, debe llamarse despues de anadir todas las condiciones 
     * @param psTabla Tabla
     * @param palTodosTipos Lista de tipos
     * @param pasTodosCampos Lista de nombres de campos
     */
    public void inicializar(String psTabla, int []palTodosTipos, String[] pasTodosCampos);
    /**
     * Devuelve si la fila pasada por parametro cumple el filtro o no 
     * @param poFila Fila de datos
     * @return Si lo cumple
     */
    public boolean mbCumpleFiltro(IFilaDatos poFila);
    /** Clona el filtro*/
    public Object clone();
    /**
     * Pasado el interfaz de generador de SQL genera la SQL 
     * @return sql
     * @param poSelect motor select
     */
    public String msSQL(ISelectMotor poSelect);
}
