/*
* JCUSUARIOSATRIB.java
*
* Creado el 2/11/2016
*/
package utilesGUIx.aplicacion.usuarios.consultas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.aplicacion.usuarios.tablasExtend.JTEEUSUARIOSATRIB;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JTFORMUSUARIOSATRIB extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    private static final String msTablaPrincipal;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiCODIGOVEHICREFO;
    public static final int lPosiCODIGOINSPECDEF;
    public static final int lPosiVALOR;

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
        msTablaPrincipal = JTEEUSUARIOSATRIB.msCTabla;
        moListDatosEstatico = new JListDatos();
        moListDatosEstatico.msTabla = msTablaPrincipal;
        moSelectEstatica = new JSelect(msTablaPrincipal);

        JTEEUSUARIOSATRIB loUSUARIOSATRIB = new JTEEUSUARIOSATRIB(null);
        int lPosi = 0;

        addCampo(JTEEUSUARIOSATRIB.msCTabla, loUSUARIOSATRIB.moList.getFields(JTEEUSUARIOSATRIB.lPosiCODIGOUSUARIO));
        lPosiCODIGOVEHICREFO = lPosi;
        lPosi++;

        addCampo(JTEEUSUARIOSATRIB.msCTabla, loUSUARIOSATRIB.moList.getFields(JTEEUSUARIOSATRIB.lPosiCODIGOUSUARIOATRIBDEF));
        lPosiCODIGOINSPECDEF = lPosi;
        lPosi++;

        addCampo(JTEEUSUARIOSATRIB.msCTabla, loUSUARIOSATRIB.moList.getFields(JTEEUSUARIOSATRIB.lPosiVALOR));
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
        return msTablaPrincipal;
    }

     /**
      * Crea una instancia de la clase intermedia para la tabla vehicrefoatribincluyendole el servidor de datos
      */
    public JTFORMUSUARIOSATRIB(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEEUSUARIOSATRIB.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEEUSUARIOSATRIB.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEEUSUARIOSATRIB.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEEUSUARIOSATRIB.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEEUSUARIOSATRIB.msCTabla, JTEEUSUARIOSATRIB.malTipos, JTEEUSUARIOSATRIB.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMUSUARIOSATRIB loCons = new JTFORMUSUARIOSATRIB(moList.moServidor);
        loCons.crearSelectSimple();
        //aNadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMUSUARIOSATRIB.class.getName() + "->cargar = No existe el registro de la tabla " + JTEEUSUARIOSATRIB.msCTabla);
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
}
