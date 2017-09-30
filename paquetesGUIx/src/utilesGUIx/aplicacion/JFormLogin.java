/*
 * JFormLogin.java
 *
 * Creado el 29/7/2008
 */
package utilesGUIx.aplicacion;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import java.awt.event.KeyEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.*;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JConexionIO;
import utilesGUIx.configForm.JConexionLista;
import utilesGUIx.configForm.JT2CONEXIONES;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;
import utilesGUIx.plugin.seguridad.visual.JPanelCambioPassWord;

public class JFormLogin extends javax.swing.JDialog {

    private final JDatosGeneralesAplicacion moDatosGenerales;

    /**
     * Creates new form JFormLogin
     */
    public JFormLogin(java.awt.Frame parent, boolean modal, JDatosGeneralesAplicacion poDatosGenerales) throws Exception {
        super(parent, modal);
        initComponents();
        jTextLogin.setTipo(JTipoTextoEstandar.mclTextCadena);
        moDatosGenerales = poDatosGenerales;

        recargarCombo(cmbConexion, moDatosGenerales.getDatosGeneralesXML());

        lblConexion.setVisible(
                moDatosGenerales.getParametrosAplicacion().isConexionLogin()
                || cmbConexion.getItemCount() == 0);
        cmbConexion.setVisible(
                moDatosGenerales.getParametrosAplicacion().isConexionLogin()
                || cmbConexion.getItemCount() == 0);
        jBtnConexionesBD.setVisible(
                moDatosGenerales.getParametrosAplicacion().isConexionLogin()
                || cmbConexion.getItemCount() == 0);
        jTextLogin.setText(moDatosGenerales.getUltUsuario());
        try {
            cmbConexion.mbSeleccionarClave(moDatosGenerales.getUltTipoConexion());
        } catch (Throwable e) {
        }
        btnCambioContrasena.setVisible(moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW()!=null);
        try {
            lblLoginImg.setIcon((Icon) moDatosGenerales.getParametrosAplicacion().getImagenLogin());
            validate();
            pack();
            validate();
            pack();
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2, this.getWidth(), this.getHeight());
            if(poDatosGenerales.getParametrosAplicacion().getImagenIcono()!=null){
                setIconImage(((ImageIcon)poDatosGenerales.getParametrosAplicacion().getImagenIcono()).getImage());
            }
        } catch (Throwable e) {
        }


    }

    public static void recargarCombo(JComboBoxCZ poComboBoxServidor, JDatosGeneralesXML poXML) throws Exception {
        JConexionIO loIO = new JConexionIO();
        loIO.setLector(poXML);
        recargarCombo(poComboBoxServidor, loIO.leerListaConexiones());
    }

    public static void recargarCombo(JComboBoxCZ poComboBoxServidor, JConexionLista poLista) throws Exception {
        String lsConexion = null;
        //guardamos la conexion seleccionada
        if (poComboBoxServidor.getSelectedIndex() >= 0) {
            lsConexion = poComboBoxServidor.getFilaActual().msCampo(0);
        }
        poComboBoxServidor.borrarTodo();
        for (int i = 0; i < poLista.size(); i++) {
            JConexion loAux = (JConexion) poLista.get(i);
            poComboBoxServidor.addLinea(
                    loAux.getNombre(),
                    loAux.getNombre());

        }
        //ponemos una por defecto
        try {
            if (poComboBoxServidor.getSelectedIndex() < 0) {
                poComboBoxServidor.setSelectedIndex(0);
            }
        } catch (Throwable e) {
        }
        //ponemos la conexion guardada
        try {
            if (lsConexion != null) {
                poComboBoxServidor.mbSeleccionarClave(lsConexion);
            }
        } catch (Throwable e) {
        }
    }

    public static String getUltUsuario(JDatosGeneralesXML poXML) {
        return poXML.getPropiedad(JDatosGeneralesAplicacionModelo.mcsUltUsuario);
    }

    public static String getUltTipoConexion(JDatosGeneralesXML poXML) {
        try {
            return poXML.getPropiedad(JDatosGeneralesAplicacionModelo.mcsUltTipoConexion);
        } catch (Exception e) {
            return "";
        }
    }

    public static void autentificar(JComboBoxCZ poComboBoxServidor, String psLogin, String psPassWord, JDatosGeneralesAplicacionModelo poDatosGenerales) throws Throwable {
        if (poComboBoxServidor.getSelectedIndex() < 0) {
            throw new Exception("No se ha seleccionado ningun servidor");
        }
        autentificar(poComboBoxServidor.getFilaActual().msCampo(0), psLogin, psPassWord, poDatosGenerales);
    }
    
    //Autentificar directamente con el nombre del servidor
    public static void autentificar(String psServidor, String psLogin, String psPassWord, JDatosGeneralesAplicacionModelo poDatosGenerales) throws Throwable {
        poDatosGenerales.autentificar(psServidor, psLogin, psPassWord);
    }
    
    public JDatosGeneralesAplicacion getDatosGenerales() {
        return moDatosGenerales;
    }

    private static boolean mbComparar(String psCadena1, String psCadena2) {
        boolean lbResult = true;
        if (psCadena1 == null || psCadena1.equals("")) {
            if (psCadena2 == null || psCadena2.equals("")) {
                lbResult = true;
            } else {
                lbResult = false;
            }
        } else {
            if (psCadena2 == null || psCadena2.equals("")) {
                lbResult = false;
            } else {
                lbResult = (psCadena1.equals(psCadena2));
            }
        }

        return lbResult;
    }

    public static void mostrarConexiones(final JComboBoxCZ poComboBoxServidor, final JDatosGeneralesAplicacionModelo poDatosGenerales) throws Exception {
        final JT2CONEXIONES loConexiones = new JT2CONEXIONES(
                poDatosGenerales.getDatosGeneralesXML()
                , (poDatosGenerales.getMostrarPantalla() == null 
                        ? JGUIxConfigGlobal.getInstancia().getMostrarPantalla() 
                        : poDatosGenerales.getMostrarPantalla()));

        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loConexiones, 800,600, 1, IMostrarPantalla.mclEdicionDialog);
        
        utilesGUIx.JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(loParam);
        
