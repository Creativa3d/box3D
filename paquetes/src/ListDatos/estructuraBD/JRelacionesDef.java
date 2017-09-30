/*
 * JRelacionesDefs.java
 *
 * Created on 15 de abril de 2005, 12:08
 */

package ListDatos.estructuraBD;

import ListDatos.JSelectUnionTablas;
import utiles.*;

public class JRelacionesDef  implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 33333337L;
    //Constantes Tipos de reglas en update,delete cascade
    /**
	 * A possible value for the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code> in the
	 * <code>ResultSet</code> objects returned by the methods
	 * <code>getImportedKeys</code>,  <code>getExportedKeys</code>,
	 * and <code>getCrossReference</code>.
	 * <P>For the column <code>UPDATE_RULE</code>,
	 * it indicates that
	 * when the primary key is updated, the foreign key (imported key)
	 * is changed to agree with it.
	 * <P>For the column <code>DELETE_RULE</code>,
	 * it indicates that
	 * when the primary key is deleted, rows that imported that key
	 * are deleted.
     */
    public static final int mclimportedKeyCascade	= 0;

    /**
	 * A possible value for the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code> in the
	 * <code>ResultSet</code> objects returned by the methods
	 * <code>getImportedKeys</code>,  <code>getExportedKeys</code>,
	 * and <code>getCrossReference</code>.
	 * <P>For the column <code>UPDATE_RULE</code>, it indicates that
	 * a primary key may not be updated if it has been imported by
	 * another table as a foreign key.
	 * <P>For the column <code>DELETE_RULE</code>, it indicates that
	 * a primary key may not be deleted if it has been imported by
	 * another table as a foreign key.
     */
    public static final int mclimportedKeyRestrict = 1;

    /**
	 * A possible value for the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code> in the
	 * <code>ResultSet</code> objects returned by the methods
	 * <code>getImportedKeys</code>,  <code>getExportedKeys</code>,
	 * and <code>getCrossReference</code>.
	 * <P>For the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code>,
	 * it indicates that
	 * when the primary key is updated or deleted, the foreign key (imported key)
	 * is changed to <code>NULL</code>.
     */
    public static final int mclimportedKeySetNull  = 2;

    /**
	 * A possible value for the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code> in the
	 * <code>ResultSet</code> objects returned by the methods
	 * <code>getImportedKeys</code>,  <code>getExportedKeys</code>,
	 * and <code>getCrossReference</code>.
	 * <P>For the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code>,
	 * it indicates that
	 * if the primary key has been imported, it cannot be updated or deleted.
     */
    public static final int mclimportedKeyNoAction = 3;

    /**
	 * A possible value for the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code> in the
	 * <code>ResultSet</code> objects returned by the methods
	 * <code>getImportedKeys</code>,  <code>getExportedKeys</code>,
	 * and <code>getCrossReference</code>.
	 * <P>For the columns <code>UPDATE_RULE</code>
	 * and <code>DELETE_RULE</code>,
	 * it indicates that
	 * if the primary key is updated or deleted, the foreign key (imported key)
	 * is set to the default value.
     */
    public static final int mclimportedKeySetDefault  = 4;

    //Tipo de relacion
    public static final int mclRelacionImport = 0;    
    public static final int mclRelacionExport = 1;
    
    //Propiedades de la relacion
    private String msNombreRelacion; //Nombre de la relacion
    private String msTablaRelacionada; //Tabla relacionada
    private int mnTipoRelacion;
    private IListaElementos moCampos; //Campos de la tabla propia
    private IListaElementos moCamposTablaRelacionada; //Campos de la tabla relacionada
    private int mlUpdate=-1; //Regla update utilizada
    private int mlDelete=-1; //Regla delete utilizada
    private String msTabla;
    /** Constructor */
    public JRelacionesDef(final String psNombreRelacion, final int plUpdate, final int plDelete) {
        super();
        msNombreRelacion = psNombreRelacion;
        mlUpdate = plUpdate;
        mlDelete = plDelete;
        moCamposTablaRelacionada = new JListaElementos();
        moCampos = new JListaElementos();
    }
    
    public JRelacionesDef(final String psNombreRelacion) {
        this(psNombreRelacion, mclimportedKeySetDefault, mclimportedKeySetDefault);
    }
    

    /**Recuperar el tipo de la relacion*/
    public int getTipoRelacion() {
        return mnTipoRelacion;
    }
    
    /**Establecer el tipo de la relacion*/
    public void setTipoRelacion(final int pnTipoRelacion) {
        mnTipoRelacion = pnTipoRelacion;
    }
    
    /**Recuperar el nombre de la clave foranea*/
    public String getNombreRelacion() {
        return msNombreRelacion;
    }
    /**Establecer el nombre de la clave foranea*/
    public void setNombreRelacion(final String psNombreRelacion) {
        msNombreRelacion = psNombreRelacion;
    }
    /**Recuperar los campos de la clave foranea*/
    public IListaElementos getListaCamposRelacion() {
        return moCamposTablaRelacionada;
    }
    /**Nombre de la tabla relacionada*/
    public String getTablaRelacionada() {
        return msTablaRelacionada;
    }
    
    public void setTabla(String psTabla){
        msTabla=psTabla;
    }
    
    /**establecer nombre de la tabla relacionada*/
    public void setTablaRelacionada(final String psTablaRelacionada) {
        msTablaRelacionada = psTablaRelacionada;
    }
    /**Recuperar reglas de update*/
    public int getUpdate() {
        return mlUpdate;
    }
    /**Establecer reglas de update*/
    public void setUpdate(final int plUpdate) {
        mlUpdate = plUpdate;
    }
    /**Recuperar reglas de delete*/
    public int getDelete() {
        return mlDelete;
    }
    /**Establecer reglas de delete*/
    public void setDelete(final int plDelete) {
        mlDelete = plDelete;
    }
    
    public void addCampoRelacion(final String psCampoPropio, final String psCampoTablaExterna) {
        moCamposTablaRelacionada.add(psCampoTablaExterna);
        moCampos.add(psCampoPropio);
    }
    
    public String getCampoRelacion(final int plcod) {
        return (String) moCamposTablaRelacionada.get(plcod);
    }
    
    public String getCampoPropio(final int plcod) {
        return (String) moCampos.get(plcod);
    }
    public int getCamposRelacionCount() {
        return moCamposTablaRelacionada.size();
    }
