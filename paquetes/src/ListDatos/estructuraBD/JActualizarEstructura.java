/*
 * JActualizarEstructura.java
 *
 * Created on 12 de septiembre de 2003, 11:54
 */
package ListDatos.estructuraBD;

import ListDatos.*;
import java.io.Serializable;
import utiles.IListaElementos;

import utiles.JDepuracion;
import utiles.JListaElementos;

/**
 * Objeto para actualizar un dato 
 */
public class JActualizarEstructura implements java.io.Serializable, ISelectEjecutarSelect, Cloneable{
  private static final long serialVersionUID = 33333331L;
    
  /**
   * Tipo de modificacion (ISelectEstructura.mclTipoModificar, mclTipoADD, mclTipoBorrar)
   */
  private int mlTipoModif;
  /**
   * FieldDefs, Nombre Campos, Tipos, Campos principales y datos
   */
  private JFieldDef moCampo;
  private JTableDef moTabla1;
  private JTableDef moTabla2;
  private JIndiceDef moIndice;
  private JRelacionesDef moRelacion;
  
  /**
   *Usuario, Actualmente no se usa
   */
  private String msUsuario=null;
  /**
   *PassWord, Actualmente no se usa
   */
  private String msPassWord=null;
  /**
   *Permisos, Actualmente no se usa
   */
  private String msPermisos=null;
  //indica si es comprimido o no
  private boolean mbComprimido = false;
    private JListaElementos<Serializable> moMETAINFORMACION;

    public JActualizarEstructura(final JFieldDef poCampo, final JTableDef poTabla, final int plTipoModif, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        moCampo = poCampo;
        moTabla1 = new JTableDef(poTabla.getNombre());
        mlTipoModif = plTipoModif;
    }
    public JActualizarEstructura(final JFieldDef poCampo, final String psTabla, final int plTipoModif, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        moCampo = poCampo;
        moTabla1 = new JTableDef(psTabla);
        mlTipoModif = plTipoModif;
    }
    public JActualizarEstructura(final JTableDef poTabla, final int plTipoModif, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        moTabla1 = poTabla;
        mlTipoModif = plTipoModif;
    }
    public JActualizarEstructura(final JIndiceDef poIndice,final JTableDef poTabla, final int plTipoModif, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        moIndice = poIndice;
        moTabla1 = poTabla;
        mlTipoModif = plTipoModif;
    }
    public JActualizarEstructura(final JRelacionesDef poRelacion,final JTableDef poTabla1,final JTableDef poTabla2, final int plTipoModif, final String psUsuario, final String psPassWord, final String psPermisos) {
        super();
        msUsuario=psUsuario;
        msPassWord=psPassWord;
        msPermisos=psPermisos;
        moRelacion = poRelacion;
        moTabla1 = poTabla1;
        moTabla2 = poTabla2;
        mlTipoModif = plTipoModif;
    }
    /**
     * Constructor de JActualizarEstructura
     * @param psUsuario usuario, generalmente es a nulo
     * @param psPassWord password, generalmente es a nulo
     * @param psPermisos permisos, generalmente es a nulo
     */
    public JActualizarEstructura(final String psUsuario, final String psPassWord, final String psPermisos) {
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
    public String msSQL(final ISelectMotor poSelect) {
        String lsResult=null;
        try{
            if(moCampo==null){
                if(moIndice==null){
                    if(moRelacion==null){
                        lsResult=poSelect.getTabla(mlTipoModif, moTabla1);
                    }else{
                        lsResult=poSelect.getRelacion(mlTipoModif, moRelacion, moTabla1, moTabla2);
                    }
                }else{
                    lsResult=poSelect.getIndice(mlTipoModif, moIndice, moTabla1);
                }
            }else{
                lsResult=poSelect.getCampo(mlTipoModif, moCampo, moTabla1);
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
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
     * Devuelve el tipo actualizacion 
     * @param plTipoModif tipo modif.
     */
    public void setTipoModif(final int plTipoModif){
        mlTipoModif = plTipoModif;
    }
    /**Devuelve el objeto en cadena*/
    public String toString(){
        try{
            return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
            throw new InternalError(e.toString());
        }
    }
    public String getXML() {
        try{
            return msSQL(new JXMLSelectMotor());
        }catch(Exception e){
            JDepuracion.anadirTexto(this.getClass().getName(), e);
            throw new InternalError(e.toString());
        }
    }

    public void setComprimido(final boolean pbValor) {
      mbComprimido = pbValor;
    }
    
    public boolean getComprimido() {
      return mbComprimido;
    }

    public Object clone() throws CloneNotSupportedException {
        JActualizarEstructura loEst =  (JActualizarEstructura) super.clone();
        loEst.moCampo=(JFieldDef) moCampo.clone();
        loEst.moIndice= (JIndiceDef) moIndice.clone();
        loEst.moRelacion=(JRelacionesDef) moRelacion.clone();
        loEst.moTabla1=(JTableDef) moTabla1.clone();
        loEst.moTabla2=(JTableDef) moTabla2.clone();
        if (moMETAINFORMACION != null) {
            for (int i = 0; i < moMETAINFORMACION.size(); i++) {
                Serializable uno = moMETAINFORMACION.get(i);
                if (uno instanceof JSelectMeta){
                    loEst.addMETAINFORMACION((JSelectMeta)((JSelectMeta)uno).clone());
                } else {
                    loEst.addMETAINFORMACION(uno);
                }
            }
        }
        return loEst;
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