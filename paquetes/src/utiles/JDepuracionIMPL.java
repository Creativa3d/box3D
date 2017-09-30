/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

public class JDepuracionIMPL implements IDepuracionIMPL {

    private String msClase="";

    public JDepuracionIMPL(){
        
    }
    public JDepuracionIMPL(String psClase){
        msClase = psClase;
    }
    
    @Override
    public void anadirTexto(int plNivel, String psGrupo, Throwable e, Object poExtra) {
        anadirTexto(plNivel, psGrupo, e.toString(), null);
        e.printStackTrace();
    }

    @Override
    public void anadirTexto(int plNivel, String psGrupo, String psTexto, Object poExtra) {
        if(JDepuracion.mbDepuracion && JDepuracion.mlNivelDepuracion>=plNivel){
            JDateEdu loDate = new JDateEdu();
            String lsTExto = loDate.toString() + " " + JDepuracion.getNivel(plNivel) + ":" + psGrupo + "->" + psTexto;
            System.out.println(lsTExto);
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
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isFatalEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
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
