/*
* JTEEGUIXMENSAJESSEND.java
*
* Creado el 8/9/2012
*/

package utilesGUIxAvisos.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import archivosPorWeb.comun.JFichero;
import java.io.File;
import java.util.HashMap;
import utiles.JCadenas;
import utiles.JDateEdu;
import utilesDoc.tablasExtend.JTEEDOCUMENTOS;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.*;
import utilesGUIxAvisos.tablas.JTGUIXMENSAJESBD;

public class JTEEGUIXMENSAJESBD extends JTGUIXMENSAJESBD {
    private static final long serialVersionUID = 1L;
    public static final String mcsURLDOC = "doc://";
    public static final String[] masCaption = JDatosGenerales.getTextosForms().getCaptions(msCTabla, masNombres);
    public static final int mclLEIDO=1;
    public static final int mclNOLEIDOYCLASIFICADO=2;
    public static final int mclLEIDOYCLASIFICADO=3;
    protected transient HashMap moListaRelaciones = new HashMap();

    public static final String mcsENVIADOS = "Enviados";
    
    /**
     * Crea una instancia de la clase intermedia para la tabla GUIXMENSAJESSEND incluyendole el servidor de datos
     * @param poServidorDatos
     */
    public JTEEGUIXMENSAJESBD(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiCODIGO).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEEGUIXMENSAJESBD getTabla(final String psCODIGO,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXMENSAJESBD loTabla = new JTEEGUIXMENSAJESBD(poServer);
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
            JListDatos.mclTIgual, new int[]{lPosiCODIGO}, new String[]{psCODIGO}) ,false);
        return loTabla;
    }
    public static JTEEGUIXMENSAJESBD getTablaPorIDMen(final String psCODIGO,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXMENSAJESBD loTabla = new JTEEGUIXMENSAJESBD(poServer);
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
            JListDatos.mclTIgual, new int[]{lPosiIDMENSAJE}, new String[]{psCODIGO}) ,false);
        return loTabla;
    }


    public static JTEEGUIXMENSAJESBD getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGO),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(JDatosGenerales poDatosGenerales) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMGUIXMENSAJESBD.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMGUIXMENSAJESBD.lPosiASUNTO,
            JTFORMGUIXMENSAJESBD.lPosiEMAILTO,
            JTFORMGUIXMENSAJESBD.lPosiFECHA,
            JTFORMGUIXMENSAJESBD.lPosiADJUNTOS
            };
        loParam.masTextosDescripciones = new String[]{
             "",
             JTFORMGUIXMENSAJESBD.getFieldEstatico(JTFORMGUIXMENSAJESBD.lPosiEMAILTO).getCaption(),
             JTFORMGUIXMENSAJESBD.getFieldEstatico(JTFORMGUIXMENSAJESBD.lPosiFECHA).getCaption(),
             ""
        };

