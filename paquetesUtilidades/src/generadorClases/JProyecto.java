/*
 * JProyecto.java
 *
 * Created on 29 de agosto de 2007, 11:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorClases;

import ListDatos.IServerServidorDatos;
import ListDatos.JSelectMotor;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosBD;
import ListDatos.JServerServidorDatosInternetLogin;
import ListDatos.estructuraBD.IConstructorEstructuraBD;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import generadorClases.modulosCodigo.*;
import generadorClases.opciones.JFormOpciones;
import generadorClases.opciones.JOpcionesGlobal;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesBD.estructuraBD.JConstructorEstructuraBDACCESS;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesBD.servidoresDatos.JServerServidorDatosDBASE;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JConexionIO;
import utilesGUIx.configForm.JT2CONEXIONES;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author chema
 */
public class JProyecto {
    //Declaracion de vbles
    private JOpcionesGlobal moOpciones = new JOpcionesGlobal();

    private IListaElementos moListaModulos = new JListaElementos();
    private JConexionGeneradorClass moConex = new JConexionGeneradorClass(this);
    
    /** Creates a new instance of JProyecto */
    public JProyecto() {
        //Creamos los diferentes modulos de codigo del proyecto
        moListaModulos.add(new JGeneradorJTable(this));
        moListaModulos.add(new JGeneradorTablasFlex(this));
        moListaModulos.add(new JGeneradorJTable2(this));
        moListaModulos.add(new JGeneradorJ2Table(this));
        moListaModulos.add(new JGeneradorJCTable(this));
        moListaModulos.add(new JGeneradorPanel(this));
        moListaModulos.add(new JGeneradorPanelForm(this));
        moListaModulos.add(new JGeneradorFormFlex(this));
        moListaModulos.add(new JGeneradorDatosGenerales(this));
        moListaModulos.add(new JGeneradorDatosGeneralesP(this));
        moListaModulos.add(new JGeneradorALogin(this));
        moListaModulos.add(new JGeneradorALoginAplicacion(this));
        moListaModulos.add(new JGeneradorJAccionDevolver(this));
        moListaModulos.add(new JGeneradorGUIxAvisos(this));
        moListaModulos.add(new JGeneradorJPlugInPrincipal(this));
        moListaModulos.add(new JGeneradorJPlugInPrincipalFX(this));
        moListaModulos.add(new JGeneradorJPlugInPrincipalForm(this));
        moListaModulos.add(new JGeneradorJPlugInPrincipalFormFX(this));
        moListaModulos.add(new JGeneradorActualizarEstructura(this));
        moListaModulos.add(new JGeneradorJMain(this));
        moListaModulos.add(new JGeneradorPlugInSeguridad(this));
        moListaModulos.add(new JGeneradorJMainFX(this));
        moListaModulos.add(new JGeneradorGestionProyecto(this));
        moListaModulos.add(new JGeneradorListadosJasper(this));
        moListaModulos.add(new JGeneradorGestionProyectoNuevo(this));
        moListaModulos.add(new JGeneradorFicheroCaption(this));
        moListaModulos.add(new JGeneradorFicheroToolTipoText(this));
        moListaModulos.add(new JGeneradorFicheroAYUDAURL(this));
        moListaModulos.add(new JGeneradorPanelFX(this));
        moListaModulos.add(new JGeneradorPanelFormFX(this));
        moListaModulos.add(new JGeneradorImages(this));
    }

    public void setMsRutaProyectoRaiz(String msRutaProyectoRaiz) {
        getConex().setRutaProyectoRaiz(msRutaProyectoRaiz);
        
    }
    
    public void nuevo() throws Throwable {
        utilesGUIx.configForm.JConexion loConexBD = new utilesGUIx.configForm.JConexion();
        JConexionIO loIO = new JConexionIO();
        loIO.leerPropiedades(loConexBD);
        utilesGUIx.configForm.antig.JFormConexion loForm = new utilesGUIx.configForm.antig.JFormConexion(new Frame(), true, loConexBD);
        loForm.setVisible(true);
        if(!loConexBD.mbCancelado){
            loIO.guardarPropiedades(loConexBD);
            crearConexion(JT2CONEXIONES.getServer(loConexBD));
            
        }
        
        //Seleccionamos el directorio de trabajo
        javax.swing.JFileChooser jFC = new javax.swing.JFileChooser();
        jFC.setFileSelectionMode(jFC.DIRECTORIES_ONLY);
        int val = jFC.showOpenDialog(new JLabel());
        if(val == jFC.APPROVE_OPTION) {
            setMsRutaProyectoRaiz(jFC.getSelectedFile().getAbsolutePath());
        }
        
    }
    
    public JTableDefs getListaTablas() {
        return getConex().getTablasBD();
    }

    public void generarModulo(IModuloProyecto poModulo) throws IOException{
        File loAux = null;
        File loFile = null;
        IModuloProyecto loModulo = poModulo;
        if(loModulo.getParametros().mbGenerar){
            String codigoJTabla = loModulo.getCodigo();
            loAux = new File(getConex().getRutaProyectoClases() ,  loModulo.getRutaRelativa());
            if(!loAux.exists()) {
                loAux.mkdirs();
            }
            loFile = new File(
                            loAux,
                            poModulo.getNombre()
                            );

            if((loFile.isFile()||!loFile.exists()) && codigoJTabla!=null){
                loFile.delete();
                loFile.createNewFile();
                FileOutputStream loFileOut = new FileOutputStream(loFile);
                PrintWriter loFileO = new java.io.PrintWriter(loFileOut);
                try{
                    loFileO.print(codigoJTabla.toCharArray());
                }finally{
                    loFileO.close();
                    loFileOut.close();
                }
            }
        }
    }
    
