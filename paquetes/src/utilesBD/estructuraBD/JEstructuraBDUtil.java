/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.estructuraBD;

import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JActualizarEstructura;
import ListDatos.estructuraBD.JIndiceDef;
import ListDatos.estructuraBD.JTableDef;
import utiles.IListaElementos;
import utiles.JDepuracion;

/**
 *
 * @author eduardo
 */
public class JEstructuraBDUtil {
    public static void addIndice(JTableDef poTableDef, String psCampo, IServerServidorDatos poServer) throws Exception{

//        //tablas origen
//        JTableDefs loTablasOrigen = new JTableDefs();
//        loTablasOrigen.add(poTableDef);
//        //tablas destino
//        JTableDefs loTablasDestino = utilesGUIx.aplicacion.actualizarEstruc.JActualizarEstruc.getTables(loTablasOrigen, JDatosGeneralesP.getDatosGenerales().getServer());
//        //tabla destino concreta
//        JTableDef loTableDefDatosGene = loTablasDestino.get(poTableDef.getNombre());
        
        //vemos la lista de indices
        IListaElementos loLista = poTableDef.getIndices().getListaIndices();
        //nombre indice
        String lsNombre = poTableDef.getNombre()+psCampo;
        boolean lbCrear=true;
        //si no existe el indice es q hay q crear el indice
        for(int i = 0 ; i < loLista.size(); i++){
            JIndiceDef loIndice = (JIndiceDef) loLista.get(i);
            if(loIndice.getNombreIndice().equalsIgnoreCase(lsNombre)){
                lbCrear=false;
            }
        }

        if(lbCrear){
            JIndiceDef loIndex = new JIndiceDef(lsNombre);
            loIndex.addCampoIndice(psCampo);

            IResultado loResult = poServer.modificarEstructura(
                    new JActualizarEstructura(
                        loIndex,
                        poTableDef,
                        JListDatos.mclNuevo,
                        null, null, null)
                    );
            if(!loResult.getBien()){
                JDepuracion.anadirTexto(JEstructuraBDUtil.class.getName(), loResult.getMensaje());
            }
        }
    }
    
    
}
