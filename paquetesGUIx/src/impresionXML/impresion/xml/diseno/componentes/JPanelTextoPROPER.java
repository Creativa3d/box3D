/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import utilesGUIx.Rectangulo;
import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JUtilTabla;
import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlTexto;
import impresionXML.impresion.xml.diseno.campos.JTCAMPOS;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JColorChooser;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;
import utilesGUIx.formsGenericos.edicion.JPanelEdicionAbstract;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author eduardo
 */
public class JPanelTextoPROPER extends JPanelEdicionAbstract implements ActionListener, FocusListener {
    private JxmlTexto moTexto;
    private JxmlInforme moInforme;
    private boolean mbMostrarDatos=false;

    /**
     * Creates new form JPanelCuadradoVIEW
     */
    public JPanelTextoPROPER() {
        initComponents();
        txtAlto.addActionListener(this);
        txtAncho.addActionListener(this);
        txtAngulo.addActionListener(this);
        txtNombre.addActionListener(this);
        txtTexto.addActionListener(this);
        txtX.addActionListener(this);
        txtY.addActionListener(this);
        chkMultilinea.addActionListener(this);
        cmbAlineacion.addActionListener(this);
        cmbFuente.addActionListener(this);
        
        txtAlto.addFocusListener(this);
        txtAncho.addFocusListener(this);
        txtAngulo.addFocusListener(this);
        txtNombre.addFocusListener(this);
        txtTexto.addFocusListener(this);
        txtX.addFocusListener(this);
        txtY.addFocusListener(this);
        chkMultilinea.addFocusListener(this);
        cmbAlineacion.addFocusListener(this);
        cmbFuente.addFocusListener(this);
        
    }
    
    public void setDatos(JxmlTexto poTexto, JxmlInforme poInforme){
        moTexto=poTexto;
        moInforme=poInforme;
    }


    public void rellenarPantalla() throws Exception {
        mbMostrarDatos=true;
        try{
            cmbAlineacion.borrarTodo();
            cmbAlineacion.addLinea(String.valueOf(ITextoLibre.mclAlineacionIzquierda) + "-Izquierda", String.valueOf(ITextoLibre.mclAlineacionIzquierda) + JFilaDatosDefecto.mcsSeparacion1 );
            cmbAlineacion.addLinea(String.valueOf(ITextoLibre.mclAlineacionDerecha) + "-Derecha", String.valueOf(ITextoLibre.mclAlineacionDerecha) + JFilaDatosDefecto.mcsSeparacion1 );
            cmbAlineacion.addLinea(String.valueOf(ITextoLibre.mclAlineacionCentro) + "-Centro", String.valueOf(ITextoLibre.mclAlineacionCentro) + JFilaDatosDefecto.mcsSeparacion1 );
            cmbAlineacion.addLinea(String.valueOf(ITextoLibre.mclAlineacionJustificada) + "-Justificada", String.valueOf(ITextoLibre.mclAlineacionJustificada) + JFilaDatosDefecto.mcsSeparacion1 );

            cmbFuente.borrarTodo();
            for(int i = 0 ; i < moInforme.sizeFuente();i++){
                JxmlFuente loFuente = moInforme.getFuente(i);
                cmbFuente.addLinea(
                        loFuente.getNombre() + "-" + loFuente.getFuente() 
                         + " " + String.valueOf(loFuente.getSize()) + " " + (loFuente.isBold() ? "Negrita":"")
                         + " " + (loFuente.isCursiva()? "Cursiva":"")+ " " + (loFuente.isUnderline()? "Subrayado":"")
                         + " " + (loFuente.isTachado()? "Tachado":"")
                        , loFuente.getNombre() + JFilaDatosDefecto.mcsSeparacion1 );
            }
        }finally{
            mbMostrarDatos=false;
        }
        
    }

    public void habilitarSegunEdicion() throws Exception {
    }

    public void ponerTipoTextos() throws Exception {
        txtAlto.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
        txtAncho.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
        txtAngulo.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
        txtX.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
        txtY.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
    }

    public void mostrarDatos() throws Exception {
        mbMostrarDatos=true;
        try{
            txtAlto.setValueTabla(String.valueOf(moTexto.getPosicionDestino().getHeight()));
            txtAncho.setValueTabla(String.valueOf(moTexto.getPosicionDestino().getWidth()));
            txtAngulo.setValueTabla(String.valueOf(moTexto.getAngulo()));
            txtNombre.setValueTabla(moTexto.getNombre());
            txtTexto.setValueTabla(moTexto.getTexto());
            txtX.setValueTabla(String.valueOf(moTexto.getPosicionDestino().getX()));
            txtY.setValueTabla(String.valueOf(moTexto.getPosicionDestino().getY()));
            chkMultilinea.setValueTabla(moTexto.isMultilinea());
            cmbAlineacion.setValueTabla(String.valueOf(moTexto.getAlineacion()) + JFilaDatosDefecto.mcsSeparacion1);
            cmbFuente.setValueTabla(moTexto.getFuente() + JFilaDatosDefecto.mcsSeparacion1);
            lblColor.setBackground(moTexto.getColor());
        }finally{
            mbMostrarDatos=false;
        }
    }

