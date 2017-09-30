/*
 * JServerServidorDatosBD.java
 *
 * Created on 16 de enero de 2007, 11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.IOException;
import java.sql.CallableStatement;
import java.util.Calendar;

import utiles.*;

//con base de datos
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;

public class JServerServidorDatosBD extends JServidorDatosAbtrac {
//    public static long mlTiempoTotal1=0;
//    public static long mlTiempoTotalBinario1=0;

    /**motor de select*/
    protected ISelectMotor moSelect = JSelectMotorFactory.getInstance().getSelectMotorDefecto();
    protected IConstructorEstructuraBD moConstrucEstruc;
    protected JTableDefs moTableDefs=null;

    /**conexion con base de datos*/
    protected transient Connection moConec=null;


    protected JServerServidorDatosBDParam moParam = new JServerServidorDatosBDParam();

    private int mlNTrasacAnidadas=0;

    private boolean mbSiempreBorrarEspaciosFinal=true;

    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosBD() {
        super();
    }

    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     */
    public JServerServidorDatosBD(String psDriver, String psURL, String psUsu, String psPassword) throws Throwable{
        this(psDriver, psURL, psUsu, psPassword, "");
    }
    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     * @param psTipoBDStandar BD Definidas en JSelectMotor
     */
    public JServerServidorDatosBD(final String psDriver, final String psURL, final String psUsu, final String psPassword, final String psTipoBDStandar) throws Throwable{
        setConexion(psDriver, psURL, psUsu, psPassword, psTipoBDStandar);
    }
    public void setParam(JServerServidorDatosBDParam poParam){
        moParam=poParam;
    }
    public void setSiempreBorrarEspaciosFinal(boolean pbValor){
        mbSiempreBorrarEspaciosFinal = pbValor;
    }
    public boolean isSiempreBorrarEspaciosFinal(){
        return mbSiempreBorrarEspaciosFinal;
    }

    public void setConexion(JServerServidorDatosConexion poServer) throws Throwable {
        setConexion(poServer.getClase(), poServer.getURL(), poServer.getUSUARIO(), poServer.getPASSWORD(), String.valueOf(poServer.getTipoBD()));
    }
    
    public void setConexion(final String psDriver, final String psURL, final String psUsu, final String psPassword, final String psTipoBDStandar) throws Throwable{
        Class.forName(psDriver);
//        if (JCadenas.isVacio(psUsu) && JCadenas.isVacio(psPassword)){
//          moConec = DriverManager.getConnection(psURL);
//        }else{
          moConec = DriverManager.getConnection(psURL, psUsu, psPassword);
//        }
        setTipoBDStandar(psTipoBDStandar);
    }
    public void setConec(java.sql.Connection poConec){
        moConec=poConec;
    }
    public java.sql.Connection getConec(){
        return moConec;
    }
    public void setTipoBDStandar(String psTipoStandar){
        moSelect = JSelectMotorFactory.getInstance().getSelectMotor(psTipoStandar);
        if(moSelect==null){
            moSelect = JSelectMotorFactory.getInstance().getSelectMotorDefecto();
        }
    }
    public void setSelect(ISelectMotor poSelect){
        moSelect= poSelect;
    }
    public ISelectMotor getSelect() {
        return moSelect;
    }

    public void setConstrucEstruc(IConstructorEstructuraBD poConstrucEstruc){
        moConstrucEstruc=poConstrucEstruc;
    }
    public IConstructorEstructuraBD  getConstrucEstruc(){
        if(moConstrucEstruc==null){
            moConstrucEstruc = new JConstructorEstructuraBDConnection(this);            
        }
        return moConstrucEstruc;
    }
    
    public void setTableDefs(JTableDefs poTableDefs){
        moTableDefs=poTableDefs;
    }

    /**
     * establece el usuario no se usa
     * @param psUsuario usuario
     * @param psPassword password
     * @param psPermisos permisos
     */
    public void setUsuario(  String psUsuario,String psPassword,String psPermisos){
      msUsuario=psUsuario;
      msPassWord=psPassword;
      msPermisos=psPermisos;
    }
    /**
     * devuelve el usuario no se usa
     * @return usuario
     */
    public String getUsuario(){
      return msUsuario;
    }

    public JServerServidorDatosBDParam getParamBD(){
        return moParam;
    }
    @Override
    public JServerServidorDatosParam getParametros() {
        return moParam;
    }

    ////////////////////////////////////////77
    ////Actualizaciones y sincronizacion fisica
    //////////////////////////////////////////
    public JTableDefs getTableDefs() throws Exception {
        if(moTableDefs==null){
            if(moConstrucEstruc==null){
                throw new ExceptionNoImplementado("No se ha asignado el constructor de definicion de campos");
            }
            moTableDefs = moConstrucEstruc.getTableDefs();
        }
        return moTableDefs;
    }
    public IResultado modificarEstructura(ISelectEjecutarSelect poEstruc) {
        return moActualizar(null, null, null, poEstruc);
    }
    public IResultado actualizar(String psSelect, JActualizar poActualizar){
        IResultado loResult;
        if(getParametros().isSoloLectura()){
            loResult = new JResultado("Solo lectura", false);
        }else{

            loResult = moActualizar(null, poActualizar, null, null);

            if (loResult.getBien()) {
                actualizarDatosCacheConj(loResult.getListDatos(), psSelect);
            }
        }
        return loResult;
    }
