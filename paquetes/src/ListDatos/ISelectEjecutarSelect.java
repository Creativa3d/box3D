/*
 * ISelectEjecutarSelect.java
 *
 * Created on 12 de septiembre de 2003, 11:57
 */

package ListDatos; 

import java.io.Serializable;
import utiles.IListaElementos;

/**
 * Interfaz para ejecutar consultas Usa el patron composite 
 * 
 */
public interface ISelectEjecutarSelect extends java.io.Serializable, ISelectEjecutarComprimido {

    /**
     * Devuelve una SQL en funcion del interfaz ISelect 
     * @return Sql
     * @param poSelect Motor select
     */
    public String msSQL( ISelectMotor poSelect);
    /**
     * Usuario, actualmente no se usa
     * @return Usuario
     */
    public String getUsuario();
    /**
     * Password, actualmente no se usa
     * @return Password
     */
    public String getPassWord();
    /**
     * Permisos, actualmente no se usa
     * @return Permisos
     */
    public String getPermisos();
    
    /**Meta información
     * @return Lista metadatos
     */
    public IListaElementos<Serializable> getMETAINFORMACION();
    
}
