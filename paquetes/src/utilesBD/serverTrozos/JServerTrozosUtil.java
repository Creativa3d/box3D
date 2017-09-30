/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.serverTrozos;

import ListDatos.IServerServidorDatos;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosBD;
import ListDatos.JServerServidorDatosInternet;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JT2CONEXIONESModelo;

/**
 *
 * @author eduardo
 */
public class JServerTrozosUtil {
    public static IServerServidorDatosTrozos getServerTrozos(IServerServidorDatos loServerO) throws Throwable{
        if(loServerO instanceof JServerServidorDatos){
            JServerServidorDatos loServer = (JServerServidorDatos) loServerO;
            if(loServer.getTipo() == JServerServidorDatos.mclTipoBD){
                return new JServerServidorDatosBDTrozos(loServer.getServerBD());
            }else{
                return new JServerServidorDatosInternetTrozos(loServer.getServerInternet());
            }
        }else if(loServerO instanceof JServerServidorDatosBD){
            JServerServidorDatosBD loServer = (JServerServidorDatosBD) loServerO;
            return new JServerServidorDatosBDTrozos(loServer);
        }else if(loServerO instanceof JServerServidorDatosInternet){
            JServerServidorDatosInternet loServer = (JServerServidorDatosInternet) loServerO;
            return new JServerServidorDatosInternetTrozos(loServer);
        }else{
            return new JServerServidorDatosRestoTrozos(loServerO);
        }
    }
}
