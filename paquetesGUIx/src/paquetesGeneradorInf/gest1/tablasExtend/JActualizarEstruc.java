
/*
* JActualizarEstr.java
*
* Creado el 2/7/2008
*/

package paquetesGeneradorInf.gest1.tablasExtend;

import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import paquetesGeneradorInf.gest1.tablas.*;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;

public class JActualizarEstruc implements IActualizarEstruc {
    
    /** Creates a new instance of JActualizarEstr */
    public JActualizarEstruc() {
    }
    
    public JTableDefs getTablasOrigen() throws Exception {
        JTableDefs loTablasOrigen = new JTableDefs();
        loTablasOrigen.add(new JTableDef(
                JTSQLGENERADOR.msCTabla, JTableDef.mclTipoTabla, 
                JTSQLGENERADOR.masNombres, JTSQLGENERADOR.malCamposPrincipales, 
                JTSQLGENERADOR.malTipos, JTSQLGENERADOR.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTSQLGENERADORATRIB.msCTabla, JTableDef.mclTipoTabla, 
                JTSQLGENERADORATRIB.masNombres, JTSQLGENERADORATRIB.malCamposPrincipales, 
                JTSQLGENERADORATRIB.malTipos, JTSQLGENERADORATRIB.malTamanos)
                );
        
        return loTablasOrigen;
    }
    public void postProceso(IServerServidorDatos poServer) throws Exception {
    }
}
