/*
* JCCRMEMAILYNOTAS.java
*
* Creado el 27/4/2017
*/
package utilesCRM.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesCRM.tablasExtend.JTEECRMEMAILYNOTAS;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JTFORMCRMEMAILYNOTAS extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    private static final String msTablaPrincipal;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGONOTA;
    public static final int lPosiCODIGOUSUARIO;
    public static final int lPosiCODIGOEMPRESA;
    public static final int lPosiFECHA;
    public static final int lPosiTIPO;
    public static final int lPosiASUNTO;
    public static final int lPosiDESCRIPCION;


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
        msTablaPrincipal = JTEECRMEMAILYNOTAS.msCTabla;
        moListDatosEstatico = new JListDatos();
        moListDatosEstatico.msTabla = msTablaPrincipal;
        moSelectEstatica = new JSelect(msTablaPrincipal);

        JTEECRMEMAILYNOTAS loCRMEMAILYNOTAS = new JTEECRMEMAILYNOTAS(null);
        int lPosi = 0;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiCODIGOEMPRESA));
        lPosiCODIGOEMPRESA = lPosi;
        lPosi++;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiCODIGONOTA));
        lPosiCODIGONOTA = lPosi;
        lPosi++;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiCODIGOUSUARIO));
        lPosiCODIGOUSUARIO = lPosi;
        lPosi++;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiFECHA));
        lPosiFECHA = lPosi;
        lPosi++;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiTIPO));
        lPosiTIPO = lPosi;
        lPosi++;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiASUNTO));
        lPosiASUNTO = lPosi;
        lPosi++;

        addCampo(JTEECRMEMAILYNOTAS.msCTabla, loCRMEMAILYNOTAS.moList.getFields(JTEECRMEMAILYNOTAS.lPosiDESCRIPCION));
        lPosiDESCRIPCION = lPosi;
        lPosi++;


        moSelectEstatica.addCampoOrder(JTEECRMEMAILYNOTAS.msCTabla, JTEECRMEMAILYNOTAS.getCODIGONOTANombre(), false);
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
      * Crea una instancia de la clase intermedia para la tabla crmemailynotasincluyendole el servidor de datos
      */
    public JTFORMCRMEMAILYNOTAS(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEECRMEMAILYNOTAS.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEECRMEMAILYNOTAS.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEECRMEMAILYNOTAS.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEECRMEMAILYNOTAS.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEECRMEMAILYNOTAS.msCTabla, JTEECRMEMAILYNOTAS.malTipos, JTEECRMEMAILYNOTAS.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMCRMEMAILYNOTAS loCons = new JTFORMCRMEMAILYNOTAS(moList.moServidor);
        loCons.crearSelectSimple();
        //aNadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMCRMEMAILYNOTAS.class.getName() + "->cargar = No existe el registro de la tabla " + JTEECRMEMAILYNOTAS.msCTabla);
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
        moList.ordenar(new int[]{lPosiCODIGONOTA}, false);
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

    public void crearSelect(String psGrupo) {
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEECRMEMAILYNOTAS.lPosiCODIGONEGOCIACION, psGrupo);
        loFiltro.inicializar(JTEECRMEMAILYNOTAS.msCTabla, JTEECRMEMAILYNOTAS.malTipos, JTEECRMEMAILYNOTAS.masNombres);
        moSelect.getWhere().addCondicionAND(loFiltro);
    }

    public void crearSelectContacto(String psCodContacto) {
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEECRMEMAILYNOTAS.lPosiCODIGOCONTACTO, psCodContacto);
        loFiltro.inicializar(JTEECRMEMAILYNOTAS.msCTabla, JTEECRMEMAILYNOTAS.malTipos, JTEECRMEMAILYNOTAS.masNombres);
        moSelect.getWhere().addCondicionAND(loFiltro);
    }
}
