/*
* JCTABLASINCRONIZACIONGENERAL.java
*
* Creado el 2/10/2008
*/
package utilesSincronizacion.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesSincronizacion.tablas.*;
import utilesSincronizacion.tablasExtend.*;

public class JTFORMTABLASINCRONIZACIONGENERAL extends JSTabla  implements IConsulta {
    private JSelect moSelect;
    private static JSelect moSelectEstatica;
    private static JListDatos moListDatosEstatico;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiNOMBRE;
    public static final int lPosiVALOR;

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
        moSelectEstatica = new JSelect(JTEETABLASINCRONIZACIONGENERAL.msCTabla);

        JTEETABLASINCRONIZACIONGENERAL loTABLASINCRONIZACIONGENERAL = new JTEETABLASINCRONIZACIONGENERAL(null);
        moListDatosEstatico.msTabla = JTEETABLASINCRONIZACIONGENERAL.msCTabla ;
        int lPosi = 0;

        addCampo(JTEETABLASINCRONIZACIONGENERAL.msCTabla, loTABLASINCRONIZACIONGENERAL.moList.getFields(JTEETABLASINCRONIZACIONGENERAL.lPosiNOMBRE));
        lPosiNOMBRE = lPosi;
        lPosi++;

        addCampo(JTEETABLASINCRONIZACIONGENERAL.msCTabla, loTABLASINCRONIZACIONGENERAL.moList.getFields(JTEETABLASINCRONIZACIONGENERAL.lPosiVALOR));
        lPosiVALOR = lPosi;
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
      * Crea una instancia de la clase intermedia para la tabla TablaSincronizacionGeneralincluyendole el servidor de datos
      */
    public JTFORMTABLASINCRONIZACIONGENERAL(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEETABLASINCRONIZACIONGENERAL.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEETABLASINCRONIZACIONGENERAL.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEETABLASINCRONIZACIONGENERAL.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEETABLASINCRONIZACIONGENERAL.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEETABLASINCRONIZACIONGENERAL.msCTabla, JTEETABLASINCRONIZACIONGENERAL.malTipos, JTEETABLASINCRONIZACIONGENERAL.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMTABLASINCRONIZACIONGENERAL loCons = new JTFORMTABLASINCRONIZACIONGENERAL(moList.moServidor);
        loCons.crearSelectSimple();
        //añadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMTABLASINCRONIZACIONGENERAL.class.getName() + "->cargar = No existe el registro de la tabla " + JTEETABLASINCRONIZACIONGENERAL.msCTabla);
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