//    public IResultado actualizarConj(ISelectEjecutarUpdate poActuConj) {
//        IResultado loResult;
//        if(isSoloLectura()){
//            loResult = new JResultado("Solo lectura", false);
//        }else{
//            loResult = moActualizar(null, poActuConj, null, null);
//
//            if (loResult.getBien()) {
//                actualizarDatosCacheConj(loResult.getListDatos(), "");
//            }
//        }
//        return loResult;
//    }
    public IResultado ejecutarServer(IServerEjecutar poEjecutar){
        IResultado loResult = moActualizar(null, null,poEjecutar, null);

        if (loResult.getBien()) {
            actualizarDatosCacheConj(loResult.getListDatos(), "");
        }
        return loResult;
    }
    protected synchronized void beginTrans() throws SQLException {
        if(mlNTrasacAnidadas==0){
            moConec.setAutoCommit(false);
        }
        mlNTrasacAnidadas++;
    }
    protected synchronized void commitTrans() throws SQLException{
        mlNTrasacAnidadas--;
        if(mlNTrasacAnidadas==0){
            moConec.commit();
            //restauramos el estado de la conexion
            moConec.setAutoCommit(true);
        }
        if(mlNTrasacAnidadas<0){
            mlNTrasacAnidadas=0;
            JDepuracion.anadirTexto(
                    JDepuracion.mclINFORMACION,
                    this.getClass().getName(), getClass().getName() + "(commitTrans): mlNTrasacAnidadas<0->Nunca deberia haber llegado aqui",
                    (getParametros() == null ? "" : getParametros().getTAG())
                    );
        }
    }
    protected synchronized void rollBackTrans() throws SQLException{
        mlNTrasacAnidadas--;
        if(mlNTrasacAnidadas==0){
            moConec.rollback();
            //restauramos el estado de la conexion
            moConec.setAutoCommit(true);
        }
        if(mlNTrasacAnidadas<0){
            mlNTrasacAnidadas=0;
            JDepuracion.anadirTexto(
                    JDepuracion.mclINFORMACION,
                    this.getClass().getName(), getClass().getName() + "(rollBackTrans): mlNTrasacAnidadas<0->Nunca deberia haber llegado aqui" ,
                    (getParametros() == null ? "" : getParametros().getTAG()));
        }
    }

    public IResultado moActualizar(String psNombreServlet, JActualizar poActualizar, IServerEjecutar poEjecutar, ISelectEjecutarSelect poEstruc){
        IResultado loResult = null;
        boolean lbTrans = false;
        boolean lbConTrans = true;
        if(moConec!=null){
            try{
//                //si solo es una actualizacion comprobamos si esta en autocommit y entonces sin transaccion
//                if(poActualizar!=null && moConec.getAutoCommit()){
//                    lbConTrans = false;
//                }
                if(poEjecutar!=null && poEjecutar.getParametros()!=null){
                    if(poEjecutar.getParametros().getValorAsBoolean(JServerEjecutarParametros.mcsSIN_EDICION)){
                        lbConTrans = false;
                    }
                }

                if(lbConTrans){
                    //comenzamos la transaccion
                    lbTrans = true;
                    beginTrans();
                }

                //ejecutamos el actualizador
                if(poActualizar==null){
                    if(poEjecutar==null){
                        loResult = ejecutarSQLCompleta(poEstruc, moConec, moSelect);
                    }else{
                        loResult = poEjecutar.ejecutar(this);
                    }
                }else{
                    if(poActualizar.getFields()!=null && poActualizar.getTabla()!=null){
                        int lTipoFiltro = mclFiltroTipoEdicion;
                        switch(poActualizar.getTipoModif()){
                            case JListDatos.mclNuevo:
                                lTipoFiltro=mclFiltroTipoNuevo;
                                break;
                            case JListDatos.mclBorrar:
                                lTipoFiltro=mclFiltroTipoBorrar;
                                break;
                            default:
                                lTipoFiltro = mclFiltroTipoEdicion;
                        }
                        comprobarRestriciones(lTipoFiltro, "", poActualizar.getTabla(), poActualizar.getFields());
                    }
                    loResult = ejecutarSQLCompleta(moConec, moSelect, poActualizar);
                }
            }catch(Throwable e1){
                 JDepuracion.anadirTexto(JDepuracion.mclWARNING,this.getClass().getName(), e1,(getParametros() == null ? "" : getParametros().getTAG()));
                 loResult = new JResultado(new JFilaDatosDefecto(), "", "En Servidor=" + e1.toString(), false, -1);
            }finally{
                try{
                    //terminamos la trasaccion
                    if(lbTrans){
                        if(loResult== null || !loResult.getBien()){
                            rollBackTrans();
                        }else{
                            commitTrans();
                        }
                    }
                }catch(Exception e2){
                    JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,this.getClass().getName(), e2,(getParametros() == null ? "" : getParametros().getTAG()));
                }
            }
        }
        return loResult;
    }


    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        //introduccion de campos a la select de sistema
        poSelect.setPassWord(msPassWord);
        poSelect.setPermisos(msPermisos);
        poSelect.setUsuario(msUsuario);
        //recuperacion de datos fisica
        recuperarDatosBD(v, poSelect.msSQL(moSelect), psTabla);

    }
    public JListDatos getListDatos(final JSelect poSelect, String psTabla) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        JListDatos loList = null;
        try{
            stmt = moConec.createStatement();
            rs = stmt.executeQuery(poSelect.msSQL(moSelect));
            loList = getListDatos(rs, psTabla);
        } finally {
            if(rs!=null){
                rs.close();
            }
            if(stmt!=null){
                stmt.close();
            }
        }
        return loList;
    }
    public JTableDef getTableDef(String psSelect, String psTabla) throws Exception {
        JTableDef loTabla = null;
        
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = moConec.createStatement();
            
            rs = stmt.executeQuery(psSelect);
            
            loTabla = new JTableDef(psTabla);
            loTabla.setTipo(loTabla.mclTipoTabla);
            loTabla.getCampos().setTabla(psTabla);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            for(int i = 0; i<numberOfColumns;i++){
                JFieldDef loCampo = new JFieldDef(
                        JFieldDef.getTipoNormalDeTipoSQL(rsmd.getColumnType(i+1)),
                        rsmd.getColumnName(i+1),//nombre
                        rsmd.getColumnName(i+1),//caption
                        false,//no se puede saber si es principal
                        rsmd.getColumnDisplaySize(i+1),//tamano
                        rsmd.getColumnTypeName(i+1),//nombre tipo bd
                        rsmd.isNullable(i+1) != rsmd.columnNoNulls,//si es nullable
                        ""//descripcion
                        );
                loTabla.getCampos().addField(loCampo);
            }
        } finally {
            if(rs!=null){
                rs.close();
            }
            if(stmt!=null){
                stmt.close();
            }
        }
        return loTabla;
    }
    public JTableDef getTableDef(String psTabla) throws Exception {
        return getTableDef(
                "select * from " + moSelect.msTabla(psTabla, "") + " where '1'='2'"
                , psTabla);
    }    
    public static JListDatos getListDatos(final ResultSet rs, String psTabla) throws Exception {
        return getListDatos(rs, psTabla, false);
    }
    public static JListDatos getListDatos(final ResultSet rs, String psTabla, boolean pbSiempreBorrarEspaciosFinal) throws Exception {
        JListDatos loListDatos = new JListDatos();
        loListDatos.getFields().setTabla(psTabla);
        //anulamos los eventos
        loListDatos.eventosGestAnular();
        try{
            //anadimos la definiciaon de los campos
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            for(int i = 0; i<numberOfColumns;i++){
                JFieldDef loCampo = new JFieldDef(
                        JFieldDef.getTipoNormalDeTipoSQL(rsmd.getColumnType(i+1)),
                        rsmd.getColumnName(i+1),//nombre
                        rsmd.getColumnName(i+1),//caption
                        false,//no se puede saber si es principal
                        rsmd.getColumnDisplaySize(i+1),//tamano
                        rsmd.getColumnTypeName(i+1),//nombre tipo bd
                        rsmd.isNullable(i+1) != rsmd.columnNoNulls,//si es nullable
                        ""//descripcion
                        );
                loListDatos.getFields().addField(loCampo);
            }

            //anadimos los datos

            JDateEdu loDateEdu = new JDateEdu();
            Calendar c = Calendar.getInstance();
            String[] lasCampos = new String[numberOfColumns];
            while (rs.next()) {
                for(int i = 0 ; i < numberOfColumns ; i++){
                    lasCampos[i]=getCampo(rs,i,loDateEdu, c, rsmd, pbSiempreBorrarEspaciosFinal);
                }
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                loFila.setArray(lasCampos);
                loListDatos.add(loFila);
            }

        } finally {
            //reactivamos eventos de insercion, borrado, ...
            loListDatos.eventosGestActivar();
            BusquedaEvent eB = new BusquedaEvent(loListDatos, loListDatos);
            eB.mbError=false;
            eB.moError=null;
            loListDatos.recuperacionDatosTerminada(eB);
        }
        return loListDatos;
    }
    public JListDatos recuperarDatosBD(final JListDatos v,final String psSELECT, final String psTabla)throws Exception  {
        Statement loSTMT = null;
        ResultSet loRS = null;
        try{
            long lTime = new java.util.Date().getTime();
            loSTMT = moConec.createStatement();
            loRS = loSTMT.executeQuery(psSELECT);
            long lTime2 = new java.util.Date().getTime();
            ResultSetMetaData loRSMD = loRS.getMetaData();
            int lNumeroColumnas = loRSMD.getColumnCount();
            JDateEdu loDateEdu = new JDateEdu();
            Calendar c = Calendar.getInstance();
            String[] lasCampos = new String[lNumeroColumnas];
            int lFila = 0;
            boolean lbContinuar = true;
            while (loRS.next() && lbContinuar) {
                for(int i = 0 ; i < lNumeroColumnas ; i++){
                    lasCampos[i]=getCampo(loRS,i,loDateEdu, c, loRSMD, getParamBD().isEliminarEspaciosDerechaSiempre());
                }
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                loFila.setArray(lasCampos);
                v.add(loFila);
                lFila++;
                //dentro jlistdatos se incrementa ^2
//                if(lFila > 1000 && v.getIncrementoMemoria()<1000){
//                    v.setIncrementoMemoria(1000);
//                }
//                if(lFila > 10000 && v.getIncrementoMemoria()<10000){
//                    v.setIncrementoMemoria(10000);
//                }
//                if(lFila > 100000 && v.getIncrementoMemoria()<100000){
//                    v.setIncrementoMemoria(100000);
//                }
                if(lFila>moParam.getNumeroMaximoRegistros() && moParam.getNumeroMaximoRegistros()>0){
                    lbContinuar=false;
                }
            }
            long lTime3=new java.util.Date().getTime();
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(),
                    "SELECT"+
                    ":Tiempo milisegundos" +
                    ":Despues ejecutar select y antes de recorrela:"+String.valueOf(lTime2 - lTime) +
                    ":Despues recorrer select:" + String.valueOf(lTime3 - lTime2) +
                    ":filas afectadas:"+String.valueOf(lFila) +
                    ":sql:" +psSELECT,
                    (getParametros() == null ? "" : getParametros().getTAG()));

//            v.setIncrementoMemoria(100);
        } finally {
            if(loRS!=null){
                loRS.close();
            }
            if(loSTMT!=null){
                loSTMT.close();
            }
        }
        return v;
    }
    /**
     *
     * cerrar objeto, si es tipo base de datos es muy importante hacerlo
     * @throws Exception el error en caso de error
     */
    public void close() throws Exception{
        if(moConec !=null) {
            moConec.close();
            moConec=null;
        }
    }

    /**
     * Ejecuta la sentencia loselect y le pasa los parametros con ISelect
     *
     * @param loSent senenticade base de datos
     * @param poSelect motor
     * @throws SQLException error
     */
    protected void ejecutarSQL(final PreparedStatement loSent, final ISelectMotor poSelect, final JActualizar poActu) throws Exception {

        int lrows = 0;
        long lTime = new Date().getTime();
        //ejecutamos la consulta
        poSelect.pasarParametros(poActu.getTabla(), poActu.getFields(), poActu.getTipoModif(), loSent);
        loSent.execute();
        lrows = loSent.getUpdateCount();
        if (poActu.isParamOut() && poActu.getTipoModif() == JListDatos.mclComando) {
            int lParam = 1;
            for (int i = 0; i < poActu.getFields().size(); i++) {
                JFieldDef loCampo = poActu.getFields().get(i);
                if (JActualizar.isParamOut(loCampo)) {
                    loCampo.setValue(((CallableStatement) loSent).getObject(lParam++));
                }
            }
        }

//        }else{
//            lrows = loSent.executeUpdate();
////        int lrows = loSent.executeUpdate();
////        //si ha actualizado 0 registros error
////        //se quita pq cuando hay triggers(SQL Server) no devuelve bien lrows
//    //          if (lrows == 0) {
//    //              switch(poActu.getTipoModif()){
//    //                  case JListDatos.mclBorrar:
//    //                      throw new SQLException("registro no borrado");
//    //                  case JListDatos.mclNuevo:
//    //                      throw new SQLException("registro no insertado");
//    //                  case JListDatos.mclEditar:
//    //                      throw new SQLException("registro no actualizado");
//    //              }
//    //          }
//        }
        long lTime2 = new Date().getTime();
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(),
                "UPDATE"
                + ":Tiempo milisegundos:" + String.valueOf(lTime2 - lTime)
                + ":filas afectadas:" + String.valueOf(lrows)
                + ":ejecutarSQL:" + poActu.msSQL(poSelect).replace(':', '.')
                + ":valores:" + (poActu.getFields() == null ? "" : poActu.getFields().toString().replace(':', '.')),
                (getParametros() == null ? "" : getParametros().getTAG()));

    }

    protected IResultado ejecutarSQLCompleta(final Connection poConex, final ISelectMotor poSelect,final JActualizar poActu) throws Exception{
        PreparedStatement loSent = null;
        ResultSet rs = null;
        JResultado loResultado = null;
        try{
            if ((poActu.getTipoModif() !=  JListDatos.mclBorrar) ){
                //realizamos las funciones especiales para cada campo
                for(int i = 0; i < poActu.getFields().count(); i++){
                    if (poActu.getFields().get(i).isVacio()){
                        if (poActu.getFields().get(i).getActualizarValorSiNulo() != JFieldDef.mclActualizarNada){
                            if(poActu.getFields().get(i).getActualizarValorSiNulo() == JFieldDef.mclActualizarUltMasUno){
                                JSelect loSelect = new JSelect(poActu.getTabla());
                                loSelect.addCampo(JSelectCampo.mclFuncionMax,poActu.getTabla(), poActu.getFields().get(i).getNombre());
                                JListDatos loList = new JListDatos(this, poActu.getTabla(), new String[]{""}, new int[]{JListDatos.mclTipoCadena}, new int[]{0});
                                //asi evitamos los filtros de servidor
                                loSelect.msSelectAPelo = loSelect.msSQL(moSelect);
                                loList.recuperarDatosNoCacheNormal(loSelect);
                                if(loList.moveFirst()){
                                    poActu.getFields().get(i).setValue(loList.getFields(0).getInteger()+1);
                                }else {
                                    poActu.getFields().get(i).setValue(1);
                                }
                            }
                            if(poActu.getFields().get(i).getActualizarValorSiNulo() == JFieldDef.mclActualizarValor){
                                poActu.getFields().get(i).setValue(poActu.getFields().get(i).getValorPorDefecto());
                            }
                            if(poActu.getFields().get(i).getActualizarValorSiNulo() == JFieldDef.mclActualizarSelect){
                                JSelect loSelect = poActu.getFields().get(i).getSelect();
                                JListDatos loList = new JListDatos(this, poActu.getTabla(), new String[]{""}, new int[]{JListDatos.mclTipoCadena}, new int[]{0});
                                loList.recuperarDatosNoCacheNormal(loSelect);
                                if(loList.moveFirst()){
                                    poActu.getFields().get(i).setValue(loList.getFields(0).getString());
                                }else{
                                    poActu.getFields().get(i).setValue(1);//pequeno parche para cuando no devuelva ningun registro, ya q si la tabla es vacia la select + 1 da error
                                }
                            }
                        }
                    }
                }
            }
            //parche access para updates de mas de 127 campos
            boolean lbParcheAcess = false;
            int lCamposPrincipales = 0;
            try{
                if(poActu.getFields()!=null 
                        && (poActu.getTipoModif() ==  JListDatos.mclEditar
                            || poActu.getTipoModif() ==  JListDatos.mclNada
                        )
                        && moSelect != null
                        ){
                        lCamposPrincipales = poActu.getFields().malCamposPrincipales().length;
                        lbParcheAcess=(poActu.getFields().size() - lCamposPrincipales) >127 
                                && moSelect instanceof JSelectMotor 
                                && ((JSelectMotor)moSelect).mlTipo == JSelectMotor.mclAccess ;
                }
            }catch(Exception e){}
            
            if(lbParcheAcess){
                JFieldDefs loCampos = new JFieldDefs();
                JFieldDefs loCamposOrigen = poActu.getFields().Clone();
                int lField = loCamposOrigen.size()-1;
                while(loCamposOrigen.size()>(127 + lCamposPrincipales)){
                    if(!loCamposOrigen.get(lField).getPrincipalSN()){
                        loCampos.addField(loCamposOrigen.remove(lField));
                    }
                    lField--;
                }
                for(int i = 0 ; i < loCamposOrigen.size(); i++){
                    if(loCamposOrigen.get(i).getPrincipalSN()){
                        loCampos.addField(loCamposOrigen.get(i));
                    }
                }
                //creamos la query de actualizacion
                JActualizar loAct1 = new JActualizar(
                        loCamposOrigen, poActu.getTabla(), poActu.getTipoModif()
                        , poActu.getUsuario(), poActu.getPassWord(), poActu.getPermisos());
                
                loSent = poConex.prepareStatement(loAct1.msSQL(poSelect));
                //ejecutamos la query
                ejecutarSQL(loSent, poSelect, loAct1);

                JActualizar loAct2 = new JActualizar(
                        loCampos, poActu.getTabla(), poActu.getTipoModif()
                        , poActu.getUsuario(), poActu.getPassWord(), poActu.getPermisos());
                //creamos la query de actualizacion
                loSent = poConex.prepareStatement(loAct2.msSQL(poSelect));
                //ejecutamos la query
                ejecutarSQL(loSent, poSelect, loAct2);
                if(poActu.getFields()==null){
                    loResultado=new JResultado(new JFilaDatosDefecto(), poActu.getTabla(), "", true, poActu.getTipoModif());
                }else{
                    loResultado=new JResultado(poActu.getFields().moFilaDatos(), poActu.getTabla(), "", true, poActu.getTipoModif());
                }

            }else{
                //creamos la query de actualizacion
                if(poActu.isParamOut()){
                    loSent = poConex.prepareCall(poActu.msSQL(poSelect));
                }else{
                    loSent = poConex.prepareStatement(poActu.msSQL(poSelect));
                }
                //ejecutamos la query
                ejecutarSQL(loSent, poSelect, poActu);
                if(poActu.getFields()==null){
                    loResultado=new JResultado(new JFilaDatosDefecto(), poActu.getTabla(), "", true, poActu.getTipoModif());
                }else{
                    loResultado=new JResultado(poActu.getFields().moFilaDatos(), poActu.getTabla(), "", true, poActu.getTipoModif());
                }
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            throw new AException("("+poActu.msSQL(poSelect)+" PARAMETROS ("+(poActu.getFields()==null ? "" :poActu.getFields().toString())+") )"  , e);
        }finally{
            cerrar(loSent, rs);
            loSent=null; rs=null;
        }
        return loResultado;
    }

    protected void cerrar(final PreparedStatement loSent, final ResultSet rs) {
        if(rs!=null) {
            try{
                rs.close();
            }catch(Exception e){
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e,(getParametros() == null ? "" : getParametros().getTAG()));
            }
        }
        if(loSent!=null) {
            try{
                loSent.close();
            }catch(Exception e){
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e, (getParametros() == null ? "" : getParametros().getTAG()));
            }
        }
    }
    public static IResultado ejecutarSQLCompleta(ISelectEjecutarSelect poEje,final Connection poConex, final ISelectMotor poSelect) throws Exception{
        PreparedStatement loSent = null;
        JResultado loResultado = null;
        String lsSelect = "";
        try{
            lsSelect = poEje.msSQL(poSelect);
            //creamos la query de actualizacion
            loSent = poConex.prepareStatement(lsSelect);
            //ejecutamos la query
            loSent.execute();
            loResultado=new JResultado("", true);
        }finally{
            if(loSent!=null) {
                try{
                    loSent.close();
                }catch(Exception e){
                    JDepuracion.anadirTexto(
                            JDepuracion.mclWARNING,
                            poEje.getClass().getName(), e);
                }
            }
        }
        return loResultado;
    }

    public static String getCampo(final ResultSet rs, final int plColumn, final JDateEdu poDateEdu, final Calendar c, final ResultSetMetaData rsmd) throws SQLException {
        return getCampo(rs, plColumn, poDateEdu, c, rsmd, false);
    }
    public static String getCampo(final ResultSet rs, final int plColumn, final JDateEdu poDateEdu, final Calendar c, final ResultSetMetaData rsmd, boolean pbSiempreBorrarEspaciosFinal) throws SQLException {
        String lsResult = "";
        Object loValor = rs.getObject(plColumn+1);
        int ltipo = rsmd.getColumnType(plColumn+1);
        lsResult=getCampo(loValor, ltipo, poDateEdu, c, pbSiempreBorrarEspaciosFinal);
        return lsResult;
    }
    public static String getCampo(Object loValor, int ltipo, final JDateEdu poDateEdu, final Calendar c, boolean pbSiempreBorrarEspaciosFinal) throws SQLException {
        String lsResult = "";
//        long lDate = new Date().getTime();
        //comprobamos si es nulo
        if (loValor != null){
            //para formatear correctamente la fecha
            Class loClase = loValor.getClass();
            if((loClase == java.sql.Date.class) ||
                   (loClase == java.util.Date.class) ||
                   (loClase == java.sql.Time.class) ||
                   (loClase == java.sql.Timestamp.class)){
                poDateEdu.clear();
                
                //anadimos ano,mes,dia
                if ((loClase == java.sql.Date.class) ||
                    (loClase == java.util.Date.class) ||
                    (loClase == java.sql.Timestamp.class)){
                    poDateEdu.setDate((java.util.Date)loValor);
                }
                //anadimos hora,minuto,segundo
                if(loClase == java.sql.Time.class){
                    poDateEdu.setHoraMinSeg((java.sql.Time)loValor);
                }else if(loClase == java.sql.Timestamp.class){//@TODO OJO parece q no hace falta, revisar
                    poDateEdu.setHoraMinSeg((java.sql.Timestamp)loValor);
                }
                lsResult=(poDateEdu.toString());

            }else if (java.sql.Clob.class.isAssignableFrom(loClase)){
                try {
                    java.sql.Clob loClob = (java.sql.Clob)loValor;
                    // Will use a Java InputStream object to read data from a CLOB (can
                    // also be used for a BLOB) object. In this example, we will use an
                    // InputStream to read ASCII characters from a CLOB.
// HACE COSAS RARAS                    
//                    java.io.Reader clobInputStream = loClob.getCharacterStream();
//                    char[] textBuffer = new char[255];
//                    StringBuilder lasCadena = new StringBuilder();
//                    int bytesRead;
//                    while ((bytesRead = clobInputStream.read(textBuffer)) != -1) {
//                        lasCadena.append(textBuffer);
//                    }    
//                    lsResult=lasCadena.toString();
                    lsResult=loClob.getSubString(1, (int)loClob.length());
                } catch (Throwable ex) {
                    throw new SQLException(ex.toString());
                }
            }else if (loClase == Boolean.class){
                if(((Boolean)loValor).booleanValue()){
                    lsResult=JListDatos.mcsTrue;
                }else{
                    lsResult=JListDatos.mcsFalse;
                }
            }else if (loClase == (Double.class)){
                lsResult=JFormat.msFormatearDouble((Double)loValor, JFormat.mcsDOUBLENOCIENTIFICO).replace(',', '.');
            }else if (byte[].class.isAssignableFrom(loClase)){//para los binarios
                lsResult=new String((byte[])loValor);
            } else {
//                if(ltipo==java.sql.Types.CHAR || ltipo==java.sql.Types.NCHAR){//los char siempre tienen muchos espacios al final q los anade la BD
                if(ltipo==1 || ltipo==-15 || pbSiempreBorrarEspaciosFinal){//los char siempre tienen muchos espacios al final q los anade la BD
                    lsResult=rTrim(loValor.toString());
                }else{
                    lsResult=(loValor.toString());
                }
            }
//            long lIncre = new Date().getTime() - lDate;
//            JServerServidorDatosBD.mlTiempoTotal1+=lIncre;
//            if (loClase == (byte[].class)){
//                JServerServidorDatosBD.mlTiempoTotalBinario1+=lIncre;
//            }
        }

        return lsResult;
    }
    
    public static String rTrim(String psCadena){
        int lLenTotal = psCadena.length();
	int len = lLenTotal;
	int st = 0;
	while ((st < len) && (psCadena.charAt(len - 1) <= ' ')) {
	    len--;
	}
	return ((st > 0) || (len < lLenTotal)) ? psCadena.substring(st, len) : psCadena;
    }
    /**
     * rellena psLinea, en funciona de la fila actual del ResultSet
     * @param psLinea buffer de la libea
     * @param rs resulset posicionado
     * @param plNumberOfColumns numero de columnas
     * @param poDateEdu Objeto dateEdu instanciado(da igual el valor)
     * @throws SQLException excepcion
     */
    public static void rellenarLinea(final StringBuffer psLinea, final ResultSet rs, final int plNumberOfColumns, final JDateEdu poDateEdu, final ResultSetMetaData rsmd) throws java.sql.SQLException {
        rellenarLinea(psLinea, rs, plNumberOfColumns, poDateEdu, rsmd, false);
    }
    /**
     * rellena psLinea, en funciona de la fila actual del ResultSet
     * @param psLinea buffer de la libea
     * @param rs resulset posicionado
     * @param plNumberOfColumns numero de columnas
     * @param poDateEdu Objeto dateEdu instanciado(da igual el valor)
     * @throws SQLException excepcion
     */
    public static void rellenarLinea(final StringBuffer psLinea, final ResultSet rs, final int plNumberOfColumns, final JDateEdu poDateEdu, final ResultSetMetaData rsmd, boolean pbSiempreBorrarEspaciosFinal) throws java.sql.SQLException {
        //se crea la linea
        psLinea.setLength(0);
        for (int i = 0; i < plNumberOfColumns; i++) {
            Object loValor = rs.getObject(i+1);
            int ltipo = rsmd.getColumnType(i+1);
            String lsCampo=getCampo(loValor, ltipo, poDateEdu, null, pbSiempreBorrarEspaciosFinal);            
            
            if(loValor!=null && 
               (java.sql.Clob.class.isAssignableFrom(loValor.getClass())
                    || byte[].class.isAssignableFrom(loValor.getClass())) && 
                (lsCampo.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea10)>=0 ||
                lsCampo.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea13)>=0 ||
                lsCampo.indexOf(JFilaDatosDefecto.mccSeparacion1)>=0  )
                    ){
                //los reemplazamos y punto
                lsCampo=lsCampo.replace(String.valueOf(JFilaDatosDefecto.mccTransparentacionCambioLinea10), "")
                        .replace(String.valueOf(JFilaDatosDefecto.mccTransparentacionCambioLinea13), "")
                        .replace(String.valueOf(JFilaDatosDefecto.mccSeparacion1), "");
             }            
            psLinea.append(lsCampo);
            psLinea.append(JFilaDatosDefecto.mccSeparacion1);
        }
    }

}
