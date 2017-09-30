/*
 * JAccionDevolver.java
 *
 * Created on 22 de noviembre de 2004, 8:41
 */

package utilesBD.servletAcciones;

import ListDatos.IServerServidorDatos;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utiles.config.JDatosGeneralesXML;
import utilesBD.poolConexiones.PoolObjetos;
import utilesCRM.JDatosGeneralesCRM;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIxAvisos.calendario.JDatosGenerales;

public class JAccionDevolver implements IAccionDevolver {
    private static final long serialVersionUID = 33333353L;
    
    @Override
    public void inicializar(ServletContext context) throws Throwable {
        JDatosGeneralesXML loDatosGeneralesXML =  (JDatosGeneralesXML) context.getAttribute(JDatosGeneralesXML.class.getName());
        PoolObjetos.inicializar(loDatosGeneralesXML);
        
        JDatosGenerales loDatosGenerAvisos = new utilesGUIxAvisos.calendario.JDatosGenerales();
        loDatosGenerAvisos.setServer(new JServerServidorDevolverProxy(this));
        loDatosGenerAvisos.setMostrarPantalla(null);
        JGUIxConfigGlobalModelo.getInstancia().setDatosGeneralesCalendario(loDatosGenerAvisos);
        JGUIxConfigGlobalModelo.getInstancia().setDatosGeneralesCRM(new JDatosGeneralesCRM());

//        try {
//            System.out.println("Antes server");
//            final MBeanServer mbeanServer = 
//                ManagementFactory.getPlatformMBeanServer();
//            System.out.println("Inicial context");
//            final InitialContext initialContext = new InitialContext();
//            final Context envContext = (Context) initialContext.lookup("java:/comp/env");
//            System.out.println("register");
//            objectName = new ObjectName("utilesBD.poolConexiones"+context.getRealPath(".")+".type=JPoolObjetosJMX");
//            final JPoolObjetosJMX helloMbean = new JPoolObjetosJMX();
//            mbeanServer.registerMBean(helloMbean, objectName);
//        } catch (final Throwable e) {
//            JDepuracion.anadirTexto(getClass().getName(), e);
//        }        
    }
    
    @Override
    public IAccion getAccion(String psAccion, String psDir) {
        IAccion loAccion = null;
        String lsAccion = psAccion.toLowerCase();
        if(lsAccion.equalsIgnoreCase(ASelectDatos.mcsselectdatos)){
            loAccion = new ASelectDatos();
        }
        if(lsAccion.equalsIgnoreCase(AGuardarDatos.mcsguardardatos)){
            loAccion = new AGuardarDatos(psDir);
        }
        if(lsAccion.equalsIgnoreCase(AEntradaComprimida.mcsEntradaComprimida)){
            loAccion = new AEntradaComprimida();
        }
        if(lsAccion.equalsIgnoreCase(AListaPropiedadesHTTP.mcslistaFicherosPropiedades)){
            loAccion = new AListaPropiedadesHTTP(psDir);
        }
        if(lsAccion.equalsIgnoreCase(ALeerCorreo.mcsAccion)){
            loAccion = new ALeerCorreo();
        }
//        if(lsAccion.equalsIgnoreCase(AEstadoServidor.mcsEstadoServidor)){
//            loAccion = new AEstadoServidor();
//        }
        return loAccion;
    }

    @Override
    public IServerServidorDatos getServidor(boolean pbEdicion) throws Throwable {
        return PoolObjetos.getServidorDatos(pbEdicion);
    }

    @Override
    public void aplicarFiltros(IServerServidorDatos poServer, boolean pbEdicion, HttpServletRequest request, HttpServletResponse response, ServletContext poServletContext, Usuario poUsuario) throws Throwable {
    }

    @Override
    public void desAplicarFiltros(IServerServidorDatos poServer, boolean pbEdicion, HttpServletRequest request, HttpServletResponse response, ServletContext poServletContext, Usuario poUsuario) throws Throwable {
        poServer.borrarFiltrosTodos();
    }

    @Override
    public void terminar(ServletContext context) throws Throwable {
        if(JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCalendario()!=null){
            JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCalendario().getPlanificador().cancelarTimer();
        }        
        PoolObjetos.close();
//        try {
//            final MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
//            mbeanServer.unregisterMBean(objectName);
//        } catch (final Throwable e) {
//            JDepuracion.anadirTexto(getClass().getName(), e);
//        }             
    }
    
}

