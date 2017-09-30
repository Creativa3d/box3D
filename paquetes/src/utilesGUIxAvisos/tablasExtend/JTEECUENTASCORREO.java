/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxAvisos.tablasExtend;

import ListDatos.ECampoError;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JListDatosFiltroElem;
import ListDatos.estructuraBD.JFieldDef;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utiles.JDateEdu;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreoLeer;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablas.JTCUENTASCORREO;


public class JTEECUENTASCORREO extends JTCUENTASCORREO{

    private static final long serialVersionUID = 1L;

    public static String[] masCaption = JDatosGenerales.getTextosForms().getCaptions(msCTabla, masNombres);
    
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla CUENTASCORREO incluyendole el servidor de datos
     */
    public JTEECUENTASCORREO(IServerServidorDatos poServidorDatos) {
        super();

        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,masCaption, malTamanos);
        moList.addListener(this);
        moList.getFields().get(lPosiCODIGOCUENTACORREO).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }


    public static boolean getPasarCache(){
        return true;
    }

    public static JTEECUENTASCORREO getTabla(String psCODIGOCOLEGIO,String psNUMEROCOLEGIADO, String psCODIGODESPACHO,
            String psCODIGOCUENTACORREO, IServerServidorDatos poServer) throws Exception {
        JTEECUENTASCORREO loTabla = new JTEECUENTASCORREO(poServer);
        if(getPasarCache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND,
                    JListDatos.mclTIgual,
                    malCamposPrincipales,
                    new String[]{psCODIGOCOLEGIO,psNUMEROCOLEGIADO,psCODIGODESPACHO,
                    psCODIGOCUENTACORREO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOCOLEGIO,psNUMEROCOLEGIADO,
                psCODIGODESPACHO, psCODIGOCUENTACORREO}) ,false);
        }
        return loTabla;
    }
    
     
    
    public static JTEECUENTASCORREO getCuentasCorreo(         IServerServidorDatos poServer) throws Exception {
        JTEECUENTASCORREO loTabla = new JTEECUENTASCORREO(poServer);
        loTabla.recuperarTodosNormalSinCache();
        
        return loTabla;
    }

//    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer){
//        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
//        loParam.mlCamposPrincipales = malCamposPrincipales;
//        loParam.masTextosDescripciones = null;
//        loParam.mbConDatos=false;
//        loParam.mbMensajeSiNoExiste=true;
//
//        loParam.malDescripciones = new int[]{
//            JTFORMCUENTASCORREO.lPosiCODIGOCOLEGIO,
//            JTFORMCUENTASCORREO.lPosiNUMEROCOLEGIADO,
//            JTFORMCUENTASCORREO.lPosiCODIGODESPACHO,
//            JTFORMCUENTASCORREO.lPosiCODIGOCUENTACORREO,
//            JTFORMCUENTASCORREO.lPosiFECHAULTMODIF,
//            JTFORMCUENTASCORREO.lPosiNOMBRE,
//            JTFORMCUENTASCORREO.lPosiDIRECCION,
//            JTFORMCUENTASCORREO.lPosiSERVIDORENTRANTE,
//            JTFORMCUENTASCORREO.lPosiSERVIDORSALIENTE,
//            JTFORMCUENTASCORREO.lPosiUSUNOMBRE
//            };
//
//        loParam.moControlador = new gestionTrafico.tablasControladoras.JT2GESTORES(poServer);
//
//        gestionTrafico.consultas.JTFORMGESTORES loConsulta = new  gestionTrafico.consultas.JTFORMGESTORES(poServer);
//        loConsulta.crearSelectSimple();
//
//        loParam.moTabla = loConsulta;
//
//        return loParam;
//    }

    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){

                moListaRelaciones = null;
                moListaRelaciones = new HashMap();

                moList.clear();
            }
    }

    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();


           }
             cargarControlador(psTabla,poMostrar);

    }
