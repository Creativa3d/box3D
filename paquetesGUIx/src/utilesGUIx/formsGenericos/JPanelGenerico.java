/*
 * JPanelBusqueda.java
 *
 * Created on 6 de septiembre de 2004, 12:07
 */

package utilesGUIx.formsGenericos;

import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import utilesGUIx.JButtonCZ;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.JTableCZ;
import utilesGUIx.WrapLayout;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;


/**representa una panel de búsqueda/principal, con posibilidades de filtros, botones de edicón estandar y botones dinamicos personalizados*/
public class JPanelGenerico extends JPanelGenericoAbstract {
    private static final long serialVersionUID = 1L;

    public static final int mclTipo = 0;
    public static Dimension moDimensionRelacionesDefecto = new Dimension(160, 29);

    
    /** Creates new form JPanelBusqueda */
    public JPanelGenerico() {
        super();
        initComponents();
        jPanelEditar.setLayout(new WrapLayout());
        jButtonAceptar.setMnemonic('a');
        jButtonCancelar.setMnemonic('c');
        jBtnBorrar.setMnemonic('b');
        jBtnNuevo.setMnemonic('n');
        jBtnEditar.setMnemonic('e');
        jBtnRefrescar.setMnemonic('r');
        
        inicializar();
        
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelGenerico");


        
    }
    @Override
    public JPanelGeneralFiltro getPanelGeneralFiltro1() {
        return jPanelGeneralFiltro1;
    }

    @Override
    public JPanelGeneralFiltroLinea getPanelGeneralFiltroLinea1() {
        return jPanelGeneralFiltroLinea1;
    }

    @Override
    public JPanelGeneralFiltroTodosCamp getPanelGeneralFiltroTodosCamp1() {
        return jPanelGeneralFiltroTodosCamp1;
    }

    @Override
    public JButtonCZ getBtnAceptar() {
        return jButtonAceptar;
    }

    @Override
    public JButtonCZ getBtnBorrar() {
        return jBtnBorrar;
    }

    @Override
    public JButtonCZ getBtnCancelar() {
        return jButtonCancelar;
    }

    @Override
    public JButtonCZ getBtnEditar() {
        return jBtnEditar;
    }

    @Override
    public JButtonCZ getBtnNuevo() {
        return jBtnNuevo;
    }

    @Override
    public JButtonCZ getBtnRefrescar() {
        return jBtnRefrescar;
    }

    @Override
    public JButtonCZ getbtnConfig() {
        return btnConfig;
    }

    @Override
    public JComboBoxCZ getcmbConfig() {
        return cmbConfig;
    }

    @Override
    public JComboBoxCZ getcmbFiltros() {
       return cmbFiltros;
    }
    @Override
    public JComboBoxCZ getcmbTipoFiltroRapido() {
        return cmbTipoFiltroRapido;
    }

    @Override
    public JTableCZ getTabla() {
        return jTableDatos;
    }

    @Override
    public void setTotal(String psValor) {
        lblTotal.setText(psValor);
        lblTotal.setToolTipText(psValor);
    }

    @Override
    public void setPosicion(String psValor) {
        lblPosicion.setText(psValor);
        lblPosicion.setToolTipText(psValor);
    }
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        jSplitPane1.setEnabled(true);
        jScrollPaneTablaDatos.setEnabled(true);
    }
    @Override
    public Container crearContenedorBotones(String psGrupo) {

        utilesGUIx.JPanelTareas jPanelTareas1 = new utilesGUIx.JPanelTareas();
        jPanelTareas1.setText((psGrupo.compareTo("")==0 ? JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  "General"): psGrupo));
