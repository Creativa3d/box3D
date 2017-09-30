/*
 * JActualizarConj.java
 *
 * Created on 12 de septiembre de 2003, 9:46
 */

package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.Serializable;
import java.util.Iterator;
import utiles.*;

/**
 * Objeto para actualizar un conjunto de datos 
 */

public final class JActualizarConj  
        implements Serializable , 
                   IServerEjecutar {
    private static final long serialVersionUID = 3333332L;
    //Lista de actualizaciones
    private IListaElementos moListaActualizar;
    private JListDatos moListDatos = null;
    /**
    *Usuario
    */
    private final String msUsuario;
    /**
    *PassWord
    */
    private final String msPassWord;
    /**
    *Permisos
    */
    private final String msPermisos;
    //indica si es comprimido o no
    private boolean mbComprimido = false;
    private JServerEjecutarParametros moParametros = new JServerEjecutarParametros();

    
    /**
     * Constructor 
     * @param psUsuario usuario, generalmente es a nulo
     * @param psPassWord password, generalmente es a nulo
     * @param psPermisos permisos, generalmente es a nulo
     */
    public JActualizarConj(final String psUsuario, final String psPassWord, final String psPermisos) {
        this(5,psUsuario, psPassWord, psPermisos);
    }
    
    /**
     * Constructor con una capacidad minima
     * @param psUsuario usuario, generalmente es a nulo
     * @param psPassWord password, generalmente es a nulo
     * @param psPermisos permisos, generalmente es a nulo
     * @param plCapacidad capacidad inicial
     */
    public JActualizarConj(final int plCapacidad,final String psUsuario, final String psPassWord, final String psPermisos) {
      super();  
      msUsuario=psUsuario;
      msPassWord=psPassWord;
      msPermisos=psPermisos;
      if(plCapacidad==0){
          moListaActualizar = new JListaElementos();
      }else{
          moListaActualizar = new JListaElementos(plCapacidad);
      }
    }
    
    public int getSize(){
        return moListaActualizar.size();
    }
    public IServerEjecutar get(int plIndex ){
        return (IServerEjecutar) moListaActualizar.get(plIndex);
    }
    public IServerEjecutar getSelectUpdate(int plIndex ){
        return get(plIndex);
    }
    public JListDatos getListDatos(){
        return moListDatos;
    }

    /**
     *Anade un nuevo objeto al array, y lo redimensiona si hace falta
     */
    private void anadirObjeto(final IServerEjecutar poObject){
        moListaActualizar.add(poObject);
    }

    /**
     * Crea un objeto actualizar con los parametros y lo anade 
     * @param poCampos def. de campos y valores que posee
     * @param psTabla tabla base
     * @param plTipoModif tipo modif.
     */
    public void add(final JFieldDefs poCampos, final String psTabla, final int plTipoModif){
        anadirObjeto(new JActualizar(poCampos, psTabla, plTipoModif, msUsuario, msPassWord, msPermisos));
    }
    /**
     * Anade un objeto actualizar 
     * @param poActu objeto  actualizar
     */
    public void add(final IServerEjecutar poActu){
        anadirObjeto(poActu);
    }
    
    /**
     * Anade un JListDatos cuyos objetos updates se crean en el servidor, por si lo normal es que la mayor parte del JListDatos SI se tiene que actualizar 
     * @param poListDatos JListDatos
     */ 
    public void add(final JListDatos poListDatos){
        moListDatos = poListDatos;
    }
    
    /**
     * Crea objetos updates a partir de un JListDatos de los registros borrados, por si lo normal es que la mayor parte del JListDatos NO se tiene que actualizar 
     * @param poList JListDatos
     * @throws Exception error
     */ 
    public void crearUpdateAPartirListSoloBorrados(final JListDatos poList) throws Exception {
        //recorremos la lista de borrados
        IListaElementos loBorrados = poList.getListBorrados();
        Iterator loEnum = loBorrados.iterator();
        while(loEnum.hasNext()){
            IFilaDatos loFila = (IFilaDatos)loEnum.next();
            JFieldDefs loFields = poList.getFields().Clone();
            loFields.cargar(loFila);
            add(new JActualizar(loFields, poList.msTabla, JListDatos.mclBorrar, msUsuario, msPassWord, msPermisos));
        }
    }

    /**
     * Crea objetos updates a partir de un JListDatos solo de las ediciones y Nuevos registros, por si lo normal es que la mayor parte del JListDatos NO se tiene que actualizar 
     * @param poList JListDatos
     * @throws Exception error
     */ 
    public void crearUpdateAPartirListSoloNuevoYEdit(final JListDatos poList) throws Exception {
        //recorremos todo el ListDatos y anadimos nuevo actualzar
        if (poList.moveFirst()){
            do{
                if(poList.moFila().getTipoModif()!=JListDatos.mclNada){
                    JFieldDefs loFields = poList.getFields().Clone();
                    add(new JActualizar(loFields, poList.msTabla, poList.moFila().getTipoModif(), msUsuario, msPassWord, msPermisos));
                }
            }while(poList.moveNext());
        }
    }
    /**
     * Crea objetos updates a partir de un JListDatos, por si lo normal es que la mayor parte del JListDatos NO se tiene que actualizar 
     * @param poList JListDatos
     * @throws Exception error
     */ 
    public void crearUpdateAPartirList(final JListDatos poList) throws Exception {
        //recorremos la lista de borrados
        IListaElementos loBorrados = poList.getListBorrados();
        Iterator loEnum = loBorrados.iterator();
        while(loEnum.hasNext()){
            IFilaDatos loFila = (IFilaDatos)loEnum.next();
            JFieldDefs loFields = poList.getFields().Clone();
            loFields.cargar(loFila);
            add(new JActualizar(loFields, poList.msTabla, JListDatos.mclBorrar, msUsuario, msPassWord, msPermisos));
        }
        //recorremos todo el ListDatos y anadimos nuevo actualzar
        if (poList.moveFirst()){
            do{
                if(poList.moFila().getTipoModif()!=JListDatos.mclNada){
                    JFieldDefs loFields = poList.getFields().Clone();
                    add(new JActualizar(loFields, poList.msTabla, poList.moFila().getTipoModif(), msUsuario, msPassWord, msPermisos));
                }
            }while(poList.moveNext());
        }
    }


    public String getPassWord() {
        return msPassWord;
    }

    public String getPermisos() {
        return msPermisos;
    }


    public String getUsuario() {
        return msUsuario;
    }
    public String toString(){
        StringBuilder lsSQL = new StringBuilder();
        for(int i = 0; i< moListaActualizar.size(); i++){
            IServerEjecutar loActu = (IServerEjecutar) moListaActualizar.get(i);
            lsSQL.append(loActu.toString());
            lsSQL.append(" ; ");
        }
        return lsSQL.toString();
    }
    /**
     * devuelve una SQL de actualizacion en funcion de ISelect
     * @param poSelect Motor select
     * @return Cadena sql
     */
    public String msSQL(final ISelectMotor poSelect){
        StringBuilder lsSQL = new StringBuilder();
        for(int i = 0; i < moListaActualizar.size(); i++){
            IServerEjecutar loActu = (IServerEjecutar) moListaActualizar.get(i);
            if(JActualizar.class.isAssignableFrom(loActu.getClass())){
                lsSQL.append(((JActualizar)loActu).msSQL(poSelect));
                lsSQL.append(" ; ");
            }
        }
        return lsSQL.toString();
    }

    public void setComprimido(final boolean pbValor) {
      mbComprimido = pbValor;
    }

    public boolean getComprimido() {
      return mbComprimido;
    }
 
    public String getXML() {
        StringBuilder lsSQL = new StringBuilder();
        for(int i = 0; i < moListaActualizar.size(); i++){
            IServerEjecutar loActu = (IServerEjecutar) moListaActualizar.get(i);
            lsSQL.append(loActu.getXML());
            lsSQL.append(' ');
        }
        return lsSQL.toString();
    }
    
    public IResultado ejecutar(final IServerServidorDatos poServer) throws Exception {
        if(moListDatos!=null){
            crearUpdateAPartirList(moListDatos);
        }
        JResultado loResult = new JResultado("", true);
        for(int i = 0 ; (i<moListaActualizar.size()) && (loResult.getBien()); i++){
            IServerEjecutar loActu = (IServerEjecutar) moListaActualizar.get(i);
            if(loActu!=null){
                if(JActualizar.class.isAssignableFrom(loActu.getClass())){
                    loResult.addResultado(
                        poServer.actualizar("",(JActualizar) loActu)
                        );
                }else{
                    loResult.addResultado(
                        poServer.ejecutarServer(loActu)
                        );
                }
            }
        }
        return loResult;
    }
    public JServerEjecutarParametros getParametros() {
        return moParametros;
    }
}
