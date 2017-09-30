/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesAndroid.bd;


import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.ISelectEjecutarSelect;
import ListDatos.ISelectMotor;
import ListDatos.IServerEjecutar;
import ListDatos.JActualizar;
import ListDatos.JFilaCrearDefecto;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import ListDatos.JResultado;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectMotor;
import ListDatos.JServerServidorDatosBDParam;
import ListDatos.JServerServidorDatosParam;
import ListDatos.JServidorDatosAbtrac;
import ListDatos.estructuraBD.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;

import utiles.*;

/**
 *
 * @author eduardo
 */
public class JServerServidorDatosSQLLite extends JServidorDatosAbtrac {
//    public static long mlTiempoTotal1=0;
//    public static long mlTiempoTotalBinario1=0;

    /**motor de select*/
    protected ISelectMotor moSelect = new JSelectMotor(JSelectMotor.mclSQLLite);
    protected IConstructorEstructuraBD moConstrucEstruc = null;
    protected JTableDefs moTableDefs=null;

    /**conexion con base de datos*/
    protected transient SQLiteOpenHelper moConec=null;
    protected transient SQLiteDatabase moBD=null;


    protected JServerServidorDatosBDParam moParam = new JServerServidorDatosBDParam();

    private int mlNTrasacAnidadas=0;

