/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.excel;


import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JExcel {
    public static final short BORDER_THIN =CellStyle.BORDER_THIN;
    public static final short BORDER_MEDIUM =CellStyle.BORDER_MEDIUM;
    public static final short BORDER_THICK =CellStyle.BORDER_THICK;
    public static final short BORDER_DASHED =CellStyle.BORDER_DASHED;
    public static final short BORDER_DOUBLE =CellStyle.BORDER_DOUBLE;

    public static final short RED = IndexedColors.RED.getIndex();
    public static final short BLACK = IndexedColors.BLACK.getIndex();
    public static final short LIGHT_BLUE = IndexedColors.LIGHT_BLUE.getIndex();
    public static final short LIGHT_GREEN = IndexedColors.LIGHT_GREEN.getIndex();
    public static final short LIGHT_TURQUOISE = IndexedColors.LIGHT_TURQUOISE.getIndex();
    public static final short GREEN = IndexedColors.GREEN.getIndex();
    public static final short GREY_25_PERCENT = IndexedColors.GREY_25_PERCENT.getIndex();
    public static final short GREY_50_PERCENT = IndexedColors.GREY_50_PERCENT.getIndex();
    public static final short GREY_80_PERCENT = IndexedColors.GREY_80_PERCENT.getIndex();
    public static final short LIGHT_ORANGE = IndexedColors.LIGHT_ORANGE.getIndex();
    public static final short PINK = IndexedColors.PINK.getIndex();
    public static final short WHITE = IndexedColors.WHITE.getIndex();
    public static final short LIGHT_YELLOW = IndexedColors.LIGHT_YELLOW.getIndex();



    private Workbook wb;
    private Sheet sheet;
    private CellStyle moEstiloDefecto;

    public JExcel(){
        nuevoLibro();
    }
    public JExcel(InputStream fileInputStream) throws IOException{
        leer(fileInputStream);
    }
    public void leer(String psFile) throws IOException{
        leer(new FileInputStream(psFile));
    }
   
    public void leer(InputStream fileInputStream) throws IOException{
        POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
        wb = new HSSFWorkbook(fsFileSystem);
        sheet = wb.getSheetAt(0);        
        moEstiloDefecto = getCell(0, 0).getCellStyle();
    }
    /**crea un nuevo libro*/
    public void nuevoLibro(){
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("hoja 1");
        moEstiloDefecto = getCell(0, 0).getCellStyle();
    }
    /**
     * Establece la hoja del libro actual
     * @param plIndex indice hoja
     */
    public void setHoja(int plIndex){
        sheet = wb.getSheetAt(plIndex);
    }
    /**crea un nueva hoja
     * @param psTitulo titulo hoja
     */
    public void nuevoHoja(String psTitulo){
        sheet = wb.createSheet(psTitulo);
    }
    
    
    /**Devuelve una fuente con color d excel*/
    public org.apache.poi.ss.usermodel.Font getFuente(final Font poFuente, boolean pbUnder, boolean pbStrikeout, short plColor){
        org.apache.poi.ss.usermodel.Font loFuente = wb.createFont();
        loFuente.setColor(plColor);

        if(poFuente.isBold()) {
            loFuente.setBoldweight(loFuente.BOLDWEIGHT_NORMAL);
        }
        loFuente.setItalic(poFuente.isItalic());
        loFuente.setFontHeightInPoints((short) poFuente.getSize());
        loFuente.setFontName(poFuente.getName());
        if(pbUnder){
            loFuente.setUnderline((byte)1);
        }
        loFuente.setStrikeout(pbStrikeout);
        return loFuente;
    }
    /**AutoRedimension de la columna*/
    public void autoSizeColumn(int plColumn){
        sheet.autoSizeColumn(plColumn);
    }
    public void setColumnWidth(int plColumn, int plWidth){
        sheet.setColumnWidth(plColumn, plWidth);
    }

    /**Establece la fuente, color  por defecto para todas las celdas*/
    public void setFuenteDefecto(final Font poFuente, boolean pbUnder, boolean pbStrikeout, short plColor){
        moEstiloDefecto.setFont(getFuente(poFuente, pbUnder, pbStrikeout, plColor));
    }
    /**Establece la fuente, color  para una celda*/
    public void setFuenteCelda(int plFila, int plCol,final Font poFuente, boolean pbUnder, boolean pbStrikeout, short plColor){
        getEstiloCelda(getCell(plFila, plCol)).setFont(getFuente(poFuente, pbUnder, pbStrikeout, plColor));
    }
    /**Establece el color de fondo para una fila de celdas*/
    public void setColorFondoCelda(int plFila, int plCol, int plColFinal, short plColor){
        for(int i = plCol; i<=plColFinal; i++){
            setColorFondoCelda(plFila, i, plColor);
        }
    }
    /**Establece el color de fondo para una celda*/
    public void setColorFondoCelda(int plFila, int plCol, short plColor){
        getEstiloCelda(getCell(plFila, plCol)).setFillForegroundColor(plColor);
        getEstiloCelda(getCell(plFila, plCol)).setFillPattern(CellStyle.SOLID_FOREGROUND);
    }
    
    /**Establece si es multilinea una celda*/
    public void setWrapTextCelda(int plFila, int plCol, boolean pbWrap){
        Cell loCelda = getCell(plFila, plCol);
        getEstiloCelda(loCelda).setWrapText(pbWrap);
        if(pbWrap){
            Row loRow = getRow(plFila);
            int lFilas=1;
            if(loCelda.getCellType()==Cell.CELL_TYPE_STRING){
                lFilas=JFilaDatosDefecto.mlNumeroCampos(loCelda.getStringCellValue(), '\n');;
            }
            if(lFilas<=0){
                lFilas=1;
            }
            loCelda=loRow.getCell(0);
            int i=0;
            while(loCelda!=null){
                if(loCelda.getCellType()==Cell.CELL_TYPE_STRING){
                    int lAux=JFilaDatosDefecto.mlNumeroCampos(loCelda.getStringCellValue(), '\n');
                    if(lAux>lFilas){
                        lFilas=lAux;
                    }                
                }
                i++;
                loCelda=loRow.getCell(i);
            }
            loRow.setHeightInPoints(18*lFilas);
        }
    }
    /**Devuelve la fila correspondiente*/
    public Row getRow(int plRow){
        Row row = sheet.getRow((short)plRow);
        if(row==null){
            row = sheet.createRow((short)plRow);
        }
        return row;
    }
    /**Devuelve una celda en la fila,colum. correspondiente*/
    public Cell getCell(int plRow, int plCol){
        Row row = getRow((short)plRow);
        Cell cell = row.getCell(plCol);
        if(cell==null){
            cell = row.createCell(plCol);
        }
        return cell;

    }
    public CellStyle getEstiloCelda(int plRow, int plCol){
        return getEstiloCelda(getCell(plRow, plCol));
    }
    /**devuelve el estilo de una celda*/
    private CellStyle getEstiloCelda(Cell cell){
        CellStyle style = cell.getCellStyle();
        if(style==null || style.equals(moEstiloDefecto)){
            style = wb.createCellStyle();
            style.setFont(wb.getFontAt(moEstiloDefecto.getFontIndex()));
            cell.setCellStyle(style);
        }
        return style;
    }

    /**Establece un valor en una celda*/
    public void setValue(int plRow, int plCol, String valor) {
        Cell cell = getCell(plRow,plCol);
        cell.setCellValue(valor);
    }
    /**Establece un valor en una celda*/
    public void setValue(int plRow, int plCol, Date valor) {
        Cell cell = getCell(plRow,plCol);
        cell.setCellValue(valor);
    }
    /**Establece un valor en una celda*/
    public void setValue(int plRow, int plCol, boolean valor) {
        Cell cell = getCell(plRow,plCol);
        cell.setCellValue(valor);
    }
    /**Establece un valor en una celda*/
    public void setValue(int plRow, int plCol, double valor) {
        Cell cell = getCell(plRow,plCol);
        cell.setCellValue(valor);
    }
    public void setValue(int plRow, int plCol, JFieldDef poCampo) throws Exception {
        if(!poCampo.isVacio()){
            if(poCampo.isNumerico()){
                setValue(plRow, plCol, poCampo.getDouble());
            } else if(poCampo.getTipo()==JListDatos.mclTipoFecha){
                setValue(plRow, plCol, poCampo.toString());
            } else if(poCampo.getTipo()==JListDatos.mclTipoBoolean){
                setValue(plRow, plCol, poCampo.getBoolean());
            } else{
                setValue(plRow, plCol, poCampo.toString());
            }
        }
    }    
    /**Define un cuadro con bordes*/
    public void cuadradoBordes(int plRow, int plRowFinal, int plCol, int plColFinal, short plTipo){
        bordeIzquierda(plRow, plRowFinal, plCol, plTipo);
        bordeDerecha(plRow, plRowFinal, plColFinal, plTipo);

        bordeArriba(plRow, plCol, plColFinal, plTipo);
        bordeAbajo(plRowFinal, plCol, plColFinal, plTipo);
    }

    /**Define una linea con bordes*/
    public void bordeAbajo(int plRow, int plCol, int plColFinal, short plTipo){
        for(int i = plCol; i<=plColFinal; i++){
            bordeAbajo(plRow, i, plTipo);
        }
    }
    /**Define una linea con bordes*/
    public void bordeArriba(int plRow, int plCol, int plColFinal, short plTipo){
        for(int i = plCol; i<=plColFinal; i++){
            bordeArriba(plRow, i, plTipo);
        }
    }
    /**Define una linea con bordes*/
    public void bordeIzquierda(int plRow, int plRowFinal, int plCol, short plTipo){
        for(int i = plRow; i<=plRowFinal; i++){
            bordeIzquierda(i, plCol, plTipo);
        }
    }
    /**Define una linea con bordes*/
    public void bordeDerecha(int plRow, int plRowFinal, int plCol, short plTipo){
        for(int i = plRow; i<=plRowFinal; i++){
            bordeDerecha(i, plCol, plTipo);
        }
    }

    /**Define un borde de una celda*/
    public void bordeAbajo(int plRow, int plCol, short plTipo){
        Cell cell = getCell(plRow, plCol);
        CellStyle style = getEstiloCelda(cell);
        style.setBorderBottom(plTipo);
        
    }
    /**Define un borde de una celda*/
    public void bordeArriba(int plRow, int plCol, short plTipo){
        Cell cell = getCell(plRow, plCol);
        CellStyle style = getEstiloCelda(cell);
        style.setBorderTop(plTipo);
        
    }
    /**Define un borde de una celda*/
    public void bordeIzquierda(int plRow, int plCol, short plTipo){
        Cell cell = getCell(plRow, plCol);
        CellStyle style = getEstiloCelda(cell);
        style.setBorderLeft(plTipo);
        
    }
    /**Define un borde de una celda*/
    public void bordeDerecha(int plRow, int plCol, short plTipo){
        Cell cell = getCell(plRow, plCol);
        CellStyle style = getEstiloCelda(cell);
        style.setBorderRight(plTipo);
        
    }

    private boolean isCellEstileSimilar(CellStyle poOrigen, CellStyle poDestino){
        if(
                poOrigen.getAlignment() == poDestino.getAlignment() &&
                poOrigen.getBorderBottom() == poDestino.getBorderBottom() &&
                poOrigen.getBorderLeft() == poDestino.getBorderLeft() &&
                poOrigen.getBorderRight() == poDestino.getBorderRight() &&
                poOrigen.getBorderTop() == poDestino.getBorderTop() &&
                poOrigen.getBottomBorderColor() == poDestino.getBottomBorderColor() &&
                poOrigen.getDataFormat() == poDestino.getDataFormat() &&
                poOrigen.getDataFormatString().equalsIgnoreCase(poDestino.getDataFormatString()) &&
                poOrigen.getFillBackgroundColor() == poDestino.getFillBackgroundColor() &&
                poOrigen.getFillForegroundColor() == poDestino.getFillForegroundColor() &&
                poOrigen.getFillPattern() == poDestino.getFillPattern() &&
                poOrigen.getFontIndex() == poDestino.getFontIndex() &&
                poOrigen.getHidden() == poDestino.getHidden() &&
                poOrigen.getIndention() == poDestino.getIndention() &&
                poOrigen.getLeftBorderColor() == poDestino.getLeftBorderColor() &&
                poOrigen.getLocked() == poDestino.getLocked() &&
                poOrigen.getRotation() == poDestino.getRotation() &&
                poOrigen.getTopBorderColor() == poDestino.getTopBorderColor() &&
                poOrigen.getVerticalAlignment() == poDestino.getVerticalAlignment() &&
                poOrigen.getWrapText() == poDestino.getWrapText()
                ){
            return true;
        }else{
            return false;
        }
    }
    private CellStyle getCellEstileSimilar(IListaElementos loH, CellStyle poOrigen){
        CellStyle loResult = null;
        for(int i= 0 ; i < loH.size() && loResult == null; i++){
            CellStyle loEst = (CellStyle) loH.get(i);
            if(isCellEstileSimilar(poOrigen, loEst) || poOrigen == loEst){
                loResult = loEst;
            }
        }

        return loResult;
    }

    /**Guarda el libro actual*/
    public void guardar(String psFichero) throws Exception{
        //simplificar formato pq en excel sale demasidos formatos diferentes, en openoffice no sale el error, manda cojones
        //OJO de esta forma el libro se suele abrir bien aunque salga un mensaje de error, desgraciadamente no se pueden borrar formatos
        IListaElementos loH = new JListaElementos();

        for(int i = 0 ; i < sheet.getLastRowNum(); i++){
            Row loFila = getRow(i);
            for(int ii = 0 ; loFila!=null &&  ii < loFila.getLastCellNum(); ii++){
                Cell loCelda = loFila.getCell(ii);
                if(loCelda!=null && loCelda.getCellStyle()!=null){
                    //comprobamos en el lista si hay un formato igual
                    CellStyle loEstilo = getCellEstileSimilar(loH,loCelda.getCellStyle());
                    if(loEstilo == null){
                        //si no lo hay lo add
                        loH.add(loCelda.getCellStyle());
                    }else{
                        //si si lo hay se establece
                        loCelda.setCellStyle(loEstilo);
                    }
                }
            }
        }

        //guardar
        FileOutputStream fileOut = new FileOutputStream(psFichero);
        wb.write(fileOut);
        fileOut.close();
    }

}
