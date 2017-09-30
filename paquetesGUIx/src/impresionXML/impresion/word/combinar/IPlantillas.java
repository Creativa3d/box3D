/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar;

import ListDatos.JListDatos;
import java.io.File;

/**
 *
 * @author eduardo
 */
public interface IPlantillas {

    /**
     * Grupo de plantillas
     * @param psGrupo
     */
    public void setGrupo(String psGrupo);
    /**
     * Devuelve la lista de plantillas
     * @return 
     * @throws java.lang.Exception 
     */
    public JListDatos getListaPlantillas()throws Exception ;

    /**
     * Rellena la lista de plantillas
     * @param moList
     * @throws java.lang.Exception
     */
    public void rellenarListaPlantillas(JListDatos moList)throws Exception ;

    /**
     * Lista de campos combinados
     * @param poListDatosFuente
     */
    public void generarPlantilla(JListDatos poListDatosFuente)throws Exception ;

    /**
     * Crear plantilla word vacía
     * @param psNombre
     * @return 
     * @throws java.lang.Exception
     */
    public String crearPlantilla(String psNombre)throws Exception ;

    /**
     * Borrar plantilla word
     * @param psNombre
     */
    public void borrar(String psNombre)throws Exception ;

    /**
     * mostramos plantilla word
     * @param psNombre
     */
    public void mostrarPlantilla(String psNombre)throws Exception ;

    /**
     * Procesa un word para que se puedan introducir campos combinados
     * @param loFile
     * @return 
     * @throws java.lang.Exception
     */
    public String procesar(File loFile)throws Exception ;

    /**
     * Renombrar plantilla word
     * @param lsAntig
     * @param lsNombre
     * @throws java.lang.Exception
     */
    public void renombrar(String lsAntig, String lsNombre)throws Exception ;
    
    /**
     * Combinar plantilla
     * @param lsNombre
     * @param poList
     * @throws java.lang.Exception
     */
    public void combinarPlantilla(String lsNombre, JListDatos poList) throws Exception;
    
    /**
     * Combiar plantilla y guarda en ruta especifica
     * @param lsNombre
     * @param poList
     * @param poFileDestino
     * @throws java.lang.Exception
     */
    public void combinarPlantilla(String lsNombre, JListDatos poList, File poFileDestino) throws Exception;
    
}
