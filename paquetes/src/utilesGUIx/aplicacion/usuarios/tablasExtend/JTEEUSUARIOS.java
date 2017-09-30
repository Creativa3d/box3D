/*
* JTUSUARIOS2.java
*
* Creado el 24/5/2006
 */
package utilesGUIx.aplicacion.usuarios.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

import java.util.HashMap;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.usuarios.consultas.JTFORMUSUARIOS;
import utilesGUIx.aplicacion.usuarios.tablas.JTUSUARIOS;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

public class JTEEUSUARIOS extends JTUSUARIOS {

    private static final long serialVersionUID = 1L;
    public static String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);

    protected HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla USUARIOS
     * incluyendole el servidor de datos
     */
    public JTEEUSUARIOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache() {
        return true;
    }

    public static JTEEUSUARIOS getTabla(final String psCODIGOUSUARIO, final IServerServidorDatos poServer) throws Exception {
        JTEEUSUARIOS loTabla = new JTEEUSUARIOS(poServer);
        if (getPasarACache()) {
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND,
                    JListDatos.mclTIgual,
                    malCamposPrincipales,
                    new String[]{psCODIGOUSUARIO});
            loTabla.moList.filtrar();
        } else {
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                    JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOUSUARIO}), false);
        }
        return loTabla;
    }

    public static JTEEUSUARIOS getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                poFila.msCampo(lPosiCODIGOUSUARIO),
                poServer);
    }

    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = malCamposPrincipales;
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos = true;
        loParam.mbMensajeSiNoExiste = true;

        loParam.malDescripciones = new int[]{
            JTFORMUSUARIOS.lPosiNOMBRE
        };
        loParam.masTextosDescripciones = null;

        loParam.moControlador = null;

        JTFORMUSUARIOS loConsulta = new JTFORMUSUARIOS(poServer);
        loConsulta.crearSelectSimple();
        loConsulta.refrescar(false, false);
        loParam.moTabla = loConsulta;

        return loParam;
    }

    @Override
    public void valoresDefecto() throws Exception {
    }

    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception {
        if (!pbEsMismaclave) {
            moListaRelaciones = null;
            moListaRelaciones = new HashMap();
        }
    }

    @Override
    public IResultado guardar() throws Exception {
        //se comprueba antes de guardar la clave pq despues de
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave = isMismaClave();
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));

        if (loResult.getBien()
                && lbIsMismaClave) {
//            JTEESUBFAMILIAS loSub = JTEESUBFAMILIAS.getTabla(getCODIGOFAMILIA().getString(),moList.moServidor);
//            if(loSub.moList.moveFirst()){
//                do{
//                    loSub.getUSALOTESSN().setValue(getUSALOTESSN().getBoolean());
//                    loSub.moList.update(false);
//                }while(loSub.moList.moveNext());
//                JActualizarConj loAct = new JActualizarConj("","","");
//                loAct.crearUpdateAPartirList(loSub.moList);
//                loResult.addResultado(loSub.moList.moServidor.actualizarConj(loAct));
//
//            }

        }
        return loResult;
    }
}
