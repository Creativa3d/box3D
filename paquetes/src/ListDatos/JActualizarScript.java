/*
 * JActualizar.java
 *
 * Created on 12 de septiembre de 2003, 11:54
 */
package ListDatos;

import java.io.Serializable;
import utiles.IListaElementos;
import utiles.JListaElementos;

/**
 * Objeto para actualizar un dato 
 */
public final class JActualizarScript implements java.io.Serializable, ISelectEjecutarSelect, Cloneable{
  private static final long serialVersionUID = 3333331L;
  /**
   * Tabla en donde actualizar
   */
  private String msScript;
  /**
   *Usuario, Actualmente no se usa
   */
  private final String msUsuario;
  /**
   *PassWord, Actualmente no se usa
   */
  private final String msPassWord;
  /**
   *Permisos, Actualmente no se usa
   */
  private final String msPermisos;
  //indica si es comprimido o no
  private boolean mbComprimido = false;
  private JListaElementos<Serializable> moMETAINFORMACION;

    /**
    * Constructor de JActualizarScript
    * @param psScript Tabla a actualizar
    * @param psUsuario usuario, generalmente es a nulo
    * @param psPassWord password, generalmente es a nulo
    * @param psPermisos permisos, generalmente es a nulo
    */
    public JActualizarScript(final String psScript, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        msScript = psScript;
    }
    /**
    * Constructor de JActualizar
    * @param psUsuario usuario, generalmente es a nulo
    * @param psPassWord password, generalmente es a nulo
    * @param psPermisos permisos, generalmente es a nulo
    */
    public JActualizarScript(final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
    }
    /**
     * Devuelve una SQL de actualizacion en funcion de ISelect 
     * @return sql
     * @param poSelect select
     */
    public String msSQL(final ISelectMotor poSelect){
        return msScript;
    }
    
   /**
   *Password, Actualmente no se usa
   */
   public String getPassWord() {
        return msPassWord;
    }
  
    public String getPermisos() {
        return msPermisos;
    }

    public String getUsuario() {
        return msUsuario;
    }
    /**
     * Devuelve el script
     * @return script
     */
    public String getScript(){
        return msScript;
    }
    
    /**
     * Establece el script
     * @param psScript script
     */
    public void setScript(final String psScript){
        msScript = psScript;
    }
    /**Devuelve el objeto en cadena*/
    public String toString(){
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
    public String getXML() {
        return msSQL(new JXMLSelectMotor());
    }

    public void setComprimido(final boolean pbValor) {
      mbComprimido = pbValor;
    }
    
    public boolean getComprimido() {
      return mbComprimido;
    }

   public Object clone() throws CloneNotSupportedException {
       return super.clone();
   
   }

    /**Devuelve la lista de elementos de metainformacion*/
    public synchronized IListaElementos<Serializable> getMETAINFORMACION(){
        if(moMETAINFORMACION==null){
            moMETAINFORMACION = new JListaElementos<Serializable>();
        }
        return moMETAINFORMACION;
    }
    /**add elemento de metainformacion, nos aseguramos q sea serializable
     * @param poObject
     */
    public void addMETAINFORMACION(final Serializable poObject){
        getMETAINFORMACION().add(poObject);
    }

}