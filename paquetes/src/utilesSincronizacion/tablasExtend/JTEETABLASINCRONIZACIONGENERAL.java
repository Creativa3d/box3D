/*
* JTEETABLASINCRONIZACIONGENERAL.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion.tablasExtend;

import utilesSincronizacion.tablas.JTTABLASINCRONIZACIONGENERAL;
import ListDatos.*;
import utilesSincronizacion.sincronizacion.JSincronizar;
import utilesSincronizacion.tablas.*;

public class JTEETABLASINCRONIZACIONGENERAL extends JTTABLASINCRONIZACIONGENERAL {
    /**
     * Crea una instancia de la clase intermedia para la tabla TABLASINCRONIZACIONGENERAL incluyendole el servidor de datos
     */
    public JTEETABLASINCRONIZACIONGENERAL(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
    }

    public static boolean getPasarCache(){
        return false;
    }

    public static JTEETABLASINCRONIZACIONGENERAL getTabla(final String psNOMBRE,final IServerServidorDatos poServer) throws Exception {
        JTEETABLASINCRONIZACIONGENERAL loTabla = new JTEETABLASINCRONIZACIONGENERAL(poServer);
        if(getPasarCache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psNOMBRE});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psNOMBRE}) ,false);
        }
        return loTabla;
    }

   public void valoresDefecto() throws Exception {   
   }
    public void inicializar() throws Exception {
        recuperarTodosNormalSinCache();
        addVariable(this, JSincronizar.mcsCampoNumeroTransaccion, "0");
    }
    
    private static void addVariable(JTEETABLASINCRONIZACIONGENERAL poDatos, String psATRIBUTO, String psValorDefecto) throws Exception {
        if(!poDatos.moList.buscar(JListDatos.mclTIgual, malCamposPrincipales, new String[]{psATRIBUTO})){
            poDatos.moList.addNew();
            poDatos.getNOMBRE().setValue(psATRIBUTO);
            poDatos.getVALOR().setValue(psValorDefecto);
            IResultado loResult = poDatos.moList.update(true);
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            }
        }
    }
    
    public String getAtributo(String psATRIBUTO){
        String lsValor = "";
        if(moList.buscar(JListDatos.mclTIgual, malCamposPrincipales, new String[]{psATRIBUTO})){
            lsValor = getVALOR().getString();        
        }
        return lsValor;
       
    }
    public void setAtributo(String psATRIBUTO, String psValor) throws Exception{
        if(!moList.buscar(JListDatos.mclTIgual, malCamposPrincipales, new String[]{psATRIBUTO})){
            moList.addNew();
        }
        getNOMBRE().setValue(psATRIBUTO);        
        getVALOR().setValue(psValor);        
        IResultado loResult = moList.update(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
       
    }
}