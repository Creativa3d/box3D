/*
* JTEEAUXILIARES.java
*
* Creado el 17/10/2014
*/

package utilesGUIx.aplicacion.auxiliares.tablasExtend;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.auxiliares.consultas.JTFORMAUXILIARES;
import utilesGUIx.aplicacion.auxiliares.tablas.JTAUXILIARES;
import utilesGUIx.formsGenericos.*;

public class JTEEAUXILIARES extends JTAUXILIARES {
    private static final long serialVersionUID = 1L;
    
    public static final String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);
    
    
    
    protected transient HashMap moListaRelaciones = new HashMap();
    
    

    /**
     * Crea una instancia de la clase intermedia para la tabla AUXILIARES incluyendole el servidor de datos
     */
    public JTEEAUXILIARES(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return true;
    }
    public static JTEEAUXILIARES getTabla(final String psCODIGOAUXILIAR,final IServerServidorDatos poServer) throws Exception {
        JTEEAUXILIARES loTabla = new JTEEAUXILIARES(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOAUXILIAR});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOAUXILIAR}) ,false);
        }
        return loTabla;
    }
    public static JTEEAUXILIARES getTablaPorAcronimo(int plGrupo, final String psACRON,final IServerServidorDatos poServer) throws Exception {
        return getTablaPorAcronimo(String.valueOf(plGrupo), psACRON, poServer);
    }
    public static JTEEAUXILIARES getTablaPorAcronimo(String psGrupo, final String psACRON,final IServerServidorDatos poServer) throws Exception {
        JTEEAUXILIARES loTabla = new JTEEAUXILIARES(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    new int[]{lPosiCODIGOGRUPOAUX,lPosiACRONIMO}, 
                    new String[]{psGrupo, psACRON});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual
                    , new int[]{lPosiCODIGOGRUPOAUX,lPosiACRONIMO}, 
                    new String[]{psGrupo, psACRON}) ,false);
        }
        return loTabla;
    }
    
    public static JTEEAUXILIARES getTablaGrupo(final int plGrupo,final IServerServidorDatos poServer) throws Exception {
        return getTablaGrupo(plGrupo, poServer, false, true);
    }
    public static JTEEAUXILIARES getTablaGrupo(final int plGrupo,final IServerServidorDatos poServer, boolean pbAcronimo, boolean pbOrden ) throws Exception {
        JTEEAUXILIARES loTabla = new JTEEAUXILIARES(poServer);
        loTabla.recuperarTodosNormalCache();
        loTabla.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                lPosiCODIGOGRUPOAUX,
                String.valueOf(plGrupo));
        loTabla.moList.filtrar();
        loTabla.moList=JUtilTabla.clonarFilasListDatos(loTabla.moList, false);
        if(pbOrden){
            if(pbAcronimo){
                loTabla.moList.ordenar(lPosiACRONIMO);
            }else{
                loTabla.moList.ordenar(lPosiDESCRIPCION);
            }
        }
        return loTabla;
    }

    public static JTEEAUXILIARES getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOAUXILIAR),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMAUXILIARES.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMAUXILIARES.lPosiCODIGOGRUPOAUX,
            JTFORMAUXILIARES.lPosiACRONIMO,
            JTFORMAUXILIARES.lPosiDESCRIPCION
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMAUXILIARES.getFieldEstatico(JTFORMAUXILIARES.lPosiCODIGOGRUPOAUX).getCaption(),
             JTFORMAUXILIARES.getFieldEstatico(JTFORMAUXILIARES.lPosiACRONIMO).getCaption(),
             JTFORMAUXILIARES.getFieldEstatico(JTFORMAUXILIARES.lPosiDESCRIPCION).getCaption()
            };

//        loParam.moControlador = new JT2AUXILIARES(poServer, poMostrar);

        JTFORMAUXILIARES loConsulta = new JTFORMAUXILIARES(poServer);
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }


    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final int plGrupo) throws Exception{
        return getParamPanelBusq(poServer, poMostrar, plGrupo, false);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final int plGrupo, final boolean pbConAcronimo) throws Exception{
        return getParamPanelBusq(poServer, poMostrar, plGrupo, false, true);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final int plGrupo, final boolean pbConAcronimo, final boolean pbOrden) throws Exception{
        return getParamPanelBusq(poServer, poMostrar, plGrupo, pbConAcronimo, pbOrden, false);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final int plGrupo, final boolean pbConAcronimo, final boolean pbOrden, final boolean pbCodAcronimo) throws Exception{
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        if(pbCodAcronimo){
            loParam.mlCamposPrincipales = new int[]{JTEEAUXILIARES.lPosiACRONIMO};
        }else{
            loParam.mlCamposPrincipales = JTEEAUXILIARES.malCamposPrincipales;
        }
        loParam.mbConDatos=true;
        loParam.mbMensajeSiNoExiste=true;

        if(pbConAcronimo){
            loParam.malDescripciones = new int[]{
                JTEEAUXILIARES.lPosiACRONIMO,
                JTEEAUXILIARES.lPosiDESCRIPCION
                };
        }else{
            loParam.malDescripciones = new int[]{
                JTEEAUXILIARES.lPosiDESCRIPCION
                };
        }
        loParam.masTextosDescripciones = null;

//        loParam.moControlador = new JT2AUXILIARES(poServer, poMostrar, JTEEAUXILIARESGRUPOS.msCTabla, new JFilaDatosDefecto(String.valueOf(plGrupo)));
        JTEEAUXILIARES loCons = getTablaGrupo(plGrupo,poServer);

        if(pbOrden){
            if(pbConAcronimo){
                loCons.getList().ordenar(JTEEAUXILIARES.lPosiACRONIMO);
            }else{
                loCons.getList().ordenar(JTEEAUXILIARES.lPosiDESCRIPCION);
            }
        }
        loParam.moTabla = loCons;
        
        return loParam;

    }  
   public void valoresDefecto() throws Exception {   
   }
    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){
                moListaRelaciones = null;
                moListaRelaciones = new HashMap();
            }
    }
    protected void cargarControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
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