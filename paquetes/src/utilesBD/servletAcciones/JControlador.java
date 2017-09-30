/*
 * Controlador.java
 *
 * Created on 4 de mayo de 2004, 18:00
 */
package utilesBD.servletAcciones;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import ListDatos.*;
import utiles.JDepuracion;
import utiles.config.*;

public class JControlador extends HttpServlet {
    private static final long serialVersionUID = 33333354L;
    public static final String mcsUsuario = "Usuario";
    
    private static final String mcsHome = "index.jsp";
    private static final String mcsLogin = "index.jsp";

    public static final String mcsNivelDepuracion="NivelDepuracion";
    public static final String mcsAccionDevolver = "ACCIONDEVOLVER";
    
    
    
    private IAccionDevolver moAccionDevolver;
   
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        JDatosGeneralesXML loDatosGeneralesXML;
        //FICHERO DE CONFIGURACION, PASO A XML Y LECTURA
        //transformamos a xml
        File loFileDir=new java.io.File(getClass().getClassLoader().getResource("/").getFile());
        File loFileXml = new File(loFileDir,"ConfigurationParameters.xml");
        if(!loFileXml.exists()){
            File loFileProperties = new File(loFileDir,"ConfigurationParameters.properties");
            if(loFileProperties.exists()){
                try {
                    JDatosGeneralesXML.convertirXML(loFileProperties, loFileXml);
                } catch (Throwable ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }
        }
        //fichero CON la ruta y la EXTENSION
        String lsFicheroSinExtension = loFileXml.getAbsolutePath();
        //quitamos la extension
        lsFicheroSinExtension=lsFicheroSinExtension.substring(0, lsFicheroSinExtension.length()-4);
        loDatosGeneralesXML = new JDatosGeneralesXML(lsFicheroSinExtension);
        try {
            loDatosGeneralesXML.leer();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        config.getServletContext().setAttribute(JDatosGeneralesXML.class.getName(), loDatosGeneralesXML);
        
        //LECTURA DE PROPIEDADES
        try{
            String lsClass = loDatosGeneralesXML.getPropiedad(mcsAccionDevolver);
            moAccionDevolver =(IAccionDevolver)Class.forName(lsClass).newInstance();
        }catch(ClassNotFoundException e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            moAccionDevolver = new JAccionDevolver();
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            moAccionDevolver = new JAccionDevolver();
        }
        try{
            JDepuracion.mlNivelDepuracion = Integer.valueOf(loDatosGeneralesXML.getPropiedad(mcsNivelDepuracion)).intValue();
        }catch(Exception e){}
        try{
            moAccionDevolver.inicializar(config.getServletContext());
            config.getServletContext().setAttribute(JControlador.class.getName()+"init", Boolean.TRUE);
            config.getServletContext().setAttribute(IAccionDevolver.class.getName(), moAccionDevolver);
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }
    public IAccionDevolver getAccionDevolver(){
        return moAccionDevolver;
    }

    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        //ENCABEZADOS PARA CORS
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","Origin, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Date, X-Api-Version, X-File-Name, Authorization");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials","true");        
        
        String lsDir = getServletConfig().getServletContext().getRealPath("/");
        
        
        if(getServletConfig().getServletContext().getAttribute(JControlador.class.getName()+"init")==null){
            try{
                moAccionDevolver.inicializar(getServletConfig().getServletContext());
                getServletConfig().getServletContext().setAttribute(JControlador.class.getName()+"init", Boolean.TRUE);
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        
        
        java.util.Locale.setDefault(new java.util.Locale("es","ES"));
        response.setLocale(new java.util.Locale("es","ES"));
        
        //Objeto peticion
        Peticion loPeticion = new Peticion(request, lsDir, moAccionDevolver);
        
        //si rompe el flujo de navegacion vuelve a la pagina de inicio
        if(loPeticion.errorNavegacion()){
            visualizar(mcsHome, request, response);
            return;
        }
        
        //conseguimos el usuario actual
        HttpSession loSesion = request.getSession(false);
        Usuario loUsuario = null;
        if(loSesion!=null){
            loUsuario = (Usuario)loSesion.getAttribute(mcsUsuario);
        }
        //vemos si el recurso a acceder necesita validacion
        if(loPeticion.necesitaValidar(loUsuario)){
//            visualizar(mcsLogin, request, response);
            return;
        }
        
        //Obtiene la accion a ejecutar
        IAccion loAcc = loPeticion.getAccion();

        IServerServidorDatos loServer = null;
        String lsVista = null;
        try{//se hace asi por la posible reentrada desde visualizar la vista (si es .ctrl)
            try{
                if(loAcc.getNecesitaConexionBD()){
                    loServer = moAccionDevolver.getServidor(
                            loAcc.getConexionEdicion()
                            );
                    loServer.getParametros().setTAG(loUsuario);
                    moAccionDevolver.aplicarFiltros(
                            loServer,
                            loAcc.getConexionEdicion(),
                            request, response, getServletConfig().getServletContext(),
                            loUsuario
                            );

                }

                //ejecuta la accion y obtiene la vista
                lsVista = loAcc.ejecutar(request, response, getServletConfig().getServletContext(), loServer);

            }finally{
                if(loServer != null) {
                    try{
                        moAccionDevolver.desAplicarFiltros(
                                loServer,
                                loAcc.getConexionEdicion(),
                                request, response, getServletConfig().getServletContext(),
                                loUsuario
                                );
                    }finally{
                        loServer.close();
                    }
                }
            }
            //visualiza el resultado
            if(lsVista!=null){
                visualizar(lsVista, request, response);
            }
        }catch(Throwable e){
//            e.printStackTrace(response.getWriter());
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        
    }
    public static void visualizar(String psVista, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lsVistaContexto ="/" + psVista; // request.getContextPath() + 
        RequestDispatcher rd = request.getRequestDispatcher(lsVistaContexto);
        rd.forward(request, response);
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Controlador de paginas";
    }
//no funciona
    public void destroy() {
        try {
            moAccionDevolver.terminar(getServletConfig().getServletContext());
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        super.destroy();
    }
    
}
