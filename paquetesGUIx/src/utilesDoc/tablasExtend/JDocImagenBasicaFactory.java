/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesDoc.tablasExtend;

import ListDatos.IServerServidorDatos;
import archivosPorWeb.comun.IServidorArchivos;
import utilesGUIx.imgTrata.lista.IImagen;


public class JDocImagenBasicaFactory implements IImagenFactoryDoc {

    private IServidorArchivos moServidorArchivos;
    private IServerServidorDatos moServer;

    public JDocImagenBasicaFactory(){
    }
    public JDocImagenBasicaFactory(IServidorArchivos poServidorArchivos, IServerServidorDatos poServer){
        moServidorArchivos = poServidorArchivos;
        moServer=poServer;
    }

    public IImagen getImagenNueva() {
        return new JDocImagenBasica(getServer());
    }


    /**
     * @return the moServer
     */
    public IServerServidorDatos getServer() {
        return moServer;
    }

    /**
     * @param moServer the moServer to set
     */
    public void setServer(IServerServidorDatos moServer) {
        this.moServer = moServer;
    }
}
