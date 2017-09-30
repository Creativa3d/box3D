/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.excel;

import android.content.Context;

import java.io.File;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import impresionJasper.JInfConfigColumnaJasper;
import impresionJasper.JInfConfigColumnasJasper;
import jxl.Workbook;
import jxl.write.Colour;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import utiles.JCadenas;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JExcelListado extends JProcesoAccionAbstracX {
    private JListDatos moList;
    private static final int mclComienzoCabezera = 2;
    private String msFile;
    private WritableSheet sheet;
    private JInfConfigColumnasJasper moConfig = new JInfConfigColumnasJasper();
	private Context moContext;
	private boolean mbMostrar;
    
    public JExcelListado(Context poContext, boolean pbMostrar){
        super(poContext);
        moContext = poContext;
        mbMostrar=pbMostrar;
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
        return "Excel " + moList.msTabla;
    }

    public int getNumeroRegistros() {
        return moList.size();
    }

    public void procesar() throws Throwable {
        //Se crea el libro Excel
        WritableWorkbook workbook =
                Workbook.createWorkbook(new File(msFile)); 

        try{
            //Se crea una nueva hoja dentro del libro
            sheet = workbook.createSheet("Hoja 1", 0); 


            WritableFont times10pt=new WritableFont(WritableFont.COURIER, 13, WritableFont.BOLD);
            WritableCellFormat loFuente = new WritableCellFormat(times10pt);
            sheet.addCell(new jxl.write.Label(0, 0, moList.msTabla, loFuente));


            crearCabecera();

            if(moList.moveFirst()){
                do{
                    toExcel(moList.getIndex()+mclComienzoCabezera+1);
                }while(moList.moveNext());
            }


            //Escribimos los resultados al fichero Excel
            workbook.write();
        }finally{
            workbook.close(); 
        }
        
        
    }
//
    public void crearCabecera() throws Exception {
        
        for(int i = 0 ; i < moList.getFields().size(); i++){
            JFieldDef loCampo = moList.getFields(i);
            WritableFont times10pt=new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD);
            WritableCellFormat loFuente = new WritableCellFormat(times10pt);
            loFuente.setWrap(true);
            loFuente.setBackground(Colour.GRAY_25);
            sheet.addCell(new jxl.write.Label(i, mclComienzoCabezera, loCampo.getCaption(), loFuente));
            sheet.setColumnView(i, (int)(getConfig().getColumna(i).getLong()/8.0));
        }
    }

    public void toExcel(int plfila) throws Exception {
        for(int i = 0 ; i < moList.getFields().size(); i++){
            JFieldDef loCampo = moList.getFields(i);
            if(!loCampo.isVacio()){
                if(loCampo.isNumerico()){
                    if(!JCadenas.isVacio(getConfig().getColumna(i).getFormato())){
                        NumberFormat loformat = new NumberFormat(getConfig().getColumna(i).getFormato());
                        WritableCellFormat loFuente = new WritableCellFormat(loformat);
                        sheet.addCell(new jxl.write.Number(i, plfila, loCampo.getDouble(), loFuente));
                    } else {
                        sheet.addCell(new jxl.write.Number(i, plfila, loCampo.getDouble()));
                    }
                } else if(loCampo.getTipo()==JListDatos.mclTipoFecha){
                    if(!JCadenas.isVacio(getConfig().getColumna(i).getFormato())){
                        DateFormat loformat = new DateFormat(getConfig().getColumna(i).getFormato());
                        WritableCellFormat loFuente = new WritableCellFormat(loformat);
                        sheet.addCell(new jxl.write.DateTime(i, plfila, loCampo.getDateEdu().getDate(), loFuente));
                    } else {
                        sheet.addCell(new jxl.write.DateTime(i, plfila, loCampo.getDateEdu().getDate()));
                    }
                } else if(loCampo.getTipo()==JListDatos.mclTipoBoolean){
                    sheet.addCell(new jxl.write.Boolean(i,plfila,loCampo.getBoolean())); 
                } else{
                    sheet.addCell(new jxl.write.Label(i, plfila, loCampo.toString()));
                }
            }
        }
    }

    public String getTituloRegistroActual() {
        return "";
    }

    public void mostrarMensaje(String psMensaje) {

    	if(mbMostrar){

    		JEjecutar.abrirDocumento(moContext, msFile);
    	}
    }


}
