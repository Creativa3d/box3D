/*
* JCDOCUMENTOS.java
*
* Creado el 19/10/2016
*/
package utilesDoc.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesDoc.tablas.*;
import utilesDoc.tablasExtend.JTEEDOCUMENTOS;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JTFORMDOCUMENTOS extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    private static final String msTablaPrincipal;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiGRUPO;
    public static final int lPosiGRUPOIDENT;
    public static final int lPosiCODIGODOCUMENTO;
    public static final int lPosiNOMBRE;
    public static final int lPosiDESCRIPCION;
    public static final int lPosiAUTOR;
    public static final int lPosiFECHA;
    public static final int lPosiUSUARIO;
    public static final int lPosiFECHAMODIF;
    public static final int lPosiCODTIPODOCUMENTO;
    public static final int lPosiCODCLASIF;
    public static final int lPosiRUTA;
    public static final int lPosiIDENTIFICADOREXTERNO;
    public static final int lPosiIDENTIFICADOROTRO;

    public static final int mclNumeroCampos;


    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo){
        if (poCampo.getTabla().equals(msTablaPrincipal)) {
            return addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());
        } else {
            return addCampo(psNombreTabla, poCampo, false);
        }
    }

    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){
        return addCampo(psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);
    }

    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado){
        return JUtilTabla.addCampo(moSelectEstatica,moListDatosEstatico,psNombreTabla, poCampo, pbEsPrincipal, plFuncion, pbAddAgrupado);
    }

    private static JFieldDef addCampoFuncion(final String psNombreTabla, final JFieldDef poCampo, final int plFuncion){
        return addCampo(psNombreTabla, poCampo, false, plFuncion, false);
    }
    private static JFieldDef addCampoYGrupo(final String psNombreTabla, final JFieldDef poCampo){
        return addCampo(psNombreTabla, poCampo, false, JSelectCampo.mclFuncionNada, true);
    }        
    private static JFieldDef addCampoLibre(final String psNombreCampo, final int pnTipoCampo) {
        JFieldDef loCampo = new JFieldDef(psNombreCampo);
        loCampo.setTipo(pnTipoCampo);
        loCampo.setPrincipalSN(false);
        moListDatosEstatico.getFields().addField(loCampo);
        //moSelectEstatica.addCampo(psNombreCampo);
        return loCampo;
    }
    static{
        msTablaPrincipal = JTEEDOCUMENTOS.msCTabla;
        moListDatosEstatico = new JListDatos();
        moListDatosEstatico.msTabla = msTablaPrincipal;
        moSelectEstatica = new JSelect(msTablaPrincipal);

        JTEEDOCUMENTOS loDOCUMENTOS = new JTEEDOCUMENTOS(null);
        int lPosi = 0;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiGRUPO));
        lPosiGRUPO = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiGRUPOIDENT));
        lPosiGRUPOIDENT = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiCODIGODOCUMENTO));
        lPosiCODIGODOCUMENTO = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiNOMBRE));
        lPosiNOMBRE = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiDESCRIPCION));
        lPosiDESCRIPCION = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiAUTOR));
        lPosiAUTOR = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiFECHA));
        lPosiFECHA = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiUSUARIO));
        lPosiUSUARIO = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiFECHAMODIF));
        lPosiFECHAMODIF = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiCODTIPODOCUMENTO));
        lPosiCODTIPODOCUMENTO = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiCODCLASIF));
        lPosiCODCLASIF = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiRUTA));
        lPosiRUTA = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiIDENTIFICADOREXTERNO));
        lPosiIDENTIFICADOREXTERNO = lPosi;
        lPosi++;

        addCampo(JTEEDOCUMENTOS.msCTabla, loDOCUMENTOS.moList.getFields(JTEEDOCUMENTOS.lPosiIDENTIFICADOROTRO));
        lPosiIDENTIFICADOROTRO = lPosi;
        lPosi++;

        mclNumeroCampos=lPosi;
    };

    public static JFieldDef getFieldEstatico(final int plPosi){
        return moListDatosEstatico.getFields(plPosi);
    }
    public static JFieldDefs getFieldsEstaticos(){
        return moListDatosEstatico.getFields();
    }
    public static String getNombreTabla(){
        return msTablaPrincipal;
    }

     /**
      * Crea una instancia de la clase intermedia para la tabla documentosincluyendole el servidor de datos
      */
    public JTFORMDOCUMENTOS(IServerServidorDatos poServidorDatos) {
        super();
        try {
            moList = JUtilTabla.clonarFilasListDatos(moListDatosEstatico, true);
        } catch (Exception ex) {
            utiles.JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        moList.moServidor = poServidorDatos;
        moList.addListener(this);
    }
     public boolean getPasarCache(){
        return (moList.moServidor.getEnCache(moSelect.toString())!=null);
    }

    public JFieldDef getField(final int plPosi){
        return moList.getFields(plPosi);
    }
    public JSelect getSelect(){
        return moSelect;
    }
    private void cargar(final IFilaDatos poFila) throws Exception{
        //creamos el filtro por los campo principales
        String[] lasValores = new String[JTEEDOCUMENTOS.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEEDOCUMENTOS.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEEDOCUMENTOS.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEEDOCUMENTOS.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEEDOCUMENTOS.msCTabla, JTEEDOCUMENTOS.malTipos, JTEEDOCUMENTOS.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMDOCUMENTOS loCons = new JTFORMDOCUMENTOS(moList.moServidor);
        loCons.crearSelectSimple();
        //aNadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMDOCUMENTOS.class.getName() + "->cargar = No existe el registro de la tabla " + JTEEDOCUMENTOS.msCTabla);
        }
    }

    public void addFilaPorClave(final IFilaDatos poFila) throws Exception {
        switch(poFila.getTipoModif()){
            case JListDatos.mclBorrar:
                moList.borrar(false);
                break;
            case JListDatos.mclEditar:
                cargar(poFila);
                moList.update(false);
                break;
            case JListDatos.mclNuevo:
                moList.addNew();
                cargar(poFila);
                moList.update(false);
                break;
            default:
                throw new Exception("Tipo modificaciOn incorrecto");
        }
    }

    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {
        moList.recuperarDatos(moSelect, getPasarCache(), JListDatos.mclSelectNormal, pbLimpiarCache);
    }

    public void crearSelectSimple(){
        try {
            moSelect = (JSelect)moSelectEstatica.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    public void crearSelectDOCUMCLASIF_CODCLASIF(
            String psCODCLASIF
        ) {
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,JTDOCUMENTOS.lPosiCODCLASIF, psCODCLASIF);
        loFiltro.inicializar(JTDOCUMENTOS.msCTabla, JTDOCUMENTOS.malTipos, JTDOCUMENTOS.masNombres);
        moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
    }

    public void crearSelectDOCUMTIPOS_CODTIPODOCUMENTO(
            String psCODTIPODOCUMENTO
        ) {
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,JTDOCUMENTOS.lPosiCODTIPODOCUMENTO, psCODTIPODOCUMENTO);
        loFiltro.inicializar(JTDOCUMENTOS.msCTabla, JTDOCUMENTOS.malTipos, JTDOCUMENTOS.masNombres);
        moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
    }
    public void crearSelect(String psGrupo, String psGrupoIden){
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,JTDOCUMENTOS.lPosiGRUPO, psGrupo);
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,JTDOCUMENTOS.lPosiGRUPOIDENT, psGrupoIden);
        loFiltro.inicializar(JTDOCUMENTOS.msCTabla, JTDOCUMENTOS.malTipos, JTDOCUMENTOS.masNombres);
        moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);

    }
    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac){
        crearSelectSimple();
        if(psTabla!=null){
        if(psTabla.equalsIgnoreCase(JTDOCUMCLASIF.msCTabla)){
            crearSelectDOCUMCLASIF_CODCLASIF(
              poFilaDatosRelac.msCampo(JTDOCUMCLASIF.lPosiCODIGOCLASIFDOC));
        }
        if(psTabla.equalsIgnoreCase(JTDOCUMTIPOS.msCTabla)){
            crearSelectDOCUMTIPOS_CODTIPODOCUMENTO(
              poFilaDatosRelac.msCampo(JTDOCUMTIPOS.lPosiCODIGOTIPODOC));
        }
        }
    }
}
