/*
 * JConstructorEstructuraBDConnection.java
 *
 * Created on 5 de agosto de 2005, 19:54
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesBD.estructuraBD;


import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.sql.*;
import utiles.*;

public class JConstructorEstructuraBDConnection implements IConstructorEstructuraBD {
    private static final long serialVersionUID = 1L;

    private Connection moBD;
    private JTableDefs moEstructura=null;
    private boolean mbconRelacionesExport;
    private boolean mbconRelacionesInport;
    private boolean mbRelacionesOptimizar=true;

    /** Creates a new instance of JConstructorMetaDatosBD */
    public JConstructorEstructuraBDConnection(Connection poBD) {
        this(poBD, true);
    }
    public JConstructorEstructuraBDConnection(Connection poBD, boolean pbOptimizado) {
        moBD = poBD;
        mbRelacionesOptimizar = pbOptimizado;
        mbconRelacionesExport=false;
        mbconRelacionesInport=true;
    }
    public JConstructorEstructuraBDConnection(Connection poBD, boolean pbconRelacionesExport, boolean pbconRelacionesInport) {
        moBD = poBD;
        mbconRelacionesExport = pbconRelacionesExport;
        mbconRelacionesInport = pbconRelacionesInport;
    }
    public JConstructorEstructuraBDConnection(JServerServidorDatosBD poBD) {
        this(poBD, true);
    }
    public JConstructorEstructuraBDConnection(JServerServidorDatosBD poBD, boolean pbOptimizado) {
        moBD = poBD.getConec();
        mbRelacionesOptimizar = pbOptimizado;
    }
    public JConstructorEstructuraBDConnection(JServerServidorDatosBD poBD, boolean pbconRelacionesExport, boolean pbconRelacionesInport) {
        moBD = poBD.getConec();
        mbconRelacionesExport = pbconRelacionesExport;
        mbconRelacionesInport = pbconRelacionesInport;
    }
    public synchronized void setRelacionesOptimizar(boolean pbOptimizar){
        mbRelacionesOptimizar = pbOptimizar;
    }
    public synchronized  void setConec(Connection poBD){
        moBD = poBD;
    }
    public synchronized Connection getConec(){
        return moBD;
    }
    //recuperamos la estructura de la base de datos
    public synchronized JTableDefs getTableDefs() throws Exception {
        if(moEstructura==null){
            moEstructura = new JTableDefs();
            String lsTipo;
            int lnTipo;

            try {
                //Recuperar Tablas
                ResultSet loResultset = getConec().getMetaData().getTables(null,null,"%",null);
                try{
                    while(loResultset.next()) {
                        lsTipo = loResultset.getString("TABLE_TYPE");
                        lnTipo = -1;
                        if(lsTipo!=null){
                            if(lsTipo.compareTo("TABLE") == 0) {
                                lnTipo = JTableDef.mclTipoTabla;
                            } else if(lsTipo.compareTo("VIEW") == 0) {
                                lnTipo = JTableDef.mclTipoView;
                            } else if(lsTipo.compareTo("SYSTEM TABLE") == 0) {
                                lnTipo = JTableDef.mclTipoSystem;
                            } else if(lsTipo.compareTo("GLOBAL TEMPORARY") == 0) {
                                lnTipo = JTableDef.mclTipoGlobalTem;
                            } else if(lsTipo.compareTo("LOCAL TEMPORARY") == 0) {
                                lnTipo = JTableDef.mclTipoLocalTem;
                            } else if(lsTipo.compareTo("ALIAS") == 0) {
                                lnTipo = JTableDef.mclTipoAlias;
                            } else if(lsTipo.compareTo("SYNONYM") == 0) {
                                lnTipo = JTableDef.mclTipoSynonym;
                            } else if(lsTipo.compareTo("INDEX") == 0) {
                                lnTipo = JTableDef.mclTipoIndex;
                            }
                        }
                        if((mbRelacionesOptimizar &&  lnTipo == JTableDef.mclTipoTabla)
                                || !mbRelacionesOptimizar 
                                ){
                        JTableDef loTabla = new JTableDef(loResultset.getString("TABLE_NAME"),lnTipo);
                        recuperarIndices(loTabla); //Recuperamos los indices de la tabla
                        recuperarCampos(loTabla); //Recuperamos todos los campos
                        moEstructura.add(loTabla);  //Anadimos la tabla a la estructura
                        }
                    }
                }finally{
                    loResultset.close();
                    loResultset=null;
                }
                if(mbconRelacionesInport){
                    recuperarRelaciones(moEstructura,JRelacionesDef.mclRelacionImport); //Recuperamos las claves importadas
                }
                //si es optimizar las relaciones export se calculan a partir de las import
                //se supone q una relacion import es la inversa de una relacion export
                if(mbconRelacionesExport && !mbRelacionesOptimizar){
                    recuperarRelaciones(moEstructura,JRelacionesDef.mclRelacionExport); //Recuperamos las claves exportadas
                }
            }catch(Exception e) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e);
            }
        }
        return moEstructura;
    }


    //Recuperamos los campos de la tabla
    private void recuperarCampos(final JTableDef poTabla) throws Exception {
        int lnTipo = JListDatos.mclTipoCadena;
        String lsCampo;

        try {
            //Recuperar Tablas
            ResultSet loResultset = getConec().getMetaData().getColumns(null,null,poTabla.getNombre(),"%");
            try{
                while(loResultset.next()) {
                    String lsTableName = loResultset.getString("TABLE_NAME");
                    if(lsTableName==null || lsTableName.equals("") || lsTableName.equalsIgnoreCase(poTabla.getNombre())){
                        String lsTippo=loResultset.getString("DATA_TYPE");
                        if(lsTippo!=null){
                            lnTipo = new Integer(lsTippo).intValue();
                            switch (lnTipo) {
                                case 16://java.sql.Types.BOOLEAN
                                case java.sql.Types.BIT:
                                    lnTipo = JListDatos.mclTipoBoolean;
                                    break;
                                case java.sql.Types.DATE:
                                case java.sql.Types.TIME:
                                case java.sql.Types.TIMESTAMP:
                                    lnTipo = JListDatos.mclTipoFecha;
                                    break;
                                case java.sql.Types.INTEGER:
                                case java.sql.Types.SMALLINT:
                                case java.sql.Types.BIGINT:
                                case java.sql.Types.TINYINT:
                                    lnTipo = JListDatos.mclTipoNumero;
                                    break;
                                case java.sql.Types.NUMERIC:
                                case java.sql.Types.DECIMAL:
                                case java.sql.Types.DOUBLE:
                                case java.sql.Types.FLOAT:
                                case java.sql.Types.REAL:
                                    lnTipo = JListDatos.mclTipoNumeroDoble;
                                    break;
        //                        case java.sql.Types.LONGVARCHAR:
        //                        case java.sql.Types.CHAR:
        //                        case java.sql.Types.VARCHAR:
        //                        case java.sql.Types.BINARY:
        //                        case java.sql.Types.VARBINARY:
        //                        case java.sql.Types.LONGVARBINARY:
        //                            lnTipo = JListDatos.mclTipoCadena;
        //                            break;
                                default:
                                    lnTipo = JListDatos.mclTipoCadena;
                                    break;
                            }
                        }

                        lsCampo = loResultset.getString("COLUMN_NAME");
                        String lsNullable = loResultset.getString("NULLABLE");
                        boolean lbNullable = true;
                        if(lsNullable!=null &&
                           (lsNullable.equals("0") ||
                            lsNullable.equals("false")
                           )){
                            lbNullable = false;
                        }
                        int lSize = 0;
                        try{
                            lSize = new Integer(loResultset.getString("COLUMN_SIZE")).intValue();
                        }catch(Exception e){}

                        JFieldDef loCampo = new JFieldDef(
                                lnTipo,lsCampo,"",esPrimaryKey(poTabla,lsCampo),
                                lSize,
                                loResultset.getString("TYPE_NAME"),
                                lbNullable ,
                                loResultset.getString("REMARKS"));
                        poTabla.getCampos().addField(loCampo);
                    }
                }
            }finally{
                if(loResultset!=null){
                    loResultset.close();
                    loResultset=null;
                }
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
    private void recuperarRelaciones(JTableDefs poTablas,int pnTipoRel) throws Exception {
        String lsNombreRelacion="",lsNombreRelacionAnt="";
        String lsTableName="";
        String lsColumName="";
        String lsColumNameFK="";
        String lsUPDATE="";
        String lsDELETE="";

        int i;

        try {
            for(i=0;i<poTablas.getListaTablas().size();i++) {
                JTableDef loTabla = poTablas.get(i);
                if(loTabla.getTipo() == JTableDef.mclTipoTabla){
                    ResultSet loResultset = null;
                    try{
                        switch(pnTipoRel) {
                            case JRelacionesDef.mclRelacionImport:
                                loResultset = getConec().getMetaData().getImportedKeys(null,null,loTabla.getNombre());
                                break;
                            case JRelacionesDef.mclRelacionExport:
                                loResultset = getConec().getMetaData().getExportedKeys(null,null,loTabla.getNombre());
                                break;
                            default:
                                throw new Exception("(RecuperarRelaciones)Opcion de la relacion incorrecta");
                        }
                        lsNombreRelacionAnt = "";
                        JRelacionesDef loRelacion=null;
                                                
                        JListDatos loList = JServerServidorDatosBD.getListDatos(loResultset, "");
                        loList.ordenar(loList.getFields().getIndiceDeCampo("FK_NAME"));
                        if(loList.moveFirst()){
                            do{
                                switch(pnTipoRel) {
                                    case JRelacionesDef.mclRelacionImport:
                                        lsNombreRelacion = loList.getFields("FK_NAME").getString();
                                        lsTableName = loList.getFields("PKTABLE_NAME").getString();
                                        lsColumName = loList.getFields("PKCOLUMN_NAME").getString();
                                        lsColumNameFK = loList.getFields("FKCOLUMN_NAME").getString();
                                        lsUPDATE = loList.getFields("UPDATE_RULE").getString();
                                        lsDELETE = loList.getFields("DELETE_RULE").getString();
                                        break;
                                    case JRelacionesDef.mclRelacionExport:
                                        lsNombreRelacion = loList.getFields("FK_NAME").getString();
                                        lsTableName = loList.getFields("FKTABLE_NAME").getString();
                                        lsColumName = loList.getFields("FKCOLUMN_NAME").getString();
                                        lsColumNameFK = loList.getFields("PKCOLUMN_NAME").getString();

                                        lsUPDATE = loList.getFields("UPDATE_RULE").getString();
                                        lsDELETE = loList.getFields("DELETE_RULE").getString();
                                        break;
                                    default:
                                        throw new Exception("(RecuperarRelaciones)Opcion de la relacion incorrecta");
                                }

                                if(lsNombreRelacion.compareTo(lsNombreRelacionAnt) == 0) {
                                    loRelacion.addCampoRelacion(lsColumNameFK, lsColumName);
                                }else{
                                    if(loRelacion!=null && mbRelacionesOptimizar && mbconRelacionesExport){
                                        crearRelacionInversa(loTabla, loRelacion);
                                    }
                                    loRelacion = new JRelacionesDef(lsNombreRelacion);
                                    loRelacion.setTipoRelacion(pnTipoRel);
                                    loRelacion.setTablaRelacionada(lsTableName);
                                    if(JConversiones.isNumeric(lsUPDATE)){
                                        loRelacion.setUpdate((int)JConversiones.cdbl(lsUPDATE));
                                    }
                                    if(JConversiones.isNumeric(lsDELETE)){
                                        loRelacion.setDelete((int)JConversiones.cdbl(lsDELETE));
                                    }
                                    loRelacion.addCampoRelacion(lsColumNameFK, lsColumName);
                                    loTabla.getRelaciones().addRelacion(loRelacion);
                                }
                                lsNombreRelacionAnt = lsNombreRelacion;
                                
                            }while(loList.moveNext());
                        }
                        
                        
                        while(loResultset.next()) {
                    }
                    if(loRelacion!=null && mbRelacionesOptimizar && mbconRelacionesExport){
                        crearRelacionInversa(loTabla, loRelacion);
                    }

                    }finally{
                        if(loResultset!=null){
                            loResultset.close();
                        }
                    }
                }

            }
        }catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
            throw e;
        }
    }

    private void recuperarIndices(JTableDef poTabla) throws Exception {
        String lsPkName="",lsPkNameAnt="";
//        String lsTableName="";
        String lsColumName="";

        try {
            JIndiceDef loIndice=null;
            //indice primario
            ResultSet loResultset = getConec().getMetaData().getPrimaryKeys(null,null,poTabla.getNombre());
            try{
                while(loResultset.next()) {

                    lsPkName = loResultset.getString("PK_NAME");
    //                lsTableName = loResultset.getString("TABLE_NAME");
                    lsColumName = loResultset.getString("COLUMN_NAME");

                    if(lsPkName.compareTo(lsPkNameAnt) != 0) {
                        loIndice = new JIndiceDef(lsPkName);
                        poTabla.getIndices().addIndice(loIndice);
                        loIndice.setEsPrimario(true);
                    }
                    loIndice.addCampoIndice(lsColumName);
                    lsPkNameAnt = lsPkName;
                }
            }finally{
                loResultset.close();
                loResultset=null;

            }
            //resto de indices
            lsPkName="";
            lsPkNameAnt="";
            lsColumName="";
            loResultset = getConec().getMetaData().getIndexInfo(null,null,poTabla.getNombre(),false,false);
            try{
                while(loResultset.next()) {

                    lsPkName = loResultset.getString("INDEX_NAME");
    //                lsTableName = loResultset.getString("TABLE_NAME");
                    lsColumName = loResultset.getString("COLUMN_NAME");
                    if(lsPkName!=null){
                        if(lsPkName.compareTo(lsPkNameAnt) != 0) {
                            loIndice = new JIndiceDef(lsPkName);
                            if(poTabla.getIndices().getIndice(lsPkName)==null){
                                poTabla.getIndices().addIndice(loIndice);
                            }
                            String lsNoUnico = loResultset.getString("NON_UNIQUE");
                            loIndice.setEsUnico(!JConversiones.cBool(lsNoUnico));
                        }
                        loIndice.addCampoIndice(lsColumName);
                        lsPkNameAnt = lsPkName;
                    }
                }
            }finally{
                loResultset.close();
                loResultset=null;

            }

        }catch(Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
            throw e;
        }
    }
}
