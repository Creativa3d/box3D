
/*
* JActualizarEstr.java
*
* Creado el 2/7/2008
*/

package utilesGUIx.aplicacion.auxiliares.tablasExtend;

import ListDatos.ECampoError;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JActualizarConj;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;

import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIx.aplicacion.auxiliares.tablas.JTAUXILIARES;
import utilesGUIx.aplicacion.auxiliares.tablas.JTAUXILIARESGRUPOS;
import utilesGUIx.aplicacion.auxiliares.tablas.JTDATOSGENERALES2;
import utilesGUIxAvisos.tablas.JTCUENTASCORREO;

public class JActualizarEstrucAux implements IActualizarEstruc {
    
    /** Creates a new instance of JActualizarEstr */
    public JActualizarEstrucAux() {
    }
    
    @Override
    public JTableDefs getTablasOrigen() throws Exception {
        JTableDefs loTablasOrigen = new JTableDefs();
        loTablasOrigen.add(new JTableDef(
                JTAUXILIARES.msCTabla, JTableDef.mclTipoTabla, 
                JTAUXILIARES.masNombres, JTAUXILIARES.malCamposPrincipales, 
                JTAUXILIARES.malTipos, JTAUXILIARES.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTAUXILIARESGRUPOS.msCTabla, JTableDef.mclTipoTabla, 
                JTAUXILIARESGRUPOS.masNombres, JTAUXILIARESGRUPOS.malCamposPrincipales, 
                JTAUXILIARESGRUPOS.malTipos, JTAUXILIARESGRUPOS.malTamanos)
                );

        loTablasOrigen.add(new JTableDef(
                JTDATOSGENERALES2.msCTabla, JTableDef.mclTipoTabla, 
                JTDATOSGENERALES2.masNombres, JTDATOSGENERALES2.malCamposPrincipales, 
                JTDATOSGENERALES2.malTipos, JTDATOSGENERALES2.malTamanos)
                );
        return loTablasOrigen;
    }
    @Override
    public void postProceso(IServerServidorDatos poServer) throws Exception {
        
    }
    public static void actualizarDatosTabla(final JSTabla poOrigen, final JSTabla poDestino, boolean pbEDIT) throws Exception{
        poDestino.recuperarTodosNormal(false);
        poDestino.moList.ordenar(poDestino.moList.getFields().malCamposPrincipales());
        JListDatos loList = new JListDatos (poDestino.moList, false);
        if(poOrigen.moList.moveFirst()){
            do{
                if(poDestino.moList.buscarBinomial(
                        poDestino.moList.getFields().malCamposPrincipales(),
                        poOrigen.moList.getFields().masCamposPrincipales()
                        )){
                    if(pbEDIT){
                        poDestino.moList.getFields().cargar(poOrigen.moList.moFila());
                        poDestino.moList.update(false, false);
                    }
                }else{
                    loList.addNew();
                    loList.getFields().cargar(poOrigen.moList.moFila());
                    loList.update(false);
                }
            }while(poOrigen.moList.moveNext());
            JActualizarConj loAct = new JActualizarConj(null, null, null);
            loAct.crearUpdateAPartirList(poDestino.moList);
            loAct.crearUpdateAPartirList(loList);
            IResultado loResult = loList.moServidor.ejecutarServer(loAct);
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            }
        }
    }
    
    
    public static void addGrupoAux(JTEEAUXILIARESGRUPOS loGruposNew, int plCodigo, String psDescripcion) throws ECampoError{
        loGruposNew.moList.addNew();
        loGruposNew.getCODIGOGRUPOAUX().setValue(plCodigo);
        loGruposNew.getDESCRIPCION().setValue(psDescripcion);
        loGruposNew.moList.update(false);
    }

}
