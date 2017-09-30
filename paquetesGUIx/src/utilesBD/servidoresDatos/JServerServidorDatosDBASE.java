/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.servidoresDatos;


import ListDatos.BusquedaEvent;
import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.ISelectEjecutarSelect;
import ListDatos.ISelectMotor;
import ListDatos.IServerEjecutar;
import ListDatos.JActualizar;
import ListDatos.JFilaCrearDefecto;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JResultado;
import ListDatos.JSelect;
import ListDatos.JServerServidorDatosBD;
import ListDatos.JServidorDatosAbtrac;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import utiles.JDateEdu;
import utilesBD.estructuraBD.JConstructorEstructuraBDDBASE;

/**
 * lee datos de hoja excell
 * @author eduardo
 */
public class JServerServidorDatosDBASE extends JServidorDatosAbtrac{
    private String msURLBase1;
    private JTableDefs moTableDefs;
   
    public JServerServidorDatosDBASE(final String psRuta) {
        super();
        msURLBase1=psRuta;
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Inicializado");
    }
    public ISelectMotor getSelect() {
        return null;
    }
    /**Directorio base en donde estan los excel*/
    public void setURLBase1(final String psURLBase){
      msURLBase1 = psURLBase;
    }
    /**Directorio base en donde estan los excel*/
    public String getURLBase1(){
        return msURLBase1;
    }  
     
