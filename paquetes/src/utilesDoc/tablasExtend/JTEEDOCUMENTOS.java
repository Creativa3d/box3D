/*
* JTEEDOCUMENTOS.java
*
* Creado el 19/10/2016
*/

package utilesDoc.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import archivosPorWeb.comun.JFichero;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import utiles.JArchivo;
import utiles.JCadenas;
import utiles.JDateEdu;
import utilesDoc.JDocDatosGeneralesModelo;
import utilesDoc.consultas.*;
import utilesDoc.tablas.*;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.*;

public class JTEEDOCUMENTOS extends JTDOCUMENTOS {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JDocDatosGeneralesModelo.getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla DOCUMENTOS incluyendole el servidor de datos
     */
    public JTEEDOCUMENTOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiCODIGODOCUMENTO).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEEDOCUMENTOS getTabla(final String psGRUPO,final String psGRUPOIDENT,final String psCODIGODOCUMENTO,final IServerServidorDatos poServer) throws Exception {
        JTEEDOCUMENTOS loTabla = new JTEEDOCUMENTOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psGRUPO,psGRUPOIDENT,psCODIGODOCUMENTO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psGRUPO,psGRUPOIDENT,psCODIGODOCUMENTO}) ,false);
        }
        return loTabla;
    }
    public static JTEEDOCUMENTOS getTablaNombre(final String psGRUPO,final String psGRUPOIDENT,final String psNombre,final IServerServidorDatos poServer) throws Exception {
        JTEEDOCUMENTOS loTabla = new JTEEDOCUMENTOS(poServer);
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual
                    , new int[]{lPosiGRUPO, lPosiGRUPOIDENT, lPosiNOMBRE}
                    , new String[]{psGRUPO, psGRUPOIDENT, psNombre})
                    , false);
        return loTabla;
    }
    public static JTEEDOCUMENTOS getTabla(final String psGRUPO,final String psGRUPOIDENT,final IServerServidorDatos poServer) throws Exception {
        JTEEDOCUMENTOS loTabla = new JTEEDOCUMENTOS(poServer);
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
            JListDatos.mclTIgual
            , new int[]{
                lPosiGRUPO, lPosiGRUPOIDENT
            }
            , new String[]{psGRUPO,psGRUPOIDENT}) ,false);
        return loTabla;
    }

    public static JTEEDOCUMENTOS getTablaOtroIden(final String psGRUPO,final String psGRUPOIDENT,final String psOtroIden,final IServerServidorDatos poServer) throws Exception {
        JTEEDOCUMENTOS loTabla = new JTEEDOCUMENTOS(poServer);
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
            JListDatos.mclTIgual
            , new int[]{
                lPosiGRUPO, lPosiGRUPOIDENT, lPosiIDENTIFICADOROTRO
            }
            , new String[]{psGRUPO,psGRUPOIDENT, psOtroIden}) ,false);
        return loTabla;
    }

    public static JTEEDOCUMENTOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiGRUPO),poFila.msCampo(lPosiGRUPOIDENT),poFila.msCampo(lPosiCODIGODOCUMENTO),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMDOCUMENTOS.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMDOCUMENTOS.lPosiNOMBRE,
            JTFORMDOCUMENTOS.lPosiDESCRIPCION
            };
        loParam.masTextosDescripciones = null;


        JTFORMDOCUMENTOS loConsulta = new JTFORMDOCUMENTOS(poServer);
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
   public void valoresDefecto() throws Exception {   
   }

    @Override
    public void validarCampos() throws Exception {
        super.validarCampos();
        getFECHAMODIF().setValue( new JDateEdu() );
        getUSUARIO().setValue(JGUIxConfigGlobalModelo.getInstancia().getUsuario() );
        
                    
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
            
            if((JTEEDOCUMCLASIF.msCTabla+getCODCLASIFNombre()).equalsIgnoreCase(psTabla)){
                JTEEDOCUMCLASIF loTabla = (JTEEDOCUMCLASIF)moListaRelaciones.get(psTabla);
                if(loTabla==null){
                    loTabla = JTEEDOCUMCLASIF.getTabla(getCODCLASIF().getString(),  moList.moServidor);
                    moListaRelaciones.put(psTabla, loTabla);
                }
            } else 

            if((JTEEDOCUMTIPOS.msCTabla+getCODTIPODOCUMENTONombre()).equalsIgnoreCase(psTabla)){
                JTEEDOCUMTIPOS loTabla = (JTEEDOCUMTIPOS)moListaRelaciones.get(psTabla);
                if(loTabla==null){
                    loTabla = JTEEDOCUMTIPOS.getTabla(getCODTIPODOCUMENTO().getString(),  moList.moServidor);
                    moListaRelaciones.put(psTabla, loTabla);
                }
            } else if ("FILE".equalsIgnoreCase(psTabla)){
                File loResult = (File) moListaRelaciones.get("FILE");

                if(loResult==null){
                    if(getRUTA().isVacio()){
                        JFichero loFic;
                        loFic = getFichero();
                        loFic=JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos().getFichero(loFic);

                        File loTemp = File.createTempFile(
                                "doc-"+getPathSoloGrupo()+"-"+getPathSoloGrupoIdent()+"-"+getNombreSinRaros()+"-"
                                , getExtensionCalculado()
                                );
                        OutputStream loOut = new FileOutputStream(loTemp);
                        InputStream loIn = JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos().getFlujoEntrada(loFic);
                        JArchivo.guardarArchivo(loIn, loOut);
                        loResult=loTemp;
                    } else{
                        loResult=new File(getRUTA().getString());
                    }
                    moListaRelaciones.put("FILE", loResult);
                }                
            } 
//            else 
//
//            cargarControlador(psTabla, poMostrar);
//        
    }
    public IPanelControlador getControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
        cargar(psTabla, poMostrar);
        return (IPanelControlador) moListaRelaciones.get(psTabla);
    }
    public JTEEDOCUMCLASIF getDOCUMCLASIFCODCLASIF() throws Exception{
        cargar(JTEEDOCUMCLASIF.msCTabla+ getCODCLASIFNombre(), null);
        return (JTEEDOCUMCLASIF) moListaRelaciones.get(JTEEDOCUMCLASIF.msCTabla + getCODCLASIFNombre());
    }        

    public JTEEDOCUMTIPOS getDOCUMTIPOSCODTIPODOCUMENTO() throws Exception{
        cargar(JTEEDOCUMTIPOS.msCTabla+ getCODTIPODOCUMENTONombre(), null);
        return (JTEEDOCUMTIPOS) moListaRelaciones.get(JTEEDOCUMTIPOS.msCTabla + getCODTIPODOCUMENTONombre());
    }        

    public IResultado guardar(final IPanelControlador poDoc) throws Exception{
        return guardar(poDoc, JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().isSubidaSegundoPlano());
    }
    public IResultado guardar(final IPanelControlador poDoc, final boolean pbSegundoPlano) throws Exception{
        return guardar(poDoc, pbSegundoPlano, true); 
    }
    public IResultado guardar(final IPanelControlador poDoc, final boolean pbSegundoPlano, final boolean pbMensajeAlTerminar) throws Exception{
        int lTipoModif = moList.getModoTabla();
        final String lsRuta = getRUTA().getString();
        getRUTA().setValue("");
        //se comprueba antes de guardar la clave pq despues de 
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave =  isMismaClave();
        //comprobamos las tablas relacionadas
        comprobarClaveCargar(lbIsMismaClave);
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));
        //se hace el path aqui pq el codigodocumento es el ult. + 1
        if(loResult.getBien()){
            getIDENTIFICADOREXTERNO().setValue(gePathCompletoCalculado());
            loResult.addResultado(moList.update(true));
        }
        if(loResult.getBien() && !JCadenas.isVacio(lsRuta)) {
            final IFilaDatos loFila = getFields().moFilaDatos();
//            loFila.setTipoModif(lTipoModif);
            final JFichero loFicheroDestino = getFichero();
            final JFichero loFicheroOrigen = JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivosLocal().getFichero(
                    new JFichero(new File(lsRuta))
            );
            JProcesoAccionAbstracX loAccion =  new JProcesoAccionAbstracX() {
                @Override
                public String getTitulo() {
                    return "Subiendo documento " +lsRuta ;
                }

                @Override
                public int getNumeroRegistros() {
                    return 1;
                }

                @Override
                public void procesar() throws Throwable {
                    JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos()
                            .crearCarpeta(new JFichero("", getPathSoloGrupo(), true, 0, null));
                    JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos()
                            .crearCarpeta(new JFichero(getPathSoloGrupo(), getPathSoloGrupoIdent(), true, 0, null));
                    
                    JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos().copiarA(
                            JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivosLocal()
                            , loFicheroOrigen, loFicheroDestino);

                    if(poDoc!=null){
                        poDoc.datosactualizados(loFila);
                    }
                }

                @Override
                public void mostrarMensaje(String psMensaje) {
                    if(pbMensajeAlTerminar){
                        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeFlotante(null, "<html>" + loFicheroOrigen.getNombre() + "<br> guardada correctamente</html>");
                    }
                }
            };
            if(!pbSegundoPlano){
                try {
                    loAccion.procesar();
                } catch (Throwable ex) {
                    throw new Exception(ex);
                }
            }else{
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto()
                    .getThreadGroup().addProcesoYEjecutar(loAccion);
            }
            
            
            
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
    public JFichero getFichero() throws Exception{
        return new JFichero(
                    getPathSoloCalculado()
                    , getNombreSoloCalculado()
                    , false
                    ,0
                    ,new JDateEdu()
            );
    }
    public JFichero getFicheroRuta() throws Exception{
        return new JFichero(
                    new File(getRUTA().getString())
            );
    }
    public IResultado borrar() throws Exception {
        JFichero loFic = getFichero();
        IResultado loResult = moList.borrar(true);
        if(loResult.getBien()){
            JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getServidorArchivos().borrar(loFic);
        }
        return loResult;
    }
    
    public String getPathSoloGrupo(){
        return getGRUPO().getString();
    }

    public String getPathSoloGrupoIdent(){
        return getGRUPOIDENT().getString() ;
    }
    
    public String getPathSoloCalculado() throws Exception {
        return getPathSoloGrupo()
                + "/" 
                + getPathSoloGrupoIdent()
                + "/";
    }
    public String getNombreSoloCalculado() throws Exception {
        return getNombreSinRaros()
                + getExtensionCalculado();
    }
    public String getNombreSinRaros() throws Exception {
        return getCODIGODOCUMENTO().getString();
    }
    public String getExtensionCalculado() throws Exception{
        return "."
                + getDOCUMTIPOSCODTIPODOCUMENTO().getEXTENSION().getString();
    }
    public String gePathCompletoCalculado() throws Exception {
        return getPathSoloCalculado() + getNombreSoloCalculado();
    }
    
    public void setDatosDefecto(File poFile) throws Exception{
        JFichero loFichero = new JFichero(poFile);
        getFECHA().setValue( loFichero.getDateEdu() );
        getUSUARIO().setValue(JGUIxConfigGlobalModelo.getInstancia().getUsuario() );
        getNOMBRE().setValue(loFichero.getNombre());
        getRUTA().setValue(loFichero.getPath());
        getCODTIPODOCUMENTO().setValue(JTEEDOCUMTIPOS.getCodigoPorExtension(moList.moServidor, poFile));
        
        
    }
    
    public File getFicheroLocal() throws Exception{
        cargar("FILE", null);
        return (File) moListaRelaciones.get("FILE");
    }

    public void setFormatoCalculado(String psFormato) throws Exception {
        if(!JCadenas.isVacio(psFormato)){
            getCODTIPODOCUMENTO().setValue(JTEEDOCUMTIPOS.getCodigoPorExtension(moList.moServidor, psFormato));
        }
    }
    
}