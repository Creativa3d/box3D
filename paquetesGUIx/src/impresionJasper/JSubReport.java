/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionJasper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author aranj3
 */
public class JSubReport {
    private InputStream moInforme; //Subreport fisico sin compilar
    private JRDataSource moDataSource; //Datos con los que rellenar el subreport se pasan como parametro
    private String msNombreParametro; //Nombre del parametro en el report principal para hacer referencia al subreport
    private JasperReport moReport; //Este es el report ya compilado se pasa como parametro

    //Constructores ***********************
    public JSubReport(String psParametro,InputStream poListado,JRDataSource poDataSource) {
        moInforme = poListado;
        moDataSource = poDataSource;
        msNombreParametro = psParametro;
    }
    public JSubReport(String psParametro,String psPath,JRDataSource poDataSource) throws FileNotFoundException {
        FileInputStream loAIE = new FileInputStream(psPath);
        moInforme = (InputStream) loAIE;
        moDataSource = poDataSource;
        msNombreParametro = psParametro;
    }
    public JSubReport(String psParametro,JasperReport poReport,JRDataSource poDataSource) throws FileNotFoundException {
        moReport = poReport;
        moDataSource = poDataSource;
        msNombreParametro = psParametro;
    }
    
    
    //**************************************
    
    //Devolvemos el report ya compilado
    public JasperReport getReport() throws JRException {
        if(moReport==null){
            //Compilar archivo jrxml lo convierte a un archivo .jasper
            moReport= JasperCompileManager.compileReport(this.moInforme);
        }
        return moReport;
        
    }
    
    //Devuelve el nombre del parametro
    public String getNombreParametro() {
        return msNombreParametro;
    }
    
    //Devuelve el datasource
    public JRDataSource getDataSource() {
        return moDataSource;
    }
}