//        
//        loConexiones.mostrarFormPrinci(new CallBack<IPanelControlador>() {
//            @Override
//            public void callBack(IPanelControlador poControlador) {
//                try {
//                    recargarCombo(poComboBoxServidor, poDatosGenerales.getDatosGeneralesXML());
//                    try {
//                        poComboBoxServidor.mbSeleccionarClave(poDatosGenerales.getUltTipoConexion());
//                    } catch (Exception ex) {
//                        JDepuracion.anadirTexto(JFormLogin.class.getName(), ex);
//                    }
//                } catch (Exception ex) {
//                    JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(
//                            null, ex, null);
//                }
//            }
//        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        lblLoginImg = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextLogin = new utilesGUIx.JTextFieldCZ();
        jLabel2 = new javax.swing.JLabel();
        jTextPassWord = new javax.swing.JPasswordField();
        btnCambioContrasena = new javax.swing.JButton();
        lblConexion = new javax.swing.JLabel();
        cmbConexion = new utilesGUIx.JComboBoxCZ();
        jBtnConexionesBD = new utilesGUIx.JButtonCZ();
        jPanelBotones = new javax.swing.JPanel();
        jButtonAceptar = new utilesGUIx.JButtonCZ();
        jLabel4 = new javax.swing.JLabel();
        jButtonCancelar = new utilesGUIx.JButtonCZ();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Conectar");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(lblLoginImg, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jTextLogin, gridBagConstraints);

        jLabel2.setText("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel2, gridBagConstraints);

        jTextPassWord.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPassWordKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jTextPassWord, gridBagConstraints);

        btnCambioContrasena.setText("Cambiar");
        btnCambioContrasena.setFocusable(false);
        btnCambioContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambioContrasenaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel2.add(btnCambioContrasena, gridBagConstraints);

        lblConexion.setText("Servidor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(lblConexion, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(cmbConexion, gridBagConstraints);

        jBtnConexionesBD.setText("...");
        jBtnConexionesBD.setFocusable(false);
        jBtnConexionesBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConexionesBDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jBtnConexionesBD, gridBagConstraints);

        jPanel3.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanelBotones.setBackground(new java.awt.Color(175, 181, 186));
        jPanelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });
        jPanelBotones.add(jButtonAceptar);

        jLabel4.setText("        ");
        jPanelBotones.add(jLabel4);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        jPanelBotones.add(jButtonCancelar);

        jPanel3.add(jPanelBotones, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(497, 302));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        try {
            autentificar(cmbConexion, jTextLogin.getText(), jTextPassWord.getText(), moDatosGenerales);
            dispose();
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e, true);
        }

    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        moDatosGenerales.setServer(null);
        dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextPassWordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPassWordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.setKeyCode(0);
            jTextPassWord.transferFocus();
        }

    }//GEN-LAST:event_jTextPassWordKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        try {
            recargarCombo(cmbConexion, moDatosGenerales.getDatosGeneralesXML());
            if (jTextLogin.getTextReal().length()!=0) {
                jTextPassWord.grabFocus();
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_formWindowActivated

    private void btnCambioContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambioContrasenaActionPerformed
        try {
            autentificar(cmbConexion, jTextLogin.getText(), jTextPassWord.getText(), moDatosGenerales);
            final JTPlugInUsuarios loUsu = moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW().getUsuarios();
            loUsu.moList.buscar(JListDatos.mclTIgual, loUsu.lPosiNOMBRE, jTextLogin.getText());
            loUsu.moList.moFila().setTipoModif(JListDatos.mclNada);
            JPanelCambioPassWord loPanel = new JPanelCambioPassWord();
            loPanel.setDatos(
                    moDatosGenerales.getParametrosAplicacion().getPlugInSeguridadRW()
                    , loUsu);
            
            JMostrarPantallaParam loParam = new JMostrarPantallaParam((IFormEdicion)loPanel, JMostrarPantalla.mclEdicionFrame);
            loParam.setCallBack(new CallBack<JMostrarPantallaParam>() {
                public void callBack(JMostrarPantallaParam poControlador) {
                    jTextPassWord.setText(loUsu.getPASSWORD().getString());
                }
            });
            loParam.setTipoMostrar(loParam.mclEdicionDialog);
            utilesGUIx.JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(loParam);
        } catch (Throwable ex) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex);
        }
    }//GEN-LAST:event_btnCambioContrasenaActionPerformed

    private void jBtnConexionesBDActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            mostrarConexiones(cmbConexion, moDatosGenerales);
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambioContrasena;
    private utilesGUIx.JComboBoxCZ cmbConexion;
    private utilesGUIx.JButtonCZ jBtnConexionesBD;
    private utilesGUIx.JButtonCZ jButtonAceptar;
    private utilesGUIx.JButtonCZ jButtonCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBotones;
    private utilesGUIx.JTextFieldCZ jTextLogin;
    private javax.swing.JPasswordField jTextPassWord;
    private javax.swing.JLabel lblConexion;
    private javax.swing.JLabel lblLoginImg;
    // End of variables declaration//GEN-END:variables
}
