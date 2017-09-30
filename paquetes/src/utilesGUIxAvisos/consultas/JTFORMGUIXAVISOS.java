/*
* JCGUIXAVISOS.java
*
* Creado el 3/11/2011
 */
package utilesGUIxAvisos.consultas;

import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIxAvisos.tablas.*;
import utilesGUIxAvisos.tablasExtend.*;

public class JTFORMGUIXAVISOS extends JSTabla implements IConsulta {

    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;

    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCALENDARIO;
    public static final int lPosiCODIGO;
    public static final int lPosiCODIGOEVENTO;
    public static final int lPosiFECHACONCRETA;
    public static final int lPosiPANTALLASN;
    public static final int lPosiTELF;
    public static final int lPosiSENDER;
    public static final int lPosiEMAIL;

    public static final int mclNumeroCampos;

    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo) {
        return addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());
    }

    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal) {
        return addCampo(psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);
    }

    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado) {
        return JUtilTabla.addCampo(moSelectEstatica, moListDatosEstatico, psNombreTabla, poCampo, pbEsPrincipal, plFuncion, pbAddAgrupado);
    }

    private static JFieldDef addCampoFuncion(final String psNombreTabla, final JFieldDef poCampo, final int plFuncion) {
        return addCampo(psNombreTabla, poCampo, false, plFuncion, false);
    }

    private static JFieldDef addCampoYGrupo(final String psNombreTabla, final JFieldDef poCampo) {
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

    static {
        moListDatosEstatico = new JListDatos();
        moSelectEstatica = new JSelect(JTEEGUIXAVISOS.msCTabla);

        JTEEGUIXAVISOS loGUIXAVISOS = new JTEEGUIXAVISOS(null);
        moListDatosEstatico.msTabla = JTEEGUIXAVISOS.msCTabla;
        int lPosi = 0;

        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiCALENDARIO));
        lPosiCALENDARIO = lPosi;
        lPosi++;

        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiCODIGOEVENTO));
        lPosiCODIGOEVENTO = lPosi;
        lPosi++;


        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiCODIGO));
        lPosiCODIGO = lPosi;
        lPosi++;
        
        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiFECHACONCRETA));
        lPosiFECHACONCRETA = lPosi;
        lPosi++;

        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiPANTALLASN));
        lPosiPANTALLASN = lPosi;
        lPosi++;

        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiTELF));
        lPosiTELF = lPosi;
        lPosi++;

        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiSENDER));
        lPosiSENDER = lPosi;
        lPosi++;

        addCampo(JTEEGUIXAVISOS.msCTabla, loGUIXAVISOS.moList.getFields(JTEEGUIXAVISOS.lPosiEMAIL));
        lPosiEMAIL = lPosi;
        lPosi++;

        mclNumeroCampos = lPosi;
    }

    ;

    public static JFieldDef getFieldEstatico(final int plPosi) {
        return moListDatosEstatico.getFields(plPosi);
    }

    public static JFieldDefs getFieldsEstaticos() {
        return moListDatosEstatico.getFields();
    }

    public static String getNombreTabla() {
        return moListDatosEstatico.msTabla;
    }

    /**
     * Crea una instancia de la clase intermedia para la tabla
     * GUIXAVISOSincluyendole el servidor de datos
     */
    public JTFORMGUIXAVISOS(IServerServidorDatos poServidorDatos) {
        super();
        try {
            moList = JUtilTabla.clonarFilasListDatos(moListDatosEstatico, true);
        } catch (Exception ex) {
            utiles.JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        moList.moServidor = poServidorDatos;
        moList.addListener(this);
    }

    public boolean getPasarCache() {
        return (moList.moServidor.getEnCache(moSelect.toString()) != null);
    }

    public JFieldDef getField(final int plPosi) {
        return moList.getFields(plPosi);
    }

    public JSelect getSelect() {
        return moSelect;
    }

    private void cargar(final IFilaDatos poFila) throws Exception {
        //creamos el filtro por los campo principales
        String[] lasValores = new String[JTEEGUIXAVISOS.malCamposPrincipales.length];
        for (int i = 0; i < JTEEGUIXAVISOS.malCamposPrincipales.length; i++) {
            lasValores[i] = poFila.msCampo(JTEEGUIXAVISOS.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro
                = new JListDatosFiltroElem(
                        JListDatos.mclTIgual,
                        JTEEGUIXAVISOS.malCamposPrincipales,
                        lasValores
                );
        loFiltro.inicializar(JTEEGUIXAVISOS.msCTabla, JTEEGUIXAVISOS.malTipos, JTEEGUIXAVISOS.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMGUIXAVISOS loCons = new JTFORMGUIXAVISOS(moList.moServidor);
        loCons.crearSelectSimple();
        //añadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if (loCons.moList.moveFirst()) {
            moList.getFields().cargar(loCons.moList.moFila());
        } else {
            throw new Exception(JTFORMGUIXAVISOS.class.getName() + "->cargar = No existe el registro de la tabla " + JTEEGUIXAVISOS.msCTabla);
        }
    }

    public void addFilaPorClave(final IFilaDatos poFila) throws Exception {
        switch (poFila.getTipoModif()) {
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

    public void crearSelectSimple() {
        try {
            moSelect = (JSelect) moSelectEstatica.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }

    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac) {
        crearSelectSimple();
        if (psTabla != null) {
            if (psTabla.equalsIgnoreCase(JTGUIXEVENTOS.msCTabla)) {
                crearSelectEVENTOS(
                        poFilaDatosRelac.msCampo(JTGUIXEVENTOS.lPosiCALENDARIO)
                        ,poFilaDatosRelac.msCampo(JTGUIXEVENTOS.lPosiCODIGO));
            }
        }
    }

    private void crearSelectEVENTOS(String psCalen, String psCod) {
        crearSelectSimple();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicionAND(JListDatos.mclTIgual,JTGUIXAVISOS.lPosiCODIGOEVENTO, psCod);
        loFiltro.addCondicionAND(JListDatos.mclTIgual,JTGUIXAVISOS.lPosiCALENDARIO, psCalen);
        
        loFiltro.inicializar(JTGUIXAVISOS.msCTabla, JTGUIXAVISOS.malTipos, JTGUIXAVISOS.masNombres);
        moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
    }
}
