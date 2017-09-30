/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.forms;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import com.hexidec.ekit.EkitCore;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import utiles.JCadenas;
import utiles.JComunicacion;
import utiles.JDepuracion;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.JTableModel;
import utilesGUIx.JTextFieldCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JPanelMensaje extends JPanelGENERALBASE {

    protected JMensaje moMensaje;
    protected JComunicacion moComu;
    protected JListDatos moListAdjuntos;
    protected String msDirActual;
    protected EkitCore ekitCore;
    protected String msPathPlantilla;
    private JGUIxAvisosDatosGenerales moDatosGenerales;
    private CallBack moCallback;
    private boolean mbEnviarMensaje;
    /**
     * Creates new form JPanelMensaje
     */
    public JPanelMensaje() {
        initComponents();
        jTableAdjuntar.addMouseListener1(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()>1){
                    jTableAdjuntarActionPerformed(null);
                }
            }
            public void mousePressed(MouseEvent e) {            }
            public void mouseReleased(MouseEvent e) {            }
            public void mouseEntered(MouseEvent e) {            }
            public void mouseExited(MouseEvent e) {            }
        });

        //editor HTML
        boolean multiBar = true;
        String sDocument = null;
        String sStyleSheet = null;
        String sRawDocument = null;
        URL urlStyleSheet = null;
        boolean includeToolBar = true;
        boolean showViewSource = false;
        boolean showMenuIcons = true;
        boolean editModeExclusive = false;
        String sLanguage = "es";
        String sCountry = "es";
        boolean base64 = true;
        boolean debugMode = true;

        ekitCore = new EkitCore(
                false, sDocument, sStyleSheet, sRawDocument, null, urlStyleSheet, includeToolBar, showViewSource, showMenuIcons, editModeExclusive, sLanguage, sCountry, base64, debugMode, true, multiBar, (multiBar ? EkitCore.TOOLBAR_DEFAULT_MULTI : EkitCore.TOOLBAR_DEFAULT_SINGLE), false);

        if (includeToolBar) {
            if (multiBar) {
                jPanelTexto.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 0.0;
                gbc.gridx = 1;

//                  gbc.gridy      = 1;
//                  jPanelTexto.add(ekitCore.getToolBarMain(includeToolBar), gbc);
                gbc.gridy = 2;
                jPanelTexto.add(ekitCore.getToolBarFormat(includeToolBar), gbc);

//                gbc.gridy = 3;
//                jPanelTexto.add(ekitCore.getToolBarStyles(includeToolBar), gbc);
                gbc.anchor = GridBagConstraints.SOUTH;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1.0;
                gbc.gridy = 4;
                jPanelTexto.add(ekitCore, gbc);
            } else {
                jPanelTexto.setLayout(new BorderLayout());
                jPanelTexto.add(ekitCore, BorderLayout.CENTER);
                jPanelTexto.add(ekitCore.getToolBar(includeToolBar), BorderLayout.NORTH);
            }
        } else {
            jPanelTexto.setLayout(new BorderLayout());
            jPanelTexto.add(ekitCore, BorderLayout.CENTER);
        }
        Vector loVect = new Vector();
        loVect.add(ekitCore.KEY_MENU_FONT);
        loVect.add(ekitCore.KEY_MENU_EDIT);
        loVect.add(ekitCore.KEY_MENU_FORMAT);
        loVect.add(ekitCore.KEY_MENU_INSERT);
        loVect.add(ekitCore.KEY_MENU_SEARCH);
        loVect.add(ekitCore.KEY_MENU_TABLE);
        JMenuBar jMenuBar1 = ekitCore.getCustomMenuBar(loVect);
//        jPopupMenu1 = new JPopupMenu("Menu");
//        JMenu lom = new JMenu("csdf");
//        lom.add(new JMenuItem("per"));