    @Override
   protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        //introduccion de campos a la select de sistema
        poSelect.setPassWord(msPassWord);
        poSelect.setPermisos(msPermisos);
        poSelect.setUsuario(msUsuario);
        //recuperacion de datos fisica
        recuperarDatosDBF(v, poSelect ,psTabla);
    }
    public File getTablaDBF(String psTabla){
        File loFile;
        if(msURLBase1 == null || msURLBase1.equals("")){
            loFile=new File(psTabla);
        }else{
            loFile=new File(msURLBase1, psTabla);
        }
        if(!loFile.exists()){
            File loFileAux = new File(loFile.getAbsolutePath() + ".dbf");
            if(loFileAux.exists()){
                loFile = loFileAux;
            }
        }
        return loFile;
    }
    private JListDatos recuperarDatosDBF(final JListDatos poList,final JSelect poSelect,final String psTabla) throws Exception  {
        InputStream miFStream=null;
	
        try{
            File loFile = getTablaDBF(psTabla);
            DBFReader loDBFReader;
            if(loFile.exists()){
                loDBFReader = new DBFReader(new FileInputStream(loFile));
            }else{
                loDBFReader = new DBFReader(getClass().getResourceAsStream(msURLBase1 + psTabla));
            }
            int[] lalCorrespondencia = new int[poList.getFields().size()];
            for(int i = 0 ; i < loDBFReader.getFieldCount(); i++){
                int lIndice = poList.getFields().getIndiceDeCampo(loDBFReader.getField(i).getName());
                if(lIndice>=0){
                    lalCorrespondencia[ lIndice] = i;
                }
            }
            //obligamos a q existan todos los campos del listdatos
            for(int i = 0 ; i < lalCorrespondencia.length; i++){
                if(lalCorrespondencia[i]<0){
                    throw new Exception("El campo " + poList.getFields(i).getNombre() + " no existe");
                }
            }
            
            poList.eventosGestAnular();
            try{
                Object[] laArrayValores = loDBFReader.nextRecord();
                Calendar c = Calendar.getInstance();
                JDateEdu loDate = new JDateEdu();
                while(laArrayValores!=null){
                    String[] lsArray = new String[lalCorrespondencia.length];
                    for(int i = 0 ; i < lalCorrespondencia.length; i++){
                        Object loValor = laArrayValores[lalCorrespondencia[i]];
                        lsArray[i]=JServerServidorDatosBD.getCampo(loValor, -1, loDate, c, true);
                    }
                    IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                    loFila.setArray(lsArray);
                    if (poSelect.getWhere().mbCumpleFiltro(loFila)) {
                        poList.add(loFila);

                    }                    
                    laArrayValores = loDBFReader.nextRecord();
                }
            }finally{
                poList.eventosGestActivar();     
            }

            return poList;
        }finally{
            if(miFStream!=null){
                miFStream.close();
            }
        }
    }

    public static JTableDef getTableDefDeDBF(InputStream poIn, String psNombre) throws Exception{
        DBFReader loDBFReader = new DBFReader(poIn);
        JTableDef loTable = new JTableDef(psNombre);
        
        for(int i = 0 ; i < loDBFReader.getFieldCount(); i++){
            JFieldDef loCampo = getFieldDef(loDBFReader.getField(i));
            loCampo.setTabla(psNombre);
            loTable.getCampos().addField(loCampo);
        }
        return loTable;
    }
    public static JListDatos getListDatosDeDBF(InputStream poIn) throws Exception{
        DBFReader loDBFReader = new DBFReader(poIn);
        JListDatos loList = new JListDatos();
        for(int i = 0 ; i < loDBFReader.getFieldCount(); i++){
            JFieldDef loCampo = getFieldDef(loDBFReader.getField(i));
            loList.getFields().addField(loCampo);
        }
        loList.eventosGestAnular();
        try{
            Calendar c = Calendar.getInstance();
            JDateEdu loDate = new JDateEdu();
            Object[] loO = loDBFReader.nextRecord();
            while(loO!=null){
                String[] lsArray = new String[loDBFReader.getFieldCount()];
                for(int i = 0 ; i < loDBFReader.getFieldCount(); i++){
                    Object loValor = loO[i];
                    lsArray[i]=JServerServidorDatosBD.getCampo(loValor, -1, loDate, c, true);
                }                
                loList.add(new JFilaDatosDefecto(lsArray));
                loO = loDBFReader.nextRecord();
            }
        }finally{
            loList.eventosGestActivar();
            BusquedaEvent eB = new BusquedaEvent(loList, loList);
            eB.mbError=false;
            eB.moError=null;
            loList.recuperacionDatosTerminada(eB);
        }
        
        
        
        return loList;
    }
    private static JFieldDef getFieldDef(DBFField poDBF){
        JFieldDef loCampo = new JFieldDef(poDBF.getName());
        switch(poDBF.getDataType()){
            case DBFField.FIELD_TYPE_L:
                loCampo.setTipo(JListDatos.mclTipoBoolean); // and set its type
                break;
            case DBFField.FIELD_TYPE_D:
                loCampo.setTipo(JListDatos.mclTipoFecha); // and set its type
                break;
            case DBFField.FIELD_TYPE_N:
                loCampo.setTipo(JListDatos.mclTipoNumero); // and set its type
                break;
            case DBFField.FIELD_TYPE_F:
                loCampo.setTipo(JListDatos.mclTipoNumeroDoble); // and set its type
                break;
            default:
                loCampo.setTipo(JListDatos.mclTipoCadena); // and set its type
                loCampo.setTamano(poDBF.getFieldLength());
        }
        return loCampo;
    }

    public static void guardar(JListDatos poList, File poFile) throws Throwable {


        DBFWriter writer = new DBFWriter();
        writer.setFields( getCamposDBF(poList));

        if(poList.moveFirst()){
            do{
                writer.addRecord(getValores(poList));
            }while(poList.moveNext());
        }
        FileOutputStream fos = new FileOutputStream( poFile);
        try{
            writer.write( fos);
        }finally{
            fos.close();
        }
    }

    private static DBFField[] getCamposDBF(JListDatos poList){
        DBFField[] fields = new DBFField[poList.getFields().size()];
        for(int i = 0;i < poList.getFields().size();i++ ){

            DBFField field = new DBFField();
            int lLen = poList.getFields(i).getNombre().length();
            if(lLen>10){
                field.setName(poList.getFields(i).getNombre().substring(0, 10)); // give a name to the field
            }else{
                field.setName(poList.getFields(i).getNombre()); // give a name to the field
            }
            switch(poList.getFields(i).getTipo()){
                case JListDatos.mclTipoBoolean:
                    field.setDataType( DBFField.FIELD_TYPE_L); // and set its type
                    break;
                case JListDatos.mclTipoFecha:
                    field.setDataType( DBFField.FIELD_TYPE_D); // and set its type
                    break;
                case JListDatos.mclTipoNumero:
                    field.setDataType( DBFField.FIELD_TYPE_N); // and set its type
                    break;
                case JListDatos.mclTipoNumeroDoble:
                case JListDatos.mclTipoMoneda3Decimales:
                case JListDatos.mclTipoMoneda:
                case JListDatos.mclTipoPorcentual3Decimales:
                case JListDatos.mclTipoPorcentual:
                    field.setDataType( DBFField.FIELD_TYPE_F); // and set its type
                    break;
                default:
                    field.setDataType( DBFField.FIELD_TYPE_C); // and set its type
                    if(poList.getFields(i).getTamano()>0 &&  poList.getFields(i).getTamano() <= 255){
                        field.setFieldLength( poList.getFields(i).getTamano()); // and length of the field
                    }else{
                        field.setFieldLength( 255); // and length of the field
                    }
            }

            fields[i]=field;
        }
        return fields;

    }
    private static Object[] getValores(JListDatos poList){
        Object[] fields = new Object[poList.getFields().size()];
        for(int i = 0;i < poList.getFields().size();i++ ){
            fields[i] = poList.getFields(i).getValueSQL();
        }
        return fields;
    }
    
    public JTableDefs getTableDefs() throws Exception {
        if(moTableDefs==null){
            moTableDefs = new JConstructorEstructuraBDDBASE(msURLBase1).getTableDefs();
        }
        return moTableDefs;
    }
    public IResultado modificarEstructura(final ISelectEjecutarSelect poEstruc) {
        return new JResultado(new JFilaDatosDefecto(), "", "No implementado", false, 0);
    }
    public IResultado ejecutarServer(final IServerEjecutar poEjecutar){
        return new JResultado(new JFilaDatosDefecto(), "", "No implementado", false, 0);
    }
    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        return new JResultado(new JFilaDatosDefecto(), "", "No implementado", false, 0);
    }


}
