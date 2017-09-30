/*
* JDatosGenerales.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion;

import ListDatos.*;
import ListDatos.config.JDevolverTextos;
import java.awt.Component;
import java.awt.Frame;
import java.util.ResourceBundle;
import javax.swing.*;
import utiles.JComunicacion;
import utiles.JDepuracion;
import utiles.config.*;
import utiles.xml.dom.Element;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesBD.relaciones.*;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JConexionIO;
import utilesGUIx.configForm.antig.JFormConfigConexiones;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;



public class JDatosGenerales extends JMostrarPantallaAbstract{
    public IServerServidorDatos moServer;
    public String jsessionid = null;

    public String msCodUsuario;
    public String msUsuario;
    private JMostrarPantalla moMostrarPantalla;

    public JBorradoCompleto moBorradoCompleto = null;

    public static final String mcsLookAndFeel = "LookAndFeel";
    public static final String mcsLookAndFeelTema = "LookAndFeelTema";
    public static final String mcsTipoFiltroRapido="TipoFiltroRapido";
    private JDevolverTextos moTextos=null;

    private int mlTipo=0;
    private JDatosGeneralesXML moDatosGeneralesXML;
    private IProcesoThreadGroup moThreadGroup;
    public JDatosGenerales() {
        moDatosGeneralesXML = new JDatosGeneralesXML("ConfigurationParametersutilesSincronizacion");
        try {
            moDatosGeneralesXML.leer();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }
//
//    public String getTipoFiltroRapido() {
//        return moDatosGeneralesXML.getPropiedad(mcsTipoFiltroRapido, JPanelGeneralParametros.mcsTipoFiltroRapidoPorTodos);
//    }
    public IProcesoThreadGroup getThreadGroup(){
        return moThreadGroup;
    }

    public void setServidor(int plTipo, String psDireccion) throws Throwable {
        mlTipo = plTipo;
        switch(plTipo){
            case JServerServidorDatos.mclTipoBD:
                        moServer = (new JServerServidorDatos(moDatosGeneralesXML.getPropiedad(
                                JDatosGeneralesXML.mcsCONEXIONDIRECTA + "/" +
                                        JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME), moDatosGeneralesXML.getPropiedad(
                                JDatosGeneralesXML.mcsCONEXIONDIRECTA + "/" +
                                        JDatosGeneralesXML.PARAMETRO_Conexion), moDatosGeneralesXML.getPropiedad(
                                JDatosGeneralesXML.mcsCONEXIONDIRECTA + "/" +
                                        JDatosGeneralesXML.PARAMETRO_Usuario), moDatosGeneralesXML.getPropiedad(
                                JDatosGeneralesXML.mcsCONEXIONDIRECTA + "/" +
                                        JDatosGeneralesXML.PARAMETRO_Password), moDatosGeneralesXML.getPropiedad(
                                JDatosGeneralesXML.mcsCONEXIONDIRECTA + "/" +
                                        JDatosGeneralesXML.PARAMETRO_TipoSQL)
                            ));
                        ((JServerServidorDatos)moServer).setConstrucEstruc(
                                new JConstructorEstructuraBDConnection(
                                    ((JServerServidorDatos)moServer).getConec(),
                                    true));
                break;
            default:
                moServer=(new ListDatos.JServerServidorDatos(plTipo, psDireccion, "selectDatos.ctrl", "guardarDatos.ctrl"));
                ((JServerServidorDatos)moServer).setConstrucEstruc(
                        new JConstructorEstructuraBDInternet(
                                (JServerServidorDatos)moServer
                                )
                            );
        }
//        if(moParam.getEstablecerServidor()!=null){
//            moParam.getEstablecerServidor().setServidor(getServer());
//        }
    }

    public String getDireccionRemoto(){
            return moDatosGeneralesXML.getPropiedad(JDatosGeneralesXML.mcsCONEXIONSERVIDOR + "/" + JDatosGeneralesXML.mcsParametroRemoto, "http://creativa3d.com:8080/superMercadosServidor/");
    }

    public void mostrarPropiedades(){
    }
    
    public void mostrarPropiedadesConexionBD() throws Exception{
        JConexion loConex = new JConexion();
        JConexionIO loIO = new JConexionIO();
        loIO.setLector(moDatosGeneralesXML);
        loIO.leerPropiedades(loConex);
        
        Element loConexionesRemotas = getDatosGeneralesXML().getElemento(JDatosGeneralesXML.mcsCONEXIONSERVIDOR);
        
        JComunicacion loComu = new JComunicacion();
        JFormConfigConexiones loFormConfigConex = new JFormConfigConexiones(new Frame(), true);
        loFormConfigConex.setDatos(loConex, loConexionesRemotas, loComu);
        loFormConfigConex.setVisible(true);
        if(((Boolean)loComu.moObjecto).booleanValue()){
            loIO.guardarPropiedades(loConex);
            //no hace falta guardar loConexionesRemotas pq es una refenrecia a la estructura de
            //arbol q ya guarda lo anterior
        }
    }
    public JDatosGeneralesXML getDatosGeneralesXML(){
        return moDatosGeneralesXML; 
    }
    public void mostrarEdicion(IFormEdicion poPanel, IFormEdicionNavegador poPanelNave, Component poPanelMismo, int plTipoEdicion) throws Exception {
        getMostrarPantalla().mostrarEdicion(poPanel, poPanelNave, poPanelMismo, plTipoEdicion);
    }

    public void mostrarEdicion(IFormEdicion poPanel, Component poPanelMismo) throws Exception {
        getMostrarPantalla().mostrarEdicion(poPanel, poPanelMismo);
    }

    public void mostrarEdicion(IFormEdicionNavegador poPanel, Component poPanelMismo) throws Exception {
        getMostrarPantalla().mostrarEdicion(poPanel, poPanelMismo);
    }

    public void mostrarFormPrinci(IPanelControlador poControlador, int plAncho, int plAlto) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poControlador, plAncho, plAlto);
    }
    public void mostrarFormPrinci(IPanelControlador poControlador, int plAncho, int plAlto, int plTipoForm, int plTipoMostrar) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poControlador, plAncho, plAlto, plTipoForm, plTipoMostrar);
    }
    public void mostrarFormPrinci(JComponent poPanel, int plAncho, int plAlto, int plTipoMostrar) throws Exception {
        getMostrarPantalla().mostrarFormPrinci(poPanel, plAncho, plAlto, plTipoMostrar);
    }
    public void mostrarForm(JMostrarPantallaParam poParam) throws Exception {
        getMostrarPantalla().mostrarForm(poParam);
    }

    public JMostrarPantalla getMostrarPantalla(){
        if(moMostrarPantalla==null){
             moMostrarPantalla = new JMostrarPantalla(null, JMostrarPantalla.mclEdicionInternal, JMostrarPantalla.mclEdicionInternal);
        }
        return moMostrarPantalla;
    }

    void setDesktopPane1(final JDesktopPane poDesktopPane1, IProcesoThreadGroup poThreadGroup) {
        moThreadGroup = poThreadGroup;
        getMostrarPantalla().setDesktopPane1(poDesktopPane1);
        getMostrarPantalla().setGrupoThreads(moThreadGroup);
    }

    public void cerrarForm(Object poForm) {
        getMostrarPantalla().cerrarForm(poForm);
    }
    public JDevolverTextos getTextosForms(){
        if(moTextos==null){
            moTextos = new JDevolverTextos(ResourceBundle.getBundle("CaptionTablas"));
        }
        return moTextos;
    }

    public void addMostrarListener(IMostrarPantallaListener poListener) {
        moMostrarPantalla.addMostrarListener(poListener);
    }

    public void removeMostrarListener(IMostrarPantallaListener poListener) {
        moMostrarPantalla.removeMostrarListener(poListener);
    }

    public Object getActiveInternalFrame() {
        return moMostrarPantalla.getActiveInternalFrame();
    }

    public void mostrarEdicion(IFormEdicion poPanel, IFormEdicionNavegador poPanelNave, Object poPanelMismo, int plTipoMostrar) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarEdicion(IFormEdicion poPanel, Object poPanelMismo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarEdicion(IFormEdicionNavegador poPanel, Object poPanelMismo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarFormPrinci(IPanelControlador poControlador) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarFormPrinci(Object poPanel, int plAncho, int plAlto, int plTipoMostrar) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarForm(IMostrarPantallaCrear poPanel) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public JMostrarPantallaParam getParam(String psNumeroForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setActividad(Object poAct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarOpcion(Object poActividad, String psTitulo, Runnable poSI, Runnable poNO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mensajeErrorYLog(Object poContex, Throwable e, Runnable poOK) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mensaje(Object poPadre, String psMensaje, int plMensajeTipo, Runnable poOK) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mensajeFlotante(Object poPadre, String psMensaje) {
        moMostrarPantalla.mensajeFlotante(poPadre, psMensaje);
    }

}
