/*
 * JCConflictos.java
 *
 * Created on 6 de octubre de 2008, 12:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion.sincronizacion.consultas;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.JSelectCampo;
import ListDatos.JUtilTabla;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import utiles.IListaElementos;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesSincronizacion.sincronizacion.conflictos.JConflicto;

public class JCConflictos extends JSTabla  implements IConsulta {
    private static JListDatos moListDatosEstatico;
    /**
     * Variables para las posiciones de los campos
     */
    public static final int lPosiGanaServidor;
    public static final int lPosiTABLA;
    public static final int lPosiDatosServidor;
    public static final int lPosiDatosCliente;
    public static final int lPosiTipoActualizacion;
    public static final int lPosiIndiceBase;

    private static final int mclNumeroCampos;

    private IListaElementos moConflictos;

    private boolean mbGanaCliente;

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo){
        addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());
    }

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){
        addCampo(psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);
    }

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado){
        poCampo.setPrincipalSN(pbEsPrincipal);
        moListDatosEstatico.getFields().addField(poCampo);
    }

    private static void addCampoFuncion(final String psNombreTabla, final JFieldDef poCampo, final int plFuncion){
        addCampo(psNombreTabla, poCampo, false, plFuncion, false);
    }
    private static void addCampoYGrupo(final String psNombreTabla, final JFieldDef poCampo){
        addCampo(psNombreTabla, poCampo, false, JSelectCampo.mclFuncionNada, true);
    }

    static{
        moListDatosEstatico = new JListDatos();

        moListDatosEstatico.msTabla = "Conflictos";
        int lPosi = 0;

        addCampo("Conflictos", new JFieldDef(JListDatos.mclTipoBoolean,"Gana servidor","Gana servidor", true));
        lPosiGanaServidor = lPosi;
        lPosi++;
        
        addCampo("Conflictos", new JFieldDef(JListDatos.mclTipoCadena,"Tabla","Tabla", true));
        lPosiTABLA = lPosi;
        lPosi++;
        
        addCampo("Conflictos", new JFieldDef(JListDatos.mclTipoCadena,"Datos servidor","Datos servidor", true));
        lPosiDatosServidor = lPosi;
        lPosi++;
        
        addCampo("Conflictos", new JFieldDef(JListDatos.mclTipoCadena,"Datos Cliente","Datos Cliente", true));
        lPosiDatosCliente = lPosi;
        lPosi++;
        
        addCampo("Conflictos", new JFieldDef(JListDatos.mclTipoCadena,"Tipo actualización","Tipo actualización", true));
        lPosiTipoActualizacion = lPosi;
        lPosi++;
        
        addCampo("Conflictos", new JFieldDef(JListDatos.mclTipoCadena,"Indice Base","Indice Base", true));
        lPosiIndiceBase = lPosi;
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
    public JCConflictos() {
        super();
        try {
            moList = JUtilTabla.clonarFilasListDatos(moListDatosEstatico, true);
        } catch (Exception ex) {
            utiles.JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        moList.addListener(this);
    }
     public boolean getPasarCache(){
        return false;
    }

    public JFieldDef getField(final int plPosi){
        return moList.getFields(plPosi);
    }

    public void addFilaPorClave(final IFilaDatos poFila) throws Exception {
        switch(poFila.getTipoModif()){
            case JListDatos.mclBorrar:
                moList.borrar(false);
                break;
            case JListDatos.mclEditar:
                moList.getFields().cargar(poFila);
                moList.update(false);
                break;
            case JListDatos.mclNuevo:
                moList.addNew();
                moList.getFields().cargar(poFila);
                moList.update(false);
                break;
            default:
                throw new Exception("Tipo modificación incorrecto");
        }
    }

    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {
        for(int i = 0 ; i < moConflictos.size(); i++){
            JConflicto loConflicto = (JConflicto)moConflictos.get(i);
            if(loConflicto.mbSinHacer){
                moList.addNew();
                if(loConflicto.moCamposCliente!=null){
                    moList.getFields(lPosiDatosCliente).setValue(loConflicto.moCamposCliente.toString());
                }
                if(loConflicto.moCamposServidor!=null){
                    moList.getFields(lPosiDatosServidor).setValue(loConflicto.moCamposServidor.toString());
                }
                moList.getFields(lPosiTABLA).setValue(loConflicto.msTabla);
                moList.getFields(lPosiGanaServidor).setValue(!mbGanaCliente);
                moList.getFields(lPosiIndiceBase).setValue(i);
                switch(loConflicto.getTipo()){
                    case JConflicto.mclBorradoServidorYModificadoCliente:
                        moList.getFields(lPosiTipoActualizacion).setValue("Borrado Servidor y Actualizado cliente");
                        break;
                    case JConflicto.mclBorradoClienteYModificadoServidor:
                        moList.getFields(lPosiTipoActualizacion).setValue("Borrado Cliente y Actualizado en servidor");
                        break;
                    case JConflicto.mclModificado:
                    default:    
                        moList.getFields(lPosiTipoActualizacion).setValue("Actualizado en cliente y servidor");
                }

                moList.update(false);
            }
        }
    }

    public JListDatos getList() {
        return moList;
    }
    public void setDatos(IListaElementos poConflictos, boolean pbGanaCliente){
        moConflictos = poConflictos;
        mbGanaCliente = pbGanaCliente;
    }
    public IListaElementos getListaConflictos(){
        return moConflictos;
    }
}
