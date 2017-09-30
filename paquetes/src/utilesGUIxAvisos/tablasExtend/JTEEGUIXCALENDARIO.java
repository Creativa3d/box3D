/*
* JTEEGUIXCALENDARIO.java
*
* Creado el 15/3/2012
*/

package utilesGUIxAvisos.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.util.HashMap;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.*;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.*;
import utilesGUIxAvisos.tablas.JTGUIXCALENDARIO;

public class JTEEGUIXCALENDARIO extends JTGUIXCALENDARIO {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JDatosGenerales.getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla GUIXCALENDARIO incluyendole el servidor de datos
     */
    public JTEEGUIXCALENDARIO(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEEGUIXCALENDARIO getTabla(final String psCALENDARIO,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXCALENDARIO loTabla = new JTEEGUIXCALENDARIO(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCALENDARIO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCALENDARIO}) ,false);
        }
        return loTabla;
    }


    public static JTEEGUIXCALENDARIO getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCALENDARIO),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(JDatosGenerales poDatosGenerales) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMGUIXCALENDARIO.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMGUIXCALENDARIO.lPosiNOMBRE
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMGUIXCALENDARIO.getFieldEstatico(JTFORMGUIXCALENDARIO.lPosiNOMBRE).getCaption()
            };

//        loParam.moControlador = new JT2GUIXCALENDARIO(poDatosGenerales);

        JTFORMGUIXCALENDARIO loConsulta = new JTFORMGUIXCALENDARIO(poDatosGenerales.getServer());
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
   public void valoresDefecto() throws Exception {   
       if(getCALENDARIO().isVacio()){
           getCALENDARIO().setValue(new JDateEdu().msFormatear("yyyyMMddHHmmss") + Math.round(Math.random()* 100));
       }
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
        
        if(loResult.getBien()) {
            JDatosGenerales.sincronizar(this);
            
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
        IFilaDatos loFila = moList.moFila();
        IResultado loResult = moList.borrar(true);
        if(loResult.getBien()){
            try{
                JTEEGUIXCALENDARIO loC = new JTEEGUIXCALENDARIO(moList.moServidor);
                loC.moList.add(loFila);
                loC.moList.moveFirst();
                JDatosGenerales.sincronizarBorrar(loC);
            }catch(ClassCastException e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }catch(Throwable e1){
                JDepuracion.anadirTexto(getClass().getName(), e1);
            }
            
        }
        return loResult;
    }
}