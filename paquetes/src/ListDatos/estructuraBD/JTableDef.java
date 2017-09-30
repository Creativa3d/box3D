/*
 * JTableDef.java
 *
 * Created on 15 de abril de 2005, 12:08
 */

package ListDatos.estructuraBD;

import ListDatos.*;
import utiles.*;

public class JTableDef  implements java.io.Serializable, Cloneable {
    private static final long serialVersionUID = 33333339L;
    //Tipos de tabla
    public static final int mclTipoTabla = 0;
    public static final int mclTipoView = 1;
    public static final int mclTipoSystem = 2;
    public static final int mclTipoGlobalTem = 3;
    public static final int mclTipoLocalTem = 4;
    public static final int mclTipoAlias = 5;
    public static final int mclTipoSynonym = 6;
    public static final int mclTipoIndex = 7;
    
    //Elementos de la tabla
    private String msNombre;
    private int mlTipo=mclTipoTabla;
//    private String msNombrePk;
    private JFieldDefs moCampos;
    private JRelacionesDefs moRelaciones;
    private JIndiceDefs moIndices;
    
    /**Crea una tabla por defecto de tipo TABLE*/
    public JTableDef(final String psNombre) {
        super();
        msNombre = psNombre;
        mlTipo = mclTipoTabla;
        moCampos = new JFieldDefs();
        moRelaciones = new JRelacionesDefs(msNombre);
        moIndices = new JIndiceDefs();
    }
    
    /**Crea una tabla asignandole un tipo*/
    public JTableDef(final String psNombre, final int plTipo) {
        super();
        msNombre = psNombre;
        mlTipo = plTipo;
        moCampos = new JFieldDefs();
        moCampos.setTabla(msNombre);
        moRelaciones = new JRelacionesDefs(msNombre); 
        moIndices = new JIndiceDefs();
    }
    /**Crea una tabla asignandole un tipo*/
    public JTableDef(final String psNombre, final int plTipo,final String[] psNombres, final int[] palCamposPrincipales, final int[] palTipos, final int[] palTamanos) {
        super();
        msNombre = psNombre;
        mlTipo = plTipo;
        moCampos = new JFieldDefs(psNombres, palCamposPrincipales,psNombres,palTipos, palTamanos);
        moCampos.setTabla(msNombre);
        moRelaciones = new JRelacionesDefs(msNombre); 
        moIndices = new JIndiceDefs();
    }
    public JTableDef(final JListDatos poList) {
        this(poList.msTabla, mclTipoTabla, poList.getFields().msNombres(), poList.getFields().malCamposPrincipales(), poList.getFields().malTipos(), poList.getFields().malTamanos());
    }
    
    /**Recuperar el tipo de tabla*/
    public int getTipo() {
        int lAux = mlTipo;
        
        if(msNombre.equalsIgnoreCase("dtproperties")){//SQLSERVER
            lAux = mclTipoSystem;
        }
        return lAux;
    }
    /**Establecer el tipo de la tabla*/
    public void setTipo(final int plTipo) {
        mlTipo = plTipo;
    }

    /**Recuperar nombre de la tabla*/
    public String getNombre() {
        return msNombre;
    }
    /**establecer el nombre de la tabla*/
    public void setNombre(final String psNombre) {
        msNombre = psNombre;
        moCampos.setTabla(msNombre);
    }
    
    /**Devolver el objeto Campos*/
    public JFieldDefs getCampos(){
        return moCampos;
    }
    /**establecer el objeto campos*/
    public void setCampos(final JFieldDefs poCampos) {
        moCampos = poCampos;
    }
    
    /**Devuelve la lista de campos de la tabla actual que son importados de otras tablas*/
    public IListaElementos getCamposImportados() {
        JListaElementos loCamposImport = new JListaElementos();
        
        for(int i=0 ; i<moCampos.count(); i++) { //para cada campo
            if(moRelaciones.getRelacionDeCampo(moCampos.get(i).getNombre()) != null) {
                loCamposImport.add(moCampos.get(i).getNombre());
            }
        }
        
        return (IListaElementos) loCamposImport;
    }
    
    /**Devolver el objeto ClavesImport*/
    public JRelacionesDefs getRelaciones(){
        return moRelaciones;
    }
    /**establecer el objeto ClavesImport*/
    public void setRelaciones(final JRelacionesDefs poRelaciones) {
        moRelaciones = poRelaciones;
    }

    /**Devolver el objeto Indices*/
    public JIndiceDefs getIndices(){
        return moIndices;
    }
    /**establecer el objeto Indices*/
    public void setIndices(final JIndiceDefs poIndices) {
        moIndices = poIndices;
    }
    
    public String toString(){
        return msNombre;
    }
    
    public JListDatos getListDatos() throws CloneNotSupportedException{
        JListDatos loList = new JListDatos();
        loList.setFields(getCampos().Clone());
        loList.msTabla = getNombre();
        return loList;
    }

    public Object clone() throws CloneNotSupportedException {
        JTableDef loTabla =  (JTableDef) super.clone();
        loTabla.moCampos=moCampos.Clone();
        loTabla.moIndices=(JIndiceDefs)moIndices.clone();
        loTabla.moRelaciones=(JRelacionesDefs)moRelaciones.clone();
        return loTabla;
    }
    
}
