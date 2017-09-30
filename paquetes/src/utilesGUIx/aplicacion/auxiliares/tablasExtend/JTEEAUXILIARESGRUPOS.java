/*
* JTEEAUXILIARESGRUPOS.java
*
* Creado el 17/10/2014
*/

package utilesGUIx.aplicacion.auxiliares.tablasExtend;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.auxiliares.consultas.JTFORMAUXILIARESGRUPOS;
import utilesGUIx.aplicacion.auxiliares.tablas.JTAUXILIARESGRUPOS;

public class JTEEAUXILIARESGRUPOS extends JTAUXILIARESGRUPOS {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);

    
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla AUXILIARESGRUPOS incluyendole el servidor de datos
     */
    public JTEEAUXILIARESGRUPOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return true;
    }
    public static JTEEAUXILIARESGRUPOS getTabla(final String psCODIGOGRUPOAUX,final IServerServidorDatos poServer) throws Exception {
        JTEEAUXILIARESGRUPOS loTabla = new JTEEAUXILIARESGRUPOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOGRUPOAUX});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOGRUPOAUX}) ,false);
        }
        return loTabla;
    }


    public static JTEEAUXILIARESGRUPOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOGRUPOAUX),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMAUXILIARESGRUPOS.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMAUXILIARESGRUPOS.lPosiDESCRIPCION
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMAUXILIARESGRUPOS.getFieldEstatico(JTFORMAUXILIARESGRUPOS.lPosiDESCRIPCION).getCaption()
            };

//        loParam.moControlador = new JT2AUXILIARESGRUPOS(poServer, poMostrar);

        JTFORMAUXILIARESGRUPOS loConsulta = new JTFORMAUXILIARESGRUPOS(poServer);
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
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