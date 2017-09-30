/*
 * JSincronizarCommand.java
 *
 * Created on 14 de octubre de 2008, 10:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion;

import ListDatos.JServerServidorDatos;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;
import utilesSincronizacion.forms.JFormConflictos;
import utilesSincronizacion.sincronizacion.JSincronizar;
import utilesSincronizacion.sincronizacion.JSincronizarParam;

public class JSincronizarCommand {
    
    /** Creates a new instance of JSincronizarCommand */
    public JSincronizarCommand() {
        
    }
    public void ejecutar() throws Throwable {
        
        //inicializamos la BD del SERVIDOR
        JDatosGeneralesP.getDatosGenerales().setServidor(JServerServidorDatos.mclTipoBD, "");
        //inicializamos la BD del CLIENTE
        JServerServidorDatos loServer = new JServerServidorDatos(
                        JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(
                            JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente/" + 
                                JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME),
                        JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(
                            JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente/" + 
                                JDatosGeneralesXML.PARAMETRO_Conexion),
                        JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(
                            JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente/" + 
                                JDatosGeneralesXML.PARAMETRO_Usuario),
                        JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(
                            JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente/" + 
                                JDatosGeneralesXML.PARAMETRO_Password), 
                        JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(
                            JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente/" + 
                                JDatosGeneralesXML.PARAMETRO_TipoSQL)
                        );
                loServer.setConstrucEstruc(
                        new JConstructorEstructuraBDConnection(
                            loServer.getConec()));
                
                
        JSincronizarParam loParam = new JSincronizarParam(new JFormConflictos());
        loParam.mbVisual=false;
        JSincronizar loSincro = new JSincronizar(
                JDatosGeneralesP.getDatosGenerales().moServer,
                loServer,
                loParam
                );
        JFormSincronizarProceso loForm = new JFormSincronizarProceso();
        loForm.setVisible(true);
        JDatosGeneralesP.getDatosGenerales().setDesktopPane1(null, loForm.jProcesoThreadGroup1);
        JDatosGeneralesP.getDatosGenerales().getThreadGroup().addProcesoYEjecutar(loSincro);
        
        synchronized (this){
            while(JDatosGeneralesP.getDatosGenerales().getThreadGroup().getListaProcesos().size()>0){
                try{
                    wait(2000);
                }catch(Exception e){
                    
                }
            }
        }
        
        loForm.dispose();
    }
    
    public static void main(String args[]) {
        JSincronizarCommand loSincronizarCommand = new JSincronizarCommand();
        try {
            loSincronizarCommand.ejecutar();
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(JSincronizarCommand.class.getName(), ex);
        }
        System.exit(0);
        
    }

    
    
    
    
}
