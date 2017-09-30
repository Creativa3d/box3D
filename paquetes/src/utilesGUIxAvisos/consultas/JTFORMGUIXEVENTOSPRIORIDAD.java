/*
* JCGUIXPRIORIDAD.java
*
* Creado el 18/2/2012
*/
package utilesGUIxAvisos.consultas;

import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOSPRIORIDAD;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIxAvisos.tablas.*;
import utilesGUIxAvisos.tablasExtend.*;

public class JTFORMGUIXEVENTOSPRIORIDAD extends JSTabla  implements IConsulta {
    private static final long serialVersionUID = 1L;
    private JSelect moSelect;
    private final static JSelect moSelectEstatica;
    private final static JListDatos moListDatosEstatico;
    
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiGUIXPRIORIDAD;
    public static final int lPosiNOMBRE;

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
        moSelectEstatica = new JSelect(JTEEGUIXEVENTOSPRIORIDAD.msCTabla);

        JTEEGUIXEVENTOSPRIORIDAD loGUIXPRIORIDAD = new JTEEGUIXEVENTOSPRIORIDAD(null);
        moListDatosEstatico.msTabla = JTEEGUIXEVENTOSPRIORIDAD.msCTabla ;
        int lPosi = 0;

        addCampo(JTEEGUIXEVENTOSPRIORIDAD.msCTabla, loGUIXPRIORIDAD.moList.getFields(JTEEGUIXEVENTOSPRIORIDAD.lPosiGUIXPRIORIDAD));
        lPosiGUIXPRIORIDAD = lPosi;
        lPosi++;

        addCampo(JTEEGUIXEVENTOSPRIORIDAD.msCTabla, loGUIXPRIORIDAD.moList.getFields(JTEEGUIXEVENTOSPRIORIDAD.lPosiNOMBRE));
        lPosiNOMBRE = lPosi;
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
      * Crea una instancia de la clase intermedia para la tabla GUIXPRIORIDADincluyendole el servidor de datos
      */
    public JTFORMGUIXEVENTOSPRIORIDAD(IServerServidorDatos poServidorDatos) {
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
        String[] lasValores = new String[JTEEGUIXEVENTOSPRIORIDAD.malCamposPrincipales.length];
        for(int i = 0 ; i < JTEEGUIXEVENTOSPRIORIDAD.malCamposPrincipales.length; i++){
            lasValores[i] = poFila.msCampo(JTEEGUIXEVENTOSPRIORIDAD.malCamposPrincipales[i]);
        }
        JListDatosFiltroElem loFiltro = 
            new JListDatosFiltroElem(
                  JListDatos.mclTIgual, 
                  JTEEGUIXEVENTOSPRIORIDAD.malCamposPrincipales,
                  lasValores
            );
        loFiltro.inicializar(JTEEGUIXEVENTOSPRIORIDAD.msCTabla, JTEEGUIXEVENTOSPRIORIDAD.malTipos, JTEEGUIXEVENTOSPRIORIDAD.masNombres);
        //creamos un objeto consulta con la select simple
        JTFORMGUIXEVENTOSPRIORIDAD loCons = new JTFORMGUIXEVENTOSPRIORIDAD(moList.moServidor);
        loCons.crearSelectSimple();
        //añadimos la condicion de los campos principales
        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        //refrescamos
        loCons.refrescar(false, false);
        //cargamos los datos 
        if(loCons.moList.moveFirst()){
            moList.getFields().cargar(loCons.moList.moFila());
        }else{
            throw new Exception(JTFORMGUIXEVENTOSPRIORIDAD.class.getName() + "->cargar = No existe el registro de la tabla " + JTEEGUIXEVENTOSPRIORIDAD.msCTabla);
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
}
