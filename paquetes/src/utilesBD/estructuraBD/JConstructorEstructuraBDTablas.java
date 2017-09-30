/*
 * JConstructorBD.java
 *
 * Created on 23 de enero de 2007, 14:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesBD.estructuraBD;

import ListDatos.estructuraBD.*;
import ListDatos.*;

import java.sql.*;

import utiles.*;


public class JConstructorEstructuraBDTablas  implements IConstructorEstructuraBD {
    private static final long serialVersionUID = 1L;
    
    private final String[] masTablas;
    
    private JTableDefs moEstructura=null;
    private final ISelectMotor moSelect;
    private final JServerServidorDatosBD moServer;
    
    /** Creates a new instance of JConstructorBD */
    public JConstructorEstructuraBDTablas(Connection poBD, String[] pasTablas) {
        moServer = new JServerServidorDatosBD();
        moServer.setConec(poBD);
        moServer.setTipoBDStandar(String.valueOf(JSelectMotor.mclSqlServer));
        masTablas = pasTablas;
        moSelect=null;
    }

    public JConstructorEstructuraBDTablas(Connection poBD, String[] pasTablas, ISelectMotor poSelect) {
        moServer = new JServerServidorDatosBD();
        moServer.setConec(poBD);
        moServer.setSelect(poSelect);
        masTablas = pasTablas;
        moSelect = poSelect;

    }
    public JConstructorEstructuraBDTablas(JServerServidorDatosBD poServer, String[] pasTablas) {
        moServer = poServer;
        masTablas = pasTablas;
        moSelect = moServer.getSelect();
        
    }
    
    //recuperamos la estructura de la base de datos
    public JTableDefs getTableDefs() throws Exception {
        if(moEstructura==null){
            moEstructura = new JTableDefs();
            for(int i = 0; i < masTablas.length;i++){
                try{
                    JTableDef loTabla = moServer.getTableDef(masTablas[i]);
                    if(loTabla!=null){
                        moEstructura.add(loTabla);
                    }
                }catch(Throwable e){
                    JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e.toString());
                }
            }
        }
        return moEstructura;
    }
//    
//    public static JListDatos getListDatos(final ResultSet rs, String psTabla) throws Exception {
//        return JServerServidorDatosBD.getListDatos(rs, psTabla);
//    }
    public static JListDatos getListDatos(JServerServidorDatosBD poServer, String psQuery) throws Exception {
        JSelect loSelect = new JSelect();
        loSelect.msSelectAPelo = psQuery;
        return poServer.getListDatos(loSelect, "");
    }
//    
//    public JTableDef getTableDefQuery(String psQuery, String psTabla1) throws Exception {
//        JSelect loSelect = new JSelect(psTabla1);
//        JListDatos loList =  moServer.getListDatos(loSelect, psTabla1);
//        JTableDef loTabla = new JTableDef(psTabla1);
//        loTabla.setTipo(loTabla.mclTipoTabla);
//        loTabla.setCampos(loList.getFields());
//        return loTabla;
//    }
//    public static JTableDef getTableDefQuery(Connection poConec,String psQuery, String psTabla1) throws SQLException {
//        JTableDef loTabla = null;
//        
//        Statement stmt = null;
//        ResultSet rs = null;
//        try{
//            stmt = poConec.createStatement();
//            
//            rs = stmt.executeQuery(psQuery);
//            
//            loTabla = new JTableDef(psTabla1);
//            loTabla.setTipo(loTabla.mclTipoTabla);
//            
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int numberOfColumns = rsmd.getColumnCount();
//            for(int i = 0; i<numberOfColumns;i++){
//                JFieldDef loCampo = new JFieldDef(
//                        JFieldDef.getTipoNormalDeTipoSQL(rsmd.getColumnType(i+1)),
//                        rsmd.getColumnName(i+1),//nombre
//                        rsmd.getColumnName(i+1),//caption
//                        false,//no se puede saber si es principal
//                        rsmd.getColumnDisplaySize(i+1),//tamano
//                        rsmd.getColumnTypeName(i+1),//nombre tipo bd
//                        rsmd.isNullable(i+1) != rsmd.columnNoNulls,//si es nullable
//                        ""//descripcion
//                        );
//                loTabla.getCampos().addField(loCampo);
//            }
//        } finally {
//            if(rs!=null){
//                rs.close();
//            }
//            if(stmt!=null){
//                stmt.close();
//            }
//        }
//        return loTabla;
//    }
//    public static JTableDef getTableDef(Connection poConec,String psTabla, ISelectMotor poSelect) throws SQLException {
//        if(poSelect==null){
//            try{
//                return getTableDefQuery(poConec,"select * from " + psTabla + " where '1'='2'", psTabla);
//            }catch(Exception e){
//                return getTableDefQuery(poConec,"select * from [" + psTabla + "] where '1'='2'", psTabla);
//            }
//        }else{
//            return getTableDefQuery(poConec,"select * from " + poSelect.msTabla(psTabla, "") + " where '1'='2'", psTabla);
//        }
//    }
//    public JTableDef getTableDef(String psTabla) throws Exception {
//        JSelect loSelect = new JSelect(psTabla);
//        JListDatos loList =  moServer.getListDatos(loSelect, psTabla);
//        JTableDef loTabla = new JTableDef(psTabla);
//        loTabla.setTipo(loTabla.mclTipoTabla);
//        loTabla.setCampos(loList.getFields());
//        return loTabla;
//    }
    
    
    
}
