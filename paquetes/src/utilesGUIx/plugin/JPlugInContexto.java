/*
 * JPlugIn.java
 *
 * Created on 7 de septiembre de 2006, 17:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.plugin;

import ListDatos.IServerServidorDatos;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public class JPlugInContexto implements IPlugInContexto {
    
    protected IMostrarPantalla moMostrarPantalla;
    protected IPlugInFrame moFormPrincipal;
    protected IProcesoThreadGroup moProcesoGroup;
    protected JPlugInContextoParametros moParametros = new JPlugInContextoParametros();
    /**
     * Servidor de datos, usar getServer()
     * @deprecated 
     */
    public IServerServidorDatos moServer;
    protected JDatosGeneralesXML moDatosGeneralesXML;
    
    public JPlugInContexto() {
    }
    
    /** Creates a new instance of JPlugIn */
    public JPlugInContexto(final IMostrarPantalla poMostrarPantalla, final IPlugInFrame poFormPrincipal) {
        moMostrarPantalla=poMostrarPantalla;
        moFormPrincipal=poFormPrincipal;
    }

    public IPlugInFrame getFormPrincipal() {
        return moFormPrincipal;
    }

    public IMostrarPantalla getMostrarPantalla() {
        return moMostrarPantalla;
    }

    public void setFormPrincipal(IPlugInFrame poFrame) {
        moFormPrincipal = poFrame;
    }

    public void setMostrarPantalla(IMostrarPantalla poMostrar) {
        moMostrarPantalla = poMostrar;
    }

    public synchronized IProcesoThreadGroup getThreadGroup() {
        return moProcesoGroup;
    }

    public synchronized void setThreadGroup(IProcesoThreadGroup poThread) {
        moProcesoGroup = poThread;
    }

    public JPlugInContextoParametros getPARAMETROS() {
        return moParametros;
    }

    public IServerServidorDatos getServer() {
        return moServer;
    }

    public JDatosGeneralesXML getDatosGeneralesXML() {
        return moDatosGeneralesXML;
    }

    public void setServer(IServerServidorDatos poServer) {
        moServer=poServer;
    }

    public void setDatosGeneralesXML(JDatosGeneralesXML poDatosXML) {
        moDatosGeneralesXML=poDatosXML;
    }


}