/**
     * devolvemos el objeto sql union (segun tipo de union(inner, left, ...)) en funcion de las propiedades de la clase
     * @param plTipoUnion tipo de union
     * @return objeto select de union de tablas 
     */
    public JSelectUnionTablas getSelectUnionTablas(final int plTipoUnion) {
        String [] lsCamposRelac = new String[moCamposTablaRelacionada.size()];
        for(int i = 0 ; i< lsCamposRelac.length; i++){
            lsCamposRelac[i]=(String)moCamposTablaRelacionada.get(i);
        }
        String [] lsCampos = new String[moCampos.size()];
        for(int i = 0 ; i< lsCampos.length; i++){
            lsCampos[i]=(String)moCampos.get(i);
        }
        
        return new JSelectUnionTablas(plTipoUnion, msTabla, msTablaRelacionada, 
            lsCampos, lsCamposRelac);
    }
    
    public Object clone() throws CloneNotSupportedException {
        JRelacionesDef loRelac = (JRelacionesDef) super.clone();
        loRelac.moCampos = new JListaElementos();
        loRelac.moCamposTablaRelacionada = new JListaElementos();
        for(int i = 0 ; i < moCampos.size(); i++){
            loRelac.moCampos.add(moCampos.get(i));
        }
        for(int i = 0 ; i < moCamposTablaRelacionada.size(); i++){
            loRelac.moCamposTablaRelacionada.add(moCamposTablaRelacionada.get(i));
        }
        
        return loRelac;
                
    }
}