    public void generarCodigo(String psNombreModulo) throws IOException {
        IModuloProyecto loModulo=null;
        for(int lM = 0 ;
            lM < moListaModulos.size() &&
            (loModulo == null || !loModulo.getNombreModulo().equals(psNombreModulo)); lM++){
            loModulo = (IModuloProyecto) moListaModulos.get(lM);
        }
        generarCodigo(loModulo);
    }
    //genera el codigo dependiente de las tablas
    public void generarCodigo(IModuloProyecto poModulo) throws IOException {
        if(poModulo.isGeneral()){
            generarModulo(poModulo);
        }else{
            for(int i = 0; i < getListaTablas().size(); i++ ){ //Para cada tabla
                JTableDef loTabla = (JTableDef)getListaTablas().get(i);
                getConex().setTablaActual(loTabla.getNombre());
                generarModulo(poModulo);
            }
        }
    }
    
    public void generarCodigoTabla(String psNombreTabla) throws IOException {
        getConex().setTablaActual(psNombreTabla);
        for(int lM = 0 ; lM < moListaModulos.size(); lM++){
            IModuloProyecto loModulo = (IModuloProyecto) moListaModulos.get(lM);
            if(!loModulo.isGeneral()){
                generarModulo(loModulo);
            }
        }
    }
    public void generarCodigoTabla(String psNombreModulo, String psNombreTabla) throws IOException {
        IModuloProyecto loModulo=null;
        for(int lM = 0 ; 
            lM < moListaModulos.size() && 
            (loModulo == null || !loModulo.getNombreModulo().equals(psNombreModulo)); lM++){
            loModulo = (IModuloProyecto) moListaModulos.get(lM);
        }
        getConex().setTablaActual(psNombreTabla);
        generarModulo(loModulo);
    }
    public String getCodigoTabla(String psNombreModulo, String psNombreTabla) throws IOException {
        IModuloProyecto loModulo=null;
        for(int lM = 0 ;
            lM < moListaModulos.size() &&
            (loModulo == null || !loModulo.getNombreModulo().equals(psNombreModulo)); lM++){
            loModulo = (IModuloProyecto) moListaModulos.get(lM);
        }
        getConex().setTablaActual(psNombreTabla);
        String lsCodigo = "";
        if(loModulo.getParametros().mbGenerar){
            lsCodigo = loModulo.getCodigo();
        }else{
            lsCodigo = "";
        }

        return lsCodigo;
    }
    
    //Genera los modulos comunes no dependientes de las tablas
    public void generarCodigoComun() throws IOException {
        for(int lM = 0 ; lM < moListaModulos.size(); lM++){
            IModuloProyecto loModulo = (IModuloProyecto) moListaModulos.get(lM);
            if(loModulo.isGeneral()){
                generarModulo(loModulo);
            }
        }
    }

    //Genera la estructura de directorios del proyecto
    private void crearDirectorios() {
        File loAux = null;
        
        //creamos el directorio raiz
        loAux = new File(getConex().getRutaProyectoRaiz());
        if(!loAux.exists()) loAux.mkdirs();
        
        //directorio cliente
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "cliente" + getConex().getDirPadre());
        if(!loAux.exists()) loAux.mkdirs();
        
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "cliente" + getConex().getDirPadre() + System.getProperty("file.separator") + "images");
        if(!loAux.exists()) loAux.mkdirs();

        loAux = new File(getConex().getRutaProyectoClases());
        if(!loAux.exists()) loAux.mkdirs();
        
        //directorio servidor
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "servidor" + getConex().getDirPadre());
        if(!loAux.exists()) loAux.mkdirs();

        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "servidor" + getConex().getDirPadre() + System.getProperty("file.separator") + "WEB-INF");
        if(!loAux.exists()) loAux.mkdirs();
        
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "servidor" + getConex().getDirPadre() + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator") + "lib");
        if(!loAux.exists()) loAux.mkdirs();
        
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "servidor" + getConex().getDirPadre() + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator") + "classes");
        if(!loAux.exists()) loAux.mkdirs();
        
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "servidor" + getConex().getDirPadre() + System.getProperty("file.separator") + "META-INF");
        if(!loAux.exists()) loAux.mkdirs();
        
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "servidor" + getConex().getDirPadre() + System.getProperty("file.separator") + "images");
        if(!loAux.exists()) loAux.mkdirs();
        
        
        //directorio documentacion
        loAux = new File(getConex().getRutaProyectoRaiz() + System.getProperty("file.separator") + "documentacion");
        if(!loAux.exists()) loAux.mkdirs();
    }

    public void generarProyectoCompleto() throws IOException {
        //Generar todo el proyecto
        crearDirectorios();
        for(int i = 0 ; i < moListaModulos.size(); i++){
            IModuloProyecto loModulo = (IModuloProyecto) moListaModulos.get(i);
            generarCodigo(loModulo);
        }
        utilesGUIx.msgbox.JMsgBox.mensajeInformacion(null,"El proyecto se ha generado correctamente...");        
    }
    
    public void mostraOpciones() {
        JFormOpciones loFormOpciones = new JFormOpciones(new Frame(),true, moOpciones);
        loFormOpciones.setVisible(true);
    }
    
    
    public void setTablaActual(String psTabla) {
        getConex().setTablaActual(psTabla);
    }
    
    private void crearConexion(IServerServidorDatos poServer) {
        try {
            getConex().setConexion(poServer);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JConexionGeneradorClass getConex() {
        return moConex;
    }

    /**
     * @return the moListaModulos
     */
    public IListaElementos getListaModulos() {
        return moListaModulos;
    }

    /**
     * @return the moOpciones
     */
    public JOpcionesGlobal getOpciones() {
        return moOpciones;
    }
}
