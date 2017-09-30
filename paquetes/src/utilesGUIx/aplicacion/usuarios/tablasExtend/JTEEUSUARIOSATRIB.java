/*
* JTEEUSUARIOSATRIB.java
*
* Creado el 2/11/2016
*/

package utilesGUIx.aplicacion.usuarios.tablasExtend;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.usuarios.consultas.JTFORMUSUARIOSATRIB;
import utilesGUIx.aplicacion.usuarios.tablas.*;
import utilesGUIx.formsGenericos.*;

public class JTEEUSUARIOSATRIB extends JTUSUARIOSATRIB {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla USUARIOSATRIB incluyendole el servidor de datos
     */
    public JTEEUSUARIOSATRIB(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEEUSUARIOSATRIB getTabla(final String psCODIGOVEHICREFO,final String psCODIGOINSPECDEF,final IServerServidorDatos poServer) throws Exception {
        JTEEUSUARIOSATRIB loTabla = new JTEEUSUARIOSATRIB(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOVEHICREFO,psCODIGOINSPECDEF});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOVEHICREFO,psCODIGOINSPECDEF}) ,false);
        }
        return loTabla;
    }


    public static JTEEUSUARIOSATRIB getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOUSUARIO),poFila.msCampo(lPosiCODIGOUSUARIOATRIBDEF),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMUSUARIOSATRIB.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMUSUARIOSATRIB.lPosiVALOR
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMUSUARIOSATRIB.getFieldEstatico(JTFORMUSUARIOSATRIB.lPosiVALOR).getCaption()
            };

        loParam.moControlador = null;

        JTFORMUSUARIOSATRIB loConsulta = new JTFORMUSUARIOSATRIB(poServer);
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