//        jPopupMenu1.add(lom);
//        jPopupMenu1.add(new JMenuItem("sdasdf"));
        for (int i = 0; i < jMenuBar1.getMenuCount(); i++) {
            JMenu loMenuAux = jMenuBar1.getMenu(i);
            JMenu loNuevo = new JMenu(loMenuAux.getText());
            addMenus(loMenuAux, loNuevo);
            jPopupMenu1.add(loNuevo);
        }
        JToolBar loToolbar = ekitCore.getToolBarFormat(includeToolBar);
        loToolbar.add(jButtonMenu);
    }
    private void mostrarMenu(){
        if (jPopupMenu1.isVisible()) {
            jPopupMenu1.setVisible(false);
        } else {
            jPopupMenu1.show(this,
                    jButtonMenu.getLocation().x, jButtonMenu.getLocation().y - 1 + jButtonMenu.getHeight()+jPanelTexto.getY());
        }
    }
    /**
     * completa los menus de jMenuDestino con los menus de jMenuOrigen
     */
    public static void addMenus(JMenu jMenuOrigen, JMenu jMenuDestino) {
        for (int i = jMenuOrigen.getMenuComponentCount() - 1; i >= 0; i--) {
            Component loAux = jMenuOrigen.getMenuComponent(i);
            if (loAux.getClass() == JMenu.class) {
                JMenu loMenu = new JMenu(((JMenu) loAux).getText());
                jMenuDestino.add(loMenu);
                addMenus(((JMenu) loAux), loMenu);
            } else {
                jMenuDestino.add(loAux);
            }
            jMenuOrigen.remove(loAux);
        }
    }

    public void setDatos(final JMensaje poMensaje, JComunicacion poComu) throws Exception {
        setDatos(poMensaje, poComu, "", null, null, false);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu, String psPathPlantilla) throws Exception {
        setDatos(poMensaje, poComu, psPathPlantilla, null, null, false);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu, String psPathPlantilla, JGUIxAvisosDatosGenerales poDatosGenerales) throws Exception {
        setDatos(poMensaje, poComu, psPathPlantilla, poDatosGenerales, null, false);
    }
    public void setDatos(final JMensaje poMensaje, JComunicacion poComu, String psPathPlantilla, JGUIxAvisosDatosGenerales poDatosGenerales, CallBack poCallback, boolean pbEnviarMensaje) throws Exception {
        moMensaje = poMensaje;
        moComu = poComu;
        msPathPlantilla = psPathPlantilla;
        moDatosGenerales=poDatosGenerales;
        moCallback=poCallback;
        mbEnviarMensaje=pbEnviarMensaje;
        if(moMensaje.getCampos()==null || moMensaje.getCampos().isEmpty()){
            btnCargarCampos.setVisible(false);
        }
        if(moDatosGenerales==null){
            moDatosGenerales=JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos();
        }

    }
    

    @Override
    public String getTitulo() {
        return "Mensaje";
    }

    @Override
    public JSTabla getTabla() {
        return null;
    }

    @Override
    public void rellenarPantalla() throws Exception {
        cmbDe.borrarTodo();
        if(moDatosGenerales==null){
            cmbDe.setVisible(false);
        } else {
            for(JGUIxAvisosCorreo loCorreo: moDatosGenerales.getListaCorreos()){
                cmbDe.addLinea(
                        loCorreo.getEnviar().getCorreoNombre()+ " <"+loCorreo.getEnviar().getCorreo() + ">"
                        , loCorreo.getIdentificador()+JFilaDatosDefecto.mcsSeparacion1);
            }   
        }
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
    }

    @Override
    public void ponerTipoTextos() throws Exception {
        txtAsunto.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtCC.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtBCC.setTipo(JTipoTextoEstandar.mclTextCadena);
        
    }

    public void mostrarDatosTO() throws Exception {
        
        StringBuffer lasEMailTO = new StringBuffer();

        lasEMailTO.append("<html>");
        for (int i = 0; i < moMensaje.getEmailTO().size(); i++) {
            if(JCadenas.isVacio(moMensaje.getEmailTO().get(i).toString())){
                moMensaje.getEmailTO().remove(i);
            }else{ 
                lasEMailTO.append(moMensaje.getEmailTO().get(i).toString());
                lasEMailTO.append("<br>");
            }
        }
        lasEMailTO.append("</html>");
        lblEmailTO.setText(lasEMailTO.toString());
    }
    @Override
    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;
        mostrarDatosTO();
        
        moListAdjuntos = new JListDatos(null, "", new String[]{"Adjunto", "Fichero"}, new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena}, new int[]{1});

        for (int i = 0; i < moMensaje.getFicheroAdjunto().size(); i++) {
            moListAdjuntos.addNew();
            moListAdjuntos.getFields(0).setValue(moMensaje.getFicheroAdjunto().get(i));
            moListAdjuntos.getFields(1).setValue(moMensaje.getFicheroAdjunto().get(i));
            moListAdjuntos.update(false);
        }

        jTableAdjuntar.setModel(new JTableModel(moListAdjuntos));
        jScrollPaneAdjuntos.setVisible(moListAdjuntos.size() > 0);

        ponerColumnaA0(jTableAdjuntar.getColumnModel().getColumn(1));

        // Si existe una plantilla por defecto la adjuntamos
        if (!msPathPlantilla.isEmpty()) {
            adjuntarPlantilla(msPathPlantilla);
        }
        if(!JCadenas.isVacio(moMensaje.getTexto())){
            ekitCore.setDocumentText(moMensaje.getTexto());
        }

        txtAsunto.setText(moMensaje.getAsunto());

        if(JCadenas.isVacio(moMensaje.getIdentificadorEnvio())){
            cmbDe.mbSeleccionarClave(moDatosGenerales.getCorreoDefecto().getIdentificador()+JFilaDatosDefecto.mcsSeparacion1);
        } else {
            cmbDe.mbSeleccionarClave(moMensaje.getIdentificadorEnvio()+JFilaDatosDefecto.mcsSeparacion1);
        }
    }

    private void adjuntarPlantilla(String pathPlantilla) throws Exception {
        File loPlantilla = new File(pathPlantilla);
        if (loPlantilla.exists()) {
            ekitCore.loadDocument(loPlantilla);
        }
    }

    private void ponerColumnaA0(TableColumn loColumn) {
        loColumn.setMinWidth(0);
        loColumn.setPreferredWidth(0);
        loColumn.setWidth(0);
        loColumn.setMaxWidth(0);

    }
 
    @Override
    public void establecerDatos() throws Exception {
        moMensaje.getEmailCC().clear();
        moMensaje.getEmailBCC().clear();
        String[] lasCorreo = JFilaDatosDefecto.moArrayDatos(txtCC.getText()+",", ',');
        for(int i = 0 ; i < lasCorreo.length ;i++){
            if(!lasCorreo[i].trim().equals("")){
                moMensaje.addEmailCC(lasCorreo[i].trim());
            }
        }
        
        lasCorreo = JFilaDatosDefecto.moArrayDatos(txtBCC.getText()+",", ',');
        for(int i = 0 ; i < lasCorreo.length ;i++){
            if(!lasCorreo[i].trim().equals("")){
                moMensaje.addEmailBCC(lasCorreo[i].trim());
            }
        }        
        moMensaje.getFicheroAdjunto().clear();
        if (moListAdjuntos.moveFirst()) {
            do {
                moMensaje.addFicheroAdjunto(moListAdjuntos.getFields(1).getString());
            } while (moListAdjuntos.moveNext());
        }
        
        
        String lsAsunto = txtAsunto.getText();
        String lsTexto = ekitCore.getDocumentText();
        if(moMensaje.getCampos()!=null){
            for (Map.Entry<String, Object> entry : moMensaje.getCampos().entrySet()) {

                String lsCampo = entry.getKey();
                if (lsAsunto.contains("[" + lsCampo + "]")) {
                    if (entry.getValue() != null) {
                        lsAsunto = lsAsunto.replace("[" + lsCampo + "]", entry.getValue().toString());
                    } else {
                        lsAsunto = lsAsunto.replace("[" + lsCampo + "]", "");
                    }
                }
                if (lsTexto.contains("[" + lsCampo + "]")) {
                    if (entry.getValue() != null) {
                        lsTexto = lsTexto.replace("[" + lsCampo + "]", entry.getValue().toString());
                    } else {
                        lsTexto = lsTexto.replace("[" + lsCampo + "]", "");
                    }
                }

            }
        }

        moMensaje.setAsunto(lsAsunto);
        moMensaje.setTexto(lsTexto);
        moMensaje.setIdentificadorEnvio(cmbDe.getFilaActual().msCampo(0));
        
        
    }

    @Override
    public void aceptar() throws Exception {
        if(moComu!=null){
            moComu.moObjecto = "1";
        }
        if(mbEnviarMensaje){
            if(JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup()==null){
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().enviarEmail(moMensaje);
            } else {
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstracX() {
                    public String getTitulo() {
                        return "Enviando eMail";
                    }
                    public int getNumeroRegistros() {return -1;}
                    public void procesar() throws Throwable {
                        JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().enviarEmail(moMensaje);
                        if(moCallback!=null){
                            moCallback.callBack(this);
                        }                        
                    }
                    public String getTituloRegistroActual() {return "";}
                    public void mostrarMensaje(String psMensaje) {
        //                JMsgBox.mensajeInformacion(null, "Email enviado");
                    }
                });     
            }            
            
        }
        if(moCallback!=null){
            moCallback.callBack(this);
        }
    }

    @Override
    public void cancelar() throws Exception {
        if(moComu!=null){
            moComu.moObjecto = "0";
        }
        if(moCallback!=null){
            moCallback.callBack(this);
        }
        if(mbEnviarMensaje){
            if(JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup()==null){
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().enviarEmail(moMensaje);
            } else {
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstracX() {
                    public String getTitulo() {
                        return "Enviando eMail";
                    }
                    public int getNumeroRegistros() {return -1;}
                    public void procesar() throws Throwable {
                        JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().enviarEmail(moMensaje);
                    }
                    public String getTituloRegistroActual() {return "";}
                    public void mostrarMensaje(String psMensaje) {
        //                JMsgBox.mensajeInformacion(null, "Email enviado");
                    }
                });     
            }            
            
        }
    }

    @Override
    public Rectangulo getTanano() {
        return new Rectangulo(0, 0, 740, 600);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jButtonMenu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdjuntar = new utilesGUIx.JButtonCZ();
        btnAdjuntarPlantilla = new utilesGUIx.JButtonCZ();
        btnGuardarPlantilla = new utilesGUIx.JButtonCZ();
        btnCargarCampos = new utilesGUIx.JButtonCZ();
        jPanel2 = new javax.swing.JPanel();
        jLabelCZ7 = new utilesGUIx.JLabelCZ();
        cmbDe = new utilesGUIx.JComboBoxCZ();
        jPanel3 = new javax.swing.JPanel();
        jLabelCZ3 = new utilesGUIx.JLabelCZ();
        btnParaMas = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblEmailTO = new utilesGUIx.JLabelCZ();
        jPanel4 = new javax.swing.JPanel();
        jLabelCZ4 = new utilesGUIx.JLabelCZ();
        txtCC = new utilesGUIx.JTextFieldCZ();
        jPanel5 = new javax.swing.JPanel();
        jLabelCZ5 = new utilesGUIx.JLabelCZ();
        txtBCC = new utilesGUIx.JTextFieldCZ();
        jLabelCZ2 = new utilesGUIx.JLabelCZ();
        txtAsunto = new utilesGUIx.JTextFieldCZ();
        jLabelCZ6 = new utilesGUIx.JLabelCZ();
        jScrollPaneAdjuntos = new javax.swing.JScrollPane();
        jTableAdjuntar = new utilesGUIx.JTableCZ();
        jPanelTexto = new javax.swing.JPanel();

        jButtonMenu.setText("Menú");
        jButtonMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMenuActionPerformed(evt);
            }
        });

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        btnAdjuntar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/attach.png"))); // NOI18N
        btnAdjuntar.setText("Adjuntar");
        btnAdjuntar.setFocusable(false);
        btnAdjuntar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAdjuntar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdjuntar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjuntarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdjuntar);

        btnAdjuntarPlantilla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/cargarPlantilla16.png"))); // NOI18N
        btnAdjuntarPlantilla.setText("Cargar Plantilla");
        btnAdjuntarPlantilla.setFocusable(false);
        btnAdjuntarPlantilla.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAdjuntarPlantilla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdjuntarPlantilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjuntarPlantillaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdjuntarPlantilla);

        btnGuardarPlantilla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/guardarPlantilla16.png"))); // NOI18N
        btnGuardarPlantilla.setText("Guardar Plantilla");
        btnGuardarPlantilla.setFocusable(false);
        btnGuardarPlantilla.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnGuardarPlantilla.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnGuardarPlantilla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarPlantilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPlantillaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardarPlantilla);

        btnCargarCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/cargarPlantilla16.png"))); // NOI18N
        btnCargarCampos.setText("Cargar Campos");
        btnCargarCampos.setFocusable(false);
        btnCargarCampos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnCargarCampos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCargarCampos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCargarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarCamposActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCargarCampos);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabelCZ7.setText("De:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(jLabelCZ7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(cmbDe, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabelCZ3.setText("Para:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(jLabelCZ3, gridBagConstraints);

        btnParaMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/mas.png"))); // NOI18N
        btnParaMas.setMaximumSize(new java.awt.Dimension(25, 25));
        btnParaMas.setMinimumSize(new java.awt.Dimension(25, 25));
        btnParaMas.setPreferredSize(new java.awt.Dimension(25, 25));
        btnParaMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParaMasActionPerformed(evt);
            }
        });
        jPanel3.add(btnParaMas, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel3, gridBagConstraints);

        lblEmailTO.setBackground(new java.awt.Color(255, 255, 255));
        lblEmailTO.setOpaque(true);
        jScrollPane3.setViewportView(lblEmailTO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabelCZ4.setText("CC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel4.add(jLabelCZ4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(txtCC, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabelCZ5.setText("BCC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel5.add(jLabelCZ5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(txtBCC, gridBagConstraints);

        jLabelCZ2.setText("Asunto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(jLabelCZ2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(txtAsunto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(jLabelCZ6, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jScrollPaneAdjuntos.setPreferredSize(new java.awt.Dimension(122, 150));

        jTableAdjuntar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneAdjuntos.setViewportView(jTableAdjuntar);

        jPanel1.add(jScrollPaneAdjuntos, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.NORTH);
        add(jPanelTexto, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdjuntarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdjuntarActionPerformed
        try {
            JFileChooser loFileM = new JFileChooser();

            if (msDirActual != null && !msDirActual.equals("")) {
                loFileM.setCurrentDirectory(new File(msDirActual));
            }
            if (loFileM.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File loFile = loFileM.getSelectedFile();
                msDirActual = loFile.getParent();
                if (!loFile.exists()) {
                    throw new Exception("Fichero no existe " + loFile.getName());

                } else {
                    moListAdjuntos.addNew();
                    moListAdjuntos.getFields(0).setValue(loFile.getName());
                    moListAdjuntos.getFields(1).setValue(loFile.getAbsolutePath());
                    moListAdjuntos.update(false);
                    jScrollPaneAdjuntos.setVisible(true);
                    jTableAdjuntar.updateUI();
                    this.validate();
                }
            }
        } catch (Throwable e) {
            JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_btnAdjuntarActionPerformed

    private void jButtonMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMenuActionPerformed
        mostrarMenu();
    }//GEN-LAST:event_jButtonMenuActionPerformed

    private void jTableAdjuntarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTableAdjuntarActionPerformed
        if(jTableAdjuntar.getSelectedRow()>=0){
            JEjecutar.abrirDocumento(jTableAdjuntar.getModel().getValueAt(jTableAdjuntar.getSelectedRow(), 1).toString());
        }
    }//GEN-LAST:event_jTableAdjuntarActionPerformed

    private void btnAdjuntarPlantillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdjuntarPlantillaActionPerformed
        try {
            JFileChooser loFileM = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Plantilla GTC", new String[]{"gtc"});
            loFileM.setFileFilter(filter);
            loFileM.setAcceptAllFileFilterUsed(false);
            if (loFileM.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

                if (!loFileM.getSelectedFile().getPath().isEmpty()) {
                    if (!loFileM.getSelectedFile().getPath().endsWith(".gtc")) {
                        throw new Exception("La plantilla debe tener la extensión .gtc");
                    }
                    adjuntarPlantilla(loFileM.getSelectedFile().getPath());
                }
            }
        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            JMsgBox.mensajeErrorYLog(this, e);
        }
    }//GEN-LAST:event_btnAdjuntarPlantillaActionPerformed

    private void btnGuardarPlantillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPlantillaActionPerformed
        try {
            JFileChooser loFileM = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Plantilla GTC", new String[]{"gtc"});
            loFileM.setFileFilter(filter);
            loFileM.setAcceptAllFileFilterUsed(false);
            if (loFileM.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File loFile = loFileM.getSelectedFile();
                exportarPlantilla(loFile.getAbsolutePath() + ".gtc");
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }
    }//GEN-LAST:event_btnGuardarPlantillaActionPerformed

    private void btnCargarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarCamposActionPerformed

        try {
            JPanelCargarCampos loPanel = new JPanelCargarCampos();
            Component loComponentFocused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            int llCaretPosition = 0;
            if (loComponentFocused.equals(getTxtAsunto())) {
                llCaretPosition = getTxtAsunto().getCaretPosition();
            } else if (loComponentFocused.equals(getEkitCore().getTextPane())) {
                llCaretPosition = getEkitCore().getTextPane().getCaretPosition();
            }

            loPanel.setDatos(moMensaje.getCampos(), loComponentFocused, llCaretPosition);
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarEdicion(loPanel, null, loPanel, JMostrarPantalla.mclEdicionDialog);
        } catch (Exception ex) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex);
        }
    }//GEN-LAST:event_btnCargarCamposActionPerformed

    private void btnParaMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParaMasActionPerformed
        String lsEmail=JOptionPane.showInputDialog("Introducir email");
        if(!JCadenas.isVacio(lsEmail)){
            try {
                moMensaje.getEmailTO().add(lsEmail);
                mostrarDatosTO();
            } catch (Exception ex) {
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex);
            }
        }
    }//GEN-LAST:event_btnParaMasActionPerformed

    private void exportarPlantilla(String psPathPlantilla) {

        FileWriter loFile = null;
        try {
            loFile = new FileWriter(psPathPlantilla);
            PrintWriter loPrintWriter = new PrintWriter(loFile);

            loPrintWriter.println(ekitCore.getDocumentText());

        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.getMessage());
        } finally {
            try {
                if (loFile != null) {
                    loFile.close();
                }
            } catch (Exception e2) {
                utilesGUIx.msgbox.JMsgBox.mensajeError(this, e2.getMessage());
            }
        }
    }

    public EkitCore getEkitCore() {
        return ekitCore;
    }

    public JTextFieldCZ getTxtAsunto() {
        return txtAsunto;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnAdjuntar;
    private utilesGUIx.JButtonCZ btnAdjuntarPlantilla;
    private utilesGUIx.JButtonCZ btnCargarCampos;
    private utilesGUIx.JButtonCZ btnGuardarPlantilla;
    private javax.swing.JButton btnParaMas;
    private utilesGUIx.JComboBoxCZ cmbDe;
    private javax.swing.JButton jButtonMenu;
    private utilesGUIx.JLabelCZ jLabelCZ2;
    private utilesGUIx.JLabelCZ jLabelCZ3;
    private utilesGUIx.JLabelCZ jLabelCZ4;
    private utilesGUIx.JLabelCZ jLabelCZ5;
    private utilesGUIx.JLabelCZ jLabelCZ6;
    private utilesGUIx.JLabelCZ jLabelCZ7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelTexto;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneAdjuntos;
    private utilesGUIx.JTableCZ jTableAdjuntar;
    protected javax.swing.JToolBar jToolBar1;
    protected utilesGUIx.JLabelCZ lblEmailTO;
    protected utilesGUIx.JTextFieldCZ txtAsunto;
    protected utilesGUIx.JTextFieldCZ txtBCC;
    protected utilesGUIx.JTextFieldCZ txtCC;
    // End of variables declaration//GEN-END:variables
}
