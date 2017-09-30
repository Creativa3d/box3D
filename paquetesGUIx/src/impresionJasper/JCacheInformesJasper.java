/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionJasper;

import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class JCacheInformesJasper {
    private static JCacheInformesJasper moCacheStatic;
    private HashMap moListaReport=new HashMap();
    private boolean mbCachear = true;

    public void setCachear(boolean pb){
        mbCachear=pb;
    }
    public boolean isCachear(){
        return mbCachear;
    }
    public synchronized  JasperReport getReport(String psReport) throws JRException{
        return getReport(psReport, "");
    }
    public synchronized  JasperReport getReport(String psReport, String psPathPrevioJar) throws JRException{
        //quitamos la extension
        if(psReport.substring(psReport.length()-".jrxml".length(), psReport.length()).equalsIgnoreCase(".jrxml") ){
            psReport=psReport.substring(0 , psReport.length()-".jrxml".length());
        }
        if(psReport.substring(psReport.length()-".jasper".length(), psReport.length()).equalsIgnoreCase(".jasper") ){
            psReport=psReport.substring(0 , psReport.length()-".jasper".length());
        }
        JasperReport loJasper=(JasperReport) moListaReport.get(psReport);
        if(loJasper==null){
            loJasper=null;
            //1º el jrxml local
            File loFile = new File(psReport + ".jrxml");
            if(loFile.exists()){
                loJasper = JasperCompileManager.compileReport(loFile.getAbsolutePath());
            }else{
                //2º el jasper local
                loFile = new File(psReport + ".jasper");
                if(loFile.exists()){
                    loJasper = (JasperReport) JRLoader.loadObject(loFile);
                }else{
                    //3º .jasper dentro del jar
                    try{
                        if(getClass().getResource(psPathPrevioJar+psReport+".jasper")!=null){
                            loJasper=(JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(psPathPrevioJar+psReport+".jasper"));
                        }else{
                            throw new Exception("No existe el .jasper");
                        }
                    }catch(Exception e){
                        //en el jar puede no existir
                        if(getClass().getResource(psPathPrevioJar+psReport+".jrxml")!=null){
                            //4º .jrxml dentro del jar
                            loJasper = JasperCompileManager.compileReport(getClass().getResourceAsStream(psPathPrevioJar+psReport+".jrxml"));
                        }
                    }
                }
            }
            if(mbCachear){
                moListaReport.put(psReport, loJasper);
            }
        }
        return loJasper;
    }
    
    public static JCacheInformesJasper getInstance(){
        if(moCacheStatic == null){
            moCacheStatic = new JCacheInformesJasper();
        }
        return moCacheStatic;
    }
}
