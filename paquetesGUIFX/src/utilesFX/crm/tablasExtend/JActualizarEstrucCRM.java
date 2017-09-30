
/*
* JActualizarEstr.java
*
* Creado el 2/7/2008
*/

package utilesFX.crm.tablasExtend;

import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import utilesCRM.tablas.JTCRMEMAILYNOTAS;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;

public class JActualizarEstrucCRM implements IActualizarEstruc {
    
    /** Creates a new instance of JActualizarEstr */
    public JActualizarEstrucCRM() {
    }
    
    @Override
    public JTableDefs getTablasOrigen() throws Exception {
        JTableDefs loTablasOrigen = new JTableDefs();
        
        loTablasOrigen.add(new JTableDef(
                JTCRMEMAILYNOTAS.msCTabla, JTableDef.mclTipoTabla, 
                JTCRMEMAILYNOTAS.masNombres, JTCRMEMAILYNOTAS.malCamposPrincipales, 
                JTCRMEMAILYNOTAS.malTipos, JTCRMEMAILYNOTAS.malTamanos)
                );
        
        return loTablasOrigen;
    }
    
    @Override
    public void postProceso(IServerServidorDatos poServer) throws Exception {
            
    }
}