//        loParam.moControlador = new JT2GUIXMENSAJESSEND(poDatosGenerales);

        JTFORMGUIXMENSAJESBD loConsulta = new JTFORMGUIXMENSAJESBD(poDatosGenerales.getServer());
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

    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();
            }
            if("ultimomensaje".equalsIgnoreCase(psTabla)){
                
            }
        
    }
    public IPanelControlador getControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
        cargar(psTabla, poMostrar);
        return (IPanelControlador) moListaRelaciones.get(psTabla);
    }
    @Override
    public IResultado guardar() throws Exception{
        //se comprueba antes de guardar la clave pq despues de 
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave =  isMismaClave();
        //comprobamos las tablas relacionadas
        comprobarClaveCargar(lbIsMismaClave);
        JMensaje loMenOriginal = (JMensaje) moListaRelaciones.get("ultimomensaje");
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));
        
        if(loResult.getBien()) {
            //si estaba en modo nuevo, si todo bien se queda en modo Nada/editar, por lo q hay
            //q actualizar el codigo relacionado
            if(lbIsMismaClave){
                msCodigoRelacionado = getClave();
            }
            
            if(!getADJUNTOS().isVacio()){
                StringBuilder lsAdjuntos = new StringBuilder();
                //si tiene adjuntos los subimos al gestor documental
                for(String ls : JFilaDatosDefecto.moArrayDatos(getADJUNTOS().getString(), ';')){
                    File loFile = new File(ls);
                    JTEEDOCUMENTOS loDoc = new JTEEDOCUMENTOS(moList.moServidor);
                    loDoc.addNew();
                    loDoc.valoresDefecto();
                    loDoc.setDatosDefecto(loFile);
                    loDoc.getGRUPO().setValue(msCTabla);
                    loDoc.getGRUPOIDENT().setValue("m"+getCODIGO().getString());
                    IResultado loResultDoc = loDoc.guardar(null, false);
                    if(!loResultDoc.getBien()){
                        throw new Exception(loResultDoc.getMensaje());
                    }
                    lsAdjuntos.append(mcsURLDOC);
                    lsAdjuntos.append(loDoc.getFichero().getPath());
                    lsAdjuntos.append(';');
                }
                getADJUNTOS().setValue(lsAdjuntos.toString());
                loResult.addResultado(moList.update(true));
            }
            if(!getGRUPO().isVacio() && JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCRM()!=null){
                JMensaje loMenDestino = getMensaje();
                
                if(loMenOriginal!=null){
                    for( HashMap.Entry<String, Object> loEntr :loMenOriginal.getAtributos().entrySet()){
                        if(loMenDestino.getAtributos().get(loEntr.getKey())==null){
                           loMenDestino.getAtributos().put(loEntr.getKey(), loEntr.getValue());
                        }
                    }
                }
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCRM().guardarNotaCorreo(loMenDestino, moList.moServidor);
            }
        }
      
        return loResult;
    }
    public static File getFileDeAdjunto(String psAdjunto) throws Exception{
        if(psAdjunto.startsWith(JTEEGUIXMENSAJESBD.mcsURLDOC)){
            psAdjunto=psAdjunto.substring(JTEEGUIXMENSAJESBD.mcsURLDOC.length());
            File loFile = new File(psAdjunto);
            loFile = loFile.getParentFile();
            loFile.mkdirs();
            JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivosLocal()
                    .copiarA(
                        JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos()
                        , new JFichero(psAdjunto, "", false, 0, new JDateEdu())
                        , new JFichero(psAdjunto, "", false, 0, new JDateEdu())
                    );

        }
        return new File(psAdjunto);
    }
    public IResultado borrar() throws Exception {
        JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCRM().borrarNotaCorreo(this);
        IResultado loResult = moList.borrar(true);
        return loResult;
    }
    
    public JMensaje getMensaje(){
        JMensaje loMensaje = new JMensaje();
        loMensaje.setEmailFrom(getEMAILFROM().getString());
        for(String ls : JFilaDatosDefecto.moArrayDatos(getEMAILTO().getString(), ';')){
            loMensaje.addEmailTO(ls);
        }
        for(String ls : JFilaDatosDefecto.moArrayDatos(getEMAILCC().getString(), ';')){
            loMensaje.addEmailCC(ls);
        }
        for(String ls : JFilaDatosDefecto.moArrayDatos(getEMAILBCC().getString(), ';')){
            loMensaje.addEmailBCC(ls);
        }
        for(String ls : JFilaDatosDefecto.moArrayDatos(getADJUNTOS().getString(), ';')){
            loMensaje.addFicheroAdjunto(ls);
        }
        loMensaje.setAsunto(getASUNTO().getString());
        loMensaje.setTexto(getTEXTO().getString());

        loMensaje.setIdMensaje(getIDMENSAJE().getString());
        loMensaje.setGrupo(getGRUPO().getString());
        loMensaje.setUsuario(getUSUARIO().getString());
        loMensaje.getAtributos().put(JTEEGUIXMENSAJESBD.msCTabla,getCODIGO().getString());
        return loMensaje;
    }
    
    public void setMensaje(JMensaje poMensaje) throws Exception{
        
        getIDMENSAJE().setValue(poMensaje.getIdMensaje());
        getEMAILFROM().setValue(poMensaje.getEmailFrom());
        getASUNTO().setValue(poMensaje.getAsunto());
        getTEXTO().setValue(poMensaje.getTexto());
        StringBuilder lasAdj = new StringBuilder();
        for(int i = 0 ; i < poMensaje.getFicheroAdjunto().size(); i++){
            lasAdj.append(poMensaje.getFicheroAdjunto().get(i));
            lasAdj.append(';');
        }
        getADJUNTOS().setValue(lasAdj.toString());
        StringBuilder lasTO = new StringBuilder();
        for(int i = 0 ; i < poMensaje.getEmailTO().size(); i++){
            lasTO.append(poMensaje.getEmailTO().get(i));
            lasTO.append(';');
        }
        getEMAILTO().setValue(lasTO.toString());
        StringBuilder lasCC = new StringBuilder();
        for(int i = 0 ; i < poMensaje.getEmailCC().size(); i++){
            lasCC.append(poMensaje.getEmailCC().get(i));
            lasCC.append(';');
        }
        getEMAILCC().setValue(lasCC.toString());
        StringBuilder lasBCC = new StringBuilder();
        for(int i = 0 ; i < poMensaje.getEmailBCC().size(); i++){
            lasBCC.append(poMensaje.getEmailBCC().get(i));
            lasBCC.append(';');
        }
        getEMAILBCC().setValue(lasBCC.toString());
                
        getUSUARIO().setValue(poMensaje.getUsuario());
        getGRUPO().setValue(poMensaje.getGrupo());
        getFECHA().setValue(poMensaje.getFecha());
        if(!getGRUPO().isVacio()){
            getESTADO().setValue(mclNOLEIDOYCLASIFICADO);
        }
        cargar("ultimomensaje", null);
        moListaRelaciones.put("ultimomensaje", poMensaje);
    }
    public String getIdentificadorDELFOLDER(){
        return getIdentificadorDELFOLDER(getFOLDER().getString());
    }

    public static String getIdentificadorFOLDER(JGUIxAvisosCorreo poAvisosCorreo, String psCarpeta) {
        return poAvisosCorreo.getIdentificador()+"/"+psCarpeta;
    }
 
    public static String getIdentificadorDELFOLDER(String psCampoFolder){
        if(!JCadenas.isVacio(psCampoFolder)){
            return JFilaDatosDefecto.moArrayDatos(psCampoFolder+"/", '/')[0];
        } else {
            return "";
        }
    } 
}