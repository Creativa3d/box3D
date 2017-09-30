/*
 * JPrincipal.java
 *
 * Created on 4 de septiembre de 2004, 19:06
 */

package main;

import CopiaSeguridad.file.JPanelCopiaSeguridad;
import CopiaSeguridad.file.JPanelMoverComprimido;
import CopiaSeguridad.file.JPanelQuitarCaracteresRaros;
import ListDatos.IServerServidorDatos;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosFichero;
import ListDatos.JServerServidorDatosInternetLogin;
import bidi.JPanelBIDI;
import conversor.plugin.JConversorPlugIn20130913;
import formateo.QuitarEspaciosBlanco;
import generadorClases.JFormGeneradorClases;
import generadorGraficosXY.JGraficoForm;
import java.awt.Frame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.scenicview.ScenicView;
import paquetesGeneradorInf.gui.JGuiConsulta;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import paquetesguifx.JT2MUNICIPIOS;
import trozearImages.JFormTrozear;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utiles.xml.dom.Element;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesGUIx.aplicacion.JFormPrincipal;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JConexionIO;
import utilesGUIx.configForm.JT2CONEXIONES;
import utilesGUIx.configForm.antig.JFormConexion;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.JPlugInUtilidades;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesSincronizacion.forms.JFormConflictos;
import utilesSincronizacion.sincronizacion.JSincronizacionCrear;
import utilesSincronizacion.sincronizacion.JSincronizar;
import utilesSincronizacion.sincronizacion.JSincronizarParam;
import utilesSincronizacion.tablasControladoras.JT2TABLASINCRONIZACIONBORRADOS;
import utilesSincronizacion.tablasControladoras.JT2TABLASINCRONIZACIONGENERAL;
import vb.JFormConversor;
import utilesBD.serverTrozos.IServerServidorDatosTrozos;
import visualizarDiferenciasBD.JPanelDiferenciasBD;
import visualizarDiferenciasBD.JRecorrerBaseDatosYQuitarRaros;
import utilesBD.serverTrozos.JServerServidorDatosBDTrozos;
import utilesBD.serverTrozos.JServerServidorDatosInternetTrozos;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.aplicacion.avisos.JAviso;
import utilesGUIx.configForm.JConexionLista;



public class JPlugInPrincipal extends javax.swing.JFrame implements IPlugIn {
    private static final long serialVersionUID = 1L;

    public static final String mcsGestion = "Gestion";            

    public JPlugInPrincipal(){
        
    }
    public void procesarInicial(IPlugInContexto poContexto) {
        initComponents();
        jMenuGestion.setActionCommand(mcsGestion);

        establecerNombresMenus();

        JPlugInUtilidades.addBotones(
                poContexto.getFormPrincipal(),
                jToolBar1,
                false);
        JPlugInUtilidades.addMenusFrame(poContexto.getFormPrincipal(), jMenuBar1);

        poContexto.getFormPrincipal().getListaComponentesAplicacion().getComponente(IComponenteAplicacion.mcsGRUPOMENU, JFormPrincipal.mcsLogin).setActivo(false);
        JPlugInUtilidades.addBotones(poContexto.getFormPrincipal(), jDesktopPane1);

//        boolean lbVisible = true;
//        try{
//            if(JDatosGeneralesP.getDatosGenerales().mlPermisos<3){
//                jMenuCContrasenyas.setVisible(false);
//            }
//            if(JDatosGeneralesP.getDatosGenerales().mlPermisos<=0){
//                lbVisible = false;
//            }
//        }catch(Throwable e){
//            JDepuracion.anadirTexto(getClass().getName(), e);
//        }
//        ((JFrame)poContexto.getFormPrincipal()).setVisible(lbVisible);
    }
    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }

    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
    }
    public void procesarFinal(IPlugInContexto poContexto) {
    }
    
    private void establecerNombresMenus(){
    }
