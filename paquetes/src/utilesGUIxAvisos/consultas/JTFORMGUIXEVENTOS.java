/*
* JCGUIXEVENTOS.java
*
* Creado el 3/11/2011
*/
package utilesGUIxAvisos.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import utiles.FechaMalException;
import utiles.JDateEdu;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIxAvisos.tablas.JTGUIXCALENDARIO;
import utilesGUIxAvisos.tablas.JTGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOSPRIORIDAD;

public class JTFORMGUIXEVENTOS extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCALENDARIO;
    public static final int lPosiCODIGO;
    public static final int lPosiFECHADESDE;
    public static final int lPosiFECHAHASTA;
    public static final int lPosiNOMBRE;
    public static final int lPosiTEXTO;
    public static final int lPosiREPETICION;
    public static final int lPosiGRUPO;
    public static final int lPosiPRIORIDADNOMBRE;
    public static final int lPosiPRIORIDADCOLOR;
    public static final int lPosiCALENDARIONOMBRE;
    public static final int lPosiEVENTOSN;

    public static final int mclNumeroCampos;


    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo){
        return addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());
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
        moListDatosEstatico = new JListDatos();
        moSelectEstatica = new JSelect(JTEEGUIXEVENTOS.msCTabla);
        moSelectEstatica.getFrom().addTabla(new JSelectUnionTablas(
                JSelectUnionTablas.mclLeft,
                JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOSPRIORIDAD.msCTabla,
                new String[]{JTEEGUIXEVENTOS.getPRIORIDADNombre()},
                new String[]{JTEEGUIXEVENTOSPRIORIDAD.getGUIXPRIORIDADNombre()}));
        moSelectEstatica.getFrom().addTabla(new JSelectUnionTablas(
                JSelectUnionTablas.mclLeft,
                JTEEGUIXEVENTOS.msCTabla, JTGUIXCALENDARIO.msCTabla,
                new String[]{JTEEGUIXEVENTOS.getCALENDARIONombre()},
                new String[]{JTGUIXCALENDARIO.getCALENDARIONombre()}));

        JTEEGUIXEVENTOS loGUIXEVENTOS = new JTEEGUIXEVENTOS(null);
        moListDatosEstatico.msTabla = JTEEGUIXEVENTOS.msCTabla ;
        int lPosi = 0;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiCALENDARIO));
        lPosiCALENDARIO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiCODIGO));
        lPosiCODIGO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiFECHADESDE));
        lPosiFECHADESDE = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiFECHAHASTA));
        lPosiFECHAHASTA = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiNOMBRE));
        lPosiNOMBRE = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiTEXTO));
        lPosiTEXTO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiREPETICION));
        lPosiREPETICION = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiGRUPO));
        lPosiGRUPO = lPosi;
        lPosi++;
        
        addCampo(JTEEGUIXEVENTOS.msCTabla, loGUIXEVENTOS.moList.getFields(JTEEGUIXEVENTOS.lPosiEVENTOSN));
        lPosiEVENTOSN = lPosi;
        lPosi++;
        
        

        JTEEGUIXEVENTOSPRIORIDAD loPri = new JTEEGUIXEVENTOSPRIORIDAD(null);
        addCampo(loPri.msCTabla, loPri.moList.getFields(loPri.lPosiCOLOR));
        lPosiPRIORIDADCOLOR = lPosi;
        lPosi++;
        
        addCampo(loPri.msCTabla, loPri.moList.getFields(loPri.lPosiNOMBRE));
        lPosiPRIORIDADNOMBRE = lPosi;
        lPosi++;

        JTEEGUIXCALENDARIO loCal = new JTEEGUIXCALENDARIO(null);
        addCampo(loCal.msCTabla, loCal.moList.getFields(loCal.lPosiNOMBRE));
        lPosiCALENDARIONOMBRE = lPosi;
        lPosi++;
        
        moSelectEstatica.addCampoOrder(JTGUIXEVENTOS.msCTabla, JTGUIXEVENTOS.getFECHADESDENombre());

        mclNumeroCampos=lPosi;
    };

    public static JFieldDef getFieldEstatico(final int plPosi){
        return moListDatosEstatico.getFields(plPosi);
    }
    public static JFieldDefs getFieldsEstaticos(){
        return moListDatosEstatico.getFields();
    }
    public static String getNombreTabla(){
        return moListDatosEstatico.msTabla;
    }
    private String msGrupo;
    private String msUsu;

     /**
      * Crea una instancia de la clase intermedia para la tabla GUIXEVENTOSincluyendole el servidor de datos
      */
    public JTFORMGUIXEVENTOS(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEEGUIXEVENTOS.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEEGUIXEVENTOS.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEEGUIXEVENTOS.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEEGUIXEVENTOS.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMGUIXEVENTOS loCons = new JTFORMGUIXEVENTOS(moList.moServidor);
        loCons.crearSelectSimple();
        //añadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMGUIXEVENTOS.class.getName() + "->cargar = No existe el registro de la tabla " + JTEEGUIXEVENTOS.msCTabla);
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
                throw new Exception("Tipo modificación incorrecto");
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
    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac){
        crearSelectSimple();
        if(psTabla!=null){
        }
    }

    public void crearSelectStandarConGrupoPrevio(String psFechaDesde) {
        crearSelectStandar(psFechaDesde, msGrupo, msUsu);
    }
    public void crearSelectStandar(String psFechaDesde, String psGrupo, String psUsu) {
        msGrupo=psGrupo;
        msUsu=psUsu;
        crearSelectSimple();
        if(JDateEdu.isDate(psFechaDesde)){
            JListDatosFiltroConj loFiltroO = new JListDatosFiltroConj();
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, JTEEGUIXEVENTOS.lPosiFECHADESDE, psFechaDesde);
            loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
            loFiltroO.addCondicionAND(loFiltro);
            
            loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, JTEEGUIXEVENTOS.lPosiFECHAHASTA, psFechaDesde);
            loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
            loFiltroO.addCondicionOR(loFiltro);
            
            loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTDistinto, JTEEGUIXEVENTOS.lPosiEVENTOSN, JListDatos.mcsTrue);
            loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
            loFiltroO.addCondicionOR(loFiltro);
            
            moSelect.getWhere().addCondicionOR(loFiltroO);
            
        }
        if(psGrupo!=null && !psGrupo.equals("")){
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEEGUIXEVENTOS.lPosiGRUPO, psGrupo);
            loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
            moSelect.getWhere().addCondicionAND(loFiltro);
        }
        if(psUsu!=null && !psUsu.equals("")){
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEEGUIXEVENTOS.lPosiUSUARIO, psUsu);
            loFiltro.addCondicionOR(JListDatos.mclTIgual, JTEEGUIXEVENTOS.lPosiUSUARIOASIGNADO, psUsu);
            loFiltro.addCondicionOR(JListDatos.mclTIgual, JTEEGUIXEVENTOS.lPosiUSUARIOASIGNADO, "");
            loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
            moSelect.getWhere().addCondicionAND(loFiltro);
        }        
    }
    public void crearSelectFechas(String psFechaDesde, String psFechaHasta) throws FechaMalException {
        crearSelectSimple();
        //fecha desde
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, JTEEGUIXEVENTOS.lPosiFECHADESDE, psFechaDesde);
        JDateEdu lodate = new JDateEdu(psFechaHasta);
        lodate.add(lodate.mclDia, 1);
        lodate.setHora(0);
        lodate.setMinuto(0);
        lodate.setSegundo(0);
        loFiltro.addCondicionAND(JListDatos.mclTMenor, JTEEGUIXEVENTOS.lPosiFECHADESDE, lodate.toString());
        loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
        moSelect.getWhere().addCondicionAND(loFiltro);
        //fecha desde menor que la fecha desde y fecha hasta mayor que fecha desde, asi cogemos los intervalos
        loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTMayorIgual, JTEEGUIXEVENTOS.lPosiFECHAHASTA, psFechaDesde);
        loFiltro.addCondicionAND(JListDatos.mclTMenor, JTEEGUIXEVENTOS.lPosiFECHADESDE, psFechaDesde);
        loFiltro.inicializar(JTEEGUIXEVENTOS.msCTabla, JTEEGUIXEVENTOS.malTipos, JTEEGUIXEVENTOS.masNombres);
        moSelect.getWhere().addCondicionOR(loFiltro);
        
    }
    
}