//   


    protected void cargarControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception {
//
//        IPanelControlador loAux =
//                        JDatosGeneralesP.getDatosGenerales().getControlador(
//                            moList.moServidor,
//                            poMostrar,
//                            psTabla,
//                            moList.msTabla,
//                            moList.getFields().moFilaDatos()
//                            );
//        moListaRelaciones.put(psTabla, loAux);
    }

    public void valoresDefecto() throws Exception {

    }

    public boolean buscar(JGUIxAvisosCorreo poAvisos) throws ECampoError{
        JFilaDatosDefecto loFila = new JFilaDatosDefecto(JFilaDatosDefecto.moArrayDatos(poAvisos.getIdentificador(), '-'));
        
        return moList.buscar(JListDatos.mclTIgual
                , new int[] {lPosiCODIGOCUENTACORREO}
                , new String[]{loFila.msCampo(lPosiCODIGOCUENTACORREO)});
        
    }
    

    public void establecer(JGUIxAvisosCorreo poAvisos) throws ECampoError{
        getCARPETAENTRADA().setValue(poAvisos.getLeer().getCarpetaCorreo());
        getPASSENTRANTE().setValue(poAvisos.getLeer().getPassword());
        getUSUARIOENTRANTE().setValue(poAvisos.getLeer().getUsuario());
        getPUERTOENTRADA().setValue(poAvisos.getLeer().getPuerto());
        getSEGURIDADENTRADA().setValue(poAvisos.getLeer().getSeguridad());
        getSERVIDORENTRANTE().setValue(poAvisos.getLeer().getServidor());

        getTIPOENTRANTE().setValue(poAvisos.getLeer().getTipoEntrante());
        
        
        getNOMBRE().setValue(poAvisos.getEnviar().getCorreoNombre());
        getDIRECCION().setValue(poAvisos.getEnviar().getCorreo());

        getFECHAULTMODIF().setValue(new JDateEdu());

        getCARPETASALIDA().setValue(poAvisos.getEnviar().getCarpetaCorreo());
        getPASSSALIENTE().setValue(poAvisos.getEnviar().getPassword());
        getUSUARIOSALIENTE().setValue(poAvisos.getEnviar().getUsuario());
        getPUERTOSALIDA().setValue(poAvisos.getEnviar().getPuerto());
        getSEGURIDADSALIDA().setValue(poAvisos.getEnviar().getSeguridad());
        getSERVIDORSALIENTE().setValue(poAvisos.getEnviar().getServidor());

    }
    public String getIdentificadorCorreo(){
        
        String lsClave = "";
        for(int i = 0 ; i < getFields().size(); i++){
            if(getField(i).getPrincipalSN()){
                lsClave += getField(i).getString() + "-";
            }
        }
        return lsClave;
    }
    public JGUIxAvisosCorreo getCorreo() throws ECampoError{
        JGUIxAvisosCorreo loAvisos = new JGUIxAvisosCorreo();
        
        
        loAvisos.setIdentificador(getIdentificadorCorreo());
                
        loAvisos.getLeer().setCarpetaCorreo(getCARPETAENTRADA().getString());
        loAvisos.getLeer().setPassword(getPASSENTRANTE().getString());
        loAvisos.getLeer().setUsuario(getUSUARIOENTRANTE().getString());
        loAvisos.getLeer().setPuerto(getPUERTOENTRADA().getInteger());
        loAvisos.getLeer().setSeguridad(getSEGURIDADENTRADA().getInteger());
        loAvisos.getLeer().setServidor(getSERVIDORENTRANTE().getString());

        
        loAvisos.getLeer().setTipoEntrante(getTIPOENTRANTE().getString());
            
        loAvisos.getEnviar().setCorreoNombre(getNOMBRE().getString());
        loAvisos.getEnviar().setCorreo(getDIRECCION().getString());


        loAvisos.getEnviar().setCarpetaCorreo(getCARPETASALIDA().getString());
        loAvisos.getEnviar().setPassword(getPASSSALIENTE().getString());
        loAvisos.getEnviar().setUsuario(getUSUARIOSALIENTE().getString());
        loAvisos.getEnviar().setPuerto(getPUERTOSALIDA().getInteger());
        loAvisos.getEnviar().setSeguridad(getSEGURIDADSALIDA().getInteger());
        loAvisos.getEnviar().setServidor(getSERVIDORSALIENTE().getString());
        loAvisos.setServer(moList.moServidor);
        
        return loAvisos;

    }
    
    public static void guardarCorreos(IServerServidorDatos poServer, List<JGUIxAvisosCorreo> poCorreos) throws Exception{

        JTEECUENTASCORREO loCuentas = new JTEECUENTASCORREO(poServer);
        loCuentas.recuperarTodosNormalSinCache();

        ArrayList<JGUIxAvisosCorreo> loBorrados = new ArrayList<JGUIxAvisosCorreo>();
        for(JGUIxAvisosCorreo loCorreo: poCorreos){
            if(loCorreo.getTipoModif()==JListDatos.mclEditar
                    || loCorreo.getTipoModif()==JListDatos.mclNuevo){
                if(!loCuentas.buscar(loCorreo)){
                    loCuentas.addNew();
                    loCuentas.valoresDefecto();

                }
                loCuentas.establecer(loCorreo);
                IResultado loResult = loCuentas.update(true);
                if(!loResult.getBien()){
                    throw new Exception(loResult.getMensaje());
                } else {
                    loCorreo.setIdentificador(loCuentas.getIdentificadorCorreo());
                    loCorreo.setTipoModif(JListDatos.mclNada);
                }
            }
            if(loCorreo.getTipoModif()==JListDatos.mclBorrar){
                loBorrados.add(loCorreo);
                if(loCuentas.buscar(loCorreo)){
                    IResultado loResult = loCuentas.moList.borrar(true);
                    if(!loResult.getBien()){
                        throw new Exception(loResult.getMensaje());
                    }                        
                }
            }
        }
        for(JGUIxAvisosCorreo loCorreo: loBorrados){
            poCorreos.remove(loCorreo);
        }
                    
    }
}
