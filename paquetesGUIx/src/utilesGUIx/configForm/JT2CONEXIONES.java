/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package utilesGUIx.configForm;

import ListDatos.IServerServidorDatos;
import ListDatos.JSelectMotor;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosInternetLogin;
import javax.swing.JOptionPane;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesBD.estructuraBD.JConstructorEstructuraBDACCESS;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesBD.servidoresDatos.JServerServidorDatosDBASE;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantalla;

public class JT2CONEXIONES extends JT2CONEXIONESModelo {
    public JT2CONEXIONES(final JDatosGeneralesXML poDatosGeneralesXML, final IMostrarPantalla poMostrarPantalla) throws Exception {
        super(poDatosGeneralesXML, poMostrarPantalla, new ICONEXIONESMostrar() {
            public void mostrar(JConexion loConex, JT2CONEXIONESModelo poConexiones, IMostrarPantalla poMostrar) throws Exception {
                JPanelConexionesEDICION loPanel = new JPanelConexionesEDICION();
                loPanel.setDatos(loConex, poConexiones);
                loPanel.getParametros().setPlugInPasados(true);
                poMostrar.mostrarEdicion(loPanel,null, loPanel,JMostrarPantalla.mclEdicionDialog);
            }
        });
    }
    public static IServerServidorDatos getServer(JConexion poConex) throws Throwable{
        IServerServidorDatos loResult = null;        
        if(poConex.getConexion().getTipoConexion()==JServerServidorDatos.mclTipoBD 
                && poConex.getConexion().getTipoBD() == JSelectMotor.mclAccess){
            JServerServidorDatos loServer = null;        
            poConex.leerConfig();
            try{
                loServer = new JServerServidorDatos(poConex.getConexion());
            }catch(RuntimeException e1){
                //en linux no funciona
                JDepuracion.anadirTexto(JT2CONEXIONES.class.getName(), e1);
                loServer = new JServerServidorDatos();
            }catch(Throwable e){
                //en linux no funciona
                JDepuracion.anadirTexto(JT2CONEXIONES.class.getName(), e);
                loServer = new JServerServidorDatos();
            }
            JConstructorEstructuraBDACCESS loEstr = new JConstructorEstructuraBDACCESS(poConex.msPantRuta, false, false);
            loServer.getServerBD().setConstrucEstruc(loEstr);
            loServer.setTipo(JServerServidorDatos.mclTipoBD);
            loResult = loServer;
            
        }else if(poConex.getConexion().getTipoConexion()==JServerServidorDatos.mclTipoBD 
                && poConex.getConexion().getTipoBD() == JConexion.mclDBASE){
            poConex.leerConfig();
            JServerServidorDatosDBASE loServer = new JServerServidorDatosDBASE(poConex.msPantRuta);
            loResult=loServer;
        }else{

            JServerServidorDatos loServer;

            if(poConex.getConexion().getTipoConexion()==JServerServidorDatos.mclTipoBD){
                loServer = new JServerServidorDatos(poConex.getConexion());
                JConstructorEstructuraBDConnection loEstr = new JConstructorEstructuraBDConnection(loServer.getServerBD(), true, true);
                loServer.setConstrucEstruc(loEstr);
            } else {
                String lsUsu = JOptionPane.showInputDialog("Usuario", poConex.getConexion().getUSUARIO());
                String lsPass = JOptionPane.showInputDialog("Password", poConex.getConexion().getPASSWORD());
                poConex.getConexion().setPASSWORD(lsPass);
                poConex.getConexion().setUSUARIO(lsUsu);
                loServer = new JServerServidorDatos(poConex.getConexion());
                JServerServidorDatosInternetLogin loLogin = new JServerServidorDatosInternetLogin(
                        lsUsu
                        , lsPass);
                loServer.setLogin(loLogin);
                if(!loLogin.autentificar()){
                    throw new Exception("usuario incorrecto");
                }
                JConstructorEstructuraBDInternet loEstr = new JConstructorEstructuraBDInternet(loServer);
                loServer.setConstrucEstruc(loEstr);
            }
            loResult = loServer;
            
        }
        return loResult;
    }
}
