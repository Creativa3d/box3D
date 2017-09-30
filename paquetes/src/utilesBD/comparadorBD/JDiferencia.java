/*
 * JDiferencia.java
 *
 * Created on 10 de agosto de 2005, 18:11
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesBD.comparadorBD;

import ListDatos.*;
import ListDatos.estructuraBD.*;


public class JDiferencia implements IDiferencia {
    private final String msDiferencia;
    private final String msEstructura;
    private final int mlTipoModificacion;
    private final int mlTipoEstructura;
    private final ISelectEjecutarSelect moActualizar;
    private final IServerServidorDatos moServer;

    /** Creates a new instance of JDiferencia */
    public JDiferencia(String psDiferencia, String psEstructura, int plTipoModificacion, int plTipoEstructura, ISelectEjecutarSelect poActualizar, IServerServidorDatos poServer) {
        msDiferencia = psDiferencia;
        msEstructura = psEstructura;
        mlTipoModificacion = plTipoModificacion;
        mlTipoEstructura = plTipoEstructura;
        moActualizar = poActualizar;
        moServer = poServer;
    }
    public String getDiferencia() {
        return msDiferencia;
    }
    public String getEstructura(){
        return msEstructura;
    }
    
    public int getTipoModificacion(){
        return mlTipoModificacion;
    }
    
    public int getTipoEstructura(){
        return mlTipoEstructura;
    }
    public void arreglarDiferencia() throws Exception {
        IResultado loResult = moServer.modificarEstructura(moActualizar);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
    }
    
}
