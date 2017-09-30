/*
 * ARegistrosRelacionados.java
 *
 * Created on 18 de noviembre de 2004, 12:08
 */

package utilesBD.relaciones;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.util.Iterator;

import utiles.*;

/**Para ver las tablas relacionadas*/
public class JRelacionadosRegistros  extends JServerEjecutarAbstract {
    private static final long serialVersionUID = 33333343L;
    private boolean mbComprimido=false;
    JActualizar moActu = null;
    
    /**
     * Creates a new instance of ARegistrosRelacionados
     * @param psDir directorio base de los servlet
     */
    public JRelacionadosRegistros(JActualizar poActu) {
        moActu=poActu;
    }
    
    /**
     * ver la relaciones en funcion de poActu, que tiene la tabla base y el registro al cual buscamos las relaciones
     * @param poRelaciones conj. relaciones
     * @param poActu Dato a buscar los registros relacionados
     * @param poServer servidor datos
     * @throws Exception exception 
     */
    public void verRelaciones(JRelacionesTablasRegistros poRelaciones, JActualizar poActu, ListDatos.IServerServidorDatos poServer) throws Exception {
        //recorremos todos las relaciones
        JTableDef loTabla = poServer.getTableDefs().get(poActu.getTabla());
        Iterator loEnum = loTabla.getRelaciones().getListaRelaciones().iterator();
        for(;loEnum.hasNext();){
            JRelacionesDef loRelac = (JRelacionesDef)loEnum.next();
            //comprobamos si es tabla principal de una relacion y tiene integridad referencial
            if( ( loRelac.getTipoRelacion()==loRelac.mclRelacionExport) &&
                ( (loRelac.getUpdate()==loRelac.mclimportedKeyRestrict) ||
                  (loRelac.getUpdate()==loRelac.mclimportedKeyCascade) )
              ){
                //creamos un objeto de registros relnacionados
                JRelacionTablaRegistros loTablaRelac = poRelaciones.addTablaRelacionada(loRelac.getTablaRelacionada());
                JTableDef loTablaRelacionada = poServer.getTableDefs().get(loRelac.getTablaRelacionada());
                //creamos la select
                JSelect loSelect = new JSelect(poActu.getTabla());
                loSelect.getFrom().addTabla(loRelac.getSelectUnionTablas(JSelectUnionTablas.mclInner));
                loSelect.addCampo(loRelac.getTablaRelacionada(), loSelect.mcsTodosCampos);
                for(int i =0;i<loRelac.getCamposRelacionCount() ;i++){
                    JFieldDef loField = poActu.getFields().get(loRelac.getCampoPropio(i));
                    loSelect.getWhere().addCondicionSQL(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual, 
                        new String[]{loField.getNombre()},
                        new String[]{loField.getString()},
                        new int[]{loField.getTipo()});
                }
                loSelect.getWhere().inicializar(poActu.getTabla(), null, null);
                //procesamos la consulta
                procesarConsulta(loSelect ,loTablaRelac, poServer, loTabla, loTablaRelacionada);
                //procesamos los registros y llamamos recursivamente a verRelaciones por registro
                procesarRegistros(poRelaciones, loTablaRelac, poServer);
            }
        }
    }
    private void procesarRegistros(JRelacionesTablasRegistros poRelaciones, JRelacionTablaRegistros poTablaRelac, ListDatos.IServerServidorDatos poServer) throws Exception {
        Iterator loEnum =  poTablaRelac.moFilas.iterator();
        for(;loEnum.hasNext();){
            JFieldDefs loFields = poTablaRelac.moFields.Clone();
            loFields.cargar((IFilaDatos)loEnum.next());
            JActualizar loAct = new JActualizar(loFields, poTablaRelac.msTabla, JListDatos.mclBorrar, null, null, null);
            verRelaciones(poRelaciones, loAct, poServer);
        }
    }
    
//    private int leerEstructura(ResultSet rs, JRelacionTablaRegistros poRelacionTabla) throws Exception {
//        ResultSetMetaData rsmd = rs.getMetaData();
//        int numberOfColumns = rsmd.getColumnCount();
//        //creamos los metadatos
//        int[] lalTipos = new int[numberOfColumns];
//        String[] lasNombres= new String[numberOfColumns];
////        int[] lalTamanos = new int[numberOfColumns];
//        int lCamposPrincipales = 0;
//
//        for(int i = 0; i < numberOfColumns; i++) {
//            lalTipos[i] = JFieldDef.getTipoNormalDeTipoSQL(rsmd.getColumnType(i+1));
//            lasNombres[i] = rsmd.getColumnName(i+1);
////            lalTamanos[i] = rsmd.getPrecision(i+1);
////            //pq al hacer lo de los campos principales 
////            if((lalTamanos[i]<=0)&&(lalTipos[i]==JListDatos.mclTipoCadena)) lalTamanos[i] = 100;
//            if((rsmd.isNullable(i+1) == rsmd.columnNoNulls)&&
//               (rsmd.getColumnType(i+1)!=Types.BINARY)&&
//               (rsmd.getColumnType(i+1)!=Types.BLOB)&&
//               (rsmd.getColumnType(i+1)!=Types.CLOB)&&
//               (rsmd.getColumnType(i+1)!=Types.LONGVARBINARY)&&
//               (rsmd.getColumnType(i+1)!=Types.LONGVARCHAR)&&
//               (rsmd.getColumnType(i+1)!=Types.OTHER)&&
//               (rsmd.getColumnType(i+1)!=Types.VARBINARY)
//               ){
//                lCamposPrincipales++;
//            }
//        }
//        int[] lalCamposPrincipales = new int[lCamposPrincipales];
//        lCamposPrincipales = 0;
//        for(int i = 0;i<numberOfColumns;i++){
//            if((rsmd.isNullable(i+1) == rsmd.columnNoNulls)&&
//               (rsmd.getColumnType(i+1)!=Types.BINARY)&&
//               (rsmd.getColumnType(i+1)!=Types.BLOB)&&
//               (rsmd.getColumnType(i+1)!=Types.CLOB)&&
//               (rsmd.getColumnType(i+1)!=Types.LONGVARBINARY)&&
//               (rsmd.getColumnType(i+1)!=Types.LONGVARCHAR)&&
//               (rsmd.getColumnType(i+1)!=Types.OTHER)&&
//               (rsmd.getColumnType(i+1)!=Types.VARBINARY)
//               ){
//                lalCamposPrincipales[lCamposPrincipales] = i;
//                lCamposPrincipales++;
//            }
//        }
//
//        poRelacionTabla.crearCampos(lasNombres, lalCamposPrincipales, lalTipos);
//        return numberOfColumns;
//    }
    /**
     * procesamos la consulta
     * @param loSelect consulta a ejecutar
     * @param poRelacionTabla almacen de registros relacionadas
     * @throws Exception Exception
     */
    protected void procesarConsulta(
            final JSelect loSelect, final JRelacionTablaRegistros poRelacionTabla, final IServerServidorDatos poServer, 
            final JTableDef poTabla, final JTableDef poTablaRelacionada) throws Exception {
        JListDatos loListDatos = new JListDatos();
        loListDatos.setFields(poTablaRelacionada.getCampos());
        loListDatos.msTabla = poTablaRelacionada.getNombre();
        
        poRelacionTabla.crearCampos(
                loListDatos.getFields().msNombres(),
                loListDatos.getFields().malCamposPrincipales(),
                loListDatos.getFields().malTipos()
                );
        
        poServer.recuperarDatos(loListDatos, loSelect, "", false, false, JListDatos.mclSelectNormal);
        if(loListDatos.moveFirst()){
            do{
                poRelacionTabla.addDatos(loListDatos.moFila());
            }while(loListDatos.moveNext());
        }
    }

    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception {
        JRelacionesTablasRegistros loResultado = new JRelacionesTablasRegistros();
        verRelaciones(loResultado, moActu, poServer);
        return loResultado;
    }


    public String getXML() {
        throw new InternalError("Todavia no implementado la parte XML");
    }

    public boolean getComprimido() {
        return mbComprimido;
    }

    public void setComprimido(boolean pbValor) {
        mbComprimido = pbValor;
    }
}
