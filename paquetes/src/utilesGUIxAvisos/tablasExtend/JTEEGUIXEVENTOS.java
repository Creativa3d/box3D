/*
* JTEEGUIXEVENTOS.java
*
* Creado el 3/11/2011
*/

package utilesGUIxAvisos.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.JFieldDef;
import java.util.HashMap;
import utiles.CadenaLarga;
import utiles.CadenaLargaOut;
import utiles.JDateEdu;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMGuardar;
import utiles.xml.dom.SAXBuilder;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOS;
import utilesGUIxAvisos.tablas.JTGUIXEVENTOS;

public class JTEEGUIXEVENTOS extends JTGUIXEVENTOS {
    private static final long serialVersionUID = 1L;
    public static String[] masCaption = JDatosGenerales.getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();
    public static final String mcsRepeticionesNumero="RepeticionesNumero";
    public static final String mcsRepeticionesTipo="RepeticionesTipo";
    public static final String mcsRepeticionesTipoUNICO="UNICO";
    public static final String mcsRepeticionesTipoDIA="DIARIO";
    public static final String mcsRepeticionesTipoSEMANAS="SEMANAL";
    public static final String mcsRepeticionesTipoMESES="MENSUAL";
    public static final String mcsRepeticionesTipoANYO="ANUAL";
    public static final String mcsREPE_NUMERO_DEFECTO="20";
    
    public static final int mclSoloEste = 0;
    public static final int mclSiguientes = 1;
    public static final int mclTodos = 2;
    
    /**
     * Crea una instancia de la clase intermedia para la tabla GUIXEVENTOS incluyendole el servidor de datos
     */
    public JTEEGUIXEVENTOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiCODIGO).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEEGUIXEVENTOS getTabla(final String psCALENDARIO,final String psCODIGO,final IServerServidorDatos poServer) throws Exception {
        JTEEGUIXEVENTOS loTabla = new JTEEGUIXEVENTOS(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCALENDARIO, psCODIGO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCALENDARIO, psCODIGO}) ,false);
        }
        return loTabla;
    }

    public static JTEEGUIXEVENTOS getTablaIdentExt(String psExt, IServerServidorDatos poServer) throws Exception {
        JTEEGUIXEVENTOS loTabla = new JTEEGUIXEVENTOS(poServer);
        loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, lPosiIDENTIFICADOREXTERNO, psExt) ,false);
        return loTabla;
    }

    public static JTEEGUIXEVENTOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCALENDARIO),
                 poFila.msCampo(lPosiCODIGO),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(JDatosGenerales poDatosGenerales) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMGUIXEVENTOS.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMGUIXEVENTOS.lPosiFECHADESDE,
            JTFORMGUIXEVENTOS.lPosiFECHAHASTA,
            JTFORMGUIXEVENTOS.lPosiNOMBRE,
            JTFORMGUIXEVENTOS.lPosiTEXTO,
            JTFORMGUIXEVENTOS.lPosiREPETICION,
            JTFORMGUIXEVENTOS.lPosiGRUPO
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMGUIXEVENTOS.getFieldEstatico(JTFORMGUIXEVENTOS.lPosiFECHADESDE).getCaption(),
             JTFORMGUIXEVENTOS.getFieldEstatico(JTFORMGUIXEVENTOS.lPosiFECHAHASTA).getCaption(),
             JTFORMGUIXEVENTOS.getFieldEstatico(JTFORMGUIXEVENTOS.lPosiNOMBRE).getCaption(),
             JTFORMGUIXEVENTOS.getFieldEstatico(JTFORMGUIXEVENTOS.lPosiTEXTO).getCaption(),
             JTFORMGUIXEVENTOS.getFieldEstatico(JTFORMGUIXEVENTOS.lPosiREPETICION).getCaption(),
             JTFORMGUIXEVENTOS.getFieldEstatico(JTFORMGUIXEVENTOS.lPosiGRUPO).getCaption()
            };

