
/*
* JActualizarEstr.java
*
* Creado el 2/7/2008
*/

package utilesDoc.tablasExtend;

import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import utilesDoc.tablas.*;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;

public class JDocActualizarEstruc implements IActualizarEstruc {
    
    /** Creates a new instance of JActualizarEstr */
    public JDocActualizarEstruc() {
    }
    
    public JTableDefs getTablasOrigen() throws Exception {
        JTableDefs loTablasOrigen = new JTableDefs();
        loTablasOrigen.add(new JTableDef(
                JTDOCUMCLASIF.msCTabla, JTableDef.mclTipoTabla, 
                JTDOCUMCLASIF.masNombres, JTDOCUMCLASIF.malCamposPrincipales, 
                JTDOCUMCLASIF.malTipos, JTDOCUMCLASIF.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTDOCUMENTOS.msCTabla, JTableDef.mclTipoTabla, 
                JTDOCUMENTOS.masNombres, JTDOCUMENTOS.malCamposPrincipales, 
                JTDOCUMENTOS.malTipos, JTDOCUMENTOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTDOCUMTIPOS.msCTabla, JTableDef.mclTipoTabla, 
                JTDOCUMTIPOS.masNombres, JTDOCUMTIPOS.malCamposPrincipales, 
                JTDOCUMTIPOS.malTipos, JTDOCUMTIPOS.malTamanos)
                );
        
        return loTablasOrigen;
    }
    
    public void postProceso(IServerServidorDatos poServer) throws Exception {
        JTDOCUMCLASIF loClasi = new JTDOCUMCLASIF(poServer);
        loClasi.addNew();
        loClasi.getCODIGOCLASIFDOC().setValue("1");
        loClasi.getDESCRIPCION().setValue("General");
        loClasi.update(true);
        
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mclJPG, "jpg", true);
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mclpng, "png", true);
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mclgif, "gif", true);
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mcltif, "tif", true);
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mclpdf, "pdf", false);
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mcldoc, "doc", false);
        addTipoDoc(poServer, JTEEDOCUMTIPOS.mcldocx, "docx", false);
        
    }
    private void addTipoDoc(IServerServidorDatos poServer, int plCod, String psDescri, boolean pbEsim) throws Exception{
        JTDOCUMTIPOS loTipo = new JTDOCUMTIPOS(poServer);
        loTipo.addNew();
        loTipo.getCODIGOTIPODOC().setValue(plCod);
        loTipo.getEXTENSION().setValue(psDescri);
        loTipo.getIMAGENSN().setValue(pbEsim);
        loTipo.update(true);
    }
}