//
//    /** Creates new form JPrincipal */
//    public JPlugInPrincipal() throws Exception {
//        boolean lbVisible = true;
//
//
//        initComponents();
//
//
//        jMenuUsuarioLoginActionPerformed(null);
//        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
//        setIconImage((new javax.swing.ImageIcon(getClass().getResource("/images/logo.gif")).getImage()));
//        JDatosGeneralesP.getDatosGeneralesPlugIn().getPlugInManager().procesarInicial(JDatosGeneralesP.getDatosGeneralesPlugIn().getPlugInContexto());
//
//        try{
//            setTitle(getTitle() + " " + JDatosGeneralesP.getDatosGenerales().getDatosGenerales().getCODESTACION() + " " + JDatosGeneralesP.getDatosGenerales().getDatosGenerales().getALOCALIDAD() );
//            if(JDatosGeneralesP.getDatosGenerales().mlPermisos<3){
//                jMenuCContrasenyas.setVisible(false);
//            }
//            if(JDatosGeneralesP.getDatosGenerales().mlPermisos<=0){
//                lbVisible = false;
//            }
//        }catch(Exception e){
//            JDepuracion.anadirTexto(getClass().getName(), e);
//        }
//        setVisible(lbVisible);
//    }
//    public JMenuBar getMenu() {
//        return jMenuBar1;
//    }
//
//    public String getIdentificador() {
//        return getClass().getName();
//    }
//
//    public Container getContenedor() {
//        return getContentPane();
//    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jToolBar1 = new javax.swing.JToolBar();
        btnComparadorBD = new javax.swing.JButton();
        btnGeneradorCons = new javax.swing.JButton();
        btnDisenoInformes = new javax.swing.JButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        btnComparadorBD2 = new javax.swing.JButton();
        btnGenerConsul = new javax.swing.JButton();
        btnDisenoInformes2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuGestion = new javax.swing.JMenu();
        jMenuGComparadorBD = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuGquitarRaros = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuGGraficos = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuGTrozearImagenes = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        jMenuGCopiaSeguridad = new javax.swing.JMenuItem();
        jMenuGQuitarCaracteresRaros = new javax.swing.JMenuItem();
        jMenuGMover = new javax.swing.JMenuItem();
        jMenuGSincronizar = new javax.swing.JMenu();
        jMenuGSSincronizar = new javax.swing.JMenuItem();
        jMenuGSTunear = new javax.swing.JMenuItem();
        jMenuGSSINCRONIZACIONBORRADOS = new javax.swing.JMenuItem();
        jMenuGSSINCRONIZACIONGENERAL = new javax.swing.JMenuItem();
        jMenuQR = new javax.swing.JMenuItem();
        jMenuProgramacion = new javax.swing.JMenu();
        jMenuPGeneradorClases = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuPQuitarDoblesLineasBlanco = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuPAyudaConversionVB = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuPInformes = new javax.swing.JMenuItem();
        jMenuPPluginModernos = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuJAVAFX = new javax.swing.JMenu();
        jMenuJAVAFXAddAviso = new javax.swing.JMenuItem();
        jMenuJAVAFXSceneView = new javax.swing.JMenuItem();
        jMenuJAVAFXVentanaFlotante = new javax.swing.JMenuItem();
        jMenuJAVAFXJPanelGeneral = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Impresión Fichas técnicas");
        setName("FichasTecnicas"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        btnComparadorBD.setText("Comparador BD");
        btnComparadorBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComparadorBDActionPerformed(evt);
            }
        });
        jToolBar1.add(btnComparadorBD);

        btnGeneradorCons.setText("Generador consultas");
        btnGeneradorCons.setFocusable(false);
        btnGeneradorCons.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGeneradorCons.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGeneradorCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneradorConsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGeneradorCons);

        btnDisenoInformes.setText("Diseño informes");
        btnDisenoInformes.setFocusable(false);
        btnDisenoInformes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDisenoInformes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDisenoInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisenoInformesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDisenoInformes);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jInternalFrame1.setTitle("Principales");
        jInternalFrame1.setVisible(true);
        jInternalFrame1.getContentPane().setLayout(new java.awt.GridLayout(0, 2));

        btnComparadorBD2.setText("Comparador BD");
        btnComparadorBD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComparadorBD2ActionPerformed(evt);
            }
        });
        jInternalFrame1.getContentPane().add(btnComparadorBD2);

        btnGenerConsul.setText("Generador consultas");
        btnGenerConsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerConsulActionPerformed(evt);
            }
        });
        jInternalFrame1.getContentPane().add(btnGenerConsul);

        btnDisenoInformes2.setText("Diseño informes");
        btnDisenoInformes2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisenoInformes2ActionPerformed(evt);
            }
        });
        jInternalFrame1.getContentPane().add(btnDisenoInformes2);

        jDesktopPane1.add(jInternalFrame1);
        jInternalFrame1.setBounds(50, 10, 480, 220);

        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);

        jMenuGestion.setText("Gestión");

        jMenuGComparadorBD.setText("Comparador BD");
        jMenuGComparadorBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGComparadorBDActionPerformed(evt);
            }
        });
        jMenuGestion.add(jMenuGComparadorBD);
        jMenuGestion.add(jSeparator3);

        jMenuGquitarRaros.setText("Quitar raros de la base datos");
        jMenuGquitarRaros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGquitarRarosActionPerformed(evt);
            }
        });
        jMenuGestion.add(jMenuGquitarRaros);
        jMenuGestion.add(jSeparator4);

        jMenuGGraficos.setText("Graficos XY");
        jMenuGGraficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGGraficosActionPerformed(evt);
            }
        });
        jMenuGestion.add(jMenuGGraficos);
        jMenuGestion.add(jSeparator6);

        jMenuGTrozearImagenes.setText("Trocear imagenes");
        jMenuGTrozearImagenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGTrozearImagenesActionPerformed(evt);
            }
        });
        jMenuGestion.add(jMenuGTrozearImagenes);
        jMenuGestion.add(jSeparator7);

        jMenu1.setText("Ficheros");

        jMenuGCopiaSeguridad.setText("Copia seguridad");
        jMenuGCopiaSeguridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGCopiaSeguridadActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuGCopiaSeguridad);

        jMenuGQuitarCaracteresRaros.setText("Quitar caracteres raros de los nombres de archivo");
        jMenuGQuitarCaracteresRaros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGQuitarCaracteresRarosActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuGQuitarCaracteresRaros);

        jMenuGMover.setText("Mover comprimido");
        jMenuGMover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGMoverActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuGMover);

        jMenuGestion.add(jMenu1);

        jMenuGSincronizar.setText("Sincronizar");

        jMenuGSSincronizar.setText("Sincronizar");
        jMenuGSSincronizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGSSincronizarActionPerformed(evt);
            }
        });
        jMenuGSincronizar.add(jMenuGSSincronizar);

        jMenuGSTunear.setText("Sincronización Tunear Servidor");
        jMenuGSTunear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGSTunearActionPerformed(evt);
            }
        });
        jMenuGSincronizar.add(jMenuGSTunear);

        jMenuGSSINCRONIZACIONBORRADOS.setText("Tabla SINCRONIZACIONBORRADOS");
        jMenuGSSINCRONIZACIONBORRADOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGSSINCRONIZACIONBORRADOSActionPerformed(evt);
            }
        });
        jMenuGSincronizar.add(jMenuGSSINCRONIZACIONBORRADOS);

        jMenuGSSINCRONIZACIONGENERAL.setText("TABLA SINCRONIZACIONGENERAL");
        jMenuGSSINCRONIZACIONGENERAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGSSINCRONIZACIONGENERALActionPerformed(evt);
            }
        });
        jMenuGSincronizar.add(jMenuGSSINCRONIZACIONGENERAL);

        jMenuGestion.add(jMenuGSincronizar);

        jMenuQR.setText("QR");
        jMenuQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuQRActionPerformed(evt);
            }
        });
        jMenuGestion.add(jMenuQR);

        jMenuBar1.add(jMenuGestion);

        jMenuProgramacion.setText("Programacion");

        jMenuPGeneradorClases.setText("Generador clases");
        jMenuPGeneradorClases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPGeneradorClasesActionPerformed(evt);
            }
        });
        jMenuProgramacion.add(jMenuPGeneradorClases);
        jMenuProgramacion.add(jSeparator5);

        jMenuPQuitarDoblesLineasBlanco.setText("Quitar dobles lineas blanco");
        jMenuPQuitarDoblesLineasBlanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPQuitarDoblesLineasBlancoActionPerformed(evt);
            }
        });
        jMenuProgramacion.add(jMenuPQuitarDoblesLineasBlanco);
        jMenuProgramacion.add(jSeparator8);

        jMenuPAyudaConversionVB.setText("Ayuda conversion VB a JAVA");
        jMenuPAyudaConversionVB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPAyudaConversionVBActionPerformed(evt);
            }
        });
        jMenuProgramacion.add(jMenuPAyudaConversionVB);
        jMenuProgramacion.add(jSeparator1);

        jMenuPInformes.setText("Informes");
        jMenuPInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPInformesActionPerformed(evt);
            }
        });
        jMenuProgramacion.add(jMenuPInformes);

        jMenuPPluginModernos.setText("Convertir a pluginmodernos");
        jMenuPPluginModernos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPPluginModernosActionPerformed(evt);
            }
        });
        jMenuProgramacion.add(jMenuPPluginModernos);
        jMenuProgramacion.add(jSeparator2);

        jMenuJAVAFX.setText("Text FX");

        jMenuJAVAFXAddAviso.setText("Add aviso");
        jMenuJAVAFXAddAviso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuJAVAFXAddAvisoActionPerformed(evt);
            }
        });
        jMenuJAVAFX.add(jMenuJAVAFXAddAviso);

        jMenuJAVAFXSceneView.setText("SceneView");
        jMenuJAVAFXSceneView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuJAVAFXSceneViewActionPerformed(evt);
            }
        });
        jMenuJAVAFX.add(jMenuJAVAFXSceneView);

        jMenuJAVAFXVentanaFlotante.setText("Ventana flotante");
        jMenuJAVAFXVentanaFlotante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuJAVAFXVentanaFlotanteActionPerformed(evt);
            }
        });
        jMenuJAVAFX.add(jMenuJAVAFXVentanaFlotante);

        jMenuJAVAFXJPanelGeneral.setText("JPanelGeneral");
        jMenuJAVAFXJPanelGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuJAVAFXJPanelGeneralActionPerformed(evt);
            }
        });
        jMenuJAVAFX.add(jMenuJAVAFXJPanelGeneral);

        jMenuProgramacion.add(jMenuJAVAFX);

        jMenuBar1.add(jMenuProgramacion);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComparadorBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComparadorBDActionPerformed
        try{
            JPanelDiferenciasBD loobj = new JPanelDiferenciasBD();
            JMostrarPantallaParam loParam = new JMostrarPantallaParam(loobj, 800, 600, JMostrarPantalla.mclEdicionInternal, "Diferencia BD");
            loParam.setMaximizado(true);
            JDatosGeneralesP.getDatosGenerales().mostrarForm(loParam);
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }

        
    }//GEN-LAST:event_btnComparadorBDActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void jMenuGComparadorBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGComparadorBDActionPerformed

       btnComparadorBDActionPerformed(evt);

}//GEN-LAST:event_jMenuGComparadorBDActionPerformed

    private void jMenuGquitarRarosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGquitarRarosActionPerformed
        try {
            JConexion moConex2 = new JConexion();
            
            JConexionIO loIO = new JConexionIO();
            loIO.setLector(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML());
            loIO.setPrefijo("QuitarRarosConex");
            loIO.leerPropiedades(moConex2);
            JFormConexion loProp = new JFormConexion(new Frame(), true, moConex2);
            loProp.setVisible(true);
            loIO.guardarPropiedades(moConex2);
            
            if(JOptionPane.showConfirmDialog(this, "<html>¿Estas seguro de recorrer todas las tablas y quitar los caracteres raros?<br>base datos: " +moConex2.getConexion().getURL()+" </html>", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                JServerServidorDatos loServer;
                IServerServidorDatosTrozos loServerT;
                switch(moConex2.getConexion().getTipoConexion()){
                    case JServerServidorDatos.mclTipoBD:
                            loServer=(new JServerServidorDatos(
                                    moConex2.getConexion().getClase(), moConex2.getConexion().getURL(),
                                    moConex2.getConexion().getUSUARIO(), moConex2.getConexion().getPASSWORD(),
                                    String.valueOf(moConex2.getConexion().getTipoBD())
                                ));
                            loServer.setConstrucEstruc(
                                    new JConstructorEstructuraBDConnection(
                                        loServer.getConec(),
                                        true));
                            loServerT = new JServerServidorDatosBDTrozos(loServer.getServerBD());
                        break;
                    default:
                        String lsLogin = JOptionPane.showInputDialog("Login");
                        String lsPassword = JOptionPane.showInputDialog("Password");
                        loServer=(new ListDatos.JServerServidorDatos(
                                moConex2.getConexion().getTipoConexion(),  moConex2.getConexion().getURL(),
                                "selectDatos.ctrl", "guardarDatos.ctrl")
                                );
                        loServer.setConstrucEstruc(
                                new JConstructorEstructuraBDInternet(
                                        (JServerServidorDatos)loServer
                                        )
                                    );
                        JServerServidorDatosInternetLogin loLogin = new
                                JServerServidorDatosInternetLogin(lsLogin, lsPassword);
                        ((JServerServidorDatos)loServer).setLogin(loLogin);
                        if(!loLogin.autentificar()){
                            throw new Exception("Login INCORRECTO");
                        }
                        loServerT = new JServerServidorDatosInternetTrozos(loServer.getServerInternet());

                }
                JDatosGeneralesP.getDatosGenerales()
                        .getThreadGroup()
                            .addProcesoYEjecutar(
                                new JRecorrerBaseDatosYQuitarRaros(loServerT));
                
            }
            
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_jMenuGquitarRarosActionPerformed

    private void jMenuPGeneradorClasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPGeneradorClasesActionPerformed
        try{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try{
                        new JFormGeneradorClases().setVisible(true);
                    }catch(Throwable e){
                        JMsgBox.mensajeErrorYLog(null, e);
                    }                    
                }
            });
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }
    }//GEN-LAST:event_jMenuPGeneradorClasesActionPerformed

    private void jMenuGGraficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGGraficosActionPerformed
        try{
        new JGraficoForm().setVisible(true);
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        
    }//GEN-LAST:event_jMenuGGraficosActionPerformed

    private void jMenuGTrozearImagenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGTrozearImagenesActionPerformed
        try{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try{
                        new JFormTrozear().show();
                    }catch(Throwable e){
                        JMsgBox.mensajeErrorYLog(null, e);
                    }                            
                }
            });
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        
        
    }//GEN-LAST:event_jMenuGTrozearImagenesActionPerformed

    private void jMenuPQuitarDoblesLineasBlancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPQuitarDoblesLineasBlancoActionPerformed
        try{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try{
                    QuitarEspaciosBlanco.main(null);
                    }catch(Throwable e){
                        JMsgBox.mensajeErrorYLog(null, e);
                    }                            
                }
            });
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        

    }//GEN-LAST:event_jMenuPQuitarDoblesLineasBlancoActionPerformed

    private void jMenuGCopiaSeguridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGCopiaSeguridadActionPerformed
        try{
            JDatosGeneralesP.getDatosGenerales().mostrarForm(new JMostrarPantallaParam(new JPanelCopiaSeguridad(), 800, 600, JMostrarPantalla.mclEdicionFrame, "Copia seguridad"));
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        

    }//GEN-LAST:event_jMenuGCopiaSeguridadActionPerformed

    private void jMenuPAyudaConversionVBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPAyudaConversionVBActionPerformed
        try{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try{
                        new JFormConversor().setVisible(true);
                    }catch(Throwable e){
                        JMsgBox.mensajeErrorYLog(null, e);
                    }                            
                }
            });
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        

    }//GEN-LAST:event_jMenuPAyudaConversionVBActionPerformed

    private void jMenuGQuitarCaracteresRarosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGQuitarCaracteresRarosActionPerformed
        try{
            JDatosGeneralesP.getDatosGenerales().mostrarForm(new JMostrarPantallaParam(new JPanelQuitarCaracteresRaros(), 800, 600, JMostrarPantalla.mclEdicionFrame, "Quitar caracteres raros"));
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        

    }//GEN-LAST:event_jMenuGQuitarCaracteresRarosActionPerformed

    private void jMenuGMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGMoverActionPerformed
        try{
            JDatosGeneralesP.getDatosGenerales().mostrarForm(new JMostrarPantallaParam(new JPanelMoverComprimido(), 800, 600, JMostrarPantalla.mclEdicionFrame, "Mover comprimido"));
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }        
    }//GEN-LAST:event_jMenuGMoverActionPerformed

    private void jMenuGSSINCRONIZACIONGENERALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGSSINCRONIZACIONGENERALActionPerformed
        try{
            IServerServidorDatos loServer = null;
            JT2TABLASINCRONIZACIONGENERAL loTABLASINCRONIZACIONGENERAL = new JT2TABLASINCRONIZACIONGENERAL(loServer, utilesSincronizacion.JDatosGeneralesP.getDatosGenerales());
            loTABLASINCRONIZACIONGENERAL.mostrarFormPrinci();
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
    }//GEN-LAST:event_jMenuGSSINCRONIZACIONGENERALActionPerformed

    private void jMenuGSSincronizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGSSincronizarActionPerformed
    try{
            JConexionIO loIO = new JConexionIO();
            loIO.setLector(utilesSincronizacion.JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML());
            JConexion loConexServer = new JConexion();
            Element loElem = utilesSincronizacion.JDatosGeneralesP.getDatosGenerales().
                        getDatosGeneralesXML().getElemento(JDatosGeneralesXML.mcsCONEXIONDIRECTA);
            if(loElem!=null){
                loIO.leerPropiedadesConexion(loConexServer, loElem);
            }
            JConexion loConexCliente = new JConexion();
            loElem = utilesSincronizacion.JDatosGeneralesP.getDatosGenerales().
                        getDatosGeneralesXML().getElemento(JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente");
            if(loElem!=null){
                loIO.leerPropiedadesConexion(loConexCliente, loElem);
            }
            JPanelCONEXIONSINCRO jPanel = new JPanelCONEXIONSINCRO();
            jPanel.setDatos(loConexCliente, loConexServer);
            JDatosGeneralesP.getDatosGenerales().mostrarFormPrinci(jPanel, 400, 500, JMostrarPantalla.mclEdicionDialog);
            if(!loConexCliente.mbCancelado){
                loIO.setPrefijo(JDatosGeneralesXML.mcsCONEXIONDIRECTA);
                loIO.guardarPropiedades(loConexServer);
                loIO.setPrefijo(JDatosGeneralesXML.mcsCONEXIONDIRECTA + "Cliente");
                loIO.guardarPropiedades(loConexCliente);
                utilesSincronizacion.JDatosGeneralesP.getDatosGenerales().
                        getDatosGeneralesXML().guardarFichero();
                //
                //inicializamos la BD del SERVIDOR
                //
                JServerServidorDatos loServerServidor;
                if(loConexServer.getConexion().getTipoConexion() == JServerServidorDatos.mclTipoBD) {
                    loServerServidor = (new JServerServidorDatos(
                                    loConexServer.getConexion().getClase(), loConexServer.getConexion().getURL(),
                                    loConexServer.getConexion().getUSUARIO(), loConexServer.getConexion().getPASSWORD(),
                                    String.valueOf(loConexServer.getConexion().getTipoBD())
                                ));
                    loServerServidor.setConstrucEstruc(
                                    new JConstructorEstructuraBDConnection(
                                        loServerServidor.getConec(),
                                        true));
                }else{
                    loServerServidor=new JServerServidorDatos(
                                    loConexServer.getConexion().getTipoConexion(), loConexServer.getConexion().getURL(),
                                    "selectDatos.ctrl", "guardarDatos.ctrl");
                    loServerServidor.setConstrucEstruc(new JConstructorEstructuraBDInternet(loServerServidor));
                    JServerServidorDatosInternetLogin loLogin = new JServerServidorDatosInternetLogin(
                            "alfonso",
                            "alfonso"
                            );
                    loLogin.setServidorInternet(loServerServidor.getServerInternet());
                    if(!loLogin.autentificar()){
                        throw new Exception("Login del servidor incorrecto");
                    }
                }
                //
                //inicializamos la BD del CLIENTE
                //
                JServerServidorDatos loServerCliente;
                if(loConexCliente.getConexion().getTipoConexion() == JServerServidorDatos.mclTipoBD) {
                    loServerCliente = (new JServerServidorDatos(
                                    loConexCliente.getConexion().getClase(), loConexCliente.getConexion().getURL(),
                                    loConexCliente.getConexion().getUSUARIO(), loConexCliente.getConexion().getPASSWORD(),
                                    String.valueOf(loConexCliente.getConexion().getTipoBD())
                                ));
                    loServerCliente.setConstrucEstruc(
                                    new JConstructorEstructuraBDConnection(
                                        loServerCliente.getConec(),
                                        true));
                }else{
                    loServerCliente=new JServerServidorDatos(
                                    loConexCliente.getConexion().getTipoConexion(), loConexCliente.getConexion().getURL(),
                                    "selectDatos.ctrl", "guardarDatos.ctrl");
                    loServerCliente.setConstrucEstruc(new JConstructorEstructuraBDInternet(loServerCliente));
                    JServerServidorDatosInternetLogin loLogin = new JServerServidorDatosInternetLogin(
                            "alfonso",
                            "alfonso"
                            );
                    loLogin.setServidorInternet(loServerCliente.getServerInternet());
                    if(!loLogin.autentificar()){
                        throw new Exception("Login del servidor incorrecto");
                    }
                }
                JSincronizarParam loParam = new JSincronizarParam(new JFormConflictos());
                JSincronizar loSincro = new JSincronizar(
                        loServerCliente,
                        loServerServidor,
                        loParam
                        );
                JDatosGeneralesP.getDatosGenerales().getThreadGroup().addProcesoYEjecutar(loSincro);
            }
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(null, e.toString());
        }
    }//GEN-LAST:event_jMenuGSSincronizarActionPerformed

    private void jMenuGSSINCRONIZACIONBORRADOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGSSINCRONIZACIONBORRADOSActionPerformed
        try{
            IServerServidorDatos loServer = null;
            JT2TABLASINCRONIZACIONBORRADOS loTABLASINCRONIZACIONGENERAL = new JT2TABLASINCRONIZACIONBORRADOS(loServer, utilesSincronizacion.JDatosGeneralesP.getDatosGenerales());
            loTABLASINCRONIZACIONGENERAL.mostrarFormPrinci();
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
    }//GEN-LAST:event_jMenuGSSINCRONIZACIONBORRADOSActionPerformed

    private void jMenuGSTunearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGSTunearActionPerformed
        try{
            IServerServidorDatos loServer = null;
            JSincronizacionCrear loEstru = new  JSincronizacionCrear(loServer);
            JDatosGeneralesP.getDatosGenerales().getThreadGroup().addProcesoYEjecutar(loEstru);
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
    }//GEN-LAST:event_jMenuGSTunearActionPerformed

    private void jMenuPInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPInformesActionPerformed
        impresionXML.impresion.xml.diseno.JMain.main(null);
    }//GEN-LAST:event_jMenuPInformesActionPerformed

    private void jMenuPPluginModernosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPPluginModernosActionPerformed
    //Seleccionamos el directorio de trabajo
        try{
        final javax.swing.JFileChooser jFC = new javax.swing.JFileChooser();
        jFC.setFileSelectionMode(jFC.DIRECTORIES_ONLY);
        int val = jFC.showOpenDialog(new JLabel());
        if(val == jFC.APPROVE_OPTION) {
            JDatosGeneralesP.getDatosGenerales().getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstracX() {

                public String getTitulo() {
                    return "";
                }

                public int getNumeroRegistros() {
                    return -1;
                }

                public void procesar() throws Throwable {
                    JConversorPlugIn20130913 loConv = new JConversorPlugIn20130913();
                    loConv.recorrer(jFC.getSelectedFile());
                }

                public void mostrarMensaje(String psMensaje) {
                    JMsgBox.mensajeError(rootPane, psMensaje);
                }
            });
        }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
    
    }//GEN-LAST:event_jMenuPPluginModernosActionPerformed

    private void btnGeneradorConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneradorConsActionPerformed
        try {
            utilesGUIx.configForm.JConexion loConexBD = new utilesGUIx.configForm.JConexion();
            JConexionIO loIO = new JConexionIO();
            loIO.leerPropiedades(loConexBD);
            utilesGUIx.configForm.antig.JFormConexion loForm = new utilesGUIx.configForm.antig.JFormConexion(new Frame(), true, loConexBD);
            loForm.setVisible(true);
            if(!loConexBD.mbCancelado){
                loIO.guardarPropiedades(loConexBD);
                IServerServidorDatos loServer = JT2CONEXIONES.getServer(loConexBD);
//                JListDatos loList = new JListDatos();
//                loList.moServidor=loServer;
//                loList.getFields().addField(new JFieldDef(""));
//                JSelect loSelect = new JSelect("sqlgenerador");
//                loSelect.msSelectAPelo="select * from sqlgenerador";
//                loList.recuperarDatosNoCacheNormal(loSelect);
//                
//                
//                
                JGuiConsulta loGuiConsulta = new JGuiConsulta();
                JGuiConsultaDatos loDatos = new JGuiConsultaDatos(loServer);
                loGuiConsulta.setDatos(loDatos);
                JDatosGeneralesP.getDatosGenerales().getMostrarPantalla().mostrarForm(
                        new JMostrarPantallaParam(
                            loGuiConsulta, 600, 500,
                            JMostrarPantalla.mclEdicionFrame,
                            "Consulta"));                
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
                    
    }//GEN-LAST:event_btnGeneradorConsActionPerformed

    private void btnDisenoInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisenoInformesActionPerformed
        impresionXML.impresion.xml.diseno.JMain.main(null);
    }//GEN-LAST:event_btnDisenoInformesActionPerformed

    private void jMenuQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQRActionPerformed
        
        try {
            JDatosGeneralesP.getDatosGenerales().mostrarFormPrinci(new JPanelBIDI(), 800, 600, JMostrarPantalla.mclEdicionFrame);
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
        
    }//GEN-LAST:event_jMenuQRActionPerformed

    private void jMenuJAVAFXAddAvisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuJAVAFXAddAvisoActionPerformed
        JDatosGeneralesP.getDatosGenerales().getAvisos().add(new JAviso("Prueba", new ActionListenerCZ() {
            @Override
            public void actionPerformed(ActionEventCZ e) {
                utilesFX.msgbox.JMsgBox.mensajeInformacion(null, "Hola machote");
            }
        }));
    }//GEN-LAST:event_jMenuJAVAFXAddAvisoActionPerformed

    private void btnDisenoInformes2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisenoInformes2ActionPerformed
        btnDisenoInformesActionPerformed(evt);
    }//GEN-LAST:event_btnDisenoInformes2ActionPerformed

    private void btnGenerConsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerConsulActionPerformed
        btnGeneradorConsActionPerformed(evt);
    }//GEN-LAST:event_btnGenerConsulActionPerformed

    private void btnComparadorBD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComparadorBD2ActionPerformed
        btnComparadorBDActionPerformed(evt);
    }//GEN-LAST:event_btnComparadorBD2ActionPerformed

    private void jMenuJAVAFXSceneViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuJAVAFXSceneViewActionPerformed
        ScenicView.show((Parent)JDatosGeneralesP.getDatosGenerales().getFormPrincipal());
    }//GEN-LAST:event_jMenuJAVAFXSceneViewActionPerformed

    private void jMenuJAVAFXVentanaFlotanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuJAVAFXVentanaFlotanteActionPerformed
        JDatosGeneralesP.getDatosGenerales().getMostrarPantalla().mensajeFlotante(rootPane, "mensaje flotante");
    }//GEN-LAST:event_jMenuJAVAFXVentanaFlotanteActionPerformed

    private void jMenuJAVAFXJPanelGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuJAVAFXJPanelGeneralActionPerformed
        try {

            JServerServidorDatosFichero loServer = new JServerServidorDatosFichero("/paquetesguifx/", '\t', true);
            JDatosGeneralesP.getDatosGenerales().setServer(loServer);
            JT2MUNICIPIOS loMuni = new JT2MUNICIPIOS(JDatosGeneralesP.getDatosGenerales().getServer(), JDatosGeneralesP.getDatosGenerales());
            loMuni.mostrarFormPrinci();
        } catch (Throwable ex) {
            utilesFX.msgbox.JMsgBox.mensajeErrorYLog(rootPane, ex);
        }
    }//GEN-LAST:event_jMenuJAVAFXJPanelGeneralActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComparadorBD;
    private javax.swing.JButton btnComparadorBD2;
    private javax.swing.JButton btnDisenoInformes;
    private javax.swing.JButton btnDisenoInformes2;
    private javax.swing.JButton btnGenerConsul;
    private javax.swing.JButton btnGeneradorCons;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuGComparadorBD;
    private javax.swing.JMenuItem jMenuGCopiaSeguridad;
    private javax.swing.JMenuItem jMenuGGraficos;
    private javax.swing.JMenuItem jMenuGMover;
    private javax.swing.JMenuItem jMenuGQuitarCaracteresRaros;
    private javax.swing.JMenuItem jMenuGSSINCRONIZACIONBORRADOS;
    private javax.swing.JMenuItem jMenuGSSINCRONIZACIONGENERAL;
    private javax.swing.JMenuItem jMenuGSSincronizar;
    private javax.swing.JMenuItem jMenuGSTunear;
    private javax.swing.JMenu jMenuGSincronizar;
    private javax.swing.JMenuItem jMenuGTrozearImagenes;
    private javax.swing.JMenu jMenuGestion;
    private javax.swing.JMenuItem jMenuGquitarRaros;
    private javax.swing.JMenu jMenuJAVAFX;
    private javax.swing.JMenuItem jMenuJAVAFXAddAviso;
    private javax.swing.JMenuItem jMenuJAVAFXJPanelGeneral;
    private javax.swing.JMenuItem jMenuJAVAFXSceneView;
    private javax.swing.JMenuItem jMenuJAVAFXVentanaFlotante;
    private javax.swing.JMenuItem jMenuPAyudaConversionVB;
    private javax.swing.JMenuItem jMenuPGeneradorClases;
    private javax.swing.JMenuItem jMenuPInformes;
    private javax.swing.JMenuItem jMenuPPluginModernos;
    private javax.swing.JMenuItem jMenuPQuitarDoblesLineasBlanco;
    private javax.swing.JMenu jMenuProgramacion;
    private javax.swing.JMenuItem jMenuQR;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables



    
}
