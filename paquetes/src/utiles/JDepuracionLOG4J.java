/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JDepuracionLOG4J implements IDepuracionIMPL {
    private static org.apache.log4j.Logger registro;
    private String msClase="";
    
    public JDepuracionLOG4J(){
        this("aplicacion");
    }
    public JDepuracionLOG4J(String psNombre){
        setDatos(getRootAutomatico(), psNombre);
    }
    public JDepuracionLOG4J(String psRuta, String psNombre){
        if(psRuta==null || psRuta.equals("")){
            psRuta=getRootAutomatico();
        }
        setDatos(psRuta, psNombre);
    }
    private String getRootAutomatico(){
        File loFileRoot = new File(System.getProperty("user.home"), "listDatos");
        loFileRoot.mkdirs();
        loFileRoot = new File(loFileRoot, "log");
        loFileRoot.mkdirs();
        return loFileRoot.getAbsolutePath();
    }
    public void setDatos(String psRuta, String psNombre){
        File loFile;
        File loFileLog;
        loFile = new File(psRuta, psNombre + "log4j.properties");
        try{
            loFile.getParentFile().mkdirs();
        }catch(Exception e){e.printStackTrace();}
        loFileLog = new File(psRuta, psNombre + ".log");
        String lsLog =
            "################################################################\n"+
            "### Configuracion para LOCAL                                 ###\n"+
            "################################################################\n"+
            "log4j.rootCategory=WARN, LOGFILE, CONSOLE\n"+
            "\n"+
            "log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender\n"+
            "log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout\n"+
            "log4j.appender.CONSOLE.layout.ConversionPattern=%d %p [%c] - <%m>%n\n"+
            "log4j.appender.CONSOLE.level=WARN\n"+
                
            "\n"+
            "################################################################\n"+
            "### Configuracion para DESARROLLO, PREPRODUCCION, PRODUCCION ###\n"+
            "###   Solo nos interesa el nivel de ERROR    		     ###\n"+
            "###   No hay salida de consola			       ###\n"+
            "################################################################\n"+
            "#log4j.rootCategory=ALL, diario\n"+
            "\n"+
            "################################################################\n"+
            "### Configuracion Comun		  		       ###\n"+
            "################################################################\n"+
            "#pone la fecha en el fichero, creo q hay problema de q no borra nunca\n"+
            "#log4j.appender.LOGFILE=org.apache.log4j.DailyRollingFileAppender\n"+
            "#log4j.appender.LOGFILE.file="+loFileLog.getAbsolutePath().replace('\\', '/') +"\n"+
            "#log4j.appender.LOGFILE.append=true\n"+
            "#log4j.appender.LOGFILE.DatePattern='.'yyyy-MM-dd\n"+
            "\n"+
            "log4j.appender.LOGFILE=org.apache.log4j.RollingFileAppender\n"+
            "log4j.appender.LOGFILE.File="+loFileLog.getAbsolutePath().replace('\\', '/') +"\n"+
            "log4j.appender.LOGFILE.MaxFileSize=500KB\n"+
            "log4j.appender.LOGFILE.MaxBackupIndex=10\n"+
            "log4j.appender.LOGFILE.append=true\n"+
            "log4j.appender.LOGFILE.level=WARN\n"+
            "log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout\n"+
            "log4j.appender.LOGFILE.layout.ConversionPattern=%d %p [%c] - <%m>%n \n"+
            "\n"+
            "log4j.appender.Default.Threshold=WARN\n";

        InputStream loin = new CadenaLarga(lsLog);

        try {
                FileOutputStream loOut = new FileOutputStream(loFile);
                JArchivo.guardarArchivo(loin, loOut);
                PropertyConfigurator.configure(loFile.getAbsolutePath());
                registro = Logger.getLogger(JDepuracion.class);
                System.out.println ("[" + loFile.getAbsolutePath() + "] Logger inicializado.");
        } catch (Exception e) {
                BasicConfigurator.configure();
                registro = Logger.getLogger(JDepuracion.class);
                System.out.println ("Excepcion al inicializar el log: " + e.toString());
        }
    }
    public void anadirTexto(int plNivel, String psGrupo, Throwable e, Object poExtra) {
        if(JDepuracion.mbDepuracion && JDepuracion.mlNivelDepuracion<=plNivel){
            switch(plNivel){
                case JDepuracion.mclINFORMACION:
                    registro.warn("INFO: "+psGrupo, e);//lo hacemos asi para evitar las depuraciones de programas ajenos
                    break;
                case JDepuracion.mclWARNING:
                    registro.warn("WARN: "+psGrupo, e);
                    break;
                case JDepuracion.mclCRITICO:
                    registro.error("ERROR: "+psGrupo, e);
                    break;
            }
        }
    }

    public void anadirTexto(int plNivel, String psGrupo, String psTexto, Object poExtra) {
        if(JDepuracion.mbDepuracion && JDepuracion.mlNivelDepuracion<=plNivel){
            switch(plNivel){
                case JDepuracion.mclINFORMACION:
                    registro.warn("INFO: "+psGrupo+" - "+psTexto);
                    break;
                case JDepuracion.mclWARNING:
                    registro.warn("WARN: "+psGrupo+" - "+psTexto);
                    break;
                case JDepuracion.mclCRITICO:
                    registro.error("ERROR: "+psGrupo+" - "+psTexto);
                    break;
            }
        }
    }

    @Override
    public void debug(Object o) {
        anadirTexto(JDepuracion.mclINFORMACION, msClase, o.toString(), null);
    }

    @Override
    public void debug(Object o, Throwable thrwbl) {
        anadirTexto(JDepuracion.mclINFORMACION, msClase, thrwbl, null);
    }

    @Override
    public void error(Object o) {
        anadirTexto(JDepuracion.mclCRITICO, msClase, o.toString(), null);
    }

    @Override
    public void error(Object o, Throwable thrwbl) {
        anadirTexto(JDepuracion.mclCRITICO, msClase, thrwbl, null);
    }

    @Override
    public void fatal(Object o) {
        anadirTexto(JDepuracion.mclCRITICO, msClase, o.toString(), null);
    }

    @Override
    public void fatal(Object o, Throwable thrwbl) {
        anadirTexto(JDepuracion.mclCRITICO, msClase, thrwbl, null);
    }

    @Override
    public void info(Object o) {
        anadirTexto(JDepuracion.mclINFORMACION, msClase, o.toString(), null);
    }

    @Override
    public void info(Object o, Throwable thrwbl) {
        anadirTexto(JDepuracion.mclINFORMACION, msClase, thrwbl, null);
    }

    @Override
    public boolean isDebugEnabled() {
        return JDepuracion.mlNivelDepuracion<=JDepuracion.mclINFORMACION;
    }

    @Override
    public boolean isErrorEnabled() {
        return JDepuracion.mlNivelDepuracion<=JDepuracion.mclCRITICO;
    }

    @Override
    public boolean isFatalEnabled() {
        return JDepuracion.mlNivelDepuracion<=JDepuracion.mclCRITICO;
    }

    @Override
    public boolean isInfoEnabled() {
        return JDepuracion.mlNivelDepuracion<=JDepuracion.mclINFORMACION;
    }

    @Override
    public boolean isTraceEnabled() {
        return JDepuracion.mlNivelDepuracion<=JDepuracion.mclINFORMACION;
    }

    @Override
    public boolean isWarnEnabled() {
        return JDepuracion.mlNivelDepuracion<=JDepuracion.mclWARNING;
    }

    @Override
    public void trace(Object o) {
        anadirTexto(JDepuracion.mclINFORMACION, msClase, o.toString(), null);
    }

    @Override
    public void trace(Object o, Throwable thrwbl) {
        anadirTexto(JDepuracion.mclINFORMACION, msClase, thrwbl, null);
    }

    @Override
    public void warn(Object o) {
        anadirTexto(JDepuracion.mclWARNING, msClase, o.toString(), null);
    }

    @Override
    public void warn(Object o, Throwable thrwbl) {
        anadirTexto(JDepuracion.mclWARNING, msClase, thrwbl, null);
    }


}
