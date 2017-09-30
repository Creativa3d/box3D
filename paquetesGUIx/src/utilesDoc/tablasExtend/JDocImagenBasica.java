/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesDoc.tablasExtend;

import ListDatos.IFilaDatos;
import ListDatos.IServerServidorDatos;
import archivosPorWeb.comun.IServidorArchivos;
import utilesDoc.consultas.JTFORMDOCUMENTOS;
import utilesGUIx.imgTrata.lista.*;

/**
 *
 * @author eduardo
 */
public class JDocImagenBasica extends JImagenBasica {
    private final IServerServidorDatos moServer;


    public JDocImagenBasica(IServerServidorDatos poServer){
        super(-1,JTFORMDOCUMENTOS.lPosiNOMBRE);
        moServer=poServer;
    }

    @Override
    public synchronized void setDatos(IFilaDatos poFila) throws Throwable {
        moFila = poFila;
        limpiar();
        
        JTEEDOCUMENTOS loDoc = new JTEEDOCUMENTOS(moServer);
        loDoc.moList.add(poFila);
        loDoc.moList.moveFirst();
        
        
        leerRuta(loDoc.getFicheroLocal().getAbsolutePath());
        
    }


}
