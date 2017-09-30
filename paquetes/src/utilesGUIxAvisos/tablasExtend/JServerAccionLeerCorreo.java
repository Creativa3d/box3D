/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.tablasExtend;

import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JResultado;
import ListDatos.JServerEjecutarAbstract;

/**
 *
 * @author eduardo
 */
public class JServerAccionLeerCorreo extends JServerEjecutarAbstract {
    private static final long serialVersionUID = 1L;
    
    public String msIdentificador=null;
    public JServerAccionLeerCorreo(String psIdentificador ){
        msIdentificador=psIdentificador;
    }
    @Override
    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception {
        return new JResultado(msIdentificador, true);
    }
    
}
