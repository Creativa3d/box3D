/*
* JTEEGUIXPRIORIDAD.java
*
* Creado el 18/2/2012
*/

package utilesGUIxAvisos.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.util.HashMap;
import utilesGUIx.formsGenericos.*;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.*;
import utilesGUIxAvisos.tablas.JTGUIXEVENTOSPRIORIDAD;

public class JTEEGUIXEVENTOSPRIORIDAD extends JTGUIXEVENTOSPRIORIDAD {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JDatosGenerales.getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla GUIXPRIORIDAD incluyendole el servidor de datos
     */
    public JTEEGUIXEVENTOSPRIORIDAD(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiGUIXPRIORIDAD).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return true;
    }
    public static JTEEGUIXEVENTOSPRIORIDAD getTabla(final String psGUIXPRIORIDAD,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXEVENTOSPRIORIDAD loTabla = new JTEEGUIXEVENTOSPRIORIDAD(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psGUIXPRIORIDAD});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psGUIXPRIORIDAD}) ,false);
        }
        return loTabla;
    }


    public static JTEEGUIXEVENTOSPRIORIDAD getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiGUIXPRIORIDAD),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(JDatosGenerales poDatosGenerales) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMGUIXEVENTOSPRIORIDAD.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMGUIXEVENTOSPRIORIDAD.lPosiNOMBRE
            };
        loParam.masTextosDescripciones = null;

//        loParam.moControlador = new JT2GUIXEVENTOSPRIORIDAD(poDatosGenerales);

        JTFORMGUIXEVENTOSPRIORIDAD loConsulta = new JTFORMGUIXEVENTOSPRIORIDAD(poDatosGenerales.getServer());
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
    public void validarCampos() throws Exception {
        if(moList.getModoTabla()==JListDatos.mclNuevo && !getGUIXPRIORIDAD().isVacio()){
            if(JTEEGUIXEVENTOSPRIORIDAD.getTabla(getGUIXPRIORIDAD().getString(), moList.moServidor).moveFirst()){
                throw new Exception("Ya existe una prioridad con el mismo orden ");
            }
        }
    }
   public void valoresDefecto() throws Exception {   
       getCOLOR().setValue(Math.random() * -10000000);
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