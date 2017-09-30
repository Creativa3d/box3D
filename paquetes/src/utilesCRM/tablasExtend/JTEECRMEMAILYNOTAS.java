/*
* JTEECRMEMAILYNOTAS.java
*
* Creado el 27/4/2017
*/

package utilesCRM.tablasExtend;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDateEdu;
import utilesCRM.consultas.JTFORMCRMEMAILYNOTAS;
import utilesCRM.tablas.JTCRMEMAILYNOTAS;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;


public class JTEECRMEMAILYNOTAS extends JTCRMEMAILYNOTAS {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);
    public static String mcsCorreo="CORREO";
    public static String mcsNota="NOTA";
    public static String mcsTarea="TAREA";

    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla CRMEMAILYNOTAS incluyendole el servidor de datos
     */
    public JTEECRMEMAILYNOTAS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiCODIGONOTA).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEECRMEMAILYNOTAS getTabla(final String psCODIGOempresa,final String psCODIGONOTA,final IServerServidorDatos poServer) throws Exception {
        JTEECRMEMAILYNOTAS loTabla = new JTEECRMEMAILYNOTAS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOempresa,psCODIGONOTA});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOempresa,psCODIGONOTA}) ,false);
        }
        return loTabla;
    }
    public static JTEECRMEMAILYNOTAS getTablaPorCorreo(String psCodTarea,final IServerServidorDatos poServer) throws Exception {
        JTEECRMEMAILYNOTAS loTabla = new JTEECRMEMAILYNOTAS(poServer);
        
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(JListDatos.mclTIgual
                    , new int[]{loTabla.lPosiGUIXMENSAJESSENDCOD}
                    , new String[]{psCodTarea}));
           
    
        return loTabla;
    }
    
    public static JTEECRMEMAILYNOTAS getTablaPorTarea(final String psCalendario,final String psCODIGOevento,final IServerServidorDatos poServer) throws Exception {
        JTEECRMEMAILYNOTAS loTabla = new JTEECRMEMAILYNOTAS(poServer);
        
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(JListDatos.mclTIgual
                    , new int[]{loTabla.lPosiCODIGOCALENDARIO, loTabla.lPosiCODIGOEVENTO}
                    , new String[]{psCalendario, psCODIGOevento}));
           
    
        return loTabla;
    }


    public static JTEECRMEMAILYNOTAS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOEMPRESA),
                 poFila.msCampo(lPosiCODIGONOTA),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMCRMEMAILYNOTAS.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMCRMEMAILYNOTAS.lPosiASUNTO
            };
        loParam.masTextosDescripciones = null;

//        loParam.moControlador = new JT2CRMEMAILYNOTAS(poServer, poMostrar);

        JTFORMCRMEMAILYNOTAS loConsulta = new JTFORMCRMEMAILYNOTAS(poServer);
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
   public void valoresDefecto() throws Exception {   
       getTIPO().setValue(mcsNota);
       getCODIGOEMPRESA().setValue("1");
       getFECHA().setValue(new JDateEdu());
   }
    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){
                moListaRelaciones = null;
                moListaRelaciones = new HashMap();
            }
    }
    protected void cargarControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
//        IPanelControlador loAux =
//                        JFXConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto().getControlador(
//                            moList.moServidor,
//                            poMostrar,
//                            psTabla,
//                            moList.msTabla,
//                            moList.getFields().moFilaDatos()
//                            );
//        moListaRelaciones.put(psTabla, loAux);
    }
    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();
            }
            
            cargarControlador(psTabla, poMostrar);
        
    }
    public IPanelControlador getControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
        cargar(psTabla, poMostrar);
        return (IPanelControlador) moListaRelaciones.get(psTabla);
    }
    public IResultado guardar() throws Exception{
        //se comprueba antes de guardar la clave pq despues de 
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave =  isMismaClave();
        //comprobamos las tablas relacionadas
        comprobarClaveCargar(lbIsMismaClave);
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));
        
        if(loResult.getBien() && 
           lbIsMismaClave) {
//            JTEESUBFAMILIAS loSub = JTEESUBFAMILIAS.getTabla(getCODIGOFAMILIA().getString(),moList.moServidor);
//            if(loSub.moList.moveFirst()){
//                do{
//                    loSub.getUSALOTESSN().setValue(getUSALOTESSN().getBoolean()); 
//                    loSub.moList.update(false);
//                }while(loSub.moList.moveNext());
//                JActualizarConj loAct = new JActualizarConj("","","");
//                loAct.crearUpdateAPartirList(loSub.moList);
//                loResult.addResultado(loSub.moList.moServidor.actualizarConj(loAct));
//                
//            }
            
        }
        //si estaba en modo nuevo, si todo bien se queda en modo Nada/editar, por lo q hay
        //q actualizar el codigo relacionado
        if(loResult.getBien()){
            if(lbIsMismaClave){
                msCodigoRelacionado = getClave();
            }
        }        
        return loResult;
    }
    public IResultado borrar() throws Exception {
        IResultado loResult = moList.borrar(true);
        return loResult;
    }
}