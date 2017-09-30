/*
* JTEEUGRUPOLISTAPERMISOS.java
*
* Creado el 31/3/2009
 */
package utilesGUIx.aplicacion.usuarios.tablasExtend;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.usuarios.tablas.JTUGRUPOLISTAPERMISOS;

public class JTEEUGRUPOLISTAPERMISOS extends JTUGRUPOLISTAPERMISOS {

    private static final long serialVersionUID = 1L;
    public static String[] masCaption = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaptions(msCTabla, masNombres);

    /**
     * Crea una instancia de la clase intermedia para la tabla
     * UGRUPOLISTAPERMISOS incluyendole el servidor de datos
     */
    public JTEEUGRUPOLISTAPERMISOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos, msCTabla, masNombres, malTipos, malCamposPrincipales, masCaption, malTamanos);
        moList.addListener(this);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarCache() {
        return false;
    }

    public static JTEEUGRUPOLISTAPERMISOS getTabla(final String psCODIGOGRUPO, final String psOBJETO, final String psACCION, final IServerServidorDatos poServer) throws Exception {
        JTEEUGRUPOLISTAPERMISOS loTabla = new JTEEUGRUPOLISTAPERMISOS(poServer);
        if (getPasarCache()) {
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND,
                    JListDatos.mclTIgual,
                    malCamposPrincipales,
                    new String[]{psCODIGOGRUPO, psOBJETO, psACCION});
            loTabla.moList.filtrar();
        } else {
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                    JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOGRUPO, psOBJETO, psACCION}), false);
        }
        return loTabla;
    }

}
