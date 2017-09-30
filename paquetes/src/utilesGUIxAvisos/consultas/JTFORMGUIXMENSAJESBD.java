/*
* JCGUIXMENSAJESSEND.java
*
* Creado el 8/9/2012
*/
package utilesGUIxAvisos.consultas;

import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.tablas.*;
import utilesGUIxAvisos.tablasExtend.*;

public class JTFORMGUIXMENSAJESBD extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    private static final String msTablaPrincipal;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGO;
    public static final int lPosiASUNTO;
    public static final int lPosiEMAILTO;
    public static final int lPosiEMAILFROM;
    public static final int lPosiFECHA;
//    public static final int lPosiTEXTO;
    public static final int lPosiGRUPO;
    public static final int lPosiUSUARIO;
    public static final int lPosiADJUNTOS;
    public static final int lPosiESTADO;

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
        msTablaPrincipal = JTEEGUIXMENSAJESBD.msCTabla;
        moListDatosEstatico = new JListDatos();
        moListDatosEstatico.msTabla = msTablaPrincipal;
        moSelectEstatica = new JSelect(msTablaPrincipal);

        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(null);
        int lPosi = 0;

        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiCODIGO));
        lPosiCODIGO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiASUNTO));
        lPosiASUNTO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiEMAILTO));
        lPosiEMAILTO = lPosi;
        lPosi++;
        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiEMAILFROM));
        lPosiEMAILFROM = lPosi;
        lPosi++;
        
        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiFECHA));
        lPosiFECHA = lPosi;
        lPosi++;


//        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiTEXTO));
//        lPosiTEXTO = lPosi;
//        lPosi++;

        
        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiADJUNTOS));
        lPosiADJUNTOS = lPosi;
        lPosi++;
        
        
        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiGRUPO));
        lPosiGRUPO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiUSUARIO));
        lPosiUSUARIO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXMENSAJESBD.msCTabla, loGUIXMENSAJESSEND.moList.getFields(JTEEGUIXMENSAJESBD.lPosiESTADO));
        lPosiESTADO = lPosi;
        lPosi++;


        moSelectEstatica.addCampoOrder(JTEEGUIXMENSAJESBD.msCTabla, JTEEGUIXMENSAJESBD.getFECHANombre(), false);
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
      * Crea una instancia de la clase intermedia para la tabla GUIXMensajesSendincluyendole el servidor de datos
      */
    public JTFORMGUIXMENSAJESBD(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEEGUIXMENSAJESBD.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEEGUIXMENSAJESBD.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEEGUIXMENSAJESBD.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEEGUIXMENSAJESBD.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEEGUIXMENSAJESBD.msCTabla, JTEEGUIXMENSAJESBD.malTipos, JTEEGUIXMENSAJESBD.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMGUIXMENSAJESBD loCons = new JTFORMGUIXMENSAJESBD(moList.moServidor);
        loCons.crearSelectSimple();
        //aNadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMGUIXMENSAJESBD.class.getName() + "->cargar = No existe el registro de la tabla " + JTEEGUIXMENSAJESBD.msCTabla);
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
    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac){
        crearSelectSimple();
        if(psTabla!=null){
        }
    }
    public void crearSelect(JGUIxAvisosCorreo poCorreo, String psCarpeta){
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEEGUIXMENSAJESBD.lPosiFOLDER, JTEEGUIXMENSAJESBD.getIdentificadorFOLDER(poCorreo, psCarpeta));
        if(JTEEGUIXMENSAJESBD.mcsENVIADOS.equalsIgnoreCase(psCarpeta)){
            loFiltro.addCondicionOR(JListDatos.mclTIgual, JTEEGUIXMENSAJESBD.lPosiFOLDER, "");
        }
        loFiltro.inicializar(new JTEEGUIXMENSAJESBD(null).getList());
        moSelect.getWhere().addCondicionAND(loFiltro);
    }
    public void crearSelectNula(){
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTIgual, JTEEGUIXMENSAJESBD.lPosiCODIGO, "-1");
        loFiltro.inicializar(new JTEEGUIXMENSAJESBD(null).getList());
        moSelect.getWhere().addCondicionAND(loFiltro);
    }
    
}
