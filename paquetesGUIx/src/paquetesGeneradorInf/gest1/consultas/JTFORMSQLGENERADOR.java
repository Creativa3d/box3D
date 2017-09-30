/*
* JCSQLGENERADOR.java
*
* Creado el 9/9/2013
*/
package paquetesGeneradorInf.gest1.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import paquetesGeneradorInf.gest1.tablas.*;
import paquetesGeneradorInf.gest1.tablasExtend.*;

public class JTFORMSQLGENERADOR extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    private static final String msTablaPrincipal;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOSQLGENERADOR;
    public static final int lPosiNOMBRE;
    public static final int lPosiPALABRASCLAVE;
    public static final int lPosiPADRE;
    public static final int lPosiTABLAPRINCIPAL;
    public static final int lPosiSQL;
    public static final int lPosiOBSERVACIONES;

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
        msTablaPrincipal = JTEESQLGENERADOR.msCTabla;
        moListDatosEstatico = new JListDatos();
        moListDatosEstatico.msTabla = msTablaPrincipal;
        moSelectEstatica = new JSelect(msTablaPrincipal);

        JTEESQLGENERADOR loSQLGENERADOR = new JTEESQLGENERADOR(null);
        int lPosi = 0;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiCODIGOSQLGENERADOR));
        lPosiCODIGOSQLGENERADOR = lPosi;
        lPosi++;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiNOMBRE));
        lPosiNOMBRE = lPosi;
        lPosi++;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiPALABRASCLAVE));
        lPosiPALABRASCLAVE = lPosi;
        lPosi++;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiPADRE));
        lPosiPADRE = lPosi;
        lPosi++;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiTABLAPRINCIPAL));
        lPosiTABLAPRINCIPAL = lPosi;
        lPosi++;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiSQL));
        lPosiSQL = lPosi;
        lPosi++;

        addCampo(JTEESQLGENERADOR.msCTabla, loSQLGENERADOR.moList.getFields(JTEESQLGENERADOR.lPosiOBSERVACIONES));
        lPosiOBSERVACIONES = lPosi;
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
      * Crea una instancia de la clase intermedia para la tabla SQLGENERADORincluyendole el servidor de datos
      */
    public JTFORMSQLGENERADOR(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEESQLGENERADOR.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEESQLGENERADOR.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEESQLGENERADOR.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEESQLGENERADOR.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEESQLGENERADOR.msCTabla, JTEESQLGENERADOR.malTipos, JTEESQLGENERADOR.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMSQLGENERADOR loCons = new JTFORMSQLGENERADOR(moList.moServidor);
        loCons.crearSelectSimple();
        //aNadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMSQLGENERADOR.class.getName() + "->cargar = No existe el registro de la tabla " + JTEESQLGENERADOR.msCTabla);
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
    public void crearSelect(String psPAdr){
        crearSelectSimple();
        moSelect.getWhere().addCondicionAND(JListDatos.mclTIgual, lPosiPADRE, psPAdr);
        moSelect.getWhere().inicializar(JTSQLGENERADOR.msCTabla, JTSQLGENERADOR.malTipos, JTSQLGENERADOR.masNombres);
    }
}
