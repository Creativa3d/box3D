/*
 * IServerServidorDatos.java
 *
 * Created on 12 de septiembre de 2003, 11:54
 */
package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.Serializable;
import utiles.*;

/**
 * Interfaz que deben cumplir todos los servidores de datos 
 */
public interface IServerServidorDatos extends IConstructorEstructuraBD, Serializable {
    public static final int mclFiltroTipoSelect = JListDatos.mclNada;
    public static final int mclFiltroTipoEdicion = JListDatos.mclEditar;
    public static final int mclFiltroTipoNuevo = JListDatos.mclNuevo;
    public static final int mclFiltroTipoBorrar = JListDatos.mclBorrar;

    /**
     * Borramos una fila concreta y de los JListDatos de CACHE menos los JLISTDATOS que coincidan con psSelect 
     * @return Resultado
     * @param psSelect Select
     * @param psTabla Tabla
     * @param poCampos Campos
     */
    public IResultado borrar(String psSelect, String psTabla, JFieldDefs poCampos);
    /**
     * Modificar una fila concreta y de los JListDatos de CACHE menos los JLISTDATOS que coincidan con psSelect 
     * @return resultado
     * @param psSelect select
     * @param psTabla tabla
     * @param poCampos campos
     */
    public IResultado modificar(String psSelect, String psTabla, JFieldDefs poCampos);
    /**
     * a?adir una fila concreta  Y de los JListDatos de CACHE menos los JLISTDATOS que coincidan con psSelect
     * @return resultado
     * @param psSelect select
     * @param psTabla tabla
     * @param poCampos campos
     */
    public IResultado anadir(String psSelect, String psTabla, JFieldDefs poCampos);
    /**
     * idem ejecutarSQL
     * @param poEstruc Objeto que actualiza estructura
     * @return El resultado de la ejecucion de la estructura
     */
    public IResultado modificarEstructura(ISelectEjecutarSelect poEstruc);
    /**
     * Ejecuta SQL, normalmente de edición
     * @param poEstruc Objeto que actualiza estructura
     * @return El resultado de la ejecucion de la estructura
     */
    public IResultado ejecutarSQL(ISelectEjecutarSelect poEstruc);
    
    /**
     * Ejecutamos una actualizacion
     * @param poActualizar objeto actualizacion
     * @return resultado
     */
    public IResultado actualizar(String psSelect, JActualizar poActualizar);
    /**
     * Ejecutamos una accion y refrescamos LA CACHE
     * @return el resultado
     * @param poEjecutar El objeto que se va a ejecutar
     */
    public IResultado ejecutarServer(IServerEjecutar poEjecutar);
//    /**
//     * Ejecutamos una accion y refrescamos LA CACHE
//     * @return el resultado
//     * @param poEjecutar El objeto que se va a ejecutar
//     * @param poParam parametros
//     */
//    public IResultado ejecutarServer(IServerEjecutar poEjecutar, JServerEjecutarParametros poParam);
    /**
     * Recuperamos datos 
     * @param v JListDatos a rellenar
     * @param poSelect Objeto select
     * @param psTabla Tabla base 
     * @param pbPasarACache Si pasa a cache
     * @param pbRefrescarACache Si refresca cahce
     * @param plOpciones Si es sincrono/asincrono
     * @throws Exception Excepcion
     */
    public void recuperarDatos(JListDatos v, JSelect poSelect,String psTabla,boolean pbPasarACache,boolean pbRefrescarACache, int plOpciones) throws Exception ;
    
    /**
     *Borra la cache
     */
    public void clearCache();

    /**
     * Actualiza la cache con el conjunto de JListDatos en poResult, se hace aqui pq puede haber un conjunto de actualizaciones, por lo que no vale solo en JListDatos 
     * @param poResult Lista de elementos a actualizar
     * @param psSelect Select
     */
    public void actualizarDatosCacheConj(IListaElementos poResult, String psSelect);
    
    /**
     * Devuelve el JListDatos que coincide con la select pasada por parametro 
     * @param psSelect Select
     * @return JListDatos
     */
    public JListDatos getEnCache(String psSelect);
    /**
     * Devuelve el JListDatos del cual es fuente del que se pasa por parametro
     * @param poDatos JListDatos a ver si esta en cache
     * @return Si esta en cache o no
     */
    public JListDatos getEnCache(JListDatos poDatos);
    /**
     * borra de la cache el listDatos que sea igual a la select
     * @return JListDatos borrado
     * @param psSelect select del JListDatos
     */
    public JListDatos borrarEnCache(String psSelect);
    
    /**
     *
     * cerrar objeto, si es tipo base de datos es muy importante hacerlo
     * @throws Exception el error en caso de error
     */
    public void close() throws Exception;


    ///////////////////////////////////
    ////Filtros para todas las select
    ///////////////////////////////////

    /**Add un filtro para todas las select q tengan una tabla*/
    public void addFiltro(String psTabla, IListDatosFiltro poFiltro);
    /**Add un filtro para todas las select/edicion/borrado/Nuevo q tengan una tabla*/
    public void addFiltro(int plTipo,String psNombre,String psTabla, IListDatosFiltro poFiltro);
    /**Devuelve un filtro en la posicion i*/
    public IListDatosFiltro getFiltro(int i);
    /**Devuelve la tabla a la q se aplica el filtro de la posicion i*/
    public String getFiltroTabla(int i);
    /**borramos todos los filtros*/
    public void borrarFiltrosTodos();
    /**conjunto todos los filtros*/
    public IListaElementos<JElementoFiltro> getFiltros();

    ///////////////////////////////////
    ////parametros
    ///////////////////////////////////
    public JServerServidorDatosParam getParametros();


  
}