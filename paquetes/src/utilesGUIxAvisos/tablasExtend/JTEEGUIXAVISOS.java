/*
* JTEEGUIXAVISOS.java
*
* Creado el 3/11/2011
*/

package utilesGUIxAvisos.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.util.HashMap;
import utiles.JDateEdu;
import utilesGUIx.formsGenericos.*;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.JTFORMGUIXAVISOS;
import utilesGUIxAvisos.tablas.JTGUIXAVISOS;

public class JTEEGUIXAVISOS extends JTGUIXAVISOS {
    private static final long serialVersionUID = 1L;
    public static String[] masCaption = JDatosGenerales.getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla GUIXAVISOS incluyendole el servidor de datos
     */
    public JTEEGUIXAVISOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiCODIGO).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEEGUIXAVISOS getTabla(final String psCALENDARIO,final String psCODEVENTO,final String psCODIGO,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXAVISOS loTabla = new JTEEGUIXAVISOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCALENDARIO,psCODEVENTO,psCODIGO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales
                    , new String[]{psCALENDARIO,psCODEVENTO,psCODIGO}) ,false);
        }
        return loTabla;
    }

    public static JTEEGUIXAVISOS getTabla(final String psCALENDARIO,final String psCODEVENTO,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXAVISOS loTabla = new JTEEGUIXAVISOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    new int[]{lPosiCALENDARIO, lPosiCODIGOEVENTO}, 
                    new String[]{psCALENDARIO, psCODEVENTO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual
                    , new int[]{lPosiCALENDARIO, lPosiCODIGOEVENTO}
                    , new String[]{psCALENDARIO, psCODEVENTO}) ,false);
        }
        return loTabla;
    }


    public static JTEEGUIXAVISOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCALENDARIO),
                 poFila.msCampo(lPosiCODIGOEVENTO),
                 poFila.msCampo(lPosiCODIGO),
                poServer);
    }
    
    public JTEEGUIXEVENTOS getEvento() throws Exception {
        JTEEGUIXEVENTOS loTabla = new JTEEGUIXEVENTOS(moList.moServidor);
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
            JListDatos.mclTIgual
            , new int[]{lPosiCALENDARIO, lPosiCODIGOEVENTO }
            , new String[]{getCALENDARIO().getString(), getCODIGOEVENTO().getString()}) 
            , false);
        return loTabla;
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(JDatosGenerales poDatosGenerales) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMGUIXAVISOS.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMGUIXAVISOS.lPosiCODIGOEVENTO,
            JTFORMGUIXAVISOS.lPosiFECHACONCRETA,
            JTFORMGUIXAVISOS.lPosiPANTALLASN,
            JTFORMGUIXAVISOS.lPosiTELF,
            JTFORMGUIXAVISOS.lPosiSENDER,
            JTFORMGUIXAVISOS.lPosiEMAIL
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMGUIXAVISOS.getFieldEstatico(JTFORMGUIXAVISOS.lPosiCODIGOEVENTO).getCaption(),
             JTFORMGUIXAVISOS.getFieldEstatico(JTFORMGUIXAVISOS.lPosiFECHACONCRETA).getCaption(),
             JTFORMGUIXAVISOS.getFieldEstatico(JTFORMGUIXAVISOS.lPosiPANTALLASN).getCaption(),
             JTFORMGUIXAVISOS.getFieldEstatico(JTFORMGUIXAVISOS.lPosiTELF).getCaption(),
             JTFORMGUIXAVISOS.getFieldEstatico(JTFORMGUIXAVISOS.lPosiSENDER).getCaption(),
             JTFORMGUIXAVISOS.getFieldEstatico(JTFORMGUIXAVISOS.lPosiEMAIL).getCaption()
            };

//        loParam.moControlador = new JT2GUIXAVISOS(poDatosGenerales);

        JTFORMGUIXAVISOS loConsulta = new JTFORMGUIXAVISOS(poDatosGenerales.getServer());
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
    @Override
   public void valoresDefecto() throws Exception {   
       getFECHACONCRETA().setValue(new JDateEdu());
   }

    @Override
    public void validarCampos() throws Exception {
        getFECHAMODIFICACION().setValue(new JDateEdu());
        super.validarCampos();
    }
   
    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){
                moListaRelaciones = null;
                moListaRelaciones = new HashMap();
            }
    }
    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();
            }
            
        
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