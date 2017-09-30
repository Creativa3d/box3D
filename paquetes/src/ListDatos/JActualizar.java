/*
 * JActualizar.java
 *
 * Created on 12 de septiembre de 2003, 11:54
 */
package ListDatos;

import ListDatos.estructuraBD.*;
import utiles.JCadenas;
import utiles.JDepuracion;

/**
 * Objeto para actualizar un dato 
 */
public final class JActualizar implements IServerEjecutar, java.io.Serializable {
  private static final long serialVersionUID = 3333331L;
  
  /**Atributo de comandos OUT*/
  public static final String mcsOUT="OUT";
  /**Atributo de comandos IN*/
  public static final String mcsIN="IN";
  
  /**
   * Tabla en donde actualizar
   */
  private String msTabla;
  /**
   * Tipo de modificacion (JLisDatos.mclBorrar, JLisDatos.mclNuevo, JLisDatos.mclEditar, JLisDatos.mclComando)
   */
  private int mlTipoModif;
  /**
   * FieldDefs, Nombre Campos, Tipos, Campos principales y datos
   */
  private JFieldDefs moCampos;
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
  private JServerEjecutarParametros moParametros = new JServerEjecutarParametros();

  private String msSQLPelo;
    /**
    * Constructor de JActualizar 
    * @param poCampos Lista de FieldsDef
    * @param psTabla Tabla a actualizar
    * @param plTipoModif Tipo de modificacion JListDatos.mclBorrar, JListDatos.mclEditar, JListDatos.mclNuevo 
    * @param psUsuario usuario, generalmente es a nulo
    * @param psPassWord password, generalmente es a nulo
    * @param psPermisos permisos, generalmente es a nulo
    */
    public JActualizar(final JFieldDefs poCampos, final String psTabla, final int plTipoModif, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        moCampos = poCampos;
        msTabla = psTabla;
        mlTipoModif = plTipoModif;
    }
    /**
    * Constructor de JActualizar
    * @param psUsuario usuario, generalmente es a nulo
    * @param psPassWord password, generalmente es a nulo
    * @param psPermisos permisos, generalmente es a nulo
    */
    public JActualizar(final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
    }
    public JActualizar() {
        this(null,null,null);
    }
    
    /**
     * Devuelve una SQL de actualizacion en funcion de ISelect 
     * @return sql
     * @param poSelect select
     */
    public String msSQL(final ISelectMotor poSelect){
        String lsResult;
        if(JCadenas.isVacio(msSQLPelo)){
            lsResult=poSelect.msActualizacion(msTabla, moCampos, mlTipoModif);
        }else{
            lsResult=msSQLPelo;
        }
        return lsResult;
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
     * Devuelve el tipo actualizacion 
     * @return tipo modif.
     */
    public int getTipoModif(){
        return mlTipoModif;
    }
    /**
     * Devuelve la tabla 
     * @return tabla
     */
    public String getTabla(){
        return msTabla;
    }
    
    /**
     * Devuelve los campos 
     * @return campos
     */
    public JFieldDefs getFields(){
        return moCampos;
    }

    /**
       * Tipo de modificacion 
     * @param plTipoModif (JLisDatos.mclBorrar, JLisDatos.mclNuevo, JLisDatos.mclEditar, JLisDatos.mclComando)
     */
    public void setTipoModif(final int plTipoModif){
        mlTipoModif = plTipoModif;
    }
    /**
     * Devuelve la tabla
     * @param psTabla tabla
     */
    public void setTabla(final String psTabla){
        msTabla = psTabla;
    }
    /**
     * Establece los campos
     * @param poCampos campos
     */
    public void setFields(final JFieldDefs poCampos){
        moCampos = poCampos;
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

    public IResultado ejecutar(final IServerServidorDatos poServer) throws Exception {
        return poServer.actualizar("", this);
    }

    public JServerEjecutarParametros getParametros() {
        return moParametros;
    }
    public boolean isParamOut(){
        boolean lbOUT = false;
        if(moCampos!=null){
            for(JFieldDef loCampo: moCampos.getListaCampos()){
                lbOUT |= isParamOut(loCampo);
            }
        }
        return lbOUT;
    }
    public JListDatos getListDatosOut() throws Exception{
        JListDatos loListDatosOut = new JListDatos();
        for(JFieldDef loCampo: moCampos.getListaCampos()){
            if(isParamOut(loCampo)){
                loListDatosOut.getFields().addField(loCampo.Clone());
            }
        }
        return loListDatosOut;
    }
    public static boolean isParamOut(JFieldDef poCampo){
        boolean lbOUT = false;
        Object loOUT = poCampo.getAtributos().get(JActualizar.mcsOUT);
        if(loOUT!=null && loOUT instanceof Boolean){
            lbOUT |= ((Boolean)loOUT).booleanValue();
        }
        return lbOUT;
    }

    /**
     * @return the msSQLPelo
     */
    public String getSQLPelo() {
        return msSQLPelo;
    }

    /**
     * @param msSQLPelo the msSQLPelo to set
     */
    public void setSQLPelo(String msSQLPelo) {
        this.msSQLPelo = msSQLPelo;
    }

 
}
