/*
* JActualizarEstr.java
*
* Creado el 9/12/2008
*/

package utilesGUIx.aplicacion.usuarios.tablasExtend;

import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIx.aplicacion.usuarios.tablas.JTUGRUPOLISTAPERMISOS;
import utilesGUIx.aplicacion.usuarios.tablas.JTUGRUPOS;
import utilesGUIx.aplicacion.usuarios.tablas.JTUSUARIOS;
import utilesGUIx.aplicacion.usuarios.tablas.JTUSUARIOSGRUPOS;
import utilesGUIx.aplicacion.usuarios.tablas.JTUSUARIOSLISTAPERMISOS;

public class JActualizarEstrucUsuarios implements IActualizarEstruc {
    private static final long serialVersionUID = 1L;

    
    /** Creates a new instance of JActualizarEstr */
    public JActualizarEstrucUsuarios() {
    }

    public JTableDefs getTablasOrigen() throws Exception{
        JTableDefs loTablasOrigen = new JTableDefs();

        loTablasOrigen.add(new JTableDef(
                JTUSUARIOS.msCTabla, JTableDef.mclTipoTabla,
                JTUSUARIOS.masNombres, JTUSUARIOS.malCamposPrincipales,
                JTUSUARIOS.malTipos, JTUSUARIOS.malTamanos)
                );


        loTablasOrigen.add(new JTableDef(
                JTUGRUPOLISTAPERMISOS.msCTabla, JTableDef.mclTipoTabla,
                JTUGRUPOLISTAPERMISOS.masNombres, JTUGRUPOLISTAPERMISOS.malCamposPrincipales,
                JTUGRUPOLISTAPERMISOS.malTipos, JTUGRUPOLISTAPERMISOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTUGRUPOS.msCTabla, JTableDef.mclTipoTabla,
                JTUGRUPOS.masNombres, JTUGRUPOS.malCamposPrincipales,
                JTUGRUPOS.malTipos, JTUGRUPOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTUSUARIOSGRUPOS.msCTabla, JTableDef.mclTipoTabla,
                JTUSUARIOSGRUPOS.masNombres, JTUSUARIOSGRUPOS.malCamposPrincipales,
                JTUSUARIOSGRUPOS.malTipos, JTUSUARIOSGRUPOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTUSUARIOSLISTAPERMISOS.msCTabla, JTableDef.mclTipoTabla,
                JTUSUARIOSLISTAPERMISOS.masNombres, JTUSUARIOSLISTAPERMISOS.malCamposPrincipales,
                JTUSUARIOSLISTAPERMISOS.malTipos, JTUSUARIOSLISTAPERMISOS.malTamanos)
                );

        return loTablasOrigen;
    }

    @Override
    public void postProceso(IServerServidorDatos poServer) throws Exception {
    }
}
