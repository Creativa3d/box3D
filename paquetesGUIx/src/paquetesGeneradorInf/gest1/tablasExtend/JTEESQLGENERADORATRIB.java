/*
* JTEESQLGENERADORATRIB.java
*
* Creado el 9/9/2013
*/

package paquetesGeneradorInf.gest1.tablasExtend;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import paquetesGeneradorInf.gest1.tablas.*;
import paquetesGeneradorInf.gest1.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import paquetesGeneradorInf.gest1.consultas.*;
import paquetesGeneradorInf.gest1.tablasControladoras.*;
import paquetesGeneradorInf.gest1.forms.*;

public class JTEESQLGENERADORATRIB extends JTSQLGENERADORATRIB {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JDatosGeneralesP.getDatosGenerales().getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla SQLGENERADORATRIB incluyendole el servidor de datos
     */
    public JTEESQLGENERADORATRIB(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEESQLGENERADORATRIB getTabla(final String psCODIGOSQLGENERADOR,final String psATRIBUTODEF,final IServerServidorDatos poServer) throws Exception {
        JTEESQLGENERADORATRIB loTabla = new JTEESQLGENERADORATRIB(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOSQLGENERADOR,psATRIBUTODEF});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOSQLGENERADOR,psATRIBUTODEF}) ,false);
        }
        return loTabla;
    }


    public static JTEESQLGENERADORATRIB getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOSQLGENERADOR),poFila.msCampo(lPosiATRIBUTODEF),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMSQLGENERADORATRIB.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMSQLGENERADORATRIB.lPosiVALOR
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMSQLGENERADORATRIB.getFieldEstatico(JTFORMSQLGENERADORATRIB.lPosiVALOR).getCaption()
            };

        loParam.moControlador = new JT2SQLGENERADORATRIB(poServer, poMostrar);

        JTFORMSQLGENERADORATRIB loConsulta = new JTFORMSQLGENERADORATRIB(poServer);
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