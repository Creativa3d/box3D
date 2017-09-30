/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.excel;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import impresionJasper.JInfConfigColumnaJasper;
import impresionJasper.JInfConfigColumnasJasper;
import java.awt.Font;
import utiles.JCadenas;
import utiles.JFormat;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;

/**
 *
 * @author eduardo
 */
public class JExcelListado extends JProcesoAccionAbstracX {
    private JListDatos moList;
    private static final int mclComienzoCabezera = 2;
    private String msFile;
    private JExcel moExcel;
    private JInfConfigColumnasJasper moConfig = new JInfConfigColumnasJasper();
    
    public JExcelListado(){
        
    }

    public JInfConfigColumnasJasper getConfig() {
        if (moList != null) {
            int lLong = 80;
            if (moConfig.size() > 0) {
                lLong = 0;
            }
            synchronized (this) {
                for (int i = 0; i < moList.getFields().size(); i++) {
                    JFieldDef loCampo = moList.getFields(i);
                    if (!moConfig.existColumna(i)) {
                        moConfig.addColumna(
                                new JInfConfigColumnaJasper(
                                i,
                                String.valueOf(i),
                                moConfig.getUltOrden() + 1,
                                lLong,
                                loCampo.getCaption()));
                    }
                }
            }
        }
        return moConfig;
    }

    /**
     * @return the moList
     */
    public JListDatos getList() {
        return moList;
    }

    /**
     * @param moList the moList to set
     */
    public void setList(JListDatos moList) {
        this.moList = moList;
    }

    /**
     * @return the msRuta
     */
    public String getFile() {
        return msFile;
    }

    /**
     * @param msRuta the msRuta to set
     */
    public void setFile(String msRuta) {
        this.msFile = msRuta;
    }
    
    public String getTitulo() {
        return "Impresión excel " + moList.msTabla;
    }

    public int getNumeroRegistros() {
        return moList.size();
    }

    public void procesar() throws Throwable {
        moExcel = new JExcel();


        moExcel.setFuenteCelda(0, 0, new Font("Courier New", Font.BOLD, 13), false, false, (short)0);
        moExcel.getCell(0, 0).setCellValue(moList.msTabla);

        moExcel.getRow(0).setHeight((short)500);
        
        crearCabecera();
        
        if(moList.moveFirst()){
            do{
                toExcel(moList.getIndex()+mclComienzoCabezera+1);
            }while(moList.moveNext());
        }
    

        moExcel.guardar(msFile);
        
       
        
    }
//    public static void createCell(HSSFRow row, int i, String value) {
//        createCell(row, i, value, null);
//    }
//    public static void createCell(HSSFRow row, int i, double value) {
//        HSSFCell cell = row.createCell((short)i);
//        cell.setCellValue(value);
//    }
//    public static void createCell(HSSFRow row, int i, String value, HSSFCellStyle style) {
//        HSSFCell cell = row.createCell((short)i);
//        value = value+" ";
//        cell.setCellValue(value);
//        // si no hay estilo, no se aplica
//        if (style != null)
//        cell.setCellStyle(style);
//    }
//
    public void crearCabecera() throws Exception {
        
        for(int i = 0 ; i < moList.getFields().size(); i++){
            JFieldDef loCampo = moList.getFields(i);
            moExcel.getCell(mclComienzoCabezera, i).setCellValue(loCampo.getCaption());
            moExcel.setWrapTextCelda(mclComienzoCabezera, i, true);
            moExcel.setFuenteCelda(mclComienzoCabezera, i, new Font("Courier New", Font.BOLD, 10), false, false, (short)0);
            
            int lWidth = getConfig().getColumna(i).getLong()*40;
            
            if(lWidth>20000 || lWidth==0){
                lWidth=20000;
            }
            if(lWidth<1000){
                lWidth=1000;
            }
            moExcel.setColumnWidth(i, lWidth);
        }
        moExcel.setColorFondoCelda(mclComienzoCabezera, 0, moList.getFields().size()-1, moExcel.LIGHT_YELLOW);
    }

    public void toExcel(int plfila) throws Exception {
        for(int i = 0 ; i < moList.getFields().size(); i++){
            JFieldDef loCampo = moList.getFields(i);
            if(!loCampo.isVacio()){
                if(loCampo.isNumerico()){
                    if(!JCadenas.isVacio(getConfig().getColumna(i).getFormato())){
                        moExcel.setValue(plfila, i, JFormat.msFormatearDouble(
                            loCampo.getDouble()
                            , getConfig().getColumna(i).getFormato()));
                    } else {
                        moExcel.setValue(plfila, i, loCampo.getDouble());
                    }
                } else if(loCampo.getTipo()==JListDatos.mclTipoFecha){
                    if(!JCadenas.isVacio(getConfig().getColumna(i).getFormato())){
                        moExcel.setValue(plfila, i
                                , JFormat.msFormatearFecha(
                                    loCampo.getDateEdu().moDate()
                                    , getConfig().getColumna(i).getFormato()));
                    } else {
                        moExcel.setValue(plfila, i, loCampo.toString());
                    }
                } else if(loCampo.getTipo()==JListDatos.mclTipoBoolean){
                    moExcel.setValue(plfila, i, loCampo.getBoolean());
                } else{
                    moExcel.setValue(plfila, i, loCampo.toString());
                }
            }
        }
    }

    public String getTituloRegistroActual() {
        return "";
    }

    public void mostrarMensaje(String psMensaje) {
         utilesx.JEjecutar.abrirDocumento(msFile);
    }


}
