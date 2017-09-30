/*
 * IPlugIn.java
 *
 * Created on 7 de septiembre de 2006, 17:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import ListDatos.IServerServidorDatos;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public interface IPlugInContexto {

    public JPlugInContextoParametros getPARAMETROS();

    public IPlugInFrame getFormPrincipal();
    public IMostrarPantalla getMostrarPantalla();
    public IProcesoThreadGroup getThreadGroup();
    public IServerServidorDatos getServer();
    public JDatosGeneralesXML getDatosGeneralesXML();

    public void setFormPrincipal(IPlugInFrame poFrame);
    public void setMostrarPantalla(IMostrarPantalla poMostrar);
    public void setThreadGroup(IProcesoThreadGroup poThread);
    public void setServer(IServerServidorDatos poServer);
    public void setDatosGeneralesXML(JDatosGeneralesXML poDatosXML);
    
}
