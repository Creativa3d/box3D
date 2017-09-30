/*
 * JTDATOSGENERALES22.java
 *
 * Created on 22 de febrero de 2005, 16:44
 */

package utilesGUIx.aplicacion.auxiliares.tablasExtend;


import ListDatos.*;
import utilesGUIx.aplicacion.auxiliares.tablas.JTDATOSGENERALES2;

public class JTEEDATOSGENERALES2 extends JTDATOSGENERALES2 {
    private static final long serialVersionUID = 1L;

    public static final String mcsSMSLicencia="recordatorioSMSLicencia";
    public static final String mcsSMSUsuario="recordatorioSMSUsuario";
    public static final String mcsSMSClave="recordatorioSMSClave";
    public static final String mcsSMSTipo="recordatorioSMSTipo";

    /** Creates a new instance of JTDATOSGENERALES22 */
    public JTEEDATOSGENERALES2(IServerServidorDatos poServer) {
        super(poServer);


    }
    protected synchronized void cargar() throws Exception {
        if(!moList.mbCargado){
            recuperarTodosNormalCache();
        }
    }
    public String setValor(final String psAtributo, final String psValor)throws Exception {
        String lsValor = "";
        cargar();
        if (!moList.buscar(JListDatos.mclTIgual, lPosiATRIBUTO, psAtributo)){
            moList.addNew();
        }
        getATRIBUTO().setValue(psAtributo);
        getVALOR().setValue(psValor);

        IResultado loREsult = moList.update(true);
        if(!loREsult.getBien()){
            throw new Exception(loREsult.getMensaje());
        }

        return lsValor;
    }
    public String recuperarValor(final String psAtributo)throws Exception {
        String lsValor = "";
        cargar();
        if (moList.buscar(JListDatos.mclTIgual, lPosiATRIBUTO, psAtributo)){
            lsValor = getVALOR().getString();
        }
        return lsValor;
    }
    protected String recuperarValor(final String psAtributo, String psValorDefecto)throws Exception {
        String lsValor = recuperarValor(psAtributo);
        if(lsValor==null || lsValor.equals("")){
            lsValor=psValorDefecto;
        }
        return lsValor;

    }
    public String setPropiedad(final String psAtributo, final String psValor)throws Exception {
        return setValor(psAtributo, psValor);
    }
    public String getPropiedad(final String psAtributo) throws Exception{
        return recuperarValor(psAtributo);
    }
    public String getPropiedad(final String psAtributo, String psValorDefecto) throws Exception{
        return recuperarValor(psAtributo, psValorDefecto);
    }    
   

    public String getSerieReformas() {
        return "RE";
    }
    public String getSerieHomologIndiv() {
        return "HIC";
    }
    public String getInspHomologIndiv() {
        return "HIC";
    }
    public String getIdentHomologIndiv() {
        return "HIC";
    }


    public String getSMSTipo() throws Exception {
        return getPropiedad(mcsSMSTipo, "");
    }
        
    public  String getSMSLicencia() throws Exception{
        return getPropiedad(mcsSMSLicencia, "");
    }
    public  String getSMSUsuario() throws Exception{
        return getPropiedad(mcsSMSUsuario, "");
    }
    public  String getSMSClave() throws Exception{
        return getPropiedad(mcsSMSClave, "");
    }

    public  void setSMSClave(String text) throws Exception {
        setPropiedad(mcsSMSClave, text);
    }
    public  void setSMSLicencia(String text) throws Exception {
        setPropiedad(mcsSMSLicencia, text);
    }
    public  void setSMSUsuario(String text) throws Exception {
        setPropiedad(mcsSMSUsuario, text);
    }
    public void setSMSTipo(String ps) throws Exception {
        setPropiedad(mcsSMSTipo, ps);
    }    
}
