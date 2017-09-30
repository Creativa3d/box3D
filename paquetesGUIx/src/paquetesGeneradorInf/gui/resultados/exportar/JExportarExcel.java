/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetesGeneradorInf.gui.resultados.exportar;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import impresionXML.impresion.excel.JExcel;
import java.awt.Font;
import java.io.File;

/**
 *
 * @author eduardo
 */
public class JExportarExcel  implements IExportar {

    public JExportarExcel() {

    }

    public void exportar(JListDatos poList, File poFile) throws Throwable {
        JExcel loExcel = new JExcel();

        loExcel.setFuenteCelda(0, 0, new Font("Courier New", Font.BOLD, 13), false, false, (short)0);
        loExcel.getCell(0, 0).setCellValue(poList.msTabla);

        loExcel.getRow(0).setHeight((short)500);
        
        crearCabecera(loExcel, poList, 2);
        
        if(poList.moveFirst()){
            do{
                toExcel(loExcel, poList, poList.getIndex()+2+1);
            }while(poList.moveNext());
        }
    
        loExcel.guardar(poFile.getAbsolutePath());
        
        utilesx.JEjecutar.abrirDocumento(poFile.getAbsolutePath());

    }
    public void crearCabecera(JExcel poExcel, JListDatos poList, int plFila) throws Exception {
        
        for(int i = 0 ; i < poList.getFields().size(); i++){
            JFieldDef loCampo = poList.getFields(i);
            poExcel.getCell(plFila, i).setCellValue(loCampo.getCaption());
            poExcel.setWrapTextCelda(plFila, i, true);
            poExcel.setFuenteCelda(plFila, i, new Font("Courier New", Font.BOLD, 10), false, false, (short)0);
            
        }
        poExcel.setColorFondoCelda(plFila, 0, poList.getFields().size()-1, poExcel.LIGHT_YELLOW);
    }
    

    public void toExcel(JExcel poExcel, JListDatos poList, int plfila) throws Exception {
        for(int i = 0 ; i < poList.getFields().size(); i++){
            JFieldDef loCampo = poList.getFields(i);
            poExcel.setValue(plfila, i, loCampo);
        }
    }

    public String getNombre() {
        return "Formato Excel";
    }

    public String[] getOpcionesNombre() {
        return null;
    }
    public String[] getOpciones() {
        return null;

    }

    public void setOpciones(String[] pasOpciones) {

    }
    public String[] getExtensiones() {
       return new String[]{"xls","xlsx"};
    }
}
