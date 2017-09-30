/*
* JCTABLASINCRONIZACIONBORRADOS.java
*
* Creado el 2/10/2008
*/
package utilesSincronizacion.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesSincronizacion.tablas.*;
import utilesSincronizacion.tablasExtend.*;

public class JTFORMTABLASINCRONIZACIONBORRADOS extends JSTabla  implements IConsulta {
    private JSelect moSelect;
    private static JSelect moSelectEstatica;
    private static JListDatos moListDatosEstatico;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOBORRADO;
    public static final int lPosiTABLA;
    public static final int lPosiREGISTRO;
    public static final int lPosiNUMEROTRANSACSINCRO;

    public static final int mclNumeroCampos;

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo){
        addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());
    }

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){
        addCampo(psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);
    }

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado){
        poCampo.setPrincipalSN(pbEsPrincipal);
        moListDatosEstatico.getFields().addField(poCampo);
        moSelectEstatica.addCampo(plFuncion, psNombreTabla, poCampo.getNombre());
        if(pbAddAgrupado){
            moSelectEstatica.addCampoGroup(psNombreTabla, poCampo.getNombre());
        }
    }

    private static void addCampoFuncion(final String psNombreTabla, final JFieldDef poCampo, final int plFuncion){
        addCampo(psNombreTabla, poCampo, false, plFuncion, false);
    }
    private static void addCampoYGrupo(final String psNombreTabla, final JFieldDef poCampo){
        addCampo(psNombreTabla, poCampo, false, JSelectCampo.mclFuncionNada, true);
    }

    static{
        moListDatosEstatico = new JListDatos();
        moSelectEstatica = new JSelect(JTEETABLASINCRONIZACIONBORRADOS.msCTabla);

        JTEETABLASINCRONIZACIONBORRADOS loTABLASINCRONIZACIONBORRADOS = new JTEETABLASINCRONIZACIONBORRADOS(null);
        moListDatosEstatico.msTabla = JTEETABLASINCRONIZACIONBORRADOS.msCTabla ;
        int lPosi = 0;

        addCampo(JTEETABLASINCRONIZACIONBORRADOS.msCTabla, loTABLASINCRONIZACIONBORRADOS.moList.getFields(JTEETABLASINCRONIZACIONBORRADOS.lPosiCODIGOBORRADO));
        lPosiCODIGOBORRADO = lPosi;
        lPosi++;

        addCampo(JTEETABLASINCRONIZACIONBORRADOS.msCTabla, loTABLASINCRONIZACIONBORRADOS.moList.getFields(JTEETABLASINCRONIZACIONBORRADOS.lPosiTABLA));
        lPosiTABLA = lPosi;
        lPosi++;

        addCampo(JTEETABLASINCRONIZACIONBORRADOS.msCTabla, loTABLASINCRONIZACIONBORRADOS.moList.getFields(JTEETABLASINCRONIZACIONBORRADOS.lPosiREGISTRO));
        lPosiREGISTRO = lPosi;
        lPosi++;

        addCampo(JTEETABLASINCRONIZACIONBORRADOS.msCTabla, loTABLASINCRONIZACIONBORRADOS.moList.getFields(JTEETABLASINCRONIZACIONBORRADOS.lPosiNUMEROTRANSACSINCRO));
        lPosiNUMEROTRANSACSINCRO = lPosi;
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
        return moListDatosEstatico.msTabla;
    }

     /**
      * Crea una instancia de la clase intermedia para la tabla TablaSincronizacionBorradosincluyendole el servidor de datos
      */
    public JTFORMTABLASINCRONIZACIONBORRADOS(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEETABLASINCRONIZACIONBORRADOS.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEETABLASINCRONIZACIONBORRADOS.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEETABLASINCRONIZACIONBORRADOS.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEETABLASINCRONIZACIONBORRADOS.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEETABLASINCRONIZACIONBORRADOS.msCTabla, JTEETABLASINCRONIZACIONBORRADOS.malTipos, JTEETABLASINCRONIZACIONBORRADOS.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMTABLASINCRONIZACIONBORRADOS loCons = new JTFORMTABLASINCRONIZACIONBORRADOS(moList.moServidor);
        loCons.crearSelectSimple();
        //añadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMTABLASINCRONIZACIONBORRADOS.class.getName() + "->cargar = No existe el registro de la tabla " + JTEETABLASINCRONIZACIONBORRADOS.msCTabla);
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

    public JListDatos getList() {
        return moList;
    }
    public void crearSelectSimple(){
        try {
            moSelect = (JSelect)moSelectEstatica.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
}