    private boolean mbSiempreBorrarEspaciosFinal=true;

    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosSQLLite() {
        super();
    }

    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexion
     * @param psUsu usuario
     * @param psPassword pasword
     */
    public JServerServidorDatosSQLLite(SQLiteOpenHelper poConec) throws Throwable{
        super();
        moConec=poConec;
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
    public void setConec(SQLiteOpenHelper poConec){
        moConec=poConec;
    }
    public SQLiteOpenHelper getConec(){
        return moConec;
    }
    public ISelectMotor getSelect(){
        return moSelect;
    }

    public void setConstrucEstruc(IConstructorEstructuraBD poConstrucEstruc){
        moConstrucEstruc=poConstrucEstruc;
    }
    public IConstructorEstructuraBD  getConstrucEstruc(){
        return moConstrucEstruc;
    }
    
    public void setTableDefs(JTableDefs poTableDefs){
        moTableDefs=poTableDefs;
    }

    public JServerServidorDatosBDParam getParamBD(){
        return moParam;
    }
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
    protected synchronized void beginTrans() {
        if(mlNTrasacAnidadas==0){
            moBD=moConec.getWritableDatabase();
            moBD.beginTransaction();
        }
        mlNTrasacAnidadas++;
    }
    protected synchronized void commitTrans() {
        mlNTrasacAnidadas--;
        if(mlNTrasacAnidadas==0){
            moBD.setTransactionSuccessful();
            moBD.endTransaction();
        }
        if(mlNTrasacAnidadas<0){
            mlNTrasacAnidadas=0;
            JDepuracion.anadirTexto(
                    JDepuracion.mclWARNING,
                    this.getClass().getName(), getClass().getName() + "(commitTrans): mlNTrasacAnidadas<0->Nunca deberia haber llegado aqui",
                    (getParametros() == null ? "" : getParametros().getTAG())
                    );
        }
    }
    protected synchronized void rollBackTrans() {
        mlNTrasacAnidadas--;
        if(mlNTrasacAnidadas==0){
            moBD.endTransaction();
            moBD.close();
            moBD=null;
        }
        if(mlNTrasacAnidadas<0){
            if(moBD!=null){
                moBD.close();
                moBD=null;
            }
            mlNTrasacAnidadas=0;
            JDepuracion.anadirTexto(
                    JDepuracion.mclWARNING,
                    this.getClass().getName(), getClass().getName() + "(rollBackTrans): mlNTrasacAnidadas<0->Nunca deberia haber llegado aqui" ,
                    (getParametros() == null ? "" : getParametros().getTAG()));
        }
    }

    public IResultado moActualizar(String psNombreServlet, JActualizar poActualizar, IServerEjecutar poEjecutar, ISelectEjecutarSelect poEstruc){
        IResultado loResult = null;
        boolean lbTrans = false;
        if(moConec!=null){
            try{
                //comenzamos la transaccion
                lbTrans = true;
                beginTrans();

                //ejecutamos el actualizador
                if(poActualizar==null){
                    if(poEjecutar==null){
                        loResult = ejecutarSQLCompleta(poEstruc, moBD, moSelect);
                    }else{
                        loResult = poEjecutar.ejecutar(this);
                    }
                }else{
                    loResult = ejecutarSQLCompleta(moBD, moSelect, poActualizar);
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
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING,this.getClass().getName(), e2,(getParametros() == null ? "" : getParametros().getTAG()));
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
    public JListDatos recuperarDatosBD(final JListDatos v,final String psSELECT, final String psTabla)throws Exception  {
        Cursor rs = null;
        SQLiteDatabase loBD = null;
        try{
            long lTime = new java.util.Date().getTime();
            if(moBD==null){
                loBD = moConec.getReadableDatabase();
                rs = loBD.rawQuery(psSELECT, null);
            }else{
                rs = moBD.rawQuery(psSELECT, null);
            }
            long lTime2 = new java.util.Date().getTime();
            int numberOfColumns = rs.getColumnCount();
            JDateEdu loDateEdu = new JDateEdu();
            Calendar c = Calendar.getInstance();
            String[] lasCampos = new String[numberOfColumns];
            int lFila = 0;
            boolean lbContinuar = true;
            if(rs.moveToFirst() && lbContinuar){
                do{
                    for(int i = 0 ; i < numberOfColumns ; i++){
                        lasCampos[i]=getCampo(rs,i,loDateEdu, c, getParamBD().isEliminarEspaciosDerechaSiempre());
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
                }while(rs.moveToNext() && lbContinuar);
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
            if(rs!=null){
                rs.close();
            }
            if(loBD!=null){
                loBD.close();
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


    protected IResultado ejecutarSQLCompleta(final SQLiteDatabase poConex, final ISelectMotor poSelect,final JActualizar poActu) throws Exception{
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
            ContentValues values = new ContentValues();
            for(int i = 0; i < poActu.getFields().count(); i++){
                JFieldDef loCampo =    poActu.getFields().get(i);
                switch(loCampo.getTipo()){
                    case JListDatos.mclTipoFecha:
                        if(poActu.getFields().get(i).isVacio()){
                            values.put(poActu.getFields().get(i).getNombre(), (String)null); 
                        }else{
                            values.put(poActu.getFields().get(i).getNombre(), poActu.getFields().get(i).getDateEdu().msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones)); 
                        }
                        break;
                    case JListDatos.mclTipoBoolean:
                        values.put(poActu.getFields().get(i).getNombre(), poActu.getFields().get(i).getIntegerConNull()); 
                        break;
                    case JListDatos.mclTipoCadena:
                        values.put(poActu.getFields().get(i).getNombre(), poActu.getFields().get(i).getString()); 
                        break;
                    case JListDatos.mclTipoNumero:
                        values.put(poActu.getFields().get(i).getNombre(), poActu.getFields().get(i).getIntegerConNull()); 
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoPorcentual:
                    case JListDatos.mclTipoPorcentual3Decimales:
                        values.put(poActu.getFields().get(i).getNombre(), poActu.getFields().get(i).getDoubleConNull()); 
                        break;

                }
            }
            switch(poActu.getTipoModif()){
                case JListDatos.mclBorrar:
                    JListDatosFiltroElem loFiltro = new JListDatosFiltroElem(JListDatos.mclTIgual, poActu.getFields().malCamposPrincipales(), poActu.getFields().masCamposPrincipales());
                    loFiltro.inicializar(poActu.getTabla(), poActu.getFields());
                    int l = poConex.delete(poActu.getTabla(), loFiltro.msSQL(poSelect), null);
                    if(l==-1){
                        throw new Exception("Error al borrar");
                    }
                    break;
                case JListDatos.mclEditar:
                    loFiltro = new JListDatosFiltroElem(JListDatos.mclTIgual, poActu.getFields().malCamposPrincipales(), poActu.getFields().masCamposPrincipales());
                    loFiltro.inicializar(poActu.getTabla(), poActu.getFields());
                    poConex.update(poActu.getTabla(), values, loFiltro.msSQL(poSelect), null);
                    break;
                case JListDatos.mclNuevo:
                    poConex.insertOrThrow(poActu.getTabla(), null, values);
                    break;
                    
            }
            
            if(poActu.getFields()==null){
                loResultado=new JResultado(new JFilaDatosDefecto(), poActu.getTabla(), "", true, poActu.getTipoModif());
            }else{
                loResultado=new JResultado(poActu.getFields().moFilaDatos(), poActu.getTabla(), "", true, poActu.getTipoModif());
            }
        }catch(Throwable e){
            throw new AException("("+poActu.msSQL(poSelect)+" PARAMETROS ("+(poActu.getFields()==null ? "" :poActu.getFields().toString())+") )"  , e);
        }finally{
        }
        return loResultado;
    }

    public static IResultado ejecutarSQLCompleta(ISelectEjecutarSelect poEje,final SQLiteDatabase poConex, final ISelectMotor poSelect) throws Exception{
        JResultado loResultado = null;
        String lsSelect = "";
        try{
            lsSelect = poEje.msSQL(poSelect);
            //creamos la query de actualizacion
            poConex.execSQL(lsSelect);
            loResultado=new JResultado("", true);
        }finally{
        }
        return loResultado;
    }

    public static String getCampo(final Cursor rs, final int plColumn, final JDateEdu poDateEdu, final Calendar c) {
        return getCampo(rs, plColumn, poDateEdu, c, false);
    }
    public static String getCampo(final Cursor rs, final int plColumn, final JDateEdu poDateEdu, final Calendar c, boolean pbSiempreBorrarEspaciosFinal) {
        String lsResult = rs.getString(plColumn);
        lsResult=(lsResult == null ? "" : lsResult);
        if(pbSiempreBorrarEspaciosFinal){
            lsResult=rTrim(lsResult);
        }
//        Object loValor = rs.getString(plColumn);
////        long lDate = new Date().getTime();
//        //comprobamos si es nulo
//        if (loValor != null){
//            //para formatear correctamente la fecha
//            Class loClase = loValor.getClass();
//            if (loClase == (byte[].class)){//para los binarios
//                lsResult=new String((byte[])loValor);
//
//            }else if (loClase == (Double.class)){
//                lsResult=JFormat.msFormatearDouble((Double)loValor, JFormat.mcsDOUBLENOCIENTIFICO).replace(',', '.');
//            } else {
//                if(pbSiempreBorrarEspaciosFinal){
//                    lsResult=rTrim(loValor.toString());
//                }else{
//                    lsResult=(loValor.toString());
//                }
//            }
//        }

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

    public JTableDef getTableDef(String psTabla) {
        SQLiteDatabase loBD = moConec.getReadableDatabase();
        JTableDef loTable=new JTableDef(psTabla);
        try{
            Cursor c = loBD.rawQuery ("pragma table_info ("+psTabla+")",null);
            if(c.moveToFirst()){
                do{
                    int lTipo = JListDatos.mclTipoCadena;
                    String lsTipo = c.getString(c.getColumnIndex("type"));
                    if("INTEGER".equalsIgnoreCase(lsTipo)){
                        lTipo = JListDatos.mclTipoNumero;
                    }
                    if("FLOAT".equalsIgnoreCase(lsTipo)
                            || "REAL".equalsIgnoreCase(lsTipo)){
                        lTipo = JListDatos.mclTipoNumeroDoble;
                    }

                    JFieldDef loCampo = new JFieldDef(
                            lTipo
                            , c.getString(c.getColumnIndex("name"))
                            , c.getString(c.getColumnIndex("name"))
                            , "1".equals(c.getString(c.getColumnIndex("pk"))));
                    loCampo.setNullable("0".equals(c.getString(c.getColumnIndex("notnull"))));
                    loCampo.setValorPorDefecto(c.getString(c.getColumnIndex("dflt_value")));
                    if(lTipo == JListDatos.mclTipoCadena){
                        String[] lasArray = JFilaDatosDefecto.moArrayDatos(lsTipo + "(", '(');
                        if(lasArray.length>1){
                            String lsTama = lasArray[1];
                            lsTama=lsTama.replace("(", "");
                            lsTama=lsTama.replace(")", "");
                            if(JConversiones.isNumeric(lsTama)){
                                loCampo.setTamano((int)JConversiones.cdbl(lsTama));
                            }
                        }
                    }
                    loCampo.setTabla(psTabla);
                    loTable.getCampos().addField(loCampo);
                }while(c.moveToNext());
            }
        }finally{
            loBD.close();
        }
        if(loTable.getCampos().size()==0){
            loTable=null;
        }
        return loTable;
    }
}
