/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.estructuraBD;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.IConstructorEstructuraBD;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JIndiceDef;
import ListDatos.estructuraBD.JRelacionesDef;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Index;
import com.healthmarketscience.jackcess.PropertyMap;
import com.healthmarketscience.jackcess.Relationship;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.impl.IndexData;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import utiles.JDepuracion;

/**
 *
 * @author eduardo
 */
public class JConstructorEstructuraBDACCESS  implements IConstructorEstructuraBD {

    private Database moBD;
    private String msRuta;
    private JTableDefs moEstructura=null;
    private boolean mbconRelacionesExport;
    private boolean mbconRelacionesInport;
    private boolean mbRelacionesOptimizar=true;

    /** Creates a new instance of JConstructorMetaDatosBD */
    public JConstructorEstructuraBDACCESS(String psRuta) {
        setRuta(psRuta);
        mbconRelacionesExport = false;
        mbconRelacionesInport = true;
    }
    public JConstructorEstructuraBDACCESS(String psRuta, boolean pbOptimizado) {
        this(psRuta);
        mbRelacionesOptimizar = pbOptimizado;
    }
    public JConstructorEstructuraBDACCESS(String psRuta, boolean pbconRelacionesExport, boolean pbconRelacionesInport) {
        this(psRuta);
        mbconRelacionesExport = pbconRelacionesExport;
        mbconRelacionesInport = pbconRelacionesInport;
    }
    public synchronized void setRelacionesOptimizar(boolean pbOptimizar){
        mbRelacionesOptimizar = pbOptimizar;
    }
    public synchronized  void setRuta(String psBD) {
        msRuta=psBD;
        
    }
    public synchronized String getRuta(){
        return msRuta;
    }
    //recuperamos la estructura de la base de datos
    public synchronized JTableDefs getTableDefs() throws Exception {
        if(moEstructura==null){
            moBD = DatabaseBuilder.open(new File(msRuta));
            moEstructura = new JTableDefs();
            String lsTipo;
            int lnTipo = JTableDef.mclTipoTabla;

            try {
                //Recuperar Tablas
                Iterator loIter = moBD.getTableNames().iterator();
                while(loIter.hasNext()) {
                    String lsTabla = loIter.next().toString();
                    lnTipo = JTableDef.mclTipoTabla;
                    JTableDef loTabla = new JTableDef(lsTabla, lnTipo);
                    Table loTable = moBD.getTable(lsTabla);
                    recuperarCampos(loTabla, loTable); //Recuperamos todos los campos
                    recuperarRelaciones(loTabla, loTable);
                    recuperarIndices(loTabla, loTable); //Recuperamos todos los campos
                    moEstructura.add(loTabla);  //Anadimos la tabla a la estructura
                }
            }catch(Exception e) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e);
            } finally{
                moBD=null;
            }
        }
        return moEstructura;
    }


    //Recuperamos los campos de la tabla
    private void recuperarCampos(final JTableDef poTabla, Table loTable) throws Exception {
        String lsCampo;

        try {
            try{
                for(int i = 0 ; i < loTable.getColumnCount(); i++) {
                    Column loColumna = (Column) loTable.getColumns().get(i);
                    String lsTableName = poTabla.getNombre();
                    DataType loTipo = loColumna.getType();
                    int lnTipo = JListDatos.mclTipoCadena;
                    if(loTipo == DataType.BOOLEAN ){
                        lnTipo = JListDatos.mclTipoBoolean;
                    }
                    if(loTipo == DataType.BINARY 
                            || loTipo == DataType.MEMO 
                            || loTipo == DataType.TEXT
                            ){
                        lnTipo = JListDatos.mclTipoCadena;
                    }
                    if(loTipo == DataType.DOUBLE 
                            || loTipo == DataType.FLOAT
                            || loTipo == DataType.MONEY
                            || loTipo == DataType.NUMERIC
                            ){
                        lnTipo = JListDatos.mclTipoNumeroDoble;
                    }
                    if(loTipo == DataType.BYTE 
                            || loTipo == DataType.INT
                            || loTipo == DataType.LONG
                            ){
                        lnTipo = JListDatos.mclTipoNumero;
                    }
                    if(loTipo == DataType.SHORT_DATE_TIME 
                            ){
                        lnTipo = JListDatos.mclTipoFecha;
                    }
                    boolean lbNullable = true;
                    String lsDescrip="";
                    Iterator loItP = loColumna.getProperties().iterator();
                    while(loItP.hasNext()){
                        PropertyMap.Property lsNombre = (PropertyMap.Property) loItP.next();
                        if(lsNombre.getName().equalsIgnoreCase("Required")){
                            lbNullable = !((Boolean)lsNombre.getValue()).booleanValue();
                        }
                        
                   }


                    lsCampo = loColumna.getName();
                    int lSize = loColumna.getLength();
                    lSize = loColumna.getLengthInUnits();
                    JFieldDef loCampo = new JFieldDef(
                            lnTipo
                            ,lsCampo,""
                            ,esPrimaryKey(poTabla,lsCampo)
                            ,lSize
                            ,loTipo.name()
                            ,lbNullable 
                            ,lsDescrip);
                    poTabla.getCampos().addField(loCampo);
                }

            }finally{
            }
        }catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
            throw e;
        }
    }

    private boolean esPrimaryKey(JTableDef poTabla,String psNombre) throws Exception {
        boolean lbResult =false;
        try {
            String lsNombre = psNombre.toUpperCase();
            for(int i = 0 ; i < poTabla.getIndices().getListaIndices().size(); i++){
                JIndiceDef loIndice = poTabla.getIndices().getIndice(i);
                if(loIndice.getEsPrimario()){
                    for(int ii = 0; ii < loIndice.getListaCampos().size();ii++){
                        if(loIndice.getCampoIndice(ii).toUpperCase().compareTo(lsNombre)==0){
                            lbResult = true;
                            break;
                        }
                    }
                }
            }
        }catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
            throw e;
        }

            
        return lbResult;
    }

    private void crearRelacionInversa(JTableDef poTabla, JRelacionesDef poRelacion) throws Exception {
        JRelacionesDef loRelacionInv = new JRelacionesDef(poRelacion.getNombreRelacion() + "inversa");
        JTableDef loTablaInv = moEstructura.get(poRelacion.getTablaRelacionada());
        loTablaInv.getRelaciones().addRelacion(loRelacionInv);
        switch(poRelacion.getTipoRelacion()){
            case JRelacionesDef.mclRelacionImport:
                loRelacionInv.setTipoRelacion(JRelacionesDef.mclRelacionExport);
                break;
            case JRelacionesDef.mclRelacionExport:
                loRelacionInv.setTipoRelacion(JRelacionesDef.mclRelacionImport);
                break;
            default:
                throw new Exception("(RecuperarRelaciones)Opcion de la relacion incorrecta");
        }
        loRelacionInv.setTablaRelacionada(poTabla.getNombre());
        loRelacionInv.setDelete(poRelacion.getDelete());
        loRelacionInv.setUpdate(poRelacion.getUpdate());
        for(int i = 0 ; i < poRelacion.getCamposRelacionCount(); i++){
            loRelacionInv.addCampoRelacion(
                    poRelacion.getCampoRelacion(i),
                    poRelacion.getCampoPropio(i)
                    );
        }


    }
    private void recuperarRelaciones(JTableDef poTabla, Table loTable) throws Exception {

        try {
            List<Relationship> loRelaciones = moBD.getRelationships(loTable);

            for(Relationship loR : loRelaciones){
                JRelacionesDef loRelacion = new JRelacionesDef(loR.getName());
                loRelacion.setTabla(poTabla.getNombre());
                loRelacion.setTablaRelacionada(loR.getFromTable().getName());
                loRelacion.setDelete(loR.cascadeDeletes() ? loRelacion.mclimportedKeyCascade : loRelacion.mclimportedKeySetDefault);
                loRelacion.setUpdate(loR.cascadeUpdates() ? loRelacion.mclimportedKeyCascade : loRelacion.mclimportedKeySetDefault);

                List<Column> loCamposMios = loR.getToColumns();
                List<Column> loCamposFora = loR.getFromColumns();
                for(int lCol = 0 ; lCol < loCamposMios.size();lCol++){
                    Column loColum = loCamposMios.get(lCol);
                    Column loColumFora = loCamposFora.get(lCol);

                    loRelacion.addCampoRelacion(loColum.getName(), loColumFora.getName());
                }
                poTabla.getRelaciones().addRelacion(loRelacion);

            }

        }catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
            throw e;
        }
    }

    private void recuperarIndices(JTableDef poTabla, Table loTable) throws Exception {
        try {
            List<? extends Index> loIndices = loTable.getIndexes();
            try{
                
                for(int i = 0 ; i < loIndices.size(); i++){
                    Index loIndex = loIndices.get(i);
                    
                    if(!loIndex.isForeignKey()){
                        JIndiceDef loIndice = new JIndiceDef(poTabla.getNombre() + "_" + loIndex.getName());
                        loIndice.setEsPrimario(loIndex.isPrimaryKey());
                        loIndice.setEsUnico(loIndex.isUnique());

                        for(int lCol = 0 ; lCol < loIndex.getColumns().size();lCol++){
                            IndexData.ColumnDescriptor loColum = (IndexData.ColumnDescriptor) loIndex.getColumns().get(lCol);
                            loIndice.addCampoIndice(loColum.getName());
                        }
                        poTabla.getIndices().addIndice(loIndice);
                    }
                }
            }finally{
            }
        }catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
            throw e;
        }
    }
    
    public static void main(String[] pas){
        try {
            JConstructorEstructuraBDACCESS loAc = new JConstructorEstructuraBDACCESS("/home/eduardo/d/bda/intecsa/chs/Ipasub97.mdb");
            loAc.getTableDefs();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
