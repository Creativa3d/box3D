/*
* JTEEMUNICIPIOS.java
*
* Creado el 5/10/2015
*/

package paquetesguifx;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JTEEMUNICIPIOS extends JTMUNICIPIOS implements IConsulta {
    private static final long serialVersionUID = 1L;
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla MUNICIPIOS incluyendole el servidor de datos
     */
    public JTEEMUNICIPIOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masNombres);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return true;
    }
    public static JTEEMUNICIPIOS getTabla(String psCodProv, String psCodHaci,final IServerServidorDatos poServer) throws Exception {
        JTEEMUNICIPIOS loTabla = new JTEEMUNICIPIOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCodProv, psCodHaci});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{}) ,false);
        }
        return loTabla;
    }


    public static JTEEMUNICIPIOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODPROV),
                 poFila.msCampo(lPosiCODHACIENDA),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTEEMUNICIPIOS.malCamposPrincipales;
        loParam.mbConDatos=true;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            };
        loParam.masTextosDescripciones = new String[]{
            };

        loParam.moControlador = new JT2MUNICIPIOS(poServer, poMostrar);

        JTEEMUNICIPIOS loConsulta = new JTEEMUNICIPIOS(poServer);
        loConsulta.recuperarTodosNormalCache();
        
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
        loResult.addResultado(moList.update(false));
        
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
        IResultado loResult = moList.borrar(false);
        return loResult;
    }

    @Override
    public void addFilaPorClave(IFilaDatos poFila) throws Exception {
    }

    @Override
    public boolean getPasarCache() {
        return true;
    }
}