//        loParam.moControlador = new JT2GUIXEVENTOS(poDatosGenerales);

        JTFORMGUIXEVENTOS loConsulta = new JTFORMGUIXEVENTOS(poDatosGenerales.getServer());
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
   public void valoresDefecto() throws Exception {
       
       JDateEdu loDate = new JDateEdu();
       loDate.add(loDate.mclHoras, 1);
       getFECHADESDE().setValue(loDate.msFormatear("dd/MM/yyyy HH:00"));
       loDate.add(loDate.mclHoras, 1);
       getFECHAHASTA().setValue(loDate.msFormatear("dd/MM/yyyy HH:00"));
       getORIGINALSN().setValue(true);
       
   }

    @Override
    public void validarCampos() throws Exception {
        if(getNOMBRE().isVacio()){
            throw new Exception("El campo nombre es obligatorio");
        }
        if(getFECHADESDE().getDateEdu().compareTo(getFECHAHASTA().getDateEdu()) >0 ){
            throw new Exception("La fecha desde no puede ser superior a la fecha hasta");
        }
        getFECHAMODIFICACION().setValue(new JDateEdu());
        super.validarCampos();
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
    /**
     * Procesa todas las repeticiones
     * plTipoActualizacion 
    mclSoloEste = 0;
    mclSiguientes = 1;
    mclTodos = 2;
     * @param poDatosGenerales
     * @param poEvento
     * @param plTipoActualizacion
     * @param poServer
     * @return 
     * @throws java.lang.Exception
     */
    public static IResultado procesarRepeticiones(JDatosGenerales poDatosGenerales, JTEEGUIXEVENTOS poEvento, int plTipoActualizacion, IServerServidorDatos poServer) throws Exception{
        JResultado loResult = new JResultado("", true);
        boolean lbNuevo = poEvento.getList().getModoTabla()==JListDatos.mclNuevo;
        if(plTipoActualizacion==mclSoloEste || plTipoActualizacion==mclSiguientes || lbNuevo || poEvento.getIDENTIFICADORSERIE().isVacio()){
            poEvento.validarCampos(); 
            loResult.addResultado(poEvento.guardar(poDatosGenerales));
        }
        //identificador serie y evento original
        JTEEGUIXEVENTOS loEventoOriginal = poEvento;
        String lsIdentSerie=poEvento.getClave().replace(JFilaDatosDefecto.mcsSeparacion1, "-");
        if(plTipoActualizacion!=mclSoloEste && !lbNuevo && loResult.getBien()){
            lsIdentSerie = poEvento.getClave().replace(JFilaDatosDefecto.mcsSeparacion1, "-");
            if(!poEvento.getIDENTIFICADORSERIE().isVacio()){
                lsIdentSerie = poEvento.getIDENTIFICADORSERIE().getString();
                JFilaDatosDefecto loFila = new JFilaDatosDefecto(JFilaDatosDefecto.moArrayDatos(poEvento.getIDENTIFICADORSERIE().getString(), '-'));
                loEventoOriginal = JTEEGUIXEVENTOS.getTabla(loFila, poServer);
                loEventoOriginal.moveFirst();
            }
        }
        if(plTipoActualizacion!=mclSoloEste && loResult.getBien()){
            //guardamos evento original cambios
            if(plTipoActualizacion==mclTodos && !poEvento.getIDENTIFICADORSERIE().isVacio()){
                loEventoOriginal.getNOMBRE().setValue(poEvento.getNOMBRE().getString());
                loEventoOriginal.getPRIORIDAD().setValue(poEvento.getPRIORIDAD().getString());
                loEventoOriginal.getREPETICION().setValue(poEvento.getREPETICION().getString());
                loEventoOriginal.getTEXTO().setValue(poEvento.getTEXTO().getString());
                loEventoOriginal.getUSUARIO().setValue(poEvento.getUSUARIO().getString());
                loEventoOriginal.getUSUARIOASIGNADO().setValue(poEvento.getUSUARIOASIGNADO().getString());
                loEventoOriginal.getGRUPO().setValue(poEvento.getGRUPO().getString());
                loEventoOriginal.getIDENTIFICADORSERIE().setValue(poEvento.getIDENTIFICADORSERIE().getString());
                loEventoOriginal.validarCampos();
                loResult.addResultado(loEventoOriginal.guardar(poDatosGenerales));
            }
            //evento original
            if(plTipoActualizacion==mclSiguientes){
                loEventoOriginal = poEvento;
            }
            //eventos existentes
            JTEEGUIXEVENTOS loEventosAModif = new JTEEGUIXEVENTOS(poServer);
            if(!lbNuevo){
                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEEGUIXEVENTOS.lPosiIDENTIFICADORSERIE, lsIdentSerie);
                if(plTipoActualizacion==mclSiguientes){
                    loFiltro.addCondicionAND(JListDatos.mclTMayor, JTEEGUIXEVENTOS.lPosiFECHADESDE, poEvento.getFECHADESDE().getString());
                }
                loEventosAModif.recuperarFiltradosNormal(loFiltro, false);
                loEventosAModif.moList.ordenar(JTEEGUIXEVENTOS.lPosiFECHADESDE);
            }
            //eventos calculados
            JTEEGUIXEVENTOS loEventosCalculados = loEventoOriginal.getEventosAutomaticos();
            
            //1º modificamos todos y añadimos
            if(loEventosCalculados.moveFirst() && loResult.getBien()){
                loEventosAModif.moveFirst();
                do{
                    if(loEventosAModif.getList().size()<=loEventosCalculados.getList().mlIndex ){
                        loEventosAModif.addNew();
                        loEventosAModif.getCALENDARIO().setValue(loEventosCalculados.getCALENDARIO().getString());
                    }
//falla                    
//                    JFieldDefs loCamposModif = loEventosAModif.getFields(); 
//                    for(int i = 0 ; i < loCamposModif.size(); i++){
//                        if(!loCamposModif.get(i).getPrincipalSN()){
//                            loCamposModif.get(i).setValue(loEventosCalculados.getField(i).getString());
//                        }
//                    }
                    loEventosAModif.getNOMBRE().setValue(loEventosCalculados.getNOMBRE().getString());
                    loEventosAModif.getPRIORIDAD().setValue(loEventosCalculados.getPRIORIDAD().getString());
                    loEventosAModif.getREPETICION().setValue(loEventosCalculados.getREPETICION().getString());
                    loEventosAModif.getTEXTO().setValue(loEventosCalculados.getTEXTO().getString());
                    loEventosAModif.getUSUARIO().setValue(loEventosCalculados.getUSUARIO().getString());
                    loEventosAModif.getGRUPO().setValue(loEventosCalculados.getGRUPO().getString());
                    loEventosAModif.getUSUARIOASIGNADO().setValue(loEventosCalculados.getUSUARIOASIGNADO().getString());
                    loEventosAModif.getFECHADESDE().setValue(loEventosCalculados.getFECHADESDE().getString());
                    loEventosAModif.getFECHAHASTA().setValue(loEventosCalculados.getFECHAHASTA().getString());
                    loEventosAModif.getORIGINALSN().setValue(false);
                    loEventosAModif.getIDENTIFICADORSERIE().setValue(lsIdentSerie);
                    loEventosAModif.validarCampos();
                    loResult.addResultado(loEventosAModif.guardar(poDatosGenerales));
                    loEventosAModif.moveNext();
                }while(loEventosCalculados.moveNext() && loResult.getBien());
                loEventosAModif.moveNext();
            }
            //2º borramos sobrantes
            while(loEventosAModif.getList().size()>loEventosCalculados.getList().size() && loResult.getBien()){
                loResult.addResultado(loEventosAModif.borrar(poDatosGenerales));
            }
            
        }
        
        
        return loResult;
    }

    /**
     * Devuelve lista de eventos automáticos 
     *
    */
    public JTEEGUIXEVENTOS getEventosAutomaticos() throws Exception{
        JTEEGUIXEVENTOS loResult = new JTEEGUIXEVENTOS(null);
        String lsRepe = getRepeticionesTipo();
        int lNumero = getRepeticionesNumero();
        if(!lsRepe.equalsIgnoreCase(mcsRepeticionesTipoUNICO) && lNumero > 0){
            for(int i = 0 ; i < lNumero; i++){
                loResult.addNew();
                loResult.getFields().cargar(moList.moFila());
                loResult.getCODIGO().setValue("");
                JDateEdu loDate = loResult.getFECHADESDE().getDateEdu();
                loDate.add(
                        getUnidadJDateEdu(lsRepe)
                        , (i+1) * (lsRepe.equalsIgnoreCase(mcsRepeticionesTipoSEMANAS) ? 7 : 1 ) );
                loResult.getFECHADESDE().setValue(loDate);

                loDate = loResult.getFECHAHASTA().getDateEdu();
                loDate.add(
                        getUnidadJDateEdu(lsRepe)
                        , (i+1) * (lsRepe.equalsIgnoreCase(mcsRepeticionesTipoSEMANAS) ? 7 : 1 ) );
                loResult.getFECHAHASTA().setValue(loDate);
                loResult.getIDENTIFICADORSERIE().setValue(getClave());
                loResult.setRepeticiones(lsRepe, String.valueOf(lNumero-i-1));
                loResult.update(false);
            }
        }
        return loResult;
    }
    private int getUnidadJDateEdu(String psRepe){
        int lResult = JDateEdu.mclDia;
        if(psRepe.equalsIgnoreCase(mcsRepeticionesTipoMESES)){
            lResult = JDateEdu.mclMes;
        }
        if(psRepe.equalsIgnoreCase(mcsRepeticionesTipoANYO)){
            lResult = JDateEdu.mclAno;
        }
        return lResult;
    }
    
    
    public IResultado guardar(JDatosGenerales poDatosGenerales) throws Exception{
        return guardar(poDatosGenerales, false);
    }
    public IResultado guardar(JDatosGenerales poDatosGenerales, boolean pbExterno) throws Exception{
        //se comprueba antes de guardar la clave pq despues de 
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave =  isMismaClave();
        //comprobamos las tablas relacionadas
        comprobarClaveCargar(lbIsMismaClave);
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));
        
        if(loResult.getBien() ) {
            if(poDatosGenerales!=null && !pbExterno){
                poDatosGenerales.addFilaEvento(moList.moFila());
            }
            if(JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCRM()!=null
                    && !pbExterno){
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCRM()
                        .guardarNotaTarea(this);
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
    public IResultado borrar(JDatosGenerales poDatosGenerales) throws Exception {
        IFilaDatos loFila = moList.moFila();
        IResultado loResult = moList.borrar(true);
        if(loResult.getBien()){
            loFila.setTipoModif(JListDatos.mclBorrar);
            poDatosGenerales.addFilaEvento(loFila);
        }
        return loResult;
    }
    
    public int getRepeticionesNumero(){
        int lNumero=1;
        if(!getREPETICION().isVacio()){
            try{
                utiles.xml.dom.SAXBuilder lo = new SAXBuilder();
                Document loDoc =  lo.build(new CadenaLarga(getREPETICION().getString()));
                lNumero = Integer.valueOf(loDoc.getPropiedad(mcsRepeticionesNumero, "")).intValue();
            }catch(Throwable e){
                lNumero=1;
            }
        }
        return lNumero;
    }
    
    public String getRepeticionesTipo() {
        String lsResult = mcsRepeticionesTipoUNICO;
        if(!getREPETICION().isVacio()){
            try{
                utiles.xml.dom.SAXBuilder lo = new SAXBuilder();
                Document loDoc =  lo.build(new CadenaLarga(getREPETICION().getString()));
                lsResult = loDoc.getPropiedad(mcsRepeticionesTipo, "");
                if(lsResult==null || lsResult.equals("")){
                    lsResult=mcsRepeticionesTipoUNICO;
                }
            }catch(Throwable e){
                lsResult=mcsRepeticionesTipoUNICO;
            }
        }
        return lsResult;
        
    }
    public void setRepeticiones(String psTipo, String psNumero) throws Exception{
        Document loDoc = new Document(new Element("root"));
        loDoc.getRootElement().addContent(new Element(mcsRepeticionesTipo, psTipo));
        loDoc.getRootElement().addContent(new Element(mcsRepeticionesNumero, psNumero));
        CadenaLargaOut lsCadenaLar = new CadenaLargaOut();
        JDOMGuardar.guardar(loDoc, lsCadenaLar);
        getREPETICION().setValue(lsCadenaLar.toString());
        
    }
    
    
}