/*
* JTEEDOCUMTIPOS.java
*
* Creado el 19/10/2016
*/

package utilesDoc.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.io.File;
import java.util.HashMap;
import utilesDoc.JDocDatosGeneralesModelo;
import utilesDoc.consultas.*;
import utilesDoc.tablas.*;
import utilesGUIx.formsGenericos.*;

public class JTEEDOCUMTIPOS extends JTDOCUMTIPOS {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JDocDatosGeneralesModelo.getTextosForms().getCaptions(msCTabla, masNombres);

    public static final int mclJPG=1;    
    public static final int mclpng=2;
    public static final int mclgif=3;
    public static final int mcltif=4;
    public static final int mclpdf=5;
    public static final int mcldoc=6;
    public static final int mcldocx=7;

    protected transient HashMap moListaRelaciones = new HashMap();
    /**
     * Crea una instancia de la clase intermedia para la tabla DOCUMTIPOS incluyendole el servidor de datos
     */
    public JTEEDOCUMTIPOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return true;
    }
    public static JTEEDOCUMTIPOS getTabla(final String psCODIGOTIPODOC,final IServerServidorDatos poServer) throws Exception {
        JTEEDOCUMTIPOS loTabla = new JTEEDOCUMTIPOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOTIPODOC});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOTIPODOC}) ,false);
        }
        return loTabla;
    }


    public static JTEEDOCUMTIPOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOTIPODOC),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMDOCUMTIPOS.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMDOCUMTIPOS.lPosiEXTENSION
            };
        loParam.masTextosDescripciones = null;


        JTFORMDOCUMTIPOS loConsulta = new JTFORMDOCUMTIPOS(poServer);
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
    @Override
   public void valoresDefecto() throws Exception {   
   }
    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){
                moListaRelaciones = null;
                moListaRelaciones = new HashMap();
            }
    }
//    protected void cargarControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
//        IPanelControlador loAux =
//                        JDocDatosGeneralesModelo.getControlador(
//                            moList.moServidor,
//                            poMostrar,
//                            psTabla,
//                            moList.msTabla,
//                            moList.getFields().moFilaDatos()
//                            );
//        moListaRelaciones.put(psTabla, loAux);
//    }
    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();
            }
            
//            cargarControlador(psTabla, poMostrar);
        
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
    
    public static String getCodigoPorExtension(IServerServidorDatos poServer, String psExt) throws Exception{
        String lsT=String.valueOf(mclJPG);
        JTEEDOCUMTIPOS loTipos = new JTEEDOCUMTIPOS(poServer);
        loTipos.recuperarTodosNormal(getPasarACache());
        
        if(loTipos.moList.buscar(JListDatos.mclTIgual, lPosiEXTENSION, psExt)){
            lsT=loTipos.getCODIGOTIPODOC().getString();
        }
        return lsT;
        
    }
    public static String getCodigoPorExtension(IServerServidorDatos poServer, File poFile) throws Exception{
        String ls = poFile.getName();
        int lIndex= ls.lastIndexOf('.');
        if(lIndex>0){
            return getCodigoPorExtension (poServer, ls.substring(lIndex+1));
        } else {
            return String.valueOf(mclJPG);
        }
    }
    public static String [] getExtensionesDoc(IServerServidorDatos poServer) throws Exception{
        return getExtensionesDoc(poServer, false);
    }
    public static String [] getExtensionesImagenes(IServerServidorDatos poServer) throws Exception{
        return getExtensionesDoc(poServer, true);
    }
    public static String [] getExtensionesDoc(IServerServidorDatos poServer, boolean pbSoloImagen) throws Exception{
        JTEEDOCUMTIPOS loTipo = new JTEEDOCUMTIPOS(poServer);
        loTipo.recuperarTodosNormal(getPasarACache());
        if(pbSoloImagen){
            loTipo.getList().getFiltro().addCondicionAND(JListDatos.mclTIgual, loTipo.lPosiIMAGENSN, JListDatos.mcsTrue);
            loTipo.getList().filtrar();
        }
        String[] lsString = new String[loTipo.moList.size()];
        if(loTipo.moveFirst()){
            do{
                lsString[loTipo.getList().getIndex()]=loTipo.getEXTENSION().getString();
            }while(loTipo.moveNext());
        }
        return lsString;
    }
}