/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxAvisos.avisos.consulta;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JUtilTabla;
import ListDatos.estructuraBD.JFieldDef;
import java.util.List;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
/**
 *
 * @author cmorales
 */
public class JTFORMCUENTASCORREO implements IConsulta{

    private static JListDatos moListDatosEstatico;

    public static final int lPosiIdentificador;
    public static final int lPosiNOMBRE;
    public static final int lPosiDIRECCION;
    public static final int lPosiSERVIDORENTRANTE;
    public static final int lPosiSERVIDORSALIENTE;
    


     public static final int mclNumeroCampos;

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo){
        addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());
    }

    private static void addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){
        poCampo.setPrincipalSN(pbEsPrincipal);
        moListDatosEstatico.getFields().addField(poCampo);
    }

    static{
        moListDatosEstatico = new JListDatos();


        moListDatosEstatico.msTabla = "Cuentas correo";
        int lPosi = 0;

        
        addCampo("", new JFieldDef(JListDatos.mclTipoCadena, "Identificador", "", true));
        lPosiIdentificador = lPosi;
        lPosi++;

        addCampo("", new JFieldDef("Nombre"));
        lPosiNOMBRE = lPosi;
        lPosi++;

        addCampo("", new JFieldDef("Dirección"));
        lPosiDIRECCION= lPosi;
        lPosi++;

        addCampo("", new JFieldDef("Servidor entrante"));
        lPosiSERVIDORENTRANTE= lPosi;
        lPosi++;

        addCampo("", new JFieldDef("Servidor saliente"));
        lPosiSERVIDORSALIENTE= lPosi;
        lPosi++;


        mclNumeroCampos=lPosi;
    };



    public static JFieldDef getFieldEstatico(final int plPosi){
        return moListDatosEstatico.getFields(plPosi);
    }
    public static JListDatos getListDatosEstatico(){
        return moListDatosEstatico;
    }
    private JListDatos moList;
    private final List<JGUIxAvisosCorreo> moListaCorreos;
    
    public JTFORMCUENTASCORREO(List<JGUIxAvisosCorreo> poListaCorreos) {
        super();
        moListaCorreos=poListaCorreos;
        try {
            moList = JUtilTabla.clonarFilasListDatos(moListDatosEstatico, true);
        } catch (Exception ex) {
            utiles.JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    public JFieldDef getField(final int plPosi){
        return moList.getFields(plPosi);
    }

    @Override
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

    public static IFilaDatos getFilaDeConex(JGUIxAvisosCorreo poConex){
        JFilaDatosDefecto loFila = new JFilaDatosDefecto();
        loFila.addCampo(poConex.getIdentificador());
        loFila.addCampo(poConex.getEnviar().getCorreoNombre());
        loFila.addCampo(poConex.getEnviar().getCorreo());
        loFila.addCampo(poConex.getLeer().getServidor());
        loFila.addCampo(poConex.getEnviar().getServidor());
        
        loFila.setTipoModif(poConex.getTipoModif());
        return loFila;
    }

    @Override
    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {
        moList.clear();
        for(int i = 0 ; i < moListaCorreos.size(); i++){
            JGUIxAvisosCorreo loConex = moListaCorreos.get(i);
            if(loConex.getTipoModif()!=JListDatos.mclBorrar){
                moList.add(getFilaDeConex(loConex));
            }
        }
    }

    @Override
    public JListDatos getList() {
        return moList;
    }

    @Override
    public boolean getPasarCache() {
        return false;
    }

    public List<JGUIxAvisosCorreo> getListaCorreos() {
        return moListaCorreos;
    }
    
}
