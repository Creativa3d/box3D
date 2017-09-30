/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.servidoresDatos;

import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.ISelectEjecutarSelect;
import ListDatos.ISelectMotor;
import ListDatos.IServerEjecutar;
import ListDatos.JActualizar;
import ListDatos.JFilaCrearDefecto;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JResultado;
import ListDatos.JSelect;
import ListDatos.JServidorDatosAbtrac;
import ListDatos.estructuraBD.JTableDefs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import utiles.JDateEdu;
import utiles.JFormat;

/**
 * lee datos de hoja excell
 * @author eduardo
 */
public class JServerServidorDatosExcel extends JServidorDatosAbtrac{
    private String msURLBase1;
    private boolean mbCabezera;
    
    public JServerServidorDatosExcel(final String psRuta, final boolean pbCabezera) {
        super();
        msURLBase1=psRuta;
        mbCabezera = pbCabezera;
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
    /**Indica si en hoja excel la 1º columna es para los nombres de las columnas*/
    public boolean isCabezera() {
        return mbCabezera;
    }
    /**Establece si en hoja excel la 1º columna es para los nombres de las columnas*/
    public void setCabezera(boolean pbCabezera) {
        this.mbCabezera = pbCabezera;
    }    
    @Override
   protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        //introduccion de campos a la select de sistema
        poSelect.setPassWord(msPassWord);
        poSelect.setPermisos(msPermisos);
        poSelect.setUsuario(msUsuario);
        //recuperacion de datos fisica
        recuperarDatosExcel(v, poSelect ,psTabla);
    }
    public File getTablaExcel(String psTabla){
        File loFile;
        if(msURLBase1 == null || msURLBase1.equals("")){
            loFile=new File(psTabla);
        }else{
            loFile=new File(msURLBase1, psTabla);
        }
        if(!loFile.exists()){
            File loFileAux = new File(loFile.getAbsolutePath() + ".xls");
            if(loFileAux.exists()){
                loFile = loFileAux;
            }else{
                loFileAux = new File(loFile.getAbsolutePath() + ".xlsx");
                if(loFileAux.exists()){
                    loFile = loFileAux;                
                }
            }
        }
        return loFile;
    }
    private JListDatos recuperarDatosExcel(final JListDatos v,final JSelect poSelect,final String psTabla) throws Exception  {
        InputStream miFStream=null;
	
        try{
            File loFile = getTablaExcel(psTabla);
            POIFSFileSystem poifsFileSystem;
            if(loFile.exists()){
                poifsFileSystem = new POIFSFileSystem(new FileInputStream(loFile));
            }else{
                poifsFileSystem = new POIFSFileSystem(getClass().getResourceAsStream(msURLBase1 + psTabla));
            }
            HSSFWorkbook hssfWorkbook = null;
            hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);            

            return recuperarDatosExcel(v, hssfSheet, poSelect.getWhere(), psTabla);
        }finally{
            if(miFStream!=null){
                miFStream.close();
            }
        }
    }

    /**
     * Metodo que se encarga de leer los datos de un archivo en formato excel
     * @return Fila que contiene la informacion del archivo
     */
    public JFilaDatosDefecto leeLineaExcel(HSSFRow hssfRow) {
        JFilaDatosDefecto loLinea = new JFilaDatosDefecto();
//Me barro todos los elementos de una fila
        for (int i = hssfRow.getFirstCellNum(); i < hssfRow.getLastCellNum(); i++) {
            HSSFCell hssfCell = hssfRow.getCell(i);
            if (hssfCell != null) {
                switch (hssfCell.getCellType()) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        loLinea.addCampo(String.valueOf(hssfCell.getBooleanCellValue()));
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        try{
                            loLinea.addCampo(hssfCell.getStringCellValue());
                        }catch(Exception e){
                            try{
                                loLinea.addCampo(String.valueOf(hssfCell.getNumericCellValue()));
                            }catch(Exception e1){
                                try{
                                    loLinea.addCampo(new JDateEdu (hssfCell.getDateCellValue()).toString().replace("31/12/1899 ", "") );
                                }catch(Exception e2){
                                    try{
                                        loLinea.addCampo(String.valueOf(hssfCell.getBooleanCellValue()));
                                    }catch(Exception e3){
                                        loLinea.addCampo("");
                                    }
                                }
                            }
                        }
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                            loLinea.addCampo(new JDateEdu(hssfCell.getDateCellValue()).toString().replace("31/12/1899 ", "") );
                        }else{
                            double ldValor = hssfCell.getNumericCellValue();
                            loLinea.addCampo(JFormat.msFormatearDouble(ldValor, "############.#########").replace(',', '.'));
                        }
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        loLinea.addCampo(hssfCell.toString());
                        break;
                    default:
                        loLinea.addCampo("");
                }
            } else {
                loLinea.addCampo("");
            }
        }
        return loLinea;
    }
    
    public JListDatos recuperarDatosExcel(final JListDatos v,final HSSFSheet hssfSheet, JListDatosFiltroConj loFiltro, final String psTabla) throws Exception  {
        BufferedReader miDStream=null;
        Iterator<Row> moIterator = hssfSheet.rowIterator();
        try{
            HSSFRow hssfRow = null;
            //si hay cabezera nos la saltamos
            if(mbCabezera && moIterator.hasNext()){
                hssfRow = (HSSFRow) moIterator.next();
            }
            while (moIterator.hasNext()){
                hssfRow = (HSSFRow) moIterator.next();
                JFilaDatosDefecto loFilaDef = leeLineaExcel(hssfRow);
                
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                loFila.setArray(loFilaDef.moArrayDatos());
                if (loFiltro.mbCumpleFiltro(loFila)) {
                    v.add(loFila);
                }
            }

            return v;
        }finally{
            if(miDStream!=null){
                miDStream.close();
            }
        }
    }

    public void guardar(JListDatos poList, File poFile) throws Throwable {
        guardar(poList, poFile, mbCabezera);
    }
    public static void guardar(JListDatos poList, File poFile, boolean pbCabezera) throws Throwable {
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet(poList.msTabla);
        int lHoja=1;
        HSSFCellStyle style = workbook.createCellStyle();
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Courier New");
        font.setItalic(true);
        // font.setStrikeout(true);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //font.setColor(Short.parseShort("#ffffff"));
        style.setFont(font);

        int lFila = 0;
        if(pbCabezera){
            lFila++;
            crearCabecera(poList, sheet, style, lFila);
        }
        if (poList.moveFirst()) {
            do {
                lFila++;
                toExcel(poList, workbook, lFila, sheet);
                if(lFila>65524){
                    lFila = 0;
                    if(pbCabezera){
                        lFila++;
                        crearCabecera(poList, sheet, style, lFila);
                    }
                    lHoja++;
                    sheet=workbook.createSheet(poList.msTabla+String.valueOf(lHoja));
                }
            } while (poList.moveNext());

        }

        // Escribir el fichero.
        OutputStream fileOut = new FileOutputStream(poFile);
        try{
            workbook.write(fileOut);
        }finally{
            fileOut.close(); 
        }
    }

    public static void createCell(HSSFRow row, int i, String value) {
        createCell(row, i, value, null);
    }
    public static void createCell(HSSFRow row, int i, double value) {
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(value);
    }
    public static void createCell(HSSFRow row, int i, String value, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(i);
        value = value+" ";
        cell.setCellValue(value);
        // si no hay estilo, no se aplica
        if (style != null)
        cell.setCellStyle(style);
    }

    private static void crearCabecera(JListDatos poList, HSSFSheet sheet, HSSFCellStyle cfobj, int plfila) throws Exception {
        HSSFRow row = sheet.createRow( plfila);
        for(int i = 0 ; i < poList.getFields().size(); i++){
            createCell(row,i,poList.getFields(i).getCaption(), cfobj);
        }
    }

    private static void toExcel(JListDatos poList, HSSFWorkbook workbook, int plfila, HSSFSheet sheet) throws Exception {
        HSSFRow row = sheet.createRow( plfila);
        for(int i = 0 ; i < poList.getFields().size(); i++){
            if(poList.getFields(i).getTipo()==JListDatos.mclTipoNumero 
                    || poList.getFields(i).getTipo()==JListDatos.mclTipoNumeroDoble 
                    || poList.getFields(i).getTipo()==JListDatos.mclTipoMoneda3Decimales 
                    || poList.getFields(i).getTipo()==JListDatos.mclTipoMoneda 
                    || poList.getFields(i).getTipo()==JListDatos.mclTipoPorcentual3Decimales 
                    || poList.getFields(i).getTipo()==JListDatos.mclTipoPorcentual ){
                createCell(row,i,poList.getFields(i).getDouble());
            }else{
                createCell(row,i,poList.getFields(i).getString());
            }
        }
    }       
    public JTableDefs getTableDefs() throws Exception {
        return null;
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