//        jPanelTareas1.getPanel().setLayout(new FlowLayout());

        return jPanelTareas1;
    }

    @Override
    public JScrollPane getScrollPaneTablaDatos() {
        return jScrollPaneTablaDatos;
    }

    @Override
    public JPanel getPanelRelacionadoGen() {
        return jPanelRelacionadoGen;
    }
    @Override
    public void propiedadesBotonRecienCreado(JButtonCZ poBoton) {
        
    }

    @Override
    public void setVisiblePanelConfigyFiltroRap(boolean pbVisible) {
        jPanelConfigyFiltroRap.setVisible(pbVisible);
    }

    @Override
    public void setVisiblePanelTareasFiltro(boolean pbVisible) {
        btnMasFiltros.setVisible(pbVisible);
    }

    @Override
    public Container getPanelEditar() {
        return jPanelEditar;
    }

    @Override
    public Dimension getDimensionDefecto(final IBotonRelacionado poBoton) {
        if(poBoton!=null && !poBoton.isEsPrincipal()){
            return moDimensionRelacionesDefecto;
        }else{
            return null;
        }
    }

    @Override
    public Object getPanelInformacion() {
        return jPanelInformacion;
    }

    public void setVisibleSplash(boolean pbVisible, String psTexto) {
        if(pbVisible){
            if(!"Procesando....".equalsIgnoreCase(psTexto)){
                lblSplashLabel.setText(psTexto);
            } else {
                lblSplashLabel.setText("");
            }
            
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            add(jPanelSplash, gridBagConstraints);
        }else{
            remove(jPanelSplash);
        }
    }

    


        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelGeneralFiltro1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltro();
        jPanelSplash = new javax.swing.JPanel();
        lblSplashLabel = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelRelacionadoGen = new utilesGUIx.JPanelTareasConj();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanelCabezera = new javax.swing.JPanel();
        jPanelConfigyFiltroRap = new javax.swing.JPanel();
        btnConfig = new utilesGUIx.JButtonCZ();
        cmbConfig = new utilesGUIx.JComboBoxCZ();
        cmbTipoFiltroRapido = new utilesGUIx.JComboBoxCZ();
        cmbFiltros = new utilesGUIx.JComboBoxCZ();
        btnMasFiltros = new javax.swing.JButton();
        btnOcultarCabezera = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblPosicion = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        JPanelTipoFiltroRapido = new javax.swing.JPanel();
        jPanelGeneralFiltroTodosCamp1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp();
        jPanelGeneralFiltroLinea1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltroLinea();
        btnMostrarCabezera = new javax.swing.JButton();
        jScrollPaneTablaDatos = new javax.swing.JScrollPane();
        jTableDatos = new utilesGUIx.JTableCZ();
        jPanel3 = new javax.swing.JPanel();
        jPanelInformacion = new javax.swing.JPanel();
        jPanelEditar = new javax.swing.JPanel();
        jBtnNuevo = new utilesGUIx.JButtonCZ();
        jBtnEditar = new utilesGUIx.JButtonCZ();
        jBtnBorrar = new utilesGUIx.JButtonCZ();
        jBtnRefrescar = new utilesGUIx.JButtonCZ();
        jButtonAceptar = new utilesGUIx.JButtonCZ();
        jButtonCancelar = new utilesGUIx.JButtonCZ();

        jPanelSplash.setLayout(new java.awt.BorderLayout());

        lblSplashLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblSplashLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSplashLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/espera.gif"))); // NOI18N
        jPanelSplash.add(lblSplashLabel, java.awt.BorderLayout.CENTER);

        setBackground(new java.awt.Color(175, 181, 186));
        setMinimumSize(new java.awt.Dimension(100, 20));
        setPreferredSize(new java.awt.Dimension(600, 200));
        setLayout(new java.awt.GridBagLayout());

        jPanelRelacionadoGen.setMinimumSize(new java.awt.Dimension(170, 34));
        jPanelRelacionadoGen.setPreferredSize(new java.awt.Dimension(170, 34));
        jSplitPane1.setLeftComponent(jPanelRelacionadoGen);

        jPanel4.setBackground(new java.awt.Color(237, 234, 229));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(237, 234, 229));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanelCabezera.setLayout(new java.awt.CardLayout());

        jPanelConfigyFiltroRap.setOpaque(false);
        jPanelConfigyFiltroRap.setLayout(new java.awt.GridBagLayout());

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/ColumnInsertAfter16.gif"))); // NOI18N
        btnConfig.setToolTipText("Configurar columnas"); // NOI18N
        btnConfig.setActionCommand("configurarcolumnas");
        btnConfig.setFocusable(false);
        btnConfig.setMinimumSize(new java.awt.Dimension(26, 26));
        btnConfig.setPreferredSize(new java.awt.Dimension(26, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(btnConfig, gridBagConstraints);

        cmbConfig.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbConfig.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(cmbConfig, gridBagConstraints);

        cmbTipoFiltroRapido.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTipoFiltroRapido.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(cmbTipoFiltroRapido, gridBagConstraints);

        cmbFiltros.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltros.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(cmbFiltros, gridBagConstraints);

        btnMasFiltros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/base_arrow_up.png"))); // NOI18N
        btnMasFiltros.setText("Más filtros"); // NOI18N
        btnMasFiltros.setFocusable(false);
        btnMasFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasFiltrosActionPerformed(evt);
            }
        });
        jPanelConfigyFiltroRap.add(btnMasFiltros, new java.awt.GridBagConstraints());

        btnOcultarCabezera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/crossed_circle.png"))); // NOI18N
        btnOcultarCabezera.setText("Ocultar"); // NOI18N
        btnOcultarCabezera.setToolTipText("Ocultar botones y filtros"); // NOI18N
        btnOcultarCabezera.setFocusable(false);
        btnOcultarCabezera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOcultarCabezeraActionPerformed(evt);
            }
        });
        jPanelConfigyFiltroRap.add(btnOcultarCabezera, new java.awt.GridBagConstraints());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        jPanelConfigyFiltroRap.add(jLabel3, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblPosicion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblPosicion.setForeground(new java.awt.Color(153, 51, 255));
        lblPosicion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPosicion.setText("1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblPosicion, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 51, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(" de "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jLabel7, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(153, 51, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("9000"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanelConfigyFiltroRap.add(jPanel2, gridBagConstraints);

        JPanelTipoFiltroRapido.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        JPanelTipoFiltroRapido.add(jPanelGeneralFiltroTodosCamp1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        JPanelTipoFiltroRapido.add(jPanelGeneralFiltroLinea1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelConfigyFiltroRap.add(JPanelTipoFiltroRapido, gridBagConstraints);

        jPanelCabezera.add(jPanelConfigyFiltroRap, "filtro");

        btnMostrarCabezera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/up16.png"))); // NOI18N
        btnMostrarCabezera.setToolTipText("Mostrar botones y filtros"); // NOI18N
        btnMostrarCabezera.setActionCommand("mostratbotonesyfiltros");
        btnMostrarCabezera.setBorder(null);
        btnMostrarCabezera.setFocusable(false);
        btnMostrarCabezera.setMaximumSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.setMinimumSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.setPreferredSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCabezeraActionPerformed(evt);
            }
        });
        jPanelCabezera.add(btnMostrarCabezera, "filtromostrar");

        jPanel8.add(jPanelCabezera, java.awt.BorderLayout.NORTH);

        jScrollPaneTablaDatos.setBackground(new java.awt.Color(237, 234, 229));
        jScrollPaneTablaDatos.setBorder(null);

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDatos.setEditingColumn(0);
        jTableDatos.setEditingRow(0);
        jTableDatos.setNextFocusableComponent(jButtonAceptar);
        jScrollPaneTablaDatos.setViewportView(jTableDatos);

        jPanel8.add(jScrollPaneTablaDatos, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanelInformacion.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanelInformacion, gridBagConstraints);

        jPanelEditar.setBackground(new java.awt.Color(175, 181, 186));
        jPanelEditar.setOpaque(false);

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Add16.gif"))); // NOI18N
        jBtnNuevo.setText("Nuevo"); // NOI18N
        jPanelEditar.add(jBtnNuevo);

        jBtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Edit16.gif"))); // NOI18N
        jBtnEditar.setText("Editar"); // NOI18N
        jPanelEditar.add(jBtnEditar);

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        jBtnBorrar.setText("Borrar"); // NOI18N
        jPanelEditar.add(jBtnBorrar);

        jBtnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Refresh16.gif"))); // NOI18N
        jBtnRefrescar.setText("Refrescar"); // NOI18N
        jPanelEditar.add(jBtnRefrescar);

        jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jButtonAceptar.setText("Aceptar"); // NOI18N
        jPanelEditar.add(jButtonAceptar);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jButtonCancelar.setText("Cancelar"); // NOI18N
        jPanelEditar.add(jButtonCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanelEditar, gridBagConstraints);

        jPanel8.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarCabezera() {
        ((CardLayout)(jPanelCabezera.getLayout())).show(
                jPanelCabezera,
                "filtro");
        jPanelConfigyFiltroRap.setVisible(true);
        btnMostrarCabezera.setVisible(false);
        jPanelCabezera.setPreferredSize(new Dimension(502, 72));
        jPanelCabezera.setMinimumSize(new Dimension(502, 72));
        updateUI();
    }
    private void ocultarCabezera() {
        ((CardLayout)(jPanelCabezera.getLayout())).show(
                jPanelCabezera,
                "filtromostrar");
        jPanelConfigyFiltroRap.setVisible(false);
        btnMostrarCabezera.setVisible(true);
        jPanelCabezera.setPreferredSize(new Dimension(50, 10));
        jPanelCabezera.setMinimumSize(new Dimension(50, 10));
        updateUI();
    }

    private void btnMostrarCabezeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCabezeraActionPerformed
        mostrarCabezera();
}//GEN-LAST:event_btnMostrarCabezeraActionPerformed

    private void btnMasFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasFiltrosActionPerformed
        try{
            jPanelGeneralFiltro1.antesmostrar();
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(
                jPanelGeneralFiltro1, 700, 500, getMostrarTipo());
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

}//GEN-LAST:event_btnMasFiltrosActionPerformed

    private void btnOcultarCabezeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcultarCabezeraActionPerformed
        ocultarCabezera();
    }//GEN-LAST:event_btnOcultarCabezeraActionPerformed
   

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelTipoFiltroRapido;
    private utilesGUIx.JButtonCZ btnConfig;
    private javax.swing.JButton btnMasFiltros;
    private javax.swing.JButton btnMostrarCabezera;
    private javax.swing.JButton btnOcultarCabezera;
    private utilesGUIx.JComboBoxCZ cmbConfig;
    private utilesGUIx.JComboBoxCZ cmbFiltros;
    private utilesGUIx.JComboBoxCZ cmbTipoFiltroRapido;
    private utilesGUIx.JButtonCZ jBtnBorrar;
    private utilesGUIx.JButtonCZ jBtnEditar;
    private utilesGUIx.JButtonCZ jBtnNuevo;
    private utilesGUIx.JButtonCZ jBtnRefrescar;
    private utilesGUIx.JButtonCZ jButtonAceptar;
    private utilesGUIx.JButtonCZ jButtonCancelar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelCabezera;
    private javax.swing.JPanel jPanelConfigyFiltroRap;
    private javax.swing.JPanel jPanelEditar;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltro jPanelGeneralFiltro1;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltroLinea jPanelGeneralFiltroLinea1;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp jPanelGeneralFiltroTodosCamp1;
    private javax.swing.JPanel jPanelInformacion;
    private utilesGUIx.JPanelTareasConj jPanelRelacionadoGen;
    private javax.swing.JPanel jPanelSplash;
    private javax.swing.JScrollPane jScrollPaneTablaDatos;
    private javax.swing.JSplitPane jSplitPane1;
    private utilesGUIx.JTableCZ jTableDatos;
    private javax.swing.JLabel lblPosicion;
    private javax.swing.JLabel lblSplashLabel;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables





    
}
class JElementoBarraInterno {
    public String msGrupo;
    public Container moBarra;
    
    public JElementoBarraInterno(final String psGrupo, final Container poBarra){
        super();
        msGrupo = psGrupo;
        moBarra = poBarra;
    }
} 