    public void establecerDatos() throws Exception {
        Rectangle2D loPos = new Rectangle2D.Double(
                Float.valueOf(txtX.getText()).floatValue()
                , Float.valueOf(txtY.getText()).floatValue()
                , Float.valueOf(txtAncho.getText()).floatValue()
                , Float.valueOf(txtAlto.getText()).floatValue()
                );
        moTexto.setPosicionDestino(loPos);
        moTexto.setAngulo(Double.valueOf(txtAngulo.getText()).doubleValue());

        moTexto.setNombre(txtNombre.getText());
        moTexto.setTexto(txtTexto.getText());
        moTexto.setMultilinea(chkMultilinea.isSelected());
        moTexto.setAlineacion(Integer.valueOf(cmbAlineacion.getFilaActual().msCampo(0)).intValue() );
        moTexto.setFuente(cmbFuente.getFilaActual().msCampo(0));
        moTexto.setColor(lblColor.getBackground());
    }
    public void aceptar() throws Exception {
    }

    public void cancelar() throws Exception {
    }


    public Rectangulo getTanano() {
        return new Rectangulo(800, 600);
    }

    public String getTitulo() {
        return "Texto";
    }
    public void actionPerformed(ActionEvent e) {
        try {
            if(!mbMostrarDatos){
                establecerDatos();
            }
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }
    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
        actionPerformed(null);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        txtNombre = new utilesGUIx.JTextFieldCZ();
        btnBuscarNombre = new utilesGUIx.JButtonCZ();
        jLabelCZ7 = new utilesGUIx.JLabelCZ();
        txtTexto = new utilesGUIx.JTextFieldCZ();
        jLabelCZ2 = new utilesGUIx.JLabelCZ();
        txtX = new utilesGUIx.JTextFieldCZ();
        jLabelCZ3 = new utilesGUIx.JLabelCZ();
        txtY = new utilesGUIx.JTextFieldCZ();
        jLabelCZ4 = new utilesGUIx.JLabelCZ();
        txtAncho = new utilesGUIx.JTextFieldCZ();
        jLabelCZ5 = new utilesGUIx.JLabelCZ();
        txtAlto = new utilesGUIx.JTextFieldCZ();
        jLabelCZ6 = new utilesGUIx.JLabelCZ();
        cmbAlineacion = new utilesGUIx.JComboBoxCZ();
        jLabelCZ8 = new utilesGUIx.JLabelCZ();
        chkMultilinea = new utilesGUIx.JCheckBoxCZ();
        jLabelCZ9 = new utilesGUIx.JLabelCZ();
        lblColor = new utilesGUIx.JLabelCZ();
        jLabelCZ10 = new utilesGUIx.JLabelCZ();
        cmbFuente = new utilesGUIx.JComboBoxCZ();
        jLabelCZ11 = new utilesGUIx.JLabelCZ();
        txtAngulo = new utilesGUIx.JTextFieldCZ();
        jPanel1 = new javax.swing.JPanel();
        btnEliminar = new utilesGUIx.JButtonCZ();
        btnClonar = new utilesGUIx.JButtonCZ();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(204, 255, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Texto");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jLabel1, gridBagConstraints);

        jLabelCZ1.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtNombre, gridBagConstraints);

        btnBuscarNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/impresionXML/impresion/xml/diseno/img/Zoom16.gif"))); // NOI18N
        btnBuscarNombre.setToolTipText("Buscar nombre");
        btnBuscarNombre.setMaximumSize(new java.awt.Dimension(25, 25));
        btnBuscarNombre.setMinimumSize(new java.awt.Dimension(25, 25));
        btnBuscarNombre.setPreferredSize(new java.awt.Dimension(25, 25));
        btnBuscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        add(btnBuscarNombre, gridBagConstraints);

        jLabelCZ7.setText("Texto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtTexto, gridBagConstraints);

        jLabelCZ2.setText("X");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtX, gridBagConstraints);

        jLabelCZ3.setText("Y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtY, gridBagConstraints);

        jLabelCZ4.setText("Ancho");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtAncho, gridBagConstraints);

        jLabelCZ5.setText("Alto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtAlto, gridBagConstraints);

        jLabelCZ6.setText("Alineación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbAlineacion, gridBagConstraints);

        jLabelCZ8.setText("Multilinea");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(chkMultilinea, gridBagConstraints);

        jLabelCZ9.setText("Color");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ9, gridBagConstraints);

        lblColor.setText("  ");
        lblColor.setOpaque(true);
        lblColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblColorMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblColor, gridBagConstraints);

        jLabelCZ10.setText("Fuente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbFuente, gridBagConstraints);

        jLabelCZ11.setText("Angulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtAngulo, gridBagConstraints);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/impresionXML/impresion/xml/diseno/img/Delete16.gif"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar);

        btnClonar.setText("Clonar");
        btnClonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClonarActionPerformed(evt);
            }
        });
        jPanel1.add(btnClonar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void lblColorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblColorMouseClicked

        //        final JColorChooser colorChooser = new JColorChooser();         
        Color loColor = JColorChooser.showDialog(this, "", lblColor.getBackground());         
        if (loColor != null) {             
            lblColor.setBackground(loColor);         
        }     
        actionPerformed(null);
        
    }//GEN-LAST:event_lblColorMouseClicked

    private void btnBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNombreActionPerformed
        try {
            
            JBusqueda loBus = new JBusqueda(new IConsulta() {
                JTCAMPOS loCampos;

                public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
                    loCampos = moInforme.getDiseno().getTablaCamposPredefinidos();
                    loCampos.getList().getFiltro().addCondicionAND(JListDatos.mclTIgual, JTCAMPOS.lPosiTIPO, JxmlTexto.mcsNombreXml);
                    loCampos.getList().filtrar();
                    loCampos.moList = JUtilTabla.clonarFilasListDatos(loCampos.getList(), false);
                }

                public JListDatos getList() {
                    return loCampos.moList;
                }

                public void addFilaPorClave(IFilaDatos poFila) throws Exception {
                }

                public boolean getPasarCache() {
                    return false;
                }
            }, "Campos");
            
            loBus.getParametros().setCallBack(new CallBack<IPanelControlador>() {

                public void callBack(IPanelControlador poControlador) {
                    try {
                        poControlador.getConsulta().getList().setIndex(poControlador.getIndex());
                        txtNombre.setText(poControlador.getConsulta().getList().getFields(JTCAMPOS.lPosiNOMBRE).getString());
                        establecerDatos();
                    } catch (Exception ex) {
                        JMsgBox.mensajeErrorYLog(JPanelTextoPROPER.this, ex);
                    }
                }
            });
            
            loBus.mostrarBusq();
            
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }

    }//GEN-LAST:event_btnBuscarNombreActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            moInforme.getBanda(0).remove(moTexto);
            moTexto = new JxmlTexto();
            mostrarDatos();
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnClonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClonarActionPerformed
        try {
            JxmlTexto loXML = (JxmlTexto) moTexto.clone();
            moInforme.getBanda(0).add(loXML);
//            IXMLDesign loDesig = JFactoryDesignXML.getInstance().getDesignXML(loXML, moInforme);
//            add(loDesig);
            
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }

    }//GEN-LAST:event_btnClonarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnBuscarNombre;
    private utilesGUIx.JButtonCZ btnClonar;
    private utilesGUIx.JButtonCZ btnEliminar;
    private utilesGUIx.JCheckBoxCZ chkMultilinea;
    private utilesGUIx.JComboBoxCZ cmbAlineacion;
    private utilesGUIx.JComboBoxCZ cmbFuente;
    private javax.swing.JLabel jLabel1;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private utilesGUIx.JLabelCZ jLabelCZ10;
    private utilesGUIx.JLabelCZ jLabelCZ11;
    private utilesGUIx.JLabelCZ jLabelCZ2;
    private utilesGUIx.JLabelCZ jLabelCZ3;
    private utilesGUIx.JLabelCZ jLabelCZ4;
    private utilesGUIx.JLabelCZ jLabelCZ5;
    private utilesGUIx.JLabelCZ jLabelCZ6;
    private utilesGUIx.JLabelCZ jLabelCZ7;
    private utilesGUIx.JLabelCZ jLabelCZ8;
    private utilesGUIx.JLabelCZ jLabelCZ9;
    private javax.swing.JPanel jPanel1;
    private utilesGUIx.JLabelCZ lblColor;
    private utilesGUIx.JTextFieldCZ txtAlto;
    private utilesGUIx.JTextFieldCZ txtAncho;
    private utilesGUIx.JTextFieldCZ txtAngulo;
    private utilesGUIx.JTextFieldCZ txtNombre;
    private utilesGUIx.JTextFieldCZ txtTexto;
    private utilesGUIx.JTextFieldCZ txtX;
    private utilesGUIx.JTextFieldCZ txtY;
    // End of variables declaration//GEN-END:variables



}
