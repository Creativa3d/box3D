/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesDoc.tablasExtend;

import ListDatos.IServerServidorDatos;
import archivosPorWeb.comun.IServidorArchivos;
import utilesGUIx.imgTrata.lista.IImagenFactory;

/**
 *
 * @author eduardo
 */
public interface IImagenFactoryDoc extends IImagenFactory {
    public IServerServidorDatos getServer();
    public void setServer(IServerServidorDatos moServer);
}
