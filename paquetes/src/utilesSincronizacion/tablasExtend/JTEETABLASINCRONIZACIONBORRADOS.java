/*
* JTEETABLASINCRONIZACIONBORRADOS.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion.tablasExtend;

import utilesSincronizacion.tablas.JTTABLASINCRONIZACIONBORRADOS;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesSincronizacion.tablas.*;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public class JTEETABLASINCRONIZACIONBORRADOS extends JTTABLASINCRONIZACIONBORRADOS {
    private JListDatos moListBorrados = null;
 
    /**
     * Crea una instancia de la clase intermedia para la tabla TABLASINCRONIZACIONBORRADOS incluyendole el servidor de datos
     */
    public JTEETABLASINCRONIZACIONBORRADOS(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarCache(){
        return false;
    }

    public static JTEETABLASINCRONIZACIONBORRADOS getTabla(final String psCODIGOBORRADO,final IServerServidorDatos poServer) throws Exception {
        JTEETABLASINCRONIZACIONBORRADOS loTabla = new JTEETABLASINCRONIZACIONBORRADOS(poServer);
        if(getPasarCache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOBORRADO});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOBORRADO}) ,false);
        }
        return loTabla;
    }

   public void valoresDefecto() throws Exception {   
   }
    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){
            }
    }
    protected void cargarControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{
    }
    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();
            }
            
            cargarControlador(psTabla, poMostrar);
        
    }
    public IResultado guardar() throws Exception{
        //se comprueba antes de guardar la clave pq despues de 
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave =  isMismaClave();
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));
        
        if(loResult.getBien() && 
           lbIsMismaClave) {
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
    
    public JListDatos getListDatosBorrados() throws Exception{
        if(moListBorrados==null){
            if(moList.moveFirst()){
                moListBorrados = new JListDatos();
                moListBorrados.setFields(JXMLSelectMotor.getCamposDesdeXML(getREGISTRO().getString()));
                do{
                    moListBorrados.addNew();
                    //rellenamos los campos con el registro borrado
                    rellenarCampos(moListBorrados.getFields(), getREGISTRO().getString());
                    moListBorrados.update(false);
                }while(moList.moveNext());
            }
        }
        return moListBorrados;
    }
    private void rellenarCampos(JFieldDefs poCamposC, String psRegistro) throws Exception {
        JFieldDefs loCamposAux = JXMLSelectMotor.getCamposDesdeXML(psRegistro);
        for(int i = 0 ; i < poCamposC.size(); i++){
            JFieldDef loCampo = loCamposAux.get(poCamposC.get(i).getNombre());
            if(loCampo!=null){
                poCamposC.get(i).setValue(loCampo.getString());
            }
        }
    }
    
}